var edaStockHisManager = {};
(function(edaStockHisManager,$){

	
/*	edaStockManager.syncEADOrderInfo = function(edaId){
		ocs.ajax({
			url:'/edaManager/syncStockInfoBySKU/'+edaId,
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
					$("#edaStockManagerList").datagrid('reload');
					$.messager.alert("信息","同步成功！");	
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//$.messager.alert("信息", "服务器发生错误！");		
			}
	   });
	};
	*/

	
	$("#edaStockManagerList").datagrid({
		url : '/ocs/edaManager/stockListHis',
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
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="edaStockManager.syncEADOrderInfo(\''+row.sku+'\')" data-options="plain:true">同步</a>';
					}
				}*/] ],
		idField : 'id',
		singleSelect : true,
		toolbar:"#edaStockManagerSearchParam-panel",
		rownumbers : true,
		fitColumns : true,
		border : true,
		nowrap:false,
		fit: true,
		pagination : true,
		pageSize : 50
	});
	$("#edaStockManagerSearch").click(function(){
		var formData = $("#edaStockManagerSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#edaStockManagerList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#edaStockManagerList").datagrid("reload");
	});
	
	$("#edaStockManagerReset").on('click',function(){
		$("#edaStockManagerSearchParam").form("clear");
	});
})(edaStockHisManager,jQuery)