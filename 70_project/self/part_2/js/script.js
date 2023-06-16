$(function () {
    $(function () {
        $('#nav > li').mouseenter(function () {
            $('#sl1').stop().slideDown();
        });
        $('#nav > li').mouseleave(function () {
            $('#sl1').stop().slideUp();
        });
    });

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

    // 멀티캐로셀
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