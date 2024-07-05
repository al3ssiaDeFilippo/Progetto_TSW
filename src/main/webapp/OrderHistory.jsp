<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.bean.OrderBean" %>
<%@ page import="main.javas.model.Order.OrderModel" %>
<%@ page import="java.sql.SQLException" %>

<%
    UserBean currentUser = (UserBean) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("LogIn.jsp");
        return; // Stop further processing of the page
    }
%>

<%
    OrderModel orderModel = new OrderModel();
    Collection<OrderBean> allOrders = null;
    try {
        allOrders = orderModel.doRetrieveByUser(currentUser.getIdUser());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    List<OrderBean> orders = new ArrayList<>(allOrders);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ordini</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<h2>Ordini</h2>
<!-- Controlla se ci sono ordini nel database -->
<% if (!orders.isEmpty()) { %>
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
        <td><%= order.getOrderDate()%></td>
        <td><%= order.getTotalPrice()%></td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>Non ci sono ordini.</p>
<% } %>
<%@ include file="Footer.jsp" %>
</body>
</html>
