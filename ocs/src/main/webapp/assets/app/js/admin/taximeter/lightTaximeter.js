var beforeProfitRateAction = null;
var beforeFinalPrice = null;
var editIndex = undefined;
var editIndex_ = undefined;
var v_temp = '';
var v_rows = [];

$(function() {

	$.extend($.fn.datagrid.methods, {
		getEditingRowIndexs : function(jq) {
			var rows = $.data(jq[0], "datagrid").panel
					.find('.datagrid-row-editing');
			var indexs = [];
			rows.each(function(i, row) {
				var index = row.sectionRowIndex;
				if (indexs.indexOf(index) == -1) {
					indexs.push(index);
				}
			});
			return indexs;
		}
	});

	$("#lightTaximeterRecoverLinkbutton").on('click', {
		url : '/lightTaximeter/recover',
		grid : '#lightTaximeterDataGrid'
	}, recover);
	$("#lightTaximeterRefreshLinkbutton").on('click', {
		url : '/lightTaximeter/refresh',
		grid : '#lightTaximeterDataGrid'
	}, refresh);
	$("#lightTaximeterExportAllLinkbutton").on('click', {
		url : '',
		grid : '#lightTaximeterDataGrid'
	}, leDerive);
	$("#lePricePlanReckonLinkbutton").on('click', {
		temp : 'reckon'
	}, lePricePlanReckon);
	$("#lePricePlanLinkVariantbutton").on('click', lePricePlanLinkVariant);
	$("#leReckonPrice_").on('click', lePriceCal);
	$("#leReckonExportLinkbutton").on('click', leReckonExport);
	$("#leReckonRate_").on('click', leRateCal);
	$("#lePricePlanTestLinkbutton").on('click', lePricePlanTest);
	$("#lePricePlanLinkCustomerbutton").on('click', {
		temp : 'customer'
	}, lePricePlanLinkCustomer);
	$("#lightTaximeterExwfobLinkbutton").on('click',lightTaximeterExwfob);

	if ($('#platform').combobox('getValue') != '') {
		setCountrys($('#platform').combobox('getValue'), '#country');
	}

	$('#lightTaximeterDataGrid').datagrid({
		onLoadSuccess : function(data) {

		},

		onBeforeEdit : function(index, row) {
			beforeProfitRateAction = row.profitRateAction;
			beforeFinalPrice = row.finalPrice;
			row.editing = true;
		},

		onAfterEdit : function(index, row) {
			row.editing = false;
			if (beforeProfitRateAction != row.profitRateAction
					|| beforeFinalPrice != row.finalPrice) {
				var entityId = row.entityId;
				var profitRateAction = row.profitRateAction;
				var finalPrice = row.finalPrice;
				var url = GLOBAL.domain
						+ "/lightTaximeter/editLightTaximeter";
				$.ajax({
					url : url,
					type : 'POST',
					data : {
						entityId : entityId,
						profitRateAction : profitRateAction,
						finalPrice : finalPrice
					},
					success : function(data) {
						var rows = JSON.parse(data);
						row.profitRateAction = rows.profitRateAction;
						row.finalPrice = rows.finalPrice;
						row.updatedAt = rows.updatedAt;
						$('#lightTaximeterDataGrid').datagrid(
								'refreshRow', index);
					}
				});
			}
		},
		onCancelEdit : function(index, row) {
			row.editing = false;
			$('#lightTaximeterDataGrid').datagrid('refreshRow', index);
		},
		onClickCell : function(index, field,value){
			$('#lightTaximeterDataGrid').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});;
		}
	});

	// 平台改变事件
	$("#platform").combobox({
		onChange : function(newValue, oldValue) {
			setCountrys(newValue, '#country');
		}
	});

	// 平台改变事件
	$("#platform_").combobox({
		onChange : function(newValue, oldValue) {
			setCountrys(newValue, '#country_');
			if (beforeValidate()) {
				var param = {
					platform : newValue,
					countryId : $('#country_').combobox('getValue'),
					isCostOf : $('#isCostOf_').combobox('getValue'),
					isStorageCharges : $('#isStorageCharges_')
							.combobox('getValue'),
					transactionMode : $('#transactionMode_').combobox(
							'getValue')
				};
				changeAllFinalPrice(param);
			}
		}
	});

	// 国家改变事件
	$("#country_").combobox({
		onChange : function(newValue, oldValue) {
			if (beforeValidate()) {
				var param = {
					platform : $('#platform_').combobox('getValue'),
					countryId : newValue,
					isCostOf : $('#isCostOf_').combobox('getValue'),
					isStorageCharges : $('#isStorageCharges_')
							.combobox('getValue'),
					transactionMode : $('#transactionMode_').combobox(
							'getValue')
				};
				changeAllFinalPrice(param);
			}
		}
	});

	// 是否用占用费比改变事件
	$("#isCostOf_").combobox({
		onChange : function(newValue, oldValue) {
			if (beforeValidate()) {
				var param = {
					platform : $('#platform_').combobox('getValue'),
					countryId : $('#country_').combobox('getValue'),
					isCostOf : newValue,
					isStorageCharges : $('#isStorageCharges_')
							.combobox('getValue'),
					transactionMode : $('#transactionMode_').combobox(
							'getValue')
				};
				changeAllFinalPrice(param);
			}
		}
	});

	// 是否用仓租费改变事件
	$("#isStorageCharges_").combobox({
		onChange : function(newValue, oldValue) {
			if (beforeValidate()) {
				var param = {
					platform : $('#platform_').combobox('getValue'),
					countryId : $('#country_').combobox('getValue'),
					isCostOf : $('#isCostOf_').combobox('getValue'),
					isStorageCharges : newValue,
					transactionMode : $('#transactionMode_').combobox(
							'getValue')
				};
				changeAllFinalPrice(param);
			}
		}
	});

	// 交易方式改变事件
	$("#transactionMode_").combobox({
		onChange : function(newValue, oldValue) {
			if (beforeValidate()) {
				var param = {
					platform : $('#platform_').combobox('getValue'),
					countryId : $('#country_').combobox('getValue'),
					isCostOf : $('#isCostOf_').combobox('getValue'),
					isStorageCharges : $('#isStorageCharges_')
							.combobox('getValue'),
					transactionMode : newValue
				};
				changeAllFinalPrice(param);
			}
		}
	});

	// 平台改变事件
	$("#platform__").combobox({
		onChange : function(newValue, oldValue) {
			setCountrys(newValue, '#country__');
		}
	});

	// 查询
	$("#query").click(function() {
		var platform = $('#platform').combobox('getValue');
		if (platform == '') {
			$.messager.alert("提示信息", "您没有分配权限！请联系管理员！");
			return;
		}
		var param = {
			platform : platform,
			country : $('#country').combobox('getValue'),
			shippingType : $('#shippingType').combobox(
					'getValue'),
			isCostOf : $('#isCostOf').combobox('getValue'),
			isStorageCharges : $('#isStorageCharges').combobox(
					'getValue'),
			transactionMode : $('#transactionMode').combobox(
					'getValue'),
			sku : $('#sku').textbox('getValue')
		}
		if ($("#lightTaximeterDataGrid").datagrid('options').url == '') {
			$("#lightTaximeterDataGrid").datagrid('options').url = GLOBAL.domain
					+ '/lightTaximeter/findAll';
		}
		$('#lightTaximeterDataGrid').datagrid('load', {
			param : param
		});
	});

	// 重置
	$("#reset").click(function() {
		$('#country').combobox('setValue', '');
		$('#shippingType').combobox('setValue', '');
		$('#sku').textbox('setValue', '');
		$('#isCostOf').combobox('setValue', '');
		$('#isStorageCharges').combobox('setValue', '');
		$('#transactionMode').combobox('setValue', '');
	});

	// 查询系数
	$("#queryProfitRateAction").click(function() {
		if (validateFinalOrProfitRateAction("profitRateAction")) {
			queryFinalOrProfitRateAction("profitRateAction");
		}
	});

	// 查询售价
	$("#queryFinalPrice").click(function() {
		if (validateFinalOrProfitRateAction("finalPrice")) {
			queryFinalOrProfitRateAction("finalPrice");
		}
	});

	$('#sku__').combobox({
		url : GLOBAL.domain + '/lightTaximeter/findAllSku',
		valueField : 'name',
		textField : 'value'
	})

	// 测试查询
	$('#lePricePlanTestQuery').click(function() {
		if (!$("#lePricePlanTestForm").form('validate')) {
			return false;
		}
		var data = {
			platform : $('#platform__').combobox('getValue'),
			countryId : $('#country__').combobox('getValue'),
			isCostOf : $('#isCostOf__').combobox('getValue'),
			isStorageCharges : $('#isStorageCharges__').combobox('getValue'),
			transactionMode : $('#transactionMode__').combobox('getValue'),
			sku : $('#sku__').combobox('getValue'),
			qty : $("#qty__").numberbox('getValue')
		};
		$.ajax({
			type : "POST",
			url : GLOBAL.domain + '/lightTaximeter/lePricePlanTest',
			data : data,
			success : function(response) {
				var rows = eval(response);
				$('#lePricePlanTestShow1').html(rows[1]);
				$('#lePricePlanTestShow2').html(rows[0]);
			}
		});
	})

	// 测试重置
	$('#lePricePlanTestReset').click(function() {
		$('#lePricePlanTestShow1').html('');
		$('#lePricePlanTestShow2').html('');
		$('#country__').combobox('setValue', '');
		$('#platform__').combobox('setValue', '');
		$('#sku__').textbox('setValue', '');
		$('#isCostOf__').combobox('setValue', '');
		$('#isStorageCharges__').combobox('setValue', '');
		$('#transactionMode__').combobox('setValue', '');
	})
})

