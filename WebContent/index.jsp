<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Cemso Publication Solution - Operate</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui-1.8.17.custom.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery-1.js"></script>
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>

<!-- sort table  -->
<script type="text/javascript" src="js/jquery.tablesorter.js"></script>
<script type="text/javascript" src="js/jquery.tablesorter.pager.js"></script>
<!-- sort table -->

<!-- start JQuery UI -->
<script type="text/javascript" src="js/jquery-ui-1.8.17.custom.js"></script>
<script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
<!-- end JQuery UI -->

<!-- start div 拖拽 -->
<script type="text/javascript" src="js/jqDnR.js"></script>
<script type="text/javascript" src="js/dimensions.js"></script>
<!-- start div 拖拽 -->

<!-- image preview -->
<script type="text/javascript" src="js/main.js"></script>
<!-- image preview -->

<style type="text/css">
#preview {
	position: absolute;
	border: 1px solid #ccc;
	background: #333;
	padding: 5px;
	display: none;
	color: #fff;
}
</style>
<!-- image preview -->

<script type="text/javascript" src="js/common.js" ></script>

<script type="text/javascript" charset="GBK">
	//tabbed menu box
	$(document).ready(function() {
		$("#menu" + 1).append('<span class=\"selected-arrow\">&nbsp;<\/span>');
		$("#content" + 1).show("slow");
		var k = 2;
		while (k <= 9) {
			$("#content" + k).empty();
			k++;
		}
	});

	function menuSelected(index) {
		$("span.selected-arrow").remove();
		var length = 9;
		clearInterval(setIntervalObj);
		// 清空除资源管理以外的content内容
		for ( var i = 1; i <= length; i++) {
			$("#content" + i).empty();
		}
		
		// 清除未选中menu的样式
		for ( var i = 1; i <= length; i++) {
			if (i == index) {
				$("#menu" + i).addClass("selected");
				$("#menu" + i).append('<span class=\"selected-arrow\">&nbsp;<\/span>');
			} else {
				$("#menu" + i).removeClass("selected");
			}
		}
		
		// 首页
		if(index == "1"){
			$("#content" + index).html('<br\/><img alt=\"首页\" src=\"images\/workflows.png\">');
		}
		// 资源管理
		if(index == "2"){
			$.ajax({
				type: "POST",
				url: "/CPS/allResourceAction.html",
				dataType:"html",
				data: {"menuNumber":index},
				success: function(data){
					if(data.indexOf('Cemso Publication Solution - Login')!=-1){
						top.window.location.href = location.href;
					}
					if(data.indexOf('Register')!=-1){
						top.window.location.href = location.href;
					}
					$("#content" + index).html(data);
					$("#content" + index).show("slow");
				},
				error:function(){
					alert($("#content" + i)+"加载失败");
				}
			});
		}
		// 模板管理
		if(index == "3"){
			$.ajax({
				type: "POST",
				url: "/CPS/getTemplateAction.html",
				dataType:"html",
				data: {"menuNumber":index},
				success: function(data){
					if(data.indexOf('Cemso Publication Solution - Login')!=-1){
						top.window.location.href = location.href;
					}
					if(data.indexOf('Register')!=-1){
						top.window.location.href = location.href;
					}
					$("#content" + index).html(data);
					$("#content" + index).show("slow");
				},
				error:function(){
					alert($("#content" + i)+"加载失败");
				}
			});
		}
		// 模板设计
		if(index == "4"){
			$.ajax({
				type: "POST",
				url: "/CPS/templateDesignAction.html",
				dataType:"html",
				data: {"menuNumber":index},
				success: function(data){
					if(data.indexOf('Cemso Publication Solution - Login')!=-1){
						top.window.location.href = location.href;
					}
					if(data.indexOf('Register')!=-1){
						top.window.location.href = location.href;
					}
					$("#content" + index).html(data);
					$("#content" + index).show("slow");
				},
				error:function(){
					alert($("#content" + i)+"加载失败");
				}
			});
		}
		// 节目管理
		if(index == "5"){
			$.ajax({
				type: "POST",
				url: "/CPS/getProgramAction.html",
				dataType:"html",
				data: {"menuNumber":index},
				success: function(data){
					if(data.indexOf('Cemso Publication Solution - Login')!=-1){
						top.window.location.href = location.href;
					}
					if(data.indexOf('Register')!=-1){
						top.window.location.href = location.href;
					}
					$("#content" + index).html(data);
					$("#content" + index).show("slow");
				},
				error:function(){
					alert($("#content" + i)+"加载失败");
				}
			});
		}
		// 播表管理
		if(index == "6"){
			$.ajax({
				type: "POST",
				url: "/CPS/getPlaylistAction.html",
				dataType:"html",
				data: {"menuNumber":index},
				success: function(data){
					if(data.indexOf('Cemso Publication Solution - Login')!=-1){
						top.window.location.href = location.href;
					}
					if(data.indexOf('Register')!=-1){
						top.window.location.href = location.href;
					}
					$("#content" + index).html(data);
					$("#content" + index).show("slow");
				},
				error:function(){
					alert($("#content" + i)+"加载失败");
				}
			});
		}
		
		// 设备管理
		if(index == "8"){
			$.ajax({
				type: "POST",
				url: "/CPS/getDeviceAction.html",
				dataType:"html",
				data: {"menuNumber":index},
				success: function(data){
					if(data.indexOf('Cemso Publication Solution - Login')!=-1){
						top.window.location.href = location.href;
					}
					if(data.indexOf('Register')!=-1){
						top.window.location.href = location.href;
					}
					$("#content" + index).html(data);
					$("#content" + index).show("slow");
				},
				error:function(){
					alert($("#content" + i)+"加载失败");
				}
			});
		}
		
		
		// 设备监控
		if(index == "7"){
			$.ajax({
				type: "POST",
				url: "/CPS/getMinitorAction.html",
				dataType:"html",
				data: {"menuNumber":index},
				success: function(data){
					if(data.indexOf('Cemso Publication Solution - Login')!=-1){
						top.window.location.href = location.href;
					}
					if(data.indexOf('Register')!=-1){
						top.window.location.href = location.href;
					}
					$("#content" + index).html(data);
					$("#content" + index).show("slow");
				},
				error:function(){
					alert($("#content" + i)+"加载失败");
				}
			});
		}
		
		// 用户管理
		if(index == "9"){
			$.ajax({
				type: "POST",
				url: "/CPS/getUserlistAction.html",
				dataType:"html",
				data: {"menuNumber":index},
				success: function(data){
					if(data.indexOf('Cemso Publication Solution - Login')!=-1){
						top.window.location.href = location.href;
					}
					if(data.indexOf('Register')!=-1){
						top.window.location.href = location.href;
					}
					$("#content" + index).html(data);
					$("#content" + index).show("slow");
				},
				error:function(){
					alert($("#content" + i)+"加载失败");
				}
			});
		}
	}

	function logout(userId){
		jQuery.post("/CPS/logoutAction.html",userId,function(){location.replace('/CPS/login.jsp');},'HTML');
	}

