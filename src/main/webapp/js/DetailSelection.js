document.addEventListener("DOMContentLoaded", function () {
    const frameSelect = document.getElementById("frameSelect");
    const frameColorSelect = document.getElementById("frameColorSelect");
    const frameColorSelectContainer = document.getElementById("frameColorSelectContainer");
    const productImage = document.getElementById("productImage");
    const sizeSelect = document.getElementById("sizeSelect");
    const errorMessage = document.getElementById("errorMessage");

    // Initially hide the frame color select container
    frameColorSelectContainer.style.display = 'none';

    function updateImage() {
        const frame = frameSelect.value;
        const frameColor = frameColorSelect.value;
        const productCode = productImage.getAttribute("data-product-code");
        let imageUrl = `GetProductImageServlet?action=get&code=${productCode}&custom=true`;

        if (frame !== "no frame") {
            imageUrl += `&frame=${frame}`;
            frameColorSelectContainer.style.display = ''; // Show frame color select when frame is selected
        } else {
            frameColorSelectContainer.style.display = 'none'; // Hide frame color select when 'no frame' is selected
        }

        if (frameColor !== "no color" && frameColor !== "selectAframeColor") {
            imageUrl += `&frameColor=${frameColor}`;
        }

        productImage.src = imageUrl;
    }

    function validateForm() {
        const frame = frameSelect.value;
        const frameColor = frameColorSelect.value;
        const size = sizeSelect.value;

        // Resetta il messaggio di errore
        errorMessage.textContent = "";
        errorMessage.style.display = 'none';

        // Controllo dimensione
        if (size === "selectAsize") {
            errorMessage.textContent = "Per favore, seleziona una dimensione.";
            errorMessage.style.display = 'block';
            return false;
        }

        // Controllo frame e colore del frame
        if (frame !== "no frame" && (frameColor === "selectAframeColor" || frameColor === "no color")) {
            errorMessage.textContent = "Seleziona un colore per la cornice.";
            errorMessage.style.display = 'block';
            return false;
        }

        return true; // Tutte le validazioni sono passate
    }

    function checkForm() {
        if(frameSelect.value === 'no frame') {
            frameColorSelect.value = 'no color';
            if(sizeSelect.value !== 'selectAsize') {
                submitButton.disabled = false;
            } else {
                submitButton.disabled = true;
            }
        } else {
            if(frameSelect.value !== 'no frame' && frameColorSelect.value !== 'selectAframeColor' && frameColorSelect.value !== 'no color' && sizeSelect.value !== 'selectAsize') {
                submitButton.disabled = false;
            } else {
                submitButton.disabled = true;
            }
        }
    }

    function hideErrorMessageIfFrameChanged() {
        if (frameSelect.value !== "wood" || frameColorSelect.value !== "selectAframeColor" && frameColorSelect.value !== "no color") {
            errorMessage.style.display = 'none'; // Nasconde il messaggio di errore
        }
    }

    frameSelect.addEventListener("change", function() {
        updateImage();
        checkForm(); // Richiama checkForm quando frameSelect cambia
        hideErrorMessageIfFrameChanged(); // Nasconde il messaggio di errore
    });
    frameColorSelect.addEventListener("change", function() {
        updateImage();
        checkForm(); // Richiama checkForm quando frameColorSelect cambia
    });
    sizeSelect.addEventListener("change", checkForm); // Richiama checkForm quando sizeSelect cambia

    // Esponi checkForm e validateForm a livello globale
    window.checkForm = checkForm;
    window.validateForm = validateForm;

    // Inizializza lo stato iniziale
    updateImage();
    checkForm();
});
