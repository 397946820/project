var walmartsaleOrder = {};
(function(walmartsaleOrder, $) {
	var editIndex = undefined;
	var editIndex_ = undefined;
	$("#walmartSaleOrderdatagrid").datagrid({
		url : GLOBAL.domain + '/walmartSaleOrder/findAll',
		columns : [ [
				/*{
					field : 'id',
					checkbox : true
				},*/
				{
					field : 'customerOrderId',
					title : '订单号',
					align : 'center',
					sortable : true
				},
				{
					field : 'methodCode',
					title : '运输方式',
					align : 'center',
					sortable : true
				},
				{
					field : 'orderDateUtc',
					title : '订单创建日期(UTC)',
					align : 'center',
					sortable : true,
					formatter : function(value,row,index){
						if(value != '' && value != null) {
							return new Date(value).format("yyyy-MM-dd hh:mm:ss");
						}
						return '';
					}
				},
				{
					field : 'name',
					title : '买家姓名',
					align : 'center',
					sortable : true
				},
				{
					field : 'country',
					title : '国家',
					align : 'center',
					sortable : true
				},
				{
					field : 'orderLineTotal',
					title : '明细数量',
					align : 'center',
					sortable : true
				},
				{
					field : 'estimatedDeliveryDateUtc',
					title : '预计交货日期(UTC)',
					align : 'center',
					sortable : true,
					formatter : function(value,row,index){
						if(value != '' && value != null) {
							return new Date(value).format("yyyy-MM-dd hh:mm:ss");
						}
						return '';
					}
				},
				{
					field : 'v',
					title : '动作',
					align : 'center',
					formatter : function(value, row, index) {
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="walmartsaleOrder.detail('
								+ index + 
								')" data-options="plain:true">详情</a> &nbsp;&nbsp;'
								+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="walmartsaleOrder.updateSale('
								+ row.purchaseOrderId +
								')"  data-options="plain:true">更新订单</a> &nbsp;&nbsp;'
								/*+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="walmartsaleOrder.refundOrder('
								+ row.id +
								')"  data-options="plain:true">退款</a>&nbsp;&nbsp;'*/
								+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="walmartsaleOrder.createRefundOrReissueOrder(\''
								+ index+'\',\'refund\')" data-options="plain:true">退款</a> &nbsp;&nbsp;'
								+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="walmartsaleOrder.createRefundOrReissueOrder(\''
								+ index+'\',\'reissue\')" data-options="plain:true">复制订单</a> &nbsp;&nbsp;';
					}
				} ] ],
		singleSelect : true,
		selectOnCheck : true,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		pageSize : 50,
		border : true,
		toolbar : "#walmartSaleOrderToolbar",
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        }
	});
	
	walmartsaleOrder.detail = function(index){
		$("#walmartSaleOrderdatagrid").datagrid('selectRow',index);
		var row = $("#walmartSaleOrderdatagrid").datagrid('getSelected');
		$("#name").text(row.name);
		$("#address1").text(row.address1);
		$("#address2").text(row.address2);
		$("#city").text(row.city);
		$("#state").text(row.state);
		$("#postalCode").text(row.postalCode);
		$("#phone").text(row.phone);
		
		$("#walmartSaleOrderDetailDatagrid").datagrid({
			url : GLOBAL.domain + '/walmartSaleOrder/getWalmartOrderLineById/'+ row.id,
			columns : [ [
				{
					field : 'productName',
					title : '物品标题',
					align : 'center',
					width : 150
				},
				{
					field : 'sku',
					title : 'SKU',
					align : 'center',
					width : 105
				},
				{
					field : 'itemPrice',
					title : '价格',
					align : 'center',
					width : 65
				},
				{
					field : 'shippingPrice',
					title : '运费',
					align : 'center',
					width : 45
				},
				{
					field : 'orderLineQuantityAmount',
					title : '数量',
					align : 'center',
					width : 45
				},
				{
					field : 'shipCarrier',
					title : '承运商',
					align : 'center',
					width : 60
				},
				{
					field : 'shipTrackingNumber',
					title : '跟踪号',
					align : 'center',
					width : 180
				},
				{
					field : 'SHIPTIME',
					title : '发货日期',
					align : 'center',
					width : 100
				},
				{
					field : 'total',
					title : '合计',
					align : 'center',
					width : 50,
					formatter : function(value,row,index){
						return (row.itemPrice * row.orderLineQuantityAmount + (row.shippingPrice == null ? 0 : row.shippingPrice)).toFixed(2);
					}
				},
				{
					field : 'opt',
					title : '操作',
					align : 'center',
					width : 145,
					formatter : function(value,row,index){ 
						return "<a href='javascript:void(0);' class='easyui-linkbutton' onclick=\"walmartsaleOrder.cancelOrder('"+row.id+"','"+row.orderLineStatus+"')\" data-options='lain:true'>取消交易</a> &nbsp;&nbsp; " +
							   "<a href='javascript:void(0);' class='easyui-linkbutton' onClick=\"walmartsaleOrder.uploadSaleTranNumber('"+row.id+"','"+row.orderLineStatus+"')\" data-options='plain:true'>上传跟踪号</a>";
					}
				}
				] ],
			idField : 'id',
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			fit : true,
			pageSize : 5,
			border : true,
			onLoadSuccess : function(data) {
				if(data){
					$("#t_total").text(data.source);
				}
			}
		});
		
		$("#walmartSaleOrderDetial").dialog("open");
	};
	
	walmartsaleOrder.updateSale = function(purchaseOrderId) {
		ocs.ajax({
			url:'/walmartSaleOrder/updateOrder/'+purchaseOrderId,
			async:true,
		    beforeSend: function () {
                   $.messager.progress({
                       title: '请稍后',
                       msg: '正在更新中...'
                   });
               },
            complete: function () {
               $.messager.progress('close');
            },
			type: "GET",
			success: function(result) {
				if(result.errorCode == 0){
					$("#walmartSaleOrderdatagrid").datagrid('reload');
					$.messager.alert("信息","更新成功！");	
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				$.messager.alert("信息", "服务器发生错误！");		
			}
	   });
	};
	
	walmartsaleOrder.uploadSaleTranNumber = function(id,orderLineStatus) {
		$.messager.confirm('提示', "您确定要上传跟踪号吗？", function(r){
			if(r) {
				if(orderLineStatus == 'Created') {
					$("#walmartSaleOrderSaleTranNumberDatagrid").datagrid({
						url : GLOBAL.domain + '/walmartSaleOrder/getById/'+ id,
						columns : [ [
							{
								field : 'lineNumber',
								title : '订单行',
								align : 'center',
								width : 100
							},
							{
								field : 'sku',
								title : 'SKU',
								align : 'center',
								width : 120
							},
							{
								field : 'orderLineQuantityAmount',
								title : '数量',
								align : 'center',
								width : 60
							},
							{
								field : 'shipTrackingNumber_',
								title : '跟踪号',
								align : 'center',
								width : 200,
								editor:'text'
							}] ],
						idField : 'id',
						singleSelect : true,
						fit : true,
						border : true,
						onSelect:function(index,row){
							if(walmartsaleOrder.editIndex_ != undefined) {
								$(this).datagrid('endEdit', walmartsaleOrder.editIndex_);
							}
							$(this).datagrid('beginEdit', index);
							walmartsaleOrder.editIndex_ = index;
						},
						onLoadSuccess : function(data) {
							$(this).datagrid("clearSelections");
				        }
					});
					$("#walmartSaleOrderSaleTranNumber").dialog({
						buttons:[{
							text:'确定',
							handler:function(){
								$("#walmartSaleOrderSaleTranNumberDatagrid").datagrid('endEdit', walmartsaleOrder.editIndex_);
								var rows = $("#walmartSaleOrderSaleTranNumberDatagrid").datagrid('getRows')[0];
								var shipTrackingNumber_ = rows.shipTrackingNumber_;
								if(shipTrackingNumber_ == '' || shipTrackingNumber_ == undefined) {
									$.messager.alert("信息","请填写跟踪号!","warning");
									return;
								}
								ocs.ajax({
									url:'/walmartSaleOrder/uploadWalmartSaleTranNumber',
									async:true,
									data : {"param" : rows},
									beforeSend: function () {
										$.messager.progress({
											title: '请稍后',
											msg: '正在上传跟踪号...'
										});
									},
									complete: function () {
										$.messager.progress('close');
									},
									type: "POST",
									success: function(result) {
										if(result.errorCode == 0){
											$("#walmartSaleOrderDetailDatagrid").datagrid('reload');
											$.messager.alert("信息","上传成功！");	
										}
									}
								});
							}
						},{
							text:'取消',
							handler:function(){
								$("#walmartSaleOrderSaleTranNumber").dialog("close");
							}
						}]
					});
					$("#walmartSaleOrderSaleTranNumber").dialog("open");
				} else {
					$.messager.alert("信息","该订单行无法上传跟踪号","warning");
					return;
				}
			}
		});
		
	};
	
	walmartsaleOrder.cancelOrder = function(id,orderLineStatus) {
		$.messager.confirm('提示', "您确定取消订单吗？", function(r){
			if (r){
				if(orderLineStatus == 'Created') {
					$("#walmartSaleOrderCancleOrder").dialog({
						buttons:[{
							text:'确定',
							handler:function(){
								var orderCase = $("#cancelOrderCase").combobox("getValue");
								ocs.ajax({
									url:'/walmartSaleOrder/cancelOrder/' + id + '/' + orderCase,
									async:true,
									beforeSend: function () {
										$.messager.progress({
											title: '请稍后',
											msg: '正在取消中...'
										});
									},
									complete: function () {
										$.messager.progress('close');
									},
									type: "POST",
									success: function(result) {
										if(result.errorCode == 0){
											$("#walmartSaleOrderdatagrid").datagrid('reload');
											$.messager.alert("信息","取消成功！");	
										}
									}
								});
							}
						},{
							text:'取消',
							handler:function(){
								$("#walmartSaleOrderCancleOrder").dialog("close");
							}
						}]
					});
					$("#walmartSaleOrderCancleOrder").dialog("open");
				} else {
					$.messager.alert("信息","该订单行无法取消","warning");
					return;
				}
			}
		});
	}
	
	walmartsaleOrder.createRefundOrReissueOrder = function(index,value) {
		var message = '';
		if(value == 'refund') {
			message = '您确定要创建退款单吗？';
		} else if(value == 'reissue') {
			message = '您确定要创建补发单吗？';
		}
		$.messager.confirm('提示', message, function(r){
			if(r) {
				$("#walmartSaleOrderdatagrid").datagrid('selectRow',index);
				var row = $("#walmartSaleOrderdatagrid").datagrid('getSelected');
				ocs.ajax({
					url:'/walmartSaleOrder/getSaleOrderRefundByParentId',
					async:true,
					type: "POST",
					data:{id:row.id,platform:'walmart',account:'US',orderId:row.customerOrderId},
					success: function(result) {
						var $row = {
							platform : 'walmart',
							account : row.country,
							site : '',
							orderId : row.purchaseOrderId,
							currencyCode : result.rows[0].itemPriceCurrency,
							items : result.rows,
							edaOrderNum : result.edaOrderNum,
							shipCost : 0
						};
						if(value == 'refund') {
							saleOrderRefund.saleOrderRefund($row,'','');
						} else if(value == 'reissue') {
							saleOrderRefund.saleOrderReissue($row,'','');
						}
					}
				});
			}
		});
	};
	
	walmartsaleOrder.refundOrder = function(id) {
		$.messager.confirm('提示', "您确定退款吗？", function(r){
			if (r){
				ocs.ajax({
					url:'/walmartSaleOrder/isExist/' + id,
					async:true,
					type: "POST",
					success: function(result) {
						if(result == true) {
							$("#walmartSaleOrderDetialRefundDatagrid").datagrid({
								url : GLOBAL.domain + '/walmartSaleOrder/getExistWalmartOrderLineById/'+ id,
								columns : [ [
									{
										field : 'id',
										checkbox : true
									},
									{
										field : 'lineNumber',
										title : '订单行',
										align : 'center',
										width : 45
									},
									{
										field : 'sku',
										title : 'SKU',
										align : 'center',
										width : 180
									},
									{
										field : 'orderLineQuantityAmount',
										title : '数量',
										align : 'center',
										width : 60
									},
									{
										field : 'itemPrice',
										title : '价格',
										align : 'center',
										width : 100
									},
									{
										field : 'shippingPrice',
										title : '运费',
										align : 'center',
										width : 100
									},
									{
										field : 'itemPrice_',
										title : '退款金额',
										align : 'center',
										width : 100,
										editor:'text'
									},
									{
										field : 'shippingPrice_',
										title : '退款运费额',
										align : 'center',
										width : 100,
										editor:'text'
									},
									{
										field : 'refundReason_',
										title : '退款理由',
										align : 'center',
										width : 100,
										editor:'text'
									}] ],
								idField : 'id',
								singleSelect : false,
								pagination : true,
								fit : true,
								pageSize : 10,
								border : true,
								onSelect:function(index,row){
									if(walmartsaleOrder.editIndex != undefined) {
										$(this).datagrid('endEdit', walmartsaleOrder.editIndex);
									}
									$(this).datagrid('beginEdit', index);
									walmartsaleOrder.editIndex = index;
								},
								onLoadSuccess : function(data) {
									$(this).datagrid("clearSelections");
						        }
							});
							$("#walmartSaleOrderDetialRefund").dialog({
								buttons:[{
									text:'确定',
									handler:function(){
										$("#walmartSaleOrderDetialRefundDatagrid").datagrid('endEdit', walmartsaleOrder.editIndex);
										var rows = $("#walmartSaleOrderDetialRefundDatagrid").datagrid('getSelections');
										if(rows.length == 0) {
											$.messager.alert("信息","请选择要退款的行","warning");
											return;
										}
										for (var i = 0; i < rows.length; i++) {
											var row = rows[i];
											if(row.itemPrice_ == null || row.itemPrice_ == "") {
												$.messager.alert("信息","第" + (i+1) + "行的退款金额为空","warning");
												return;
											}
											if(row.itemPrice_ > 0 || row.itemPrice < Math.abs(row.itemPrice_)) {
												$.messager.alert("信息","第" + (i+1) + "行的退款金额大于0或者退款金额大于价格","warning");
												return;
											}
											if(row.shippingPrice == null && row.shippingPrice_ != '') {
												$.messager.alert("信息","第" + (i+1) + "行的退款运费应为空","warning");
												return;
											} else if(row.shippingPrice != null  && row.shippingPrice_ != ''
													&& (row.shippingPrice < Math.abs(row.shippingPrice_) || row.shippingPrice_ > 0)) {
												$.messager.alert("信息","第" + (i+1) + "行的退款运费大于0或者退款运费大于运费","warning");
												return;
											}
										}
										ocs.ajax({
											url : '/walmartSaleOrder/refundOrder',
											data : {"param" : rows},
											type: "POST",
											async:true,
											beforeSend: function () {
												$.messager.progress({
													title: '请稍后',
													msg: '正在退款中...'
												});
											},
											complete: function () {
												$.messager.progress('close');
											},
											success: function(result) {
												if(result.errorCode == 0){
													$("#walmartSaleOrderDetialRefundDatagrid").datagrid('reload');
													$.messager.alert("信息","退款成功！");	
												}
											}
										});
									}
								},{
									text:'取消',
									handler:function(){
										$("#walmartSaleOrderDetialRefund").dialog("close");
									}
								}]
							});
							$("#walmartSaleOrderDetialRefund").dialog("open");
						} else {
							$.messager.alert("信息","该订单没有可退款的订单行!","warning");
							return;
						}
					}
				});
			}
		});
	}
	
	$(".con-button li").click(function(){
		$(this).find("input").prop("checked","checked");
		$(this).css( "background",'#ccc').siblings().css( "background",'#fff');
		$(this).siblings().find("input").prop("checked",false);
		var count = $(this).find("label").find("span").html();
		if(count == '(0)') {
			$("#walmartSaleOrderdatagrid").datagrid('loadData', { total: 0, rows: [], footer:[] }); 
		} else {
			$("#walmartSaleOrderdatagrid").datagrid("load",{
				param : walmartsaleOrder.searchParam()
			});
		}
		
	});
	
	walmartsaleOrder.searchParam = function() {
		var param = {};
		var formData = $("#walmartSaleOrderSearchForm").serializeArray();
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	}
	
	walmartsaleOrder.getCount = function() {
		$(".con-button li").each(function(){
			var this_ = this;
			var param = walmartsaleOrder.searchParam();
			param['orderAllStatus'] = $(this).find("input").val();
			ocs.ajax({
				type:"POST",
				data : param,
				url:"/walmartSaleOrder/countOrderByStatus",
				success:function(data){
					$(this_).find("span").html("("+data+")");
				}
			});
		});
	}
	
	walmartsaleOrder.getCount();
	
	$("#walmartSaleOrderQuery").click(function(){
		$('#walmartSaleOrderdatagrid').datagrid('load',{
			param : walmartsaleOrder.searchParam()
		});
		walmartsaleOrder.getCount();
	});
	
	$("#walmartSaleOrderReset").on('click',function(){
		$("#walmartSaleOrderSearchForm").form("clear");
		$(".con-button li").each(function(){
			var value = $(this).find("input").val();
			if(value == 0) {
				$(this).find("input").prop("checked","checked");
				$(this).css( "background",'#ccc').siblings().css( "background",'#fff');
			} else {
				$(this).siblings().find("input").prop("checked",false);
			}
		});
	});

})(walmartsaleOrder, jQuery)