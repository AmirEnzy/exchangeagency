<!DOCTYPE html>
<html>
  <head>
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" />
    <style>
      .login-container {
        max-width: 400px;
        margin: auto;
        padding: 20px;
        margin-top: 100px;
        box-shadow: 0 0 10px rgba(0, 119, 255, 0.67);
        border-radius: 5px;
        background-color: white;
      }
      .register-link {
        text-align: center;
        margin-top: 15px;
      }
    </style>
  </head>
  <body>
    <jsp:include page="navbar.jsp" />
    <div class="container">
      <div class="login-container">
        <h2 class="text-center">Login</h2>
        <form action="login" method="post">
          <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="username" required />
          </div>
          <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required />
          </div>
          <button type="submit" class="btn btn-primary btn-block mt-3">Login</button>
        </form>
        <div class="register-link">
          <p>Don't have an account? <a href="register.jsp">Register here</a></p>
        </div>
      </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  </body>
</html>
