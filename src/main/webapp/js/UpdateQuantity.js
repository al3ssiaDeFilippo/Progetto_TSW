function updateQuantity(productCode, quantity) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "ServletCarrello", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var response = JSON.parse(xhr.responseText);
            if (response.success) {
                // Aggiorna il totale del carrello con sconto
                document.getElementById("totalPrice").innerText = response.totalPrice + " €";

                // Aggiorna anche il totale del carrello senza sconto, se presente
                var totalPriceWithoutDiscountElement = document.getElementById("totalPriceWithoutDiscount");
                if (totalPriceWithoutDiscountElement) {
                    totalPriceWithoutDiscountElement.innerText = response.totalPriceWithoutDiscount + " €";
                }
            } else {
                alert("Errore nell'aggiornamento della quantità");
            }
        }
    };

    xhr.send("action=updateQuantity&code=" + productCode + "&quantity=" + quantity);
}
