<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="GBK"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
    <%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript">
	var User = new function(){

		// selected users for delete
		this.selectedUsersId = "";
		this.usersSelected = function(userId){
			if(userId.checked == true){
				this.selectedUsersId += userId.id + ",";
			}else{
				this.selectedUsersId = this.selectedUsersId.replace(userId.id + ",","");
			}
		};

		// selected privileges
		this.selectedPrivilegesIDs = "1";
		this.checkboxSelectd = function(checkboxObj){
			if(checkboxObj.checked == true){
				this.selectedPrivilegesIDs += checkboxObj.id;
			}else{
				this.selectedPrivilegesIDs = this.selectedPrivilegesIDs.replace(checkboxObj.id, "");
			}
		};

		// save request url for add & edit
		this.requestUrl = "";
		this.userId = "";
		this.userPwd = "";
		this.userType = "";
		this.privileges = "";

		// add user
		this.addUser = function(){
			// set the url for add user
			this.requestUrl = "/CPS/addUserAction.html";
			this.initAlertBox();
			popupAlert("alertBoxAddUser",806,450,"content9");
		};

		// reset fields
		this.initAlertBox = function() {
			if($('#userId').attr("disabled") != "disabled"){
				$('#userId').val("");
			}
			$('#userPwd').val("");
			$('#userType').get(0).selectedIndex=0;
			$('input[name="checkbox"]').each(function(){
				this.checked = false;
			});
			this.selectedPrivilegesIDs = "1";
		};

		// save
		this.save = function(){
			User.userId = $('#userId').val();
			User.userPwd = $('#userPwd').val();
			User.userType = $('#userType').val();
			User.privileges = User.selectedPrivilegesIDs;

			if(this.userId == null || this.userId.length < 6){
				alert("�������û�ID���ҳ��ȴ��ڵ���6");
				return false;
			}
			if(this.userPwd == null || this.userPwd.length < 6){
				alert("�������û����룬�ҳ��ȴ��ڵ���6");
				return false;
			}
			if(this.privileges == "1"){
				if(confirm('ȷ���������κ�Ȩ��? ���û���ֻӵ��Ĭ�ϵġ���ҳ��Ȩ��!')) {

				}else{
				    return false;
			    }
			}
			this.postData();
		};

		// edit user
		this.edit = function(id){
			id = encodeURI(id);
			$.ajax({
				type: "POST",
				url: "/CPS/editGetUser.html",
				dataType:"json",
				data: {id:id},
				success: function(data){
					User.requestUrl = "/CPS/editUserAction.html";
					User.initAlertBox();
					popupAlert("alertBoxAddUser",806,450,"content9");

					// set properties
					$('#userId').val(data.userDTO.id).attr("disabled","disabled");
					$('#userPwd').val(data.userDTO.userPwd);
					$('#userType').val(data.userDTO.userType);
					$('#userType').trigger("change");
					$('input[name="checkbox"]').each(function(){
						if(data.userDTO.privileges.match(this.id) != null){
							this.checked = true;
							User.selectedPrivilegesIDs += this.id;
						}
					});
				},
				error:function(){
					alert("����ʧ��");
				}
			});
			
			
			$('#userId').val("");
			$('#userPwd').val("");
			$('#userType').get(0).selectedIndex=0;
			$('input[name="checkbox"]').each(function(){
				this.checked = false;
			});
			this.selectedPrivilegesIDs = "1";
		};

		// ajax post
		this.postData = function(){
			var userId = encodeURI(User.userId);
			var userPwd = User.userPwd;
			var userType = User.userType;
			var privileges = User.privileges;

			$.ajax({
				type: "POST",
				url: this.requestUrl,
				dataType:"html",
				data: {userId:userId,userPwd:userPwd,userType:userType,privileges:privileges},
				success: function(data){
					if(data == 'ERROR_ADD_USER'){
						alert("�û����Ѿ����ڣ�");
					}else{
						$.ajax({
							type: "POST",
							url: "/CPS/getUserlistAction.html",
							dataType:"html",
							success: function(data){
								alert("�û�����ɹ�");
								$("#content9").html(data);
								$("#content9").show("slow");
							},
							error:function(){
								alert($("#content9")+"����ʧ��");
							}
						});
					}
				},
				error:function(){
					alert("����û�ʧ��");
				}
			});
		};
	};

	var userSelectAll = false;
	$("#userCheckboxTh").bind("click",function(){
		User.selectedUsersId = "";
		if(userSelectAll){
			$("#userForm [type='checkbox']").removeAttr("checked");//ȡ��ȫѡ
			User.selectedUsersId = "";
			userSelectAll = false;
		}else{
			$("#userForm [type='checkbox']").each(function(){
				User.selectedUsersId += this.id+",";
				if(!this.checked){
					$("#userForm [type='checkbox']").attr("checked",'true');//ȫѡ
					userSelectAll = true;
					//return false;
				}
			});
		}
		return false;
	});
