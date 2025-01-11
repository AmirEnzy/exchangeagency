package com.exchangeagencyplatform.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.servlet.http.Part;

public class FileUploadUtil {
  private static final String UPLOAD_DIR = "D:\\Coventry University\\Master\\Second semester\\Web Application and AI\\exchangeagencyamir\\src\\main\\webapp\\uploads";

  public static String savePhoto(Part photo) throws IOException {
    // Create upload directory if it doesn't exist
    File uploadDir = new File(UPLOAD_DIR);
    if (!uploadDir.exists()) {
      uploadDir.mkdirs(); // Use mkdirs to create the directory and any necessary parent directories
    }

    // Generate a unique file name
    String fileName = UUID.randomUUID().toString() + "_" + getFileName(photo);
    File file = new File(uploadDir, fileName);

    // Save the file to the directory
    try {
      Files.copy(photo.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new IOException("Failed to save file.", e);
    }

    return fileName;
  }

  private static String getFileName(Part part) {
    String contentDisposition = part.getHeader("content-disposition");
    for (String content : contentDisposition.split(";")) {
      if (content.trim().startsWith("filename")) {
        return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
      }
    }
    return null;
  }
}
