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
</head>
<body>
<%@ include file="Header.jsp" %>
<h2>Modifica Dati Profilo</h2>

<form action="<%= request.getContextPath() %>/UpdateProfileServlet" method="post">
    <label for="nome">Nome:</label>
    <input type="text" id="nome" name="nome" value="<%= user.getName() %>" required><br>

    <label for="cognome">Cognome:</label>
    <input type="text" id="cognome" name="cognome" value="<%= user.getSurname() %>" required><br>

    <label for="nickname">Nickname:</label>
    <input type="text" id="nickname" name="nickname" value="<%= user.getUsername() %>" required><br>

    <label for="dataNascita">Data di Nascita:</label>
    <input type="date" id="dataNascita" name="dataNascita" value="<%= user.getBirthDate() %>" required><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required><br>

    <label for="telefono">Numero di Telefono:</label>
    <input type="tel" id="telefono" name="telefono" value="<%= user.getTelNumber() %>" required><br>

    <input type="submit" value="Aggiorna Dati">
</form>

<a href="Profilo.jsp">Torna al Profilo</a>
<%@ include file="Footer.jsp" %>
</body>
</html>