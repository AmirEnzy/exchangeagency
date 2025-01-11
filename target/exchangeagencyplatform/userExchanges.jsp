<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>User Exchanges</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
      crossorigin="anonymous" />
    <style>
      body {
        background-color: #f8f9fa;
      }
      .container {
        margin-top: 50px;
      }
      h1 {
        color: #343a40;
        margin-bottom: 30px;
      }
      h2 {
        color: #495057;
        margin-bottom: 20px;
      }
      .table {
        background-color: #e08ed4;
        border-radius: 8px;
        overflow: hidden;
      }
      .table th,
      .table td {
        vertical-align: middle;
      }
      .table thead {
        background-color: #6f42c1;
        color: #ffffff;
      }
      .table tbody tr:nth-child(odd) {
        background-color: #f2f2f2;
      }
      .table img {
        max-width: 100px;
        max-height: 100px;
        border-radius: 5px;
      }
      .btn-primary {
        background-color: #6f42c1;
        border-color: #6f42c1;
      }
      .btn-primary:hover {
        background-color: #563d7c;
        border-color: #563d7c;
      }
      .form-group {
        margin-bottom: 1rem;
      }
    </style>
  </head>
  <body>
    <jsp:include page="navbar.jsp" />
    <div class="container">
      <h1>User Exchanges</h1>

      <table class="table table-bordered table-striped">
        <thead>
          <tr>
            <th class="title">Name</th>
            <th class="description">About</th>
            <th class="status">Status</th>
            <th class="condition">Item Condition</th>
            <th class="image">Image</th>
            <th class="action">Action</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="interestListing" items="${interestListings}">
            <tr>
              <td class="title">${interestListing.listing.title}</td>
              <td class="description">${interestListing.listing.description}</td>
              <td class="status">${interestListing.listing.status}</td>
              <td class="condition">${interestListing.listing.item_condition}</td>
              <td class="image">
                <c:if test="${not empty interestListing.listing.photoUrl}">
                  <img src="/uploads/${interestListing.listing.photoUrl}" alt="${interestListing.listing.title}" />
                </c:if>
                <c:if test="${empty interestListing.listing.photoUrl}">No Image</c:if>
              </td>
              <td>
                <form action="${pageContext.request.contextPath}/finalizeExchange" method="post">
                  <input type="hidden" name="listingId" value="${interestListing.listing.id}" />
                  <input type="hidden" name="exchangeWithUserId" value="${interestListing.interest.userId}" />
                  <input type="hidden" name="interestId" value="${interestListing.interest.id}" />
                  <button type="submit" class="btn btn-primary">Finalize Exchange</button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"></script>
  </body>
</html>
