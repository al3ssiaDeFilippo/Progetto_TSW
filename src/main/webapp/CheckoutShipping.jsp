<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
</head>
<body>

<form action="ShippingServlet" method="post">
    <input type="hidden" name="action" value="shipping">
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
        <input type="submit" value="Spedisci">
    </div>
</form>

<a href="ProductView.jsp">Continua lo shopping</a>
<br>
<a href="RiepilogoOrdine.jsp">Acquista ora</a>

</body>
</html>