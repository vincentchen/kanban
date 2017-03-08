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
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/gantt.css">
<script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath%>js/jquery.flot.js"></script>
<script src="<%=basePath%>js/jquery.fn.gantt.js"></script>
<script src="<%=basePath%>js/common.js"></script>
</head>
<body style="width:100%;height:98%;">
	<s:bean name="com.idealism.kanban.util.Global" var="Global" />
	<div style="margin:3px auto;height:35px;border-bottom:1px solid #E5E5E5;">
		<ul style="width:180px;margin:3px auto;height:31px;list-style-type:none;background-color:#fff">
			<div id="selectBG" class="boxradius" style="background-color:#E5E5E5;position: absolute;z-index:8"></div>
			<a href="javscript:void(0);" onclick="SelectTab('burndown');"><li id="burndownli" class="boxradius" style="float:left;height:23px;z-index:99;position: absolute;padding:3px;">
					<h1 style="padding:0px;">
						<s:text name="burndown" />
					</h1></li> </a>
			<a href="javscript:void(0);" onclick="SelectTab('gantt');"><li id="ganttli" class="boxradius" style="margin-left:82px;float:left;height:23px;z-index:99;position: absolute;padding:3px;">
					<h1 style="padding:0px;">
						<s:text name="gantt" />
					</h1></li> </a>
		</ul>
	</div>
	<div style="margin:5px auto;width:98%;height:90%;overflow: hidden;">
		<ul id="selectTab" style="width:200%;height:98%;list-style-type:none;margin-left:1px;">
			<li  style="width:50%;height:98%;margin:0px;padding:0px;float:left;">
				<div id="burndown" style="width:99%;height:100%;margin:0 auto;overflow:hidden;">
				</div>
			</li>
			<li style="width:50%;height:98%;margin:0px;padding:0px;float:left;">
				<div id="gantt" class="gantt" style="margin:0 auto;overflow:hidden;">
				</div>
			</li>
		</ul>
	</div>
	<script type="text/javascript">
	//----burndown Begin
	var referenceMain = [<s:property value="#request.Graph[1]" />];
	var remainderMain = [<s:property value="#request.Graph[3]" />];
	var datasets = {
	        "reference":{
	        	label:"<s:text name='reference' />",
	        	data:[<s:property value="#request.Graph[2]" />]
	        },
	        "remainder":{
	        	label:"<s:text name='remainder' />",
	        	data:[<s:property value="#request.Graph[4]" />]
	        }
		};
	
	var i = 0;
    $.each(datasets, function(key, val) {
        val.color = i;
        ++i;
    });
    
	function getLocalTime(now) {     
		var   year=now.getFullYear();     
		var   month=now.getMonth()+1;     
        var   date=now.getDate();     
        var   hour=now.getHours();     
        var   minute=now.getMinutes();     
        var   second=now.getSeconds();     
        return   year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second; 
	}
	
	var options = {
		series: {
	    	lines: { show: true },
	    	points: { show: true }
		},
	    xaxis: { mode: "time", timeformat: "%y/%m/%d"  },
		grid: { hoverable: true, clickable: true },
		yaxis: {min: 0 }
	};
		
	function BurndownDraw() {
		var data = [];
		data.push(datasets["reference"]);
		data.push(datasets["remainder"]);
	    if (data.length > 0){
	    	$.plot($("#burndown"), data, options);
		}
	}
		
	function showTooltip(x, y, contents) {
		var client_width = document.documentElement.clientWidth;
		var client_height = document.documentElement.clientHeight;
		var toolTop = 0;
		var toolLeft = 0;
		$('<div id="tooltip">' + contents + '</div>').css( {
	    	position: 'absolute',
	    	display: 'none',
	    	top: toolTop,
	    	left: toolLeft,
	    	border: '1px solid #09F',
	    	padding: '3px',
	    	'background-color': '#FFF',
	    	'border-radius': '3px',
	    	'box-shadow': '0 -1px 0 rgba(0, 0, 0, .3), inset 0 1px 2px rgba(0, 0, 0, .1), 0 0 10px rgba(0, 153, 255, .6)',
	   		opacity: 0.80
	    }).appendTo("body");
		if(x>client_width/2){
			toolLeft = x-($("#tooltip").width())-10;
		}else{
			toolLeft = x+10;
		}
		if(y>client_height/2){
			toolTop = y-25;
		}else{
			toolTop = y+5;
		}
		$("#tooltip").css({top: toolTop,left: toolLeft});
		$("#tooltip").fadeIn(200);
	}
		
	var previousPoint = null;
	$("#burndown").bind("plothover", function (event, pos, item) {
		$("#x").text(pos.x.toFixed(2));
		$("#y").text(pos.y.toFixed(2));
		if (item) {
	    	if (previousPoint != item.dataIndex) {
	        	previousPoint = item.dataIndex;
	            $("#tooltip").remove();
	            var x = item.datapoint[0],y = item.datapoint[1];
	            var title="";
	            if(item.series.label == "<s:text name='reference' />"){
	            	title = referenceMain[previousPoint];
	            }else{
	            	title = remainderMain[previousPoint];
	            }
	            showTooltip(item.pageX, item.pageY,title + ":" + getLocalTime(new Date(x)));
	        }
	    }else {
	    	$("#tooltip").remove();
	    	previousPoint = null;            
	    }
	});

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
	//----burndown End
	
	//----gantt Begin
	
	var ganttValues = [<s:property value="#request.Graph[5]" />];
	function GanttDraw(){
		if(ganttValues.length>0){
			$("#gantt").gantt({
				source:ganttValues,
				navigate: 'scroll',
				scale: 'days',
				maxScale: 'weeks',
				minScale: 'days',
				months:["<s:text name='month.1' />","<s:text name='month.2' />","<s:text name='month.3' />","<s:text name='month.4' />","<s:text name='month.5' />","<s:text name='month.6' />","<s:text name='month.7' />","<s:text name='month.8' />","<s:text name='month.9' />","<s:text name='month.10' />","<s:text name='month.11' />","<s:text name='month.12' />"],
				dow:["<s:text name='week.7' />","<s:text name='week.1' />","<s:text name='week.2' />","<s:text name='week.3' />","<s:text name='week.4' />","<s:text name='week.5' />","<s:text name='week.6' />"]
			});
			$("#gantt").css("margin-top",$("#selectTab").height()/2-$("#gantt").height()/2);
		}
	}
	
	
	
	$(document).ready(function () {
		BurndownDraw();
		GanttDraw();
	});
	
	SelectTab('burndown');
	
	</script>
</body>
</html>