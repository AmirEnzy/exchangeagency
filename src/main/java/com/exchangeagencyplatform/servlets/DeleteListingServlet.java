
package com.exchangeagencyplatform.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exchangeagencyplatform.services.ListingService;

@WebServlet("/deleteListing")
public class DeleteListingServlet extends HttpServlet {
  private ListingService listingService;

  @Override
  public void init() throws ServletException {

    listingService = new ListingService();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    int listingId = Integer.parseInt(request.getParameter("listingId"));
    System.out.println(listingId);
    boolean success = listingService.deleteListing(listingId);

    if (success) {
      response.sendRedirect("userListings");
    } else {
      response.sendRedirect("error.jsp");
    }
  }
}