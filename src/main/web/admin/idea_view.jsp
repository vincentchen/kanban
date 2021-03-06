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
		<input id="IdeaID" type="hidden" value="<s:property value="#Global.RegisterID('Idea',#request.Idea.idea_id)" />">
		<div style="width:100%;border-bottom:1px solid #E5E5E5;">
			<table class="gTab">
				<tr>
					<td style="width:30px;"><a href="javascript:void(0);" onclick="javascript:window.location.href='<%=basePath%>admin/AdminIdeaList?search=1';"><img alt="" src="<%=basePath%>image/left.png"></a></td>
					<td style="text-indent: 2em;">
						<h1>
							<s:property value="#request.Idea.idea_title" />
						</h1>
					</td>
					<td style="width:100px;">
						<s:text name="author" />:<s:if test="#request.Author==''"><s:text name="anonymous" /></s:if><s:else><s:property value="#request.Author"/></s:else>
					</td>
					<td style="width:550px;">
						<table class="gTab">
							<tr>
								<td>
									<s:if test="#session.IdeaVerifyPri.WRITE>0">
									<a href="javascript:void(0);" onclick="EditIdea();"><img src="<%=basePath%>image/edit.png" ><s:text name="edit" /></a>
									</s:if>
								</td>
								<td>
									<img src="<%=basePath%>image/smiley.png" />&nbsp;<s:property value="#request.Idea.good"/>
								</td>
								<td>
									<img src="<%=basePath%>image/smiley_sad.png" />&nbsp;<s:property value="#request.Idea.bad"/>
								</td>
								<s:if test="#request.Idea.create_user_id!=#session.user_id && #request.HasChoose == false">
								<td>
									<a href="javascript:void(0);" onclick="ChangeVote(1);"><img src="<%=basePath%>image/medal_bronze_add.png"><s:text name="idea.agree" /></a>&nbsp;
									<a href="javascript:void(0);" onclick="ChangeVote(2);"><img src="<%=basePath%>image/medal_bronze_delete.png"><s:text name="idea.disagree" /></a>
								</td>
								</s:if>
								<s:if test="#request.Idea.by_audit==0 && #session.IdeaVerifyPri.WRITE>0 && #request.Audit>0">
								<td>
									<a href="javascript:void(0);" onclick="AuditIdea();"><img src="<%=basePath%>image/bell_go_b.png"><s:text name="idea.by_audit" /></a>
								</td>
								</s:if>
								<td>
									<a href="javascript:void(0);" onclick="OpenDownload();"><img src="<%=basePath%>image/download2.png"><s:text name="attach" /></a>
								</td>
								<td>
									<s:if test="#session.IdeaVerifyPri.DEL>0">
									<a href="javascript:void(0);" onclick="Delete();"><img src="<%=basePath%>image/delete.png"><s:text name="delete" /></a>
									</s:if>
								</td>
							</tr>
						</table>
					
					</td>
				</tr>
			</table>
		</div>
		<div style="width:95%;margin:0 auto;padding:3px">
			<s:property escape="false" value="#request.Idea.idea_content" />
		</div>
	</div>
	<script type="text/javascript">
	<s:if test="#session.IdeaVerifyPri.WRITE>0">
	function EditIdea(){
		var id = $("#IdeaID").val();
		CreatePopDiv("EditIdea","70%","70%","<%=basePath%>admin/AdminIdeaEdit?id="+id);
	}
	
	function AuditIdea(){
		var id = $("#IdeaID").val();
		$.ajax({
			url:"<%=basePath%>admin/AdminIdeaAudit",
			type:"POST",
			data:"id="+id,
			success:function(data){
				if(data == ""){
					alert('<s:text name="error.db" />');
					return;
				}
				if(data == "ok"){
					window.location.reload(true);
				}else{
					alert(data);
				}
			}
		});
	}
	</s:if>
	
	function OpenDownload(){
		var id = $("#IdeaID").val();
		CreatePopDiv("Download","70%","70%","<%=basePath%>admin/AdminIdeaDownload?id="+id);
	}
	
	function ChangeVote(choose){
		var id = $("#IdeaID").val();
		$.ajax({
			url:"<%=basePath%>admin/AdminIdeaSaveVoteByUser",
			type:"POST",
			data:"id="+id+"&choose="+choose,
			success:function VoteOk(data){
				if(data == ""){
					alert('<s:text name="error.db" />');
					return;
				}
				if(data == "ok"){
					window.location.reload(true);
				}else{
					alert(data);
				}
			}
		});
	}
	
	<s:if test="#session.IdeaVerifyPri.DEL>0">
	function Delete(rowID){
		var id = $("#IdeaID").val();
		$.ajax({
			url:"<%=basePath%>admin/AdminIdeaDelete",
			type:"POST",
			data:"id="+id,
			success:function (data){
				if(data == ""){
					alert('<s:text name="error.db" />');
					return;
				}
				if(data == "ok"){
					window.location.href = "<%=basePath%>admin/AdminIdeaList?search=1";
				}else{
					alert(data);
				}
			}
		});
	}
	</s:if>
	</script>
</body>
</html>