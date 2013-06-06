<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>修改密码</title>
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<script type="text/javascript">
	function passwd(){
		var uid = encodeURI($("#uid").val());
		var oldpwd = $("#oldpwd").val();
		var newpwd1 = $("#newpwd1").val();
		var newpwd2 = $("#newpwd2").val();
		if(oldpwd == null || oldpwd == ""){
			alert("请输入原始密码！");
			return false;
		}

		if(newpwd1 == null || newpwd1 == ""){
			alert("请输入新改密码！");
			return false;
		}
		if(newpwd1.split("").length < 6){
			alert("新改密码长度必须大于等于6！");
			return false;
		}
		if(newpwd2 == null || newpwd2 == ""){
			alert("请重复新改密码！");
			return false;
		}
		if(newpwd2.split("").length < 6){
			alert("新改密码长度必须大于等于6！");
			return false;
		}

		if(newpwd1 != newpwd2){
			alert("两次密码输入不一致！请重新输入");
			$("#newpwd1").val("");
			$("#newpwd2").val("");
			return false;
		}

		$.ajax({
			type: "POST",
			url: "/CPS/passwdAction.html",
			dataType:"json",
			data: {uid:uid,oldpwd:oldpwd,newpwd1:newpwd1,newpwd2:newpwd2},
			success: function(data){
				if(data.passwdresult == "true"){
					alert("修改成功！");
				}
				top.window.location.href = "login.jsp"
			},
			error:function(){
				alert("修改失败！");
			}
		});
	}
</script>
</head>
<body>
	<input id="uid" type="hidden" value="${sessionScope.user.id}" />
	
	<table>
		<tr><td>原始密码：</td><td><input id="oldpwd" type="password" /></td></tr>
		<tr><td>新改密码：</td><td><input id="newpwd1" type="password" /></td></tr>
		<tr><td>重复密码：</td><td><input id="newpwd2" type="password" /></td></tr>
		<tr><td><input onclick="history.go(-1);" type="button" value="取消"></td><td><input onclick="javascript:passwd();" type="button" value="确认"></td></tr>
	</table>
</body>
</html>