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
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div>
		<h1>
			<s:text name="user.rel_dept" />
		</h1>
	</div>
	<div class="boxradius" style="margin:0px auto;width:98%;text-align:left;">
		<input id="id" type="hidden" value="<s:property value="#Global.RegisterID('UserID',#request.Users.user_id)" />">
		<div style="width:100%;">
			<table class="gTab">
				<tr style="border-bottom:1px solid #E5E5E5;">
					<td style="width:30px;"><img alt="" src="<%=basePath%>image/add.png">
					</td>
					<td style="width:60px;">
						<s:text name="dept.select" />:
					</td>
					<td>
						<input type="hidden" id="dept_id" value="">
						<input type="text" id="dept_name" value="" onclick="RegisterDeptSearch();"> <font style="color:red;">*</font>
					</td>
					<td style="width:60px;">
						<s:text name="role.select" />:
					</td>
					<td>
						<input type="hidden" id="role_id" value="">
						<input type="text" id="role_name" value="" onclick="RegisterRoleSearch();">
					</td>
					<td>
						<input type="button" value="<s:text name='select.ok' />" onclick="AddRel();">
					</td>
				</tr>
				<tr>
					<td colspan="6">
						<s:iterator value="#request.RelDeptAndRole" status="st">
							<div class="boxradius" style="margin:5px;width:30%;">
								<input type="hidden" id="Rel_<s:property value="#st.index" />" value="<s:property value="#Global.RegisterID('RelID',#request.RelDeptAndRole[#st.index].rel_id)" />">
								<table class="gTab">
									<tr>
										<td>
											<s:property value="#request.RelDeptAndRole[#st.index].dept_name" />
											<s:if test="#request.RelDeptAndRole[#st.index].role_id>0">
											- <s:property value="#request.RelDeptAndRole[#st.index].role_name" />
											</s:if>
										</td>
										<td style="width:20px;">
											<a href="javascript:void(0);" onclick="DelRel(<s:property value="#st.index" />);"><img src="<%=basePath%>image/close.png" /></a>
										</td>
									</tr>
								</table>
							</div>
						</s:iterator>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		
		function RegisterDeptSearch(){
			KeyUpHandler.init(document.getElementById("dept_name"), "AdminDeptSearchByAjax", "","searchDept");
		}
		
		function searchDept(id,name){
			$("#dept_id").val(id);
			$("#dept_name").val(name);
		}
		
		function RegisterRoleSearch(){
			KeyUpHandler.init(document.getElementById("role_name"), "AdminRoleSearchByAjax", "","searchRole");
		}
		
		function searchRole(id,name){
			$("#role_id").val(id);
			$("#role_name").val(name);
		}
		
		function AddRel(){
			var user_id = $("#id").val();
			var dept_id = $("#dept_id").val();
			var role_id = $("#role_id").val();
			if(dept_id == ""){
				alert('<s:text name="prompt.select_dept" />');
				return;
			}
			
			if(role_id == ""){
				role_id = 0;
			}
			
			if($("#role_name").val() !="" && $("#role_id").val() == ''){
				alert('<s:text name="prompt.select_role.correct" />');
				return;
			}
			
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminRelDeptUserInsertRelDeptUser",
				data:{
					"user_id":user_id,
					"dept_id":dept_id,
					"role_id":role_id
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
		
		function DelRel(rowID){
			var rel_id = $("#Rel_"+rowID).val();
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminRelDeptUserDeleteRelDeptUser",
				data:{
					"rel_id":rel_id
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
