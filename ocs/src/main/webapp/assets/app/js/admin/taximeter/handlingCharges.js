function editOpen(rowData) {
	$('#handlingChargesForm').form('clear');
	$('#handlingChargesForm').form('load', rowData);
	//下拉框不可选
	$('#type').combobox('disable');
	$('#handlingChargesDialog').window('open');
}


var add = function() {
	$('#handlingChargesForm').form('clear');
	$("#type").combobox('setValue', $("#type_").combobox('getValue'));
	// 下拉框不可选
	$('#type').combobox('disable');
	$('#handlingChargesDialog').window('open');
};

var cancle = function() {
	$('#handlingChargesDialog').window('close');
}

var save = function() {
	if(!$("#handlingChargesForm").form('validate')) {
		return;
	}
	var jsonData = $("#handlingChargesForm").serializeArray();
	var data = [];
	jsonData.forEach(function(e){  
		data.push(e);
	});
	data.push({name : 'type' , value : $('#type').combobox('getValue')});
	var url = GLOBAL.domain + "/handlingCharges/saveEdit";
	$.ajax({
		url : url,
		type : 'POST',
		data : data,
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#handlingChargesDialog').window('close');
				$("#handlingChargesDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#handlingChargesDataGrid').datagrid('clearSelections');
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	});
}

$(function() {
	$("#handlingChargesAddLinkbutton").on('click', add);
	$("#handlingChargesSaveLinkbutton").on('click', save);
	$("#handlingChargesCancelLinkbutton").on('click', cancle);
	$("#handlingChargesUploadLinkbutton").on('click', lightEbayUpload);
	$("#handlingChargesExportLinkbutton").on('click', {url: '/excel/template/handlingChargesImport'},lightEbayExport);
	$("#handlingChargesExportAllLinkbutton").on('click', {url: '/excel/export/handlingChargesExport',grid: '#handlingChargesDataGrid'},export_);
	$("#upload").on('click', {grid: '#handlingChargesDataGrid', dialog: '#uploadDialog', url: '/excel/import/handlingChargesImport'}, newUpload);
	
	//来源改变事件
	$("#type_").combobox({
		onChange: function (newValue,oldValue) {
			var rows = $('#handlingChargesDataGrid').datagrid('options');
			rows.sortName = '';
			rows.sortOrder = '';
			var param = {
					type : newValue
			};
			$('#handlingChargesDataGrid').datagrid('load',{
				param : param
			});
		}
	});
})