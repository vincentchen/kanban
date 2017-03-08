<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body>
	<div class="desktopline boxshadow" style="float:left;">
		<ul>
			<a href="AdminDesktop" target="MainFrame">
				<li><img src="<%=basePath%>image/finder.png" />
				</li>
				<li><s:text name="msg.desktop" /></li>
			</a>
			<!-- beta版本先取消制作
			<s:if test="1==#session.ClientChatPri.READ">
				<li><img src="<%=basePath%>image/chat.png"> <span id="chat_cnt"><s:if test="0<titleInfo.ChatCnt"><s:property value="titleInfo.ChatCnt" /> </s:if></span>
				</li>
				<li><s:text name="msg.chat" /></li>
			</s:if>
			-->
		</ul>
	</div>
	<div class="usertopline boxshadow" style="float:right;">
		<ul>
			<li><a href="<%=basePath%>" target="_parent"><img src="<%=basePath%>image/application_home.png" /></a></li>
			<li><a href="javascript:void(0);" onclick="ExitSystem();"><img src="<%=basePath%>image/door_in.png"></a></li>
		</ul>
	</div>
	<script type="text/javascript">
		function ExitSystem(){
			window.parent.window.location.href = "<%=basePath%>LoginOut";
		}
	</script>
</body>
</html>