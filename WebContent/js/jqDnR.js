/*
* jqDnR - Minimalistic Drag'n'Resize for jQuery.
*
* Copyright (c) 2007 Brice Burgess <bhb@iceburg.net>, http://www.iceburg.net/
* Licensed under the MIT License:
* http://www.opensource.org/licenses/mit-license.php
*
* $Version: 2007.08.19 +r2
* 
* last modified by Cross @2012.02.08
* email:mag_lee@126.com
* add features:
* 1. limit the child div in parent div 
* 2. 2. add jqResizeWithLimitHeight function
*/

(function($) {
	$.fn.jqDrag = function(h) {
		return i(this, h, 'd'); 
	};
	
	$.fn.jqResize = function(h) { 
		return i(this, h, 'r'); 
	};
	$.fn.jqResizeWithLimitHeight = function(h) { 
		return i(this, h, 'limitHeight'); 
	};
	
	$.jqDnR = {
		dnr: {}, 
		e: 0,
		drag: function(v) {
			
			// 获取父容器的绝对坐标
			var parentX = parseInt(E.parent().offset().left); 
			var parentY = parseInt(E.parent().offset().top); 
			// 获取父容器的宽度
			var parentW = parseInt(E.parent().css("width").split("px")[0]);
			var parentH = parseInt(E.parent().css("height").split("px")[0]);
			
			// 获取子容器的相对坐标
			var childX = parseInt(E.position().left); 
			var childY = parseInt(E.position().top); 
			// 获取子容器的宽度
			var childW = parseInt(E.css("width").split("px")[0]);
			var childH = parseInt(E.css("height").split("px")[0]);
			
			/**
			 * beishu must be set
			 */
			beishu = 1.6;
			var x = parseInt(parseFloat(beishu)*parseInt(childX));
			var y = parseInt(parseFloat(beishu)*parseInt(childY));
			var xx = parseInt(parseFloat(beishu)*(parseInt(childX)+parseInt(childW)));
			var yy = parseInt(parseFloat(beishu)*(parseInt(childY)+parseInt(childH)));
			displayXY = ",坐标： "+x+","+y+","+xx+","+yy;
			var oldtext = E.text();
			var type = "";
			if(oldtext.indexOf("image") != -1){
				type = "image";
			}
			if(oldtext.indexOf("video") != -1){
				type = "video";
			}
			if(oldtext.indexOf("text") != -1){
				type = "text";
			}
			if(oldtext.indexOf("clock") != -1){
				type = "clock";
			}
			E.attr("title",displayXY);
			var eid = E.attr("id");
			$("#" + eid + " > span").html(type + displayXY);
			if (M.k == 'd') {
				// 限制子容器只能在父容器中拖动
				E.css({
					left: (M.X + v.pageX - M.pX) < 0 ? 0:(M.X + v.pageX - M.pX) < parentW- M.W ? (M.X + v.pageX - M.pX):parentW - M.W, 
					top: (M.Y + v.pageY - M.pY) < 0 ? 0:(M.Y + v.pageY - M.pY) < parentH -M.H ?(M.Y + v.pageY - M.pY):parentH - M.H 
				});
			} else {
				// 限制子容器只能在父容器中缩放
				if(M.k == 'limitHeight'){
					E.css({
						width: (M.X + v.pageX - M.pX) < parentW- M.W ? Math.max(v.pageX - M.pX + M.W, 50):parentW - childX, 
						height: 72 // limit the height of the div
					}); 
					return false;
				}else{
					E.css({
						width: (M.X + v.pageX - M.pX) < parentW- M.W ? Math.max(v.pageX - M.pX + M.W, 50):parentW - childX, 
						height: (M.Y + v.pageY - M.pY) < parentH -M.H ? Math.max(v.pageY - M.pY + M.H, 25):parentH - childY
					}); 
					return false;
				}
				
			}
		},
		stop: function() { 
			E.css('opacity', M.o); 
			$(document).unbind('mousemove', J.drag).unbind('mouseup', J.stop); 
		}
	};
	
	var J = $.jqDnR, 
		M = J.dnr,	//{}
		E = J.e,	//0
		i = function(e, h, k) {
			return e.each(function() {
				h = (h) ? $(h, e) : e;
				h.bind('mousedown', { e: e, k: k }, function(v) {
					var d = v.data, 
						p = {}; 
						E = d.e;
						
					// attempt utilization of dimensions plugin to fix IE issues
					if (E.css('position') != 'relative') {
						p = E.position();
						if (!($.browser.msie && ($.browser.version == "6.0")) && (E.css('position') == 'fixed')) {
							p.top -= $(window).scrollTop();
							p.left -= $(window).scrollLeft();
						}
					}
				
					M = { 
						X: p.left || f('left') || 0, 
						Y: p.top || f('top') || 0, 
						W: f('width') || E[0].scrollWidth || 0, 
						H: f('height') || E[0].scrollHeight || 0, 
						pX: v.pageX, 
						pY: v.pageY, 
						k: d.k, 
						o: E.css('opacity')
					};
					
					E.css({ opacity: 0.8 }); $(document).mousemove($.jqDnR.drag).mouseup($.jqDnR.stop);
					return false;
				});
			});
		},
	
		f = function(k) { 
			return parseInt(E.css(k)) || false; 
		};
})(jQuery); 
