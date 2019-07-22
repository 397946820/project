//打开添加的窗口
var add = function() {
	$('#saveForm').form('clear');
	$('#saveDialog').window('open');
};
var addList = function() {
	$('#listSiteForm').form('clear');
	$('#listSiteDialog').window('open');
}

// 打开修改的窗口
var edit = function() {
	var rows = $("#listDataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示信息", "请选中一行编辑");
		return;
	} else if (rows.length > 1) {
		$.messager.alert("提示信息", "只能选择一行进行编辑");
		return;
	}
	var $row = $('#listDataGrid').datagrid('getSelected');
	$('#saveForm').form('clear');
	$('#saveForm').form('load', $row);
	$('#saveDialog').window('open');
};

// 还原作废的记录
var restore = function() {
	// 获得datagrid中选中的行
	var rows = $("#listDataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		// 没有选中行
		$.messager.alert("提示信息", "请至少选择一条记录还原！", "warning");
	} else {
		// 选中了行
		// 将选中行的记录的id拼接为字符串使用逗号分隔，提交到服务端，完成作废操作
		var arr = new Array();
		for (var i = 0; i < rows.length; i++) {
			arr.push(rows[i].id);
		}
		var ids = arr.join(",");
		var url = GLOBAL.domain + "/advertisementFeatures/restore";
		$.ajax({
			url : url,
			type : 'POST',
			data : {ids : ids },
			success : function(response) {
				var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$('#saveDialog').window('close');
					$("#listDataGrid").datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
			}
		})
	}
};

// 作废记录
var remove = function() {
	// 获得datagrid中选中的行
	var rows = $("#listDataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		// 没有选中行
		$.messager.alert("提示信息", "请至少选择一条记录作废！", "warning");
	} else {
		// 选中了行
		// 将选中行的记录的id拼接为字符串使用逗号分隔，提交到服务端，完成作废操作
		var arr = new Array();
		for (var i = 0; i < rows.length; i++) {
			arr.push(rows[i].id);
		}
		var ids = arr.join(",");
		var url = GLOBAL.domain + "/advertisementFeatures/remove";
		$.ajax({
			url : url,
			type : 'POST',
			data : {ids : ids},
			success : function(response) {
				var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$('#saveDialog').window('close');
					$("#listDataGrid").datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
			}
		})
	}
};

// 保存和修改
var formSave = function() {
	$('#saveForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#saveDialog').window('close');
				$("#listDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
		}
	});
};

//同步到ebay
var formReload = function() {
	
}

var formNo = function() {
	$('#saveDialog').window('close');
}

var addRateBySites = function() {
	$.ajax({
		url : GLOBAL.domain + "/admin/rate/addRateByItems",
		type : 'post',
		dataType : 'json',
		cache : false,
		beforeSend : function() {
			$.messager.progress({
				title : '请稍后',
				msg : '正在添加商品评论...'
			});
		},
		complete : function() {
			$.messager.progress('close');
		},
		success : function(response) {
			$.messager.alert('消息提示', response.description, 'info');
		},
		error : function() {
			$.messager.alert('消息提示', '系统开小差，不给力', 'error');
		}
	})
};

var cotextMenu = function(e, rowIndex, rowData) {
	e.preventDefault();
	$("#siteDatagrid").datagrid('selectRow', rowIndex);
	$('#siteCotextMenu').menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};

$(function() {
	$('#editContextMenu').on('click', edit);
	$("#addLinkbutton").on('click', add);
	$("#editLinkbutton").on('click', edit);
	$("#saveLinkbutton").on('click', restore);
	$("#removeLinkbutton").on('click', remove);
	$("#removeContextMenu").on('click', remove);
	
});

