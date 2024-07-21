<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.util.GeneralUtils" %>
<%
    // Controlla se l'utente è loggato
    UserBean user = (UserBean) session.getAttribute("user");

    if (user == null) {
        // Se l'utente non è loggato, reindirizza alla pagina di login
        response.sendRedirect("LogIn.jsp");
        return;
    }

    GeneralUtils utils = new GeneralUtils();
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profilo Utente</title>
    <link rel="stylesheet" href="css/Profile.css">
</head>
<body>
<%@ include file="Header.jsp" %>
<div class="profile-wrapper">
    <div class="profile-container">
        <h2>Profilo Utente</h2>

        <div class="profile-row">
            <div class="profile-cell">
                <p class="profile-label">Nome</p>
                <p class="profile-value"><%= user.getName() %></p>
            </div>
            <div class="profile-cell">
                <p class="profile-label">Cognome</p>
                <p class="profile-value"><%= user.getSurname() %></p>
            </div>
            <div class="profile-cell">
                <p class="profile-label">Username</p>
                <p class="profile-value"><%= user.getUsername() %></p>
            </div>
            <div class="profile-cell">
                <p class="profile-label">Data di Nascita</p>
                <p class="profile-value"><%= user.getBirthDate() %></p>
            </div>
            <div class="profile-cell">
                <p class="profile-label">Email</p>
                <p class="profile-value"><%= user.getEmail() %></p>
            </div>
            <div class="profile-cell">
                <p class="profile-label">Password</p>
                <p class="profile-value"><%= utils.zippedPassword(user.getPassword())%></p>
            </div>
            <div class="profile-cell">
                <p class="profile-label">Numero di Telefono</p>
                <p class="profile-value"><%= user.getTelNumber() %></p>
            </div>
        </div>
        <div class="click-containers-secondary">
            <form action="ModificaDati.jsp" method="post">
                <button class="change-btn" type="submit">Modifica Dati</button>
            </form>
        </div>
    </div>
</div>

<%@ include file="Footer.jsp" %>
</body>
</html>
