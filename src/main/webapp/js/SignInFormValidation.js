document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("registrationForm");

    const usernameInput = document.getElementById("username");
    const emailInput = document.getElementById("email");
    const surnameInput = document.getElementById("surname");
    const nameInput = document.getElementById("name");
    const birthdateInput = document.getElementById("birthdate");
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("confirmPassword");
    const telNumberInput = document.getElementById("telNumber");
    const confirmPasswordGroup = document.getElementById("confirmPasswordGroup");

    const inputFields = [
        { input: usernameInput, validator: validateUsername, errorField: "usernameError" },
        { input: emailInput, validator: validateEmail, errorField: "emailError" },
        { input: surnameInput, validator: validateSurname, errorField: "surnameError" },
        { input: nameInput, validator: validateName, errorField: "nameError" },
        { input: birthdateInput, validator: validateBirthDate, errorField: "BirthDateError" },
        { input: passwordInput, validator: validatePassword, errorField: "passwordError" },
        { input: telNumberInput, validator: validateTelNumber, errorField: "telNumberError" }
    ];

    inputFields.forEach(({ input, validator, errorField }) => {
        input.addEventListener("input", async function() {
            const value = input.value;
            clearError(errorField);

            const error = await validator(value);
            if (error) {
                showError(errorField, error);
                if (input === passwordInput) {
                    confirmPasswordGroup.style.display = 'none';
                    confirmPasswordInput.value = '';
                }
            } else {
                if (input === passwordInput) {
                    confirmPasswordGroup.style.display = 'block';
                }
            }
        });
    });

    confirmPasswordInput.addEventListener("input", function() {
        clearError("confirmPasswordError");
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;
        if (password !== confirmPassword) {
            showError("confirmPasswordError", "Le password non coincidono");
        }
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

        if (passwordInput.value !== confirmPasswordInput.value) {
            showError("confirmPasswordError", "Le password non coincidono");
            valid = false;
        }

        if (valid) {
            form.submit();
        }
    });

    function clearErrors() {
        const errorFields = ["surnameError", "nameError", "usernameError", "BirthDateError", "emailError", "passwordError", "telNumberError", "confirmPasswordError"];
        errorFields.forEach(clearError);
    }

    function clearError(errorField) {
        document.getElementById(errorField).textContent = "";
    }

    function showError(errorField, errorMessage) {
        document.getElementById(errorField).textContent = errorMessage;
    }

    async function validateUsername(username) {
        if (username.trim().length === 0) {
            return "Username non valido";
        }

        if (username.length > 30) {
            return "Superato limite massimo dei caratteri (30)";
        }

        try {
            const response = await checkUsernameAvailability(username);
            if (response.error) {
                return response.error;
            }
            if (!response.available) {
                return "Username già in uso";
            }
        } catch (error) {
            console.error('Errore durante la verifica dell\'username:', error);
            return "Errore durante la verifica dell'username";
        }

        return ""; // Nessun errore trovato
    }

    async function validateEmail(email) {
        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

        if (!emailPattern.test(email)) {
            return "Email non valida";
        }

        if (email.length > 50) {
            return "L'email deve essere di massimo 50 caratteri";
        }

        try {
            const response = await checkEmailAvailability(email);
            if (response.error) {
                return response.error;
            }
            if (!response.available) {
                return "Email già in uso";
            }
        } catch (error) {
            console.error('Errore durante la verifica dell\'email:', error);
            return "Errore durante la verifica dell'email";
        }

        return ""; // Nessun errore trovato
    }

    async function checkUsernameAvailability(username) {
        const response = await fetch(`CheckUsernameServlet?username=${encodeURIComponent(username)}`);
        return await response.json();
    }

    async function checkEmailAvailability(email) {
        const response = await fetch(`CheckEmailServlet?email=${encodeURIComponent(email)}`);
        return await response.json();
    }

    function validateSurname(surname) {
        if (surname.length === 0) {
            return "Cognome non valido";
        }

        if (surname.length > 20) {
            return "Superato limite massimo dei caratteri (20)";
        }

        return "";
    }

    function validateName(name) {
        if (name.length === 0) {
            return "Nome non valido";
        }

        if (name.length > 20) {
            return "Superato limite massimo dei caratteri (20)";
        }

        return "";
    }

    function validateBirthDate(birthdate) {
        const today = new Date();
        const birthDate = new Date(birthdate);

        if (isNaN(birthDate)) {
            return "Data di nascita non valida";
        }

        const age = today.getFullYear() - birthDate.getFullYear();
        const monthDifference = today.getMonth() - birthDate.getMonth();
        const dayDifference = today.getDate() - birthDate.getDate();

        if (monthDifference < 0 || (monthDifference === 0 && dayDifference < 0)) {
            if (age - 1 < 18) {
                return "Devi avere almeno 18 anni per registrarti";
            }
        } else {
            if (age < 18) {
                return "Devi avere almeno 18 anni per registrarti";
            }
        }

        return "";
    }

    function validatePassword(password) {
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

        // Valutazione aggiuntiva (es. password non può essere uguale all'username)
        if (password === document.getElementById("username").value) {
            return "La password non può essere uguale all'username";
        }

        // Controllo finale della forza della password
        if (strength < 3) {
            return "Password troppo debole";
        }

        return ""; // Nessun errore trovato
    }

    function validateTelNumber(telNumber) {
        const telNumberPattern = /^[0-9]{10}$/;
        if (!telNumberPattern.test(telNumber)) {
            return "Numero di telefono non valido";
        }

        return "";
    }
});
