$(function () {
    $('#searchbox').keyup(function () {
        var searchTerm = $(this).val().toLowerCase();

        $(`.card[data-meta*='${searchTerm}']`).show();
        $('.card').not(`[data-meta*='${searchTerm}']`).hide();

        if (searchTerm.trim() == '') {
            $(".card").show();
        }
    });

    $.ajax({
        url: `${apiserver}/document?${encodeURI('fields=id,description,name,team,belongs_to_bounded_context&sort=name:asc')}`,
        dataType: 'json',
        cache: false,
        success: function configureApp(documents) {

            function makeCards(documents) {
                var cards = [];

                documents.map(function (document) {
                    var card = {
                        id: document.id,
                        description: document.description,
                        name: document.name,
                        team: document.team,
                        bounded_context: document.belongs_to_bounded_context,
                        meta: (document.description + " " + document.name + " " + document.team + " " + document.belongs_to_bounded_context).toLowerCase()
                        };

                    cards.push(card);
                });

                return cards;
            }

            var source = $("#card").html(),
                template = Handlebars.compile(source);
            $('#cards').append(template(makeCards(documents)));
        }
    });
});
