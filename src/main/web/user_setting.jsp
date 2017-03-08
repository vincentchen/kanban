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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/jquery.ibutton.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
<script src="<%=basePath%>js/jquery.masonry.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.ibutton.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.metadata.js"></script>
</head>
<body>
	<div style="margin:3px auto;height:35px;border-bottom:1px solid #E5E5E5;">
		<ul style="width:180px;margin:3px auto;height:31px;list-style-type:none;background-color:#fff">
			<div id="selectBG" class="boxradius" style="background-color:#E5E5E5;position: absolute;z-index:8"></div>
			<a href="javscript:void(0);" onclick="SelectTab('setting');"><li id="settingli" class="boxradius" style="float:left;height:23px;z-index:99;position: absolute;padding:3px;">
					<h1 style="padding:0px;">
						<s:text name="desktop.setting" />
					</h1></li> </a>
			<a href="javscript:void(0);" onclick="SelectTab('msg_send');"><li id="msg_sendli" class="boxradius" style="margin-left:82px;float:left;height:23px;z-index:99;position: absolute;padding:3px;">
					<h1 style="padding:0px;">
						<s:text name="setting.msg_send" />
					</h1></li> </a>
		</ul>
	</div>
	<div style="margin:5px auto;width:98%;height:90%;overflow: hidden;">
		<ul id="selectTab" style="width:200%;height:98%;list-style-type:none;margin-left:1px;">
			<li style="width:50%;height:98%;margin:0px;padding:0px;float:left;">
		<div id="setting" class="boxradius" style="width:99%;height:100%;margin:0 auto;overflow:hidden;">
			<div style="margin:10px auto;"><h2><s:text name="desktop.setting.rownum" /></h2></div>
			<div id="container" style="margin:0 auto;">
				<div class="item boxradius" style="margin:10px;width:120px;height:50px;">
					<table class="gTab">
						<tr>
							<td rowspan="2" style="width:50px;"><img src="<%=basePath%>image/flag.png" />
							</td>
							<td><s:text name="desktop.todo" /></td>
						</tr>
						<tr>
							<td><input id="TODO" name="TODO" type="text" value="" style="width:20px;text-align:center">
							</td>
						</tr>
					</table>
				</div>
				<div class="item boxradius" style="margin:10px;width:120px;height:50px;">
					<table class="gTab">
						<tr>
							<td rowspan="2" style="width:50px;"><img src="<%=basePath%>image/alarm.png" />
							</td>
							<td><s:text name="desktop.doing" /></td>
						</tr>
						<tr>
							<td><input id="DOING" name="DOING" type="text" value="" style="width:20px;text-align:center">
							</td>
						</tr>
					</table>
				</div>
				<div class="item boxradius" style="margin:10px;width:120px;height:50px;">
					<table class="gTab">
						<tr>
							<td rowspan="2" style="width:50px;"><img src="<%=basePath%>image/library.png" />
							</td>
							<td><s:text name="desktop.pro" /></td>
						</tr>
						<tr>
							<td><input id="PRO" name="PRO" type="text" value="" style="width:20px;text-align:center">
							</td>
						</tr>
					</table>
				</div>
				<!--暂未开放
				<div class="item boxradius" style="margin:10px;width:120px;height:50px;">
					<table class="gTab">
						<tr>
							<td rowspan="2" style="width:50px;"><img src="<%=basePath%>image/chat.png" />
							</td>
							<td><s:text name="desktop.chat" /></td>
						</tr>
						<tr>
							<td><input id="CHAT" name="CHAT" type="text" value="" style="width:20px;text-align:center">
							</td>
						</tr>
					</table>
				</div>
				-->
				<div class="item boxradius" style="margin:10px;width:120px;height:50px;">
					<table class="gTab">
						<tr>
							<td rowspan="2" style="width:50px;"><img src="<%=basePath%>image/lamp.png" />
							</td>
							<td><s:text name="desktop.idea" /></td>
						</tr>
						<tr>
							<td><input id="IDEA" name="IDEA" type="text" value="" style="width:20px;text-align:center">
							</td>
						</tr>
					</table>
				</div>
				<div class="item boxradius" style="margin:10px;width:120px;height:50px;">
					<table class="gTab">
						<tr>
							<td rowspan="2" style="width:50px;"><img src="<%=basePath%>image/newspaper.png" />
							</td>
							<td><s:text name="desktop.msg" /></td>
						</tr>
						<tr>
							<td><input id="MSG" name="MSG" type="text" value="" style="width:20px;text-align:center">
							</td>
						</tr>
					</table>
				</div>
				<div class="item boxradius" style="margin:10px;width:120px;height:50px;">
					<table class="gTab">
						<tr>
							<td rowspan="2" style="width:50px;"><img src="<%=basePath%>image/help.png" />
							</td>
							<td><s:text name="desktop.help" /></td>
						</tr>
						<tr>
							<td><input id="HELP" name="HELP" type="text" value="" style="width:20px;text-align:center">
							</td>
						</tr>
					</table>
				</div>
				<!--暂未开放
				<div class="item boxradius" style="margin:10px;width:120px;height:50px;">
					<table class="gTab">
						<tr>
							<td rowspan="2" style="width:50px;"><img src="<%=basePath%>image/approval.png" />
							</td>
							<td><s:text name="desktop.flow" /></td>
						</tr>
						<tr>
							<td><input id="FLOW" name="FLOW" type="text" value="" style="width:20px;text-align:center">
							</td>
						</tr>
					</table>
				</div>
				-->
			</div>
			<div style="margin:15px auto;width:70%;height:70%;">
				<input type="submit" value="提交" onclick="UpdateConfigText();">
			</div>
		</div>
		</li>
			<li style="width:50%;height:98%;margin:0px;padding:0px;float:left;">
				<div id="msg_send" class="boxradius" style="width:99%;height:100%;margin:0 auto;overflow:hidden;">
					<div id="ms" style="margin:0 auto;">
						<div class="detail boxradius" style="margin:10px;width:300px;height:50px;">
							<table class="gTab" style="height:100%;">
								<tr>
									<td>
										<h2><s:text name="setting.help_msg" /></h2>
									</td>
									<td style="width:90px;">
										<input id="help_msg" type="checkbox" value="1" <s:if test="#request.MsgConfig[0][1]>0">checked</s:if>>
									</td>
								</tr>
							</table>
						</div>
						<div class="detail boxradius" style="margin:10px;width:300px;height:50px;">
							<table class="gTab" style="height:100%;">
								<tr>
									<td>
										<h2><s:text name="setting.idea_msg" /></h2>
									</td>
									<td style="width:90px;">
										<input id="idea_msg" type="checkbox" value="1" <s:if test="#request.MsgConfig[0][0]>0">checked</s:if>>
									</td>
								</tr>
							</table>
						</div>
						<div class="detail boxradius" style="margin:10px;width:300px;height:50px;">
							<table class="gTab" style="height:100%;">
								<tr>
									<td>
										<h2><s:text name="setting.pro_msg" /></h2>
									</td>
									<td style="width:90px;">
										<input name="pro_target" id="pro_msg" type="radio" value="1" <s:if test="#request.MsgConfig[0][2]>0">checked</s:if>>
									</td>
								</tr>
							</table>
						</div>
						<div class="detail boxradius" style="margin:10px;width:300px;height:50px;">
							<table class="gTab" style="height:100%;">
								<tr>
									<td>
										<h2><s:text name="setting.protarget_msg" /></h2>
									</td>
									<td style="width:90px;">
										<input name="pro_target" id="protarget_msg" type="radio" value="1" <s:if test="#request.MsgConfig[0][3]>0">checked</s:if>>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div style="margin:15px auto;width:70%;height:70%;">
						<input type="submit" value="提交" onclick="UpdateMsgConfig();">
					</div>
				</div>
			</li>
		</ul>
	</div>
	<div>
		
	</div>
