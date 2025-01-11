package com.exchangeagencyplatform.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.exchangeagencyplatform.models.Listing;
import com.exchangeagencyplatform.services.ListingService;

@WebServlet("/updateListing")
@MultipartConfig
public class UpdateListingServlet extends HttpServlet {

  private ListingService listingService = new ListingService();

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      // Get parameters from the form
      String listingIdParam = request.getParameter("listingId");
      if (listingIdParam == null || listingIdParam.isEmpty()) {
        throw new IllegalArgumentException("Listing ID is missing");
      }

      int listingId = Integer.parseInt(listingIdParam);
      String title = request.getParameter("title");
      String description = request.getParameter("description");
      String itemCondition = request.getParameter("item_condition");
      String status = request.getParameter("status");

      // Handle file upload
      Part filePart = request.getPart("photoUrl");
      String photoUrl = null;

      if (filePart != null && filePart.getSize() > 0) {
        // Handle the file upload
        photoUrl = processFileUpload(filePart);
      }

      // Create or update the Listing object
      Listing listing = new Listing();
      listing.setId(listingId);
      listing.setTitle(title);
      listing.setDescription(description);
      listing.setItem_condition(itemCondition);
      listing.setStatus(status);
      listing.setPhotoUrl(photoUrl);

      // Update the listing in the database
      listingService.updateListing(listing);

      // Redirect to the listings page
      response.sendRedirect("userListings");
    } catch (NumberFormatException e) {
      // Handle invalid integer format
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Listing ID format");
    } catch (IllegalArgumentException e) {
      // Handle missing listing ID
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    } catch (Exception e) {
      // Handle any other exceptions
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while updating the listing");
    }
  }

  private String processFileUpload(Part filePart) throws IOException {
    // Define the path where uploaded files will be saved
    String uploadDir = getServletContext().getInitParameter("uploadDirectory");
    File uploadDirFile = new File(uploadDir);
    if (!uploadDirFile.exists()) {
      uploadDirFile.mkdir();
    }

    // Generate a unique file name
    String fileName = UUID.randomUUID().toString() + "_"
        + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
    Path filePath = Paths.get(uploadDir, fileName);

    // Save the file
    Files.copy(filePart.getInputStream(), filePath);

    // Return the file URL or name
    return fileName;
  }
}
