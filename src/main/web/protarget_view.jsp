<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body>
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div class="boxradius" style="margin:0px auto;width:98%;height:100%;text-align:left;">
		<input id="id" type="hidden" value="<s:property value="#Global.RegisterID('ProTarget',#request.ProTarget.target_id)" />">
		<div style="float:left;width:100%;border-bottom:1px solid #E5E5E5;">
			<table class="gTab">
				<tr>
					<td style="width:30px;">
						<a href="javascript:void(0);" onclick="javascript:window.location.href='ProjectView?id=<s:property value="#Global.RegisterID('Project',#request.ProTarget.pro_id)" />';"><img alt="" src="<%=basePath%>image/left.png"></a>
					</td>
					<td style="text-indent: 2em;">
						<h1>
							<s:property value="#request.ProTarget.target_name" />
						</h1>
					</td>
					<td style="width:120px;"><s:text name="time.start" />:<s:date name="#request.ProTarget.start_time" format="yyyy-MM-dd" /><br /> <s:text name="time.end" />:<s:date name="#request.ProTarget.end_time" format="yyyy-MM-dd"/>
					</td>
					<td style="width:400px;">
						<table style="width:100%;">
							<tr>
								<td>
								<s:if test="#request.ProTarget.create_user_id == #session.user_id">
								<a href="javascript:void(0);" onclick="ChangeTargetStatus();"><s:if test="#request.ProTarget.target_status==0"><img src="<%=basePath%>image/play.png"><s:text name="pro.status.start" /></s:if><s:elseif test="#request.ProTarget.target_status==100"><img src="<%=basePath%>image/stop.png"><s:text name="pro.status.end" /></s:elseif><s:elseif test="#request.ProTarget.target_status==200"><img src="<%=basePath%>image/refresh.png"><s:text name="pro.status.refresh" /></s:elseif></a>
								</s:if>
								</td>
								<td>
								<s:if test="#request.ProTarget.create_user_id == #session.user_id">
								<a href="javascript:void(0);" onclick="OpenDescription();"><img src="<%=basePath%>image/clipboard.png" /><s:text name="target.des" /></a>
								</s:if>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenDownload();"><img src="<%=basePath%>image/download2.png" /><s:text name="pro.download" /></a>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenGraph();"><img alt="" src="<%=basePath%>image/graph.png"><s:text name="graph" /></a>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenHistory();"><img alt="" src="<%=basePath%>image/history.png"><s:text name="history" /></a>
								</td>
								<td>
								<s:if test="#request.Attention>0 && #request.canOperation>0">
									<s:if test="#request.hasAttention>0">
										<a href="javascript:void(0);" onclick="Attention();"><img src="<%=basePath%>image/eye_stop.png"><s:text name="attention.cancel" /></a>
									</s:if>
									<s:else>
										<a href="javascript:void(0);" onclick="Attention();"><img src="<%=basePath%>image/eye_add.png"><s:text name="attention" /></a>
									</s:else>
								</s:if>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div>
			<table class="gTab" id="content">
				<tr style="height:25px;">
					<td style="text-align:center;border-right:1px solid #E5E5E5;border-bottom:1px solid #E5E5E5">
						<h2><s:text name="protask.status.todo" />&nbsp;<s:if test="#request.ProTarget.target_status>0 && #request.ProTarget.create_user_id == #session.user_id"><a href="javascript:void(0);" onclick="AddNewTask();"><img alt="" src="<%=basePath%>image/add_small.png"></a></s:if></h2>
					</td>
					<td style="text-align:center;border-right:1px solid #E5E5E5;border-bottom:1px solid #E5E5E5">
						<h2><s:text name="protask.status.doing" /></h2>
					</td>
					<td style="text-align:center;border-bottom:1px solid #E5E5E5">
						<h2><s:text name="protask.status.done" /></h2>
					</td>
				</tr>
				<tr>
					<td style="border-right:1px solid #E5E5E5;padding:0px;margin:0px;" id="aaa"><iframe id="TODOIframe" src="about:blank" style="width:100%;padding:0px;margin:0px;border:0px;"></iframe>
					</td>
					<td style="border-right:1px solid #E5E5E5;padding:0px;margin:0px;"><iframe id="DOINGIframe" src="about:blank" style="width:100%;padding:0px;margin:0px;border:0px;"></iframe>
					</td>
					<td style="padding:0px;margin:0px;"><iframe src="about:blank" id="DONEIframe" style="width:100%;padding:0px;margin:0px;border:0px;"></iframe>
					</td>
				</tr>
			</table>
		</div>
	<script type="text/javascript">
	$(document).ready(function() {
		var content = $('#content');
		var content_height = (document.documentElement.clientHeight - 70) + "px";
		content.css("height", content_height);
		OpenTask();
	});
	
	
	function OpenTask(){
		var height = (document.documentElement.clientHeight - 90) + "px";
		$("#TODOIframe").css("height",height);
		$("#DOINGIframe").css("height",height);
		$("#DONEIframe").css("height",height);
		
		$("#TODOIframe").attr("src","ProTargetTask?id="+$("#id").val()+"&type=TODO");
		$("#DOINGIframe").attr("src","ProTargetTask?id="+$("#id").val()+"&type=DOING");
		$("#DONEIframe").attr("src","ProTargetTask?id="+$("#id").val()+"&type=DONE");
	}
	
	<s:if test="#request.ProTarget.create_user_id == #session.user_id">
	function AddNewTask(){
		CreatePopDiv("ProTaskEdit","80%","80%","ProTaskEdit?targetID="+$("#id").val());
	}
	
	function ChangeTargetStatus(){
		$.ajax({
			type:"POST",
			url:"ProTargetChangeTargetStatus",
			data:{
				"id":$("#id").val()
			},
			success:function ChangeProStatusOK(data){
				if(data == ""){
					alert('<s:text name="error.db" />');
					return;
				}
				if(data == "ok"){
					window.location.reload();
				}else{
					alert(data);
				}
			}
		});
	}
	
	function OpenDescription(){
		var id = $("#id").val();
		CreatePopDiv("Description","70%","70%","ProTargetEdit?id="+id);
	}
	
	function Attention(){
		$.ajax({
			type:"POST",
			url:"ProTargetAttentionProtarget",
			data:{
				"id":$("#id").val()
			},
			success:function(data){
				if(data == ""){
					alert('<s:text name="error.db" />');
					return;
				}
				if(data == "ok"){
					window.location.reload();
				}else{
					alert(data);
				}
			}
		});
	}
	
	</s:if>
	function OpenDownload(){
		CreatePopDiv("Download","70%","70%","ProTargetDownload?id="+$("#id").val());
	}
	
	function OpenGraph(){
		CreatePopDiv("OpenGraph","70%","70%","ProTargetGraph?id="+$("#id").val());
	}
	
	function OpenHistory(){
		CreatePopDiv("OpenHistory","85%","85%","ProTargetHistory?id="+$("#id").val());
	}
	
	</script>
</body>
</html>