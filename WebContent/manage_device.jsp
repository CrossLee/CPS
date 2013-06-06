<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
response.setHeader("Cache-Control","no-store");
response.setDateHeader("Expires", 0);
response.setHeader("Pragma","no-cache"); 
%>
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT"> 
<meta http-equiv="content-type" content="text/html; charset=GBK" />
<script src="js/xtree2.js"></script>
<script src="js/xloadtree2.js"></script>
<link type="text/css" rel="stylesheet" href="css/xtree2.links.css">
<script type="text/javascript">

	var tree = new WebFXLoadTree("根目录", "/CPS/getTreeXmlAction.html?date="+new Date());
	$(document).ready(function(){
		/* Change the behavior of the tree */
		tree.setBehavior('explorer');
		tree.expandAll();
		$('#deviceCategory').html(tree.toHtml());
	});

	function addNode() {
		var value = trim($('#insertedGroup').val());
		if(value == null || value == ""){
			alert("请输入要添加的分组名称");
			return;
		}
		if(!tree.getSelected()){
			alert("请选择一个父分组");
			return;
		}
		if (tree.getSelected()) {
			if(tree.getSelected().getText() == "根目录"){
				alert("请在默认目录下创建子目录");
				return;
			}
			
			var fatherId = tree.getSelected().getId();
			var childId = tree.getSelected().getId() + "" + (tree.getSelected().getChildrenElement().children.length+1);
			var actionUrl = "javascript:getDeviceList('"+ childId +"','"+ value +"')";

			value = encodeURI(value);
			actionUrl = encodeURI(actionUrl);
			
			// add node to tree.xml
			$.ajax({
				type: "POST",
				url: "/CPS/addNodeToTreeXmlAction.html",
				dataType:"json",
				async: false,
				data: {fatherId:fatherId,childId:childId,value:value,actionUrl:actionUrl},
				success: function(data){
					if(data.addflag == true){
						alert("添加分组成功");
						tree.reload();
					}
				},
				error:function(){
					alert("设备列表获取失败");
				}
			});
		}
		$('#insertedGroup').val("");
	}
	function delNode() {

		//alert(tree.getSelected().parentNode);
		if (!tree.getSelected()){
			alert("请选择要删除的分组名称，并确认要删除的分组下没有设备或者分组！");
			return;
		}
		if(tree.getSelected().parentNode == null){
			alert("根目录无法删除！");
			return;
		}
		if(tree.getSelected().getId() == "default"){
			alert("默认分组无法删除！");
			return;
		}

		if(tree.getSelected().childNodes[0] != null){
			alert("请确认此分组下没有设备或者分组！");
			return;
		}

		if (tree.getSelected().childNodes[0] == null && $('#deviceDisplay').text().indexOf("此分组暂无设备") != -1){
			if (tree.getSelected()) {
				// delete node from tree.xml
				var nodeId = tree.getSelected().getId();
				$.ajax({
					type: "POST",
					url: "/CPS/delNodeToTreeXmlAction.html",
					dataType:"json",
					async: false,
					data: {nodeId:nodeId},
					success: function(data){
						if(data.deleteflag == true){
							alert("删除分组成功");
							tree.reload();
						}
					},
					error:function(){
						alert("删除失败");
					}
				});
			}
		}else{
			alert("删除分组前，请确认此分组下没有设备");
		}
		
	}
	function trim(string){
		return string.replace(/(^\s*)|(\s*$)/g, "");
	}

	var ManageDevice = new function(){
		this.save = function(){
			var groupId = $('#belongGroupId').val();
			var groupText = $('#belongGroupText').val();
			var ip = $.trim($('#ip').val());
			var deviceName = $('#deviceName').val();
			var deviceRemark = $('#deviceRemark').val();

			groupText = encodeURI(groupText);			
			deviceName = encodeURI(deviceName);			
			deviceRemark = encodeURI(deviceRemark);			
			
			// validate field
			if(!fnValidateIPAddress(ip)){
				alert("请输入正确的IP地址，格式为：192.168.1.100");
				return;
			}
			if($.trim(deviceName) == "" || $.trim(deviceName) == null){
				alert("请输入设备名称，不能含有空格");
				return;
			}
			if($.trim(deviceRemark) == "" || $.trim(deviceRemark) == null){
				alert("请输入设备备注，不能含有空格");
				return;
			}
			$.ajax({
				type: "POST",
				url: "/CPS/addDeviceAction.html",
				dataType:"html",
				async: false,
				data: {deviceName:deviceName,ip:ip,deviceRemark:deviceRemark,groupId:groupId,groupText:groupText},
				success: function(data){
					if(data.indexOf('LICENSEERROR')!=-1){
						alert("此IP设备未注册License，无法添加！");
						return;
					}
					// get the latest device list
					getDeviceList(groupId,$('#belongGroupText').val());

					// remove mask div
					$('#mask').remove();
					
					$("#addDevicePumpup").css("display","none");
					// init blank field
					$('#ip').val("");
					$('#deviceName').val("");
					$('#deviceRemark').val("");
				},
				error:function(){
					alert("没有含有此IP的设备，添加失败，请重新输入IP地址");
				}
			});
		};

		this.checkIp = function(obj){
			var ip = obj.value;
			// validate field
			if(!fnValidateIPAddress(ip)){
				alert("请输入正确的IP地址，格式为：192.168.1.100");
				obj.value = "";
				obj.focus();
				return;
			}
			
			$.ajax({
				type: "POST",
				url: "/CPS/services/root/checkIP",
				dataType:"html",
				async: false,
				data: {ip:obj.value},
				success: function(data){
					if(data == "true"){
						alert("此IP地址已经存在，请重新输入！");
						obj.value = "";
						return;
					}
					if(data == "offline"){
						alert("此IP地址不通或者设备不在线，请重新输入！");
						obj.value = "";
						return;
					}
				},
				error:function(){
					alert("由于网络原因，添加失败");
				}
			});
		};
	};
