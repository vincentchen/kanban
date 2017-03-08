<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html style="width:100%;height:98%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:98%;">
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div class="boxradius" style="margin:0px auto;width:98%;height:100%;text-align:left;">
		<input id="MsgID" type="hidden" value="<s:property value="#Global.RegisterID('Msg',#request.MsgInfo.msg_id)" />">
		<div style="width:100%;border-bottom:1px solid #E5E5E5;">
			<table class="gTab">
				<tr>
					<td style="width:30px;"><a href="javascript:void(0);" onclick="javascript:history.go(-1);"><img alt="" src="<%=basePath%>image/left.png"></a></td>
					<td style="text-indent: 2em;">
						<h1>
							<s:property value="#request.MsgInfo.msg_title" />
						</h1>
					</td>
					<td style="width:100px;">
						<s:text name="author" />:<s:property value="#request.MsgInfo.create_user_name"/><br/>
						<s:date name="#request.MsgInfo.create_time" format="yyyy-MM-dd"/>
					</td>
					<%--<td style="width:80px;"><a href="javascript:void(0);" onclick="OpenDownload();"><img src="<%=basePath%>image/download2.png" /><s:text name="download" /></a>
					</td>
				--%></tr>
			</table>
		</div>
		<div style="width:95%;margin:0 auto;padding:3px">
			<s:property escape="false" value="#request.MsgInfo.msg_content" />
		</div>
	</div>
</body>
</html>