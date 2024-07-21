<%@ page import="java.util.*" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="main.javas.model.Product.FavoritesModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    // Recupera i prodotti dalla richiesta impostata dal FilterProductServlet
    Collection<ProductBean> products = (Collection<ProductBean>) request.getAttribute("products");

    // Aggiungi un controllo per evitare NullPointerException
    if (products == null) {
        products = new ArrayList<ProductBean>();
    }

    ProductModelDS productModel = new ProductModelDS();
    FavoritesModel favoritesModel = new FavoritesModel();
    UserBean user = (UserBean) session.getAttribute("user");

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

<div class="main-container">
    <div class="sidebar">
        <form id="filter-form" action="<%= request.getContextPath() %>/FilterProductServlet" method="get">
            <fieldset>
                <legend>Filtra per:</legend>
                <label>
                    <input type="radio" name="filter" value="all" <%= (request.getParameter("filter") == null || request.getParameter("filter").equals("all")) ? "checked" : "" %>>
                    Tutti i prodotti
                </label>
                <label>
                    <input type="radio" name="filter" value="discounted" <%= "discounted".equals(request.getParameter("filter")) ? "checked" : "" %>>
                    Prodotti Scontati
                </label>
                <label>
                    <input type="radio" name="filter" value="up_to_50" <%= "up_to_50".equals(request.getParameter("filter")) ? "checked" : "" %>>
                    Prezzo fino a 50€
                </label>
                <label>
                    <input type="radio" name="filter" value="up_to_100" <%= "up_to_100".equals(request.getParameter("filter")) ? "checked" : "" %>>
                    Prezzo da 50 a 100€
                </label>
                <label>
                    <input type="radio" name="filter" value="over_100" <%= "over_100".equals(request.getParameter("filter")) ? "checked" : "" %>>
                    Prezzo oltre 100€
                </label>
                <input type="submit" value="Applica Filtro">
            </fieldset>
        </form>
    </div>

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
</div>

<%@ include file="Footer.jsp" %>
</body>
</html>
