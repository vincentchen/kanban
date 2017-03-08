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
			<h1><s:text name="password.reset" /></h1>
		</div>
		<div>
			<form id="loginForm">
				<ul>
					<li>
						<div style="position:relative;">
							<div>
								<input type="password" id="password" value="" placeholder="<s:text name="prompt.password" />" tabindex="87" size="35" style="width:320px;">
							</div>
							<div id="err_pwd" style="position:absolute;top:0px;right:0px;text-align:right;display:none">
								<img src="<%=basePath%>image/x.png" style="margin-right:10px;margin-top:2px"/>
							</div>
						</div></li>
					<li>
						<div style="position:relative;">
							<div>
								<input type="password" id="password2" value="" placeholder="<s:text name="prompt.password2" />" tabindex="88" size="35" style="width:320px;">
							</div>
							<div id="err_pwd2" style="position:absolute;top:0px;right:0px;text-align:right;display:none">
								<img src="<%=basePath%>image/x.png" style="margin-right:10px;margin-top:2px"/>
							</div>
						</div></li>
					<li><input type="submit" id="submit" class="s_btn" value="<s:text name='login' />" tabindex="100" style="width:328px;height:35px"></li>
				</ul>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		var MSG_LOADING = "<s:text name='loading' />";
		var MSG_LOGIN = "<s:text name='login' />";
		
		$(function() {
			$("#loginForm").bind("submit", function(event) {
				if (!CheckForm()) {
					SubmitLock();
					PostForm();
				}
				event.preventDefault();
			});
			$("#password").change(function(){
				$("#err_pwd").css("display","none"); 
			});
			$("#password2").change(function(){
				$("#err_pwd2").css("display","none"); 
			});
		});
		
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
				url : "LoginSavePWD",
				data : {
					"password" : $("#password").val()
				},
				success:function(data){
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
			if ($("#password").val() != $("#password2").val() || $("#password").val() == "" || $("#password2").val() == "") {
				$("#err_pwd").css("display","block"); 
				$("#err_pwd2").css("display","block"); 
				return true;
			}
			return false;
		}
		
	</script>
</body>
</html>