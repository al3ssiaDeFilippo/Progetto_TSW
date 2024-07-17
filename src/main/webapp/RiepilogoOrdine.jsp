<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="main.javas.util.Carrello" %>
<%@ page import="main.javas.bean.CartBean" %>
<%@ page import="main.javas.model.Order.CartModel" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.bean.ShippingBean" %>
<%@ page import="main.javas.bean.CreditCardBean" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="main.javas.util.GeneralUtils" %>

<%

    UserBean user = (UserBean) session.getAttribute("user");

    if (user == null) {
        // Se l'utente non è loggato, reindirizza alla pagina di login
        response.sendRedirect("LogIn.jsp");
        return;
    }

    CartModel model = new CartModel();

    ProductModelDS productModel = new ProductModelDS();

    Carrello carrello = (Carrello) session.getAttribute("cart");

    if (carrello == null) {
        carrello = new Carrello();
    }
    List<CartBean> arrayArticoli = carrello.getProdotti();

    GeneralUtils utils = new GeneralUtils();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Riepilogo Ordine</title>
    <link rel="stylesheet" type="text/css" href="css/RiepilogoOrdine.css">
</head>
<body>
<%@ include file="Header.jsp" %>
<h2>Riepilogo Ordine</h2>

<div class="container">
    <h2>Prodotti Acquistati</h2>
    <div class="order-table">
        <table class="order-table">
            <tr>
                <th>Immagine</th>
                <th>Nome Prodotto</th>
                <th>Quantità</th>
                <th>Prezzo</th>
                <th>IVA</th>
                <th>Sconto</th>
                <th>Dettagli</th>
            </tr>
            <% for (CartBean prodotto : arrayArticoli) {
                ProductBean product = productModel.doRetrieveByKey(prodotto.getProductCode());
                boolean hasDiscount = model.checkDiscount(prodotto);
            %>
            <tr>
                <td><img src="GetProductImageServlet?action=get&code=<%= product.getCode() %>&frame=<%= prodotto.getFrame() %>&frameColor=<%= prodotto.getFrameColor() %>" alt="Immagine attuale del prodotto"></td>
                <td><%= product.getProductName() %></td>
                <td><%= prodotto.getQuantity() %></td>
                <td>
                    <% if(!model.checkDiscount(prodotto)) { %>
                    <%= model.getProductTotalPrice(prodotto) %> €
                    <% } else if(model.getSingleProductDiscountedPrice(prodotto) != model.getProductTotalPrice(prodotto)) { %>
                    <del><%= model.getProductTotalPrice(prodotto) %> €</del> <span class="discount-price"><%= model.getSingleProductDiscountedPrice(prodotto) %>  </span> €
                    <% } %>
                </td>
                <td><%=product.getIva()%></td>
                <td><%=product.getDiscount()%></td>
                <td>
                    <div>
                        Cornice: <%=prodotto.getFrame()%>
                    </div>
                    <div>
                        Colore cornice: <%=prodotto.getFrameColor()%>
                    </div>
                    <div>
                        Dimensioni: <%=prodotto.getSize()%>
                    </div>
                </td>
            </tr>
            <% } %>
        </table>
    </div>
    <h2>Indirizzo di Spedizione</h2>
    <%
        ShippingBean selectedAddress = (ShippingBean) session.getAttribute("selectedAddress");
        if (selectedAddress != null) {
    %>
    <div class="address-detail">
        <p>Nome del Ricevente: <%= selectedAddress.getRecipientName() %></p>
        <p>Indirizzo: <%= selectedAddress.getAddress() %></p>
        <p>Città: <%= selectedAddress.getCity() %></p>
        <p>CAP: <%= selectedAddress.getCap() %></p>
    </div>
    <%
    } else {
    %>
    <p>Indirizzo di spedizione non disponibile</p>
    <%
        }
    %>

    <h2>Informazioni sulla Carta</h2>
    <%
        CreditCardBean selectedCard = (CreditCardBean) session.getAttribute("selectedCard");
        if (selectedCard != null) {
    %>
    <div class="card-detail">
        <p>Numero Carta: <%= utils.zippedIDcard(selectedCard.getIdCard()) %></p>
        <p>Nome Titolare: <%= selectedCard.getOwnerCard() %></p>
        <p>Data di Scadenza: <%= selectedCard.getExpirationDate() %></p>
    </div>
    <%
    } else {
    %>
    <p>Informazioni carta non disponibile</p>
    <%
        }
    %>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
    <p class="errorMessage"><%= errorMessage %></p>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/OrderServlet" method="post">
        <input type="submit" value="Ordina">
    </form>

</div>
<div class="shopping-link-container">
    <a href="ProductView.jsp" class="continue-shopping">Continua lo shopping</a>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>
