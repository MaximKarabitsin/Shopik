$(document).ready(function () {

    $(".row").each(function () {
        setCost(this);
    });
    setSum();

    function setCost(row) {
        var cost = row.children[1].innerHTML;
        var quantity = row.children[2].children[0].value;
        row.children[4].innerHTML = cost * quantity;
    }

    function setSum() {
        var sum = 0;
        $(".row").each(function () {
            sum += +this.children[4].innerHTML;
        })
        $("#sum").text(sum);
    }

    function changeQuantityAJAX(input) {
        var quantity = input.value;
        var productId = $(input).attr("name");
        $.ajax({
            url: "/basket/changeQuantity",
            data: {
                quantity:quantity,
                productId:productId
            },
            type: "POST",
            success: function (res) {
                if (res) {

                } else {
                    location.reload(true);
                }
            },
            error: function (res) {
                $(location).attr('href', '/error');
            }
        });
    }

    function changeQuantity(input) {
        var timer;
        return function () {
            clearTimeout(timer);
            timer = setTimeout(changeQuantityAJAX, 500, input);
            var row = $(input).parents(".row")[0];
            setCost(row);
            setSum();
        }
    }

    $("input[type=number]").each(function () {
        $(this).on("change", changeQuantity(this));
    });

});