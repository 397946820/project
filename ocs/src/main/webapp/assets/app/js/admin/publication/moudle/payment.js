var renderPaymentModel = function(paymentModel) {
	var supportInfo = paymentModel.supportPaypaiInfo;
	if(supportInfo){
		$("input[name=payMentType]").removeAttr("checked");
		$('input[name=payMentType]').each(function(index,obj){
			if (supportInfo.indexOf($(this).val()) >= 0) {
				$(this).attr("checked","checked");
			}
		});
	}else{
		$("input[name=payMentType]").removeAttr("checked");
	}

	setSelectValue('#paypaiAccount',paymentModel.paypaiAccount);
	$("#payMentDescription").textbox("setValue",paymentModel.payDescription);
	if(eval(paymentModel.autoPay)){
		$("#autoPay").attr('checked',true);
	}else{
		$("#autoPay").attr('checked',false);
	}
}
var contructPaymentModel = function(){
	var payMentModel = {
			paypaiAccount:'dasfdasfdsaf',
			supportPaypaiInfo:'',
			payDescription:'',
			autoPay :""
		};
	payMentModel.paypaiAccount = $('#paypaiAccount').val();
	payMentModel.payDescription = $('#payMentDescription').textbox("getValue");
	$('input[name="payMentType"]:checked').each(function(index,obj){
		payMentModel.supportPaypaiInfo += $(this).val() + ",";
	});
	if($("#autoPay").is(":checked")){
		payMentModel.autoPay = "true";
	}else{
		payMentModel.autoPay = "false";
	}
	
	return payMentModel;
}
var saveAs = function(payMentModel){
	ocs.ajax({
		url: "/paymentSelect/saveAs",
        type: "post",
        data:payMentModel
	});
}
$(function(){
	$("#payMentDescription").textbox("textbox").bind("keyup",function(){$("#payMentDescriptionLength").text($(this).val().length)})
})