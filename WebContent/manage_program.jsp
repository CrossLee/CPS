<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="/WEB-INF/tag/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<!-- image preview -->
<script src="js/flowplayer/jquery.tools.min.js" type="text/javascript" charset="GBK"></script>
<script src="js/flowplayer/flowplayer-3.2.8.min.js" type="text/javascript" charset="GBK"></script>
<script type="text/javascript" src="js/main.js"></script>
<style type="text/css">
#preview {
	position: absolute;
	border: 1px solid #ccc;
	background: #333;
	padding: 5px;
	display: none;
	color: #fff;
}

.overlay {
	padding: 40px;
	width: 576px;
	display: none;
	background-image: url(images/white.png);
}

.close {
	background: url(images/close.png) no-repeat;
	position: absolute;
	top: 2px;
	right: 5px;
	display: block;
	width: 35px;
	height: 35px;
	cursor: pointer;
}

#player {
	height: 450px;
	display: block;
}
</style>
<!-- image preview -->
<style type="text/css">
.programConfig {
	width: 200px;
	height: 400px;
	border: 1px solid;
	float: left;
}

.programArea {
	border: 1px solid;
	position: relative;
	width: 600px;
	height: 400px;
	left: 250px;
	margin: 30px;
}
.hid{
	display: none;
}
</style>

