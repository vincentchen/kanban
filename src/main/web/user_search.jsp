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
<title>用户高级查询</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/zTreeStyle.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.ztree.core-3.4.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.ztree.excheck-3.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.ztree.exedit-3.4.min.js"></script>
<script src="<%=basePath%>js/jquery.masonry.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:100%;">
	<div style="height:50px;border-bottom:1px solid #E5E5E5;line-height:50px;">
		<table style="width:90%;margin:0 auto;">
			<tr>
				<td style="width:70px"><s:text name="search.user_name"/>:
				</td>
				<td style="width:150px;">
					<input id="user_name" type="text" value="">
				</td>
				<td style="width:60px"><s:text name="search.user_account"/>:
				</td>
				<td style="width:150px;">
					<input id="user_account" type="text" value="">
				</td>
				<td style="width:60px"><s:text name="search.email"/>:
				</td>
				<td style="width:150px;">
					<input id="email" type="text" value="">
				</td>
				<td style="width:100px;">
					<input type="button" value="<s:text name="determine"/>" onclick="SearchAdvanced();">
				</td>
				<td style="width:100px;">
					<input type="button" value="<s:text name="search.selected"/>" onclick="SelectedView();">
				</td>
				<td>
					<input type="button" value="<s:text name="select.ok"/>" onclick="SelectOk();">
				</td>
			</tr>
		</table>
	</div>
	<div style="height:90%;">
		<input id="usersSelectList" type="hidden" value="">
		<table class="gTab" style="height:100%;">
			<tr style="border-bottom:1px solid #E5E5E5;">
				<td style="width:30%;height:100%;border-right:1px solid #E5E5E5;overflow:auto;">
					<div style="height:100%;"><div style="width:100%;text-align:center"><s:text name="dept.list" /></div><ul id="dept" class="ztree" style="padding:5px 0px 0px 0px;"></ul></div>
				</td>
				<td id="users" style="width:70%;height:100%;">
					<s:text name="search.prompt" />
				</td>
			</tr>
		</table>
	</div>
	<div id="block" style="position: absolute;display:none;background-color:#fff;opacity:0.6;z-index:88"><div style="width:100%;height:50%;"></div><div class="boxshadow" style="width:200px;margin:0 auto;"><h2><s:text name="loading"/></h2></div></div>
	<script type="text/javascript">
var usersSelectType = "<s:property value='#request.usersSelectType'/>";
var setting = {view: {addDiyDom: addDiyDom,dblClickExpand: false,showLine: false},data: {simpleData: {enable: true}},callback:{onClick:ztreeClick}};
var ajax;
var thisChangeDept = 0;
var zNodes =[
<s:iterator value="#request.DeptList" status="st">
	{id:<s:property value="#request.DeptList[#st.index].dept_id"/>, pId:<s:property value="#request.DeptList[#st.index].parent_id"/>, name:"<s:property escape='false' value='#request.DeptList[#st.index].dept_name'/>"<s:if test="#request.DeptList[#st.index].has_child==1">, open:true</s:if>}<s:if test="!#st.last">,</s:if>
</s:iterator>
	];
var selectButtonText = "<s:text name='select'/>";
var cancelButtonText = "<s:text name='select.cancel'/>";

