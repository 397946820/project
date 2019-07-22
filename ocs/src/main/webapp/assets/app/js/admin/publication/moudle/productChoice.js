
$(function() {
	$("#clearSku").click(function() {
		$("#skuChecked").text("");
	});

	$("#skuSure").click(function() {
		var skuValue = $("#skuChecked").text();
		var skuId = $("#skuid").val();
		$("#productSKU").val(skuValue);
		$("#skuId").val(skuId);
		layer.closeAll();
	});
	
	$("#skuCancel").click(function(){
		layer.closeAll();
	});
	
	$("#query_product").click(function(){
		var param = {
				sku : $('#productChooseSku').val()
		}
		
		$('#productDatagrid').datagrid('load',{
			param : param
		});
	})
	
	$("#reset_product").click(function(){
		$("#productChooseSku").val(null);
	})
})