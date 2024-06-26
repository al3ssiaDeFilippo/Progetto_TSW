<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>
<%@ page import="main.javas.model.UserBean" %>

<%
    ProductBean product = (ProductBean) request.getAttribute("product");
    UserBean user = (UserBean) session.getAttribute("user"); // Assumi che l'oggetto utente sia salvato in sessione come "user"
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="DetailProductPage.css" rel="stylesheet" type="text/css">
    <script src="js/DetailSelection.js"></script>
    <title>Dettagli Prodotto</title>
</head>
<body>

<div class="product-details-container">
    <%
        if (product != null) {
    %>
    <div class="product-header">
        <h2><%= product.getCategory() %> - <%= product.getProductName() %></h2>
    </div>
    <div class="product-content">
        <div class="product-image">
            <img id="productImage" data-product-code="<%= product.getCode() %>" src="ProductImageServlet?action=get&code=<%= product.getCode() %>&custom=true" alt="image not found">
            <p><strong>Dettagli:</strong> <%= product.getDetails() %></p>
            <p><strong>Prezzo:</strong> <%= product.getPrice() %> €</p>
            <p><strong>IVA:</strong> <%= product.getIva() %> %</p>
            <p><strong>Sconto:</strong> <%= product.getDiscount() %> %</p>
            <form action="ServletCarrello" method="post">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="code" value="<%= product != null ? product.getCode() : "" %>">
                <!-- Add select fields for frame, frame color, and size -->
                <p><strong>Materiale Cornice:</strong>
                    <select name="frame" id="frameSelect" onchange="checkFrame()">
                        <option value="no frame" selected>No Frame</option>
                        <option value="wood">Wood</option>
                        <option value="PVC">PVC</option>
                    </select>
                </p>
                <p id="frameColorSelectContainer"><strong>Colore Cornice:</strong>
                    <select name="frameColor" id="frameColorSelect">
                        <option value="selectAframeColor" disabled selected>Seleziona un colore per il frame</option>
                        <option value="black">Black</option>
                        <option value="brown">Brown</option>
                        <option value="white">White</option>
                    </select>
                </p>
                <p><strong>Dimensioni:</strong>
                    <select name="size">
                        <option value="selectAsize" disabled selected>Seleziona la dimensione</option>
                        <option value="21x30">21x30</option>
                        <option value="85x60">85x60</option>
                        <option value="91x61">91x61</option>
                    </select>
                </p>
                <% if (user == null || !user.getAdmin()) { %> <!-- Se l'utente non è loggato o non è un admin, mostra il pulsante -->
                <input type="submit" value="Aggiungi al Carrello">
                <% } %>
            </form>
            <div class="centered-links">
                <% if (user == null || !user.getAdmin()) { %> <!-- Se l'utente non è loggato o non è un admin, mostra il link -->
                <a href="carrello.jsp">Visualizza Carrello</a>
                <% } %>
                <a href="ProductView.jsp">Torna alla Home</a>
            </div>
        </div>
    </div>
    <%
    } else {
    %>
    <p>Nessun prodotto selezionato</p>
    <%
        }
    %>
</div>

</body>
</html>
