var returnOrder = {};
(function(returnOrder, $) {
	var editIndex = undefined;
	$("#returnOrderDatagrid").datagrid({
		url : GLOBAL.domain + '/returnOrder/findAll',
		columns : [ [
				{
					field : 'platform',
					title : '平台',
					align : 'center',
					sortable : true,
					formatter : function(value,row,index){
						if(value == 'ebay') {
							return 'Ebay';
						} else if(value == 'light') {
							return '官网';
						} else if(value == 'walmart') {
							return '沃尔玛';
						} else if(value == 'amazon') {
							return '亚马逊';
						}
						return '';
					}
				},
				{
					field : 'account',
					title : '账号',
					align : 'center',
					sortable : true
				},
				{
					field : 'site',
					title : '站点',
					align : 'center',
					sortable : true
				},
				{
					field : 'orderId',
					title : '订单号',
					align : 'center',
					sortable : true
				},
				{
					field : 'cause',
					title : '原因',
					align : 'center',
					sortable : true,
					formatter : function(value,row,index){
						if(row.orderType == 2) {
							return '';
						}
						return value;
					}
				},
				{
					field : 'orderType',
					title : '订单类型',
					align : 'center',
					sortable : true,
					formatter : function(value,row,index){
						if(value == 0) {
							return '退货退款单';
						} else if(value == 1) {
							return '补发单';
						} else if(value == 2) {
							return '线下单';
						}
						return '';
					}
				},
				{
					field : 'sku',
					title : 'SKU',
					align : 'center'
				},
				{
					field : 'qty',
					title : '数量',
					align : 'center'
				},
				{
					field : 'returnCost',
					title : '金额',
					align : 'center',
					formatter : function(value,row,index){
						if(row.orderType == 2) {
							return row.deliveryWarehouse;
						}
						return value;
					}
				},
				{
					field : 'productCaseType',
					title : '问题类型',
					align : 'center'
				},
				{
					field : 'createName',
					title : '创建人',
					align : 'center'
				},
				{
					field : 'tarckingService',
					title : '运输服务和跟踪号',
					align : 'center',
					/*formatter : function(value,row,index){
						var result = '';
						if(value && row.tarckingNum) {
							var tarckingService = value.split(",");
							var tarckingNum = row.tarckingNum.split(",");
							for (var i = 0; i < tarckingService.length; i++) {
								result += tarckingService[i] + ":" + tarckingNum[i] + '<br/>' ;
							}
						}
						return result;
					}*/
				},
				{
					field : 'createDate',
					title : '创建时间',
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
					field : 'updateDate',
					title : '更新时间',
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
					field : 'approveResult',
					title : '审核状态',
					align : 'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(row.enabledFlag == 'N') {
							return '已取消申请';
						} else {
							if(value == null) {
								return '未审核';
							} else if(value == 0) {
								return '审核未通过';
							} else if(value == 1) {
								return '审核通过';
							} else if(value == 2) {
								return '已取消';
							}
						}
					}
				},
				{
					field : 'v',
					title : '动作',
					align : 'center',
					formatter : function(value, row, index) {
						var result = '';
						if(row.enabledFlag == 'N') {
							result += '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="returnOrder.check('
								+ index
								+ ')" data-options="plain:true">查看</a> &nbsp;&nbsp;';
						} else {
							var orderType = row.orderType;
							if(row.approveResult == null) {
								result += '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="returnOrder.update('
										+ index
										+ ')" data-options="plain:true">编辑</a> &nbsp;&nbsp;'
										+ '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="returnOrder.cancelApplication('
										+ index
										+ ')" data-options="plain:true">取消申请</a> &nbsp;&nbsp;';
							} else {
								if(row.approveResult == 1) {
									if(orderType == 0 && row.isReceiving == 0) {
										result += '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="returnOrder.confirmRefund('
											+ index
											+ ')" data-options="plain:true">确认退款收货</a> &nbsp;&nbsp;';
									} else if(orderType == 1 && row.isReissue == 0) {
										result += '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="returnOrder.confirmReissue('
											+ index
											+ ')" data-options="plain:true">确认补发</a> &nbsp;&nbsp;';
									} else if(orderType == 2 && row.isConfirmOrder == 0) {
										result += '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="returnOrder.confirmOrder('
											+ index
											+ ')" data-options="plain:true">确认下单</a> &nbsp;&nbsp;';
									} 
								} else {
									result += '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="returnOrder.check('
										+ index
										+ ')" data-options="plain:true">查看</a> &nbsp;&nbsp;';
								}
							}
						}
						return result;
					}
				} ] ],
		singleSelect : true,
		selectOnCheck : false,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		pageSize : 50,
		border : true,
		toolbar : "#returnOrderToolbar",
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        }
	});

	returnOrder.update = function(index) {
		$.messager.confirm('提示', "您确定要编辑吗?", function(r){
			if(r) {
				$("#returnOrderDatagrid").datagrid('selectRow',index);
				var row = $("#returnOrderDatagrid").datagrid('getSelected');
				if(row.orderType == 0) {
					saleOrderRefund.saleOrderRefund(row,'#returnOrderDatagrid','');
				} else if(row.orderType == 1) {
					saleOrderRefund.saleOrderReissue(row,'#returnOrderDatagrid','');
				} else if(row.orderType == 2) {
					returnOrder.offlineOrder(row,null);
				}
			}
		});
	};
	
	returnOrder.check = function(index) {
		$("#returnOrderDatagrid").datagrid('selectRow',index);
		var row = $("#returnOrderDatagrid").datagrid('getSelected');
		if(row.orderType == 0) {
			saleOrderRefund.saleOrderRefund(row,'#returnOrderDatagrid','check');
		} else if(row.orderType == 1) {
			saleOrderRefund.saleOrderReissue(row,'#returnOrderDatagrid','check');
		} else if(row.orderType == 2) {
			returnOrder.offlineOrder(row,'value');
		}
	}
	
	returnOrder.cancelApplication = function(index) {
		$("#returnOrderDatagrid").datagrid('selectRow',index);
		var $row = $("#returnOrderDatagrid").datagrid('getSelected');
		$.messager.confirm('提示', "您确定要取消申请吗?", function(r){
			if(r) {
				ocs.ajax({
					url:'/returnOrder/cancelApplication/'+$row.id,
					async:true,
					type: "POST",
					data : {"row" : $row},
					beforeSend: function () {
						$.messager.progress({
							title: '请稍后',
							msg: '正在取消中...'
						});
					},
					complete: function () {
						$.messager.progress('close');
					},
					success: function(result) {
						if(result.errorCode == 0){
							$.messager.alert("信息","取消成功！");	
							$("#returnOrderDatagrid").datagrid('reload')
						}
					}
				});
			}
		});
	};

	returnOrder.confirmRefund = function(index) {
		$("#returnOrderDatagrid").datagrid('selectRow',index);
		var $row = $("#returnOrderDatagrid").datagrid('getSelected');
		$.messager.confirm('提示', "您确定要退款收货吗?", function(r){
			if(r) {
				saleOrderRefund.saleOrderRefund($row,'#returnOrderDatagrid','value');
			}
		});
	}
	
	returnOrder.confirmReissue = function(index) {
		$("#returnOrderDatagrid").datagrid('selectRow',index);
		var $row = $("#returnOrderDatagrid").datagrid('getSelected');
		$.messager.confirm('提示', "您确定要补发吗?", function(r){
			if(r) {
				saleOrderRefund.saleOrderReissue($row,'#returnOrderDatagrid','value');
			}
		});
	}
	
	returnOrder.confirmOrder = function(index) {
		$("#returnOrderDatagrid").datagrid('selectRow',index);
		var $row = $("#returnOrderDatagrid").datagrid('getSelected');
		$.messager.confirm('提示', "您确定要下单吗?", function(r){
			if(r) {
				returnOrder.offlineOrder($row,'');
			}
		});
	}
	
	$("#saleOrderOfflineDatagrid").datagrid({
		url : '',
		columns : [ [
				{	field: 'id',
					width: 50, 
					checkbox: true 
				},
				{
					field : 'sku',
					title : 'SKU',
					align : 'center',
					width : 150
				},
				{
					field : 'qty',
					title : '数量',
					align : 'center',
					width : 100
				},
				{
					field : 'rowTotal',
					title : '价格',
					align : 'center',
					width : 100
				} ] ],
		singleSelect : false,
		fitColumns : true,
		border : true,
		toolbar : "#saleOrderOfflineToolbar",
		onClickCell:function(index){
        	$('#saleOrderOfflineDatagrid').datagrid('clearSelections');
    	    if (returnOrder.endEditing()) {  
    	        $('#saleOrderOfflineDatagrid').datagrid('selectRow', index).datagrid('beginEdit', index);  
    	        returnOrder.editIndex = index;  
    	    } else {  
    	        $('#saleOrderOfflineDatagrid').datagrid('selectRow', returnOrder.editIndex);  
    	    }  
		},
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        }
	});
	
	returnOrder.endEditing = function(){
		if (returnOrder.editIndex == undefined){return true}
		if ($('#saleOrderOfflineDatagrid').datagrid('validateRow', returnOrder.editIndex)){
			$('#saleOrderOfflineDatagrid').datagrid('endEdit', returnOrder.editIndex);
			return true;
		} else {
			return false;
		}
	}
	
	$('#offlineOrderAdd').click(function(){
		if (returnOrder.editIndex != undefined) {
			$('#saleOrderOfflineDatagrid').datagrid('unselectRow', returnOrder.editIndex);
			$('#saleOrderOfflineDatagrid').datagrid("endEdit", returnOrder.editIndex);
		}
		if (returnOrder.endEditing()){
			$('#saleOrderOfflineDatagrid').datagrid('appendRow',{});
			returnOrder.editIndex = $('#saleOrderOfflineDatagrid').datagrid('getRows').length-1;
			$('#saleOrderOfflineDatagrid').datagrid('selectRow', returnOrder.editIndex).datagrid('beginEdit', returnOrder.editIndex);
		}
	});
	$('#offlineOrderRemove').click(function(){
		var rows = $("#saleOrderOfflineDatagrid").datagrid("getSelections");
 	    if (rows.length > 0) {
 	        $.messager.confirm("提示", "你确定要删除吗?", function (r) {
 	            if (r) {
 	                for (var i = 0; i < rows.length; i++) {
 	                	var rowIndex = $('#saleOrderOfflineDatagrid').datagrid('getRowIndex', rows[i]);
 	                	$("#saleOrderOfflineDatagrid").datagrid('deleteRow', rowIndex);
 	                }
 	            }
 	        });
 	    } else {
 	        $.messager.alert("提示", "请选择要删除的行", "error");
 	    }
	});
	
	$('#offlineOrderAddLinkbutton').click(function(){
		returnOrder.offlineOrder({items:[],deliveryAddress:null},null); 
	});
	
	returnOrder.offlineOrder = function($row,value) {
		$("#saleOrderOfflineDialog").dialog({
			buttons:[{
				id : 'saleOrderOffline_',
				text:'确定',
				handler:function(){
					if(value == null && !$("#saleOrderOfflineForm").form('validate')) {
						return;
					}
					if($("#saleOrderOfflineDatagrid").datagrid('validateRow', returnOrder.editIndex)) {
						$("#saleOrderOfflineDatagrid").datagrid('endEdit', returnOrder.editIndex);
					} else {
						return;
					}
					if($("#saleOrderOfflineDatagrid").datagrid('getRows').length == 0) {
						$.messager.alert("信息","请填写订单行!");
						return;
					}
					var param = {
						platform : $("#platform_1").combobox('getValue'),
						account	: $("#account_1").combobox('getValue'),
						site : $("#site_1").combobox('getValue'),
						id : $("#id_1").val(),
						deliveryWarehouse : $("#deliveryWarehouse_1").textbox('getValue'),
						cause : $("#cause_1").textbox('getValue'),
						currencyCode : $("#currencyCode_1").combobox('getValue'),
						name : $("#name_1").textbox('getValue'),
						phone : $("#phone_1").textbox('getValue'),
						country : $("#country_1").combobox('getValue'),
						city : $("#city_1").textbox('getValue'),
						provState : $("#provState_1").textbox('getValue'),
						addressLine1 : $("#addressLine1_1").textbox('getValue'),
						addressLine2 : $("#addressLine2_1").textbox('getValue'),
						postalCode : $("#postalCode_1").textbox('getValue')
					};
					var url = '';
					if(value == null) {
						url = '/returnOrder/saveEditReturnOrder/2';
					} else if(value == '') {
						url = '/returnOrder/orderReissue/2';
					}
					ocs.ajax({
						url: url,
						async:true,
						data : {"order":param,"items":$("#saleOrderOfflineDatagrid").datagrid('getRows')},
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
								$("#saleOrderOfflineDialog").dialog("close");
								$("#returnOrderDatagrid").datagrid('reload');
							}
						}
					});
				}
			},{
				text:'取消',
				handler:function(){
					$("#saleOrderOfflineDialog").dialog("close");
				}
			}]
		});
		if(value == null) {
			saleOrderRefund.showOrHide_(new Array('trackingNum1_','trackingNum2_','approveDescription1_','approveDescription2_'),'hide');
			saleOrderRefund.showOrHide_(new Array('saleOrderOffline_','offlineOrderAdd','offlineOrderRemove','offlineOrderAddressImport'),'show');
			$("#saleOrderOfflineDatagrid").datagrid('addEditor', {    
				field : 'sku',    
				editor : { type: 'textbox'}
			});
			$("#saleOrderOfflineDatagrid").datagrid('addEditor', {    
				field : 'qty',    
				editor : { type: 'numberbox', options: {min:0,precision:0} }
			});
			$("#saleOrderOfflineDatagrid").datagrid('addEditor', {    
				field : 'rowTotal',    
				editor : { type: 'numberbox', options: {min:0,precision:4} }
			});
			saleOrderRefund.showOrHide(new Array('cause_1','deliveryWarehouse_1','name_1','postalCode_1','phone_1','city_1','provState_1','addressLine1_1','addressLine2_1','approveDescription_1'),'textbox',false);
			saleOrderRefund.showOrHide(new Array('platform_1','account_1','site_1','currencyCode_1','country_1'),'combobox',false);
		} else {
			if(value == '') {
				saleOrderRefund.showOrHide_(new Array('saleOrderOffline_'),'show');
				saleOrderRefund.showOrHide_(new Array('approveDescription1_','approveDescription2_'),'hide');
			} else {
				saleOrderRefund.showOrHide_(new Array('saleOrderOffline_'),'hide');
				saleOrderRefund.showOrHide_(new Array('approveDescription1_','approveDescription2_'),'show');
			}
			$("#saleOrderOfflineDatagrid").datagrid('removeEditor',new Array('qty','sku','rowTotal'));
			saleOrderRefund.showOrHide_(new Array('offlineOrderAdd','offlineOrderRemove','offlineOrderAddressImport'),'hide');
			saleOrderRefund.showOrHide(new Array('cause_1','deliveryWarehouse_1','name_1','postalCode_1','phone_1','city_1','provState_1','addressLine1_1','addressLine2_1','approveDescription_1'),'textbox',true);
			saleOrderRefund.showOrHide(new Array('platform_1','account_1','site_1','currencyCode_1','country_1'),'combobox',true);
		}
		$("#saleOrderOfflineForm").form('clear');
		$("#account_1").combobox('loadData',[]);
		$("#site_1").combobox('loadData',[]);
		$("#saleOrderOfflineForm").form('load',$row);
		if($row.deliveryAddress != null) {
			var obj = JSON.parse($row.deliveryAddress)
			$("#name_1").textbox('setValue',obj.name);
			$("#phone_1").textbox('setValue',obj.phone);
			$("#country_1").combobox('setValue',obj.country);
			$("#city_1").textbox('setValue',obj.city);
			$("#provState_1").textbox('setValue',obj.provState);
			$("#addressLine1_1").textbox('setValue',obj.addressLine1);
			$("#addressLine2_1").textbox('setValue',obj.addressLine2);
			$("#postalCode_1").textbox('setValue',obj.postalCode);
		}
		$("#saleOrderOfflineDatagrid").datagrid('loadData', { total: 0, rows: $row.items, footer:[] });
		$("#saleOrderOfflineDialog").dialog("open");
	}
	
	$("#offlineOrderAddressImport").click(function(){
		$("#orderId").textbox('setValue','');
		$("#offlineOrderAddressImportDialog").dialog({
			buttons:[{
				text:'确定',
				handler:function(){
					if($('#platform_1').combobox('getValue') == '' || $('#account_1').combobox('getValue') == '') {
						$.messager.alert("提示信息", "请选择平台和账号!");
						return;
					}
					if($('#orderIdImport').textbox('getValue') == '') {
						$.messager.alert("提示信息", "请填写订单号!");
						return;
					}
					ocs.ajax({
						url: '/returnOrder/getAddress',
						async:true,
						data : {"orderId":$('#orderIdImport').textbox('getValue'),platform:$('#platform_1').combobox('getValue'),account:$('#account_1').combobox('getValue')},
						type: "POST",
						success: function(result) {
							if(result.errorCode == 1) {
								$.messager.alert("提示信息", "订单号有误，无法查询到地址!");
								return;
							} else {
								$("#saleOrderOfflineForm").form('load',result.data);
								$('#offlineOrderAddressImportDialog').dialog('close');
							}
						}
					});
				}
			},{
				text:'关闭',
				handler:function(){
					$('#offlineOrderAddressImportDialog').dialog('close');
				}
			}]
		});
		$("#offlineOrderAddressImportDialog").dialog("open");
	});
	
	
	
	//平台改变事件
	$("#platform").combobox({
		onChange: function (newValue,oldValue) {
			returnOrder.changePlatform(newValue,'#account','#site')
		}
	});
	
	returnOrder.changePlatform = function(newValue,account,site) {
		if(typeof(newValue) && newValue) {
			if(newValue != 'walmart') {
				ocs.ajax({
					url:'/returnOrder/getAccount/'+newValue,
					async:true,
					type: "POST",
					success: function(result) {
						returnOrder.loadData(result,[],account);
					}
				});
			} else {
				$(account).combobox('loadData',[{text:'USA',value:'USA'}]);
			}
		} else {
			$(account).combobox('loadData',[]);
		}
		$(account).combobox('setValue','');
		$(site).combobox('loadData',[]);
		$(site).combobox('setValue','');
	}
	
	$("#platform_1").combobox({
		onChange: function (newValue,oldValue) {
			returnOrder.changePlatform(newValue,'#account_1','#site_1')
			/*if(newValue == 'amazon' || newValue == 'ebay') {
				$("#site_1").combobox({
					required:true
				});
			} else {
				$("#site_1").combobox({
					required:false
				});
			}*/
		}
	});
	
	//账号改变事件
	$("#account").combobox({
		onChange: function (newValue,oldValue) {
			var platform = $("#platform").combobox('getValue');
			returnOrder.changeAccount(newValue,platform,'#site');
		}
	});
	
	returnOrder.changeAccount = function(newValue,platform,site) {
		if(typeof(newValue) && newValue) {
			ocs.ajax({
				url:'/returnOrder/getSite?platform='+platform+'&account='+newValue,
				async:true,
				type: "POST",
				success: function(result) {
					returnOrder.loadData(result,[],site);
				}
			});
		} else {
			$(site).combobox('loadData',[]);
		}
		$(site).combobox('setValue','');
	}
	
	$("#account_1").combobox({
		onChange: function (newValue,oldValue) {
			var platform = $("#platform_1").combobox('getValue');
			returnOrder.changeAccount(newValue,platform,'#site_1');
		}
	});
	
	returnOrder.loadData = function(result,data,value) {
		result = eval(result);
		if(result.length > 0) {
			$.each(result,function(){
				data.push({
					text : this,
					value : this
				});
			});
		}
		$(value).combobox('loadData',data);
	}
	
	$("#returnOrderExportLinkbutton").click(function(){
		window.location.href = GLOBAL.domain + '/excel/export/returnOrderExport?platform='+$("#platform").combobox('getValue')
							+'&account='+$("#account").combobox('getValue')+'&site='+$("#site").combobox('getValue')
							+'&orderId='+$("#orderId").textbox('getValue')+'&createName='+$("#createName").textbox('getValue')
							+'&orderType='+$("#orderType").combobox('getValue')+'&cause='+$("#cause").textbox('getValue');
	})
	
	$("#returnOrderQuery").click(function(){
		var formData = $("#returnOrderForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#returnOrderDatagrid').datagrid('load',{
			param : param
		});
	});
	
	$("#returnOrderReset").on('click',function(){
		$("#returnOrderForm").form("clear");
	});
	
})(returnOrder, jQuery);