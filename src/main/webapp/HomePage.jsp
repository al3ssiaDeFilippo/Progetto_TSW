<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="main.javas.bean.ProductBean" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/HomePage.css">
    <title>HomePage</title>
    <!-- Swiper CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.css">
    <style>
        /* Sfondo della pagina */
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f4f4f4; /* Colore di sfondo leggero per l'intera pagina */
            color: #333; /* Colore del testo principale */
        }

        .overlay {
            position: relative;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
            background: linear-gradient(135deg, #ffffff 0%, #f0f0f0 100%); /* Gradiente grigio chiaro */
            box-shadow: 0 10px 22px rgba(85, 81, 81, 0.1);
            transition: all 1s ease-in-out;
        }
        .site-name {
            font-size: 10vw;
            font-weight: 900;
            background: url('Images/world.jpg') no-repeat center center;
            background-size: cover;
            -webkit-background-clip: text;
            color: transparent;
            transition: all 1s ease-in-out;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 1000;
            text-align: center;
            filter: blur(0);
        }
        .site-name.small {
            position: absolute;
            font-size: 6rem;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            text-shadow:
                    3px 3px 3px rgba(0, 0, 0, 0.1),
                    6px 6px 6px rgba(0, 0, 0, 0.1),
                    9px 9px 9px rgba(0, 0, 0, 0.1);
        }
        .carousel-container {
            width: 100%;
            height: auto;
            overflow: hidden;
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 auto 20px;
            background-color: white;
        }
        .swiper-container {
            width: 100%;
            height: 100%;
        }
        .swiper-wrapper {
            display: flex;
            flex-direction: row;
            align-items: center;
        }
        .swiper-slide {
            flex: 0 0 auto;
            width: 25%;
            box-sizing: border-box;
            padding: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 10px;
        }
        .swiper-slide img {
            width: 100%;
            height: auto;
            border-radius: 8px;
            transition: box-shadow 0.3s ease-in-out;
        }
        .swiper-slide img:hover {
            box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
        }
        .swiper-button-next,
        .swiper-button-prev {
            display: none;
        }
        .swiper-pagination {
            display: none;
        }
        .discount-title {
            font-size: 3rem;
            font-weight: 700;
            text-align: center;
            margin: 10px;
            color: black;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
        }

        .content-section {
            display: flex;
            justify-content: space-between;
            gap: 20px;
            margin: 20px 0;
        }

        .content-section a {
            text-decoration: none;
        }

        /* Effetti specifici per il riquadro "About Us" */
        .content-box.square {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }

        .content-box.square p {
            margin: 0;
            padding: 0;
        }

        .content-box.square p:first-child {
            font-size: 2em;
            font-weight: bold;
            color: #37474f;
            margin-bottom: 10px;
        }

        .content-box.square p.sub-text {
            font-size: 1em;
            color: #607d8b;
        }

        /* Effetti di hover per i riquadri */
        .content-box {
            flex: 1;
            background-color: #ffffff;
            padding: 50px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: left;
            position: relative;
            height: 400px;
            font-size: 25px;
            font-weight: bold;
            align-content: center;
            transition: all 0.3s ease-in-out;
        }

        .content-box:hover {
            background-color: #f9f9f9; /* Cambia il colore di sfondo al passaggio del mouse */
            border-color: #b0bec5; /* Bordo blu-grigio chiaro al passaggio del mouse */
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2); /* Aumenta l'ombra per dare l'effetto di sollevamento */
            transform: scale(1.02); /* Leggero ingrandimento del box */
        }

        .discount-box {
            padding: 20px;
            width: 100%;
            min-width: 300px;
            text-align: center;
            margin: 20px 0 0 0;
            background-color: #ffffff; /* Colore di sfondo chiaro e caldo per la sezione sconto */
            border: 2px solid #cfd8dc; /* Bordi colorati per enfatizzare la sezione */
            border-radius: 8px; /* Angoli arrotondati */
        }
        .slogan {
            font-size: 70px;
            font-weight: bold;
            color: black;
            text-align: center;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            background-color: #f5f5f5;
            padding: 20px;
            border-radius: 10px;
            text-transform: uppercase;
        }
        .best-seller-title {
            font-size: 3rem;
            font-weight: 700;
            text-align: center;
            margin: 40px 0 20px;
            color: black;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
        }

        /* Effetti di hover per il box del catalogo */
        .catalog-box {
            padding: 20px;
            width: 100%;
            text-align: center;
            background-color: #ffffff; /* Colore di sfondo bianco */
            margin: 40px 0;
            cursor: pointer;
            transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
            border: 1px solid #e0e0e0; /* Bordo grigio chiaro */
        }

        .catalog-box:hover {
            background-color: #f5f5f5; /* Colore di sfondo grigio più chiaro al passaggio del mouse */
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            transform: scale(1.05);
        }

        .catalog-box:hover {
            transform: scale(1.05);
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        }

        .catalog-box p {
            font-size: 2rem;
            font-weight: 700;
            margin-bottom: 20px;
            color: #37474f; /* Colore verde scuro per il testo */
        }

        .catalog-img {
            width: 30%;
            height: auto;
            border-radius: 8px;
            margin: 0 10px;
            transition: box-shadow 0.3s ease-in-out;
        }

        .catalog-img:hover {
            box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
        }
    </style>
