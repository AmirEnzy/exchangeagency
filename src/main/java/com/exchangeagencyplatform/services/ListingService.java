package com.exchangeagencyplatform.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.exchangeagencyplatform.dao.DBConnection;
import com.exchangeagencyplatform.models.Listing;

public class ListingService {

  // Method to get a listing by ID
  public Listing getListingById(int id) {
    Listing listing = null;
    String sql = "SELECT * FROM listings WHERE id = ?";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          listing = mapResultSetToListing(resultSet);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return listing;
  }

  // Method to update a listing
  public boolean updateListing(Listing listing) {
    String sql = "UPDATE listings SET title = ?, description = ?, item_condition = ? WHERE id = ?";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, listing.getTitle());
      statement.setString(2, listing.getDescription());
      statement.setString(3, listing.getItem_condition());
      statement.setInt(4, listing.getId());
      int rowsAffected = statement.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // Method to delete a listing
  public boolean deleteListing(int id) {
    String sql = "DELETE FROM listings WHERE id = ?";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, id);
      int rowsAffected = statement.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  // Method to get all listings for a user
  public List<Listing> getUserListings(int userId) {
    List<Listing> listings = new ArrayList<>();
    String sql = "SELECT * FROM listings WHERE user_id = ?";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setInt(1, userId);
      try (ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
          listings.add(mapResultSetToListing(resultSet));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return listings;
  }

  // Method to map ResultSet to Listing object
  private Listing mapResultSetToListing(ResultSet resultSet) throws SQLException {
    Listing listing = new Listing();
    listing.setId(resultSet.getInt("id"));
    listing.setUserId(resultSet.getInt("user_id"));
    listing.setCategoryId(resultSet.getInt("category_id"));
    listing.setTitle(resultSet.getString("title"));
    listing.setDescription(resultSet.getString("description"));
    listing.setItem_condition(resultSet.getString("item_condition"));
    listing.setPhotoUrl(resultSet.getString("photo_url"));
    listing.setStatus(resultSet.getString("status"));
    return listing;
  }
}
