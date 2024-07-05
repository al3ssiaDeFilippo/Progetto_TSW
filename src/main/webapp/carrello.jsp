<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.util.Carrello" %>
<%@ page import="main.javas.bean.CartBean" %>
<%@ page import="main.javas.model.Order.CartModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.javas.bean.UserBean" %>

<%
    Carrello cart = (Carrello) session.getAttribute("cart");
    if (cart == null) {
        cart = new Carrello();
        session.setAttribute("cart", cart);
    }
    List<CartBean> prodotti = cart.getProdotti();
    CartModel model = new CartModel();
    float totalPrice = model.getDiscountedTotalPrice(prodotti);
%>

<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
    <script src="<c:url value='/js/cart.js'/>"></script>
</head>
<body>
<%@ include file="Header.jsp" %>
<h1>Carrello</h1>
<table border="1">
    <tr>
        <th>Code</th>
        <th>Quantity</th>
        <th>Frame</th>
        <th>Frame Color</th>
        <th>Size</th>
        <th>Price</th>
        <th>Action</th>
    </tr>
    <% for (CartBean prodotto : prodotti) { %>
    <tr>
        <td><%= prodotto.getProductCode() %></td>
        <td>
            <input type="number" name="quantity" value="<%= prodotto.getQuantity() %>" min="1" max="<%= model.ProductMaxQuantity(prodotto) %>" onchange="updateQuantity('<%= prodotto.getProductCode() %>', this.value)">
        </td>
        <td><%= prodotto.getFrame() %></td>
        <td><%= prodotto.getFrameColor() %></td>
        <td><%= prodotto.getSize() %></td>
        <% if(!model.checkDiscount(prodotto)) { %>
        <td> <%= model.getProductTotalPrice(prodotto) %> €</td>
        <% } else { %>
        <td> <del><%= model.getProductTotalPrice(prodotto) %> €</del> <span style="color: red;"><%= model.getSingleProductDiscountedPrice(prodotto) %> </span> €</td>
        <% } %>
        <td>
            <form action="ServletCarrello" method="post">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="code" value="<%= prodotto.getProductCode() %>">
                <input type="submit" value="Remove">
            </form>
        </td>
    </tr>
    <% } %>
    <tr>
        <td colspan="5">Total Price</td>
        <td id="totalPrice"><%= totalPrice %> €</td>
    </tr>
</table>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>

<a href="ProductView.jsp">Torna alla home</a>
<form action="ServletCarrello" method="post">
    <input type="hidden" name="action" value="clear">
    <input type="submit" value="Svuota carrello">
</form>

<%
    if(cart.getProdotti().isEmpty()) { %>
<p style="color:red;">Il carrello è vuoto</p>
<%  } else { %>
<form action="CheckoutServlet" method="get">
    <input type="hidden" name="nextPage" value="CheckoutShipping.jsp">
    <button type="submit">Vai al pagamento</button>
</form>
<%}%>
<%@ include file="Footer.jsp" %>
</body>
</html>
