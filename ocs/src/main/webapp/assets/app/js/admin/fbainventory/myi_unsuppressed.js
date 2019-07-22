var myi_unsuppressed = {};
(function(myi_unsuppressed, $) {
	var date = new Date();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var today = date.getFullYear() + '-' + (month > 9 ? '' : '0') + month + '-' + (day > 9 ? '' : '0') + day;
	date.setDate(date.getDate() - 2);
	month = date.getMonth() + 1;
	day = date.getDate();
	var yesterday = date.getFullYear() + '-' + (month > 9 ? '' : '0') + month + '-' + (day > 9 ? '' : '0') + day;
	$('#fba_startTime').datebox({}).datebox('setValue', today);
	$('#fba_endTime').datebox({}).datebox('setValue', today);
	$('#myi_unsuppressedSearchParam table tr td.title').css('padding-left', '10px');
	
	var cellStringStyler = function(value, row, index) {
		return 'text-indent: 5px;';
	} 
	
	function getSearchParam() {
		var formData = $("#myi_unsuppressedSearchParam").serializeArray();
		var param = {};
		$.each(formData, function() {
			param[this.name] = this.value;
		});
		return param;
	}
	
	var columns = [[
		{ field: 'id', hidden: true }
		, { field: 'request_created_at', title: 'request_created_at', halign: 'center', align: 'center', width: 140 }
		//, { field: 'request_updated_at', title: 'request_updated_at', halign: 'center', align: 'center', width: 150 }
		//, { field: 'report_id', title: 'report_id', halign: 'center', align: 'center', width: 150 }
		, { field: 'site', title: 'site', halign: 'center', align: 'center', width: 100 }
		, { field: 'sku', title: 'sku', halign: 'center', align: 'left', width: 150, styler: cellStringStyler }
		//, { field: 'fnsku', title: 'fnsku', halign: 'center', align: 'left', width: 200 }
		//, { field: 'asin', title: 'asin', halign: 'center', align: 'left', width: 200 }
		//, { field: 'product_name', title: 'product_name', halign: 'center', align: 'left', width: 200 }
		//, { field: 'condition', title: 'condition', halign: 'center', align: 'left', width: 80 }
		//, { field: 'your_price', title: 'your_price', halign: 'center', align: 'right', width: 80 }
		//, { field: 'mfn_listing_exists', title: 'mfn_listing_exists', halign: 'center', align: 'center', width: 120 }
		, { field: 'mfn_fulfillable_quantity', title: 'mfn_fulfillable_quantity', halign: 'center', align: 'right', width: 150 }
		//, { field: 'afn_listing_exists', title: 'afn_listing_exists', halign: 'center', align: 'center', width: 120 }
		, { field: 'afn_warehouse_quantity', title: 'afn_warehouse_quantity', halign: 'center', align: 'right', width: 150 }
		, { field: 'afn_fulfillable_quantity', title: 'afn_fulfillable_quantity', halign: 'center', align: 'right', width: 150 }
		, { field: 'afn_unsellable_quantity', title: 'afn_unsellable_quantity', halign: 'center', align: 'right', width: 150 }
		, { field: 'afn_reserved_quantity', title: 'afn_reserved_quantity', halign: 'center', align: 'right', width: 150 }
		, { field: 'afn_total_quantity', title: 'afn_total_quantity', halign: 'center', align: 'right', width: 130 }
		, { field: 'per_unit_volume', title: 'per_unit_volume', halign: 'center', align: 'right', width: 120 }
		, { field: 'afn_inbound_working_quantity', title: 'afn_inbound_working_quantity', halign: 'center', align: 'right', width: 200 }
		, { field: 'afn_inbound_shipped_quantity', title: 'afn_inbound_shipped_quantity', halign: 'center', align: 'right', width: 200 }
		, { field: 'afn_inbound_receiving_quantity', title: 'afn_inbound_receiving_quantity', halign: 'center', align: 'right', width: 200 }
		, { field: 'reserved_fc_transfers', title: 'reserved_fc_transfers', halign: 'center', align: 'right', width: 150 }
		//, { field: 'created_at', title: 'created_at', halign: 'center', align: 'center', width: 100 }
		//, { field: 'updated_at', title: 'updated_at', halign: 'center', align: 'center', width: 100 }
		//, { field: 'enabled_flag', title: 'enabled_flag', halign: 'center', align: 'center', width: 100 }
	]];
	
	var maxDays = 31;
	
	$("#myi_unsuppressedList").datagrid({
		url: '/ocs/fbainventory/list',
		queryParams: {
			param: getSearchParam()
		},
		columns: columns,
		singleSelect: true,
		rownumbers: true,
		border: true,
		pagination: true,
		pageSize: 50,
		showFooter: true,
		emptyMsg: '<h3>数据尚未同步过来</h3>',
		onLoadSuccess: function(data){
			$(".datagrid-ftable tbody tr").each(function() {
			    this.style.backgroundColor="#E1EDFF";
			    this.style.color="blue";
			    this.style.fontWeight="bold";
			})
		},
		toolbar: [{
			text: '导出',
			iconCls: 'icon-redo',
			handler: function() {
				var downloadUrl = "/ocs/excel/dynamicExport/amazonMyiUnsuppressed";
				var param = getSearchParam();
				//if(!param['marketplace']) {
				//	$.messager.alert('提示', '不支持一次下载所有站点数据，请选择站点！', 'info');
				//	return;
				//}

				var startTime = formartStringToDate(param["startTime"])
					//, endTime = formartStringToDate(param["endTime"])
					, validInterval = startTime ;//&& endTime && (endTime.getTime() - startTime.getTime()) < maxDays * 24 * 60 * 60 * 1000;
				
				if(!validInterval) {
					//$.messager.alert('提示', '时间段过大，最多一次只能导出' + maxDays + '天数据，请分段导出！', 'info');
					$.messager.alert('提示', '仅支持按具体日期进行导出，请输入日期！', 'info');
					return;
				}
				
				var paramUrl = '?';
				for(var key in param) {
					paramUrl += key + '=' + param[key] + '&';
				}
				
				var includeFields = '';
				for(var i = 0, l = columns[0].length; i < l; i++) {
					includeFields += columns[0][i].field;
					if(i < l - 1) {
						includeFields += ',';
					}
				} 
				paramUrl += 'includeFields=' + includeFields;

				window.location.href = downloadUrl + paramUrl;
			}
		}]
	});
	
	function formartStringToDate(time){
		if(time){
			var regEx = new RegExp("\\-","gi"); 
			time=time.replace(regEx,"/");
			return new Date(time);
		}else{
			return null;
		}
	}
	
	$("#myi_unsuppressedSearch").click(function(){
		var param = getSearchParam();
		$("#myi_unsuppressedList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#myi_unsuppressedList").datagrid("reload");
	});
	
	$("#myi_unsuppressedReset").on('click',function(){
		$("#myi_unsuppressedSearchParam").form("clear");
	});
	
})(myi_unsuppressed,jQuery)