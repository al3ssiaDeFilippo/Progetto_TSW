<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Inserisci Prodotto</title>
</head>
<body>
<h2>Inserisci Prodotto</h2>
<% if (request.getAttribute("errorMessage") != null) { %>
<div class="alert alert-danger">
    <%= request.getAttribute("errorMessage") %>
</div>
<% } %>
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

    <label for="frame">Frame:</label><br>
    <select id="frame" name="frame">
        <option value="default" selected>Default</option>
        <option value="no frame" disabled>No Frame</option>
        <option value="wood" disabled>Wood</option>
        <option value="PVC" disabled>PVC</option>
    </select><br>

    <label for="frameColor">Frame Color:</label><br>
    <select id="frameColor" name="frameColor">
        <option value="default" selected>Default</option>
        <option value="black" disabled>Black</option>
        <option value="brown" disabled>Brown</option>
        <option value="white" disabled>White</option>
    </select><br>

    <label for="size">Size:</label><br>
    <select id="size" name="size">
        <option value="default" selected>Default</option>
        <option value="21x30" disabled>21x30</option>
        <option value="85x60" disabled>85x60</option>
        <option value="91x61" disabled>91x61</option>
    </select><br>

    <label for="photoPath">Immagine:</label><br>
    <input type="file" id="photoPath" name="photoPath"><br>

    <input type="submit" value="Procedi">
</form>
</body>
</html>
