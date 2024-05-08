<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>

<%
    ProductBean product = (ProductBean) request.getAttribute("product");
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
        <th>Quantità</th>
        <th>Categoria</th>
        <th>Prezzo</th>
        <th>Iva</th>
        <th>Sconto</th>
        <th>Frame</th>
        <th>FrameColor</th>
        <th>Size</th>

    </tr>

    <tr>
        <td><%= product.getCode() %></td>
        <td><%= product.getProductName() %></td>
        <td><%= product.getDetails() %></td>
        <td><%= product.getQuantity() %></td>
        <td><%= product.getCategory() %></td>
        <td><%= product.getPrice() %></td>
        <td><%= product.getIva() %></td>
        <td><%= product.getDiscount() %></td>
        <td><%= product.getFrame() %></td>
        <td><%= product.getFrameColor() %></td>
        <td><%= product.getSize() %></td>

    </tr>
</table>
<a href="ServletCarrello?action=add&code=<%=product.getCode()%>">Add to Cart</a>
<a href="carrello.jsp">Visualizza Carrello</a>
<% } else { %>
<p>Nessun prodotto selezionato</p>
<% } %>
</body>
</html>