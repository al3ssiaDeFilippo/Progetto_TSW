<%@ page import="java.util.*" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="main.javas.model.Product.FavoritesModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    ProductModelDS productModel = new ProductModelDS();
    FavoritesModel favoritesModel = new FavoritesModel();
    UserBean user = (UserBean) session.getAttribute("user");

    Collection<ProductBean> products = null;
    try {
        products = productModel.doRetrieveAll("");
    } catch (SQLException e) {
        e.printStackTrace();
    }

    if (products == null) {
        response.sendRedirect("./RetrieveProductServlet");
        return; // Stop further processing of the page
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/ProductView.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
    <script>
        var contextPath = '<%= request.getContextPath() %>';
    </script>
    <script src="js/FavGifControl.js"></script>
    <title>PosterWorld</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<h2>Catalogo Prodotti</h2>
<div class="products-container">
    <%
        if (products.isEmpty()) {
    %>
    <p>Nessun prodotto disponibile.</p>
    <%
    } else {
        for (ProductBean product : products) {
            boolean isFavorite = user != null && favoritesModel.isFavorite(product, user);
    %>
    <div class="product">
        <% if (product.getDiscount() > 0) { %>
        <div class="discount-badge">Sconto</div>
        <% } %>

        <% if (user != null && !user.getAdmin()) { %>
        <form class="favorite-form"
              action="<%= request.getContextPath() + "/ToggleFavoriteServlet" %>"
              method="post"
              data-action="<%= isFavorite ? "remove" : "add" %>"
              onclick="return toggleFavorite(this, '<%=product.getCode()%>', '<%=user != null ? user.getIdUser() : ""%>')">
            <input type="hidden" name="productCode" value="<%=product.getCode()%>">
            <img class="favorite-icon" src="<%= isFavorite ? "Images/full-heart.png" : "Images/empty-heart.png" %>" alt="favorite-icon">
        </form>
        <% } %>

        <a href="<%= request.getContextPath() %>/RetrieveProductServlet?action=read&code=<%=product.getCode()%>">
            <img class="product-image" src="<%= request.getContextPath() %>/GetProductImageServlet?action=get&code=<%=product.getCode()%>" alt="<%= product.getProductName()%>">
            <h2><%= product.getProductName() %></h2>
            <div class="price-p">
                <% if (product.getDiscount() > 0) { %>
                <p class="price-label">Prezzo: <del class="original-price"><%= product.getPrice() %> €  &nbsp;</del> <span class="discounted-price"><%= productModel.calculateDiscountedPrice(product.getCode())%> €</span></p>
                <% } else { %>
                <p>Prezzo: <%= product.getPrice() %> €</p>
                <% } %>
            </div>
        </a>
    </div>
    <%
            }
        }
    %>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>