<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.javas.bean.UserBean" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/ProductView.css">
    <title>Login</title>
</head>
<body>
<%
    // Use the implicit session object
    UserBean user = (UserBean) session.getAttribute("user");
%>
<%@ include file="Header.jsp" %>
<h2>Login</h2>
<% if (user == null) { %>
<form action="LogInServlet" method="post">
    <input type="hidden" name="action" value="login">
    <!-- modifica -->
    <input type="hidden" name="nextPage" value="ProductView.jsp">
    <!-- fine modifica -->
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <!--<form action="LogInServlet" method="get">
            <input type="hidden" name="action" value="login">
            <input type="hidden" name="nextPage" value="ProductView.jsp">-->
            <input type="submit" value="Login">
        <!--</form>-->
    </div>
</form>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>
<p>Non hai un account? <a href="SignIn.jsp">Registrati</a></p>
<% }%>
<a href="ProductView.jsp">Torna al catalogo</a>
<%@ include file="Footer.jsp" %>
</body>
</html>
