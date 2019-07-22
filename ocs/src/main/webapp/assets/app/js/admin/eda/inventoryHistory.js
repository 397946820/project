var inventoryHistory = {};
(function(inventoryHistory,$){

	$('#recordTypeCombobox').combobox({
	    url:'/ocs/edaManager/getRecordType',
	    valueField:'value',
	    textField:'displayName'
	});
	
	//导出
	$("#exportDataBtn").click(function(){
		var formData = $("#inventoryHistorySearchParam").serializeArray();
		var param = [];
		param.push("?");
		$.each(formData,function(){
			param.push(this.name+"="+this.value+"&");
		});
		window.location.href = "/ocs/excel/export/inventoryHistoryExport"+param.join("");
	});
	
	$("#inventoryHistoryList").datagrid({
		url : '/ocs/edaManager/inventoryHistoryList',
		queryParams : {
			param : {
				sku:'',
				timeStart:'',
				timeEnd : '',
				billNum :'',
				recordType:'',
				warehouseId:''
			}
		},
		columns : [ [
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'st',
					title : '出入库',
					align : 'center',
					width : 60,
					formatter : function(value,row,index){
						if(row.qty > 0){
							return "入库";
						}else{
							return "出库";
						}
					}
				},
				{
					field : 'recordType',
					title : '流水类型',
					align : 'center',
					width : 100
				},
				{
					field : 'warehouseId',
					title : '仓库',
					align : 'center',
					width : 100,
					formatter : function(value,row,index){
						if(value == 2){
							return "Los Angeles Warehouse";
						}else if(value == 7){
							return "US New Jersey Warehouse";
						}else{
							return value;
						}
					}
				},
				{
					field : 'sku',
					title : 'SKU',
					align : 'center',
					width : 100
				},		
				{
					field : 'productName',
					title : '产品名称',
					align : 'center',
					width : 100
				},
				{
					field : 'qty',
					title : '数量',
					align : 'center',
					width : 60
				},
				{
					field : 'billNum',
					title : 'EDA BillNum',
					align : 'center',
					width : 100
				},
				{
					field : 'orderId',
					title : '平台订单id',
					align : 'center',
					width : 150
				},
				{
					field : 'edaOrderCreateDate',
					title : 'EDA发货单创建时间',
					align : 'center',
					width : 100
				},
				
				{
					field : 'changeDate',
					title : '流水产生时间',
					align : 'center',
					width : 100
				}] ],
		idField : 'id',
		singleSelect : true,
		toolbar:"#inventoryHistorySearchParam-panel",
		rownumbers : true,
		fitColumns : true,
		border : true,
		nowrap:false,
		fit: true,
		pagination : true,
		pageSize : 50,
		showFooter:true,
		onLoadSuccess:function(data){
			$(".datagrid-ftable tbody tr").each(function(){
			    this.style.backgroundColor="#E1EDFF";
			    this.style.color="blue";
			    this.style.fontWeight="bold";
			   })
		}
	});
	$("#inventoryHistorySearch").click(function(){
		var formData = $("#inventoryHistorySearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#inventoryHistoryList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#inventoryHistoryList").datagrid("reload");
	});
	
	$("#inventoryHistoryReset").on('click',function(){
		$("#inventoryHistorySearchParam").form("clear");
	});
})(inventoryHistory,jQuery)