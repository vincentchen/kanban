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
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div style="width:95%;height:45px;margin:0px auto;border-bottom:1px solid #E5E5E5">
		<div style="width:180px;">
			<h1 style="float:left"><s:text name="pri.custom"/></h1>
		</div>
		<div style="float:right">
			<table style="float:left;">
				<tr>
					<td style="width:70px;height:45px;line-height:45px;float:left;">
						<s:if test="#session.PriConfig.ADD>0"><a href="javascript:void(0);" onclick="Add();"><img src="<%=basePath%>image/add.png" style="vertical-align:middle;"><h1 style="float:right"><s:text name="add" /></h1></a></s:if>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="width:95%;margin:0px auto;">
		<table class="gListTab">
			<tr style="border-bottom:1px solid #E5E5E5">
				<td style="width:200px;border-right:1px solid #E5E5E5">
					<h2><s:text name="pri.name" /></h2>
				</td>
				<td style="width:100px;border-right:1px solid #E5E5E5">
					<h2><s:text name="pri.define" /></h2>
				</td>
				<td style="border-right:1px solid #E5E5E5;text-align:center">
					<h2><s:text name="pri.dec" /></h2>
				</td>
				<td style="width:80px;border-right:1px solid #E5E5E5;text-align:center">
					<h2><s:text name="msg.operation" /></h2>
				</td>
			</tr>
		<s:iterator value="#request.Privilege" status="st">
			<input type="hidden" id="priID_<s:property value="#st.index" />" value="<s:property value="#Global.RegisterID('Privilege',#request.Privilege[#st.index].privilege_id)" />">
			<tr style="border-bottom:1px dashed #E5E5E5">
				<td style="border-right:1px dashed #E5E5E5">
					<s:property value="#request.Privilege[#st.index].privilege_name"/>
				</td>
				<td style="border-right:1px dashed #E5E5E5">
					<s:property value="#request.Privilege[#st.index].privilege_define"/>
				</td>
				<td style="border-right:1px dashed #E5E5E5">
					<s:property value="#request.Privilege[#st.index].privilege_dec"/>
				</td>
				<td>
					<s:if test="#session.PriConfig.WRITE>0"><a href="javascript:void();" onclick="Edit(<s:property value="#st.index" />);"><img src="<%=basePath%>image/table_edit.png"><s:text name="edit" /></a></s:if>
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
		
		<s:if test="#session.PriConfig.ADD>0">
		function Add(){
			CreatePopDiv('AddPanel','70%','70%','<%=basePath%>admin/AdminPrivilegeEdit');
		}
		</s:if>
		
		<s:if test="#session.PriConfig.WRITE>0">
		function Edit(rowID){
			CreatePopDiv('AddPanel','70%','70%','<%=basePath%>admin/AdminPrivilegeEdit?id='+$("#priID_"+rowID).val());
		}
		</s:if>
	</script>
</body>
</html>