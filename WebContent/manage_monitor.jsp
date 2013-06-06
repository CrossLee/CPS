<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="js/jquery.messager.js"></script>
<script src="js/jquery.progressbar.min.js"></script>

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

<script>
	var c=0;// for processbar
	var oTimer = null;

	var selectedOnlineDevice = "";
	function getselectedOnlineDevice(){
		selectedOnlineDevice = "";
		$("tbody input[type='checkbox']").each(function(){
			if(this.name == "onlineCheckbox" && this.checked){
				selectedOnlineDevice =  selectedOnlineDevice + "_" + this.title;
			}
		});
	}

	//invoke loading image 
	function loading(){
		popupAlertWithoutClose("loadingDiv", 300, 100, "content8");
	}

	//invoke clear loading image
	function unloading(){
		$('#loadingDiv_close').trigger('click');
	} 

	var ip = "";

	function pushSelectedPlaylist(playlistId){
		c = 0;
		//loading();
		$.messager.lays("250", "70");
		var htmlCon = "<font style='color:red'>��Ŀ�·��У�����ز�Ҫ�ٽ��н�Ŀ�·���</font>";
		$.messager.show("<span class='progressBar' id='progressor'>75%</span>",htmlCon,"0");
		$("#message").unbind("hover");
		$("#progressor").progressBar(c);

		if(selectedOnlineDevice != ""){
			ip = selectedOnlineDevice;
		}
		
		$.ajax({
			type: "POST",
			url: "/CPS/pushPlaylistAction.html?id="+playlistId+"&ip="+ip,
			dataType:"JSON",
			async: true,
			success: function(data){
				//unloading();
				if(data.pushStatus == "true"){
					c = 100;
					$("#progressor").progressBar(100);
					$("#message #message_content").html("<font style='color:red'>��Ŀ���ͳɹ�</font>");
					$.messager.rmmessage (3000);
					refreshAllStautes('auto');
				}else{
					// alert("��Ŀ�·�ʧ�ܣ������ԣ�");
					$.messager.rmmessage (2000);
				}
				var line = "";
				var resent = false;
				var failedIPs = "_";
				for(var key in data.result){
					if(data.result[key] == "false"){
						line = line + key + "�·�ʧ��\r\n"; 
						resent = true;
						failedIPs = failedIPs + key + "_";
					}else{
						line = line + key + "�·��ɹ�\r\n";
					}
				}
				alert(line);
				if(resent){
					if(window.confirm("�·�ʧ�ܵ��豸���·��ͣ�")){
						selectedOnlineDevice = "";
						ip = failedIPs;
						pushSelectedPlaylist(playlistId);
					}else{
					}
				}
			},
			error:function(){
				//unloading();
				alert("����ʧ��");
			}
		});
		oTimer = setInterval('processGo()', 1000);
	}

	function processGo(){
		if(c ==99){
			window.clearInterval(oTimer);
		}
		if(c ==100){
			window.clearInterval(oTimer);
		}
		if(c != 99 && c!=100){
			$("#progressor").progressBar(c);
			c++;
		}
	}

	var onlineSelectAll = false;
	var offlineSelectAll = false;
	$(function() {

		$("#accordion").accordion({ collapsible: true });
		$('#online').css("height","");
		$('#offline').css("height","");
		
		$("#onlineTable").tablesorter({
			headers:{
				0: { sorter: false},
				7: { sorter: false},
				8: { sorter: false},
				9: { sorter: false}
			}
		}).tablesorterPager({container : $("#onlinePager")}); //page function
		$("#offlineTable").tablesorter({
			headers:{
				0: { sorter: false},
				7: { sorter: false},
				8: { sorter: false},
				9: { sorter: false}
			}
		}).tablesorterPager({container : $("#offlinePager")}); //page function

		$('#onlinePager').css("position","relative");
		$('#onlinePager').css("top","0px");
		$('#offlinePager').css("position","relative");
		$('#offlinePager').css("top","0px");
		
		// online check box
		$("#onlineCheckboxTh").removeAttr("class");
		$("#onlineCheckboxTh").bind("mousedown",function(){
			if(onlineSelectAll){
				$("[name='onlineCheckbox']").removeAttr("checked");//ȡ��ȫѡ
				onlineSelectAll = false;
			}else{
				$("[name='onlineCheckbox']").each(function(){
					if(!this.checked){
						$("[name='onlineCheckbox']").attr("checked",'true');//ȫѡ
						onlineSelectAll = true;
						return false;
					}
				});
			}
			return false;
		});
		
		// offline check box
		$("#offlineCheckboxTh").removeAttr("class");
		$("#offlineCheckboxTh").bind("mousedown",function(){
			if(offlineSelectAll){
				$("[name='offlineCheckbox']").removeAttr("checked");//ȡ��ȫѡ
				offlineSelectAll = false;
			}else{
				$("[name='offlineCheckbox']").each(function(){
					if(!this.checked){
						$("[name='offlineCheckbox']").attr("checked",'true');//ȫѡ
						offlineSelectAll = true;
						return false;
					}
				});
			}
			return false;
		});

		setIntervalObj = setInterval("refreshDevice();", 1000*2*60);
	});

	function refreshAllStautes(type){
		if(type=="clicked"){
			alert("����豸�϶࣬ˢ��ʱ������Գ��������ĵȴ�ˢ�½��������");
		}
		$.ajax({
			type: "POST",
			url: "/CPS/refreshAllStautesAction.html",
			dataType:"JSON",
			async: true,
			success: function(data){
				if(data.refreshAllStautesResult == "true"){
					refreshDevice();
					if(type=="clicked"){
						alert("ˢ�������豸�ɹ���");
					}
				}else{
					alert("ˢ��ʧ�ܣ������³��ԣ�");
				}
			},
			error:function(){
				alert("����ʧ��");
			}
		});
	}
	
	function refreshDevice(){
		$.ajax({
			type: "POST",
			url: "/CPS/refreshMinitorAction.html",
			dataType:"html",
			success: function(data){
				$('#refreshContent').html(data);
				$('#onlineH3Refresh').html($('#onlineNumber').html());
				$('#onlineTbodyRefresh').html($('#onlineDevice tbody').html());
				$('#offlineH3Refresh').html($('#offlineNumber').html());
				$('#offlineTbodyRefresh').html($('#offlineDevice tbody').html());
				$("#onlineTable").trigger("update");
				$("#offlineTable").trigger("update");
				$("#onlineTable").tablesorter({
					headers:{
						0: { sorter: false},
						7: { sorter: false},
						8: { sorter: false},
						9: { sorter: false}
					}
				}).tablesorterPager({container : $("#onlinePager")}); //page function
				$("#offlineTable").tablesorter({
					headers:{
						0: { sorter: false},
						7: { sorter: false},
						8: { sorter: false},
						9: { sorter: false}
					}
				}).tablesorterPager({container : $("#offlinePager")}); //page function

				$('#onlinePager').css("position","relative");
				$('#onlinePager').css("top","0px");
				$('#offlinePager').css("position","relative");
				$('#offlinePager').css("top","0px");
				var content = $.trim($('#newOnOrOfflineTips').html());
				
				if(content!=''&&content!=null){
					$.messager.lays(200, 200);
					$.messager.anim('fade', 2000); 
					$.messager.show('<font color="gray">�豸����������</font>', '<font color="red" style="font-size:10px;">'+$('#newOnOrOfflineTips').html()+'</font>');  
				}
			},
			error:function(){
				alert("ҳ��ˢ��ʧ��");
			}
		});
	}

	var Monitor = new function(){
		// search
		this.search = function(){
			var searchWords = $('#searchWords').val().trim();
			if(searchWords != null && searchWords != ""){
				$.ajax({
					type: "POST",
					url: "/CPS/searchMinitorAction.html",
					data: {searchWords:searchWords},
					dataType:"html",
					success: function(data){
						//alert(data);
						$('#searchResult').html(data);
					},
					error:function(){
						alert("ҳ��ˢ��ʧ��");
					}
				});
			}else{
				alert("�����������ؼ���");
				return;
			}
		};

		// clear result list
		this.clear = function(){
			$('#searchResult').html("");
		};

		// push one playlist to one device
		this.pushPlaylist = function(ipAddress){
			ip = ipAddress;
			popupAlert('pushPlaylistPopup',900,350,'content8');
			$("#pushPlaylistPopup").css("height","");
		};
		
		// push one playlist to all selected devices 
		this.pushAllPlaylist = function(){
			getselectedOnlineDevice();
			if(selectedOnlineDevice.length > 5){
				popupAlert('pushPlaylistPopup',900,350,'content8');
				$("#pushPlaylistPopup").css("height","");
			}else{
				alert("��ѡ������һ���豸���ٽ������Ͳ���");
			}
		};

		this.moreOperation = function(deviceId){
			$.ajax({
				type: "POST",
				url: "/CPS/monitorOperationAction.html",
				data: {deviceId:deviceId},
				dataType:"html",
				success: function(data){
					$('#details').html(data);
				},
				error:function(){
					alert("��ȡ�豸��Ϣʧ��");
				}
			});
			popupAlert('moreOperationDiv',900,450,'content8');
		};

		// fresh one device
		this.refreshSelectedDevice = function(ipAddress){
			$.ajax({
				type: "POST",
				url: "/CPS/refreshSelectedDeviceAction.html",
				data: {ip:ipAddress},
				dataType:"json",
				success: function(data){
					if(data.refreshStatus == "success"){
						alert("ˢ�³ɹ�!");
					}else{
						alert("ˢ�³ɹ�,�豸������");
					}
					refreshDevice();
				},
				error:function(){
					alert("�����豸��Ϣʧ�ܣ������豸��������״̬");
				}
			});
		};

		// pull default pic
		this.pullDef = function(ipAddress){
			var imageName = "";

			$("#imageSourceLists a").unbind("click");
			//$("#imageSourceList").remove();
			$("#imageSourceLists").dialog({
				height:520,
				width:900,
				modal:true
			});
			
			$("#imageSourceLists a").click(function(){
				$('#imageSourceLists').dialog("destroy");
				imageName =  this.title;
				if(imageName != "" && imageName != "undefined"){
					$.ajax({
						type: "POST",
						url: "/CPS/pullDefaultPicAction.html",
						data: {ip:ipAddress,imageName:imageName},
						dataType:"json",
						success: function(data){
							if(data.pulledStatus == "true"){
								alert("���óɹ�!");
							}else{
								alert("����ʧ��!����ϵ����Ա");
							}
						},
						error:function(){
							alert("����ʧ�ܣ�����ϵ����Ա");
						}
					});
				}
			});
		};
	};
