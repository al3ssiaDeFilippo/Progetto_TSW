<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Inserisci Prodotto</title>
</head>
<body>
<h2>Inserisci Prodotto</h2>
<form action="ProductControl?action=insert" method="post" enctype="multipart/form-data">
    <label for="productName">Nome:</label><br>
    <input type="text" id="productName" name="productName"><br>

    <label for="details">Dettagli:</label><br>
    <input type="text" id="details" name="details"><br>

    <label for="quantity">Quantità:</label><br>
    <input type="number" id="quantity" name="quantity"><br>

    <label for="category">Categoria:</label><br>
    <select id="category" name="category">
        <option value="selectAcategory">Seleziona una categoria</option>
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

    <label for="frame">Frame:</label><br>
    <select id="frame" name="frame">
        <option value="no frame">No Frame</option>
        <option value="wood">Wood</option>
        <option value="PVC">PVC</option>
    </select><br>

    <label for="frameColor">Colore Frame:</label><br>
    <select id="frameColor" name="frameColor">
        <option value="black">Black</option>
        <option value="brown">Brown</option>
        <option value="white">White</option>
    </select><br>

    <label for="size">Dimensione:</label><br>
    <select id="size" name="size">
        <option value="21x30">21x30</option>
        <option value="85x60">85x60</option>
        <option value="91x61">91x61</option>
    </select><br>

    <label for="photoPath">Immagine:</label><br>
    <input type="file" id="photoPath" name="photoPath"><br>

    <input type="submit" value="Inserisci Prodotto">
    <a href="ProductView.jsp">Torna al catalogo</a>
</form>
</body>
</html>