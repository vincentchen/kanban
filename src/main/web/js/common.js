var currentPageNum = 0;
var pageLoadAjax = null;
function getBasePath() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	if(projectName == "/admin"){
		return localhostPaht+"/";
	}else{
		return localhostPaht+projectName+"/";
	}
}

var current_popdiv;
function CreateCrossPopDIV(objframe, id, width, height, url) {
	var objdocument = objframe.document;
	if (current_popdiv) {
		if (current_popdiv.id != id) {
			current_popdiv.style.display = "none";
		}
	}
	if (objframe.current_popdiv) {
		if (objframe.current_popdiv.id != id) {
			objframe.current_popdiv.style.display = "none";
		}
	}
	if (objdocument.getElementById(id)) {
		var loading_div = objdocument.getElementById(id + "loading_div");
		loading_div.style.display = "block";
		objdocument.getElementById(id).style.display = "block";
		objdocument.getElementById(id).focus();
		var iframe = objdocument.getElementById(id + "iframe");
		if (url != "") {
			if (url.indexOf("?") > 0) {
				iframe.src = url + "&temp=" + new Date().getTime();
			} else {
				iframe.src = url + "?temp=" + new Date().getTime();
			}
			if (iframe.attachEvent) {
				iframe.attachEvent("onload", function() {
					loading_div.style.display = "none";
				});
			} else {
				iframe.onload = function() {
					loading_div.style.display = "none";
				};
			}
		}
		objframe.current_popdiv = objdocument.getElementById(id);
		current_popdiv = objdocument.getElementById(id);
	} else {
		var div = objdocument.createElement("div");
		div.id = id;
		div.style.width = width;
		div.style.height = height;
		div.style.position = "fixed";
		div.className = "boxshadow";
		div.style.zIndex = "7";
		objdocument.body.appendChild(div);
		var loading_div = objdocument.createElement("div");
		loading_div.id = id + "loading_div";
		div.appendChild(loading_div);
		loading_div.className = "loading_div";
		loading_div.innerHTML = "<div style='top:50%;left:50%;position: absolute;'><img src='"+getBasePath()+"image/loading.gif'></div>";

		var close_div = objdocument.createElement("div");
		div.appendChild(close_div);
		close_div.className = "pop_div_close";
		close_div.innerHTML = "<a href='javascript:void(0);' onclick='ClosePopDiv();'><img src='"+getBasePath()+"image/big_close.png' /></a>";

		var marginX = objdocument.body.clientWidth > div.offsetWidth ? -(div.offsetWidth/2) : 0,  
            marginY = objdocument.body.clientHeight > div.offsetHeight ? -(div.offsetHeight/2) : 0,  
            left = marginX == 0 ? 0 : '50%',  
            top = marginY == 0 ? 0 : '50%';  
		div.style.left = left;
		div.style.top = top;
		div.style.marginLeft = marginX + 'px';
		div.style.marginTop = marginY + 'px';
		div.focus();
		var content = objdocument.createElement("div");
		content.style.width = div.offsetWidth + "px";
		content.style.height = div.offsetHeight + "px";
		content.style.position = "absolute";
		content.style.zIndex = "7";
		div.appendChild(content);

		var iframe = objdocument.createElement("iframe");
		iframe.id = id + "iframe";
		iframe.style.width = "100%";
		iframe.style.height = "100%";
		content.appendChild(iframe);
		if (url != "") {
			if (url.indexOf("?") > 0) {
				iframe.src = url + "&temp=" + new Date().getTime();
			} else {
				iframe.src = url + "?temp=" + new Date().getTime();
			}
			if (iframe.attachEvent) {
				iframe.attachEvent("onload", function() {
					loading_div.style.display = "none";
				});
			} else {
				iframe.onload = function() {
					loading_div.style.display = "none";
				};
			}
		}
		objframe.current_popdiv = div;
		current_popdiv = div;
	}
}

