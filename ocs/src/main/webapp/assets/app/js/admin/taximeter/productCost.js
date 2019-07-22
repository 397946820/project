function editOpen($row) {
	$('#productCostForm').form('clear');
	$('#productCostForm').form('load', $row);
	//sku不可选
	$('#sku').textbox('disable');
	$('#productCostDialog').window('open');
}

// 保存和修改
var save = function() {
	$('#productCostForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#productCostDialog').window('close');
				$("#productCostDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#productCostDataGrid').datagrid('clearSelections');
		}
	});
};

var cancle = function() {
	$('#productCostDialog').window('close');
}

var productCostUpload = function() {
	$('#uploadForm').form('clear');
	$('#uploadDialog').window('open');
}

$(function() {
	$("#productCostSaveLinkbutton").on('click', save);
	$("#productCostCancelLinkbutton").on('click', cancle);
	$("#productCostExportLinkbutton").on('click', {url : '/productCost/exportExcel',grid: '#productCostDataGrid'},derive);
	$("#productCostUploadLinkbutton").on('click', productCostUpload);
	$("#upload").on('click', {grid: '#productCostDataGrid', dialog: '#uploadDialog', url: '/productCost/uploadExcel'}, upload);
	
	$("#productCostDataGrid").datagrid({
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	});
	
	// 遮罩
	$('#productCostDialog').dialog({
		modal:true
	});
	
	//查询
	$("#query").click(function(){
		if(!$("#productCost_Form").form('validate')) {
			return;
		}
		var boolean = moreTime();
		if(boolean) {
			$.messager.alert("提示信息", "结束时间应大于开始时间");
			return;
		}
		var param = {
				sku : $('#sku_').textbox('getValue'),
				username : $('#username').textbox('getValue'),
				price : $('#price').numberbox('getValue'),
				cstarttime : $('#cstarttime').datebox('getValue'),
				cendtime : $('#cendtime').datebox('getValue'),
				ustarttime : $('#ustarttime').datebox('getValue'),
				uendtime : $('#uendtime').datebox('getValue')
		}
		
		$('#productCostDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#sku_').textbox('setValue',''),
		$('#username_').textbox('setValue',''),
		$('#price').numberbox('setValue','');
		$('#cstarttime').datebox('setValue','');
		$('#cendtime').datebox('setValue','');
		$('#ustarttime').datebox('setValue','');
		$('#uendtime').datebox('setValue','');
	});
	
});
