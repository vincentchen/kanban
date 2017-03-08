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
		<ul id="intro_list" style="width:400%;height:100%;padding:0px;list-style-type:none;margin-left:-1px;">
			<li style="width:25%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>介绍</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<h2>点击<img src="<%=basePath%>image/key_rukou.jpg" /> 进入后台，点击 <img src="<%=basePath%>image/role_key.jpg" /> 角色信息链接进入角色信息管理界面:<br />
								<img src="<%=basePath%>image/role_list.jpg" /><br/>
								注: 没有该图标表示没有此权限
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:25%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>角色权限配置</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<h2><img src="<%=basePath%>image/role_list.jpg" /><br />
								点击 <img src="<%=basePath%>image/application_key_small.png" /> 按钮打开权限编辑界面 ，勾选所需权限，点击提交按钮保存<br />
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:25%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>角色权限配置</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<h2>从<img src="<%=basePath%>image/key_rukou.jpg" />点击圆圈进入Key后台，点击 <img src="<%=basePath%>image/user_key.jpg" /> 用户信息链接进入员工信息管理界面，点击 <img src="<%=basePath%>image/group.png" /> 按钮打开绑定界面 <br />
								<img src="<%=basePath%>image/bind_role.png" /><br />
								① : 输入部门名称，必填项，不支持拼音搜索&nbsp;&nbsp;&nbsp;② : 输入之后会弹出查询结果，点击选择&nbsp;&nbsp;&nbsp;③ : 如部门选择一样，输入后进行选择&nbsp;&nbsp;&nbsp;④ : 选择完毕之后点击该按钮进行新增
								注:如无图标按钮代表无权限进入。<br />
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:25%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>员工关联角色</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								
							</td>
						</tr>
					</table>
				</div>
			</li>
		</ul>
	</div>
	<div id="button_list" style="position:absolute;padding:10px;">
		<div class="boxshadow" id="button_0" style="float:left" onclick="Read(0);">介绍</div>
		<div class="boxshadow" id="button_1" style="float:left" onclick="Read(1);">角色权限配置</div>
		<div class="boxshadow" id="button_2" style="float:left" onclick="Read(2);">员工关联角色</div>
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