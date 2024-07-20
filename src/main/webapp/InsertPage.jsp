<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/InsertPage.css">
    <title>Inserisci Prodotto</title>
</head>
<body>
<%@ include file="Header.jsp" %>

<h2>Inserisci Prodotto</h2>
<% if (request.getAttribute("errorMessage") != null) { %>
<div class="alert alert-danger">
    <%= request.getAttribute("errorMessage") %>
</div>
<% } %>
<div class="as">
<form action="<%= request.getContextPath() %>/InsertProductServlet" method="post" enctype="multipart/form-data">
    <label for="productName">Nome:</label><br>
    <input type="text" id="productName" name="productName"><br>

    <label for="details">Dettagli:</label><br>
    <input type="text" id="details" name="details"><br>

    <label for="quantity">Quantità:</label><br>
    <input type="number" id="quantity" name="quantity"><br>

    <label for="category">Categoria:</label><br>
    <select id="category" name="category">
        <option value="selectAcategory" disabled selected>Seleziona una categoria</option>
        <option value="Film">Film</option>
        <option value="Anime">Anime</option>
        <option value="Giochi">Giochi</option>
        <option value="Serie TV">Serie TV</option>
        <option value="Fumetti">Fumetti</option>
    </select><br>

    <label for="price">Prezzo:</label><br>
    <input type="number" id="price" name="price" step="0.01"><br>

    <label for="iva">IVA:</label><br>
    <input type="number" id="iva" name="iva"><br>

    <label for="discount">Sconto:</label><br>
    <input type="number" id="discount" name="discount" step="0.01"><br>

    <label for="photoPath">Immagine:</label><br>
    <input type="file" id="photoPath" name="photoPath"><br>

    <input type="submit" value="Procedi">
</form>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>
