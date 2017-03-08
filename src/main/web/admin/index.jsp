<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<title><s:text name="admin.interface"/></title>
</head>
<FRAMESET rows="70,*" FRAMESPACING=0>
<FRAME ID="Title" NAME="Title" SCROLLING="NO" NORESIZE FRAMEBORDER=0 MARGINWIDTH=0 MARGINHEIGHT=0 SRC="<%=basePath%>admin/AdminTitle" TARGET="MainFrame">
<FRAME ID="MainFrame" NAME="MainFrame" SCROLLING="YES" FRAMEBORDER=0 MARGINWIDTH=0 MARGINHEIGHT=0 SRC="<%=basePath%>admin/AdminDesktop">
</FRAMESET>
</html>