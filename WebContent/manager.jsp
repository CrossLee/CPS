<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>CPS平台管理</title>
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

<script type="text/javascript" src="js/common.js" ></script>

<style type="text/css">
	.pager{
		position: relative;!important;
		top: 0px;!important;
	}
</style>

<script type="text/javascript">
$(document).ready(function(){
	$("#result").tablesorter({
		headers:{
			0: { sorter: false},
			1: { sorter: false},
			2: { sorter: false},
			3: { sorter: false}
		}
	}).tablesorterPager({container : $("#resultPager"),positionFixed: false,size:10});
	$("#starttimer").datetimepicker({
		dateFormat: 'yy-mm-dd',
		showSecond: true,
		timeFormat: 'hh:mm:ss',
		stepHour: 1,
		stepMinute: 1,
		stepSecond: 1
	});
	$("#endtimer").datetimepicker({
		dateFormat: 'yy-mm-dd',
		showSecond: true,
		timeFormat: 'hh:mm:ss',
		stepHour: 1,
		stepMinute: 1,
		stepSecond: 1
	});

	$('#resultPager').css("position","relative");
	$('#resultPager').css("top","0px");
	
});

var Manager = new function(){
	this.query = function(){
		var starttime = $('#starttimer').val();
		var endtime = $('#endtimer').val();

		if(starttime == null || starttime == ""){
			alert("请选择开始时间！");
			return;
		}
		if(endtime == null || endtime == ""){
			alert("请选择结束时间！");
			return;
		}
		
		var user = $('#user').val();
		$.ajax({
			type: "POST",
			url: "/CPS/queryActionlog.html",
			dataType:"html",
			data: {
				starttime:starttime,
				endtime:endtime,
				user:user	
			},
			success: function(data){
				$('#logbody').html(data);
				$("#result").tablesorter({
					headers:{
						0: { sorter: false},
						1: { sorter: false},
						2: { sorter: false},
						3: { sorter: false}
					}
				}).tablesorterPager({container : $("#resultPager"),positionFixed: false,size:10});
				$('#resultPager').css("position","relative");
				$('#resultPager').css("top","0px");
				$("#result").trigger("update");
				$("#resultPager").trigger("update");
			},
			error:function(){
				alert("查询失败，请联系管理员！");
			}
			
		});
	};
};

</script>
</head>

<body style="font-size: 12px;">
<div style="display: none" id="goTopBtn">
		<img border="0" src="images/gototop.jpg">
	</div>
	<script type="text/javascript">
		goTopEx();
	</script>
	<div id="container">
		<div id="header">
			<div id="logo"><div style="width:400px;font-size:15px; color: #DACEC0; font-weight:bold;">平台管理与配置</div></div>
			
			<!-- begin of #userBox -->
			<div id="userBox" style="width: 300px; font-size: 12px;">
				<p>
					<img src="images/icon_user.gif" width="16" height="19" alt=" " />
					&nbsp;你好, <span class="white">${sessionScope.user.id}</span>.| 
					<span>
						<c:if test='${sessionScope.user.userType == "operator"}'>操作员</c:if>
						<c:if test='${sessionScope.user.userType == "admin"}'>管理员</c:if>
					</span> | 
					<c:if test='${sessionScope.user.userType == "admin"}'><a href="/CPS/index.jsp">退出管理</a></c:if>
				</p>
				<p class="small" style="font-size: 12px;">
					上次登录: <span class="white">${sessionScope.user.lastLogin}</span>
				</p>
			</div>
			<!-- end of #userBox -->

			<!-- begin of #menu -->
			<div id="menu">
			</div>
			<!-- end of #menu -->
		</div>
		<!-- end of  #header -->
		<hr />

		<!-- begin of #content -->
		<div id="content">
			<!-- begin of #leftBox -->
			<div id="leftBox" style="width: 100%">
				<div id="content1">
					<br />
					<!-- 
					<table width="100%">
						<thead><tr><td style="border: 1px solid; font-weight: bolder;" colspan="2">CPS平台配置与管理</td></tr></thead>
						<tr><td style="border: 1px solid; " width="50%">平台名称</td><td style="border: 1px solid; " width="50%"><input size="60" width="100%" type="text" value=""/></td></tr>
						<tr><td style="border: 1px solid; " width="50%">自动刷新设备时间间隔</td><td style="border: 1px solid; " width="50%"><input size="60" width="100%" type="text" value=""/></td></tr>
					</table>
					<br/>
					 -->
					<table width="100%">
						<thead><tr><td style="border: 1px solid; font-weight: bolder;" colspan="4">操作日志查询</td></tr></thead>
						<tr>
							<td style="border: 1px solid; ">开始时间：<input id="starttimer" type="text" /></td>
							<td style="border: 1px solid; ">结束时间：<input id="endtimer" type="text" /></td>
							<td style="border: 1px solid; ">操作用户：<s:select multiple="false" id="user" name="user" headerKey="all" headerValue="全部" cssStyle="width: 240px;" list="userList" listKey="id" listValue="id"/></td>
							<td style="border: 1px solid; "><a class="buttonStyle" href="javascript:Manager.query();" >点击查询</a></td>
						</tr>
					</table>
					<table id="result" width="100%" class="sTable tablesorter">
						<thead>
							<!-- <tr><td style="border: 1px solid; font-weight: bolder;" colspan="4">操作日志查询结果</td></tr> -->
							<tr id="logtitle">
								<th style="border: 1px solid; ">indexid</th>
								<th style="border: 1px solid; ">操作日期</th>
								<th style="border: 1px solid; ">操作用户</th>
								<th style="border: 1px solid; ">动作</th>
							</tr>
						</thead>
						<tbody id="logbody">
						
						<tr style="display: none;"><td></td></tr>
						</tbody>
					</table>
					<div id="resultPager" class="pager">
						<form>
							<img src="images/first.png" class="first" />
							<img src="images/prev.png" class="prev" />
							<input type="text" class="pagedisplay" />
							<img src="images/next.png" class="next" />
							<img src="images/last.png" class="last" />
							<select class="pagesize hid">
								<option selected="selected" value="10">10</option>
								<option value="20">20</option>
								<option value="30">30</option>
								<option value="40">40</option>
								<option value="50">50</option>
							</select>
						</form>
					</div>
				</div>
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
