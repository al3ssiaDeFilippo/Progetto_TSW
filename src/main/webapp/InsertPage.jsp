<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="css/InsertPage.css">
    <script src="js/InsertProductFormValidation.js" defer></script>

    <title>Inserisci Prodotto</title>
</head>
<body>
<%@ include file="Header.jsp" %>

<h2>Inserisci Prodotto</h2>
<%
    UserBean user = (UserBean) session.getAttribute("user");
    if (user == null || !user.getAdmin()) {
        response.sendRedirect(request.getContextPath() + "/LogIn.jsp");
    }
    if (request.getAttribute("errorMessage") != null) { %>
<div class="alert alert-danger">
    <%= request.getAttribute("errorMessage") %>
</div>
<% } %>
<div class="as">
    <form id="insertProductForm" action="<%= request.getContextPath() %>/InsertProductServlet" method="post" enctype="multipart/form-data">
        <label class="form-domain" for="productName">Nome:</label><br>
        <input type="text" id="productName" name="productName"><br>
        <span id="productNameError" class="error"></span><br>

        <label class="form-domain" for="details">Dettagli:</label><br>
        <input type="text" id="details" name="details"><br>
        <span id="detailsError" class="error"></span><br>

        <label class="form-domain" for="quantity">Quantità:</label><br>
        <input type="number" id="quantity" name="quantity"><br>
        <span id="quantityError" class="error"></span><br>

        <label class="form-domain" for="category">Categoria:</label><br>
        <select id="category" name="category">
            <option value="selectAcategory" disabled selected>Seleziona una categoria</option>
            <option value="Film">Film</option>
            <option value="Anime">Anime</option>
            <option value="Giochi">Giochi</option>
            <option value="Serie TV">Serie TV</option>
            <option value="Fumetti">Fumetti</option>
        </select><br>
        <span id="categoryError" class="error"></span><br>

        <label class="form-domain" for="price">Prezzo:</label><br>
        <input type="number" id="price" name="price" step="1"><br>
        <span id="priceError" class="error"></span><br>

        <label class="form-domain" for="iva">IVA:</label><br>
        <input type="number" id="iva" name="iva" step="1"><br>
        <span id="ivaError" class="error"></span><br>

        <label class="form-domain" for="discount">Sconto:</label><br>
        <input type="number" id="discount" name="discount" step="1"><br>
        <span id="discountError" class="error"></span><br>

        <label class="form-domain" for="photoPath">Immagine:</label><br>
        <input type="file" id="photoPath" name="photoPath"><br>
        <span id="photoPathError" class="error"></span><br>

        <!-- Pulsante di invio -->
        <button id="submitButton" type="submit">
            Inserisci Prodotto
            <img src="Images/addProduct.gif" id="loadingGif" alt="Loading...">
        </button>

    </form>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>
