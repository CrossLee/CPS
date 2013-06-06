<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="sTable tablesorter" style="padding: 0px;">
	<thead>
		<tr>
			<th id="onlineCheckboxTh" style="cursor: pointer; color: #AB8617;">ȫѡ</th>
			<th >����</th>
			<th >������IP</th>
			<th >����״̬</th>
			<th >MAC��ַ</th>
			<th >�·�״̬</th>
			<th >���Ž�Ŀ</th>
			<!-- 
			<th class="header">�ն˽��� </th>
			 -->
			<th class="header">�豸���� </th>
			<th >ˢ��</th>
			<th >������Ϣ</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${searchedResult}" var="device">
			<tr class="oddRow">
				<td style="padding: 0px; width: 70px;"><input type="checkbox"
					id="" name="onlineCheckbox" onchange=""></td>
				<td>${device.name }</td>
				<td>${device.ip }</td>
				<c:if test="${device.onlineState == '����'}">
					<td style="color: red;" >${device.onlineState }</td>
				</c:if>
				<c:if test="${device.onlineState == '����'}">
					<td style="color: green;" >${device.onlineState }</td>
				</c:if>
				<td>${device.macAdd }</td>
				<td>${device.currentPlaylist }</td>
				<td>
					<ul>
						<c:if test="${device.onlineState == '����'}"><li><a class="hrefColor" href="javascript:alert('�����豸�޷����в���');"><span class="hrefColor">ѡ���Ŀ��</span></a></li></c:if>
						<c:if test="${device.onlineState == '����'}"><li><a class="hrefColor" href="javascript:Monitor.pushPlaylist('${device.ip }');"><span class="hrefColor" >ѡ���Ŀ��</span></a></li></c:if>
						<li></li>
					</ul>
				</td>
				<!-- 
				<td>
					<ul>
						<c:if test="${device.onlineState == '����'}"><li><a class="hrefColor" href="javascript:alert('�����豸�޷����в���');"><span class="hrefColor">����</span></a></li></c:if>
						<c:if test="${device.onlineState == '����'}"><li><a class="hrefColor" href="javascript:unsupport();"><span class="hrefColor">����</span></a></li></c:if>
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
						<c:if test="${device.onlineState == '����'}"><li><a class="hrefColor" href="javascript:alert('�����豸�޷����в���');"><span class="hrefColor">ˢ��</span></a></li></c:if>
						<c:if test="${device.onlineState == '����'}"><li><a class="hrefColor" href="javascript:Monitor.refreshSelectedDevice('${device.ip }');"><span class="hrefColor" >ˢ��</span></a></li></c:if>
					</ul>
				</td>
				<td>
					<ul>
						<c:if test="${device.onlineState == '����'}"><li><a class="hrefColor" href="javascript:alert('�����豸�޷����в���');"><span class="hrefColor">�������</span></a></li></c:if>
						<c:if test="${device.onlineState == '����'}"><li><a class="hrefColor" href="javascript:Monitor.moreOperation('${device.id }');"><span class="hrefColor">�������</span></a></li></c:if>
					</ul>
				</td>
			</tr>
		</c:forEach>
		<tr style="display: none;"><td></td></tr>
	</tbody>
</table>