package com.exchangeagencyplatform.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exchangeagencyplatform.dao.DBConnection;
import com.exchangeagencyplatform.utils.SessionUtils;

// @WebServlet("/expressInterest")
public class ExpressInterestServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int listingId = Integer.parseInt(request.getParameter("listingId"));

    Integer currentUserId = SessionUtils.getUserId(request);
    if (currentUserId == null) {
      response.sendRedirect(request.getContextPath() + "/login.jsp");
      return;
    }
    try (Connection conn = DBConnection.getConnection()) {
      String sql = "INSERT INTO interests (user_id, listing_id) VALUES (?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setInt(1, currentUserId);
      stmt.setInt(2, listingId);
      stmt.executeUpdate();

      response.sendRedirect("userExchanges");
    } catch (SQLException e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while expressing interest.");
    }
  }
}
