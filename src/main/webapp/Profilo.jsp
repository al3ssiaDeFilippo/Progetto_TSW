<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.javas.bean.UserBean" %>
<%
    // Controlla se l'utente è loggato
    UserBean user = (UserBean) session.getAttribute("user");

    if (user == null) {
        // Se l'utente non è loggato, reindirizza alla pagina di login
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Profilo Utente</title>
    <style>
        .profile-container {
            width: 50%;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .profile-container h1 {
            text-align: center;
        }
        .profile-container table {
            width: 100%;
            border-collapse: collapse;
        }
        .profile-container th, .profile-container td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        .profile-container th {
            background-color: #f2f2f2;
        }
        .profile-container .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .profile-container button {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
<%@ include file="Header.jsp" %>
<div class="profile-container">
    <h1>Profilo Utente</h1>
    <table>
        <tr>
            <th>Nome</th>
            <td><%= user.getName() %></td>
        </tr>
        <tr>
            <th>Cognome</th>
            <td><%= user.getSurname() %></td>
        </tr>
        <tr>
            <th>Nickname</th>
            <td><%= user.getUsername() %></td>
        </tr>
        <tr>
            <th>Data di Nascita</th>
            <td><%= user.getBirthDate() %></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><%= user.getEmail() %></td>
        </tr>
        <tr>
            <th>Password</th>
            <td><input type="password" value="<%= user.getPassword() %>" readonly></td>
        </tr>
        <tr>
            <th>Numero di Telefono</th>
            <td><%= user.getTelNumber() %></td>
        </tr>
    </table>
    <div class="button-container">
        <form action="ModificaDati.jsp" method="post">
            <button type="submit">Modifica Dati</button>
        </form>
        <a href="ModificaPassword.jsp">Modifica Password</a>
    </div>

        <style>
        .button-container form {
            display: inline-block;
            margin-right: 10px;
        }
    </style>

    <div class="button-container">
        <form action="CarteUtente.jsp" method="post">
            <button type="submit">Le mie carte</button>
        </form>
        <form action="OrderHistory.jsp" method="post">
            <button type="submit">I miei ordini</button>
        </form>
        <form action="IndirizziUtente.jsp" method="post">
            <button type="submit">Indirizzi</button>
        </form>
    </div>

    <a href="ProductView.jsp">Torna alla home</a>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>