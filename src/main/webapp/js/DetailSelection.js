document.addEventListener("DOMContentLoaded", function () {
    const frameSelect = document.getElementById("frameSelect");
    const frameColorSelect = document.getElementById("frameColorSelect");
    const productImage = document.getElementById("productImage");

    function updateImage() {
        const frame = frameSelect.value;
        const frameColor = frameColorSelect.value;
        const productCode = productImage.getAttribute("data-product-code");
        const custom = true; // or fetch based on some condition if custom is needed

        let imageUrl = `GetProductImageServlet?action=get&code=${productCode}&custom=${custom}`;

        if (frame !== "no frame") {
            imageUrl += `&frame=${frame}`;
        }

        if (frameColor !== "no color") {
            imageUrl += `&frameColor=${frameColor}`;
        }

        productImage.src = imageUrl;
    }

    frameSelect.addEventListener("change", updateImage);
    frameColorSelect.addEventListener("change", updateImage);

    // Initial call to set the image
    updateImage();
});
