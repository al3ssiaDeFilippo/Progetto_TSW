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
</head>
<body>
<%@ include file="Header.jsp" %>
<h2>Modifica Dati Profilo</h2>

<div class="form-container">
    <form action="<%= request.getContextPath() %>/UpdateProfileServlet" method="post">
        <div class="form-group">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" value="<%= user.getName() %>" required>
        </div>

        <div class="form-group">
            <label for="cognome">Cognome:</label>
            <input type="text" id="cognome" name="cognome" value="<%= user.getSurname() %>" required>
        </div>

        <div class="form-group">
            <label for="nickname">Nickname:</label>
            <input type="text" id="nickname" name="nickname" value="<%= user.getUsername() %>" required>
        </div>

        <div class="form-group">
            <label for="dataNascita">Data di Nascita:</label>
            <input type="date" id="dataNascita" name="dataNascita" value="<%= user.getBirthDate() %>" required>
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>
        </div>

        <div class="form-group">
            <label for="telefono">Numero di Telefono:</label>
            <input type="tel" id="telefono" name="telefono" value="<%= user.getTelNumber() %>" required>
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
