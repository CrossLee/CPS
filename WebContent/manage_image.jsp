<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
    <%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- image preview -->
<script type="text/javascript" src="js/main.js"></script>
<style type="text/css">
	#preview {
	position: absolute;
	border: 1px solid #ccc;
	background: #333;
	padding: 5px;
	display: none;
	color: #fff;
}
</style>
<!-- image preview -->

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript">
	var Image = new function(){
		// ѡ�е�id
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
	};

	var imageSelectAll = false;
	$("#imageCheckboxTh").bind("click",function(){
		Image.selectedIDs = "";
		Image.selectedNames = "";
		if(imageSelectAll){
			$("#imageForm [type='checkbox']").removeAttr("checked");//ȡ��ȫѡ
			Image.selectedIDs = "";
			Image.selectedNames = "";
			imageSelectAll = false;
		}else{
			$("#imageForm [type='checkbox']").each(function(){
				Image.selectedIDs += this.id+",";
				Image.selectedNames += this.name +"|";
				if(!this.checked){
					$("#imageForm [type='checkbox']").attr("checked",'true');//ȫѡ
					imageSelectAll = true;
					//return false;
				}
			});
		}
		return false;
	});
	
</script>
</head>
<body>
<s:if test="#request.imageList.size()==0">
	<span>��������</span>
</s:if>
<s:else>
	<div>
		<a href="javascript:delSelectedResource(Image.selectedIDs,Image.selectedNames,'IMAGETYPE','#box-3')" class="buttonStyle">ɾ������ѡ���ͼƬ�ļ�</a>
	</div>
	<br/>
	<form id="imageForm" name="imageForm" method="post" action="">
		<table class="sTable tdNowap">
			<thead>
				<tr>
					<th id="imageCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>ȫѡ</strong></th>
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
			<s:iterator value="imageList" id="" status="" var="imageObj">
			<tr class="oddRow">
				<td>
					<c:if test="${imageObj.canBeDelete == false}">
						����ѡ��
					</c:if>
					<c:if test="${imageObj.canBeDelete == true}">
						<input type="checkbox" id="${imageObj.id }" name="${imageObj.resourceName }" onchange="javascript:Image.checkboxSelectd(this)">
					</c:if>
				</td>
				<td title="${imageObj.resourceName }">${imageObj.resourceName }</td>
				<td title="${imageObj.paramRemark }">${imageObj.paramRemark }</td>
				<td>${imageObj.paramCreatetime }</td>
				<td>${imageObj.paramCreateby }</td>
				<td>
					<ul>
						<li><span style="cursor: pointer;" id="/resource/Images/${imageObj.resourceName }" class="preview" title="${imageObj.resourceName }">Ԥ��</span></li>
					</ul>
				</td>
				<td>
					<ul>
						<li>
							<c:if test="${imageObj.canBeDelete == false}">
								<div style="cursor: help;" title="���ڽ�Ŀ���Ѿ�ʹ�õ�����Դ������ɾ������Ҫɾ��������ɾ��ʹ�õ�����Դ�Ľ�Ŀ">����ɾ��</div>
							</c:if>
							<c:if test="${imageObj.canBeDelete == true}">
								<a class="hrefColor" href="javascript:delResourceAjaxCall('${imageObj.id }','${imageObj.resourceName }','IMAGETYPE','#box-3')">ɾ��</a>
							</c:if>
						</li>
					</ul>
				</td>
				<!-- 
				<td>
					<ul>
						<li><a class="hrefColor" href="javascript:unsupport();">�༭</a></li>
					</ul>
				</td>
				 -->
				<td>
					<ul>
						<li><a class="hrefColor" href="/resource/Images/${imageObj.resourceName }">����</a></li>
					</ul>
				</td>
			</tr>
			</s:iterator>
		</table>
	</form>
</s:else>
<div>
	<a class="buttonStyle" href="javascript:scroll(0,0)">���ض���</a>
</div>
</body>
</html>