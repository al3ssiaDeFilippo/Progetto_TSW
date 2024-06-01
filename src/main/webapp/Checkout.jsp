<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.model.ProductBean" %>
<%@ page import="main.javas.util.Carrello" %>
<%@ page import="main.javas.model.CartBean" %>
<%@ page import="main.javas.model.CartModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.javas.model.UserBean" %>

<!DOCTYPE html>
<html>
<head>
    <title>Checkout</title>
</head>
<body>
    <form action="CheckoutServlet" method="post">
        <input type="hidden" name="action" value="payment">
        <div>
            <label for="idCard">Numero Carta:</label>
            <input type="text" id="idCard" name="idCard" required>
        </div>
        <div>
            <label for="ownerCard">Intestatario Carta:</label>
            <input type="text" id="ownerCard" name="ownerCard" required>
        </div>
        <div>
            <label for="expirationDate">Data di Scadenza (YYYY-MM-DD):</label>
            <input type="text" id="expirationDate" name="expirationDate" required>
        </div>
        <div>
            <label for="cvv">CVV:</label>
            <input type="text" id="cvv" name="cvv" required>
        </div>
        <div>
            <input type="submit" value="Paga">
        </div>
    </form>
<!--
    <form action="ShippingServlet" method="post">
        <input type="hidden" name="action" value="shipping">
        <input type="hidden" id="idUser" name="idUser" value=" Inserisci qui l'ID utente se disponibile
        <h1>2. AGGIUNGERE UN FORM PER LO SHIPPING</h1>
        <div>
            <label for="recipientName">Nome e Cognome del Ricevente:</label>
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
            <label for="additionalDetails">Dettagli Aggiuntivi (Opzionale):</label>
            <textarea id="additionalDetails" name="additionalDetails"></textarea>
        </div>
        <div>
            <input type="submit" value="Spedisci">
        </div>
    </form>
-->
<a href="ProductView.jsp">Continua lo shopping</a>
<br>
<a href="RiepilogoOrdine.jsp">Acquista ora</a>

</body>
</html>