function CreatePopDiv(id, width, height, url) {
	if (current_popdiv) {
		if (current_popdiv.id != id) {
			current_popdiv.style.display = "none";
		}
	}
	if (document.getElementById(id)) {
		var loading_div = document.getElementById(id + "loading_div");
		loading_div.style.display = "block";
		document.getElementById(id).style.display = "block";
		document.getElementById(id).focus();
		var iframe = document.getElementById(id + "iframe");
		if (url != "") {
			if (url.indexOf("?") > 0) {
				iframe.src = url + "&temp=" + new Date().getTime();
			} else {
				iframe.src = url + "?temp=" + new Date().getTime();
			}
			if (iframe.attachEvent) {
				iframe.attachEvent("onload", function() {
					loading_div.style.display = "none";
				});
			} else {
				iframe.onload = function() {
					loading_div.style.display = "none";
				};
			}
		}
		current_popdiv = document.getElementById(id);
	} else {
		var div = document.createElement("div");
		div.id = id;
		div.style.width = width;
		div.style.height = height;
		div.style.position = "fixed";
		div.className = "boxshadow";
		div.style.zIndex = "7";
		document.body.appendChild(div);
		var loading_div = document.createElement("div");
		loading_div.id = id + "loading_div";
		div.appendChild(loading_div);
		loading_div.className = "loading_div";
		loading_div.innerHTML = "<div style='top:50%;left:50%;position: absolute;'><img src='"+getBasePath()+"image/loading.gif'></div>";

		var close_div = document.createElement("div");
		div.appendChild(close_div);
		close_div.className = "pop_div_close";
		close_div.innerHTML = "<a href='javascript:void(0);' onclick='ClosePopDiv();'><img src='"+getBasePath()+"image/big_close.png' /></a>";

		var marginX = document.body.clientWidth > div.offsetWidth ? -(div.offsetWidth/2) : 0,  
	            marginY = document.body.clientHeight > div.offsetHeight ? -(div.offsetHeight/2) : 0,  
	            left = marginX == 0 ? 0 : '50%',  
	            top = marginY == 0 ? 0 : '50%';  
			div.style.left = left;
			div.style.top = top;
			div.style.marginLeft = marginX + 'px';
			div.style.marginTop = marginY + 'px';
		div.focus();
		var content = document.createElement("div");
		content.style.width = div.offsetWidth + "px";
		content.style.height = div.offsetHeight + "px";
		content.style.position = "absolute";
		content.style.zIndex = "7";
		div.appendChild(content);

		var iframe = document.createElement("iframe");
		iframe.id = id + "iframe";
		iframe.style.width = "100%";
		iframe.style.height = "100%";
		content.appendChild(iframe);
		if (url != "") {
			if (url.indexOf("?") > 0) {
				iframe.src = url + "&temp=" + new Date().getTime();
			} else {
				iframe.src = url + "?temp=" + new Date().getTime();
			}
			if (iframe.attachEvent) {
				iframe.attachEvent("onload", function() {
					loading_div.style.display = "none";
				});
			} else {
				iframe.onload = function() {
					loading_div.style.display = "none";
				};
			}
		}
		current_popdiv = div;
	}
}

function ClosePopDiv() {
	current_popdiv.style.display = "none";
}

function keyUp(e) {
	var currKey = 0, e = e || event;
	var obj = e.target || e.srcElement;
	var t = obj.type || obj.getAttribute('type');
	currKey = e.keyCode || e.which || e.charCode;
	if (currKey == 8 && t != "password" && t != "text" && t != "textarea") {
		return false;
	}
	if (currKey == 27) {
		if (current_popdiv) {
			current_popdiv.style.display = "none";
		}
	}
}
document.onkeyup = keyUp;

function GetAbsoluteLocation(element) {
	if (arguments.length != 1 || element == null) {
		return null;
	}
	var offsetTop = element.offsetTop;
	var offsetLeft = element.offsetLeft;
	var offsetWidth = element.offsetWidth;
	var offsetHeight = element.offsetHeight;
	while (element = element.offsetParent) {
		if (element.tagName == "BODY") {
			break;
		}
		offsetTop += element.offsetTop;
		offsetLeft += element.offsetLeft;
	}
	return {
		absoluteTop : offsetTop,
		absoluteLeft : offsetLeft,
		offsetWidth : offsetWidth,
		offsetHeight : offsetHeight
	};
}

