//打开添加的窗口
var add = function() {
	$('#templateSettingForm').form('clear');
	$("#ebayAccount").val("le.deutschland");
	$("#topPromotionType").val("0");
	$("#footerPromotionType").val("0");
	$("#scaler").val("0");
	$('#templateSettingDialog').window('open');
};

// 打开修改的窗口
var edit = function() {
	var rows = $("#templateSettingDataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示信息", "请选中一行编辑");
		return;
	} else if (rows.length > 1) {
		$.messager.alert("提示信息", "只能选择一行进行编辑");
		return;
	}
	var $row = $('#templateSettingDataGrid').datagrid('getSelected');
	$('#templateSettingForm').form('clear');
	$('#templateSettingForm').form('load', $row);
	$('#templateSettingDialog').window('open');
};

// 保存和修改
var templateSettingFormSave = function() {
	$('#templateSettingForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#templateSettingDialog').window('close');
				$("#templateSettingDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#templateSettingDataGrid').datagrid('clearSelections');
		}
	});
};



var templateSettingCotextMenu = function(e, rowIndex, rowData) {
	e.preventDefault();
	$("#templateSettingDataGrid").datagrid('selectRow', rowIndex);
	$('#templateSettingCotextMenu').menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};

var templateSettingFormNo = function() {
	$('#templateSettingDialog').window('close');
}

$(function() {
	$('#templateSettingEditContextMenu').on('click', edit);
	$("#templateSettingAddLinkbutton").on('click', add);
	$("#templateSettingEditLinkbutton").on('click', edit);
	$("#templateSettingRemoveLinkbutton").on('click',{grid: '#templateSettingDataGrid', type: 'datagrid', url: '/templateSetting/remove'}, removeAll);
	$("#templateSettingRemoveContextMenu").on('click',{grid: '#templateSettingDataGrid', type: 'datagrid', url: '/templateSetting/remove'}, removeAll);

});
