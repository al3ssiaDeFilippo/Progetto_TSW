<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.ProductBean" %>

<%
    Collection<?> products = (Collection<?>) request.getAttribute("products");
    ProductBean product = (ProductBean) request.getAttribute("product");
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">
    <title>Storage DS/BF</title>
</head>

<body>
<h2>Products</h2>
<a href="product">List</a>
<table border="1">
    <tr>
        <th>Id_prodotto <a href="product?sort=id_prodotto&direction=asc">Sort</a></th>
        <th>Nome_prodotto <a href="product?sort=nome_prodotto&direction=asc">Sort</a></th>
        <th>Dettagli <a href="product?sort=dettagli&direction=asc">Sort</a></th>
        <th>Quantita <a href="product?sort=quantita&direction=asc">Sort</a></th>
        <th>Categoria <a href="product?sort=categoria&direction=asc">Sort</a></th>
        <th>Prezzo <a href="product?sort=prezzo&direction=asc">Sort</a></th>
        <th>Iva <a href="product?sort=iva&direction=asc">Sort</a></th>
        <th>Sconto <a href="product?sort=sconto&direction=asc">Sort</a></th>
        <th>Action</th>
    </tr>
    <%
        if (products == null || !products.iterator().hasNext()) {
    %>
    <tr>
        <td colspan="9">No products available</td>
    </tr>
    <%
    } else {
        Iterator<?> it = products.iterator();
        while (it.hasNext()) {
            ProductBean bean = (ProductBean) it.next();
    %>
    <tr>
        <td><%=bean.getCode()%></td>
        <td><%=bean.getProductName()%></td>
        <td><%=bean.getDetails()%></td>
        <td><%=bean.getQuantity()%></td>
        <td><%=bean.getCategory()%></td>
        <td><%=bean.getPrice()%></td>
        <td><%=bean.getIva()%></td>
        <td><%=bean.getDiscount()%></td>
        <td><a href="product?action=delete&code=<%=bean.getCode()%>">Delete</a><br>
            <a href="product?action=read&code=<%=bean.getCode()%>">Details</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

<h2>Insert</h2>
<form action="product" method="post">
    <%--@declare id="nome_prodotto"--%><%--@declare id="dettagli"--%><%--@declare id="categoria"--%><%--@declare id="quantita"--%><%--@declare id="prezzo"--%><%--@declare id="iva"--%><%--@declare id="sconto"--%><input type="hidden" name="action" value="insert">

    <label for="nome_prodotto">Nome:</label><br>
    <input name="productName" type="text" maxlength="20" required placeholder="enter name"><br>

    <label for="dettagli">Dettagli:</label><br>
    <textarea name="details" maxlength="100" rows="3" required placeholder="enter description"></textarea><br>

    <label for="quantita">Quantita:</label><br>
    <input name="quantity" type="number" min="1" value="1" required><br>

    <label for="categoria">Categoria:</label><br>
    <input id="anime" name="category" type="radio" value="anime"> Anime<br>
    <input id="film" name="category" type="radio" value="film"> Film<br>
    <input id="giochi" name="category" type="radio" value="giochi"> Giochi<br>
    <input id="fumetti" name="category" type="radio" value="fumetti"> Fumetti<br>
    <input id="serie_tv" name="category" type="radio" value="serie_tv"> Serie TV<br>

    <label for="prezzo">Prezzo:</label><br>
    <input name="price" type="number" min="0" value="0" required><br>

    <label for="iva">Iva</label><br>
    <input name="iva" type="number" min="0" value="0" required><br>

    <label for="sconto">Sconto</label><br>
    <input name="discount" type="number" min="0" value="0"><br>

    <input type="submit" value="Add"><input type="reset" value="Reset">

</form>
</body>
</html>
