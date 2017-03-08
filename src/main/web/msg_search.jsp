<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html style="width:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;overflow-y:hidden">
<s:bean name="com.idealism.kanban.util.DefineConfig" var="conf" />
	<div>
		<h1>
		<s:text name="search" /></h1>
	</div>
	<div>
		<div class="boxradius" style="margin:0 auto;width:90%;">
			<table class="gTab">
				<tr>
					<td>
						<s:text name="search"/><s:text name="msg.src_type"/>
					</td>
					<td>
						<select id="src_type">
							<s:iterator value="#conf.GetMsgTypeMap().keySet()" id="key">
								<s:if test="#key >0">
									<option value="<s:property value="#key"/>" <s:if test="#request.Info.src_type == #key">selected="true"</s:if>><s:property value="#conf.GetMsgTypeMap().get(#key)"/></option>
								</s:if>
								<s:else>
									<option value="<s:property value="#key"/>"></option>
								</s:else>
							</s:iterator>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="search"/><s:text name="title"/>
					</td>
					<td>
						<input type="text" id="title" value="<s:property value="#request.Info.msg_title" />" style="width:95%;">
					</td>
				</tr>
				<tr>
					<td>
						<s:text name="search"/><s:text name="msg.content"/>
					</td>
					<td>
						<input type="text" id="content" value="<s:property value="#request.Info.msg_content" />" style="width:95%;">
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center">
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
			url:"MsgSaveSearch",
			data:{
				"src_type":$("#src_type").val(),
				"msg_title":$("#title").val(),
				"msg_content":$("#content").val()
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
					window.parent.window.location.href = "MsgList?search=1";
				}
			}
		});
	}
	</script>
</body>
</html>