<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.User.UserBean" %>
<%@ page import="main.javas.model.Order.OrderBean" %>
<%@ page import="main.javas.model.Order.OrderModel" %>

<%
    UserBean currentUser = (UserBean) session.getAttribute("user");
    if (currentUser == null || !currentUser.getAdmin()) {
        response.sendRedirect("LogIn.jsp");
        return; // Stop further processing of the page
    }
%>

<%
    OrderModel orderModel = new OrderModel();
    Collection<OrderBean> allOrders = orderModel.getOrdersByDate();

    List<OrderBean> orders = new ArrayList<>(allOrders);
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ordini</title>
</head>
<body>
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
                <th>      </th>
            </tr>
            <% for (OrderBean order : orders) { %>
            <tr>
                <td><%= order.getIdOrder() %></td>
                <td><%= order.getIdUser() %></td>
                <td><%= order.getIdShipping() %></td>
                <td><%= order.getIdCreditCard() %></td>
                <td><%= order.getOrderDate()%></td>
                <td><%= order.getTotalPrice()%></td>
                <td>
                    <form action="OrderControlServlet" method="post">
                        <input type="hidden" name="idOrder" value="<%= order.getIdOrder() %>">
                        <input type="hidden" name="action" value="delete">
                        <input type="submit" value="Elimina">
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>Non ci sono ordini.</p>
    <% } %>
</body>
</html>
