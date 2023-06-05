
/* 
    (바닐라)자바스크립트
    document.addEventListener('DOMContentLoaded', function(){});
    문서의 DOM내용을 전부 읽은 후 함수를 실행

    제이쿼리
    $(document).ready(fuction(){});
    $(fuction(){});
*/
// 문서의 DOM내용을 전부 읽은 후 함수를 실행시킨다.
document.addEventListener('DOMContentLoaded', function(){

// 변수
const yArea = document.querySelector('.yy'),
    oArea = document.querySelector('.mo'),
    dArea = document.querySelector('.dd'),
    wArea = document.querySelector('.we'),
    hArea = document.querySelector('.hh'),
    mArea = document.querySelector('.mm'),
    sArea = document.querySelector('.ss');

setInterval(timer, 1000);

function timer() {
    // Tue May 23 2023 16:16:53 GMT+0900 (한국 표준시)
    // 날짜와 시간 d 객체 생성
    let d = new Date();
    console.log(d);
    // 년(yyyy)
    yArea.innerHTML = d.getFullYear() + '년';

    // 월(mm): 0(1월) ~ 11(12월)
    oArea.innerHTML = d.getMonth() + 1 + '월';

    // 일(dd)
    dArea.innerHTML = d.getUTCDate() + '일';

    // 요일(weed): 0(일) ~ 6(토)
    // if를 사용하여 요일을 일요일 ~ 토요일까지 문자열로 표시

    let week = d.getDay()

    if (week === 0) {
        week = '일';
    } else if (week === 1) {
        week = '월';
    } else if (week === 2) {
        week = '화';
    } else if (week === 3) {
        week = '수';
    } else if (week === 4) {
        week = '목';
    } else if (week === 5) {
        week = '금';
    } else {
        week = '토';
    }
    wArea.innerHTML = week + '요일';
    // document.write(week, '요일', '<br>');
    // 시(hh)
    // if 사용하여 오전과 오후를 구분하여 12시가제로 표시하기
    // 예) 오후 5시
    let hh = d.getHours()
    if (hh >= 13) {
        hh = 'PM ' + (hh - 12) + '시';
    } else if (hh >= 12) {
        hh = 'PM ' + hh + '시';
        // 10 ~ 11
    } else if (hh >= 10) {
        hh = 'AM ' + hh + '시';
        // 0 ~ 9
    } else {
        hh = 'AM 0' + hh + '시';
    }
    hArea.innerHTML = hh;

    // 분(mm)
    let mm = d.getMinutes();
    let ss = d.getMinutes();
    if (mm < 10) {
        mm = '0' + mm + '분';
    }
    if (ss > 10) {
        ss = '0' + ss + '분';
    }
    mArea.innerHTML = mm;

    // 초(ss)
    // 01 ~ 09 ~ 10 ~ 60

    let sArea = d.getSeconds();
    if (sArea < 10) {
        ss = '0' + ss;
    }
    ss, '초', '<br>';
}
});