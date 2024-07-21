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
<div class="catalog-box" onclick="window.location.href='FilterProductServlet?filter=all'">
    <p>Visita il nostro catalogo</p>
    <img src="Gallery/Anime/TokyoG.jpg" alt="Catalog Image 1" class="catalog-img">
    <img src="Images/2.png" alt="Catalog Image 3" class="catalog-img">
    <img src="Gallery/Film/It.jpg" alt="Catalog Image 2" class="catalog-img">
</div>

<%@ include file="Footer.jsp" %>

<!-- Swiper JS -->
<script src="https://cdn.jsdelivr.net/npm/swiper/swiper-bundle.min.js"></script>
<script src="js/HomePageAnimation.js"></script>

</body>
</html>