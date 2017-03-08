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
			<a href="DeskTopUserDesktop" target="MainFrame">
			<li><img src="<%=basePath%>image/finder.png" /> <span id="desktop_cnt"></span>
			</li>
			<li><s:text name="msg.desktop" /></li>
			</a>
			<a href="MsgList" target="MainFrame">
			<li><img src="<%=basePath%>image/newspaper.png" /> <span id="news_cnt"><s:property value="titleInfo.MsgCnt" /></span>
			</li>
			<li><s:text name="msg.msg" /></li>
			</a>
			<s:if test="1==#session.ClientIdeaPri.READ">
				<a href="IdeaList" target="MainFrame">
				<li><img src="<%=basePath%>image/lamp.png" /> <span id="idea_cnt"><s:property value="titleInfo.IdeaCnt" /></span>
				</li>
				<li><s:text name="msg.idea" /></li>
				</a>
			</s:if>
			<s:if test="1==#session.ClientProPri.READ">
				<a href="ProjectList" target="MainFrame">
				<li><img src="<%=basePath%>image/library.png"> <span id="pro_cnt"><s:property value="titleInfo.ProCnt" /></span>
				</li>
				<li><s:text name="msg.pro" /></li>
				</a>
			</s:if>
			<s:if test="1==#session.ClientHelpPri.READ">
				<a href="HelpList?search_type=0&search=1" target="MainFrame">
				<li><img src="<%=basePath%>image/help.png"> <span id="help_cnt"><s:property value="titleInfo.HelpCnt" /></span>
				</li>
				<li><s:text name="msg.help" /></li>
				</a>
			</s:if>
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
			<a href="javascript:void(0);" onclick="OpenTitleUserPanel();">
			<li><img src="<%=basePath%>image/user.png" /></li>
			<li><s:property value="#session.user_name" /></li>
			</a>
			<a href="javascript:void(0);" onclick="OpenTitleSetting();">
			<li><img src="<%=basePath%>image/settings.png" /></li>
			</a>
			<s:if test="1==#session.KeyPri.READ">
			<a href="<%=basePath%>admin/" target="_parent">
			<li><img src="<%=basePath%>image/application_key.png" /></li>
			</a>
			</s:if>
			<a href="<%=basePath%>introduction_list.jsp" target="_parent">
				<li><img src="<%=basePath%>image/explain.png" /></li>
			</a>
			<a href="javascript:void(0);" onclick="ExitSystem();">
			<li><img src="<%=basePath%>image/door_in.png"></li>
			</a>
		</ul>
	</div>
	<script type="text/javascript">
		var mainframe = window.parent.frames["MainFrame"];
		
		function OpenTitleUserPanel(){
			CreateCrossPopDIV(mainframe,"TitleUserPanel","70%","80%","UserInfoPanel");
		}
		
		function OpenTitleSetting(){
			CreateCrossPopDIV(mainframe,"TitleSetting","70%","60%","UserSetting");
		}
		
		function ExitSystem(){
			window.parent.window.location.href = "LoginOut";
		}
	</script>
</body>
</html>