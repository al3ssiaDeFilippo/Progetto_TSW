//funzione eseguita quando la pagina è completamente caricata
window.onload = function() {
    // Ottengo i riferimenti agli elementi
    var frameSelect = document.getElementById('frameSelect');
    var frameColorSelect = document.getElementById('frameColorSelect');
    var frameColorSelectContainer = document.getElementById('frameColorSelectContainer');
    var productImage = document.getElementById('productImage');

    // Nascondi o mostra il selettore del colore del frame in base al valore iniziale del frame
    if (frameSelect.value === 'no frame') {
        frameColorSelectContainer.style.display = 'none';
    } else {
        frameColorSelectContainer.style.display = 'inline';
    }

    // se il valore del frame è "no frame", viene nascosto il selettore del colore del frame
    frameSelect.onchange = function() {
        if (frameSelect.value === 'no frame') {
            frameColorSelectContainer.style.display = 'none';
        } else {
            frameColorSelectContainer.style.display = 'inline';
        }
        //in base alla selezione di frame, viene aggiornata l'immagine
        updateImage();
    };


    frameColorSelect.onchange = function() {
        updateImage();
    };

    // Funzione per aggiornare l'immagine del prodotto in base alla selezione dell'utente
    function updateImage() {
        // Ottieni i valori selezionati
        var frame = frameSelect.value;
        var frameColor = frameColorSelect.value;
        var productCode = productImage.getAttribute('data-product-code');
        var newImageSrc;
        //Costruisci l'URL dell'immagine del prodotto
        if (frame === 'no frame' || frameColor === 'selectAframeColor') {
            newImageSrc = "ProductImageServlet?action=get&code=" + productCode;
        } else {
            newImageSrc = "ProductImageServlet?action=get&frame=" + frame + "&frameColor=" + frameColor + "&code=" + productCode + "&custom=true";
        }

        // Aggiungi un parametro univoco all'URL per prevenire la memorizzazione nella cache
        newImageSrc += "&t=" + new Date().getTime();
        console.log(newImageSrc);
        productImage.src = newImageSrc;
    }

    // Chiamata alla funzione updateImage quando la pagina viene caricata
    updateImage();

    var submitButton = document.querySelector('input[type="submit"]');
    var frameSelect = document.getElementById('frameSelect');
    var frameColorSelect = document.getElementById('frameColorSelect');
    var sizeSelect = document.querySelector('select[name="size"]');

    // Funzione per controllare se il modulo è pronto per essere inviato
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

    // Aggiungi gli event listener per i cambiamenti nei selettori
    frameSelect.onchange = function() {
        if (frameSelect.value === 'no frame') {
            frameColorSelectContainer.style.display = 'none';
        } else {
            frameColorSelectContainer.style.display = 'inline';
        }
        updateImage();
        checkForm();
    };

    frameColorSelect.onchange = function() {
        checkForm();
    };

    sizeSelect.onchange = function() {
        checkForm();
    };

    // Disabilita il pulsante di invio all'inizio
    submitButton.disabled = true;

};

