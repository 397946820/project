var transModel = function(){
	var trans ={};
	//国内运输
	var domesticTrans = [];
	//国外运输
	var calCulateTrans = [];
	var i = 1;
	$(".d_trans_flag").each(function(){
		var tranOne = {};
		tranOne["domesticShipType"] = $(this).find("select[name=domesticShipType]").val();
		tranOne["domesticShipCost"] = $(this).find("input[name=domesticShipCost]").val();
		tranOne["domesticExtraCost"] = $(this).find("input[name=domesticExtraCost]").val();
		tranOne["domesticShipAkHiPr"] = $(this).find("input[name=domesticShipAkHiPr]").val();
		tranOne["tranKind"] = 0;
		tranOne["tranOrder"] = i;
		domesticTrans.push(tranOne);
		i++;
	});
	trans["domesticTrans"] = domesticTrans;
	i = 1
	//启用国际运输
	if($("#InternationalShippingIsUse").is(":checked")){
		$(".d_trans_flag2").each(function(){
			var tranOne = {};
			tranOne["domesticShipType"] = $(this).find("select[name=domesticShipType]").val();
			tranOne["domesticShipCost"] = $(this).find("input[name=domesticShipCost]").val();
			tranOne["domesticExtraCost"] = $(this).find("input[name=domesticExtraCost]").val();
			if($("#chkShip2WorldWide1").is(":checked")){
				tranOne["shipLocationIn"] = "Worldwide";
			}else{
				var shipOther = [];
				$("#shipwideOtherDiv").find("input[name='shipWideOtherChoose']:checked").each(function(){
					shipOther.push($(this).val());
				});
				if(shipOther.length == 0){
					tranOne["shipLocationIn"] = "Worldwide";
				}else{
					tranOne["shipLocationIn"] = shipOther.join(",");
				}
				debugger
			}
			
			tranOne["tranKind"] = 1;
			tranOne["tranOrder"] = i;
			if(!ocs.stringIsNull($(this).find("select[name=domesticShipType]").val())){
				calCulateTrans.push(tranOne);
			}
			i++;
		});
	}
	trans["calCulateTrans"] = calCulateTrans;
	trans["domesticOptDay"] = $('#ddlDispatchTimeMax').val();
	//noShip SHIP_LOCATION_OVER shipLocationOver 
	var noShip = [];
	var noShipVal="";
	$("#noArriveRegion").children("span").each(function(){
		noShipVal = $(this).attr("value");
		if($.trim(noShipVal)<=0){
			$(this).remove();
			return true;
		}
		noShip.push(noShipVal)
	});
	
	trans["shipLocationOver"] = noShip.join(",");
	return trans;

}



var renderTransChooseModel = function(transChooseModel) {
	noShipSelected(transChooseModel);
	setSelectValue('#ddlDispatchTimeMax',transChooseModel.domesticOptDay);
	//加载运输信息
	ocs.ajax({
		url:"/publication/getPublictonTransById/"+transChooseModel.id,
		type:"POST",
		data:"",
		success:function(result){
			if(result){
				var hasCalCulateTrans = false;
				$.each(result,function(){
					if(this.tranKind == 0){
						setSelectValue('#domesticShipType',this.domesticShipType);
						$("#domesticShipCost").val(this.domesticShipCost);
						if(this.domesticShipCost == 0){
							$("#domesticShipCostOpt").attr("checked",true);
						}else{
							$("#domesticShipCostOpt").attr("checked",false);
						}
						$("#domesticExtraCost").val(this.domesticExtraCost);
						$("#domesticShipAkHiPr").val(this.domesticShipAkHiPr);
					}else{
						hasCalCulateTrans = true;
						setSelectValue('#domesticShipType1',this.domesticShipType);
						$("#domesticShipCost1").val(this.domesticShipCost);
						if(this.domesticShipCost == 0){
							$("#chkShippingFreeInt1").attr("checked",true);
						}else{
							$("#chkShippingFreeInt1").attr("checked",false);
						}
						$("#domesticExtraCost1").val(this.domesticExtraCost);
						
						//shipLocationIn
						var shipLoactionValue = this.shipLocationIn;
						if(shipLoactionValue){
							$("#chkShip2WorldWide1").attr("checked",false);
							if(shipLoactionValue == 'Worldwide'){
								$("#chkShip2WorldWide1").attr("checked",true);
							}else{
								$("#shipwideOtherDiv").find("input").each(function(){
									var cValue = $(this).val();
									if(shipLoactionValue.indexOf(cValue) > -1){
										$(this).attr("checked",true);
									}else{
										$(this).attr("checked",false);
									}
								});
							}
						}else{
							$("#chkShip2WorldWide1").attr("checked",true);
						}
					}
				});
				
				if(hasCalCulateTrans){
					$("#InternationalShippingIsUse").attr("checked","checked");
					$("#InternationalShippingFrom").show();
				}else{
					$("#InternationalShippingIsUse").attr("checked",false);
					$("#InternationalShippingFrom").hide();
				}
			}
		}
	});
	
	
}

