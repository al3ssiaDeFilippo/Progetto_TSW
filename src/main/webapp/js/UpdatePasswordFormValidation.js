document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("updatePasswordForm");
    const oldPassword = document.getElementById("oldPassword");
    const newPassword = document.getElementById("newPassword");
    const confirmPassword = document.getElementById("confirmPassword");

    const inputFields = [
        { input: newPassword, errorField: "newPasswordError", validator: validatePassword }
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

    oldPassword.addEventListener("input", async function() {
        clearError("oldPasswordError");
        const value = oldPassword.value;
        console.log("Old Password Input: " + value);
        const error = await validateOldPassword(value);
        if (error) {
            showError("oldPasswordError", error);
        }
    });

    confirmPassword.addEventListener("input", function() {
        clearError("confirmPasswordError");
        if (newPassword.value !== confirmPassword.value) {
            showError("confirmPasswordError", "Le password non coincidono");
        }
    });

    form.addEventListener("submit", async function(event) {
        event.preventDefault();
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

        const oldPasswordError = await validateOldPassword(oldPassword.value);
        if (oldPasswordError) {
            showError("oldPasswordError", oldPasswordError);
            valid = false;
        }

        if (newPassword.value !== confirmPassword.value) {
            showError("confirmPasswordError", "Le password non coincidono");
            valid = false;
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

    async function validatePassword(password) {
        const strengthBar = document.getElementById("strengthBar");
        const passwordLength = password.length;
        let strength = 0;

        // Valutazione della forza della password
        if (passwordLength >= 5) {
            strength += 1;
        }

        if (/[a-z]/.test(password) && /[A-Z]/.test(password)) {
            strength += 1;
        }

        if (/[a-z]/.test(password) && /[A-Z]/.test(password) && /[0-9]/.test(password) && /[!@#$%^&*]/.test(password) && passwordLength >= 6 && passwordLength <= 9) {
            strength += 1;
        }

        if (/[!@#$%^&*]/.test(password) && passwordLength >= 10 && /[0-9]/.test(password) && /[!@#$%^&*]/.test(password) && /[a-z]/.test(password) && /[A-Z]/.test(password)) {
            strength += 1;
        }

        // Assegnazione della larghezza in base alla forza
        switch (strength) {
            case 0:
            case 1:
                strengthBar.style.width = "33%"; // Larghezza per password debole
                strengthBar.className = "strength-bar weak";
                break;
            case 2:
                strengthBar.style.width = "66%"; // Larghezza per password normale
                strengthBar.className = "strength-bar normal";
                break;
            case 3:
            case 4:
                strengthBar.style.width = "100%"; // Larghezza per password forte
                strengthBar.className = "strength-bar strong";
                break;
            default:
                break;
        }

        // Controllo finale della forza della password
        if (strength < 3) {
            return "Password troppo debole";
        }

        return ""; // Nessun errore trovato
    }

    async function validateOldPassword(oldPassword) {
        try {
            const response = await fetch('/CheckOldPasswordServlet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: `oldPassword=${encodeURIComponent(oldPassword)}`
            });

            if (response.ok) {
                const result = await response.json();
                console.log("Old Password Validation Result: ", result);
                return result.error;
            } else {
                console.error('Error checking old password:', response.status, response.statusText);
                return 'Errore nella verifica della vecchia password';
            }
        } catch (error) {
            console.error('Fetch error:', error);
            return 'Errore nella verifica della vecchia password';
        }
    }
});
