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
<h1>Checkout Ordine</h1>

<h1>1. AGGIUNGERE UN FORM PER IL PAGAMENTO: intestatario carta, cvv, numero carta (chiave primaria), scadenza</h1>
<h1>2. AGGIUNGERE UN FORM PER LO SHIPPING: nome e cognome del ricevente, città, cap, indirizzo, dettagli aggiuntivi (opzionale) + non so se mi sto dimenticando qualcosa </h1>


<a href="ProductView.jsp">Continua lo shopping</a>
<br>
<a href="RiepilogoOrdine.jsp">Acquista ora</a>

</body>
</html>
