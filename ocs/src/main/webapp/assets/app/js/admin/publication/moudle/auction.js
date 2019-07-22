var renderAuctionModel = function(auctionModel) {
	setSelectValue('#discountId',auctionModel.discount);
	if (eval(auctionModel.individual)) {
		$('#chkPrivateAuction').attr('checked',true);
	}
	setSelectValue('#publicationDays',auctionModel.publicationDays);
	$("#auctionPrice").val(auctionModel.price);
	$("#reserverPrice").val(auctionModel.reserverPrice);
	$("#productCount").val(auctionModel.productCount);
	$("#sellerBaseCount").val(auctionModel.sellerBaseCount);
	if(auctionModel.publicationType == "Chinese"){
		if (eval(auctionModel.secondTrading)) {
			$('#chkSecondChanceOfferEnabled').attr('checked',true);
			$("#dlSecondChanceOffer").show();
			var secondTrading = auctionModel.secondTradinfo;
			if(secondTrading){
				secondTrading =  eval('(' + secondTrading + ')');
				$('#secondAuctionPrice').val(secondTrading.price);
				setSelectValue('#secondAuctionDays',secondTrading.days);
				$('#secondAuctionMessage').val(secondTrading.message)
			}
		
		}
	}else if(auctionModel.publicationType == "FixedPriceItem"){
		if(eval(auctionModel.acceptBuyerCounter)){
			$("#isOrNotChangePrice").attr('checked',true);
			$("#ChangePriceRefuse").val(auctionModel.acceptBuyerCounterMin);
			$("#ChangePriceAccept").val(auctionModel.acceptBuyerCounterMax);
			$("#ChangePriceDivAccept").show();
			$("#ChangePriceDivRefuse").show();
		}
		
	}
	
}
var contructAuctionModel = function(){
	var auctionModel = {
			discount:'dasfdasfdsaf',
			individual:"false",
			publicationDays:'4',
			price:'',
			reserverPrice:'',
			productCount:'',
			sellerBaseCount :'',
			secondTrading:'',
			secondTradinfo:'',
			acceptBuyerCounter:'',
			acceptBuyerCounterMin:'',
			acceptBuyerCounterMax:''
		};
	auctionModel.discount = $('#discountId').val();
	if( $('#chkPrivateAuction').is(':checked')){
		auctionModel.individual = "true";
	}else{
		auctionModel.individual = "false";
	}
	auctionModel.publicationDays = $('#publicationDays').val();
	auctionModel.price = $('#auctionPrice').val();
	auctionModel.reserverPrice = $('#reserverPrice').val();
	auctionModel.productCount = $('#productCount').val();
	auctionModel.sellerBaseCount = $('#sellerBaseCount').val();
	//auctionModel.secondTrading = $("#chkSecondChanceOfferEnabled").is(':checked');
	if ($("#chkSecondChanceOfferEnabled").is(':checked')) {
		auctionModel.secondTrading = 'true';
		var sencondTradingJson = {};
		sencondTradingJson["price"] = $('#secondAuctionPrice').val();
		sencondTradingJson["days"] = $('#secondAuctionDays').val();
		sencondTradingJson["message"] = $('#secondAuctionMessage').val();
		auctionModel.secondTradinfo = JSON.stringify(sencondTradingJson);
	}else{
		auctionModel.secondTrading = 'false';
	}
	if($("#publictionType").val()=="FixedPriceItem"){
		if($("#isOrNotChangePrice").is(':checked')){
			auctionModel.acceptBuyerCounter = 'true';
			auctionModel.acceptBuyerCounterMin = $("#ChangePriceRefuse").val();
			auctionModel.acceptBuyerCounterMax = $("#ChangePriceAccept").val();
		}else{
			auctionModel.acceptBuyerCounter = 'false';
		}
	}
	return auctionModel;
}
$(function(){
	//二次交易机会
    $("#chkSecondChanceOfferEnabled").on("change",function(){
        $("#dlSecondChanceOffer").toggle();
    });
})
