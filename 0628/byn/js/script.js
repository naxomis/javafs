$(function(){

var off = $("#main_vid").offset().top/4;
var didScroll;
var lastScrollTop = 0;
var delta = 5;
var navbarHeight = $('header').outerHeight();
//alert(navbarHeight);  높이 80

var sct;
  $(window).scroll(function(){
    sct = $(window).scrollTop();
    didScroll = true;
      
    if(sct > off){
      countUp();
    }
  });

  setInterval(function(){
    if(didScroll){
     hasScrolled();
     didScroll = false;
   }
   },250);


function hasScrolled(){
  sct = $(window).scrollTop();
  if(Math.abs(lastScrollTop - sct) <= delta ) return;
  //미세한 움직임에는 동작하지 말라 abs 절대값
  if(sct > 300){
    $('.header2').stop().slideDown('fast');
  }

  if(sct > lastScrollTop && sct > navbarHeight){
    //scroll Down
    //console.log(sct, lastScrollTop);
    $('.header1').removeClass('on');
  }else{
    //scroll Up
    if(sct + $(window).height() < $(document).height()){
     //console.log(sct, lastScrollTop);
     //console.log(sct, $(window).height(), $(document).height());
      $('.header1').addClass('on');
      $('.header2').hide();
    }
  }
  lastScrollTop = sct;

}
//$(window).height() 브라우저의 높이 952
//$(document).height() 현재 html 전체 문서의 높이 3597



function countUp(){
  if($('#main1_sub').hasClass('on')==false){

    $('#main1_sub ul .comma').each(function(){
      var t = $(this);
      var c = t.attr('data-count');
      //console.log(c);
      $({count:0}).stop().animate({count:c},{
        duration:3000,
        progress:function(){
          var now = Math.ceil(this.count);
          if(now>=1000){
            var g = Math.floor(now/1000);
            //console.log(g);
            var h = now - (g*1000);
            t.find(".over").text(g+',');
            t.find(".under").text(h);
          }else{
            t.find(".under").text(now);
          }

        }
      });
   
    });
   /*
    $({count:0}).stop().animate({
      count:c
    },{
      duration:2000,
      progress:function(){
        var now = Math.ceil(this.count);
        if(now>=1000){
          var g = Math.floor(now/1000);
          var h = now-(g*1000);
          
          t.find(".over").text(g+",");
          
          if(h<10){
            t.find(".under").text("00"+h);
          }else if(h>=10 && h<100){
            t.find(".under").text("0"+h);
          }else{
            t.find(".under").text(h);
          }
        }else{
          
          t.find(".under").text(now);
          
        }
        
      }
    });
*/





   
   
    $('#main1_sub ul .nocomma').each(function(){
      var t = $(this);
      var c = t.attr('data-count');
      //console.log(c);
      $({count:0}).stop().animate({count:c},{
        duration:3000,
        progress:function(){
          var now = Math.ceil(this.count);
          t.find('.under').text(now);

        }
      });

    });

  }

  $('#main1_sub').addClass('on');
}

var swiper = new Swiper('.swiper-container', {
  slidesPerView : '3',//보여지는 슬라이드 수
  spaceBetween : 485, //슬라이드 간의 거리(px 단위)
  loop:true,
  navigation: {   // 버튼 사용자 지정
    nextEl: '.next',
    prevEl: '.prev',
  },
  // autoplay : {  // 자동 슬라이드 설정 , 비 활성화 시 false
  //   delay : 3000,   // 시간 설정
  //   disableOnInteraction : false,  // false로 설정하면 스와이프 후 자동 재생이 비활성화 되지 않음
  // },


});

$('form[name=rel_f]').on('change',function(){
  var  url = $('select',this).val();
  //console.log(url);
  window.open(url);
  return false;
});





});