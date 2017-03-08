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
		<h1>
		<s:if test="#request.ProTarget.target_id>0"><s:text name="target.edit" /></s:if>
		<s:else><s:text name="target.add" /></s:else>
		</h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;height:90%;">
			<s:if test="#request.ProTarget.target_id>0">
				<input id="ProTargetID" type="hidden" value="<s:property value="#Global.RegisterID('ProTarget',#request.ProTarget.target_id)" />">
			</s:if><s:else>
				<input id="ProTargetID" type="hidden" value="">
			</s:else>
				<input id="ProID" type="hidden" value="<s:property value="#request.ProTarget.proID" />">
			<table class="gTab">
				<tr>
					<td>
						<s:text name="target.name"/>
					</td>
					<td>
						<input type="text" id="name" value="<s:property value="#request.ProTarget.target_name" />">
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="target.des"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<textarea id="des" style="width:98%;height:200px"><s:property value="#request.ProTarget.target_des" /></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="target.start_target_time" />
					</td>
					<td>
						<input id="start_target_time" type="text" value="<s:date name="#request.ProTarget.start_target_time" format="yyyy-MM-dd"/>" style="width:95%;" readonly="readonly" />
					</td>
					<td>
						<s:text name="target.end_target_time" />
					</td>
					<td>
						<input id="end_target_time" type="text" value="<s:date name="#request.ProTarget.end_target_time" format="yyyy-MM-dd"/>" style="width:95%;" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="target.estimated_day" />
					</td>
					<td>
						<input id="estimated_day" type="text" value="<s:property value="#request.ProTarget.estimated_day"/>" style="width:200px">
					</td>
					<td colspan="2">
						<s:if test="#request.ProTarget.target_status>0">
							<s:if test="#request.ProTarget.target_status==100"><s:text name="target.status.doing" /></s:if>
							<s:elseif test="#request.ProTarget.target_status==200"><s:text name="target.status.done" /></s:elseif>
						</s:if>
						<s:else>
							<input type="checkbox" id="imm_start" checked><label for="imm_start"><s:text name="immediately.start" /></label>
						</s:else>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="button" value="<s:text name="submit" />" onclick="Submit();">
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	$(document).ready(function() {
		if($("#start_target_time").length>0){
			$('#start_target_time').datepicker({
				format : 'yyyy-mm-dd'
			});
		}
		if($("#end_target_time").length>0){
			$('#end_target_time').datepicker({
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
		if($("#name").val() == ""){
			alert('<s:text name="prompt.target_name.null" />');
			return;
		}
		var s = $("#start_target_time").val();
	    var e = $("#end_target_time").val();
		if(s == ""){
			alert('<s:text name="prompt.target_starttime.null" />');
			return;
		}
		if(e == ""){
			alert('<s:text name="prompt.target_endtime.null" />');
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
			alert('<s:text name="prompt.target.end_early_start" />');
			return;
		}
		var imm_start = 0;
		if($("#imm_start").is(":checked")){
			imm_start = 1;
		}
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminProTargetSave",
			data:{
				"id":$("#ProTargetID").val(),
				"proID":$("#ProID").val(),
				"target_name":$("#name").val(),
				"target_des":$("#des").val(),
				"estimated_day":$("#estimated_day").val(),
				"imm_start":imm_start,
				"start_target_time":$("#start_target_time").val(),
				"end_target_time":$("#end_target_time").val()
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
					window.parent.window.location.href = "<%=basePath%>admin/AdminProTargetView?id="+json_data[0].content;
				}
			}
		});
	}
	</script>
</body>
</html>