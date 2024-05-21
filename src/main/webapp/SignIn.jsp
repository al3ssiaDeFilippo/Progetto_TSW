<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Login</h2>
<form action="UserServlet" method="post">
    <input type="hidden" name="action" value="register">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div>
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
    </div>
    <div>
        <label for="surname">Surname:</label>
        <input type="text" id="surname" name="surname" required>
    </div>
    <div>
        <label for="birthdate">Birth Date:</label>
        <input type="date" id="birthdate" name="birthdate" required>
    </div>
    <div>
        <label for="address">Address:</label>
        <input type="text" id="address" name="address" required>
    </div>
    <div>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <label for="telNumber">telNumber:</label>
        <input type="tel" id="telNumber" name="telNumber" required>
    </div>
    <div>
        <input type="submit" value="Sign In">
    </div>
</form>
<p>Hai già un account? <a href="LogIn.jsp">Accedi</a></p>
<a href="ProductView.jsp">Torna al catalogo</a>

</body>
</html>