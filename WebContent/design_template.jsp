<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Template Design</title>
<script src="js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
<script>
$(document).ready(function(){
    $("#templateForm").validationEngine();
});
</script>
<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css"/>
<style type="text/css">
.jqHandle {
	background-image: url("images/ui-icons_222222_256x240.png");
	background-position: -64px -224px;
	height: 16px;
	width: 16px;
}

.jqDrag {
	width: 100%;
	cursor: move;
}

.jqResize {
	width: 15px;
	position: absolute;
	bottom: 0;
	right: 0;
	cursor: se-resize;
}

.jqDnR {
	position: absolute;
	cursor: move;
	font-size: 0.8em;
	color: white;
	overflow: hidden;
}

.toolArea {
	width: 200px;
	height: 400px;
	border: 1px solid;
	float: left;
}

.operationArea {
	border: 1px solid;
	position: relative;
	width: 800px;
	height: 640px;
	left: 250px;
}

.toolBox {
	width: 90px;
	height: 90px;
	margin: 8px;
	float: left;
}
</style>

<script type="text/javascript">

	var displayXY = "";
	var beishu = null;

	// set text select to default value
	function setTextToDefaultValue(){
		$("#textColorSelect").val("255.255.255");
		$("#textFontSizeSelect").val("72");
		$("#textFontBolderSelect").val("0");
		$("#textNextLineSelect").val("1");
		$("#textHAlignSelect").val("2");
		$("#textVAlignSelect").val("2");
		$("#textDirectionSelect").val("static");
		$("#textSpeedSelect").val("1");
		$("#textDelaySelect").val("0");
	}

	// set clock select to default value
	function setClockToDefaultValue(){
		$("#clockColorSelect").val("255.255.255");
		$("#clockFontSizeSelect").val("18");
		$("#clockFontBolderSelect").val("0");
		$("#clockModeSelect").val("1");
		$("#clockFormatSelect").val("1");
	}
	// unbind mousedown and mouseup
	function unbindAll(){
		$("#operationArea").unbind("mousedown");
		$("#operationArea").unbind("mouseup");
	}

	// bind mousedown and mouseup
	function bindAll(idindex,type){
		document.getElementById("content4").style.cursor= 'crosshair';
		var startX = 0;
		var startY = 0;
		var endX = 0;
		var endY = 0;
		
		$("#operationArea").unbind("mousedown").bind("mousedown", function(event) {
			startX = event.pageX - $("#operationArea").offset().left;
			startY = event.pageY - $("#operationArea").offset().top;
		});
		
		$("#operationArea").unbind("mouseup").bind("mouseup", function(event) {
			endX = event.pageX - $("#operationArea").offset().left;
			endY = event.pageY - $("#operationArea").offset().top;
			TemplateDesign.generateDIV(startX,startY,endX,endY,type+(idindex),type);
			document.getElementById("content4").style.cursor= 'auto';
			$("#operationArea").unbind("mousedown");
			$("#operationArea").unbind("mouseup");
			$("#operationArea").focus();
		});
	}
	
	// callback function when close the text
	function closeText(){
		document.getElementById("content4").style.cursor = 'default';
		unbindAll();
	}

	// callback function when close the clock
	function closeClock(){
		document.getElementById("content4").style.cursor = 'default';
		unbindAll();
	}
	var textDirectionValue = "";
	var clockPramsValue = "";

	$(function() {
		$("#code h1").click(function() {
			$("p").toggle();
		});
	});

	var TemplateDesign = new function(){
		
		var templateName = "";
		var tempalteRemark = "";
		var templateSize = "";
		var model = "";
		
		var zindex = 1;
		var idindex = 1;
		
		// 根据坐标生成div
		this.generateDIV = function (sx,sy,ex,ey,id,type){
			var div = "";
			if(type=="video"){
				div = '<div id="'+id+'" class="jqDnR" style="background:red;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'"><span>这里填充的是'+type+'类型'+displayXY+'</span><div class="jqHandle jqResize"></div></div>';
			}
			if(type=="image"){
				div = '<div id="'+id+'" class="jqDnR" style="background:blue;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'"><span>这里填充的是'+type+'类型'+displayXY+'</span><div class="jqHandle jqResize"></div></div>';
			}
			if(type=="audio"){
				div = '<div id="'+id+'" class="jqDnR" style="background:black;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'"><span>这里填充的是'+type+'类型'+displayXY+'</span><div class="jqHandle jqResize"></div></div>';
			}
			if(type=="text"){
				div = '<div id="'+id+'" class="jqDnR" style="background:green;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'"><span>这里填充的是'+type+'类型'+displayXY+'</span><input type="hidden" id="'+id+'_directionValueId" value="'+textDirectionValue+'"/><div class="jqHandle jqResize"></div></div>';
			}
			if(type=="weather"){
				var weatherFrame1 = '<iframe name="weather_inc" src="http://tianqi.xixik.com/cframe/10" width="300" height="25" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>';
				var weatherFrame2 = '<iframe name="weather_inc" src="http://tianqi.xixik.com/cframe/11" width="500" height="15" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>';
				var weatherFrame = '<iframe src="http://www.thinkpage.cn/weather/weather.aspx?uid=&cid=101020100&l=&p=CMA&a=1&u=C&s=1&m=1&x=1&d=2&fc=&bgc=FFFFFF&bc=000000&ti=1&in=1&li=2&ct=iframe" frameborder="0" scrolling="no" width="200" height="260" allowTransparency="true"></iframe>';
				div = '<div id="'+id+'" class="jqDnR" style="background:gray;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'">'+weatherFrame+'<div class="jqHandle jqResize"></div></div>';
			}
			if(type=="clock"){
				var clockPramsValueTemp = clockPramsValue.substring(0,clockPramsValue.length - 1); 
				var params = clockPramsValueTemp.split("|");
				var paramsSize = params.length;
				var color = params[paramsSize - 8].replace(/\./ig, ",");
				var font = params[paramsSize - 7];
				var fontsize = params[paramsSize - 6];
				var border = params[paramsSize - 5];
				var italic = params[paramsSize - 4];
				var underline = params[paramsSize - 3];
				var displaypart = params[paramsSize - 2];
				var type = params[paramsSize - 1];
				if(border == "1"){
					border = "bold";
				}else{
					border = "normal";
				}
				if(italic == "1"){
					italic = "italic";
				}else{
					italic = "normal";
				}
				if(underline == "1"){
					underline = "underline";
				}else{
					underline = "none";
				}

				var text = "这里是时间";
				if(displaypart == "1"){
					if(type == "1"){
						text = "2012年08月08日 星期三 08时08分08秒";
					}
					if(type == "2"){
						text = "2012-08-08 星期三 08:08:08";
					}
					if(type == "3"){
						text = "08/08/2012 星期三 08:08:08";
					}
				}
				if(displaypart == "2"){
					if(type == "1"){
						text = "2012年08月08日";
					}
					if(type == "2"){
						text = "2012-08-08";
					}
					if(type == "3"){
						text = "08/08/2012";
					}
				}
				if(displaypart == "3"){
					if(type == "1"){
						text = "08月08日";
					}
					if(type == "2"){
						text = "08-08";
					}
					if(type == "3"){
						text = "08/08";
					}
				}
				if(displaypart == "4"){
					if(type == "1"){
						text = "08时08分08秒";
					}
					if(type == "2"){
						text = "08:08:08";
					}
					if(type == "3"){
						text = "08:08:08";
					}
				}
				if(displaypart == "5"){
					if(type == "1"){
						text = "08时08分";
					}
					if(type == "2"){
						text = "08:08";
					}
					if(type == "3"){
						text = "08:08";
					}
				}
				if(displaypart == "6"){
					text = "星期三";
				}
				if(displaypart == "7"){
					if(type == "1"){
						text = "2012年";
					}
					if(type == "2"){
						text = "2012";
					}
					if(type == "3"){
						text = "2012";
					}
				}
				if(displaypart == "8"){
					if(type == "1"){
						text = "08月";
					}
					if(type == "2"){
						text = "08";
					}
					if(type == "3"){
						text = "08";
					}
				}
				if(displaypart == "9"){
					if(type == "1"){
						text = "08日";
					}
					if(type == "2"){
						text = "08";
					}
					if(type == "3"){
						text = "08";
					}
				}
				if(displaypart == "10"){
					if(type == "1"){
						text = "08时";
					}
					if(type == "2"){
						text = "08";
					}
					if(type == "3"){
						text = "08";
					}
				}
				if(displaypart == "11"){
					if(type == "1"){
						text = "08分";
					}
					if(type == "2"){
						text = "08";
					}
					if(type == "3"){
						text = "08";
					}
				}
				if(displaypart == "12"){
					if(type == "1"){
						text = "08秒";
					}
					if(type == "2"){
						text = "08";
					}
					if(type == "3"){
						text = "08";
					}
				}
				
				div = '<div id="'+id+'" class="jqDnR" style="color:rgb('+color+');font-family:'+font+';font-size:'+fontsize+'px;font-weight: '+border+';font-style:'+italic+';text-decoration:'+underline+';background:gray;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'">'+text+'<div id="'+id+id+'"></div><input type="hidden" id="'+id+'_clockParamsId" value="'+clockPramsValue+'"/><div class="jqHandle jqResize"></div></div>';
			}
			$("#operationArea").append(div);
			if(type=="text"){
				// limit the height
				$('#'+id).jqResizeWithLimitHeight('.jqResize').jqDrag();
				$('#'+id).css("height","72px");
			}else{
				$('#'+id).jqResize('.jqResize').jqDrag();
			}
			// clock preview
			if(type=="clock"){
				//setInterval(_showTime(id+id),1000);
			}
		};
		
		// 清空模板
		this.cancel = function (){
			if(confirm('确定要重置，重置将清空所有布局?')) {
				$("#operationArea").empty();
		        //return true;
		    }
		    //return false;
		};
		
		// 保存模板
		this.saveTemplate = function (){
			if($("#operationArea:has(div)").length == 0){
				alert("面板中没有内容，无需保存");
			}else{
				// alert(model);
				if(model != "" && model != null){
					//return;
					model = "";
				}
				// 迭代operationArea中的div
				$("#operationArea").children().each(function(){
					var name = this.id;
					var gradation = $("#"+name).css("z-index");
					var sx = $("#"+name).css("left").split("px")[0];
					var sy = $("#"+name).css("top").split("px")[0];
					var ex = parseInt(sx) + parseInt($("#"+name).css("width").split("px")[0]);
					var ey = parseInt(sy) + parseInt($("#"+name).css("height").split("px")[0]);
					//alert(this.id.substring(0,4) == 'text');
					if(this.id.substring(0,4) == 'text'){
						var hiddenId = this.id+'_directionValueId';
						model = model + name+"_"+gradation+"_"+sx+"_"+sy+"_"+ex+"_"+ey+"_"+$('#'+hiddenId).val()+",";
						//alert(model);
					}
					if(this.id.substring(0,4) == 'cloc'){
						var hiddenId = this.id+'_clockParamsId';
						model = model + name+"_"+gradation+"_"+sx+"_"+sy+"_"+ex+"_"+ey+"_"+$('#'+hiddenId).val()+",";
						//alert(model);
					}
					if(this.id.substring(0,4) != 'text' && this.id.substring(0,4) != 'cloc'){
						model = model + name+"_"+gradation+"_"+sx+"_"+sy+"_"+ex+"_"+ey+",";
					}
				});
				//alert(model);
				popupAlert("alertBox",250,280,"content4");
				
				
			}
		};
		
		// 弹出窗口的save按钮
		this.save = function(){

			if($("#tempalteName").val() == null || $("#tempalteName").val() == ""){
				alert("请输入模板名称");
			}else{
				if($("#tempalteRemark").val() == null || $("#tempalteRemark").val() == ""){
					alert("请输入模板备注");
				}
			}
			if($("#tempalteName").val() != null && $("#tempalteName").val() != "" && $("#tempalteRemark").val() != null && $("#tempalteRemark").val() != ""){
				templateName = encodeURI($("#tempalteName").val());
				tempalteRemark = encodeURI($("#tempalteRemark").val());
				templateSize = $('#modelsize').val();
				model = encodeURI(model);
				
				TemplateDesign.submitTemplate(templateName,tempalteRemark,templateSize,model);
			}
		};
		
		// 保存并提交temlate
		this.submitTemplate = function (templateName,tempalteRemark,templateSize,model){
			$.ajax({
				type: "POST",
				url: "/CPS/addTemplateAction.html",
				data: {"templateName":templateName,"tempalteRemark":tempalteRemark,"templateSize":templateSize,"model":model},
				success: function(data){
					alert("模板保存成功,查看请进入模板管理页签");
					$.ajax({
						type: "POST",
						url: "/CPS/templateDesignAction.html",
						dataType:"html",
						success: function(data){
							$("#content4").html(data);
							$("#content4").show("slow");
						},
						error:function(){
							alert($("#content6")+"加载失败");
						}
					});
				},
				error:function(){
					alert("error");
				}
			});
		};


		// set font params
		this.textDerection = function(){
			var length = 0;
			textDirectionValue = "";
			$("#textSelects select").each(function(j){
				if($(this).val() == "default"){
					alert(this.options[0].text);
					return false;
				}else{
					textDirectionValue = textDirectionValue + $(this).val()+"|";
					length = length+1;
				}
			});

			if(length != 12){
				textDirectionValue = "";
				return false;
			}else{
			}
			
			$('#textDirection_close').trigger('click');
			//bind the mousedown and mouseup function because trigger the close funcion before
			bindAll(idindex++,"text");
			
			// set font parameters to default
			setTextToDefaultValue();
		};
		
		// set clock params
		this.clockParams = function(){
			var length = 0;
			$("#clockParamsDiv select").each(function(j){
				if($(this).val() == "default"){
					alert(this.options[0].text);
					return false;
				}else{
					clockPramsValue = clockPramsValue + $(this).val()+"|";
					length = length+1;
				}
			});

			// 8 means the params select number
			if(length != 8){
				clockPramsValue = "";
				return false;
			}else{
			}
			
			$('#clockParamsDiv_close').trigger('click');
			//bind the mousedown and mouseup function because trigger the close funcion before
			bindAll(idindex++,"clock");
			
			// set font parameters to default
			setClockToDefaultValue();
		};
		
		// 点击Tool面板中的Icon
		this.toolIconClicked = function (toolIcon) {

			if(toolIcon.id == 'text'){
				popupAlertWithCloseFunction('textDirection', 200, 340, 'content4',"closeText");
			}
			if(toolIcon.id == 'clock'){
				popupAlertWithCloseFunction('clockParamsDiv', 200, 340, 'content4',"closeClock");
			}
			
			// 改变按钮的状态：按下，凸起
			document.getElementById("content4").style.cursor= 'crosshair';
			var startX = 0;
			var startY = 0;
			var endX = 0;
			var endY = 0;
			
			$("#operationArea").unbind("mousedown").bind("mousedown", function(event) {
				startX = event.pageX - $("#operationArea").offset().left;
				startY = event.pageY - $("#operationArea").offset().top;
			});
			
			$("#operationArea").unbind("mouseup").bind("mouseup", function(event) {
				endX = event.pageX - $("#operationArea").offset().left;
				endY = event.pageY - $("#operationArea").offset().top;
				TemplateDesign.generateDIV(startX,startY,endX,endY,toolIcon.id+(idindex++),toolIcon.id);
				document.getElementById("content4").style.cursor= 'auto';
				$("#operationArea").unbind("mousedown");
				$("#operationArea").unbind("mouseup");
				$("#operationArea").focus();
			});
		};

		this.preValue = null;
		this.saveOriginalSelectValue = function(){
			TemplateDesign.preValue = $('#modelsize').val();
		};
		
		this.selectModelSize = function(selectObj){

			if(confirm("切换模板比例将会清空已经设计好的模板，您确认要更换模板比例吗？")){
				if(selectObj.value == "4_3"){
					$('#operationArea').css('width','800px');
					$('#operationArea').css('height','600px');
				}
				if(selectObj.value == "5_4"){
					$('#operationArea').css('width','800px');
					$('#operationArea').css('height','640px');
				}
				if(selectObj.value == "16_9"){
					$('#operationArea').css('width','800px');
					$('#operationArea').css('height','450px');
				}
				if(selectObj.value == "1280_1024"){
					$('#operationArea').css('width','800px');
					$('#operationArea').css('height','640px');
				}
				if(selectObj.value == "3_4"){
					$('#operationArea').css('width','600px');
					$('#operationArea').css('height','800px');
				}
				if(selectObj.value == "4_5"){
					$('#operationArea').css('width','640px');
					$('#operationArea').css('height','800px');
				}
				if(selectObj.value == "9_16"){
					$('#operationArea').css('width','450px');
					$('#operationArea').css('height','800px');
				}
				$("#operationArea").empty();
				TemplateDesign.saveOriginalSelectValue();
				textDirectionValue = "";		
			}else{
				if(TemplateDesign.preValue != null){
			        $("#modelsize").val(TemplateDesign.preValue);
			    }
				TemplateDesign.saveOriginalSelectValue();
				$("#modelsize").val(TemplateDesign.preValue);
			}
		};
			
	};
	TemplateDesign.saveOriginalSelectValue();
	
