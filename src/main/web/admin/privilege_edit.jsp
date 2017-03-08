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
	<div>
		<h1>
		<s:if test="#request.ProTarget.target_id>0"><s:text name="target.edit" /></s:if>
		<s:else><s:text name="target.add" /></s:else>
		</h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;height:90%;">
			<input id="id" type="hidden" value="<s:property value="#Global.RegisterID('Privilege',#request.Privilege.privilege_id)" />">
			<table class="gTab">
				<tr>
					<td>
						<s:text name="pri.name"/>
					</td>
					<td>
						<input type="text" id="name" value="<s:property value="#request.Privilege.privilege_name" />">
					</td>
					<td>
						<s:text name="pri.define" />
					</td>
					<td>
						<input type="text" id="define" value="<s:property value="#request.Privilege.privilege_define" />">
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="pri.dec"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<textarea id="des" style="width:98%;height:200px"><s:property value="#request.Privilege.privilege_dec" /></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align:center">
						<input type="button" value="<s:text name="submit" />" onclick="Submit();">
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function Submit(){
		if($("#name").val() == ""){
			alert('<s:text name="prompt.pri_name.null" />');
			return;
		}
		if($("#define").val() == ""){
			alert('<s:text name="prompt.pri_define.null" />');
			return;
		}
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminPrivilegeSave",
			data:{
				"id":$("#id").val(),
				"privilege_name":$("#name").val(),
				"privilege_dec":$("#des").val(),
				"privilege_define":$("#define").val()
			},
			success:function SubmitOK(data){
				if(data == ""){
					alert('<s:text name="error.db" />');
					$('#block').css("display", "none");
					return;
				}
				if(data != "ok"){
					alert(data);
				}else{
					window.parent.window.location.reload(true);
				}
			}
		});
	}
	</script>
</body>
</html>