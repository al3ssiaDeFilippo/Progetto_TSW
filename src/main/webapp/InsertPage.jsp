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
    <input type="text" id="category" name="category"><br>
    <label for="price">Prezzo:</label><br>
    <input type="number" id="price" name="price" step="0.01"><br>
    <label for="iva">IVA:</label><br>
    <input type="number" id="iva" name="iva" step="0.01"><br>
    <label for="discount">Sconto:</label><br>
    <input type="number" id="discount" name="discount" step="0.01"><br>
    <label for="frame">Frame:</label><br>
    <input type="text" id="frame" name="frame"><br>
    <label for="frameColor">Colore Frame:</label><br>
    <input type="text" id="frameColor" name="frameColor"><br>
    <label for="size">Dimensione:</label><br>
    <input type="text" id="size" name="size"><br>
    <label for="photoPath">Immagine:</label><br>
    <input type="file" id="photoPath" name="photoPath"><br>
    <input type="submit" value="Inserisci Prodotto">
</form>
</body>
</html>