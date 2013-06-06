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
		
		// ������������div
		this.generateDIV = function (sx,sy,ex,ey,id,type){
			var div = "";
			if(type=="video"){
				div = '<div id="'+id+'" class="jqDnR" style="background:red;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'"><span>����������'+type+'����'+displayXY+'</span><div class="jqHandle jqResize"></div></div>';
			}
			if(type=="image"){
				div = '<div id="'+id+'" class="jqDnR" style="background:blue;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'"><span>����������'+type+'����'+displayXY+'</span><div class="jqHandle jqResize"></div></div>';
			}
			if(type=="audio"){
				div = '<div id="'+id+'" class="jqDnR" style="background:black;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'"><span>����������'+type+'����'+displayXY+'</span><div class="jqHandle jqResize"></div></div>';
			}
			if(type=="text"){
				div = '<div id="'+id+'" class="jqDnR" style="background:green;left:'+sx+'px;top:'+sy+'px;width:'+(ex-sx)+'px;height:'+(ey-sy)+'px;z-index:'+(zindex++)+'"><span>����������'+type+'����'+displayXY+'</span><input type="hidden" id="'+id+'_directionValueId" value="'+textDirectionValue+'"/><div class="jqHandle jqResize"></div></div>';
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

				var text = "������ʱ��";
				if(displaypart == "1"){
					if(type == "1"){
						text = "2012��08��08�� ������ 08ʱ08��08��";
					}
					if(type == "2"){
						text = "2012-08-08 ������ 08:08:08";
					}
					if(type == "3"){
						text = "08/08/2012 ������ 08:08:08";
					}
				}
				if(displaypart == "2"){
					if(type == "1"){
						text = "2012��08��08��";
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
						text = "08��08��";
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
						text = "08ʱ08��08��";
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
						text = "08ʱ08��";
					}
					if(type == "2"){
						text = "08:08";
					}
					if(type == "3"){
						text = "08:08";
					}
				}
				if(displaypart == "6"){
					text = "������";
				}
				if(displaypart == "7"){
					if(type == "1"){
						text = "2012��";
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
						text = "08��";
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
						text = "08��";
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
						text = "08ʱ";
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
						text = "08��";
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
						text = "08��";
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
		
		// ���ģ��
		this.cancel = function (){
			if(confirm('ȷ��Ҫ���ã����ý�������в���?')) {
				$("#operationArea").empty();
		        //return true;
		    }
		    //return false;
		};
		
		// ����ģ��
		this.saveTemplate = function (){
			if($("#operationArea:has(div)").length == 0){
				alert("�����û�����ݣ����豣��");
			}else{
				// alert(model);
				if(model != "" && model != null){
					//return;
					model = "";
				}
				// ����operationArea�е�div
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
		
		// �������ڵ�save��ť
		this.save = function(){

			if($("#tempalteName").val() == null || $("#tempalteName").val() == ""){
				alert("������ģ������");
			}else{
				if($("#tempalteRemark").val() == null || $("#tempalteRemark").val() == ""){
					alert("������ģ�屸ע");
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
		
		// ���沢�ύtemlate
		this.submitTemplate = function (templateName,tempalteRemark,templateSize,model){
			$.ajax({
				type: "POST",
				url: "/CPS/addTemplateAction.html",
				data: {"templateName":templateName,"tempalteRemark":tempalteRemark,"templateSize":templateSize,"model":model},
				success: function(data){
					alert("ģ�屣��ɹ�,�鿴�����ģ�����ҳǩ");
					$.ajax({
						type: "POST",
						url: "/CPS/templateDesignAction.html",
						dataType:"html",
						success: function(data){
							$("#content4").html(data);
							$("#content4").show("slow");
						},
						error:function(){
							alert($("#content6")+"����ʧ��");
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
		
		// ���Tool����е�Icon
		this.toolIconClicked = function (toolIcon) {

			if(toolIcon.id == 'text'){
				popupAlertWithCloseFunction('textDirection', 200, 340, 'content4',"closeText");
			}
			if(toolIcon.id == 'clock'){
				popupAlertWithCloseFunction('clockParamsDiv', 200, 340, 'content4',"closeClock");
			}
			
			// �ı䰴ť��״̬�����£�͹��
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

			if(confirm("�л�ģ�������������Ѿ���ƺõ�ģ�壬��ȷ��Ҫ����ģ�������")){
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
			<h3>������</h3>
		</div>
		<div class="rightContent">
			<div style="float: left;margin-left: 14px;">ģ�������
				<select id="modelsize" onchange="javascript:TemplateDesign.selectModelSize(this);">
					<option id="1280_1024" value="1280_1024">5��4��1280*1024��</option>
					<!-- 
					<option id="4_3" value="4_3">4��3��800*600��</option>
					<option id="5_4" value="5_4">5��4��800*640��</option>
					<option id="16_9" value="16_9">16��9��800*450��</option>
					<option id="3_4" value="3_4">3��4��600*800��</option>
					<option id="4_5" value="4_5">4��5��640*800��</option>
					<option id="9_16" value="9_16">9��16��450*800��</option>
					 -->
				</select>
			</div>
			<!-- <font style="visibility: hidden;">below is tool icons</font> -->
			<hr class="blackLine" />
			<font style="visibility: hidden;">uppers are tool icons</font>
		    <div id="video" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_video.jpg" title="������ұ�����һ����Ƶ����" /></div>  
		    <div id="image" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_image.jpg" title="������ұ�����һ��ͼƬ����" /></div>  
		    <!-- <div id="audio" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_audio.jpg" title="������ұ�����һ����Ƶ����" /></div> -->  
		    <div id="text" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_text.jpg" title="������ұ�����һ���ı�����" /></div>  
		    <!--<div id="weather" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_weather.png" title="������ұ����������������" /></div> -->  
		    <div id="clock" class="toolBox" onclick="javascript:TemplateDesign.toolIconClicked(this)"><img style="width: 90px;height: 90px;" src="images/toolbox_time.jpg" title="������ұ��������ʱ������" /></div>  
			<font style="visibility: hidden;">below is button</font>
			<hr class="blackLine" />
		    <a class="buttonStyle" style="float: left;margin-left: 30px;cursor: pointer;margin-top: 15px;" href="javascript:TemplateDesign.saveTemplate();">����ģ��</a>  
		    <a class="buttonStyle" style="float: left;margin-left: 50px;cursor: pointer;margin-top: 15px;" href="javascript:TemplateDesign.cancel();">����ģ��</a> 
		</div>

		<div class="rightContent">
			<br/>
			<br/>
			<hr class="blackLine" />
		    <div>
		    	<font>������ʾ��</font><br/>
		    	<font>1. ����ģ��ͼƬ�����ұߵ����������϶�һ����������ٵ��������������λ�û�ߴ硣</font><br/>
		    	<font>2. ������˶��ģ�鵼���޷����ұ����������϶�һ����������밴F5ˢ��ҳ������²�����</font>
		    </div>  
		</div>
	</div>
	
<div id="operationArea" class="operationArea">  
</div>

<div id="alertBox" style="display: none;">
	<form id="templateForm" action="">
	<div style="margin: 20px;">ģ�����ƣ�<label class="smallInput"><input id="tempalteName" class="inputS validate[required,maxSize[20]]" type="text" size="28" name="tempalteName"></label></div>
	<div style="margin: 20px;">ģ�屸ע��<label class="textareaDiv"><textarea id="tempalteRemark" class="textarea validate[required,maxSize[30]]" rows="5" cols="80" name="remark"></textarea><br></label></div>
			
	<div style="position: relative;left:30px;cursor: pointer;">
		<a class="buttonStyle" href="javascript:TemplateDesign.save()">����</a>
	</div>
	</form>
</div>
<div id="textDirection" style="display: none; cursor: default;">
	<div id="textSelects" style="margin-top: 40px;"> 
		<select id="textColorSelect" name="" class="width200">
			<option id="" value="default">��ѡ��������ɫ</option>
			<option id="" value="0.0.0" style="color: black;">��ɫ</option>
			<option id="" value="255.255.255" style="color: white;" selected="selected">��ɫ</option>
			<option id="" value="255.0.0" style="color: red;">��ɫ</option>
			<option id="" value="255.255.0" style="color: yellow;">��ɫ</option>
			<option id="" value="0.255.0" style="color: green;">��ɫ</option>
			<option id="" value="0.0.255" style="color: blue;">��ɫ</option>
			<option id="" value="128.0.255" style="color: purple;">��ɫ</option>
		</select>
		<select id="textTypeSelect" name="" class="width200" disabled="disabled">
			<option id="" value="default">��ѡ��������</option>
			<option id="" value="����" selected="selected">��ֻ֧������</option>
		</select>
		<select id="textFontSizeSelect" name="" class="width200" disabled="disabled">
			<option id="" value="default">��ѡ�������</option>
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
			<option id="" value="default">��ѡ���Ƿ�Ӵ�����</option>
			<option id="" value="1">�Ӵ�</option>
			<option id="" value="0" selected="selected">���Ӵ�</option>
		</select>
		<select id="textFontItalicSelect" disabled="disabled" name="" class="width200">
			<option id="" value="default">��ѡ���Ƿ�б��</option>
			<option id="" value="1">��</option>
			<option id="" value="0" selected="selected">�ݲ�֧��б��</option>
		</select>
		<select id="textFontUnderSelect" disabled="disabled" name="" class="width200">
			<option id="" value="default">ѡ���Ƿ���»���</option>
			<option id="" value="1">��</option>
			<option id="" value="0" selected="selected">�ݲ�֧���»���</option>
		</select>
		<select id="textNextLineSelect" name="" class="width200">
			<option id="" value="default">��ѡ���Ƿ��Զ�����</option>
			<option id="" value="1" selected="selected">��</option>
			<option id="" value="0">��</option>
		</select>
		<select id="textHAlignSelect" name="" class="width200">
			<option id="" value="default">��ѡ��ˮƽ���뷽ʽ</option>
			<option id="" value="1">�����</option>
			<option id="" value="2" selected="selected">����</option>
			<option id="" value="3">�Ҷ���</option>
		</select>
		<select id="textVAlignSelect" name="" class="width200">
			<option id="" value="default">��ѡ��ֱ���뷽ʽ</option>
			<option id="" value="1">�϶���</option>
			<option id="" value="2" selected="selected">����</option>
			<option id="" value="3">�¶���</option>
		</select>
		<select id="textDirectionSelect" name="" class="width200">
			<option id="" value="default">��ѡ�����ֹ�����ʽ</option>
			<option id="" value="static" selected="selected">��ֹ������</option>
			<option id="" value="right">�������ҹ���</option>
			<option id="" value="left">�����������</option>
			<option id="" value="down">�������¹���</option>
			<option id="" value="up">�������Ϲ���</option>
		</select>
		<select id="textSpeedSelect" name="" class="width200">
			<option id="" value="default">��ѡ�����ֹ����ٶ�</option>
			<option id="" value="0">����</option>
			<option id="" value="1" selected="selected">����</option>
			<option id="" value="2">����</option>
			<option id="" value="4">�ǳ�����</option>
		</select>
		<select id="textDelaySelect" name="" class="width200">
			<option id="" value="default">��ѡ������ͣ��ʱ��</option>
			<option id="" value="0" selected="selected">��ͣ��</option>
			<option id="" value="30">0.5��</option>
			<option id="" value="60">1.0��</option>
			<option id="" value="90">1.5��</option>
			<option id="" value="120">2.0��</option>
			<option id="" value="150">2.5��</option>
			<option id="" value="300">5��</option>
			<option id="" value="600">10��</option>
		</select>
		<br/>
		<a class="buttonStyle" href="javascript:TemplateDesign.textDerection();">ȷ��</a>
	</div>
</div>

<!-- clock -->
<div id="clockParamsDiv" style="display: none; cursor: default;">
	<div id="clockSelects" style="margin-top: 40px;"> 
		<select id="clockColorSelect" name="" class="width200">
			<option id="" value="default">��ѡ��������ɫ</option>
			<option id="" value="0.0.0" style="color: black;">��ɫ</option>
			<option id="" value="255.255.255" style="color: white;" selected="selected">��ɫ</option>
			<option id="" value="255.0.0" style="color: red;">��ɫ</option>
			<option id="" value="255.255.0" style="color: yellow;">��ɫ</option>
			<option id="" value="0.255.0" style="color: green;">��ɫ</option>
			<option id="" value="0.0.255" style="color: blue;">��ɫ</option>
			<option id="" value="128.0.255" style="color: purple;">��ɫ</option>
		</select>
		<select id="clockTypeSelect" name="" class="width200" disabled="disabled">
			<option id="" value="default">��ѡ��������</option>
			<option id="" value="����" selected="selected">��ֻ֧������</option>
		</select>
		<select id="clockFontSizeSelect" name="" class="width200">
			<option id="" value="default">��ѡ�������</option>
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
			<option id="" value="default">��ѡ���Ƿ�Ӵ�����</option>
			<option id="" value="1">�Ӵ�</option>
			<option id="" value="0" selected="selected">���Ӵ�</option>
		</select>
		<select id="clockFontItalicSelect" disabled="disabled" name="" class="width200">
			<option id="" value="default">��ѡ���Ƿ�б��</option>
			<option id="" value="1">��</option>
			<option id="" value="0" selected="selected">�ݲ�֧��б��</option>
		</select>
		<select id="clockFontUnderSelect" disabled="disabled" name="" class="width200">
			<option id="" value="default">ѡ���Ƿ���»���</option>
			<option id="" value="1">��</option>
			<option id="" value="0" selected="selected">�ݲ�֧���»���</option>
		</select>
		<select id="clockModeSelect" name="" class="width200">
			<option id="" value="default">��ѡ������ʱ�Ӹ�ʽ</option>
			<option id="" value="1" selected="selected">ȫ����ʾ</option>
			<option id="" value="2" >������</option>
			<option id="" value="3" >����</option>
			<option id="" value="4" >ʱ����</option>
			<option id="" value="5" >ʱ��</option>
			<option id="" value="6" >����</option>
			<option id="" value="7" >��</option>
			<option id="" value="8" >��</option>
			<option id="" value="9" >��</option>
			<option id="" value="10" >ʱ</option>
			<option id="" value="11" >��</option>
			<option id="" value="12" >��</option>
		</select>
		<select id="clockFormatSelect" name="" class="width200">
			<option id="" value="default">��ѡ������ʱ����ʾģʽ</option>
			<option id="" value="1" selected="selected">2008��08��08�� 08ʱ08��08��</option>
			<option id="" value="2">2008-08-08 08:08:08</option>
			<option id="" value="3">08/08/2008 08:08:08</option>
		</select>
		<br/>
		<a class="buttonStyle" href="javascript:TemplateDesign.clockParams();">ȷ��</a>
	</div>
</div>
</body>
</html>