//打开添加的窗口
var add = function() {
	$('#taxForm').form('clear');
	$('#countryId').combobox('enable');
	$("#countryId").combobox('setValue','US');
	$("#type").combobox('setValue','duty');
	$('#taxDialog').window('open');
};

function editOpen($row) {
	$('#taxForm').form('clear');
	$('#taxForm').form('load', $row);
	//下拉框不可选
	$('#countryId').combobox('disable');
	$('#taxDialog').window('open');
}

// 保存和修改
var save = function() {
	$('#taxForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#taxDialog').window('close');
				$("#taxDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#taxDataGrid').datagrid('clearSelections');
		}
	});
};

var cancle = function() {
	$('#taxDialog').window('close');
}

var taxUpload = function() {
	$('#uploadForm').form('clear');
	$('#uploadDialog').window('open');
}

$(function() {
	$("#taxAddLinkbutton").on('click', add);
	$("#taxSaveLinkbutton").on('click', save);
	$("#taxCancelLinkbutton").on('click', cancle);
	$("#taxExportLinkbutton").on('click',  { url : '/tax/exportExcel',grid: '#taxDataGrid'},derive);
	$("#taxUploadLinkbutton").on('click', taxUpload);
	$("#upload").on('click', {grid: '#taxDataGrid', dialog: '#uploadDialog', url: '/tax/uploadExcel'}, upload);
	
	$("#taxDataGrid").datagrid({
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	});
	
	// 遮罩
	$('#taxDialog').dialog({
		modal:true
	});
	
	//查询
	$("#query").click(function(){
		if(!$("#tax_Form").form('validate')) {
			return;
		}
		var boolean = moreTime();
		if(boolean) {
			$.messager.alert("提示信息", "结束时间应大于开始时间");
			return;
		}
		var param = {
				countryId : $('#country_Id').combobox('getValue'),
				type : $('#type_').combobox('getValue'),
				vat : $('#vat').numberbox('getValue'),
				afFluctuation : $('#afFluctuation').numberbox('getValue'),
				sfFluctuation : $('#sfFluctuation').numberbox('getValue'),
				coFluctuation : $('#coFluctuation').numberbox('getValue'),
				clearCoefficient : $('#clearCoefficient').numberbox('getValue'),
				cstarttime : $('#cstarttime').datebox('getValue'),
				cendtime : $('#cendtime').datebox('getValue'),
				ustarttime : $('#ustarttime').datebox('getValue'),
				uendtime : $('#uendtime').datebox('getValue')
		}
		
		$('#taxDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#country_Id').combobox('setValue','');
		$('#type_').combobox('setValue','');
		$('#vat').numberbox('setValue','');
		$('#afFluctuation').numberbox('setValue','');
		$('#sfFluctuation').numberbox('setValue','');
		$('#coFluctuation').numberbox('setValue','');
		$('#clearCoefficient').numberbox('setValue','');
		$('#cstarttime').datebox('setValue','');
		$('#cendtime').datebox('setValue','');
		$('#ustarttime').datebox('setValue','');
		$('#uendtime').datebox('setValue','');
	});
	
});
