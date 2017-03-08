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
<script src="<%=basePath%>js/datepicker.js"></script>
</head>
<body style="width:100%;height:100%;">
<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div>
		<h1><s:text name="pro.detail" /></h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;height:90%;">
			<input id="ProID" type="hidden" value="<s:property value="#Global.RegisterID('Project',#request.Project.pro_id)" />">
			<table class="gTab">
				<tr>
					<td>
						<s:text name="pro.name" />:
					</td>
					<td colspan="3">
						<s:if test="#request.Project.create_user_id==#session.user_id"><input id="ProName" type="text" value="<s:property value="#request.Project.pro_name" />" style="width:90%"></s:if>
						<s:else><s:property value="#request.Project.pro_name" /></s:else>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="pro.des" />:
					</td>
					<td colspan="3">
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<s:if test="#request.Project.create_user_id==#session.user_id"><textarea id="ProDes" style="width:98%;"><s:property value="#request.Project.pro_des" /></textarea></s:if>
						<s:else><s:property value="#request.Project.pro_des" /></s:else>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="time.create" />:
					</td>
					<td>
						<s:date name="#request.Project.create_time" format="yyyy-MM-dd" />
					</td>
					<td>
						<s:text name="time.update" />:
					</td>
					<td>
						<s:date name="#request.Project.update_time" format="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="pro.start_pro_time" />:
					</td>
					<td>
						<s:if test="#request.Project.create_user_id==#session.user_id"><input id="start_pro_time" type="text" value="<s:date name="#request.Project.start_pro_time" format="yyyy-MM-dd"/>" style="width:95%;" readonly="readonly" /></s:if>
						<s:else><s:date name="#request.Project.start_pro_time" format="yyyy-MM-dd"/></s:else>
					</td>
					<td>
						<s:text name="pro.end_pro_time" />:
					</td>
					<td>
						<s:if test="#request.Project.create_user_id==#session.user_id"><input id="end_pro_time" type="text" value="<s:date name="#request.Project.end_pro_time" format="yyyy-MM-dd"/>" style="width:95%;" readonly="readonly" /></s:if>
						<s:else><s:date name="#request.Project.end_pro_time" format="yyyy-MM-dd" /></s:else>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="msg.estimated" />:
					</td>
					<td>
						<s:if test="#request.Project.create_user_id==#session.user_id"><input id="estimated_day" type="text" value="<s:property value="#request.Project.estimated_day"/>" style="width:200px"></s:if>
						<s:else><s:property value="#request.Project.estimated_day"/></s:else><s:text name="day" />
						
					</td>
					<td colspan="2">
						<s:if test="#request.Project.create_user_id==#session.user_id"><input type="checkbox" id="is_open" <s:if test="#request.Project.is_open>0">checked</s:if>><label for="is_open"><s:text name="pro.is_open" /></label></s:if>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<s:if test="#request.Project.create_user_id==#session.user_id"><input type="button" value="<s:text name="submit" />" onclick="Submit();"></s:if>
					</td>
				</tr>
			</table>
		</div>
	</div>
<script type="text/javascript">
<s:if test="#request.Project.create_user_id==#session.user_id">
$(document).ready(function() {
	if($("#start_pro_time").length>0){
		$('#start_pro_time').datepicker({
			format : 'yyyy-mm-dd'
		});
	}
	if($("#end_pro_time").length>0){
		$('#end_pro_time').datepicker({
			format : 'yyyy-mm-dd'
		});
	}
	
	$("#estimated_day").keyup(function(){
		$(this).val($(this).val().replace(/[^0-9.]/g,''));  
    }).bind("paste",function(){
    	$(this).val($(this).val().replace(/[^0-9.]/g,''));   
    }).css("ime-mode", "disabled");
});

function Submit(){
	if($("#ProName").val() == ""){
		alert('<s:text name="prompt.project_name.null" />');
		return;
	}
	var s = $("#start_pro_time").val();
    var e = $("#end_pro_time").val();
	if(s == ""){
		alert('<s:text name="prompt.project_starttime.null" />');
		return;
	}
	if(e == ""){
		alert('<s:text name="prompt.project_endtime.null" />');
		return;
	}
	if($("#estimated_day").val() == ""){
		alert('<s:text name="prompt.estimated_day.null" />');
		return;
	}
	var b_date = s.split('-');
	var e_date = e.split('-');
	d1 = new Date(b_date[0], b_date[1], b_date[2]);
	d2 = new Date(e_date[0], e_date[1], e_date[2]);
	if (d1 > d2) {                            
		alert('<s:text name="prompt.project.end_early_start" />');
		return;
	}
	
	var is_open = 0;
	if($("#is_open").is(":checked")){
		is_open = 1;
	}
	$.ajax({
		type:"POST",
		url:"ProjectSave",
		data:{
			"id":$("#ProID").val(),
			"pro_name":$("#ProName").val(),
			"pro_des":$("#ProDes").val(),
			"estimated_day":$("#estimated_day").val(),
			"start_pro_time":$("#start_pro_time").val(),
			"end_pro_time":$("#end_pro_time").val(),
			"is_open":is_open
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
				window.parent.window.location.href = "ProjectView?id="+$("#ProID").val();
			}
		}
	});
}
</s:if>
</script>
</body>
</html>