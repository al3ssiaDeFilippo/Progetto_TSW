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
    <title>Carrello</title>
</head>
<body>
<h1>Carrello</h1>
<table border="1">
    <tr>
        <th>Id Prodotto</th>
        <th>Quantità</th>
        <th>Prezzo</th>
        <th>Azioni</th>
    </tr>
    <%
        for (CartBean articolo : arrayArticoli) {
    %>
    <tr>
        <td><%= articolo.getCode() %></td>
        <td>
            <form action="ServletCarrello" method="post">
                <input type="hidden" name="action" value="updateQuantity">
                <input type="hidden" name="code" value="<%= articolo.getCode() %>">
                <%
                    int maxQuantity = 0;
                    try {
                        maxQuantity = model.getProductQuantity(articolo.getCode());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                %>
                <input type="number" name="quantity" value="<%= articolo.getQuantity() %>" min="0" max="<%= maxQuantity %>">
                <input type="submit" value="Aggiorna">
            </form>
        </td>
        <%
            if(model.getDiscountedPrice(articolo.getCode()) == articolo.getPrice()) {
        %>
        <td> <%= articolo.getPrice() %> € x <%= articolo.getQuantity() %> </td>
        <%
        } else {
            if(model.getDiscountedPrice(articolo.getCode()) != articolo.getPrice()) {
        %>
        <td> <del><%= articolo.getPrice() %></del> <span style="color: red;"><%= model.getDiscountedPrice(articolo.getCode()) %>  </span> € x <%= articolo.getQuantity() %> </td>
        <%
                }
            }
        %>
        <td>
            <form action="ServletCarrello" method="post">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="code" value="<%= articolo.getCode() %>">
                <input type="submit" value="Elimina">
            </form>
        </td>
    </tr>
    <%
        }
    %>
    <tr class="total-row">
        <td colspan="2">Prezzo totale:</td>
        <td><%= String.format("%.2f", carrello.calcolaPrezzoTotale(model)) %>€</td>
        <td></td>
    </tr>

</table>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>

<a href="ProductView.jsp">Torna alla home</a>

<form action="CheckoutServlet" method="get">
    <input type="hidden" name="nextPage" value="Checkout.jsp">
    <button type="submit">Vai al pagamento</button>
</form>

</body>
</html>
