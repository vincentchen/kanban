<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html style="width:100%;height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:100%">
	<div style="margin:0 auto;width:80%">
		<table class="gTab">
			<tr>
				<td colspan='2' style="text-align:center">
					<h1><s:text name="idealism" /></h1>
				</td>
				
			</tr>
			<tr>
				<td>
					<s:text name="prompt.stay_tuned" />
				</td>
			</tr>
		</table>
	</div>
</body>
</html>