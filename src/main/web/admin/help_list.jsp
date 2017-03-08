<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
	<div style="width:95%;height:45px;margin:0px auto;border-bottom:1px solid #E5E5E5">
		<div style="width:200px"><h1 style="float:left"><s:text name="msg.help" /></h1></div>
		<div style="float:right">
			<table>
				<tr>
					<td style="width:75px;height:45px;line-height:45px;float:left;margin-right:10px;">
					<s:if test="#session.HelpInfoPri.ADD>0">
					<a href="javascript:void(0);" onclick="Add();">
						<img alt="" src="<%=basePath%>image/add.png" style="vertical-align:middle;">
						<h1 style="float:right">
							<s:text name="add" />
						</h1>
					</a>
					</s:if></td>
					<td style="width:75px;height:45px;line-height:45px;float:left;">
					<a href="javascript:void(0);" onclick="Search();">	
						<img alt="" src="<%=basePath%>image/search.png" style="vertical-align:middle;">
						<h1 style="float:right">
							<s:text name="search" />
						</h1>
					</a></td>
				</tr>
			</table>
		</div>
	</div>
	<div style="width:95%;margin:0px auto;" id="HelpList">
		<s:iterator value="#request.HelpList" status="st">
			<div style="border-bottom:1px dashed #E5E5E5">
				<input id="HelpID_<s:property value="#st.index" />" type="hidden" value="<s:property value="#Global.RegisterID('Help',#request.HelpList[#st.index].request_id)" />">
				<table class="gListTab">
					<tr>
						<td>
							<h2>
								<s:property value="#request.HelpList[#st.index].help_title" />
							</h2>
						</td>
						<td style="width:230px;text-align:right;height:30px;line-height:30px;">
						<a href="javascript:void(0);" onclick="Goto(<s:property value="#st.index" />);"><img alt="" src="<%=basePath%>image/table_go.png"><s:text name="goto"/></a>
						<s:if test="#session.HelpInfoPri.WRITE>0"><a href="javascript:void(0);" onclick="Edit(<s:property value="#st.index" />);"><img src="<%=basePath%>image/edit_small.png"><s:text name="edit" /></a></s:if>
						<a href="javascript:void(0);" onclick="DownLoad(<s:property value="#st.index" />);"><img src="<%=basePath%>image/attach.png"><s:text name="attach" /></a>
						<s:if test="#session.HelpInfoPri.DEL>0"><a href="javascript:void(0);" onclick="Delete(<s:property value="#st.index" />);"><img src="<%=basePath%>image/close.png"><s:text name="delete" /></a></s:if>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="float:left"><s:property value="#request.HelpList[#st.index].help_content" /></td>
					</tr>
				</table>
			</div>
		</s:iterator>
	</div>
	<div id='LoadingStatus' style='height:50px;text-align:center;border-left:1px dashed #E5E5E5;border-right:1px dashed #E5E5E5;border-bottom:1px dashed #E5E5E5'></div>
	<script type="text/javascript">
	$(document).ready(function(){  
	    $(".gListTab").mouseover(function(){
	    $(this).css("background-color","#FEF3D1");}).mouseout(function(){
	    $(this).css("background-color","#FFF");})
	});
	
	function Goto(rowID){
		window.location.href = "<%=basePath%>admin/AdminHelpView?id="+$("#HelpID_"+rowID).val();	
	}
	
	<s:if test="#session.IdeaVerifyPri.ADD>0">
	function Add(){
		CreatePopDiv("AddHelp","70%","70%","<%=basePath%>admin/AdminHelpEdit");
	}
	</s:if>
	
	function DownLoad(rowID){
		CreatePopDiv("DownLoad","70%","70%","<%=basePath%>admin/AdminHelpDownload?id="+$("#HelpID_"+rowID).val());
	}
	
	<s:if test="#session.IdeaVerifyPri.WRITE>0">
	function Edit(rowID){
		CreatePopDiv("AddHelp","70%","70%","<%=basePath%>admin/AdminHelpEdit?id="+$("#HelpID_"+rowID).val());
	}
	</s:if>
	
	function Search(){
		CreatePopDiv("Search","70%","70%","<%=basePath%>admin/AdminHelpSearch");
	}
	
	<s:if test="#session.IdeaVerifyPri.DEL>0">
	function Delete(rowID){
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminHelpDelete",
			data:{
				"id":$("#HelpID_"+rowID).val()
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
	</s:if>
	
	function HelpAccept(rowID){
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminHelpUpdateAccept",
			data:{
				"id":$("#HelpID_"+rowID).val()
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
	
	function HelpRefuse(rowID){
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminHelpRefuse",
			data:{
				"id":$("#HelpID_"+rowID).val()
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
	
	function Finish(rowID){
		$.ajax({
			type:"POST",
			url:"<%=basePath%>admin/AdminHelpFinish",
			data:{
				"id":$("#HelpID_"+rowID).val()
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
	
	$(document).ready(function(){
		scrollHandler.init($("#HelpList").get(0),"LoadHelpListByPageNum");
	});
	
	function LoadHelpListByPageNum(){
		if(pageLoadAjax == null){
			currentPageNum++;
			LoadingStart();
			pageLoadAjax = $.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminHelpLoadListByAjax",
				data:{
					"currentPage":currentPageNum,
					"search":1
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
		var len = $("#HelpList div").length;
		if(objList.length>0){
			for(var i=0;i<objList.length;i++){
				len++;
				list_Str = "<div style='border-bottom:1px dashed #E5E5E5'>";
				list_Str += "<input id='HelpID_"+len+"' type='hidden' value='"+objList[i][0]+"'>";
				list_Str += "<table class='gTab'><tr><td><h2>"+objList[i][1]+"</h2></td>";
				list_Str += "<td style='width:230px;text-align:right;height:30px;line-height:30px;'>";
				list_Str += "<a href='javascript:void(0);' onclick='Goto("+len+");'><img src='<%=basePath%>image/table_go.png'><s:text name='goto'/></a> ";
				<s:if test="#session.IdeaVerifyPri.WRITE>0">
				list_Str += "<a href='javascript:void(0);' onclick='Edit("+len+");'><img src='<%=basePath%>image/edit_small.png'><s:text name='edit' /></a> ";
				</s:if>
				list_Str += "<a href='javascript:void(0);' onclick='DownLoad("+len+");'><img src='<%=basePath%>image/attach.png'><s:text name='attach' /></a> ";
				<s:if test="#session.IdeaVerifyPri.DEL>0">
				list_Str += "<a href='javascript:void(0);' onclick='Delete("+len+");'><img src='<%=basePath%>image/close.png'><s:text name='delete' /></a> ";
				</s:if>
				list_Str += "</td></tr><tr>";
				list_Str += "<td colspan='2' style='float:left'>"+objList[i][2]+"</td>";
				list_Str += "</tr></table></div>";
				$("#HelpList").append(list_Str);
			}
		}else{
			LoadingLast();
			scrollHandler.stop();
		}
	}
	</script>
</body>
</html>