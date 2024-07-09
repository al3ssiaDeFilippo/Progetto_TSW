<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.util.Carrello" %>
<%@ page import="main.javas.bean.CartBean" %>
<%@ page import="main.javas.model.Order.CartModel" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>

<%
    Carrello cart = (Carrello) session.getAttribute("cart");
    CartModel model = new CartModel();
    ProductModelDS productModel = new ProductModelDS();
    float totalPriceWithDiscount = 0;

    // Se il carrello è nullo, crea un nuovo carrello
    if (cart == null) {
        cart = new Carrello();
        session.setAttribute("cart", cart);
    }

    // Recupera la lista dei prodotti nel carrello e calcola i totali
    List<CartBean> prodotti = cart.getProdotti();
    float totalPriceWithoutDiscount = cart.getCartTotalPriceWithoutDiscount(model);
    totalPriceWithDiscount = cart.getCartTotalPriceWithDiscount(model);
    float totalDiscount = cart.getTotalDiscount(model);

    System.out.println("Total discount: " + totalDiscount);
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

<div class="cart-container">
    <div class="products-list">
        <% for (CartBean prodotto : prodotti) {
            ProductBean product = productModel.doRetrieveByKey(prodotto.getProductCode());
            boolean hasDiscount = model.checkDiscount(prodotto);
        %>
        <div class="product-item-horizontal">
            <div class="product-image">
                <img src="GetProductImageServlet?action=get&code=<%= product.getCode() %>&frame=<%= prodotto.getFrame() %>&frameColor=<%= prodotto.getFrameColor() %>" alt="Immagine attuale del prodotto">
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

                <div class="product-price" data-discount="<%= hasDiscount %>" data-price="<%= product.getPrice() %>" data-discounted-price="<%= model.getSingleProductDiscountedPrice(prodotto) %>">
                    <% if (!hasDiscount) { %>
                    <!-- Prezzo totale di un prodotto senza sconto-->
                    Prezzo unitario: <span class="product-total-price"><%= model.getProductTotalPrice(prodotto) %></span> €
                    <% } else { %>
                    <!-- Prezzo totale di un prodotto con sconto-->
                    Prezzo unitario: <del><%= model.getProductTotalPrice(prodotto) %> €</del> <span class="product-total-price"><%= model.getSingleProductDiscountedPrice(prodotto) %></span> €
                    <% } %>
                </div>

                <form action="RemoveFromCartServlet" method="post">
                    <input type="hidden" name="code" value="<%= prodotto.getProductCode() %>">
                    <button type="submit" class="icon-button">
                        <img src="Images/binIcon.png" alt="Rimuovi">
                    </button>
                </form>

            </div>
        </div>
        <% } %>
    </div>

    <div class="cart-summary">
        <h2>Riepilogo</h2>
        <div class="summary-item">
            <span>Subtotale</span>
            <span id="totalPriceWithoutDiscount"><%= totalPriceWithoutDiscount %> €</span>
        </div>

        <div class="summary-discount">
            <span>Sconto</span>
            <span id="totalDiscount">- <%= totalDiscount %> €</span>
        </div>

        <div class="summary-item">
            <span>Costi di spedizione</span>
            <span>Gratis</span>
        </div>

        <div class="summary-total">
            <span>Totale</span>
            <span id="totalPrice"><%= totalPriceWithDiscount %> €</span>
        </div>

        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
        <p><%= errorMessage %></p>
        <% } %>

        <!--<form action="ServletCarrello" method="post">
            <input type="hidden" name="action" value="clear">
            <input type="submit" value="Svuota carrello" class="btn">
        </form>-->

        <% if (cart.getProdotti().isEmpty()) { %>
        <p>Il carrello è vuoto</p>
        <% } else { %>
        <form action="CheckoutServlet" method="get">
            <input type="hidden" name="nextPage" value="CheckoutShipping.jsp">
            <button type="submit" class="btn-checkout">Vai al pagamento</button>
        </form>
        <% } %>


        <div class="payment-accepted"><p>Modalità di pagamento disponibili</p></div>

        <div class="payment-modes">
            <img src="Images/masterCardIcon.png" alt="MasterCard">
            <img src="Images/visaIcon.png" alt="Visa">
            <img src="Images/postePayIcon.png" alt="PostePay">
        </div>
    </div>
</div>

<%@ include file="Footer.jsp" %>
</body>
</html>