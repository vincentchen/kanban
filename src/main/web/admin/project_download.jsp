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
<html style="width:100%;height:98%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/uploadify.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
<script src="<%=basePath%>js/jquery.uploadify.js"></script>
</head>
<body style="width:100%;height:98%;">
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<input id="ProID" type="hidden" value="<s:property value="#Global.RegisterID('Project',#request.ProId)" />">
	<div style="margin:3px auto;height:35px;">
		<ul style="width:180px;margin:3px auto;height:31px;list-style-type:none;background-color:#fff">
			<div id="selectBG" class="boxradius" style="width:78px;height:29px;background-color:#E5E5E5;position: absolute;z-index:8"></div>
			<a href="javscript:void(0);" onclick="SelectTab('download');"><li id="downloadli" class="boxradius" style="float:left;height:23px;z-index:99;position: absolute;padding:3px;">
					<h1 style="padding:0px;">
						<s:text name="download" />
					</h1></li> </a>
			<s:if test="#session.ProPri.WRITE>0">
			<a href="javscript:void(0);" onclick="SelectTab('upload');"><li id="uploadli" class="boxradius" style="margin-left:82px;float:left;height:23px;z-index:99;position: absolute;padding:3px;">
					<h1 style="padding:0px;">
						<s:text name="uploadfiles" />
					</h1></li> 
			</s:if></a>
		</ul>
	</div>
	<div class="boxradius" style="margin:0px auto;width:90%;height:90%;overflow: hidden;">
		<ul id="selectTab" style="width:200%;height:100%;list-style-type:none;">
			<li id="download" style="width:50%;height:100%;margin:0px;padding:0px;float:left;">
				<div style="width:100%;height:100%;overflow:auto">
					<s:iterator value="#request.Files" status="st">
						<a href="SystemFilesDownLoad?id=<s:property value="#Global.RegisterID('SystemFiles',#request.Files[#st.index][0])"/>">
							<div class="ListDiv">
								<table class="gTab">
									<tr>
										<td style="text-align:left;"><s:text name="files.name" />:<s:property value="#request.Files[#st.index][1]" />
										</td>
										<td rowspan="2" style="width:10%;height:33px;"><img alt="" src="<%=basePath%>image/download.png">
										</td>
									</tr>
									<tr>
										<td style="text-align:left;"><s:text name="files.des" />:<s:property value="#request.Files[#st.index][4]" />
										</td>
									</tr>
								</table>
							</div> </a>
						</table>
					</s:iterator>
				</div>
			</li>
			<s:if test="#session.ProPri.WRITE>0">
			<li id="upload" style="width:50%;height:100%;margin-top:5%;padding:0px;float:left;">
				<table class="gTab">
					<tr>
						<td style="width:45px;height:65px;line-height:65px;"><img src="<%=basePath%>image/1.png" /></td>
						<td id="tdUpload" style="height:65px;line-height:65px;"><input type="file" name="uploadify" id="uploadify" /></td>
					</tr>
					<tr>
						<td style="width:45px;height:65px;line-height:65px;"><img src="<%=basePath%>image/2.png" /></td>
						<td style="text-align:left;">&nbsp;<s:text name="download.des" />:<br /> <textarea id="UploadDes" style="width:98%;margin:5px;"></textarea></td>
					</tr>
					<tr>
						<td style="width:45px;height:65px;line-height:65px;"><img src="<%=basePath%>image/3.png" /></td>
						<td><input type="submit" value="<s:text name="determine.upload" />" style="margin:5px;width:98%;float:left;" onclick="$('#uploadify').uploadify('upload','*');"></td>
					</tr>
				</table></li>
			</s:if>
		</ul>
	</div>
	<script type="text/javascript">
		<s:if test="#session.ProPri.WRITE>0">
		$(document).ready(function() {
			$('#uploadify').uploadify({
				'auto':false,
				'fileObjName' : 'file',
				'multi'    : false,
				'buttonText' : '<s:text name="uploadfiles" />',
				'swf'      : '<%=basePath%>image/uploadify.swf',
				'uploader' : '<%=basePath%>SystemFilesUpload',
				'width' : 70,
				'height' : 37,
				'formData' : {'tblName':'Project','tblId':$("#ProID").val()},
				'onDialogOpen' : function() {
					$('#uploadify').uploadify('cancel', '*');
				},
				'onSWFReady' : function() {
					$("#uploadify").css("float", "left");
					$("#uploadify").css("margin", "5px");
					$("#uploadify-queue").css("float", "left");
					$("#uploadify-queue").css("width", $("#tdUpload").width() - 80 + "px");
					$("#uploadify-queue").css("height", "37px");
				},
				'onUploadStart' : function(file) {
					var datajson = {"files_des":$("#UploadDes").val()}
					$("#uploadify").uploadify("settings", "formData", datajson);
				},
				'onUploadSuccess' : function(file, data, response) {
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);
					} else {
						window.location.reload();
					}
				}
			});
		});

		var thisSelected = "";
		function SelectTab(tabName) {
			if(thisSelected == tabName){
				return;
			}
			$("#selectBG").animate({
				marginLeft : $("#" + tabName + "li").css("margin-left")
			}, 300);
			if (tabName == 'upload') {
				$("#selectTab").animate({
					marginLeft : "-100%"
				}, 300);
			} else {
				$("#selectTab").animate({
					marginLeft : "0"
				}, 300);
			}
			thisSelected = tabName;
		}
		</s:if>
	</script>
</body>
</html>