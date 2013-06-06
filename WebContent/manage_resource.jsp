<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
//tabbed forms box
$(function() {
	var tabContainers = $('div#forms > div.innerContent'); // change div#forms to your new div id (example:div#pages) if you want to use tabs on another page or div.

	$('ul.switcherTabs a').click(function() {
		tabContainers.hide("slow");
		tabContainers.filter(this.hash).show("slow");
		$('ul.switcherTabs li').removeClass('selected');
		$(this).parent().addClass('selected');
		var boxType = $(this).attr("id");
		$.ajax({
			type: "POST",
			url: "/CPS/resourceAction.html",
			dataType:"html",
			data: {"type":$(this).attr("id")},
			success: function(data){
				$(""+boxType).html(data);
			},
			error:function(){
				alert($(this).attr("href")+"资源获取失败");
			}
		});
		return false;
	}).filter(':last').click();
});
</script>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>

</head>
<body>
	<div class="contentBox" id="forms" style="width: 100%;background-size:100%;">
		<div class="contentBoxTop" style="background-size: 100%;">
			<ul class="switcherTabs">
				<li><a id="#box-5" href="#box-5">上传文件</a></li>
				<li><a id="#box-1" href="#box-1">文本管理</a></li>
				<!-- the link location of this tab refers to the id of the related div -->
				<li><a id="#box-2" href="#box-2">音频管理</a></li>
				<!-- the link location of this tab refers to the id of the related div -->
				<li><a id="#box-3" href="#box-3">图片管理</a></li>
				<!-- the link location of this tab refers to the id of the related div -->
				<li class="selected"><a id="#box-4" href="#box-4">视频管理</a></li>
				<!-- the link location of this tab refers to the id of the related div -->
			</ul>
			<h3>资源管理</h3>
		</div>

		<div class="innerContent" id="box-1" style="display: none">
		</div>
		<!--end of #box-1-->
		<div class="innerContent" id="box-2" style="display: none">
		</div>
		<!--end of #box-2-->
		<div class="innerContent" id="box-3" style="display: none">
		</div>
		<!--end of #box-3-->
		<div class="innerContent" id="box-4">
		</div>
		<!--end of #box-4-->
		<div class="innerContent" id="box-5" style="display: none">
		</div>
		<!--end of #box-5-->
	</div>
	<hr>
</body>
</html>