// 문서가 준비되면 함수 실행
$(function () {

    // 내비게이션바
    $('.main > li > a').mouseenter(function (e) {
        // a태그 기본 이벤트 제거
        e.preventDefault();
        $('.sub').stop().slideDown(400);
        $('.nav_bg').stop().animate({ height: 180 }, 400);
    });
    $('nav').mouseleave(function () {
        $('.sub').stop().slideUp(400);
        $('.nav_bg').stop().animate({ height: 0 }, 400);
    });

    // 비디오 자동플레이
    // $('video').get(0).play();
    // 섹션1 캐러셀
    $('.bxslider').bxSlider({
        // 자동실행
        auto: true,
        stopAutoOnClick: true
        // 일시정지와 재실행 버튼
        // autoControls: true,
        // pager: false,
        // slideWidth: 1400
      });
    

    // 풀페이지 레이아웃
    $('.section').mousewheel(function (e, delta) {
        let prev;
        if (delta > 0) {
            prev = $(this).prev().offset().top;
            console.log(prev);
            $('html').stop().animate({ scrollTop: prev }, 400, 'easeOutExpo');
        } else if (delta < 0) {
            let next = $(this).next().offset().top;
            console.log(next);
            $('html').stop().animate({ scrollTop: next }, 400, 'easeOutExpo');
        }
    });
});