<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>CPS多媒体发布平台  —— 登录    Cemso Publication Solution - Login</title>
<script src="js/jquery-1.7.1.js" type="text/javascript" charset="GBK"></script>
<script>
var query = location.search.substring(1); // Get query string

if(query == 'usernull=yes'){
	top.window.location.href = '/CPS/login.jsp';
}

if (window != top){
	top.window.location.href = location.href;
	location.reload(true);
}

$(document).ready(function(){

	var bro=$.browser;
    var binfo="";
    if(bro.msie) {binfo="Microsoft Internet Explorer "+bro.version;}
    if(bro.mozilla) {binfo="Mozilla Firefox "+bro.version;}
    if(bro.safari) {binfo="Apple Safari "+bro.version;}
    if(bro.opera) {binfo="Opera "+bro.version;}
    alert('您使用的浏览器版本是：'+binfo+'\n\n为了保证显示效果及脚本正常运行，请使用火狐10.0以上或IE9以上版本');
    $('#userPwd').focus();
    $('#userName').focus().blur().focus();
    $('#userPwd').bind('keydown',function(e){
		var key = e.which;
		if(key == 13){
			Login.loginSubmit();
		}
    });
});

var Login = new function(){
	this.loginSubmit = function(){
		$.ajax({
			type: "POST",
			url:"/CPS/loginAction.do",
			data:encodeURI($('#loginForm').serialize()),
			dataType:"json",
			async: false,
		    error: function(request) {
		        alert("由于网络原因请求失败");
		    },
		    success: function(data) {
			    if(data.loginState == "failed"){
				    alert("用户名或密码不正确，登陆失败");
			    }else{
				    window.location.replace("index.jsp");
			    }
				    
		    }
		});
	};

};

</script>
<style type="text/css">
body,form,p {
	margin: 0;
	padding: 0;
}

body {
	font: 10pt "Lucida Sans Unicode", "Lucida Grande", sans-serif;
	background: #383838;
}

div#logo p {
	display: none;
}

div#logo {
	background: url(images/logo_login.png) no-repeat;
	width: 174px;
	height: 48px;
	margin: 0 auto 55px auto;
}

div#logo h1 {
	font-size: 18pt;
}

div#logo h1  a {
	display: block;
	height: 48px;
}

div#logo h1 span {
	display: none;
}

h1,h2 {
	margin: 0;
	padding: 0;
}

#container {
	width: 980px;
	background: url(images/bg_login.jpg) no-repeat top center;
	margin: 0 auto;
	padding-top: 49px;
	height: 355px
}

label.smallInput {
	background: url(images/bg_s_input.gif) no-repeat;
	width: 168px;
}

label.smallInput,label.mediumInput,label.largeInput {
	padding: 4px 6px 0px 6px;
	height: 23px;
	display: block;
	margin: 5px 0 0 0;
}

label.smallInput input,label.mediumInput input,label.largeInput input {
	background: none;
	border: none;
	font-size: 0.9em;
	color: #666;
}

#loginForm {
	background: url(images/bg_loginBox.jpg) no-repeat;
	width: 421px;
	height: 160px;
	margin: 0 auto 0 auto;
	padding: 10px
}

#loginForm h2 {
	color: #fff;
	display: block;
	width: 180px;
	float: left;
	font-size: 14pt
}

p#forgotPass {
	float: right;
	margin-right: 15px;
	margin-top: 3px
}

p#forgotPass a {
	color: #100d00;
}

p#forgotPass a:hover {
	text-decoration: none;
}

div#loginContainer {
	clear: both;
	width: 100%;
	padding-top: 25px;
	color: #666;
	padding-left: 7px
}

p#user,p#pass,p#remember,p#submit {
	float: left;
	margin-right: 30px;
	margin-bottom: 20px
}

.versionTips{
	text-align: center;
	color:white;
}

</style>
</head>

<body>
	<div id="container">
		<div id="logo">
			<h1>
				<a href="javascript:void(0);"><span>Smart AD</span></a>
			</h1>
			<div style="width: 400px; color: rgb(218, 206, 192); font-weight: bold; float: left; position: relative; font-size: 15px; left: -25px;">黄石电子站牌多媒体发布系统</div>
			<p>Smart AD</p>
		</div>
		<div class="versionTips"><span>* 为了保证显示效果，请使用IE9以上版本或火狐10.0以上版本</span></div>
		<form id="loginForm" name="loginForm" method="post">
			<h2>登录系统</h2>
			<!-- 
			<p id="forgotPass"><a href="#">密码忘记了?</a></p>
			-->
			<div id="loginContainer">
				<p id="user">
					用户名:<br />
					<label class="smallInput">
						<input id="userName" name="userName" type="text" size="28" />
					</label>
				</p>
				<p id="pass">
					密码: <br />
					<label class="smallInput">
						<input id="userPwd"	name="userPwd" type="password" size="28" />
					</label>
				</p>
				<p id="remember" style="width: 275px; visibility: hidden;">
					<label>
						<input type="checkbox" name="checkbox" id="checkbox" disabled="disabled"/>
					</label>
					记住我
				</p>
				<p id="submit">
					<label>
						<img onclick="javascript:Login.loginSubmit();" id="imageField" src="images/bt_submit.gif" />
					</label>
				</p>
			</div>
		</form>
		<h2>&nbsp;</h2>
	</div>
	<p>&nbsp;</p>
</body>
</html>
