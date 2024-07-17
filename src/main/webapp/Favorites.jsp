<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.bean.FavoritesBean" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.model.Product.FavoritesModel" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <title>Preferiti</title>
    <link rel="stylesheet" type="text/css" href="css/Favorites.css">
</head>
<body>
<%@ include file="Header.jsp" %> <!-- Include the header -->

<h1 class="page-title">Preferiti</h1>
<div class="favorites-container">
    <%
        UserBean user = (UserBean) session.getAttribute("user");
        if(user == null) {
            response.sendRedirect(request.getContextPath() + "/Login.jsp");
            return;
        }
        FavoritesModel favoritesModel = new FavoritesModel();
        ProductModelDS productModel = new ProductModelDS();
        Collection<FavoritesBean> favorites = null;
        try {
            favorites = favoritesModel.doRetrieveAll(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (FavoritesBean favorite : favorites) {
            try {
                ProductBean product = productModel.doRetrieveByKey(favorite.getProductCode());
    %>
    <div class="favorite-item">
        <div class="product-image">
            <a href="<%= request.getContextPath() %>/RetrieveProductServlet?action=read&code=<%=product.getCode()%>">
                <img src="<%= request.getContextPath() %>/GetProductImageServlet?action=get&code=<%= product.getCode() %>" alt="<%= product.getProductName() %>">
            </a>
        </div>
        <div class="product-details">
            <a href="<%= request.getContextPath() %>/RetrieveProductServlet?action=read&code=<%=product.getCode()%>">
                <h3><%= product.getProductName() %></h3>
            </a>
            <% if (product.getDiscount() > 0) { %>
            <p>Prezzo: <del class="original-price"><%= product.getPrice() %> €</del> &nbsp; <span class="discounted-price"><%= productModel.calculateDiscountedPrice(product.getCode()) %> €</span></p>
            <% } else { %>
            <p>Prezzo: <span class="original-price"><%= product.getPrice() %> €</span></p>
            <% } %>

            <form class="remove-favorite-form" action="<%= request.getContextPath() %>/RemoveFromFavoriteServlet" method="post">
                <input type="hidden" name="productCode" value="<%= product.getCode() %>">
                <button type="submit">Rimuovi dai preferiti</button>
            </form>
        </div>
    </div>
    <%
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    %>
</div>

<%@ include file="Footer.jsp" %> <!-- Include the footer -->
</body>
</html>
