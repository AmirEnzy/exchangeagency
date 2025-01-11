<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Review Interests</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-5">
        <h1>Review Interests</h1>
        <c:if test="${not empty interestListings}">
            <table class="table table-striped table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th>Listing Title</th>
                        <th>Description</th>
                        <th>Condition</th>
                        <th>Photo</th>
                        <th>Interested Buyer</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="interestListing" items="${interestListings}">
                        <tr>
                            <td>${interestListing.listing.title}</td>
                            <td>${interestListing.listing.description}</td>
                            <td>${interestListing.listing.item_condition}</td>
                            <td><img src="/uploads/${interestListing.listing.photoUrl}" alt="Listing Photo" width="100" class="img-thumbnail"></td>
                            <td>${interestListing.buyerName}</td>
                            <td>
                                <form action="confirmExchanges" method="post">
                                    <input type="hidden" name="listingId" value="${interestListing.listing.id}">
                                    <input type="hidden" name="interestId" value="${interestListing.interest.id}">
                                    <input type="hidden" name="buyerId" value="${interestListing.interest.userId}">
                                    <button type="submit" class="btn btn-primary">Confirm Exchange</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${empty interestListings}">
            <p>No interests expressed yet.</p>
        </c:if>
    </div>
</body>
</html>
