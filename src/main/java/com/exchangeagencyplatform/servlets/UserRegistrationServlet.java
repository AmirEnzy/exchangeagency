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
import com.exchangeagencyplatform.utils.PasswordUtils;

// @WebServlet("/register")
public class UserRegistrationServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // Validate input
    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Username and password are required.");
      return;
    }

    // Hash password
    String hashedPassword = PasswordUtils.hashPassword(password);

    try (Connection conn = DBConnection.getConnection()) {
      String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
      PreparedStatement stmt = conn.prepareStatement(sql);
      stmt.setString(1, username);
      stmt.setString(2, hashedPassword);
      stmt.executeUpdate();
      response.sendRedirect("login.jsp");
    } catch (SQLException e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while registering the user.");
    }
  }
}
