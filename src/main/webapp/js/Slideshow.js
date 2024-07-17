function showSlides(className, slideIndex) {
    let slides = document.getElementsByClassName(className);
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.opacity = 0;
        slides[i].style.display = "none";
    }
    slideIndex++;
    if (slideIndex > slides.length) { slideIndex = 1 }
    slides[slideIndex - 1].style.display = "block";
    setTimeout(() => {
        slides[slideIndex - 1].style.opacity = 1;
    }, 50); // Applica l'opacità poco dopo aver mostrato la slide
    setTimeout(() => showSlides(className, slideIndex), 3000); // Cambia immagine ogni 3 secondi
}

document.addEventListener('DOMContentLoaded', () => {
    showSlides("mySlides pvc", 0);
    showSlides("mySlides wood", 0);
});
