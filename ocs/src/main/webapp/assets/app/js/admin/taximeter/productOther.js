

function editOpen($row) {
	$('#productOtherForm').form('clear');
	$('#productOtherForm').form('load', $row);
	//下拉框不可选
	$('#countryId').combobox('disable');
	$('#sku').textbox('disable');
	$('#productOtherDialog').window('open');
}

// 修改
var save = function() {
	$('#productOtherForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#productOtherDialog').window('close');
				$("#productOtherDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#productOtherDataGrid').datagrid('clearSelections');
		}
	});
};

var cancle = function() {
	$('#productOtherDialog').window('close');
}

var productOtherUpload = function() {
	$('#uploadForm').form('clear');
	$('#uploadDialog').window('open');
}

$(function() {
	$("#productOtherSaveLinkbutton").on('click', save);
	$("#productOtherCancelLinkbutton").on('click', cancle);
	$("#productOtherExportLinkbutton").on('click',  {url : '/productOther/exportExcel',grid: '#productOtherDataGrid'},derive);
	$("#productOtherUploadLinkbutton").on('click', productOtherUpload);
	$("#upload").on('click', {grid: '#productOtherDataGrid', dialog: '#uploadDialog', url: '/productOther/uploadExcel'}, upload);
	
	$("#productOtherDataGrid").datagrid({
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	});
	
	// 遮罩
	$('#productOtherDialog').dialog({
		modal:true
	});
	
	//查询
	$("#query").click(function(){
		if(!$("#productOther_Form").form('validate')) {
			return;
		}
		var boolean = moreTime();
		if(boolean) {
			$.messager.alert("提示信息", "结束时间应大于开始时间");
			return;
		}
		var param = {
				countryId : $('#country_Id').combobox('getValue'),
				sku : $('#sku_').textbox('getValue'),
				username : $('#username').textbox('getValue'),
				category : $('#category').textbox('getValue'),
				turnoverRate : $('#turnoverRate').numberbox('getValue'),
				qtyOrdered : $('#qtyOrdered').numberbox('getValue'),
				averageMonth : $('#averageMonth').numberbox('getValue'),
				unfulliableRate : $('#unfulliableRate').numberbox('getValue'),
				replacementRate : $('#replacementRate').numberbox('getValue'),
				clearPrice : $('#clearPrice').numberbox('getValue'),
				amzFba : $('#amzFba').numberbox('getValue'),
				efnFee : $('#efnFee').numberbox('getValue'),
				dutyRate : $('#dutyRate').numberbox('getValue'),
				cstarttime : $('#cstarttime').datebox('getValue'),
				cendtime : $('#cendtime').datebox('getValue'),
				ustarttime : $('#ustarttime').datebox('getValue'),
				uendtime : $('#uendtime').datebox('getValue')
		}
		
		$('#productOtherDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#country_Id').combobox('setValue','');
		$('#sku_').textbox('setValue','');
		$('#username').textbox('setValue','');
		$('#category').textbox('setValue','');
		$('#turnoverRate').numberbox('setValue','');
		$('#qtyOrdered').numberbox('setValue','');
		$('#averageMonth').numberbox('setValue','');
		$('#unfulliableRate').numberbox('setValue','');
		$('#replacementRate').numberbox('setValue','');
		$('#clearPrice').numberbox('setValue','');
		$('#amzFba').numberbox('setValue','');
		$('#efnFee').numberbox('setValue','');
		$('#dutyRate').numberbox('setValue','');
		$('#cstarttime').datebox('setValue','');
		$('#cendtime').datebox('setValue','');
		$('#ustarttime').datebox('setValue','');
		$('#uendtime').datebox('setValue','');
	});
	
});
