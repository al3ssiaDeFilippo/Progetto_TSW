<%@ page import="java.util.Collection" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/Header.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
    <title>Prodotti per Categoria</title>
</head>
<body>
<%@ include file="Header.jsp" %> <!-- Includi l'header -->

<main>
    <h1>Prodotti in <%= request.getParameter("category") %></h1>
    <div class="products-container">
        <%
            String category = request.getParameter("category");
            if (category != null && !category.isEmpty()) {
                ProductModelDS productDAO = new ProductModelDS();
                try {
                    Collection<ProductBean> products = productDAO.doRetrieveByCategory(category);

                    if (products.isEmpty()) {
        %>
        <p>Nessun prodotto trovato in questa categoria.</p>
        <%
        } else {
            for (ProductBean product : products) {
        %>
        <div class="product">
            <h2><%= product.getProductName() %></h2>
            <p><%= product.getDetails() %></p>
            <p>Prezzo: <%= product.getPrice() %> €</p>
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
</main>

</body>
</html>
