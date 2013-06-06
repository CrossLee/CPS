<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<style type="text/css">
.overlay {
	padding: 40px;
	width: 576px;
	display: none;
	background-image: url(images/white.png);
}

.close {
	background: url(images/close.png) no-repeat;
	position: absolute;
	top: 2px;
	right: 5px;
	display: block;
	width: 35px;
	height: 35px;
	cursor: pointer;
}

#player {
	height: 450px;
	display: block;
}
</style>
<script src="js/flowplayer/jquery.tools.min.js" type="text/javascript" charset="GBK"></script>
<script src="js/flowplayer/flowplayer-3.2.8.min.js" type="text/javascript" charset="GBK"></script>
<script type="text/javascript">

	var Video = new function(){
		// ѡ�е�id
		this.selectedIDs = "";
		this.selectedNames = "";
		
		this.checkboxSelectd = function(checkboxObj){
			if(checkboxObj.checked == true){
				this.selectedIDs += checkboxObj.id + ",";
				this.selectedNames += checkboxObj.name + "|";
			}else{
				this.selectedIDs = this.selectedIDs.replace(checkboxObj.id + ",", "");
				this.selectedNames = this.selectedNames.replace(checkboxObj.name + "|", "");
			}
		};

		// pre-view video
		this.replaceFlvUrl = function(aObj,url){
			$('#player').attr('href',url);
			var player = null;
			var player = $f("player","js/flowplayer/flowplayer-3.2.8.swf",{
			    clip: {
			        autoPlay: true,
			        autoBuffering: true
			    }
			});

			$("#flowplayerdiv").overlay( {
				// use the Apple effect for overlay
				effect : 'apple',
				// when overlay is opened, load our player
				onLoad : function() {
				},
				// when overlay is closed, unload our player
				onClose : function() {
					player.unload();
					$("#flowplayerdiv > #player").html("");
				}
			});
			
			// play the clip specified in href- attribute with Flowplayer
			$("#flowplayerdiv").overlay().load();
		};
	};

	var videoSelectAll = false;
	$("#videoCheckboxTh").bind("click",function(){
		Video.selectedIDs = "";
		Video.selectedNames = "";
		if(videoSelectAll){
			$("#box-4 [type='checkbox']").removeAttr("checked");//ȡ��ȫѡ
			Video.selectedIDs = "";
			Video.selectedNames = "";
			videoSelectAll = false;
		}else{
			$("#box-4 [type='checkbox']").each(function(){
				Video.selectedIDs += this.id+",";
				Video.selectedNames += this.name +"|";
				if(!this.checked){
					$("#box-4 [type='checkbox']").attr("checked",'true');//ȫѡ
					videoSelectAll = true;
					//return false;
				}
			});
		}
		return false;
	});
	
</script>
</head>
<body>
	<s:if test="#request.videoList.size()==0">
		<span>��������</span>
	</s:if>
	<s:else>
		<div>
			<a href="javascript:delSelectedResource(Video.selectedIDs,Video.selectedNames,'VIDEOTYPE','#box-4')" class="buttonStyle">ɾ������ѡ�����Ƶ�ļ�</a>
		</div>
		<br />
		<form id="videoForm" name="videoForm" method="post" action="">
			<table class="sTable tdNowap">
				<thead>
					<tr>
						<th id="videoCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>ȫѡ</strong></th>
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
				<s:iterator value="videoList" id="" status="index" var="videoObj">
				<tr class="oddRow">
					<td>
						<c:if test="${videoObj.canBeDelete == false}">
							����ѡ��
						</c:if>
						<c:if test="${videoObj.canBeDelete == true}">
							<input type="checkbox" id="${videoObj.id }" name="${videoObj.resourceName }" onclick="javascript:Video.checkboxSelectd(this)">
						</c:if>
					</td>
					<td title="${videoObj.resourceName }">${videoObj.resourceName }</td>
					<td title="${videoObj.paramRemark }">${videoObj.paramRemark }</td>
					<td>${videoObj.paramCreatetime }</td>
					<td>${videoObj.paramCreateby }</td>
					<td>
						<ul>
							<li><a class="hrefColor" onclick="javascript:Video.replaceFlvUrl(this,'/resource/Flvs/${videoObj.flvname }');" >Ԥ��</a></li>
						</ul>
					</td>
					<td>
						<ul>
							<li>
								<c:if test="${videoObj.canBeDelete == false}">
									<div style="cursor: help;" title="���ڽ�Ŀ���Ѿ�ʹ�õ�����Դ������ɾ������Ҫɾ��������ɾ��ʹ�õ�����Դ�Ľ�Ŀ">����ɾ��</div>
								</c:if>
								<c:if test="${videoObj.canBeDelete == true}">
									<a class="hrefColor" href="javascript:delResourceAjaxCall('${videoObj.id }','${videoObj.resourceName }','VIDEOTYPE','#box-4')">ɾ��</a>
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
							<li><a class="hrefColor" href="/resource/Videos/${videoObj.resourceName }">����</a></li>
						</ul>
					</td>
				</tr>
				</s:iterator>
			</table>
		</form>
		<div>
			<a class="buttonStyle" href="javascript:scroll(0,0)">���ض���</a>
		</div>
	</s:else>

	<!-- flowplayer div -->
	<div id="flowplayerdiv" class="overlay" style="background-image: url(images/white.png)">
		<a id="player" href="/resource/Flvs/1332676825470_2.flv"></a>
	</div>
</body>
</html>