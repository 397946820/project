var addOrEdit = '';
var upload = '';
$(function() {
	formatterDate('date');
	formatterDate('date_1');
	formatterDate('date_2');
	
	$("#operatingProfitSkuDataGrid").datagrid({
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        },
        onDblClickRow : function(rowIndex, rowData) {
        	$('#operatingProfitSkuForm').form('clear');
        	$('#operatingProfitSkuForm').form('load', rowData);
        	//下拉框不可选
        	$('#platform').combobox('disable');
        	$('#country').combobox('disable');
        	$('#sku').textbox('disable');
        	$('#operatingProfitSkuDialog').window('open');
        	addOrEdit = 'edit';
        }
	})
	
	$("#operatingProfitSkuAddLinkbutton").on('click', function(){
		$('#operatingProfitSkuForm').form('clear');
		$('#platform').combobox('enable');
    	$('#country').combobox('enable');
    	$('#sku').textbox('enable');
		$('#operatingProfitSkuDialog').window('open');
		addOrEdit = 'add';
	});
	
	$("#operatingProfitSkuSaveLinkbutton").on('click', function(){
		if(!$("#operatingProfitSkuForm").form('validate')) {
			return;
		}
		var jsonData = $("#operatingProfitSkuForm").serializeArray();
		var data = [];
		jsonData.forEach(function(e){  
			data.push(e);
		});
		if(addOrEdit == 'edit') {
			data.push({name: "platform", value: $('#platform').combobox('getValue')});
			data.push({name: "country", value: $('#country').combobox('getValue')});
			data.push({name: "sku", value: $('#sku').textbox('getValue')});
		}
		var url = GLOBAL.domain + "/operatingProfitSku/saveEdit";
		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			success : function(response) {
				var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$('#operatingProfitSkuDialog').window('close');
					$("#operatingProfitSkuDataGrid").datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
			},
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	  $.messager.alert('消息提示', '操作失败', 'warning');
		    }
		});
	});
	
	$("#operatingProfitSkuCancelLinkbutton").on('click', function(){
		$('#operatingProfitSkuDialog').window('close');
	});
	
	$("#operatingProfitSkuUploadLinkbutton").on('click', function(){
		upload = '1';
		lightEbayUpload();
	});
	$("#operatingProfitSkuUploadNewSkuLinkbutton").on('click', function(){
		upload = '2';
		lightEbayUpload();
	});
	$("#operatingProfitStockUploadLinkbutton").on('click', function(){
		upload = '3';
		lightEbayUpload();
	});
	$("#operatingProfit_S_I").on('click', function(){
		upload = '4';
		lightEbayUpload();
	});
	$("#operatingProfit_H_I").on('click', function(){
		upload = '5';
		lightEbayUpload();
	});
	
	$("#operatingProfitSkuExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/operatingProfitSkuImport'}});
	});
	
	$("#operatingProfitSkuNewExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/operatingProfitNewSkuImport'}});
	});
	$("#operatingProfitStockExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/operatingProfitStockImport'}});
	});
	
	$("#operatingProfitSkuExportAllLinkbutton").unbind().on('click', function(){
		export_({data:{url: '/excel/export/operatingProfitSkuExport',grid: '#operatingProfitSkuDataGrid'}});
	});
	
	$("#operatingProfit_S_E").on('click', function(){
		window.location.href = GLOBAL.domain + '/excel/export/operatingProfitPriceExport?flag=2';
	});
	
	$("#operatingProfit_H_E").on('click', function(){
		window.location.href = GLOBAL.domain + '/excel/export/operatingProfitPriceExport?flag=1';
	});
	
	$("#upload").unbind().on('click', function(){
		var url = '';
		if(upload == '1') {
			url = '/excel/import/operatingProfitSkuImport';
			newUpload({data:{grid: '#operatingProfitSkuDataGrid', dialog: '#uploadDialog', url: url}});
		} else if(upload == '2') {
			url = '/excel/import/operatingProfitNewSkuImport';
			newUpload({data:{grid: '#operatingProfitSkuDataGrid', dialog: '#uploadDialog', url: url}});
		} else if(upload == '3') {
			url = '/excel/import/operatingProfitStockImport';
			newUpload({data:{grid: '#operatingProfitStockDataGrid', dialog: '#uploadDialog', url: url}});
		} else if(upload == '4') {
			url = '/excel/import/operatingProfitPriceSImport';
			newUpload({data:{grid: '#operatingProfitSkuDataGrid', dialog: '#uploadDialog', url: url}});
		} else if(upload == '5') {
			url = '/excel/import/operatingProfitPriceHImport';
			newUpload({data:{grid: '#operatingProfitSkuDataGrid', dialog: '#uploadDialog', url: url}});
		}
	});
	
	$("#operatingProfitStockLinkbutton").click(function(){
		$("#operatingProfitStockDataGrid").datagrid({
	        url: GLOBAL.domain + '/operatingProfitStock/findAll',
	        queryParams : {
				param : {
					
				}
			}
		});
		$('#stockSearchForm').form('clear');
		$("#operatingProfitStock").dialog("open");
	});
	
	$("#operatingProfitCostLinkbutton").click(function(){
		$("#operatingProfitCostDataGrid").datagrid({
	        url: GLOBAL.domain + '/operatingProfitCost/findAll',
	        queryParams : {
				param : {
					
				}
			},
			onDblClickRow : function(rowIndex, rowData) {
	        	$('#operatingProfitCostForm').form('clear');
	        	$('#operatingProfitCostForm').form('load', rowData);
	        	$('#operatingProfitCostDialog').window('open');
	        }
		});
		$('#costSearchForm').form('clear');
		$("#operatingProfitCost").dialog("open");
	});
	
	$("#operatingProfitCostAddLinkbutton").on('click', function(){
		$('#operatingProfitCostForm').form('clear');
		$('#operatingProfitCostDialog').window('open');
	});
	
	$("#operatingProfitCostSaveLinkbutton").on('click', function(){
		if(!$("#operatingProfitCostForm").form('validate')) {
			return;
		}
		var jsonData = $("#operatingProfitCostForm").serializeArray();
		var data = [];
		jsonData.forEach(function(e){  
			data.push(e);
		});
		var url = GLOBAL.domain + "/operatingProfitCost/saveEdit";
		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			success : function(response) {
				var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$('#operatingProfitCostDialog').dialog('close');
					$("#operatingProfitCostDataGrid").datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
			},
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	  $.messager.alert('消息提示', '操作失败', 'warning');
		    }
		});
	});
	
	$("#operatingProfitCostCancelLinkbutton").on('click', function(){
		$('#operatingProfitCostDialog').window('close');
	});
	
	$("#queryStock").click(function(){
		var formData = $("#stockSearchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#operatingProfitStockDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#resetStock").on('click',function(){
		$("#stockSearchForm").form("clear");
	});
	
	$("#queryCost").click(function(){
		var formData = $("#costSearchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#operatingProfitCostDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#resetCost").on('click',function(){
		$("#costSearchForm").form("clear");
	});
	
	$("#query").click(function(){
		var formData = $("#searchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#operatingProfitSkuDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#reset").on('click',function(){
		$("#searchForm").form("clear");
	});
})