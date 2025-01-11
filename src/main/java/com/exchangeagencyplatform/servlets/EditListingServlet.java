package com.exchangeagencyplatform.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exchangeagencyplatform.models.Listing;
import com.exchangeagencyplatform.services.ListingService;

@WebServlet("/editListing")
public class EditListingServlet extends HttpServlet {

  private ListingService listingService = new ListingService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String listingIdStr = request.getParameter("listingId");
    if (listingIdStr != null) {
      try {
        int listingId = Integer.parseInt(listingIdStr);
        Listing listing = listingService.getListingById(listingId);
        if (listing != null) {
          request.setAttribute("listing", listing);
          request.getRequestDispatcher("/editListingForm.jsp").forward(request, response);
        } else {
          response.sendError(HttpServletResponse.SC_NOT_FOUND, "Listing not found");
        }
      } catch (NumberFormatException e) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid listing ID format");
      }
    } else {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Listing ID is missing");
    }
  }
}
