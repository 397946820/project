<#import "dialog/addDiscount.ftl" as DISCOUNT />
<#import "dialog/categorySearch.ftl" as CATSEARCH />
<#import "dialog/categorySelect.ftl" as CATSELECT />
<#import "dialog/imgSelect.ftl" as IMGSELECT />
<#import "dialog/productSelect.ftl" as PROSELECT />
<#import "dialog/useExternalImg.ftl" as EXTERNALIMG />
<#import "dialog/productChoice.ftl" as PROCHOICE />
<#import "dialog/paymentSelect.ftl" as PAYMENTSELECT />
<#import "dialog/buyerRequireSelect.ftl" as BUYERREQUIRESELECT />
<#import "dialog/templateView.ftl" as TEMPLATEVIEW />
<#import "footer/footer.ftl" as FOOTER />
<#import "head/header.ftl" as HEADER />
<#import "moudle/advertFeature.ftl" as ADVERT />
<#import "moudle/auction.ftl" as AUCTION />
<#import "moudle/baseInfo.ftl" as BASEINFO />
<#import "moudle/buyerRequire.ftl" as BUYER />
<#import "moudle/ebayArticleComment.ftl" as ARTICLE />
<#import "moudle/other.ftl" as OTHER />
<#import "moudle/payment.ftl" as PAYMENT />
<#import "moudle/productPlace.ftl" as PRODUCTPLACE />
<#import "moudle/returnPolicy.ftl" as RETURNPOLICY />
<#import "moudle/transportChoose.ftl" as TRANS />
<#import "navigation/right.ftl" as NAV />
<@FTL.savePublication id="aLotModifyPublication" title="范本批量修改" 
add_script_files=[
'easyui/jquery-1.8.0.min.js',
'jqueryUI/jquery-ui-1.10.4.custom.min.js',
'easyui/jquery.easyui.min.js',
'admin/pic/ssi-uploader.js',
'admin/pic/category.js',
'ocs.js',
'image_upload.js',
'admin/public.js',
'admin/publication/jquery.ztree.core.js',
'admin/publication/bootstrap.min.js',
'admin/publication/moment.js',
'admin/publication/bootstrap-datetimepicker.js',
'admin/publication/bootstrapValidator.js',
'admin/publication/layer.js',
'admin/publication/model.js',
'admin/publication/moudle/baseInfo.js',
'admin/publication/moudle/productChoice.js',
'admin/publication/moudle/articleComment.js',
'admin/pic/pic.js',
'admin/ocs/mainDate.js',
'easyui/locale/easyui-lang-zh_CN.js',
'admin/publication/moudle/articleComment.js',
'admin/publication/moudle/trans/transportChoose.js',
'admin/publication/moudle/trans/ddlHtml.js',
'admin/publication/moudle/buyerRequire.js',
'admin/publication/moudle/auction.js',
'admin/publication/moudle/payment.js',
'admin/publication/moudle/advertFeature.js',
'admin/publication/moudle/returnPolicy.js',
'admin/publication/moudle/productPlace.js',
'admin/publication/moudle/other.js',
'admin/ocs/main.js',
'admin/publication/moudle/buyerRequire.js',
'admin/publication/save.js',
'admin/noArriveRegion/noArriveRegion.js'
]
add_style_files=[
'pic/ssi-uploader.min.css',
'publication/bootstrap-datetimepicker.min.css',
'publication/bootstrap.min.css',
'publication/bootstrapValidator.css',
'publication/model.css',
'publication/layer.css',
'publication/zTreeStyle.css',
'publication/color.css',
'publication/easyui.css',
'pic/index.css',
'pic/index2.css',
'publication/icon.css',
'noShip.css',
'publication/jqueryUI/jquery-ui-1.10.4.custom.min.css'
]>
<div class="model_container">
	<div class="model_content_wrapper">
		<div class="model_content">
			<div class="profile_left">
				<@HEADER.publicationHead></@>
				<@BASEINFO.publicationBaseInfo></@>
				<@ARTICLE.ebayArticleComment></@>
				<@AUCTION.auction></@>
				<@PAYMENT.payment></@>
				<@BUYER.buyerRequire></@>
				<@RETURNPOLICY.returnPolicy></@>
				<@PRODUCTPLACE.productPlace></@>
				<@TRANS.transportChoose></@>
				<@ADVERT.advertFeature></@>
				<@OTHER.other></@>
			</div>
			<div class="profile_right">
			<@NAV.navicatRight></@>
			</div>
		</div>
		<div class="footpanel">
			<@FOOTER.footer></@>
		</div>
		<@PROCHOICE.productChoice></@>
		<@PROSELECT.productSelect></@>
		<@CATSELECT.categorySelect></@>
		<@CATSEARCH.categorySearch></@>
		<@DISCOUNT.addDiscount></@>
		<@IMGSELECT.imgSelect></@>
		<@EXTERNALIMG.useExternalImg></@>
		<@PAYMENTSELECT.paymentSelect></@>
		<@TEMPLATEVIEW.templateView></@>
	</div>