function SearchAdvanced(){
	if(ajax != null){
		ajax.abort();
	}
	var usersSelectList = $("#usersSelectList").val();
	usersSelectList = usersSelectList.substr(0,usersSelectList.length-1);
	ajax = $.ajax({
		type : 'POST',
		url : "UserLoadUsersByCondition",
		data:{
			"user_name":$("#user_name").val(),
			"user_account":$("#user_account").val(),
			"email":$("#email").val(),
			"usersSelectList":usersSelectList,
			"usersSelectType":usersSelectType
		},
		beforeSend :function(){$("#block").css("display","block")},
		success : function(data) {
			if(data == ""){
				alert('<s:text name="error.db" />');
				$('#block').css("display", "none");
				return;
			}
			var json_data = $.parseJSON(data);
			if (json_data[0].error != "") {
				alert(json_data[0].error);
			} else {
				$("#users").html("");
				var list = json_data[0].content;
				var content = "";
				for (i = 0; i < list.length; i++) {
					if(list[i][2] == null || list[i][2] == ""){
						list[i][2] = "<%=basePath%>image/no_pictur.png";
					}
					if(usersSelectList.indexOf(list[i][0])>0){
						content += '<div class="item boxshadow" style="width:203px;height:100px;margin:5px;float:left"><table style="width:203px;height:100px"><tr><td rowspan="2" style="width:100px;height:100px;border-right:1px solid #E5E5E5;text-align:center"><img src="'+list[i][2]+'"/></td><td style="border-bottom:1px solid #E5E5E5;height:50px;line-height:50px;text-align:center"><h2>'+list[i][1]+'</h2></td></tr><tr><td style="text-align:center"><input name="sl" id="'+list[i][0]+'" type="button" value="'+cancelButtonText+'" onclick="ChangeUsersSelectList('+list[i][0]+',\''+list[i][1]+'\');"/></td></tr></table></div>';
					}else{
						content += '<div class="item boxshadow" style="width:203px;height:100px;margin:5px;float:left"><table style="width:203px;height:100px"><tr><td rowspan="2" style="width:100px;height:100px;border-right:1px solid #E5E5E5;text-align:center"><img src="'+list[i][2]+'"/></td><td style="border-bottom:1px solid #E5E5E5;height:50px;line-height:50px;text-align:center"><h2>'+list[i][1]+'</h2></td></tr><tr><td style="text-align:center"><input name="sl" id="'+list[i][0]+'" type="button" value="'+selectButtonText+'" onclick="ChangeUsersSelectList('+list[i][0]+',\''+list[i][1]+'\');"/></td></tr></table></div>';
					}
				}
				$('#users').html(content);
			}
			RangeUsersDiv();
			$("#block").css("display","none");
			ajax = null;
		}
	});
}

function SelectedView(){
	$("#users").html("");
	thisChangeDept=0;
	var usersSelectList = $("#usersSelectList").val();
	usersSelectList = usersSelectList.substr(0,usersSelectList.length-1);
	var arr = usersSelectList.split(",");
	if(arr.length>0 && arr[0] != ""){
		for(var i=0;i<arr.length;i++){
			var a = arr[i];
			var b = a.split(":");
			var user_id = b[0].substr(1);
			var user_name = b[1].substr(0,b[1].length-1);
			$('#users').append('<div class="item boxshadow" id="'+user_id+'" style="width:150px;height:30px;margin:5px"><table style="width:150px;height:30px"><tr><td rowspan="2" style="width:120px;height:30px;padding:0px;border-right:1px solid #E5E5E5;text-align:center"><h2>'+user_name+'</h2></td><td style="height:30px;padding:0px;line-height:30px;text-align:center"><img src="<%=basePath%>image/close.png" style="cursor:pointer;" onclick="CancelUsers('+user_id+',\''+user_name+'\');"/></td></tr></table></div>');
		}
	}
	RangeUsersDiv();
}

function CancelUsers(id,name){
	var usersSelectList = $("#usersSelectList").val();
	if(usersSelectType == "checkbox"){
		var arr = usersSelectList.split("["+id+":"+name+"],");
		var a = "";
		for (var i=0 ; i< arr.length ; i++)
		{
		    a += arr[i];
		}
		$("#usersSelectList").val(a);
	}else{
		$("#usersSelectList").val("");
	}
	SelectedView();
}

function ztreeClick(e,treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("dept");
	zTree.expandNode(treeNode);
}

function addDiyDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	var editStr = "<span class='addIcon' id='diyBtn_" +treeNode.id+ "' title='"+treeNode.name+"' onfocus='this.blur();'><span class='button openDept'></span></span>";
	aObj.after(editStr);
	var btn = $("#diyBtn_"+treeNode.id);
	if (btn) btn.click(function(){GetUserByDept(treeNode.id,treeNode.name)});
}

