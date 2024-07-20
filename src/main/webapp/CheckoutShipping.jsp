<%@ page import="main.javas.bean.ShippingBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.model.Order.ShippingModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Indirizzo di spedizione</title>
    <link rel="stylesheet" type="text/css" href="css/CheckoutShipping.css">
    <script src="js/ShippingFormValidation.js"></script>
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

<div class="addresses-detail">
    <h2>Indirizzo di Spedizione</h2>
    <p>Nome del Ricevente: <%= address.getRecipientName() %></p>
    <p>Indirizzo: <%= address.getAddress() %></p>
    <p>Città: <%= address.getCity() %></p>
    <p>CAP: <%= address.getCap() %></p>
    <form action="<%= request.getContextPath() %>/SelectAddressServlet" method="post">
        <input type="hidden" name="selectedAddress" value="<%= address.getIdShipping() %>">
        <div class="btn-add">
            <input type="submit" value="Seleziona indirizzo">
        </div>
    </form>
</div>
<% } %>
<% } %>

<div class="addresses-detail">
    <h2>Inserimento nuovo indirizzo</h2>
    <form id="addAddressForm" action="<%= request.getContextPath() %>/AddAddressServlet" method="post">
        <input type="hidden" name="nextPage" value="CheckoutCard.jsp">
        <input type="hidden" id="idUser" name="idUser">
        <div>
            <label for="recipientName">Nome del Ricevente:</label>
            <input type="text" id="recipientName" name="recipientName" required>
            <div id="recipientNameError" class="error"></div>
        </div>
        <div>
            <label for="address">Indirizzo:</label>
            <input type="text" id="address" name="address" required>
            <div id="addressError" class="error"></div>
        </div>
        <div>
            <label for="city">Città:</label>
            <input type="text" id="city" name="city" required>
            <div id="cityError" class="error"></div>
        </div>
        <div>
            <label for="cap">CAP:</label>
            <input type="text" id="cap" name="cap" required>
            <div id="capError" class="error"></div>
        </div>
        <div>
            <input type="checkbox" id="saveAddress" name="saveAddress" value="true">
            <label for="saveAddress">Salva indirizzo (autorizzo PosterWorld a salvare i miei dati)</label>
        </div>
        <div class="btn-add">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="nextPage" value="CheckoutCard.jsp">
            <input type="submit" value="Prosegui al pagamento">
        </div>
    </form>
</div>
<div class="addresses-cata">
    <a href="ProductView.jsp">Continua lo shopping</a>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>
