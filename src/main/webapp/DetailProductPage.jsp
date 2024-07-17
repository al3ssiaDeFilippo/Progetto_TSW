<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.bean.CartBean" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="java.sql.SQLException" %>

<%
    ProductBean product = (ProductBean) request.getAttribute("product");
    UserBean user = (UserBean) session.getAttribute("user"); // Retrieve the user object from the session
    float discounted = 0; // Declare the discounted variable
    if (product != null) {
        // Assuming ProductModelDS has a method to calculate discounted price
        ProductModelDS PMDS = new ProductModelDS();
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
    <script src="js/DetailSelection.js"></script>
    <script src="js/ModalImage.js"></script>
    <title>Dettagli Prodotto</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<%
    if (product != null) {
%>
<div class="product-header">
    <h2><%= product.getProductName() %></h2>
</div>
<div class="product-content">
    <div class="product-image" id="product-image">
        <img id="productImage" data-product-code="<%= product.getCode() %>" src="GetProductImageServlet?action=get&code=<%= product.getCode() %>&custom=true" alt="image not found">
    </div>

    <div class="product-info">
        <% if(product.getDiscount() > 0) { %>
        <p><strong>Prezzo: </strong> <strong><span class="discount-percentage">-<%=product.getDiscount() %>% &nbsp;</span></strong> <span class="discounted-price"><%= discounted %> €</span></p>
        <p><span class="original-price"><%= product.getPrice() %> €</span></p>
        <% } else { %>
        <p><strong>Prezzo:</strong> <%= product.getPrice() %> €</p>
        <% } %>
        <form action="<%= request.getContextPath() %>/AddToCartServlet" method="post" onsubmit="return validateForm()">
            <input type="hidden" name="code" value="<%= product != null ? product.getCode() : "" %>">
            <p><strong>Dimensioni:</strong>
                <select name="size" id="sizeSelect">
                    <option value="selectAsize" disabled selected>Seleziona la dimensione</option>
                    <option value="21x30">21x30</option>
                    <option value="85x60">85x60</option>
                    <option value="91x61">91x61</option>
                </select>
            </p>
            <p id="frameSelectContainer"><strong>Materiale Cornice:</strong>
                <select name="frame" id="frameSelect" >
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
            <p id="errorMessage" style="color:red; display:none;"></p>
            <% if(product.getQuantity() > 0) { %>
            <% if (user == null || !user.getAdmin()) { %>
            <input type="submit" value="Aggiungi al Carrello">
            <% } %>
            <% } else { %>
            <p class="unavailable-product">Prodotto non disponibile al momento</p>
            <% } %> <!-- Chiudi il blocco if-else per il controllo della quantità del prodotto -->
        </form>
    </div>
    <div class="product-details">
        <p><strong>Dettagli: <br></strong> <%= product.getDetails() %></p>
    </div>
</div>
<% } else { %>
<p>Nessun prodotto selezionato</p>
<% } %>

<div id="myModal" class="modal">
    <span class="close">&times;</span>
    <div class="modal-content">
        <img id="img01" class="modal-image">
    </div>
    <div id="caption"></div>
</div>

<%@ include file="Footer.jsp" %>
</body>
</html>
