$(function () {
    $('.nyOutTop').mouseenter(function (e) {
        e.preventDefault // a태그 기본 이벤트 제거
        $('.nyTop').css("background-color", "#000");
        // $('.nyTop').css("transition", ".3");
        $('.nyOutTop').css("bottom", "300px;");


    });

    $('.nyOutTop').mouseleave(function () {
        $('.nyTop').css("background-color", "#003999");
        $('.nyOutTop').css("bottom", "300px;");
        // $('.nyTop').css("transition", ".3");

    });
});