<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<table class="sTable tablesorter" style="padding: 0px;">
	<thead>
		<tr>
			<th id="onlineCheckboxTh" style="cursor: pointer; color: #AB8617;">全选</th>
			<th >名称</th>
			<th >局域网IP</th>
			<th >在线状态</th>
			<th >MAC地址</th>
			<th >下发状态</th>
			<th >播放节目</th>
			<!-- 
			<th class="header">终端截屏 </th>
			 -->
			<th class="header">设备容量 </th>
			<th >刷新</th>
			<th >更多信息</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${searchedResult}" var="device">
			<tr class="oddRow">
				<td style="padding: 0px; width: 70px;"><input type="checkbox"
					id="" name="onlineCheckbox" onchange=""></td>
				<td>${device.name }</td>
				<td>${device.ip }</td>
				<c:if test="${device.onlineState == '离线'}">
					<td style="color: red;" >${device.onlineState }</td>
				</c:if>
				<c:if test="${device.onlineState == '在线'}">
					<td style="color: green;" >${device.onlineState }</td>
				</c:if>
				<td>${device.macAdd }</td>
				<td>${device.currentPlaylist }</td>
				<td>
					<ul>
						<c:if test="${device.onlineState == '离线'}"><li><a class="hrefColor" href="javascript:alert('离线设备无法进行操作');"><span class="hrefColor">选择节目单</span></a></li></c:if>
						<c:if test="${device.onlineState == '在线'}"><li><a class="hrefColor" href="javascript:Monitor.pushPlaylist('${device.ip }');"><span class="hrefColor" >选择节目单</span></a></li></c:if>
						<li></li>
					</ul>
				</td>
				<!-- 
				<td>
					<ul>
						<c:if test="${device.onlineState == '离线'}"><li><a class="hrefColor" href="javascript:alert('离线设备无法进行操作');"><span class="hrefColor">截屏</span></a></li></c:if>
						<c:if test="${device.onlineState == '在线'}"><li><a class="hrefColor" href="javascript:unsupport();"><span class="hrefColor">截屏</span></a></li></c:if>
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
						<c:if test="${device.onlineState == '离线'}"><li><a class="hrefColor" href="javascript:alert('离线设备无法进行操作');"><span class="hrefColor">刷新</span></a></li></c:if>
						<c:if test="${device.onlineState == '在线'}"><li><a class="hrefColor" href="javascript:Monitor.refreshSelectedDevice('${device.ip }');"><span class="hrefColor" >刷新</span></a></li></c:if>
					</ul>
				</td>
				<td>
					<ul>
						<c:if test="${device.onlineState == '离线'}"><li><a class="hrefColor" href="javascript:alert('离线设备无法进行操作');"><span class="hrefColor">更多操作</span></a></li></c:if>
						<c:if test="${device.onlineState == '在线'}"><li><a class="hrefColor" href="javascript:Monitor.moreOperation('${device.id }');"><span class="hrefColor">更多操作</span></a></li></c:if>
					</ul>
				</td>
			</tr>
		</c:forEach>
		<tr style="display: none;"><td></td></tr>
	</tbody>
</table>