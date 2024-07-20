document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("insertProductForm");
    const submitButton = document.getElementById("submitButton");
    const buttonText = document.getElementById("buttonText");
    const loadingGif = document.getElementById("loadingGif");

    const productName = document.getElementById("productName");
    const details = document.getElementById("details");
    const quantity = document.getElementById("quantity");
    const category = document.getElementById("category");
    const price = document.getElementById("price");
    const iva = document.getElementById("iva");
    const discount = document.getElementById("discount");
    const image = document.getElementById("photoPath");

    const inputFields = [
        { input: productName, errorField: "productNameError", validator: validateProductName },
        { input: details, errorField: "detailsError", validator: validateDetails },
        { input: quantity, errorField: "quantityError", validator: validateQuantity },
        { input: category, errorField: "categoryError", validator: validateCategory },
        { input: price, errorField: "priceError", validator: validatePrice },
        { input: iva, errorField: "ivaError", validator: validateIva },
        { input: discount, errorField: "discountError", validator: validateDiscount },
        { input: image, errorField: "photoPathError", validator: validateImage }
    ];

    inputFields.forEach(({ input, errorField, validator }) => {
        input.addEventListener("input", async function() {
            clearError(errorField);
            const value = input.value;
            const error = await validator(value);
            if (error) {
                showError(errorField, error);
            }
        });
    });

    form.addEventListener("submit", async function(event) {
        event.preventDefault();
        console.log("Submit button clicked");

        clearErrors();

        let valid = true;

        for (const { input, validator, errorField } of inputFields) {
            const value = input.value;
            const error = await validator(value);
            if (error) {
                showError(errorField, error);
                valid = false;
            }
        }

        if (valid) {
            // Nascondi il testo del pulsante e mostra la GIF
            submitButton.querySelector('img').style.display = 'inline'; // Mostra la GIF
            submitButton.disabled = true; // Disabilita il pulsante per prevenire ulteriori clic

            setTimeout(() => {
                form.submit();
            }, 3000); // Ritardo di 3 secondi
        }
    });

    function clearErrors() {
        document.querySelectorAll(".error").forEach(error => error.textContent = "");
    }

    function clearError(errorField) {
        document.getElementById(errorField).textContent = "";
    }

    function showError(errorField, error) {
        document.getElementById(errorField).textContent = error;
    }

    async function validateProductName(productName) {
        if (productName === "") {
            return "Campo obbligatorio";
        }

        if(productName.length > 50) {
            return "Caratteri massimi consentiti: 50";
        }

        return "";
    }

    async function validateDetails(details) {
        if (details === "") {
            return "Campo obbligatorio";
        }

        if(details.length > 500) {
            return "Caratteri massimi consentiti: 500";
        }

        return "";
    }

    async function validateQuantity(quantity) {
        if (quantity === "") {
            return "Campo obbligatorio";
        }

        if(quantity < 0) {
            return "Il valore deve essere maggiore di 0";
        }

        return "";
    }

    async function validateCategory(category) {
        if (category === "") {
            return "Campo obbligatorio";
        }

        return "";
    }

    async function validatePrice(price) {
        if (price === "") {
            return "Campo obbligatorio";
        }

        if(price < 0) {
            return "Il valore deve essere maggiore di 0";
        }

        return "";
    }

    async function validateIva(iva) {
        if (iva === "") {
            return "Campo obbligatorio";
        }

        if(iva < 0) {
            return "Il valore deve essere maggiore di 0";
        }

        return "";
    }

    async function validateDiscount(discount) {
        if (discount === "") {
            return "Campo obbligatorio";
        }

        if(discount < 0) {
            return "Il valore deve essere maggiore di 0";
        }

        return "";
    }

    async function validateImage(image) {
        if (image === "") {
            return "Campo obbligatorio";
        }

        return "";
    }
});
