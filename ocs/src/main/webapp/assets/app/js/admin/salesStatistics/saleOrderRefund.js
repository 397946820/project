var saleOrderRefund = {};
(function(saleOrderRefund, $) {
	var editIndex_ = undefined;
	var editIndex = undefined;
	

	$("#applyBackGoodsDatagrid").datagrid({
		url : '', 
		frozenColumns:[[
			{
				field : 'entityId',
				title : '退货行号',
				hidden : false
			},
			{
				field : 'rmaSeq',
				title : 'RMA',
				hidden : false
			},
			{
				field : 'sku',
				title : '退货sku',
				align : 'center',
				width : 120
			},
			{
				field : 'platform',
				title : '国家',
				align : 'center',
				width : 95 
			},
 
			{
				field : 'totalQtyOrdered',
				title : '数量',
				align : 'center',
				width : 50
			}
		]],
		columns : [ [
			{
				field : 'orderId',
				title : '订单号',
				align : 'center',
				width : 60,
				hidden : false
			},
			{
				field : 'cause',
				title : '退货原因',
				align : 'center',
				width : 80
			},
			 {
				field : 'trackingNum',
				title : '运单号',
				align : 'center',
				width : 80
			}, {
				field : 'createdAt',
				title : '创建日期(UTC)',
				align : 'center',
				sortable : true,
				formatter : function(value,row,index){
					if(value != '' && value != null) {
						return new Date(value).format("yyyy-MM-dd hh:mm:ss");
					}
					return '';
				}
			} ] ],
		singleSelect : false,
		rownumbers : true,
		fitColumns : true,
		border : true,
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        },
        onSelect:function(index,row){
        	if($(this).datagrid('validateRow', saleOrderRefund.editIndex)) {
        		$(this).datagrid('endEdit', saleOrderRefund.editIndex);
        		$(this).datagrid('beginEdit', index);
        		saleOrderRefund.editIndex = index;
        	}
		},
	});
	
	var backGoodEditIndex = undefined;
	
	$("#saleOrderBackGoodsDatagrid").datagrid({
		url : '', 
		frozenColumns:[[
			{
				field : 'entityId',
				title : '行号',
				hidden : false
			},
			{
				field : 'orderId',
				title : '订单号',
				align : 'center',
				width : 90,
				hidden : false
			},
			{
				field : 'sku',
				title : 'SKU',
				align : 'center',
				width : 120
			},
			{
				field : 'platform',
				title : '国家',
				align : 'center',
				width : 60 
			},
			{
				field : 'totalQtyOrdered',
				title : '数量',
				align : 'center',
				width : 60
			}
		]],
		columns : [ [
			{
				field : 'retqty',
				title : '退货数量（可编辑）',
				align : 'right',
				width : 60
			},
			{
				field : 'name',
				title : '客户',
				align : 'center',
				width : 80
			},
			 {
				field : 'address',
				title : '地址',
				align : 'center',
				width : 180
			},
			{
				field : 'telephone',
				title : '联系方式',
				align : 'center',
				width : 80
			} ] ],
		singleSelect : false,
		rownumbers : true,
		fitColumns : true,
		border : true,
		toolbar : "#saleOrderBackGoodsToolbar",
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        },
        onSelect:function(index,row){
        	if($(this).datagrid('validateRow', backGoodEditIndex)) {
        		$(this).datagrid('endEdit', backGoodEditIndex);
        		$(this).datagrid('beginEdit', index);
        		backGoodEditIndex = index;
        	}
		},
		loadFilter: function(data) {
			var l = data.rows || data.rows.length || 0;
			for(var i = 0; i < l; i++) {
				data.rows[i].retqty = 0;
			}
			return data;
		}
	});
	
	$("#saleOrderRefundDatagrid").datagrid({
		url : '', 
		frozenColumns:[[
			{
				field : 'entityId',
				title : 'entityId',
				hidden : true
			},
			{
				field : 'sku',
				title : 'SKU',
				align : 'center',
				width : 120
			},
			{
				field : 'asin',
				title : 'ASIN',
				align : 'center',
				width : 95,
				hidden : true,
				formatter : function(value,row,index){
					if(typeof(row.url) && row.url) {
						return "<a href='" + row.url + "' target='_blank'>" + value + "</a>";
					}
					return value;
				}
			},
			{
				field : 'lineNumber',
				title : '订单行',
				align : 'center',
				width : 45,
				hidden : true
			},
			{
				field : 'qtyOrdered',
				title : '数量',
				align : 'center',
				width : 50
			},
			{
				field : 'rowTotal',
				title : '总额',
				align : 'center',
				width : 80
			}
		]],
		columns : [ [
			{
				field : 'qty',
				title : '退货数量',
				align : 'center',
				width : 50
			},
			{
				field : 'returnCost',
				title : '退款总额',
				align : 'center',
				width : 60
			},
			{
				field : 'cancellationType',
				title : '取消类型',
				align : 'center',
				width : 120,
				hidden : true
			},
			{
				field : 'badProductQty',
				title : '有问题产品数量',
				align : 'center',
				width : 80
			},
			{
				field : 'productCaseType',
				title : '问题类型',
				align : 'center',
				width : 90
			},
			/*{
				field : 'remarks',
				title : '备注',
				align : 'center',
				width : 80,
			},*/
			{
				field : 'inventoryQuantity',
				title : '入库数量',
				align : 'center',
				width : 60,
				hidden : true
			}
			] ],
		singleSelect : true,
		rownumbers : true,
		fitColumns : true,
		border : true,
		toolbar : "#saleOrderRefundToolbar",
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        },
        onSelect:function(index,row){
        	if($(this).datagrid('validateRow', saleOrderRefund.editIndex_)) {
        		$(this).datagrid('endEdit', saleOrderRefund.editIndex_);
        		$(this).datagrid('beginEdit', index);
        		saleOrderRefund.editIndex_ = index;
        	}
		},
	});
	
	saleOrderRefund.saleOrderRefund = function(rowData,flag,value) {
		$("#saleOrderRefund").dialog({
			onClose:function(){  
				$("#saleOrderRefundDatagrid").datagrid("rejectChanges");
				rowData.items = $("#saleOrderRefundDatagrid").datagrid('getRows'); 
		    }, 
			buttons:[{
				id : 'orderRefund_',
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					if($("#saleOrderRefundDatagrid").datagrid('validateRow', saleOrderRefund.editIndex_)) {
						$("#saleOrderRefundDatagrid").datagrid('endEdit', saleOrderRefund.editIndex_);
					} else {
						return;
					}
					var rows_ = [];
					var rows = $("#saleOrderRefundDatagrid").datagrid('getRows');
					var url = '';
					if(value == '') {
						if($("#cause_").textbox('getValue') == ''
							|| $("#isConsumerPaid_").combobox('getValue') == '') {
							return;
						}
						if(rowData.platform == 'light' && $("#adjustmentPositive_").textbox('getValue') == '') {
							return;
						}
						if(flag == '') {
							$.each(rows,function(){
								if(this.sku && this.qty != '' && this.productCaseType != null && this.productCaseType != '') {
									if(rowData.platform == 'light') {
										rows_.push(this);
									} else {
										if(this.returnCost != '' ) {
											rows_.push(this);
										}
									}
								}
							});
						} else {
							for (var i = 0; i < rows.length; i++) {
								var row_ = rows[i];
								if(rowData.platform == 'light') {
									if(!row_.sku || row_.qty == '' || row_.productCaseType == null || row_.productCaseType == '') {
										$.messager.alert("信息","请填写退款行!");
										return;
									}
								} else {
									if(!row_.sku || row_.qty == '' || row_.productCaseType == null || row_.productCaseType == '' || 
											row_.returnCost == '' || row_.returnCost == null) {
										$.messager.alert("信息","请填写退款行!");
										return;
									}
								}
							}
							rows_ = rows;
						}
						if(rows_.length == 0) {
							$.messager.alert("信息","请填写退款行!");
							return;
						}
						url = '/returnOrder/saveEditReturnOrder/0';
					} else {
						for (var i = 0; i < rows.length; i++) {
							var row_ = rows[i];
							if(row_.inventoryQuantity != row_.qty) {
								$.messager.alert("信息","第" + (i + 1) +"行入库数量跟退货数量不等!");
								return;
							}
						}
						rows_ = rows;
						url = '/returnOrder/orderRefund';
					}
					var param = {};
					if(rowData.platform == 'ebay') {
						param = {
								platform : rowData.platform,
								account	: rowData.account,
								orderId : rowData.orderId,
								currencyCode : rowData.currencyCode,
								site : rowData.site,
								id : $("#id_").textbox('getValue'),
								cause : $("#cause_").combobox('getValue'),
								trackingNum : $("#trackingNum_").textbox('getValue'),
								isConsumerPaid : $("#isConsumerPaid_").combobox('getValue'),
								adjustmentPositive : $("#adjustmentPositive_").textbox('getValue'),
								edaOrderNum : $("#edaOrderNum_").textbox('getValue'),
								receiptNo : $("#receiptNo_").textbox('getValue'),
								receivingTime : $("#receivingTime_").datetimebox('getValue'),
								shipCost : $("#shipCost").text()
						};
					} else {
						param = {
								platform : rowData.platform,
								account	: rowData.account,
								orderId : rowData.orderId,
								currencyCode : rowData.currencyCode,
								site : rowData.site,
								id : $("#id_").textbox('getValue'),
								cause : $("#cause_").combobox('getValue'),
								trackingNum : $("#trackingNum_").textbox('getValue'),
								isConsumerPaid : $("#isConsumerPaid_").combobox('getValue'),
								adjustmentPositive : $("#adjustmentPositive_").textbox('getValue'),
								edaOrderNum : $("#edaOrderNum_").textbox('getValue'),
								receiptNo : $("#receiptNo_").textbox('getValue'),
								receivingTime : $("#receivingTime_").datetimebox('getValue'),
								shipCost : $("#shipCost").text(),
								payMethod:$("#payMethod").combobox('getValue')
						};
					}
					
					ocs.ajax({
						url:url,
						async:true,
						data : {"order":param,"items":rows_},
						beforeSend: function () {
							$.messager.progress({
								title: '请稍后',
								msg: '正在操作中...'
							});
						},
						complete: function () {
							$.messager.progress('close');
						},
						type: "POST",
						success: function(result) {
							if(result.errorCode != 1){
								if(result.errorCode == 0) {
									$.messager.alert("信息","成功！");	
								} else if(result.errorCode == 2) {
									$.messager.alert("信息","因该订单可退款时间超出180天,请通过其他渠道退款！");
								}
								$("#saleOrderRefund").dialog("close");
								if(flag != '') {
									$(flag).datagrid('reload');
								}
							} else {
								$.messager.alert("信息","失败！");	
							} 
						}
					});
				}
			},{
				text:'取消',
				iconCls:"icon-no",
				handler:function(){
					$("#saleOrderRefundDatagrid").datagrid("rejectChanges");
					rowData.items = $("#saleOrderRefundDatagrid").datagrid('getRows'); 
					$("#saleOrderRefund").dialog("close");
				}
			}]
		});
		if(value != 'check') {
			$("#orderRefund_").show();
			saleOrderRefund.showOrHide_(new Array('approveDescription1','approveDescription2'),'hide');
			if(value == '') { 
				$("#saleOrderRefundDatagrid").datagrid('showColumn', 'productCaseType');
				$("#saleOrderRefundDatagrid").datagrid('hideColumn', 'inventoryQuantity');
				$("#saleOrderRefundDatagrid").datagrid('addEditor', {    
					field : 'sku',    
					editor : { type: 'textbox'} 
				});
				$("#saleOrderRefundDatagrid").datagrid('addEditor', {    
	                field : 'qty',    
	                editor : { type: 'numberbox'/*, options: {min:0,precision:0,validType:'isGreaterQtyOrder'}*/ } 
	            });
				if(rowData.platform == 'amazon') {
				     $("#saleOrderRefundDatagrid").datagrid('addEditor', {    
				      field : 'returnCost',    
				      editor : { type: 'numberbox', options: {min:0,precision:4} }
				     });
				} else {
				     $("#saleOrderRefundDatagrid").datagrid('addEditor', {    
				      field : 'returnCost',    
				      editor : { type: 'numberbox', options: {min:0,precision:4,validType:'isGreaterRowTotal'} }
				     });
				}
				$("#saleOrderRefundDatagrid").datagrid('addEditor', {    
					field : 'badProductQty',    
					editor : { type: 'numberbox'/*, options: {min:0,precision:0,validType:'isGreaterQtyOrder'}*/ }
				});
				$("#saleOrderRefundDatagrid").datagrid('addEditor', {    
					field : 'productCaseType',    
					editor :  { type: 'combobox', 
						options: {
							panelHeight: 'auto',
							panelMaxHeight: '120',
							valueField:'PRODUCTCASETYPE',
							textField:'PRODUCTCASETYPENAME',
							url: GLOBAL.domain + '/returnOrder/getReturnOrderCause/0'
						} 
					}
				});
				/*$("#saleOrderRefundDatagrid").datagrid('addEditor', {    
					field : 'remarks',    
					editor : { type: 'textbox'}
				});*/
				$("#saleOrderRefundDatagrid").datagrid('removeEditor',new Array('inventoryQuantity'));
				saleOrderRefund.showOrHide_(new Array('receiptNo1','receiptNo2','receivingTime1',
						'receivingTime2','trackingNum1','trackingNum2'),'hide');
				saleOrderRefund.showOrHide(new Array('isConsumerPaid_','cause_'),'combobox',false);
				saleOrderRefund.showOrHide(new Array('edaOrderNum_','adjustmentPositive_'),'textbox',false);
			} else {
				$("#saleOrderRefundDatagrid").datagrid('showColumn', 'inventoryQuantity');
				$("#saleOrderRefundDatagrid").datagrid('hideColumn', 'productCaseType');
				$("#saleOrderRefundDatagrid").datagrid('removeEditor',new Array('qty','returnCost','badProductQty','productCaseType'/*,'remarks'*/));
				$("#saleOrderRefundDatagrid").datagrid('addEditor',{
					field : 'inventoryQuantity',
					editor:{ type: 'numberbox', options: {min:0,precision:0,required:true,validType:'isGreaterQty'}}
				});
				saleOrderRefund.showOrHide_(new Array('receiptNo1','receiptNo2','receivingTime1',
						'receivingTime2','trackingNum1','trackingNum2'),'show');
				saleOrderRefund.showOrHide(new Array('isConsumerPaid_','cause_'),'combobox',true);
				saleOrderRefund.showOrHide(new Array('edaOrderNum_','adjustmentPositive_'),'textbox',true);
			}
			if(rowData.edaOrderNum) {
				saleOrderRefund.showOrHide(new Array('edaOrderNum_'),'textbox',true);
			} else {
				saleOrderRefund.showOrHide(new Array('edaOrderNum_'),'textbox',false);
			}
		} else {
			$("#orderRefund_").hide();
			$("#saleOrderRefundDatagrid").datagrid('showColumn', 'productCaseType');
			$("#saleOrderRefundDatagrid").datagrid('hideColumn', 'inventoryQuantity');
			$("#saleOrderRefundDatagrid").datagrid('removeEditor',new Array('qty','returnCost','badProductQty',
						'productCaseType'/*,'remarks'*/,'inventoryQuantity'));
			saleOrderRefund.showOrHide_(new Array('receiptNo1','receiptNo2','receivingTime1',
					'receivingTime2','trackingNum1','trackingNum2'),'hide');
			saleOrderRefund.showOrHide_(new Array('approveDescription1','approveDescription2'),'show');
			saleOrderRefund.showOrHide(new Array('isConsumerPaid_','cause_'),'combobox',true);
			saleOrderRefund.showOrHide(new Array('edaOrderNum_','adjustmentPositive_','approveDescription_'),'textbox',true);
		}
		saleOrderRefund.init(rowData,'#saleOrderRefundDatagrid','refund');
		$("#saleOrderRefundBeforeForm").form("clear");
		$("#saleOrderRefundBackForm").form("clear");
		$("#saleOrderRefundBeforeForm").form("load",rowData);
		$("#saleOrderRefundBackForm").form("load",rowData);
		if(rowData.platform == 'light') {
			$("#adjustmentPositive1").show();
			$("#adjustmentPositive2").show();
			$("#Site1").hide();
			$("#Site2").hide();
		} else {
			if(rowData.platform == 'ebay' || rowData.platform == 'amazon') {
				$("#Site1").show();
				$("#Site2").show();
				$("#adjustmentPositive1").hide();
				$("#adjustmentPositive2").hide();
			} else {
				$("#Site1").hide();
				$("#Site2").hide();
				$("#adjustmentPositive1").hide();
				$("#adjustmentPositive2").hide();
			}
		}
		$("#id_").next().hide();
		if(rowData.shipCost != 0) {
			$("#shipCost").text(rowData.shipCost);
		} else {
			$("#shipCost").text(0);
			$("#isConsumerPaid_").combobox('setValue','0');
			saleOrderRefund.showOrHide(new Array('isConsumerPaid_'),'combobox',true);
		}
		$("#saleOrderRefund").dialog("open");
	};
	
	$("#saleOrderReissueDatagrid").datagrid({
		url : '', 
		frozenColumns:[[
			{
				field : 'entityId',
				title : 'entityId',
				hidden : true
			},
			{
				field : 'sku',
				title : 'SKU',
				align : 'center',
				width : 120
			},
			{
				field : 'mSku',
				title : 'mSku',
				align : 'center',
				hidden : true,
			},
			{
				field : 'asin',
				title : 'ASIN',
				align : 'center',
				width : 95,
				hidden : true,
				formatter : function(value,row,index){
					if(typeof(row.url) && row.url) {
						return "<a href='" + row.url + "' target='_blank'>" + value + "</a>";
					}
					return value;
				}
			},
			{
				field : 'lineNumber',
				title : '订单行',
				align : 'center',
				width : 60,
				hidden : true
			},
			{
				field : 'qtyOrdered',
				title : '数量',
				align : 'center',
				width : 50
			}
		]],
		columns : [ [
			{
				field : 'qty',
				title : '补发数量',
				align : 'center',
				width : 50
			},
			{
				field : 'badProductQty',
				title : '有问题产品数量',
				align : 'center',
				width : 80
			},
			{
				field : 'productCaseType',
				title : '问题类型',
				align : 'center',
				width : 90
			}/*,
			{
				field : 'remarks',
				title : '备注',
				align : 'center',
				width : 80,
			}*/] ],
		singleSelect : true,
		rownumbers : true,
		fitColumns : true,
		border : true,
		toolbar : "#saleOrderReissueToolbar",
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        },
        onSelect:function(index,row){
        	if($(this).datagrid('validateRow', saleOrderRefund.editIndex)) {
        		$(this).datagrid('endEdit', saleOrderRefund.editIndex);
        		$(this).datagrid('beginEdit', index);
        		saleOrderRefund.editIndex = index;
        	}
		},
	});
	
	saleOrderRefund.saleOrderReissue = function($row,flag,value) {
		$("#saleOrderReissue").dialog({
			buttons:[{
				id : 'orderReissue_',
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var rows_ = [];
					var rows = $("#saleOrderReissueDatagrid").datagrid('getRows');
					var ulr = '';
					if(value == '') {
						if($("#saleOrderReissueDatagrid").datagrid('validateRow', saleOrderRefund.editIndex)) {
							$("#saleOrderReissueDatagrid").datagrid('endEdit', saleOrderRefund.editIndex);
						} else {
							return;
						}
						if($("#cause__").textbox('getValue') == '' || $("#deliveryWarehouse__").combobox('getValue') == '') {
							return;
						}
						if($("#newAddress").combobox('getValue') == 1 && ($("#name_").textbox('getValue') == '' ||
								$("#postalCode_").textbox('getValue') == '')) {
							return;
						}
						$.each(rows,function(){
							if(this.qty != 0 && this.qty != ''  && this.badProductQty != ''
									&& this.productCaseType != null && this.productCaseType != '') {
								rows_.push(this);
							}
						});
						if(rows_.length == 0) {
							$.messager.alert("信息","请填写补发行!");
							return;
						}
						url = '/returnOrder/saveEditReturnOrder/1';
					} else {
						rows_ = rows;
						url = '/returnOrder/orderReissue/1';
					}
					var param = {
						platform : $row.platform,
						account	: $row.account,
						orderId : $row.orderId,
						currencyCode : $row.currencyCode,
						site : $row.site,
						id : $("#id__").textbox('getValue'),
						deliveryWarehouse : $("#deliveryWarehouse__").combobox('getValue'),
						cause : $("#cause__").textbox('getValue'),
						trackingNum : $("#trackingNum__").textbox('getValue')
					};
					if($("#newAddress").combobox('getValue') == 1) {
						var param1 = {
							name : $("#name_").textbox('getValue'),
							phone : $("#phone_").textbox('getValue'),
							country : $("#country_").combobox('getValue'),
							city : $("#city_").textbox('getValue'),
							provState : $("#provState_").textbox('getValue'),
							addressLine1 : $("#addressLine1_").textbox('getValue'),
							addressLine2 : $("#addressLine2_").textbox('getValue'),
							postalCode : $("#postalCode_").textbox('getValue')
						}
						param = Object.assign(param,param1);
					}
					ocs.ajax({
						url:url,
						async:true,
						data : {"order":param,"items":rows_},
						beforeSend: function () {
							$.messager.progress({
								title: '请稍后',
								msg: '正在操作...'
							});
						},
						complete: function () {
							$.messager.progress('close');
						},
						type: "POST",
						success: function(result) {
							if(result.errorCode == 0){
								$.messager.alert("信息","成功！");	
								$("#saleOrderReissue").dialog("close");
								if(flag != '') {
									$(flag).datagrid('reload');
								}
							} else {
								$.messager.alert("信息","失败！");	
							}
						}
					});
				}
			},{
				text:'取消',
				iconCls:"icon-no",
				handler:function(){
					$("#saleOrderReissue").dialog("close");
				}
			}]
		});
		if(value != 'check') {
			$('#orderReissue_').show();
			saleOrderRefund.showOrHide_(new Array('approveDescription3','approveDescription4'),'hide');
			if(value == '') { 
				$('#newAddress').combobox({disabled: false});
				$("#saleOrderReissueDatagrid").datagrid('addEditor', {    
					field : 'sku',    
					editor : { type: 'textbox'} 
				});
				$("#saleOrderReissueDatagrid").datagrid('addEditor', {    
	                field : 'qty',    
	                editor : { type: 'numberbox', options: {min:0,precision:0} } 
	            });
				$("#saleOrderReissueDatagrid").datagrid('addEditor', {    
					field : 'badProductQty',    
					editor : { type: 'numberbox'/*, options: {min:0,precision:0,validType:'isGreaterQtyOrder_'}*/ }
				});
				$("#saleOrderReissueDatagrid").datagrid('addEditor', {    
					field : 'productCaseType',    
					editor :  { type: 'combobox', 
						options: {
							panelHeight: 'auto',
							panelMaxHeight: '120',
							valueField:'PRODUCTCASETYPE',
							textField:'PRODUCTCASETYPENAME',
							url: GLOBAL.domain + '/returnOrder/getReturnOrderCause/0'
						} 
					}
				});
				/*$("#saleOrderReissueDatagrid").datagrid('addEditor', {    
					field : 'remarks',    
					editor : { type: 'textbox'}
				});*/
				saleOrderRefund.showOrHide_(new Array('trackingNum3','trackingNum4'),'hide');
				saleOrderRefund.showOrHide(new Array('deliveryWarehouse__','country_'),'combobox',false);
				saleOrderRefund.showOrHide(new Array('cause__','name_','phone_','city_','provState_','addressLine1_','addressLine2_','postalCode_'),'textbox',false);
			} else {
				$('#newAddress').combobox({disabled: true});
				$("#saleOrderReissueDatagrid").datagrid('removeEditor',new Array('sku','qty','badProductQty','productCaseType'/*,'remarks'*/));
				saleOrderRefund.showOrHide_(new Array('trackingNum3','trackingNum4'),'show');
				saleOrderRefund.showOrHide(new Array('deliveryWarehouse__','country_'),'combobox',true);
				saleOrderRefund.showOrHide(new Array('cause__','name_','phone_','city_','provState_','addressLine1_','addressLine2_','postalCode_'),'textbox',true);
			}
		} else {
			$('#orderReissue_').hide();
			$('#newAddress').combobox({disabled: true});
			$("#saleOrderReissueDatagrid").datagrid('removeEditor',new Array('sku','qty','badProductQty','productCaseType'/*,'remarks'*/));
			saleOrderRefund.showOrHide_(new Array('trackingNum3','trackingNum4'),'hide');
			saleOrderRefund.showOrHide_(new Array('approveDescription3','approveDescription4'),'show');
			saleOrderRefund.showOrHide(new Array('deliveryWarehouse__','country_'),'combobox',true);
			saleOrderRefund.showOrHide(new Array('cause__','name_','phone_','city_','provState_','addressLine1_','addressLine2_','approveDescription__','postalCode_'),'textbox',true);
		}
		saleOrderRefund.init($row,'#saleOrderReissueDatagrid','reissue');
		$("#saleOrderReissueBeforeForm").form("clear");
		$("#saleOrderReissueBackForm").form("clear");
		if($row.deliveryAddress != null) {
			$("#newAddress").combobox('setValue','1');
			var obj = JSON.parse($row.deliveryAddress)
			$("#name_").textbox('setValue',obj.name);
			$("#phone_").textbox('setValue',obj.phone);
			$("#country_").combobox('setValue',obj.country);
			$("#city_").textbox('setValue',obj.city);
			$("#provState_").textbox('setValue',obj.provState);
			$("#addressLine1_").textbox('setValue',obj.addressLine1);
			$("#addressLine2_").textbox('setValue',obj.addressLine2);
			$("#postalCode_").textbox('setValue',obj.postalCode);
		} else {
			$("#newAddress").combobox('setValue','0');
		}
		if($row.platform == 'ebay' || $row.platform == 'amazon') {
			$("#Site3").show();
			$("#Site4").show();
		} else {
			$("#Site3").hide();
			$("#Site4").hide();
		}
		$("#id__").next().hide();
		
		if('light' === $row.platform) {
			//官网的要去掉FBA发货仓
			var $dw = $('#deliveryWarehouse__');
			$dw.find('option[value="FBA"]').remove();
			var comboboxdata = $dw.combobox('getData');
			for(var i = 0; i < comboboxdata.length; i++) {
				if('FBA' === comboboxdata[i].value) {
					comboboxdata.splice(i, 1);
					break;
				}
			}
			$dw.combobox('loadData', comboboxdata);
		}
		$("#saleOrderReissueBeforeForm").form("load",$row);
		$("#saleOrderReissueBackForm").form("load",$row);
		$("#saleOrderReissue").dialog("open");
	};
	
	$('#newAddress').combobox({  
        onChange:function(newValue,oldValue){  
        	if(newValue != '1') {
        		saleOrderRefund.showOrHide_(new Array('newAddress1','newAddress2'),'hide');
        	} else {
        		saleOrderRefund.showOrHide_(new Array('newAddress1','newAddress2'),'show');
        	}
        }  
    });  
	
	saleOrderRefund.init = function($row,value,temp) {
		$(value).datagrid('loadData', { total: 0, rows: $row.items, footer:[] });
		if($row.platform == 'walmart') {
			$(value).datagrid('showColumn', 'lineNumber');
			$(value).datagrid('hideColumn', 'asin');
		} else if($row.platform == 'amazon') {
			$(value).datagrid('showColumn', 'asin');
			$(value).datagrid('hideColumn', 'lineNumber');
		} else {
			$(value).datagrid('hideColumn', 'asin');
			$(value).datagrid('hideColumn', 'lineNumber');
			if(temp == 'refund') {
				if($row.platform == 'light') {
					$(value).datagrid('hideColumn', 'returnCost');
				}
			}
		}
	}
	
	saleOrderRefund.showOrHide = function(array,value,flag) {
		$.each(array,function(){
			if(value == 'textbox') {
				$('#'+this).textbox({disabled: flag});
			} else if(value == 'combobox') {
				$('#'+this).combobox({disabled: flag});
			} else if(value == 'numberbox') {
				$('#'+this).numberbox({disabled: flag});
			}
		})
	}
	
	saleOrderRefund.showOrHide_ = function(array,value) {
		$.each(array,function(){
			if(value == 'show') {
				$('#'+this).show();
			} else {
				$('#'+this).hide();
			}
		})
	}

	//退货申请单数据填写的验证结果
	var retvr = saleOrderRefund.validateResult = {
		retqty: false,
		cause: false
	};
	
	$.extend($.fn.validatebox.defaults.rules, {
		retqtyValidator: {
			validator: function(value, param) {
	    		var index = $('#saleOrderBackGoodsDatagrid').datagrid('getEditingRowIndexs')
	    			, row = $("#saleOrderBackGoodsDatagrid").datagrid('getRows')[index]
	    			, map = {};
	    		le.utils.eachArray($("#applyBackGoodsDatagrid").datagrid('getRows'), function(reted) {
	    			if('undefined' === typeof map[reted.sku]) {
	    				map[reted.sku] = 0;
	    			}
	    			map[reted.sku] += reted.totalQtyOrdered;
	    		});
	    		retvr.retqty = row.totalQtyOrdered && value <= row.totalQtyOrdered - (map[row.sku] || 0);
	    		return retvr.retqty;
			},
			message: '当前SKU的退货数量应该小于其可退货的数量（总数量减去已经退货数量的差）'
		},
		retcauseValidator: {
			validator: function(value, param) {
				retvr.cause = value && $.trim(value).length > 0;
	    		return retvr.cause;
			},
			message: '退货原因是必选项'
		},
	    isGreaterQtyOrder:{
	    	validator:function(value,param) {
	    		var index = $('#saleOrderRefundDatagrid').datagrid('getEditingRowIndexs');
	    		var row = $("#saleOrderRefundDatagrid").datagrid('getRows')[index];
	    		var qtyOrdered = row.qtyOrdered;
	    		if(qtyOrdered == null || value > qtyOrdered) {
	    			return false;
	    		}
	    		return true;
	    	},
	    	message: '该数量应该小于购买数量!'
	    },
	    isGreaterQtyOrder_:{
	    	validator:function(value,param) {
	    		var index = $('#saleOrderReissueDatagrid').datagrid('getEditingRowIndexs');
	    		var row = $("#saleOrderReissueDatagrid").datagrid('getRows')[index];
	    		var qtyOrdered = row.qtyOrdered;
	    		if(qtyOrdered == null || value > qtyOrdered) {
	    			return false;
	    		}
	    		return true;
	    	},
	    	message: '该数量应该小于购买数量!'
	    },
	    isGreaterQty:{
	    	validator:function(value,param) {
	    		var index = $('#saleOrderRefundDatagrid').datagrid('getEditingRowIndexs');
	    		var row = $("#saleOrderRefundDatagrid").datagrid('getRows')[index];
	    		var qty = row.qty;
	    		if(value != qty) {
	    			return false;
	    		}
	    		return true;
	    	},
	    	message: '该数量应该等于退货数量!'
	    },
	    isGreaterRowTotal:{
			validator:function(value,param) {
				var index = $('#saleOrderRefundDatagrid').datagrid('getEditingRowIndexs');
				var row = $("#saleOrderRefundDatagrid").datagrid('getRows')[index];
				var rowTotal = row.rowTotal;
				if(rowTotal == null || value > rowTotal) {
					return false;
				}
				return true;
			},
			message: '该金额应该小于总额!'
		},
	    isGreaterShippingTotal:{
			validator:function(value,param) {
				var index = $('#saleOrderRefundDatagrid').datagrid('getEditingRowIndexs');
				var row = $("#saleOrderRefundDatagrid").datagrid('getRows')[index];
				var shippingTotal = row.shippingTotal;
				value = parseFloat(value);
				if(value > shippingTotal || value != 0) {
					return false;
				}
				return true;
			},
			message: '该金额应该小于运费总额!'
		},
		isGreaterHours:{
			validator:function(value,param) {
				if(value > 24) {
					return false;
				}
				return true;
			},
			message: '每天使用时长应小于24!'
		}
		
	});
	
	$.extend($.fn.datagrid.methods, {
	    getEditingRowIndexs: function(jq) {
	        var rows = $.data(jq[0], "datagrid").panel.find('.datagrid-row-editing');
	        var indexs = [];
	        rows.each(function(i, row) {
	            var index = row.sectionRowIndex;
	            if (indexs.indexOf(index) == -1) {
	                indexs.push(index);
	            }
	        });
	        return indexs;
	    },
	    addEditor : function(jq, param) {  
	        if (param instanceof Array) {    
	            $.each(param, function(index, item) {    
	                var e = $(jq).datagrid('getColumnOption', item.field);    
	                e.editor = item.editor;    
	            });    
	        } else {    
	            var e = $(jq).datagrid('getColumnOption', param.field);    
	            e.editor = param.editor;    
	        }    
	    },    
	    removeEditor : function(jq, param) {    
	        if (param instanceof Array) {    
	            $.each(param, function(index, item) {    
	                var e = $(jq).datagrid('getColumnOption', item);    
	                e.editor = {};    
	            });    
	        } else {    
	            var e = $(jq).datagrid('getColumnOption', param);    
	            e.editor = {};    
	        }    
	    }    
	});
	 
	/**
	* 退货申请
	**/
	saleOrderRefund.saleOrderBackGoods = function($row,flag,value) {
		$("#saleOrderBackGoods").dialog({
			buttons:[{
				id : 'orderReissue_',
				text:'确定',
				iconCls:'icon-ok',
				handler:function() {
					$("#saleOrderBackGoodsDatagrid").datagrid('endEdit', backGoodEditIndex);
					var nopass = false;
					le.utils.eachObject(retvr, function(value) {
						if(!value) {
							nopass = true; 
							return true;
						}
						return false;
					}, true);
					
					if(nopass) {
				    	$.messager.alert("信息","填写的数据未通过校验，无法提交！");
				    	return;
					}
					
					var rows = [];
					le.utils.eachArray($("#saleOrderBackGoodsDatagrid").datagrid('getRows'), function(row) {
						if(!isNaN(row.retqty) && parseInt(row.retqty) > 0) {
							rows.push(row); 
						}
					});
					
					/*var rows_ = [];
					var rows = $("#saleOrderBackGoodsDatagrid").datagrid('getRows');
					var ulr = '';
				    var skuValue=$("#sku__").textbox('getValue');
				    var cause_val = $("#cause__back").textbox("getValue");
				    if(''==cause_val){
				    	$.messager.alert("信息","请输入退货原因!");
						return;
				    }
					$.each(rows,function(){
						if(this.sku ==skuValue ) {
							rows_.push(this);
						}
					});
					if(rows_.length == 0) {
						$.messager.alert("信息","请选择sku信息!");
						return;
					}*/
					
					//sku填入数量校验开始
					/*var  skuTotal = $("#skuTotal__").numberbox('getValue');
					if(''== skuTotal){
				    	$.messager.alert("信息","请输入sku个数!");
						return;
				    }else if('0'== skuTotal){
				    	$.messager.alert("信息","输入的sku个数不能为0!");
						return;
				    }
			    	var param = {
							orderId : $row.orderId,
							entityId:$row.entityId,
							sku : $("#sku__").textbox('getValue'),
							totalQtyOrdered : skuTotal 
						};
			    	var skuNum=0;
			    	var urlSeq   = '/returnOrder/findAlreadyApplyReturnSkuNum';
					
			    	var rowsObject = $('#saleOrderBackGoodsDatagrid').datagrid('getRows');
			    	var  sku = $("#sku__").textbox('getValue');
			    	for (var i = 0; i < rowsObject.length; i++) {
						if(rowsObject[i].sku == sku) {
							
							if(skuTotal>rowsObject[i].totalQtyOrdered ){
								$("#skuTotal__").numberbox('setValue','')
								$.messager.alert("信息", "退货数量超出订单量,订单sku数量为"+rowsObject[i].totalQtyOrdered+ ",您当前输入的数量是"+skuTotal+",请重新输入!");
								return;
							}
							ocs.ajax({
								url:urlSeq,
								async:false,
								data : param,
								type: "POST",
								success: function(result) {
									skuNum=result;
								}
							});
							var checkNum=rowsObject[i].totalQtyOrdered-skuTotal-skuNum;
							if(checkNum<0){
								$("#skuTotal__").numberbox('setValue','')
								$.messager.alert("信息", "退货数量超出订单量,订单sku数量为"+rowsObject[i].totalQtyOrdered+",之前已申请的sku数量是"+skuNum+",您当前输入的数量是"+skuTotal+",请重新输入!");
								return;
							}
							
						}
					} */
					//sku填入数量校验结束

					ocs.ajax({
						url: '/returnOrder/saveEditReturnGoodsOrder/1',
						async:true,
						data: { 
							'order': {
								platform: $row.platform,
								account: $row.account,
								orderId: $row.orderId,
								entityId: $row.entityId,
								site : $row.site,
								rmaSeq : $row.rmaSeq,
								//sku : $("#sku__").textbox('getValue'),
								//totalQtyOrdered : $("#skuTotal__").numberbox('getValue'),
								cause : $("#cause__back").textbox('getValue'),
								trackingNum : $("#trackingNum_return__").textbox('getValue')
							}, 
							'items': rows
						},
						beforeSend: function () {
							$.messager.progress({
								title: '请稍后',
								msg: '正在操作...'
							});
						},
						complete: function () {
							$.messager.progress('close');
						},
						type: "POST",
						success: function(result) {
							var message, icon = 'info';
							if(1 === result.errorCode && 'SKU_NUMBER_ERROR' === result.error) {
								message = result.description;
							}else if(1 === result.errorCode && 'EX_ERROR' === result.error) {
								message = result.description;
								icon = 'error';
							} else if(0 === result.errorCode && !result.error) {
								message = "保存成功！";
							} else {
								message = "未知错误，亲联系管理员！";
								icon = 'warning';
							}
							
							$.messager.alert("信息", message, icon);	
							$("#saleOrderBackGoods").dialog("close");
							if(flag) {
								$(flag).datagrid('reload');
							}
						}
					});
				}
			},{
				text:'取消',
				iconCls:"icon-no",
				handler:function(){
					$("#saleOrderBackGoods").dialog("close");
				}
			}]
		});

	    var rows = $row.items;
     	/*var skus=[];
    	for (var i = 0; i < rows.length; i++) {
			 skus.push({
			        "Value": rows[i].sku,
			        "Name": rows[i].sku
			    });
		}
    	$('#sku__').combobox({
    	    valueField: 'Value',
    	    textField: 'Name',
    	    panelHeight: 'auto',
    	    required: true,
    	    editable: false, //不可编辑，只能选择
    	});*/
    	
		ocs.ajax({
			url:'/returnOrder/getReturnOrderCauseAll',
			async:true,
			type: "GET",
			success: function(result) {
				$("#cause__back").combobox({ validType: 'retcauseValidator' }).combobox("clear").combobox("loadData",result);
			}
		});
			
    	/*$("#sku__").combobox("clear").combobox("loadData", skus);
		if(rows.length==1){
			$("#sku__").combobox("setValue",skus[0].Value);
		}*/
		
		if(value != 'check') {
			$('#orderReissue_').show();
			saleOrderRefund.showOrHide_(new Array('approveDescription3','approveDescription4'),'hide');
			 
		}  
		$('#saleOrderBackGoodsDatagrid').datagrid('loadData', { total: 0, rows: $row.items, footer:[] });
		$("#saleOrderBackGoodsDatagrid").datagrid('addEditor', {    
            field: 'retqty',    
            editor: { type: 'numberbox', options: { min: 0, precision: 0, validType: 'retqtyValidator' } } 
        });
		/*ocs.ajax({
			url:'/lightSaleOrder/findReturnInformationByParentId/'+ $row.entityId,
			async:true,
			type: "POST",
			success: function(result) {
				$('#applyBackGoodsDatagrid').datagrid('loadData', { total: 0, rows: result.rows, footer:[] });
			}
		});*/
		ocs.ajax({
			url:'/returnOrder/findAppliedReturnOrders/'+ $row.entityId,
			async:true,
			type: "POST",
			success: function(result) {
				$('#applyBackGoodsDatagrid').datagrid('loadData', { total: 0, rows: result.rows, footer:[] });
			}
		});

		$("#saleOrderBackGoodsBeforeForm").form("clear");
		$("#saleOrderBackGoodsBackForm").form("clear")
			 
		$("#saleOrderBackGoodsBeforeForm").form("load",$row);
		$("#saleOrderBackGoodsBackForm").form("load",$row);
		if($row.platform == 'ebay' || $row.platform == 'amazon') {
			$("#Site3").show();
			$("#Site4").show();
		} else {
			$("#Site3").hide();
			$("#Site4").hide();
		}
		$("#id__").next().hide();
		$("#saleOrderBackGoods").dialog("open");
	};
	
	 
})(saleOrderRefund, jQuery);