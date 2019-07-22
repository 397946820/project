var fpxStockHisManager = {};
(function(fpxStockHisManager,$){

	
/*	fpxStockManager.syncEADOrderInfo = function(fpxId){
		ocs.ajax({
			url:'/fpxManager/syncStockInfoBySKU/'+fpxId,
			async:true,
		    beforeSend: function () {
                   $.messager.progress({
                       title: '请稍后',
                       msg: '正在同步中...'
                   });
               },
           complete: function () {
               $.messager.progress('close');
           },
			type: "GET",
			success: function(result) {
				if(result.data){
					$("#fpxStockManagerList").datagrid('reload');
					$.messager.alert("信息","同步成功！");	
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//$.messager.alert("信息", "服务器发生错误！");		
			}
	   });
	};
	*/

	
	$("#fpxStockManagerList").datagrid({
		url : '/ocs/fourpx/stockListHis',
		queryParams : {
			param : {
				sku:'',
				warehouseName:'',
				dayTime :''
			}
		},
		columns : [ [
				
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'warehouseId',
					hidden:true
				},
				{
					field : 'busarea',
					title : '业务区域',
					align : 'center',
					width : 50
				},
				{
					field : 'sku',
					title : 'SKU',
					align : 'center',
					width : 100
				},
				{
					field : 'warehouseName',
					title : '仓库名称',
					align : 'center',
					width : 100
				},
				{
					field : 'qty',
					title : '可售数量',
					align : 'center',
					width : 100
				},
				{
					field : 'totalInventory',
					title : '实际库存',
					align : 'center',
					width : 100
				},
				{
					field : 'forOutboundInventory',
					title : '待出库存',
					align : 'center',
					width : 100
				},
				{
					field : 'createDate',
					title : '创建时间',
					align : 'center',
					width : 100
				},
				{
					field : 'updateDate',
					title : '最后更新时间',
					align : 'center',
					width : 100
				}/*,
				{
					field : 'opt',
					title : '操作',
					align : 'center',
					width : 70,
					formatter : function(value,row,index){
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="fpxStockManager.syncEADOrderInfo(\''+row.sku+'\')" data-options="plain:true">同步</a>';
					}
				}*/] ],
		idField : 'id',
		singleSelect : true,
		toolbar:"#fpxStockManagerSearchParam-panel",
		rownumbers : true,
		fitColumns : true,
		border : true,
		nowrap:false,
		fit: true,
		pagination : true,
		pageSize : 50
	});
	$("#fpxStockManagerSearch").click(function(){
		var formData = $("#fpxStockManagerSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#fpxStockManagerList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#fpxStockManagerList").datagrid("reload");
	});
	
	$("#fpxStockManagerReset").on('click',function(){
		$("#fpxStockManagerSearchParam").form("clear");
	});
})(fpxStockManager,jQuery)