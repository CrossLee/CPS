<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#shutdowntime").datetimepicker({
			dateFormat: 'yy-mm-dd',
			showSecond: true,
			timeFormat: 'hh:mm:ss',
			stepHour: 1,
			stepMinute: 1,
			stepSecond: 1
		});
		$("#boottime").datetimepicker({
			dateFormat: 'yy-mm-dd',
			showSecond: true,
			timeFormat: 'hh:mm:ss',
			stepHour: 1,
			stepMinute: 1,
			stepSecond: 1
		});
	});

	function addtimesheet(){
		var shutdowntime = $('#shutdowntime').val();
		var boottime = $('#boottime').val();

		if(shutdowntime.length != 19 || boottime.length != 19){
			alert("请选择时间！");
			return false;
		}
		
		var shutdowntimeNumber = shutdowntime.replace(/-/g, "").replace(/ /g,"").replace(/:/g,"");
		var boottimeNumber = boottime.replace(/-/g, "").replace(/ /g,"").replace(/:/g,"");
		
		if(parseInt(boottimeNumber) > parseInt(shutdowntimeNumber)){
			$('#timesheetdisplay').append(shutdowntime + "~" + boottime + "<br />");
		}else{
			alert("选择的下次自动开机时间必须大于自动关机时间！");
			return false;
		}
	}
</script>
	
	<table class="sTable" style="margin: 30px 0 0 0;border-collapse: separate;">
		<tr class="oddRow">
			<th colspan="6">设备详细信息</th>
		</tr>
		<tr class="oddRow">
			<td>设备名称：</td>
			<td>${deviceForOperation.name }</td>
			<td>MAC 地址：</td>
			<td>${deviceForOperation.macAdd }</td>
			<td>设备所在分组：</td>
			<td>${deviceForOperation.groupText }</td>
		</tr>
		<tr class="oddRow">
			<td>设备备注：</td>
			<td colspan="5">${deviceForOperation.remark }</td>
		</tr>
		<tr class="oddRow">
			<td>设备  IP：</td>
			<td>${deviceForOperation.ip }</td>
			<td>设备网关：</td>
			<td>${deviceForOperation.gateway }</td>
			<td>在线状态：</td>
			<td>${deviceForOperation.onlineState }</td>
		</tr>
		<tr class="oddRow">
			<td>开机时间：</td>
			<td>${deviceForOperation.bootTime }</td>
			<td>最后通信时间：</td>
			<td>${deviceForOperation.lastCommunicateTime }</td>
			<td>设备版本：</td>
			<td>${deviceForOperation.deviceVersion }</td>
		</tr>
		<tr class="oddRow">
			<td>下次关机时间：</td>
			<td>${deviceForOperation.autoShutdownTime }</td>
			<td>下次开机时间：</td>
			<td>${deviceForOperation.autoBootTime }</td>
			<td>设备可用空间：</td>
			<c:if test="${deviceForOperation.availableSpace < 1000}">
				<td>${deviceForOperation.availableSpace } MB</td>
			</c:if>
			<c:if test="${deviceForOperation.availableSpace > 1000}">
				<td>${deviceForOperation.availableSpace/1024 } GB</td>
			</c:if> 
			<!-- <td>${deviceForOperation.availableSpace } M</td> -->
		</tr>
		<tr class="oddRow">
			<td colspan="6"><hr style="display: block;"/></td>
		</tr>
		<!-- 
		<tr class="oddRow">
			<td colspan="6" style="text-align: left;height: 40px;" >
				定时开关机设定：
				|&nbsp;&nbsp;关机时间：<label class="smallInput" style="display: inline;"><input id="shutdowntime" class="inputS" type="text" size="28" name="shutdowntime" value="点击选择关机时间"></label>
				&nbsp;&nbsp;|&nbsp;&nbsp;开机时间：<label class="smallInput" style="display: inline;"><input id="boottime" class="inputS" type="text" size="28" name="boottime" value="点击选择开机时间"></label>
				&nbsp;&nbsp;|&nbsp;&nbsp;<input type="button" value="添加" onclick="javascript:addtimesheet();"/>
				&nbsp;&nbsp;|&nbsp;&nbsp;<input type="button" value="确定"/>
			</td>
		</tr>
		 -->
	</table>
	<div id="timesheetdisplay" style="text-align: left;height: 40px;" >
			
	</div>

