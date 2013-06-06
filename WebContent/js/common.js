// terminate setIntenal function
var setIntervalObj = null;

var winWidth = 0;
var winHeight = 0;
function findDimensions() { // 函数：获取尺寸

	if (document.body.scrollWidth > document.body.clientWidth) {
		winWidth = document.body.scrollWidth;
	} else {
		winWidth = document.body.clientWidth;
	}
	if (document.body.scrollHeight > document.body.clientHeight) {
		winHeight = document.body.scrollHeight;
	} else {
		winHeight = document.body.clientHeight;
	}

}

/**
 * @param newDivID test
 * @param width	300
 * @param height 400
 * @param contentIndex content5
 * @return
 */

function popupAlert(newDivID, width, height, contentIndex) {
	// height = 462;
	findDimensions(); // 调用函数，获取数值
	window.onresize = findDimensions;

	var newMaskID = "mask"; // 遮罩层id
	var newMaskWidth = winWidth;// 遮罩层宽度
	var newMaskHeight = winHeight;// 遮罩层高度
	// var newMaskWidth = document.body.scrollWidth;// 遮罩层宽度
	// var newMaskHeight = document.body.scrollHeight;// 遮罩层高度

	// mask遮罩层
	var newMask = document.createElement("div");// 创建遮罩层
	newMask.id = newMaskID;// 设置遮罩层id
	newMask.style.position = "absolute";// 遮罩层位置
	newMask.style.zIndex = "100";// 遮罩层zIndex
	newMask.style.width = newMaskWidth + "px";// 设置遮罩层宽度
	newMask.style.height = newMaskHeight + "px";// 设置遮罩层高度
	newMask.style.top = "0px";// 设置遮罩层于上边距离
	newMask.style.left = "0px";// 设置遮罩层左边距离
	newMask.style.background = "black";// #33393C//遮罩层背景色
	newMask.style.filter = "alpha(opacity=40)";// 遮罩层透明度IE
	newMask.style.opacity = "0.40";// 遮罩层透明度FF
	document.getElementById(contentIndex).appendChild(newMask);// 遮罩层添加到DOM中

	// 新弹出层
	var newDivWidth = width;// 新弹出层宽度
	var newDivHeight = height;// 新弹出层高度
	var newDivtop = (screen.height / 2 - newDivHeight / 2) - 80;// 新弹出层距离上边距离
	var newDivleft = (screen.width / 2 - newDivWidth / 2);// 新弹出层距离左边距离

	document.getElementById(newDivID).style.position = "absolute";// 新弹出层位置
	document.getElementById(newDivID).style.zIndex = "1000";// 新弹出层zIndex
	document.getElementById(newDivID).style.width = newDivWidth + "px";// 新弹出层宽度
	document.getElementById(newDivID).style.height = newDivHeight + "px";// 新弹出层高度
//	alert(newDivtop);
	if(newDivtop < 0 || newDivtop == 0){
		document.getElementById(newDivID).style.top = 10 + "px";// 新弹出层距离上边距离
	}else{
		document.getElementById(newDivID).style.top = newDivtop + "px";// 新弹出层距离上边距离
	}		
	document.getElementById(newDivID).style.left = newDivleft + "px";// 新弹出层距离左边距离
	document.getElementById(newDivID).style.background = "#EFEFEF";// 新弹出层背景色
	document.getElementById(newDivID).style.border = "1px solid #860001";// /新弹出层边框样式
	document.getElementById(newDivID).style.padding = "5px";// 新弹出层
	document.getElementById(newDivID).style.display = "";

	document.getElementById(contentIndex).appendChild(
			document.getElementById(newDivID));// 新弹出层添加到DOM中

	// 弹出层滚动居中
	function newDivCenter() {
		document.getElementById(newDivID).style.top = (document.body.scrollTop
				+ document.body.clientHeight / 2 - newDivHeight / 2)
				+ "px";
		document.getElementById(newDivID).style.left = (document.body.scrollLeft
				+ document.body.clientWidth / 2 - newDivWidth / 2)
				+ "px";
	}
	// 处理滚动事件，使弹出层始终居中
	if (document.all) {
		if(newDivID != null){
			//window.attachEvent("onscroll", newDivCenter);
		}
	} else {
		if(newDivID != null){
			//window.addEventListener('scroll', newDivCenter, false);
		}
	}

	// 关闭新图层和mask遮罩层
	var newA = document.createElement("span");
	newA.href = "#";
	newA.id = newDivID+"_close";
	newA.style.position = "absolute";// span位置
	newA.style.right = 10 + "px";
	newA.style.top = 10 + "px";
	newA.style.cursor = "pointer";
	newA.style.color = "#AB8617";
	newA.innerHTML = "关闭";

	// 处理关闭事件
	newA.onclick = function() {
		if (document.all) {
			window.detachEvent("onscroll", newDivCenter);
		} else {
			window.removeEventListener('scroll', newDivCenter, false);
		}
		document.getElementById(contentIndex).removeChild(newMask);// 移除遮罩层
		//document.getElementById(contentIndex).removeChild(newDivID);// 移除
		document.getElementById(newDivID).removeChild(newA);// 移除Close
		document.getElementById(newDivID).style.display = "none";
		return false;
	};

	document.getElementById(newDivID).appendChild(newA);// 添加关闭span
}