</script>
	
</head>
<body>
	<div style="float: left;overflow:auto;overflow-y:hidden;width:200px;">
		<div style="margin-bottom: 20px;display: inline;">
			<h2>分组信息</h2>
			<hr style="display: block;"/>
			
		</div>
		<div id="deviceCategory" style="margin-bottom: 20px;display: inline;">
		</div>
		<div style="float: right;position: relative;">
			<input type="text" id="insertedGroup" /><br>
			<a class="buttonStyle" href="javascript:addNode();" >添加分组</a>
			<a class="buttonStyle" href="javascript:delNode();" >删除分组</a><br>
			<a class="buttonStyle" href="javascript:tree.expandAll();">全部展开</a>
			<a class="buttonStyle" href="javascript:tree.collapseAll();">全部收起</a><br>
		</div>
	</div>
	<div style="float: left; width: 750px;margin-left: 20px;">
		<div style="float: inherit；">
			<h2>设备列表</h2>
			<hr style="display: block;"/>
		</div>
		<div id="deviceDisplay"></div>
	</div>
	<div id="addDevicePumpup" style="display: none;">
		<form id="deviceForm" action="">
			<div style="margin: 20px; display: none;"><input id="belongGroupId" type="text" value=""/></div>
			<div style="margin: 20px;">所属分组：<label class="smallInput"><input id="belongGroupText" class="inputS" disabled="disabled" type="text" size="28" /></label></div>
			<div style="margin: 20px;">IP地址：<label class="smallInput"><input onblur="ManageDevice.checkIp(this);" id="ip" class="inputS validate[required,maxSize[20]]" type="text" size="28" /></label></div>
			<div style="margin: 20px;">设备名称：<label class="smallInput"><input id="deviceName" class="inputS validate[required,maxSize[20]]" type="text" size="28" /></label></div>
			<div style="margin: 20px;">设备备注：<label class="textareaDiv"><textarea id="deviceRemark" class="textarea validate[required,maxSize[30]]" rows="5" cols="80" ></textarea><br></label></div>
					
			<div style="position: relative;left:30px;cursor: pointer;">
				<a class="buttonStyle" href="javascript:ManageDevice.save()">保存</a>
			</div>
		</form>
	</div>
</body>
</html>