var stateChange = function(site){
	var sideIdText2=site.combobox('getValue');
	if(sideIdText2==203||sideIdText2==207||sideIdText2==3){
		$("#divAdditionalLocations").hide();
	}else if(sideIdText2==77){
		$("#POBox").next("label").text("Filialen und Shops der Versanddienstleister");
		$("#Packstationen2").show();
	}else if(sideIdText2==186){
		$("#POBox").next("label").text("Apartado postal");
	}else if(sideIdText2==212){
		$("#POBox").next("label").text("Skrytki pocztowe");
	}else if(sideIdText2==210||sideIdText2==23||sideIdText2==71){
		$("#POBox").next("label").text("Boîte postale");
	}else{
		$("#POBox").next("label").text("PO Box");
	}
}

var noShipSelected = function(transChooseModel){
	var sideIdText=$("#site").combobox('getValue');
	if($(".left .content ul").find("li").length<1){
		areaSelect();
		if(sideIdText==0||sideIdText==3||sideIdText==77){
			areaNoship();
		}
	}
	stateChange($("#site"));
	$("#noArriveRegion").html("");
	if(transChooseModel.shipLocationOver){
		 $("#ddlExclusionListType").val(2).attr("checked",true);
		var shipLocationString=transChooseModel.shipLocationOver;
		var shipLocationArr = shipLocationString.split(",");
		var noShipHtml ="";
		for (var i = 0; i < shipLocationArr.length; i++) {
	//		 $("input[type=checkbox][value='" + noShipChoose[i] +"']").attr("checked",'true');
			 var a =  $("input[type=checkbox][value='" + shipLocationArr[i] +"']").next("label").text();
			  noShipHtml +="<span value='"+shipLocationArr[i]+"'>"+a+"</span>&nbsp;,";
			
		}
		if(noShipHtml!=null||noShipHtml!=""){
			noShipHtml = noShipHtml.substr(0, noShipHtml.length - 1); 
		}
		$("#noArriveRegion").append(noShipHtml);
	    $("#dlEeclusionList").show();
	    $(".noArray-noSelected").hide();
	    $(".noArray-selected").show();
	}else{
		 $("#ddlExclusionListType").val(0).attr("checked",true);
		 $("#dlEeclusionList").hide();
		 $(".noArray-noSelected").show();
		 $(".noArray-selected").hide();
		 
	}
}

var renderSaveAsData = function(transChooseModel){
	setSelectValue('#ddlDispatchTimeMax',transChooseModel.domesticOptDay);
	var domesticTran = transChooseModel.domesticTrans[0];
	if(domesticTran){
		setSelectValue('#domesticShipType',domesticTran.domesticShipType);
		$("#domesticShipCost").val(domesticTran.domesticShipCost);
		if(domesticTran.domesticShipCost == 0){
			$("#domesticShipCostOpt").attr("checked",true);
		}else{
			$("#domesticShipCostOpt").attr("checked",false);
		}
		$("#domesticExtraCost").val(domesticTran.domesticExtraCost);
		$("#domesticShipAkHiPr").val(domesticTran.domesticShipAkHiPr);
	}
	
	var calCulateTran = transChooseModel.calCulateTrans[0];
	if(calCulateTran){
		setSelectValue('#domesticShipType1',calCulateTran.domesticShipType);
		$("#domesticShipCost1").val(calCulateTran.domesticShipCost);
		if(calCulateTran.domesticShipCost == 0){
			$("#chkShippingFreeInt1").attr("checked",true);
		}else{
			$("#chkShippingFreeInt1").attr("checked",false);
		}
		$("#domesticExtraCost1").val(calCulateTran.domesticExtraCost);
	}
	noShipSelected(transChooseModel);
}
var areaSelect = function(){
	var international=  $(".international").find(".subRegion content");
	var Ul = $(".international").find(".content ul");
	$.ajax({
		url:"/ocs/assets/app/json/internationalArea.json",
		type:"get",
		data:"",
		async:false,
		success:function(data){
				for(var i in data){
					for(var j = 0;j<data[i].length;j++){
						var index = data[i][j].code;
						var name = 
							"<li>" +
								"<input type='checkbox' id='"+data[i][j].id+"' value='"+data[i][j].value+"'>"+
								"<label style='padding-left:5px;' for='"+data[i][j].id+"'>"+data[i][j].country +"</label>"+
							"</li>"
						Ul.eq(index).append(name);
						$(".region .content").hide();
					}
				}
		},
		error:function(){
			console.log("请求失败")
		}
	})
};
var areaNoship = function(){
	var domestic=  $(".domestic").find(".domesticChoose .subRegion");
	$.ajax({
		url:"/ocs/assets/app/json/Domestic.json",
		type:"get",
		data:"",
		async:false,
		success:function(data){
				for(var i in data){
					for(var j = 0;j<data[i].length;j++){
						var index = data[i][j].code;
						var name = 
							"<input style='padding-right:5px;line-height:20px;' type='checkbox' id='"+data[i][j].value+"' value='"+data[i][j].value+"'>"+
							"<label style='padding-right:5px;line-height:20px;' for='"+data[i][j].value+"'>"+data[i][j].areaName+"</label>"
						domestic.eq(index).append(name)		
					}
				}
		},
		error:function(){
			console.log("请求失败")
		}
	})
}; 

