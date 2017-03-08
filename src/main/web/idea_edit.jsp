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
	<div>
		<h1>
		<s:if test="#request.Info.idea_id>0"><s:text name="idea.edit" /></s:if>
		<s:else><s:text name="idea.add" /></s:else>
		</h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;height:90%;">
			<input type="hidden" id="id" value="<s:property value="#Global.RegisterID('Idea',#request.Info.idea_id)" />">
			<table class="gTab">
				<tr>
					<td style="width:80px;">
						<s:text name="idea.title" />:
					</td>
					<td>
						<input id="idea_title" type="text" value="<s:property value="#request.Info.idea_title" />" style="width:90%">
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="idea.des" />:
					</td>
					<td>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea id="idea_content" style="width:98%;height:200px;"><s:property value="#request.Info.idea_content" /></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="idea.expressway" />:
					</td>
					<td>
						<s:if test="#request.Info.idea_id>0">
						<INPUT TYPE="radio" id="anonymous1" NAME="anonymous" VALUE="2" style="width:20px;padding:0px;margin:0px;" <s:if test="#request.Info.is_anonymous == 2">checked</s:if>><label for="anonymous1"><s:text name="idea.anonymous1" /></label>
						<INPUT TYPE="radio" id="anonymous2" NAME="anonymous" VALUE="1" style="width:20px;padding:0px;margin:0px;" <s:if test="#request.Info.is_anonymous == 1">checked</s:if>><label for="anonymous2"><s:text name="idea.anonymous2" /></label>
						<INPUT TYPE="radio" id="anonymous3" NAME="anonymous" VALUE="0" style="width:20px;padding:0px;margin:0px;" <s:if test="#request.Info.is_anonymous == 0">checked</s:if>><label for="anonymous3"><s:text name="idea.anonymous3" /></label>
						</s:if>
						<s:else>
						<INPUT TYPE="radio" id="anonymous1" NAME="anonymous" VALUE="2" style="width:20px;padding:0px;margin:0px;"><label for="anonymous1"><s:text name="idea.anonymous1" /></label>
						<INPUT TYPE="radio" id="anonymous2" NAME="anonymous" VALUE="1" style="width:20px;padding:0px;margin:0px;" checked><label for="anonymous2"><s:text name="idea.anonymous2" /></label>
						<INPUT TYPE="radio" id="anonymous3" NAME="anonymous" VALUE="0" style="width:20px;padding:0px;margin:0px;"><label for="anonymous3"><s:text name="idea.anonymous3" /></label>
						</s:else>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="button" value="<s:text name="submit" />" onclick="Submit();">
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function Submit(){
		$.ajax({
			type:"POST",
			url:"IdeaSave",
			data:{
				"id":$("#id").val(),
				"idea_title":$("#idea_title").val(),
				"idea_content":$("#idea_content").val(),
				"is_anonymous":$("input[name='anonymous']:checked").val()
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
					window.parent.window.location.href = "IdeaView?id="+json_data[0].content;
				}
			}
		});
	}
	</script>
</body>
</html>