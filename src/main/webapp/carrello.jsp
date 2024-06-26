<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.Product.ProductBean" %>
<%@ page import="main.javas.util.Carrello" %>
<%@ page import="main.javas.model.Order.CartBean" %>
<%@ page import="main.javas.model.Order.CartModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.javas.model.User.UserBean" %>

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
</head>
<body>
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
            <form action="ServletCarrello" method="post">
                <input type="hidden" name="action" value="updateQuantity">
                <input type="hidden" name="code" value="<%= prodotto.getProductCode() %>">
                <input type="number" name="quantity" value="<%= prodotto.getQuantity() %>" min="1" max="<%= model.ProductMaxQuantity(prodotto) %>">
                <input type="submit" value="Update">
            </form>
        </td>

        <td><%= prodotto.getFrame() %></td>
        <td><%= prodotto.getFrameColor() %></td>
        <td><%= prodotto.getSize() %></td>

        <%
            if(!model.checkDiscount(prodotto)) {
        %>
        <td> <%= model.getProductTotalPrice(prodotto) * prodotto.getQuantity() %> €</td>
        <%
        } else {
            if(model.getSingleProductDiscountedPrice(prodotto) != model.getProductTotalPrice(prodotto)){
        %>
        <td> <del><%= model.getProductTotalPrice(prodotto) * prodotto.getQuantity() %> €</del> <span style="color: red;"><%= model.getSingleProductDiscountedPrice(prodotto) %>  </span> € </td>
        <%
                }
            }
        %>

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
        <td colspan="2">Total Price</td>
        <% if(model.getTotalPriceWithDiscount(prodotti) == totalPrice) { %>
        <td><%=model.getTotalPriceWithDiscount(prodotti)%> €</td>
        <% } else { %>
        <td colspan="2"> <del><%=model.getTotalPriceWithDiscount(prodotti)%> € </del> <span style="color: red;"><%=totalPrice%></span> €</td>
        <% } %>
    </tr>
</table>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
<p style="color:red;"><%= errorMessage %></p>
<% } %>

<a href="ProductView.jsp">Torna alla home</a>

<%
    if(cart.getProdotti().isEmpty()) { %>
<p style="color:red;">Il carrello è vuoto</p>
<%  } else { %>

<form action="CheckoutServlet" method="get">
    <input type="hidden" name="nextPage" value="CheckoutShipping.jsp">
    <button type="submit">Vai al pagamento</button>
</form>
<%}%>
</body>
</html>