var renderCalCulateObj = function(calCulateObj) {//回显计算类型运输数据
	setSelectValue('#ddlDispatchTimeMax',calCulateObj.domesticOptDay);

}
var contructTransChooseModel = function() {
	var transChooseModel = {
		transClauseExisPub : $('#chkShippingTermsInDescription').is(':checked'),// 运输条款是否已在刊登描述中
		calCulateObj : {},
		domestic : {
			transType : $('#ddlShippingDomType').val(),//运输方式（标准/计算）
			handTime : $('#ddlDispatchTimeMax').val(),//处理时间
			isExpressDelivery : $('#chkGetItFast').is(':checked'),//快速寄货
			transScheme : {
				standardInfos : [],
				calculateInfos : []
			}
		},
		international : {
			noShippingArea : $('#ddlExclusionListType').val(),//不运送地区
			transType : $('#ddlShippingIntType').val(),//运送方式（标准/计算）
			transScheme : {
				standardInfos : [],
				calculateInfos : []
			},
		}
	};
	
	return transChooseModel;
}

// 运送选项
// 运输类型为计算时
$("#ddlShippingDomType").on("change", function() {
	$(".DomesticShipping_wrap").find(".add_domesticShipping").remove();
	$("#hrefRemoveShippingDom").hide();
	id = 1;
	$('#hrefAddShippingDom').show();
	if ($(this).val() == 1 || $("#ddlShippingIntType").val() == 1) {
		// 初始化动态添加的运输(国内运输为计算时)
		$(".calculation").show();
		$(".standard").hide();
		$(".calculate_wrap").show();
	} else {
		// 初始化动态添加的运输(国内运输为标准时)
		$(".calculation").hide();
		$(".standard").show();
		$(".calculate_wrap").hide();
	}
});
$("#ddlShippingIntType").on("change", function() {
	$(".InternationalShipping_wrap").find(".add_internationalShipping").remove();
    $("#hrefRemoveShippingInt").hide();
    id2 = 0;
    if($(this).val() == 1 || $("#ddlShippingDomType") == 1){
        $(".calculate_wrap").show();
    } else {
        $(".calculate_wrap").hide();
    }
});
var transMap = {
	"1":"一",
	"2":"二",
	"3":"三",
	"4":"四",
	"5":"五",
	"6":"六"
};
// 国内运输添加
$("#hrefAddShippingDom").on("click",function() {
	var trans = $(".d_trans_flag");
	var last = trans[trans.length-1];
	var add = $(last).clone();
	$(last).after(add);
	$(add).find(".shippingtitle").text("第"+transMap[trans.length+1]+"运输");
});

