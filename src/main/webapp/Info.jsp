<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Informazioni - PosterWorld</title>
    <link rel="stylesheet" type="text/css" href="css/Info.css">
</head>
<body>
<%@ include file="Header.jsp" %>

<div class="info-content">
    <h2>Chi Siamo</h2>
    <p>Benvenuti su PosterWorld, il tuo negozio online di fiducia per poster di alta qualità. Siamo specializzati in poster riguardanti fumetti, film, serie TV, videogiochi e manga. Il nostro obiettivo è offrire ai fan di tutto il mondo i migliori poster per decorare le loro case, uffici o qualsiasi altro spazio con i loro personaggi e scene preferite.</p>

    <h2>La Nostra Missione</h2>
    <p>La nostra missione è fornire prodotti di alta qualità che soddisfino le aspettative dei nostri clienti. Crediamo che ogni poster racconti una storia e porti gioia a chi lo guarda. Ecco perché ci impegniamo a selezionare con cura ogni prodotto che offriamo nel nostro negozio.</p>

    <h2>I Nostri Prodotti</h2>
    <p>Da PosterWorld troverai una vasta gamma di poster, tra cui:</p>
    <ul>
        <li>Fumetti: Poster dei tuoi eroi e villain preferiti dei fumetti.</li>
        <li>Film: Poster di classici del cinema e blockbuster moderni.</li>
        <li>Serie TV: Poster delle serie TV più amate e seguite.</li>
        <li>Videogiochi: Poster delle saghe di videogiochi più iconiche.</li>
        <li>Manga: Poster dei manga più popolari e di successo.</li>
    </ul>

    <h2>Il Nostro Team</h2>
    <div class="team-section">
        <div class="team-member">
            <img class="team-img" src="Images/team1.jpg" alt="Membro del Team 1">
            <h3>Barack Obama</h3>
            <p>CEO & Fondatore</p>
            <p>Barack è il fondatore di PosterWorld e ha una passione per i fumetti e i film. Con anni di esperienza nel settore, guida il nostro team verso l'eccellenza.</p>
        </div>
        <div class="team-member">
            <img class="team-img" src="Images/team2.jpg" alt="Membro del Team 2">
            <h3>Matteo Salvini</h3>
            <p>Direttore Creativo</p>
            <p>Matteo è il nostro direttore creativo, responsabile della selezione dei poster e della loro qualità. La sua creatività è fondamentale per il nostro successo.</p>
        </div>
    </div>

    <h2>Materiali di Qualità</h2>
    <div class="materials-section">
        <div class="material">
            <div class="slideshow-container pvc-slideshow">
                <div class="mySlides pvc fade">
                    <img src="Images/pvc-black-frame.jpg" class="material-img" alt="Cornice in PVC - Colore 1">
                </div>
                <div class="mySlides pvc fade">
                    <img src="Images/pvc-white-frame.jpg" class="material-img" alt="Cornice in PVC - Colore 2">
                </div>
                <div class="mySlides pvc fade">
                    <img src="Images/pvc-brown-frame.jpg" class="material-img" alt="Cornice in PVC - Colore 3">
                </div>
            </div>
            <h3>Cornici in PVC</h3>
            <p class="material-info">Le nostre cornici in PVC sono leggere, resistenti e offrono un look moderno che si adatta a qualsiasi tipo di arredamento. Ideali per chi cerca una soluzione pratica e duratura.</p>
        </div>
        <div class="material">
            <div class="slideshow-container wood-slideshow">
                <div class="mySlides wood fade">
                    <img src="Images/wood-black-frame.jpg" class="material-img" alt="Cornice in Legno - Colore 1">
                </div>
                <div class="mySlides wood fade">
                    <img src="Images/wood-white-frame.jpg" class="material-img" alt="Cornice in Legno - Colore 2">
                </div>
                <div class="mySlides wood fade">
                    <img src="Images/wood-brown-frame.jpg" class="material-img" alt="Cornice in Legno - Colore 3">
                </div>
            </div>
            <h3>Cornici in Legno</h3>
            <p class="material-info">Le nostre cornici in legno sono realizzate con materiali di alta qualità, offrendo un aspetto classico e raffinato. Perfette per chi desidera un tocco di eleganza e calore nel proprio spazio.</p>
        </div>
    </div>

    <h2>Contattaci</h2>
    <p>Se hai domande o hai bisogno di assistenza, non esitare a contattarci via email a <a class="contact-ref" href="mailto:yourposterworld@gmail.com">yourposterworld@gmail.com</a> o per telefono al numero <a class="contact-ref" href="tel:+39202347075">+3202347075</a>. Il nostro team di assistenza clienti è sempre pronto ad aiutarti.</p>
</div>

<%@ include file="Footer.jsp" %>
<script src="js/Slideshow.js"></script>
</body>
</html>
