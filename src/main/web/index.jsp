<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="idealism.title" /></title>
</head>
<FRAMESET rows="70,*" FRAMESPACING=0>
<FRAME ID="Title" NAME="Title" SCROLLING="NO" NORESIZE FRAMEBORDER=0 MARGINWIDTH=0 MARGINHEIGHT=0 SRC="TitleUserTitle" TARGET="MainFrame">
<FRAME ID="MainFrame" NAME="MainFrame" SCROLLING="YES" FRAMEBORDER=0 MARGINWIDTH=0 MARGINHEIGHT=0 SRC="<s:if test="#request.url != ''"><s:property value='#request.url' /></s:if><s:else>DeskTopUserDesktop</s:else>">
</FRAMESET>
</html>