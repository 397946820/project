function editOpen(rowData) {
	$('#smallRateForm').form('clear');
	$('#smallRateForm').form('load', rowData);
	$('#platform').combobox('disable');
	$('#smallRateDialog').window('open');
}

var cancle = function() {
	$('#smallRateDialog').window('close');
}

var save = function() {
	$('#smallRateForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#smallRateDialog').window('close');
				$("#smallRateDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#smallRateDataGrid').datagrid('clearSelections');
		}
	});
}

$(function() {
	$("#smallRateSaveLinkbutton").on('click', save);
	$("#smallRateCancelLinkbutton").on('click', cancle);
})