$("#hrefRemoveShippingDom").on("click", function() {
	$(this).parents(".add_wrap").prev(".add_domesticShipping").remove();
	id = $(".add_domesticShipping").length + 1;
	$("#hrefAddShippingDom").show();
	if ($(".add_domesticShipping").length <= 0) {
		$("#hrefRemoveShippingDom").hide();
	}
});
var searchMap = function(num) {
	var result = null;
	$.each(transMap, function(n, value) {
		if (num == value.code) {
			result = value;
		}
	});
	return result;
};
// 国际运输添加
var id2 = 0;
$("#hrefAddShippingInt").on("click",function() {
			/*id2++;
			if ($("#ddlShippingIntType").val() == 0) {
				Shipping(searchMap(id2).name, id2, InternationalShipping,
						".addInternation_wrap", "#hrefAddShippingInt",
						"#hrefRemoveShippingInt",id2);
			} else {
				Shipping(searchMap(id2).name, id2, InternationalShipping2,
						".addInternation_wrap", "#hrefAddShippingInt",
						"#hrefRemoveShippingInt",id2);
			}*/
	id2++;
	$(this).before(InternationalShipping1(transMap[id2],id2))
});
$("#hrefRemoveShippingInt").on(
		"click",
		function() {
			$(this).parents(".addInternation_wrap").prev(
					".add_internationalShipping").remove();
			id2 = $(".add_internationalShipping").length;
			$("#hrefAddShippingInt").show();
			if (id2 == 0) {
				$("#hrefRemoveShippingInt").hide();
			}
		});


var InternationalShipping1= function(num,id){
    var InternationalShipping = '<div class="add_internationalShipping d_trans_flag2">'+
                                '<div class="form-group">'+
                                    '<label class="form_label col-md-2"></label>'+
                                    '<div class="col-md-4 form_inline">'+
                                        '<p class="shippingtitle">第'+num+'运输</p>'+
                                    '</div>'+
                                '</div>'+
                                '<div class="form-group">'+
                                    '<label class="form_label col-md-2">运输方式</label>'+
                                    '<div class="col-md-10 form_inline">'+
                                        '<select id="domesticShipType'+id+'" name="domesticShipType" class="form-control width_auto">'+
                                            '<option value="" selected="selected">-- 选择 --</option>'+
                                        '</select>'+
                                    '</div>'+
                                '</div>'+
                                '<div  class="form-group">'+
                                    '<label class="form_label col-md-2">运费</label>'+
                                    '<div  class="col-md-4 form_inline">'+
                                        '<input id="domesticShipCost'+id+'" name="domesticShipCost" onChange="cuShipChange(this);" type="text" value="0.00"  class="form-control input_small">'+
                                        '<span class="CSymbol help-inline">USD</span>'+
                                        '<input type="checkbox" name="chkShippingFreeInt'+id+'" onClick="cuShipCheckBoxClick(this);" checked="checked"><label class="free" for="chkShippingFreeInt'+id+'">免费</label>'+
                                    '</div>'+
                                '</div>'+
                                '<div id="dtShippingFeeAddInt'+id+'" class="form-group">'+
                                    '<label class="form_label col-md-2">额外每件加收</label>'+
                                    '<div id="ddShippingFeeAddInt'+id+'" class="col-md-4 form_inline">'+
                                        '<input id="domesticExtraCost'+id+'" name="domesticExtraCost" type="text" value="0.00" class="form-control input_small">'+
                                        '<span class="CSymbol help-inline">USD</span>'+
                                    '</div>'+
                                '</div>'+
                                '<div class="form-group">'+
                                    '<label class="form_label col-md-2"> 运到</label>'+
                                    '<div class="col-md-10 form_inline">'+
                                        '<div>'+
                                            '<input name="chkShipWorldWide'+id+'" type="checkbox" id="chkShip2WorldWide'+id+'" checked="checked" readOnly value="Worldwide"> <label for="chkShip2WorldWide">全球 </label>'+
                                        '</div>'+
                       
                                    '</div>'+
                                '</div>'+
                            '<div>';
    return InternationalShipping;
}