/**
 * @param newDivID test
 * @param width	300
 * @param height 400
 * @param contentIndex content5
 * @return
 */

function popupAlertWithCloseFunction(newDivID, width, height, contentIndex, callbackName) {
	// height = 462;
	findDimensions(); // 调用函数，获取数值
	window.onresize = findDimensions;

	var newMaskID = "mask"; // 遮罩层id
	var newMaskWidth = winWidth;// 遮罩层宽度
	var newMaskHeight = winHeight;// 遮罩层高度
	// var newMaskWidth = document.body.scrollWidth;// 遮罩层宽度
	// var newMaskHeight = document.body.scrollHeight;// 遮罩层高度

	// mask遮罩层
	var newMask = document.createElement("div");// 创建遮罩层
	newMask.id = newMaskID;// 设置遮罩层id
	newMask.style.position = "absolute";// 遮罩层位置
	newMask.style.zIndex = "100";// 遮罩层zIndex
	newMask.style.width = newMaskWidth + "px";// 设置遮罩层宽度
	newMask.style.height = newMaskHeight + "px";// 设置遮罩层高度
	newMask.style.top = "0px";// 设置遮罩层于上边距离
	newMask.style.left = "0px";// 设置遮罩层左边距离
	newMask.style.background = "black";// #33393C//遮罩层背景色
	newMask.style.filter = "alpha(opacity=40)";// 遮罩层透明度IE
	newMask.style.opacity = "0.40";// 遮罩层透明度FF
	document.getElementById(contentIndex).appendChild(newMask);// 遮罩层添加到DOM中

	// 新弹出层
	var newDivWidth = width;// 新弹出层宽度
	var newDivHeight = height;// 新弹出层高度
	var newDivtop = (screen.height / 2 - newDivHeight / 2) - 80;// 新弹出层距离上边距离
	var newDivleft = (screen.width / 2 - newDivWidth / 2);// 新弹出层距离左边距离

	document.getElementById(newDivID).style.position = "absolute";// 新弹出层位置
	document.getElementById(newDivID).style.zIndex = "1000";// 新弹出层zIndex
	document.getElementById(newDivID).style.width = newDivWidth + "px";// 新弹出层宽度
	document.getElementById(newDivID).style.height = newDivHeight + "px";// 新弹出层高度
//	alert(newDivtop);
	if(newDivtop < 0 || newDivtop == 0){
		document.getElementById(newDivID).style.top = 10 + "px";// 新弹出层距离上边距离
	}else{
		document.getElementById(newDivID).style.top = newDivtop + "px";// 新弹出层距离上边距离
	}		
	document.getElementById(newDivID).style.left = newDivleft + "px";// 新弹出层距离左边距离
	document.getElementById(newDivID).style.background = "#EFEFEF";// 新弹出层背景色
	document.getElementById(newDivID).style.border = "1px solid #860001";// /新弹出层边框样式
	document.getElementById(newDivID).style.padding = "5px";// 新弹出层
	document.getElementById(newDivID).style.display = "";

	document.getElementById(contentIndex).appendChild(
			document.getElementById(newDivID));// 新弹出层添加到DOM中

	// 弹出层滚动居中
	function newDivCenter() {
		document.getElementById(newDivID).style.top = (document.body.scrollTop
				+ document.body.clientHeight / 2 - newDivHeight / 2)
				+ "px";
		document.getElementById(newDivID).style.left = (document.body.scrollLeft
				+ document.body.clientWidth / 2 - newDivWidth / 2)
				+ "px";
	}
	// 处理滚动事件，使弹出层始终居中
	if (document.all) {
		if(newDivID != null){
			//window.attachEvent("onscroll", newDivCenter);
		}
	} else {
		if(newDivID != null){
			//window.addEventListener('scroll', newDivCenter, false);
		}
	}

	// 关闭新图层和mask遮罩层
	var newA = document.createElement("span");
	newA.href = "#";
	newA.id = newDivID+"_close";
	newA.style.position = "absolute";// span位置
	newA.style.right = 10 + "px";
	newA.style.top = 10 + "px";
	newA.style.cursor = "pointer";
	newA.style.color = "#AB8617";
	newA.innerHTML = "关闭";

	// 处理关闭事件
	newA.onclick = function() {
		if (document.all) {
			window.detachEvent("onscroll", newDivCenter);
		} else {
			window.removeEventListener('scroll', newDivCenter, false);
		}
		document.getElementById(contentIndex).removeChild(newMask);// 移除遮罩层
		//document.getElementById(contentIndex).removeChild(newDivID);// 移除
		document.getElementById(newDivID).removeChild(newA);// 移除Close
		document.getElementById(newDivID).style.display = "none";
		eval(callbackName+"()");
		return false;
	};

	document.getElementById(newDivID).appendChild(newA);// 添加关闭span
}


