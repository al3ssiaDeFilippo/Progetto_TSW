<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.javas.bean.UserBean" %>

<%
    UserBean user = (UserBean) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("LogIn.jsp");
        return; // Stop further processing of the page
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Modifica Dati Profilo</title>
    <link rel="stylesheet" type="text/css" href="css/ModificaDati.css">
    <script src="js/SignInFormValidation.js"></script>
</head>
<body>
<%@ include file="Header.jsp" %>
<h2>Modifica Dati Profilo</h2>

<div class="form-container">
    <form id="registrationForm" action="<%= request.getContextPath() %>/UpdateProfileServlet" method="post">
        <div class="form-group">
            <label for="name">Nome:</label>
            <input type="text" id="name" name="name" value="<%= user.getName() %>" required>
            <span id="nameError" class="error"></span>
        </div>

        <div class="form-group">
            <label for="surname">Cognome:</label>
            <input type="text" id="surname" name="surname" value="<%= user.getSurname() %>" required>
            <span id="surnameError" class="error"></span>
        </div>

        <div class="form-group">
            <label for="username">Nickname:</label>
            <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>
            <span id="usernameError" class="error"></span>
        </div>

        <div class="form-group">
            <label for="birthdate">Data di Nascita:</label>
            <input type="date" id="birthdate" name="birthdate" value="<%= user.getBirthDate() %>" required>
            <span id="BirthDateError" class="error"></span>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>
            <span id="emailError" class="error"></span>
        </div>

        <div class="form-group">
            <label for="telNumber">Numero di Telefono:</label>
            <input type="tel" id="telNumber" name="telNumber" value="<%= user.getTelNumber() %>" required>
            <span id="telNumberError" class="error"></span>
        </div>

        <div class="btn-submit">
            <input type="submit" value="Aggiorna Dati">
        </div>
    </form>
</div>

<div class="back-link">
    <a href="Profilo.jsp">Torna al Profilo</a>
</div>

<%@ include file="Footer.jsp" %>
</body>
</html>