<script type="text/javascript">

	// 判断是add还是edit
	var requestUrl = null;
	// 如果是add，这programId为""，否则是edit
	var programId = "";
	var programAreaOrignalHtml = "";
	var preview_programAreaOrignalHtml = "";

	// close video
	function closeVideo(){
		$("#preview_programArea #player_id").html("");
	}
	
	$(document).ready(function(){

		$("#pStartdate").datepicker({
			dateFormat: 'yy-mm-dd'
		});
		$("#pEnddate").datepicker({
			dateFormat: 'yy-mm-dd'
		});
		
		programAreaOrignalHtml = $("#programArea").html();
		preview_programAreaOrignalHtml = $('#preview_programArea').html();
		//save clicked
		$('#btnSaveProgram').click(function(){
			var programName = encodeURI($('#programName').val());
			var programRemark = encodeURI($('#programRemark').val());
			var hours = $('#hours').val();
			var minutes = $('#minutes').val();
			var seconds = $('#seconds').val();

			if(programName == null || programName == ""){
				alert("请输入节目名称");
				return false;
			}

			if(hours == "时"){
				hours = '0';
			}
			if(minutes == "分"){
				minutes = '0';
			}
			if(seconds == "秒"){
				seconds = '0';
			}
			var programLength = (Number(hours)*60 + Number(minutes))*60 + Number(seconds);

			// check the checkbox, if checked, then the programLength = 0
			if($('#isSelectLength').attr('checked') != 'checked'){
				programLength = 0;
			}else{
				if(programLength == '0'){
					alert('播放时长必须大于1秒');
					return false;
				}
			}

			var radioValue = "";
			
			// date && time
			var startDate = "";
			var endDate = "";
			var startHour = "";
			var startMinute = "";
			var startSecond = "";
			var endHour = "";
			var endMinute = "";
			var endSecond = "";

			// monday to sunday
			var xingqi1 = "";
			var xingqi2 = "";
			var xingqi3 = "";
			var xingqi4 = "";
			var xingqi5 = "";
			var xingqi6 = "";
			var xingqi7 = "";
			
			// radio cycle
			if($("#radio0").attr("checked") == "checked"){
				radioValue = "0";
			}else{
				if($("#radio1").attr("checked") == "checked"){
					radioValue = "1";
				}
				if($("#radio2").attr("checked") == "checked"){
					radioValue = "2";
				}
				startDate = $("#pStartdate").val();
				endDate = $("#pEnddate").val();
				startHour = $("#pStarttimeHour").val();
				startMinute = $("#pStarttimeMinute").val();
				startSecond = $("#pStarttimeSecond").val();
				endHour = $("#pEndtimeHour").val();
				endMinute = $("#pEndtimeMinute").val();
				endSecond = $("#pEndtimeSecond").val();

				var sdate = startDate.replace(/-/g, "").replace(/ /g,"").replace(/:/g,"");
				var edate = endDate.replace(/-/g, "").replace(/ /g,"").replace(/:/g,"");
				if(parseInt(edate) < parseInt(sdate)){
					alert("选择的结束日期必须等于或晚于开始日期！");
					return false;
				}

				if(startDate == ""){
					alert("请选择开始日期");
					return false;
				}
				if(endDate == ""){
					alert("请选择结束日期");
					return false;
				}
				if(startHour == ""){
					alert("请选择开始时间：时");
					return false;
				}
				if(parseInt(startHour) > 23){
					alert("时：最大值为23");
					return false;
				}
				if(startMinute == ""){
					alert("请选择开始时间：分");
					return false;
				}
				if(parseInt(startMinute) > 59){
					alert("分：最大值为59");
					return false;
				}
				if(startSecond == ""){
					alert("请选择开始时间：秒");
					return false;
				}
				if(parseInt(startSecond) > 59){
					alert("秒：最大值为59");
					return false;
				}
				if(endHour == ""){
					alert("请选择结束时间：时");
					return false;
				}
				if(parseInt(endHour) > 23){
					alert("时：最大值为23");
					return false;
				}
				if(endMinute == ""){
					alert("请选择开始时间：分");
					return false;
				}
				if(parseInt(endMinute) > 59){
					alert("分：最大值为59");
					return false;
				}
				if(endSecond == ""){
					alert("请选择开始时间：秒");
					return false;
				}
				if(parseInt(endSecond) > 59){
					alert("秒：最大值为59");
					return false;
				}
				
				var stime = parseInt(startHour)*3600 + parseInt(startMinute)*60 + parseInt(startSecond);
				var etime = parseInt(endHour)*3600 + parseInt(endMinute)*60 + parseInt(endSecond);
				if(parseInt(edate) == parseInt(sdate)){
					if(etime <= stime){
						alert("选择的结束时间必须晚于开始时间！");
						return false;
					}
				}

				if($("#xingqi1").attr("checked") == "checked"){
					xingqi1 = "1";
				}else{
					xingqi1 = "0";
				}
				if($("#xingqi2").attr("checked") == "checked"){
					xingqi2 = "1";
				}else{
					xingqi2 = "0";
				}
				if($("#xingqi3").attr("checked") == "checked"){
					xingqi3 = "1";
				}else{
					xingqi3 = "0";
				}
				if($("#xingqi4").attr("checked") == "checked"){
					xingqi4 = "1";
				}else{
					xingqi4 = "0";
				}
				if($("#xingqi5").attr("checked") == "checked"){
					xingqi5 = "1";
				}else{
					xingqi5 = "0";
				}
				if($("#xingqi6").attr("checked") == "checked"){
					xingqi6 = "1";
				}else{
					xingqi6 = "0";
				}
				if($("#xingqi7").attr("checked") == "checked"){
					xingqi7 = "1";
				}else{
					xingqi7 = "0";
				}
			}
			
			if($('#templateSelect').val()=='0'){
				alert("请选择模板");
				return false;
			}
			if(programRemark == null || programRemark == ""){
				alert("请输入节目描述");
				return false;
			}
			
			var jsonData = "";
			var flag = "false";// break or not
			$("#programArea div").each(function(data){
				if(this.style.display != "none"){
					var key = this.id;// template model id
					var value1 = this.title; // template model dimension
					var value2 = this.style.zIndex; // template model gradation
					var value3 = this.id; // resource id
					var value4 = $('#'+this.id).text();
					if(value4.indexOf('双击')!=-1){
						alert("请在模板中选择相应资源");
						flag = "true";
					}
					jsonData += (key + ":" + value1 + ":" + value2 + ":" + value3 + ":" + value4 + "|");
				}
			});
			// each model has selected resource
			if(flag == "false"){
				jsonData = jsonData.substring(0, jsonData.length - 1);
				jsonData = encodeURI(jsonData);
				// radio value is 0
				if(radioValue == "0"){
					$.ajax({
						type: "POST",
						url: requestUrl,
						dataType:"html",
						data: {radioValue:radioValue,programId:programId,programName:programName,programRemark:programRemark,length:programLength,relation:jsonData},
						success: function(data){
							$.ajax({
								type: "POST",
								url: "/CPS/getProgramAction.html",
								dataType:"html",
								success: function(data){
									alert("节目保存成功");
									// delete html witch generated by jquery dialog
									$("#footerWrap").nextAll().remove();
									while (document.body.lastChild.nodeType === 3) {
									  document.body.removeChild(document.body.lastChild);
									}
									
									$("#content5").html(data);
									$("#content5").show("slow");
								},
								error:function(){
									alert($("#content5")+"加载失败");
								}
							});
						},
						error:function(){
							alert("加载失败");
						}
					});
				}
				// radio value is 1 or 2
				if(radioValue == "1" || radioValue == "2"){
					$.ajax({
						type: "POST",
						url: requestUrl,
						dataType:"html",
						data: {radioValue:radioValue,startDate:startDate,endDate:endDate,startHour:startHour,startMinute:startMinute,startSecond:startSecond,endHour:endHour,endMinute:endMinute,endSecond:endSecond,xingqi1:xingqi1,xingqi2:xingqi2,xingqi3:xingqi3,xingqi4:xingqi4,xingqi5:xingqi5,xingqi6:xingqi6,xingqi7:xingqi7,programId:programId,programName:programName,programRemark:programRemark,length:programLength,relation:jsonData},
						success: function(data){
							$.ajax({
								type: "POST",
								url: "/CPS/getProgramAction.html",
								dataType:"html",
								success: function(data){
									alert("节目保存成功");
									// delete html witch generated by jquery dialog
									$("#footerWrap").nextAll().remove();
									while (document.body.lastChild.nodeType === 3) {
									  document.body.removeChild(document.body.lastChild);
									}
									
									$("#content5").html(data);
									$("#content5").show("slow");
								},
								error:function(){
									alert($("#content5")+"加载失败");
								}
							});
						},
						error:function(){
							alert("加载失败");
						}
					});
				}
			}else{
				jsonData = "";
			}
		});

		// initial music
		$("a[rel='music']").each(function(obj){
			$('#'+this.id).css('width','268px');
			$f(""+this.id, "js/flowplayer/flowplayer-3.2.8.swf", {
			    // fullscreen button not needed here
			    plugins: {
			        controls: {
			            fullscreen: false,
			            height: 30,
			            autoHide: false
			        }
			    },
			    clip: {
			        autoPlay: false,
			        // optional: when playback starts close the first audio playback
			        onBeforeBegin: function() {
			            $f("player").close();
			        }
			    }
			});
		}); 
	});

	function selectResource(modeldiv){
		var type = modeldiv.id.split("_")[1].substr(0,1);
		$("#imageSourceList a").unbind("click");
		$("#videoSourceList a").unbind("click");
		$("#textSourceList a").unbind("click");
		if(type == "i"){
			//$("#imageSourceList").remove();
			$("#imageSourceList").dialog({
				height:520,
				width:900,
				modal:true
			});
			
			$("#imageSourceList a").click(function(){
				$('#imageSourceList').dialog("destroy");
				$("#"+modeldiv.id).html(this.title);
				//modeldiv.innerHTML = this.title;
			});
		}
		if(type == "v"){
			$("#videoSourceList").dialog({
				height:520,
				width:900,
				modal:true
			});
			$("#videoSourceList a").click(function(){
				$('#videoSourceList').dialog("destroy");
				modeldiv.innerHTML = this.title;
			});
		}
		if(type == "t"){
			$("#textSourceList").dialog({
				height:520,
				width:900,
				modal:true
			});
			$("#textSourceList a").click(function(){
				$('#textSourceList').dialog("destroy");
				modeldiv.innerHTML = this.title;
			});
		}
		if(type == "a" ){
			$("#audioSourceList").dialog({
				height:520,
				width:900,
				modal:true
			});
			$("#audioSourceList a").click(function(){
				$('#audioSourceList').dialog("destroy");
				modeldiv.innerHTML = this.title;
			});
		}
	}

	// select template
	function templateSelected(selectObj){
		var selectedValue = selectObj.value;
		if(selectedValue == "0"){
			selectObj.options[0].selected=true;
			$('#programArea').html(programAreaOrignalHtml);
		}else{
			// init programArea
			$('#programArea').html(programAreaOrignalHtml);
			
			// 将模板中的布局画到programArea中
			$("#programArea div input").each(function(data){
				var modelDiv = $("#"+(this.id)).parent();
				modelDiv.css("display","none");
				
				if(this.value.indexOf(selectedValue) != -1){

					var size = this.value.split("_")[2]+"_"+this.value.split("_")[3];
					if($('#resized').text() == ""){
						if(size == "4_3"){
							$('#programArea').css('width','800px');
							$('#programArea').css('height','600px');
						}
						if(size == "5_4"){
							$('#programArea').css('width','800px');
							$('#programArea').css('height','640px');
						}
						if(size == "16_9"){
							$('#programArea').css('width','800px');
							$('#programArea').css('height','450px');
						}
						if(size == "1280_1024"){
							$('#programArea').css('width','800px');
							$('#programArea').css('height','640px');
						}
						if(size == "3_4"){
							$('#programArea').css('width','600px');
							$('#programArea').css('height','800px');
						}
						if(size == "4_5"){
							$('#programArea').css('width','640px');
							$('#programArea').css('height','800px');
						}
						if(size == "9_16"){
							$('#programArea').css('width','450px');
							$('#programArea').css('height','800px');
						}
						$('#resized').text(size);
					}
					
					
					var dimensionVals = modelDiv.attr("title").split(",");
					var leftVal = dimensionVals[0];
					var topVal = dimensionVals[1];
					var widthVal = parseInt(dimensionVals[2]) - parseInt(leftVal);
					var heightVale = parseInt(dimensionVals[3]) - parseInt(topVal);
					
					modelDiv.css("left",leftVal+"px");
					modelDiv.css("top",topVal+"px");
					modelDiv.css("width",widthVal);
					modelDiv.css("height",heightVale);
					modelDiv.css("display","");
					modelDiv.css("position","absolute");
					modelDiv.css("overflow","hidden");
					
					if(this.id.split("_")[2] == "video"){
						modelDiv.css("background","red");
					}
					if(this.id.split("_")[2] == "audio"){
						modelDiv.css("background","black");
					}
					if(this.id.split("_")[2] == "image"){
						modelDiv.css("background","blue");
					}
					if(this.id.split("_")[2] == "text"){
						modelDiv.css("background","green");
					}
					if(this.id.split("_")[2] == "weather"){
						modelDiv.css("background","gray");
					}
					if(this.id.split("_")[2] == "clock"){
						modelDiv.css("background","gray");
					}
				}
			});
			$('#resized').text("");
		}
	}
	
	function initAlertBox(){
		$('#programName').val("");
		$('#programRemark').val("");
		if($('#isSelectLength').attr('checked') == 'checked'){
			$('#isSelectLength').removeAttr('checked');
		}
		$('#hours').val("时").attr("disabled","disabled");
		$('#minutes').val("分").attr("disabled","disabled");
		$('#seconds').val("秒").attr("disabled","disabled");
		$('#programArea').html(programAreaOrignalHtml);
		$("#templateSelect").get(0).selectedIndex=0;
	}
	
	// 添加节目
	function addProgram(){
		// 设置save按钮的请求url为添加program
		requestUrl = "/CPS/addProgramAction.html";
		initAlertBox();
		popupAlert("alertBoxAddProgram",1100,462,"content5");
		// 使弹出框自适应高度
		$('#alertBoxAddProgram').css("height","");
	}
	
	// 编辑&preview节目
	var Program = new function(){
		
		/**
		* 多选删除
		*/
		// 选中的id
		this.selectedIDs = "";
		this.checkboxSelectd = function(checkboxObj){
			if(checkboxObj.checked == true){
				this.selectedIDs += checkboxObj.id +",";
			}else{
				this.selectedIDs = this.selectedIDs.replace(checkboxObj.id + ",", "");
			}
		};
		
		var hh = null;
		var mm = null;
		var ss = null;

		this.preview = function(id){
			// initial preview_programArea
			$('#preview_programArea').html('');
			$.ajax({
				type: "POST",
				url: "/CPS/previewGetProgram.html",
				dataType:"json",
				data: {id:id},
				success: function(data){
					var templateWidth = data.programDTO.templateWidth;
					var templateHeight = data.programDTO.templateHeight;

					$('#preview_programArea_popup').css("width",parseInt(templateWidth)+30+"px");
					$('#preview_programArea_popup').css("height",parseInt(templateHeight)+30+"px");
					
					//让模板自动填充高度
					$('#preview_programArea_popup').css("height","");
					
					$('#preview_programArea').css("width",templateWidth+"px");
					$('#preview_programArea').css("height",templateHeight+"px");
					
					var onebyoneSize = data.programDTO.onebyoneList.length;
					for(var i = 0; i < onebyoneSize; i++){

						var player = null;
						
						var resource = data.programDTO.onebyoneList[i].resource;
						var resourceName = data.programDTO.onebyoneList[i].resourceName;
						var dimension = data.programDTO.onebyoneList[i].dimension;
						var direction = data.programDTO.onebyoneList[i].direction;
						var gradation = data.programDTO.onebyoneList[i].gradation;

						var dimensionVals = dimension.split(",");
						var leftVal = dimensionVals[0];
						var topVal = dimensionVals[1];
						var widthVal = parseInt(dimensionVals[2]) - parseInt(leftVal);
						var heightVal = parseInt(dimensionVals[3]) - parseInt(topVal);

						var modelDiv = document.createElement("div");
						modelDiv.style.position = "absolute";
						modelDiv.style.zIndex = gradation;
						modelDiv.style.left = leftVal + "px";
						modelDiv.style.top = topVal + "px";
						modelDiv.style.width = widthVal + "px";
						modelDiv.style.height = heightVal + "px";
						modelDiv.style.display = "";
						modelDiv.style.overflow = "hidden";
						modelDiv.style.background = "black";

						// font style for text and clock
						var color1 = data.programDTO.onebyoneList[i].fontColor;
						var colorNumbers = null;
						if(color1 != null && color1 != ""){
							colorNumbers = color1.split(".");
						}
						var color = "";
						var colorsize = null;
						if(colorNumbers != null && colorNumbers !=""){
							colorsize = colorNumbers.length;
						}
						for(var k =0 ; k < colorsize; k++){
							if(k == colorsize - 1){
								color += colorNumbers[k];
							}else{
								color += colorNumbers[k]+",";
							}
						}
						
						var font = data.programDTO.onebyoneList[i].fontName;
						var fontsize = data.programDTO.onebyoneList[i].fontSize;
						var border = data.programDTO.onebyoneList[i].fontBolder;
						var italic = data.programDTO.onebyoneList[i].fontItalic;
						var underline = data.programDTO.onebyoneList[i].fontUnder;
						
						if(resource.split('_')[1].indexOf('image') != -1){
							$(modelDiv).html('<img width="'+widthVal+'" height="'+heightVal+'" src="/resource/Images/'+resourceName+'" />');
						}
						if(resource.split('_')[1].indexOf('video') != -1){
							player = 'player_id';
							var flvname = resourceName.substring(0,resourceName.lastIndexOf("."))+".flv";
							var htmlContent = '<a href="/resource/Flvs/'+flvname+'"'
								+'style="width:'+widthVal+';height:'+heightVal+';" id="'+player+'"></a>';
							$(modelDiv).html(htmlContent);
						}
						if(resource.split('_')[1].indexOf('audio') != -1){
							$(modelDiv).html('<div style="color:white;">此处是音频，没有预览</div>');
						}
						if(resource.split('_')[1].indexOf('text') != -1){
							var textForPreview;
							var textListSize = data.textList.length;
							for(var j = 0; j < textListSize; j++){
								if(resourceName == data.textList[j].resourceName){
									textForPreview = data.textList[j].paramRemark;
									break;
								}
							}
							var htmlContent;
							if(direction == 'static'){
								htmlContent = '<div style="color:rgb('+color+');font-family:'+font+';font-size:'+fontsize+'px;font-weight: '+border+';font-style:'+italic+';text-decoration:'+underline+';background:gray;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'">'+textForPreview+'</div>';
							}
							if(direction == 'left'){
								htmlContent = '<marquee height="'+heightVal+'" width="'+widthVal+'" direction="left" onmouseout="this.start();" onmouseover="this.stop();" scrollAmount="1" scrollDelay="20" scrollleft="0" scrolltop="0">'
									+'<div style="color:rgb('+color+');font-family:'+font+';font-size:'+fontsize+'px;font-weight: '+border+';font-style:'+italic+';text-decoration:'+underline+';background:black;">'+textForPreview+'</div></marquee>';
							}
							if(direction == 'right'){
								htmlContent = '<marquee height="'+heightVal+'" width="'+widthVal+'" direction="right" onmouseout="this.start();" onmouseover="this.stop();" scrollAmount="1" scrollDelay="20" scrollleft="0" scrolltop="0">'
									+'<div style="color:rgb('+color+');font-family:'+font+';font-size:'+fontsize+'px;font-weight: '+border+';font-style:'+italic+';text-decoration:'+underline+';background:black;">'+textForPreview+'</div></marquee>';
							}
							if(direction == 'up'){
								htmlContent = '<marquee height="'+heightVal+'" width="'+widthVal+'" direction="up" onmouseout="this.start();" onmouseover="this.stop();" scrollAmount="1" scrollDelay="20" scrollleft="0" scrolltop="0">'
									+'<div style="color:rgb('+color+');font-family:'+font+';font-size:'+fontsize+'px;font-weight: '+border+';font-style:'+italic+';text-decoration:'+underline+';background:black;">'+textForPreview+'</div></marquee>';
							}
							if(direction == 'down'){
								htmlContent = '<marquee height="'+heightVal+'" width="'+widthVal+'" direction="down" onmouseout="this.start();" onmouseover="this.stop();" scrollAmount="1" scrollDelay="20" scrollleft="0" scrolltop="0">'
									+'<div style="color:rgb('+color+');font-family:'+font+';font-size:'+fontsize+'px;font-weight: '+border+';font-style:'+italic+';text-decoration:'+underline+';background:black;">'+textForPreview+'</div></marquee>';
							}
							$(modelDiv).html(htmlContent);
						}
						if(resource.split('_')[1].indexOf('weather') != -1){
							var weatherFrame = '<iframe src="http://www.thinkpage.cn/weather/weather.aspx?uid=&cid=101020100&l=&p=CMA&a=1&u=C&s=1&m=1&x=1&d=2&fc=&bgc=FFFFFF&bc=000000&ti=1&in=1&li=2&ct=iframe" frameborder="0" scrolling="no" width="200" height="260" allowTransparency="true"></iframe>';
							$(modelDiv).html(weatherFrame);
						}
						if(resource.split('_')[1].indexOf('clock') != -1){
							var style = 'color:rgb('+color+');font-family:'+font+';font-size:'+fontsize+'px;font-weight: '+border+';font-style:'+italic+';text-decoration:'+underline+';background:black;';
							$(modelDiv).attr('style',style);
							var currentMillis = getCurrentMillis();
							$(modelDiv).attr('id',currentMillis);
							setInterval(_showTimeForPrivew(currentMillis),1000);
						}
						
						document.getElementById('preview_programArea').appendChild(modelDiv);
						if(player != null){
							flowplayer(player, "js/flowplayer/flowplayer-3.2.8.swf");
						}
					}

					popupAlertWithCloseFunction('preview_programArea_popup', parseInt(templateWidth)+30, parseInt(templateHeight)+30, 'content5', "closeVideo");
				},
				error:function(){
					alert("加载失败");
				}
			});
		};
		
		this.edit = function(id){
			$.ajax({
				type: "POST",
				url: "/CPS/editGetProgram.html",
				dataType:"json",
				data: {id:id},
				success: function(data){
					// 设置save按钮的请求url为编辑program
					requestUrl = "/CPS/editProgram.html";
					programId = id;
					initAlertBox();
					popupAlert("alertBoxAddProgram",1100,462,"content5");
					//让模板自动填充高度
					$('#alertBoxAddProgram').css("height","");
					$('#programName').val(data.programDTO.name);
					var allseconds = data.programDTO.length;

					//if allseconds == 0
					if(allseconds == 0){
						if($('#isSelectLength').attr('checked') == 'checked'){
							$('#isSelectLength').removeAttr('checked');
						}
						$('#hours').val("时").attr("disabled","disabled");
						$('#minutes').val("分").attr("disabled","disabled");
						$('#seconds').val("秒").attr("disabled","disabled");
					}else{
						$('#isSelectLength').attr('checked','checked');
						// 给hh mm ss赋值
						Program.time_To_hhmmss(allseconds);
						
						$('#hours').val(hh);
						$('#minutes').val(mm);
						$('#seconds').val(ss);
					}
					$('#programRemark').val(data.programDTO.remark);
					
					// 设置select默认选中项
					$('#templateSelect').val(data.programDTO.templateId);
					$('#templateSelect').trigger("change");
					
					var size = data.programDTO.onebyoneList.length;
					var templateID = data.programDTO.templateId;
					for(var i = 0; i < size; i++){
						var resourceId = data.programDTO.onebyoneList[i].resource;
						var resourceName = data.programDTO.onebyoneList[i].resourceName;
						
						var hiddenId = resourceId+"_"+resourceId.split("_")[1].replace(/\d+/g,'');
						$('#'+resourceId).html('<input type="hidden" id="'+hiddenId+'" value="'+templateID+'"/>'+resourceName);
					}
				},
				error:function(){
					alert("加载失败");
				}
			});
		};
		
		this.time_To_hhmmss = function (seconds){
			//传入的时间为空或小于0
			if(seconds==null||seconds<0){
			    return;
			}
			//得到小时
			hh=seconds/3600|0;
			seconds=parseInt(seconds)-hh*3600;
			//得到分
			mm=seconds/60|0;
			//得到秒
			ss=parseInt(seconds)-mm*60;
		};

		// pre-view video
		this.replaceFlvUrl = function(aObj,url){
			$('#player').attr('href',url);
			var player = $f("player","js/flowplayer/flowplayer-3.2.8.swf",{
			    clip: {
			        autoPlay: true,
			        autoBuffering: true
			    }
			});
			
			$("#flowplayerdiv").overlay( {
				// use the Apple effect for overlay
				effect : 'apple',
				// when overlay is opened, load our player
				onLoad : function() {
				},
				// when overlay is closed, unload our player
				onClose : function() {
					player.unload();
					$("#flowplayerdiv > #player").html("");
				}
			});
			
			// play the clip specified in href- attribute with Flowplayer
			$("#flowplayerdiv").overlay().load();
		};

		this.changeCheckbox = function(obj){
			if($(obj).attr("checked") == "checked"){
				$('#hours').removeAttr("disabled");
				$('#minutes').removeAttr("disabled");
				$('#seconds').removeAttr("disabled");
			}else{
				$('#hours').attr("disabled","disabled");
				$('#minutes').attr("disabled","disabled");
				$('#seconds').attr("disabled","disabled");			
			}
		};

		// select cycle type
		this.selectCycle = function(obj){
			if(obj.value == "0"){
				$("#cycle").css("display","none");
			}
			if(obj.value == "1" || obj.value == "2"){
				$("#cycle").css("display","");
			}
		};
	};

	var programSelectAll = false;
	$("#programCheckboxTh").bind("click",function(){
		Program.selectedIDs = "";
		if(programSelectAll){
			$("#programForm [type='checkbox']").removeAttr("checked");//取消全选
			Program.selectedIDs = "";
			programSelectAll = false;
		}else{
			$("#programForm [type='checkbox']").each(function(){
				//alert(this.id);
				//alert(this.name);
				Program.selectedIDs += this.id+",";
				if(!this.checked){
					$("#programForm [type='checkbox']").attr("checked",'true');//全选
					programSelectAll = true;
					//return false;
				}
			});
		}
		return false;
	});