function changeAllFinalPrice(data) {
	var rows = $('#TaximeterDialogTable').datagrid("getRows");
	var sku = '', shippingType = '';
	for (var i = 0; i < rows.length; i++) {
		if (i != rows.length - 1) {
			sku += rows[i].sku + ",";
			shippingType += rows[i].shippingType + ",";
		} else {
			sku += rows[i].sku;
			shippingType += rows[i].shippingType;
		}
	}
	var temp = {
		sku : sku,
		shippingType : shippingType
	};
	$.ajax({
		type : "POST",
		url : GLOBAL.domain + '/lightTaximeter/changeAllFinalPrice',
		data : Object.assign(temp, data),
		success : function(response) {
			var prices = eval(response);
			for (var i = 0; i < rows.length; i++) {
				rows[i].finalPrice = prices[i];
				$('#TaximeterDialogTable').datagrid('refreshRow', i);
			}
		}
	});
}

function changeFinalPrice(newValue, value) {
	var i = $('#TaximeterDialogTable').datagrid('getEditingRowIndexs')[0];
	var temp = $('#TaximeterDialogTable').datagrid('getEditor', {
		index : i,
		field : value
	}).target.val();
	if (temp != '') {
		var param = {};
		if (value == 'sku') {
			param = {
				sku : temp,
				shippingType : newValue
			};
		} else if (value == 'shippingType') {
			param = {
				sku : newValue,
				shippingType : temp
			};
		}
		var data = {
			platform : $('#platform_').combobox('getValue'),
			countryId : $('#country_').combobox('getValue'),
			isCostOf : $('#isCostOf_').combobox('getValue'),
			isStorageCharges : $('#isStorageCharges_').combobox('getValue'),
			transactionMode : $('#transactionMode_').combobox('getValue')
		};
		$.ajax({
			type : "POST",
			url : GLOBAL.domain + '/lightTaximeter/changeFinalPrice',
			data : Object.assign(param, data),
			success : function(response) {
				$('#TaximeterDialogTable').datagrid('getEditor', {
					index : i,
					field : "finalPrice"
				}).target.numberbox("setValue", response);
			}
		});
	}
}