var KeyUpHandler = {
	keyUphandlerValue : "",
	startInterval : 0,
	IntervalObj : null,
	inputObj : null,
	funcName : "",
	valStr : "",
	clickOn : 0,
	endEnter : 0,
	callBackFunc : "",
	init : function(inputObj, funcName, valStr, callBackFunc) {
		KeyUpHandler.inputObj = inputObj;
		KeyUpHandler.funcName = funcName;
		KeyUpHandler.valStr = valStr;
		KeyUpHandler.callBackFunc = callBackFunc;
		inputObj.onkeyup = KeyUpHandler.start;
		document.onclick = function() {
			if (KeyUpHandler.clickOn == 1) {
				KeyUpHandler.closeQueryPanel();
			}
		};
	},
	start : function() {
		if (!KeyUpHandler.IntervalObj) {
			KeyUpHandler.IntervalObj = setInterval("KeyUpHandler.check()", 100);
		}
	},
	callAjax : function() {
		var dataString = KeyUpHandler.inputObj.id + "=" + KeyUpHandler.inputObj.value + "&callBackFunc=" + KeyUpHandler.callBackFunc;
		var b = document.createElement("div");
		var location = GetAbsoluteLocation(KeyUpHandler.inputObj);
		b.style.position = "absolute";
		b.style.left = location.absoluteLeft + location.offsetWidth - 18 + "px";
		b.style.top = location.absoluteTop + location.offsetHeight / 2 - 8 + "px";
		document.body.appendChild(b);
		b.innerHTML = "<IMG SRC='"+getBasePath()+"image/loading_small.gif' />";
		if(pageLoadAjax != null){
			pageLoadAjax.abort();
		}
		pageLoadAjax = $.ajax({
			type : "POST",
			url : KeyUpHandler.funcName + KeyUpHandler.valStr,
			data : dataString,
			success : function(data) {
				if(data == ""){
					alert('<s:text name="error.db" />');
					return;
				}
				var json_data = $.parseJSON(data);
				KeyUpHandler.end(json_data);
				document.body.removeChild(b);
				pageLoadAjax = null;
			}
		});
	},
	check : function() {
		if (KeyUpHandler.inputObj.value != "") {
			if (KeyUpHandler.keyUphandlerValue == KeyUpHandler.inputObj.value) {
				KeyUpHandler.endEnter++;
				if (KeyUpHandler.endEnter > 5) {
					clearInterval(KeyUpHandler.IntervalObj);
					KeyUpHandler.IntervalObj = null;
					KeyUpHandler.callAjax();
					KeyUpHandler.endEnter = 0;
				}
			} else {
				KeyUpHandler.keyUphandlerValue = KeyUpHandler.inputObj.value;
				KeyUpHandler.endEnter = 0;
			}
		} else {
			KeyUpHandler.endEnter = 0;
			if(document.getElementById("KeyUpHandlerDiv")){
				document.body.removeChild(document.getElementById("KeyUpHandlerDiv"));
			}
		}
	},
	end : function(json_data) {
		if (document.getElementById("KeyUpHandlerDiv") == null) {
			var b = document.createElement("div");
			b.className = "boxradius";
			var location = GetAbsoluteLocation(KeyUpHandler.inputObj);
			b.id = "KeyUpHandlerDiv";
			b.style.position = "absolute";
			b.style.background = "#fff";
			b.style.width = location.offsetWidth + "px";
			b.style.left = location.absoluteLeft + "px";
			b.style.top = location.absoluteTop + location.offsetHeight + 5 + "px";
			document.body.appendChild(b);
		} else {
			var b = document.getElementById("KeyUpHandlerDiv");
		}
		if (json_data[0].error != "") {
			b.innerHTML = "<TABLE class='gTab'><TR><TD>" + json_data[0].error + "</TD></TR></TABLE>";
		} else {
			var content = json_data[0].content;
			b.innerHTML = content;
		}
		KeyUpHandler.clickOn = 1;
	},
	closeQueryPanel : function() {
		document.body.removeChild(document.getElementById("KeyUpHandlerDiv"));
		KeyUpHandler.clickOn = 0;
	}
};

var Cookies = {};

Cookies.set = function(name, value) {
	var argv = arguments;
	var argc = arguments.length;
	var expires = (argc > 2) ? argv[2] : null;
	var path = (argc > 3) ? argv[3] : '/';
	var domain = (argc > 4) ? argv[4] : null;
	var secure = (argc > 5) ? argv[5] : false;
	document.cookie = name + "=" + escape(value) + ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + ((path == null) ? "" : ("; path=" + path)) + ((domain == null) ? "" : ("; domain=" + domain)) + ((secure == true) ? "; secure" : "");
};

Cookies.get = function(name) {
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	var j = 0;
	while (i < clen) {
		j = i + alen;
		if (document.cookie.substring(i, j) == arg)
			return Cookies.getCookieVal(j);
		i = document.cookie.indexOf(" ", i) + 1;
		if (i == 0)
			break;
	}
	return null;
};

Cookies.clear = function(name) {
	if (Cookies.get(name)) {
		var expdate = new Date();
		expdate.setTime(expdate.getTime() - (86400 * 1000 * 1));
		Cookies.set(name, "", expdate);
	}
};

function printf(str,arg,isArr){
	var pattern = /\{\d+\}/g;
	if(isArr>0){
		for(var i=0;i<arg.length;i++){
			var match = pattern.exec(s);
			if (match)
	        {
				str = str.replace(match, arg[i]);
	        }
		}
	}else{
		var match = pattern.exec(str);
		str = str.replace(match, arg);
	}
	return str;
}

var scrollHandler = {
	baseLineDOM:"",
	callBackFunc:"",
	is_end:0,
	init:function(baseLineDOM, callBackFunc){
		scrollHandler.baseLineDOM = baseLineDOM;
		scrollHandler.callBackFunc = callBackFunc;
		scrollHandler.start();
	},
	start : function() {
		$(window).resize(function(){
			if(scrollHandler.is_end==0){
				var location = GetAbsoluteLocation(scrollHandler.baseLineDOM);
				if(location.offsetHeight-document.body.scrollTop-document.body.clientHeight+location.absoluteTop<=document.body.clientHeight){
					eval(scrollHandler.callBackFunc);
				}
			}
		}).scroll(function(){
			if(scrollHandler.is_end==0){
				var location = GetAbsoluteLocation(scrollHandler.baseLineDOM);
				if(location.offsetHeight-document.body.scrollTop-document.body.clientHeight+location.absoluteTop<=document.body.clientHeight){
					eval(scrollHandler.callBackFunc+"();");
				}
			}
		});
	},
	stop:function(){
		scrollHandler.is_end = 1;
	}
}