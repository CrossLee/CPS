<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#request.logs.size()==0">
	<tr>
		<td colspan="4" style="border: 1px solid; ">ÎÞÊý¾Ý</td>
	</tr>
</s:if>

<s:iterator id="" value="logs" var="logObj" status="index">
	<tr>
		<td style="border: 1px solid; ">${logObj.indexid }</td>
		<td style="border: 1px solid; ">${logObj.time }</td>
		<td style="border: 1px solid; ">${logObj.user }</td>
		<td style="border: 1px solid; ">${logObj.action }</td>
	</tr>
</s:iterator>