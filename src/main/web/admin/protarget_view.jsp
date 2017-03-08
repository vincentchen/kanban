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
						<a href="javascript:void(0);" onclick="javascript:window.location.href='<%=basePath%>admin/AdminProjectView?id=<s:property value="#Global.RegisterID('Project',#request.ProTarget.pro_id)" />';"><img alt="" src="<%=basePath%>image/left.png"></a>
					</td>
					<td style="text-indent: 2em;">
						<h1>
							<s:property value="#request.ProTarget.target_name" />
						</h1>
					</td>
					<td style="width:120px;"><s:text name="time.start" />:<s:date name="#request.ProTarget.start_time" format="yyyy-MM-dd" /><br /> <s:text name="time.end" />:<s:date name="#request.ProTarget.end_time" format="yyyy-MM-dd"/>
					</td>
					<td style="width:400px;">
						<table style="width:400px;">
							<tr>
								<td>
								<s:if test="#session.ProPri.WRITE>0">
								<a href="javascript:void(0);" onclick="ChangeTargetStatus();"><s:if test="#request.ProTarget.target_status==0"><img src="<%=basePath%>image/play.png"><s:text name="pro.status.start" /></s:if><s:elseif test="#request.ProTarget.target_status==100"><img src="<%=basePath%>image/stop.png"><s:text name="pro.status.end" /></s:elseif><s:elseif test="#request.ProTarget.target_status==200"><img src="<%=basePath%>image/refresh.png"><s:text name="pro.status.refresh" /></s:elseif></a>
								</s:if>
								</td>
								<td>
								<s:if test="#session.ProPri.WRITE>0">
									<a href="javascript:void(0);" onclick="OpenDescription();"><img src="<%=basePath%>image/clipboard.png" /><s:text name="description" /></a>
								</s:if>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenDownload();"><img src="<%=basePath%>image/download2.png" /><s:text name="pro.download" /></a>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenGraph();"><img alt="" src="<%=basePath%>image/graph.png"><s:text name="graph" /></a>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenHistory();"><img alt="" src="<%=basePath%>image/history.png"><s:text name="history" /></a>
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
						<h2><s:text name="protask.status.todo" />&nbsp;<s:if test="#request.ProTarget.target_status>0 && #session.ProPri.WRITE>0"><a href="javascript:void(0);" onclick="AddNewTask();"><img alt="" src="<%=basePath%>image/add_small.png"></a></s:if></h2>
					</td>
					<td style="text-align:center;border-right:1px solid #E5E5E5;border-bottom:1px solid #E5E5E5">
						<h2><s:text name="protask.status.doing" /></h2>
					</td>
					<td style="text-align:center;border-bottom:1px solid #E5E5E5">
						<h2><s:text name="protask.status.done" /></h2>
					</td>
				</tr>
				<tr>
					<td style="border-right:1px solid #E5E5E5"><iframe id="TODOIframe" src="about:blank" style="width:100%;height:100%;"></iframe>
					</td>
					<td style="border-right:1px solid #E5E5E5"><iframe id="DOINGIframe" src="about:blank" style="width:100%;height:100%;"></iframe>
					</td>
					<td><iframe src="about:blank" id="DONEIframe" style="width:100%;height:100%;"></iframe>
					</td>
				</tr>
			</table>
		</div>
	<script type="text/javascript">
	$(document).ready(function() {
		var content = $('#content');
		var content_height = (document.documentElement.clientHeight - 50-20) + "px";
		content.css("height", content_height);
	});
	
	<s:if test="#session.ProPri.WRITE>0">
	function AddNewTask(){
		CreatePopDiv("ProTaskEdit","80%","80%","<%=basePath%>admin/AdminProTaskEdit?targetID="+$("#id").val());
	}
	</s:if>
	function OpenTask(){
		var height = (document.documentElement.clientHeight - 90) + "px";
		$("#TODOIframe").css("height",height);
		$("#DOINGIframe").css("height",height);
		$("#DONEIframe").css("height",height);
		
		$("#TODOIframe").attr("src","<%=basePath%>admin/AdminProTargetTask?id="+$("#id").val()+"&type=TODO");
		$("#DOINGIframe").attr("src","<%=basePath%>admin/AdminProTargetTask?id="+$("#id").val()+"&type=DOING");
		$("#DONEIframe").attr("src","<%=basePath%>admin/AdminProTargetTask?id="+$("#id").val()+"&type=DONE");
	}
	OpenTask();
	
	<s:if test="#session.ProPri.WRITE>0">
	function ChangeTargetStatus(){
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminProTargetChangeTargetStatus",
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
		CreatePopDiv("Description","70%","70%","<%=basePath%>admin/AdminProTargetEdit?id="+id);
	}
	</s:if>
	function OpenDownload(){
		CreatePopDiv("Download","70%","70%","<%=basePath%>admin/AdminProTargetDownload?id="+$("#id").val());
	}
	
	function OpenGraph(){
		CreatePopDiv("OpenGraph","70%","70%","<%=basePath%>admin/AdminProTargetGraph?id="+$("#id").val());
	}
	
	function OpenHistory(){
		CreatePopDiv("OpenHistory","85%","85%","<%=basePath%>admin/AdminProTargetHistory?id="+$("#id").val());
	}
	</script>
</body>
</html>