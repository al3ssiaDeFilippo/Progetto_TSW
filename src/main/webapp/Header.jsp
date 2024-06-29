<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/Header.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
</head>
<body>
<!-- Header -->
<header class="header-container">

    <!-- Aggiunta del logo -->
    <a href="HomePage.jsp" class="logo-link">
        <img class="logo-img" src="Images/PosterWorldLogo.png" alt="Image not found">
    </a>

    <!-- Aggiunta della barra di ricerca -->
    <div class="search-container">
        <form action="/search" method="get">
            <input type="search" name="q" placeholder="Cerca...">

            <!-- Aggiunta del bottone di ricerca -->
            <button type="submit">
                <img class="src-img" src="Images/searchIcon.png" alt="Image not found">
            </button>
        </form>
    </div>

    <!-- Aggiunta dei bottoni -->
    <div class="button-container">
        <a href="Footer.jsp">
            <img class="footer-button" src="Images/infoIcon.png" alt="Image not found">
        </a>
        <a href="carrello.jsp">
            <img class="cart-button" src="Images/cartIcon.png" alt="Image not found">
        </a>
        <a href="LogIn.jsp">
            <img class="login-button" src="Images/userIcon.png" alt="Image not found">
        </a>
    </div>

</header>

<header class="sub-header-container">

    <!-- Aggiunta della barra di navigazione -->
    <div class="nav-bar">
        <div class="dropdown">
            <a class="film-button" href="Film.jsp">Film</a>
            <div class="dropdown-content">
                <a href="#">Sottocategoria 1</a>
                <a href="#">Sottocategoria 2</a>
                <a href="#">Other</a>
            </div>
        </div>
        <div class="dropdown">
            <a class="serieTV-button" href="SerieTV.jsp">Serie TV</a>
            <div class="dropdown-content">
                <a href="#">Best from Netflix</a>
                <a href="#">Marvel</a>
                <a href="#">Other</a>
            </div>
        </div>
        <div class="dropdown">
            <a class="anime-button" href="Anime.jsp">Anime</a>
            <div class="dropdown-content">
                <a href="#">Sottocategoria 1</a>
                <a href="#">Sottocategoria 2</a>
                <a href="#">Other</a>
            </div>
        </div>
        <div class="dropdown">
            <a class="fumetti-button" href="Fumetti.jsp">Fumetti</a>
            <div class="dropdown-content">
                <a href="#">Marvel</a>
                <a href="#">Sottocategoria 2</a>
                <a href="#">Other</a>
            </div>
        </div>
        <div class="dropdown">
            <a class="giochi-button" href="Giochi.jsp">Videogiochi</a>
            <div class="dropdown-content">
                <a href="#">Sottocategoria 1</a>
                <a href="#">Sottocategoria 2</a>
                <a href="#">Other</a>
            </div>
        </div>
    </div>
</header>

</body>
</html>
