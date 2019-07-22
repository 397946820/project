$(function() {
	formatterDate('date');
	
	$("#operatingProfitSkuShipDataGrid").datagrid({
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        },
        onDblClickRow : function(rowIndex, rowData) {
        	$('#operatingProfitSkuShipForm').form('clear');
        	$('#operatingProfitSkuShipForm').form('load', rowData);
        	$('#operatingProfitSkuShipDialog').window('open');
        }
	})
	
	$("#operatingProfitSkuShipSaveLinkbutton").on('click', function(){
		if(!$("#operatingProfitSkuShipForm").form('validate')) {
			return;
		}
		var jsonData = $("#operatingProfitSkuShipForm").serializeArray();
		var data = [];
		jsonData.forEach(function(e){  
			data.push(e);
		});
		var url = GLOBAL.domain + "/operatingProfitSkuShip/saveEdit";
		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			success : function(response) {
				var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$('#operatingProfitSkuShipDialog').window('close');
					$("#operatingProfitSkuShipDataGrid").datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
			},
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	  $.messager.alert('消息提示', '操作失败', 'warning');
		    }
		});
	});
	
	$("#operatingProfitSkuShipCancelLinkbutton").on('click', function(){
		$('#operatingProfitSkuShipDialog').window('close');
	});
	
	$("#operatingProfitSkuShipUploadLinkbutton").on('click', function(){
		lightEbayUpload();
	});
	
	$("#operatingProfitSkuShipExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/operatingProfitSkuShipImport'}});
	});
	
	$("#upload").unbind().on('click', function(){
		newUpload({data:{grid: '#operatingProfitSkuShipDataGrid', dialog: '#uploadDialog', url: '/excel/import/operatingProfitSkuShipImport'}});
	});
	
	$("#query").click(function(){
		var formData = $("#skuShipForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#operatingProfitSkuShipDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#reset").on('click',function(){
		$("#skuShipForm").form("clear");
	});
})