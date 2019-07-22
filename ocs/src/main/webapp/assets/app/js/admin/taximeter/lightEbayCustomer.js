function editOpen(rowData) {
	$('#lightEbayCustomerForm').form('clear');
	$('#lightEbayCustomerForm').form('load', rowData);
	//下拉框不可选
	$('#country').combobox('disable');
	$('#region').combobox('disable');
	$('#shippingType').combobox('disable');
	$('#lightEbayCustomerDialog').window('open');
}


var add = function() {
	$('#lightEbayCustomerForm').form('clear');
	$('#country').combobox('enable');
	$('#country').combobox('setValue','US');
	$('#region').combobox('enable');
	$('#region').combobox('setValue','US美东');
	$('#shippingType').combobox('enable');
	$('#shippingType').combobox('setValue','af');
	$('#lightEbayCustomerDialog').window('open');
};

var cancle = function() {
	$('#lightEbayCustomerDialog').window('close');
}

var save = function() {
	if(!$("#lightEbayCustomerForm").form('validate')) {
		return;
	}
	var jsonData = $("#lightEbayCustomerForm").serializeArray();
	var data = [];
	jsonData.forEach(function(e){  
		data.push(e);
	});
	var url = GLOBAL.domain + "/lightEbayCustomer/saveEdit";
	$.ajax({
		url : url,
		type : 'POST',
		data : data,
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#lightEbayCustomerDialog').window('close');
				$("#lightEbayCustomerDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#lightEbayCustomerDataGrid').datagrid('clearSelections');
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	});
}

$(function() {
	$("#lightEbayCustomerAddLinkbutton").on('click', add);
	$("#lightEbayCustomerSaveLinkbutton").on('click', save);
	$("#lightEbayCustomerCancelLinkbutton").on('click', cancle);
	$("#lightEbayCustomerUploadLinkbutton").on('click', lightEbayUpload);
	$("#lightEbayCustomerExportLinkbutton").on('click', {url: '/excel/template/lightEbayCustomerImport'},lightEbayExport);
	$("#lightEbayCustomerExportAllLinkbutton").on('click', {url: '/excel/export/lightEbayCustomerExport',grid: '#lightEbayCustomerDataGrid'},export_);
	$("#upload").on('click', {grid: '#lightEbayCustomerDataGrid', dialog: '#uploadDialog', url: '/excel/import/lightEbayCustomerImport'}, newUpload);
	
	//查询
	$("#query").click(function(){
		var param = {
				country : $('#country_').combobox('getValue'),
				region : $('#region_').combobox('getValue'),
				shippingType : $('#shippingType_').combobox('getValue')
		}
		
		$('#lightEbayCustomerDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#country_').combobox('setValue','');
		$('#region_').combobox('setValue','');
		$('#shippingType_').combobox('setValue','');
	});
})