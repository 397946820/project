var addOrEdit = '';
function editOpen(rowData) {
	$('#lightEbayTaxForm').form('clear');
	$('#lightEbayTaxForm').form('load', rowData);
	//下拉框不可选
	$('#country').combobox('disable');
	$('#sku_').combobox('disable');
	$('#lightEbayTaxDialog').window('open');
	addOrEdit = 'edit';
}

var add = function() {
	$('#lightEbayTaxForm').form('clear');
	$('#country').combobox('setValue','US');
	$('#country').combobox('enable');
	$('#sku_').combobox('enable');
	$('#lightEbayTaxDialog').window('open');
	addOrEdit = 'add';
};

var cancle = function() {
	$('#lightEbayTaxDialog').window('close');
}

var save = function() {
	if(!$("#lightEbayTaxForm").form('validate')) {
		return;
	}
	var jsonData = $("#lightEbayTaxForm").serializeArray();
	var data = [];
	jsonData.forEach(function(e){  
		data.push(e);
	});
	if(addOrEdit == 'edit') {
		data.push({name: "country", value: $('#country').combobox('getValue')});
		data.push({name: "sku", value: $('#sku_').textbox('getValue')});
	}
	var url = GLOBAL.domain + "/lightEbayTax/saveEdit";
	$.ajax({
		url : url,
		type : 'POST',
		data : data,
		beforeSend: function () {
            $.messager.progress({
                title: '请稍后',
                msg: '正在操作...'
            });
        },
        complete: function () {
            $.messager.progress('close');
        },
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#lightEbayTaxDialog').window('close');
				$("#lightEbayTaxDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#lightEbayTaxDataGrid').datagrid('clearSelections');
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	});
}

$(function() {
	$("#lightEbayTaxAddLinkbutton").on('click', add);
	$("#lightEbayTaxSaveLinkbutton").on('click', save);
	$("#lightEbayTaxCancelLinkbutton").on('click', cancle);
	$("#lightEbayTaxUploadLinkbutton").on('click', lightEbayUpload);
	$("#lightEbayTaxExportLinkbutton").on('click', {url: '/excel/template/lightEbayTaxImport'},lightEbayExport);
	$("#lightEbayTaxExportAllLinkbutton").on('click', {url: '/excel/export/lightEbayTaxExport',grid: '#lightEbayTaxDataGrid'},export_);
	$("#upload").on('click', {grid: '#lightEbayTaxDataGrid', dialog: '#uploadDialog', url: '/excel/import/lightEbayTaxImport'}, newUpload);
	
	$('#lightEbayTaxDataGrid').datagrid({
		onLoadSuccess : function(data) {
			
		}
	});
	
	//查询
	$("#query").click(function(){
		var param = {
				country : $('#country_').combobox('getValue'),
				sku : $('#sku').textbox('getValue')
		};
		
		$('#lightEbayTaxDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#country_').combobox('setValue','');
		$('#sku').textbox('setValue','');
	});
})

