<%@ page import="java.util.Collection" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="main.javas.model.Product.FavoritesModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/Categories.css">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/Header.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
    <script src="js/FavGifControl.js" defer></script>
    <script src="js/DetailSelection.js" defer></script>
    <title>Prodotti per Categoria</title>
</head>
<body>

<%
    ProductModelDS productModel = new ProductModelDS();
    FavoritesModel favoritesModel = new FavoritesModel();
    UserBean user = (UserBean) session.getAttribute("user");
%>

<%@ include file="Header.jsp" %> <!-- Include the header -->

<h2>Prodotti in <%= request.getParameter("category") %></h2>
<div class="products-container">
    <%
        String category = request.getParameter("category");
        if (category != null && !category.isEmpty()) {
            try {
                Collection<ProductBean> products = productModel.doRetrieveByCategory(category);

                if (products.isEmpty()) {
    %>
    <p>Nessun prodotto trovato in questa categoria.</p>
    <%
    } else {
        for (ProductBean product : products) {
            boolean isFavorite = user != null && favoritesModel.isFavorite(product, user);
    %>
    <div class="product">
        <!-- Aggiungi il simbolo dello sconto se il prodotto è scontato -->
        <% if (product.getDiscount() > 0) { %>
        <div class="discount-badge">Sconto</div>
        <% } %>

        <script>
            var contextPath = '<%= request.getContextPath() %>';
        </script>

        <% if (user != null && !user.getAdmin()) { %>
            <!-- Aggiungi il simbolo del prodotto preferito -->
            <form class="favorite-form"
                  action="<%= request.getContextPath() + "/ToggleFavoriteServlet" %>"
                  method="post"
                  data-action="<%= isFavorite ? "remove" : "add" %>"
                  onclick="return toggleFavorite(this, '<%=product.getCode()%>', '<%=user.getIdUser()%>')">
                <input type="hidden" name="productCode" value="<%=product.getCode()%>">
                <img class="favorite-icon" src="<%= isFavorite ? "Images/full-heart.png" : "Images/empty-heart.png" %>" alt="favorite-icon">
            </form>
        <% } %>
        <!-- Link to DetailProductPage.jsp with product code as a query parameter -->
        <a href="<%= request.getContextPath() %>/RetrieveProductServlet?action=read&code=<%=product.getCode()%>">
            <img class="product-image" src="<%= request.getContextPath() %>/GetProductImageServlet?action=get&code=<%=product.getCode()%>" alt="<%= product.getProductName()%>">
            <br>
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
    } catch (SQLException e) {
        e.printStackTrace();
    %>
    <p>Errore nel recuperare i prodotti. Riprova più tardi.</p>
    <%
        }
    } else {
    %>
    <p>Categoria non trovata.</p>
    <%
        }
    %>
</div>

<%@ include file="Footer.jsp" %> <!-- Include the footer -->

</body>
</html>
