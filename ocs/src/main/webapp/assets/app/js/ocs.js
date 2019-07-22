//ajax method
var ocs = {};
ocs.validate = function(){
	//验证空
	var required = function(){
		var isPass = true;
		$(".ocs_required").each(function(){
			var value = $(this).val();
			if(ocs.stringIsNull(value)){
				var viewName = $(this).attr("message");
				$.messager.alert('提示',viewName+'为必填','Warning');
				isPass = false
				return false;
					
			}
		});
		return isPass;
	};
	var required2 = function(){
		var isPass = true;
		var mpn = $("#productMpnId").val();
		if(mpn==null ||mpn==''){
			$.messager.alert('提示','MPN为必填','Warning');
			isPaass = false;
			return false;
		}
		var brand = $("#productBrandId").val();
		if(brand==null ||brand==''){
			$.messager.alert('提示','Brand为必填','Warning');
			isPaass = false;
			return false;
		}
		return isPass;
	};
	//数字
	var number = function(){
		var isPass = true;
		$(".ocs_number").each(function(){
			var value = $(this).val();
			if(value){
				if(isNaN(value)){
					var viewName = $(this).attr("message");
					$.messager.alert('提示',viewName+'不是数字','Warning');
					isPass = false
					return false;
				}  
			}
		});
		return isPass;
	};
	//保留价格大于起始价格
	var checkReserverPrice = function(){
//		if($("#rbleBayListType1:checked")==true){
			var isPass = true;
			var reserverPrice = $("#reserverPrice").val();
			if(reserverPrice){
				var startPrice = $("#auctionPrice").val();
				if(startPrice && $("#rbleBayListType1:checked")==true){
					if((reserverPrice-0) <= (startPrice-0)){
						$.messager.alert('提示','保留价格低于起始价格','Warning');
						isPass = false;
					}
				}
			
		}else{
			isPass = true;
		}
		
		return isPass;
	}
	//固价类型时：自动接受价格应该小于价格，自动拒绝价格应该小于自动接受价格；
	var checkAutoAcceptRefusePrice = function(){
		var isPass = true;
		var type = $("#publictionType").val();
		if(type=="FixedPriceItem"){
			var price = $('#auctionPrice').val();
			var acceptPrice = $("#ChangePriceAccept").val();
			var refusePrice =  $("#ChangePriceRefuse").val();
			if(acceptPrice&&refusePrice){
				isPass =  (acceptPrice-0) < (price-0)&&(refusePrice-0)<(acceptPrice-0);
			}else if(ocs.stringIsNull(acceptPrice)&&ocs.stringIsNull(refusePrice)){
				isPass = true;
			}else{
				isPass = false;
			}
			if(!isPass){
				$.messager.alert('提示','自动接受价格应该小于价格，自动拒绝价格应该小于自动接受价格；','Warning');
			}
		}
		return isPass;
	};
	
	//验证sku 和 标题联合唯一
	var checkSKUAndTitle = function(){
		var isPass = true;
		var sku = $("#productSKU").val();
		var title = $("#itemTitle").val();
		var ebayAccount =$("#ebayAccount").val();
		var site = $("#site").val();
		var param = {};
		param["sku"] = sku;
		param["title"] = title;
		param["ebayAccount"] = ebayAccount;
		param["site"] = site;
		param["templateId"] = $('#publicationId').val();
		ocs.ajax({
			type:"POST",
			url:'/publication/checkSKUAndTitle',
			data : param,
			success:function(data){
				if(data.data>0){
					isPass = false;
					$.messager.alert('提示','sku和标题组合已存在','Warning');
				}
			},
			error:function(){
				isPass = false;
			}
		});
		return isPass;
	}
	if(!required()||!required2()||!number()||!checkReserverPrice()||!checkSKUAndTitle()||!checkAutoAcceptRefusePrice()){
		return false
	}
	return true;
}
ocs.stringIsNull = function(str){ 
	if(str == 'undefined' || str == null || str == ''){
		return true;		
	}else{
		return false;
	}
}
ocs.ajax = function(option){
	var url =  GLOBAL.domain + option.url;
	var type = "GET";
	if(option.type){
		type = option.type;
	}
	var data = "";
	if(option.data){
		data = JSON.stringify(option.data);
	}
	var async = false;
	if(option.async){
		async = option.async;
	}
	$.ajax({
        url: url,
        async: async,
        type: type,
        dataType: 'json',
        data:data,
		contentType: "application/json",
		beforeSend:function(){
			if(option.beforeSend){
				option.beforeSend();
			}
		},
		complete: function () {
            if(option.complete){
            	option.complete();
            }
        },
        success:function(data){
        	if(option.success){
        		option.success(data);
        	}
        },
        error:function(data){
        	$.messager.alert('提示','操作失败','info');
        	if(option.error){
        		option.error(data);
        	}
        }
    });
}
var setSelectValue = function(selectId,value) {
	$(selectId+' option').each(function(index,obj){
		if ($(this).val() == value){
			$(this).attr('selected',true);
		}
	});
}
String.prototype.sTrim = function (){
	var str = this;
	str = str.replace(/\s/g,'');
	str = str.replace(/\//g,'');
	str = str.replace(/\(/g,'');
	str = str.replace(/\)/g,'');
	str = str.replace(/\*/,'____');
	str = str.replace(/\;/g,'___');
	str = str.replace(/\./g,'__');
	str = str.replace(/\,/g,'_____');
	str = str.replace(/\&/g,'______');
	str = str.replace(/\?/g,"_______");
	return str;
}

//文字长度监听
function changeWords(obj){
	$(obj).keyup(function(){
		$(this).parent().find(".overWords").text($(this).val().length);
	})
}
