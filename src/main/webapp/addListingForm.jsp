<!DOCTYPE html>
<html>
  <head>
    <title>Add Listing</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
      body {
        background-color: #e9ecef; /* Light gray background */
      }
      .container {
        max-width: 600px;
        margin-top: 50px;
        background-color: #ffffff; /* White background for the form container */
        padding: 30px;
        border-radius: 8px; /* Rounded corners for the container */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
      }
      h1 {
        color: #343a40; /* Dark text color for heading */
        text-align: center;
        margin-bottom: 30px;
      }
      .btn-primary {
        background-color: #6f42c1; /* Primary button color */
        border-color: #6f42c1;
      }
      .btn-primary:hover {
        background-color: #5a2d91; /* Darker shade on hover */
        border-color: #5a2d91;
      }
      .form-group label {
        font-weight: bold;
        color: #495057; /* Dark text color for labels */
      }
      .form-control {
        border-radius: 0.25rem; /* Rounded corners for input fields */
        border-color: #ced4da; /* Border color for input fields */
      }
      .form-control-file {
        border-radius: 0.25rem; /* Rounded corners for file input */
      }
      .form-group.mb-3 {
        margin-bottom: 1rem;
      }
      .form-group.mb-4 {
        margin-bottom: 1.5rem;
      }
      .form-control:focus {
        box-shadow: 0 0 0 0.2rem rgba(38, 143, 255, 0.25); /* Blue shadow on focus */
        border-color: #80bdff;
      }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  </head>
  <body>
    <jsp:include page="navbar.jsp" />
    <div class="container">
      <h1>Add Listing</h1>
      <form action="addListing" method="post" enctype="multipart/form-data">
        <div class="form-group mb-3">
          <label for="title">Name:</label>
          <input type="text" class="form-control" id="title" name="title" required />
        </div>
        <div class="form-group mb-3">
          <label for="brand">Brand:</label>
          <textarea class="form-control" id="brand" name="brand" required></textarea>
        </div>
        <div class="form-group mb-3">
          <label for="description">About:</label>
          <textarea class="form-control" id="description" name="description" required></textarea>
        </div>
        <div class="form-group mb-3">
          <label for="item_condition">Condition:</label>
          <input type="text" class="form-control" id="item_condition" name="item_condition" required />
        </div>
        <div class="form-group mb-4">
          <label for="photo">Photo:</label>
          <input type="file" class="form-control-file" id="photo" name="photo" />
        </div>
        <div class="form-group mb-3">
          <label for="predicted_category">Predicted Category (ML):</label>
          <input type="text" class="form-control" id="predicted_category" name="predicted_category" readonly />
          <input type="hidden" id="category" name="category" />
        </div>
        <button type="submit" class="btn btn-primary w-100">Add Listing</button>
      </form>
    </div>
    <script>
      $(document).ready(function () {
        $("#title, #description").on("input", function () {
          var title = $("#title").val().trim();
          var description = $("#description").val().trim();

          if (title && description) {
            var category = predictCategory(title);
            updateCategory(category);
          } else {
            // Clear the prediction if either field is empty
            $("#predicted_category").val("");
            $("#category").val("");
          }
        });

        function predictCategory(title) {
          var lowercaseTitle = title.toLowerCase();
          var clothingKeywords = ["shoes", "jacket", "cap", "hat", "jeans", "apparel", "wear", "clothing"];
          var electronicsKeywords = [
            "phone",
            "tablet",
            "mouse",
            "monitor",
            "computer",
            "electronic",
            "device",
            "gadget",
          ];

          for (var i = 0; i < clothingKeywords.length; i++) {
            if (lowercaseTitle.includes(clothingKeywords[i])) {
              return "Clothing";
            }
          }

          for (var i = 0; i < electronicsKeywords.length; i++) {
            if (lowercaseTitle.includes(electronicsKeywords[i])) {
              return "Electronics";
            }
          }

          return "Unknown";
        }

        function updateCategory(category) {
          $("#predicted_category").val(category);
          var categoryId = category === "Clothing" ? 1 : category === "Electronics" ? 2 : "";
          $("#category").val(categoryId);
        }
      });
    </script>
  </body>
</html>