var constructDomStandardModel = function() {
	var domesticStandards = [];
	var standardInfo = {
		shippingType : $('#domesticShippingType_0').val(),// 运输方式
		fare : $('#domShippingCost_0').val(),// 运费
		isFreeDomShipping : $('#isFreeDomShipping_0').is(':checked'),// 是否免费
		additionalCost : $('#additionalCost_0').val(),// 额外每件加收
		akHiPrAdditionalCost : $('#akHiPrAdditionalCost_0').val()
	// AK,HI,PR额外加费
	};
	domesticStandards.push(standardInfo);
	if ($('.add_domesticShipping').length > 0) {
		$('.add_domesticShipping').each(
				function(index, obj) {
					var idIndex = index + 2;
					var standardObj = {
						shippingType : $('#domesticShippingType_' + idIndex)
								.val(),// 运输方式
						fare : $('#domShippingCost_' + idIndex).val(),// 运费
						isFreeDomShipping : $('#isFreeDomShipping_' + idIndex)
								.is(':checked'),// 是否免费
						additionalCost : $('#additionalCost_' + idIndex).val(),// 额外每件加收
						akHiPrAdditionalCost : $(
								'#akHiPrAdditionalCost_' + idIndex).val()
					// AK,HI,PR额外加费
					}
					domesticStandards.push(standardObj);
				});
	}
	return domesticStandards;
}
var constructDomCalculationModel = function() {
	var domesticCalculations = [];
	var calculationDefaultObj = {
			shippingType:$('#domCalcuShippingType_0').val()//运输方式
	};
	domesticCalculations.push(calculationDefaultObj);
	if ($('.add_domesticShipping').length > 0) {
		$('.add_domesticShipping').each(function(index, obj) {
			var idIndex = index + 2;
			var calculationObj = {
				shippingType : $('#domCalculationShipping_' + idIndex).val()
			// 运输方式
			}
			domesticCalculations.push(calculationObj);
		});
	}
	return domesticCalculations;
}
var constructInterStandardModel = function() {
	var interStandards = [];
	$('.add_internationalShipping').each(function(index,obj){
		var idIndex = index+1;
		var interStandardInfo = {
				shippingType : $('#ddlShippingType_'+idIndex).val(),// 运输方式
				fare : $('#shippingIntCost_'+idIndex).val(),// 运费
				additionalCost : $('#shippingIntAddCost_'+idIndex).val(),// 额外每件加收
				isShipWorldWide : $('#isShipWorldWide_'+idIndex).is(':checked'),
				supportSentTo : '',// 支持送到那些国家/区域
				isFreeInterShipping : $('#isFreeShippingInt_'+idIndex).is(':checked')
			};
		$('#chkShipTo_'+idIndex).find('input[type="checkbox"]').each(function(index,obj){
			if ($(this).is(':checked')) {
				interStandardInfo.supportSentTo+=$(this).val()+",";
			}
		});
		interStandards.push(interStandardInfo);
	});
	return interStandards;
}
var constructInterCalculationModel = function() {
	var interCalculations = [];
	$('.add_internationalShipping').each(function(index,obj){
		var idIndex = index+1;
		var interCalculatoinInfo = {
				shippingType : $('#ddlShippingType_'+idIndex).val(),// 运输方式
				isShipWorldWide : $('#isShipWorldWide_'+idIndex).is(':checked'),
				supportSentTo : ''// 支持送到那些国家/区域
			};
		$('#chkShipTo_'+idIndex).find('input[type="checkbox"]').each(function(index,obj){
			if ($(this).is(':checked')) {
				interCalculatoinInfo.supportSentTo+=$(this).val()+",";
			}
		});
		interCalculations.push(interCalculatoinInfo);
	});
	return interCalculations;
}

var Shipping = function(num, id, func, addWrap, addClass, removeClass,showNum) {// num:第几运输
	// func:添加的模板
	// addWrap:添加模板的最外层class
	// addClass:添加按钮的class removeClass:移除按钮的class
	if (id == showNum) {
		$(removeClass).show();
	} 
	if (id == 5) {
		$(addClass).hide();
	}
	$(addWrap).before(func(num, id));
}

$(function(){
	//$("#hrefAddShippingInt1").before(InternationalShipping1(transMap['1'],1));
	$("#domesticShipCost").on("change",function(){
		if($(this).val()>0){
			$("#domesticShipCostOpt").attr("checked",false);
		}else{
			$("#domesticShipCostOpt").attr("checked",true);
		}
	});
	$("#domesticShipCostOpt").on("click",function(){
		if($(this).is(":checked")){
			$("#domesticShipCost").val("0");
		}
	});
	
	$("#domesticShipCost1").on("change",function(){
		if($(this).val()>0){
			$("#chkShippingFreeInt1").attr("checked",false);
		}else{
			$("#chkShippingFreeInt1").attr("checked",true);
		}
	});
	$("#chkShippingFreeInt1").on("click",function(){
		if($(this).is(":checked")){
			$("#domesticShipCost1").val("0");
		}
	});
	
	$("#InternationalShippingIsUse").on("click",function(){
		if($(this).is(":checked")){
			$("#InternationalShippingFrom").show();
		}else{
			$("#InternationalShippingFrom").hide();
		}
	});
	
	$("#chkShip2WorldWide1").on("click",function(){
		if($(this).is(":checked")){
			$("#shipwideOtherDiv").find("input").each(function(){
				$(this).attr("checked",false);
			});
		}
	});
	
	$("#shipwideOtherDiv").on("click",".shipwideList",function(){
		$("#chkShip2WorldWide1").attr("checked",false);
	});
})