function popupAlertWithoutClose(newDivID, width, height, contentIndex) {
	// height = 462;
	findDimensions(); // 调用函数，获取数值
	window.onresize = findDimensions;

	var newMaskID = "mask"; // 遮罩层id
	var newMaskWidth = winWidth;// 遮罩层宽度
	var newMaskHeight = winHeight;// 遮罩层高度
	// var newMaskWidth = document.body.scrollWidth;// 遮罩层宽度
	// var newMaskHeight = document.body.scrollHeight;// 遮罩层高度

	// mask遮罩层
	var newMask = document.createElement("div");// 创建遮罩层
	newMask.id = newMaskID;// 设置遮罩层id
	newMask.style.position = "absolute";// 遮罩层位置
	newMask.style.zIndex = "100";// 遮罩层zIndex
	newMask.style.width = newMaskWidth + "px";// 设置遮罩层宽度
	newMask.style.height = newMaskHeight + "px";// 设置遮罩层高度
	newMask.style.top = "0px";// 设置遮罩层于上边距离
	newMask.style.left = "0px";// 设置遮罩层左边距离
	newMask.style.background = "black";// #33393C//遮罩层背景色
	newMask.style.filter = "alpha(opacity=40)";// 遮罩层透明度IE
	newMask.style.opacity = "0.40";// 遮罩层透明度FF
	document.getElementById(contentIndex).appendChild(newMask);// 遮罩层添加到DOM中

	// 新弹出层
	var newDivWidth = width;// 新弹出层宽度
	var newDivHeight = height;// 新弹出层高度
	var newDivtop = (screen.height / 2 - newDivHeight / 2) - 80;// 新弹出层距离上边距离
	var newDivleft = (screen.width / 2 - newDivWidth / 2);// 新弹出层距离左边距离

	document.getElementById(newDivID).style.position = "absolute";// 新弹出层位置
	document.getElementById(newDivID).style.zIndex = "1000";// 新弹出层zIndex
	document.getElementById(newDivID).style.width = newDivWidth + "px";// 新弹出层宽度
	document.getElementById(newDivID).style.height = newDivHeight + "px";// 新弹出层高度
	document.getElementById(newDivID).style.top = newDivtop + "px";// 新弹出层距离上边距离
	document.getElementById(newDivID).style.left = newDivleft + "px";// 新弹出层距离左边距离
	document.getElementById(newDivID).style.background = "#EFEFEF";// 新弹出层背景色
	document.getElementById(newDivID).style.border = "1px solid #860001";// /新弹出层边框样式
	document.getElementById(newDivID).style.padding = "5px";// 新弹出层
	document.getElementById(newDivID).style.display = "";

	document.getElementById(contentIndex).appendChild(
			document.getElementById(newDivID));// 新弹出层添加到DOM中

	// 弹出层滚动居中
	function newDivCenter() {
		document.getElementById(newDivID).style.top = (document.body.scrollTop
				+ document.body.clientHeight / 2 - newDivHeight / 2)
				+ "px";
		document.getElementById(newDivID).style.left = (document.body.scrollLeft
				+ document.body.clientWidth / 2 - newDivWidth / 2)
				+ "px";
	}
	// 处理滚动事件，使弹出层始终居中
	if (document.all) {
		if(newDivID != null){
			//window.attachEvent("onscroll", newDivCenter);
		}
	} else {
		if(newDivID != null){
			//window.addEventListener('scroll', newDivCenter, false);
		}
	}

	// 关闭新图层和mask遮罩层
	var newA = document.createElement("span");
	newA.href = "#";
	newA.id = newDivID+"_close";
	newA.style.position = "absolute";// span位置
	newA.style.right = 10 + "px";
	newA.style.top = 10 + "px";
	newA.style.cursor = "pointer";
	newA.style.display = "none";
	newA.style.color = "#AB8617";
	newA.innerHTML = "关闭";

	// 处理关闭事件
	newA.onclick = function() {
		if (document.all) {
			window.detachEvent("onscroll", newDivCenter);
		} else {
			window.removeEventListener('scroll', newDivCenter, false);
		}
		document.getElementById(contentIndex).removeChild(newMask);// 移除遮罩层
		//document.getElementById(contentIndex).removeChild(newDivID);// 移除
		document.getElementById(newDivID).removeChild(newA);// 移除Close
		document.getElementById(newDivID).style.display = "none";
		return false;
	};

	document.getElementById(newDivID).appendChild(newA);// 添加关闭span
}


