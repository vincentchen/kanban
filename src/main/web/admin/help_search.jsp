<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html style="width:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;overflow-y:hidden">
	<div>
		<h1>
		<s:text name="search" /></h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;">
			<table class="gTab">
				<tr>
					<td>
						<s:text name="search"/><s:text name="title"/>
					</td>
					<td>
						<input type="text" id="title" value="<s:property value="#request.Info.help_title" />" style="width:95%;">
					</td>
					
				</tr>
				<tr>
					<td>
						<s:text name="search"/><s:text name="msg.content"/>
					</td>
					<td>
						<input type="text" id="content" value="<s:property value="#request.Info.help_content" />" style="width:95%;">
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center">
						<input type="button" value="<s:text name="submit" />" onclick="Submit();">
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function Submit(){
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminHelpSaveSearch",
			data:{
				"help_title":$("#title").val(),
				"help_content":$("#content").val()
			},
			success:function SubmitOK(data){
				if(data == ""){
					alert('<s:text name="error.db" />');
					return;
				}
				var json_data = $.parseJSON(data);
				if (json_data[0].error != "") {
					alert(json_data[0].error);
				}else{
					window.parent.window.location.href = "<%=basePath%>admin/AdminHelpList?search=1";
				}
			}
		});
	}
	</script>
</body>
</html>