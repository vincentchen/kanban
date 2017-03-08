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
	<div style="width:95%;height:45px;margin:0px auto;border-bottom:1px solid #E5E5E5">
		<div style="width:200px"><h1 style="float:left"><s:text name="msg.idea" /></h1></div>
		<div style="float:right">
			<table>
				<tr>
					<td style="width:75px;height:45px;line-height:45px;float:left;margin-right:10px;">
					<a href="javascript:void(0);" onclick="AddIdea();">
						<img alt="" src="<%=basePath%>image/add.png" style="vertical-align:middle;">
						<h1 style="float:right"><s:text name="add" /></h1>
					</a></td>
					<td style="width:75px;height:45px;line-height:45px;float:left;">
					<a href="javascript:void(0);" onclick="SearchIdea();">
						<img alt="" src="<%=basePath%>image/search.png" style="vertical-align:middle;">
						<h1 style="float:right">
							<s:text name="search" />
						</h1>
						</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="width:95%;margin:0px auto;" id="IdeaList">
		<s:iterator value="#request.IdeaInfo" status="st">
			<div style="border-bottom:1px dashed #E5E5E5">
				<input id="IdeaID_<s:property value="#st.index" />" type="hidden" value="<s:property value="#request.IdeaInfo[#st.index].id" />">
				<table class="gListTab">
					<tr>
						<td>
							<h2>
								<s:if test="#request.IdeaInfo[#st.index].is_read==0"><img src="<%=basePath%>image/new.gif" /></s:if><s:if test="#request.IdeaInfo[#st.index].create_user_name != ''"><s:property value="#request.IdeaInfo[#st.index].create_user_name" />&nbsp;<s:text name="say" />:</s:if><s:property value="#request.IdeaInfo[#st.index].idea_title" />
							</h2>
						</td>
						<td style="width:250px;text-align:right;height:30px;line-height:30px;">
							<a href="javascript:void(0);" onclick="OpenIdea(<s:property value="#st.index" />);"><img src="<%=basePath%>image/table_go.png"><s:text name="goto" /></a>
							<s:if test="#request.IdeaInfo[#st.index].create_user_id==#session.user_id">
								<a href="javascript:void(0);" onclick="OpenAttach(<s:property value="#st.index" />);"><img src="<%=basePath%>image/attach.png"><s:text name="attach" /></a>
							</s:if>
							<s:elseif test="#request.IdeaInfo[#st.index].hasAttach==true">
								<a href="javascript:void(0);" onclick="OpenAttach(<s:property value="#st.index" />);"><img src="<%=basePath%>image/attach.png"><s:text name="attach" /></a>
							</s:elseif>
							<img src="<%=basePath%>image/happy.png"><s:property value="#request.IdeaInfo[#st.index].cntGood" />
							<img src="<%=basePath%>image/unhappy.png"><s:property value="#request.IdeaInfo[#st.index].cntBad" />
							<s:if test="#request.IdeaInfo[#st.index].hasChoose==false && #request.IdeaInfo[#st.index].create_user_id!=#session.user_id">
								<a href="javascript:void(0);" onclick="ChangeVote(<s:property value="#st.index" />,1);"><img src="<%=basePath%>image/good.png"></a>&nbsp;
								<a href="javascript:void(0);" onclick="ChangeVote(<s:property value="#st.index" />,2);"><img src="<%=basePath%>image/bad.png"></a>
							</s:if>
							<s:if test="#request.IdeaInfo[#st.index].create_user_id==#session.user_id">
								<a href="javascript:void(0);" onclick="EditIdea(<s:property value="#st.index" />);"><img src="<%=basePath%>image/edit_small.png"><s:text name="edit" /></a>
								<a href="javascript:void(0);" onclick="Delete(<s:property value="#st.index" />);"><img src="<%=basePath%>image/close.png"><s:text name="delete" /></a>
							</s:if>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<s:property value="#request.IdeaInfo[#st.index].idea_content" />
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
	
	function OpenIdea(rowID){
		window.location.href = "IdeaView?id="+$("#IdeaID_"+rowID).val();
	}
	
	function AddIdea(){
		CreatePopDiv("AddIdea","70%","70%","IdeaEdit");
	}
	
	function EditIdea(rowID){
		var id = $("#IdeaID_"+rowID).val();
		CreatePopDiv("EditIdea","70%","70%","IdeaEdit?id="+id);
	}
	
	function OpenAttach(rowID){
		var id = $("#IdeaID_"+rowID).val();
		CreatePopDiv("Download","70%","70%","IdeaDownload?id="+id);
	}
	
	function SearchIdea(){
		CreatePopDiv("Search","50%","150px","IdeaSearch");
	}
	
	function ChangeVote(rowID,choose){
		var id = $("#IdeaID_"+rowID).val();
		$.ajax({
			url:"IdeaSaveVoteByUser",
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
	
	function Delete(rowID){
		var id = $("#IdeaID_"+rowID).val();
		$.ajax({
			url:"IdeaDelete",
			type:"POST",
			data:"id="+id,
			success:function (data){
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
	
	$(document).ready(function(){
		scrollHandler.init($("#IdeaList").get(0),"LoadIdeaListByPageNum");
	});
	
	function LoadIdeaListByPageNum(){
		if(pageLoadAjax == null){
			currentPageNum++;
			LoadingStart();
			pageLoadAjax = $.ajax({
				type:"POST",
				url:"IdeaLoadListByAjax",
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
		var len = $("#IdeaList div").length;
		if(objList.length>0){
			for(var i=0;i<objList.length;i++){
				len++;
				list_Str = "<div style='border-bottom:1px dashed #E5E5E5'>";
				list_Str += "<input id='IdeaID_"+len+"' type='hidden' value='"+objList[i][0]+"'>";
				list_Str += "<table class='gTab'><tr><td><h2>";
				if(objList[i][1] != ""){
					list_Str += objList[i][1]+" <s:text name='say' />:";
				}
				list_Str += objList[i][2]+"</h2></td>";
				list_Str += "<td style='width:200px;text-align:right;height:30px;line-height:30px;'>";
				if(objList[i][3]>0){
					list_Str += "<a href='javascript:void(0);' onclick='OpenAttach("+len+");'><img src='<%=basePath%>image/attach.png'><s:text name='attach' /></a> ";
				}
				list_Str += "<img src='<%=basePath%>image/arrow_up.png'>"+objList[i][4]+" ";
				list_Str += "<img src='<%=basePath%>image/arrow_up.png'>"+objList[i][5]+" ";
				if(objList[i][6]>0){
					list_Str += "<a href='javascript:void(0);'><img src='<%=basePath%>image/good.png' onclick='ChangeVote("+len+",1);'></a> ";
					list_Str += "<a href='javascript:void(0);'><img src='<%=basePath%>image/bad.png' onclick='ChangeVote("+len+",2);'></a> ";
				}
				if(objList[i][7]>0){
					list_Str += "<a href='javascript:void(0);' onclick='EditIdea("+len+");'><img src='<%=basePath%>image/edit_small.png'><s:text name='edit' /></a> ";
					list_Str += "<a href='javascript:void(0);' onclick='Delete("+len+");'><img src='<%=basePath%>image/close.png'><s:text name='delete' /></a>";
				}
				list_Str += "</td></tr><tr><td colspan='2'>"+objList[i][8]+"</td></tr></table></div>";
				
				$("#IdeaList").append(list_Str);
			}
		}else{
			LoadingLast();
			scrollHandler.stop();
		}
	}
	</script>
</body>
</html>