function ajaxCall(urlString, jsonData) {

}

function selectOneResource(resourceName) {
	var jsonData = "{resourceName:" + resourceName + "}";

	$('#imageSourceList').dialog("close");

	ajaxCall("selectOneResourceAction.html", jsonData);

}

/*
 * delete resource type : IMAGETYPE VIDEOTYPE AUDIOTYPE TEXTTYPE pageIndex :
 * #box-1, #box-2, #box-3, #box-4, #box-5
 */
function delResourceAjaxCall(id, name, type, pageIndex) {
	$.ajax( {
		type : "POST",
		url : "/CPS/delResourceAction.html",
		dataType : "html",
		data : {
			id : id,
			type : type,
			name : name
		},
		success : function(data1) {
			$.ajax( {
				type : "POST",
				url : "/CPS/resourceAction.html",
				dataType : "html",
				data : {
					"type" : pageIndex
				},
				success : function(data2) {
					alert("删除成功");
					$("" + pageIndex).html(data2);
				},
				error : function() {
					alert($(this).attr("href") + "资源获取失败");
				}
			});
		},
		error : function() {
			alert($(this).attr("href") + "资源获取失败");
		}
	});
}

/*
 * delete resource selectedIDs : resource_xxx.xxx,resource_xxx.xxx,
 * selectedNames : xxxx.xxx|xxxx.xxx|xxxx.xxx 
 * type : IMAGETYPE VIDEOTYPE AUDIOTYPE TEXTTYPE 
 * pageIndex : #box-1, #box-2, #box-3, #box-4, #box-5
 */
function delSelectedResource(selectedIDs, selectedNames, type, pageIndex) {
	
	if(selectedIDs == null || selectedIDs == ""){
		alert("请选择要删除的选项");
		return;
	}
	
	$.ajax( {
		type : "POST",
		url : "/CPS/delSelectedResourceAction.html",
		dataType : "html",
		data : {
			selectedIDs : selectedIDs,
			selectedNames : selectedNames,
			type : type
		},
		success : function(data1) {
			$.ajax( {
				type : "POST",
				url : "/CPS/resourceAction.html",
				dataType : "html",
				data : {
					"type" : pageIndex
				},
				success : function(data2) {
					alert("删除成功");
					$("" + pageIndex).html(data2);
				},
				error : function() {
					alert($(this).attr("href") + "资源获取失败");
				}
			});
		},
		error : function() {
			alert($(this).attr("href") + "资源获取失败");
		}
	});

}

/*
 * delete xml content id : id xmlDbType : template,program,playlist,user
 */
