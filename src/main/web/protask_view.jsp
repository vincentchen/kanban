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
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div class="boxradius" style="margin:0px auto;width:98%;height:98%;text-align:left;">
		<input id="id" type="hidden" value="<s:property value="#Global.RegisterID('ProTask',#request.ProTask.task_id)" />">
		
		<div style="width:100%;height:100%;">
			<table class="gTab" style="height:100%;">
				<tr style="border-bottom:1px solid #E5E5E5;height:50px;">
					<td style="width:30px;">
						<a href="javascript:void(0);" onclick="javascript:history.go(-1);"><img alt="" src="<%=basePath%>image/left.png"></a>
					</td>
					<td style="text-indent: 2em;width:200px;">
						<h1>
							#<s:property value="#request.ProTask.task_id" />
						</h1>
					</td>
					<td><s:text name="task.start_task_time" />:<s:date name="#request.ProTask.start_task_time" format="yyyy-MM-dd HH:mm:ss" /><br /> <s:text name="task.end_task_time" />:<s:date name="#request.ProTask.end_task_time" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td><s:text name="task.start_time" />:<s:date name="#request.ProTask.start_time" format="yyyy-MM-dd HH:mm:ss" /><br /> <s:text name="task.end_time" />:<s:date name="#request.ProTask.end_time" format="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						<s:text name="task.urgent_degree" />:<s:property value="#request.ProTask.urgent_degree"/> <s:text name="level" />
					</td>
					<td>
						<s:text name="create.user" />:<s:property value="#request.ProTask.create_user_name" /><br />
						<s:text name="receive.user" />:<s:property value="#request.ProTask.receive_task_user_name" />
					</td>
					<td>
						<s:text name="task.estimated_hour" />:<s:property value="#request.ProTask.estimated_hour"/><br />
						<s:text name="task.actual_hour" />:<s:property value="#request.ProTask.estimated_hour"/>
					</td>
					<td style="width:350px;">
						<table style="width:100%;">
							<tr>
								<td>
									<s:if test="#request.canOperation">
										<s:if test="#request.ProTask.status_value == 0">
											<a href="javascript:void(0);" onclick="checkout(<s:property value="#st.index" />);"><img src="<%=basePath%>image/upcoming_work.png"><s:text name="msg.receive_task" /></a>
										</s:if>
										<s:elseif test="#request.ProTask.status_value == 100">
											<a href="javascript:void(0);" onclick="checkin(<s:property value="#st.index" />);"><img src="<%=basePath%>image/check.png"><s:text name="msg.finish_task" /></a>&nbsp;&nbsp;
											<a href="javascript:void(0);" onclick="cancel(<s:property value="#st.index" />);"><img src="<%=basePath%>image/delete_small.png"><s:text name="msg.block_task" /></a>
										</s:elseif>
										<s:elseif test="#request.ProTask.status_value == 200">
											<s:text name="protask.status.done" />
										</s:elseif>
										<s:elseif test="#request.ProTask.status_value == 300">
											<s:text name="protask.status.cancel" />
										</s:elseif>
									</s:if>
								</td>
								<td><s:if test="#request.ProTask.create_user_id == #session.user_id && #request.ProTask.status_value != 200 && #request.ProTask.status_value != 300"><a href="javascript:void(0);" onclick="OpenDescription();"><img src="<%=basePath%>image/clipboard.png" /><s:text name="edit" /></s:if></a>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenAttach();"><img src="<%=basePath%>image/download2.png" /><s:text name="pro.download" /></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="8" style='height:100%;padding:10px;vertical-align:top'>
						<s:property value="#request.ProTask.task_des" />
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		
		function OpenAttach(){
			CreatePopDiv("Description","70%","70%","ProTaskDownload?id="+$("#id").val());
		}
		
		<s:if test="#request.ProTask.create_user_id == #session.user_id">
		function OpenDescription(){
			CreatePopDiv("ProTaskEdit","80%","80%","ProTaskEdit?id="+$("#id").val());
		}
		</s:if>
		
		<s:if test="#request.canOperation">
		function checkout(){
			$.ajax({
				type:"POST",
				url:"ProTaskReceive",
				data:{
					"id":$("#id").val()
				},
				success : function(data) {
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
		
		function checkin(){
			$.ajax({
				type:"POST",
				url:"ProTaskFinish",
				data:{
					"id":$("#id").val()
				},
				success : function(data) {
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
		
		function cancel(id){
			$.ajax({
				type:"POST",
				url:"ProTaskBlock",
				data:{
					"id":$("#id").val()
				},
				success : function(data) {
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
		</s:if>
	</script>
</body>
</html>