</script>
</head>

<body>
	<div id="toolArea" class="rightBoxes">
		<div class="rightBoxesTop">
			<h3>工具栏</h3>
		</div>
		<div class="rightContent">
			<div style="float: left;margin-left: 14px;">模板比例：
				<select id="modelsize" onchange="javascript:TemplateDesign.selectModelSize(this);">
					<option id="1280_1024" value="1280_1024">5：4（1280*1024）</option>
					<!-- 
					<option id="4_3" value="4_3">4：3（800*600）</option>
					<option id="5_4" value="5_4">5：4（800*640）</option>
					<option id="16_9" value="16_9">16：9（800*450）</option>
					<option id="3_4" value="3_4">3：4（600*800）</option>
					<option id="4_5" value="4_5">4：5（640*800）</option>
					<option id="9_16" value="9_16">9：16（450*800）</option>
					 -->
				</select>
			</div>
			<!-- <font style="visibility: hidden;">below is tool icons</font> -->
			<hr class="blackLine" />
			<font style="visibility: hidden;">uppers are tool icons</font>
		    <div id="video" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_video.jpg" title="点击在右边区域画一个视频区域" /></div>  
		    <div id="image" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_image.jpg" title="点击在右边区域画一个图片区域" /></div>  
		    <!-- <div id="audio" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_audio.jpg" title="点击在右边区域画一个音频区域" /></div> -->  
		    <div id="text" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_text.jpg" title="点击在右边区域画一个文本区域" /></div>  
		    <!--<div id="weather" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_weather.png" title="点击在右边区域添加天气区域" /></div> -->  
		    <div id="clock" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_time.jpg" title="点击在右边区域添加时间区域" /></div>  
			<font style="visibility: hidden;">below is button</font>
			<hr class="blackLine" />
		    <a class="buttonStyle" style="float: left;margin-left: 30px;cursor: pointer;margin-top: 15px;" href="javascript:TemplateDesign.saveTemplate();">保存模板</a>  
		    <a class="buttonStyle" style="float: left;margin-left: 50px;cursor: pointer;margin-top: 15px;" href="javascript:TemplateDesign.cancel();">重置模板</a> 
		</div>

		<div class="rightContent">
			<br/>
			<br/>
			<hr class="blackLine" />
		    <div>
		    	<font>操作提示：</font><br/>
		    	<font>1. 单击模块图片，在右边的区域任意拖动一块矩形区域，再单击矩形区域调整位置或尺寸。</font><br/>
		    	<font>2. 若点击了多个模块导致无法在右边区域任意拖动一块矩形区域，请按F5刷新页面后重新操作。</font>
		    </div>  
		</div>
	</div>
	
