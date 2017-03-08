<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html style="height: 100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="login" /></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
</head>
<body style="height:100%;">
	<div class="floter"></div>
	<div class="login boxshadow">
		<div style="text-align:center;margin:10px;">
			<img src="<%=basePath%>image/logo.png">
		</div>
		<div>
			<form id="loginForm" action="LoginCheckIn">
				<ul>
					<li>
						<div style="position:relative;">
							<div>
								<input type="text" id="user_account" name="user_account" value="" placeholder="<s:text name="prompt.account" />" tabindex="87" size="35" style="width:320px;">
							</div>
							<div id="err_user" style="position:absolute;top:0px;right:0px;text-align:right;display:none">
								<img src="<%=basePath%>image/x.png" style="margin-right:10px;margin-top:2px"/>
							</div>
						</div></li>
					<li>
						<div style="position:relative;">
							<div>
								<input type="password" id="password" name="password" value="" placeholder="<s:text name="prompt.password" />" tabindex="88" size="35" style="width:320px;">
							</div>
							<div id="err_pwd" style="position:absolute;top:0px;right:0px;text-align:right;display:none">
								<img src="<%=basePath%>image/x.png" style="margin-right:10px;margin-top:2px"/>
							</div>
						</div></li>
					<li style="height:25px;text-align:right;margin-right:10px;">
						<input name="AutoLogin" type="checkbox" id="AutoLogin" value="true" tabindex="91"><label for="AutoLogin"><s:text name="auto.login" /></label>
					</li>
					<li><input type="submit" id="submit" class="s_btn" value="<s:text name='login' />" tabindex="100" style="width:328px;height:35px"></li>
				</ul>
			</form>
		</div>
	</div>
	<div id="author" class="boxshadow" style="position: absolute;padding:10px;"><h2><s:text name="idealism.author" /></h2></div>
	<div id="is_chrome" class="boxshadow" style="position:absolute;padding:10px;display:none"><h2><img src="<%=basePath%>image/caution.png">&nbsp;<s:text name="prompt.is_chrome" /></h2></div>
	<div id="help" style="position:absolute;top:0px;height:50px;margin-top:-5px;" >
		<table class="boxshadow" style="margin:5px;padding:5px;">
			<tr>
				<td style="margin:5px;padding:5px;">
					<h1><a href="introduction_list.jsp"><s:text name="idealism.introduction" /></a></h1>
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		var MSG_LOADING = "<s:text name='loading' />";
		var MSG_LOGIN = "<s:text name='login' />";
		$(function() {
			var author_top = document.body.clientHeight-50;
			var author_left = document.body.clientWidth/2 - $("#author").width()/2;
			$("#author").css({"top":author_top,"left":author_left});
			$("#loginForm").bind("submit", function(event) {
				if (CheckForm()) {
					event.preventDefault();
				} else {
					SubmitLock();
					PostForm();
					event.preventDefault(); // 阻表单默认事件，阻止提交
				}
			});
			$("#username").change(function(){
				$("#err_user").css("display","none"); 
			});
			$("#password").change(function(){
				$("#err_pwd").css("display","none"); 
			});
			
			var is_chrome = 0;
			if(browser().toLowerCase() == "chrome"){
				is_chrome = 1;
			}
			if(is_chrome == 0){
				var is_chrome_top = document.body.clientHeight-110;
				var is_chrome_left = document.body.clientWidth/2 - $("#is_chrome").width()/2;
				$("#is_chrome").css({"top":is_chrome_top,"left":is_chrome_left,"display":"block"});
			}
			
			$("#help").css("left",document.body.clientWidth/2 - $("#help").width()/2);
			
		});
		
		function browser() {
			var browsertype="";
			function c(browser) {return navigator.userAgent.toLowerCase().indexOf(browser) > -1;}
			return browsertype=c('opera')===true?'opera':(c('msie') && c('360se'))===true?'360se':((c('msie') && c('tencenttraveler')) && c('metasr'))===true?"sogobrowser":(c('msie') && c('qqbrowser'))===true?"QQbrowser":(c('msie') && c('tencenttraveler'))===true?"TTbrowser":c('msie')===true?'msie':(c('safari') && !c('chrome'))===true?'safari':c('maxthon')===true?"maxthon":((c('chrome') && c('safari')) && c('qihu 360ee'))===true?"360ee":(c('chrome') && c('taobrowser'))===true?"taobrowser":c('chrome')===true?"chrome":((c('gecko') && !c('webkit')) && c('seamonkey'))===true?"SeaMonkey":((c('gecko') && !c('webkit')) && !c('netscape'))===true?'firefox':((c('gecko') && !c('webkit')) && c('netscape'))===true?'netscape':"other";
		}
		
		function SubmitLock(){
			$("#submit").attr("disabled","disabled");
			$("#submit").val(MSG_LOADING);
		}
		
		function SubmitUnLock(){
			$("#submit").removeAttr("disabled");
			$("#submit").val(MSG_LOGIN);
		}
		
		function PostForm(){
			$.ajax({
				type : 'POST',
				url : "LoginCheckIn",
				data : {
					"user_account" : $("#user_account").val(),
					"password" : $("#password").val(),
					"AutoLogin" : $("#AutoLogin").val()
				},
				success:function LoginCK(data){
					if(data == "ok"){
						window.location.href = "<%=basePath%>";
					}else{
						alert(data);
						SubmitUnLock();
					}
				}
			});
		}

		function CheckForm() {
			if ($("#username").val() == "") {
				$("#err_user").css("display","block"); 
				return true;
			}
			return false;
		}
	</script>
</body>
</html>