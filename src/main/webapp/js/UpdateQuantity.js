// cart.js

function updateQuantity(productCode, quantity) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "ServletCarrello", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // Aggiorna il totale del carrello
            var response = JSON.parse(xhr.responseText);
            if (response.success) {
                document.getElementById("totalPrice").innerText = response.totalPrice + " €";
            } else {
                alert("Errore nell'aggiornamento della quantità");
            }
        }
    };

    xhr.send("action=updateQuantity&code=" + productCode + "&quantity=" + quantity);
}
