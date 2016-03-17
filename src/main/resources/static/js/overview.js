$(function () {
    $('#searchbox').keyup(function () {
        var searchTerm = $(this).val().toLowerCase();

        console.log(searchTerm);

        $(`.card[data-meta*='${searchTerm}']`).show();
        $('.card').not(`[data-meta*='${searchTerm}']`).hide();

        if (searchTerm.trim() == '') {
            $(".card").show();
        }
    })
});
