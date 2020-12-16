$(document).ready(function () {
    $('.product-images-slider').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        arrows: false,
        fade: true,
        draggable:false,
        asNavFor: '.product-images-slider-nav'
    });
    $('.product-images-slider-nav').slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        asNavFor: '.product-images-slider',
        dots: false,
        centerMode: true,
        prevArrow: "<button type=\"button\" class=\"slider__prev product-slider__prev\"></button>",
        nextArrow: "<button type=\"button\" class=\"slider__next product-slider__next\"></button>",
        focusOnSelect: true
    });

});