$(document).ready(function() {
	set_size();
});

function set_size() {
	//获得浏览器宽高
	var window_heigh = $(document).height();
	var window_width = $(document).width();
	//设置warp
	set_warp_padding();
	//设置good_warp
	set_good_warp();
	//设置good_img
	set_good_img();
	//设置good_info_warp
	set_good_info_warp();
	//设置上下行高
	set_good_integral();
	//设置预约、搜藏按钮上下边距
	set_appoin_keep();
	//设置按钮大小
	set_page_btn();
	//设置字体
	$("*").css("font-size", parseInt((38 / 390) * $(".good_warp").height())+'px');
	$(".good_title").css("line-height",parseInt((38 / 390) * $(".good_warp").height())+'px');
}

//设置warp padding值
function set_warp_padding() {
	var padding_top_bottom = parseInt((30 / 1080) * $(document).height());
	var padding_left_right = parseInt((30 / 1080) * $(document).width());
	$(".warp").css("padding-top", padding_top_bottom);
	$(".warp").css("padding-bottom", padding_top_bottom);
	$(".warp").css("padding-left", padding_left_right);
	$(".warp").css("padding-right", padding_left_right);
}

//设置good_warp
function set_good_warp() {
		var height = parseInt((390 / 1418) * $(document).height());
		var margin_bottom = parseInt((32 / 1080) * $(document).height());
		$(".good_warp").height(height);
		$(".good_warp").css("margin-bottom", margin_bottom);
	}
//设置good_img
function set_good_img() {
		var height_width = parseInt((280 / 390) * $(".good_warp").height());
		//判断横向是否超出
		if(height_width > parseInt((282 / 1022) * $(".good_warp").width()) ){
			height_width = parseInt((282 / 1022) * $(".good_warp").width());
		}
		$(".good_img").height(height_width);
		$(".good_img").width(height_width);
		var margin_top = parseInt((45 / 390) * $(".good_warp").height());
		var margin_left = parseInt((95 / 1022) * $(".good_warp").width());
		$(".good_img").css("margin-top", margin_top);
		$(".good_img").css("margin-left", margin_left);
		var good_warp_height = $(".good_warp").height();
		var good_img_heigh = $(".good_img").height();
		if (good_img_heigh >= good_warp_height) {
			$(".good_warp").height(good_img_heigh + parseInt((55 / 1418) * $(document).height()))
		}
	}
//设置good_info_warp
function set_good_info_warp() {
		height = parseInt((263 / 390) * $(".good_warp").height());
		width = parseInt((472 / 1022) * $(".good_warp").width());
		$(".good_info_wap").height(height);
		$(".good_info_wap").width(width);
		var margin_top = parseInt((57 / 390) * $(".good_warp").height());
		var margin_right = parseInt((45 / 1022) * $(document).width());
		$(".good_info_wap").css("margin-top", margin_top);
		$(".good_info_wap").css("margin-right", margin_right);
	}
//设置积分栏上下行高
function set_good_integral() {
		height = parseInt((45 / 390) * $(".good_warp").height());
		bottom = parseInt((35 / 390) * $(".good_warp").height());
		$("p.good_integral").css("margin-top", height);
		$("p.good_integral").css("margin-bottom",bottom);
	}
//设置预约上下编剧
function set_appoin_keep() {
	height = parseInt((22 / 390) * $(".good_warp").height());
	$("a.good_appion").css("margin-top", height);
	$("a.good_appion").css("margin-right",10);
	$("a.good_keep").css("margin-top", height);
}
//设置翻页按钮
function set_page_btn(){
	var height = parseInt((130 / 390) * $(".good_warp").height());
	var width =  parseInt((49 / 100) * $(".good_warp").width());
	$(".page_perv").height(height);
	$(".page_perv").width(width);
	$(".page_perv").css("line-height",height+"px");
	$(".page_perv").css("margin-bottom","10px");
	$(".page_next").height(height);
	$(".page_next").width(width);
	$(".page_next").css("line-height",height+"px");
	$(".page_next").css("margin-bottom","10px");
}
