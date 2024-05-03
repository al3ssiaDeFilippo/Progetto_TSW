<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>

<%
    ProductBean product = (ProductBean) request.getAttribute("product");
    System.out.println(product);
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">
    <title>Dettagli Prodotto</title>
</head>
<body>
<h2>Dettagli Prodotto</h2>
<%
    if (product != null) {
%>
<table border="1">
    <tr>
        <th>Id</th>
        <th>Nome</th>
        <th>Dettagli</th>
        <!-- Aggiungi qui gli altri attributi del prodotto, se necessario -->
    </tr>
    <tr>
        <td><%= product.getCode() %></td>
        <td><%= product.getProductName() %></td>
        <td><%= product.getDetails() %></td>

        <!-- Aggiungi qui gli altri attributi del prodotto, se necessario -->
    </tr>
</table>
<% } else { %>
<p>Nessun prodotto selezionato</p>
<% } %>
</body>
</html>
