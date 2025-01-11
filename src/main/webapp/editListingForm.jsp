<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Edit Listing</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container mt-5">
        <h1>Edit Listing</h1>
        <c:if test="${not empty listing}">
            <form action="updateListing" method="post" enctype="multipart/form-data">
                <input type="hidden" name="listingId" value="${listing.id}">
                <div class="mb-3">
                    <label for="title" class="form-label">Title</label>
                    <input type="text" id="title" name="title" class="form-control" value="${listing.title}" required>
                </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea id="description" name="description" class="form-control" rows="4" required>${listing.description}</textarea>
                </div>
                <div class="mb-3">
                    <label for="item_condition" class="form-label">Condition</label>
                    <input type="text" id="item_condition" name="item_condition" class="form-control" value="${listing.item_condition}" required>
                </div>
                <div class="mb-3">
                    <label for="status" class="form-label">Status</label>
                    <select id="status" name="status" class="form-select" required>
                        <option value="available" ${listing.status == 'available' ? 'selected' : ''}>Available</option>
                        <option value="sold" ${listing.status == 'sold' ? 'selected' : ''}>Sold</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="photoUrl" class="form-label">Change Photo</label>
                    <input type="file" id="photoUrl" name="photoUrl" class="form-control">
                    <c:if test="${not empty listing.photoUrl}">
                        <div class="mt-2">
                            <img src="/uploads/${listing.photoUrl}" alt="Current Photo" width="100" class="img-thumbnail">
                        </div>
                    </c:if>
                </div>
                <button type="submit" class="btn btn-primary">Update Listing</button>
            </form>
        </c:if>
        <c:if test="${empty listing}">
            <div class="alert alert-warning" role="alert">
                Listing not found or has been removed.
            </div>
        </c:if>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
