<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Credit Card Flip</title>
    <link rel="stylesheet" href="css/CreditCardFlip.css"> <!-- Collega il CSS qui -->
    <link rel="stylesheet" href="css/CarteUtente.css"> <!-- Collega il CSS qui -->
    <script src="js/UserCreditCardFormValidation.js" defer></script> <!-- Link al JavaScript -->
</head>
<body>

<h2>Aggiungi Carta di Credito</h2> <!-- Titolo aggiunto -->

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
    <form id="creditCardForm">
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
            <input type="text" id="expiryDate" name="expiryDate" required>
            <span id="expiryDateError" class="error"></span>
        </div>
        <div>
            <label for="cvv">CVV:</label>
            <input type="text" id="cvv" name="cvv" required>
            <span id="cvvError" class="error"></span>
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
<script src="js/UserCreditCardFormValidation.js"></script>
</body>
</html>