function GetUserByDept(id,name){
	if(thisChangeDept == id){
		return;
	}
	thisChangeDept = id;
	if(ajax != null){
		ajax.abort();
	}
	var usersSelectList = $("#usersSelectList").val();
	usersSelectList = usersSelectList.substr(0,usersSelectList.length-1);
	ajax = $.ajax({
		type : 'POST',
		url : "UserLoadUsersByDept",
		data:{
			"id":id,
			"usersSelectList":usersSelectList,
			"usersSelectType":usersSelectType
		},
		beforeSend :function(){$("#block").css("display","block")},
		success : function(data) {
			if(data == ""){
				alert('<s:text name="error.db" />');
				$('#block').css("display", "none");
				return;
			}
			var json_data = $.parseJSON(data);
			if (json_data[0].error != "") {
				alert(json_data[0].error);
			} else {
				$("#users").html("");
				var list = json_data[0].content;
				var content = "";
				for (i = 0; i < list.length; i++) {
					if(list[i][2] == null || list[i][2] == ""){
						list[i][2] = "<%=basePath%>image/no_pictur.png";
					}
					if(usersSelectList.indexOf(list[i][0])>0){
						content += '<div class="item boxshadow" style="width:203px;height:100px;margin:5px;float:left"><table style="width:203px;height:100px"><tr><td rowspan="2" style="width:100px;height:100px;border-right:1px solid #E5E5E5;text-align:center"><img src="'+list[i][2]+'"/></td><td style="border-bottom:1px solid #E5E5E5;height:50px;line-height:50px;text-align:center"><h2>'+list[i][1]+'</h2></td></tr><tr><td style="text-align:center"><input name="sl" id="'+list[i][0]+'" type="button" value="'+cancelButtonText+'" onclick="ChangeUsersSelectList('+list[i][0]+',\''+list[i][1]+'\');"/></td></tr></table></div>';
					}else{
						content += '<div class="item boxshadow" style="width:203px;height:100px;margin:5px;float:left"><table style="width:203px;height:100px"><tr><td rowspan="2" style="width:100px;height:100px;border-right:1px solid #E5E5E5;text-align:center"><img src="'+list[i][2]+'"/></td><td style="border-bottom:1px solid #E5E5E5;height:50px;line-height:50px;text-align:center"><h2>'+list[i][1]+'</h2></td></tr><tr><td style="text-align:center"><input name="sl" id="'+list[i][0]+'" type="button" value="'+selectButtonText+'" onclick="ChangeUsersSelectList('+list[i][0]+',\''+list[i][1]+'\');"/></td></tr></table></div>';
					}
				}
				$('#users').html(content);
			}
			RangeUsersDiv();
			$('#block').css("display", "none");
			ajax = null;
		}
	});
}

function ChangeUsersSelectList(id,name){
	var b = $("#"+id);
	var st = $("#usersSelectList");
	if(usersSelectType == "checkbox"){
		if(b.val() == selectButtonText){
			b.val(cancelButtonText);
			if(st.val().indexOf(id)==-1){
				var idList = st.val()+"["+id+":"+name+"],";
				st.val(idList);
			}
		}else{
			b.val(selectButtonText);
			var idList = st.val();
			if(idList.indexOf("["+id+":"+name+"],")>-1){
				var arr = idList.split("["+id+":"+name+"],");
				var a = "";
				for (var i=0 ; i< arr.length ; i++)
				{
				    a += arr[i];
				}
				st.val(a);
			}
		}
	}else{
		if(b.val() == selectButtonText){
			b.val(cancelButtonText);
			var str = st.val();
			str = str.split(":");
			var user_id = str[0].substr(1);
			$("#"+user_id).val(selectButtonText);
			st.val("["+id+":"+name+"],");
		}else{
			b.val(selectButtonText);
			st.val("");
		}
	}
}

function RangeUsersDiv(){
	//var $container = $('#users');
	//$container.imagesLoaded(function() {
	//	$container.masonry({
	//		itemSelector : '.item'
	//	});
	//});
	//$container.masonry('reload');
	//$('#users').css("position","absolute");
}

$(document).ready(function(){
	$.fn.zTree.init($("#dept"), setting, zNodes);
});

$(document).ready(function(){
	var users = $("#users");
	$("#block").css({"width":users.width()-1+"px","height":users.height()+"px","line-height":users.height()+"px","top":users.offset().top+"px","left":users.offset().left+"px"});
});

function SelectOk(){
	var usersJson = {};
	var usersSelectList = $("#usersSelectList").val();
	usersSelectList = usersSelectList.substr(0,usersSelectList.length-1);
	var arr = usersSelectList.split(",");
	for(var i=0;i<arr.length;i++){
		var a = arr[i];
		var b = a.split(":");
		var user_id = b[0].substr(1);
		var user_name = b[1].substr(0,b[1].length-1);
		usersJson[user_id] = user_name;
	}
	window.opener.<s:property value='#request.callBackFunc'/>(usersJson);
	window.close();
}
	</script>
</body>
</html>