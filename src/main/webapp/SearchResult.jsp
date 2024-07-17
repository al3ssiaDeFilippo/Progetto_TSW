<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/Categories.css"> <!-- Usa lo stesso CSS di Categories -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
    <title>Risultati della Ricerca</title>
</head>
<body>
<!-- Header -->
<%@ include file="Header.jsp" %>

<!-- Contenuto principale -->
<main>
    <h1>Risultati della Ricerca</h1>
    <div class="products-container">
        <%
            List<ProductBean> searchResults = (List<ProductBean>) request.getAttribute("searchResult");
            if (searchResults != null && !searchResults.isEmpty()) {
                for (ProductBean product : searchResults) {
        %>
        <div class="product">
            <% if (product.getDiscount() > 0) { %>
            <div class="discount-badge">Sconto</div>
            <% } %>
            <a href="<%= request.getContextPath() %>/RetrieveProductServlet?action=read&code=<%=product.getCode()%>">
            <!-- Immagine del prodotto (assumendo che esista un servlet per recuperare l'immagine) -->
                <img class="product-image" src="GetProductImageServlet?action=get&code=<%=product.getCode()%>" alt="<%= product.getProductName()%>">
                <h2><%= product.getProductName() %></h2>
                <div class="price-p">
                    <% if (product.getDiscount() > 0) { %>
                    <span class="discounted-price"><%= product.getPrice() - (product.getPrice() * product.getDiscount() / 100) %> €</span>                    <% } else { %>
                    <p>Prezzo: <%= product.getPrice() %> €</p>
                    <% } %>
                </div>
            </a>
        </div>
        <%
            }
        } else {
        %>
        <p>Nessun prodotto trovato.</p>
        <%
            }
        %>
    </div>
</main>

<!-- Footer -->
<%@ include file="Footer.jsp" %>
</body>
</html>