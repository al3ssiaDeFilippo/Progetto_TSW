<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.javas.model.Product.FavoritesModel" %>

<%
    ProductBean product = (ProductBean) request.getAttribute("product");
    UserBean user = (UserBean) session.getAttribute("user"); // Assumi che l'oggetto utente sia salvato in sessione come "user"
    float discounted = 0;
    ProductModelDS PMDS = new ProductModelDS();

    if (product != null) {
        // Assuming ProductModelDS has a method to calculate discounted price
        try {
            discounted = PMDS.calculateDiscountedPrice(product.getCode()); // Calculate the discounted price
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/DetailProductPage.css" rel="stylesheet" type="text/css">
    <script src="js/DetailSelection.js" defer></script>
    <script src="js/ModalImage.js" defer></script>
    <title>Dettagli Prodotto</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<%
    FavoritesModel favoritesModel = new FavoritesModel();
    boolean isFavorite = false;
    try {
        if(product != null) {
            isFavorite = user != null && favoritesModel.isFavorite(product, user);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    if (product != null) {
%>
<div class="product-content">
    <div class="product-image">
        <img id="productImage" data-product-code="<%= product.getCode() %>" src="<%= request.getContextPath()%>/GetProductImageServlet?action=get&code=<%= product.getCode() %>&custom=true" alt="image not found">
    </div>

    <div class="product-info">
        <h2><%= product.getProductName() %></h2>
        <div class="price-container">
            <% if(product.getDiscount() > 0) { %>
            <div class="discount-badge">Sconto del <%= product.getDiscount() %>%</div>
            <p><strong>Prezzo:</strong> <span class="original-price"><%= product.getPrice() %> €</span></p>
            <p><span class="discounted-price"><%= discounted %> €</span></p>
            <% } else { %>
            <p><strong>Prezzo:</strong> <%= product.getPrice() %> €</p>
            <% } %>
        </div>
        <!-- Aggiungi al Carrello e Preferiti -->
        <% if (product.getQuantity() > 0) { %>
        <form action="<%= request.getContextPath() %>/AddToCartServlet" method="post" onsubmit="return validateForm()">
            <input type="hidden" name="code" value="<%= product.getCode() %>">
            <p><strong>Dimensioni:</strong>
                <select name="size" id="sizeSelect">
                    <option value="selectAsize" disabled selected>Seleziona la dimensione</option>
                    <option value="21x30">21x30</option>
                    <option value="85x60">85x60</option>
                    <option value="91x61">91x61</option>
                </select>
            </p>
            <p id="frameSelectContainer"><strong>Materiale Cornice:</strong>
                <select name="frame" id="frameSelect">
                    <option value="no frame" selected>No Frame</option>
                    <option value="wood">Wood</option>
                    <option value="PVC">PVC</option>
                </select>
            </p>
            <p id="frameColorSelectContainer"><strong>Colore Cornice:</strong>
                <select name="frameColor" id="frameColorSelect">
                    <option value="no color" style="display:none;">No Color</option>
                    <option value="selectAframeColor" disabled selected>Seleziona un colore per il frame</option>
                    <option value="black">Black</option>
                    <option value="brown">Brown</option>
                    <option value="white">White</option>
                </select>
            </p>
            <% if (user == null || !user.getAdmin()) { %>
            <!-- Form for adding to cart, hidden for admins -->
            <p id="errorMessage" style="color:red; display:none;"></p>
            <input type="submit" value="Aggiungi al Carrello" class="add-to-cart-button">
            <% } else { %>
            <p class="admin-message">Gli amministratori non possono aggiungere prodotti al carrello</p>
            <% } %>
        </form>
        <% } else if(product.getQuantity() <= 0) { %>
        <p class="unavailable-product">Prodotto non disponibile al momento</p>
        <% } %>

        <% if (user != null && user.getAdmin()) { %> <!-- Show admin controls -->
        <form action="<%= request.getContextPath() %>/DeleteProductServlet" method="post">
            <input type="hidden" name="code" value="<%= product.getCode() %>">
            <input type="submit" class="delete-button" value="Elimina">
        </form>
        <form action="<%= request.getContextPath() %>/UpdateProductServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="code" value="<%=product.getCode()%>">
            <input type="hidden" name="action" value="edit">
            <input type="submit" class="modify-button" value="Modifica">
        </form>
        <% } %>
    </div>
</div>
<div class="product-details">
    <h2>Dettagli del Prodotto</h2>
    <p><%= product.getDetails() %></p>
</div>
<%
} else {
%>
<p>Prodotto non trovato.</p>
<%
    }
%>

<div id="myModal" class="modal">
    <span class="close">&times;</span>
    <div class="modal-content">
        <img id="img01" class="modal-image">
    </div>
    <div id="caption"></div>
</div>

<!-- Sezione Best Seller -->
<div class="best-seller-section">
    <div class="best-seller-title">Prodotti correlati: </div>
    <div class="carousel-container">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <%
                    List<ProductBean> bestSellerProducts = null;
                    try {
                        bestSellerProducts = (List<ProductBean>) PMDS.getRandomProducts();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    for (ProductBean products : bestSellerProducts) {
                %>
                <div class="swiper-slide">
                    <a href="<%= request.getContextPath() %>/RetrieveProductServlet?action=read&code=<%=products.getCode()%>">
                        <img src="<%= request.getContextPath() %>/GetProductImageServlet?action=get&code=<%=products.getCode() %>" alt="<%= product.getProductName() %>">
                    </a>
                </div>
                <%
                    }
                %>
            </div>
            <!-- Pagination -->
            <div class="swiper-pagination"></div>
            <!-- Navigation -->
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>
        </div>
    </div>
</div>

<%@ include file="Footer.jsp" %>
</body>
</html>
