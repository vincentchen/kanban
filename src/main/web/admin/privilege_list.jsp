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
	<div style="width:95%;margin:0px auto;border-bottom:1px solid #E5E5E5">
		<h1><s:text name="pri.system"/></h1>
	</div>
	<div style="width:95%;margin:0px auto;">
		<table class="gListTab" id="sysPri">
			<tr style="border-bottom:1px solid #E5E5E5">
				<td style="width:200px;border-right:1px solid #E5E5E5">
					<h2><s:text name="pri.name" /></h2>
				</td>
				<td style="width:100px;border-right:1px solid #E5E5E5">
					<h2><s:text name="pri.define" /></h2>
				</td>
				<td style="text-align:center">
					<h2><s:text name="pri.dec" /></h2>
				</td>
			</tr>
		<s:iterator value="#request.Privilege" status="st">
			<s:if test="#request.Privilege[#st.index].privilege_id<10000">
				<tr style="border-bottom:1px dashed #E5E5E5">
					<td style="border-right:1px dashed #E5E5E5">
						<s:property value="#request.Privilege[#st.index].privilege_name"/>
					</td>
					<td style="border-right:1px dashed #E5E5E5">
						<s:property value="#request.Privilege[#st.index].privilege_define"/>
					</td>
					<td>
						<s:property value="#request.Privilege[#st.index].privilege_dec"/>
					</td>
				</tr>
			</s:if>
		</s:iterator>
		</table>
	</div>
	<div style="width:95%;height:45px;margin:0px auto;border-bottom:1px solid #E5E5E5">
		<h1><s:text name="pri.custom"/></h1>
	</div>
	<div style="width:95%;margin:0px auto;">
		<table class="gListTab" id="customPri">
			<tr style="border-bottom:1px solid #E5E5E5">
				<td style="width:200px;border-right:1px solid #E5E5E5">
					<h2><s:text name="pri.name" /></h2>
				</td>
				<td style="width:100px;border-right:1px solid #E5E5E5">
					<h2><s:text name="pri.define" /></h2>
				</td>
				<td style="text-align:center">
					<h2><s:text name="pri.dec" /></h2>
				</td>
			</tr>
		<s:iterator value="#request.Privilege" status="st">
			<s:if test="#request.Privilege[#st.index].privilege_id>=10000">
				<tr style="border-bottom:1px dashed #E5E5E5">
					<td style="border-right:1px dashed #E5E5E5">
						<s:property value="#request.Privilege[#st.index].privilege_name"/>
					</td>
					<td style="border-right:1px dashed #E5E5E5">
						<s:property value="#request.Privilege[#st.index].privilege_define"/>
					</td>
					<td>
						<s:property value="#request.Privilege[#st.index].privilege_dec"/>
					</td>
				</tr>
			</s:if>
		</s:iterator>
		</table>
	</div>
	<script type="text/javascript">
	$(document).ready(function(){  
        $(".gListTab tr").mouseover(function(){
        $(this).addClass("hover");}).mouseout(function(){
        $(this).removeClass("hover");})
        $("#sysPri tr:even").addClass("alt");
        $("#customPri tr:even").addClass("alt");
	});
	</script>
</body>
</html>