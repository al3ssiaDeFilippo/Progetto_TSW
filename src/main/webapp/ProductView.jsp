<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>
<%@ page import="main.javas.model.UserBean" %>

<%
    Collection<?> products = (Collection<?>) request.getAttribute("products");
    if (products == null) {
        response.sendRedirect("./ProductControl");
        return; // Stop further processing of the page
    }

    // Use the implicit session object
    UserBean user = (UserBean) session.getAttribute("user");
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
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="code" value="<%=bean.getCode()%>">
                <input type="submit" value="Delete">
            </form>
            <form action="product" method="get">
                <input type="hidden" name="action" value="read">
                <input type="hidden" name="code" value="<%=bean.getCode()%>">
                <input type="submit" value="Details">
            </form>
            <form action="ServletCarrello" method="get">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="code" value="<%=bean.getCode()%>">
                <input type="submit" value="Add to Cart">
            </form>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>

<div class="centered-links">
    <a href="carrello.jsp">Visualizza Carrello</a>
    <a href="InsertPage.jsp">Inserisci un prodotto</a>
    <% if (user == null) { %>
    <a href="LogIn.jsp">Log In</a>
    <% } if (user != null) { %>
    <form action="LogInServlet" method="post">
        <input type="hidden" name="action" value="logout">
        <input type="submit" value="Logout">
    </form>
    <% } %>
</div>

</body>
</html>