</div>

<script type="text/javascript" src="${FTL.X.global_domain}/assets/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript">
	

	
	var selected = undefined;
	//选择更改的属性跟页面关联
	var keyMap = {
		"EBAY_ACCOUNT":"ebayAccount",
		"PUBLICATION_TYPE":"publicationType",
		"SITE_ID":"siteId",
		"SKU":"sku",
		"PRODUCT_TITLE":"productTitle",
		"PRODUCT_SUBTILTE":"productSubtitle",
		"PRODUCT_FIRST_CATEGORY_ID":"productFirstCategoryId",
		"PRODUCT_SECOND_CATEGORY_ID":"productSecondCategoryId",
		"STORE_FIRST_CATEGORY_ID":"storeFirstCategoryId",
		"STORE_SECOND_CATEGORY_ID":"storeSecondCategoryId",
		"TOP_PROMOTION_TYPE":"topPromotionType",
		"FOOTER_PROMOTION_TYPE":"footerPromotionType",
		"SELLER_DESCRIPTION":"sellerDescription",
		"ADVERT_ID":"advert_id",
		"EBAY_IMAGES":"ebayImages",
		"TEMPLATE_IMAGES":"templateImages",
		"COMMENTS":"comments",
		"PUBLICATION_DAYS":"publicationDays",
		"PRICE":"price",
		"PRODUCT_COUNT":"productCount",
		"PAYPAI_ACCOUNT":"paypaiAccount",
		"AUTO_PAY":"autoPay",
		"SUPPORT_PAYPAI_INFO":"supportPaypaiInfo",
		"PAY_DESCRIPTION":"payDescription",
		"POLICY_TYPE":"policyType",
		"RETURN_DAYS":"returnDays",
		"ALLOW_DELAY":"allowDelay",
		"RETURN_TYPE":"returnType",
		"FARETAKEINHANDER":"faretakeinhander",
		"DEPRECIATION_RATE":"depreciationRate",
		"RETURN_DESCRIPTION":"returnDescription",
		"PRODUCT_ADDRESS":"productAddress",
		"REGION":"region",
		"POST_CODE":"postCode",
		"domesticTrans":"domesticTrans",
		"calCulateTrans":"calCulateTrans",
		"DOMESTIC_OPT_DAY":"domesticOptDay",
		"SHIP_LOCATION_OVER":"shipLocationOver",
		"ACCEPT_BUYER_COUNTER":"acceptBuyerCounter",
		"ACCEPT_BUYER_COUNTER_MIN":"acceptBuyerCounterMin",
		"ACCEPT_BUYER_COUNTER_MAX":"acceptBuyerCounterMax",
		"ALLOW_ALLBUYER":"allowAllbuyer",
		"NO_PAYPAL":"noPaypai",
		"OUT_SHIP_COUNTRY":"outShipCountry",
		"BUYER_REQ_1":"buyerReq1",
		"BUYER_REQ_2":"buyerReq2",
		"BUYER_REQ_3":"buyerReq3",
		"BUYER_REQ_4":"buyerReq4",
		"BUYER_REQ_4_1":"buyerReq41"
	};
	
	function loadSuccess(data){
		//通用隐藏
		$(".profile_right").hide();
		$("#lbtnRefresh").next().hide();
		$("#lbtnRefresh").hide();
		$("#articleCommentSelect").hide();
		$("#articleCommentSelectSave").hide();
		$("#itemPropertiesSelect").hide();
		$('#itemPropertiesSave').hide();
		$("#PaymentSelect").hide();
		$("#PaymentSelectSave").hide();
		$("#buyerRequireSelect").hide();
		$("#buyerRequireSelectSave").hide();
		$("#returnPolicySelect").hide();
		$("#returnPolicySelectSave").hide();
		$("#productPlaceSelect").hide();
		$("#productPlaceSelectSave").hide();
		$("#transportChooseSelect").hide();
		$("#transportChooseSelectSave").hide();
		$("#spShowRemark").hide();
		$("#oldDescriptionView").hide();
		$(".profile_left").css("width","100%");
		//页面
		$(".blue_madison").each(function(){
			$(this).hide();
		});
		var keys = data["keys"];
		selected = keys;
		var viewFlag = data["viewFlag"];
		if(viewFlag){
			for(var i=0;i<viewFlag.length;i++){
				$("#"+viewFlag[i]).show();
				//一般信息
				if(viewFlag[i] == "tabbox1"){
					$("#Category").hide();
					$("#StoreCategory").hide();
					$("#ItemSpecificsandCondition1").hide();
					$("#productAttrTabDiv").hide();
					$("#"+viewFlag[i]).find(".form-group").each(function(){
						var link = $(this).attr("link");
						if(keys.toString().indexOf(link)==-1){
							 $(this).hide();
						}
					});
				}
				//eBay 物品描述
				if(viewFlag[i] == "tabbox5"){
					$("#ListTemplateSetting").hide();
					$("#eBayPicture").hide();
					$("#ListTemplatePicture").hide();
					$("#"+viewFlag[i]).find(".form-group").each(function(){
						var link = $(this).attr("link");
						if(keys.toString().indexOf(link)==-1){
							 $(this).hide();
						}else{
							if(link == "EBAY_IMAGES"){
								$("#eBayPicture").show();
							}
							if(link == "TEMPLATE_IMAGES"){
								$("#ListTemplatePicture").show();
							}
						}
					});
				}
				//拍卖
				if(viewFlag[i] == "tabbox9"){
					$("#"+viewFlag[i]).find(".form-group").each(function(){
						var link = $(this).attr("link");
						if(keys.toString().indexOf(link)==-1){
							 $(this).hide();
						}else{
							if(link == "PRODUCT_COUNT"){
								$("#productCount").attr("disabled",false);
							}
							if(link == "ACCEPT_BUYER_COUNTER"){
								$("#isOrNotChangePriceDiv").show();
							}
						}
					});
				}
				
				//付款
				if(viewFlag[i] == "tabbox10"){
					$("#"+viewFlag[i]).find(".form-group").each(function(){
						var link = $(this).attr("link");
						if(keys.toString().indexOf(link)==-1){
							 $(this).hide();
						}
					});
				}
				//付款
				if(viewFlag[i] == "tabbox13"){
					$("#"+viewFlag[i]).find(".form-group").each(function(){
						var link = $(this).attr("link");
						if(keys.toString().indexOf(link)==-1){
							 $(this).hide();
						}
					});
				}
				//运输
				if(viewFlag[i] == "tabbox14"){
					$("#DomesticShipping").hide();
					$("#InternationalShipping").hide();
					$("#noShippingLocationChoose").hide();
					$("#"+viewFlag[i]).find(".form-horizontal").each(function(){
						var link = $(this).attr("link");
						if(keys.toString().indexOf(link)==-1){
							 $(this).hide();
						}else{
							if(link == "domesticTrans"){
								$("#DomesticShipping").show();
							}
							if(link == "calCulateTrans"){
								$("#InternationalShipping").show();
							}
							if(link == "SHIP_LOCATION_OVER"){
								$("#noShippingLocationChoose").show();
							}
						}
					});
				}
			}
		}
	};
	
	function getData(){
		var data = {};
		debugger
		var requestdata = getPageData();
		for(var i=0;i<selected.length;i++){
			data[selected[i]] = requestdata[keyMap[selected[i]]];
		}
		return data;
	}

</script>
</@FTL.savePublication>