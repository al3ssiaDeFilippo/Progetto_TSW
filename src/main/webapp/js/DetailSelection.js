document.addEventListener("DOMContentLoaded", function () {
    const sizeSelect = document.getElementById("sizeSelect");
    const frameSelect = document.getElementById("frameSelect");
    const frameSelectContainer = document.getElementById("frameSelectContainer");
    const frameColorSelectContainer = document.getElementById("frameColorSelectContainer");
    const frameColorSelect = document.getElementById("frameColorSelect");
    const productImage = document.getElementById("productImage");
    const errorMessage = document.getElementById("errorMessage");
    const submitButton = document.querySelector("input[type='submit']");

    // Initially hide the frame color select container and frame select container
    frameSelectContainer.style.display = 'none';
    frameColorSelectContainer.style.display = 'none';

    // Function to update the product image
    function updateImage() {
        let imageUrl = `GetProductImageServlet?action=get&code=${productImage.getAttribute("data-product-code")}&custom=true`;
        if (frameSelect.value !== "no frame") {
            imageUrl += `&frame=${frameSelect.value}`;
            frameColorSelectContainer.style.display = ''; // Show the frame color select container
        } else {
            frameColorSelectContainer.style.display = 'none'; // Hide the frame color select container
        }
        if (frameColorSelect.value !== "no color" && frameColorSelect.value !== "selectAframeColor") {
            imageUrl += `&frameColor=${frameColorSelect.value}`;
        }
        productImage.src = imageUrl;
    }

    // Function to validate the form
    function validateForm() {
        errorMessage.style.display = 'none';
        errorMessage.textContent = "";
        if (sizeSelect.value === "selectAsize") {
            errorMessage.textContent = "Per favore, seleziona una dimensione.";
            errorMessage.style.display = 'block';
            return false;
        }
        if (frameSelect.value !== "no frame" && (frameColorSelect.value === "selectAframeColor" || frameColorSelect.value === "no color")) {
            errorMessage.textContent = "Seleziona un colore per la cornice.";
            errorMessage.style.display = 'block';
            return false;
        }
        return true;
    }

    // Function to toggle the frame select container
    function toggleFrameSelect() {
        if (sizeSelect.value !== "selectAsize") {
            frameSelectContainer.style.display = '';
        } else {
            frameSelectContainer.style.display = 'none';
            frameColorSelectContainer.style.display = 'none';
        }
    }

    // Event listeners
    sizeSelect.addEventListener("change", function() {
        toggleFrameSelect();
        updateImage();
    });
    frameSelect.addEventListener("change", function() {
        updateImage();
        if (frameSelect.value === "no frame") {
            frameColorSelect.value = "no color"; // Reset frame color select to "no color"
            frameColorSelectContainer.style.display = 'none';
        }
    });
    frameColorSelect.addEventListener("change", updateImage);

    // Initialize the initial state
    toggleFrameSelect();
    updateImage();

    // Expose validateForm globally
    window.validateForm = validateForm;
});