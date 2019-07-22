$(function() {
	formatterDate('date');
	
	$("#operatingProfitSkuFeeDataGrid").datagrid({
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        },
        onDblClickRow : function(rowIndex, rowData) {
        	$('#operatingProfitSkuFeeForm').form('clear');
        	$('#operatingProfitSkuFeeForm').form('load', rowData);
        	$('#operatingProfitSkuFeeDialog').window('open');
        }
	})
	
	$("#operatingProfitSkuFeeSaveLinkbutton").on('click', function(){
		if(!$("#operatingProfitSkuFeeForm").form('validate')) {
			return;
		}
		var jsonData = $("#operatingProfitSkuFeeForm").serializeArray();
		var data = [];
		jsonData.forEach(function(e){  
			data.push(e);
		});
		var url = GLOBAL.domain + "/operatingProfitSkuFee/saveEdit";
		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			success : function(response) {
				var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$('#operatingProfitSkuFeeDialog').window('close');
					$("#operatingProfitSkuFeeDataGrid").datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
			},
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	  $.messager.alert('消息提示', '操作失败', 'warning');
		    }
		});
	});
	
	$("#operatingProfitSkuFeeCancelLinkbutton").on('click', function(){
		$('#operatingProfitSkuFeeDialog').window('close');
	});
	
	$("#operatingProfitSkuFeeUploadLinkbutton").on('click', function(){
		lightEbayUpload();
	});
	
	$("#operatingProfitSkuFeeExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/operatingProfitSkuFeeImport'}});
	});
	
	$("#operatingProfitSkuFeeExportAllLinkbutton").on('click', function(){
		window.location.href = GLOBAL.domain + '/excel/export/operatingProfitSkuFeeExport';
	});
	
	$("#upload").unbind().on('click', function(){
		newUpload({data:{grid: '#operatingProfitSkuFeeDataGrid', dialog: '#uploadDialog', url: '/excel/import/operatingProfitSkuFeeImport'}});
	});

	
	$("#query").click(function(){
		var formData = $("#skuFeeSearchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#operatingProfitSkuFeeDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#reset").on('click',function(){
		$("#skuFeeSearchForm").form("clear");
	});
})