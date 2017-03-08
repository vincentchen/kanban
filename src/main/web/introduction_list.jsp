<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html style="width:100%;height:100%">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/jquery.masonry.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:98%">
	<div style="margin:10px;height:40px;padding:5px;border-bottom:1px solid #E5E5E5;width:98%;">
		<table class="gTab" style="height:40px;">
			<tr>
				<td><img src="<%=basePath%>image/logo.png">
				</td>
				<td>
					<a href="<%=basePath%>">
						<img src="<%=basePath%>image/application_home.png"><s:text name="prompt.back_home" />
					</a>
				</td>
				<td valign="bottom" style="width:210px;"><h2><s:text name="introduction.title" /></h2></td>
			</tr>
		</table>
	</div>
	
	<div style="margin:10px;padding:5px;">
		<table class="gTab">
			<tr>
				<td>
					<h1>软件目标 : 以项目管理为主，辅以求助，创意，审批，评估，绩效、评级等管理方法，为企业创造人性化的企业文化，提高员工的主动性，积极性，建立正确的价值导向。</h1>
				</td>
			</tr>
			<tr>
				<td>
					<h1>已完成功能列表:</h1>
				</td>
			</tr>
			<tr>
				<td>
					<div id="container" style="width:100%;">
					<div class="item boxshadow" style="width:150px;float:left;padding:5px;margin:5px;">
						<img src="<%=basePath%>image/library.png" /> 项目管理 
						<div style="border-top:1px solid #E5E5E5;width:100%;margin-top:5px;padding-top:5px;text-align:right;"><a href="javascript:void(0);" onclick="OpenManual('pro');">操作手册</a></div>
					</div>
					<div class="item boxshadow" style="width:150px;float:left;padding:5px;margin:5px;">
						<img src="<%=basePath%>image/lamp.png" /> 创意管理
						<div style="border-top:1px solid #E5E5E5;width:100%;margin-top:5px;padding-top:5px;text-align:right;"><a href="javascript:void(0);" onclick="OpenManual('idea');">操作手册</a></div>
					</div>
					<div class="item boxshadow" style="width:150px;float:left;padding:5px;margin:5px;">
						<img src="<%=basePath%>image/help.png" /> 求助管理
						<div style="border-top:1px solid #E5E5E5;width:100%;margin-top:5px;padding-top:5px;text-align:right;"><a href="javascript:void(0);" onclick="OpenManual('help');">操作手册</a></div>
					</div>
					<div class="item boxshadow" style="width:150px;float:left;padding:5px;margin:5px;">
						<img src="<%=basePath%>image/dept.png"> 部门管理
						<div style="border-top:1px solid #E5E5E5;width:100%;margin-top:5px;padding-top:5px;text-align:right;"><a href="javascript:void(0);" onclick="OpenManual('dept');">操作手册</a></div>
					</div>
					<div class="item boxshadow" style="width:150px;float:left;padding:5px;margin:5px;">
						<img src="<%=basePath%>image/key.png"> 权限管理
						<div style="border-top:1px solid #E5E5E5;width:100%;margin-top:5px;padding-top:5px;text-align:right;"><a href="javascript:void(0);" onclick="OpenManual('pri');">操作手册</a></div>
					</div>
					<div class="item boxshadow" style="width:150px;float:left;padding:5px;margin:5px;">
						<img src="<%=basePath%>image/status_online.png"> 角色管理
						<div style="border-top:1px solid #E5E5E5;width:100%;margin-top:5px;padding-top:5px;text-align:right;"><a href="javascript:void(0);" onclick="OpenManual('role');">操作手册</a></div>
					</div>
					<div class="item boxshadow" style="width:150px;float:left;padding:5px;margin:5px;">
						<img src="<%=basePath%>image/newspaper.png"> 消息管理
						<div style="border-top:1px solid #E5E5E5;width:100%;margin-top:5px;padding-top:5px;text-align:right;"><a href="javascript:void(0);" onclick="OpenManual('msg');">操作手册</a></div>
					</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<h1>未完成的功能列表:</h1>
				</td>
			</tr>
			<tr>
				<td>
					<h2>审批体系、评估体系、绩效体系、评级体系、会议室、客户端工具</h2>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
	
	$(function() {
		var $container = $('#container');
		$container.imagesLoaded(function() {
			$container.masonry({
				itemSelector : '.item'
			});
		});
	});
	
	function OpenManual(name){
		CreatePopDiv("OpenManual","90%","90%","introduction_"+name+".jsp");
	}
	</script>
</body>
</html>