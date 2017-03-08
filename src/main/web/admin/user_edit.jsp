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
	<div>
		<h1>
		<s:if test="#request.Info.user_id>0"><s:text name="user.edit" /></s:if>
		<s:else><s:text name="user.add" /></s:else>
		</h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;height:90%;">
			<s:if test="#request.id>0">
				<input id="UserID" type="hidden" value="<s:property value="#request.id" />">
			</s:if><s:else>
				<input id="UserID" type="hidden" value="">
			</s:else>
			<table class="gTab">
				<tr>
					<td style="width:80px;text-align:center">
						<s:text name="account" />:
					</td>
					<td>
						<input id="user_account" type="text" value="<s:property value='#request.Info.user_account' />">
					</td>
				</tr>
				<tr>
					<td style="width:80px;text-align:center">
						<s:text name="username" />:
					</td>
					<td>
						<input id="user_name" type="text" value="<s:property value='#request.Info.user_name' />">
					</td>
				</tr>
				<tr>
					<td style="width:80px;text-align:center">
						<s:text name="email" />:
					</td>
					<td>
						<input id="email" type="text" value="<s:property value='#request.Info.email' />">
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="<s:text name="submit" />" onclick="Submit();" style="width:100%">
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function Submit(){
		$.ajax({
			type:"POST",
			url:"AdminUserSave",
			data:{
				"id":$("#UserID").val(),
				"user_account":$("#user_account").val(),
				"user_name":$("#user_name").val(),
				"email":$("#email").val()
			},
			success:function(data){
				if(data == ""){
					alert('<s:text name="error.db" />');
					$('#block').css("display", "none");
					return;
				}
				var json_data = $.parseJSON(data);
				if (json_data[0].error != "") {
					alert(json_data[0].error);
				}else{
					window.parent.window.location.href = "AdminUserView?id="+json_data[0].content;
				}
			}
		});
	}
	</script>
</body>
</html>