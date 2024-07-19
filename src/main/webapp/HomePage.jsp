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
        .overlay {
            position: relative;
            width: 100%;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
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
        .content-box {
            flex: 1;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 45%; /* Adattato per stare sulla stessa riga */
            text-align: center;
            position: relative;
            height: 400px; /* Aggiungi un'altezza fissa per rendere i box quadrati */
        }
        .content-box.square {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
        .content-box.square img {
            width: 100%;
            height: auto;
            border-radius: 10px;
        }
        .content-box.square p {
            position: absolute;
            font-size: 2rem;
            font-weight: bold;
            color: white;
            text-align: center;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
        }
        .slideshow-container {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
        .slideshow {
            width: 100%;
            position: relative;
            overflow: hidden;
        }
        .slideshow img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 8px;
        }
        .slideshow-content {
            position: absolute;
            width: 100%;
            height: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .slideshow-content img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 8px;
        }
        .prev, .next {
            cursor: pointer;
            position: absolute;
            top: 50%;
            width: auto;
            margin-top: -22px;
            padding: 16px;
            color: white;
            font-weight: bold;
            font-size: 18px;
            transition: 0.6s ease;
            border-radius: 0 3px 3px 0;
            user-select: none;
        }
        .next {
            right: 0;
            border-radius: 3px 0 0 3px;
        }
        .prev:hover, .next:hover {
            background-color: rgba(0, 0, 0, 0.8);
        }
        .discount-box {
            padding: 20px;
            width: 100%;
            min-width: 300px;
            text-align: center;
            background-color: white;
            margin: 20px 0 0 0;
        }
        .slogan {
            font-size: 25px;
            font-weight: bold;
            color: black;
            text-align: center;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
            background-color: #f5f5f5;
            padding: 20px;
            border-radius: 10px;
            text-transform: uppercase;
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
</div>

<div class="slogan">
    <p>"I migliori poster per la tua casa"</p>
</div>

<!-- Due contenitori: uno per il box quadrato e uno per lo slideshow -->
<div class="content-section">
    <!-- Primo contenitore: box quadrato -->
    <div class="content-box square">
        <img src="Images/sconto2.jpg" alt="Sconto">
        <p>Info</p>
    </div>
    <!-- Secondo contenitore: slideshow -->
    <div class="content-box slideshow-container">
        <div class="slideshow">
            <%
                List<ProductBean> slideshowProducts = null;
                try {
                    slideshowProducts = (List<ProductBean>) productModel.getRandomProducts();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                for (ProductBean product : slideshowProducts) {
            %>
            <a href="<%= request.getContextPath() %>/RetrieveProductServlet?action=read&code=<%=product.getCode()%>">
                <div class="slideshow-content">
                    <img src="<%= request.getContextPath() %>/GetProductImageServlet?action=get&code=<%=product.getCode()%>" alt="<%= product.getProductName() %>">
                </div>
            </a>
            <%
                }
            %>
        </div>
        <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
        <a class="next" onclick="plusSlides(1)">&#10095;</a>
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

        var slideIndex = 0;
        showSlides();

        function showSlides() {
            var slides = document.getElementsByClassName("slideshow-content");
            for (var i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";
            }
            slideIndex++;
            if (slideIndex > slides.length) {slideIndex = 1}
            slides[slideIndex-1].style.display = "block"; // Cambia "flex" in "block"
            setTimeout(showSlides, 3000);
        }

        function plusSlides(n) {
            slideIndex += n;
            var slides = document.getElementsByClassName("slideshow-content");
            if (slideIndex > slides.length) {slideIndex = 1}
            if (slideIndex < 1) {slideIndex = slides.length}
            for (var i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";
            }
            slides[slideIndex-1].style.display = "block"; // Cambia "flex" in "block"
        }
    }
</script>
</body>
</html>
