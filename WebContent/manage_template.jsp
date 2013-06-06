<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript">
	var Template = new function(){
		// 选中的id
		this.selectedIDs = "";
		this.checkboxSelectd = function(checkboxObj){
			if(checkboxObj.checked == true){
				this.selectedIDs += checkboxObj.id +",";
			}else{
				this.selectedIDs = this.selectedIDs.replace(checkboxObj.id + ",", "");
			}
		};

		// preview template
		this.preview = function(containerId,tempId){
			// set area siez
			var size =  $('#'+tempId).attr('title');
			if(size == "4_3"){
				$('#'+tempId).css('width','800px');
				$('#'+tempId).css('height','600px');
			}
			if(size == "5_4"){
				$('#'+tempId).css('width','800px');
				$('#'+tempId).css('height','640px');
			}
			if(size == "1280_1024"){
				$('#'+tempId).css('width','800px');
				$('#'+tempId).css('height','640px');
			}
			if(size == "16_9"){
				$('#'+tempId).css('width','800px');
				$('#'+tempId).css('height','450px');
			}
			if(size == "3_4"){
				$('#'+tempId).css('width','600px');
				$('#'+tempId).css('height','800px');
			}
			if(size == "4_5"){
				$('#'+tempId).css('width','640px');
				$('#'+tempId).css('height','800px');
			}
			if(size == "9_16"){
				$('#'+tempId).css('width','450px');
				$('#'+tempId).css('height','800px');
			}

			// set each model's dimension
			var childDiv = '#' + tempId+' div';
			$(childDiv).each(function(){
				var dimensionVals = this.title.split(',');
				var leftVal = dimensionVals[0];
				var topVal = dimensionVals[1];
				var widthVal = parseInt(dimensionVals[2]) - parseInt(leftVal);
				var heightVale = parseInt(dimensionVals[3]) - parseInt(topVal);

				$(this).css("left",leftVal+"px");
				$(this).css("top",topVal+"px");
				$(this).css("width",widthVal);
				$(this).css("height",heightVale);
				$(this).css("display","");
				$(this).css("position","absolute");
			});

			var divWidthpx = $('#'+tempId).css('width');
			var divHeightpx = $('#'+tempId).css('height');

			divWidth = parseInt(divWidthpx.substring(0,divWidthpx.lastIndexOf('px')))+40;
			divHeight = parseInt(divHeightpx.substring(0,divHeightpx.lastIndexOf('px')))+40;
			
			popupAlert(''+containerId,divWidth,divHeight,'content3');
			$('#'+containerId).css('top','0px');
			$('#'+tempId).css('display','');
		};
		
	};

	var templateSelectAll = false;
	$("#templateCheckboxTh").bind("click",function(){
		Template.selectedIDs = "";
		if(templateSelectAll){
			$("#templateForm [type='checkbox']").removeAttr("checked");//取消全选
			Template.selectedIDs = "";
			templateSelectAll = false;
		}else{
			$("#templateForm [type='checkbox']").each(function(){
				Template.selectedIDs += this.id+",";
				if(!this.checked){
					$("#templateForm [type='checkbox']").attr("checked",'true');//全选
					templateSelectAll = true;
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
	<a class="buttonStyle" href="javascript:menuSelected(4);">添加模板</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:delSelectedXmlContetnAjaxCall(Template.selectedIDs,'template','content3')" class="buttonStyle">删除所有选择的模板</a>
</div>
<br>
<s:if test="#request.templateList.size()==0">
	<span>暂无数据</span>
</s:if>
<s:else>
	<form id="templateForm" name="templateForm" method="post" action="">
		<table class="sTable tdNowap900">
			<thead>
				<tr>
					<th id="templateCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>全选</strong></th>
					<th><strong>名称</strong></th>
					<th><strong>备注</strong></th>
					<th><strong>最后更新</strong></th>
					<th><strong>作者</strong></th>
					<th><strong>屏幕比例 </strong></th>
					<th><strong>预览</strong></th>
					<th><strong>模块列表</strong></th>
					<th><strong>删除 </strong></th>
				</tr>
			</thead>
			<s:iterator value="templateList" id="" status="" var="templateObj">
			<tr class="oddRow">
				<td>
					<c:if test="${templateObj.canBeDelete == false}">
							不能选择
					</c:if>
					<c:if test="${templateObj.canBeDelete == true}">
						<input type="checkbox" id="${templateObj.id }" onchange="javascript:Template.checkboxSelectd(this)">
					</c:if>
				</td>
				<td>${templateObj.name }</td>
				<td>${templateObj.remark }</td>
				<td>${templateObj.createTime }</td>
				<td>${templateObj.createBy }</td>
				<td>${templateObj.size }</td>
				<td>
					<ul>
						<li>
							<a class="hrefColor" href="javascript:Template.preview('container_${templateObj.id }','div_${templateObj.id }');">预览</a>
							<div id="container_${templateObj.id }">
								<div style="position: relative; display: none;border-style: solid;margin-left: 20px;margin-top: 30px;" id="div_${templateObj.id }" title="${templateObj.size }">
									<s:iterator value="#templateObj.models" var="model">
										<c:if test="${model.type =='weather'}">
											<div title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; text-align:center;font-size:12px;color:white;background:gray;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}">天气区域</div>
										</c:if>
										<c:if test="${model.type =='clock'}">
											<div title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; text-align:center;font-size:12px;color:white;background:gray;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}">时钟区域</div>
										</c:if>
										<c:if test="${model.type =='video'}">
											<div title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; text-align:center;font-size:12px;color:white;background:red;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}">视频区域</div>
										</c:if>
										<c:if test="${model.type =='image'}">
											<div title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; text-align:center;font-size:12px;color:white;background:blue;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}">图片区域</div>
										</c:if>
										<c:if test="${model.type =='audio'}">
											<div title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; text-align:center;font-size:12px;color:white;background:black;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}">音频区域</div>
										</c:if>
										<c:if test="${model.type =='text'}">
											<div title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; text-align:center;font-size:12px;color:white;background:green;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}">文本区域</div>
										</c:if>
									</s:iterator>
								</div>
							</div>
						</li>
					</ul>
				</td>
				<td>
					<ul>
						<li>
							<a class="hrefColor" onmouseout="javascript:hidTip('modeldiv_${templateObj.id }');" onmouseover="javascript:showTip('modeldiv_${templateObj.id }');" href="javascript:void(0);">模块列表</a>
							<div id="modeldiv_${templateObj.id }" style="margin-left:60px;width:240px;display: none;z-index: 3000;background: gray;position: absolute;">
								<s:iterator value="#templateObj.models" status="indexId" var="model">
									<div style="float: left;clear: both;color: white;text-align: left;">
										<c:if test="${model.type=='video'}">
											${indexId.index+1 }.视频模块&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;坐标：${model.dimension }
										</c:if>
										<c:if test="${model.type=='image'}">
											${indexId.index+1 }.图片模块&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;坐标：${model.dimension }
										</c:if>
										<c:if test="${model.type=='audio'}">
											${indexId.index+1 }.音频模块&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;坐标：${model.dimension }
										</c:if>
										<c:if test="${model.type=='text'}">
											${indexId.index+1 }.文本模块&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;坐标：${model.dimension }
										</c:if>
										<c:if test="${model.type=='weather'}">
											${indexId.index+1 }.天气模块&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;坐标：${model.dimension }
										</c:if>
										<c:if test="${model.type=='clock'}">
											${indexId.index+1 }.时间模块&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;坐标：${model.dimension }
										</c:if>
									</div>
								</s:iterator>
							</div>
						</li>
					</ul>
				</td>
				<td>
					<ul>
						<li>
							<c:if test="${templateObj.canBeDelete == false}">
								<div style="cursor: help;" title="由于节目中已经使用到此模板，不能删除，若要删除，请先删除使用到此模板的节目">不能删除</div>
							</c:if>
							<c:if test="${templateObj.canBeDelete == true}">
								<a class="hrefColor" href="javascript:delXmlContetnAjaxCall('${templateObj.id }','template','content3')">删除</a>
							</c:if>
						</li>
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