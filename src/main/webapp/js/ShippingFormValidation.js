document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("addAddressForm");
    const recipientName = document.getElementById("recipientName");
    const address = document.getElementById("address");
    const city = document.getElementById("city");
    const cap = document.getElementById("cap");

    const inputFields = [
        { input: recipientName, errorField: "recipientNameError", validator: validateRecipientName },
        { input: address, errorField: "addressError", validator: validateAddress },
        { input: city, errorField: "cityError", validator: validateCity },
        { input: cap, errorField: "capError", validator: validateCap }
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
            form.submit();
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

    async function validateRecipientName(name) {
        if (name === "") {
            return "Campo obbligatorio";
        }

        if(name.length > 50) {
            return "Caratteri massimi consentiti: 50";
        }

        return "";
    }

    async function validateAddress(address) {
        if (address === "") {
            return "Campo obbligatorio";
        }

        if(address.length > 100) {
            return "Caratteri massimi consentiti: 100";
        }

        return "";
    }

    async function validateCity(city) {
        if (city === "") {
            return "Campo obbligatorio";
        }

        if(city.length > 50) {
            return "Caratteri massimi consentiti: 50";
        }

        return "";
    }

    async function validateCap(cap) {
        if (cap === "") {
            return "Campo obbligatorio";
        }

        if(cap.length !== 5 || !/^\d+$/.test(cap)) {
            return "Il CAP deve essere di 5 cifre";
        }

        return "";
    }
});