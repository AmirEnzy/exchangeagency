package com.exchangeagencyplatform.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

  public static Integer getUserId(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      Object userIdObj = session.getAttribute("userId");
      if (userIdObj instanceof Integer) {
        return (Integer) userIdObj;
      }
    }
    return null;
  }

  public static boolean isLoggedIn(HttpServletRequest request) {
    return getUserId(request) != null;
  }
}