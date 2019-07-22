var renderAdvertFeatureModel = function(advertFeatureModel) {
	$('input[name="featureType"]').each(function(index,obj){
		if ((advertFeatureModel.featureType).indexOf($(this).val()) >= 0) {
			$(this).attr('checked',true);
		}
	});
}
var contructAdvertFeatureModel = function(){
	var advertFeatureModel = {
			featureType:'dasfdasfdsaf'
		};
	$('input[name="featureType"]:checked').each(function(){
		advertFeatureModel.featureType+=$(this).val()+"," 
	});
	return advertFeatureModel;
}
