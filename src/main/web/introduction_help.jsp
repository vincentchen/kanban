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
<script src="<%=basePath%>js/jquery-ui-1.10.2.custom.min.js"></script>

</head>
<body style="width:100%;height:100%;">
	<div style="width:100%;height:100%;overflow-x: hidden;">
		<ul id="intro_list" style="width:500%;height:100%;padding:0px;list-style-type:none;margin-left:-1px;">
			<li style="width:20%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>介绍</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td>
								<h2>此功能主要为企业提供跨部门，跨职能的问题解决办法，通过发布，接取，完成等功能帮助需要帮助的人完成某项事务，同时把这个过程记录进个人历史中，以便后期进行绩效考评。</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:20%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>新增求助</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/help_add.jpg" />
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 求助标题，必填&nbsp;&nbsp;&nbsp;② : 求助内容&nbsp;&nbsp;&nbsp;③ : 指定帮助人，输入名称可自动查询相关人，需选择查询出的员工才可保存。不支持拼音模糊搜索<br />
								④ : 高级查询，通过多种查询方法查询员工,具体看下一步操作</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:20%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>高级查询</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/user_search.png" />
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 通过部门列表查看部门下的员工列表&nbsp;&nbsp;&nbsp;② : 点该选项选择该用户&nbsp;&nbsp;&nbsp;③ : 该选项是确定使用，用户名，帐号，邮件三个输入框内所输入的内容进行查询<br />
									④ : 选择完毕后点击该按钮，所选员工会自动选入上一页的选项中
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:20%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>接取求助任务</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/help_list.png" />
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 查看除了自己接受或自己发布的求助任务以外的求助&nbsp;&nbsp;&nbsp;② : 点此项任务可接取该任务，并线下进行帮助。</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:20%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>谢绝/完成求助</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/help_list_aboutme.png" />
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 查看自己接受或自己发布的求助&nbsp;&nbsp;&nbsp;② : 谢绝代表拒绝对方帮助或对方没帮上忙，取消对方接取任务继续等候帮助人。完成代表在对方的帮助下，已解决困难</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
		</ul>
	</div>
	<div id="button_list" style="position:absolute;padding:10px;">
		<div class="boxshadow" id="button_0" style="float:left" onclick="Read(0);">介绍</div>
		<div class="boxshadow" id="button_1" style="float:left" onclick="Read(1);">新增求助</div>
		<div class="boxshadow" id="button_2" style="float:left" onclick="Read(2);">高级查询</div>
		<div class="boxshadow" id="button_3" style="float:left" onclick="Read(3);">接取求助任务</div>
		<div class="boxshadow" id="button_4" style="float:left" onclick="Read(4);">谢绝/完成求助</div>
	</div>
	<script type="text/javascript">
	$(function() {
		$("#button_list").children("div").each(function(){
			$(this).css({"font":"bold 14px/1.3 '微软雅黑',Arial","color":"#333","text-shadow":"rgba(50, 50, 50, .3) 1px 1px 3px","padding":"3px","margin":"5px"});
		});
		var button_top = document.body.clientHeight-60;
		var button_left = document.body.clientWidth/2 - $("#button_list").width()/2;
		$("#button_list").css({"top":button_top,"left":button_left});
		$("#button_list").children("div").each(function(){
			$(this).hover(function(){$(this).css({"border-color":"#09F","cursor":"pointer"});},function(){$(this).css("border-color","#DEDEDE");});
		});
		Read(0);
	});
	
	var current_button = 0;
	function Read(id){
		var margin_left = (-100*id)+"%";
		if($("#intro_list").css("margin-left") != margin_left){
			$("#intro_list").animate({
				marginLeft : margin_left
			},300);
			
			$("#button_"+current_button).animate({
				backgroundColor : '#FFFFFF',
				color:'#333'
			},300);
			
			current_button = id;
			$("#button_"+current_button).animate({
				backgroundColor : 'blue',
				color:'#FFFFFF'
			},300);
		}
	}
	</script>
</body>
</html>