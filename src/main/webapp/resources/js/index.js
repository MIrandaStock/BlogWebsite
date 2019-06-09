/*var tab = "${tab}";
var url = "/?tab="+tab+"&"*/
$(function(){
  $("#shouye").addClass("active");


    
  	// 初始化板块的样式
    function section(){
      if(tab == "good"){
        $("#tab li:eq(1)").addClass("active");
      }else if(tab == "newest"){
        $("#tab li:eq(2)").addClass("active");
      }else if(tab == "noReply"){
        $("#tab li:eq(3)").addClass("active");
      }else{
        $("#tab li:eq(0)").addClass("active");
      }
    };

    section();

    //改变当前选中板块的样式
    $("#tab li").each(function(){
    	if(tab == $(this).attr("class")){
    		$("#tab li").removeClass("active");
    		$(this).addClass("active");
    	}
    })
   
   function showScroll(){
    $(window).scroll( function(){ 
     var scrollValue=$(window).scrollTop();
			//console.log(scrollValue);
			if (scrollValue > 200)
			{
				$('#back2Top').css("display","flex");
			} else {
				$('#back2Top').css("display","none");
			}
		});	
  }
  showScroll();
  $("#back2Top").click(function(){
		//缓慢效果回到顶部
		$('body,html').animate({scrollTop:0},500);
		return false;
		//直接回到顶部
		//window.scroll(0,0);
	});


  
  //格式化时间
  $(".formate-date").each(function(i,e){
	  // console.log(formatDate(Date.parse($(this).text())));
	  $(this).text(formatDate(Date.parse($(this).text())));
  })
});