<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ page import="com.cemso.util.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Excel档案呈现方式</title>
</head>
<body>
<%
	String deviceType = request.getParameter("deviceType");
	if (deviceType != null) {
		response.setContentType("application/msexcel");
		response.setHeader("Content-disposition",
				"inline; filename=data.xls"); //attachment  
		DownExcel excel = new DownExcel();
		HSSFWorkbook wb = excel.downExcel(deviceType);
		OutputStream os = response.getOutputStream();
		wb.write(os);
		os.flush();
		os.close();
		out.clear();
		out = pageContext.pushBody();

	} else {
%>
<div align="center"><a href="TestExcel.jsp?excel=true">生成Excel</a></div>
<%
	}
%>
</body>