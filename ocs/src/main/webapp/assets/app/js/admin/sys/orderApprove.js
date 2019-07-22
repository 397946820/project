var orderApprove = {};
(function(orderApprove,$){
	
	function initDialogData(index){
		$("#orderApproveList").datagrid("selectRow",index);
		var row = $("#orderApproveList").datagrid("getSelected");
		return row;
	}
	
	function initRefoundDialogData(row){
		$("#platform_").textbox("setValue",row.platform);
		$("#account_").textbox("setValue",row.account);
		$("#site_").textbox("setValue",row.site);
		$("#orderId_").textbox("setValue",row.orderId);
		$("#currencyCode_").textbox("setValue",row.currencyCode);
		$("#cause_").combobox("setValue",row.cause);
		$("#isConsumerPaid_").combobox("setValue",row.isConsumerPaid);
		$("#edaOrderNum_").textbox("setValue",row.edaOrderNum);
		if(row.platform == 'light') {
			$("#saleOrderRefundDatagrid").datagrid("hideColumn",'returnCost');
			showOrHide(new Array('adjustmentPositive1','adjustmentPositive2'),'show');
			$("#adjustmentPositive_").textbox("setValue",row.adjustmentPositive);
		} else {
			$("#saleOrderRefundDatagrid").datagrid("showColumn",'returnCost');
			showOrHide(new Array('adjustmentPositive1','adjustmentPositive2'),'hide');
		}
	}
	
	function initReissueDialogData(row){
		$("#platform__").textbox("setValue",row.platform);
		$("#account__").textbox("setValue",row.account);
		$("#site__").textbox("setValue",row.site);
		$("#orderId__").textbox("setValue",row.orderId);
		$("#currencyCode__").textbox("setValue",row.currencyCode);
		$("#cause__").textbox("setValue",row.cause);
		$("#deliveryWarehouse__").combobox("setValue",row.deliveryWarehouse);
		if(row.deliveryAddress != null) {
			showOrHide(new Array('newAddress1','newAddress2'),'show');
			$("#newAddress").combobox('setValue','1');
			var obj = JSON.parse(row.deliveryAddress)
			$("#name_").textbox('setValue',obj.name);
			$("#phone_").numberbox('setValue',obj.phone);
			$("#country_").textbox('setValue',obj.country);
			$("#city_").textbox('setValue',obj.city);
			$("#provState_").textbox('setValue',obj.provState);
			$("#addressLine1_").textbox('setValue',obj.addressLine1);
			$("#addressLine2_").textbox('setValue',obj.addressLine2);
			$("#postalCode_").numberbox('setValue',obj.postalCode);
		} else {
			showOrHide(new Array('newAddress1','newAddress2'),'hide');
			$("#newAddress").combobox('setValue','0');
		}
	}
	
	function initOfflineDialogData(row){
		$("#platform_1").combobox("setValue",row.platform);
		$("#account_1").combobox("setValue",row.account);
		$("#site_1").combobox("setValue",row.site);
		$("#cause_1").textbox("setValue",row.cause);
		$("#deliveryWarehouse_1").textbox("setValue",row.deliveryWarehouse);
		if(row.deliveryAddress != null) {
			var obj = JSON.parse(row.deliveryAddress)
			$("#name_1").textbox('setValue',obj.name);
			$("#phone_1").textbox('setValue',obj.phone);
			$("#country_1").textbox('setValue',obj.country);
			$("#city_1").textbox('setValue',obj.city);
			$("#provState_1").textbox('setValue',obj.provState);
			$("#addressLine1_1").textbox('setValue',obj.addressLine1);
			$("#addressLine2_1").textbox('setValue',obj.addressLine2);
			$("#postalCode_1").textbox('setValue',obj.postalCode);
		}
	}
	
	function showOrHide(arr,value) {
		$.each(arr,function(){
			if(value == 'show') {
				$('#'+this).show();
			} else {
				$('#'+this).hide();
			}
		});
	};
	
	function saveApproveData(data){
		if(data["isPass"]&&data["approveCase"]){
			//保存审批数据
			ocs.ajax({
				type:"POST",
				url:"/orderApprove/saveApproveData",
				data : data,
				success:function(data){
					if(data.data){
						$.messager.show({
							title:'提示',
							msg:'审批成功.',
							timeout:3000,
							showType:'slide'
						});
					}
				}
			});
		}else{
			$.messager.alert('提示','必须输入审批结果与理由!','warning');
		}
	}
	
	orderApprove.cancel = function(index) {
		$.messager.confirm({
			title: '确认', 
			msg: '您确认要取消该单，终止其后续流程吗？',
			fn: function(r) {
				if(r) {
					var row = initDialogData(index);
					ocs.ajax({
						type: 'POST',
						url: '/orderApprove/cancel/' + row.id,
						success: function(resp) {
							$.messager.show({
								title: resp.errorCode === 0 ? '提示' : '错误',
								msg: resp.description,
								timeout: 3000,
								showType: 'slide'
							});
							$("#orderApproveList").datagrid("reload");
						}
					});
				}
			}
		});
	}
	
	orderApprove.approve = function(index){
		var row = initDialogData(index);
		if(row.orderType == "0"){
			initRefoundDialogData(row);
			$("#approveMessage1").show();
			//初始化按钮
			$("#saleOrderRefund").dialog({
				buttons:[{
					text:'确定',
					handler:function(){
						var approveData = {};
						approveData["type"]= 0;
						approveData["id"] = row.id;
						approveData["isPass"] = $("#isPass_").combobox("getValue");
						approveData["approveCase"] = $("#approveCase_").textbox("getValue");
						saveApproveData(approveData);
						$('#saleOrderRefund').dialog('close');
						$("#orderApproveList").datagrid("reload");
						
					}
				},{
					text:'关闭',
					handler:function(){
						$('#saleOrderRefund').dialog('close');
					}
				}]
			});
			$("#isPass_").combobox("setValue","");
			$("#approveCase_").textbox("setValue","");
			$("#saleOrderRefund").dialog("open");
			//查询sku信息
			ocs.ajax({
				type:"POST",
				url:"/orderApprove/getInfoById/"+row.id+"/0",
				success:function(data){
					$("#saleOrderRefundDatagrid").datagrid("loadData",data);
				}
			});
		}else if(row.orderType == "1"){
			initReissueDialogData(row);
			showOrHide(new Array('isPass__1','isPass__2','approveCase__1','approveCase__2'),'show');
			//初始化按钮
			$("#saleOrderReissue").dialog({
				buttons:[{
					text:'确定',
					handler:function(){
						var approveData = {};
						approveData["type"]= 1;
						approveData["id"] = row.id;
						approveData["isPass"] = $("#isPass__").combobox("getValue");
						approveData["approveCase"] = $("#approveCase__").combobox("getValue");
						saveApproveData(approveData);
						$('#saleOrderReissue').dialog('close');
						$("#orderApproveList").datagrid("reload");
					}
				},{
					text:'关闭',
					handler:function(){
						$('#saleOrderReissue').dialog('close');
					}
				}]
			});
			$("#isPass__").combobox("setValue","");
			$("#approveCase__").textbox("setValue","");
			$("#saleOrderReissue").dialog("open");
			ocs.ajax({
				type:"POST",
				url:"/orderApprove/getInfoById/"+row.id+"/1",
				success:function(data){
					$("#saleOrderReissueDatagrid").datagrid("loadData",data);
				}
			});
			//
		} else if(row.orderType == "2"){
			initOfflineDialogData(row);
			showOrHide(new Array('isPass1','isPass2','approveCase1','approveCase2'),'show');
			//初始化按钮
			$("#saleOrderOfflineDialog").dialog({
				buttons:[{
					text:'确定',
					handler:function(){
						var approveData = {};
						approveData["type"]= 2;
						approveData["id"] = row.id;
						approveData["isPass"] = $("#isPass_1").combobox("getValue");
						approveData["approveCase"] = $("#approveCase_1").textbox("getValue");
						saveApproveData(approveData);
						$('#saleOrderOfflineDialog').dialog('close');
						$("#orderApproveList").datagrid("reload");
					}
				},{
					text:'关闭',
					handler:function(){
						$('#saleOrderOfflineDialog').dialog('close');
					}
				}]
			});
			$("#isPass_1").combobox("setValue","");
			$("#approveCase_1").textbox("setValue","");
			$("#saleOrderOfflineDialog").dialog("open");
			ocs.ajax({
				type:"POST",
				url:"/orderApprove/getInfoById/"+row.id+"/2",
				success:function(data){
					$("#saleOrderOfflineDatagrid").datagrid("loadData",data);
				}
			});
		}
	}
	orderApprove.view = function(index){
		var row = initDialogData(index);
		if(row.orderType == "0"){$("#platform_").textbox("setValue",row.platform);
			$("#approveMessage1").hide();
			initRefoundDialogData(row);
			//初始化按钮
			$("#saleOrderRefund").dialog({
				buttons:[{
					text:'关闭',
					handler:function(){
						$('#saleOrderRefund').dialog('close');
					}
				}]
			});
			$("#saleOrderRefund").dialog("open");
			//查询sku信息
			ocs.ajax({
				type:"POST",
				url:"/orderApprove/getInfoById/"+row.id+"/0",
				success:function(data){
					$("#saleOrderRefundDatagrid").datagrid("loadData",data);
				}
			});
		}else if(row.orderType == "1"){
			initReissueDialogData(row);
			showOrHide(new Array('isPass__1','isPass__2','approveCase__1','approveCase__2'),'hide');
			//初始化按钮
			$("#saleOrderReissue").dialog({
				buttons:[{
					text:'关闭',
					handler:function(){
						$('#saleOrderReissue').dialog('close');
					}
				}]
			});
			$("#saleOrderReissue").dialog("open");
			ocs.ajax({
				type:"POST",
				url:"/orderApprove/getInfoById/"+row.id+"/1",
				success:function(data){
					$("#saleOrderReissueDatagrid").datagrid("loadData",data);
				}
			});
		}else if(row.orderType == "2"){
			initOfflineDialogData(row);
			showOrHide(new Array('isPass1','isPass2','approveCase1','approveCase2'),'hide');
			//初始化按钮
			$("#saleOrderOfflineDialog").dialog({
				buttons:[{
					text:'关闭',
					handler:function(){
						$('#saleOrderOfflineDialog').dialog('close');
					}
				}]
			});
			$("#saleOrderOfflineDialog").dialog("open");
			ocs.ajax({
				type:"POST",
				url:"/orderApprove/getInfoById/"+row.id+"/2",
				success:function(data){
					$("#saleOrderOfflineDatagrid").datagrid("loadData",data);
				}
			});
		}
		
	}
	
	var cols = [ [
		{
			field : 'id',
			hidden:true
		},
		{
			field : 'platform',
			title : '平台',
			align : 'center',
		},
		{
			field : 'account',
			title : '账号',
			align : 'center',
		},
		{
			field : 'site',
			title : '站点',
			align : 'center',
		},
		{
			field : 'orderId',
			title : '订单ID',
			align : 'center',
		},
		{
			field : 'orderType',
			title : '类型',
			align : 'center',
			formatter:function(value,row,index){
				if(value == "0"){
					return "退货退款单";
				}else if(value == "1"){
					return "补发单";
				}else if(value == "2"){
					return "线下单";
				}else{
					return value;
				}
			}
		},
		{
			field : 'cause',
			title : '原因',
			align : 'center',
		},
		{
			field : 'returnCost',
			title : '退款金额',
			align : 'right',
			hidden : true
		},
		{
			field : 'descriptions',
			hidden:true
		},
		{
			field : 'isConsumerPaid',
			hidden:true
		},
		{
			field : 'trankingService',
			hidden:true
		},
		{
			field : 'trankingNum',
			hidden:true
		},
		{
			field : 'edaOrderNum',
			hidden:true
		},
		{
			field : 'warehouseNum',
			hidden:true
		},
		{
			field : 'returnCostTime',
			hidden:true
		},
		{
			field : 'reissuedOrderId',
			hidden:true
		},
		{
			field : 'ressuedOrderTime',
			hidden:true
		},
		{
			field : 'createBy',
			title : '申请人',
			align : 'center',
		},
		{
			field : 'createDate',
			title : '申请时间',
			align : 'center',
		},
		{
			field : 'approveResult',
			title : '审批结果',
			align : 'center',
			formatter :function(value,row,index){
				if(value == 0){
					return "未通过";
				}else if(value == 1){
					return "通过";
				}else if(value == 2){
					return '已取消';
				}else{
					return "未审批";
				}
			}
		},
		{
			field : 'approveDescription',
			title : '审批理由',
			align : 'center',
		},
		{
			field : 'approveTime',
			title : '审批时间',
			align : 'center',
		},
		{
			field : 'opt',
			title : '审批',
			align : 'center',
			formatter:function(value,row,index){
				if(row.approveResult){
					return '[<a href="javascript:void(0);" class="easyui-linkbutton" onclick="orderApprove.view('+index+')" data-options="plain:true">详情</a>]';
				}else{
					return '[<a href="javascript:void(0);" class="easyui-linkbutton" onclick="orderApprove.approve('+index+')" data-options="plain:true" style="color:red">审批</a>]';
				}
			}
		},
		{
			field: 'cancel',
			title: '取消',
			align : 'center',
			formatter: function(value, row, index) {
				if(row.approveResult == 2) {
					return '';
				} else {
					return '[<a href="javascript:void(0);" class="easyui-linkbutton" onclick="orderApprove.cancel(' + index + ')" data-options="plain:true" style="color:red">取消</a>]';
				}
			}
		}] ];
	
	var getIncludeFields = function() {
		var fields = [];
		var returnFlag = '0' === getSearchParam()['orderType'];
		for(var i = 0, l = cols[0].length; i < l; i++) {
			var field = cols[0][i].field;
			if(!cols[0][i].hidden && 'cancel' !== field && 'opt' !== field && (returnFlag || 'returnCost' !== field)) {
				fields.push(field);
			}
		}
		return fields.join(',');
	}
	
	$("#orderApproveList").datagrid({
		url : '/ocs/orderApprove/list',
		queryParams : {
			param : {
				orderId:'',
				platform : '',
				orderType : ''
			}
		},
		columns : cols,
		singleSelect : true,
		rownumbers : true,
		fit : true,
		border : true,
		pagination : true,
		pageSize : 50,
		toolbar : [ {
			text : '导出',
			iconCls : 'icon-redo',
			handler : function() {
				var param = $("#orderApproveList").datagrid('options').queryParams.param;
				window.location.href = GLOBAL.domain + '/excel/dynamicExport/orderApproveExcel?orderId='+param.orderId+'&platform='+param.platform+'&orderType='+param.orderType
										+"&startCreateDate="+param.startCreateDate+"&endCreateDate="+param.endCreateDate+"&includeFields="+getIncludeFields();
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
		var formData = $("#orderApproveSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	}
	$("#orderApproveSearch").click(function(){
		var param = getSearchParam();
		$("#orderApproveList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#orderApproveList").datagrid("reload");
		if(returnCostColumn) {
			$("#orderApproveList").datagrid(returnCostColumn.hidden ? 'hideColumn' : 'showColumn', returnCostColumn.field);
		} 
	});
	
	$("#orderApproveReset").on('click',function(){
		$("#orderApproveSearchParam").form("clear");
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
				hidden : true
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
				field : 'badProductQty',
				title : '有问题产品数量',
				align : 'center',
				width : 80
			},
			{
				field : 'productCaseType',
				title : '产品问题类型',
				align : 'center',
				width : 90
			},
			{
				field : 'remarks',
				title : '备注',
				align : 'center',
				width : 80
			},
			{
				field : 'inventoryQuantity',
				title : '入库数量',
				align : 'center',
				width : 90,
				hidden : true
			}
			] ],
		singleSelect : true,
		rownumbers : true,
		fitColumns : true,
		border : true,
		toolbar : "#saleOrderRefundToolbar"
	});
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
				title : '产品问题类型',
				align : 'center',
				width : 90
			},
			{
				field : 'remarks',
				title : '备注',
				align : 'center',
				width : 80
			}] ],
		singleSelect : true,
		rownumbers : true,
		fitColumns : true,
		border : true,
		toolbar : "#saleOrderRefundToolbar1"
	});
	
	$("#saleOrderOfflineDatagrid").datagrid({
		url : '',
		columns : [ [
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
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        }
	});
	
	var returnCostColumn = null;
	$(document).ready(function() {
		var buttons = $.extend([], $.fn.datebox.defaults.buttons);
		buttons.splice(0, 0, {
			text: '清空',
			handler: function(target) {
				$(target).datebox('clear').datebox("panel").panel('close');
			}
		});
		$(".easyui-datebox").datebox({ buttons: buttons });
		$("span.datebox .textbox-text").attr("readonly", "true");

		$('select[name="orderType"').combobox({
			onChange: function(n, o) {
				for(var i = 0, l = cols[0].length; i < l; i++) {
					var field = cols[0][i].field;
					if('returnCost' === field) {
						cols[0][i].hidden = '0' !== n;
						returnCostColumn = cols[0][i];
						break;
					}
				}
			}
		})
	});
})(orderApprove,jQuery)