function previewImage(event) {
    const input = event.target;
    const reader = new FileReader();
    reader.onload = function() {
        const dataURL = reader.result;
        const output = document.getElementById('productImage');
        output.src = dataURL;
    };
    reader.readAsDataURL(input.files[0]);

    // Upload the image immediately after selection
    uploadImage(input.files[0]);
}

function uploadImage(file) {
    const formData = new FormData();
    formData.append('photoPath', file);
    formData.append('action', 'uploadImage');
    formData.append('code', document.getElementById('productCode').value);

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'UpdateProductServlet', true);
    xhr.onload = function() {
        if (xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
            document.getElementById('productImage').src = response.newImageUrl;
        } else {
            console.error('Upload failed');
        }
    };
    xhr.send(formData);
}
