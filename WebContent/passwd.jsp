<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�޸�����</title>
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<script type="text/javascript">
	function passwd(){
		var uid = encodeURI($("#uid").val());
		var oldpwd = $("#oldpwd").val();
		var newpwd1 = $("#newpwd1").val();
		var newpwd2 = $("#newpwd2").val();
		if(oldpwd == null || oldpwd == ""){
			alert("������ԭʼ���룡");
			return false;
		}

		if(newpwd1 == null || newpwd1 == ""){
			alert("�������¸����룡");
			return false;
		}
		if(newpwd1.split("").length < 6){
			alert("�¸����볤�ȱ�����ڵ���6��");
			return false;
		}
		if(newpwd2 == null || newpwd2 == ""){
			alert("���ظ��¸����룡");
			return false;
		}
		if(newpwd2.split("").length < 6){
			alert("�¸����볤�ȱ�����ڵ���6��");
			return false;
		}

		if(newpwd1 != newpwd2){
			alert("�����������벻һ�£�����������");
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
					alert("�޸ĳɹ���");
				}
				top.window.location.href = "login.jsp"
			},
			error:function(){
				alert("�޸�ʧ�ܣ�");
			}
		});
	}
</script>
</head>
<body>
	<input id="uid" type="hidden" value="${sessionScope.user.id}" />
	
	<table>
		<tr><td>ԭʼ���룺</td><td><input id="oldpwd" type="password" /></td></tr>
		<tr><td>�¸����룺</td><td><input id="newpwd1" type="password" /></td></tr>
		<tr><td>�ظ����룺</td><td><input id="newpwd2" type="password" /></td></tr>
		<tr><td><input onclick="history.go(-1);" type="button" value="ȡ��"></td><td><input onclick="javascript:passwd();" type="button" value="ȷ��"></td></tr>
	</table>
</body>
</html>