$(function () {

    var url = `${apiserver}/changeset`;

    console.log(url)

    var feed = [];

    $.ajax({
        url: url,
        dataType: 'json',
        cache: false,
        success: function changeSet(documents) {
            var source = $("#feed-template").html(),
                template = Handlebars.compile(source);

            for (var i = 0; i < documents.length; i++) {
                var d = documents[i];
                console.log(d);
                for (var j = 0; j < d.fields.length; j++) {
                    var entry = {
                        date: prettyDate(d.timestamp),
                        document: d.document,
                        field: d.fields[j].field,
                        previous: d.fields[j].previous,
                        current: d.fields[j].current
                    };
                    feed.push(entry);

                }

            }
            $('#feed').append(template(feed));
        }
    });

});


