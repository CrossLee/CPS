<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>

<!--װ���ļ�-->
<link href="uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="uploadify/swfobject.js"></script>
<script type="text/javascript" src="uploadify/jquery.uploadify.v2.1.4.min.js"></script>

<!--ready�¼�-->
<script type="text/javascript">
	$(document).ready(
			function() {
				
				$("#uploadify").uploadify(
						{
							'uploader' : 'uploadify/uploadify.swf',
							'script' : 'fileUpload.html;jsessionid=<%=session.getId()%>',//��̨���������
							'cancelImg' : 'images/cancel.png',
							'folder' : 'uploads',//���뽫�ļ����浽��·��
							'queueID' : 'fileQueue',//�������id��Ӧ
							'queueSizeLimit' : 5,
							'fileDataName' : 'uploadify',
							'fileDesc' : '��Ƶ��ͼ���ı��ļ�',
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
									alert("�ļ������ȣ�������׺�����ܳ���50��Ӣ���ַ���һ�������ַ�ռ2��Ӣ���ַ�������������������ѡ�񣬷����޷�ʹ�ô��ļ���");
									//$("#uploadify").uploadify("cancel","*");
								}
							},
							'onAllComplete' : function(event,data) {
								alert(data.filesUploaded + '���ļ��ϴ��ɹ�,'+"ƽ���ϴ��ٶ�Ϊ:"+data.speed+"KB/s,"+"�ϴ��ļ����ܴ�СΪ:"+data.allBytesLoaded+"KB" );
							}

						});
			});
</script>
</head>
<body>
	<div id="fileQueue"></div>
	1. �����˰�ťѡ��Ҫ�ϴ����ļ���<input type="file" name="uploadify" id="uploadify" /><br/>
	2. ѡ����ϴ��ļ��������˰�ť��ʼ�ϴ���<a href="javascript:jQuery('#uploadify').uploadifyUpload()" class="buttonStyle" >��ʼ�ϴ�</a>
	
	<p>
		ע�⣺<br/>
		1. ���û�г���<img src="images/browse.jpg">�����Ȱ�װflash�����<a href="http://get.adobe.com/cn/flashplayer/" target="blank">����</a>��<br/>
		2. ���<img src="images/browse.jpg">ѡ���Ҫ�ϴ����ļ����������ʼ�ϴ�����<br/>
		3. �ϴ���Ƶ�ļ�ʱ����������ϴ����ȴ���100%����δ�����ϴ��ɹ����ڣ�������Ϊ��̨�ڽ�����Ƶת�룬����صȴ������ϴ��ɹ����ڡ�<br/>
		4. Ҫ�ϴ����ļ������Ȳ��ܳ���50��Ӣ���ַ���һ�������ַ�����2��Ӣ���ַ���<br/>
		5. ��������ʹ�������ļ�����
	</p>
	<!-- 
	<p>
		<a href="javascript:jQuery('#uploadify').uploadifyUpload()" class="deleteLink">��ʼ�ϴ�</a>&nbsp;
		<a href="javascript:jQuery('#uploadify').uploadifyClearQueue()" class="deleteLink">ȡ�������ϴ�</a>
	</p>
	 -->
</body>
</html>