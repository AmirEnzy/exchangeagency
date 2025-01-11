package com.exchangeagencyplatform.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.exchangeagencyplatform.dao.DBConnection;
import com.exchangeagencyplatform.utils.PasswordUtils;

// @WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    // Validate input
    if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
      request.setAttribute("error", "Username and password are required.");
      request.getRequestDispatcher("/login.jsp").forward(request, response);
      return;
    }

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT password, id FROM users WHERE username = ?")) {

      stmt.setString(1, username);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          String storedPassword = rs.getString("password");
          int userId = rs.getInt("id");

          // Check password
          if (PasswordUtils.verifyPassword(password, storedPassword)) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            session.setAttribute("username", username);
            response.sendRedirect(request.getContextPath() + "/browseListings");
          } else {
            handleLoginFailure(request, response);
          }
        } else {
          handleLoginFailure(request, response);
        }
      }
    } catch (SQLException e) {
      getServletContext().log("Database error occurred", e);
      request.setAttribute("error", "An unexpected error occurred. Please try again later.");
      request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
  }

  private void handleLoginFailure(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setAttribute("error", "Invalid username or password.");
    request.getRequestDispatcher("/login.jsp").forward(request, response);
  }
}