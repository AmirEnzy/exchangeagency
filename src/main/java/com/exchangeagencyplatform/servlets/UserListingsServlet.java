package com.exchangeagencyplatform.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exchangeagencyplatform.dao.DBConnection;
import com.exchangeagencyplatform.models.Listing;

@WebServlet("/userListings")
public class UserListingsServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = LoggerFactory.getLogger(UserListingsServlet.class);

  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<Listing> userListings = new ArrayList<>();

    // Get the logged-in user's ID
    HttpSession session = request.getSession();
    Integer loggedInUserId = (Integer) session.getAttribute("userId");

    if (loggedInUserId == null) {
      // Handle the case where no user is logged in
      response.sendRedirect(request.getContextPath() + "/login.jsp");
      return;
    }

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM listings WHERE user_id = ?")) {

      stmt.setInt(1, loggedInUserId);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          Listing listing = new Listing();
          listing.setId(rs.getInt("id"));
          listing.setUserId(rs.getInt("user_id"));
          listing.setCategoryId(rs.getInt("category_id"));
          listing.setTitle(rs.getString("title"));
          listing.setDescription(rs.getString("description"));
          listing.setItem_condition(rs.getString("item_condition"));
          listing.setPhotoUrl(rs.getString("photo_url"));
          listing.setStatus(rs.getString("status"));
          userListings.add(listing);

        }
      }

      request.setAttribute("userListings", userListings);
      request.getRequestDispatcher("/userListings.jsp").forward(request, response);
    } catch (SQLException e) {
      LOGGER.error("An error occurred while retrieving user listings: ", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "An error occurred while retrieving your listings.");
    }
  }
}