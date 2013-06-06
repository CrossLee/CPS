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

	var audioSelectAll = false;
	$("#audioCheckboxTh").bind("click",function(){
		Audio.selectedIDs = "";
		Audio.selectedNames = "";
		if(audioSelectAll){
			$("#box-2 [type='checkbox']").removeAttr("checked");//ȡ��ȫѡ
			Audio.selectedIDs = "";
			Audio.selectedNames = "";
			audioSelectAll = false;
		}else{
			$("#box-2 [type='checkbox']").each(function(){
				Audio.selectedIDs += this.id+",";
				Audio.selectedNames += this.name +"|";
				if(!this.checked){
					$("#box-2 [type='checkbox']").attr("checked",'true');//ȫѡ
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
	<span>��������</span>
</s:if>
<s:else>
	<form id="audioForm" name="audioForm" method="post" action="">
		<table class="sTable tdNowap">
			<thead>
				<tr>
					<th id="audioCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>ȫѡ</strong></th>
					<th><strong>����</strong></th>
					<th><strong>��ע</strong></th>
					<th><strong>������</strong></th>
					<th><strong>����</strong></th>
					<th><strong>����</strong></th>
					<th><strong>ɾ�� </strong></th>
					<!-- <th><strong>�༭ </strong></th> -->
					<th><strong>����</strong></th>
				</tr>
			</thead>
			<s:iterator value="audioList" id="" status="" var="audioObj">
			<tr class="oddRow">
				<td>
					<c:if test="${audioObj.canBeDelete == false}">
							����ѡ��
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
						<li><a class="hrefColor" href="javascript:unsupport();">�༭</a></li>
					</ul>
				</td>
				 -->
				<td>
					<ul>
						<li>
							<c:if test="${audioObj.canBeDelete == false}">
								<div style="cursor: help;" title="���ڽ�Ŀ���Ѿ�ʹ�õ�����Դ������ɾ������Ҫɾ��������ɾ��ʹ�õ�����Դ�Ľ�Ŀ">����ɾ��</div>
							</c:if>
							<c:if test="${audioObj.canBeDelete == true}">
								<a class="hrefColor" href="javascript:delResourceAjaxCall('${audioObj.id }','${audioObj.resourceName }','AUDIOTYPE','#box-2')">ɾ��</a>
							</c:if>
						</li>
					</ul>
				</td>
				<td>
					<ul>
						<li><a class="hrefColor" href="/resource/Audios/${audioObj.resourceName }">����</a></li>
					</ul>
				</td>
			</tr>
			</s:iterator>
		</table>
		<p>
			<a href="javascript:delSelectedResource(Audio.selectedIDs,Audio.selectedNames,'AUDIOTYPE','#box-2')" class="buttonStyle">ɾ������ѡ�����Ƶ�ļ�</a>
		</p>
	</form>
	<div>
		<a class="buttonStyle" href="javascript:scroll(0,0)">���ض���</a>
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