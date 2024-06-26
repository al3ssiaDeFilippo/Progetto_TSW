<%--
  Created by IntelliJ IDEA.
  User: Utente
  Date: 21/06/2024
  Time: 12:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.User.UserBean" %>
<%@ page import="main.javas.model.User.UserModel" %>


<%
    UserBean currentUser = (UserBean) session.getAttribute("user");
    if (currentUser == null || !currentUser.getAdmin()) {
        response.sendRedirect("LogIn.jsp");
        return; // Stop further processing of the page
    }
%>

<%
    UserModel userModel = new UserModel();
    Collection<UserBean> allUsers = userModel.doRetrieveAll();

    List<UserBean> admins = new ArrayList<>();
    List<UserBean> users = new ArrayList<>();

    for (UserBean user : allUsers) {
        if (user.getAdmin()) {
            admins.add(user);
        } else {
            users.add(user);
        }
    }
%>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Utenti</title>
</head>
<body>

<h2>Utenti</h2>
<!-- Controlla se ci sono utenti nel database -->
<% if (!users.isEmpty()) { %>
    <table>
        <tr>
            <th>Nickname</th>
            <th>Nome</th>
            <th>Cognome</th>
            <th>Email</th>
            <th>Numero di Telefono</th>
            <th>Data di Nascita</th>
            <th>   </th>
        </tr>
        <% for (UserBean user : users) { %>
        <tr>
            <td><%= user.getUsername() %></td>
            <td><%= user.getName() %></td>
            <td><%= user.getSurname() %></td>
            <td><%= user.getEmail() %></td>
            <td><%= user.getTelNumber() %></td>
            <td><%= user.getBirthDate()%></td>
            <td>
                <form action="UserControlServlet" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%=user.getIdUser()%>">
                    <input type="submit" value="Elimina">
                </form>
                <form action="UserControlServlet" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="id" value="<%=user.getIdUser()%>">
                    <input type="submit" value="Admin">
                </form>

            </td>
        </tr>
        <% } %>
    </table>
<% } %>

<h2>Admin</h2>
<!-- Controlla se ci sono admin nel database -->
<% if (!admins.isEmpty()) { %>
    <table>
        <tr>
            <th>Nickname</th>
            <th>Nome</th>
            <th>Cognome</th>
            <th>Email</th>
            <th>Numero di Telefono</th>
            <th>Data di Nascita</th>
            <th></th>
        </tr>
        <% for (UserBean admin : admins) { %>
        <tr>
            <td><%= admin.getUsername() %></td>
            <td><%= admin.getName() %></td>
            <td><%= admin.getSurname() %></td>
            <td><%= admin.getEmail() %></td>
            <td><%= admin.getTelNumber() %></td>
            <td><%= admin.getBirthDate()%></td>
            <td>
                <form action="UserControlServlet" method="post">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%=admin.getIdUser()%>">
                    <input type="submit" value="Elimina">
                </form>
            </td>
        </tr>
        <% } %>
    </table>
<% } %>

</body>
</html>