<div id="operationArea" class="operationArea">  
</div>

<div id="alertBox" style="display: none;">
	<form id="templateForm" action="">
	<div style="margin: 20px;">模板名称：<label class="smallInput"><input id="tempalteName" class="inputS validate[required,maxSize[20]]" type="text" size="28" name="tempalteName"></label></div>
	<div style="margin: 20px;">模板备注：<label class="textareaDiv"><textarea id="tempalteRemark" class="textarea validate[required,maxSize[30]]" rows="5" cols="80" name="remark"></textarea><br></label></div>
			
	<div style="position: relative;left:30px;cursor: pointer;">
		<a class="buttonStyle" href="javascript:TemplateDesign.save()">保存</a>
	</div>
	</form>
</div>
<div id="textDirection" style="display: none; cursor: default;">
	<div id="textSelects" style="margin-top: 40px;"> 
		<select id="textColorSelect" name="" class="width200">
			<option id="" value="default">请选择字体颜色</option>
			<option id="" value="0.0.0" style="color: black;">黑色</option>
			<option id="" value="255.255.255" style="color: white;" selected="selected">白色</option>
			<option id="" value="255.0.0" style="color: red;">红色</option>
			<option id="" value="255.255.0" style="color: yellow;">黄色</option>
			<option id="" value="0.255.0" style="color: green;">绿色</option>
			<option id="" value="0.0.255" style="color: blue;">蓝色</option>
			<option id="" value="128.0.255" style="color: purple;">紫色</option>
		</select>
		<select id="textTypeSelect" name="" class="width200" disabled="disabled">
			<option id="" value="default">请选择字体名</option>
			<option id="" value="宋体" selected="selected">暂只支持宋体</option>
		</select>
		<select id="textFontSizeSelect" name="" class="width200" disabled="disabled">
			<option id="" value="default">请选择字体号</option>
			<option id="" value="8" >8</option>
			<option id="" value="9" >9</option>
			<option id="" value="10" >10</option>
			<option id="" value="11" >11</option>
			<option id="" value="12" >12</option>
			<option id="" value="14" >14</option>
			<option id="" value="16" >16</option>
			<option id="" value="18" >18</option>
			<option id="" value="20" >20</option>
			<option id="" value="22" >22</option>
			<option id="" value="24" >24</option>
			<option id="" value="26" >26</option>
			<option id="" value="28" >28</option>
			<option id="" value="36" >36</option>
			<option id="" value="48" >48</option>
			<option id="" value="72" selected="selected">72</option>
		</select>
		<select id="textFontBolderSelect" name="" class="width200">
			<option id="" value="default">请选择是否加粗字体</option>
			<option id="" value="1">加粗</option>
			<option id="" value="0" selected="selected">不加粗</option>
		</select>
		<select id="textFontItalicSelect" disabled="disabled" name="" class="width200">
			<option id="" value="default">请选择是否斜体</option>
			<option id="" value="1">是</option>
			<option id="" value="0" selected="selected">暂不支持斜体</option>
		</select>
		<select id="textFontUnderSelect" disabled="disabled" name="" class="width200">
			<option id="" value="default">选择是否加下划线</option>
			<option id="" value="1">是</option>
			<option id="" value="0" selected="selected">暂不支持下划线</option>
		</select>
		<select id="textNextLineSelect" name="" class="width200">
			<option id="" value="default">请选择是否自动换行</option>
			<option id="" value="1" selected="selected">是</option>
			<option id="" value="0">否</option>
		</select>
		<select id="textHAlignSelect" name="" class="width200">
			<option id="" value="default">请选择水平对齐方式</option>
			<option id="" value="1">左对齐</option>
			<option id="" value="2" selected="selected">居中</option>
			<option id="" value="3">右对齐</option>
		</select>
		<select id="textVAlignSelect" name="" class="width200">
			<option id="" value="default">请选择垂直对齐方式</option>
			<option id="" value="1">上对齐</option>
			<option id="" value="2" selected="selected">居中</option>
			<option id="" value="3">下对齐</option>
		</select>
		<select id="textDirectionSelect" name="" class="width200">
			<option id="" value="default">请选择文字滚动方式</option>
			<option id="" value="static" selected="selected">静止不动的</option>
			<option id="" value="right">自左向右滚动</option>
			<option id="" value="left">自右向左滚动</option>
			<option id="" value="down">自上向下滚动</option>
			<option id="" value="up">自下向上滚动</option>
		</select>
		<select id="textSpeedSelect" name="" class="width200">
			<option id="" value="default">请选择文字滚动速度</option>
			<option id="" value="0">快速</option>
			<option id="" value="1" selected="selected">正常</option>
			<option id="" value="2">缓慢</option>
			<option id="" value="4">非常缓慢</option>
		</select>
		<select id="textDelaySelect" name="" class="width200">
			<option id="" value="default">请选择文字停留时间</option>
			<option id="" value="0" selected="selected">不停留</option>
			<option id="" value="30">0.5秒</option>
			<option id="" value="60">1.0秒</option>
			<option id="" value="90">1.5秒</option>
			<option id="" value="120">2.0秒</option>
			<option id="" value="150">2.5秒</option>
			<option id="" value="300">5秒</option>
			<option id="" value="600">10秒</option>
		</select>
		<br/>
		<a class="buttonStyle" href="javascript:TemplateDesign.textDerection();">确定</a>
	</div>