function changeSku(newSku) {
	if (newSku != undefined) {
		changeFinalPrice(newSku, 'shippingType');
	}
}

function changeShippingType(newShippingType) {
	if (newShippingType != undefined) {
		changeFinalPrice(newShippingType, 'sku');
	}
}

var lePricePlanTest = function() {
	$('#lePricePlanTestShow1').html('');
	$('#lePricePlanTestShow2').html('');
	$('#lePricePlanTestForm').form('clear');
	$('#lePricePlanTest').window('open');
}

var leDerive = function(event) {
	var template = $('#template').combobox('getValue');
	if (template == '') {
		event.data.url = '/excel/export/lightTaximeterExport';
		export_(event);
	} else {
		event.data.url = '/lightTaximeter/exportExcel';
		derive(event);
	}
}
// 导出
var leReckonExport = function() {
	$.messager.confirm('请先确认', '确定要导出吗？', function(r) {
		if (r) {
			$("#leReckon_").hide();
			window.location.href = GLOBAL.domain
					+ '/lightTaximeter/reckonExport';
		}
	});
};
function leCal(url) {
	var formData = new FormData();
	var myfile = document.getElementById("file").files[0];
	if (myfile == '' || myfile == null) {
		$.messager.alert("提示信息", "请选择导入的文件");
		return;
	}
	formData.append("myfile", myfile);
	var url = GLOBAL.domain + url;
	$.ajax({
		type : "POST",
		url : url,
		enctype : 'multipart/form-data',
		data : formData,
		contentType : false,
		processData : false,
		beforeSend : function() {
			$.messager.progress({
				title : '请稍后',
				msg : '正在计算...'
			});
		},
		complete : function() {
			$.messager.progress('close');
		},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				if ($data.data == '你没有权限,请联系管理员') {
					$("#leMessages_").html("失败!")
					$("#leShowMessages_").html($data.data);
					$("#leMessages").panel('open');
					$("#leMessages_").show();
				} else {
					var json = $data.data;
					if (json && json.length > 1) {
						$("#leShowMessages_").html(json);
						$("#leMessages").panel('open');
						$("#leMessages_").show();
					}
					$("#leReckon_").show();
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert('消息提示', '导入失败', 'warning');
		}
	});
}
function leRateCal() {
	if (v_temp == 'reckon') {
		leCal('/lightTaximeter/profitRate');
	} else if (v_temp == 'customer') {
		leCal('/lightTaximeter/customerProfitRate');
	}
}
function lePriceCal() {
	if (v_temp == 'reckon') {
		leCal('/lightTaximeter/priceCal');
	} else if (v_temp == 'customer') {
		leCal('/lightTaximeter/customerPrice');
	}
}

