<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript">
	$(document).ready(function(){
		$("#starttime").datetimepicker({
			dateFormat: 'yy-mm-dd',
			showSecond: true,
			timeFormat: 'hh:mm:ss',
			stepHour: 1,
			stepMinute: 1,
			stepSecond: 1
		});
		$("#endtime").datetimepicker({
			dateFormat: 'yy-mm-dd',
			showSecond: true,
			timeFormat: 'hh:mm:ss',
			stepHour: 1,
			stepMinute: 1,
			stepSecond: 1
		});
		
		// regist downloadr plugin
		//$('a[rel*=downloadr]').downloadr();
		
	});
	
	$(function(){
    	//移到右边
	   $('#add').click(function() {
			//$('#select1 option:selected').remove().appendTo('#select2');
			var val = $('#select1 option:selected').val();
			var txt = $('#select1 option:selected').html();
			var item = "<option value=\""+val+"\">"+txt+"</option>";
			$('#select2').append(item);
	   });
	   //移到左边
	   $('#remove').click(function() {
			//$('#select2 option:selected').remove().appendTo('#select1');
		   $('#select2 option:selected').remove();
	   });
       //双击选项
	   $('#select1').dblclick(function(){
	       //$("option:selected",this).remove().appendTo('#select2');
			var val = $('#select1 option:selected').val();
			var txt = $('#select1 option:selected').html();
			var item = "<option value=\""+val+"\">"+txt+"</option>";
			$('#select2').append(item);
	   });
	   //双击选项
		$('#select2').dblclick(function(){
	       //$("option:selected",this).remove().appendTo('#select1');
			$("option:selected",this).remove();
	   });
	   //左边向上按钮
	   $('#left_up').click(function(){
	      var index =  $('#select1 option').index($('#select1 option:selected:first'));
		  var $recent =  $('#select1 option:eq('+(index-1)+')');
		  if(index>0){
		     var $options = $('#select1 option:selected').remove();
			 setTimeout(function(){
				  $recent.before($options);
			 },10);
		  }
	   });
	   //左边向下按钮
	   $('#left_down').click(function(){
	      var index =  $('#select1 option').index($('#select1 option:selected:last'));
		  var len = $('#select1 option').length-1;
		  var $recent =  $('#select1 option:eq('+(index+1)+')');
		  if(index<len ){
		 	 var $options = $('#select1 option:selected').remove();
			 setTimeout(function(){
				 $recent.after( $options );
			 },10);
		   }
	   });
	   //右边向上按钮
	   $('#right_up').click(function(){
	      var index =  $('#select2 option').index($('#select2 option:selected:first'));
		  var $recent =  $('#select2 option:eq('+(index-1)+')');
		  if(index>0){
		     var $options = $('#select2 option:selected').remove();
			 setTimeout(function(){
				  $recent.before($options);
			 },10);
		  }
	   });
	   //右边向下按钮
	   $('#right_down').click(function(){
	      var index =  $('#select2 option').index($('#select2 option:selected:last'));
		  var len = $('#select2 option').length-1;
		  var $recent =  $('#select2 option:eq('+(index+1)+')');
		  if(index<len ){
		 	 var $options = $('#select2 option:selected').remove();
			 setTimeout(function(){
				 $recent.after($options);
			 },10);
		   }
	   });
	});
	
	var Playlist = new function(){
		
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
			//alert(Playlist.selectedIDs);
		};
		
		// 判断是add还是edit
		var requestUrl = null;
		// 如果是add，这programId为""，否则是edit
		var playlistId = "";
		
		// 保存
		this.save = function(){
			var name = encodeURI($('#name').val());
			var starttime = $('#starttime').val();
			var endtime = $('#endtime').val();
			var startnow = $('#startnow').attr("checked")=="checked";
			var remark = encodeURI($('#remark').val());
			
			$("#select2 option").attr("selected", true);
			
			var select2 = $('#select2').val();

			if(name == null || $.trim(name) == ""){
				alert("请输入播表名称");
				return;
			}
			if(starttime == null || $.trim(starttime) == ""){
				alert("请选择开始时间");
				return;
			}
			if(endtime == null || $.trim(endtime) == ""){
				alert("请选择结束时间");
				return;
			}
			
			var stime = starttime.replace(/-/g, "").replace(/ /g,"").replace(/:/g,"");
			var etime = endtime.replace(/-/g, "").replace(/ /g,"").replace(/:/g,"");
			
			if(parseInt(etime) > parseInt(stime)){
			}else{
				alert("选择的结束时间必须晚于开始时间！");
				return;
			}
			if(remark == null || $.trim(remark) == ""){
				alert("请输入播表备注");
				return;
			}
			if(select2 == null || select2 == ""){
				alert("请至少选择一个节目");
				return;
			}
			
			var options = "";
			for(var i = 0;i < select2.length;i++){
				options += select2[i]+",";
			}
			
			$.ajax({
				type: "POST",
				url: requestUrl,
				dataType:"html",
				data: {
					id:playlistId,name:name,starttime:starttime,endtime:endtime,startnow:startnow,remark:remark,options:options		
				},
				success: function(data){
					$.ajax({
						type: "POST",
						url: "/CPS/getPlaylistAction.html",
						dataType:"html",
						success: function(data){
							alert("播表保存成功");
							$("#content6").html(data);
							$("#content6").show("slow");
						},
						error:function(){
							alert($("#content6")+"加载失败");
						}
					});
				},
				error:function(){
					alert($("#content6")+"加载失败");
				}
				
			});
		};
		
		this.addPlaylist = function(){
			requestUrl = "/CPS/addPlaylistAction.html";
			Playlist.initAlertBox();
			popupAlert("alertBoxAddPlaylist",800,500,"content6");
		};
		
		this.initAlertBox = function(){
			$('#name').val("");
			$('#startnow').attr("checked",false);
			$('#remark').val("");
			$('#select1').append($('#select2').html());
			$('#select2').empty();
		};
		
		
		// 编辑
		this.edit = function(id){
			$.ajax({
				type: "POST",
				url: "/CPS/editGetPlaylist.html",
				dataType:"json",
				data: {id:id},
				success: function(data){
					// 设置save按钮的请求url为编辑program
					requestUrl = "/CPS/editPlaylist.html";
					playlistId = id;
					Playlist.initAlertBox();
					popupAlert("alertBoxAddPlaylist",800,500,"content6");
					
					$('#name').val(data.playlistObject.name);
					$('#starttime').val(data.playlistObject.starttime);
					$('#endtime').val(data.playlistObject.endtime);
					$('#remark').val(data.playlistObject.remark);
					$('#startnow').attr("checked",data.playlistObject.startnow);
					
					// 设置默认的options
					var size = data.playlistObject.programList.length;
					for(var i=0;i<size;i++){
						$('#select1 option').each(function(){
							if($(this).val() == data.playlistObject.programList[i].id){
								var optionHTML = '<option value="'+$(this).val()+'">'+$(this).html()+'</option>';
								$('#select2').append(optionHTML);
								$(this).remove();
							}
						});
					}
					
				},
				error:function(){
					alert("加载失败");
				}
			});
		};
	};
	
	var palylistSelectAll = false;
	$("#palylistCheckboxTh").bind("click",function(){
		Playlist.selectedIDs = "";
		if(palylistSelectAll){
			$("#playlistForm [type='checkbox']").removeAttr("checked");//取消全选
			Playlist.selectedIDs = "";
			palylistSelectAll = false;
		}else{
			$("#playlistForm [type='checkbox']").each(function(){
				Playlist.selectedIDs += this.id+",";
				if(!this.checked){
					$("#playlistForm [type='checkbox']").attr("checked",'true');//全选
					palylistSelectAll = true;
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
		<a class="buttonStyle" href="javascript:Playlist.addPlaylist()">添加播表</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:delSelectedXmlContetnAjaxCall(Playlist.selectedIDs,'playlist','content6')" class="buttonStyle">删除所有选择的播表</a>
	</div>
	<br>
	<s:if test="#request.playlistList.size()==0">
		<span>暂无数据</span>
	</s:if>
	<s:else>
		<form id="playlistForm" name="playlistForm" method="post" action="">
			<table class="sTable tdNowap900">
				<thead>
					<tr>
						<th id="palylistCheckboxTh" style="cursor: pointer;color: #AB8617;"><strong>全选</strong></th>
						<th><strong>名称</strong></th>
						<th><strong>备注</strong></th>
						<th><strong>播放清单</strong></th>
						<th><strong>创建时间</strong></th>
						<th><strong>创建用户</strong></th>
						<!-- 
						<th><strong>立即播放</strong></th>
						<th><strong>开始时间</strong></th>
						<th><strong>结束时间</strong></th>
						 -->
						<th><strong>删除 </strong></th>
						<!--
						<th><strong>编辑 </strong></th>
						<th><strong>下载</strong></th>
						 -->
					</tr>
				</thead>
				<s:iterator value="playlistList" id="" status="" var="palylistObj">
				<tr class="oddRow">
					<td><input type="checkbox" id="${palylistObj.id }" onchange="javascript:Playlist.checkboxSelectd(this)"></td>
					<td>${palylistObj.name }</td>
					<td>${palylistObj.remark }</td>
					<td>
						<ul>
							<li style="">
								<a class="hrefColor" onmouseout="javascript:hidTip('div_${palylistObj.id }');" onmouseover="javascript:showTip('div_${palylistObj.id }');" href="javascript:void(0);">查看</a>
								<div id="div_${palylistObj.id }" style="margin-left:60px;width:200px;display: none;z-index: 3000;background: gray;position: absolute;">
									<s:iterator value="#palylistObj.programList" status="indexids" var="prog">
										<div style="float: left;clear: both;color: white;">节目${indexids.index+1}.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${prog.name }</div>
									</s:iterator>
								</div>
							</li>
						</ul>
					</td>
					<td>${palylistObj.createtime }</td>
					<td>${palylistObj.createby }</td>
					<!-- 
					<td>${palylistObj.startnow }</td>
					<td>${palylistObj.starttime }</td>
					<td>${palylistObj.endtime }</td>
					-->
					<td>
						<ul>
							<li><a class="hrefColor" href="javascript:delXmlContetnAjaxCall('${palylistObj.id }','playlist','content6')">删除</a></li>
						</ul>
					</td>
					<!-- 
					<td>
						<ul>
							<li><a class="hrefColor" href="javascript:Playlist.edit('${palylistObj.id }')">编辑</a></li>
						</ul>
					</td>
					<td>
						<ul>
							<li><a class="hrefColor" href="fileDownload.html?id=${palylistObj.id }" rel="downloadr" title="${palylistObj.id }.rar">下载</a></li>
						</ul>
					</td>
					 -->
				</tr>
				</s:iterator>
			</table>
		</form>
	</s:else>

	<div id="alertBoxAddPlaylist" style="display: none;">
		<form id="addPlaylistForm" method="post" name="addPlaylistForm" action="">
			<div style="float: left;">
			<div style="margin: 20px;">播表名称：<label class="smallInput"><input id="name" class="inputS" type="text" size="28" name="name"></label></div>
			
			
			<div style="margin: 20px; visibility: hidden;">开始时间：<label class="smallInput"><input value="2012-12-12 12:12:12" id="starttime" class="inputS" type="text" size="28" name="starttime"></label></div>
			<div style="margin: 20px; visibility: hidden;">结束时间：<label class="smallInput"><input value="2013-12-12 12:12:12" id="endtime" class="inputS" type="text" size="28" name="endtime"></label></div>
			<div style="margin: 20px; visibility: hidden;">是否插播：<label class="smallInput"><input checked="checked" id="startnow" class="inputS" type="checkbox" size="28" name="startnow"></label></div>
			
			<!-- 
			<div style="margin: 20px;">开始时间：<label class="smallInput"><input id="starttime" class="inputS" type="text" size="28" name="starttime"></label></div>
			<div style="margin: 20px;">结束时间：<label class="smallInput"><input id="endtime" class="inputS" type="text" size="28" name="endtime"></label></div>
			<div style="margin: 20px;">是否插播：<label class="smallInput"><input id="startnow" class="inputS" type="checkbox" size="28" name="startnow"></label></div>
			 -->
			
			<div style="margin: 20px;">播表备注：<label class="textareaDiv"><textarea id="remark" class="textarea" rows="5" cols="80" name="remark"></textarea><br></label></div>
		</div>
		
		<div style="float: left;">
			<div style="margin: 20px 0 0 20px;">选择节目：</div>
			<div style="float: left;text-align: center;margin: 0 0 0 20px;">
				<s:select multiple="true" id="select1" name="select1" cssStyle="width: 240px;height:314px;" list="programList" listKey="id" listValue="name"/>
				<a href="#" id="left_up" class="move">上移</a>
				<a href="#" id="left_down" class="move">下移</a>
				<a href="#" id="add" class="move">添加到右边&gt;&gt;</a>
			</div>
			
			<div style="float: left;text-align: center;margin: 0 0 0 10px;">
				<select multiple id="select2" name="select2" style="width: 240px;height:314px;"></select>
				<a href="#" id="right_up" class="move">上移</a>
				<a href="#" id="right_down" class="move">下移</a>
				<a href="#" id="remove" class="move">&lt;&lt; 删除到左边</a>
			</div>
		</div>
		<div style="position: relative;left:30px;top: 460px;cursor: pointer;">
			<a class="buttonStyle" href="javascript:Playlist.save()">保存</a>
		</div>
		</form>
	</div>
	<div>
		<a class="buttonStyle" href="javascript:scroll(0,0)">返回顶部</a>
	</div>
</body>
</html>