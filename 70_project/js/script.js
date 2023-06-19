$(function(){
    // 프로그래스바
    let progressState = 0;
        // 변수 선언
        $(window).scroll(function () {
            if ($(this).scrollTop() >= 150) {
                if (progressState === 0) {
                    pgbar(0, 95);
                    pgbar(1, 90);
                    pgbar(2, 80);
                    progressState = 1;
                }else {
                    return;
                }
            } else {
                progressState = 0;
            }
        });


        function pgbar(idx, maxCnt) {
            const elem = $(".progress");
            let w = 0;
            const timer = setInterval(bar, 30);

            function bar() {
                if (w >= maxCnt) {
                    clearInterval(timer);
                } else {
                    w++;
                    elem.eq(idx).find('div').css({ width: w + "%" });
                    elem.eq(idx).find('div').text(w + "%");
                }
            }
        }

    // counter
    const elem = $('#counter span');
        let counterState = 0;

        $(window).scroll(function(){
            // winbow 스크롤이 150이상이면
            console.log($(this).scrollTop());
            if ($(this).scrollTop() > 2150){
                if (counterState === 0) {
                    move(0, 32, 100);
                    move(1, 8, 400);
                    move(2, 6, 500);
                    move(3, 2, 600);
                    counterState = 1;
                } else {
                    return;
                }
            } else {
                counterState = 0;
                
            }
        });

        function move(idx, maxCnt, mSec) {
            let num = 0;
            // counter 함수를 0.1초마다 실행
            var timer = setInterval(counter, mSec);
            function counter() {
                if (num >= maxCnt) {
                    clearInterval(timer);
                } else {
                    num++;
                    elem.eq(idx).text(`${num}+`);
                }
            }
        }
});

