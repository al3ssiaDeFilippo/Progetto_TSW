<%@ page import="java.util.*" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.bean.UserBean" %>


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
    <link rel="stylesheet" type="text/css" href="css/ProductView.css">
    <title>PosterWorld</title>
</head>
<body>

<%@ include file="Header.jsp" %>
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
        <td><img class="product-image" src="ProductImageServlet?action=get&code=<%=bean.getCode()%>" alt="image not found"></td>        <td><%=bean.getPrice()%></td>
        <td>

            <form action="ProductControl" method="get">
                <input type="hidden" name="action" value="read">
                <input type="hidden" name="code" value="<%=bean.getCode()%>">
                <input type="submit" value="Details">
            </form>

            <% if (user != null && user.getAdmin()) { %>
            <form action="ProductControl" method="post">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="code" value="<%=bean.getCode()%>">
                <input type="submit" value="Delete">
            </form>
            <form action="ProductControl" method="post">
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="code" value="<%=bean.getCode()%>">
                <input type="submit" value="Edit">
            </form>
            <% } %>

        </td>
    </tr>
    <%
            }
        }
    %>
</table>

<div class="centered-links">

    <a href="HomePage.jsp">Home</a>

    <!-- Blocco admin: -->
    <% if (user != null && user.getAdmin()) { %>
    <a href="Profilo.jsp">Profilo</a>

    <form action="LogInServlet" method="post">
        <input type="hidden" name="action" value="logout">
        <input type="submit" value="Logout">
    </form>

    <a href="InsertPage.jsp">Inserisci prodotto</a>

    <a href="UserView.jsp">Utenti</a>

    <a href="OrderView.jsp">Ordini</a>
    <% } %>

    <!-- Blocco utente: -->
    <% if (user != null && !user.getAdmin()) { %>
    <a href="Profilo.jsp">Profilo</a>

    <form action="LogInServlet" method="post">
        <input type="hidden" name="action" value="logout">
        <input type="submit" value="Logout">
    </form>

    <a href="carrello.jsp">Visualizza Carrello</a>
    <% } %>

    <!-- Blocco guest: -->
    <% if (user == null) { %>
    <a href="LogIn.jsp">Log In</a>
    <a href="carrello.jsp">Visualizza Carrello</a>
    <% } %>
</div>

<%@ include file="Footer.jsp" %>
</body>
</html>