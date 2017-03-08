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
<script src="<%=basePath%>js/jquery.masonry.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:100%;overflow:hidden">
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<input id="proID" type="hidden" value="<s:property value="#Global.RegisterID('Project',#request.Project.pro_id)" />">
	<div style="border-bottom:1px solid #E5E5E5;">
		<table class="gTab" style="margin-left:15px;">
			<tr style="height:43px;">
				<td>
					<h1>
						<s:text name="pro.users" />
					</h1>
				</td>
				<td>
					<s:text name="prompt.rel_user" />
				</td>
				<td style="float:right;width:100px">
					<s:if test="#request.Project.create_user_id == #session.user_id"><a href="javascript:void(0);" onclick="AdvancedSearch();"><h2 style="height:43px;line-height:43px;"><img src="<%=basePath%>image/add_small.png" ><s:text name="add"/></h2></a></s:if>
				</td>
			</tr>
		</table>
	</div>
	<div style="padding-top:10px;width:95%;height:85%;overflow-y:auto">
		<div id="ct" style="position:relative;margin:0 auto;">
			<s:if test="#request.Users.size()>0">
				<s:iterator value="#request.Users" id="users" status='st'>
					<div class="item boxshadow" style="width:120px;height:125px;margin:8px;">
						<ul>
							<li style="height:102px;top:-10px;padding-top:3px;">
								<s:if test="#request.Users[#st.index][0] != #session.user_id && #request.Project.create_user_id == #session.user_id">
									<a href="javascript:void(0);" onclick="DeleteRelUser(<s:property value="#Global.RegisterID('UserID',#request.Users[#st.index][0])" />)"><span style="position: absolute;left:110px;top:-10px;width:16px;height:16px;"><img src="<%=basePath%>image/delete_small.png" /></span></a>
								</s:if>
								<s:if test="null==#request.Users[#st.index][2] || #request.Users[#st.index][2].isEmpty()">
									<img id="user_photo" src="<%=basePath%>image/no_pictur.png" style="width:100px;height:100px;" />
								</s:if>
								<s:else>
									<img src="<%=basePath%><s:property value='#request.Users[#st.index][2]' />" style="width:100px;height:100px;">
								</s:else>
							</li>
							<li style="height:20px;line-height:20px;">
								<s:property value="#request.Users[#st.index][1]"/>
							</li>
						</ul>
					</div>
				</s:iterator>
			</s:if>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			var $container = $('#ct');
			$container.imagesLoaded(function() {
				$container.masonry({
					// 每一列数据的宽度，若所有栏目块的宽度相同，可以不填这段
					// .imgbox 为各个图片的容器
					itemSelector : '.item',
					isFitWidth : true,
					isResizable : true
				});
			});
		});
		
		<s:if test="#request.Project.create_user_id == #session.user_id">
		function searchUser(id,name){
			$("#user_name").val(name);
			$("#user_id").val(id);
		}
		
		function ForcedReceive(proID){
			if($("#user_id").val() == ""){
				alert('<s:text name='error.select.user' />');
			}else{
				$.ajax({
					type : "POST",
					url : "ProjectAddRelUsers",
					data : "id="+proID+"&userIDs="+$("#user_id").val(),
					success : function ForcedReceiveOK(data) {
						if(data == ""){
							alert('<s:text name="error.db" />');
							return;
						}
						var json_data = $.parseJSON(data);
						if (json_data[0].error != "") {
							alert(json_data[0].error);	
						}else{
							window.location.href = "ProjectUsers?id="+proID;
						}
					}
				});
			}
		}
		
		function DeleteRelUser(userID){
			$.ajax({
				type : "POST",
				url : "ProjectDeleteRelUsers",
				data : "id="+$("#proID").val()+"&userIDs="+userID,
				success : function DeleteRelUserOK(data) {
					if(data == ""){
						alert('<s:text name="error.db" />');
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);	
					}else{
						window.location.reload();
					}
				}
			});
		}
		
		function AdvancedSearch(){
			window.open('UserSearch?usersSelectType=checkbox&callBackFunc=saveUsers');
		}
		
		function saveUsers(userJson){
			if(userJson.length>0){
				return false;
			}
			var userIDs = "";
			for(var x in userJson){
				userIDs+= x+",";
			}
			userIDs = userIDs.substr(0,userIDs.length-1);
			var proID = $("#proID").val();
			$.ajax({
				type : "POST",
				url : "ProjectAddRelUsers",
				data : "id="+proID+"&userIDs="+userIDs,
				success : function(data) {
					if(data == ""){
						alert('<s:text name="error.db" />');
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);	
					}else{
						window.location.href = "ProjectUsers?id="+proID;
					}
				}
			});
		}
		</s:if>
	</script>
</body>
</html>