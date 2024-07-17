$(function() {
    $("#searchBar").autocomplete({
        source: function(request, response) {
            $.ajax({
                url: "SearchServlet?action=autocomplete",
                type: "GET",
                data: {
                    term: request.term
                },
                success: function(data) {
                    response(data);
                }
            });
        },
        minLength: 2,
        select: function(event, ui) {
            $("#searchBar").val(ui.item.label);
            return false;
        }
    });
});