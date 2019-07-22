//打开添加的窗口
var add = function() {
	$('#currencyRateForm').form('clear');
	$('#countryId').combobox('enable');
	$("#countryId").combobox('setValue','US');
	$('#currencyRateDialog').window('open');
};

function editOpen($row) {
	$('#currencyRateForm').form('clear');
	$('#currencyRateForm').form('load', $row);
	//下拉框不可选
	$('#countryId').combobox('disable');
	$('#currencyRateDialog').window('open');
}

// 保存和修改
var save = function() {
	$('#currencyRateForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#currencyRateDialog').window('close');
				$("#currencyRateDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#currencyRateDataGrid').datagrid('clearSelections');
		}
	});
};

var cancle = function() {
	$('#currencyRateDialog').window('close');
}

var currencyRateUpload = function() {
	$('#uploadForm').form('clear');
	$('#uploadDialog').window('open');
}

$(function() {
	$("#currencyRateAddLinkbutton").on('click', add);
	$("#currencyRateSaveLinkbutton").on('click', save);
	$("#currencyRateCancelLinkbutton").on('click', cancle);
	$("#currencyRateExportLinkbutton").on('click', {url : '/currencyRate/exportExcel',grid: '#currencyRateDataGrid'},derive);
	$("#currencyRateUploadLinkbutton").on('click', currencyRateUpload);
	$("#upload").on('click', {grid: '#currencyRateDataGrid', dialog: '#uploadDialog', url: '/currencyRate/uploadExcel'}, upload);
	
	$("#currencyRateDataGrid").datagrid({
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	});
	
	// 遮罩
	$('#currencyRateDialog').dialog({
		modal:true
	});
	
	//查询
	$("#query").click(function(){
		if(!$("#rateForm").form('validate')) {
			return;
		}
		var boolean = moreTime();
		if(boolean) {
			$.messager.alert("提示信息", "结束时间应大于开始时间");
			return;
		}
		var param = {
				countryId : $('#country_Id').combobox('getValue'),
				currencyCode : $('#currencyCode').textbox('getValue'),
				currencySymbol : $('#currencySymbol').textbox('getValue'),
				riskFactor : $('#riskFactor').numberbox('getValue'),
				cstarttime : $('#cstarttime').datebox('getValue'),
				cendtime : $('#cendtime').datebox('getValue'),
				ustarttime : $('#ustarttime').datebox('getValue'),
				uendtime : $('#uendtime').datebox('getValue')
		}
		
		$('#currencyRateDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#country_Id').combobox('setValue','');
		$('#currencyCode').textbox('setValue','');
		$('#currencySymbol').textbox('setValue','');
		$('#riskFactor').numberbox('setValue','');
		$('#cstarttime').datebox('setValue','');
		$('#cendtime').datebox('setValue','');
		$('#ustarttime').datebox('setValue','');
		$('#uendtime').datebox('setValue','');
	});
	
});
