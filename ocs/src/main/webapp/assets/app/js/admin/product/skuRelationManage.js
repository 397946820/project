//弹窗关闭
var closeRelationDialog = function() {
	$("#relationManageDialog").window("close");
};

var castingSku = function() {
	var $row = $('#userDatagrid').datagrid('getSelected');
	if (!$row) {
		$.messager.alert("信息", "请选中一个用户进行分配SKU");
		return;
	}
	$('#skuCastingDatagrid').datagrid('clearSelections');
	var options = $('#skuCastingDatagrid').datagrid('options');
	options.onLoadSuccess = function(data) {
		var rowData = data.rows;
		$.each(rowData, function(idx, val) {
			if (val.checked) {
				$('#skuCastingDatagrid').datagrid("selectRow", idx);
			}
		});
	}
	options.url = GLOBAL.domain + '/productRelation/query';
	$('#skuCastingDatagrid').datagrid('load',{
		param:{
			userId:$row.id
		},
	});
	$('#relationManageDialog').window('open');
}

var castingSkuSave = function() {
	var skuIds = '';
	var selectRows = $('#skuCastingDatagrid').datagrid('getChecked');
	if (!selectRows || $(selectRows).length == 0) {
		$('#relationManageDialog').window('close');
	} else {
		$(selectRows).each(function(index, obj) {
			skuIds += obj.entityId + ',';
		});
		var $row = $('#userDatagrid').datagrid('getSelected');
		var requestdata = {
			userId : $row.id,
			productIds : skuIds
		};
		$.ajax({
			url : GLOBAL.domain + '/productRelation/castSku',
			type : 'post',
			dataType : 'json',
			contentType : "application/json",
			data : JSON.stringify(requestdata),
			success : function(response) {
				$.messager.alert('消息提示', response.description,
						(response.errorCode == 0) ? 'info' : 'error');
				$('#relationManageDialog').window('close');
			},
			error : function() {
				$.messager.alert('消息提示', 'SKU分配失败', 'error');
			}
		});
	}
}
var getFlag = function(value, row, index) {
	if (value == '0') {
		return '否';
	} else {
		return '是';
	}
}
var getStatus = function(value, row, index) {
	if (value == '0') {
		return '不启用';
	} else {
		return '启用';
	}
}
var skuQuery = function() {
	var ssku = $('#relationSku').val();
	var $row = $('#skuCastingDatagrid').datagrid('load', {
		param : {
			sku : ssku
		}
	});
}

$(function() {
	$('#skuCastingLinkbutton').on('click', castingSku);
	$('#relationQuery').on('click', skuQuery);
});