<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.bean.ShippingBean" %>
<%@ page import="main.javas.model.Order.ShippingModel" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    UserBean user = (UserBean) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
    ShippingModel shippingModel = new ShippingModel();
    Collection<ShippingBean> addresses = shippingModel.doRetrieveAll(user.getIdUser());
%>

<html>
<head>
    <title>Indirizzi di Spedizione</title>
    <link rel="stylesheet" type="text/css" href="css/IndirizziUtente.css">
    <script src="js/ShippingFormValidation.js"></script>
</head>
<body>
<%@ include file="Header.jsp" %>

<% if (!addresses.isEmpty()) { %>
<% for (ShippingBean shipping : addresses) { %>
<div class="address-detail">
    <h2>Indirizzo di Spedizione</h2>
    <p>Nome Destinatario: <%= shipping.getRecipientName() %></p>
    <p>Indirizzo: <%= shipping.getAddress() %></p>
    <p>Città: <%= shipping.getCity() %></p>
    <p>CAP: <%= shipping.getCap() %></p>
    <form action="<%= request.getContextPath() %>/DeleteAddressServlet" method="post">
        <input type="hidden" name="idAddress" value="<%= shipping.getIdShipping() %>">
        <div class="btn-delete">
            <input type="submit" value="Elimina">
        </div>
    </form>
</div>
<% } %>
<% } %>
<div class="addresses-detail">
    <form id="addAddressForm" action="<%= request.getContextPath() %>/AddAddressServlet" method="post">
        <h2>Inserimento Indirizzo di Spedizione</h2>
        <input type="hidden" id="idUser" name="idUser" value="<%= user.getIdUser() %>">
        <div>
            <label for="recipientName">Nome Destinatario:</label>
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
            <input type="hidden" name="saveAddress" value="true">
            <div class="btn-add">
                <input type="submit" value="Salva indirizzo">
            </div>
        </div>
    </form>
</div>
<div class="addresses-cata">
    <a href="ProductView.jsp">Torna al catalogo</a>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>
