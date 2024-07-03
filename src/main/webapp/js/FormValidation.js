document.addEventListener('DOMContentLoaded', function() {
    let dialog = document.getElementById('dialog');
    let confirmButton = document.getElementById('confirmButton');
    let cancelButton = document.getElementById('cancelButton');
    let modifyButton = document.getElementById('modifyButton');

    // Aggiungi un event listener per il click sul pulsante "Modifica Prodotto"
    modifyButton.addEventListener('click', function() {
        dialog.style.display = 'block';
    });

    // Aggiungi un event listener per il click sul pulsante "Conferma"
    confirmButton.addEventListener('click', function() {
        // Chiudi la casella di dialogo
        dialog.style.display = 'none';

        // Esegui la validazione del form
        if (validateForm()) {
            // Invia il form se è valido
            document.querySelector('form[action="ProductControl"]').submit();
        } else {
            console.log("Validazione del form fallita.");
        }
    });

    // Aggiungi un event listener per il click sul pulsante "Annulla"
    cancelButton.addEventListener('click', function() {
        // Chiudi la casella di dialogo
        dialog.style.display = 'none';
        console.log("Modifica annullata.");
    });

    // Funzione per la validazione del form
    function validateForm() {
        let productName = document.getElementById('productName').value.trim();
        let details = document.getElementById('details').value.trim();
        let quantity = document.getElementById('quantity').value.trim();
        let category = document.getElementById('category').value;
        let price = document.getElementById('price').value.trim();
        let iva = document.getElementById('iva').value.trim();
        let discount = document.getElementById('discount').value.trim();
        let photoPath = document.getElementById('photoPath').value.trim();

        // Esempio di validazione semplice: controlla se i campi obbligatori non sono vuoti
        if (productName === '' || details === '' || quantity === '' || category === 'selectACategory' || price === '' || iva === '' || discount === '') {
            alert("Assicurati di compilare tutti i campi obbligatori.");
            return false;
        }

        // Altre regole di validazione possono essere aggiunte a seconda delle necessità

        return true;
    }
});
