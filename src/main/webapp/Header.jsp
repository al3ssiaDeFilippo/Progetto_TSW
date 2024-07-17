<%@ page import="main.javas.bean.UserBean" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/Header.css"> <!-- Percorso corretto al file CSS -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
    <title>Header Example</title>
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
        <form action="SearchServlet" method="get">
            <input type="search" name="q" placeholder="Cerca...">
            <button type="submit">
                <img class="src-img" src="Images/searchIcon.png" alt="Cerca">
            </button>
        </form>
    </div>

    <!-- Aggiunta dei bottoni -->
    <div class="button-container">
        <a href="#footer">
            <img class="footer-button" src="Images/infoIcon.png" alt="Image not found">
        </a>
        <a href="carrello.jsp">
            <img class="cart-button" src="Images/cartIcon.png" alt="Image not found">
        </a>
        <%
            // Recupera l'utente dalla sessione
            UserBean loggedInUser = (UserBean) session.getAttribute("user");
            if (loggedInUser != null) {
        %>
        <a href="ProfileOverview.jsp">
            <img class="login-button" src="Images/userIcon.png" alt="Image not found">
        </a>
        <%
        } else {
        %>
        <a href="LogIn.jsp">
            <img class="login-button" src="Images/userIcon.png" alt="Image not found">
        </a>
        <%
            }
        %>
    </div>
</header>

<!-- Sub Header -->
<header class="sub-header-container">
    <!-- Aggiunta della barra di navigazione -->
    <div class="nav-bar">
        <a class="film-button" href="Categories.jsp?category=Film">Film</a>
        <a class="serieTV-button" href="Categories.jsp?category=SerieTV">Serie TV</a>
        <a class="anime-button" href="Categories.jsp?category=Anime">Anime</a>
        <a class="fumetti-button" href="Categories.jsp?category=Fumetti">Fumetti</a>
        <a class="giochi-button" href="Categories.jsp?category=Giochi">Videogiochi</a>
    </div>
</header>

</body>
</html>
