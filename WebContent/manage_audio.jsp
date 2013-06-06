<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript">
	var Audio = new function(){
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

	var audioSelectAll = false;
	$("#audioCheckboxTh").bind("click",function(){
		Audio.selectedIDs = "";
		Audio.selectedNames = "";
		if(audioSelectAll){
			$("#box-2 [type='checkbox']").removeAttr("checked");//取消全选
			Audio.selectedIDs = "";
			Audio.selectedNames = "";
			audioSelectAll = false;
		}else{
			$("#box-2 [type='checkbox']").each(function(){
				Audio.selectedIDs += this.id+",";
				Audio.selectedNames += this.name +"|";
				if(!this.checked){
					$("#box-2 [type='checkbox']").attr("checked",'true');//全选
					audioSelectAll = true;
					//return false;
				}
			});
		}
		return false;
	});

	$("a[rel='music']").each(function(obj){
		$('#'+this.id).css('width','268px');
		$f(""+this.id, "js/flowplayer/flowplayer-3.2.8.swf", {
		    // fullscreen button not needed here
		    plugins: {
		        controls: {
		            fullscreen: false,
		            height: 30,
		            autoHide: false
		        }
		    },
		    clip: {
		        autoPlay: false,
		        // optional: when playback starts close the first audio playback
		        onBeforeBegin: function() {
		            $f("player").close();
		        }
		    }
		});
	}); 
</script>
</head>
<body>
<s:if test="#request.audioList.size()==0">
	<span>暂无数据</span>
</s:if>
<s:else>
	<form id="audioForm" name="audioForm" method="post" action="">
		<table class="sTable tdNowap">
			<thead>
				<tr>
					<th id="audioCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>全选</strong></th>
					<th><strong>名称</strong></th>
					<th><strong>备注</strong></th>
					<th><strong>最后更新</strong></th>
					<th><strong>作者</strong></th>
					<th><strong>试听</strong></th>
					<th><strong>删除 </strong></th>
					<!-- <th><strong>编辑 </strong></th> -->
					<th><strong>下载</strong></th>
				</tr>
			</thead>
			<s:iterator value="audioList" id="" status="" var="audioObj">
			<tr class="oddRow">
				<td>
					<c:if test="${audioObj.canBeDelete == false}">
							不能选择
					</c:if>
					<c:if test="${audioObj.canBeDelete == true}">
						<input type="checkbox" id="${audioObj.id }" name="${audioObj.resourceName }" onchange="javascript:Audio.checkboxSelectd(this)">
					</c:if>
				</td>
				<td title="${audioObj.resourceName }">${audioObj.resourceName }</td>
				<td title="${audioObj.paramRemark }">${audioObj.paramRemark }</td>
				<td>${audioObj.paramCreatetime }</td>
				<td>${audioObj.paramCreateby }</td>
				<td align="center" style="padding-left: 24px;">
					<div style="width: 170px;overflow: hidden;">
						<a rel="music" id="music${audioObj.id }" style="display:block;width:648px;height:30px;" href="/resource/Audios/${audioObj.resourceName }"></a>
					</div>
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
						<li>
							<c:if test="${audioObj.canBeDelete == false}">
								<div style="cursor: help;" title="由于节目中已经使用到此资源，不能删除，若要删除，请先删除使用到此资源的节目">不能删除</div>
							</c:if>
							<c:if test="${audioObj.canBeDelete == true}">
								<a class="hrefColor" href="javascript:delResourceAjaxCall('${audioObj.id }','${audioObj.resourceName }','AUDIOTYPE','#box-2')">删除</a>
							</c:if>
						</li>
					</ul>
				</td>
				<td>
					<ul>
						<li><a class="hrefColor" href="/resource/Audios/${audioObj.resourceName }">下载</a></li>
					</ul>
				</td>
			</tr>
			</s:iterator>
		</table>
		<p>
			<a href="javascript:delSelectedResource(Audio.selectedIDs,Audio.selectedNames,'AUDIOTYPE','#box-2')" class="buttonStyle">删除所有选择的音频文件</a>
		</p>
	</form>
	<div>
		<a class="buttonStyle" href="javascript:scroll(0,0)">返回顶部</a>
	</div>
	<div style="display: none" id="goTopBtn">
		<img border="0" src="images/gototop.jpg">
	</div>
	<script type="text/javascript">
		goTopEx();
	</script>
</s:else>
</body>
</html>