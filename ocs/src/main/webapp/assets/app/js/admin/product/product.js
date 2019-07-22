//打开添加的窗口
var add = function() {
	$('#productForm').form('clear');
	$('#productDialog').window('open');
};

// 打开修改的窗口
var edit = function() {
	var rows = $("#productDatagrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示信息", "请选中一行编辑");
		return;
	} else if (rows.length > 1) {
		$.messager.alert("提示信息", "只能选择一行进行编辑");
		return;
	}
	var $row = $('#productDatagrid').datagrid('getSelected');
	$('#productForm').form('clear');
	$('#productForm').form('load', $row);
	$('#productDialog').window('open');
};

// 保存和修改
var productFormSave = function() {
	$('#productForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#productDialog').window('close');
				$("#productDatagrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#productDatagrid').datagrid('clearSelections');
		}
	});
};

var productCotextMenu = function(e, rowIndex, rowData) {
	e.preventDefault();
	$("#productDatagrid").datagrid('selectRow', rowIndex);
	$('#productCotextMenu').menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};

var productFormNo = function() {
	$('#productDialog').window('close');
}

$(function() {
	$('#editProductContextMenu').on('click', edit);
	$("#productAddLinkbutton").on('click', add);
	$("#productEditLinkbutton").on('click', edit);

	$("#productRemoveLinkbutton").on('click', {
		grid : '#productDatagrid',
		type : 'datagrid',
		url : '/product/remove'
	}, removeAll);

	$("#removeProductContextMenu").on('click', {
		grid : '#productDatagrid',
		type : 'datagrid',
		url : '/product/remove'
	}, removeAll);

	$("#query_product").click(
			function() {
				/*var options = $('#productDatagrid').datagrid('getPager').data("pagination").options;
				var page = options.pageNumber;
				var rows = options.pageSize;*/
				
				/*var requestdata = {
					page : page,
					rows : rows,
					param : param
				}
				$.ajax({
					url : '/product/findAll',
					type : 'post',
					data : requestdata,
					success : function(data) {
						console.log(data);
						$('#productDatagrid').datagrid('loadData', data);
					}
				});*/
				
				var param = {
						sku : $('#sku').val(),
						name : $('#name').val()
				}
				
				$('#productDatagrid').datagrid('load',{
					param : param
				});
			})

	$("#reset_product").click(function() {
		$("#ProductListForm").form("clear")
	})

});