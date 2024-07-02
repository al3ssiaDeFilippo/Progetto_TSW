<%@ page import="main.javas.bean.ShippingBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.model.Order.ShippingModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Indirizzo di spedizione</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<%
    UserBean user = (UserBean) session.getAttribute("user");
    String nextPage = (String) request.getParameter("nextPage");
    ShippingModel shippingModel = new ShippingModel();
    Collection<ShippingBean> Addresses = shippingModel.doRetrieveAll(user.getIdUser());
%>

<%
    if (!Addresses.isEmpty()) {
        for (ShippingBean address : Addresses) {
%>

<div>
    <h2>Indirizzo di Spedizione</h2>
    <p>Nome del Ricevente: <%= address.getRecipientName() %></p>
    <p>Indirizzo: <%= address.getAddress() %></p>
    <p>Città: <%= address.getCity() %></p>
    <p>CAP: <%= address.getCap() %></p>
    <form action="ShippingServlet" method="post">
        <input type="hidden" name="action" value="select">
        <input type="hidden" name="selectedAddress" value="<%= address.getIdShipping() %>">
        <input type="submit" value="Seleziona indirizzo">
    </form>
</div>
<% } %>
<% } %>

<h2>Inserimento nuovo indirizzo</h2>
<form action="ShippingServlet" method="post">
    <input type="hidden" name="action" value="add">
    <input type="hidden" name="nextPage" value="CheckoutCard.jsp">
    <input type="hidden" id="idUser" name="idUser">
    <div>
        <label for="recipientName">Nome del Ricevente:</label>
        <input type="text" id="recipientName" name="recipientName" required>
    </div>
    <div>
        <label for="address">Indirizzo:</label>
        <input type="text" id="address" name="address" required>
    </div>
    <div>
        <label for="city">Città:</label>
        <input type="text" id="city" name="city" required>
    </div>
    <div>
        <label for="cap">CAP:</label>
        <input type="text" id="cap" name="cap" required>
    </div>
    <div>
        <input type="checkbox" id="saveAddress" name="saveAddress" value="true">
        <label for="saveAddress">Salva indirizzo (autorizzo PosterWorld a salvare i miei dati)</label>
    </div>
    <div>
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="nextPage" value="CheckoutCard.jsp">
        <input type="submit" value="Prosegui al pagamento">
    </div>

</form>

<a href="ProductView.jsp">Continua lo shopping</a>
<%@ include file="Footer.jsp" %>
</body>
</html>