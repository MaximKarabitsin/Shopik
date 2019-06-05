$(document).ready(function () {

    $(".user-menu-icon").on("click", function () {
        $(".user-menu").toggleClass("user-menu--open");
    });

    $(".lang-switch").on("click", function () {
        var lang = $(this).attr("name");
        $.cookie("lang", lang, { expires: 1 }); //1 day
        location.reload();
    });

});