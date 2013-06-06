<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="onlineNumber">${fn:length(onlineDevice)}</div>
<table id="onlineDevice">
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
			 <!-- 
			<td>${device.availableSpace } M</td>
			 
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
</table>

<div id="offlineNumber">${fn:length(offlineDevice)}</div>
<table id="offlineDevice">
	<c:forEach items="${offlineDevice}" var="device">
		<tr class="oddRow">
			<td style="padding: 0px;width: 70px;"><input type="checkbox" id="" name="offlineCheckbox" onchange=""></td>
			<td>${device.name }</td>
			<td>${device.ip }</td>
			<td>${device.onlineState }</td>
			<td>${device.bootTime }</td>
			<td>${device.macAdd }</td>
			<td>${device.currentPlaylist }</td>
			<!-- 
			<td>
				<ul>
					<li><a class="hrefColor" href="javascript:alert('离线设备无法进行操作');"><span class="hrefColor" >截屏</span></a></li>
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
					<li><a class="hrefColor" href="javascript:alert('离线设备无法进行操作');"><span class="hrefColor" >更多操作</span></a></li>
				</ul>
			</td>
		</tr>
	</c:forEach>
	<tr style="display: none;"><td></td></tr>
</table>
<div id="newOnOrOfflineTips">
	<c:forEach items="${newOnlineDevices}" var="device">
		设备：${device.name } 上线了！<br/>
	</c:forEach>
	<c:forEach items="${newOfflineDevices}" var="device">
		设备：${device.name } 下线了！<br/>
	</c:forEach>
</div>