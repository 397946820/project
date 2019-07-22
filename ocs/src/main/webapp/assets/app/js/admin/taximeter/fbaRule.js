//打开添加的窗口
var add = function() {
	$('#fbaRuleForm').form('clear');
	$('#countryId').combobox('enable');
	$("#countryId").combobox('setValue','US');
	$('#fbaRuleDialog').window('open');
};

function editOpen($row) {
	$('#fbaRuleForm').form('clear');
	$('#fbaRuleForm').form('load', $row);
	//下拉框不可选
	$('#countryId').combobox('disable');
	//处理月份
	var isMonth = $row.isMonth;
	var months = isMonth.split(",");
	var checkArry = document.getElementsByName("isMonth");
	for (var i = 0; i < months.length; i++) {
		for (var j = 0; j < checkArry.length; j++) {
			if(months[i] == checkArry[j].value) {
				$("input[type=checkbox][value='"+checkArry[j].value+"']").prop("checked", true);
			}
		}
	}
	$('#fbaRuleDialog').window('open');
}

// 保存和修改
var save = function() {
	$('#fbaRuleForm').form('submit', {
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#fbaRuleDialog').window('close');
				$("#fbaRuleDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#fbaRuleDataGrid').datagrid('clearSelections');
		}
	});
};

var cancle = function() {
	$('#fbaRuleDialog').window('close');
}

var fbaRuleUpload = function() {
	$('#uploadForm').form('clear');
	$('#uploadDialog').window('open');
}

$(function() {
	$("#fbaRuleAddLinkbutton").on('click', add);
	$("#fbaRuleSaveLinkbutton").on('click', save);
	$("#fbaRuleCancelLinkbutton").on('click', cancle);
	$("#fbaRuleExportLinkbutton").on('click', {url : '/fbaRule/exportExcel',grid: '#fbaRuleDataGrid'},derive);
	$("#fbaRuleUploadLinkbutton").on('click', fbaRuleUpload);
	$("#upload").on('click', {grid: '#fbaRuleDataGrid', dialog: '#uploadDialog', url: '/fbaRule/uploadExcel'}, upload);
	
	$("#fbaRuleDataGrid").datagrid({
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	});
	
	// 遮罩
	$('#fbaRuleDialog').dialog({
		modal:true
	});
	
	//查询
	$("#query").click(function(){
		if(!$("#ruleForm").form('validate')) {
			return;
		}
		var boolean = moreTime();
		if(boolean) {
			$.messager.alert("提示信息", "结束时间应大于开始时间");
			return;
		}
		var param = {
				countryId : $('#country_Id').combobox('getValue'),
				title : $('#title').textbox('getValue'),
				handlingFee : $('#handlingFee').numberbox('getValue'),
				pickpackFee : $('#pickpackFee').numberbox('getValue'),
				flag : $('#flag').textbox('getValue'),
				price : $('#price').textbox('getValue'),
				isMonth : $('#isMonth').textbox('getValue'),
				sortOrder : $('#sortOrder').numberbox('getValue'),
				cstarttime : $('#cstarttime').datebox('getValue'),
				cendtime : $('#cendtime').datebox('getValue'),
				ustarttime : $('#ustarttime').datebox('getValue'),
				uendtime : $('#uendtime').datebox('getValue')
		}
		
		$('#fbaRuleDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#country_Id').combobox('setValue','');
		$('#title').textbox('setValue','');
		$('#handlingFee').numberbox('setValue','');
		$('#pickpackFee').numberbox('setValue','');
		$('#flag').textbox('setValue','');
		$('#price').textbox('setValue','');
		$('#isMonth').textbox('setValue','');
		$('#sortOrder').numberbox('setValue','');
		$('#cstarttime').datebox('setValue','');
		$('#cendtime').datebox('setValue','');
		$('#ustarttime').datebox('setValue','');
		$('#uendtime').datebox('setValue','');
	});
	
});

function size(data, row, index) {
	return '长:' + row.length + '<br/>宽:' + row.width + '<br/>高:' + row.height;
}

function condition(data, row, index) {

	return '起始重量:' + row.fromWeight + '<br/>结束重量:' + row.toWeight + '<br/>最大重量:' + row.maxWeight;
}

function other(data, row, index) {
	return 'EFN费用:' + row.efnFee + '<br/>FBA额外费用(JP,CA):' + row.extraFee;
}
