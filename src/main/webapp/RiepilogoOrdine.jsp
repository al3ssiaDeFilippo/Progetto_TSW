<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>
<%@ page import="main.javas.util.Carrello" %>
<%@ page import="main.javas.model.CartBean" %>
<%@ page import="main.javas.model.CartModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.javas.model.UserBean" %>
<%@ page import="main.javas.model.ShippingBean" %>
<%@ page import="main.javas.model.CreditCardBean" %>

<%
    Carrello carrello = (Carrello) session.getAttribute("cart");
    ShippingBean shippingAddress = (ShippingBean) session.getAttribute("shippingAddress");
    CreditCardBean cardInfo = (CreditCardBean) session.getAttribute("cardInfo");
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

<h2>Prodotti Acquistati</h2>
<table border="1">
    <tr>
        <th>Code</th>
        <th>Quantity</th>
        <th>Price</th>
    </tr>
    <% for (CartBean prodotto : arrayArticoli) { %>
    <tr>
        <td><%= prodotto.getProductCode() %></td>
        <td><%= prodotto.getQuantity() %></td>
        <td>
            <% if(!model.checkDiscount(prodotto)) { %>
            <%= prodotto.getPrice() * prodotto.getQuantity()%> €
            <% } else if(model.getSingleProductDiscountedPrice(prodotto) != prodotto.getPrice()) { %>
            <del><%= prodotto.getPrice() * prodotto.getQuantity() %> €</del> <span style="color: red;"><%= model.getSingleProductDiscountedPrice(prodotto) %>  </span> €
            <% } %>
        </td>
    </tr>
    <% } %>
</table>

<h2>Indirizzo di Spedizione</h2>
<% if (shippingAddress != null) { %>
<p><%= shippingAddress.getRecipientName() %></p>
<p><%= shippingAddress.getAddress() %></p>
<p><%= shippingAddress.getCity() %></p>
<p><%= shippingAddress.getCap() %></p>
<% } else { %>
<p>Indirizzo di spedizione non disponibile</p>
<% } %>

<h2>Informazioni sulla Carta</h2>
<%
    CreditCardBean selectedCard = (CreditCardBean) session.getAttribute("selectedCard");
    if (selectedCard != null) {
%>
<p>Numero Carta: <%= selectedCard.getIdCard() %></p>
<p>Nome Titolare: <%= selectedCard.getOwnerCard() %></p>
<p>Data di Scadenza: <%= selectedCard.getExpirationDate() %></p>
<%
} else {
%>
<p>Informazioni sulla carta non disponibili</p>
<%
    }
%>

<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>
<a href="ProductView.jsp">Continua lo shopping</a>

</body>
</html>