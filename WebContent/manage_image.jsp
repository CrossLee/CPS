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
		// 选中的id
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
			$("#imageForm [type='checkbox']").removeAttr("checked");//取消全选
			Image.selectedIDs = "";
			Image.selectedNames = "";
			imageSelectAll = false;
		}else{
			$("#imageForm [type='checkbox']").each(function(){
				Image.selectedIDs += this.id+",";
				Image.selectedNames += this.name +"|";
				if(!this.checked){
					$("#imageForm [type='checkbox']").attr("checked",'true');//全选
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
	<span>暂无数据</span>
</s:if>
<s:else>
	<div>
		<a href="javascript:delSelectedResource(Image.selectedIDs,Image.selectedNames,'IMAGETYPE','#box-3')" class="buttonStyle">删除所有选择的图片文件</a>
	</div>
	<br/>
	<form id="imageForm" name="imageForm" method="post" action="">
		<table class="sTable tdNowap">
			<thead>
				<tr>
					<th id="imageCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>全选</strong></th>
					<th><strong>名称</strong></th>
					<th><strong>备注</strong></th>
					<th><strong>最后更新</strong></th>
					<th><strong>作者</strong></th>
					<th><strong>预览</strong></th>
					<th><strong>删除 </strong></th>
					<!-- <th><strong>编辑 </strong></th> -->
					<th><strong>下载</strong></th>
				</tr>
			</thead>
			<s:iterator value="imageList" id="" status="" var="imageObj">
			<tr class="oddRow">
				<td>
					<c:if test="${imageObj.canBeDelete == false}">
						不能选择
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
						<li><span style="cursor: pointer;" id="/resource/Images/${imageObj.resourceName }" class="preview" title="${imageObj.resourceName }">预览</span></li>
					</ul>
				</td>
				<td>
					<ul>
						<li>
							<c:if test="${imageObj.canBeDelete == false}">
								<div style="cursor: help;" title="由于节目中已经使用到此资源，不能删除，若要删除，请先删除使用到此资源的节目">不能删除</div>
							</c:if>
							<c:if test="${imageObj.canBeDelete == true}">
								<a class="hrefColor" href="javascript:delResourceAjaxCall('${imageObj.id }','${imageObj.resourceName }','IMAGETYPE','#box-3')">删除</a>
							</c:if>
						</li>
					</ul>
				</td>
				<!-- 
				<td>
					<ul>
						<li><a class="hrefColor" href="javascript:unsupport();">编辑</a></li>
					</ul>
				</td>
				 -->
				<td>
					<ul>
						<li><a class="hrefColor" href="/resource/Images/${imageObj.resourceName }">下载</a></li>
					</ul>
				</td>
			</tr>
			</s:iterator>
		</table>
	</form>
</s:else>
<div>
	<a class="buttonStyle" href="javascript:scroll(0,0)">返回顶部</a>
</div>
</body>
</html>