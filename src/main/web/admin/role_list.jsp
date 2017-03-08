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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/zTreeStyle.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.ztree.core-3.4.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.ztree.excheck-3.4.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.ztree.exedit-3.4.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:100%;">
	<div style="width:100%;height:45px;border-bottom:1px solid #E5E5E5">
		<div style="width:200px;margin-left:25px;"><h1 style="float:left"><s:text name="role.list" /></h1></div>
		<div style="float:right">
			<table style="margin-right:25px;">
				<tr>
					<td style="float:left;height:32px;line-height:32px;">
					<s:if test="#session.RoleInfoPri.ADD>0">
					<a href="javascript:void(0);" onclick="AddRole();">
						<img alt="" src="<%=basePath%>image/add.png" style="float:left;">
						<h1 style="padding:3px;float:left;"><s:text name="add" /></h1>
					</a>
					</s:if></td>
				</tr>
			</table>
		</div>
	</div>
	<div style="width:98%;float:left;overflow:auto;margin:0 auto;padding:3px;">
		<div style="text-align:right;"><s:text name="prompt.role.move" /></div>
		<ul id="role" class="ztree" style="padding:5px 0px 0px 0px"></ul>
	</div>
	<div id="block" style="position: absolute;top:0px;left:0px;display:none;background-color:#fff;opacity:0.6;z-index:88"><div style="width:100%;height:50%;"></div><div class="boxshadow" style="width:200px;margin:0 auto;"><h2><s:text name="loading"/></h2></div></div>
	
	<script type="text/javascript">
	var setting = {view: {addDiyDom: addDiyDom,dblClickExpand:false,showLine:false}
	<s:if test="#session.RoleInfoPri.WRITE>0">
		,edit:{enable:true,editNameSelectAll:true,showRemoveBtn:false,showRenameBtn:false}
	</s:if>,data:{simpleData:{enable:true}},callback:{onClick:ztreeClick
	<s:if test="#session.RoleInfoPri.WRITE>0">
		,beforeRename:beforeRename,onRename:onRename,onDrop:onDrop
	</s:if>
	<s:if test="#session.RoleInfoPri.WRITE>0 && #session.RoleInfoPri.DEL>0">,</s:if>
	<s:if test="#session.RoleInfoPri.DEL>0">
	beforeRemove:beforeRemove,onRemove:onRemove
	</s:if>}};
	var zNodes =[
	<s:iterator value="#request.RoleList" status="st">
		{id:<s:property value="#request.RoleList[#st.index].role_id"/>, pId:<s:property value="#request.RoleList[#st.index].parent_id"/>, name:"<s:property escape='false' value='#request.RoleList[#st.index].role_name'/>", open:true}<s:if test="!#st.last">,</s:if>
	</s:iterator>
		];

	var className = "dark";
	function ztreeClick(e,treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("role");
		zTree.expandNode(treeNode);
	}

	function addDiyDom(treeId, treeNode) {
		var aObj = $("#" + treeNode.tId + "_a");
		var editStr = "<span class='addIcon' onfocus='this.blur();'>";
		<s:if test="#session.RoleInfoPri.ADD>0">
			editStr += "<span class='button add' id='addBtn_" +treeNode.id+ "' title='"+treeNode.name+"'></span>";
		</s:if>
		<s:if test="#session.RoleInfoPri.WRITE>0">
			editStr += "<span class='button edit' id='editBtn_" +treeNode.id+ "' title='"+treeNode.name+"'></span>";
		</s:if>
		<s:if test="#session.RoleInfoPri.DEL>0">
			editStr += "<span class='button remove' id='delBtn_" +treeNode.id+ "' title='"+treeNode.name+"'></span>";
		</s:if>
		<s:if test="#session.RoleInfoPri.WRITE>0">
			editStr += "<span><a href='<%=basePath%>admin/AdminRolePri?id="+treeNode.id+"'><img src='<%=basePath%>image/application_key_small.png'></a></span>";
		</s:if>
		editStr += "</span>";
		aObj.after(editStr);
		<s:if test="#session.RoleInfoPri.ADD>0">
		var add = $("#addBtn_"+treeNode.id);
		if (add) add.click(function(){AddRoleByParentID(treeNode.id,treeNode)});
		</s:if>
		<s:if test="#session.RoleInfoPri.WRITE>0">
		var edit = $("#editBtn_"+treeNode.id);
		if (edit) edit.click(function(){EditRoleNameByID(treeNode.id,treeNode)});
		</s:if>
		<s:if test="#session.RoleInfoPri.DEL>0">
		var del = $("#delBtn_"+treeNode.id);
		if (del) del.click(function(){DelRoleById(treeNode.id,treeNode)});
		</s:if>
	}
	
	$(document).ready(function(){
		$.fn.zTree.init($("#role"), setting, zNodes);
		$("#block").css({"width":$(document.body)[0].clientWidth+"px","height":$(document.body)[0].clientHeight+"px"});
	});
	
	<s:if test="#session.RoleInfoPri.ADD>0">
	function AddRoleByParentID(id,treeNode){
		$.ajax({
			type : 'POST',
			url : "<%=basePath%>admin/AdminRoleAdd",
			data:{
				"parentID":id
			},
			beforeSend :function(){$("#block").css("display","block")},
			success : function(data) {
				$("#block").css("display","none");
				if(data == ""){
					alert('<s:text name="error.db" />');
					$('#block').css("display", "none");
					return;
				}
				var json_data = $.parseJSON(data);
				if (json_data[0].error != "") {
					alert(json_data[0].error);
				} else {
					var zTree = $.fn.zTree.getZTreeObj("role");
					var node = zTree.addNodes(treeNode, {id:json_data[0].content, pId:id, name:""});
					EditRoleNameByID(json_data[0].content,node[0]);
				}
			}
		});
	}
	</s:if>
	<s:if test="#session.RoleInfoPri.WRITE>0">
	function EditRoleNameByID(id,treeNode){
		var zTree = $.fn.zTree.getZTreeObj("role");
		zTree.editName(treeNode);
	}
	
	function beforeRename(treeId, treeNode, newName) {
		className = (className === "dark" ? "":"dark");
		if (newName.length == 0) {
			alert("<s:text name='prompt.role_name'/>");
			var zTree = $.fn.zTree.getZTreeObj("role");
			setTimeout(function(){zTree.editName(treeNode)}, 10);
			return false;
		}
		return true;
	}
	
	function onRename(e, treeId, treeNode) {
		$.ajax({
			type : 'POST',
			url : "<%=basePath%>admin/AdminRoleRename",
			data:{
				"id":treeNode.id,
				"role_name":treeNode.name
			},
			beforeSend :function(){$("#block").css("display","block")},
			success : function(data) {
				$("#block").css("display","none");
				if(data == ""){
					alert('<s:text name="error.db" />');
					$('#block').css("display", "none");
					return;
				}
				var json_data = $.parseJSON(data);
				if (json_data[0].error != "") {
					alert(json_data[0].error);
				}
			}
		});
	}
	
	function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
		var parent_id = 0;
		if(treeNodes[0].pId != null){
			parent_id = treeNodes[0].pId;
		}
		$.ajax({
			type : 'POST',
			url : "<%=basePath%>admin/AdminRoleUpdateParent",
			data:{
				"id":treeNodes[0].id,
				"parentID":parent_id
			},
			beforeSend :function(){$("#block").css("display","block")},
			success : function(data) {
				$("#block").css("display","none");
				if(data == ""){
					alert('<s:text name="error.db" />');
					$('#block').css("display", "none");
					return;
				}
				var json_data = $.parseJSON(data);
				if (json_data[0].error != "") {
					alert(json_data[0].error);
				}
			}
		});
	}
	</s:if>
	
	<s:if test="#session.RoleInfoPri.DEL>0">
	function DelRoleById(id,treeNode){
		var zTree = $.fn.zTree.getZTreeObj("role");
		zTree.selectNode(treeNode);
		zTree.removeNode(treeNode, true);
	}
	
	function beforeRemove(treeId, treeNode) {
		className = (className === "dark" ? "":"dark");
		var zTree = $.fn.zTree.getZTreeObj("role");
		zTree.selectNode(treeNode);
		return confirm(printf("<s:text name='prompt.delete.confirm'/>",treeNode.name));
	}
	
	function onRemove(e, treeId, treeNode) {
		$.ajax({
			type : 'POST',
			url : "<%=basePath%>admin/AdminRoleDelete",
			data:{
				"id":treeNode.id
			},
			beforeSend :function(){$("#block").css("display","block")},
			success : function(data) {
				$("#block").css("display","none");
				if(data == ""){
					alert('<s:text name="error.db" />');
					$('#block').css("display", "none");
					return;
				}
				var json_data = $.parseJSON(data);
				if (json_data[0].error != "") {
					alert(json_data[0].error);
				}
			}
		});
	}
	</s:if>
	
	<s:if test="#session.RoleInfoPri.ADD>0">
	function AddRole(){
		CreatePopDiv("AddRole","50%","30%","role_edit.jsp");
	}
	</s:if>
	</script>
</body>
</html>