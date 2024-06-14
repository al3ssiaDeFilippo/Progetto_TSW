<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="main.javas.model.UserBean" %>
<%@ page import="main.javas.model.CreditCardModel" %>
<%@ page import="main.javas.model.CreditCardBean" %>
<%@ page import="java.util.Collection" %>

<%
    UserBean user = (UserBean) session.getAttribute("user");
    String nextPage = (String) session.getAttribute("nextPage");
    CreditCardModel creditCardModel = new CreditCardModel();
    Collection<CreditCardBean> cards = creditCardModel.doRetrieveAll(user.getIdUser());
%>
<html>
<head>
    <title>Carte di Credito</title>
</head>
<body>
    <% if (!cards.isEmpty()) { %>
        <% for (CreditCardBean card : cards) { %>
        <div>
            <h2>Carta di Credito</h2>
            <p>Numero Carta: <%= card.getIdCard() %></p>
            <p>Nome Titolare: <%= card.getOwnerCard() %></p>
            <p>Data di Scadenza: <%= card.getExpirationDate() %></p>
            <form action="CreditCardServlet" method="post">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="cardId" value="<%= card.getIdCard() %>">
                <input type="submit" value="Elimina">
            </form>
        </div>
        <% } %>
    <% } %>
    <h2>Inserisci una nuova Carta di Credito</h2>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>Inserimento Carta</title>
    </head>
    <body>
    <form action="CreditCardServlet" method="post">
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
        <div>
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="nextPage" value="CarteUtente.jsp">
            <input type="submit" value="Salva carta">
        </div>
    </form>

    <div>
        <a href="ProductView.jsp">Torna al catalogo</a>
    </div>
    </body>
    </html>
</body>
</html>