</script>
</head>

<body>
	<div>
		<a class="buttonStyle" href="javascript:addProgram();">添加节目</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:delSelectedXmlContetnAjaxCall(Program.selectedIDs,'program','content5')" class="buttonStyle">删除所有选择的节目</a>
	</div>
	<br>
	<s:if test="#request.programList.size()==0">
		<span>暂无数据</span>
	</s:if>
	<s:else>
		<form id="programForm" name="programForm" method="post" action="">
			<table class="sTable tdNowap900">
				<thead>
					<tr>
						<th id="programCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>全选</strong></th>
						<th><strong>名称</strong></th>
						<th><strong>备注</strong></th>
						<th><strong>最后更新</strong></th>
						<th><strong>作者</strong></th>
						<th><strong>时长(秒)</strong></th>
						<th><strong>模板(ID)</strong></th>
						<th><strong>节目资源</strong></th>
						<th><strong>预览</strong></th>
						<th><strong>删除 </strong></th>
						<!-- <th><strong>编辑 </strong></th> -->
					</tr>
				</thead>
				<s:iterator value="programList" id="" status="" var="programObj">
				<tr class="oddRow">
					<td>
						<c:if test="${programObj.canBeDelete == false}">
								不能选择
						</c:if>
						<c:if test="${programObj.canBeDelete == true}">
							<input type="checkbox" id="${programObj.id }" onchange="javascript:Program.checkboxSelectd(this)">
						</c:if>
					</td>
					<td>${programObj.name }</td>
					<td title="${programObj.remark }">${programObj.remark }</td>
					<td>${programObj.createtime }</td>
					<td>${programObj.createby }</td>
					<td>${programObj.length }</td>
					<td title="${programObj.templateId }">${programObj.templateId }</td>
					<td>
						<a class="hrefColor" onmouseout="javascript:hidTip('resourcediv_${programObj.id }');" onmouseover="javascript:showTip('resourcediv_${programObj.id }');" href="javascript:void(0);">资源列表</a>
						<div id="resourcediv_${programObj.id }" style="margin-left:60px;width:340px;display: none;z-index: 3000;background: gray;position: absolute;">
							<s:iterator value="#programObj.onebyoneList" status="indexids" var="onebyone">
								<div style="float: left;clear: both;color: white;text-align: left;">
									${indexids.index+1 }.资源名称：${onebyone.resourceName }<br />
									   资源坐标：${onebyone.dimension }
								</div>
							</s:iterator>
						</div>
					</td>
					<td><a class="hrefColor" href="javascript:Program.preview('${programObj.id }');">预览</a></td>
					<td>
						<ul>
							<li>
								<c:if test="${programObj.canBeDelete == false}">
									<div style="cursor: help;" title="由于播表中已经使用到此节目，不能删除，若要删除，请先删除使用到此节目的播表">不能删除</div>
								</c:if>
								<c:if test="${programObj.canBeDelete == true}">
									<a class="hrefColor" href="javascript:delXmlContetnAjaxCall('${programObj.id }','program','content5')">删除</a>
								</c:if>
							</li>
						</ul>
					</td>
					<!-- <td><a class="hrefColor" href="javascript:Program.edit('${programObj.id }')">编辑</a></td> -->
				</tr>
				</s:iterator>
			</table>
		</form>
	</s:else>

	<!-- add program -->
	<div id="alertBoxAddProgram" style="display: none;">
		<div id="programConfig" style="float:left;z-index: 1000;">
			<div style="margin: 10px 0 0 20px;">节目名称：<label class="smallInput"><input id="programName" class="inputS" type="text" size="28" name="programName"></label></div>
			<div style="margin: 10px 0 0 20px;"><input onclick="Program.changeCheckbox(this);" id="isSelectLength" type="checkbox" /><font style="cursor: help;color: red;" title="如果不选择，默认节目放完，如果选择，则可指定节目播放的时长">【?】</font>播放时长：<br/>
			<input disabled="disabled" id="hours" type="text" size="6" name="hours" onblur="javascript:if(this.value=='')this.value='0'" onfocus="javascript:this.value=''" onKeyUp="this.value=this.value.replace(/\D/g,'')" >
			<input disabled="disabled" id="minutes" type="text" size="6" name="minutes" onblur="javascript:if(this.value=='')this.value='0'" onfocus="javascript:this.value=''" onKeyUp="this.value=this.value.replace(/\D/g,'')" >
			<input disabled="disabled" id="seconds" type="text" size="6" name="seconds" onblur="javascript:if(this.value=='')this.value='0'" onfocus="javascript:this.value=''" onKeyUp="this.value=this.value.replace(/\D/g,'')" >
			</div>
			
			<div style="margin: 10px 0 0 20px;">
				<input id="radio0" onclick="javascript:Program.selectCycle(this);" value="0" checked="checked" type="radio" name="settimer"/>无定时<font style="cursor: help;color: red;" title="无定时，正常播放节目">【?】</font>
				<input id="radio1" onclick="javascript:Program.selectCycle(this);" value="1" type="radio" name="settimer"/>循环<font style="cursor: help;color: red;" title="定时类型为循环播放，按照开始日期到结束日期，且星期显示的设置的日期，然后每天根据开始时间到结束时间播放节目">【?】</font>
				<input id="radio2" onclick="javascript:Program.selectCycle(this);" value="2" type="radio" name="settimer"/>指定<font style="cursor: help;color: red;" title="指定播放，按照开始日期的开始时间一直到结束日期的结束时间播放节目">【?】</font></div>
			<div id="cycle" style="display: none;">
			<div style="margin: 10px 0 0 20px;">
				<font color="gray">小提示：如果鼠标点进下面的时间输入框中</font><br/>
				<font color="gray">没有自动弹出时间选择，请按F5刷新页面。</font><br/>
				开始日期：<input id="pStartdate" type="text" size="14" /><br/>
				结束日期：<input id="pEnddate" type="text" size="14" />
			</div>
			<div style="margin: 10px 0 0 20px;">
				开始时间：<input onKeyUp="this.value=this.value.replace(/\D/g,'')" id="pStarttimeHour" type="text" size="2" maxlength="2" />时，
				<input onKeyUp="this.value=this.value.replace(/\D/g,'')" id="pStarttimeMinute" type="text" size="2" maxlength="2" />分，
				<input onKeyUp="this.value=this.value.replace(/\D/g,'')" id="pStarttimeSecond" type="text" size="2" maxlength="2" />秒，<br/>
				结束时间：<input onKeyUp="this.value=this.value.replace(/\D/g,'')" id="pEndtimeHour" type="text" size="2" maxlength="2" />时，
				<input onKeyUp="this.value=this.value.replace(/\D/g,'')" id="pEndtimeMinute" type="text" size="2" maxlength="2" />分，
				<input onKeyUp="this.value=this.value.replace(/\D/g,'')" id="pEndtimeSecond" type="text" size="2" maxlength="2" />秒，<br/>
			</div>
			<div style="margin: 10px 0 0 20px;">
				<input id="xingqi1" type="checkbox" name="dayofweek" />星期一
				<input id="xingqi2" type="checkbox" name="dayofweek" />星期二
				<input id="xingqi3" type="checkbox" name="dayofweek" />星期三
				<input id="xingqi4" type="checkbox" name="dayofweek" />星期四<br/>
				<input id="xingqi5" type="checkbox" name="dayofweek" />星期五
				<input id="xingqi6" type="checkbox" name="dayofweek" />星期六
				<input id="xingqi7" type="checkbox" name="dayofweek" />星期日
			</div>
			</div>
			<div style="margin: 10px 0 0 20px;">选择模板：<label class="smallInput"><s:select id="templateSelect" onchange="javascript:templateSelected(this)" name="template" list="templateList" listKey="id" listValue="name" headerKey="0" headerValue="请选择模板" label="请选择模板" cssStyle="width:100%"/></label></div>
			<div style="margin: 10px 0 0 20px;">节目描述：<label class="textareaDiv"><textarea id="programRemark" class="textarea" rows="5" cols="80" name="programRemark"></textarea><br></label></div>
			<div style="position: relative;cursor: pointer;margin:10px 0 0 20px;float: left;">
				<a class="buttonStyle" id="btnSaveProgram">保存</a>
			</div>
			<div style="position: relative;cursor: pointer;margin:10px 0 0 20px;float: left;">
				<a class="buttonStyle" id="btnCancelProgram" href="javascript:initAlertBox();">重置</a>
			</div>
		</div>
		<div id="programArea" class="programArea">
			<s:iterator value="templateList" var="templateObj">
				<s:iterator value="#templateObj.models" var="model">
					<c:if test="${model.type =='weather'}">
						<div title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; display:none;text-align:center;font-size:12px;color:white;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}">天气区域</div>
					</c:if>
					<c:if test="${model.type =='clock'}">
						<div title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; display:none;text-align:center;font-size:12px;color:white;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}">时钟区域</div>
					</c:if>
					<c:if test="${model.type =='text'}">
						<c:if test="${model.direction =='static'}">
							<div ondblclick="javascript:selectResource(this)" title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; display:none;text-align:center;font-size:12px;color:white;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}">双击我选择${model.type }类型资源</div>
						</c:if>
						<c:if test="${model.direction !='static'}">
							<div ondblclick="javascript:selectResource(this)" title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; display:none;text-align:center;font-size:12px;color:white;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}"><marquee direction="${model.direction }">双击我选择${model.type }类型资源</marquee></div>
						</c:if>
					</c:if>
					<c:if test="${model.type !='weather' && model.type !='clock' && model.type !='text'}">
						<div ondblclick="javascript:selectResource(this)" title="${model.dimension}" id="${model.id }" style="z-index: ${model.gradation }; display:none;text-align:center;font-size:12px;color:white;" ><input id="${model.id }_${model.type }" type="hidden" value="${templateObj.id }_${templateObj.size}">双击我选择${model.type }类型资源</div>
					</c:if>
				</s:iterator>
			</s:iterator>
		</div>
		<div id="resized" style="height:1px; margin-top:-1px;clear: both;overflow:hidden;"></div>
	</div>
	<!-- add program -->
	
	<div id="videoSourceList" class="hid">
		<table class="sTable">
			<thead>
				<tr>
					<th><strong>视频名称</strong></th>
					<th><strong>视频备注</strong></th>
					<th><strong>预览</strong></th>
					<th><strong>选择</strong></th>
				</tr>
			</thead>
			<s:iterator value="videoList" id="" status="" var="video">
			<tr class="oddRow">
				<td class="firstCol">${video.resourceName }</td>
				<td>${video.paramRemark }</td>
				<td class="secondCol" style="cursor: pointer;" onclick="javascript:Program.replaceFlvUrl(this,'/resource/Flvs/${video.flvname }');">点击预览</td>
				<td class="secondCol"><a title="${video.resourceName }" href="javascript:void(0);">点击选择</a></td>
			</tr>
			</s:iterator>
		</table>
	</div>
	
	<!-- image -->
	<div id="imageSourceList" class="hid">
		<table class="sTable">
			<thead>
				<tr>
					<th><strong>图片名称</strong></th>
					<th><strong>图片备注</strong></th>
					<th><strong>预览</strong></th>
					<th><strong>选择</strong></th>
				</tr>
			</thead>
			<s:iterator value="imageList" id="" status="" var="image">
			<tr class="oddRow">
				<td class="firstCol">${image.resourceName }</td>
				<td>${image.paramRemark }</td>
				<td class="secondCol"><span style="cursor: pointer;" id="/resource/Images/${image.resourceName }" class="preview" title="Lake and a mountain">预览</span></td>
				<td class="secondCol"><a id="${image.resourceName }" title="${image.resourceName }" href="javascript:void(0);">点击选择</a></td>
			</tr>
			</s:iterator>
		</table>
	</div>
	
	<!-- audio -->
	<div id="audioSourceList" class="hid">
		<table class="sTable">
			<thead>
				<tr>
					<th><strong>音频名称</strong></th>
					<th><strong>音频备注</strong></th>
					<th><strong>试听</strong></th>
					<th><strong>选择</strong></th>
				</tr>
			</thead>
			<s:iterator value="audioList" id="" status="" var="audio">
			<tr class="oddRow">
				<td class="firstCol">${audio.resourceName }</td>
				<td>${audio.paramRemark }</td>
				<td class="secondCol"><div style="width: 170px;overflow: hidden;padding-left: 50px;"><a rel="music" id="music${audio.id }" style="display:block;width:648px;height:30px;" href="/resource/Audios/${audio.resourceName }"></a></div></td>
				<!--  <td class="secondCol" onclick="javascript:unsupport();">点击试听</td>-->
				<td class="secondCol"><a title="${audio.resourceName }" href="javascript:void(0);">点击选择</a></td>
			</tr>
			</s:iterator>
		</table>
	</div>
	
	<!-- text -->
	<div id="textSourceList" class="hid">
		<table class="sTable">
			<thead>
				<tr>
					<th><strong>文本名称</strong></th>
					<th><strong>文本备注</strong></th>
					<th><strong>预览</strong></th>
					<th><strong>选择</strong></th>
				</tr>
			</thead>
			<s:iterator value="textList" id="" status="" var="text">
			<tr class="oddRow">
				<td class="firstCol">${text.resourceName }</td>
				<td>${text.resourceName }</td>
				<td class="secondCol" title="${text.paramRemark }" style="cursor: pointer;">预览</td>
				<td class="secondCol"><a title="${text.resourceName }" href="javascript:void(0);">点击选择</a></td>
			</tr>
			</s:iterator>
		</table>
	</div>
	<!-- flowplayer div -->
	<div id="flowplayerdiv" class="overlay" style="background-image: url(images/white.png)">
		<a id="player" href="/resource/Flvs/1332676825470_2.flv"></a>
	</div>
	<div id="preview_programArea_popup" class="programArea" style="display: none;">
		<div id="preview_programArea"  style="margin-top: 30px;position: relative;border: 1px solid;background: black;">
			
		</div>
	</div>
	<div>
		<a class="buttonStyle" href="javascript:scroll(0,0)">返回顶部</a>
	</div>
</body>
</html>