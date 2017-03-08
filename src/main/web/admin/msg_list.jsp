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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/radio.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:100%;">
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div style="width:95%;height:45px;margin:0px auto;border-bottom:1px solid #E5E5E5">
		<div style="width:300px;">
			<h1 style="float:left;">
				<s:text name="desktop.msg" />
			</h1>
		</div>
		<div style="float:right">
			<table>
				<tr>
					<td style="width:75px;height:45px;line-height:45px;float:left;margin-right:10px;">
						<s:if test="#session.MsgInfo.ADD>0">
						<a href="javascript:void(0);" onclick="Add();">
							<img alt="" src="<%=basePath%>image/add.png" style="vertical-align:middle;">
							<h1 style="float:right">
								<s:text name="add" />
							</h1>
						</a>
						</s:if>
					</td>
					<td style="width:75px;height:45px;line-height:45px;float:left;">
					<a href="javascript:void(0);" onclick="SearchMsg();">
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
	<s:if test="#session.MsgInfo.DEL>0">
	<div style="width:95%;margin:0px auto;">
		<div style="border-bottom:1px solid #E5E5E5">
			<table class="gTab">
				<tr>
					<td><input id="SelectAllMsg" name="SelectAllMsg" type="checkbox" onclick="SelectAll();"><label for="SelectAllMsg"><s:text name="select.all" />
					</label> <input type="button" value="<s:text name="delete.select" />" onclick="DeleteSelect();" style="width:100px;" /></td>
				</tr>
			</table>
		</div>
	</div>
	</s:if>
	<div style="width:95%;margin:0px auto;" id="MsgList">
		<s:iterator value="#request.MsgList" status="st">
			<div style="border-bottom:1px dashed #E5E5E5">
				<input type="hidden" id="MsgID_<s:property value="#st.index"/>" value="<s:property value="#request.MsgList[#st.index].id" />"/>
				<table class="gListTab" <s:if test="#request.MsgList[#st.index].is_read==0">style="font-weight:bold;"</s:if>>
					<tr>
						<s:if test="#session.MsgInfo.DEL>0"><td style="width:20px;"><input name="SelectMsg" type="checkbox" value="<s:property value="#st.index"/>"></td></s:if>
						<td style="width:60px;"><s:property value="#request.MsgList[#st.index].src_type_name" /></td>
						<td><a href="javascript:void(0);" onclick="Redirect(<s:property value="#st.index"/>);"><s:property value="#request.MsgList[#st.index].msg_title" /></a></td>
						<td style="width:140px;"><s:date name="#request.MsgList[#st.index].create_time" format="yyyy-MM-dd HH:mm:ss" /></td>
						<td style="width:60px;"><s:if test="#session.MsgInfo.DEL>0"><a href="javascript:void(0);" onclick="Delete(<s:property value="#st.index"/>);"><img src="<%=basePath%>image/close.png"><s:text name="delete" /></a></s:if></td>
					</tr>
					<tr>
						<td colspan="3"><s:property value="#request.MsgList[#st.index].msg_content" /></td>
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
	
		<s:if test="#session.MsgInfo.ADD>0">
		function Add(){
			CreatePopDiv("Add","70%","70%","<%=basePath%>admin/msg_edit.jsp");
		}
		</s:if>
		
		<s:if test="#session.MsgInfo.DEL>0">
		function Delete(rowID){
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminMsgDelete",
				data:{
					"msgIDs":$("#MsgID_"+rowID).val()
				},
				success:function SubmitOK(data){
					if (data != "ok") {
						alert(data);
					}else{
						window.location.reload(true);
					}
				}
			});
		}
		</s:if>
		
		function SelectAll(){
			$("input[name='SelectMsg']").each(function(i){        
				$(this).attr("checked","checked");
			});
		}
		
		function SearchMsg(){
			CreatePopDiv("Search","70%","40%","<%=basePath%>admin/AdminMsgSearch");
		}
		
		<s:if test="#session.MsgInfo.DEL>0">
		function DeleteSelect(){
			var value="";
			$("input[name='SelectMsg']:checked").each(function(i){        
				value += $("#MsgID_"+$(this).val()).val()+',';
			});
			var msgIDs = value.substring(0,value.length-1);
			if(msgIDs.length <= 0){
				alert('<s:text name="select.no" />');
				return;
			}
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminMsgDelete",
				data:{
					"msgIDs":msgIDs
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
		</s:if>
		
		function Redirect(rowID){
			var msgID = $("#MsgID_"+rowID).val();
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminMsgRedirect",
				data:{
					"id":msgID
				},
				success:function(data){
					if(data == ""){
						alert('<s:text name="error.db" />');
						$('#block').css("display", "none");
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);
					}else{
						window.location.href = json_data[0].content;
					}
				}
			});
		}
		
		$(document).ready(function(){
			scrollHandler.init($("#MsgList").get(0),"LoadMsgListByPageNum");
		});
		
		function LoadMsgListByPageNum(){
			if(pageLoadAjax == null){
				currentPageNum++;
				LoadingStart();
				pageLoadAjax = $.ajax({
					type:"POST",
					url:"<%=basePath%>admin/AdminMsgLoadListByAjax",
					data:{
						"currentPage":currentPageNum,
						"search":1
					},
					success:function(data){
						if(data == ""){
							alert('<s:text name="error.db" />');
							$('#block').css("display", "none");
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
			var len = $("#MsgList div").length;
			if(objList.length>0){
				for(var i=0;i<objList.length;i++){
					len++;
					list_Str = "<div style='border-bottom:1px dashed #E5E5E5'>";
					list_Str += "<table class='gTab' ";
					if(objList[i].is_read == 0){
						list_Str += "style='font-weight:bold;'";
					}
					list_Str += "><tr>";
					list_Str += "<td style='width:20px;'><input type='hidden' id='MsgID_"+len+"' value='"+objList[i][0]+"'/><input name='SelectMsg' type='checkbox' value='"+len+"'></td>";
					list_Str += "<td style='width:60px;'>"+objList[i][1]+"</td>";
					list_Str += "<td><a href='javascript:void(0);' onclick='Redirect("+len+");'>"+objList[i][2]+"</a></td>";
					list_Str += "<td style='width:140px;'>"+objList[i][3]+"</td>";
					<s:if test="#session.MsgInfo.DEL>0">
					list_Str += "<td style='width:60px;'><a href='javascript:void(0);' onclick='Delete("+len+");'><img src='<%=basePath%>image/close.png'><s:text name="delete" /></a></td>";
					</s:if>
					list_Str += "</tr>";
					list_Str += "<tr><td colspan='3'>"+objList[i][4]+"</td></tr></table></div>";
					$("#MsgList").append(list_Str);
				}
			}else{
				LoadingLast();
				scrollHandler.stop();
			}
		}
	</script>
</body>
</html>