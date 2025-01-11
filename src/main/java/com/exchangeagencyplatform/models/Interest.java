package com.exchangeagencyplatform.models;

public class Interest {
  private int id;
  private int userId;
  private int listingId;

  // Default constructor
  public Interest() {
  }

  // Constructor with fields
  public Interest(int id, int userId, int listingId) {
    this.id = id;
    this.userId = userId;
    this.listingId = listingId;
  }

  // Getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    if (id > 0) {
      this.id = id;
    } else {
      throw new IllegalArgumentException("ID must be positive.");
    }
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    if (userId > 0) {
      this.userId = userId;
    } else {
      throw new IllegalArgumentException("User ID must be positive.");
    }
  }

  public int getListingId() {
    return listingId;
  }

  public void setListingId(int listingId) {
    if (listingId > 0) {
      this.listingId = listingId;
    } else {
      throw new IllegalArgumentException("Listing ID must be positive.");
    }
  }

  // toString method for debugging
  @Override
  public String toString() {
    return "Interest{" +
        "id=" + id +
        ", userId=" + userId +
        ", listingId=" + listingId +
        '}';
  }
}