</head>
<body>
<%@ include file="Header.jsp" %>

<div class="overlay" id="overlay">
    <div class="site-name" id="siteName">PosterWorld</div>
</div>

<!-- Unified discount section -->
<div class="discount-section">
    <!-- Scritta "Sconti" stilizzata -->
    <div class="discount-box">
        <div class="discount-title">Prodotti in sconto</div>
    </div>
    <!-- Contenitore per il carosello dei prodotti scontati -->
    <div class="carousel-container">
        <!-- Swiper -->
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <%
                    ProductModelDS productModel = new ProductModelDS();
                    List<ProductBean> discountedProducts = null;
                    try {
                        discountedProducts = (List<ProductBean>) productModel.getRandomProducts();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    for (ProductBean product : discountedProducts) {
                %>
                <div class="swiper-slide">
                    <a href="<%= request.getContextPath() %>/RetrieveProductServlet?action=read&code=<%=product.getCode()%>">
                        <img src="<%= request.getContextPath() %>/GetProductImageServlet?action=get&code=<%=product.getCode()%>" alt="<%= product.getProductName() %>">
                    </a>
                </div>
                <%
                    }
                %>
            </div>
            <!-- Pagination -->
            <div class="swiper-pagination"></div>
            <!-- Navigation -->
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>
        </div>
    </div>
</div>

<div class="slogan">
    <p>"Poster<br>Your<br>World"</p>
</div>

<!-- Due contenitori: uno per il box quadrato e uno per lo slideshow -->
<div class="content-section">
    <!-- Primo contenitore: box quadrato -->
    <a href="Info.jsp">
        <div class="content-box square">
            <p>About Us</p>
            <p class="sub-text">Scopri chi siamo e cosa facciamo per offrirti i migliori poster.</p>
        </div>
    </a>
    <!-- Secondo contenitore: breve recap del sito -->
    <div class="content-box">
        <p>Benvenuti su PosterWorld!</p>
        <p class="sub-text">Esplora la nostra vasta collezione di poster esclusivi per ogni gusto e stile. Offriamo una selezione accurata dei migliori poster per decorare la tua casa e il tuo ufficio. Trova il pezzo perfetto che aggiungerà un tocco speciale ai tuoi spazi.</p>
    </div>
</div>


<!-- Sezione Best Seller -->
<div class="best-seller-section">
    <div class="best-seller-title">Best Seller</div>
    <div class="carousel-container">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <%
                    List<ProductBean> bestSellerProducts = null;
                    try {
                        bestSellerProducts = (List<ProductBean>) productModel.getRandomProducts();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    for (ProductBean product : bestSellerProducts) {
                %>
                <div class="swiper-slide">
                    <a href="<%= request.getContextPath() %>/RetrieveProductServlet?action=read&code=<%=product.getCode()%>">
                        <img src="<%= request.getContextPath() %>/GetProductImageServlet?action=get&code=<%=product.getCode()%>" alt="<%= product.getProductName() %>">
                    </a>
                </div>
                <%
                    }
                %>
            </div>
            <!-- Pagination -->
            <div class="swiper-pagination"></div>
            <!-- Navigation -->
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>
        </div>
    </div>
</div>

<!-- Riquadro del catalogo -->
<div class="catalog-box" onclick="window.location.href='ProductView.jsp'">
    <p>Visita il nostro catalogo</p>
    <img src="Images/1.png" alt="Catalog Image 1" class="catalog-img">
    <img src="Images/2.png" alt="Catalog Image 2" class="catalog-img">
    <img src="Images/3.png" alt="Catalog Image 3" class="catalog-img">
</div>

<%@ include file="Footer.jsp" %>

<!-- Swiper JS -->
<script src="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.js"></script>
<script>
    window.onload = function() {
        var siteName = document.getElementById('siteName');
        var overlay = document.getElementById('overlay');

        siteName.addEventListener('transitionend', function() {
            if (siteName.classList.contains('small')) {
                overlay.style.height = '120px';
            } else {
                overlay.style.height = '100%';
            }
        });

        setTimeout(function() {
            siteName.classList.add('small');
            document.body.style.overflow = 'auto';
        }, 3000);

        var swiper = new Swiper('.swiper-container', {
            direction: 'horizontal',
            slidesPerView: 6,
            spaceBetween: 10,
            loop: true,
            autoplay: {
                delay: 3000,
                disableOnInteraction: false,
            },
            pagination: {
                el: '.swiper-pagination',
                clickable: true,
            },
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
        });
    }
</script>
</body>
</html>
