document.write('<script language="javascript" src="'+ GLOBAL.domain +'/assets/app/js/admin/ocs/main.js"></script>');
function lightSynchronou(){
	 var variationArray = $("#lightOrder").serializeArray();
 	 var params={};
	 $.each(variationArray,function(){
		 params[this.name] = this.value;
	 });
	 var string = JSON.stringify(params);
	 mainAjax('/Synchronou/lightSynchronou',string,'正在同步Light订单和订单详情数据......',"post",null);
}
function amazonSynchronou(){
	 var variationArray = $("#amazonOrder").serializeArray();
 	 var params={};
	 $.each(variationArray,function(){
		 params[this.name] = this.value;
	 });
	 var string = JSON.stringify(params);
	 mainAjax('/Synchronou/amazonSynchronou',string,'正在同步Amazon订单和订单详情数据......',"post",null);
}
function ebaySynchronou(){
	 var variationArray = $("#ebayOrder").serializeArray();
 	 var params={};
	 $.each(variationArray,function(){
		 params[this.name] = this.value;
	 });
	 var string = JSON.stringify(params);
	 mainAjax('/Synchronou/ebaySynchronou',string,'正在同步ebay订单和订单详情数据......',"post",null);
}