<%@ page import="main.javas.bean.UserBean" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <link rel="stylesheet" type="text/css" href="css/Header.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700&display=swap">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <title>Header Example</title>
    <style>
        /* Ensure the suggestion box is as wide as the search bar */
        .ui-autocomplete {
            max-width: 100%;
            box-sizing: border-box; /* Ensures padding is included in the element's total width and height */
        }
    </style>
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
        <form id="searchForm" action="SearchServlet" method="get">
            <input type="search" name="q" id="searchBar" placeholder="Cerca...">
            <button type="submit">
                <img class="src-img" src="Images/searchIcon.png" alt="Cerca">
            </button>
        </form>
    </div>

    <!-- Aggiunta dei bottoni -->
    <div class="button-container">
        <a href="#footer">
            <img class="footer-button header-icon" src="Images/infoIcon.png" alt="Image not found">
        </a>
        <a href="carrello.jsp">
            <img class="cart-button header-icon" src="Images/cartIcon.png" alt="Image not found">
        </a>
        <div class="profile-menu-container">
            <a href="#" class="profile-icon">
                <img class="login-button header-icon" src="Images/userIcon.png" alt="Image not found">
            </a>
            <div class="profile-dropdown-menu">
                <%
                    // Recupera l'utente dalla sessione
                    UserBean loggedInUser = (UserBean) session.getAttribute("user");
                    if (loggedInUser != null) {
                %>
                <a href="ProfileOverview.jsp" class="link-like">Il tuo Profilo</a>
                <form action="<%= request.getContextPath() %>/LogOutServlet" method="post" class="logout-button link-like">
                    <input type="submit" value="Logout">
                </form>
                <%
                } else {
                %>
                <a href="LogIn.jsp" class="link-like">Accedi</a>
                <%
                    }
                %>
            </div>
        </div>
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

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
<script>
    $(function() {
        $("#searchBar").autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "SearchServlet?action=autocomplete",
                    type: "GET",
                    data: {
                        term: request.term
                    },
                    success: function(data) {
                        response(data);
                    }
                });
            },
            minLength: 1,
            select: function(event, ui) {
                $("#searchBar").val(ui.item.label);
                $("#searchForm").submit(); // Invia il form quando viene selezionato un suggerimento
                return false;
            },
            open: function() {
                var $input = $(this),
                    $autocomplete = $input.autocomplete("widget"),
                    inputWidth = $input.outerWidth();

                $autocomplete.css("width", inputWidth + "px");
            }
        });
    });
</script>

</body>

<style>
    /* Header.css */

    .logout-button input[type="submit"] {
        background-color: red;
        color: white;
        border: none;
        padding: 10px 20px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
        border-radius: 4px;
    }

    .button-container {
        text-align: center;
    }

    .profile-menu-container {
        position: relative;
        display: inline-block;
    }

    .profile-icon {
        display: block;
    }

    .profile-dropdown-menu {
        display: none;
        position: absolute;
        top: 100%;
        right: 0;
        background-color: #fff;
        border: 1px solid #ddd;
        box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
        z-index: 999999; /* Increase this value as needed */
        min-width: 160px;
    }

    .profile-dropdown-menu a {
        color: black;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
    }

    .profile-dropdown-menu a:hover {
        background-color: #ddd;
    }

    /* Mostra il menu a tendina quando si passa sopra l'icona del profilo */
    .profile-menu-container:hover .profile-dropdown-menu {
        display: block;
    }


</style>
</html>
