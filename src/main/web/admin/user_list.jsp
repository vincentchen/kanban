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
		<div style="width:200px"><h1 style="float:left"><s:text name="admin.user.info" /></h1></div>
		<div style="float:right">
			<table style="float:left;">
				<tr>
					<td style="width:75px;height:45px;line-height:45px;float:left;margin-right:10px;">
					<s:if test="#session.UserInfoPri.ADD>0">
					<a href="javascript:void(0);" onclick="AddUser();">
						<img alt="" src="<%=basePath%>image/add.png" style="vertical-align:middle;">
						<h1 style="float:right"><s:text name="add" /></h1>
					</a>
					</s:if></td>
					<td style="width:75px;height:45px;line-height:45px;float:left;">
					<a href="javascript:void(0);" onclick="SearchUser();">
						<img alt="" src="<%=basePath%>image/search.png" style="vertical-align:middle;">
						<h1 style="float:right">
							<s:text name="search" />
						</h1>
						</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="width:95%;margin:0px auto;" id="UserList">
		<table class="gListTab" >
			<tr style="border-bottom:1px solid #E5E5E5">
				<td style="border-right:1px solid #E5E5E5">
					<s:text name="account"/>
				</td>
				<td style="border-right:1px solid #E5E5E5">
					<s:text name="username"/>
				</td>
				<td style="border-right:1px solid #E5E5E5">
					<s:text name="email"/>
				</td>
				<td style="width:120px;text-align:center;border-right:1px solid #E5E5E5">
					<s:text name="login.last"/>
				</td>
				<td style="width:150px;text-align:center;">
					<s:text name="msg.operation"/>
				</td>
			</tr>
		<s:iterator value="#request.UserList" status="st">
			<tr style="border-bottom:1px dashed #E5E5E5">
				<td>
					<input id="UserID_<s:property value="#st.index" />" type="hidden" value="<s:property value="#request.UserList[#st.index].id" />">
					<s:property value="#request.UserList[#st.index].user_account"/>
				</td>
				<td>
					<s:property value="#request.UserList[#st.index].user_name"/>
				</td>
				<td>
					<s:property value="#request.UserList[#st.index].email"/>
				</td>
				<td style="text-align:center">
					<s:date name="#request.UserList[#st.index].login_time" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="text-align:center">
					<a href="javascript:void(0);" onclick="OpenUser(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/table_go.png"></a>
					<s:if test="#session.UserInfoPri.WRITE>0">
					<a href="javascript:void(0);" onclick="EditUser(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/edit_small.png"></a>
					<a href="javascript:void(0);" onclick="OpenRelDept(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/group.png"></a>
					<a href="javascript:void(0);" onclick="EditPri(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/application_key_small.png"></a>
					<a href="javascript:void(0);" onclick="DeletePWD(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/key_delete_small.png"></a>
					</s:if>
					<a href="javascript:void(0);" onclick="OpenHistory(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/inbox_full.png"></a>
					<s:if test="#session.UserInfoPri.DEL>0">
						<s:if test="#request.UserList[#st.index].is_delete == 0">
							<a href="javascript:void(0);" onclick="DeleteUser(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/close.png"></a>
						</s:if>
						<s:else>
							<a href="javascript:void(0);" onclick="ActivationUser(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/repeat.png"></a>
						</s:else>
					</s:if>
				</td>
			</tr>
		</s:iterator>
		</table>
	</div>
	<div id='LoadingStatus'></div>
	<script type="text/javascript">
	$(document).ready(function(){  
        $(".gListTab tr").mouseover(function(){
        $(this).addClass("hover");}).mouseout(function(){
        $(this).removeClass("hover");})
        $(".gListTab tr:even").addClass("alt");
    });
    
	<s:if test="#session.UserInfoPri.WRITE>0">
	function AddUser(){
		CreatePopDiv("AddUser","50%","50%","<%=basePath%>admin/AdminUserEdit");
	}
	
	function EditUser(rowID){
		var id = $("#UserID_"+rowID).val();
		CreatePopDiv("EditUser","50%","50%","<%=basePath%>admin/AdminUserEdit?id="+id);
	}
	</s:if>
	function SearchUser(){
		window.open('<%=basePath%>UserSearch?usersSelectType=checkbox&callBackFunc=searchUsers');
	}
	
	function OpenHistory(rowID){
		var id = $("#UserID_"+rowID).val();
		window.location.href="<%=basePath%>admin/AdminUserHistory?id="+id;
	}
	
	function searchUsers(obj){
		var idList = "";
		for(var xx in obj){
			idList+= xx + ",";
		}
		idList = idList.substring(0,idList.length-1);
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminUserSaveSearch",
			data:{
				"userIDs":idList
			},
			success:function(data){
				if(data == "ok"){
					window.location.href="<%=basePath%>admin/AdminUserList?search=1";
				}else{
					alert(data);
				}
			}
		});
	}
	
	function OpenUser(rowID){
		var id = $("#UserID_"+rowID).val();
		window.location.href = "<%=basePath%>admin/AdminUserView?id="+id;
	}
	
	<s:if test="#session.UserInfoPri.WRITE>0">
	function EditPri(rowID){
		var id = $("#UserID_"+rowID).val();
		CreatePopDiv("EditPri","80%","80%","<%=basePath%>admin/AdminUserPri?id="+id);
	}
	</s:if>
	$(document).ready(function(){
		scrollHandler.init($("#UserList").get(0),"LoadUserListByPageNum");
	});
	
	function LoadUserListByPageNum(){
		if(pageLoadAjax == null){
			currentPageNum++;
			LoadingStart();
			pageLoadAjax = $.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminUserLoadListByAjax",
				data:{
					"currentPage":currentPageNum,
					"search":1
				},
				success:function(data){
					if(data == ""){
						alert('<s:text name="error.db" />');
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);
					}else{
						LoadingStatus();
						pageLoadAjax = null;
						AppendList(json_data[0].content);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					LoadingError();
					currentPageNum--;
					pageLoadAjax = null;
                }
			});
		}
	}
	
	function LoadingStatus(){
		$("#LoadingStatus").html("<a href='javascript:void(0);' onclick='LoadHelpListByPageNum();' style='height:50px;line-height:50px;'><s:text name='showmore' /></a>");
	}
	
	function LoadingStart(){
		$("#LoadingStatus").html("<img src='<%=basePath%>image/loading.gif' style='position: relative;top:50%;margin-top:-16px;'/>");
	}
	
	function LoadingError(){
		$("#LoadingStatus").html("<font style='height:50px;line-height:50px;'><s:text name='loading.error' /> <a href='javascript:void(0);' onclick='LoadHelpListByPageNum();'><s:text name='click.this' /></a> <s:text name='loading.restart' /></font>");
	}
	
	function LoadingLast(){
		$("#LoadingStatus").html("<font style='height:50px;line-height:50px;'><s:text name='prompt.page.end' /></font>");
	}
	
	function AppendList(objList){
		var list_Str = "";
		var len = $("#UserList tr").length;
		len--;
		if(objList.length>0){
			for(var i=0;i<objList.length;i++){
				len++;
				list_Str = "<tr style='border-bottom:1px dashed #E5E5E5'>";
				list_Str += "<td><input id='UserID_"+len+"' type='hidden' value='"+objList[i][0]+"'>"+objList[i][1]+"</td>";
				list_Str += "<td>"+objList[i][2]+"</td>";
				list_Str += "<td>"+objList[i][3]+"</td>";
				list_Str += "<td style='text-align:center'>"+objList[i][4]+"</td>";
				list_Str += "<td style='text-align:center'>";
				list_Str += "<a href='javascript:void(0);' onclick='OpenUser("+len+");'><img src='<%=basePath%>image/table_go.png'></a> ";
				<s:if test="#session.UserInfoPri.WRITE>0">
				list_Str += "<a href='javascript:void(0);' onclick='OpenRelDept("+len+");'><img src='<%=basePath%>image/group.png'></a>";
				list_Str += "<a href='javascript:void(0);' onclick='EditUser("+len+");'><img src='<%=basePath%>image/edit_small.png'></a> ";
				list_Str += "<a href='javascript:void(0);' onclick='EditGroup("+len+");'><img src='<%=basePath%>image/group.png'></a> ";
				list_Str += "<a href='javascript:void(0);' onclick='EditPri("+len+");'><img src='<%=basePath%>image/application_key_small.png'></a> ";
				list_Str += "<a href='javascript:void(0);' onclick='DeletePWD("+len+");'><img src='<%=basePath%>image/key_delete_small.png'></a> ";
				</s:if>
				<s:if test="#session.UserInfoPri.DEL>0">
				if(objList[i][5] == 0){
					list_Str += "<a href='javascript:void(0);' onclick='DeleteUser("+len+");'><img src='<%=basePath%>image/close.png'></a>";
				}else{
					list_Str += "<a href='javascript:void(0);' onclick='ActivationUser("+len+");'><img src='<%=basePath%>image/repeat.png'></a>";
				}
				</s:if>
				list_Str += "</td></tr>";
				$("#UserList table").append(list_Str);
			}
		}else{
			LoadingLast();
			scrollHandler.stop();
		}
	}
	
	<s:if test="#session.UserInfoPri.WRITE>0">
	function DeletePWD(rowID){
		var id = $("#UserID_"+rowID).val();
		if(confirm("<s:text name='prompt.resetpwd' />")){
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminUserResetPwd",
				data:{
					"id":id
				},
				success:function(data){
					if (data != "ok") {
						alert(data);
					}else{
						window.location.reload(true);
					}
				}
			});
		}
	}
	</s:if>
	
	<s:if test="#session.UserInfoPri.DEL>0">
	function DeleteUser(rowID){
		var id = $("#UserID_"+rowID).val();
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminUserDeleteUser",
			data:{
				"id":id
			},
			success:function(data){
				if (data != "ok") {
					alert(data);
				}else{
					window.location.reload(true);
				}
			}
		});
	}
		
	function ActivationUser(rowID){
		var id = $("#UserID_"+rowID).val();
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminUserActivationUser",
			data:{
				"id":id
			},
			success:function(data){
				if (data != "ok") {
					alert(data);
				}else{
					window.location.reload(true);
				}
			}
		});
	}
	</s:if>
	
	<s:if test="#session.UserInfoPri.WRITE>0">
	function OpenRelDept(rowID){
		var id = $("#UserID_"+rowID).val();
		CreatePopDiv("RelDept","80%","80%","<%=basePath%>admin/AdminUserRelDept?id="+id);
	}
	</s:if>
	
	</script>
</body>
</html>