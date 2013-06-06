<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=GBK" />

<style type="text/css">
table {
	border-collapse: collapse;
	width: 100%;
}

td {
	border: #000000 1px solid;
	text-align: center;
}
</style>
<script type="text/javascript">
	var DeviceDisplay = new function(){

		// selected id
		this.selectedIDs = "";
		this.selectedNames = "";
		this.checkboxSelectd = function(checkboxObj){
			if(checkboxObj.checked == true){
				this.selectedIDs += checkboxObj.id +",";
				this.selectedNames += checkboxObj.name +"|";
			}else{
				this.selectedIDs = this.selectedIDs.replace(checkboxObj.id + ",", "");
				this.selectedNames = this.selectedNames.replace(checkboxObj.name + "|", "");
			}
		};
		
		this.addDevice = function(){
			popupAlert("addDevicePumpup",250,400,"content7");
			var groupId = $('#groupidHidden').val();
			var groupText = $('#groupTextHidden').val();
			$('#belongGroupId').val(groupId);
			$('#belongGroupText').val(groupText);

		};

		this.editDevice = function(deviceId, deviceName, deviceRemark){
			popupAlert("editDevicePumpup",250,300,"content7");
			$('#editDeviceId').val(deviceId);
			$('#editDeviceName').val(deviceName);
			$('#editDeviceRemark').val(deviceRemark);
		};

		this.save = function(){
			var deviceName = $('#editDeviceName').val();
			var deviceRemark = $('#editDeviceRemark').val();
			
			if($.trim(deviceName) == "" || $.trim(deviceName) == null){
				alert("�������豸���ƣ����ܺ��пո�");
				return;
			}
			if($.trim(deviceRemark) == "" || $.trim(deviceRemark) == null){
				alert("�������豸��ע�����ܺ��пո�");
				return;
			}

			//datavalue = decodeURIComponent($('#editDeviceForm').serialize(),true); 
			datavalue = encodeURI($('#editDeviceForm').serialize(),true); 
			
			$.ajax({
				type: "POST",
				url: "/CPS/editDeviceAction.html",
				dataType:"html",
				async: false,
				data: datavalue,
				success: function(data){
					$('#editDevicePumpup_close').trigger('click');
					$('#content7 #editDevicePumpup').remove();
					var groupId = $('#groupidHidden').val();
					var groupText = $('#groupTextHidden').val();
					getDeviceList(groupId,groupText);
				},
				error:function(){
					alert("���ʧ��");
				}
			});
		};

		this.delDevice = function(deviceId){
			var flag = delXmlContetnAjaxCall(deviceId,'device',null);
			if(flag){
				var groupId = $('#groupidHidden').val();
				var groupText = $('#groupTextHidden').val();
				getDeviceList(groupId,groupText);
			};
		};
		this.delSelectedDevice = function(deviceIds){
			var flag = delSelectedXmlContetnAjaxCall(deviceIds,'device','content7');
			if(flag){
				var groupId = $('#groupidHidden').val();
				var groupText = $('#groupTextHidden').val();
				getDeviceList(groupId,groupText);
			};
		};
	};

	var deviceDisplaySelectAll = false;
	$("#deviceDisplayCheckboxTh").bind("click",function(){
		DeviceDisplay.selectedIDs = "";
		DeviceDisplay.selectedNames = "";
		if(deviceDisplaySelectAll){
			$("#deviceDisplay [type='checkbox']").removeAttr("checked");//ȡ��ȫѡ
			DeviceDisplay.selectedIDs = "";
			DeviceDisplay.selectedNames = "";
			deviceDisplaySelectAll = false;
		}else{
			$("#deviceDisplay [type='checkbox']").each(function(){
				DeviceDisplay.selectedIDs += this.id+",";
				DeviceDisplay.selectedNames += this.name +"|";
				if(!this.checked){
					$("#deviceDisplay [type='checkbox']").attr("checked",'true');//ȫѡ
					deviceDisplaySelectAll = true;
					//return false;
				}
			});
		}
		return false;
	});
</script>
</head>
<body>
<a class="buttonStyle" href="javascript:DeviceDisplay.addDevice();">����豸</a>
<font style="float: right; cursor: pointer;" color="red" title="���豸�����б��У�ԭ���У�1�������IP��ַ���Ϸ������豸���粻ͨ��2����IP���豸δ��License��ע�ᡣ">��ӵ��豸�����б���?</font>
<br>
<input id="groupidHidden" type="hidden" value="" />
<input id="groupTextHidden" type="hidden" value="" />
<c:if test="${fn:length(groupDevices) == '0'}">
		�˷��������豸
	</c:if>
<c:if test="${fn:length(groupDevices) != '0'}">
	<table class="tableBorder">
		<thead>
			<tr>
				<th id="deviceDisplayCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>ȫѡ</strong></th>
				<th><strong>����</strong></th>
				<th><strong>��ע</strong></th>
				<th><strong>����״̬</strong></th>
				<th><strong>IP��ַ</strong></th>
				<th><strong>����</strong></th>
				<th><strong>ɾ��</strong></th>
				<th><strong>�༭</strong></th>
			</tr>
		</thead>
		<s:iterator value="groupDevices" id="deviceList" status="statu"
			var="deviceObj">

			<tr class="oddRow"
				<c:if test="${statu.index%2 == 0}">
					bgcolor="#f4f4f4"	
				</c:if>>
				<td><input type="checkbox" id="${deviceObj.id }" name="${deviceObj.name }"
					onchange="javascript:DeviceDisplay.checkboxSelectd(this)"></td>
				<td>${deviceObj.name }</td>
				<td>${deviceObj.remark }</td>
				<c:if test="${deviceObj.onlineState == '����'}">
					<td style="color: green;">${deviceObj.onlineState }</td>
				</c:if>
				<c:if test="${deviceObj.onlineState == '����'}">
					<td style="color: red;">${deviceObj.onlineState }</td>
				</c:if>
				<td>${deviceObj.ip }</td>
				<td>${deviceObj.gateway }</td>
				<td>
				<ul>
					<li><a class="hrefColor"
						href="javascript:DeviceDisplay.delDevice('${deviceObj.id }');">ɾ��</a></li>
				</ul>
				</td>
				<td>
				<ul>
					<li><a class="hrefColor"
						href="javascript:DeviceDisplay.editDevice('${deviceObj.id }','${deviceObj.name }','${deviceObj.remark }')">�༭</a></li>
				</ul>
				</td>
			</tr>
		</s:iterator>
	</table>
	<p>
		<!-- <a href="javascript:DeviceDisplay.delSelectedDevice(DeviceDisplay.selectedIDs)" class="deleteLink">ɾ������ѡ��Ľ�Ŀ</a> -->
	</p>
</c:if>
<div id="editDevicePumpup" style="display: none;">
	<form id="editDeviceForm" action="">
		<div style="margin: 20px; display: none;"><input id="editDeviceId" name="editDeviceId" /></div>
		<div style="margin: 20px;">�豸���ƣ�<label class="smallInput"><input id="editDeviceName" name="editDeviceName" class="inputS validate[required,maxSize[20]]" type="text" size="28" onclick="this.value=''"/></label></div>
		<div style="margin: 20px;">�豸��ע��<label class="textareaDiv"><textarea id="editDeviceRemark" name="editDeviceRemark" class="textarea validate[required,maxSize[30]]" rows="5" cols="80" onclick="this.value=''"></textarea><br></label></div>
		<div style="position: relative;left:30px;cursor: pointer;">
			<a class="buttonStyle" href="javascript:DeviceDisplay.save();">����</a>
		</div>
	</form>
</div>
</body>
</html>