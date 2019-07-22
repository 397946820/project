//打开添加的窗口
var add = function() {
	
	$('#advertisementFeaturesForm').form('clear');
	$("#siteId").val("18");
	$('#advertisementFeaturesDialog').window('open');
};

// 打开修改的窗口
var edit = function() {
	var rows = $("#advertisementFeaturesDataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示信息", "请选中一行编辑");
		return;
	} else if (rows.length > 1) {
		$.messager.alert("提示信息", "只能选择一行进行编辑");
		return;
	}
	var $row = $('#advertisementFeaturesDataGrid').datagrid('getSelected');
	$('#advertisementFeaturesForm').form('clear');
	$('#advertisementFeaturesForm').form('load', $row);
	var featureProperty = $row.featureProperty.split(",");
	var checkArry = document.getElementsByName("featureProperty");
	for (var i = 0; i < featureProperty.length; i++) {
		for (var j = 0; j < checkArry.length; j++) {
			if(checkArry[j].value == featureProperty[i]) {
				$("input[type=checkbox][value='"+checkArry[j].value+"']").prop("checked", true);
			}
		}
	}
	$('#advertisementFeaturesDialog').window('open');
};

// 保存和修改
var advertisementFeaturesFormSave = function() {
	$('#advertisementFeaturesForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#advertisementFeaturesDialog').window('close');
				$("#advertisementFeaturesDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#advertisementFeaturesDataGrid').datagrid('clearSelections');
		}
	});
};



var advertisementFeaturesCotextMenu = function(e, rowIndex, rowData) {
	e.preventDefault();
	$("#advertisementFeaturesDataGrid").datagrid('selectRow', rowIndex);
	$('#advertisementFeaturesCotextMenu').menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};

var advertisementFeaturesFormNo = function() {
	$('#advertisementFeaturesDialog').window('close');
}

$(function() {
	$('#advertisementFeaturesEditContextMenu').on('click', edit);
	$("#advertisementFeaturesAddLinkbutton").on('click', add);
	$("#advertisementFeaturesEditLinkbutton").on('click', edit);
	$("#advertisementFeaturesRemoveLinkbutton").on('click', {grid: '#advertisementFeaturesDataGrid', type: 'datagrid', url: '/advertisementFeatures/remove'}, removeAll);
	$("#advertisementFeaturesRemoveContextMenu").on('click', {grid: '#advertisementFeaturesDataGrid', type: 'datagrid', url: '/advertisementFeatures/remove'}, removeAll);

});
