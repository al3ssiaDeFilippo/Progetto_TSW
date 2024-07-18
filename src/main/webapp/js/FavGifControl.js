function toggleFavorite(form, productCode, userId) {
    console.log('toggleFavorite called');
    console.log('Product Code:', productCode);
    console.log('User ID:', userId);

    var img = form.querySelector('.favorite-icon');
    var currentAction = form.getAttribute('data-action');
    var actionUrl = form.action;

    // Check if user is logged in
    if (!userId) {
        // If user is not logged in, redirect to login page
        console.log('User not logged in, redirecting to login page');
        window.location.href = contextPath + "/LogIn.jsp";
        return false;
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", actionUrl, true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
            console.log('Request completed successfully');
            // Change the image to the correct static image based on the action
            var responseText = xhr.responseText.trim();
            if (responseText === "added") {
                img.src = contextPath + "/Images/full-heart.png";
                form.setAttribute('data-action', 'remove');
                form.action = actionUrl.replace("AddToFavoriteServlet", "RemoveFromFavoriteServlet");
            } else if (responseText === "removed") {
                img.src = contextPath + "/Images/empty-heart.png";
                form.setAttribute('data-action', 'add');
                form.action = actionUrl.replace("RemoveFromFavoriteServlet", "AddToFavoriteServlet");
            }
        } else if (this.readyState === XMLHttpRequest.DONE) {
            console.log('Request failed with status:', this.status);
        }
    }
    xhr.send("productCode=" + productCode);

    return false; // Prevent form from submitting
}
