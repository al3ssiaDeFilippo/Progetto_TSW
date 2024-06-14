<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="main.javas.model.ProductBean" %>


<%
    ProductBean product = (ProductBean) request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Modifica Prodotto</title>
</head>
<body>
<h2>Modifica Prodotto</h2>
<form action="ProductControl?action=update" method="post" enctype="multipart/form-data">
    <input type="hidden" name="code" value="<%=product.getCode()%>">
    <label for="productName">Nome:</label><br>
    <input type="text" id="productName" name="productName" value="<%=product.getProductName()%>"><br>

    <label for="details">Dettagli:</label><br>
    <input type="text" id="details" name="details" value="<%=product.getDetails()%>"><br>

    <label for="quantity">Quantità:</label><br>
    <input type="number" id="quantity" name="quantity" value="<%=product.getQuantity()%>"><br>

    <label for="category">Categoria:</label><br>
    <select id="category" name="category">
        <option value="selectAframe" disabled selected>Seleziona una tipologia di frame</option>
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

    <label for="frame">Frame:</label><br>
    <select id="frame" name="frame">
        <option value="selectAframe" disabled selected>Seleziona una tipologia di frame</option>
        <option value="no frame" <%=product.getFrame().equals("no frame") ? "selected" : ""%>>No Frame</option>
        <option value="wood" <%=product.getFrame().equals("wood") ? "selected" : ""%>>Wood</option>
        <option value="PVC" <%=product.getFrame().equals("PVC") ? "selected" : ""%>>PVC</option>
    </select><br>

    <label for="frameColor">Colore Frame:</label><br>
    <select id="frameColor" name="frameColor">
        <option value="selectAframeColor" disabled selected>Seleziona il colore del frame</option>
        <option value="black" <%=product.getFrameColor().equals("black") ? "selected" : ""%>>Black</option>
        <option value="brown" <%=product.getFrameColor().equals("brown") ? "selected" : ""%>>Brown</option>
        <option value="white" <%=product.getFrameColor().equals("white") ? "selected" : ""%>>White</option>
    </select><br>

    <label for="size">Dimensione:</label><br>
    <select id="size" name="size">
        <option value="selectAsize" disabled selected>Seleziona la dimensione</option>
        <option value="21x30" <%=product.getSize().equals("21x30") ? "selected" : ""%>>21x30</option>
        <option value="85x60" <%=product.getSize().equals("85x60") ? "selected" : ""%>>85x60</option>
        <option value="91x61" <%=product.getSize().equals("91x61") ? "selected" : ""%>>91x61</option>
    </select><br>

    <input type="submit" value="Modifica Prodotto">
</form>
</body>
</html>