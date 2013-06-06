<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>

<!--装载文件-->
<link href="uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="uploadify/swfobject.js"></script>
<script type="text/javascript" src="uploadify/jquery.uploadify.v2.1.4.min.js"></script>

<!--ready事件-->
<script type="text/javascript">
	$(document).ready(
			function() {
				
				$("#uploadify").uploadify(
						{
							'uploader' : 'uploadify/uploadify.swf',
							'script' : 'fileUpload.html;jsessionid=<%=session.getId()%>',//后台处理的请求
							'cancelImg' : 'images/cancel.png',
							'folder' : 'uploads',//您想将文件保存到的路径
							'queueID' : 'fileQueue',//与下面的id对应
							'queueSizeLimit' : 5,
							'fileDataName' : 'uploadify',
							'fileDesc' : '视频、图像、文本文件',
							/*
							'fileExt' : '*.BMP;*.JPG;*.JPEG;*.PNG;*.GIF;'+
										'*.AVI;*.WMV;*.FLV;*.MKV;*.MOV;*.3GP;*.MP4;*.MPG;*.MPEG;*.RM;*.RMVB;*.TS;*.SWF;'+
										'*.MP3;*.WAV;*.WMA;*.MID;*.MKA;*.WV;'+
										'*.TXT',
							*/
							'fileExt' : '*.BMP;*.JPG;*.PNG;*.GIF;'+
										'*.MP4;*.MPEG;*.MPG;'+
										'*.TXT',
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
									//$("#uploadify").uploadify("cancel","*");
								}
							},
							'onAllComplete' : function(event,data) {
								alert(data.filesUploaded + '个文件上传成功,'+"平均上传速度为:"+data.speed+"KB/s,"+"上传文件的总大小为:"+data.allBytesLoaded+"KB" );
							}

						});
			});
</script>
</head>
<body>
	<div id="fileQueue"></div>
	1. 请点击此按钮选择要上传的文件：<input type="file" name="uploadify" id="uploadify" /><br/>
	2. 选择好上传文件后，请点击此按钮开始上传：<a href="javascript:jQuery('#uploadify').uploadifyUpload()" class="buttonStyle" >开始上传</a>
	
	<p>
		注意：<br/>
		1. 如果没有出现<img src="images/browse.jpg">，请先安装flash插件：<a href="http://get.adobe.com/cn/flashplayer/" target="blank">下载</a>。<br/>
		2. 点击<img src="images/browse.jpg">选择好要上传的文件，点击【开始上传】。<br/>
		3. 上传视频文件时，如果发现上传进度处于100%，但未弹出上传成功窗口，这是因为后台在进行视频转码，请务必等待弹出上传成功窗口。<br/>
		4. 要上传的文件名长度不能超过50个英文字符，一个中文字符等于2个英文字符。<br/>
		5. 尽量避免使用中文文件名。
	</p>
	<!-- 
	<p>
		<a href="javascript:jQuery('#uploadify').uploadifyUpload()" class="deleteLink">开始上传</a>&nbsp;
		<a href="javascript:jQuery('#uploadify').uploadifyClearQueue()" class="deleteLink">取消所有上传</a>
	</p>
	 -->
</body>
</html>