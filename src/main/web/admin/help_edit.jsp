<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html style="width:100%;height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:100%">
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div>
		<h1>
		<s:if test="#request.Info.request_id>0"><s:text name="help.add" /></s:if>
		<s:else><s:text name="help.edit" /></s:else>
		</h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;height:90%;">
			<input type="hidden" id="id" value="<s:property value="#Global.RegisterID('Help',#request.Info.request_id)" />">
			<table class="gTab">
				<tr>
					<td style="width:80px;">
						<s:text name="help.title" />:
					</td>
					<td>
						<input id="help_title" type="text" value="<s:property value="#request.Info.help_title" />" style="width:90%">
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="help.content" />:
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea id="help_content" style="width:98%;height:200px;"><s:property value="#request.Info.help_content" /></textarea>
					</td>
				</tr>
				<s:if test="#request.Info.request_id == 0">
				<tr>
					<td>
						<s:text name="help.assign_helper" />
					</td>
					<td>
						<table class="gTab">
							<tr>
								<td style="width:300px;">
									<input type="hidden" id="user_id" name="user_id"/>
									<input type="text" id="user_name" name="user_name" placeholder="<s:text name="prompt.account" />" style="width:180px" onclick="Register();" />&nbsp;<a href="javascript:void(0);"><s:text name="advanced.search" /></a>
								</td>
								<td id="users_td">
								
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</s:if>
				<tr>
					<td colspan="2">
						<input type="button" value="<s:text name="submit" />" onclick="Submit();">
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function Submit(){
		var userIdList = 0;
		if($("#userIdList")){
			userIdList = $("#userIdList").attr("name");
		}
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminHelpSave",
			data:{
				"id":$("#id").val(),
				"help_title":$("#help_title").val(),
				"help_content":$("#help_content").val(),
				"help_user_id":userIdList
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
					window.parent.window.location.href = "<%=basePath%>admin/AdminHelpView?id="+json_data[0].content;
				}
			}
		});
	}
	
	function Register(){
		KeyUpHandler.init(document.getElementById("user_name"), "UserSearchByAjax", "","searchUser");
	}
	
	function searchUser(id,name){
		document.getElementById("users_td").innerHTML = "<div id='userIdList' name='"+id+"' class='boxradius' style='float:left;height:28px;'><table><tr><td>"+name+"</td><td style='padding:0px;line-height:28px;'><a href='javascript:void(0);' onclick='DeleteUsers(\"userIdList\");' style='line-height:28px;'><img src='<%=basePath%>image/x.png'></a></td></tr></div>";
	}
	
	function DeleteUsers(id){
		$("#"+id).remove();
	}
	</script>
</body>
</html>