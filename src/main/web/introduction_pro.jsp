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
		<ul id="intro_list" style="width:1000%;height:100%;padding:0px;list-style-type:none;margin-left:-1px;">
			<li style="width:10%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>介绍</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td colspan='2' style="text-align:center">
								<h2>名次解释</h2>
							</td>
						</tr>
						<tr>
							<td style="text-align:right">
								<img src="<%=basePath%>image/library.png" /> 
							</td>
							<td>
								<h2>项目:是指任何一件以月为单位长期进行的工作活动，统称为项目，项目下可包含若干目标</h2>
							</td>
						</tr>
						<tr>
							<td style="text-align:right">
								<img src="<%=basePath%>image/milestone.png" />
							</td>
							<td>
								<h2>目标:是指任何一件以周或天为单位进行的工作活动，统称为目标，目标下可包含若干任务单</h2>
							</td>
						</tr>
						<tr>
							<td style="text-align:right">
								<img src="<%=basePath%>image/flag.png" />
							</td>
							<td>
								<h2>任务单:是指任何一件以天或小时为单位进行的短期工作活动，统称为任务单</h2>
							</td>
						</tr>
						<tr>
							<td style="text-align:right">
								<img src="<%=basePath%>image/customers.png">
							</td>
							<td>
								<h2>相关员工:可查看该项目信息，并可以接受，完成任务单的员工列表，在公开项目中，只有相关员工列表中的人员才可以操作任务单</h2>
							</td>
						</tr>
						<tr>
							<td colspan='2' style="text-align:center">
								<h2>各模块功能</h2>
							</td>
						</tr>
						<tr>
							<td style="text-align:right">
								<h2>项目:</h2>
							</td>
							<td>
								<h2>项目列表，新增/编辑项目信息，查询项目，添加/删除项目相关员工，项目历程，项目图表，开始/完成/重启项目，项目附件管理</h2>
							</td>
						</tr>
						<tr>
							<td style="text-align:right">
								<h2>目标:</h2>
							</td>
							<td>
								<h2>未完成/已完成目标列表，新增/编辑目标，目标附件管理，目标历程，开始/完成/重启目标，目标图表</h2>
							</td>
						</tr>
						<tr>
							<td style="text-align:right">
								<h2>任务单:</h2>
							</td>
							<td>
								<h2>新建/编辑任务单，接收/完成/取消任务单，任务单附件管理</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:10%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>项目列表</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/pro_list.png">
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 项目名称<br />
									② : 项目描述<br />
									注  : 上图修改，停止等项如不显示，则代表没有权限操作，只有项目创建人才有权限操作修改，停止，附件->新增附件，人员->新增/删除人员，删除等操作
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:10%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>新增项目</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/pro_add.png">
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 项目名称必填 &nbsp;&nbsp;&nbsp;② : 项目预计开始时间必填 &nbsp;&nbsp;&nbsp;③ : 项目预计结束时间必填 &nbsp;&nbsp;&nbsp;④ : 预估天数必填 <br / >
									⑤ : 勾选此项代表创建项目时即时开始任务<br /> 
									⑥ : 勾选此项可让所有员工都可以阅读此项目相关信息，包括附件，相关人员，历史记录，目标，任务单，但无操作权限<br />
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:10%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>添加相关员工</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/rel_user.png">
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 新增按钮，创建项目者拥有权限操作 &nbsp;&nbsp;&nbsp;② : 删除按钮，创建项目者拥有操作权，创建人不可删除
								</h2>
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/search_user.png">
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>③ : 可通过点选部门查看该部门人员列表 &nbsp;&nbsp;&nbsp;④ : 人员列表区域 &nbsp;&nbsp;&nbsp;⑤ :选择查询到的员工&nbsp;&nbsp;&nbsp;⑥ : 点击选择完毕，才能新增员工
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:10%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>项目详细界面</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/pro_view.png">
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 返回项目列表 &nbsp;&nbsp;&nbsp;② : 项目名称 &nbsp;&nbsp;&nbsp;③ : 项目实际开始时间和实际结束时间 &nbsp;&nbsp;&nbsp;④ : 查看未完成/已完成的目标<br/>
								⑤ : 目标名称 &nbsp;&nbsp;&nbsp;⑥ : 目标内容 &nbsp;&nbsp;&nbsp;⑦ : 目标预计开始时间与预计结束时间 &nbsp;&nbsp;&nbsp;⑧ : 目标预计工作天数 &nbsp;&nbsp;&nbsp;<br />
								⑨ : 目标操作栏,按钮功能从左到右分别是:进入目标详细界面，修改目标，目标附件，进行/结束/重启目标，目标图表<br />
								⑩ : 目标下任务单未完成/进行中/已完成(已取消)个数
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:10%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>新增目标</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/target_add.png">
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 目标名称，必填 &nbsp;&nbsp;&nbsp;② : 目标预计开始时间，必填 &nbsp;&nbsp;&nbsp;③ : 目标预计结束时间，必填 &nbsp;&nbsp;&nbsp;④ : 目标预估工作天数，必填<br/>
								⑤ : 勾选此项，则目标直接进入进行中状态
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:10%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>目标详细界面</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/target_view.png">
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 返回项目详细界面 &nbsp;&nbsp;&nbsp;② : 目标名称 &nbsp;&nbsp;&nbsp;③ : 目标实际开始时间和实际结束时间<br />
								④ : 任务单编号 &nbsp;&nbsp;&nbsp;⑤ : 任务单预计使用时间&nbsp;&nbsp;&nbsp;⑥ : 新建任务单按钮，创建者项目者可点击&nbsp;&nbsp;&nbsp;<br />
								⑦ : 任务单预计开始时间&nbsp;&nbsp;&nbsp;⑧ : 任务单操作栏，功能从左至右分别为:查看任务单详细，修改任务单，任务单附件<br/>
								⑨ : 任务单紧急程度，从低到高紧急程度图标: <img src="<%=basePath%>image/lv1.png">1级 <img src="<%=basePath%>image/lv2.png">2级 <img src="<%=basePath%>image/lv3.png">3级 <img src="<%=basePath%>image/lv4.png">4级 <img src="<%=basePath%>image/lv5.png">5级 <br />
								⑩ : 创建人名称 &nbsp;&nbsp;&nbsp;⑪ : 接收任务按钮&nbsp;&nbsp;&nbsp;⑫ : 任务单预计结束时间&nbsp;&nbsp;&nbsp;⑬ : 接受人名称&nbsp;&nbsp;&nbsp;⑭ : 任务单完成/取消按钮
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:10%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>新增任务单</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/task_add.png">
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 任务单内容，必填&nbsp;&nbsp;&nbsp;② : 任务单预计开始时间，必填 &nbsp;&nbsp;&nbsp;③ : 任务单预计结束时间，必填 &nbsp;&nbsp;&nbsp;④ : 任务单预估小时数，必填<br/>
								⑤ : 将此任务强制指派给选中人&nbsp;&nbsp;&nbsp;⑥ : 选择任务单紧急程度，必选
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:10%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>燃尽图</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/pro_tubiao.png">
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>注：横向为时间轴，纵向为统计目标或任务单个数 <br />
								① : 项目/目标预计开始时间点&nbsp;&nbsp;&nbsp;② : 项目/目标理想结束时间线 &nbsp;&nbsp;&nbsp;③ : 项目/目标预计结束时间点<br />
								④ : 目标/任务单实际开始时间&nbsp;&nbsp;&nbsp;⑤ : 目标/任务单实际结束时间线&nbsp;&nbsp;&nbsp;⑥ : 目标/任务单实际结束时间点
								</h2>
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li style="width:10%;height:100%;float:left;">
				<div style="width:100%;border-bottom:1px dashed #e5e5e5"><h1>历史记录</h1></div>
				<div style="width:100%;">
					<table class="gTab">
						<tr>
							<td style="text-align:center">
								<img src="<%=basePath%>image/pro_history.png">
							</td>
						</tr>
						<tr>
							<td style="text-align:center">
								<h2>① : 下载记录的开始时间，必填&nbsp;&nbsp;&nbsp;② : 下载记录的结束时间，必填
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
		<div class="boxshadow" id="button_1" style="float:left" onclick="Read(1);">项目列表</div>
		<div class="boxshadow" id="button_2" style="float:left" onclick="Read(2);">新增项目</div>
		<div class="boxshadow" id="button_3" style="float:left" onclick="Read(3);">添加相关员工</div>
		<div class="boxshadow" id="button_4" style="float:left" onclick="Read(4);">项目详细界面</div>
		<div class="boxshadow" id="button_5" style="float:left" onclick="Read(5);">新增目标</div>
		<div class="boxshadow" id="button_6" style="float:left" onclick="Read(6);">目标详细界面</div>
		<div class="boxshadow" id="button_7" style="float:left" onclick="Read(7);">新增任务单</div>
		<div class="boxshadow" id="button_8" style="float:left" onclick="Read(8);">燃尽图</div>
		<div class="boxshadow" id="button_9" style="float:left" onclick="Read(9);">历史记录</div>
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