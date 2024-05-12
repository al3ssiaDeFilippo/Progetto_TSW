<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>
<%@ page import="main.javas.util.Carrello" %>
<%@ page import="main.javas.model.CartBean" %>

<%
    Carrello carrello = (Carrello) session.getAttribute("cart");
    if (carrello == null) {
        carrello = new Carrello();
    }
    List<CartBean> arrayArticoli = carrello.getProdotti();
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

            <th>Id Prodotto</th>
            <th>Prezzo</th>
            <th>Quantità</th>
            <th>Elimina</th>
        </tr>
        <%
            for (CartBean articolo : arrayArticoli) {
        %>
        <tr>
            <td><%=articolo.getCode()%></td>
            <td><%=articolo.getPrice()%></td>
            <!-- <td><%=articolo.getQuantity()%></td>-->
            <td>
                <form action="ServletCarrello?action=add" method="post">
                    <input type="hidden" name="code" value="<%=articolo.getCode()%>">
                    <input type="number" name="quantity" value="1" min="1"> <!-- Campo per la quantità -->
                    <input type="submit" value="Aggiorna">
                </form>

            </td>
            <td><a href="ServletCarrello?action=delete&code=<%=articolo.getCode()%>">Rimuovi</a></td>
        </tr>
        <%
            }
        %>
    </table>
    <a href="ProductView.jsp">Torna alla home</a>
    <a href="checkout.jsp">Vai al checkout</a>
</body>
</html>