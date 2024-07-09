<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.bean.ShippingBean" %>
<%@ page import="main.javas.model.Order.ShippingModel" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<%
    // Controlla se l'utente è loggato
    UserBean user = (UserBean) session.getAttribute("user");
    ShippingModel shippingModel = new ShippingModel();
    Collection<ShippingBean> addresses = shippingModel.doRetrieveAll(user.getIdUser());
%>

<% if (!addresses.isEmpty()) { %>
<% for (ShippingBean shipping : addresses) { %>
<div>
    <h2>Indirizzo di spedizione</h2>
    <p>Nome Destinatario: <%= shipping.getRecipientName() %></p>
    <p>Indirizzo: <%= shipping.getAddress() %></p>
    <p>Città: <%= shipping.getCity() %></p>
    <p>CAP: <%= shipping.getCap() %></p>
    <form action="<%= request.getContextPath() %>/DeleteAddressServlet" method="post">
        <input type="hidden" name="idAddress" value="<%= shipping.getIdShipping() %>">
        <input type="submit" value="Rimuovi">
    </form>
</div>
<% } %>
<% } %>

<h1>Inserimento Indirizzo di Spedizione</h1>
<form action="<%= request.getContextPath() %>/AddAddressServlet" method="post">
    <input type="hidden" id="idUser" name="idUser">
    <div>
        <label for="recipientName">Nome Destinatario:</label>
        <input type="text" id="recipientName" name="recipientName">
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
        <input type="hidden" name="saveAddress" value="true">
        <input type="submit" value="Salva indirizzo">
    </div>
</form>
<%@ include file="Footer.jsp" %>
</body>
</html>
