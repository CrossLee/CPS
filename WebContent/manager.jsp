<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>CPSƽ̨����</title>
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
			alert("��ѡ��ʼʱ�䣡");
			return;
		}
		if(endtime == null || endtime == ""){
			alert("��ѡ�����ʱ�䣡");
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
				alert("��ѯʧ�ܣ�����ϵ����Ա��");
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
			<div id="logo"><div style="width:400px;font-size:15px; color: #DACEC0; font-weight:bold;">ƽ̨����������</div></div>
			
			<!-- begin of #userBox -->
			<div id="userBox" style="width: 300px; font-size: 12px;">
				<p>
					<img src="images/icon_user.gif" width="16" height="19" alt=" " />
					&nbsp;���, <span class="white">${sessionScope.user.id}</span>.| 
					<span>
						<c:if test='${sessionScope.user.userType == "operator"}'>����Ա</c:if>
						<c:if test='${sessionScope.user.userType == "admin"}'>����Ա</c:if>
					</span> | 
					<c:if test='${sessionScope.user.userType == "admin"}'><a href="/CPS/index.jsp">�˳�����</a></c:if>
				</p>
				<p class="small" style="font-size: 12px;">
					�ϴε�¼: <span class="white">${sessionScope.user.lastLogin}</span>
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
						<thead><tr><td style="border: 1px solid; font-weight: bolder;" colspan="2">CPSƽ̨���������</td></tr></thead>
						<tr><td style="border: 1px solid; " width="50%">ƽ̨����</td><td style="border: 1px solid; " width="50%"><input size="60" width="100%" type="text" value=""/></td></tr>
						<tr><td style="border: 1px solid; " width="50%">�Զ�ˢ���豸ʱ����</td><td style="border: 1px solid; " width="50%"><input size="60" width="100%" type="text" value=""/></td></tr>
					</table>
					<br/>
					 -->
					<table width="100%">
						<thead><tr><td style="border: 1px solid; font-weight: bolder;" colspan="4">������־��ѯ</td></tr></thead>
						<tr>
							<td style="border: 1px solid; ">��ʼʱ�䣺<input id="starttimer" type="text" /></td>
							<td style="border: 1px solid; ">����ʱ�䣺<input id="endtimer" type="text" /></td>
							<td style="border: 1px solid; ">�����û���<s:select multiple="false" id="user" name="user" headerKey="all" headerValue="ȫ��" cssStyle="width: 240px;" list="userList" listKey="id" listValue="id"/></td>
							<td style="border: 1px solid; "><a class="buttonStyle" href="javascript:Manager.query();" >�����ѯ</a></td>
						</tr>
					</table>
					<table id="result" width="100%" class="sTable tablesorter">
						<thead>
							<!-- <tr><td style="border: 1px solid; font-weight: bolder;" colspan="4">������־��ѯ���</td></tr> -->
							<tr id="logtitle">
								<th style="border: 1px solid; ">indexid</th>
								<th style="border: 1px solid; ">��������</th>
								<th style="border: 1px solid; ">�����û�</th>
								<th style="border: 1px solid; ">����</th>
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
			<span style="color:gray; font-size:0.8em;"><!-- Copyright @ 2012-2012 �Ϻ����ȵ��ӿƼ����޹�˾ All Rights Reserved --></span><br/>
			<span style="color:gray; font-size:0.8em;"><!-- ��ϵ���ǣ�mag_lee@126.com --></span>
		</div>
		<br class="clearFix" />
	</div>
</body>
</html>
