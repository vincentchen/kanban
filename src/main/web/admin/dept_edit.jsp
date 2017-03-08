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
		<s:text name="dept.add" />
		</h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;height:90%;">
			<table class="gTab">
				<tr>
					<td style="width:80px;text-align:center">
						<s:text name="dept.name" />:
					</td>
					<td>
						<input id="dept_name" type="text" value="">
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
			url:"AdminDeptAdd",
			data:{
				"dept_name":$("#dept_name").val(),
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
					window.parent.window.location.href = "AdminDeptList";
				}
			}
		});
	}
	</script>
</body>
</html>