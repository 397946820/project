var renderProductPlaceModel = function(productPlaceModel) {
	$("#productAddress").val(productPlaceModel.productAddress);
	$("#region").combobox("setValue",productPlaceModel.region);
	$("#postCode").val(productPlaceModel.postCode);
}
var contructProductPlaceModel = function(){
	var productPlaceModel = {
			productAddress:'',
			region:'',
			postCode:''
		};
	productPlaceModel.productAddress = $('#productAddress').val();
	productPlaceModel.region = $("#region").combobox("getValue");
	productPlaceModel.postCode = $('#postCode').val();
	return productPlaceModel;
}
