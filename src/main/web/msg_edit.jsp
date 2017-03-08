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
	<div>
		<h1>
			<s:text name="msg.add" />
		</h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;height:90%;">
			<table class="gTab">
				<tr>
					<td style="width:80px;">
						<s:text name="msg.title" />:
					</td>
					<td>
						<input id="msg_title" type="text" value="" style="width:90%">
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="msg.content" />:
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea id="msg_content" style="width:98%;height:200px;"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="msg.sendout" />
					</td>
					<td>
						<input type="hidden" id="user_id" name="user_id"/>
						<input type="text" id="user_name" name="user_name" placeholder="<s:text name="prompt.account" />" style="width:180px" onclick="Register();" />&nbsp;<a href="javascript:void(0);" onclick="AdvancedSearch();"><s:text name="advanced.search" /></a>
					</td>
				</tr>
				<tr>
					<td id="users_td" colspan="2" style="height-min:200px;">
						
					</td>
				</tr>
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
		if($("#msg_title").val() == ""){
			alert('<s:text name="prompt.title.null" />');
			return;
		}
		var userIdList = "";
		$('div[name="userIdList"]').each(function(){    
			userIdList+= $(this).attr("id")+",";    
		});
		if(userIdList == ""){
			alert('<s:text name="prompt.send_user.null" />');
			return;
		}
		userIdList.substring(0,userIdList.length-1);
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminMsgAdd",
			data:{
				"msg_title":$("#msg_title").val(),
				"msg_content":$("#msg_content").val(),
				"userIdList":userIdList
			},
			success:function SubmitOK(data){
				if (data != "ok") {
					alert(data);
				}else{
					window.parent.window.location.reload(true);
				}
			}
		});
	}
	
	function Register(){
		KeyUpHandler.init(document.getElementById("user_name"), "UserSearchByAjax", "","searchUser");
	}
	
	function searchUser(id,name){
		document.getElementById("users_td").innerHTML += "<div id='"+id+"' name='userIdList' class='boxradius' style='float:left;height:28px;margin:2px;'><table><tr><td>"+name+"</td><td style='padding:0px;line-height:28px;'><a href='javascript:void(0);' onclick='DeleteUsers(\""+id+"\");' style='line-height:28px;'><img src='<%=basePath%>image/close.png'></a></td></tr></div>";
	}
	
	function DeleteUsers(id){
		$("#"+id).remove();
	}
	
	function AdvancedSearch(){
		window.open('UserSearch?usersSelectType=checkbox&callBackFunc=searchUsers');
	}
	
	function searchUsers(userJson){
		for(var x in userJson){
			searchUser(x,userJson[x]);
		}
	}
	</script>
</body>
</html>