</body>
<script type="text/javascript">
	var config_str = '<s:property value="#request.ConfigText"/> ';
	$(function() {
		var $container = $('#container');
		$container.imagesLoaded(function() {
			$container.masonry({
				itemSelector : '.item',
				isFitWidth : true,
				isResizable : true
			});
		});
		var $ms = $('#ms');
		$ms.imagesLoaded(function() {
			$ms.masonry({
				itemSelector : '.detail',
				isFitWidth : true,
				isResizable : true
			});
		});
		$("#help_msg").iButton();
		$("#idea_msg").iButton();
		$("input[name='pro_target']").iButton({allowRadioUncheck: true});
		SelectTab('setting');
	});
	
	if(config_str != ""){
		var configArr = config_str.split(";");
		for(i=0;i<configArr.length;i++){
			var c = configArr[i].split(":");
			if(c[1]>0 && c[0] != ""){
				SetConfigTextValue(c[0],c[1]);
			}
		}
	}
	
	function SetConfigTextValue(id,val){
		$("#"+id).val(val);
	}
	
	function UpdateConfigText(){
		var textArr = $(":text");
		var data = "";
		for(i=0;i<textArr.length;i++){
			data += textArr[i].id+":"+textArr[i].value+";";
		}
		
		$.ajax({
			type : 'POST',
			url : "UserSaveSetting",
			data : {
				"config_text" : data
			},
			success : function UpdateOK(data) {
				if(data == ""){
					alert('<s:text name="error.db" />');
					return;
				}
				var json_data = $.parseJSON(data);
				if (json_data[0].error != "") {
					alert(json_data[0].error);	
				}else{
					window.parent.window.location.reload();
				}
			}
		});
	}
	
	function UpdateMsgConfig(){
		var idea_send = 0;
		var help_send = 0;
		var pro_send = 0;
		var protarget_send = 0;
		if($("#idea_msg").is(":checked")){
			idea_send = 1;
		}
		
		if($("#help_msg").is(":checked")){
			help_send = 1;
		}
		
		if($("#pro_msg").is(":checked")){
			pro_send = 1;
		}
		
		if($("#protarget_msg").is(":checked")){
			protarget_send = 1;
		}
		
		$.ajax({
			type : 'POST',
			url : "UserSaveMsgConfig",
			data : {
				"idea_send" : idea_send,
				"help_send" : help_send,
				"pro_send" : pro_send,
				"protarget_send":protarget_send
			},
			success : function(data) {
				if(data != "ok"){
					alert(data);
				}else{
					window.location.reload(true);
				}
			}
		});
	}
	
	var thisSelected = "";
	function SelectTab(tabName) {
		if(thisSelected == tabName){
			return;
		}
		$("#selectBG").animate({
			marginLeft : $("#" + tabName + "li").css("margin-left"),
			width: $("#" + tabName + "li").width()+6+"px",
			height: $("#" + tabName + "li").height()+6+"px"
		}, 300);
		if($("#selectTab").css("marginLeft") != "0px"){
			$("#selectTab").animate({marginLeft : "0px"}, 300);
		}else{
			$("#selectTab").animate({marginLeft : "-100%"}, 300);
		}
		thisSelected = tabName;
	}
</script>
</html>