$(document).ready(function () {

    $($('select[name$=mainCategory]')).on('change', function () {
        var categoryName = $(this).find(':selected').attr('value');
        $.ajax({
            url: "/profile/products/addProduct/categoryMain",
            data: {name: categoryName},
            type: "GET",
            contentType: 'application/json',
            success: function (res) {
                var text = ($.cookie('lang') === "en") ? "en" : "ru" + 'Category'
                $('select[name$=motherCategory]').empty();
                var selectSize = 0;
                $.each(res, function (element, value) {
                    $('select[name$=motherCategory]').append($("<option></option>")
                        .attr('value', value.enCategory).text(value[text]));
                    selectSize = element + 1;
                });
                $('select[name$=motherCategory]').attr('size', selectSize);
            },
            error: function (res) {
                alert(JSON.stringify(res));
            }
        })
    });
    }
);
