<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Register</title>
<script src="js/jquery-1.7.1.js" type="text/javascript" charset="GBK"></script>

<!--装载文件-->
<link href="uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="uploadify/swfobject.js"></script>
<script type="text/javascript" src="uploadify/jquery.uploadify.v2.1.4.min.js"></script>

<!--ready事件-->
<script type="text/javascript">

	$(document).ready(
			function() {
				var query = location.search.substring(1); // Get query string

				if(query == 'refresh=yes'){
					top.window.location.href = '/CPS/register.jsp';
				}
				$("#uploadify").uploadify(
						{
							'uploader' : 'uploadify/uploadify.swf',
							'script' : 'fileUpload.html?type=register&jsessionid=<%=session.getId()%>',//后台处理的请求
							'cancelImg' : 'images/cancel.png',
							'folder' : 'uploads',//您想将文件保存到的路径
							'queueID' : 'fileQueue',//与下面的id对应
							'queueSizeLimit' : 1,
							'fileDataName' : 'uploadify',
							'fileDesc' : 'LICENSE文件',
							'fileExt' : '*.txt',
							'auto' : false,
							'multi' : true,
							'simUploadLimit' : 2,
							'buttonText' : 'BROWSE',
							'onError' : function(event, ID, fileObj, errorObj) {
								alert(errorObj.type + ' Error: '+ errorObj.info);
							},
							'onSelect' : function(e, queueId, fileObj) {
								if(fileObj.name.length > 50){
									alert("文件名长度（包括后缀）不能超过50个英文字符（一个中文字符占2个英文字符），请重命名后重新选择，否则将无法使用此文件！");
								}
							},
							'onAllComplete' : function(event,data,response) {
								//alert(data.filesUploaded + '个文件上传成功,'+"平均上传速度为:"+data.speed+"KB/s,"+"上传文件的总大小为:"+data.allBytesLoaded+"KB" );
								alert("License上传成功，点击确定进行License验证...");
								$.ajax({
									type: "POST",
									url: "/CPS/checkRegisterAction.html?type=check&jsessionid=<%=session.getId()%>",
									dataType:"JSON",
									success: function(data){
										alert(data.result+"系统将跳转到登陆页面...");
										if(data.type == "valid"){
											top.window.location.href = '/CPS/login.jsp';
										}
									},
									error:function(){
										alert("检查License失败，请联系管理员！");
									}
								});
							}

						});
			});
</script>

<style type="text/css">
body,form,p {
	margin: 0;
	padding: 0;
}

body {
	font: 10pt "Lucida Sans Unicode", "Lucida Grande", sans-serif;
	background: white;
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
	cursor: default;
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
	margin: 0 auto;
	padding-top: 49px;
	height: 355px
}

.versionTips{
	text-align: center;
	color:gray;
}

</style>
</head>

<body>
	<div id="container">
		<div id="logo">
			<h1>
				<a href="javascript:void(0);"><span>Smart Admin</span></a>
			</h1>
			<p>Corporate CMS Skin</p>
		</div>
		<div class="versionTips"><span>* 为了保证显示效果，请使用IE9以上版本或火狐10.0以上版本</span></div>
		<div id="fileQueue"></div>
		<input type="file" name="uploadify" id="uploadify" />
		<a href="javascript:jQuery('#uploadify').uploadifyUpload()" class="deleteLink" style="font-size: 18px;margin-left: 10px;">开始上传</a>
		<br/>
		<br/>
		
		<p>
			<font color="red">若登录时自动跳转到此页面，说明系统还没有上传License！</font><font>若系统已上传了合法的License文件，点击登陆系统&gt;&gt;&gt;</font><a href="login.jsp">登陆</a><br/>
			<br/>
			<font color="red">警告：</font><br/>
			<font color="red">1. 请联系管理员获取合法License文件！</font><br>
			<font color="red">2. 请勿使用不合法的License文件，包括过期的License，自行修改的License等等。使用非法License文件将导致License验证失败，从而无法正常使用本平台！</font><br>
			<font color="red"></font><br>
			上传License步骤：<br/>
			1. 点击【BROWSE】按钮，选择要上传的License.txt文件。<br/>
			2. 点击【开始上传】，完成上传后会弹出上传成功对话框，点击【确定】。<br/>
			3. 系统将对License文件进行验证，若合法将跳转到登陆页面，若验证失败，请确认使用了合法的License。
		</p>
			<h2>&nbsp;</h2>
		</div>
	<p>&nbsp;</p>
</body>
</html>
