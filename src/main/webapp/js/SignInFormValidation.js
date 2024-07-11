document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("registrationForm");

    const usernameInput = document.getElementById("username");

    // Aggiungi un event listener per l'evento change sull'input dell'username
    usernameInput.addEventListener("change", async function() {
        const username = usernameInput.value;
        clearUsernameError();

        if (username.trim().length === 0) {
            showUsernameError("L'username deve essere di almeno un carattere");
            return;
        }

        if (username.length > 30) {
            showUsernameError("L'username deve essere di massimo 30 caratteri");
            return;
        }

        try {
            const response = await checkUsernameAvailability(username);
            if (!response.available) {
                showUsernameError("L'username è già in uso");
            }
        } catch (error) {
            console.error('Errore durante la verifica dell\'username:', error);
            showUsernameError("Errore durante la verifica dell'username");
        }
    });

    form.addEventListener("submit", async function(event) {
        event.preventDefault();
        console.log("Submit button clicked");

        clearErrors();

        let valid = true;

        const surname = document.getElementById("surname").value;
        const name = document.getElementById("name").value;
        const username = document.getElementById("username").value;
        const BirthDate = document.getElementById("birthdate").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const telNumber = document.getElementById("telNumber").value;

        // Validazione username
        const usernameError = await validateUsername(username);
        if (usernameError) {
            showUsernameError(usernameError);
            valid = false;
        }

        // Altri controlli di validazione come prima...

        if (valid) {
            // Invia il form se tutti i campi sono validati correttamente
            form.submit();
        }
    });

    function clearErrors() {
        document.getElementById("surnameError").textContent = "";
        document.getElementById("nameError").textContent = "";
        document.getElementById("usernameError").textContent = "";
        document.getElementById("BirthDateError").textContent = "";
        document.getElementById("emailError").textContent = "";
        document.getElementById("passwordError").textContent = "";
        document.getElementById("telNumberError").textContent = "";
    }

    function clearUsernameError() {
        document.getElementById("usernameError").textContent = "";
    }

    function showUsernameError(errorMessage) {
        document.getElementById("usernameError").textContent = errorMessage;
    }

    // Validazione asincrona dell'username
    async function validateUsername(username) {
        if (username.trim().length === 0) {
            return "L'username deve essere di almeno un carattere";
        }

        if (username.length > 30) {
            return "L'username deve essere di massimo 30 caratteri";
        }

        try {
            const response = await checkUsernameAvailability(username);
            if (!response.available) {
                return "L'username è già in uso";
            }
        } catch (error) {
            console.error('Errore durante la verifica dell\'username:', error);
            return "Errore durante la verifica dell'username";
        }

        return ""; // Nessun errore trovato
    }

    //cognome: almeno un carattere, massimo 20 caratteri
    function validateSurname(surname) {
        if(surname.length <= 0) {
            return "Il cognome deve essere di almeno un carattere"
        }

        if(surname.length > 20) {
            return "Il cognome deve essere di masssimo 20 caratteri"
        }
    }

    //nome: almeno un carattere, massimo 20 caratteri
    function validateName(name) {
        if(name.length <= 0) {
            return "Il nome deve essere di almeno un carattere"
        }

        if(name.length > 20) {
            return "Il nome deve essere di masssimo 20 caratteri"
        }
    }

    //data di nascita: deve essere una data valida e l'utente deve avere almeno 18 anni
    function validateBirthDate(BirthDate) {
        const today = new Date();
        const birthDate = new Date(BirthDate);

        const birthYear = birthDate.getFullYear();
        if(birthYear < 1900 || birthYear >= today.getFullYear()) {
            return "La data di nascita non è valida";
        }

        let age = today.getFullYear() - birthDate.getFullYear();
        const monthDifference = today.getMonth() - birthDate.getMonth();
        const dayDifference = today.getDate() - birthDate.getDate();

        if(monthDifference < 0 || (monthDifference === 0 && dayDifference < 0)) {
            age--;
        }

        if(age <= 18) {
            return "Devi avere almeno 18 anni per registrarti";
        }
    }

    //email: deve essere una email valida e non deve essere già in uso
    function validateEmail(email) {
        const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        const isNotUsed = email !== document.getElementById("email").value;
        if(isNotUsed) {
            return "L'email è già in uso";
        }
        if(emailPattern.test(email) <= 50) {
            return "L'email non è valida";
        }
        return "";
    }

    //controlli sulla password
    function validatePassword(password) {
        const hasLowerCase = /[a-z]/.test(password);
        if(hasLowerCase) {
            return "La password deve contenere almeno una lettera minuscola"
        }
        const hasUpperCase = /[A-Z]/.test(password)
        if(hasUpperCase) {
            return "La password deve contenere almeno una lettera maiuscola"
        }
        const hasNumber = /[0-9]/.test(password);
        if(hasNumber) {
            return "La password deve contenere almeno un numero"
        }
        const hasSpecialCharacter = /[!@#$%^&*]/.test(password);
        if(hasSpecialCharacter) {
            return "La password deve contenere almeno un carattere speciale"
        }
        const isNotUsername = password !== document.getElementById("username").value;
        if(isNotUsername) {
            return "La password non può essere uguale all'username"
        }
        const isTooShort = password.length >= 8;
        if(isTooShort) {
            return "La password deve essere di almeno 8 caratteri"
        }
        const isLongEnough = password.length <= 20;
        if(isLongEnough) {
            return "La password deve essere di massimo 20 caratteri"
        }

        return "";
    }

    function validateTelNumber(telNumber) {
        const telNumberPattern = /^[0-9]{10}$/;
        if(telNumberPattern || telNumber.length !== 10) {
            return "Numero di telefono non valido"
        }
    }
});