<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>
<%@ page import="main.javas.util.Carrello" %>
<%@ page import="main.javas.model.CartBean" %>
<%@ page import="main.javas.model.CartModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.javas.model.UserBean" %>

<%
    Carrello carrello = (Carrello) session.getAttribute("cart");
    if (carrello == null) {
        carrello = new Carrello();
    }
    List<CartBean> arrayArticoli = carrello.getProdotti();
    CartModel model = new CartModel();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Riepilogo Ordine</title>
</head>
<body>
<h1>Riepilogo Ordine</h1>
<table border="1">
    <tr>
        <th>Id Prodotto</th>
        <th>Quantità</th>
        <th>Prezzo Unitario</th>
        <th>Subtotale</th>
    </tr>
    <%
        for (CartBean articolo : arrayArticoli) {
    %>
    <tr>
        <td><%= articolo.getCode() %></td>
        <td><%= articolo.getQuantity() %></td>
        <%
            if(articolo.getPrice() == model.getDiscountedPrice(articolo.getCode())) {
        %>
        <td><%= articolo.getPrice() %> €</td>
        <%
            } else {
        %>
        <td><del><%= articolo.getPrice() %> €</del> <span style="color: red;"><%=model.getDiscountedPrice(articolo.getCode())%> €</span></td>
        <%
            }
        %>
        <td><%=model.getDiscountedPrice(articolo.getCode()) * articolo.getQuantity()%> €</td>
    </tr>
    <%
        }
    %>
    <tr>
        <td colspan="3" style="text-align:left;"><strong>Totale:</strong></td>
        <td><%= String.format("%.2f", carrello.calcolaPrezzoTotale(model)) %>€</td>
    </tr>
</table>

<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>
<a href="ProductView.jsp">Continua lo shopping</a>


</body>
</html>
