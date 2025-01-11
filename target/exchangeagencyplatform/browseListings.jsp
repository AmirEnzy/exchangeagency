<%@ page import="java.util.List" %> <%@ page import="com.exchangeagencyplatform.models.Listing" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
  <head>
    <title>Browse Listings</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" />
    <style>
      body {
        background-color: #f8f9fa;
      }
      h1 {
        color: #343a40;
      }
      .table thead {
        background-color: #0083f5;
        color: white;
      }
      .table tbody tr:hover {
        background-color: #f1f1f1;
      }
      .btn-primary {
        background-color: #6f42c1;
        border-color: #6f42c1;
      }
      .btn-primary:hover {
        background-color: #563d7c;
        border-color: #563d7c;
      }
    </style>
  </head>
  <body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-5">
      <table class="table table-striped table-bordered">
        <thead class="thead-dark">
          <tr>
            <th>Name</th>
            <th>About</th>
            <th>Item Condition</th>
            <th>Image</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="listing" items="${listings}">
            <tr>
              <td>${listing.title}</td>
              <td>${listing.description}</td>
              <td>${listing.item_condition}</td>
              <td>
                <c:if test="${not empty listing.photoUrl}">
                  <img
                    src="/uploads/${listing.photoUrl}"
                    alt="Listing Photo"
                    class="img-thumbnail"
                    width="100"
                    height="100" />
                </c:if>
              </td>
              <td>
                <form action="expressInterest" method="post">
                  <input type="hidden" name="listingId" value="${listing.id}" />
                  <button type="submit" class="btn btn-primary">Show Interest</button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  </body>
</html>
