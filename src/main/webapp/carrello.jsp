<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>
<%@ page import="main.javas.util.Carrello" %>

<%
    Carrello carrello = (Carrello) session.getAttribute("cart");
    if (carrello == null) {
        carrello = new Carrello();
    }
    List<ProductBean> arrayArticoli = carrello.getProdotti();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
</head>
<body>
    <h1>Carrello</h1>
    <table border="1">
        <tr>
            <th>Nome</th>
            <th>Prezzo</th>
            <th>Quantità</th>
            <th>Elimina</th>
        </tr>
        <%
            for (ProductBean articolo : arrayArticoli) {
        %>
        <tr>
            <td><%=articolo.getProductName()%></td>
            <td><%=articolo.getPrice()%></td>
            <td><%=articolo.getQuantity()%></td>
            <td><a href="ServletCarrello?action=remove&code=<%=articolo.getCode()%>">Rimuovi</a></td>
        </tr>
        <%
            }
        %>
    </table>
    <a href="ProductView.jsp">Torna alla home</a>
    <a href="checkout.jsp">Vai al checkout</a>
</body>
</html>