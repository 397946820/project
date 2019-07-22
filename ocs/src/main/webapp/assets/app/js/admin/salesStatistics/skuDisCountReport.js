$(function(){
	$("#skuDisCountReportList").datagrid({
		url : '/ocs/productSaleReport/list_dis',
		queryParams : {
			param : {
				startTime:'',
				endTime:''
			}
		},
		columns : [ [
				
				{
					field : 'disFlag',
					title : '状态',
					align : 'center',
					width : 100
				},
				{
					field : 'site',
					title : '站点',
					align : 'center',
					width : 100
				},
				{
					field : 'account',
					title : '帐号',
					align : 'center',
					width : 100
				},
				
				{
					field : 'price',
					title : '金额',
					align : 'center',
					sortable : true,
					width : 100,
					sorter:function(a,b){
						return (a-0) > (b-0)?1:-1;
					}
				},
				{
					field : 'orderqty',
					title : '订单数量',
					align : 'center',
					width : 100
				},
				{
					field : 'saleqty',
					title : '产品数量',
					align : 'center',
					width : 100
				},
				{
					field : 'deduction',
					title : '折扣额',
					sortable : true,
					align : 'center',
					width : 100,
					sorter:function(a,b){
						return (a-0) > (b-0)?1:-1;
					}
				},
				{
					field : 'taxrate',
					title : '税额',
					sortable : true,
					align : 'center',
					width : 100,
					sorter:function(a,b){
						return (a-0) > (b-0)?1:-1;
					}
				},
				{
					field : 'currencycode',
					title : '币种',
					align : 'center',
					width : 100
				}] ],
		idField : 'id',
		singleSelect : true,
		rownumbers : true,
		fitColumns : true,
		border : true,
		nowrap:false,
		pagination : true,
		pageSize : 50,
		showFooter:true,
		remoteSort:false,
		onLoadSuccess : function(data) {
			$(".datagrid-ftable tbody tr").each(function(){
				this.style.backgroundColor="#E1EDFF";
				this.style.color="blue";
				this.style.fontWeight="bold";
			});
			return data;
		},
		toolbar : [ {
			text : '导出',
			iconCls : 'icon-redo',
			handler : function() {
				var formData = $("#skuDisCountSearchParam").serializeArray();
				var param = "?";
				var a = 1;
				$.each(formData,function(){
					if(a == 1){
						param = param+this.name+"="+this.value;
					}else{
						param = param+"&"+this.name+"="+this.value;
					}
					a++
				});
				window.location.href = "/ocs/excel/export/skuDisCountExport"+param;
			}
		}
		]
	});
	
	$("#skuDisCountSearch").click(function(){
		var formData = $("#skuDisCountSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#skuDisCountReportList").datagrid({
			queryParams:{
				param :param
			}
		});
	});
	
	$("#skuDisCountReset").on('click',function(){
		$("#skuDisCountSearchParam").form("clear");
	});
})
