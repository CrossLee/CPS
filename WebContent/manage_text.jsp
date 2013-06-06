<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript">
	var Text = new function() {
		// ѡ�е�id
		this.selectedIDs = "";
		this.selectedNames = "";
		this.checkboxSelectd = function(checkboxObj) {
			if (checkboxObj.checked == true) {
				this.selectedIDs += checkboxObj.id + ",";
				this.selectedNames += checkboxObj.name + "|";
			} else {
				this.selectedIDs = this.selectedIDs.replace(checkboxObj.id
						+ ",", "");
				this.selectedNames = this.selectedNames.replace(
						checkboxObj.name + "|", "");
			}
		};
	};

	var textSelectAll = false;
	$("#textCheckboxTh").bind("click",function(){
		Text.selectedIDs = "";
		Text.selectedNames = "";
		if(textSelectAll){
			$("#box-1 [type='checkbox']").removeAttr("checked");//ȡ��ȫѡ
			Text.selectedIDs = "";
			Text.selectedNames = "";
			textSelectAll = false;
		}else{
			$("#box-1 [type='checkbox']").each(function(){
				Text.selectedIDs += this.id+",";
				Text.selectedNames += this.name +"|";
				if(!this.checked){
					$("#box-1 [type='checkbox']").attr("checked",'true');//ȫѡ
					textSelectAll = true;
					//return false;
				}
			});
		}
		return false;
	});
</script>
<style type="text/css">

</style>

</head>
<body>
<s:if test="#request.textList.size()==0">
	<span>��������</span>
</s:if>
<s:else>
	<div>
		<a href="javascript:delSelectedResource(Text.selectedIDs,Text.selectedNames,'TEXTTYPE','#box-1')" class="buttonStyle">ɾ������ѡ�����Ƶ�ļ�</a>
	</div>
	<br/>
	<form id="textForm" name="textForm" method="post" action="">
	<table class="sTable tdNowap">
		<thead>
			<tr>
				<th id="textCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>ȫѡ</strong></th>
				<th><strong>����</strong></th>
				<th><strong>��ע</strong></th>
				<th><strong>������</strong></th>
				<th><strong>����</strong></th>
				<th><strong>Ԥ��</strong></th>
				<th><strong>ɾ�� </strong></th>
				<!-- <th><strong>�༭ </strong></th> -->
				<th><strong>����</strong></th>
			</tr>
		</thead>
		<s:iterator value="textList" id="" status="" var="textObj">
			<tr class="oddRow">
				<td>
					<c:if test="${textObj.canBeDelete == false}">
							����ѡ��
					</c:if>
					<c:if test="${textObj.canBeDelete == true}">
						<input type="checkbox" id="${textObj.id }"	name="${textObj.resourceName }"	onchange="javascript:Text.checkboxSelectd(this)">
					</c:if>
				</td>
				<td title="${textObj.resourceName }">${textObj.resourceName }</td>
				<td title="${textObj.resourceName }">${textObj.resourceName }</td>
				<td>${textObj.paramCreatetime }</td>
				<td>${textObj.paramCreateby }</td>
				<td><ul><li><a class="hrefColor" title="${textObj.paramRemark }" href="javascript:void(0);">Ԥ��</a></li></ul>	</td>
				<td>
					<ul>
						<li>
							<c:if test="${textObj.canBeDelete == false}">
								<div style="cursor: help;" title="���ڽ�Ŀ���Ѿ�ʹ�õ�����Դ������ɾ������Ҫɾ��������ɾ��ʹ�õ�����Դ�Ľ�Ŀ">����ɾ��</div>
							</c:if>
							<c:if test="${textObj.canBeDelete == true}">
								<a class="hrefColor" href="javascript:delResourceAjaxCall('${textObj.id }','${textObj.resourceName }','TEXTTYPE','#box-1')">ɾ��</a>
							</c:if>
						</li>
					</ul>
				</td>
				<!--<td><ul><li><a class="hrefColor" href="javascript:unsupport();">�༭</a></li></ul>	</td>-->
				<td><ul><li><a class="hrefColor" href="/resource/Texts/${textObj.resourceName }">����</a></li></ul>	</td>
			</tr>
		</s:iterator>
	</table>
	</form>
	<div>
		<a class="buttonStyle" href="javascript:scroll(0,0)">���ض���</a>
	</div>
</s:else>
</body>
</html>