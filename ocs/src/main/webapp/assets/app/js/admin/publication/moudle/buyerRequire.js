var renderBuyerRequireModel = function(buyerRequireModel) {

	if(eval(buyerRequireModel.allowAllbuyer)) {
		$("#rbtnBuyerRequirementSpecified1").attr('checked',true);
	}else{
		$('#rbtnBuyerRequirementSpecified2').attr('checked',true);
		$(".buyerrequirement").show();
		$('#isHavePaypalAccount').attr('checked',eval(buyerRequireModel.noPaypai)?true:false);
		$('#isOffscale').attr('checked',eval(buyerRequireModel.outShipCountry)?true:false);
		//多少天有多少弃标方案
		var buyerReq1 = buyerRequireModel.buyerReq1;
		if(!ocs.stringIsNull(buyerReq1)){
			$('#chkMaxUnpaidItemStrikesInfoSpecified').attr('checked',true);
			var bys = buyerReq1.split("|");
			setSelectValue('#ddlMaxUnpaidItemStrikesInfoCount',bys[0]);
			setSelectValue('#ddlMaxUnpaidItemStrikesInfoPeriod',bys[1]);
			$('#ddlMaxUnpaidItemStrikesInfoCount').attr('disabled',false);
			$('#ddlMaxUnpaidItemStrikesInfoPeriod').attr('disabled',false);
		}else{
			$('#chkMaxUnpaidItemStrikesInfoSpecified').attr('checked',false);
			$('#ddlMaxUnpaidItemStrikesInfoCount').attr('disabled',true);
			$('#ddlMaxUnpaidItemStrikesInfoPeriod').attr('disabled',true);
		}
		//多少天内违反政策检举
		var buyerReq2 = buyerRequireModel.buyerReq2;
		if(!ocs.stringIsNull(buyerReq2)){
			$('#chkMaxBuyerPolicyViolationsSpecified').attr('checked',true);
			var bys = buyerReq2.split("|");
			setSelectValue('#ddlMaxBuyerPolicyViolationsCount',bys[0]);
			setSelectValue('#ddlMaxBuyerPolicyViolationsPeriod',bys[1]);
			$('#ddlMaxBuyerPolicyViolationsCount').attr('disabled',false);
			$('#ddlMaxBuyerPolicyViolationsPeriod').attr('disabled',false);
		}else{
			$('#chkMaxBuyerPolicyViolationsSpecified').attr('checked',false);
			$('#ddlMaxBuyerPolicyViolationsCount').attr('disabled',true);
			$('#ddlMaxBuyerPolicyViolationsPeriod').attr('disabled',true);
		}
		//信用指数
		var buyerReq3 = buyerRequireModel.buyerReq3;
		if(!ocs.stringIsNull(buyerReq3)){
			$('#chkMinFeedbackScoreSpecified').attr('checked',true);
			setSelectValue('#ddlMinFeedbackScore',buyerReq3);
			$('#ddlMinFeedbackScore').attr('disabled',false);
		}else{
			$('#chkMinFeedbackScoreSpecified').attr('checked',false);
			$('#ddlMinFeedbackScore').attr('disabled',true);
		}
		//在过去10天内曾出价或购买我的物品，已达到我所设定的限制
		var buyerReq4 = buyerRequireModel.buyerReq4;
		if(!ocs.stringIsNull(buyerReq4)){
			$('#chkMaxItemRequirementsMaxItemCountSpecified').attr('checked',true);
			setSelectValue('#ddlMaxItemRequirementsMaxItemCount',buyerReq4);
			$('#ddlMaxItemRequirementsMaxItemCount').attr('disabled',false);
			var buyerReq41 = buyerRequireModel.buyerReq41;
			if(!ocs.stringIsNull(buyerReq41)){
				$('#chkMaxItemRequirementsMinFeedbackScoreSpecified').attr('checked',true);
				setSelectValue('#ddlMaxItemRequirementsMinFeedbackScore',buyerReq41);
				$('#ddlMaxItemRequirementsMinFeedbackScore').attr('disabled',false);
			}else{
				$('#chkMaxItemRequirementsMinFeedbackScoreSpecified').attr('checked',false);
				$('#ddlMaxItemRequirementsMinFeedbackScore').attr('disabled',true);
			}
		}else{
			$('#chkMaxItemRequirementsMaxItemCountSpecified').attr('checked',false);
			$('#ddlMaxItemRequirementsMaxItemCount').attr('disabled',true);
		}
	}
}
var contructBuyerRequireModel = function(){
//	$('input:radio:checked').val()；
	var buyerRequireModel = {
			allowAllbuyer:"",
			noPaypai:"",
			outShipCountry:"",
			buyerReq1:"",
			buyerReq2:"",
			buyerReq3:"",
			buyerReq4:"",
			buyerReq41:""
		};
	if($('#rbtnBuyerRequirementSpecified1').is(':checked')){
		buyerRequireModel.allowAllbuyer = 'true';
	}else{
		buyerRequireModel.allowAllbuyer = 'false';
		buyerRequireModel.noPaypai = $('#isHavePaypalAccount').is(':checked')?'true':'false';
		buyerRequireModel.outShipCountry =$('#isOffscale').is(':checked')?'true':'false';
		if($('#chkMaxUnpaidItemStrikesInfoSpecified').is(':checked')){
			buyerRequireModel.buyerReq1 = $("#ddlMaxUnpaidItemStrikesInfoCount").val()+"|"+$("#ddlMaxUnpaidItemStrikesInfoPeriod").val();
		}else{
			buyerRequireModel.buyerReq1 = '';
		}
		if($('#chkMaxBuyerPolicyViolationsSpecified').is(':checked')){
			buyerRequireModel.buyerReq2 = $("#ddlMaxBuyerPolicyViolationsCount").val()+"|"+$("#ddlMaxBuyerPolicyViolationsPeriod").val();
		}else{
			buyerRequireModel.buyerReq2 = '';
		}
		if($('#chkMinFeedbackScoreSpecified').is(':checked')){
			buyerRequireModel.buyerReq3= $("#ddlMinFeedbackScore").val();
		}else{
			buyerRequireModel.buyerReq3 = '';
		}
		if($('#chkMaxItemRequirementsMaxItemCountSpecified').is(':checked')){
			buyerRequireModel.buyerReq4 = $("#ddlMaxItemRequirementsMaxItemCount").val();
			if(($('#chkMaxItemRequirementsMinFeedbackScoreSpecified').is(':checked'))){
				buyerRequireModel.buyerReq41 = $("#ddlMaxItemRequirementsMinFeedbackScore").val();
			}else{
				buyerRequireModel.buyerReq41 = '';
			}
		}else{
			buyerRequireModel.buyerReq4 = '';
		}
	}
	
	return buyerRequireModel;
}

$("#rbtnBuyerRequirementSpecified1").on("change", function() {
	if ($(this).prop("checked")) {
		$(".buyerrequirement").hide();
	} else {
		$(".buyerrequirement").show();
	}
});
$("#rbtnBuyerRequirementSpecified2").on("change", function() {
	if ($(this).prop("checked")) {
		$(".buyerrequirement").show();
	} else {
		$(".buyerrequirement").hide();
	}
});
$(function(){
	$("#tabbox11").on("change","input[type=checkbox]",function(){
		var li = $(this).parent("li");
		if($(this).is(":checked")){
			$(li).find("select").each(function(){
				$(this).attr('disabled',false);
			});
		}else{
			$(li).find("select").each(function(){
				$(this).attr('disabled',true);
			});
		}
	})
})