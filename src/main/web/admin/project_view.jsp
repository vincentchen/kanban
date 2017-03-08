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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/radio.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/jquery.masonry.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:98%;">
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div class="boxradius" style="margin:0px auto;width:98%;height:100%;text-align:left;">
		<input id="ProID" type="hidden" value="<s:property value="#Global.RegisterID('Project',#request.Project.pro_id)" />">
		<div style="width:100%;">
			<table class="gTab">
				<tr>
					<td style="width:30px;"><a href="javascript:void(0);" onclick="javascript:window.location.href='<%=basePath%>admin/AdminProjectList?search=1';"><img alt="" src="<%=basePath%>image/left.png">
					</a></td>
					<td style="text-indent: 2em;">
						<h1>
							<s:property value="#request.Project.pro_name" />
						</h1>
					</td>
					<td style="width:120px;"><s:text name="time.start" />:<s:date name="#request.Project.start_time" format="yyyy-MM-dd" /><br /> <s:text name="time.end" />:<s:date name="#request.Project.end_time" format="yyyy-MM-dd"/>
					</td>
					<td style="width:450px;">
						<table style="width:450px;">
							<tr>
								<td>
								<s:if test="#session.ProPri.WRITE>0">
								<a href="javascript:void(0);" onclick="ChangeProStatus();"><s:if test="#request.Project.pro_status==0"><img src="<%=basePath%>image/play.png"><s:text name="pro.status.start" /></s:if><s:elseif test="#request.Project.pro_status==100"><img src="<%=basePath%>image/stop.png"><s:text name="pro.status.end" /></s:elseif><s:elseif test="#request.Project.pro_status==200"><img src="<%=basePath%>image/refresh.png"><s:text name="pro.status.refresh" /></s:elseif></a>
								</s:if>
								</td>
								<td>
								<s:if test="#session.ProPri.WRITE>0">
								<a href="javascript:void(0);" onclick="OpenDescription();"><img src="<%=basePath%>image/clipboard.png" /><s:text name="description" /></a>
								</s:if>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenRelUsers();"><img src="<%=basePath%>image/customers.png" /><s:text name="rel.users" /></a>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenDownload();"><img src="<%=basePath%>image/download2.png" /><s:text name="pro.download" /></a>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenGraph();"><img alt="" src="<%=basePath%>image/graph.png"><s:text name="graph" /></a>
								</td>
								<td><a href="javascript:void(0);" onclick="OpenHistory();"><img alt="" src="<%=basePath%>image/history.png"><s:text name="history" /></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div style="height:54px;width:100%;border-top:1px solid #E5E5E5;border-bottom:1px solid #E5E5E5;">
			<table style="width:98%;margin:0 auto;">
				<tr style="height:54px;">
					<td style="width:60px;">
						<a href="javascript:void(0);" onclick="ChangeReadTargetStatus(true);"><s:text name="unfinished" /></a>
					</td>
					<td style="width:60px;">
						<span class="toggle-bg">
							<input type="radio" id="unfinished" name="toggle" value="off" onclick="ChangeReadTargetStatus(true);" />
							<input type="radio" id="finished" name="toggle" value="on" onclick="ChangeReadTargetStatus(false);" <s:if test="#request.Info.targetStatus!=null">checked</s:if> />
							<span class="switch" />
						</span>
					</td>
					<td style="width:60px;">
						<a href="javascript:void(0);" onclick="ChangeReadTargetStatus(false);"><s:text name="finished" /></a>
					</td>
					<td style="text-align:right;">
						&nbsp;
						<s:if test="#session.ProPri.WRITE>0">
						<a href="javascript:void(0);" onclick="CreateTarget();"><img src="<%=basePath%>image/add_small.png"><s:text name="target.add" /></a>
						</s:if>
					</td>
				</tr>
			</table>
		</div>
		<div id="con" style="position:relative;margin:0 auto;width:98%;">
			<s:iterator value="#request.Target" status="st">
				<div class="item boxradius" style="width:32%;margin:5px auto;">
					<input type="hidden" id="TargetID_<s:property value="#st.index" />" value="<s:property value="#Global.RegisterID('ProTarget',#request.Target[#st.index][0])" />">
					<table class="gTab">
						<tr>
							<td>
								<h2>
									<s:property value="#request.Target[#st.index][2]" />
								</h2>
							</td>
							<td style="width:140px;">
								<s:date name="#request.Target[#st.index][4]" format="yyyy-MM-dd"/> - <s:date name="#request.Target[#st.index][5]" format="yyyy-MM-dd"/><br />
								<s:property value="#request.Target[#st.index][6]" /><s:text name="day" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<s:property escape="false" value="#request.Target[#st.index][3]" />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<a href="javascript:void(0);" onclick="OpenTarget(<s:property value="#st.index" />);"><img src="<%=basePath%>image/application_go.png"></a>
								<s:if test="#session.ProPri.WRITE>0">
									<a href="javascript:void(0);" onclick="OpenDetail(<s:property value="#st.index" />);"><img src="<%=basePath%>image/detail.png"></a>
								</s:if>
								<a href="javascript:void(0);" onclick="OpenAttach(<s:property value="#st.index" />);"><img src="<%=basePath%>image/attach.png"></a>
								<s:if test="#session.ProPri.WRITE>0">
									<a href="javascript:void(0);" onclick="ChangeTargetStatus(<s:property value="#st.index" />);"><s:if test="#request.Target[#st.index][7] == 0"><img alt="" src="<%=basePath%>image/next_one.png"></s:if><s:elseif test="#request.Target[#st.index][7] == 100"><img alt="" src="<%=basePath%>image/stop_small.png"></s:elseif><s:elseif test="#request.Target[#st.index][7] == 200"><img alt="" src="<%=basePath%>image/arrow_refresh.png"></s:elseif></a>
								</s:if>
								<a href="javascript:void(0);" onclick="TargetGraph(<s:property value="#st.index" />);"><img src="<%=basePath%>image/graph_small.png"></a>
							</td>
						</tr>
					</table>
				</div>
			</s:iterator>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			var $container = $('#con');
			$container.imagesLoaded(function() {
				$container.masonry({
					// 每一列数据的宽度，若所有栏目块的宽度相同，可以不填这段
					// .imgbox 为各个图片的容器
					itemSelector : '.item',
					gutterWidth:2
				});
			});
		});
		
		<s:if test="#session.ProPri.WRITE>0">
		function OpenDescription(){
			CreatePopDiv("Description","70%","70%","<%=basePath%>admin/AdminProjectEdit?id="+$("#ProID").val());
		}
		
		function ChangeReadTargetStatus(status){
			if(status){
				$("#finished").attr("checked","checked");
				window.location.href='<%=basePath%>admin/AdminProjectView?id='+$('#ProID').val();
			}else{
				$("#finished").removeAttr("checked");
				$("#unfinished").attr("checked","checked");
				window.location.href='<%=basePath%>admin/AdminProjectView?targetStatus=history&id='+$('#ProID').val();
			}
		}
		
		
		function OpenRelUsers(){
			CreatePopDiv("OpenRelUsers","95%","85%","<%=basePath%>admin/AdminProjectUsers?id="+$('#ProID').val());
		}
		
		function ChangeProStatus(){
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminProjectChangeProStatus",
				data:{
					"id":$("#ProID").val()
				},
				success:function ChangeProStatusOK(data){
					if(data == "ok"){
						window.location.reload(true);
					}else{
						alert(data);
					}
				}
			});
		}
		</s:if>
		
		function OpenDownload(){
			CreatePopDiv("ProjectDownload","70%","70%","<%=basePath%>admin/AdminProjectDownload?id="+$("#ProID").val());
		}
		
		function OpenGraph(){
			CreatePopDiv("OpenGraph","85%","85%","<%=basePath%>admin/AdminProjectGraph?id="+$("#ProID").val());
		}
		
		function OpenHistory(){
			CreatePopDiv("OpenHistory","85%","85%","<%=basePath%>admin/AdminProjectHistory?id="+$("#ProID").val());
		}
		
		<s:if test="#session.ProPri.WRITE>0">
		function CreateTarget(){
			CreatePopDiv("Protarget","70%","80%","<%=basePath%>admin/AdminProTargetEdit?proID="+$("#ProID").val());
		}
		</s:if>
		function OpenTarget(rowID){
			window.location.href='<%=basePath%>admin/AdminProTargetView?id='+$('#TargetID_'+rowID).val();
		}
		
		<s:if test="#session.ProPri.WRITE>0">
		function OpenDetail(rowID){
			CreatePopDiv("Protarget","70%","70%","<%=basePath%>admin/AdminProTargetEdit?id="+$("#TargetID_"+rowID).val());
		}
		</s:if>
		
		function OpenAttach(rowID){
			CreatePopDiv("TargetDownload","70%","70%","<%=basePath%>admin/AdminProTargetDownload?id="+$("#TargetID_"+rowID).val());
		}
		
		<s:if test="#session.ProPri.WRITE>0">
		function ChangeTargetStatus(rowID){
			$.ajax({
				type:"POST",
				url:"<%=basePath%>admin/AdminProTargetChangeTargetStatus",
				data:{
					"id":$("#TargetID_"+rowID).val()
				},
				success:function ChangeProStatusOK(data){
					if(data == "ok"){
						window.location.reload(true);
					}else{
						alert(data);
					}
				}
			});
		}
		</s:if>
		function TargetGraph(rowID){
			CreatePopDiv("TargetGraph","85%","85%","<%=basePath%>admin/AdminProTargetGraph?id="+$("#TargetID_"+rowID).val());
		}
	</script>
</body>
</html>