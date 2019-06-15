$(document).ready(function () {

        $($('select[name$=mainCategory]')).on('change', $('select[name$=mainCategory]'), function () {
            var categoryName = $(this).find(':selected').attr('value');
            $.ajax({
                url: "/profile/products/addProduct/categoryMain",
                data: {name: categoryName},
                type: "GET",
                contentType: 'application/json',
                success: function (res) {
                    $($('select[name$=motherCategory]')).empty();
                    var selectSize = 0;
                    $.each(res, function (element, value) {
                        $($('select[name$=motherCategory]')).append($("<option></option>")
                            .attr('value', value.enCategory).text(value[$.cookie('lang') + 'Category']));
                        selectSize = element + 1;
                    });
                    $($('select[name$=motherCategory]')).attr('size', selectSize);
                },
                error: function (res) {
                    alert(JSON.stringify(res));
                }
            })
        });
    }
);
