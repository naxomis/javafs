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
    // scrollTop
    // 헤더
    const headerBg = $('#header');

    // 윈도우에 스크롤 이벤트가 발생하면 함수 실행
    $(window).scroll(function () {
        // 스크롤바를 스크롤한 양을 st에 저장
        let st = document.documentElement.scrollTop;
        // let stVal = 600;

        if (st < 0) {
            headerBg.css({ background: '#243c84' })
        } else {
            headerBg.css({ background: 'transparent' })
        }
    });


    // slide 섹션1
    const slideList = $('#slideList');
    const slideListItem = $('#slideList').find('li');
    const prevBtn = $('#prevBtn');
    const nextBtn = $('#nextBtn');
    const pager = $('.pager');
    const pagerList = $('.pager').find('li');
    let num = 1;
    const slideListItemWidth = 100;
    const slideCount = slideListItem.length + 2;

    // 요소의 가로 크기
    // 탬플릿 리터럴(Template Literal): 백틱(`${변수}문자열`)
    $('.frame').width(`${slideListItemWidth}%`);
    slideList.width(`${slideListItemWidth * slideCount}%`);
    slideListItem.width(`${slideListItemWidth}%`);
    // 슬라이드의 처음 위치
    slideList.css({ left: `${-1 * slideListItemWidth}%`});

    // 처음과 마지막 이미지 복제
    const firstSlideList = slideList.find('li:first-of-type');
    const lastSlideList = slideList.find('li:last-of-type');
    lastSlideList.clone().prependTo(slideList);
    firstSlideList.clone().appendTo(slideList);

    prevBtn.click(prevSlide);
    nextBtn.click(nextSlide);

    function prevSlide() {
        clearInterval(timer);
        slide('prevBtn');
        pagination(num - 1);
        timer = setInterval(autoSlide, 2000);
    }
    function nextSlide() {
        clearInterval(timer);
        slide('nextBtn');
        pagination(num - 1);
        timer = setInterval(autoSlide, 2000);
    }
    
    pagerList.click(function () {
        clearInterval(timer);
        let idx = $(this).index();
        pagination(idx);
        slideList.addClass('on');
        moveSlide(idx + 1);
        timer = setInterval(autoSlide, 2000);
    });
    
    function pagination(cnt) {
        pagerList.removeClass('active');
        pagerList.eq(cnt).addClass('active');
    }

    function slide(btn) {
        if (btn === 'prevBtn') {
            num--;
            if (num < 1) {
                num = slideListItem.length;
                slideList.removeClass('on');
                moveSlide(num + 1);
            }
        } else {
            num++;
            slideList.addClass('on');
            if (num > slideListItem.length) {
                num = 1;
                slideList.removeClass('on');
                moveSlide(num - 1);
            }
        }
        setTimeout(function () {
            slideList.addClass('on');
            moveSlide(num);
        }, 100);
    }

    function moveSlide(cnt) {
        let posX = cnt * slideListItemWidth * -1;
        slideList.css({ left: posX + '%' });
        return false;

    }

    let timer = setInterval(autoSlide, 2000);

    function autoSlide() {
        slide('nextBtn');
        pagination(num - 1);
    }
    //섹션1 끝

    // 멀티캐로셀 섹션2
    $(function () {
        const slider = $('.multiSlider').bxSlider({
            pager: false,
            controls: false,
            // 하나의 이미지 가로크기
            slideWidth: 272,
            // 최소 슬라이드 수
            minSlides: 1,
            // 최대 슬라이드 수
            maxSlides: 8,
            // 움직이는 슬라이드 수
            moveSlides: 1,
            // 이미지 사이 여백
            slideMargin: 40
        });

        // 이전 버튼
        $('.slideWrap #prev').click(function (e) {
            e.preventDefault();
            // 이전 슬라이드로 이동
            slider.goToPrevSlide();
            autoPager();
            return false;
        });
        // 이후 버튼
        $('.slideWrap #next').click(function (e) {
            e.preventDefault();
            // 다음 슬라이드로 이동
            slider.goToNextSlide();
            autoPager();
            return false;
        });
    });
});