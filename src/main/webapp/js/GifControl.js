$(document).ready(function() {
    // Store the jQuery object in a variable
    var boxes = $('.profile-box, .favorites-box, .info-box, .order-box, .card-box, .address-box, .favorites-heart');

    // On mouse hover on the box, change the GIF from static to animated
    boxes.mouseenter(function() {
        var animatedGif = $(this).find('.myGif').data('animated-gif'); // Get the URL of the animated GIF from the data attribute
        $(this).find('.myGif').attr('src', animatedGif);
        console.log('mouseenter event triggered');
    });

    // When the mouse leaves the box, change the GIF from animated to static
    boxes.mouseleave(function() {
        var staticGif = $(this).find('.myGif').data('static-gif'); // Get the URL of the static GIF from the data attribute
        $(this).find('.myGif').attr('src', staticGif);
        console.log('mouseleave event triggered');
    });
});