var renderOtherModle = function(otherModel) {
	var salesTax = otherModel.salesTax;
	if(salesTax != null){
		salesTax = eval('('+salesTax+')');
		setSelectValue('#salesTaxType',salesTax.type);
		$('#txtSalesTaxPercent').val(salesTax.percent);
		$('#chkSalesTaxShippingIncludedInTax').attr('checked',eval(salesTax.shippingIncludedInTax)?true:false);
		//$('#txtMaxListing').val(otherModel.onlinePubCount);
	}
	
	var tags = otherModel.tag;
	if(tags != null){
		tags = tags.split(",");
		$.each(tags,function(){
			 $(".profile_tag").append('<span class="profile_tag_span">'+this+'<span class="delete_profile">X</span></span>'); 
		});
	}
	
	$('#otherDescription').textbox("setValue",otherModel.otherDescription);
}
var contructOtherModel = function(){
	var otherModel = {
			salesTax:"",
			tag:'',
			otherDescription:'',
			onlinePubCount:1
		};
	var salesTaxObj = {};
	salesTaxObj["type"] = $('#salesTaxType').val();
	salesTaxObj["percent"] = $('#txtSalesTaxPercent').val();
	salesTaxObj["shippingIncludedInTax"] = $('#chkSalesTaxShippingIncludedInTax').is(':checked')?'true':'false';
	otherModel.salesTax =  JSON.stringify(salesTaxObj);
	//otherModel.onlinePubCount = $('#txtMaxListing').val();
	otherModel.otherDescription = $('#otherDescription').textbox("getValue");
	var tagArr =[];
	$('.profile_tag_span').each(function(){
		var one = $(this).text();
		tagArr.push(one.substring(0,one.length-1));
	});
	otherModel.tag = tagArr.join(",");
	return otherModel;
}
$(function(){
	 //其它
    //点击标签
    $(".dropdown-menu li").on("click",function(){
        var profile = $(this).find("a").html();
        $(".profile_tag").append('<span class="profile_tag_span">'+profile+'<span class="delete_profile">X</span></span>');                           
    });
    //删除标签
    $(".profile_tag").on("click",".delete_profile",function(){
        $(this).parents(".profile_tag_span").remove();
    });
})
