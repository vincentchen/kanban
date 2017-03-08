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
		<input id="id" type="hidden" value="<s:property value="#Global.RegisterID('Role',#request.Role.role_id)" />">
		<div style="width:100%;">
			<table class="gTab">
				<tr style="border-bottom:1px solid #E5E5E5;">
					<td style="width:30px;"><a href="javascript:void(0);" onclick="javascript:history.go(-1);"><img alt="" src="<%=basePath%>image/left.png">
					</a></td>
					<td colspan="3" style="text-indent: 2em;">
						<h1>
							<s:property value="#request.Role.role_name" />
						</h1></td>
				</tr>
				<tr>
					<td colspan='4'>
						<h2><s:text name="role.pri" />:</h2>
					</td>
				</tr>
				<tr>
					<td colspan='4' >
						<table class="gListTab">
							<tr style="border-bottom:1px solid #E5E5E5">
								<td>
									<s:text name="pri.content" />
								</td>
								<td style="width:40px;height:20px;line-height:20px;">
									<input id="SelectPriAddAll" type="checkbox" onclick="SelectAll('PriAdd');"><laber for="SelectPriAddAll" onclick="SelectAll('PriAdd');"><s:text name="add"/></laber>
								</td>
								<td style="width:40px;height:20px;">
									<input id="SelectPriDelAll" type="checkbox" onclick="SelectAll('PriDel');"><laber for="SelectPriDelAll" onclick="SelectAll('PriDel');"><s:text name="delete"/></laber>
								</td>
								<td style="width:40px;height:20px;">
									<input id="SelectPriEditAll" type="checkbox" onclick="SelectAll('PriEdit');"><laber for="SelectPriEditAll" onclick="SelectAll('PriEdit');"><s:text name="edit"/></laber>
								</td>
								<td style="width:40px;height:20px;">
									<input id="SelectPriReadAll" type="checkbox" onclick="SelectAll('PriRead');"><laber for="SelectPriReadAll" onclick="SelectAll('PriRead');"><s:text name="search"/></laber>
								</td>
							</tr>
							<s:iterator value="#request.Pri" status="st">
								<tr style="border-bottom:1px dashed #E5E5E5">
									<td>
										<s:property value="#request.Pri[#st.index][1]"/>(<s:property value="#request.Pri[#st.index][2]"/>):<br />
										<s:property value="#request.Pri[#st.index][3]"/>
									</td>
									<td>
										<input name="PriAdd" type="checkbox" value="<s:property value="#st.index" />" <s:if test="#request.Pri[#st.index][4].ADD>0">checked</s:if>>
									</td>
									<td>
										<input name="PriDel" type="checkbox" value="<s:property value="#st.index" />" <s:if test="#request.Pri[#st.index][4].DEL>0">checked</s:if>>
									</td>
									<td>
										<input name="PriEdit" type="checkbox" value="<s:property value="#st.index" />" <s:if test="#request.Pri[#st.index][4].WRITE>0">checked</s:if>>
									</td>
									<td>
										<input name="PriRead" type="checkbox" value="<s:property value="#st.index" />" <s:if test="#request.Pri[#st.index][4].READ>0">checked</s:if>>
									</td>
								</tr>
							</s:iterator>
							<tr>
								<td colspan="5" style="text-align:center">
									<input type="button" value="<s:text name="submit" />" onclick="Submit();">
								</td>
							</tr>
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
		
		function Submit(){
			var is_cover = 0;
			if(confirm(printf("<s:text name='prompt.role.pri.cover' />","<s:property value='#request.Role.role_name' escape='false' />"))){
				is_cover = 1;
			}
			var priStr = "";
			$("input[type='checkbox']").each(function (i){
				switch ($(this).attr("name")) {
					case "PriAdd":
						if($(this).attr("checked")){
							priStr += "1";
						}else{
							priStr += "0";
						}
						break;
					case "PriDel":
						if($(this).attr("checked")){
							priStr += "1";
						}else{
							priStr += "0";
						}
						break;
					case "PriEdit":
						if($(this).attr("checked")){
							priStr += "1";
						}else{
							priStr += "0";
						}
						break;
					case "PriRead":
						if($(this).attr("checked")){
							priStr += "1";
						}else{
							priStr += "0";
						}
						break;
				}
			});
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminRolePriSave",
				data:{
					"id":$("#id").val(),
					"priString":priStr,
					"is_cover":is_cover
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
		
		function SelectAll(str){
			if($("#Select"+str+"All").attr("checked")){
				$("input[name='"+str+"']").each(function (i){
					$(this).attr("checked","checked");
				});
			}else{
				$("input[name='"+str+"']:checked").each(function (i){
					$(this).removeAttr("checked");
				});
			}
		}
	</script>
</body>
</html>