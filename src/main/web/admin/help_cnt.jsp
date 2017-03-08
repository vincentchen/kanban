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
	<div style="width:95%;height:45px;margin:0px auto;border-bottom:1px solid #E5E5E5">
		<div style="width:200px"><h1 style="float:left"><s:text name="help.cnt" /></h1></div>
	</div>
	<div style="width:95%;margin:0px auto;">
		<table class="gListTab">
			<tr style="border-bottom:1px solid #E5E5E5">
				<td style="border-right:1px solid #E5E5E5">
					<h2><s:text name="users" /></h2>
				</td>
				<td style="width:150px;border-right:1px solid #E5E5E5">
					<h2><s:text name="help.cnt.seek_help" /></h2>
				</td>
				<td style="width:150px;">
					<h2><s:text name="help.cnt.helper" /></h2>
				</td>
			</tr>
		<s:iterator value="#request.CntList" status="st">
			<tr style="border-bottom:1px dashed #E5E5E5">
				<td style="border-right:1px dashed #E5E5E5">
					<s:property value="#request.CntList[#st.index].seek_help_user_name"/>
				</td>
				<td style="border-right:1px dashed #E5E5E5">
					<s:property value="#request.CntList[#st.index].seek_help_cnt"/>
				</td>
				<td>
					<s:property value="#request.CntList[#st.index].helper_cnt"/>
				</td>
			</tr>
		</s:iterator>
		</table>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){  
	    $(".gListTab tr").mouseover(function(){
	    $(this).addClass("hover");}).mouseout(function(){
	    $(this).removeClass("hover");})
	    $(".gListTab tr:even").addClass("alt");
	});
		
	</script>
</body>
</html>