function delXmlContetnAjaxCall(id, xmlDbType, contentIndex) {
	var url1 = "";
	var url2 = "";
	if (xmlDbType == "template") {
		url1 = "/CPS/delTemplateAction.html";
		url2 = "/CPS/getTemplateAction.html";
	}
	if (xmlDbType == "program") {
		url1 = "/CPS/delProgramAction.html";
		url2 = "/CPS/getProgramAction.html";
	}
	if (xmlDbType == "playlist") {
		url1 = "/CPS/delPlaylistAction.html";
		url2 = "/CPS/getPlaylistAction.html";
	}
	if (xmlDbType == "user") {
		url1 = "/CPS/delUserAction.html";
		url2 = "/CPS/getUserlistAction.html";
	}
	id = encodeURI(id);
	if (xmlDbType == "device") {
		url1 = "/CPS/delDeviceAction.html";
		var flag = false;
		$.ajax( {
			type : "POST",
			url : url1,
			dataType : "html",
			async : false,
			data : {
				id : id,
				xmlDbType : xmlDbType
			},
			success : function(data1) {
				if(data1 == "DEFAULT_CODE"){
					alert("由于网络原因，删除失败！");
				}else{
					flag = true;
					alert("删除成功");
				}
			},
			error : function() {
				alert("资源获取失败");
				falg = false;
			}
		});
		return flag;
	}

	if(xmlDbType != "device"){
		$.ajax( {
			type : "POST",
			url : url1,
			dataType : "html",
			data : {
				id : id,
				xmlDbType : xmlDbType
			},
			success : function(data1) {
				if(data1 == "DEFAULT_CODE"){
					alert("由于网络原因，删除失败！");
				}else{
					$.ajax( {
						type : "POST",
						url : url2,
						dataType : "html",
						success : function(data) {
							alert("删除成功");
							$("#" + contentIndex).html(data);
							$("#" + contentIndex).show("slow");
						},
						error : function() {
							alert($("#" + contentIndex) + "加载失败");
						}
					});
				}
				
			},
			error : function() {
				alert($(this).attr("href") + "资源获取失败");
			}
		});
	}
}

/*
 * delete selected xml content id : id xmlDbType : template,program,playlist
 */
function delSelectedXmlContetnAjaxCall(ids, xmlDbType, contentIndex) {
	
	if(ids == null || ids == ""){
		alert("请选择要删除的选项");
		return;
	}
	ids = encodeURI(ids);
	var url1 = "";
	var url2 = "";
	if (xmlDbType == "template") {
		url1 = "/CPS/delSelectedTemplateAction.html";
		url2 = "/CPS/getTemplateAction.html";
	}
	if (xmlDbType == "program") {
		url1 = "/CPS/delSelectedProgramAction.html";
		url2 = "/CPS/getProgramAction.html";
	}
	if (xmlDbType == "playlist") {
		url1 = "/CPS/delSelectedPlaylistAction.html";
		url2 = "/CPS/getPlaylistAction.html";
	}
	if (xmlDbType == "user") {
		url1 = "/CPS/delSelectedUserAction.html";
		url2 = "/CPS/getUserlistAction.html";
	}
	if (xmlDbType == "device") {
		url1 = "/CPS/delSelectedDeviceAction.html";
		var flag = false;
		$.ajax( {
			type : "POST",
			url : url1,
			dataType : "html",
			async : false,
			data : {
				ids : ids
			},
			success : function(data1) {
				flag = true;
				alert("删除成功");
			},
			error : function() {
				alert("资源获取失败");
				falg = false;
			}
		});
		return flag;
	}
	if (xmlDbType != "device") {
		$.ajax( {
			type : "POST",
			url : url1,
			dataType : "html",
			data : {
				ids : ids
			},
			success : function(data1) {
				$.ajax( {
					type : "POST",
					url : url2,
					dataType : "html",
					success : function(data) {
						alert("删除成功");
						$("#" + contentIndex).html(data);
						$("#" + contentIndex).show("slow");
					},
					error : function() {
						alert($("#" + contentIndex) + "加载失败");
					}
				});
			},
			error : function() {
				alert($(this).attr("href") + "资源获取失败");
			}
		});
	}
}

// 暂未提供此功能
function unsupport() {
	alert("暂未提供此功能！");
}

// display time
function showTime(divId) {
	var curTime = new Date();
	$('#' + divId).html(
			curTime.toLocaleString() + '<div class="jqHandle jqResize"></div>');
}

function _showTime(divId) {
	return function() {
		showTime(divId);
	};
}

//display time for preview
function _showTimeForPrivew(divId) {
	return function() {
		showTimeForPrivew(divId);
	};
}
//display time for preview
function showTimeForPrivew(divId) {
	var curTime = new Date();
	$('#' + divId).html(curTime.toLocaleString());
}

//get current millins
function getCurrentMillis(){
	var date=new Date();
	var yy=date.getFullYear();
	var MM=date.getMonth() + 1;
	var dd=date.getDay();
	var hh=date.getHours();
	var mm=date.getMinutes();
	var ss=date.getSeconds();
	var sss=date.getMilliseconds(); 
	var result=Date.UTC(yy,MM,dd,hh,mm,ss,sss);
	return result;
}

