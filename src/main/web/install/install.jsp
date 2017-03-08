<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html style="height:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<base href="<%=basePath%>">
<title><s:text name="install.msg.title" />
</title>
</head>
<body style="height:100%;">
	<div class="install_floter"></div>
	<div class="install_div boxshadow" style="width:700px;">
		<div class="install_layout" style="height:70px;border-bottom:solid 1px #E5E5E5;">
			<div style="float:left;padding:9px;">
				<img src='<%=basePath%>image/install.gif' style="margin-top:5px;">
			</div>
			<div style="position: relative;top: 25%;margin-right:15px;text-align:center;float:right;">
				<ul>
					<li id="step_2" class="install_step"><s:text name="install.msg.complete" />
					</li>
					<li id="step_1" class="install_step"><s:text name="install.msg.install" />
					</li>
					<li id="step_0" class="install_step"><s:text name="install.msg.setting" />
					</li>
				</ul>
			</div>
		</div>
		<div class="install_layout" style="height:250px;">
			<ul id="install_config" class="install_ul">
				<li>
					<form id="installform">
						<div style="padding:5px;margin-left:5px;text-align:left;">
							<h2>
								<s:text name="install.prompt.managerinfo" />
							</h2>
						</div>
						<table style="margin:0 auto;">
							<tr>
								<td style="width:132px;"><s:text name="install.msg.manager.name" />
								</td>
								<td><input type="text" id="ManagerName" name="ManagerName" value="admin" size="35" style="width:200px;">
								</td>
								<td></td>
							</tr>
							<tr>
								<td style="width:132px;"><s:text name="install.msg.manager.password" />
								</td>
								<td><input type="password" id="ManagerPassword" name="ManagerPassword" value="" size="35" style="width:200px;">
								</td>
								<td><s:text name="install.prompt.manager.password" />
								</td>
							</tr>
							<tr>
								<td style="width:132px;"><s:text name="install.msg.manager.password2" />
								</td>
								<td><input type="password" id="ManagerPassword2" name="ManagerPassword2" value="" size="35" style="width:200px;" onKeyDown="clearTip();">
								</td>
								<td id="error_password" style="display:none"><font style="color:red"><s:text name="message.error.password2" /> </font></td>
							</tr>
							<tr>
								<td style="width:132px;"><s:text name="install.msg.manager.email" />
								</td>
								<td><input type="text" id="ManagerEmail" name="ManagerEmail" value="" size="35" style="width:200px;">
								</td>
								<td></td>
							</tr>
						</table>
						<br /> <input type="submit" id="submit" name="submit" value="<s:text name='global.next' />" style="width:200px;height:24px;color:#000;">
					</form></li>
				<li>
					<div id="installing" style="position:relative;top:50%;left:50%;font-size:12px;margin:-75px 0 0 -175px;width:350px;height:150px">
						<table class="gTab">
							<tr>
								<td style="text-align:center"><img src="<%=basePath%>image/installing.jpg"></td>
							</tr>
							<tr>
								<td style="text-align:center"><object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="210" height="60" id="loading_blue" align="middle">
										<param name="allowScriptAccess" value="sameDomain" />
										<param name="allowFullScreen" value="false" />
										<param name="movie" value="<%=basePath%>image/loading_blue.swf" />
										<param name="quality" value="high" />
										<param name="wmode" value="transparent" />
										<param name="bgcolor" value="#ffffff" />
										<embed src="<%=basePath%>image/loading_blue.swf" quality="high" wmode="transparent" bgcolor="#ffffff" width="210" height="60" name="loading_blue" align="middle" allowScriptAccess="sameDomain" allowFullScreen="false" type="application/x-shockwave-flash" pluginspage="http://www.adobe.com/go/getflashplayer_cn" />
									</object></td>
							</tr>
						</table>
					</div>
					<div id="install_error" style="position:relative;top:50%;left:50%;font-size:12px;margin:-75px 0 0 -175px;width:350px;height:150px;display:none">
						<table>
							<tr>
								<td align="center"><img src="<%=basePath%>image/error.png"></td>
							</tr>
							<tr>
								<td id="error_code" align="center"></td>
							</tr>
						</table>
					</div>
				</li>
				<li>
					<div id="installing" style="position:relative;top:50%;left:50%;font-size:12px;margin:-75px 0 0 -175px;width:350px;height:150px">
						<table>
							<tr>
								<td align="center"><img src="<%=basePath%>image/installok.jpg"></td>
							</tr>
							<tr>
								<td align="center" style="padding:10px;"><input type="button" style="width:100px;height:25px" value="<s:text name='into.management' />" onclick="LoginIn();"> <input type="button" style="width:100px;height:25px" value="产品相关"></td>
							</tr>
						</table>
					</div></li>
			</ul>
		</div>
		<div class="install_layout" style="background-color:#E5E5E5;height:1px;font-size:1px;"></div>
		<div class="install_layout" style="line-height: 40px;text-align: center;">
			<h2><s:text name="idealism.author" /></h2>
		</div>
	</div>
	<script type="text/javascript">
		var on_off = 0;
		var step = 0;
		var step_on = $("#step_" + step);
		var fm = $("#install_config");
		var sb = $("#submit");

		
		$(function() {
			$("#installform").bind("submit", function(event) {
				if(CheckForm()){
					$("#error_password").css('display','block');
					event.preventDefault();
				}else{
					SubmitLock();
					InstallStepAnimate();
					PostForm();
					event.preventDefault(); // 阻表单默认事件，阻止提交
				}
			});
		})

		function clearTip(){
			$("#error_password").css('display','none');
		}

		function CheckForm(){
			if ($("#ManagerPassword").val() != $("#ManagerPassword2").val()) {
				return true;
			}else{
				return false;
			}
		}
		
		function PostForm() {
			$.ajax({
				type : 'POST',
				url : "install/SystemInstall",
				data : {
					"ManagerName" : $("#ManagerName").val(),
					"ManagerPassword" : $("#ManagerPassword").val(),
					"ManagerPassword2" : $("#ManagerPassword2").val(),
					"ManagerEmail" : $("#ManagerEmail").val()
				},
				success : function PostResult(data) {
					if (data == "ok") {
						InstallStepAnimate();
					} else {
						SubmitUnLock();
						ShowError(data);
					}
				}
			});
		}
		
		function LoginIn(){
			$.ajax({
				type : 'POST',
				url : "LoginCheckIn",
				data : {
					"user_account" : $("#ManagerName").val(),
					"password" : $("#ManagerPassword").val(),
					"AutoLogin" : "false"
				},
				success:function LoginCK(data){
					if(data == "ok"){
						window.location.href = "<%=basePath%>admin/";
					}else{
						document.write(data);
					}
				}
			});
		}

		function ShowError(data) {
			$("#installing").css("display", "none");
			$("#install_error").css("display", "block");
			$("#error_code").html(data);
		}

		function SubmitLock() {
			sb.attr("disabled", true);
		}

		function SubmitUnLock() {
			sb.attr("disabled", false);
		}

		function InstallStepAnimate() {
			step++;
			step_on.removeClass().addClass("install_step");
			step_on = $("#step_" + step);
			$("#install_config").animate({
				marginLeft : "-" + 700 * step + "px",
			}, 300);
		}

		function BackStepAnimate() {
			step_on.removeClass().addClass("install_step");
			step--;
			step_on = $("#step_" + step);
			$("#install_config").animate({
				marginLeft : "-" + 700 * step + "px",
			}, 300);
		}

		function resetbordercolor() {
			if (on_off == 0) {
				step_on.removeClass().addClass("install_step_on");
				on_off = 1;
				setTimeout("resetbordercolor()", 400);
			} else {
				step_on.removeClass().addClass("install_step");
				on_off = 0;
				setTimeout("resetbordercolor()", 400);
			}
		}
		resetbordercolor();
	</script>
</body>
</html>
