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
		var htmlCon = "<font style='color:red'>节目下发中，请务必不要再进行节目下发！</font>";
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
					$("#message #message_content").html("<font style='color:red'>节目发送成功</font>");
					$.messager.rmmessage (3000);
					refreshAllStautes('auto');
				}else{
					// alert("节目下发失败，请重试！");
					$.messager.rmmessage (2000);
				}
				var line = "";
				var resent = false;
				var failedIPs = "_";
				for(var key in data.result){
					if(data.result[key] == "false"){
						line = line + key + "下发失败\r\n"; 
						resent = true;
						failedIPs = failedIPs + key + "_";
					}else{
						line = line + key + "下发成功\r\n";
					}
				}
				alert(line);
				if(resent){
					if(window.confirm("下发失败的设备重新发送？")){
						selectedOnlineDevice = "";
						ip = failedIPs;
						pushSelectedPlaylist(playlistId);
					}else{
					}
				}
			},
			error:function(){
				//unloading();
				alert("请求失败");
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
				$("[name='onlineCheckbox']").removeAttr("checked");//取消全选
				onlineSelectAll = false;
			}else{
				$("[name='onlineCheckbox']").each(function(){
					if(!this.checked){
						$("[name='onlineCheckbox']").attr("checked",'true');//全选
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
				$("[name='offlineCheckbox']").removeAttr("checked");//取消全选
				offlineSelectAll = false;
			}else{
				$("[name='offlineCheckbox']").each(function(){
					if(!this.checked){
						$("[name='offlineCheckbox']").attr("checked",'true');//全选
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
			alert("如果设备较多，刷新时间可能稍长，请耐心等待刷新结果弹出！");
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
						alert("刷新所有设备成功！");
					}
				}else{
					alert("刷新失败，请重新尝试！");
				}
			},
			error:function(){
				alert("请求失败");
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
					$.messager.show('<font color="gray">设备上下线提醒</font>', '<font color="red" style="font-size:10px;">'+$('#newOnOrOfflineTips').html()+'</font>');  
				}
			},
			error:function(){
				alert("页面刷新失败");
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
						alert("页面刷新失败");
					}
				});
			}else{
				alert("请输入搜索关键字");
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
				alert("请选择至少一个设备，再进行推送播表");
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
					alert("获取设备信息失败");
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
						alert("刷新成功!");
					}else{
						alert("刷新成功,设备已离线");
					}
					refreshDevice();
				},
				error:function(){
					alert("更新设备信息失败，或者设备处于离线状态");
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
								alert("设置成功!");
							}else{
								alert("设置失败!请联系管理员");
							}
						},
						error:function(){
							alert("设置失败，请联系管理员");
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
		<a class="buttonStyle" href="javascript:Monitor.search();">模糊搜索</a>
		<a class="buttonStyle" href="javascript:Monitor.clear();">清空搜索结果</a>
		<a class="buttonStyle" style="display: none;" href="javascript:refreshAllStautes('clicked');">刷新所有设备</a>
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
		<h3><a id="onlineTitle" href="#">在线设备(<span style="color:red;" id="onlineH3Refresh">${fn:length(onlineDevice)}</span>)</a></h3>
		<div id="online">
				<div style="font-size: 0.9em;">
					<a class="buttonStyle" style="color:white;" href="javascript:Monitor.pushAllPlaylist();">立即更新播表</a>
					<!-- <a class="hrefColor" href="manage_monitor_excel.jsp?deviceType=online"><span class="hrefColor" >导出 Excel</span></a> -->
				
				</div>
				<hr style="display: block;margin-bottom: 20px;"/>
				<table id="onlineTable" class="sTable tablesorter" style="padding: 0px;">
					<thead>
						<tr>
							<th id="onlineCheckboxTh" style="cursor: pointer;color: #AB8617;">全选</th>
							<th class="header">名称</th>
							<th class="header">局域网IP</th>
							<th class="header">在线状态</th>
							<th class="header">MAC地址</th>
							<th class="header">下发状态</th>
							<th class="header">播放节目</th>
							<!-- 
							<th class="header">终端截屏 </th>
							 -->
							<th class="header">设备容量 </th>
							<th class="header">刷新 </th>
							<th class="header">预置图片</th>
							<th class="header">更多信息 </th>
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
									<li><a class="hrefColor" href="javascript:Monitor.pushPlaylist('${device.ip }');"><span class="hrefColor" >选择节目单</span></a></li>
								</ul>
							</td>
							<!-- 
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:unsupport();"><span class="hrefColor" >截屏</span></a></li>
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
									<li><a class="hrefColor" href="javascript:Monitor.refreshSelectedDevice('${device.ip }');"><span class="hrefColor" >刷新</span></a></li>
								</ul>
							</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:Monitor.pullDef('${device.ip }');"><span class="hrefColor" >设置预置图片</span></a></li>
								</ul>
							</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:Monitor.moreOperation('${device.id }');"><span class="hrefColor" >更多操作</span></a></li>
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
		<h3><a href="#">离线设备(<span style="color:red;" id="offlineH3Refresh">${fn:length(offlineDevice)}</span>)</a></h3>
		<div id="offline">
				<!-- <a class="hrefColor" href="manage_monitor_excel.jsp?deviceType=offline"><span class="hrefColor" >导出 Excel</span></a> -->
				<hr style="display: block;margin-bottom: 20px;"/>
				<table id="offlineTable" class="sTable tablesorter" style="padding: 0px;">
					<thead>
						<tr>
							<th id="offlineCheckboxTh" style="cursor: pointer;color: #AB8617;">全选</th>
							<th class="header">名称</th>
							<th class="header">局域网IP</th>
							<th class="header">在线状态</th>
							<th class="header">MAC地址</th>
							<th class="header">下发状态</th>
							<th class="header">播放节目</th>
							<!-- 
							<th class="header">终端截屏 </th>
							 -->
							<th class="header">设备容量 </th>
							<th class="header">刷新 </th>
							<th class="header">更多信息 </th>
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
									<li><a class="hrefColor" href="javascript:unsupport();"><span class="hrefColor" >选择节目单</span></a></li>
								</ul>
							</td>
							<!-- 
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:unsupport();"><span class="hrefColor" >截屏</span></a></li>
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
									<li><a class="hrefColor" href="javascript:Monitor.refreshSelectedDevice('${device.ip }');"><span class="hrefColor" >刷新</span></a></li>
								</ul>
							</td>
							<td>
								<ul>
									<li><a class="hrefColor" href="javascript:unsupport();"><span class="hrefColor" >更多操作</span></a></li>
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
					<th>播表名称</th>
					<th>播表备注</th>
					<th>立即播放</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>点击选择</th>
				</tr>
			</thead>
			<tr class="oddRow">
				<td>暂无数据</td>
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
					<th>播表名称</th>
					<th>播表备注</th>
					<!--
					<th>立即播放</th>
					<th>开始时间</th>
					<th>结束时间</th>
					 -->
					<th>点击选择</th>
				</tr>
			</thead>
			<s:iterator value="playlist" id="" status="" var="playlistObj">
			<tr class="oddRow">
				<td>${playlistObj.name }</td>
				<td>${playlistObj.remark }</td>
				<!--
				<td>
					<c:if test="${playlistObj.startnow == true}">是</c:if>
					<c:if test="${playlistObj.startnow == false}">否</c:if>
				</td>
				<td>${playlistObj.starttime }</td>
				<td>${playlistObj.endtime }</td>
				-->
				<td>
					<ul>
						<li><a title="${playlistObj.name }" class="hrefColor" href="javascript:pushSelectedPlaylist('${playlistObj.id }');"><span class="hrefColor" >点击选择</span></a></li>
					</ul>
				</td>
				<!-- <td><a title="${playlistObj.name }" href="javascript:unsupport();">点击选择</a></td>  -->
				<!-- <td><a title="${playlistObj.name }" href="javascript:pushSelectedPlaylist('${playlistObj.id }');">点击选择</a></td> -->
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
	节目正在下发中，请耐心等待...
</div>

<!-- image -->
<div id="imageSourceLists" class="hid">
	<input type="hidden" id="imageFileName" value="" />
	<table class="sTable">
		<thead>
			<tr>
				<th><strong>图片名称</strong></th>
				<th><strong>图片备注</strong></th>
				<th><strong>预览</strong></th>
				<th><strong>选择</strong></th>
			</tr>
		</thead>
		<s:iterator value="imageList" id="" status="" var="image">
		<tr class="oddRow">
			<td class="firstCol">${image.resourceName }</td>
			<td>${image.paramRemark }</td>
			<td class="secondCol"><span style="cursor: pointer;" id="/resource/Images/${image.resourceName }" class="preview" title="${image.resourceName }">预览</span></td>
			<td class="secondCol"><a id="${image.resourceName }" title="${image.resourceName }" href="javascript:void(0);">点击选择</a></td>
		</tr>
		</s:iterator>
	</table>
</div>
	
</body>
</html>