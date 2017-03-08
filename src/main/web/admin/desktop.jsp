<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/jquery.masonry.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body>
	<div id="c" style="position:relative;margin:0 auto;">
		<s:if test="1==#session.UserPri.READ">
			<div class="item boxshadow" style="width:200px;float:left;margin:5px;">
				<ul style="list-style-type: none;width:200px;">
					<li style="float:left;">
						<table style="width:80px;margin:5px;">
							<tr>
								<td style="text-align:center;height:35px;"><img src="<%=basePath%>image/customers.png" /></td>
							</tr>
							<tr>
								<td style="text-align:center"><s:text name="admin.user" /></td>
							</tr>
						</table></li>
					<li style="float:left;">
						<table class="gTab" style="min-width:100px;margin-top:5px;">
							<s:if test="1==#session.UserInfoPri.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminUserList"><s:text name="admin.user.info" /></a>
								</td>
							</tr>
							</s:if>
						</table>
					</li>
				</ul>
			</div>
		</s:if>
		<s:if test="1==#session.OrgPri.READ">
			<div class="item boxshadow" style="width:200px;float:left;margin:5px;">
				<ul style="list-style-type: none;width:200px;">
					<li style="float:left;">
						<table style="width:80px;margin:5px;">
							<tr>
								<td style="text-align:center;height:35px;"><img src="<%=basePath%>image/dept.png" /></td>
							</tr>
							<tr>
								<td style="text-align:center"><s:text name="admin.dept" /></td>
							</tr>
						</table>
					</li>
					<li style="float:left;">
						<table class="gTab" style="min-width:100px;margin-top:5px;">
						<s:if test="1==#session.OrgInfoPri.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminDeptList"><s:text name="admin.dept.info" /></a>
								</td>
							</tr>
						</s:if>
						</table></li>
				</ul>
			</div>
		</s:if>
		<s:if test="1==#session.BusinessPri.READ">
			<div class="item boxshadow" style="width:200px;float:left;margin:5px;">
				<ul style="list-style-type: none;width:200px;">
					<li style="float:left;">
						<table style="width:80px;margin:5px;">
							<tr>
								<td style="text-align:center;height:35px;">
									<img src="<%=basePath%>image/library.png"></a>
								</td>
							</tr>
							<tr>
								<td style="text-align:center"><s:text name="admin.pro" /></td>
							</tr>
						</table>
					</li>
					<li style="float:left;">
						<table class="gTab" style="min-width:100px;margin-top:5px;">
							<s:if test="1==#session.ProPri.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminProjectList"><s:text name="admin.pro.pri" /></a>
								</td>
							</tr>
							</s:if>
							<s:if test="1==#session.ProCntPri.READ">
							<tr>
								<td style="text-align:center;">
									<!-- <a href="AdminProjectCnt"><s:text name="admin.pro.cnt" /></a> -->
								</td>
							</tr>
							</s:if>
						</table></li>
				</ul>
			</div>
		</s:if>
		<s:if test="1==#session.PriManager.READ">
			<div class="item boxshadow" style="width:200px;float:left;margin:5px;">
				<ul style="list-style-type: none;width:200px;">
					<li style="float:left;">
						<table style="width:80px;margin:5px;">
							<tr>
								<td style="text-align:center;height:35px;"><img src="<%=basePath%>image/key.png"></td>
							</tr>
							<tr>
								<td style="text-align:center"><s:text name="admin.pri" /></td>
							</tr>
						</table>
					</li>
					<li style="float:left;">
						<table class="gTab" style="min-width:100px;margin-top:5px;">
							<s:if test="1==#session.PriInfo.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminPrivilegeList"><s:text name="admin.pri.info" /></a>
								</td>
							</tr>
							</s:if>
							<s:if test="1==#session.PriConfig.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminPrivilegeCustom"><s:text name="admin.pri.custom" /></a>
								</td>
							</tr>
							</s:if>
						</table></li>
				</ul>
			</div>
		</s:if>
		<s:if test="1==#session.RolePri.READ">
			<div class="item boxshadow" style="width:200px;float:left;margin:5px;">
				<ul style="list-style-type: none;width:200px;">
					<li style="float:left;">
						<table style="width:80px;margin:5px;">
							<tr>
								<td style="text-align:center;height:35px;"><img src="<%=basePath%>image/status_online.png"></td>
							</tr>
							<tr>
								<td style="text-align:center"><s:text name="admin.role" /></td>
							</tr>
						</table>
					</li>
					<li style="float:left;">
						<table class="gTab" style="min-width:100px;margin-top:5px;">
							<s:if test="1==#session.RoleInfoPri.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminRoleList"><s:text name="admin.role.info" /></a>
								</td>
							</tr>
							</s:if>
						</table></li>
				</ul>
			</div>
		</s:if>
		<s:if test="1==#session.IdeaPri.READ">
			<div class="item boxshadow" style="width:200px;float:left;margin:5px;">
				<ul style="list-style-type: none;width:200px;">
					<li style="float:left;">
						<table style="width:80px;margin:5px;">
							<tr>
								<td style="text-align:center;height:35px;"><img src="<%=basePath%>image/lamp.png"></td>
							</tr>
							<tr>
								<td style="text-align:center"><s:text name="admin.idea" /></td>
							</tr>
						</table>
					</li>
					<li style="float:left;">
						<table class="gTab" style="min-width:100px;margin-top:5px;">
							<s:if test="1==#session.IdeaVerifyPri.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminIdeaList"><s:text name="admin.idea.verify" /></a>
								</td>
							</tr>
							</s:if>
						</table></li>
				</ul>
			</div>
		</s:if>
		<s:if test="1==#session.HelpPri.READ">
			<div class="item boxshadow" style="width:200px;float:left;margin:5px;">
				<ul style="list-style-type: none;width:200px;">
					<li style="float:left;">
						<table style="width:80px;margin:5px;">
							<tr>
								<td style="text-align:center;height:35px;"><img src="<%=basePath%>image/help.png"></td>
							</tr>
							<tr>
								<td style="text-align:center"><s:text name="admin.help" /></td>
							</tr>
						</table>
					</li>
					<li style="float:left;">
						<table class="gTab" style="min-width:100px;margin-top:5px;">
							<s:if test="1==#session.HelpInfoPri.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminHelpList"><s:text name="admin.help.info" /></a>
								</td>
							</tr>
							</s:if>
							<s:if test="1==#session.HelpCntPri.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminHelpCnt"><s:text name="admin.help.cnt" /></a>
								</td>
							</tr>
							</s:if>
						</table></li>
				</ul>
			</div>
		</s:if>
		<s:if test="1==#session.MsgCenterPri.READ">
			<div class="item boxshadow" style="width:200px;float:left;margin:5px;">
				<ul style="list-style-type: none;width:200px;">
					<li style="float:left;">
						<table style="width:80px;margin:5px;">
							<tr>
								<td style="text-align:center;height:35px;"><img src="<%=basePath%>image/newspaper.png"></td>
							</tr>
							<tr>
								<td style="text-align:center"><s:text name="admin.msg" /></td>
							</tr>
						</table>
					</li>
					<li style="float:left;">
						<table class="gTab" style="min-width:100px;margin-top:5px;">
							<s:if test="1==#session.MsgInfo.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminMsgList"><s:text name="admin.msg.info" /></a>
								</td>
							</tr>
							</s:if>
							<s:if test="1==#session.MsgBroadcast.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminMsgBroadcast"><s:text name="admin.msg.broadcast" /></a>
								</td>
							</tr>
							</s:if>
						</table></li>
				</ul>
			</div>
		</s:if>
		<s:if test="1==#session.ServicePri.READ">
			<div class="item boxshadow" style="width:200px;float:left;margin:5px;">
				<ul style="list-style-type: none;width:200px;">
					<li style="float:left;">
						<table style="width:80px;margin:5px;">
							<tr>
								<td style="text-align:center;height:35px;"><img src="<%=basePath%>image/database_gear.png"></td>
							</tr>
							<tr>
								<td style="text-align:center"><s:text name="admin.service" /></td>
							</tr>
						</table>
					</li>
					<li style="float:left;">
						<table class="gTab" style="min-width:100px;margin-top:5px;">
							<s:if test="1==#session.ServerPri.READ">
							<!--  
							<tr>
								<td style="text-align:center;">
									<a href="AdminServerStatus"><s:text name="admin.service.status" /></a>
								</td>
							</tr>
							-->
							</s:if>
							<s:if test="1==#session.PluginPri.READ">
							<tr>
								<td style="text-align:center;">
									<a href="AdminServerPlugin"><s:text name="admin.service.plugin" /></a>
								</td>
							</tr>
							</s:if>
						</table></li>
				</ul>
			</div>
		</s:if>
	</div>
	<script type="text/javascript">
		$(function() {
			var $container = $('#c');
			$container.imagesLoaded(function() {
				$container.masonry({
					// 每一列数据的宽度，若所有栏目块的宽度相同，可以不填这段
					// .imgbox 为各个图片的容器
					itemSelector : '.item',
					isFitWidth : true,
					isResizable : true
				});
			});
		});
	</script>
</body>
</html>