</script>
</head>
<body>
	<div>
		<a class="buttonStyle" href="javascript:User.addUser();">����û�</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:delSelectedXmlContetnAjaxCall(User.selectedUsersId,'user','content9')" class="buttonStyle">ɾ������ѡ����û�</a>
	</div>
	<br/>
	<s:if test="#request.userList.size()==1">
		<span>���������û�����</span>
	</s:if>
	<s:else>
		<form id="userForm" name="userForm" method="post" action="">
			<table class="sTable tdNowap900">
				<thead>
					<tr>
						<th id="userCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>ȫѡ</strong></th>
						<th><strong>UID</strong></th>
						<th><strong>�û���</strong></th>
						<th><strong>�ϴε�¼</strong></th>
						<th><strong>�û�����</strong></th>
						<th><strong>�û�Ȩ��</strong></th>
						<th><strong>ɾ�� </strong></th>
						<th><strong>�༭ </strong></th>
					</tr>
				</thead>
				<s:iterator value="userList" id="" status="" var="userObj">
					<c:if test="${userObj.id != sessionScope.user.id}">
						<tr class="oddRow">
							<td><input type="checkbox" id="${userObj.id }" onchange="javascript:User.usersSelected(this)"></td>
							<td>${userObj.indexid }</td>
							<td>${userObj.id }</td>
							<td>${userObj.lastLogin }</td>
							<td><c:if test='${userObj.userType == "operator"}'>����Ա</c:if><c:if test='${userObj.userType == "admin"}'>����Ա</c:if></td>
							<td>${userObj.fullPrivileges }</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:delXmlContetnAjaxCall('${userObj.id }','user','content9')">ɾ��</a></li>
								</ul>
							</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:User.edit('${userObj.id }');">�༭</a></li>
								</ul>
							</td>
						</tr>
					</c:if>
				</s:iterator>
			</table>
		</form>
	</s:else>

	<!-- add user -->
	<div id="alertBoxAddUser" style="display: none;">
		<div style="display:inline;">
			<!-- left -->
			<div id="leftDiv" style="float:left;">
				<div style="margin: 10px 0 0 20px;">�û�ID��<label class="smallInput"><input id="userId" class="inputS" type="text" maxlength="14" size="28"></label></div>
				<div style="margin: 10px 0 0 20px;">�û����룺<label class="smallInput"><input id="userPwd" class="inputS" type="password" maxlength="14" size="28"></label></div>
				<div style="margin: 10px 0 0 20px;">
					�û����ͣ�<font color="red" title="�û����ͽ�����������ʾ���û�����Ȩ����ο��ұ�ѡ��">��?��</font>
					<label class="smallInput">
						<select id="userType" name="userType" style="width:100%">
							<option value="operator">����Ա</option>
							<option value="admin">����Ա</option>
						</select>
					</label>
				</div>
			</div>
			
			<!-- right -->
			<div id="rightDiv" style="z-index: 1000;">
				<div style="margin: 10px 60px 40px; 20px;">
					<div style="margin-right: 20px; margin-left: 20px; margin-bottom: 20px;display: inline;">
						��ѡ���û�Ȩ�ޣ�
					</div>
				</div>
				<div style="margin: 10px 60px 40px; 20px;">
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					��Դ����
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="2" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					ģ�����
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="3" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					ģ����ƣ�
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="4" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					��Ŀ����
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="5" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
				</div>
				<div style="margin: 10px 60px 40px; 20px;">
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					�������
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="6" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					�������ͣ�
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="7" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					�豸����
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="8" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					�û�����
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="9" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
				</div>
			</div>
		</div>
		<div style="clear:both;padding-top: 20px;text-align: center;">
			<div style="cursor: pointer;color: #AB8617;">
				<a class="buttonStyle" id="btnSave" href="javascript:User.save();" style="margin-right:60px;">����</a>
				<a class="buttonStyle" id="btnCancel" href="javascript:User.initAlertBox();">����</a>
			</div>
		</div>
	</div>
	<!-- add user -->
	<div>
		<a class="buttonStyle" href="javascript:scroll(0,0)">���ض���</a>
	</div>
</body>
</html>