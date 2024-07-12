<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile Overview</title>
    <link rel="stylesheet" type="text/css" href="css/ProfileOverview.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="js/GifControl.js" defer></script>
</head>
<body>
<%@include file="Header.jsp" %>

    <div class="overview-container">

        <a href="Profilo.jsp">
            <div class="profile-box">
                <img class="myGif" src="Images/profile-gif-freezed.png" data-animated-gif="Images/profile-gif.gif" data-static-gif="Images/profile-gif-freezed.png" alt="Profile Image" class="profile-image" >
                <p class="box-text">Dettagli Profilo</p>
            </div>
        </a>

        <a href="Favorites.jsp">
        <div class="favorites-box">
            <img class="myGif" src="Images/fav-gif-freezed.png" data-animated-gif="Images/fav-gif.gif" data-static-gif="Images/fav-gif-freezed.png" alt="Favorites Image" class="favorites-image" >
            <p class="box-text">Preferiti</p>
        </div>
        </a>

        <a href="Info.jsp">
        <div class="info-box">
            <img class="myGif" src="Images/info-gif-freezed.png" data-animated-gif="Images/info-gif.gif" data-static-gif="Images/info-gif-freezed.png" alt="Info Image" class="info-image" >
            <p class="box-text">Informazioni</p>
        </div>
        </a>

        <a href="OrderHistory.jsp">
        <div class="order-box">
            <img class="myGif" src="Images/order-gif-freezed.png" data-animated-gif="Images/order-gif.gif" data-static-gif="Images/order-gif-freezed.png" alt="Order Image" class="order-image" >
            <p class="box-text">Storico Ordini</p>
        </div>
        </a>

        <a href="CarteUtente.jsp">
        <div class="card-box">
            <img class="myGif" src="Images/credit-card-gif-freezed.png" data-animated-gif="Images/credit-card-gif.gif" data-static-gif="Images/credit-card-gif-freezed.png" alt="Card Image" class="card-image" >
            <p class="box-text">Le Tue Carte</p>
        </div>
        </a>

        <a href="IndirizziUtente.jsp">
        <div class="address-box">
            <img class="myGif" src="Images/address-gif-freezed.png" data-animated-gif="Images/address-gif.gif" data-static-gif="Images/address-gif-freezed.png" alt="Address Image" class="address-image" >
            <p class="box-text">I Tuoi Indirizzi</p>
        </div>
        </a>

    </div>

<%@include file="Footer.jsp" %>
</body>
</html>
