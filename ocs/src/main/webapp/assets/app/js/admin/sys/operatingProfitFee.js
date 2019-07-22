$(function() {
	formatterDate('date');
	
	$("#operatingProfitFeeDataGrid").datagrid({
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        },
        onDblClickRow : function(rowIndex, rowData) {
        	$('#operatingProfitFeeForm').form('clear');
        	$('#operatingProfitFeeForm').form('load', rowData);
        	$('#operatingProfitFeeDialog').window('open');
        }
	})
	
	$("#operatingProfitFeeSaveLinkbutton").on('click', function(){
		if(!$("#operatingProfitFeeForm").form('validate')) {
			return;
		}
		var jsonData = $("#operatingProfitFeeForm").serializeArray();
		var data = [];
		jsonData.forEach(function(e){  
			data.push(e);
		});
		var url = GLOBAL.domain + "/operatingProfitFee/saveEdit";
		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			success : function(response) {
				var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$('#operatingProfitFeeDialog').window('close');
					$("#operatingProfitFeeDataGrid").datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
			},
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	  $.messager.alert('消息提示', '操作失败', 'warning');
		    }
		});
	});
	
	$("#operatingProfitFeeCancelLinkbutton").on('click', function(){
		$('#operatingProfitFeeDialog').window('close');
	});
	
	$("#operatingProfitFeeUploadLinkbutton").on('click', function(){
		lightEbayUpload();
	});
	
	$("#operatingProfitFeeExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/operatingProfitFeeImport'}});
	});
	
	$("#upload").unbind().on('click', function(){
		newUpload({data:{grid: '#operatingProfitFeeDataGrid', dialog: '#uploadDialog', url: '/excel/import/operatingProfitFeeImport'}});
	});

	
	$("#query").click(function(){
		var formData = $("#feeSearchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#operatingProfitFeeDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#reset").on('click',function(){
		$("#feeSearchForm").form("clear");
	});
})