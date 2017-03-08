<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div class="boxradius" style="margin:0px auto;width:98%;text-align:left;">
		<input id="id" type="hidden" value="<s:property value="#Global.RegisterID('UserID',#request.Users.user_id)" />">
		<div style="width:100%;">
			<table class="gTab">
				<tr style="border-bottom:1px solid #E5E5E5;">
					<td style="width:30px;"><a href="javascript:void(0);" onclick="javascript:window.location.href='AdminUserList?search=1';"><img alt="" src="<%=basePath%>image/left.png">
					</a></td>
					<td style="text-indent: 2em;">
						<h1>
							<s:property value="#request.Users.user_name" />(<s:property value="#request.Users.user_account" />:<s:property value="#request.Users.email" />)
						</h1></td>
					<td style="width:200px;"><s:text name="time.create" />:<s:date name="#request.Users.create_time" format="yyyy-MM-dd HH:mm:ss" /><br /> <s:text name="login.last" />:<s:date name="#request.Users.login_time" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td style="width:230px;">
						<table style="width:230px;">
							<tr>
								<td><s:if test="#session.UserInfoPri.WRITE>0">
								<a href="javascript:void(0);" onclick="EditUser();"><img src="<%=basePath%>image/edit.png" />
									<s:text name="edit" />
								</a>
								</s:if></td>
								<td>
								<s:if test="#session.UserInfoPri.WRITE>0"><a href="javascript:void(0);" onclick="ResetPwd();"><img src="<%=basePath%>image/key_delete.png" />
									<s:text name="key.delete" />
								</a>
								</s:if></td>
								<td>
								<s:if test="#session.UserInfoPri.DEL>0">
									<s:if test="#request.Users.is_delete >0">
										<a href="javascript:void(0);" onclick="ActivationUser();"><img src="<%=basePath%>image/delete.png" />
											<s:text name="delete" />
										</a>
									</s:if>
									<s:else>
										<a href="javascript:void(0);" onclick="DeleteUser();"><img src="<%=basePath%>image/delete.png" />
											<s:text name="delete" />
										</a>
									</s:else>
								</s:if>
								</td>
							</tr>
						</table></td>
				</tr>
				<tr>
					<td colspan='3'>
						<h2><s:text name="dept.role.user" />:</h2>
					</td>
					<td style="text-align:right">
						<s:if test="#session.UserInfoPri.WRITE>0">
						<a href="javascript:void(0);" onclick="OpenRelDept();"><s:text name="user.rel_dept" /></a>
						</s:if>
					</td>
				</tr>
				<tr style="border-bottom:1px solid #E5E5E5;">
					<td colspan="4">
						<s:iterator value="#request.DeptRole" status="st">
							<s:if test="#request.DeptRole[#st.index].dept_name != null"><s:property value="#request.DeptRole[#st.index].dept_name"/></s:if>
							<s:if test="#request.DeptRole[#st.index].dept_name != null && #request.DeptRole[#st.index].role_name != null">:</s:if>
							<s:if test="#request.DeptRole[#st.index].role_name != null"><s:property value="#request.DeptRole[#st.index].role_name"/></s:if>
						</s:iterator>
					</td>
				</tr>
				<tr>
					<td colspan='3'>
						<h2><s:text name="user.pri" />:</h2>
					</td>
					<td style="text-align:right">
						<s:if test="#session.UserInfoPri.WRITE>0">
						<a href="javascript:void(0);" onclick="EditPri();"><s:text name="pri.revise" /></a>
						</s:if>
					</td>
				</tr>
				<tr>
					<td colspan='4' >
						<table class="gListTab">
							<tr style="border-bottom:1px solid #E5E5E5">
								<td>
									<s:text name="pri.content" />
								</td>
								<td style="width:25px;height:20px;">
									<s:text name="add"/>
								</td>
								<td style="width:25px;height:20px;">
									<s:text name="delete"/>
								</td>
								<td style="width:25px;height:20px;">
									<s:text name="edit"/>
								</td>
								<td style="width:25px;height:20px;">
									<s:text name="search"/>
								</td>
							</tr>
							<s:iterator value="#request.Pri" status="st">
								<tr style="border-bottom:1px dashed #E5E5E5">
									<td>
										<s:property value="#request.Pri[#st.index][1]"/>(<s:property value="#request.Pri[#st.index][2]"/>):<br />
										<s:property value="#request.Pri[#st.index][3]"/>
									</td>
									<td>
										<input type="checkbox" <s:if test="#request.Pri[#st.index][4].ADD>0">checked</s:if> disabled>
									</td>
									<td>
										<input type="checkbox" <s:if test="#request.Pri[#st.index][4].DEL>0">checked</s:if> disabled>
									</td>
									<td>
										<input type="checkbox" <s:if test="#request.Pri[#st.index][4].WRITE>0">checked</s:if> disabled>
									</td>
									<td>
										<input type="checkbox" <s:if test="#request.Pri[#st.index][4].READ>0">checked</s:if> disabled>
									</td>
								</tr>
							</s:iterator>
						</table>
					</td>
				</tr>
			</table>
		</div>
		
	</div>
	<script type="text/javascript">
		$(document).ready(function(){  
	        $(".gListTab tr").mouseover(function(){
	        $(this).addClass("hover");}).mouseout(function(){
	        $(this).removeClass("hover");})
	        $(".gListTab tr:even").addClass("alt");
	         
	    });
		
		function EditUser(rowID){
			var id = $("#id").val();
			CreatePopDiv("EditUser","50%","50%","<%=basePath%>admin/AdminUserEdit?id="+id);
		}
		
		function EditPri(){
			var id = $("#id").val();
			CreatePopDiv("EditPri","80%","80%","<%=basePath%>admin/AdminUserPri?id="+id);
		}
		
		function ResetPwd(){
			if(confirm("<s:text name='prompt.resetpwd' />")){
 				$.ajax({
					type:"POST",
					url:"<%=basePath%>admin/AdminUserResetPwd",
					data:{
						"id":$("#id").val()
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
		
		function DeleteUser(){
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminUserDeleteUser",
				data:{
					"id":$("#id").val()
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
		
		function OpenRelDept(){
			var id = $("#id").val();
			CreatePopDiv("RelDept","80%","80%","<%=basePath%>admin/AdminUserRelDept?id="+id);
		}
		
		function ActivationUser(){
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminUserActivationUser",
				data:{
					"id":$("#id").val()
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
	</script>
</body>
</html>