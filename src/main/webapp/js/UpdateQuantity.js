function updateQuantity(productCode, quantity) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "ServletCarrello", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4) {
            if (xhr.status == 200) {
                var response = JSON.parse(xhr.responseText);
                if (response.success) {
                    // Aggiorna il totale del carrello con sconto
                    var totalPriceElement = document.getElementById("totalPrice");
                    if (totalPriceElement) {
                        totalPriceElement.innerText = response.totalPrice + " €";
                    } else {
                        console.error("Element with ID 'totalPrice' not found");
                    }

                    // Aggiorna anche il totale del carrello senza sconto
                    var totalPriceWithoutDiscountElement = document.getElementById("totalPriceWithoutDiscount");
                    if (totalPriceWithoutDiscountElement) {
                        totalPriceWithoutDiscountElement.innerText = response.totalPriceWithoutDiscount + " €";
                    } else {
                        console.error("Element with ID 'totalPriceWithoutDiscount' not found");
                    }

                    // Aggiorna lo sconto totale
                    var totalDiscountElement = document.getElementById("totalDiscount");
                    if (totalDiscountElement) {
                        totalDiscountElement.innerText = "- " + response.totalDiscount + " €";
                    } else {
                        console.error("Element with ID 'totalDiscount' not found");
                    }

                    // Aggiorna il prezzo del singolo prodotto
                    var productPriceElement = document.querySelector('.product-price[data-code="' + productCode + '"]');
                    if (productPriceElement) {
                        var price = parseFloat(productPriceElement.getAttribute('data-price'));
                        var newProductPrice = price * quantity;
                        var hasDiscount = productPriceElement.getAttribute('data-discount') === 'true';
                        var discountedPrice = parseFloat(productPriceElement.getAttribute('data-discounted-price'));
                        var newDiscountedPrice = discountedPrice * quantity;

                        if (hasDiscount) {
                            productPriceElement.innerHTML = 'Prezzo: <del>' + newProductPrice.toFixed(2) + ' €</del> <span class="product-total-price">' + newDiscountedPrice.toFixed(2) + ' €</span>';
                        } else {
                            productPriceElement.innerHTML = 'Prezzo: <span class="product-total-price">' + newProductPrice.toFixed(2) + ' €</span>';
                        }
                    } else {
                        console.error("Element with class 'product-price' and data-code '" + productCode + "' not found");
                    }
                } else {
                    alert("Errore nell'aggiornamento della quantità");
                }
            } else {
                alert("Errore nella richiesta al server: " + xhr.status);
            }
        }
    };

    xhr.send("action=updateQuantity&code=" + productCode + "&quantity=" + quantity);
}
