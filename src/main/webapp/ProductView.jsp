<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>

<%
    Collection<?> products = (Collection<?>) request.getAttribute("products");
    if(products == null){
      response.sendRedirect("./ProductControl");
    }
    ProductBean product = (ProductBean) request.getAttribute("product");
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="ProductStyle.css" rel="stylesheet" type="text/css">
    <title>PosterWorld</title>
</head>
<body>

<h2>Products</h2>
<table border = "1">
    <tr>
        <th>Id_prodotto</th>
        <th>Nome_Prodotto</th>
        <th>Dettagli</th>
        <th>Quantità</th>
        <th>Categoria</th>
        <th>Prezzo</th>
        <th>Iva</th>
        <th>Sconto</th>
        <th>Action</th>
    </tr>

    <%
        if (products == null) {
    %>

    <tr>
        <td colspan = "9">No products available.</td>
    </tr>

    <%
        } else {
            Iterator<?> it = products.iterator();
            while(it.hasNext()) {
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
        <td>
            <a href="product?action=delete&code=<%=bean.getCode()%>">Delete</a><br>
            <a href="DetailProductPage.jsp?action=read&code=<%=bean.getCode()%>">Details</a>
        </td>

    </tr>

    <%
            }
        }
    %>

</table>

</body>
</html>
