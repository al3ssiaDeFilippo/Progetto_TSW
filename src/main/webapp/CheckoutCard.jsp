<%@ page import="main.javas.model.Order.CreditCardBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="main.javas.model.User.UserBean" %>
<%@ page import="main.javas.model.Order.CreditCardModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inserimento Carta</title>
</head>
<body>
<%
    UserBean user = (UserBean) session.getAttribute("user");
    String nextPage = (String) session.getAttribute("nextPage");
    CreditCardModel creditCardModel = new CreditCardModel();
    Collection<CreditCardBean> cards = creditCardModel.doRetrieveAll(user.getIdUser());
%>

<% if (!cards.isEmpty()) { %>
<% for (CreditCardBean card : cards) { %>
<div>
    <h2>Carta di Credito</h2>
    <p>Numero Carta: <%= card.getIdCard() %></p>
    <p>Nome Titolare: <%= card.getOwnerCard() %></p>
    <p>Data di Scadenza: <%= card.getExpirationDate() %></p>
    <form action="CreditCardServlet" method="post">
        <input type="hidden" name="action" value="select">
        <input type="hidden" name="selectedCard" value="<%= card.getIdCard() %>">
        <input type="submit" value="Seleziona carta">
    </form>
</div>
<% } %>
<% } %>
<h1>Inserimento nuova carta</h1>
<form action="CreditCardServlet" method="post">
    <input type="hidden" name="action" value="add">
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
    <div>
        <input type="hidden" name="nextPage" value="RiepilogoOrdine.jsp">
        <input type="submit" value="Prosegui al pagamento">
    </div>
</form>
</body>
</html>