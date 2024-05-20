<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>
<%@ page import="main.javas.util.Carrello" %>
<%@ page import="main.javas.model.CartBean" %>
<%@ page import="main.javas.model.OrderModel" %>
<%@ page import="java.sql.SQLException" %>

<%
    Carrello carrello = (Carrello) session.getAttribute("cart");
    if (carrello == null) {
        carrello = new Carrello();
    }
    List<CartBean> arrayArticoli = carrello.getProdotti();
    OrderModel model = new OrderModel();
    float totalPrice = 0;
    try {
        totalPrice = model.getTotalPrice();
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
</head>
<body>
    <h1>Carrello</h1>
    <table border="1">
        <tr>
            <th>Id Prodotto</th>
            <th>Quantità</th>
            <th>Prezzo</th>
        </tr>
        <%
            for (CartBean articolo : arrayArticoli) {
        %>
        <tr>
            <td><%=articolo.getCode()%></td>
            <td>
                <form action="UpdateQuantityServlet" method="post">
                    <input type="hidden" name="code" value="<%=articolo.getCode()%>">
                    <input type="number" name="quantity" value="<%=articolo.getQuantity()%>" min="0">
                    <input type="submit" value="Aggiorna">
                </form>
            </td>
            <td> <%=articolo.getPrice()%> x <%=articolo.getQuantity()%> </td>
        </tr>
        <%
            }
        %>
        <tr>
            <td colspan = "2">Prezzo totale:</td>
            <td><%=totalPrice%></td>
        </tr>
    </table>
    <a href="ProductView.jsp">Torna alla home</a>
    <a href="Checkout.jsp">Vai al checkout</a>
</body>
</html>