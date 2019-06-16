$(document).ready(function () {

    var token = $("meta[name=_csrf]").attr("content");
    var header = $("meta[name=_csrf_header]").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $(".user-menu-icon").on("click", function () {
        $(".user-menu").toggleClass("user-menu--open");
    });

    $(".lang-switch").on("click", function () {
        var lang = $(this).attr("name");
        $.removeCookie('lang');
        $.cookie("lang", lang,{ expires: 1 , path: "/"}); //1 day
        location.reload();
    });


    /*ADD TO BASKET*/
    $(".product__cart").on("click", function () {
       var productId = $(this).attr("name");
        $.ajax({
            url: "/basket/add",
            data: "productId=" + productId,
            type: "POST",
            success: function (res) {
                if (res) {
                } else {
                    $(location).attr('href', '/error');
                }
            },
            error: function () {
                $(location).attr('href', '/error');
            }
        });
    });

    /*ADD TO WISHLIST*/
    $(".product__wish-list").on("click", function () {
        var productId = $(this).attr("name");
        $.ajax({
            url: "/wishlist/add",
            data: "productId=" + productId,
            type: "POST",
            success: function (res) {
                if (res) {
                } else {
                    $(location).attr('href', '/error');
                }
            },
            error: function () {
                $(location).attr('href', '/error');
            }
        });
    });


});