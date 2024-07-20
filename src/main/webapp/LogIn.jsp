<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.javas.bean.UserBean" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/SignIn.css">
    <title>Login</title>
</head>
<body>
<%
    UserBean user = (UserBean) session.getAttribute("user");
%>
<%@ include file="Header.jsp" %>
<h2>Login</h2>
<% if (user == null) { %>
<form id="loginForm" action="<%= request.getContextPath() %>/LogInServlet" method="post" class="registration-form">
    <input type="hidden" name="nextPage" value="ProductView.jsp">

    <section>
        <div class="form-wrapper">
            <input type="text" id="username" name="username" placeholder="Username" required>
            <label class="form-label" for="username">Username</label>
            <span id="usernameError" class="error"></span>
        </div>
        <div class="form-wrapper">
            <input type="password" id="password" name="password" placeholder="Password" required>
            <label class="form-label" for="password">Password</label>
            <span id="passwordError" class="error"></span>
        </div>
    </section>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <p class="error" style="color:red;"><%= errorMessage %></p>
    <% } %>
    <p>Non hai un account? <a href="SignIn.jsp">Registrati</a></p>

    <div class="form-wrapper">
        <input type="submit" value="Log in">
    </div>
</form>

<% }%>
<%@ include file="Footer.jsp" %>
</body>
</html>