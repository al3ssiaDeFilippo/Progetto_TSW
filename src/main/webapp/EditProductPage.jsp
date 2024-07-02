<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.javas.model.Product.*" %>
<%@ page import="main.javas.bean.ProductBean" %>


<%
    ProductBean product = (ProductBean) request.getAttribute("product");
    System.out.println("prodotto: " + product.toString());
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/ProductView.css">
    <script src="js/DetailSelection.js"></script>
    <title>Modifica Prodotto</title>
</head>
<body>
<h2>Modifica Prodotto</h2>
<form action="ProductControl" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="code" value="<%=product.getCode()%>">
    <label for="productName">Nome:</label><br>
    <input type="text" id="productName" name="productName" value="<%=product.getProductName()%>"><br>

    <label for="details">Dettagli:</label><br>
    <input type="text" id="details" name="details" value="<%=product.getDetails()%>"><br>

    <label for="quantity">Quantità:</label><br>
    <input type="number" id="quantity" name="quantity" value="<%=product.getQuantity()%>"><br>

    <label for="category">Categoria:</label><br>
    <select id="category" name="category">
        <option value="selectACategory" disabled selected>Seleziona una categoria</option>
        <option value="Film" <%=product.getCategory().equals("Film") ? "selected" : ""%>>Film</option>
        <option value="Anime" <%=product.getCategory().equals("Anime") ? "selected" : ""%>>Anime</option>
        <option value="Giochi" <%=product.getCategory().equals("Giochi") ? "selected" : ""%>>Giochi</option>
        <option value="Serie TV" <%=product.getCategory().equals("Serie TV") ? "selected" : ""%>>Serie TV</option>
        <option value="Fumetti" <%=product.getCategory().equals("Fumetti") ? "selected" : ""%>>Fumetti</option>
    </select><br>

    <label for="price">Prezzo:</label><br>
    <input type="number" id="price" name="price" value="<%=product.getPrice()%>" step="0.01"><br>

    <label for="iva">IVA:</label><br>
    <input type="number" id="iva" name="iva" value="<%=product.getIva()%>"><br>

    <label for="discount">Sconto:</label><br>
    <input type="number" id="discount" name="discount" value="<%=product.getDiscount()%>" step="0.01"><br>

    <!-- Mostra l'immagine attuale del prodotto -->
    <img class="product-image" src="ProductImageServlet?action=get&code=<%=product.getCode()%>" alt="Immagine attuale del prodotto"><br>

    <!-- Fornisci la possibilità di caricare una nuova immagine -->
    <label for="photoPath">Foto:</label><br>
    <input type="file" id="photoPath" name="photoPath"><br>

    <input type="submit" value="Modifica Prodotto">
</form>
</body>
</html>