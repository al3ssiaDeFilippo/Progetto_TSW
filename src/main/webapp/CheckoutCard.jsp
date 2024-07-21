<%@ page import="main.javas.bean.CreditCardBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="main.javas.bean.UserBean" %>
<%@ page import="main.javas.model.Order.CreditCardModel" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="main.javas.util.GeneralUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inserimento Carta di Credito</title>
    <link rel="stylesheet" href="css/CreditCardFlip.css"> <!-- Utilizza lo stesso CSS -->
    <script src="js/UserCreditCardFormValidation.js" defer></script> <!-- Link al JavaScript -->
</head>
<body>
<%@ include file="Header.jsp" %>

<div class="container">
    <!-- Container per le carte salvate -->
    <div class="saved-cards">
        <h2>Carte Salvate</h2>
        <%
            UserBean user = (UserBean) session.getAttribute("user");
            CreditCardModel creditCardModel = new CreditCardModel();
            Collection<CreditCardBean> cards = null;
            try {
                cards = creditCardModel.doRetrieveAll(user.getIdUser());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (cards != null && !cards.isEmpty()) {
                for (CreditCardBean card : cards) {
                    GeneralUtils utils = new GeneralUtils();
        %>
        <div class="saved-card">
            <div class="credit-card-wrapper">
                <div class="credit-card">
                    <div class="card-front">
                        <div class="card-logo">
                            <img src="Images/masterCardIcon.png" alt="Card Logo" class="card-logo-img">
                        </div>
                        <div class="card-number">
                            <%= utils.zippedIDcard(card.getIdCard()) %> <!-- Mostra solo le ultime 4 cifre -->                        </div>
                        <div class="card-details">
                            <div class="card-holder">
                                <%= card.getOwnerCard() %>
                            </div>
                            <div class="card-expiry">
                                <%= new SimpleDateFormat("MM/yy").format(card.getExpirationDate()) %>
                            </div>
                        </div>
                    </div>
                    <div class="card-back">
                        <div class="stripe"></div>
                        <div class="cvv-cont">
                            <div class="card-cvv">
                                ***
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="btn-add">
                <form action="<%= request.getContextPath() %>/SelectCardServlet" method="post">
                    <input type="hidden" name="cardId" value="<%= card.getIdCard() %>">
                    <input type="hidden" name="selectedCard" value="<%= card.getIdCard() %>">
                    <input type="submit" value="Seleziona">
                </form>

            </div>
        </div>
        <%
            }
        } else {
        %>
        <p>Non ci sono carte salvate.</p>
        <%
            }
        %>
    </div>

    <!-- Container per l'inserimento di una nuova carta -->
    <div class="new-card">
        <h2>Aggiungi Carta di Credito</h2>
        <div class="credit-card-wrapper">
            <div class="credit-card" id="creditCard">
                <div class="card-front">
                    <div class="card-logo">
                        <img src="Images/masterCardIcon.png" alt="Card Logo" class="card-logo-img">
                    </div>
                    <div class="card-number" id="cardNumberDisplay">
                        <!-- Numero carta -->
                    </div>
                    <div class="card-details">
                        <div class="card-holder" id="cardHolderDisplay">
                            <!-- Nome titolare -->
                        </div>
                        <div class="card-expiry" id="cardExpiryDisplay">
                            <!-- Data di scadenza -->
                        </div>
                    </div>
                </div>
                <div class="card-back">
                    <div class="stripe"></div>
                    <div class="cvv-cont">
                        <div class="card-cvv" id="cardCvvDisplay">
                            <!-- CVV -->
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modulo per inserire i dati della carta -->
        <div class="card-detail">
            <form action="<%= request.getContextPath() %>/AddCardServlet" method="post" id="creditCardForm">
                <h2>Inserisci una nuova Carta di Credito</h2>
                <div>
                    <label for="cardNumber">Numero Carta:</label>
                    <input type="text" id="cardNumber" name="cardNumber" required>
                    <span id="cardNumberError" class="error"></span>
                </div>
                <div>
                    <label for="cardHolder">Intestatario Carta:</label>
                    <input type="text" id="cardHolder" name="cardHolder" required>
                    <span id="cardHolderError" class="error"></span>
                </div>
                <div>
                    <label for="expiryDate">Data di Scadenza (MM/YY):</label>
                    <input type="month" id="expiryDate" name="expiryDate" required>
                    <span id="expiryDateError" class="error"></span>
                </div>
                <div>
                    <label for="cvv">CVV:</label>
                    <input type="text" id="cvv" name="cvv" required>
                    <span id="cvvError" class="error"></span>
                </div>
                <div>
                    <input type="checkbox" id="saveCard" name="saveCard" value="true">
                    <label for="saveCard">Salva Carta (autorizzo PosterWorld a salvare i dati della mia carta)</label>
                    <input type="hidden" id="nextPage" name="nextPage" value="RiepilogoOrdine.jsp">
                </div>
                <div class="btn-add">
                    <input type="submit" value="Procedi">
                </div>
            </form>
        </div>
    </div>
</div>

<style>

</style>

<%@ include file="Footer.jsp" %>
</body>
</html>