</script>

<style type="text/css">
	.pager{
		position: relative;!important;
		top: 0px;!important;
	}
</style>

</head>
<body>
<div>
	<div>
		<input id="searchWords" type="text" />&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="buttonStyle" href="javascript:Monitor.search();">ģ������</a>
		<a class="buttonStyle" href="javascript:Monitor.clear();">����������</a>
		<a class="buttonStyle" style="display: none;" href="javascript:refreshAllStautes('clicked');">ˢ�������豸</a>
	</div>
	<div>
		<table>
			<tr><td>${data.result.key }</td><td>${result.value }</td></tr>
		</table>
	</div>
	<div id="searchResult" style="border: 1px;">
		
	</div>
	<div id="needRefresh">
	
	<div id="accordion" style="width: 1050px;">
		<h3><a id="onlineTitle" href="#">�����豸(<span style="color:red;" id="onlineH3Refresh">${fn:length(onlineDevice)}</span>)</a></h3>
		<div id="online">
				<div style="font-size: 0.9em;">
					<a class="buttonStyle" style="color:white;" href="javascript:Monitor.pushAllPlaylist();">�������²���</a>
					<!-- <a class="hrefColor" href="manage_monitor_excel.jsp?deviceType=online"><span class="hrefColor" >���� Excel</span></a> -->
				
				</div>
				<hr style="display: block;margin-bottom: 20px;"/>
				<table id="onlineTable" class="sTable tablesorter" style="padding: 0px;">
					<thead>
						<tr>
							<th id="onlineCheckboxTh" style="cursor: pointer;color: #AB8617;">ȫѡ</th>
							<th class="header">����</th>
							<th class="header">������IP</th>
							<th class="header">����״̬</th>
							<th class="header">MAC��ַ</th>
							<th class="header">�·�״̬</th>
							<th class="header">���Ž�Ŀ</th>
							<!-- 
							<th class="header">�ն˽��� </th>
							 -->
							<th class="header">�豸���� </th>
							<th class="header">ˢ�� </th>
							<th class="header">Ԥ��ͼƬ</th>
							<th class="header">������Ϣ </th>
						</tr>
					</thead>
					<tbody id="onlineTbodyRefresh">
						<c:forEach items="${onlineDevice}" var="device">
						<tr class="oddRow">
							<td style="padding: 0px;width: 70px;"><input type="checkbox" id="" title="${device.ip }" name="onlineCheckbox" onchange=""></td>
							<td>${device.name }</td>
							<td>${device.ip }</td>
							<td>${device.onlineState }</td>
							<td>${device.macAdd }</td>
							<td>${device.currentPlaylist }</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:Monitor.pushPlaylist('${device.ip }');"><span class="hrefColor" >ѡ���Ŀ��</span></a></li>
								</ul>
							</td>
							<!-- 
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:unsupport();"><span class="hrefColor" >����</span></a></li>
								</ul>
							</td>
							 -->
							<c:if test="${device.availableSpace < 1000}">
								<td>${device.availableSpace } MB</td>
							</c:if>
							<c:if test="${device.availableSpace > 1000}">
								<td>${device.availableSpace/1024 } GB</td>
							</c:if>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:Monitor.refreshSelectedDevice('${device.ip }');"><span class="hrefColor" >ˢ��</span></a></li>
								</ul>
							</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:Monitor.pullDef('${device.ip }');"><span class="hrefColor" >����Ԥ��ͼƬ</span></a></li>
								</ul>
							</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:Monitor.moreOperation('${device.id }');"><span class="hrefColor" >�������</span></a></li>
								</ul>
							</td>
						</tr>
						</c:forEach>
						<tr style="display: none;"><td></td></tr>
					</tbody>
				</table>
				<div id="onlinePager" class="pager">
					<form>
						<img src="images/first.png" class="first" />
						<img src="images/prev.png" class="prev" />
						<input type="text" class="pagedisplay" />
						<img src="images/next.png" class="next" />
						<img src="images/last.png" class="last" />
						<select class="pagesize hid">
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="30">30</option>
							<option value="40">40</option>
							<option value="50" selected="selected">50</option>
						</select>
					</form>
				</div>
		</div>
		<h3><a href="#">�����豸(<span style="color:red;" id="offlineH3Refresh">${fn:length(offlineDevice)}</span>)</a></h3>
		<div id="offline">
				<!-- <a class="hrefColor" href="manage_monitor_excel.jsp?deviceType=offline"><span class="hrefColor" >���� Excel</span></a> -->
				<hr style="display: block;margin-bottom: 20px;"/>
				<table id="offlineTable" class="sTable tablesorter" style="padding: 0px;">
					<thead>
						<tr>
							<th id="offlineCheckboxTh" style="cursor: pointer;color: #AB8617;">ȫѡ</th>
							<th class="header">����</th>
							<th class="header">������IP</th>
							<th class="header">����״̬</th>
							<th class="header">MAC��ַ</th>
							<th class="header">�·�״̬</th>
							<th class="header">���Ž�Ŀ</th>
							<!-- 
							<th class="header">�ն˽��� </th>
							 -->
							<th class="header">�豸���� </th>
							<th class="header">ˢ�� </th>
							<th class="header">������Ϣ </th>
						</tr>
					</thead>
					<tbody id="offlineTbodyRefresh">
						<c:forEach items="${offlineDevice}" var="device">
						<tr class="oddRow">
							<td style="padding: 0px;width: 70px;"><input type="checkbox" id="" name="offlineCheckbox" onchange=""></td>
							<td>${device.name }</td>
							<td>${device.ip }</td>
							<td>${device.onlineState }</td>
							<td>${device.macAdd }</td>
							<td>${device.currentPlaylist }</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:unsupport();"><span class="hrefColor" >ѡ���Ŀ��</span></a></li>
								</ul>
							</td>
							<!-- 
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:unsupport();"><span class="hrefColor" >����</span></a></li>
								</ul>
							</td>
							-->
							<c:if test="${device.availableSpace < 1000}">
								<td>${device.availableSpace } MB</td>
							</c:if>
							<c:if test="${device.availableSpace > 1000}">
								<td>${device.availableSpace/1024 } GB</td>
							</c:if> 
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:Monitor.refreshSelectedDevice('${device.ip }');"><span class="hrefColor" >ˢ��</span></a></li>
								</ul>
							</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:unsupport();"><span class="hrefColor" >�������</span></a></li>
								</ul>
							</td>
						</tr>
						</c:forEach>
						<tr style="display: none;"><td></td></tr>
					</tbody>
				</table>
				<div id="offlinePager" class="pager">
					<form>
						<img src="images/first.png" class="first" />
						<img src="images/prev.png" class="prev" />
						<input type="text" class="pagedisplay" />
						<img src="images/next.png" class="next" />
						<img src="images/last.png" class="last" />
						<select class="pagesize hid">
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="30">30</option>
							<option value="40">40</option>
							<option value="50" selected="selected">50</option>
						</select>
					</form>
				</div>
		</div>
	</div>
	</div>
