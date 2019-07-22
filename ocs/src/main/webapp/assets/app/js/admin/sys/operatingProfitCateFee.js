$(function() {
	formatterDate('date');
	formatterDate('date1');
	
	$("#operatingProfitCateFeeDataGrid").datagrid({
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        },
        onDblClickRow : function(rowIndex, rowData) {
        	$('#operatingProfitCateFeeForm').form('clear');
        	$('#operatingProfitCateFeeForm').form('load', rowData);
        	$('#operatingProfitCateFeeDialog').window('open');
        }
	})
	
	$("#operatingProfitCateFeeSaveLinkbutton").on('click', function(){
		if(!$("#operatingProfitCateFeeForm").form('validate')) {
			return;
		}
		var jsonData = $("#operatingProfitCateFeeForm").serializeArray();
		var data = [];
		jsonData.forEach(function(e){  
			data.push(e);
		});
		var url = GLOBAL.domain + "/operatingProfitCateFee/saveEdit";
		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			success : function(response) {
				var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$('#operatingProfitCateFeeDialog').window('close');
					$("#operatingProfitCateFeeDataGrid").datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
			},
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	  $.messager.alert('消息提示', '操作失败', 'warning');
		    }
		});
	});
	
	$("#operatingProfitCateFeeCancelLinkbutton").on('click', function(){
		$('#operatingProfitCateFeeDialog').window('close');
	});
	
	$("#operatingProfitCateFeeUploadLinkbutton").on('click', function(){
		upload = '1';
		lightEbayUpload();
	});
	
	$("#operatingProfitFeeCateUploadLinkbutton").on('click', function(){
		upload = '2';
		lightEbayUpload();
	});
	
	$("#operatingProfitCateFeeExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/operatingProfitCateFeeImport'}});
	});
	$("#operatingProfitFeeCateExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/operatingProfitFeeCateImport'}});
	});
	
	$("#upload").unbind().on('click', function(){
		if(upload == '1') {
			newUpload({data:{grid: '#operatingProfitCateFeeDataGrid', dialog: '#uploadDialog', url: '/excel/import/operatingProfitCateFeeImport'}});
		} else if(upload == '2') {
			newUpload({data:{grid: '#operatingProfitFeeCateDataGrid', dialog: '#uploadDialog', url: '/excel/import/operatingProfitFeeCateImport'}});
		}
	});

	$("#operatingProfitFeeCateLinkbutton").click(function(){
		$("#operatingProfitFeeCateDataGrid").datagrid({
	        url: GLOBAL.domain + '/operatingProfitFeeCate/findAll',
	        queryParams : {
				param : {
					
				}
			}
		});
		$('#feeCateSearchForm').form('clear');
		$("#operatingProfitFeeCate").dialog("open");
	});
	
	$("#queryFeeCate").click(function(){
		var formData = $("#feeCateSearchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#operatingProfitFeeCateDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#resetFeeCate").on('click',function(){
		$("#feeCateSearchForm").form("clear");
	});
	
	$("#query").click(function(){
		var formData = $("#cateFeeSearchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#operatingProfitCateFeeDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#reset").on('click',function(){
		$("#cateFeeSearchForm").form("clear");
	});
})