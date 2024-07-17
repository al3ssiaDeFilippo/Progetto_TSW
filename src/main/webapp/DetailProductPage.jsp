<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.bean.CartBean" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="java.sql.SQLException" %>

<%
    ProductBean product = (ProductBean) request.getAttribute("product");
    UserBean user = (UserBean) session.getAttribute("user"); // Assumi che l'oggetto utente sia salvato in sessione come "user"
    ProductModelDS PMDS = new ProductModelDS();
    float discounted = 0;
    try {
        discounted = PMDS.calculateDiscountedPrice(product.getCode());
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/DetailProductPage.css" rel="stylesheet" type="text/css">
    <script src="js/DetailSelection.js"></script>
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
    <div class="product-image">
        <img id="productImage" data-product-code="<%= product.getCode() %>" src="GetProductImageServlet?action=get&code=<%= product.getCode() %>&custom=true" alt="image not found">
    </div>

    <div class="product-info">
        <% if(product.getDiscount() > 0) { %>
        <p><strong>Prezzo: </strong> <strong> -<span class="discount-percentage"><%=product.getDiscount() %>% &nbsp;</strong> <span class="discounted-price"><%= discounted%> €</span></p>
        <p><span class="original-price"><%= product.getPrice()%> €</span></p>
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
            <input type="submit" value="Aggiungi al Carrello">
            <% if(product.getQuantity() > 0) { %>
            <% if (user == null || !user.getAdmin()) { %>
            <% } %>
        </form>
        <% } else { %>
        <p class="unavailable-product">Prodotto non disponibile al momento</p>
        <% } %> <!-- Chiudi il blocco if-else per il controllo della quantità del prodotto -->
    </div>
    <div class="product-details">
        <p><strong>Dettagli: <br></strong> <%= product.getDetails() %></p>
    </div>
</div>
<% } else { %>
<p>Nessun prodotto selezionato</p>
<% } %>
<%@ include file="Footer.jsp" %>
</body>
</html>
