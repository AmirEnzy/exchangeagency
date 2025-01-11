package com.exchangeagencyplatform.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.exchangeagencyplatform.dao.DBConnection;
import com.exchangeagencyplatform.utils.FileUploadUtil;
import com.exchangeagencyplatform.utils.SessionUtils;

@MultipartConfig
public class AddListingServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(AddListingServlet.class.getName());

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String title = request.getParameter("title");
    String description = request.getParameter("description");
    String condition = request.getParameter("item_condition");
    String categoryId = request.getParameter("category");
    String status = "available";
    Part photo = request.getPart("photo");

    // Get the logged-in user's ID
    HttpSession session = request.getSession();
    Integer loggedInUserId = (Integer) session.getAttribute("userId");

    if (loggedInUserId == null) {
      // Handle the case where no user is logged in
      response.sendRedirect(request.getContextPath() + "/login.jsp");
      return;
    }

    String photoUrl = null;
    if (photo != null && photo.getSize() > 0) {
      photoUrl = FileUploadUtil.savePhoto(photo);
      LOGGER.info("Photo uploaded successfully with URL: " + photoUrl);
    }

    try (Connection conn = DBConnection.getConnection()) {
      String sql = "INSERT INTO listings (user_id, category_id, title, description, item_condition, photo_url, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
      Integer currentUserId = SessionUtils.getUserId(request);
      if (currentUserId == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        LOGGER.warning("User not logged in. Redirecting to login page.");
        return;
      }
      stmt.setInt(1, currentUserId);
      stmt.setString(2, categoryId);
      stmt.setString(3, title);
      stmt.setString(4, description);
      stmt.setString(5, condition);
      stmt.setString(6, photoUrl);
      stmt.setString(7, status);

      stmt.executeUpdate();

      response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
      response.setHeader("Pragma", "no-cache"); // HTTP 1.0
      response.setDateHeader("Expires", 0); // Proxies
      response.sendRedirect("browseListings");
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "An error occurred while adding the listing.", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while adding the listing.");
    }
  }
}
