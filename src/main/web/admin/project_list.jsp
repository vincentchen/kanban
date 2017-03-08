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
	<div style="width:95%;height:45px;margin:0px auto;border-bottom:1px solid #E5E5E5">
		<div style="width:80px;">
			<h1 style="float:left">
				<s:text name="pro.list" />
			</h1>
		</div>
		<div style="float:right">
			<table>
				<tr>
					<td style="width:75px;height:45px;line-height:45px;float:left;margin-right:10px;">
						<s:if test="#session.ProPri.ADD>0">
						<a href="javascript:void(0);" onclick="CreatePopDiv('AddPanel','70%','70%','<%=basePath%>project_add.jsp');">
							<img src="<%=basePath%>image/add.png" style="vertical-align:middle;">
							<h1 style="float:right"><s:text name="add" /></h1>
						</a>
						</s:if>
					</td>
					<td style="width:75px;height:45px;line-height:45px;float:left;">
						<a href="javascript:void(0);" onclick="OpenSearch();">
							<img alt="" src="<%=basePath%>image/search.png" style="vertical-align:middle;">
							<h1 style="float:right"><s:text name="search" /></h1>
						</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="width:95%;margin:0px auto;" id="ProjectList">
		<s:iterator value="#request.ProList" status="st">
			<div class="gListTab" style="border-bottom:1px dashed #E5E5E5">
				<input type="hidden" id="ProjectId_<s:property value="#st.index" />" value="<s:property value="#request.ProList[#st.index].id" />">
				<table class="gTab">
					<tr>
						<td><h2>
								<s:property value="#request.ProList[#st.index].pro_name" />
							</h2>
						</td>
						<td style="width:400px;text-align:right">
							<div style="height:20px;line-height:20px;">
								<a href="javascript:void(0);" onclick="GotoView(<s:property value="#st.index" />);"><img src="<%=basePath%>image/table_go.png"><s:text name="goto" /></a>
							<s:if test="#session.ProPri.WRITE>0">
								<a href="javascript:void(0);" onclick="EditPro(<s:property value="#st.index" />);"><img src="<%=basePath%>image/table_edit.png"><s:text name="edit" /></a>
								<s:if test="#request.ProList[#st.index].pro_status == 0"><a href="javascript:void(0);" onclick="ChangeProStatus(<s:property value="#st.index" />);"><img src="<%=basePath%>image/next_one.png" /><s:text name="start" /></a></s:if>
								<s:elseif test="#request.ProList[#st.index].pro_status == 100"><a href="javascript:void(0);" onclick="ChangeProStatus(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/stop_small.png"><s:text name="stop" /></a></s:elseif>
								<s:elseif test="#request.ProList[#st.index].pro_status == 200"><a href="javascript:void(0);" onclick="ChangeProStatus(<s:property value="#st.index" />);"><img src="<%=basePath%>image/repeat.png"><s:text name="pro.status.refresh" /></a></s:elseif>
							</s:if>
								<a href="javascript:void(0);" onclick="Attach(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/attach.png"><s:text name="attach" /></a>
								<a href="javascript:void(0);" onclick="ShowUsers(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/group.png"><s:text name="users" /></a>
								<a href="javascript:void(0);" onclick="OpenGraph(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/graph_small.png"><s:text name="graph" /></a>
								<a href="javascript:void(0);" onclick="OpenHistory(<s:property value="#st.index" />);"><img src="<%=basePath%>image/inbox_full.png"><s:text name="history" /></a>
							<s:if test="#session.ProPri.DEL>0">
								<a href="javascript:void(0);" onclick="Delete(<s:property value="#st.index" />)"><img alt="" src="<%=basePath%>image/delete_small.png"><s:text name="delete" /></a>
							</s:if>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="float:left;"><s:property value="#request.ProList[#st.index].pro_des" />
						</td>
					</tr>
				</table>
			</div>
		</s:iterator>
	</div>
	<div id='LoadingStatus'></div>
	<script type="text/javascript">
		$(document).ready(function(){  
	        $(".gListTab").mouseover(function(){
	        $(this).css("background-color","#FEF3D1");}).mouseout(function(){
	        $(this).css("background-color","#FFF");})
   		});
   		
		function GotoView(rowID){
			var id = $("#ProjectId_"+rowID).val();
			window.location.href = "<%=basePath%>admin/AdminProjectView?id="+id;
		}
		<s:if test="#session.ProPri.WRITE>0">
		function EditPro(rowID){
			var id = $("#ProjectId_"+rowID).val();
			CreatePopDiv("Description","70%","70%","<%=basePath%>admin/AdminProjectEdit?id="+id);
		}
		</s:if>
		function Attach(rowID){
			var id = $("#ProjectId_"+rowID).val();
			CreatePopDiv("Download","70%","70%","<%=basePath%>admin/AdminProjectDownload?id="+id);
		}
		
		function OpenSearch(){
			CreatePopDiv("Search","70%","70%","<%=basePath%>admin/AdminProjectSearch");
		}
		
		<s:if test="#session.ProPri.WRITE>0">
		function ChangeProStatus(rowID){
			var id = $("#ProjectId_"+rowID).val();
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminProjectChangeProStatus",
				data:{
					"id":id,
				},
				success:function ChangeProStatusOK(data){
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
		
		<s:if test="#session.ProPri.DEL>0">
		function Delete(rowID){
			var id = $("#ProjectId_"+rowID).val();
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminProjectDelete",
				data:{
					"id":id
				},
				success:function ProDeleteOK(data){
					if(data == ""){
						alert('<s:text name="error.db" />');
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);
					}else{
						window.location.reload(true);
					}
				}
			});
		}
		</s:if>
		
		function ShowUsers(rowID){
			var id = $("#ProjectId_"+rowID).val();
			CreatePopDiv("ShowUsers","70%","70%","<%=basePath%>admin/AdminProjectUsers?id="+id);
		}
		
		function OpenGraph(rowID){
			var id = $("#ProjectId_"+rowID).val();
			CreatePopDiv("OpenGraph","95%","85%","<%=basePath%>admin/AdminProjectGraph?id="+id);
		}
		
		$(document).ready(function(){
			scrollHandler.init($("#ProjectList").get(0),"LoadProjectListByPageNum");
		});
		
		function LoadProjectListByPageNum(){
			if(pageLoadAjax == null){
				currentPageNum++;
				LoadingStart();
				pageLoadAjax = $.ajax({
					type:"POST",
					url:"<%=basePath%>admin/AdminProjectLoadListByAjax",
					data:{
						"currentPage":currentPageNum
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
							LoadingStatus();
							pageLoadAjax = null;
							AppendList(json_data[0].content);
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						LoadingError();
						currentPageNum--;
						pageLoadAjax = null;
	                }
				});
			}
		}
		
		function LoadingStatus(){
			$("#LoadingStatus").html("<a href='javascript:void(0);' onclick='LoadHelpListByPageNum();' style='height:50px;line-height:50px;'><s:text name='showmore' /></a>");
		}
		
		function LoadingStart(){
			$("#LoadingStatus").html("<img src='<%=basePath%>image/loading.gif' style='position: relative;top:50%;margin-top:-16px;'/>");
		}
		
		function LoadingError(){
			$("#LoadingStatus").html("<font style='height:50px;line-height:50px;'><s:text name='loading.error' /> <a href='javascript:void(0);' onclick='LoadHelpListByPageNum();'><s:text name='click.this' /></a> <s:text name='loading.restart' /></font>");
		}
		
		function LoadingLast(){
			$("#LoadingStatus").html("<font style='height:50px;line-height:50px;'><s:text name='prompt.page.end' /></font>");
		}
		
		function AppendList(objList){
			var list_Str = "";
			var len = $("#ProjectList div").length;
			if(objList.length>0){
				for(var i=0;i<objList.length;i++){
					len++;
					list_Str = "<div style='border-bottom:1px dashed #E5E5E5'>";
					list_Str += "<input id='ProjectId_"+len+"' type='hidden' value='"+objList[i][0]+"'>";
					list_Str += "<table class='gTab'><tr><td><h2>"+objList[i][1]+"</h2></td>";
					list_Str += "<td style='width:350px;text-align:right'>";
					list_Str += "<div style='height:20px;line-height:20px;'>";
					list_Str += "<a href='javascript:void(0);' onclick='GotoView("+len+");'><img src='<%=basePath%>image/table_go.png'><s:text name='goto' /></a> ";
					<s:if test="#session.ProPri.WRITE>0">
						list_Str += "<a href='javascript:void(0);' onclick='EditPro("+len+");'><img src='<%=basePath%>image/table_edit.png'><s:text name='edit' /></a> ";
						if(objList[i][3] == 0){
							list_Str += "<a href='javascript:void(0);' onclick='ChangeProStatus("+len+");'><img src='<%=basePath%>image/next_one.png' /><s:text name='start' /> ";
						}else if(objList[i][3] == 100){
							list_Str += "<a href='javascript:void(0);' onclick='ChangeProStatus("+len+");'><img src='<%=basePath%>image/stop_small.png'><s:text name='stop' /></a> ";
						}else if (objList[i][3] == 200) {
							list_Str += "<a href='javascript:void(0);' onclick='ChangeProStatus("+len+");'><img src='<%=basePath%>image/repeat.png'><s:text name='pro.status.refresh' /></a> ";
						}
					</s:if>
					list_Str += "<a href='javascript:void(0);' onclick='Attach("+len+");'><img src='<%=basePath%>image/attach.png'><s:text name='attach' /></a> ";
					list_Str += "<a href='javascript:void(0);' onclick='ShowUsers("+len+");'><img src='<%=basePath%>image/group.png'><s:text name='users' /></a> ";
					list_Str += "<a href='javascript:void(0);' onclick='OpenGraph("+len+");'><img src='<%=basePath%>image/graph_small.png'><s:text name='graph' /></a> ";
					<s:if test="#session.ProPri.DEL>0">
						list_Str += "<a href='javascript:void(0);' onclick='Delete("+len+")'><img src='<%=basePath%>image/delete_small.png'><s:text name='delete' /></a> ";
					</s:if>
					list_Str += "</div></td></tr><tr><td colspan='2'>"+objList[i][2]+"</td></tr></table></div>";
					$("#ProjectList").append(list_Str);
				}
			}else{
				LoadingLast();
				scrollHandler.stop();
			}
		}
		
		function OpenHistory(rowID){
			var id = $("#ProjectId_"+rowID).val();
			CreatePopDiv("OpenHistory","70%","70%","<%=basePath%>admin/AdminProjectHistory?id="+id);
		}
	</script>
</body>
</html>