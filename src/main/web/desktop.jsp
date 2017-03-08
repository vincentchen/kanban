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
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/default.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/jquery.masonry.min.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:100%;">
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div id="container" style="position:relative;margin:0 auto;">
		<s:if test="#request.Config.TODO>0 && 1==#session.ClientProPri.READ">
			<div class="item boxshadow">
				<input type="hidden" id="TODO_Length" value="<s:property value="#request.Config.TODO" />">
				<div style="height:32px;line-height:32px;border-bottom:1px solid #E5E5E5">
					<ul>
						<li style="text-align:left;"><img alt="" src="<%=basePath%>image/flag.png"></li>
						<li><s:text name="desktop.todo" />
						</li>
						<li style="float:right;">
							<ul>
								<li>
									<div style="height:24px;line-height:24px;margin-top: 1px;">
										<input id="TODO_page" type="text" name="" value="0" style="width:20px;text-align:center;height:18px;" onclick="this.select()" onblur="LoadListByPluginName('TODO',0);" />
									</div></li>
								<li>
									<div class="previous">
										<ul>
											<li><a href="javascript:void(0);" onclick="LoadListByPluginName('TODO',-1);"><img src="<%=basePath%>image/left.png" /></a></li>
											<li><a href="javascript:void(0);" onclick="LoadListByPluginName('TODO',1);"><img src="<%=basePath%>image/right.png" /></a></li>
										</ul>
									</div></li>
							</ul></li>
					</ul>
				</div>
				<div>
					<table class="gTab" id='TODO_Tab'>
						<tr>
							<td style="width:20px;text-align:center">ID</td>
							<td style="width:40px;text-align:center"><s:text name="msg.urgency" /></td>
							<td style="text-align:center"><s:text name="task.content" /></td>
							<td style="width:30px;text-align:center"><s:text name="msg.operation" /></td>
						</tr>
						<s:iterator var="counter" begin="1" end="#request.Config.TODO" status='st'>
							<s:if test="null==#request.TODO">
								<tr>
									<td style="width:20px">&nbsp;</td>
									<td style="width:40px">&nbsp;</td>
									<td>&nbsp;</td>
									<td style="width:30px">&nbsp;</td>
								</tr>
							</s:if>
							<s:else>
								<s:if test="#request.TODO.size()>#st.index">
									<tr>
										<td style="width:20px">#<s:property value="#request.TODO[#st.index][0]" /></td>
										<td style="width:40px"><img src="<%=basePath%>image/lv<s:property value="#request.TODO[#st.index][10]" />.png" />
										</td>
										<td style="text-align:left;">
											<input type="hidden" id="TODO_<s:property value="#st.index" />" value="<s:property value="#Global.RegisterID('ProTask',#request.TODO[#st.index][0])"/>">
											<a href="javascript:void(0);" onclick="OpenTodoPanel(<s:property value="#st.index" />);">
												<s:if test="#request.TODO[#st.index][2].length()>50"><s:property value="#request.TODO[#st.index][2].substring(0,50)+'...'" /></s:if>
												<s:else>
													<s:property value="#request.TODO[#st.index][2]" />
												</s:else>
											</a>
										</td>
										<td style="width:30px"><a href="javascript:void(0);" onclick="checkout(<s:property value="#st.index" />);"><img src="<%=basePath%>image/checkout.png">
										</a></td>
									</tr>
								</s:if>
								<s:else>
									<tr>
										<td style="width:20px">&nbsp;</td>
										<td style="width:40px">&nbsp;</td>
										<td>&nbsp;</td>
										<td style="width:30px">&nbsp;</td>
									</tr>
								</s:else>
							</s:else>
						</s:iterator>
					</table>
				</div>
			</div>
			<script type="text/javascript">
			function checkout(id){
				$.ajax({
					type:"POST",
					url:"ProTaskReceive",
					data:{
						"id":$("#TODO_"+id).val()
					},
					success : function checkoutOK(data) {
						if(data == ""){
							alert('<s:text name="error.db" />');
							return;
						}
						var json_data = $.parseJSON(data);
						if (json_data[0].error != "") {
							alert(json_data[0].error);	
						}else{
							window.location.reload();
						}
					}
				});
			}
			
			function OpenTodoPanel(id) {
				window.location.href = "ProTaskView?id="+$("#TODO_"+id).val();
			}
			
			</script>
		</s:if>
		<s:if test="#request.Config.DOING>0 && 1==#session.ClientProPri.READ">
			<div class="item boxshadow">
				<input type="hidden" id="DOING_Length" value="<s:property value="#request.Config.DOING" />">
				<div style="height:32px;line-height:32px;border-bottom:1px solid #E5E5E5">
					<ul>
						<li style="text-align:left;"><img alt="" src="<%=basePath%>image/alarm.png"></li>
						<li><s:text name="desktop.doing" />
						</li>
						<li style="float:right;">
							<ul>
								<li>
									<div style="height:24px;line-height:24px;margin-top: 1px;">
										<input id="DOING_page" type="text" name="" value="0" style="width:20px;text-align:center;height:18px;" onclick="this.select()" onblur="LoadListByPluginName('DOING',0);" />
									</div></li>
								<li>
									<div class="previous">
										<ul>
											<li><a href="javascript:void(0);" onclick="LoadListByPluginName('DOING',-1);"><img src="<%=basePath%>image/left.png" /></a></li>
											<li><a href="javascript:void(0);" onclick="LoadListByPluginName('DOING',1);"><img src="<%=basePath%>image/right.png" /></a></li>
										</ul>
									</div></li>
							</ul></li>
					</ul>
				</div>
				<div>
					<table class="gTab" id="DOING_Tab">
						<tr>
							<td style="width:20px;text-align:center">ID</td>
							<td style="text-align:center"><s:text name="task.content" /></td>
							<td style="width:60px;text-align:center"><s:text name="task.endtime" />
							</td>
							<td style="width:30px;text-align:center"><s:text name="msg.operation" />
							</td>
						</tr>
						<s:iterator var="counter" begin="1" end="#request.Config.DOING" status='st'>
							<s:if test="null==#request.DOING">
								<tr>
									<td style="width:20px;">&nbsp;</td>
									<td>&nbsp;</td>
									<td style="width:60px;">&nbsp;</td>
									<td style="width:30px">&nbsp;</td>
								</tr>
							</s:if>
							<s:else>
								<s:if test="#request.DOING.size()>#st.index">
									<tr>
										<td>#<s:property value="#request.DOING[#st.index][0]" /></td>
										<td style="text-align:left;"><input type="hidden" id="DOING_<s:property value="#st.index" />" value="<s:property value="#Global.RegisterID('ProTask',#request.DOING[#st.index][0])"/>">
										<a href="javascript:void(0);" onclick="OpenDoingPanel(<s:property value="#st.index" />);">
										<s:if test="#request.DOING[#st.index][2].length()>50"><s:property value="#request.DOING[#st.index][2].substring(0,50)+'...'" /></s:if>
										<s:else>
											<s:property value="#request.DOING[#st.index][2]" />
										</s:else>
										</a>
										</td>
										<td>
										<s:if test="#Global.getDayIsToday(#request.DOING[#st.index][7])">
											<s:text name="today" /><s:date name="#request.DOING[#st.index][7]" format="HH" /><s:text name="before"/>
										</s:if>
										<s:else>
											<s:date name="#request.DOING[#st.index][7]" format="yyyy-MM-dd" />
										</s:else>
										</td>
										<td><a href="javascript:void(0);" onclick="checkin(<s:property value="#st.index" />);"><img src="<%=basePath%>image/checkin.png"></a></td>
									</tr>
								</s:if>
								<s:else>
									<tr>
										<td style="width:20px;">&nbsp;</td>
										<td>&nbsp;</td>
										<td style="width:60px;">&nbsp;</td>
										<td style="width:30px">&nbsp;</td>
									</tr>
								</s:else>
							</s:else>
						</s:iterator>
					</table>
				</div>
			</div>
			<script type="text/javascript">
			function checkin(id){
				$.ajax({
					type:"POST",
					url:"ProTaskFinish",
					data:{
						"id":$("#DOING_"+id).val()
					},
					success : function checkinOK(data) {
						if(data == ""){
							alert('<s:text name="error.db" />');
							return;
						}
						var json_data = $.parseJSON(data);
						if (json_data[0].error != "") {
							alert(json_data[0].error);	
						}else{
							window.location.reload();
						}
					}
				});
			}
			
			function OpenDoingPanel(id){
				window.location.href = "ProTaskView?id="+$("#DOING_"+id).val();
			}
			</script>
		</s:if>
		<s:if test="#request.Config.PRO>0 && 1==#session.ClientProPri.READ">
			<div class="item boxshadow">
				<input type="hidden" id="PRO_Length" value="<s:property value="#request.Config.PRO" />">
				<div style="height:32px;line-height:32px;border-bottom:1px solid #E5E5E5">
					<ul>
						<li style="text-align:left;"><img alt="" src="<%=basePath%>image/library.png"></li>
						<li><s:text name="desktop.pro" />
						</li>
						<li style="float:right;">
							<ul>
								<li>
									<div style="height:24px;line-height:24px;margin-top: 1px;">
										<input id="PRO_page" type="text" name="" value="0" style="width:20px;text-align:center;height:18px;" onclick="this.select()" onblur="LoadListByPluginName('PRO',0);" />
									</div></li>
								<li>
									<div class="previous">
										<ul>
											<li><a href="javascript:void(0);" onclick="LoadListByPluginName('PRO',-1);"><img src="<%=basePath%>image/left.png" /></a></li>
											<li><a href="javascript:void(0);" onclick="LoadListByPluginName('PRO',1);"><img src="<%=basePath%>image/right.png" /></a></li>
										</ul>
									</div></li>
							</ul></li>
					</ul>
				</div>
				<div>
					<table class="gTab" id="PRO_Tab">
						<tr>
							<td style="text-align:center"><s:text name="pro.name" /></td>
							<td style="width:100px;text-align:center"><s:text name="protask.status.todo" />/<s:text name="protask.status.doing" />/<s:text name="protask.status.done" /></td>
						</tr>
						<s:iterator var="counter" begin="1" end="#request.Config.PRO" status='st'>
							<s:if test="null==#request.PRO">
								<tr>
									<td>&nbsp;</td>
									<td style="text-align:center">&nbsp;</td>
								</tr>
							</s:if>
							<s:else>
								<s:if test="#request.PRO.size()>#st.index">
									<tr>
										<td><a href="ProjectView?id=<s:property value="#Global.RegisterID('Project',#request.PRO[#st.index].get('ID'))"/>">
										<s:if test="#request.PRO[#st.index].get('NAME').length()>50"><s:property value="#request.PRO[#st.index].get('NAME').substring(0,50)+'...'" /></s:if>
										<s:else>
											<s:property value="#request.PRO[#st.index].get('NAME')" />
										</s:else> </a>
										</td>
										<td style="text-align:center"><s:property value="#request.PRO[#st.index].get('TODO')" />/<s:property value="#request.PRO[#st.index].get('DOING')" />/<s:property value="#request.PRO[#st.index].get('DONE')" /></td>
									</tr>
								</s:if>
								<s:else>
									<tr>
										<td>&nbsp;</td>
										<td style="text-align:center">&nbsp;</td>
									</tr>
								</s:else>
							</s:else>
						</s:iterator>
					</table>
				</div>
			</div>
		</s:if>
		<s:if test="#request.Config.IDEA>0 && 1==#session.ClientIdeaPri.READ">
		<div class="item boxshadow">
			<input type="hidden" id="IDEA_Length" value="<s:property value="#request.Config.IDEA" />">
			<div style="height:32px;line-height:32px;border-bottom:1px solid #E5E5E5">
				<ul>
					<li style="text-align:left;"><img alt="" src="<%=basePath%>image/lamp.png"></li>
					<li><s:text name="desktop.idea" />
					</li>
					<li style="float:right;">
						<ul>
							<li>
								<div style="height:24px;line-height:24px;margin-top: 1px;">
									<input type="text" id="IDEA_page" name="" value="0" style="width:20px;text-align:center;height:18px;" onclick="this.select()" onblur="LoadListByPluginName('IDEA',0);" />
								</div></li>
							<li>
								<div class="previous">
									<ul>
										<li><a href="javascript:void(0);" onclick="LoadListByPluginName('IDEA',-1);"><img src="<%=basePath%>image/left.png" /></a></li>
										<li><a href="javascript:void(0);" onclick="LoadListByPluginName('IDEA',1);"><img src="<%=basePath%>image/right.png" /></a></li>
									</ul>
								</div></li>
						</ul></li>
				</ul>
			</div>
			<div>
				<table class="gTab" id="IDEA_Tab">
					<tr>
						<td style="text-align:center">
							<s:text name="msg.idea.title" />
						</td>
						<td style="width:60px;text-align:center">
							<s:text name="msg.operation" />
						</td>
					</tr>
					<s:iterator var="counter" begin="1" end="#request.Config.IDEA" status='st'>
						<s:if test="null==#request.IDEA">
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						</s:if>
						<s:else>
							<s:if test="#request.IDEA.size()>#st.index">
								<tr>
									<td><input type="hidden" id="IDEA_<s:property value="#st.index" />" value="<s:property value="#Global.RegisterID('Idea',#request.IDEA[#st.index][0])" />" />
									<a href="javascript:void(0);" onclick="OpenIdea(<s:property value="#st.index" />);">
									<s:if test="#request.IDEA[#st.index][1].length()>50"><s:property value="#request.IDEA[#st.index][1].substring(0,50)+'...'" /></s:if>
									<s:else>
										<s:property value="#request.IDEA[#st.index][1]" />
									</s:else>
									</a>
									</td>
									<td><a href="javascript:void(0);" onclick="ChangeVote(<s:property value="#st.index" />,1);"><img src="<%=basePath%>image/good.png"></a>&nbsp;<a href="javascript:void(0);" onclick="ChangeVote(<s:property value="#st.index" />,2);"><img src="<%=basePath%>image/bad.png"></a></td>
								</tr>
							</s:if>
							<s:else>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</s:else>
						</s:else>
					</s:iterator>
				</table>
			</div>
		</div>
		<script type="text/javascript">
		function OpenIdea(rowID){
			CreateCrossPopDIV(window,"IdeaPanel","70%","70%","IdeaView?id="+$("#IDEA_"+rowID).val());
		}
		
		function ChangeVote(rowID,choose){
			var id = $("#IDEA_"+rowID).val();
			$.ajax({
				url:"IdeaSaveVoteByUser",
				type:"POST",
				data:"id="+id+"&choose="+choose,
				success:function VoteOk(data){
					if(data == ""){
						alert('<s:text name="error.db" />');
						return;
					}
					if(data == "ok"){
						window.location.reload(true);
					}else{
						alert(data);
					}
				}
			});
		}
		</script>
		</s:if>
		<s:if test="#request.Config.MSG>0">
		<div class="item boxshadow">
			<input type="hidden" id="MSG_Length" value="<s:property value="#request.Config.MSG" />">
			<div style="height:32px;line-height:32px;border-bottom:1px solid #E5E5E5">
				<ul>
					<li style="text-align:left;"><img alt="" src="<%=basePath%>image/newspaper.png"></li>
					<li><s:text name="desktop.msg" />
					</li>
					<li style="float:right;">
						<ul>
							<li>
								<div style="height:24px;line-height:24px;margin-top: 1px;">
									<input id="MSG_page" type="text" name="" value="0" style="width:20px;text-align:center;height:18px;" onclick="this.select()" onblur="LoadListByPluginName('MSG',0);" />
								</div></li>
							<li>
								<div class="previous">
									<ul>
										<li><a href="javascript:void(0);" onclick="LoadListByPluginName('MSG',-1);"><img src="<%=basePath%>image/left.png" /></a></li>
										<li><a href="javascript:void(0);" onclick="LoadListByPluginName('MSG',1);"><img src="<%=basePath%>image/right.png" /></a></li>
									</ul>
								</div></li>
						</ul></li>
				</ul>
			</div>
			<div>
				<table class="gTab" id="MSG_Tab">
					<tr>
						<td style="width:60px;text-align:center">
							<s:text name="msg.src_type" />
						</td>
						<td style="text-align:center">
							<s:text name="msg.content" />
						</td>
					</tr>
					<s:iterator var="counter" begin="1" end="#request.Config.MSG" status='st'>
					<s:if test="null==#request.MSG">
						<tr>
							<td style="width:60px;">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						</s:if>
						<s:else>
							<s:if test="#request.MSG.size()>#st.index">
								<tr>
									<td style="width:60px;"><input type="hidden" id="MSG_<s:property value="#st.index" />" value="<s:property value="#Global.RegisterID('Msg',#request.MSG[#st.index].msg_id)" />" /><s:property value="#request.MSG[#st.index].src_type_name" />
									</td>
									<td><a href="javascript:void(0);" onclick="OpenMsg(<s:property value="#st.index" />);">
									<s:if test="#request.MSG[#st.index].msg_title.length()>50"><s:property value="#request.MSG[#st.index].msg_title.substring(0,50)+'...'" /></s:if>
									<s:else>
										<s:property value="#request.MSG[#st.index].msg_title" />
									</s:else>
									</a></td>
								</tr>
							</s:if>
							<s:else>
								<tr>
									<td style="width:60px;">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</s:else>
						</s:else>
					</s:iterator>
				</table>
			</div>
		</div>
		</s:if>
		<s:if test="#request.Config.HELP>0 && 1==#session.ClientHelpPri.READ">
		<div class="item boxshadow">
			<input type="hidden" id="HELP_Length" value="<s:property value="#request.Config.HELP" />">
			<div style="height:32px;line-height:32px;border-bottom:1px solid #E5E5E5">
				<ul>
					<li style="text-align:left;"><img alt="" src="<%=basePath%>image/help.png"></li>
					<li><s:text name="desktop.help" />
					</li>
					<li style="float:right;">
						<ul>
							<li>
								<div style="height:24px;line-height:24px;margin-top: 1px;">
									<input id="HELP_page" type="text" name="" value="0" style="width:20px;text-align:center;height:18px;" onclick="this.select()" onblur="LoadListByPluginName('HELP',0);" />
								</div></li>
							<li>
								<div class="previous">
									<ul>
										<li><a href="javascript:void(0);" onclick="LoadListByPluginName('HELP',-1);"><img src="<%=basePath%>image/left.png" /></a></li>
										<li><a href="javascript:void(0);" onclick="LoadListByPluginName('HELP',1);"><img src="<%=basePath%>image/right.png" /></a></li>
									</ul>
								</div></li>
						</ul></li>
				</ul>
			</div>
			<div>
				<table class="gTab" id="HELP_Tab">
					<tr>
						<td style="width:80px;text-align:center">
							<s:text name="msg.help.user_name" />
						</td>
						<td style="text-align:center">
							<s:text name="msg.help.title" />
						</td>
						<td style="width:30px;text-align:center"><s:text name="msg.operation" />
						</td>
					</tr>
					<s:iterator var="counter" begin="1" end="#request.Config.HELP" status='st'>
						<s:if test="null==#request.HELP">
						<tr>
							<td style="width:60px;">&nbsp;</td>
							<td>&nbsp;</td>
							<td></td>
						</tr>
						</s:if>
						<s:else>
							<s:if test="#request.HELP.size()>#st.index">
								<tr>
									<td><input type="hidden" id="HELP_<s:property value="#st.index" />" value="<s:property value="#Global.RegisterID('Help',#request.HELP[#st.index].request_id)" />" />
									<s:property value="#request.HELP[#st.index].seek_help_user_name" />
									</td>
									<td><a href="javascript:void(0);" onclick="OpenHelp(<s:property value="#st.index" />);">
									<s:if test="#request.HELP[#st.index].help_title.length()>50"><s:property value="#request.HELP[#st.index].help_title.substring(0,50)+'...'" /></s:if>
									<s:else>
										<s:property value="#request.HELP[#st.index].help_title" />
									</s:else></a></td>
									<td><a href="javascript:void(0);" onclick="HelpAccept(<s:property value="#st.index" />);"><img src="<%=basePath%>image/group_go.png"></a></td>
								</tr>
							</s:if>
							<s:else>
								<tr>
									<td style="width:60px;">&nbsp;</td>
									<td>&nbsp;</td>
								</tr>
							</s:else>
						</s:else>
					</s:iterator>
				</table>
			</div>
		</div>
		<script type="text/javascript">
			function HelpAccept(id){
				$.ajax({
					type:"POST",
					url:"HelpUpdateAccept",
					data:{
						"id":$("#HELP_"+id).val()
					},
					success : function(data) {
						if (data != "ok") {
							alert(data);
						}else{
							window.location.reload(true);
						}
					}
				});
			}
			
			function OpenHelp(id){
				window.location.href = "HelpView?id="+$("#HELP_"+id).val();
			}
			</script>
		</s:if>
	</div>
	<script type="text/javascript">
		var ajax = null;
		
		$(function() {
			var $container = $('#container');
			$container.imagesLoaded(function() {
				$container.masonry({
					itemSelector : '.item'
				});
			});
		});
		
		function LoadingStart(pluName){
			if(document.getElementById(pluName+"LoadingDiv")){
				var tab_location = GetAbsoluteLocation(document.getElementById(pluName+"_Tab"));
			}else{
				var tab_location = GetAbsoluteLocation(document.getElementById(pluName+"_Tab"));
				
				var div = document.createElement("div");
				div.id = pluName+"LoadingDiv";
				div.className = "desktop_loading_div";
				div.style.width = tab_location.offsetWidth+"px";
				div.style.height = "0px";
				div.style.display = "none";
				div.style.top = tab_location.absoluteTop+1+"px";
				div.style.left = tab_location.absoluteLeft+"px";
				document.body.appendChild(div);
				div.innerHTML = "<img src='<%=basePath%>image/loading.gif' style='position:absolute;top:50%;left:50%;margin-left:-16px;margin-top:-16px;' />";
			}
			$("#"+pluName+"LoadingDiv").animate({ height: tab_location.offsetHeight+"px", opacity: 'show' }, 'fast');
		} 
		
		function LoadingEnd(pluName){
			$("#"+pluName+"LoadingDiv").animate({ height: '0px', opacity: 'hide' }, 'fast');
		}
		
		function LoadListByPluginName(pluName,pageGo){
			if(pageGo==1){
				var plu_length = $("#"+pluName+"_Length").val();
				var tr_length = $("#"+pluName+"_Tab tr").length -1;
				if(plu_length != tr_length){
					alert('<s:text name="prompt.page.end" />');
					return;
				}
			}
			if(pageGo==-1){
				if($("#"+pluName+"_page").val()==0){
					return;
				}
			}
			
			var url = "DeskTop"+pluName+"ByAjaxList";
			var current_page = parseInt($("#"+pluName+"_page").val())+pageGo;
			var page_size = $("#"+pluName+"_Length").val();
			LoadingStart(pluName);
			$("#"+pluName+"_page").val(current_page);
			ajax = $.ajax({
				type:"POST",
				url:url,
				data:{
					"currentPage":current_page,
					"pageSize":page_size
				},
				success : function(data) {
					if(data == ""){
						alert('<s:text name="error.db" />');
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);	
					}else{
						RestTab(pluName,json_data[0].content);
						LoadingEnd(pluName);
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown) {
	                    alert(XMLHttpRequest.status);
	                    alert(XMLHttpRequest.readyState);
	                    alert(textStatus);
                }
			});
		}
		
		function RestTab(pluName,objList){
			$("#"+pluName+"_Tab tr:not(:first)").remove();
			eval("Append"+pluName+"Tr(objList);");
		}
		
		
		function AppendTODOTr(objList){
			var tr_Str = "";
			for(var i=0;i<objList.length;i++){
				var tr_Str = "<tr><td>#"+objList[i][0]+"</td>";
				tr_Str += "<td><img src='<%=basePath%>image/lv"+objList[i][2]+".png' /></td>";
				tr_Str += "<td><input type='hidden' id='TODO_"+i+"' value='"+objList[i][3]+"' ><a href='javascript:void(0);' onclick='OpenTodoPanel("+i+");'>"+objList[i][1]+"</a></td>";
				tr_Str += "<td><a href='javascript:void(0);' onclick='checkout("+i+");'><img src='<%=basePath%>image/checkout.png'></a></td>";
				tr_Str += "</tr>";
				$("#TODO_Tab").append(tr_Str);
			}
		}
		
		function AppendDOINGTr(objList){
			var tr_Str = "";
			for(var i=0;i<objList.length;i++){
				tr_Str = "<tr><td>#"+objList[i][0]+"</td>";
				tr_Str += "<td><input type='hidden' id='DOING_"+i+"' value='"+objList[i][3]+"' ><a href='javascript:void(0);' onclick='OpenTodoPanel("+i+");'>"+objList[i][1]+"</a></td>";
				tr_Str += "<td>"+objList[i][2]+"</td>";
				tr_Str += "<td><a href='javascript:void(0);' onclick='checkin("+i+");'><img src='<%=basePath%>image/checkin.png'></a></td>";
				tr_Str += "</tr>";
				$("#DOING_Tab").append(tr_Str);
			}
		}
		
		function AppendPROTr(objList){
			var tr_Str = "";
			for(var i=0;i<objList.length;i++){
				tr_Str = "<tr>";
				tr_Str += "<td><a href='ProjectView?id="+objList[i][0]+"'>"+objList[i][1]+"</a></td>";
				tr_Str += "<td style='text-align:center'>"+objList[i][2]+"/"+objList[i][3]+"/"+objList[i][4]+"</td>";
				tr_Str += "</tr>";
				$("#PRO_Tab").append(tr_Str);
			}
		}
		
		function AppendHELPTr(objList){
			var tr_Str = "";
			for(var i=0;i<objList.length;i++){
				tr_Str = "<tr>";
				tr_Str += "<td><input type='hidden' id='HELP_"+i+"' value='"+objList[i][0]+"' />"+objList[i][2]+"</td>";
				tr_Str += "<td><a href='javascript:void(0);' onclick='OpenHelp("+i+");'>"+objList[i][1]+"</a></td>";
				tr_Str += "<td><a href='javascript:void(0);' onclick='HelpAccept("+i+");'><img src='<%=basePath%>image/group_go.png'></a></td>";
				tr_Str += "</tr>";
				$("#HELP_Tab").append(tr_Str);
			}
		}
		
		function AppendIDEATr(objList){
			var tr_Str = "";
			for(var i=0;i<objList.length;i++){
				tr_Str = "<tr>";
				tr_Str += "<td><input type='hidden' id='IDEA_"+i+"' value='"+objList[i][0]+"' /><a href='javascript:void(0);' onclick='OpenIdea("+i+");'>"+objList[i][1]+"</a></td>";
				tr_Str += "<td><a href='javascript:void(0);' onclick='ChangeVote("+i+",1);'><img src='<%=basePath%>image/good.png'></a>&nbsp;<a href='javascript:void(0);' onclick='ChangeVote("+i+",2);'><img src='<%=basePath%>image/bad.png'></a></td>";
				tr_Str += "</tr>";
				$("#IDEA_Tab").append(tr_Str);
			}
		}
		
		function AppendMSGTr(objList){
			var tr_Str = "";
			for(var i=0;i<objList.length;i++){
				tr_Str = "<tr>";
				tr_Str += "<td><input type='hidden' id='MSG_"+i+"' value='"+objList[i][0]+"' />"+objList[i][1]+"</td>";
				tr_Str += "<td><a href='javascript:void(0);' onclick='OpenMsg("+i+")'>"+objList[i][2]+"</a></td>";
				tr_Str += "</tr>";
				$("#MSG_Tab").append(tr_Str);
			}
		}
		
		function OpenMsg(rowID){
			var msgID = $("#MSG_"+rowID).val();
			$.ajax({
				type:"POST",
				url:"MsgRedirect",
				data:{
					"id":msgID
				},
				success:function(data){
					if(data == ""){
						alert('<s:text name="error.db" />');
						$('#block').css("display", "none");
						return;
					}
					var json_data = $.parseJSON(data);
					if (json_data[0].error != "") {
						alert(json_data[0].error);
					}else{
						window.location.href = json_data[0].content;
					}
				}
			});
		}
	</script>
</body>
</html>