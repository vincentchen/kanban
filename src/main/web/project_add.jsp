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
<script src="<%=basePath%>js/datepicker.js"></script>
</head>
<body style="width:100%;height:100%">
	<div>
		<h1><s:text name="pro.add" /></h1>
	</div>
		<div class="boxradius" style="margin:0 auto;width:90%;height:88%;">
			<table class="gTab" style="height:100%;">
				<tr>
					<td>
						<s:text name="pro.name" />:
					</td>
					<td colspan="3">
						<input id="ProName" type="text" value="" style="width:90%">
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
						<textarea id="ProDes" style="width:98%;height:200px"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width:15%">
						<s:text name="pro.start_pro_time" />:
					</td>
					<td style="width:35%">
						<input id="start_pro_time" type="text" value="" style="width:95%;" readonly="readonly" />
					</td>
					<td style="width:15%">
						<s:text name="pro.end_pro_time" />:
					</td>
					<td style="width:35%">
						<input id="end_pro_time" type="text" value="" style="width:95%;" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="msg.estimated" />:
					</td>
					<td>
						<input id="estimated_day" type="text" value="" style="width:200px">&nbsp;<s:text name="day" />
					</td>
					<td colspan="2">
						<input type="checkbox" id="imm_start" checked><label for="imm_start"><s:text name="immediately.start" /></label>
						<input type="checkbox" id="is_open"><label for="is_open"><s:text name="pro.is_open" /></label>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="button" value="<s:text name="submit" />" onclick="Submit();">
					</td>
				</tr>
			</table>
		</div>
	
	<script type="text/javascript">
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
    
	var imm_start = 0;
	if($("#imm_start").is(":checked")){
		imm_start = 1;
	}
	var is_open = 0;
	if($("#is_open").is(":checked")){
		is_open = 1;
	}
	$.ajax({
		type:"POST",
		url:"ProjectAdd",
		data:{
			"pro_name":$("#ProName").val(),
			"pro_des":$("#ProDes").val(),
			"estimated_day":$("#estimated_day").val(),
			"imm_start":imm_start,
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
				window.parent.window.location.href = "ProjectView?id="+json_data[0].content;
			}
		}
	});
}
</script>
</body>
</html>