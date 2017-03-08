<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html style="width:100%;height:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:100%;">
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div class="boxradius" style="margin:0px auto;width:98%;text-align:left;">
		<input id="id" type="hidden" value="<s:property value="#Global.RegisterID('UserID',#request.Users.user_id)" />">
		<div style="width:100%;">
			<table class="gTab">
				<tr style="border-bottom:1px solid #E5E5E5;">
					<td style="width:30px;"><a href="javascript:void(0);" onclick="javascript:window.location.href='<%=basePath%>admin/AdminUserList?search=1';"><img alt="" src="<%=basePath%>image/left.png">
					</a></td>
					<td style="text-indent: 2em;">
						<h1>
							<s:property value="#request.Users.user_name" />(<s:property value="#request.Users.user_account" />:<s:property value="#request.Users.email" />)
						</h1>
					</td>
					</tr>
				<tr style="border-bottom:1px solid #E5E5E5;">
					<td id="userWorkYear" colspan="2">
						
					</td>
				</tr>
				<tr style="border-bottom:1px solid #E5E5E5;">
					<td id="userWorkMonth" colspan="2">
						<input type='button' style='width:100px;margin:5px;' id='Month1' onclick='SelectMonth(1);' value='<s:text name="month.1" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month2' onclick='SelectMonth(2);' value='<s:text name="month.2" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month3' onclick='SelectMonth(3);' value='<s:text name="month.3" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month4' onclick='SelectMonth(4);' value='<s:text name="month.4" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month5' onclick='SelectMonth(5);' value='<s:text name="month.5" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month6' onclick='SelectMonth(6);' value='<s:text name="month.6" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month7' onclick='SelectMonth(7);' value='<s:text name="month.7" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month8' onclick='SelectMonth(8);' value='<s:text name="month.8" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month9' onclick='SelectMonth(9);' value='<s:text name="month.9" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month10' onclick='SelectMonth(10);' value='<s:text name="month.10" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month11' onclick='SelectMonth(11);' value='<s:text name="month.11" />'>
						<input type='button' style='width:100px;margin:5px;' id='Month12' onclick='SelectMonth(12);' value='<s:text name="month.12" />'>
					</td>
				</tr>
				<tr>
					<td id="historyContent" colspan="2" style="text-align:center">
						
					</td>
				</tr>
			</table>
		</div>
		<input id="select_year" type="hidden" value="">
		<input id="select_month" type="hidden" value="">
	</div>
	<script type="text/javascript">
		var create_year = <s:date name="#request.Users.create_time" format="yyyy" />;
		var login_year = <s:date name="#request.Users.login_time" format="yyyy" />;
		var myDate = new Date();
		var current_month = myDate.getMonth()+1;
		
		function p(s) {
       		return s < 10 ? '0' + s: s;
    	}
    
		var ajax = null;
		$(document).ready(function(){
			var i = login_year;
			if(i != create_year){
				for(i;i>=create_year;i--){
					$("#userWorkYear").append("<input type='button' style='width:150px;margin:5px;' id='Year"+i+"' onclick='SelectYear("+i+");' value='"+i+"'>");
				}
			}
			SelectYear(login_year);
			SelectMonth(current_month);
		});
		
		function SelectYear(year){
			var old_year = $("#select_year").val();
			if(old_year != year){
				$("#Year"+old_year).css("border-color","#DEDEDE");
				$("#select_year").val(year);
				$("#Year"+year).css("border-color","#09F");
				LoadHistoryByAjax();
			}
		}
		
		function SelectMonth(month){
			var old_month = $("#select_month").val();
			if(old_month != month){
				$("#Month"+old_month).css("border-color","#DEDEDE");
				$("#select_month").val(month);
				$("#Month"+month).css("border-color","#09F");
				LoadHistoryByAjax();
			}
		}
		
		function LoadHistoryByAjax(){
			var select_year = $("#select_year").val();
			var select_month = $("#select_month").val();
			if(select_year == "" || select_month == ""){
				return;
			}
			
			var history_min_time = select_year+"-"+select_month+"-01";
			var history_max_time =  select_year+"-"+(parseFloat(select_month)+1)+"-01";
			
			
			if(select_month==12){
				history_max_time = (parseFloat(select_year)+1)+"-01-01";
			}
			
			ajax = $.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminUserLoadHistoryByAjax",
				data:{
					"id":$("#id").val(),
					"history_min_time":history_min_time,
					"history_max_time":history_max_time
				},
				beforeSend:function(){
					$("#historyContent").html("<img src='<%=basePath%>image/loading.gif'>");
				},
				success:function(data){
					if(data == ""){
						alert('<s:text name="error.db" />');
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);
					}else{
						MakeHistoryTable(json_data[0].content);
					}
				}
			});
		}
		
		function MakeHistoryTable(list){
			$("#historyContent").empty();
			var table = "<table class='gTab'>";
			if(list.length>0){
				table += "<tr><td style='width:100px;'><s:text name='history.logtime' /></td><td style='width:80px;'><s:text name='history.type' /><td style='width:80px;'><s:text name='history.status' /></td><td><s:text name='title' /></td></tr>";
				for(var i=0;i<list.length;i++){
					table += "<tr><td>"+list[i][0]+"</td><td>"+list[i][1]+"</td><td>"+list[i][2]+"</td><td>"+list[i][3]+"</td></tr>";
				}
				table += "</table>";
			}else{
				table += "<tr><td style='text-align:center'><s:text name='msg.nocontent' /></td></tr></table>";
			}
			$("#historyContent").html(table);
		}
	
	</script>
</body>
</html>