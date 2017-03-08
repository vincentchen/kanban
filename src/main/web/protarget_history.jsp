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
<script src="<%=basePath%>js/datepicker.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:98%;">
	<s:bean name="com.idealism.kanban.util.DefineConfig" var="config"></s:bean>
	<div>
		<h1>
			<s:text name="history" />
		</h1>
	</div>
	<div class="boxradius" style="margin:0 auto;width:70%;height:70%;overflow-y:scroll;">
		<table class="gListTab" id="current_month_history">
			<tr>
				<td colspan="3" style="text-align:center;font-size:14px;color:#000;font-weight:bold;border-bottom:1px solid #E5E5E5;"><s:text name="history.current.month" />
				</td>
			</tr>
			<tr>
				<td style="width:50px;border-bottom:1px solid #E5E5E5;border-right:1px solid #E5E5E5"><s:text name="history.status" />
				</td>
				<td style="text-align:center;border-bottom:1px solid #E5E5E5;border-right:1px solid #E5E5E5"><s:text name="history.content" />
				</td>
				<td style="width:120px;border-bottom:1px solid #E5E5E5;"><s:text name="history.logtime" />
				</td>
			</tr>
			<s:if test="#request.History.size()>0">
				<s:iterator value="#request.History" status="st">
					<tr>
						<td style="border-bottom:1px dashed #E5E5E5;border-right:1px dashed #E5E5E5" >
							<s:property value="#config.GetStatusName(#request.History[#st.index].history_status)"/>
						</td>
						<td style="border-bottom:1px dashed #E5E5E5;border-right:1px dashed #E5E5E5">
							<s:property value="#request.History[#st.index].history_content"/>
						</td>
						<td style="border-bottom:1px dashed #E5E5E5;">
							<s:date name="#request.History[#st.index].create_time" format="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<td colspan="3" style="text-align:center">
						<s:text name="msg.nocontent"/>
					</td>
				</tr>
			</s:else>
		</table>
	</div>
	<div class="boxradius" style="margin:5px auto;width:70%;height:10%;">
		<table class="gTab" style="height:100%">
			<tr>
				<td style="width:32px;"><img src="<%=basePath%>image/date.png" /></td>
				<td style="width:50px;"><s:text name="history.download" /></td>
				<td style="width:50px;"><s:text name="time.start" /></td>
				<td style="padding-right:10px;"><input id="start_time" type="text" value="" /></td>
				<td style="width:50px;"><s:text name="time.end" /></td>
				<td style="padding-right:10px;"><input id="end_time" type="text" value="" /></td>
				<td style="text-align:left;width:80px;vertical-align:middle;"><a href="javascript:void(0);" onclick="DownLoadHistory();"><img src="<%=basePath%>image/download.png" />下载</a></td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
	$(document).ready(function() {
		$('#start_time').datepicker({
			format : 'yyyy-mm-dd'
		});
		$('#end_time').datepicker({
			format : 'yyyy-mm-dd'
		});
		$(".gListTab tr").mouseover(function(){
        $(this).addClass("hover");}).mouseout(function(){
        $(this).removeClass("hover");})
        $(".gListTab tr:even").addClass("alt");
	});
	function DownLoadHistory() {
		if ($("#start_time").val() != "" && $("#end_time").val() != "") {
			if (Date.parse($("#end_time").val()) <= Date.parse($("#start_time").val())) {
				alert("<s:text name='error.endtime.greater.starttime' />");
				return;
			}
		} else {
			alert("<s:text name='error.time.is_null' />");
			return;
		}
		window.location.href = "ProTargetDownLoadHistory?history_min_time=" + $("#start_time").val() + "&history_max_time=" + $("#end_time").val();
	}
	</script>
</body>
</html>