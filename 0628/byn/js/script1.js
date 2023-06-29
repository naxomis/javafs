$(function(){
  
	var off = $("#main_vid").offset().top/4;
    var didScroll; 
	var lastScrollTop = 0; 
	var delta = 5; 
	var navbarHeight = $('header').outerHeight();
	

	$(window).scroll(function(){
     var s = $(window).scrollTop();
	 didScroll = true;


      if(s>off){
        countUp();
      }
    });
	setInterval(function() { if (didScroll) {
		hasScrolled(); 
		didScroll = false; 
		} 
		}, 250);


function hasScrolled() { 
var st = $(this).scrollTop(); 
// Make sure they scroll more than delta 
if(Math.abs(lastScrollTop - st) <= delta) return;
 // If they scrolled down and are past the navbar, add class .nav-up. 
 // This is necessary so you never see what is "behind" the navbar. 
 if(st > 300){
	 $('.header2').stop().slideDown('fast');
 }
 
 if (st > lastScrollTop && st > navbarHeight){ 
 // Scroll Down 

 $('.header1').removeClass('on');

 }else{
	 // Scroll Up 
	 if(st + $(window).height() < $(document).height()){
		$('.header1').addClass('on');
		$('.header2').hide();
	 console.log(st,lastScrollTop );
	
	}

 } 

 lastScrollTop = st; 
 }


	





    function countUp(){
      if($("#main1_sub").hasClass("on")==false){
        
        $("#main1_sub ul .comma").each(function(){
          var t = $(this);
          var c = t.attr('data-count');
          
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
          
          
        });
        $("#main1_sub ul .nocomma").each(function(){
          var t = $(this);
          var c = t.attr('data-count');
          
          $({count:0}).stop().animate({
            count:c
          },{
            duration:2000,
            progress:function(){
              var now = Math.ceil(this.count);
              
              t.find(".under").text(now);
                
              
              
            }
          });
          
          
        });
        
      }$("#main1_sub").addClass("on");
      
    }


   var swiper = new Swiper('.swiper-container', {
      slidesPerView: 3, // 보여지는 슬라이드 수
      spaceBetween: 485, // 슬라이드 간의 거리(px 단위)
      loop: true, // 슬라이드 무한 반복
    });



});