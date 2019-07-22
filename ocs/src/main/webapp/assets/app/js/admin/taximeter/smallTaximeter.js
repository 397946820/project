var beforeProfitRateAction = null;
var beforeQty = null;
var beforeFinalPrice = null;

$(function() {
	
	comboboxInit('shippingType', '');
	comboboxInit('typeName', '/smallTypeItem/getTypeName');
	comboboxChange('typeName', 'shippingType');
	
	$("#shippingType").combobox({
		onChange : function(newValue, oldValue) {
			var typeName = $("#typeName").combobox('getValue');
			if (newValue && (typeName == '欧洲专线' || typeName == '经济小包')) {
				$("#currencyCode_1").show();
				$("#currencyCode_2").show();
			} else {
				$("#currencyCode_1").hide();
				$("#currencyCode_2").hide();
			}
		}
	})

	$('#smallTaximeterDataGrid').datagrid({
		onLoadSuccess : function(data) {
			var platform = $("#platform").combobox('getValue');
			if(platform == 'ebay') {
				$('#smallTaximeterDataGrid').datagrid('showColumn','tradingMode');
			} else if(platform == 'light') {
				$('#smallTaximeterDataGrid').datagrid('hideColumn','tradingMode');
			}
			$(this).datagrid("clearSelections");
		},
		onBeforeEdit : function(index, row) {
			beforeProfitRateAction = row.profitRateAction;
			beforeQty = row.qty;
			beforeFinalPrice = row.finalPrice;
			row.editing = true;
		},

		onAfterEdit : function(index, row) {
			row.editing = false;
			if (beforeProfitRateAction != row.profitRateAction
					|| beforeQty != row.qty || beforeFinalPrice != row.finalPrice) {
				$.ajax({
					url : GLOBAL.domain + "/smallTaximeter/editSmallTaximeter",
					type : 'POST',
					data : {
						entityId : row.entityId,
						profitRateAction : row.profitRateAction,
						finalPrice : row.finalPrice,
						qty : row.qty,
						currencyCode : $("#smallTaximeterDataGrid").datagrid('options').queryParams.param.currencyCode
					},
					success : function(data) {
						data = JSON.parse(data);
						if(data.errorCode == 0) {
							row.profitRateAction = data.data.profitRateAction;
							row.finalPrice = data.data.finalPrice;
							row.finalCost = data.data.finalCost;
							row.qty = data.data.qty;
							row.updatedAt = data.data.updatedAt;
							$('#smallTaximeterDataGrid').datagrid('refreshRow', index);
						} else {
							$.messager.alert('消息提示', '数量过大,无法匹配并计算!', 'warning');
							return;
						}
					}
				});
			}
		},
		onCancelEdit : function(index, row) {
			row.editing = false;
			$('#smallTaximeterDataGrid').datagrid('refreshRow', index);
		},
		onClickCell : function(index, field,value){
			$('#smallTaximeterDataGrid').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});;
		}
	});
	
	$("#smallTaximeterRefreshLinkbutton").on('click', {
		url : '/smallTaximeter/refresh',
		grid : '#smallTaximeterDataGrid'
	}, refresh);
	
	$("#query").click(function() {
		var platform = $('#platform').combobox('getValue');
		if (platform == '') {
			$.messager.alert("提示信息", "您没有分配权限！请联系管理员！");
			return;
		}
		if ($("#smallTaximeterDataGrid").datagrid('options').url == '') {
			$("#smallTaximeterDataGrid").datagrid('options').url = GLOBAL.domain
					+ '/smallTaximeter/findAll';
		}
		var formData = $("#smallTaximeterSearchForm").serializeArray();
		var param = {};
		$.each(formData, function() {
			param[this.name] = this.value;
		});
		$('#smallTaximeterDataGrid').datagrid('load', {
			param : param
		});
	});

	$("#reset").on('click', function() {
		var platform = $("#platform").combobox('getValue');
		$("#smallTaximeterSearchForm").form("clear");
		$("#platform").combobox('setValue', platform);
	});
})

function getTransactionMode(value, row, index) {
	if (value == '0') {
		return '线下';
	} else if (value == '1') {
		return '线上';
	} else {
		return value;
	}
}

	