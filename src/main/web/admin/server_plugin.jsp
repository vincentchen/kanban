<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html style="width:100%;height:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:100%;">
	<div style="margin:0 auto;width:80%;height:98%">
		<table class="gTab" style="height:100%;">
			<tr style="border-bottom:1px solid #E5E5E5;height:30px;">
				<td style="text-align:center">
					<h1><s:text name="idealism" /></h1>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<table class="gListTab">
						<tr style="border-bottom:1px solid #E5E5E5">
							<td style="text-align:center;border-right:1px solid #E5E5E5">
								<h2><s:text name="plugin.mark" /></h2>
							</td>
							<td style="text-align:center;border-right:1px solid #E5E5E5">
								<h2><s:text name="plugin.field_name"/></h2>
							</td>
							<td style="text-align:center">
								<h2><s:text name="plugin.field_value"/></h2>
							</td>
						</tr>
						<s:iterator value="#request.Plugins" status="st">
						<tr style="border-bottom:1px dashed #E5E5E5;">
							<td style="border-right:1px dashed #E5E5E5">
								<s:property value="#request.Plugins[#st.index][3]"/>
							</td>
							<td style="border-right:1px dashed #E5E5E5">
								<s:property value="#request.Plugins[#st.index][4]"/>
							</td>
							<td>
								<input type="checkbox" name="<s:property value="#request.Plugins[#st.index][0]"/>" value="<s:property value="#request.Plugins[#st.index][1]"/>" <s:if test="#request.Plugins[#st.index][2]>0">checked</s:if> <s:if test="#session.PluginPri.WRITE==0">disabled="disabled"</s:if> >
							</td>
						</tr>
						</s:iterator>
					</table>
				</td>
			</tr>
			<tr style="height:30px;">
				<td>
					<s:if test="#session.PluginPri.WRITE>0">
					<input type="button" value="<s:text name="submit" />" onclick="Submit();" style="width:100%">
					</s:if>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){  
	        $(".gListTab tr").mouseover(function(){
	        $(this).addClass("hover");}).mouseout(function(){
	        $(this).removeClass("hover");})
	        $(".gListTab tr:even").addClass("alt");
	    });
		
		<s:if test="#session.PluginPri.WRITE>0">
		function Submit(){
			var configString = "";
			$("input[type='checkbox']").each(function(){
				configString += $(this).attr("name")+":"+$(this).val()+":"+$(this).is(":checked")+",";
			});
			configString = configString.substring(0,configString.length-1);
			
			$.ajax({
				type:"POST",
				url:"AdminServerPluginSave",
				data:{
					"configString":configString
				},
				success:function(data){
					if(data != "ok"){
						alert(data);
					}else{
						window.location.reload(true);
					}
				}
			});
		}
		</s:if>
	</script>
</body>
</html>