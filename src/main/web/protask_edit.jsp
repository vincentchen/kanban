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
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div>
		<h1>
		<s:if test="#request.ProTask.task_id>0"><s:text name="task.edit" /></s:if>
		<s:else><s:text name="task.add" /></s:else>
		</h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;height:90%;">
			<s:if test="#request.ProTask.task_id>0">
				<input id="id" type="hidden" value="<s:property value="#Global.RegisterID('ProTask',#request.ProTask.task_id)" />">
				<input id="targetID" type="hidden" value="<s:property value="#Global.RegisterID('ProTarget',#request.ProTask.target_id)" />">
			</s:if>
			<s:else>
				<input id="id" type="hidden" value="">
				<input id="targetID" type="hidden" value="<s:property value="#request.ProTask.targetID" />">
			</s:else>
			<table class="gTab">
				<tr>
					<td colspan="4">
						<textarea id="des" style="width:98%;height:200px"><s:property value="#request.ProTask.task_des" /></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="task.start_task_time" />
					</td>
					<td>
						<input id="start_task_day" type="text" value="<s:date name="#request.ProTask.start_task_time" format="yyyy-MM-dd"/>" style="width:200px" readonly="readonly" />
						<select id="start_task_hour">
							<option value="00"><s:text name="day.hour.0" /></option>
							<option value="01"><s:text name="day.hour.1" /></option>
							<option value="02"><s:text name="day.hour.2" /></option>
							<option value="03"><s:text name="day.hour.3" /></option>
							<option value="04"><s:text name="day.hour.4" /></option>
							<option value="05"><s:text name="day.hour.5" /></option>
							<option value="06"><s:text name="day.hour.6" /></option>
							<option value="07"><s:text name="day.hour.7" /></option>
							<option value="08"><s:text name="day.hour.8" /></option>
							<option value="09"><s:text name="day.hour.9" /></option>
							<option value="10"><s:text name="day.hour.10" /></option>
							<option value="11"><s:text name="day.hour.11" /></option>
							<option value="12"><s:text name="day.hour.12" /></option>
							<option value="13"><s:text name="day.hour.13" /></option>
							<option value="14"><s:text name="day.hour.14" /></option>
							<option value="15"><s:text name="day.hour.15" /></option>
							<option value="16"><s:text name="day.hour.16" /></option>
							<option value="17"><s:text name="day.hour.17" /></option>
							<option value="18"><s:text name="day.hour.18" /></option>
							<option value="19"><s:text name="day.hour.19" /></option>
							<option value="20"><s:text name="day.hour.20" /></option>
							<option value="21"><s:text name="day.hour.21" /></option>
							<option value="22"><s:text name="day.hour.22" /></option>
							<option value="23"><s:text name="day.hour.23" /></option>
							<option value="24"><s:text name="day.hour.24" /></option>
						</select>
					</td>
					<td>
						<s:text name="task.end_task_time" />
					</td>
					<td>
						<input id="end_task_day" type="text" value="<s:date name="#request.ProTask.end_task_time" format="yyyy-MM-dd"/>" style="width:200px" readonly="readonly" />
						<select id="end_task_hour">
							<option value="00"><s:text name="day.hour.0" /></option>
							<option value="01"><s:text name="day.hour.1" /></option>
							<option value="02"><s:text name="day.hour.2" /></option>
							<option value="03"><s:text name="day.hour.3" /></option>
							<option value="04"><s:text name="day.hour.4" /></option>
							<option value="05"><s:text name="day.hour.5" /></option>
							<option value="06"><s:text name="day.hour.6" /></option>
							<option value="07"><s:text name="day.hour.7" /></option>
							<option value="08"><s:text name="day.hour.8" /></option>
							<option value="09"><s:text name="day.hour.9" /></option>
							<option value="10"><s:text name="day.hour.10" /></option>
							<option value="11"><s:text name="day.hour.11" /></option>
							<option value="12"><s:text name="day.hour.12" /></option>
							<option value="13"><s:text name="day.hour.13" /></option>
							<option value="14"><s:text name="day.hour.14" /></option>
							<option value="15"><s:text name="day.hour.15" /></option>
							<option value="16"><s:text name="day.hour.16" /></option>
							<option value="17"><s:text name="day.hour.17" /></option>
							<option value="18"><s:text name="day.hour.18" /></option>
							<option value="19"><s:text name="day.hour.19" /></option>
							<option value="20"><s:text name="day.hour.20" /></option>
							<option value="21"><s:text name="day.hour.21" /></option>
							<option value="22"><s:text name="day.hour.22" /></option>
							<option value="23"><s:text name="day.hour.23" /></option>
							<option value="24"><s:text name="day.hour.24" /></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="task.estimated_hour" />
					</td>
					<td>
						<input id="estimated_hour" type="text" value="<s:property value="#request.ProTask.estimated_hour"/>" style="width:200px">
					</td>
					<td colspan="2">
						<s:if test="#request.ProTask.status_value>0">
							<s:if test="#request.ProTask.status_value==100"><s:text name="task.status.doing" /></s:if>
							<s:elseif test="#request.ProTask.status_value==200"><s:text name="task.status.done" /></s:elseif>
						</s:if>
						<s:elseif test="#request.ProTask.task_id<=0">
							<s:text name="task.forced.receive" />:
							<select id="forced">
								<option value=""></option>
								<s:iterator value="#request.UserList" status="st">
									<option value="<s:property value="#Global.RegisterID('UserID',#request.UserList[#st.index][0])" />"><s:property value="#request.UserList[#st.index][1]" /></option>
								</s:iterator>
							</select>
						</s:elseif>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="task.urgent_degree" />
					</td>
					<td colspan="3" style="height:25px;line-height:25px;">
						<s:if test="#request.ProTarget.target_id>0">
							<s:property value="#request.ProTask.urgent_degree"/><s:text name="level" />
						</s:if>
						<s:else>
							<INPUT TYPE="radio" id="urgent_degree1" NAME="urgent_degree" VALUE="1" style="width:20px;padding:0px;margin:0px;" <s:if test="#request.ProTask.urgent_degree == 1">checked</s:if> ><label for="urgent_degree1">1<s:text name="level" /></label>
							<INPUT TYPE="radio" id="urgent_degree2" NAME="urgent_degree" VALUE="2" style="width:20px;padding:0px;margin:0px;" <s:if test="#request.ProTask.urgent_degree == 2">checked</s:if> ><label for="urgent_degree2">2<s:text name="level" /></label>
							<INPUT TYPE="radio" id="urgent_degree3" NAME="urgent_degree" VALUE="3" style="width:20px;padding:0px;margin:0px;" <s:if test="#request.ProTask.urgent_degree == 3">checked</s:if> ><label for="urgent_degree3">3<s:text name="level" /></label>
							<INPUT TYPE="radio" id="urgent_degree4" NAME="urgent_degree" VALUE="4" style="width:20px;padding:0px;margin:0px;" <s:if test="#request.ProTask.urgent_degree == 4">checked</s:if> ><label for="urgent_degree4">4<s:text name="level" /></label>
							<INPUT TYPE="radio" id="urgent_degree5" NAME="urgent_degree" VALUE="5" style="width:20px;padding:0px;margin:0px;" <s:if test="#request.ProTask.urgent_degree == 5">checked</s:if> ><label for="urgent_degree5">5<s:text name="level" /></label>
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
		if($("#start_task_day").length>0){
			$('#start_task_day').datepicker({
				format : 'yyyy-mm-dd'
			});
		}
		if($("#end_task_day").length>0){
			$('#end_task_day').datepicker({
				format : 'yyyy-mm-dd'
			});
		}
		var start_task_hour = "<s:date name="#request.ProTask.start_task_time" format="HH"/>";
		var end_task_hour = "<s:date name="#request.ProTask.end_task_time" format="HH"/>";
		if(start_task_hour.length>0){
			$("#start_task_hour").val(start_task_hour);
		}
		if(end_task_hour.length>0){
			$("#end_task_hour").val(end_task_hour);
		}
		
		$("#estimated_hour").keyup(function(){
			$(this).val($(this).val().replace(/[^0-9.]/g,''));  
	    }).bind("paste",function(){
	    	$(this).val($(this).val().replace(/[^0-9.]/g,''));   
	    }).css("ime-mode", "disabled");
	});
	
	function Submit(){
		if($("#des").val() == ""){
			alert("<s:text name='prompt.task.des.null' />");
		}
		
		var s = $("#start_task_day").val();
	    var e = $("#end_task_day").val();
		if(s == ""){
			alert('<s:text name="prompt.task.starttime.null" />');
			return;
		}
		if(e == ""){
			alert('<s:text name="prompt.task.endtime.null" />');
			return;
		}
		
		if($("#estimated_hour").val() == ""){
			alert('<s:text name="prompt.estimated_hour.null" />');
		}
		
		var b_date = s.split('-');
		var e_date = e.split('-');
		var beginTime = b_date[1] + '-' + b_date[2] + '-' + b_date[0] + ' ' + $("#start_task_hour").val()+":00:00";
   		var endTime = e_date[1] + '-' + e_date[2] + '-' + e_date[0] + ' ' + $("#end_task_hour").val()+":00:00";
		if(beginTime > endTime){
			alert('<s:text name="prompt.task.end_early_start" />');
		}
		
		var receive_task_user = 0;
		if($("#forced")){
			receive_task_user = $("#forced").val();
		}
		$.ajax({
			type:"POST",
			url:"ProTaskSave",
			data:{
				"id":$("#id").val(),
				"targetID":$("#targetID").val(),
				"task_des":$("#des").val(),
				"start_task_time":$("#start_task_day").val()+" "+$("#start_task_hour").val()+":00:00",
				"end_task_time":$("#end_task_day").val()+" "+$("#end_task_hour").val()+":00:00",
				"receive_task_user":receive_task_user,
				"estimated_hour":$("#estimated_hour").val(),
				"urgent_degree":$("input[name='urgent_degree']:checked").val()
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
					window.parent.DOINGIframe.window.location.reload(true);
					window.parent.TODOIframe.window.location.reload(true);
					window.parent.ClosePopDiv();
				}
			}
		});
	}
	</script>
</body>
</html>