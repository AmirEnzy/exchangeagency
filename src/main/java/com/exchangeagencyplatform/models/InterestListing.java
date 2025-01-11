package com.exchangeagencyplatform.models;

public class InterestListing {
  private Interest interest;
  private Listing listing;
  private String buyerName;

  public InterestListing(Interest interest, Listing listing) {
    this.interest = interest;
    this.listing = listing;
  }

  public Interest getInterest() {
    return interest;
  }

  public void setInterest(Interest interest) {
    this.interest = interest;
  }

  public Listing getListing() {
    return listing;
  }

  public void setListing(Listing listing) {
    this.listing = listing;
  }

  public String getBuyerName() {
    return buyerName;
  }

  public void setBuyerName(String buyerName) {
    this.buyerName = buyerName;
  }
}
