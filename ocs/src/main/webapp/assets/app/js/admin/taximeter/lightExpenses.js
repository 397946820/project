var addOrEdit = '';
function editOpen(rowData) {
	$('#lightExpensesForm').form('clear');
	$('#lightExpensesForm').form('load', rowData);
	//下拉框不可选
	$('#country').combobox('disable');
	$('#lightExpensesDialog').window('open');
	addOrEdit = 'edit';
}

var add = function() {
	$('#lightExpensesForm').form('clear');
	$('#country').combobox('setValue','US');
	$('#country').combobox('enable');
	$('#lightExpensesDialog').window('open');
	addOrEdit = 'add';
};

var cancle = function() {
	$('#lightExpensesDialog').window('close');
}

var save = function() {
	if(!$("#lightExpensesForm").form('validate')) {
		return;
	}
	var jsonData = $("#lightExpensesForm").serializeArray();
	var data = [];
	jsonData.forEach(function(e){  
		data.push(e);
	});
	if(addOrEdit == 'edit') {
		data.push({name: "country", value: $('#country').combobox('getValue')});
	}
	var url = GLOBAL.domain + "/lightExpenses/saveEdit";
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
				$('#lightExpensesDialog').window('close');
				$("#lightExpensesDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#lightExpensesDataGrid').datagrid('clearSelections');
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	});
}

$(function() {
	$("#lightExpensesAddLinkbutton").on('click', add);
	$("#lightExpensesSaveLinkbutton").on('click', save);
	$("#lightExpensesCancelLinkbutton").on('click', cancle);
	$("#lightExpensesUploadLinkbutton").on('click', lightEbayUpload);
	$("#lightExpensesExportLinkbutton").on('click', {url: '/excel/template/lightExpensesImport'},lightEbayExport);
	$("#lightExpensesExportAllLinkbutton").on('click', {url: '/excel/export/lightExpensesExport',grid: '#lightExpensesDataGrid'},export_);
	$("#upload").on('click', {grid: '#lightExpensesDataGrid', dialog: '#uploadDialog', url: '/excel/import/lightExpensesImport'}, newUpload);
	
	$('#lightExpensesDataGrid').datagrid({
		onLoadSuccess : function(data) {
			
		}
	});
	
	//查询
	$("#query").click(function(){
		var param = {
				country : $('#country_').combobox('getValue'),
				sku : $('#sku').textbox('getValue')
		};
		
		$('#lightExpensesDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#country_').combobox('setValue','US');
		$('#sku').textbox('setValue','');
	});
})

