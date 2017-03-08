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
	<div class="boxradius" style="margin:0px auto;width:98%;height:100%;text-align:left;">
		<input id="HelpID" type="hidden" value="<s:property value="#Global.RegisterID('Help',#request.Help.request_id)" />">
		<div style="width:100%;border-bottom:1px solid #E5E5E5;">
			<table class="gTab">
				<tr>
					<td style="width:30px;"><a href="javascript:void(0);" onclick="javascript:history.go(-1);"><img alt="" src="<%=basePath%>image/left.png"></a></td>
					<td style="text-indent: 2em;">
						<h1>
							<s:property value="#request.Help.help_title" />
						</h1>
					</td>
					<td style="width:130px;">
						<s:text name="time.create" />:<s:date name="#request.Help.create_time" format="yyyy-MM-dd" /><br/>
						<s:text name="help.seek_help_user_id" />:<s:property value="#request.Help.seek_help_user_name"/>
					</td>
					<td style="width:130px;">
						<s:text name="help.solve_time" />:<s:date name="#request.Help.solve_time" format="yyyy-MM-dd" /><br/>
						<s:text name="help.help_user_id" />:<s:property value="#request.Help.help_user_name"/>
					</td>
					<td style="width:80px;"><a href="javascript:void(0);" onclick="OpenDownload();"><img src="<%=basePath%>image/download2.png" /><s:text name="download" /></a>
					</td>
					<s:if test="#request.Help.seek_help_user_id==#session.user_id && #request.Help.help_user_id>0 && #request.Help.solve_time == null">
						<td style="width:120px;">
							<a href="javascript:void(0);" onclick="HelpRefuse();"><img src="<%=basePath%>image/group_delete_big.png" /><s:text name="help.refuse" /></a>
							<a href="javascript:void(0);" onclick="Finish();"><img src="<%=basePath%>image/accept.png" /><s:text name="help.ok" /></a>
						</td>
					</s:if>
					<s:elseif test="#request.Help.seek_help_user_id != #session.user_id && #request.Help.help_user_id<=0">
						<td style="width:80px;">
							<a href="javascript:void(0);" onclick="Accept();"><img src="<%=basePath%>image/group_add_big.png" /><s:text name="help.him" /></a>
						</td>
					</s:elseif>
					<s:if test="#request.Help.seek_help_user_id==#session.user_id && #request.Help.help_user_id<=0">
						<td style="width:80px;">
								<a href="javascript:void(0);" onclick="Delete();"><img src="<%=basePath%>image/delete.png" /><s:text name="delete" /></a>
							
						</td>
					</s:if>
				</tr>
			</table>
		</div>
		<div style="width:95%;margin:0 auto;padding:3px">
			<s:property escape="false" value="#request.Help.help_content" />
		</div>
	</div>
	<script type="text/javascript">
	function OpenDownload(){
		CreatePopDiv("DownLoad","70%","70%","HelpDownload?id="+$("#HelpID").val());
	}
	
	function Accept(){
		$.ajax({
			type:"POST",
			url:"HelpUpdateAccept",
			data:{
				"id":$("#HelpID").val()
			},
			success:function (data){
				if (data != "ok") {
					alert(data);
				}else{
					window.location.reload(true);
				}
			}
		});
	}
	
	function Delete(){
		$.ajax({
			type:"POST",
			url:"HelpDelete",
			data:{
				"id":$("#HelpID").val()
			},
			success:function(data){
				if (data != "ok") {
					alert(data);
				}else{
					history.go(-1);
				}
			}
		});
	}
	
	function HelpRefuse(){
		$.ajax({
			type:"POST",
			url:"HelpRefuse",
			data:{
				"id":$("#HelpID").val()
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
	
	function Finish(){
		$.ajax({
			type:"POST",
			url:"HelpFinish",
			data:{
				"id":$("#HelpID").val()
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