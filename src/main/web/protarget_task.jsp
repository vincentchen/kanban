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
<script src="<%=basePath%>js/masonry.pkgd.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:100%;">
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div id="container" style="width:100%;height:100%;position:relative;">
		<s:iterator value="#request.ProTask" status="st">
			<div class="op boxshadow" style="margin:2px;float: left;position: absolute;min-width:200px;">
				<input type="hidden" id="id_<s:property value="#st.index" />" value="<s:property value="#Global.RegisterID('ProTask',#request.ProTask[#st.index].task_id)" />">
				<table class="gTab">
					<tr style="height:24px;">
						<td style="text-align:left">#<s:property value="#request.ProTask[#st.index].task_id" />
						</td>
						<td style="text-align:center">
							<s:property value="#request.ProTask[#st.index].estimated_hour" /><s:text name="hour" />
						</td>
						<td style="text-align:right">
							<img src="<%=basePath%>image/lv<s:property value="#request.ProTask[#st.index].urgent_degree"/>.png" />
						</td>
					</tr>
					<tr>
						<td colspan="3" style="text-align:left">
							<s:property value="#request.ProTask[#st.index].task_des"/>
						</td>
					</tr>
					<tr>
						<s:if test="#request.ProTask[#st.index].status_value == 0">
							<td style="text-align:left" colspan="2">
								<s:date name="#request.ProTask[#st.index].start_task_time" format="yyyy-MM-dd HH:mm"/>
							</td>
							<td style="text-align:right">
								<s:property value="#request.ProTask[#st.index].create_user_name"/>
							</td>
						</s:if>
						<s:elseif test="#request.ProTask[#st.index].status_value == 100">
							<td style="text-align:left" colspan="2">
								<s:date name="#request.ProTask[#st.index].end_task_time" format="yyyy-MM-dd HH:mm"/>
							</td>
							<td style="text-align:right">
								<s:property value="#request.ProTask[#st.index].receive_task_user_name"/>
							</td>
						</s:elseif>
						<s:elseif test="#request.ProTask[#st.index].status_value == 200 || #request.ProTask[#st.index].status_value == 300">
							<td style="text-align:left" colspan="2">
								<s:date name="#request.ProTask[#st.index].end_time" format="yyyy-MM-dd HH:mm"/>
							</td>
							<td style="text-align:right">
								<s:property value="#request.ProTask[#st.index].receive_task_user_name"/>
							</td>
						</s:elseif>
					</tr>
					<tr style="padding:0px;height:25px;line-height:25px;">
						<td colspan="2">
							<a href="javascript:void(0);" onclick="Open(<s:property value="#st.index" />);"><img src="<%=basePath%>image/application_go.png"></a>
							<s:if test="#request.ProTask[#st.index].create_user_id == #session.user_id && #request.ProTask[#st.index].status_value != 300 && #request.ProTask[#st.index].status_value != 200">
								<a href="javascript:void(0);" onclick="Edit(<s:property value="#st.index" />);"><img src="<%=basePath%>image/edit_small.png"></a>
							</s:if>
							<a href="javascript:void(0);" onclick="OpenAttach(<s:property value="#st.index" />);"><img src="<%=basePath%>image/attach.png"></a>
						</td>
						<s:if test="#request.canOperation>0">
						<td style="text-align:right;">
							<s:if test="#request.ProTask[#st.index].status_value == 0">
								<a href="javascript:void(0);" onclick="checkout(<s:property value="#st.index" />);"><img src="<%=basePath%>image/checkout.png"></a>
							</s:if>
							<s:elseif test="#request.ProTask[#st.index].status_value == 100 && #request.ProTask[#st.index].receive_task_user == #session.user_id">
								<a href="javascript:void(0);" onclick="checkin(<s:property value="#st.index" />);"><img src="<%=basePath%>image/checkin.png"></a>
								<a href="javascript:void(0);" onclick="cancel(<s:property value="#st.index" />);"><img src="<%=basePath%>image/delete_small.png"></a>
							</s:elseif>
							<s:elseif test="#request.ProTask[#st.index].status_value == 200">
								<s:text name="protask.status.done" />
							</s:elseif>
							<s:elseif test="#request.ProTask[#st.index].status_value == 300">
								<s:text name="protask.status.cancel" />
							</s:elseif>
						</td>
						</s:if>
					</tr>
				</table>
			</div>
		</s:iterator>
	</div>
	<script type="text/javascript">
		$(function() {
			var $container = $('#container');
			$('#container').masonry({
			  itemSelector: '.op',
			  "gutter": 10,
			});
			/*
			$container.imagesLoaded(function() {
				$container.masonry({
					// 每一列数据的宽度，若所有栏目块的宽度相同，可以不填这段
					// .imgbox 为各个图片的容器
					itemSelector : '.op',
					
					isFitWidth : true,
					isResizable : true
				});
			});
			*/
		});
		
		function Open(id){
			window.parent.window.location.href = "ProTaskView?id="+$("#id_"+id).val();
		}
		
		<s:if test="#request.ProTarget.create_user_id == #session.user_id">
		function Edit(id){
			CreateCrossPopDIV(window.parent,"ProTaskEdit","80%","80%","ProTaskEdit?id="+$("#id_"+id).val());
		}
		</s:if>
		
		<s:if test="#request.canOperation>0">
		function checkout(id){
			$.ajax({
				type:"POST",
				url:"ProTaskReceive",
				data:{
					"id":$("#id_"+id).val()
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
						window.parent.DOINGIframe.window.location.reload();
						window.location.reload();
					}
				}
			});
		}
		
		function checkin(id){
			$.ajax({
				type:"POST",
				url:"ProTaskFinish",
				data:{
					"id":$("#id_"+id).val()
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
						window.parent.DONEIframe.window.location.reload();
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
					"id":$("#id_"+id).val()
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
						window.parent.DONEIframe.window.location.reload();
						window.location.reload();
					}
				}
			});
		}
		</s:if>
		
		function OpenAttach(rowID){
			var id = $("#id_"+rowID).val();
			CreateCrossPopDIV(window.parent,"Description","70%","70%","ProTaskDownload?id="+id);
		}
	</script>
</body>
</html>