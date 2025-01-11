<!DOCTYPE html>
<html>
  <head>
    <title>Register</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" />
    <style>
      .register-container {
        max-width: 400px;
        margin: auto;
        padding: 20px;
        margin-top: 100px;
        box-shadow: 0 0 10px rgb(1, 218, 251);
        border-radius: 5px;
        background-color: white;
      }
    </style>
  </head>
  <body>
    <jsp:include page="navbar.jsp" />
    <div class="container">
      <div class="register-container">
        <h2 class="text-center">Register</h2>
        <form action="register" method="post">
          <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="username" required />
          </div>
          <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required />
          </div>
          <div class="form-group">
            <label for="confirm_password">Confirm Password:</label>
            <input type="password" class="form-control" id="confirm_password" name="confirm_password" required />
          </div>
          <button type="submit" class="btn btn-primary btn-block mt-3">Register</button>
        </form>
      </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  </body>
</html>
