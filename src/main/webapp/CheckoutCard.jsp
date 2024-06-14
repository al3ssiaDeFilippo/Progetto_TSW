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
        <input type="checkbox" id="saveCard" name="saveCard" value="true">
        <label for="saveCard">Salva Carta (autorizzo PosterWorld a salvare i dati della mia carta)</label>
    </div>
    <div>
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="nextPage" value="RiepilogoOrdine.jsp">
        <input type="submit" value="Prosegui al pagamento">
    </div>
</form>
</body>
</html>