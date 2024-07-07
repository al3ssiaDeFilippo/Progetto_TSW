<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.javas.bean.UserBean" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/LogInStyle.css">
    <title>Login</title>
</head>
<body>
<%
    UserBean user = (UserBean) session.getAttribute("user");
%>
<%@ include file="Header.jsp" %>
<h2>Login</h2>
<% if (user == null) { %>
<form action="LogInServlet" method="post">
    <input type="hidden" name="action" value="login">
    <input type="hidden" name="nextPage" value="ProductView.jsp">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <p>Non hai un account? <a href="SignIn.jsp">Registrati</a></p>
    <div>
        <input type="submit" value="Login">
    </div>
</form>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>
<% }%>
<%@ include file="Footer.jsp" %>
</body>
</html>