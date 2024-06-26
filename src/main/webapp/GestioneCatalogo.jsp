<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>


<%
    Collection<?> products = (Collection<?>) request.getAttribute("products");
    if (products == null) {
        response.sendRedirect("./ProductControl");
        return; // Stop further processing of the page
    }

%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="ProductView.css">
    <title>PosterWorld</title>
</head>
<body>

<h2>Products</h2>
<table class="product-table" border="1">
    <tr>
        <th>Nome_Prodotto</th>
        <th>Foto</th>
        <th>Prezzo</th>
        <th>Action</th>
    </tr>

    <%
        if (products.isEmpty()) {
    %>
    <tr>
        <td colspan="4">No products available.</td>
    </tr>
    <%
    } else {
        for (Object obj : products) {
            ProductBean bean = (ProductBean) obj;
    %>
    <tr>
        <td><%=bean.getProductName()%></td>
        <td><img class="product-image" src="ImmagineProdottoServlet?code=<%=bean.getCode()%>" alt="image not found"></td>
        <td><%=bean.getPrice()%></td>
        <td>
            <form action="product" method="get">
                <input type="hidden" name="action" value="read">
                <input type="hidden" name="code" value="<%=bean.getCode()%>">
                <input type="submit" value="Details">
            </form>
            <form action="ProductControl" method="get">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="code" value="<%=bean.getCode()%>">
                <input type="submit" value="Delete">
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>