<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="ProductView.css">
    <title>LogIn</title>
</head>
<body>
<h2>Login</h2>
<form action="UserServlet" method="post">
    <input type="hidden" name="action" value="login">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <input type="submit" value="Login">
    </div>
</form>
<p>Non hai un account? <a href="SignIn.jsp">Registrati</a></p>
<a href="ProductView.jsp">Torna al catalogo</a>
</body>
</html>