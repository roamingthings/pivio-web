$(function () {

    var searchQuery = encodeURI(`{"match":{"${field}":"'${value}'"}}`),
        url = `${apiserver}/document?query=${searchQuery}&fields=id,name`;

    console.log(url);

    $.ajax({
        url: url,
        dataType: 'json',
        cache: false,
        success: function listDocuments(documents) {
            var source = $("#list-template").html(),
                template = Handlebars.compile(source);
            $('#list').append(template(documents));
        }
    });

});