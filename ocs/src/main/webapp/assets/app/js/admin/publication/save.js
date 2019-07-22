function copyProperties(source,obj){
	for(var key in source){
		obj[key] = source[key]
	}
}
var getTitleAndItemNum =function(value,row,index){
	 if(value!=null){
		   return "<span style='color:blue'><a href='"+value+"' target='_blank'>有<a></span>";
	   }else{
		   return "无";
	   }


}
var getPicServlet = function (value,row,index) {
	return "<img width=60 height=60 src='"+row.url+"'/>";
}
function getPageData(){
	var requestdata = {
			id:$('#publicationId').val(),
			itemId:$('#publicationItemId').val(),
			end_publication_date:$('#endPublicationDate').val()
	};
	var baseInfoModel = contructBaseModel();//一般信息
	copyProperties(baseInfoModel,requestdata);
	var commentModel = constructCommentModel();//eBay物品描述constructCommentModel
	copyProperties(commentModel,requestdata);
	var auctionModel = contructAuctionModel();//拍卖
	copyProperties(auctionModel,requestdata);
	var paymentModel = contructPaymentModel();//付款
	copyProperties(paymentModel,requestdata);
	var buyerRequireModel = contructBuyerRequireModel();//买家要求
	copyProperties(buyerRequireModel,requestdata);
	var returnPolicyModel = contructReturnPolicyModel();//退货政策
	copyProperties(returnPolicyModel,requestdata);
	var productPlaceModel = contructProductPlaceModel();//物品所在地
	copyProperties(productPlaceModel,requestdata);
	var transChooseModel = transModel();//运输选项
	copyProperties(transChooseModel,requestdata);
	//var advertFeatureModel = contructAdvertFeatureModel();//广告特色
	var otherModel = contructOtherModel();//其他
	copyProperties(otherModel,requestdata);
	return requestdata;
}
var endLinkPublication = function(){
	$("#itemId").val($("#publicationItemId").val());
    $('#endDialog').dialog('open').dialog('center').dialog('setTitle','立即结束');
}

function endPublication(){
	
	var itemId = $("#itemId").val();
	var endingReason = $("#endingReasonId").combobox("getValue");
	var data={};
	data["itemId"]=itemId;
	data["endingReason"]=endingReason;
	
	data = JSON.stringify(data)
	mainAjax('/Item/endItem',data,'正在结束产品中......',"post",function(result) {
	    $.messager.alert({
	      	 title:'消息',
	      	 msg:result.description,
	      	 width:'600px',
	      	 height:'500px'
	      	 
	       });

	    $('#endDialog').dialog('close');
	   });
	
}
var savePublication = function() {
	var requestdata = getPageData();
	
	if(requestdata.publicationType&&requestdata.publicationType=="FixedPriceItem1"){
		$("#productEAN_ISBN_UPC").removeClass("ocs_required");
	}else{
		$("#productEAN_ISBN_UPC").addClass("ocs_required");
	}
	//验证数据
	var state = $(this).attr("state");
	if(!ocs.validate()){
		return;
	}
	
	requestdata['state']=state;
	 $.ajax({
         url: GLOBAL.domain + "/publication/add",
         type: 'post',
         dataType: 'json',
         beforeSend:function(){
        	 $.messager.progress({
        	        title: '请稍后',
        	        msg: '正在操作中'
        	    });
         },
         complete:function () {
        	    $.messager.progress('close');
        	},
         data:JSON.stringify(requestdata),
 		 contentType: "application/json",
         success: function (response) {
        	 $('#publicationId').val(response.data.id);
        	 $.messager.alert({
    	      	 title:'消息',
    	      	 msg:response.description,
    	      	 width:'600px',
    	      	 height:'500px'
    	      	 
    	       });
         },
         error: function () {
            alert("保存失败");
         }
     });
}
var renderPage = function (id) {
	$.ajax({
        url: GLOBAL.domain + "/publication/getById",
        type: 'get',
        dataType: 'json',
        data:'id='+id,
        success: function (response) {
        	renderCommentModel(response.articleComment);
        	renderAuctionModel(response.auction);
        	renderPaymentModel(response.payment);
        	renderBuyerRequireModel(response.buyerRequire);
        	renderReturnPlicyModel(response.returnPolicy);
        	renderProductPlaceModel(response.productPlace);
        	//renderAdvertFeatureModel(response.advertFeature);
        	renderOtherModle(response.other);
        	renderTransChooseModel(response.transChoose);
        },
        error: function () {
           alert("保存失败");
        }
    });
}


