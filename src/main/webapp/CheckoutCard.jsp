<%@ page import="main.javas.bean.CreditCardBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.model.Order.CreditCardModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inserimento Carta</title>
    <link rel="stylesheet" type="text/css" href="css/CheckoutCard.css">
    <script src="js/UserCreditCardFormValidation.js"></script>
</head>
<body>
<%@ include file="Header.jsp" %>
<%
    UserBean user = (UserBean) session.getAttribute("user");
    String nextPage = (String) request.getParameter("nextPage");
    CreditCardModel creditCardModel = new CreditCardModel();
    Collection<CreditCardBean> cards = creditCardModel.doRetrieveAll(user.getIdUser());
%>

<% if (!cards.isEmpty()) { %>
    <% for (CreditCardBean card : cards) { %>
<div class="card-detail p">
    <h2>Carta di Credito</h2>
    <p>Numero Carta: <%= card.getIdCard() %></p>
    <p>Nome Titolare: <%= card.getOwnerCard() %></p>
    <p>Data di Scadenza: <%= card.getExpirationDate() %></p>
    <form action="<%= request.getContextPath() %>/SelectCardServlet" method="post">
        <input type="hidden" name="selectedCard" value="<%= card.getIdCard() %>">
        <div class="btn-add">
            <input type="submit" value="Seleziona carta">
        </div>
    </form>
</div>
    <% } %>
<% } %>
<div class="card-detail">
    <form action="<%= request.getContextPath() %>/AddCardServlet" method="post">
        <h2>Inserimento nuova carta</h2>
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
            <input type="checkbox" id="saveCard" name="saveCard" value="true">
            <label for="saveCard">Salva Carta (autorizzo PosterWorld a salvare i dati della mia carta)</label>
        </div>
    </form>
</div>
<div class="card-cata">
    <a href="RiepilogoOrdine.jsp"> Riepilogo Ordine</a>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>