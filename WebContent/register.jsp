<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Register</title>
<script src="js/jquery-1.7.1.js" type="text/javascript" charset="GBK"></script>

<!--װ���ļ�-->
<link href="uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="uploadify/swfobject.js"></script>
<script type="text/javascript" src="uploadify/jquery.uploadify.v2.1.4.min.js"></script>

<!--ready�¼�-->
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
							'script' : 'fileUpload.html?type=register&jsessionid=<%=session.getId()%>',//��̨���������
							'cancelImg' : 'images/cancel.png',
							'folder' : 'uploads',//���뽫�ļ����浽��·��
							'queueID' : 'fileQueue',//�������id��Ӧ
							'queueSizeLimit' : 1,
							'fileDataName' : 'uploadify',
							'fileDesc' : 'LICENSE�ļ�',
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
									alert("�ļ������ȣ�������׺�����ܳ���50��Ӣ���ַ���һ�������ַ�ռ2��Ӣ���ַ�������������������ѡ�񣬷����޷�ʹ�ô��ļ���");
								}
							},
							'onAllComplete' : function(event,data,response) {
								//alert(data.filesUploaded + '���ļ��ϴ��ɹ�,'+"ƽ���ϴ��ٶ�Ϊ:"+data.speed+"KB/s,"+"�ϴ��ļ����ܴ�СΪ:"+data.allBytesLoaded+"KB" );
								alert("License�ϴ��ɹ������ȷ������License��֤...");
								$.ajax({
									type: "POST",
									url: "/CPS/checkRegisterAction.html?type=check&jsessionid=<%=session.getId()%>",
									dataType:"JSON",
									success: function(data){
										alert(data.result+"ϵͳ����ת����½ҳ��...");
										if(data.type == "valid"){
											top.window.location.href = '/CPS/login.jsp';
										}
									},
									error:function(){
										alert("���Licenseʧ�ܣ�����ϵ����Ա��");
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
		<div class="versionTips"><span>* Ϊ�˱�֤��ʾЧ������ʹ��IE9���ϰ汾����10.0���ϰ汾</span></div>
		<div id="fileQueue"></div>
		<input type="file" name="uploadify" id="uploadify" />
		<a href="javascript:jQuery('#uploadify').uploadifyUpload()" class="deleteLink" style="font-size: 18px;margin-left: 10px;">��ʼ�ϴ�</a>
		<br/>
		<br/>
		
		<p>
			<font color="red">����¼ʱ�Զ���ת����ҳ�棬˵��ϵͳ��û���ϴ�License��</font><font>��ϵͳ���ϴ��˺Ϸ���License�ļ��������½ϵͳ&gt;&gt;&gt;</font><a href="login.jsp">��½</a><br/>
			<br/>
			<font color="red">���棺</font><br/>
			<font color="red">1. ����ϵ����Ա��ȡ�Ϸ�License�ļ���</font><br>
			<font color="red">2. ����ʹ�ò��Ϸ���License�ļ����������ڵ�License�������޸ĵ�License�ȵȡ�ʹ�÷Ƿ�License�ļ�������License��֤ʧ�ܣ��Ӷ��޷�����ʹ�ñ�ƽ̨��</font><br>
			<font color="red"></font><br>
			�ϴ�License���裺<br/>
			1. �����BROWSE����ť��ѡ��Ҫ�ϴ���License.txt�ļ���<br/>
			2. �������ʼ�ϴ���������ϴ���ᵯ���ϴ��ɹ��Ի��򣬵����ȷ������<br/>
			3. ϵͳ����License�ļ�������֤�����Ϸ�����ת����½ҳ�棬����֤ʧ�ܣ���ȷ��ʹ���˺Ϸ���License��
		</p>
			<h2>&nbsp;</h2>
		</div>
	<p>&nbsp;</p>
</body>
</html>