// device list
function getDeviceList(groupId, nodeText) {
	$.ajax( {
		type : "POST",
		url : "/CPS/getGroupDeviceListAction.html",
		dataType : "html",
		data : {
			groupId : groupId
		},
		success : function(data) {
			$("#deviceDisplay").html(data);
			$("#groupidHidden").val(groupId);
			$("#groupTextHidden").val(nodeText);
		},
		error : function() {
			alert("设备列表获取失败");
		}
	});
}

// add node to tree.xml
/*
 * function addNodeToTreeXml(fatherId,childId,value,actionUrl){ $.ajax({ type:
 * "POST", url: "/CPS/addNodeToTreeXmlAction.html", dataType:"json",
 * async: true, data:
 * {fatherId:fatherId,childId:childId,value:value,actionUrl:actionUrl}, success:
 * function(data){ alert(data.addflag); if(data.addflag == "true"){
 * alert("添加分组成功"); //$("#deviceDisplay").html(data); } }, error:function(){
 * alert("设备列表获取失败"); } }); }
 */

// validate mac
function fnValidateMacAddress(macaddr) {
	var reg1 = /^[A-Fa-f0-9]{1,2}\-[A-Fa-f0-9]{1,2}\-[A-Fa-f0-9]{1,2}\-[A-Fa-f0-9]{1,2}\-[A-Fa-f0-9]{1,2}\-[A-Fa-f0-9]{1,2}$/;
	var reg2 = /^[A-Fa-f0-9]{1,2}\:[A-Fa-f0-9]{1,2}\:[A-Fa-f0-9]{1,2}\:[A-Fa-f0-9]{1,2}\:[A-Fa-f0-9]{1,2}\:[A-Fa-f0-9]{1,2}$/;

	if (reg1.test(macaddr)) {
		return true;
	} else if (reg2.test(macaddr)) {
		return true;
	} else {
		return false;
	}
}

// validate ip
function fnValidateIPAddress(ip){
	var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/; 
	//var reg = ip.match(exp);
	if(exp.test(ip)){
		return true;
	} else {
		return false;
	}
}

/*
 * export xls
 * @param onlie offline
 * 
 */ 
function exportXls(deviceType){
	$.ajax({
		type : "POST",
		url : "/CPS/exportDeviceAction.html",
		dataType : "html",
		data : {
			deviceType : deviceType
		},
		success : function(data) {
			alert(data);
			$('#export').html(data);
			//window.open("manage_monitor_excel.jsp","");
			
	            var s = $('#export').html();
	            location.href = "manage_monitor_excel.jsp";
	           
		},
		error : function() {
			alert("导出xls失败");
		}
	});
}
// mouseover show div
function showTip(divid){
	$('#'+divid).show("slow");
}
// mouseout hide div
function hidTip(divid){
	$('#'+divid).hide("slow");
}



/*
*
*string:原始字符串
*substr:子字符串
*isIgnoreCase:忽略大小写
*/

function contains(string,substr,isIgnoreCase){
	if(isIgnoreCase){
		string = string.toLowerCase();
		substr = substr.toLowerCase();
	}
	var startChar = substr.substring(0,1);
	var strLen = substr.length;
	for(var j=0;j<string.length-strLen+1;j++){
		//如果匹配起始字符,开始查找
		if(string.charAt(j)==startChar){
			//如果从j开始的字符与str匹配，那ok
			if(string.substring(j,j+strLen)==substr){
				return true;
			}
		}
	}
	return false;
}

// replace
function sReplace(father, old, newreplace){   
	var r, re;                    // 声明变量。
	var ss = father;
	re = "/"+old+"/g";             // 创建正则表达式模式。
	r = ss.replace(re, ""+newreplace);    // 用 "A" 替换 "The"。
	return r;  // 返回替换后的字符串。
}

function goTopEx(){
    var obj=document.getElementById("goTopBtn");
    function getScrollTop(){
        return document.documentElement.scrollTop;
    }
    function setScrollTop(value){
        document.documentElement.scrollTop=value;
    }    
    window.onscroll=function(){
    	getScrollTop()>0?obj.style.display="":obj.style.display="none";
    }
    obj.onclick=function(){
        var goTop=setInterval(scrollMove,10);
        function scrollMove(){
            setScrollTop(getScrollTop()/1.1);
            if(getScrollTop()<1)clearInterval(goTop);
        }
    };
}