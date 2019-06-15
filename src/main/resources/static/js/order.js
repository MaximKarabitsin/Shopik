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
            sum += +this.children[1].innerHTML;
        })
        $("#sum").text(sum);
    }

    $("input[type=number]").on("change", function () {
        var row = $(this).parents(".row")[0];
        console.log(row);
        setCost(row);
        setSum();
    })
});