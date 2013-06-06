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
				alert("请输入用户ID，且长度大于等于6");
				return false;
			}
			if(this.userPwd == null || this.userPwd.length < 6){
				alert("请输入用户密码，且长度大于等于6");
				return false;
			}
			if(this.privileges == "1"){
				if(confirm('确定不赋予任何权限? 此用户将只拥有默认的《首页》权限!')) {

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
					alert("加载失败");
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
						alert("用户名已经存在！");
					}else{
						$.ajax({
							type: "POST",
							url: "/CPS/getUserlistAction.html",
							dataType:"html",
							success: function(data){
								alert("用户保存成功");
								$("#content9").html(data);
								$("#content9").show("slow");
							},
							error:function(){
								alert($("#content9")+"加载失败");
							}
						});
					}
				},
				error:function(){
					alert("添加用户失败");
				}
			});
		};
	};

	var userSelectAll = false;
	$("#userCheckboxTh").bind("click",function(){
		User.selectedUsersId = "";
		if(userSelectAll){
			$("#userForm [type='checkbox']").removeAttr("checked");//取消全选
			User.selectedUsersId = "";
			userSelectAll = false;
		}else{
			$("#userForm [type='checkbox']").each(function(){
				User.selectedUsersId += this.id+",";
				if(!this.checked){
					$("#userForm [type='checkbox']").attr("checked",'true');//全选
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
		<a class="buttonStyle" href="javascript:User.addUser();">添加用户</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:delSelectedXmlContetnAjaxCall(User.selectedUsersId,'user','content9')" class="buttonStyle">删除所有选择的用户</a>
	</div>
	<br/>
	<s:if test="#request.userList.size()==1">
		<span>暂无其他用户数据</span>
	</s:if>
	<s:else>
		<form id="userForm" name="userForm" method="post" action="">
			<table class="sTable tdNowap900">
				<thead>
					<tr>
						<th id="userCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>全选</strong></th>
						<th><strong>UID</strong></th>
						<th><strong>用户名</strong></th>
						<th><strong>上次登录</strong></th>
						<th><strong>用户类型</strong></th>
						<th><strong>用户权限</strong></th>
						<th><strong>删除 </strong></th>
						<th><strong>编辑 </strong></th>
					</tr>
				</thead>
				<s:iterator value="userList" id="" status="" var="userObj">
					<c:if test="${userObj.id != sessionScope.user.id}">
						<tr class="oddRow">
							<td><input type="checkbox" id="${userObj.id }" onchange="javascript:User.usersSelected(this)"></td>
							<td>${userObj.indexid }</td>
							<td>${userObj.id }</td>
							<td>${userObj.lastLogin }</td>
							<td><c:if test='${userObj.userType == "operator"}'>操作员</c:if><c:if test='${userObj.userType == "admin"}'>管理员</c:if></td>
							<td>${userObj.fullPrivileges }</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:delXmlContetnAjaxCall('${userObj.id }','user','content9')">删除</a></li>
								</ul>
							</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:User.edit('${userObj.id }');">编辑</a></li>
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
				<div style="margin: 10px 0 0 20px;">用户ID：<label class="smallInput"><input id="userId" class="inputS" type="text" maxlength="14" size="28"></label></div>
				<div style="margin: 10px 0 0 20px;">用户密码：<label class="smallInput"><input id="userPwd" class="inputS" type="password" maxlength="14" size="28"></label></div>
				<div style="margin: 10px 0 0 20px;">
					用户类型：<font color="red" title="用户类型仅用来区分显示，用户具体权限请参考右边选项">【?】</font>
					<label class="smallInput">
						<select id="userType" name="userType" style="width:100%">
							<option value="operator">操作员</option>
							<option value="admin">管理员</option>
						</select>
					</label>
				</div>
			</div>
			
			<!-- right -->
			<div id="rightDiv" style="z-index: 1000;">
				<div style="margin: 10px 60px 40px; 20px;">
					<div style="margin-right: 20px; margin-left: 20px; margin-bottom: 20px;display: inline;">
						请选择用户权限：
					</div>
				</div>
				<div style="margin: 10px 60px 40px; 20px;">
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					资源管理：
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="2" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					模板管理：
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="3" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					模板设计：
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="4" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					节目管理：
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="5" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
				</div>
				<div style="margin: 10px 60px 40px; 20px;">
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					播表管理：
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="6" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					播表推送：
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="7" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					设备管理：
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="8" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
					<div style="float: left; margin-right: 20px; margin-left: 20px; margin-bottom: 20px;">
					用户管理：
					<label class="smallInput" style="width: 20px;display: inline;">
						<input name="checkbox" id="9" class="inputS" type="checkbox" onchange="javascript:User.checkboxSelectd(this)">
					</label>
					</div>
				</div>
			</div>
		</div>
		<div style="clear:both;padding-top: 20px;text-align: center;">
			<div style="cursor: pointer;color: #AB8617;">
				<a class="buttonStyle" id="btnSave" href="javascript:User.save();" style="margin-right:60px;">保存</a>
				<a class="buttonStyle" id="btnCancel" href="javascript:User.initAlertBox();">重置</a>
			</div>
		</div>
	</div>
	<!-- add user -->
	<div>
		<a class="buttonStyle" href="javascript:scroll(0,0)">返回顶部</a>
	</div>
</body>
</html>