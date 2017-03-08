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
	<div style="width:100%;height:100%;overflow: hidden;">
		<ul id="intro_list" style="width:200%;height:100%;padding:0px;list-style-type:none;margin-left:-1px;">
			<li style="width:50%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>介绍</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td>
								<h2>关于权限的信息，如有权限，可查看KEY界面的权限信息中的相关说明，权限管理中的自定义配置，可用于二次开发试用。</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:50%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>员工权限配置</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<h2>点击<img src="<%=basePath%>image/key_rukou.jpg" /> 进入后台，点击 <img src="<%=basePath%>image/user_key.jpg" /> 用户信息链接进入员工信息管理界面，点击 <img src="<%=basePath%>image/application_key_small.png" /> 按钮打开权限编辑界面 ，勾选所需权限，点击提交按钮保存<br />
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
		</ul>
	</div>
	<div id="button_list" style="position:absolute;padding:10px;">
		<div class="boxshadow" id="button_0" style="float:left" onclick="Read(0);">介绍</div>
		<div class="boxshadow" id="button_1" style="float:left" onclick="Read(1);">员工权限配置</div>
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