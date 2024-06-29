window.onload = function() {
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

    frameSelect.onchange = function() {
        if (frameSelect.value === 'no frame') {
            frameColorSelectContainer.style.display = 'none';
        } else {
            frameColorSelectContainer.style.display = 'inline';
        }
        updateImage();
    };

    frameColorSelect.onchange = function() {
        updateImage();
    };

    function updateImage() {
        var frame = frameSelect.value;
        var frameColor = frameColorSelect.value;
        var productCode = productImage.getAttribute('data-product-code');
        var newImageSrc;
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

    function checkForm() {
        if(frameSelect.value === 'no frame') {
            frameColorSelect.value = 'no color';
            if(sizeSelect.value !== 'selectAsize') {
                submitButton.disabled = false;
            } else {
                submitButton.disabled = true;
            }
        } else {
            if(frameSelect.value !== 'no frame' && frameColorSelect.value !== 'selectAframeColor' && sizeSelect.value !== 'selectAsize') {
                submitButton.disabled = false;
            } else {
                submitButton.disabled = true;
            }
        }
    }

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

