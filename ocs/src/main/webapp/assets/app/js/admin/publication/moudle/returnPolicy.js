var renderReturnPlicyModel = function(returnPolicyModel) {
	var policyType = returnPolicyModel.policyType;
	setSelectValue('#policyType',returnPolicyModel.policyType);
		if(policyType=="ReturnsAccepted"){
		setSelectValue('#returnDays',returnPolicyModel.returnDays);
		$('#isAllowDelay').attr('checked',eval(returnPolicyModel.allowDelay)?true:false);
		setSelectValue('#returnType',returnPolicyModel.returnType);
		setSelectValue('#fareTakeInHander',returnPolicyModel.faretakeinhander);
		setSelectValue('#depreciationRate',returnPolicyModel.depreciationRate);
		if(returnPolicyModel.returnDescription){
			$('#returnPolicyDescription').textbox("setValue",returnPolicyModel.returnDescription);
		}
		$("#dlReturnPolicyDetail").show();
	}else{
		$("#dlReturnPolicyDetail").hide();
	}

	
	
}
var contructReturnPolicyModel = function(){
	debugger;
	var returnPolicyModel = {
			policyType:'',
			returnDays:'',
			allowDelay:'',
			returnType:'',
			faretakeinhander:'',
			depreciationRate:'',
			returnDescription:''
		};
	returnPolicyModel.policyType = $('#policyType').val();
	returnPolicyModel.returnDays =$('#returnDays').val();
	/*var days = $('#returnDays').val();
	if(days == 60){
		returnPolicyModel.returnDays = 'Months_1';
	}else{
		returnPolicyModel.returnDays = 'Days_'+days;
	}*/
	/*returnPolicyModel.returnDays = $('#returnDays').val();*/
	if($('#isAllowDelay').is(":checked")){
		returnPolicyModel.allowDelay = 'true';
	}else{
		returnPolicyModel.allowDelay = 'false';
	}
	returnPolicyModel.returnType = $('#returnType').val();
	returnPolicyModel.faretakeinhander = $('#fareTakeInHander').val();
	returnPolicyModel.depreciationRate = $('#depreciationRate').val();
	returnPolicyModel.returnDescription = $('#returnPolicyDescription').textbox("getValue");
	return returnPolicyModel;
}
$(function(){
	$("#returnPolicyDescription").textbox("textbox").bind("keyup",function(){$("#returnPolicyDescriptionLength").text($(this).val().length)});
	
})