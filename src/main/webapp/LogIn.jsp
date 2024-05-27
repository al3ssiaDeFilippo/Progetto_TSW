<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.javas.model.UserBean" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="ProductView.css">
    <title>Login</title>
</head>
<body>
<%
    // Use the implicit session object
    UserBean user = (UserBean) session.getAttribute("user");
%>

<h2>Login</h2>
<% if (user == null) { %>
<form action="LogInServlet" method="post">
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
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>
<p>Non hai un account? <a href="SignIn.jsp">Registrati</a></p>
<% } else { %>
<p>Benvenuto, <%= user.getUsername() %>!</p>
<form action="LogInServlet" method="post">
    <input type="hidden" name="action" value="logout">
    <input type="submit" value="Logout">
</form>
<% } %>
<a href="ProductView.jsp">Torna al catalogo</a>
</body>
</html>