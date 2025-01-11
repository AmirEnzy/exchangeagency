

package com.exchangeagencyplatform.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exchangeagencyplatform.dao.DBConnection;
import com.exchangeagencyplatform.utils.SessionUtils;

// @WebServlet("/finalizeExchange")
public class FinalizeExchangeServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger LOGGER = Logger.getLogger(FinalizeExchangeServlet.class.getName());

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Integer currentUserId = SessionUtils.getUserId(request);
    if (currentUserId == null) {
      response.sendRedirect(request.getContextPath() + "/login.jsp");
      return;
    }

    String listingIdParam = request.getParameter("listingId");
    String exchangeWithUserIdParam = request.getParameter("exchangeWithUserId");

    // Log incoming parameters
    LOGGER.info("Received request to finalize exchange with listingId: " + listingIdParam + " and exchangeWithUserId: "
        + exchangeWithUserIdParam);

    // Input validation
    if (listingIdParam == null || listingIdParam.isEmpty() || exchangeWithUserIdParam == null
        || exchangeWithUserIdParam.isEmpty()) {
      LOGGER.warning("Validation failed: Missing listingId or exchangeWithUserId.");
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "listingId and exchangeWithUserId are required.");
      return;
    }

    int listingId;
    int exchangeWithUserId;

    try {
      listingId = Integer.parseInt(listingIdParam);
      exchangeWithUserId = Integer.parseInt(exchangeWithUserIdParam);
    } catch (NumberFormatException e) {
      LOGGER.warning("Validation failed: listingId or exchangeWithUserId is not a valid integer.");
      response.sendError(HttpServletResponse.SC_BAD_REQUEST,
          "listingId and exchangeWithUserId must be valid integers.");
      return;
    }

    try (Connection conn = DBConnection.getConnection()) {
      conn.setAutoCommit(false);
      try {

        // Update the listing status to 'exchanged'
        updateListingStatus(listingId, exchangeWithUserId, conn);

        // Remove all other interests for this listing
        removeOtherInterests(listingId, conn);

        // Create an exchange record
        createExchangeRecord(listingId, currentUserId, exchangeWithUserId, conn);

        conn.commit();
        response.sendRedirect(request.getContextPath() + "/userExchanges.jsp");
      } catch (SQLException e) {
        conn.rollback();
        throw e;
      } finally {
        conn.setAutoCommit(true);
      }
    } catch (SQLException e) {
      LOGGER.log(Level.SEVERE, "Error while finalizing exchange", e);
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "An error occurred while finalizing the exchange.");
    }
  }

  private void updateListingStatus(int listingId, int exchangeWithUserId, Connection conn) throws SQLException {
    String sql = "UPDATE listings SET status = 'exchanged', exchanged_with_user_id = ? WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, exchangeWithUserId);
      stmt.setInt(2, listingId);
      int rowsAffected = stmt.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("Updating listing failed, no rows affected.");
      }
    }
  }

  private void removeOtherInterests(int listingId, Connection conn) throws SQLException {
    String sql = "DELETE FROM interests WHERE listing_id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, listingId);
      stmt.executeUpdate();
    }
  }

  private void createExchangeRecord(int listingId, int listingOwnerId, int exchangeWithUserId, Connection conn)
      throws SQLException {
    String sql = "INSERT INTO exchanges (listing_id, listing_owner_id, exchanged_with_user_id, exchange_date) VALUES (?, ?, ?, NOW())";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, listingId);
      stmt.setInt(2, listingOwnerId);
      stmt.setInt(3, exchangeWithUserId);
      stmt.executeUpdate();
    }
  }
}
