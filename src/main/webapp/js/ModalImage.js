// ModalImage.js

document.addEventListener("DOMContentLoaded", function() {
    var modal = document.getElementById("myModal");
    var img = document.getElementById("productImage");
    var modalImg = document.getElementById("img01");
    var zoomed = false; // Flag per tenere traccia dello stato di zoom

    img.onclick = function() {
        modal.style.display = "block";
        modalImg.src = this.src; // Imposta l'URL dell'immagine originale nel tag img
        modalImg.style.transform = "scale(1)"; // Reset dello zoom
        zoomed = false; // Reset dello stato di zoom
    }

    modalImg.onclick = function() {
        if (!zoomed) {
            this.style.transform = "scale(1.5)"; // Zoom in
        } else {
            this.style.transform = "scale(1)"; // Reset dello zoom
        }
        zoomed = !zoomed; // Toggle dello stato di zoom
    }

    var span = document.getElementsByClassName("close")[0];

    span.onclick = function() {
        modal.style.display = "none";
    }

    modal.onclick = function(e) {
        if (e.target === modal) {
            modal.style.display = "none";
        }
    }

});
