document.addEventListener('DOMContentLoaded', function() {
    var siteName = document.getElementById('siteName');
    var overlay = document.getElementById('overlay');

    // Assicurati che il CSS iniziale sia applicato prima di aggiungere la classe "small"
    siteName.classList.add('small'); // Avvia subito la transizione

    siteName.addEventListener('transitionend', function() {
        if (siteName.classList.contains('small')) {
            overlay.style.height = '120px';
        } else {
            overlay.style.height = '100%';
        }
    });

    // Rimuovi il setTimeout se vuoi che la transizione parta subito
    // setTimeout(function() {
    //     siteName.classList.add('small');
    //     document.body.style.overflow = 'auto';
    // }, 3000);
});

// Inizializza Swiper
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