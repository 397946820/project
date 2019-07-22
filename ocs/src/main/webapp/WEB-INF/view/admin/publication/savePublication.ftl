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
<#import "dialog/modelGroup.ftl" as MODELGROUP />
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
<@FTL.savePublication id="savePublication" title="添加范本" 
add_script_files=[
'easyui/jquery-1.8.0.min.js',
'jqueryUI/jquery-ui-1.10.4.custom.min.js',
'easyui/jquery.easyui.min.js',
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
		<@MODELGROUP.modelGroup></@>
	</div>
</div>
<script type="text/javascript">
 function verifyItemFees(){
	 var id = $("#publicationId").value;
	 $.ajax({
 		
			url: GLOBAL.domain+'/Item/publishedItem',
			
			data: {id:id},
			
			dataType: "json",
			
			contentType: "application/json; charset=UTF-8",
			
			type: "get",
		
			success: function(result) {
				     $.messager.alert("信息",result.description);
	
			},
			error: function(jqXHR, textStatus, errorThrown) {
				$.messager.alert("信息", "检查EBay费用失败！");		
			}
	   });
	 
 }
</script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/kindeditor/lang/zh_CN.js"></script>
</@FTL.savePublication>