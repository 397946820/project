// JavaScript Document
// 图片高亮效果
var blockHighLight = (function(window, $, undefined){
    var markers = [];
    return function(boxCls, itemCls, sizeArr){
        var box = $(boxCls);
        itemCls = itemCls || "a";
        box.find(itemCls).each(function(i){
            var self = $(this);
            var arr,w,h,marker;
            if(sizeArr !== undefined){
                arr = sizeArr[i].split(",");
                w = arr[0];
                h = arr[1];
            }else{
                w = self.find("img").attr("width");
                h = self.find("img").attr("height");
            }
            marker = $('<div style="cursor:pointer;top:0;left:0;position:absolute;width:'+w+'px;height:'+h+'px;filter:alpha(opacity=0);opacity: 0;background-color:#000;"></div>');
            self.append(marker);
            self.mouseover(function(){
                for(var i=0; i<markers.length; i++){
                    markers[i].show().css({"opacity":'0.4',"filter":"alpha(opacity=40)"});
                }
                marker.hide();
            });     
            markers.push(marker);
                
        });

        box.mouseout(function(){
            for(var i=0; i<markers.length; i++){
                markers[i].css({"opacity":'0',"filter":"alpha(opacity=0)"});
            }
        })
    }
})(this, $);