var skuSaleCountReport={};
(function(skuSaleCountReport,$){
	$("#skuSaleCountReportList").datagrid({
		url : '/ocs/productSaleReport/list',
		queryParams : {
			param : {
				startTime:'',
				endTime:''
			}
		},
		columns : [ [
				
				{
					field : 'field',
					title : '分类',
					align : 'center',
					width : 100
				},
				{
					field : 'amazonQty',
					hidden:true
				},
				{
					field : 'amazonPrice',
					title : '亚马逊（$）',
					align : 'center',
					width : 100
				},
				{
					field : 'amazonPEC',
					title : '类目占比（亚马逊）',
					align : 'center',
					sortable : true,
					width : 100,
					sorter:function(a,b){
						var aNum = a.replace("%","");
						var bNum = b.replace("%","");
						return (aNum-0) > (bNum-0)?1:-1;
					}
				},
				{
					field : 'ebayQty',
					hidden:true
				},
				{
					field : 'ebayPrice',
					title : 'EBay（$）',
					align : 'center',
					width : 100
				},
				{
					field : 'ebayPEC',
					title : '类目占比（EBay）',
					sortable : true,
					align : 'center',
					width : 100,
					sorter:function(a,b){
						var aNum = a.replace("%","");
						var bNum = b.replace("%","");
						return (aNum-0) > (bNum-0)?1:-1;
					}
				},
				{
					field : 'lightQty',
					hidden:true
				},
				{
					field : 'lightPrice',
					title : '官网（$）',
					align : 'center',
					width : 100
				},
				{
					field : 'lightPEC',
					title : '类目占比（官网）',
					sortable : true,
					align : 'center',
					width : 100,
					sorter:function(a,b){
						var aNum = a.replace("%","");
						var bNum = b.replace("%","");
						return (aNum-0) > (bNum-0)?1:-1;
					}
				},
				{
					field : 'total',
					title : '合计',
					align : 'center',
					width : 100
				},
				{
					field : 'totalPEC',
					title : '类目占比（全平台）',
					sortable : true,
					align : 'center',
					width : 100,
					sorter:function(a,b){
						var aNum = a.replace("%","");
						var bNum = b.replace("%","");
						return (aNum-0) > (bNum-0)?1:-1;
					}
				}] ],
		idField : 'id',
		singleSelect : true,
		rownumbers : true,
		fitColumns : true,
		border : true,
		nowrap:false,
		remoteSort:false,
		onLoadSuccess : function(data) {
			return data;
		},
		toolbar : [ {
			text : '导出',
			iconCls : 'icon-redo',
			handler : function() {
				var formData = $("#skuSaleCountSearchParam").serializeArray();
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
				window.location.href = "/ocs/excel/export/skuSaleCountExport"+param;
			}
		}
		]
	});
	$("#skuSaleCountSearch").click(function(){
		var formData = $("#skuSaleCountSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#skuSaleCountReportList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#skuSaleCountReportList").datagrid("reload");
	});
	
	$("#skuSaleCountReset").on('click',function(){
		$("#skuSaleCountSearchParam").form("clear");
	});
})(skuSaleCountReport,jQuery)