var lePricePlanLinkVariant = function() {
	$('#TaximeterDialogToolbar').form('clear');
	$("#isCostOf_").combobox('setValue',1);
	$("#isStorageCharges_").combobox('setValue',1);
	$("#transactionMode_").combobox('setValue',1);
	$('#TaximeterDialogTable').datagrid("loadData", []);
	$('#lightTaximeterDialog').window('open');
}

function beforeValidate() {
	if (!$("#TaximeterDialogToolbar").form('validate')) {
		return false;
	}
	if (editIndex != undefined) {
		if (!$('#TaximeterDialogTable').datagrid('validateRow', editIndex)) {
			return false;
		} else {
			$('#TaximeterDialogTable').datagrid('endEdit', editIndex);
		}
	}
	if ($("#TaximeterDialogTable").datagrid("getRows").length == 0) {
		return false;
	}
	return true;
}

function validateFinalOrProfitRateAction(value) {
	if (beforeValidate()) {
		if (value == 'profitRateAction') {
			var finalPrice = $('#finalPrice').numberbox('getValue');
			if (finalPrice == '' || finalPrice == 0) {
				$.messager.alert("提示信息", "价格为0或者空!");
				return false;
			}
		} else {
			var profitRateAction = $('#profitRateAction').numberbox('getValue');
			if (profitRateAction == '') {
				$.messager.alert("提示信息", "系数为空!");
				return false;
			}
		}
	} else {
		return false;
	}
	return true;
}