//初始化页面
function initPage(){
	//站点联动
	$('#site').combobox({
		valueField :"value",
		textField :"displayName",
		url : "/ocs/publication/getSiteList",
		onChange : function(newValue,oldValue){
			if(newValue==77){
				$("#productCode2").html("Herstellernummer");
				$("#productCode3").html("Marke");
				$("#productUinVidId").attr("style","display:block;")
				
			}else{
				$("#productUinVidId").attr("style","display:none;");
				$("#productCode2").html("MPN");
				$("#productCode3").html("Brand");

			}
			$('#advert_id').combogrid({
				url:GLOBAL.domain+'/AdvertTemplates/list',
				onBeforeLoad:function(param){
					param.param.ebay_account=$('#ebayAccount').combobox("getValue");
					param.param.site_id=$('#site').combobox("getValue");
					
				}
			});
			if(newValue != oldValue){
				
				$('#sellerDescriptionTemp').combobox("clear");
				initSiteRelation(newValue);
				siteIdAll = newValue;

           		$("#upeBayCatText1").html("");
                $("#upeBayCatId1").html("");
 
           		$("#upeBayCatText2").html("");
                $("#upeBayCatId2").html("");

           		$("#UpeBayCatText3").html("");
                $("#upeBayCatId3").html("");

           		$("#UpdatePanel5").html("");
                $("#upeBayCatId6").html("");
                if(curPublictionType&&curPublictionType=="FixedPriceItem1"){
                	//更改多属性显示的值
                    if(newValue == 0){
                    	$("#upcmpnCode").text("UPC");
                    	$("#upcmpnCode").attr("head","UPC");
                    	$(".moreAttrValueTable tbody").find("input[name='EAN']").each(function(){
                    		$(this).attr("name","UPC");
                    	});
                    }else{
                    	$("#upcmpnCode").text("EAN");
                    	$("#upcmpnCode").attr("head","EAN");
                    	$(".moreAttrValueTable tbody").find("input[name='UPC']").each(function(){
                    		$(this).attr("name","EAN");
                    	});
                    }
                    curMoreAttr = moreAttrModel("FixedPriceItem1");
                }
                
			}
		}
	});
	var siteId = $('#site').combobox("getValue");
	if(siteId!=77){
		$("#productUinVidId").attr("style","display:none;")

	}else{
			$("#productCode2").html("Herstellernummer");
			$("#productCode3").html("Marke");

	}
	
	var curPublictionType = "Chinese";
	//刊登类型联动
	$("#rbleBayListType").find("input[name=rbleBayListType]").on("click",function(){
		if(this.checked){
			initPublicationTypeRelation(this.value);
			curPublictionType = this.value;
		}
	})
	//账号
	$("#ebayAccount").combobox({
		valueField :"account",
		textField :"account",
		url : "/ocs/publication/getAccounts",
		onChange : function(newValue,oldValue){
			$('#advert_id').combogrid({
				url:GLOBAL.domain+'/AdvertTemplates/list',
				onBeforeLoad:function(param){
					param.param.ebay_account=$('#ebayAccount').combobox("getValue");
					param.param.site_id=$('#site').combobox("getValue");
					
				}
			});
		}
	});
	
	$("#isOrNotChangePrice").on('change',function(){
		if($(this).is(':checked')){
			$("#ChangePriceDivAccept").show();
			$("#ChangePriceDivRefuse").show();
		}else{
			$("#ChangePriceDivAccept").hide();
			$("#ChangePriceAccept").val("");
			$("#ChangePriceDivRefuse").hide();
			$("#ChangePriceRefuse").val("");
		}
	});
	
	//初始化模块合并的信息
	$("#m_siteId").combobox({
		url:'/ocs/publication/getSiteList',
	    valueField:'value',
	    textField:'displayName',
	    onChange : function(newValue,oldValue){
			
		}
	});
}
var money = {
		"0":"USD",
		"3":"GBP",
		"77":"EUR",
		"71":"EUR",
		"101":"EUR"
};
//编辑初始化站点相关联动数据
function initSiteRelation(siteId){
	//货币
	$(".CSymbol").each(function(){
		var siteId = $('#site').combobox("getValue");
		$(this).text(money[siteId]);
	});
	//卖家描述
	$('#sellerDescriptionTemp').combobox("reload", "/ocs/publication/getSellerDescription/"+siteId);
	//退货政策
	ocs.ajax({
		url:"/publication/getReturnPolicyData/"+siteId,
		type:"POST",
		data:"",
		success:function(returnData){
			if(returnData){
				var optionHtml = ["<option selected value></option>"];
				var retunType = returnData.returns_accepted;
				if(retunType){
					retunType = eval('('+retunType+')');
					$.each(retunType,function(){
						optionHtml.push(' <option value="'+this.returnsAcceptedOption+'">'+this.description+'</option>');
					});
					$("#policyType").html(optionHtml.join(""));
					optionHtml = ["<option  selected value></option>"];
				}else{
					$("#policyType").html("");
				}
				var returnDays = returnData.returns_within;
				if(returnDays){
					returnDays = eval('('+returnDays+')');
					$.each(returnDays,function(){
						optionHtml.push(' <option value="'+this.returnsWithinOption+'">'+this.description+'</option>');
					});
					$("#returnDays").html(optionHtml.join(""));
					optionHtml = ["<option  selected value></option>"];
				}else{
					$("#returnDays").html("");
				}
				var returnKind = returnData.refund;
				if(returnKind){
					$("#dtReturnType").show();
					returnKind = eval('('+returnKind+')');
					$.each(returnKind,function(){
						optionHtml.push(' <option value="'+this.refundOption+'">'+this.description+'</option>');
					});
					$("#returnType").html(optionHtml.join(""));
					optionHtml = ["<option selected value></option>"];
				}else{
					$("#returnType").html("");
					$("#dtReturnType").hide();
				}
				var returnPayCost = returnData.shippingcost_paidby;
				if(returnPayCost){
					returnPayCost = eval('('+returnPayCost+')');
					$.each(returnPayCost,function(){
						optionHtml.push(' <option value="'+this.shippingCostPaidByOption+'">'+this.description+'</option>');
					});
					$("#fareTakeInHander").html(optionHtml.join(""));
					optionHtml = ["<option selected value></option>"];
				}else{
					$("#fareTakeInHander").html("");
				}
				var returnOldCost = returnData.restocking_feevalue;
				if(returnOldCost){
					$("#dtReturnRestockingFee").show();
					returnOldCost = eval('('+returnOldCost+')');
					$.each(returnOldCost,function(){
						optionHtml.push(' <option value="'+this.restockingFeeValueOption+'">'+this.description+'</option>');
					});
					$("#depreciationRate").html(optionHtml.join(""));
					optionHtml = ["<option selected value></option>"];
				}else{
					$("#depreciationRate").html("");
					$("#dtReturnRestockingFee").hide();
				}
				var returnDesc = eval(returnData.description)?true:false;
				if(returnDesc){
					$("#returnPolicyDescriptionDiv").show();
				}else{
					$("#returnPolicyDescriptionDiv").hide();
				}
			}else{
				$("#policyType").html("");
				$("#returnDays").html("");
				$("#returnType").html("");
				$("#fareTakeInHander").html("");
				$("#depreciationRate").html("");
				$("#returnPolicyDescriptionDiv").hide();
			}
		}
	});
	//清空分类，物品属性等
	$("#productFirstCategory").val("");
	$("#productSecondCategory").val("");
	$("#storeFirstCategory").val("");
	$("#storeSecondCategory").val("");
	$(".specificslist .prospecificsremove").each(function(){
		var input = $(this).parent("td").next().text();
		if(input){
			$(this).parents("tr").remove();
		}
		
	});
	$("#prospecificsList").html("");
	//运输方式
	ocs.ajax({
		async:false,
		url:"/publication/getTransTypeData/"+siteId,
		type:"POST",
		data:"",
		success:function(returnData){
			var dom = [];
			var cu = [];
			//cu.push(' <option value="" selected="selected">-- 选择 --</option>');
			var firstSelect = true;
			if(returnData){
				$.each(returnData,function(){
					if(this.dataType == 1){
						if(firstSelect){
							dom.push('<option selected="selected" value="'+this.value+'">'+this.displayName+'</option>');
							firstSelect = false;
						}else{
							dom.push('<option value="'+this.value+'">'+this.displayName+'</option>');
						}
					}else{
						cu.push('<option value="'+this.value+'">'+this.displayName+'</option>');
					}
				});
			}
			$("#domesticShipTypeOptions").html(dom.join(""));
			if(cu.length ==0){
				$("#InternationalShippingFrom").hide();
				$("#InternationalShipping").hide();
			}else{
				$("#InternationalShippingFrom").show();
				$("#InternationalShipping").show();
				$("#domesticShipType1").html(cu.join(""));
			}
		}
	});
	//国际运输运输范围
	ocs.ajax({
		async:false,
		url:"/publication/getTransShipWide/"+siteId,
		type:"GET",
		data:"",
		success:function(returnData){
			if(returnData){
				var otherShipwide = [];
				$.each(returnData,function(){
					otherShipwide.push('<input name="shipWideOtherChoose" class="shipwideList" type="checkbox" id="shipWide_'+this.shippingLocation+'" value="'+this.shippingLocation+'" /> ');
					otherShipwide.push('<label for="shipWide_'+this.shippingLocation+'">'+this.description+'</label> ');
				});
				$("#shipwideOtherDiv").html(otherShipwide.join(""));
			}
		}
	});
	
	//付款
	ocs.ajax({
		async:false,
		url:"/publication/getPaymentSupportData/"+siteId,
		type:"POST",
		data:"",
		success:function(returnData){
			var paymentSupportHtml = [];
			if(returnData){
				var i = 1;
				$.each(returnData,function(){
					paymentSupportHtml.push('<li ><input id="paymentSupportList'+i+'"  type="checkbox" name="payMentType" value="'+this.value+'"><label for="paymentSupportList'+i+'">'+this.displayName+'</label></li>');
					i++;
				});
				$("#paymentTypeList").html(paymentSupportHtml.join(""));
			}
		}
	});
}
//初始化账号相关联动数据
function initAccountRelation(account){
	
}
//初始化拍卖相关联动数据
function initPublicationTypeRelation(type){
	if(type == "Chinese"){
		var pubDaysOption = [];
		pubDaysOption.push(' <option value="Days_1">1</option>');
		pubDaysOption.push(' <option value="Days_3">3</option>');
		pubDaysOption.push(' <option value="Days_5">5</option>');
		pubDaysOption.push(' <option value="Days_7">7</option>');
		pubDaysOption.push(' <option value="Days_10">10</option>');
		$("#publicationDays").html(pubDaysOption.join(""));
		$("#isOrNotChangePriceDiv").hide();
		$("#ChangePriceDivAccept").hide();
		$("#ChangePriceDivRefuse").hide();
		$("#auctionPriceLabelName").text("起始价格");
		$("#dtReservePrice").show();
		//$("#dlSecondChanceOfferLabel").show();
		$('#productCount').attr("disabled",true);
		$('#sellerBaseCount').attr("disabled",true);
		$("#dlVariation").hide();
	}else{ //if(type == "FixedPriceItem"){
		$("#auctionPriceLabelName").text("价格");
		var pubDaysOption = [];
		pubDaysOption.push(' <option value="Days_3">3</option>');
		pubDaysOption.push(' <option value="Days_5">5</option>');
		pubDaysOption.push(' <option value="Days_7">7</option>');
		pubDaysOption.push(' <option value="Days_10">10</option>');
		pubDaysOption.push(' <option value="Days_30">30</option>');
		pubDaysOption.push(' <option value="GTC">GTC</option>');
		$("#publicationDays").html(pubDaysOption.join(""));
		$("#isOrNotChangePriceDiv").show();
		$("#dtReservePrice").hide();
		$("#dlSecondChanceOfferLabel").hide();
		$("#dlSecondChanceOffer").hide();
		$('#productCount').attr("disabled",false);
		$('#sellerBaseCount').attr("disabled",false);
		$("#reserverPrice").val("");
		if(type=="FixedPriceItem1"){
			$("#dlVariation").show();
			$("#auctionPrice").parents(".form-group").hide();
			$("#productCount").parents(".form-group").hide();
			$("#productCode").parents(".form-group").hide();
			var ccSite = $('#site').combobox("getValue");
			 if(ccSite == 0){
             	$("#upcmpnCode").text("UPC");
             	$("#upcmpnCode").attr("head","UPC");
             	$(".moreAttrValueTable tbody").find("input[name='EAN']").each(function(){
             		$(this).attr("name","UPC");
             	});
             }else{
             	$("#upcmpnCode").text("EAN");
             	$("#upcmpnCode").attr("head","EAN");
             	$(".moreAttrValueTable tbody").find("input[name='UPC']").each(function(){
             		$(this).attr("name","EAN");
             	});
             }
             curMoreAttr = moreAttrModel("FixedPriceItem1");
		}else{
			$("#dlVariation").hide();
			$("#auctionPrice").parents(".form-group").show();
			$("#productCount").parents(".form-group").show();
			$("#productCode").parents(".form-group").show();
		}
	}
}
var oldDesciprionData = undefined;
$(function(){
	//模板描述
	KindEditor.ready(function(K) {
        window.editor = K.create('#commentContent', {
        	  width: '100%',
              height: '450px',
     		afterCreate : function() { 
    			this.sync(); 
            }, 
            afterBlur:function(){ 
                this.sync(); 
            }        
    	});
	});
	initPage();
	$('#saveAndPublication').on('click',savePublication);
	$('#endPublication').on('click',endLinkPublication);
	$('#btnSave').on('click',savePublication);

	var id = $('#publicationId').val();
	if (id != ''){
		renderPage(id);
	}
	$("#rbleBayListType li").on("click",function(){
		$("#publictionType").val($(this).find("input").val());
	});
	//退货政策联动
	$("#policyType").on("change",function(){
		if($(this).val() == "ReturnsNotAccepted"){
			$("#dlReturnPolicyDetail").hide();
		}else{
			$("#dlReturnPolicyDetail").show();
		}
	});
	var params = window.location.search;
	if(params){
		
		//var conditions = parent.ocsPublication.getConditions();
		ocs.ajax({
			url: "/publication/getById"+params,
	        type: "GET",
	        data:"",
	        success:function(data){
	        	if(data){
	        		$('#publicationId').val(data.id);
	        		var itemId = data.itemId;
	        		
	        		var endPublicationDate = data.end_publication_date;
	        		$('#publicationItemId').val(itemId);
	        		$('#endPublicationDate').val(endPublicationDate);
	        		var sysdate  = new Date().getTime();
	        		if(itemId==null ||itemId==''){
	        			$('#saveAndPublication').val("保存并发布");
	        			//模块组合
	        			$("#btnElementSetting").show();
	        		}else{
	        			if((endPublicationDate>sysdate||data.publicationDays=="GTC")&&data.ending_state=="N"){
	        				$('#saveAndPublication').val("保存并更新");  
	        				$("#endPublication").show(); 
	        			}else{
	        				$('#saveAndPublication').val("重新刊登");
	        			}
	        		}
	        		if(data.shipLocationOver===null){
	        			$("#ddlExclusionListType option[value='0']").attr("selected","selected");
	        		}
	        		if(data.itemId){
	        			//禁用站点、刊登类型、账号
	        			$("#site").combobox({ disabled: true });
	        			$("#ebayAccount").combobox({disabled:true});
	        			$("#rbleBayListType").find("input[type=radio]").each(function(){
	        				$(this).attr("disabled","disabled");
	        			});
	        		}
	        		//初始化联动数据
	        		var siteId = data.siteId;
	        		siteIdAll = siteId;
	        		initSiteRelation(siteId);
	        		var ebayAccount = data.ebayAccount;
	        		initAccountRelation(ebayAccount);
	        		var publicationType = data.publicationType;
	        		initPublicationTypeRelation(publicationType);
	        		baseInfoSetValue(data);
	        		renderAuctionModel(data);
	        		renderPaymentModel(data);
	        		renderBuyerRequireModel(data);
	        		renderReturnPlicyModel(data);
	        		renderProductPlaceModel(data);
	        		renderCommentModel(data);
	        		renderOtherModle(data);
	        		renderTransChooseModel(data);
	        		oldDesciprionData = data.oldDescription;
	        		if(data.itemId){
	        			
	        			//禁用多属性部分操作
	        			//$("#lbtnAddNewVariationRow").hide();
	        			/*$(".add_line").each(function(){
	        				$(this).hide();
	        			});*/
	        			/*$(".del_line").each(function(){
	        				$(this).hide();
	        			});
	        			$("#moreAttrList").find("input").each(function(){
	        				$(this).attr("disabled","disabled");
	        			});
	        			$(".moreAttrValueTable").find("input[name=SKU]").each(function(){
	        				$(this).attr("disabled","disabled");
	        			});	        			
	        			$(".moreAttrValueDel").each(function(){
	        				$(this).hide();
	        			});*/
	        			$("#btnSave").hide();
	        		}
	        	}
	        }
		});
	}else{
		$('#productCount').attr("disabled",true);
		$('#sellerBaseCount').attr("disabled",true);
		$("#btnElementSetting").show(); 
	}
	
	//模块合并
	$("#btnElementSetting").on('click',function(){
		$("#modelManagerAddfm").form("reset");
		$("#m_modeSeleted").combobox("setValues","");
		page.layerOpen("660px","620px",".modelGroupModel");
		$(".layui-layer").css("z-index",99999);
		$(".layui-layer-shade").css("z-index",99998);
	});
	
	$('#m_siteId').combobox({
		onChange : function(newValue,oldValue){
			if(newValue != oldValue){
				$('#sellerDescriptionTemp').combobox("clear");
				$('#sellerDescriptionTemp').combobox("reload", "/ocs/publication/getSellerDescription/"+newValue);
				//初始化数据
				$('#m_payment').combobox("clear");
				$('#m_payment').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/1");
				
				$('#m_buyRequire').combobox("clear");
				$('#m_buyRequire').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/2");
				
				$('#m_returnPolicy').combobox("clear");
				$('#m_returnPolicy').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/3");
				
				$('#m_itemPlace').combobox("clear");
				$('#m_itemPlace').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/4");
				
				$('#m_tran').combobox("clear");
				$('#m_tran').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/5");
				
				//$('#m_noShipPlace').combobox("clear");
				//$('#m_noShipPlace').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/1");
			}
		}
	});
	
	$('#m_modeSeleted').combobox({
		onChange : function(newValue,oldValue){
			if(newValue != oldValue&&newValue){
				ocs.ajax({
					url:"/publication/getModeSet/"+newValue,
			        type: "post",
			        data:"",
			        success:function(data){
			        	if(data.data){
			        		var formData = eval('(' + data.data + ')');
			        		$("#modelManagerAddfm").form("load",formData);
			        	}
			        }
				});
			}
		}
	});
	
	$("#modeSelectOk").click(function(){
		var id = $('#m_modeSeleted').combobox("getValue");
		//根据设置选择数据
		if(id){
			ocs.ajax({
				url:"/publication/getModeSet/"+id,
		        type: "post",
		        data:"",
		        success:function(data){
		        	if(data.data){
		        		var setData = eval("("+data.data+")");
		        		/**
		        		 * {"m_name":"dsasdaddada1211111678",
		        		 * "m_siteId":"3",
		        		 * "m_account":"testuser_yangguanbao",
		        		 * "m_topPromotionType":"31",
		        		 * "m_footerPromotionType":"32",
		        		 * "m_sellerDescriptionTemp":"1517",
		        		 * "m_payment":"6",
		        		 * "m_buyRequire":"21",
		        		 * "m_returnPolicy":"44",
		        		 * "m_itemPlace":"40",
		        		 * "m_tran":"38"}
		        		 */
		        		for(var key in setData){
		        			if(key == "m_siteId"){
		        				$('#site').combobox("setValue",setData[key]);
		        			}else if(key == "m_account"){
		        				$('#ebayAccount').combobox("setValue",setData[key]);
		        			}else if(key == "m_topPromotionType"){
		        				$('#topPromotionType').combobox("setValue",setData[key]);
		        			}else if(key == "m_footerPromotionType"){
		        				$('#footerPromotionType').combobox("setValue",setData[key]);
		        			}else if(key == "m_sellerDescriptionTemp"){
		        				$('#sellerDescriptionTemp').combobox("setValue",setData[key]);
		        			}else{
		        				if(key == "m_name"){
		        					continue;
		        				}
		        				var mId = setData[key];
		        				ocs.ajax({
		        					url:"/publication/getModeSet/"+mId,
		        			        type: "post",
		        			        data:"",
		        			        success:function(data1){
		        			        	if(data1.data){
		        			        		var mData = eval("("+data1.data+")");
		        			        		if(key == "m_payment"){
		    		        					renderPaymentModel(mData);
		    			        			}else if(key == "m_buyRequire"){
		    			        				renderBuyerRequireModel(mData);
		    			        			}else if(key == "m_returnPolicy"){
		    			        				renderReturnPlicyModel(mData);
		    			        			}else if(key == "m_itemPlace"){
		    			        				renderProductPlaceModel(mData);
		    			        			}else if(key == "m_tran"){
		    			        				renderSaveAsData(mData);
		    			        			}
		        			        	}
		        			        }
		        				});
		        				
		        			}
		        			
		        		}
		        		layer.closeAll();
		        	}
		        }
			});
		}else{
			alert('请选择模块组合！');
		}
	});
	
	$("#modeSaveAs").click(function(){
		var ispass = $("#modelManagerAddfm").form("validate");
		if(ispass){
			var saveAsMode = {};
			var title = "";
			var siteId = "";
			var saveData = $("#modelManagerAddfm").serializeArray();
			var set = {};
			$.each(saveData,function(){
				if(this.name == "m_name"){
					title = this.value;
				}
				if(this.name == "m_siteId"){
					siteId = this.value;
				}
				set[this.name] = this.value;
			});
			
	    	saveAsMode["data"] = JSON.stringify(set);
	    	saveAsMode["title"] = title;
	    	saveAsMode["siteId"] = siteId;
	    	saveAsMode["dataType"] = 7;
			ocs.ajax({
				url:"/publication/saveAs",
		        type: "post",
		        data:saveAsMode,
		        success:function(data){
		        	alert('保存成功！');
		        	$('#m_modeSeleted').combobox("reload");
		        	$('#m_modeSeleted').combobox("setValue","");
		        	$("#modelManagerAddfm").form("reset");
		        }
			});
		}else{
			$.messager.alert('提示','红色为必填项！','warning');
			return;
		}
	});
})