</script>

</head>

<body>
	<div style="display: none" id="goTopBtn">
		<img border="0" src="images/gototop.jpg">
	</div>
	<script type="text/javascript">
		goTopEx();
	</script>
	<div id="container">
		<div id="header">
			<div id="logo"><div style="width:400px;font-size:15px; color: #DACEC0; font-weight:bold;">黄石电子站牌多媒体发布系统</div></div>
			
			<!-- begin of #userBox -->
			<div id="userBox" style="width: 300px; font-size: 12px;">
				<p>
					<img src="images/icon_user.gif" width="16" height="19" alt=" " />
					&nbsp;你好, <span class="white">${sessionScope.user.id}</span>.| 
					<span>
						<c:if test='${sessionScope.user.userType == "operator"}'>操作员</c:if>
						<c:if test='${sessionScope.user.userType == "admin"}'>管理员</c:if>
					</span> | <a href="javascript:logout('${sessionScope.user.id}');">退出</a> | 
					<a href="/CPS/passwd.jsp">修改密码</a> | 
					<c:if test='${sessionScope.user.userType == "admin"}'><a href="/CPS/managerAction.html">管理</a></c:if>
				</p>
				<p class="small" style="font-size: 12px;">
					上次登录: <span class="white">${sessionScope.user.lastLogin}</span>
				</p>
			</div>
			<!-- end of #userBox -->

			<!-- begin of #menu -->
			<div id="menu">
				<ul>
					<li id="menu1" class="selected"><a href="javascript:menuSelected(1)">首页</a></li>
					<c:if test="${fn:contains(sessionScope.user.privileges, '2')}">
						<li id="menu2"><a href="javascript:menuSelected(2)">资源管理</a></li>
					</c:if>
					<c:if test="${fn:contains(sessionScope.user.privileges, '3')}">
						<li id="menu3"><a href="javascript:menuSelected(3)">模板管理</a></li>
					</c:if>
					<c:if test="${fn:contains(sessionScope.user.privileges, '4')}">
						<li id="menu4"><a href="javascript:menuSelected(4)">模板设计</a></li>
					</c:if>
					<c:if test="${fn:contains(sessionScope.user.privileges, '5')}">
						<li id="menu5"><a href="javascript:menuSelected(5)">节目管理</a></li>
					</c:if>
					<c:if test="${fn:contains(sessionScope.user.privileges, '6')}">
						<li id="menu6"><a href="javascript:menuSelected(6)">播表管理</a></li>
					</c:if>
					<c:if test="${fn:contains(sessionScope.user.privileges, '7')}">
						<li id="menu7"><a href="javascript:menuSelected(7)">播表推送</a></li>
					</c:if>
					<c:if test="${fn:contains(sessionScope.user.privileges, '8')}">
						<li id="menu8"><a href="javascript:menuSelected(8)">设备管理</a></li>
					</c:if>
					<c:if test="${fn:contains(sessionScope.user.privileges, '9')}">
						<li id="menu9"><a href="javascript:menuSelected(9)">用户管理</a></li>
					</c:if>
				</ul>
				<p id="rightLink" style="right: 100px;">
					<c:if test='${sessionScope.user.userType == "admin"}'>
						<a href="register.jsp">更新License</a>
					</c:if>
				</p>
				<p id="rightLink">
					<a href="help/help.jsp">视频演示</a>
				</p>
			</div>
			<!-- end of #menu -->

		</div>
		<!-- end of  #header -->
		<hr />

		<!-- begin of #content -->
		<div id="content">
			<!-- begin of #leftBox -->
			<div id="leftBox" style="width: 100%">
				<!-- 首页  -->
				<div id="content1">
					<br />
					<img alt="首页" src="images/workflows.png">
				</div>

				<!-- 资源管理  -->
				<div id="content2">
				</div>

				<!-- 模板管理  -->
				<div id="content3" class="innerContent">
				</div>
				
				<!-- 模板设计  -->
				<div id="content4">
				</div>

				<!-- 节目管理  -->
				<div id="content5">节目管理</div>

				<!-- 播表管理  -->
				<div id="content6">播表管理</div>

				<!-- 设备管理  -->
				<div id="content7">设备管理</div>

				<!-- 设备监控  -->
				<div id="content8">设备监控</div>
				
				<!-- 设备监控  -->
				<div id="content9">用户管理</div>

			</div>
			<!-- end of #leftBox -->
		</div>
		<!-- end of #content -->
		<br class="clearFix" />
	</div>
	<!-- end of #container -->

	<hr />
	<div id="footerWrap">
		<div id="footer">
			<span style="color:gray; font-size:0.8em;"><!-- Copyright @ 2012-2012 上海正先电子科技有限公司 All Rights Reserved --></span><br/>
			<span style="color:gray; font-size:0.8em;"><!-- 联系我们：mag_lee@126.com --></span>
		</div>
		<br class="clearFix" />
	</div>
</body>
</html>