document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('creditCardForm');

    const cardNumber = document.getElementById('cardNumber');
    const cardHolder = document.getElementById('cardHolder');
    const expiryDate = document.getElementById('expiryDate');
    const cvv = document.getElementById('cvv');

    const cardNumberDisplay = document.getElementById('cardNumberDisplay');
    const cardHolderDisplay = document.getElementById('cardHolderDisplay');
    const cardExpiryDisplay = document.getElementById('cardExpiryDisplay');
    const cardCvvDisplay = document.getElementById('cardCvvDisplay');
    const creditCard = document.getElementById('creditCard');

    const inputFields = [
        { input: cardNumber, errorField: 'cardNumberError', validator: validateCardNumber },
        { input: cardHolder, errorField: 'cardHolderError', validator: validateCardHolder },
        { input: expiryDate, errorField: 'expiryDateError', validator: validateExpiryDate },
        { input: cvv, errorField: 'cvvError', validator: validateCvv }
    ];

    inputFields.forEach(({ input, errorField, validator }) => {
        input.addEventListener('input', async function() {
            clearError(errorField);
            const value = input.value;
            const error = await validator(value);
            if (error) {
                showError(errorField, error);
            }
        });
    });

    form.addEventListener('submit', async function(event) {
        event.preventDefault();
        console.log('Submit button clicked');

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
        document.querySelectorAll('.error').forEach(error => error.textContent = '');
    }

    function clearError(errorField) {
        document.getElementById(errorField).textContent = '';
    }

    function showError(errorField, error) {
        document.getElementById(errorField).textContent = error;
    }

    async function validateCardNumber(cardNumber) {
        if (cardNumber === '') {
            return 'Campo obbligatorio';
        }

        if (cardNumber.length !== 16) {
            return 'Il numero della carta di credito deve essere di 16 cifre';
        }

        if (!/^\d+$/.test(cardNumber)) {
            return 'Il numero della carta di credito deve contenere solo cifre';
        }

        return '';
    }

    async function validateCardHolder(cardHolder) {
        if (cardHolder === '') {
            return 'Campo obbligatorio';
        }

        if (cardHolder.length > 50) {
            return 'Il nome del titolare della carta di credito deve contenere al massimo 50 caratteri';
        }

        return '';
    }

    async function validateExpiryDate(expiryDate) {
        if (expiryDate === '') {
            return 'Campo obbligatorio';
        }

        if (!/^\d{2}\/\d{2}$/.test(expiryDate)) {
            return 'La data di scadenza deve essere nel formato MM/YY';
        }

        const [month, year] = expiryDate.split('/');
        const currentYear = new Date().getFullYear().toString().slice(-2);
        const currentMonth = new Date().getMonth() + 1;

        if (year < currentYear || (year === currentYear && month < currentMonth)) {
            return 'La carta di credito è scaduta';
        }

        return '';
    }

    async function validateCvv(cvv) {
        if (cvv === '') {
            return 'Campo obbligatorio';
        }

        if (cvv.length !== 3) {
            return 'Il CVV deve essere di 3 cifre';
        }

        if (!/^\d+$/.test(cvv)) {
            return 'Il CVV deve contenere solo cifre';
        }

        return '';
    }

    // Funzione per aggiornare i dati della carta
    function updateCard() {
        const cardNumberValue = cardNumber.value;
        const cardHolderValue = cardHolder.value;
        const expiryDateValue = expiryDate.value;
        const cvvValue = cvv.value;

        // Aggiorna i dati nella carta
        cardNumberDisplay.textContent = cardNumberValue.replace(/(.{4})/g, '$1 ').trim(); // Formatta il numero carta con spazi
        cardHolderDisplay.textContent = cardHolderValue;
        cardExpiryDisplay.textContent = expiryDateValue;
        cardCvvDisplay.textContent = cvvValue;

        // Aggiungi o rimuovi la classe flip-card
        if (cvvValue.length > 0) {
            creditCard.classList.add('flip-card');
        } else {
            creditCard.classList.remove('flip-card');
        }
    }

    // Aggiungi l'evento input per aggiornare i dati della carta mentre si digita
    cardNumber.addEventListener('input', () => {
        updateCard();
        creditCard.classList.remove('flip-card');
    });
    cardHolder.addEventListener('input', () => {
        updateCard();
        creditCard.classList.remove('flip-card');
    });
    expiryDate.addEventListener('input', () => {
        updateCard();
        creditCard.classList.remove('flip-card');
    });
    cvv.addEventListener('input', () => {
        updateCard();
        creditCard.classList.add('flip-card');
    });
});
