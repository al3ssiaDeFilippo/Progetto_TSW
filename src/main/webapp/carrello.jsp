<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.util.Carrello" %>
<%@ page import="main.javas.bean.CartBean" %>
<%@ page import="main.javas.model.Order.CartModel" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>

<%
    // Check if the user is logged in
    Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
    Carrello cart;
    CartModel model = new CartModel();
    ProductModelDS productModel = new ProductModelDS();
    float totalPriceWithDiscount = 0;

    if (isLoggedIn != null && isLoggedIn) {
        cart = (Carrello) session.getAttribute("loggedInCart");
        if (cart == null) {
            cart = new Carrello();
            session.setAttribute("loggedInCart", cart);
        }
    } else {
        cart = (Carrello) session.getAttribute("cart");
        if (cart == null) {
            cart = new Carrello();
            session.setAttribute("cart", cart);
        }
    }

    List<CartBean> prodotti = cart.getProdotti();
    float totalPriceWithoutDiscount = cart.getCartTotalPriceWithoutDiscount(model);
    totalPriceWithDiscount = cart.getCartTotalPriceWithDiscount(model);

%>

<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
    <script src="js/UpdateQuantity.js" defer></script>
    <link rel="stylesheet" type="text/css" href="css/Cart.css">
</head>
<body>
<%@ include file="Header.jsp" %>
<h1>Carrello</h1>

<div class="product-container-horizontal">
    <% for (CartBean prodotto : prodotti) {
        ProductBean product = productModel.doRetrieveByKey(prodotto.getProductCode());
        boolean hasDiscount = model.checkDiscount(prodotto);
    %>
    <div class="product-item-horizontal">

        <div class="product-image">
            <img src="ProductImageServlet?action=get&code=<%= product.getCode() %>&frame=<%= prodotto.getFrame() %>&frameColor=<%= prodotto.getFrameColor() %>" alt="Immagine attuale del prodotto">
        </div>

        <div class="product-details">
            <div class="product-name"><%= product.getProductName() %></div>

            <div class="product-quantity">
                <p>Quantità:</p>
                <input type="number" name="quantity" value="<%= prodotto.getQuantity() %>" min="1" max="<%= model.ProductMaxQuantity(prodotto) %>" onchange="updateQuantity('<%= prodotto.getProductCode() %>', this.value)">
            </div>

            <div class="product-frame">Cornice: <%= prodotto.getFrame() %></div>
            <div class="product-frame-color">Colore del frame: <%= prodotto.getFrameColor() %></div>
            <div class="product-size">Dimensioni: <%= prodotto.getSize() %></div>

            <div class="product-price">
                <% if (!hasDiscount) { %>
                <!-- Prezzo totale di un prodotto senza sconto-->
                Prezzo: <%= model.getProductTotalPrice(prodotto) %> €
                <% } else { %>
                <!-- Prezzo totale di un prodotto con sconto-->
                Prezzo: <del><%= model.getProductTotalPrice(prodotto) %> €</del> <span><%= model.getSingleProductDiscountedPrice(prodotto) %> €</span>
                <% } %>
            </div>

            <form action="ServletCarrello" method="post">
                <input type="hidden" name="action" value="remove">
                <input type="hidden" name="code" value="<%= prodotto.getProductCode() %>">
                <input type="submit" value="Remove">
            </form>
        </div>
    </div>
    <% } %>
</div>

<div class="cart-summary">
    <div class="total-price" id="totalPrice">
        <% if (totalPriceWithoutDiscount == totalPriceWithDiscount) { %>
        Totale: <%= totalPriceWithDiscount %> €
        <% } else { %>
        Totale: <span class="total-price-without-discount" id="totalPriceWithoutDiscount"><%= totalPriceWithoutDiscount %> €</span>
        <span class="total-price"><%= totalPriceWithDiscount %> €</span>
        <% } %>
    </div>

    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <p><%= errorMessage %></p>
    <% } %>

    <a href="ProductView.jsp">Torna alla home</a>
    <form action="ServletCarrello" method="post">
        <input type="hidden" name="action" value="clear">
        <input type="submit" value="Svuota carrello">
    </form>

    <% if (cart.getProdotti().isEmpty()) { %>
    <p>Il carrello è vuoto</p>
    <% } else { %>
    <form action="CheckoutServlet" method="get">
        <input type="hidden" name="nextPage" value="CheckoutShipping.jsp">
        <button type="submit">Vai al pagamento</button>
    </form>
    <% } %>
</div>

<%@ include file="Footer.jsp" %>
</body>
</html>
