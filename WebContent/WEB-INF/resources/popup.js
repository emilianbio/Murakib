var popupStatus = 0;
var jqp = jQuery.noConflict();

function loadPopup(){
	if(popupStatus==0){
		jqp("#divPopBackGround").css({
			"opacity": "0.8"
		});
		jqp("#divPopBackGround").fadeIn("slow");
		jqp("#divPopAnaPencere").fadeIn("slow");
		popupStatus = 1;
	}
}
function disablePopup(){
	if(popupStatus==1){
		jqp("#divPopBackGround").fadeOut("slow");
		jqp("#divPopAnaPencere").fadeOut("slow");
		popupStatus = 0;
	}
}

function centerPopup(){
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = jqp("#divPopAnaPencere").height();
	var popupWidth = jqp("#divPopAnaPencere").width();
	jqp("#divPopAnaPencere").css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	jqp("#divPopBackGround").css({
		"height": windowHeight
	});
}

jqp(document).ready(function(){
	jqp("#btnPopAc").click(function(){
		centerPopup();
		loadPopup();
	});
	
	jqp("#aPopKapat").click(function(){
		disablePopup();
	});
	
	jqp("#divPopBackGround").click(function(){
		disablePopup();
	});
	
	jqp(document).keypress(function(e){
		if(e.keyCode==27 && popupStatus==1){
			disablePopup();
		}
	});

});

