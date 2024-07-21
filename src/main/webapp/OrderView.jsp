<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.bean.OrderBean" %>
<%@ page import="main.javas.model.Order.OrderModel" %>
<%@ page import="main.javas.model.User.UserModel" %>
<%@ page import="java.sql.SQLException" %>

<%
    UserBean currentUser = (UserBean) session.getAttribute("user");
    if (currentUser == null || !currentUser.getAdmin()) {
        response.sendRedirect("LogIn.jsp");
        return; // Stop further processing of the page
    }
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/OrderView.css">
    <title>Ordini</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<h2>Ordini</h2>

<!-- Form per ricerca per nome utente -->
<form action="OrderView.jsp" method="get" class="form-inserimento">
    <label for="userName">Nome Utente:</label>
    <input type="text" id="userName" name="userName" placeholder="Cerca...">
    <input type="submit" value="Cerca per Utente">
</form>

<!-- Form per ricerca per data -->
<form action="OrderView.jsp" method="get" class="form-inserimento">
    <label for="startDate">Data Inizio:</label>
    <input type="date" id="startDate" name="startDate">
    <label for="endDate">Data Fine:</label>
    <input type="date" id="endDate" name="endDate">
    <input type="submit" value="Cerca per Data">
</form>

<%
    String userName = request.getParameter("userName");
    String startDate = request.getParameter("startDate");
    String endDate = request.getParameter("endDate");
    Collection<OrderBean> orders = null;

    // Ricerca per nome utente
    if (userName != null && !userName.isEmpty()) {
        try {
            UserModel userModel = new UserModel();
            UserBean userBean = userModel.doRetrieveByUsername(userName);
            if (userBean != null) {
                OrderModel orderModel = new OrderModel();
                orders = orderModel.doRetrieveByUser(userBean.getIdUser());
            } else {
                orders = new ArrayList<>();
                request.setAttribute("errorMessage", "Utente non trovato.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Ricerca per data
    else if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
        try {
            OrderModel orderModel = new OrderModel();
            orders = orderModel.doRetrieveByDateRange(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Nessun parametro specificato, recupera tutti gli ordini
    else {
        try {
            OrderModel orderModel = new OrderModel();
            orders = orderModel.doRetrieveAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null) {
%>
<p style="color: red;"><%= errorMessage %></p>
<% } %>

<% if (orders != null && !orders.isEmpty()) { %>
<table>
    <tr>
        <th>ID Ordine</th>
        <th>ID Utente</th>
        <th>ID Spedizione</th>
        <th>ID Carta di Credito</th>
        <th>Data Ordine</th>
        <th>Prezzo Totale</th>
    </tr>
    <% for (OrderBean order : orders) { %>
    <tr>
        <td><%= order.getIdOrder() %></td>
        <td><%= order.getIdUser() %></td>
        <td><%= order.getIdShipping() %></td>
        <td><%= order.getIdCreditCard() %></td>
        <td><%= order.getOrderDate() %></td>
        <td><%= order.getTotalPrice() %></td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>Non ci sono ordini.</p>
<% } %>

<%@ include file="Footer.jsp" %>
</body>
</html>
