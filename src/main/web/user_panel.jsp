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
<html style="width:100%;height:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="P3P" content='CP="IDC DSP COR CURa ADMa OUR IND PHY ONL COM STA"'>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/uploadify.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
<script src="<%=basePath%>js/datepicker.js"></script>
<script src="<%=basePath%>js/jquery.uploadify.js"></script>
</head>
<body style="width:100%;height:100%;">
	<div style="width:100%;height:100%;clear: both;overflow: hidden;">
		<ul id="Panel" style="width:200%;height:100%;list-style-type:none;margin:0px;padding:0px;">
			<li id="UserInfoPanel" style="width:50%;height:100%;margin:0px;padding:0px;float:left;">
				<div>
					<h1>
						<s:text name="msg.user.info" />
					</h1>
				</div>
				<div class="boxradius" style="margin:0 auto;width:70%;height:70%;">
					<table class="gTab" style="margin:10px;">
						<colgroup style="width:130px;"></colgroup>
						<colgroup style="width:250px;"></colgroup>
						<tr>
							<td><s:text name="account" /></td>
							<td><s:property value="#request.Users.user_account" /></td>
							<td rowspan="5" style="text-align:center"><s:if test="null==#request.Users.user_photo || #request.Users.user_photo.isEmpty()">
									<div style="width:100%;float:left;">
										<img id="user_photo" src="<%=basePath%>image/no_pictur.png" style="width:100px;height:100px;" />
									</div>
									<div style="width:100%;float:left;">
										<input id="photo_upload" name="photo_upload" type="file">
									</div>
								</s:if> <s:else>
									<div style="width:100%;float:left;">
									<img id="user_photo" src="<%=basePath%><s:property value='#request.Users.user_photo' />" style="width:100px;height:100px;" />
									</div>
									<div style="width:100%;float:left;text-align:center">
									<input id="photo_upload" name="photo_upload" type="file">
									</div>
								</s:else></td>
						</tr>
						<tr>
							<td><s:text name="username" /></td>
							<td>
								<table class="gTab">
									<tr>
										<td style="width:80%;"><input id="user_name" type="text" value="<s:property value='#request.Users.user_name' />" onpropertychange="InputChange('user_name_modify');" oninput="InputChange('user_name_modify');" />
										</td>
										<td><a href="javascript:void(0)" id="user_name_modify" onclick="UpdateUserName(this);" style="display:none">确定</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td><s:text name="email" /></td>
							<td>
								<table class="gTab">
									<tr>
										<td style="width:80%;"><input id="email" type="text" value="<s:property value='#request.Users.email' />" onpropertychange="InputChange('email_modify');" oninput="InputChange('email_modify');"></td>
										<td><a href="javascript:void(0)" id="email_modify" onclick="UpdateUserEmail(this);" style="display:none">确定</a></td>
									</tr>
								</table></td>
						</tr>
						<tr>
							<td><s:text name="user.dept.role" /></td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td colspan="2"><s:if test="#request.RelDeptRoleList.size()>0">
									<s:iterator value="#request.RelDeptRoleList" id="list">
										<div style="width:20%;" class="boxradius">
											<s:property value="#list.dept_name" />
											-
											<s:property value="#list.role_name" />
										</div>
									</s:iterator>
								</s:if> <s:else>&nbsp;</s:else></td>
						</tr>
					</table>
				</div>
				<div class="boxradius" style="margin:5px auto;width:70%;height:10%;">
					<table style="width:100%;height:100%;">
						<tr>
							<td style="width:32px;"><img src="<%=basePath%>image/lvup.png">
							</td>
							<td style="width:50px;"><s:text name="msg.user.lever" />
							</td>
							<td><s:text name="yet.open" />
							</td>
						</tr>
					</table>
				</div>
			</li>
			<li id="UserHistory" style="width:50%;height:100%;margin:0px;padding:0px;float:left;">
				<div>
					<h1>
						<s:text name="msg.user.history" />
					</h1>
				</div>
				<div class="boxradius" style="margin:0 auto;width:70%;height:70%;overflow-y:scroll;">
					<table class="gTab" id="current_month_history">
						<tr>
							<td colspan="3" style="text-align:center;font-size:14px;color:#000;font-weight:bold;border-bottom:1px solid #E5E5E5">
								<h2><s:text name="history.current.month" /></h2>
							</td>
						</tr>
						<tr>
							<td style="width:50px;border-bottom:1px solid #E5E5E5;border-right:1px solid #E5E5E5"><s:text name="history.type" />
							</td>
							<td style="border-bottom:1px solid #E5E5E5;border-right:1px solid #E5E5E5;text-align:center"><s:text name="history.content" />
							</td>
							<td style="width:60px;border-bottom:1px solid #E5E5E5"><s:text name="history.logtime" />
							</td>
						</tr>
					</table>
					<div id="month_loading" style="width:100%;height:32px;">
						<img src="<%=basePath%>image/loading.gif" />
					</div>
				</div>
				<div class="boxradius" style="margin:5px auto;width:70%;height:10%;">
					<table class="gTab" style="height:100%">
						<tr>
							<td style="width:32px;"><img src="<%=basePath%>image/date.png" /></td>
							<td style="width:50px;"><s:text name="history.download" /></td>
							<td style="width:50px;"><s:text name="time.start" /></td>
							<td style="padding-right:10px;"><input id="start_time" type="text" value="" /></td>
							<td style="width:50px;"><s:text name="time.end" /></td>
							<td style="padding-right:10px;"><input id="end_time" type="text" value="" /></td>
							<td style="text-align:left;width:80px;vertical-align:middle;"><a href="javascript:void(0);" onclick="DownLoadHistory();"><img src="<%=basePath%>image/download.png" />下载</a></td>
						</tr>
					</table>
				</div>
			</li>
		</ul>
	</div>
	<div style="border-top: 1px solid #E5E5E5;width:100%;bottom:8px;position: absolute;text-align:center">
		<div id="content_select_arrow" style="position: absolute;height:10px;">
			<div class="arrow_entity"></div>
			<div class="arrow_empty"></div>
		</div>
		<ul style="list-style-type: none;height:32px;line-height:32px;text-align:center">
			<li id="UserInfoPanel_link" style="height:32px;line-height:32px;display:inline;"><a href="javascript:void(0);" onclick="OpenUrl('UserInfoPanel',0);"><img src="<%=basePath%>image/idcard.png" style="margin-top:-7px;"> <s:text name="msg.user.info" /> </a></li>
			<li id="UserHistory_link" style="height:32px;line-height:32px;display:inline;"><a href="javascript:void(0);" onclick="OpenUrl('UserHistory',1);"><img src="<%=basePath%>image/history.png" style="margin-top:-7px;"> <s:text name="msg.user.history" /> </a></li>
		</ul>
	</div>
	<script type="text/javascript">
		var current = "";
		var is_read = 0;
		$(document).ready(function() {
			MoveArrowToSelect('UserInfoPanel');
			$('#start_time').datepicker({
				format : 'yyyy-mm-dd'
			});
			$('#end_time').datepicker({
				format : 'yyyy-mm-dd'
			});
			
			$('#photo_upload').uploadify({
				'fileObjName' : 'file_photo',
				'multi'    : false,
				'buttonText' : '<s:text name="upload.user_photo" />',
				'swf'      : '<%=basePath%>image/uploadify.swf',
				'uploader' : 'UserUploadPhoto',
				'width' : 70,
				'height' : 20,
				'fileTypeDesc' : '请选择图片文件',
		        'fileTypeExts' : '*.jpeg; *.jpg; *.png',
		        'onSWFReady' : function() {
		        	$("#photo_upload-queue").css("width","200px");
		        	$("#photo_upload-queue").css("margin","0 auto");
		        },
		        'onUploadSuccess' : function(file, data, response) {
		        	var json = eval("(" + data + ")");
		        	if(json[0].error != ""){
		        		alert(json[0].error);
		        	}else{
			        	$('#user_photo').attr("src","<%=basePath%>"+json[0].content);
		        	}
		        }
			});
		});

		function MoveArrowToSelect(id) {
			if (current == id) {
				return;
			}
			var left_value = ($("#" + id + "_link").offset().left + $("#" + id + "_link").outerWidth() / 2 - 10);
			$("#content_select_arrow").animate({
				left : left_value
			}, 300);
			if (id == "UserHistory") {
				if (is_read == 0) {
					LoadUserCurrentMonthHistory();
				}
			}
		}
		
		function LoadUserCurrentMonthHistory(){
			$.ajax({
				type : 'POST',
				url : "UserTheMonthHistory",
				beforeSend : function Loading() {
					$('#month_loading').css("display", "block");
				},
				success : function addMonthHistory(data) {
					if(data == ""){
						alert('<s:text name="error.db" />');
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						$('#current_month_history>tbody').append('<tr><td colspan="3" style="text-align:center">' + json_data[0].error + '</td></tr>');
					} else {
						var list = json_data[0].content;
						for (var i = 0; i < list.length - 1; i++) {
							$('#current_month_history>tbody').append('<tr><td style="border-bottom:1px dashed #E5E5E5;border-right:1px dashed #E5E5E5">' + list[i].historyTypeName + '</td><td style="border-bottom:1px dashed #E5E5E5;border-right:1px dashed #E5E5E5">' + list[i].historyContent + '</td><td style="border-bottom:1px dashed #E5E5E5;">' + list[i].historyLogTime + '</td></tr>');
						}
						$('#current_month_history>tbody').append('<tr><td colspan="3" style="border-bottom:1px solid #E5E5E5">' + list[i] + '</td></tr>');
					}
					$('#month_loading').css("display", "none");
					is_read = 1;
				}
			});
		}

		function MovePanel(num) {
			$("#Panel").animate({
				marginLeft : "-" + num * 100 + "%"
			}, 200);
		}

		function OpenUrl(str, num) {
			if (current != str) {
				MoveArrowToSelect(str);
				MovePanel(num);
			}
		}

		function DownLoadHistory() {
			if ($("#start_time").val() != "" && $("#end_time").val() != "") {
				if (Date.parse($("#end_time").val()) <= Date.parse($("#start_time").val())) {
					alert("<s:text name='error.endtime.greater.starttime' />");
					return;
				}
			} else {
				alert("<s:text name='error.time.is_null' />");
				return;
			}
			window.location.href = "UserDownLoadHistory.action?history_min_time=" + $("#start_time").val() + "&history_max_time=" + $("#end_time").val();
		}
		
		function UpdateUserName(obj){
			$.ajax({
				type : 'POST',
				url : "UserUpdateUserName",
				data : {
					"user_name" : $("#user_name").val()
				},
				success : function UpdateUserNameOK(data) {
					if(data == ""){
						alert('<s:text name="error.db" />');
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);
					}else{
						obj.style.display = "none";
					}
				}
			});
		}
		
		function UpdateUserEmail(obj){
			$.ajax({
				type : 'POST',
				url : "UserUpdateUserEmail",
				data : {
					"email" : $("#email").val()
				},
				success : function UpdateUserNameOK(data) {
					if(data == ""){
						alert('<s:text name="error.db" />');
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);
					}else{
						obj.style.display = "none";
					}
				}
			});
		}

		function InputChange(id) {
			$("#" + id).css("display", "block");
		}
	</script>
</body>
</html>