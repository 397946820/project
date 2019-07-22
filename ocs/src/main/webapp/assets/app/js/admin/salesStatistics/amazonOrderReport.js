var amazonOrderReport = {};
(function(amazonOrderReport,$){
	
	$("#amazonOrderReportList").datagrid({
		url : '/ocs/amazonReport/list',
		queryParams : {
			param : {
				marketplace:'',
				orderId:'',
				sku : '',
				orderType :'',
				startTime : '',
				endTime :''
			}
		},
		columns : [ [
				
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'platform',
					title : '国家站点',
					align : 'center',
					width : 60
				},
				{
					field : 'selfDate',
					title : '日期',
					align : 'center',
					width : 100
				},
				{
					field : 'type',
					title : 'type',
					align : 'center',
					width : 70
				},
				{
					field : 'orderId',
					title : 'orderId',
					align : 'center',
					width : 100
				},
				{
					field : 'sku',
					title : 'sku',
					align : 'center',
					width : 100
				},
				{
					field : 'description',
					title : 'description',
					align : 'center',
					width : 150
				},
				{
					field : 'marketplace',
					title : 'marketplace',
					align : 'center',
					width : 100
				},
				{
					field : 'quantity',
					title : 'quantity',
					align : 'center',
					width : 70
				},
				
				{
					field : 'productSales',
					title : 'productSales',
					align : 'center',
					width : 100
				},
				{
					field : 'shippingCredits',
					title : 'shippingCredits',
					align : 'center',
					width : 100
				},
				{
					field : 'giftWrapCredits',
					title : 'giftWrapCredits',
					align : 'center',
					width : 100
				},
				{
					field : 'promotionalRebates',
					title : 'promotionalRebates',
					align : 'center',
					width : 100
				},
				{
					field : 'salesTaxCollected',
					title : 'salesTaxCollected',
					align : 'center',
					width : 100
				},
				{
					field : 'sellingFees',
					title : 'sellingFees',
					align : 'center',
					width : 100
				},
				{
					field : 'fbaFees',
					title : 'fbaFees',
					align : 'center',
					width : 100
				},
				{
					field : 'otherTransactionFees',
					title : 'otherTransactionFees',
					align : 'center',
					width : 100
				},
				{
					field : 'other',
					title : 'other',
					align : 'center',
					width : 100
				},
				{
					field : 'total',
					title : 'total',
					align : 'center',
					width : 100
				},
				{
					field : 'fulfillment',
					title : 'fulfillment',
					align : 'center',
					width : 100
				},
				{
					field : 'orderCity',
					title : 'orderCity',
					align : 'center',
					width : 100
				},
				{
					field : 'orderState',
					title : 'orderState',
					align : 'center',
					width : 100
				},
				{
					field : 'orderPostal',
					title : 'orderPostal',
					align : 'center',
					width : 100
				},
				{
					field : 'settlementId',
					title : 'settlementId',
					align : 'center',
					width : 100
				},
				{
					field : 'reportId',
					title : 'reportId',
					align : 'center',
					width : 100
				}] ],
		singleSelect : true,
		rownumbers : true,
		//fitColumns : true,
		border : true,
		//nowrap:false,
		pagination : true,
		pageSize : 50,
		showFooter:true,
		onLoadSuccess:function(data){
			$(".datagrid-ftable tbody tr").each(function(){
			    this.style.backgroundColor="#E1EDFF";
			    this.style.color="blue";
			    this.style.fontWeight="bold";
			   })
		},
		toolbar : [ {
			text : '导出',
			iconCls : 'icon-redo',
			handler : function() {
				var downloadUrl = "/ocs/excel/export/amazonReportDataExport";
				var param = getSearchParam();
				if(param){
					var startTime = formartStringToDate(param["startTime"]) ;
					var endTime = formartStringToDate(param["endTime"]);
					if(startTime&&endTime&&(endTime.getTime()-startTime.getTime())<40*24*60*60*1000){
						downloadUrl = downloadUrl+"?startTime="+param["startTime"]+"&endTime="+param["endTime"];
						for(var key in param){
							if(key != "startTime"&&key != "endTime"){
								downloadUrl = downloadUrl+"&"+key+"="+param[key];
							}
						}
						window.location.href = downloadUrl;
					}else{
						$.messager.alert('提示','时间段过大,最多一次只能导出40天数据,请分段导出!','warning');
					}
				}else{
					$.messager.alert('提示','时间段过大,最多一次只能导出40天数据,请分段导出!','warning');
				}
			}
		}
		]
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
	function getSearchParam(){
		var formData = $("#amazonOrderReportSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	}
	$("#amazonOrderReportSearch").click(function(){
		var param = getSearchParam();
		$("#amazonOrderReportList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#amazonOrderReportList").datagrid("reload");
	});
	
	$("#amazonOrderReportReset").on('click',function(){
		$("#amazonOrderReportSearchParam").form("clear");
	});
	
	var curSiteSelect = "";
	$("#siteCombobox").combobox({
		onSelect : function(record){
			if(record.value != "" && record.value != curSiteSelect ){
				//重置分类
				$('#orderTypeCombobox').combobox({
				    url:'/ocs/amazonReport/getOrderTypeBySite?site='+record.value,
				    valueField:'value',
				    textField:'displayName'
				});
				curSiteSelect = record.value;
			}
		}
	});
})(amazonOrderReport,jQuery)