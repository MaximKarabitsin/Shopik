$(document).ready(function () {

    /*СЛАЙДЕР ГЛАВНАЯ*/
    $('.main-slider').slick({
        dots: true,
        autoplay: true,
        autoplaySpeed: 5000,
        accessibility: false,
        prevArrow: "<button type=\"button\" class=\"slider__prev main-slider__prev\"></button>",
        nextArrow: "<button type=\"button\" class=\"slider__next main-slider__next\"></button>",
        dotsClass: "main-slider__dots"
    });
    $('.products-slider').slick({
        mobileFirst: true,
        autoplay: true,
        autoplaySpeed: 10000,
        slidesToShow: 1,
        slidesToScroll: 1,
        centerMode: false,
        prevArrow: "<button type=\"button\" class=\"slider__prev product-slider__prev\"></button>",
        nextArrow: "<button type=\"button\" class=\"slider__next product-slider__next\"></button>",

        responsive: [

            {
                breakpoint: 1199,
                settings: {

                    slidesToShow: 3,
                }
            },
            {
                breakpoint: 991,
                settings: {
                    slidesToShow: 3,
                }
            },
            {
                breakpoint: 767,
                settings: {
                    slidesToShow: 2,

                }
            },

        ]
    });


});