function queryFinalOrProfitRateAction(value) {
	$("#TaximeterDialogTable").datagrid('clearSelections');
	var rows = $("#TaximeterDialogTable").datagrid("getRows");
	var sku = '', shippingType = '', qty = '';
	for (var i = 0; i < rows.length; i++) {
		if (i != rows.length - 1) {
			sku += rows[i].sku + ",";
			shippingType += rows[i].shippingType + ",";
			qty += rows[i].qty + ",";
		} else {
			sku += rows[i].sku;
			shippingType += rows[i].shippingType;
			qty += rows[i].qty + '';
		}
	}
	var data = {
		platform : $('#platform_').combobox('getValue'),
		countryId : $('#country_').combobox('getValue'),
		sku : sku,
		shippingType : shippingType,
		qty : qty,
		isCostOf : $('#isCostOf_').combobox('getValue'),
		isStorageCharges : $('#isStorageCharges_').combobox('getValue'),
		transactionMode : $('#transactionMode_').combobox('getValue'),
		profitRateAction : $('#profitRateAction').numberbox('getValue'),
		finalPrice : $('#finalPrice').numberbox('getValue')
	};
	var url = GLOBAL.domain;
	if (value == 'finalPrice') {
		url += '/lightTaximeter/calculateFinalPrice';
	} else {
		url += '/lightTaximeter/calculateProfitRateAction';
	}

	$.ajax({
		type : "POST",
		url : url,
		data : data,
		beforeSend : function() {
			$.messager.progress({
				title : '请稍后',
				msg : '正在计算...'
			});
		},
		complete : function() {
			$.messager.progress('close');
		},
		success : function(response) {
			if (value == 'finalPrice') {
				$('#finalPrice').numberbox('setValue', response);
			} else {
				$('#profitRateAction').numberbox('setValue', response);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert('消息提示', '计算失败', 'warning');
		}
	});
}

function endEditing() {
	if (editIndex == undefined) {
		return true
	}
	if ($('#TaximeterDialogTable').datagrid('validateRow', editIndex)) {
		$('#TaximeterDialogTable').datagrid('endEdit', editIndex);
		return true;
	} else {
		return false;
	}
}

function onClickCell(index) {
	$('#TaximeterDialogTable').datagrid('clearSelections');
	if (endEditing()) {
		$('#TaximeterDialogTable').datagrid('selectRow', index).datagrid(
				'beginEdit', index);
		editIndex = index;
	} else {
		$('#TaximeterDialogTable').datagrid('selectRow', editIndex);
	}
}

function append() {
	if (!$("#TaximeterDialogToolbar").form('validate')) {
		return;
	}
	if (editIndex != undefined) {
		$('#TaximeterDialogTable').datagrid('unselectRow', editIndex);
		$('#TaximeterDialogTable').datagrid("endEdit", editIndex);
	}
	if (endEditing()) {
		$('#TaximeterDialogTable').datagrid('appendRow', {});
		editIndex = $('#TaximeterDialogTable').datagrid('getRows').length - 1;
		$('#TaximeterDialogTable').datagrid('selectRow', editIndex).datagrid(
				'beginEdit', editIndex);
	}

}
function removeit() {
	// 删除时先获取选择行
	var rows = $("#TaximeterDialogTable").datagrid("getSelections");
	// 选择要删除的行
	if (rows.length > 0) {
		$.messager.confirm("提示", "你确定要删除吗?", function(r) {
			if (r) {
				for (var i = 0; i < rows.length; i++) {
					var rowIndex = $('#TaximeterDialogTable').datagrid(
							'getRowIndex', rows[i]);
					$("#TaximeterDialogTable").datagrid('deleteRow', rowIndex);
				}
			}
		});
	} else {
		$.messager.alert("提示", "请选择要删除的行", "error");
	}
}

var lePricePlanReckon = function(even) {
	reset_(even);
}

var lePricePlanLinkCustomer = function(even) {
	reset_(even);
}

function reset_(even) {
	v_temp = even.data.temp;
	$('#leReckonForm').form('clear');
	$('#leReckonDialog').window('open');
	$("#leReckon_").hide();
	$("#leMessages_").hide();
	$("#leMessages").panel('close');
	$("#leShowMessages_").html('');
}
function setCountrys(source, value) {
	$.ajax({
		url : GLOBAL.domain + "/lightTaximeter/getCountrys",
		data : {
			platform : source
		},
		type : 'POST',
		success : function(data) {
			var data = eval(data);
			if (data.length > 0) {
				var country_ = [];
				if (value == '#country') {
					country_.push({
						text : '-- 请选择 --',
						value : ''
					});
				}
				for (var i = 0; i < data.length; i++) {
					country_.push({
						text : getCountry_(data[i]),
						value : data[i]
					});
				}
				$(value).combobox('loadData', country_);
			}
		}
	})
}

function getIsCostOf(value, row, index) {
	if (value == '0') {
		return '否';
	} else if (value == '1') {
		return '是';
	} else {
		return value;
	}

}

function getTransactionMode(value, row, index) {
	if (value == '0') {
		return '线下';
	} else if (value == '1') {
		return '线上';
	} else {
		return value;
	}

}

$("#EXW-FOBForm").datagrid({
	url : '',
	columns : [ [ 
	{
		field : 'id',
		checkbox : true
	}, 
	{
		field : 'sku',
		title : 'SKU',
		align : 'center',
		width : 160,
		editor:{ type: 'combobox', 
			 options: {
				panelHeight: 'auto',
				panelMaxHeight: '120',
			    url : GLOBAL.domain + '/lightTaximeter/findAllSku',
				valueField : 'name',
				textField : 'value',
				required:true
			 } 
		}
	}, 
	{
		field : 'qty',
		title : '数量',
		align : 'center',
		width : 100,
		editor : {type:'numberbox',options: {required:true,min:1,precision:0}}
	},
	{
		field : 'profitRateAction1',
		title : '利润系数1',
		align : 'center',
		width : 100,
		editor : {type : 'numberbox',options: {required:true,min:0,precision:4}}
	}, 
	{
		field : 'profitRateAction2',
		title : '利润系数2',
		align : 'center',
		width : 100,
		editor : {type : 'numberbox',options: {min:0,precision:4}}
	}, 
	{
		field : 'profitRateAction3',
		title : '利润系数3',
		align : 'center',
		width : 100,
		editor : {type : 'numberbox',options: {min:0,precision:4}}
	}, 
	{
		field : 'profitRateAction4',
		title : '利润系数4',
		align : 'center',
		width : 100,
		editor : {type : 'numberbox',options: {min:0,precision:4}}
	}, 
	{
		field : 'profitRateAction5',
		title : '利润系数5',
		align : 'center',
		width : 100,
		editor : {type : 'numberbox',options: {min:0,precision:4}}
	},
	{
		field : 'sku_',
		title : 'SKU',
		align : 'center',
		width : 160,
		hidden : true
	},
	{
		field : 'qty_',
		title : '数量',
		align : 'center',
		width : 100,
		hidden : true
	},
	{
		field : 'refundDuty',
		title : '是否退税',
		align : 'center',
		width : 80,
		hidden : true
	},
	{
		field : 'profitRateAction',
		title : '利润系数',
		align : 'center',
		width : 80,
		hidden : true
	},
	{
		field : 'totalClearPrice',
		title : '清关手续费',
		align : 'center',
		width : 80,
		hidden : true
	},
	{
		field : 'totalLogisticFee',
		title : '物流费',
		align : 'center',
		width : 80,
		hidden : true
	},
	{
		field : 'price',
		title : '总售价',
		align : 'center',
		width : 100,
		hidden : true
	},
	{
		field : 'unitPrice',
		title : '单个售价',
		align : 'center',
		width : 80,
		hidden : true
	}
	] ],
	toolbar : "#EXW-FOBtoolbar",
	singleSelect : false,
	rownumbers : true,
	border : true,
	onClickCell:function(index){
    	$("#EXW-FOBForm").datagrid('clearSelections');
	    if (exwFobEndEditing()) {  
	        $("#EXW-FOBForm").datagrid('selectRow', index).datagrid('beginEdit', index);  
	        editIndex_ = index;  
	    } else {  
	        $("#EXW-FOBForm").datagrid('selectRow', editIndex_);  
	    }  
	},
	onLoadSuccess : function(data) {
		$(this).datagrid("clearSelections");

	}
});

function beforeOpen() {
	$("#setButtons").show();
	$("#resultLinkbutton_").show();
	$("#resultButtons").hide();
	$("#EXW-FOBForm").datagrid('loadData', { total: 0, rows: [], footer:[] }); 
	exwFobShowOrHide("#EXW-FOBForm",new Array('sku','qty','profitRateAction1','profitRateAction2','profitRateAction3',
			'profitRateAction4','profitRateAction5'),'show');
	exwFobShowOrHide("#EXW-FOBForm",new Array('sku_','qty_','refundDuty','profitRateAction','totalClearPrice','totalLogisticFee',
			'price','unitPrice'),'hide');
}

function lightTaximeterExwfob() {
	beforeOpen();
	$("#EXW-FOB").form("clear");
	$("#EXW-FOBDialod").dialog({
		buttons:[{
			text:'关闭',
			handler:function(){
				v_rows = [];
				$("#EXW-FOBDialod").dialog("close");
			}
		}]
	});
	$("#EXW-FOBDialod").dialog("open");
}

function exwFobShowOrHide(value,arr,flag) {
	if(flag == 'show') {
		for (var i = 0; i < arr.length; i++) {
			$(value).datagrid('showColumn', arr[i]);
		}
	} else if(flag == 'hide'){
		for (var i = 0; i < arr.length; i++) {
			$(value).datagrid('hideColumn', arr[i]);
		}
	}
}

function exwFobEndEditing() {
	if (editIndex_ == undefined){return true}
	if ($("#EXW-FOBForm").datagrid('validateRow', editIndex_)){
		$("#EXW-FOBForm").datagrid('endEdit', editIndex_);
		return true;
	} else {
		return false;
	}
}

$("#addEXW-FOBLinkbutton").click(function() {
	if (editIndex_ != undefined) {
		$("#EXW-FOBForm").datagrid('unselectRow', editIndex_);
		$("#EXW-FOBForm").datagrid("endEdit", editIndex_);
	}
	if (exwFobEndEditing() && $("#EXW-FOB").form('validate')){
		$("#EXW-FOBForm").datagrid('appendRow',{});
		editIndex_ = $("#EXW-FOBForm").datagrid('getRows').length-1;
		$("#EXW-FOBForm").datagrid('selectRow', editIndex_).datagrid('beginEdit', editIndex_);
	}
});

$("#removeEXW-FOBLinkbutton").click(function() {
	var rows = $("#EXW-FOBForm").datagrid("getSelections");
	if (rows.length > 0) {
		$.messager.confirm("提示", "你确定要删除吗?", function(r) {
			if (r) {
				for (var i = 0; i < rows.length; i++) {
					var rowIndex = $('#EXW-FOBForm').datagrid(
							'getRowIndex', rows[i]);
					$("#EXW-FOBForm").datagrid('deleteRow', rowIndex);
				}
			}
		});
	} else {
		$.messager.alert("提示", "请选择要删除的行", "error");
	}

});

$("#resultLinkbutton").click(function() {
	if(!$("#EXW-FOB").form('validate') || !$("#EXW-FOBForm").datagrid('validateRow', editIndex_)) {
		return;
	}
	$("#EXW-FOBForm").datagrid("endEdit", editIndex_);
	var rows = $("#EXW-FOBForm").datagrid('getRows');
	if(rows.length == 0) {
		return;
	}
	var param = {
			rows : rows
	};
	var exwfob = $("#EXW-FOB").serializeArray();
	$.each(exwfob,function(){
		param[this.name] = this.value;
	});
	ocs.ajax({
		url:'/lightTaximeter/fobexwReverse',
		async:true,
		data : {"row":param},
		beforeSend: function () {
			$.messager.progress({
				title: '请稍后',
				msg: '正在推算...'
			});
		},
		complete: function () {
			$.messager.progress('close');
		},
		type: "POST",
		success: function(result) {
			if(result) {
				v_rows = rows;
				$("#setButtons").hide();
				$("#resultLinkbutton_").hide();
				$("#resultButtons").show();
				$("#EXW-FOBForm").datagrid('loadData', { total: 0, rows: result, footer:[] }); 
				exwFobShowOrHide("#EXW-FOBForm",new Array('sku','qty','profitRateAction1','profitRateAction2','profitRateAction3',
						'profitRateAction4','profitRateAction5'),'hide');
				exwFobShowOrHide("#EXW-FOBForm",new Array('sku_','qty_','refundDuty','profitRateAction','totalClearPrice','totalLogisticFee',
						'price','unitPrice'),'show');
			}
		}
	});
});

$("#deriveEXW-FOBLinkbutton").on("click", function() {
	$.messager.confirm('请先确认', '确定要导出吗？', function (r) {
        if (r) {
        	var rows = $("#EXW-FOBForm").datagrid("getRows");
			var template = JSON.stringify(rows);
			var form = $("<form>");   
		    form.attr('style','display:none');
		    form.attr('method','post');
		    form.attr('action',GLOBAL.domain + '/excel/export/lightFobexwExport'); 
		    var input1 = $('<input>'); 
		    input1.attr('type','hidden'); 
		    input1.attr('name','template'); 
		    input1.attr('value',template);  
	        $('body').append(form);
	        form.append(input1); 
	        form.submit(); 
        }
	});
});

$("#setLinkbutton").on("click", function() {
	if(!$("#EXW-FOBForm").datagrid('validateRow', editIndex_)) {
		return;
	}
	$("#EXW-FOBForm").datagrid("endEdit", editIndex_);
	var rows = $("#EXW-FOBForm").datagrid('getRows');
	if(rows.length == 0 || rows[0].sku_ == undefined) {
		return;
	}
	beforeOpen();
	$("#EXW-FOBForm").datagrid('loadData', { total: 0, rows: v_rows, footer:[] });
});
