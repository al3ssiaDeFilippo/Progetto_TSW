<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="main.javas.model.Product.ProductModelDS" %>
<%@ page import="main.javas.bean.ProductBean" %>
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

        .overlay {
            position: relative;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center; /* Allinea i figli orizzontalmente al centro */
            align-items: center; /* Allinea i figli verticalmente al centro */
            z-index: 1000;
            background: linear-gradient(135deg, #ffffff 100%, #cac6c6 0%, #ffffff 100%);
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
            top: 50%; /* Posiziona il testo al centro dell'overlay */
            left: 50%;
            transform: translate(-50%, -50%); /* Aggiorna la trasformazione per allineare il testo al centro */
            text-shadow:
                    3px 3px 3px rgba(0, 0, 0, 0.1),
                    6px 6px 6px rgba(0, 0, 0, 0.1),
                    9px 9px 9px rgba(0, 0, 0, 0.1); /* Ombra più 3D */
        }


        /* Stili per il carosello dei prodotti scontati */
        .carousel-container {
            margin: 70px auto; /* Riduci il margine superiore */
            width: 100%;
            height: auto; /* Altezza del carosello */
            overflow: hidden; /* Nasconde le immagini che escono dal contenitore */
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
            box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
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
            width: 25%; /* Mostra 4 prodotti per riga */
            box-sizing: border-box;
            padding: 10px;
            display: flex; /* Centra il contenuto all'interno della slide */
            justify-content: center; /* Centra il contenuto orizzontalmente */
            align-items: center; /* Centra il contenuto verticalmente */
            margin: 10px; /* Aggiungi un margine attorno a ogni prodotto */
        }
        .swiper-slide img {
            width: 100%;
            height: auto;
            border-radius: 8px;
            transition: box-shadow 0.3s ease-in-out; /* Aggiungi una transizione all'ombra */
        }

        .swiper-slide img:hover {
            box-shadow: 5px 5px 15px rgba(0, 0, 0, 0.3);
        }

        /* Nascondi le frecce */
        .swiper-button-next,
        .swiper-button-prev {
            display: none;
        }

        /* Nascondi i puntini di paginazione */
        .swiper-pagination {
            display: none;
        }
    </style>
</head>
<body>
<%@ include file="Header.jsp" %>

<div class="overlay" id="overlay">
    <div class="site-name" id="siteName">PosterWorld</div>
</div>

<!-- Contenitore per il carosello dei prodotti scontati -->
<div class="carousel-container">
    <!-- Swiper -->
    <div class="swiper-container">
        <div class="swiper-wrapper">
            <%
                ProductModelDS productModel = new ProductModelDS();
                List<ProductBean> discountedProducts = (List<ProductBean>) productModel.getRandomProducts();

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

<%@ include file="Footer.jsp" %>

<!-- Swiper JS -->
<script src="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.js"></script>
<script>
    window.onload = function() {
        var siteName = document.getElementById('siteName');
        var overlay = document.getElementById('overlay');

        siteName.addEventListener('transitionend', function() {
            if (siteName.classList.contains('small')) {
                overlay.style.height = '120px'; // Riduci l'altezza dell'overlay
            } else {
                overlay.style.height = '100%'; // Ripristina l'altezza dell'overlay
            }
        });

        setTimeout(function() {
            siteName.classList.add('small');
            document.body.style.overflow = 'auto'; // Restore scrolling
        }, 3000); // Adjust the time as needed

        // Initialize Swiper
        var swiper = new Swiper('.swiper-container', {
            direction: 'horizontal', // Configura Swiper per scorrere orizzontalmente
            slidesPerView: 6, // Numero di prodotti visibili per riga
            spaceBetween: 10, // Spazio tra le immagini
            loop: true, // Loop continuo
            autoplay: {
                delay: 3000, // Tempo tra le transizioni
                disableOnInteraction: false, // Non disabilitare l'autoplay al passaggio del mouse
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