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
		<ul id="intro_list" style="width:500%;height:100%;padding:0px;list-style-type:none;margin-left:-1px;">
			<li style="width:20%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>介绍</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td>
								<h2>创意 : 给所有人提供一个说话的场所，让所有人都敢于说话，说实话。使用不记名，匿名以及公开发表三种状态，保护员工自身的同时发表反对言论，建议批评，以及经验创意等文章。</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:20%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>新增创意</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/idea_add.jpg" />
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 点子标题，必填&nbsp;&nbsp;&nbsp;② : 点子描述，叙述你所阐述的内容&nbsp;&nbsp;&nbsp;<br />
								③ : 三种发表方式:不记名发表方式是数据库中不存储发表人的ID号，完全无责任发表。匿名发表是数据库中保存发表人ID，但不公开发表人名称。公开发表，顾名思义，就是发表的内容会标上你的大名
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:20%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>创意审核</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<h2>创意审核是具有KEY界面权限和创意审核权限的人才可以进入的，具体步骤是<img src="<%=basePath%>image/key_rukou.jpg" />点击圆圈内按钮进入Key后台，点击 <img src="<%=basePath%>image/idea_key.jpg" /> 创意审核链接，<img src="<%=basePath%>image/idea_key_list.jpg" /> 点击通过审核按钮，如无此项代表审核完毕</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:20%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>附件管理</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/fujian.jpg" />
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>注:创意新增后会跳转至详细界面，在未审核通过之前，只有在该界面可以上传附件。附件只有创建人才有权限上传，不记名创意所有人都可以上传附件</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:20%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>创意详细界面</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/idea_view.png" />
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 后退至创意列表&nbsp;&nbsp;&nbsp;② : 创意标题&nbsp;&nbsp;&nbsp;③ : 创意作者，匿名发表和不记名发表显示为匿名&nbsp;&nbsp;&nbsp;<br />
								④ : 同意此观点的人数&nbsp;&nbsp;&nbsp;⑤ : 不同意此观点的人数&nbsp;&nbsp;&nbsp;⑥ : 赞成或者反对此创意，已投票的不显示&nbsp;&nbsp;&nbsp;⑦ : 创意内容</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
		</ul>
	</div>
	<div id="button_list" style="position:absolute;padding:10px;">
		<div class="boxshadow" id="button_0" style="float:left" onclick="Read(0);">介绍</div>
		<div class="boxshadow" id="button_1" style="float:left" onclick="Read(1);">新增创意</div>
		<div class="boxshadow" id="button_2" style="float:left" onclick="Read(2);">创意审核</div>
		<div class="boxshadow" id="button_3" style="float:left" onclick="Read(3);">附件管理</div>
		<div class="boxshadow" id="button_4" style="float:left" onclick="Read(4);">创意详细界面</div>
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