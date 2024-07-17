<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.model.Order.CreditCardModel" %>
<%@ page import="main.javas.bean.CreditCardBean" %>
<%@ page import="java.util.Collection" %>

<%
    UserBean user = (UserBean) session.getAttribute("user");
    String nextPage = (String) request.getParameter("nextPage");
    CreditCardModel creditCardModel = new CreditCardModel();
    Collection<CreditCardBean> cards = creditCardModel.doRetrieveAll(user.getIdUser());
%>
<!DOCTYPE html>
<html>
<head>
    <title>Carte di Credito</title>
    <link rel="stylesheet" type="text/css" href="css/CarteUtente.css">
</head>
<body>
<%@ include file="Header.jsp" %>
<% if (!cards.isEmpty()) { %>
<% for (CreditCardBean card : cards) { %>
<div class="card-detail">
    <h2>Carta di Credito</h2>
    <p>Numero Carta: <%= card.getIdCard() %></p>
    <p>Nome Titolare: <%= card.getOwnerCard() %></p>
    <p>Data di Scadenza: <%= card.getExpirationDate() %></p>
    <form action="<%= request.getContextPath() %>/DeleteCardServlet" method="post">
        <input type="hidden" name="cardId" value="<%= card.getIdCard() %>">
        <div class="btn-delete">
            <input type="submit" value="Elimina">
        </div>
    </form>
</div>
<% } %>
<% } %>
<div class="card-detail">
    <form action="<%= request.getContextPath() %>/AddCardServlet" method="post">
        <h2>Inserisci una nuova Carta di Credito</h2>
        <div>
            <label for="cardNumber">Numero Carta:</label>
            <input type="text" id="cardNumber" name="cardNumber" required>
        </div>
        <div>
            <label for="cardHolder">Intestatario Carta:</label>
            <input type="text" id="cardHolder" name="cardHolder" required>
        </div>
        <div>
            <label for="expiryDate">Data di Scadenza (MM/YY):</label>
            <input type="month" id="expiryDate" name="expiryDate" required>
        </div>
        <div>
            <label for="cvv">CVV:</label>
            <input type="text" id="cvv" name="cvv" required>
        </div>
        <div>
            <input type="hidden" id="saveCard" name="saveCard" value="true">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="nextPage" value="CarteUtente.jsp">
            <div class="btn-add">
                <input type="submit" value="Salva carta">
            </div>
        </div>
    </form>
</div>
<div class="card-cata">
    <a href="ProductView.jsp">Torna al catalogo</a>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>
