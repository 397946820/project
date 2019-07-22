var addOrEdit = '';
function editOpen(rowData) {
	$('#lightEbayRateForm').form('clear');
	$('#lightEbayRateForm').form('load', rowData);
	//下拉框不可选
	$('#platform').combobox('disable');
	$('#country').combobox('disable');
	$('#shippingType').combobox('disable');
	$('#lightEbayRateDialog').window('open');
	addOrEdit = 'edit';
}


var add = function() {
	$('#lightEbayRateForm').form('clear');
	$('#platform').combobox('enable');
	$('#country').combobox('enable');
	$('#shippingType').combobox('enable');
	$('#lightEbayRateDialog').window('open');
	addOrEdit = 'add';
};

var cancle = function() {
	$('#lightEbayRateDialog').window('close');
}

var save = function() {
	if(!$("#lightEbayRateForm").form('validate')) {
		return;
	}
	var jsonData = $("#lightEbayRateForm").serializeArray();
	var data = [];
	jsonData.forEach(function(e){  
		data.push(e);
	});
	if(addOrEdit == 'edit') {
		data.push({name: "platform", value: $('#platform').combobox('getValue')});
		data.push({name: "country", value: $('#country').combobox('getValue')});
		data.push({name: "shippingType", value: $('#shippingType').combobox('getValue')});
	}
	var url = GLOBAL.domain + "/lightEbayRate/saveEdit";
	$.ajax({
		url : url,
		type : 'POST',
		beforeSend: function () {
            $.messager.progress({
                title: '请稍后',
                msg: '正在操作...'
            });
        },
        complete: function () {
            $.messager.progress('close');
        },
		data : data,
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#lightEbayRateDialog').window('close');
				$("#lightEbayRateDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#lightEbayRateDataGrid').datagrid('clearSelections');
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	});
}

$(function() {
	$("#lightEbayRateAddLinkbutton").on('click', add);
	$("#lightEbayRateSaveLinkbutton").on('click', save);
	$("#lightEbayRateCancelLinkbutton").on('click', cancle);
	$("#lightEbayRateUploadLinkbutton").on('click', lightEbayUpload);
	$("#lightEbayRateExportLinkbutton").on('click', {url: '/excel/template/lightEbayRateImport'},lightEbayExport);
	$("#lightEbayRateExportAllLinkbutton").on('click', {url: '/excel/export/lightEbayRateExport',grid: '#lightEbayRateDataGrid'},export_);
	$("#upload").on('click', {grid: '#lightEbayRateDataGrid', dialog: '#uploadDialog', url: '/excel/import/lightEbayRateImport'}, newUpload);

	//查询
	$("#query").click(function(){
		var param = {
				platform : $('#platform_').combobox('getValue'),
				country : $('#country_').combobox('getValue'),
				shippingType : $('#shippingType_').combobox('getValue')
		}
		
		$('#lightEbayRateDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#platform_').combobox('setValue','');
		$('#country_').combobox('setValue','');
		$('#shippingType_').combobox('setValue','');
	});
})