</div>

<!-- clock -->
<div id="clockParamsDiv" style="display: none; cursor: default;">
	<div id="clockSelects" style="margin-top: 40px;"> 
		<select id="clockColorSelect" name="" class="width200">
			<option id="" value="default">请选择字体颜色</option>
			<option id="" value="0.0.0" style="color: black;">黑色</option>
			<option id="" value="255.255.255" style="color: white;" selected="selected">白色</option>
			<option id="" value="255.0.0" style="color: red;">红色</option>
			<option id="" value="255.255.0" style="color: yellow;">黄色</option>
			<option id="" value="0.255.0" style="color: green;">绿色</option>
			<option id="" value="0.0.255" style="color: blue;">蓝色</option>
			<option id="" value="128.0.255" style="color: purple;">紫色</option>
		</select>
		<select id="clockTypeSelect" name="" class="width200" disabled="disabled">
			<option id="" value="default">请选择字体名</option>
			<option id="" value="宋体" selected="selected">暂只支持宋体</option>
		</select>
		<select id="clockFontSizeSelect" name="" class="width200">
			<option id="" value="default">请选择字体号</option>
			<option id="" value="8" >8</option>
			<option id="" value="9" >9</option>
			<option id="" value="10" >10</option>
			<option id="" value="11" >11</option>
			<option id="" value="12" >12</option>
			<option id="" value="14" >14</option>
			<option id="" value="16" >16</option>
			<option id="" value="18" selected="selected">18</option>
			<option id="" value="20" >20</option>
			<option id="" value="22" >22</option>
			<option id="" value="24" >24</option>
			<option id="" value="26" >26</option>
			<option id="" value="28" >28</option>
			<option id="" value="36" >36</option>
			<option id="" value="48" >48</option>
			<option id="" value="72">72</option>
		</select>
		<select id="clockFontBolderSelect" name="" class="width200">
			<option id="" value="default">请选择是否加粗字体</option>
			<option id="" value="1">加粗</option>
			<option id="" value="0" selected="selected">不加粗</option>
		</select>
		<select id="clockFontItalicSelect" disabled="disabled" name="" class="width200">
			<option id="" value="default">请选择是否斜体</option>
			<option id="" value="1">是</option>
			<option id="" value="0" selected="selected">暂不支持斜体</option>
		</select>
		<select id="clockFontUnderSelect" disabled="disabled" name="" class="width200">
			<option id="" value="default">选择是否加下划线</option>
			<option id="" value="1">是</option>
			<option id="" value="0" selected="selected">暂不支持下划线</option>
		</select>
		<select id="clockModeSelect" name="" class="width200">
			<option id="" value="default">请选择数字时钟格式</option>
			<option id="" value="1" selected="selected">全部显示</option>
			<option id="" value="2" >年月日</option>
			<option id="" value="3" >月日</option>
			<option id="" value="4" >时分秒</option>
			<option id="" value="5" >时分</option>
			<option id="" value="6" >星期</option>
			<option id="" value="7" >年</option>
			<option id="" value="8" >月</option>
			<option id="" value="9" >日</option>
			<option id="" value="10" >时</option>
			<option id="" value="11" >分</option>
			<option id="" value="12" >秒</option>
		</select>
		<select id="clockFormatSelect" name="" class="width200">
			<option id="" value="default">请选择数字时钟显示模式</option>
			<option id="" value="1" selected="selected">2008年08月08日 08时08分08秒</option>
			<option id="" value="2">2008-08-08 08:08:08</option>
			<option id="" value="3">08/08/2008 08:08:08</option>
		</select>
		<br/>
		<a class="buttonStyle" href="javascript:TemplateDesign.clockParams();">确定</a>
	</div>
</div>
</body>
</html>