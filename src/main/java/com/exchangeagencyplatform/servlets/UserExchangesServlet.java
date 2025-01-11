package com.exchangeagencyplatform.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exchangeagencyplatform.dao.DBConnection;
import com.exchangeagencyplatform.models.Interest;
import com.exchangeagencyplatform.models.InterestListing;
import com.exchangeagencyplatform.models.Listing;
import com.exchangeagencyplatform.utils.SessionUtils;

public class UserExchangesServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(UserExchangesServlet.class.getName());

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Integer currentUserId = SessionUtils.getUserId(request);
    if (currentUserId == null) {
      response.sendRedirect(request.getContextPath() + "/login.jsp");
      return;
    }

    List<InterestListing> interestListings = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection()) {
      // Fetch listings where the user has shown interest
      String interestSql = "SELECT i.id as interest_id, i.user_id, i.listing_id, "
          + "l.id as listing_id, l.title, l.description, l.item_condition, l.photo_url, l.status, u.username "
          + "FROM interests i "
          + "JOIN listings l ON i.listing_id = l.id "
          + "JOIN users u ON l.user_id = u.id "
          + "WHERE i.user_id = ?";

      try (PreparedStatement stmt = conn.prepareStatement(interestSql)) {
        stmt.setInt(1, currentUserId);
        try (ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
            Interest interest = new Interest();
            interest.setId(rs.getInt("interest_id"));
            interest.setUserId(rs.getInt("user_id"));
            interest.setListingId(rs.getInt("listing_id"));

            Listing listing = new Listing();
            listing.setId(rs.getInt("listing_id"));
            listing.setTitle(rs.getString("title"));
            listing.setDescription(rs.getString("description"));
            listing.setItem_condition(rs.getString("item_condition"));
            listing.setPhotoUrl(rs.getString("photo_url"));
            listing.setStatus(rs.getString("status"));
            listing.setSellerUsername(rs.getString("username")); // Add this line

            InterestListing interestListing = new InterestListing(interest, listing);
            interestListings.add(interestListing);
          }
        }
      }

    } catch (SQLException e) {
      LOGGER.severe("SQL error occurred: " + e.getMessage());
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred.");
      return;
    }

    request.setAttribute("interestListings", interestListings);
    request.getRequestDispatcher("/userExchanges.jsp").forward(request, response);
  }
}