</div>

<!-- set interval ajax call and return the td content here  -->
<div id="refreshContent" class="hid"></div>

<!-- popup -->
<div id="pushPlaylistPopup" style="display: none;">
	<s:if test="#request.playlist.size()==0">
		<table class="sTable" style="margin: 30px 0 0 0;">
			<thead>
				<tr>
					<th>��������</th>
					<th>����ע</th>
					<th>��������</th>
					<th>��ʼʱ��</th>
					<th>����ʱ��</th>
					<th>���ѡ��</th>
				</tr>
			</thead>
			<tr class="oddRow">
				<td>��������</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</s:if>
	<s:else>
		<table class="sTable" style="margin: 30px 0 0 0;">
			<thead>
				<tr>
					<th>��������</th>
					<th>����ע</th>
					<!--
					<th>��������</th>
					<th>��ʼʱ��</th>
					<th>����ʱ��</th>
					 -->
					<th>���ѡ��</th>
				</tr>
			</thead>
			<s:iterator value="playlist" id="" status="" var="playlistObj">
			<tr class="oddRow">
				<td>${playlistObj.name }</td>
				<td>${playlistObj.remark }</td>
				<!--
				<td>
					<c:if test="${playlistObj.startnow == true}">��</c:if>
					<c:if test="${playlistObj.startnow == false}">��</c:if>
				</td>
				<td>${playlistObj.starttime }</td>
				<td>${playlistObj.endtime }</td>
				-->
				<td>
					<ul>
						<li><a title="${playlistObj.name }" class="hrefColor" href="javascript:pushSelectedPlaylist('${playlistObj.id }');"><span class="hrefColor" >���ѡ��</span></a></li>
					</ul>
				</td>
				<!-- <td><a title="${playlistObj.name }" href="javascript:unsupport();">���ѡ��</a></td>  -->
				<!-- <td><a title="${playlistObj.name }" href="javascript:pushSelectedPlaylist('${playlistObj.id }');">���ѡ��</a></td> -->
			</tr>
			</s:iterator>
		</table>
	</s:else>
</div>

<div id="moreOperationDiv" style="display: none;">
	<div id="details">
	</div>
</div>
	
<div id="loadingDiv" style="display: none;">
	��Ŀ�����·��У������ĵȴ�...
</div>

<!-- image -->
<div id="imageSourceLists" class="hid">
	<input type="hidden" id="imageFileName" value="" />
	<table class="sTable">
		<thead>
			<tr>
				<th><strong>ͼƬ����</strong></th>
				<th><strong>ͼƬ��ע</strong></th>
				<th><strong>Ԥ��</strong></th>
				<th><strong>ѡ��</strong></th>
			</tr>
		</thead>
		<s:iterator value="imageList" id="" status="" var="image">
		<tr class="oddRow">
			<td class="firstCol">${image.resourceName }</td>
			<td>${image.paramRemark }</td>
			<td class="secondCol"><span style="cursor: pointer;" id="/resource/Images/${image.resourceName }" class="preview" title="${image.resourceName }">Ԥ��</span></td>
			<td class="secondCol"><a id="${image.resourceName }" title="${image.resourceName }" href="javascript:void(0);">���ѡ��</a></td>
		</tr>
		</s:iterator>
	</table>
</div>
	
</body>
</html>