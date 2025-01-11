<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Your Listings</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #cb7dff;
        }
        .container {
            margin-top: 50px;
        }
        h1 {
            color: #343a40;
            margin-bottom: 30px;
        }
        .table {
            background-color: #f8c3ff;
            border-radius: 8px;
            overflow: hidden;
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
        .status {
            font-weight: bold;
        }
        .status.text-success {
            color: #28a745;
        }
        .status.text-primary {
            color: #007bff;
        }
        .btn-warning {
            background-color: #ffc107;
            border-color: #ffc107;
        }
        .btn-warning:hover {
            background-color: #e0a800;
            border-color: #d39e00;
        }
        .btn-danger {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #c82333;
            border-color: #bd2130;
        }
        .alert-warning {
            background-color: #fff3cd;
            color: #856404;
        }
    </style>
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container">
        <h1>Listings</h1>
        <c:if test="${not empty userListings}">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>About</th>
                        <th>Item Condition</th>
                        <th>Photo</th>
                        <th>Status</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="listing" items="${userListings}">
                        <tr>
                            <td>${listing.title}</td>
                            <td>${listing.description}</td>
                            <td>${listing.item_condition}</td>
                            <td><img src="/uploads/${listing.photoUrl}" alt="Listing Photo" class="img-thumbnail"></td>
                            <td class="status ${listing.status == 'available' ? 'text-success' : 'text-primary'}">
                                ${listing.status}
                            </td>
                            <td>
                                <a href="editListing?listingId=${listing.id}" class="btn btn-primary btn-sm">Edit</a>
                               
                            </td>
                            <td>
                                <form action="deleteListing" method="post" style="display:inline;">
                                    <input type="hidden" name="listingId" value="${listing.id}">
                                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty userListings}">
            <div class="alert alert-warning" role="alert">
                You haven't created any listings yet.
            </div>
        </c:if>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
