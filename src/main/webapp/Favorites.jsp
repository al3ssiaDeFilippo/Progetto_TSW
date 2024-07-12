<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.bean.FavoritesBean" %>
<%@ page import="main.javas.model.Product.FavoritesModel" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <title>Favorites</title>
</head>
<body>
<h1>Preferiti</h1>
<%
    UserBean user = (UserBean) session.getAttribute("user");
    FavoritesModel favoritesModel = new FavoritesModel();
    Collection<FavoritesBean> favorites = null;
    try {
        favorites = favoritesModel.doRetrieveAll(user);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    for (FavoritesBean favorite : favorites) {
%>
        <p>Product Code: <%= favorite.getProductCode() %></p>
<%
    }
%>
</body>
</html>