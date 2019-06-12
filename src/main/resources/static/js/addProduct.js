$(document).ready(function () {

        $("form[name=productForm]").submit(function (e) {
            e.preventDefault();
            $.ajax({
                url: "/addProduct",
                data: $(this).serialize(),
                type: "POST",
                success: function (res) {
                    $.each(res.errorMessages, function (element, value) {
                        // $("#error-"+element).text(value);
                        $("#error-" + element).addClass("active");
                    });
                },
                error: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        });

        $($('select[name$=mainCategory]')).on('change', $('select[name$=mainCategory]'), function () {
            var categoryName = $(this).find(':selected').attr('value');
            $.ajax({
                url: "/addProduct/categoryMain",
                data: {name: categoryName},
                type: "GET",
                contentType: 'application/json',
                success: function (res) {
                    $($('select[name$=motherCategory]')).empty();
                    var selectSize = 0;
                    $.each(res, function (element, value) {
                        $($('select[name$=motherCategory]')).append($("<option></option>")
                            .attr('value', value.enCategory).text(value.enCategory));
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
