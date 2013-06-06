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
			alert("��ѡ��ʱ�䣡");
			return false;
		}
		
		var shutdowntimeNumber = shutdowntime.replace(/-/g, "").replace(/ /g,"").replace(/:/g,"");
		var boottimeNumber = boottime.replace(/-/g, "").replace(/ /g,"").replace(/:/g,"");
		
		if(parseInt(boottimeNumber) > parseInt(shutdowntimeNumber)){
			$('#timesheetdisplay').append(shutdowntime + "~" + boottime + "<br />");
		}else{
			alert("ѡ����´��Զ�����ʱ���������Զ��ػ�ʱ�䣡");
			return false;
		}
	}
</script>
	
	<table class="sTable" style="margin: 30px 0 0 0;border-collapse: separate;">
		<tr class="oddRow">
			<th colspan="6">�豸��ϸ��Ϣ</th>
		</tr>
		<tr class="oddRow">
			<td>�豸���ƣ�</td>
			<td>${deviceForOperation.name }</td>
			<td>MAC ��ַ��</td>
			<td>${deviceForOperation.macAdd }</td>
			<td>�豸���ڷ��飺</td>
			<td>${deviceForOperation.groupText }</td>
		</tr>
		<tr class="oddRow">
			<td>�豸��ע��</td>
			<td colspan="5">${deviceForOperation.remark }</td>
		</tr>
		<tr class="oddRow">
			<td>�豸  IP��</td>
			<td>${deviceForOperation.ip }</td>
			<td>�豸���أ�</td>
			<td>${deviceForOperation.gateway }</td>
			<td>����״̬��</td>
			<td>${deviceForOperation.onlineState }</td>
		</tr>
		<tr class="oddRow">
			<td>����ʱ�䣺</td>
			<td>${deviceForOperation.bootTime }</td>
			<td>���ͨ��ʱ�䣺</td>
			<td>${deviceForOperation.lastCommunicateTime }</td>
			<td>�豸�汾��</td>
			<td>${deviceForOperation.deviceVersion }</td>
		</tr>
		<tr class="oddRow">
			<td>�´ιػ�ʱ�䣺</td>
			<td>${deviceForOperation.autoShutdownTime }</td>
			<td>�´ο���ʱ�䣺</td>
			<td>${deviceForOperation.autoBootTime }</td>
			<td>�豸���ÿռ䣺</td>
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
				��ʱ���ػ��趨��
				|&nbsp;&nbsp;�ػ�ʱ�䣺<label class="smallInput" style="display: inline;"><input id="shutdowntime" class="inputS" type="text" size="28" name="shutdowntime" value="���ѡ��ػ�ʱ��"></label>
				&nbsp;&nbsp;|&nbsp;&nbsp;����ʱ�䣺<label class="smallInput" style="display: inline;"><input id="boottime" class="inputS" type="text" size="28" name="boottime" value="���ѡ�񿪻�ʱ��"></label>
				&nbsp;&nbsp;|&nbsp;&nbsp;<input type="button" value="���" onclick="javascript:addtimesheet();"/>
				&nbsp;&nbsp;|&nbsp;&nbsp;<input type="button" value="ȷ��"/>
			</td>
		</tr>
		 -->
	</table>
	<div id="timesheetdisplay" style="text-align: left;height: 40px;" >
			
	</div>

