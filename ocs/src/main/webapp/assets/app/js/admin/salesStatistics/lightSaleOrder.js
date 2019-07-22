var lightsaleOrder = {};
(function(lightsaleOrder, $) {
	var editIndex = undefined;
	var orderId = undefined;
	$("#lightSaleOrderdatagrid").datagrid({
		url : GLOBAL.domain + '/lightSaleOrder/findAll',
		columns : [ [
				/*{
					field : 'entityId',
					checkbox : true
				},*/
				{
					field : 'platform',
					title : '国家',
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
					field : 'status',
					title : '订单状态',
					align : 'center',
					sortable : true
				},
				{
					field : 'lightCreatedAt',
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
					field : 'lightUpdatedAt',
					title : '订单更新时间(UTC)',
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
					field : 'name_',
					title : '买家姓名',
					align : 'center',
					sortable : true,
					formatter : function(value,row,index){
						return lightsaleOrder.isEmpty(row.customerFirstname) + " " + lightsaleOrder.isEmpty(row.customerLastname);
						
					}
				},
				{
					field : 'shippingDescription',
					title : '快递方式',
					align : 'center',
					sortable : true
				},
				{
					field : 'subtotal',
					title : '行合计',
					align : 'center',
					sortable : true
				},
				{
					field : 'taxAmount',
					title : '税金',
					align : 'center',
					sortable : true
				},
				{
					field : 'shippingAmount',
					title : '运费',
					align : 'center',
					sortable : true
				},
				{
					field : 'grandTotal',
					title : '订单金额',
					align : 'center',
					sortable : true
				},
				{
					field : 'v',
					title : '动作',
					align : 'center',
					formatter : function(value, row, index) {
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="lightsaleOrder.detail('
								+ index
								+ ')" data-options="plain:true">详情</a> &nbsp;&nbsp;'
								+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="lightsaleOrder.updateSale(\''
								+ row.orderId+'\',\''+row.platform+'\',\''+row.entityId
								+ '\')"  data-options="plain:true">更新订单</a> &nbsp;&nbsp;'
								+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="lightsaleOrder.uploadSaleTranNumber(\''
								+ row.orderId+'\',\''+row.platform
								+ '\')" data-options="plain:true">上传跟踪号</a> &nbsp;&nbsp;'
								+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="lightsaleOrder.createRefundOrReissueOrder(\''
								+ index+'\',\'refund\')" data-options="plain:true">退款</a> &nbsp;&nbsp;'
								+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="lightsaleOrder.createRefundOrReissueOrder(\''
								+ index+'\',\'reissue\')" data-options="plain:true">复制订单</a> &nbsp;&nbsp;'
								+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="lightsaleOrder.backGoods(\''
								+ index+'\',\'back\')" data-options="plain:true">退货申请</a> &nbsp;&nbsp;';
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
		toolbar : "#lightSaleOrderToolbar",
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        }
	});
	
	$("#lightSaleOrderSaleTranNumberDatagrid").datagrid({
		url : '',
		columns : [ [
			{	field: 'id',
				width: 50, 
				checkbox: true 
			},
			{
				field : 'trackNumber',
				title : '跟踪号',
				align : 'center',
				width : 180,
				editor:{ type: 'textbox', options: { required: true,validType:'trackNumberExist'} }
			},
			{
				field : 'carrier',
				title : '承运商',
				align : 'center',
				width : 180,
				editor:{ type: 'combobox', 
						options: { 
							panelHeight: 'auto',
						    editable:false,
							valueField:'carrierType',
							textField:'carrierTypeName',
							url:GLOBAL.domain + '/assets/app/json/carrierType.json',
							required: true,
							validType:'carrierExist'
						} 
				}
			},
			{
				field : 'title',
				title : '跟踪标题',
				align : 'center',
				width : 180,
				editor:'text'
			}] ],
		singleSelect : false,
		fit : true,
		border : true,
		toolbar: [
			{text: '添加', iconCls: 'icon-add', handler: function () {
				if (lightsaleOrder.editIndex != undefined) {
					$('#lightSaleOrderSaleTranNumberDatagrid').datagrid('unselectRow', lightsaleOrder.editIndex);
					$('#lightSaleOrderSaleTranNumberDatagrid').datagrid("endEdit", lightsaleOrder.editIndex);
				}
				if (lightsaleOrder.endEditing()){
					$('#lightSaleOrderSaleTranNumberDatagrid').datagrid('appendRow',{});
					lightsaleOrder.editIndex = $('#lightSaleOrderSaleTranNumberDatagrid').datagrid('getRows').length-1;
					$('#lightSaleOrderSaleTranNumberDatagrid').datagrid('selectRow', lightsaleOrder.editIndex).datagrid('beginEdit', lightsaleOrder.editIndex);
				}
			} }, '-',
	        {text: '删除', iconCls: 'icon-remove', handler: function () {
	    	    var rows = $("#lightSaleOrderSaleTranNumberDatagrid").datagrid("getSelections");
	    	    if (rows.length > 0) {
	    	        $.messager.confirm("提示", "你确定要删除吗?", function (r) {
	    	            if (r) {
	    	                for (var i = 0; i < rows.length; i++) {
	    	                	var rowIndex = $('#lightSaleOrderSaleTranNumberDatagrid').datagrid('getRowIndex', rows[i]);
	    	                	$("#lightSaleOrderSaleTranNumberDatagrid").datagrid('deleteRow', rowIndex);
	    	                }
	    	            }
	    	        });
	    	    } else {
	    	        $.messager.alert("提示", "请选择要删除的行", "error");
	    	    }
	         } }
		 ],
         onClickCell:function(index){
        	$('#lightSaleOrderSaleTranNumberDatagrid').datagrid('clearSelections');
    	    if (lightsaleOrder.endEditing()) {  
    	        $('#lightSaleOrderSaleTranNumberDatagrid').datagrid('selectRow', index).datagrid('beginEdit', index);  
    	        lightsaleOrder.editIndex = index;  
    	    } else {  
    	        $('#lightSaleOrderSaleTranNumberDatagrid').datagrid('selectRow', lightsaleOrder.editIndex);  
    	    }  
		},
		onLoadSuccess : function(data) {
			
        }
	});
	
	$.extend($.fn.validatebox.defaults.rules, {
		trackNumberExist:{
			validator:function(value,param) {
				var ed = $("#lightSaleOrderSaleTranNumberDatagrid").datagrid('getEditor', {index:lightsaleOrder.editIndex,field:'carrier'});
				var carrier = $(ed.target).combobox('getText'); 
				if(carrier == '') {
					return true;
				}
				var isExit;
				$.ajax({
					url : GLOBAL.domain+'/lightSaleOrder/trackNumberExist',
					dataType:'json',
					type:'post',
					data:{
						trackNumber:value,
						carrier:carrier,
						orderId:lightsaleOrder.orderId
					},
					success:function(data) {
						isExit = data
					},
					async:false
				});
				return isExit;
			},
			message: '该订单号此跟踪号在此承运商已经存在'
		},
		carrierExist:{
	    	validator:function(value,param) {
	    		var ed = $("#lightSaleOrderSaleTranNumberDatagrid").datagrid('getEditor', {index:lightsaleOrder.editIndex,field:'trackNumber'});
	    		var trackNumber = $(ed.target).textbox('getText'); 
	    		if(trackNumber == '') {
	    			return true;
	    		}
	    		var isExit;
	    		$.ajax({
	    			url : GLOBAL.domain+'/lightSaleOrder/trackNumberExist',
	    			dataType:'json',
	    			type:'post',
	    			data:{
	    				trackNumber:trackNumber,
	    				carrier:value,
	    				orderId:lightsaleOrder.orderId
	    			},
	    			success:function(data) {
	    				isExit = data
	    			},
	    			async:false
	    		});
	    		return isExit;
	    	},
	    	message: '该订单号此跟踪号在此承运商已经存在'
	    }
	});
	
	lightsaleOrder.endEditing = function(){
		if (lightsaleOrder.editIndex == undefined){return true}
		if ($('#lightSaleOrderSaleTranNumberDatagrid').datagrid('validateRow', lightsaleOrder.editIndex)){
			$('#lightSaleOrderSaleTranNumberDatagrid').datagrid('endEdit', lightsaleOrder.editIndex);
			return true;
		} else {
			return false;
		}
	}
	
	lightsaleOrder.detail = function(index){
		$("#lightSaleOrderdatagrid").datagrid('selectRow',index);
		var row = $("#lightSaleOrderdatagrid").datagrid('getSelected');
		$("#name").text(lightsaleOrder.isEmpty(row.customerFirstname) + " " + lightsaleOrder.isEmpty(row.customerLastname));
		$("#lightSaleOrderDetailDatagrid").datagrid({
			url : GLOBAL.domain + '/lightSaleOrder/getLightOrderItemById/'+ row.entityId,
			columns : [ [
				{
					field : 'name',
					title : '物品标题',
					align : 'center',
					width : 285
				},
				{
					field : 'sku',
					title : 'SKU',
					align : 'center',
					width : 200
				},
				{
					field : 'price',
					title : '价格',
					align : 'center',
					width : 120
				},
				{
					field : 'qtyOrdered',
					title : '数量',
					align : 'center',
					width : 80
				},
				{
					field : 'rowTotal',
					title : '当行总金额',
					align : 'center',
					width : 140
				},
				{
					field : 'v',
					title : '动作',
					align : 'center',
					width : 80,
					formatter : function(value, row, index) {
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="lightsaleOrder.cancelOrder('+ row.parentId + ')" data-options="plain:true">取消订单</a>';
					}
				}] ],
			idField : 'ID',
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			fitColumns : true,
			fit : true,
			pageSize : 10,
			border : true,
			onLoadSuccess : function(data) {
				if(data && data.rows.length > 0){
					var row = data.rows[0];
					$("#region").text(lightsaleOrder.isEmpty(row.region));
					$("#city").text(lightsaleOrder.isEmpty(row.city));
					$("#street").text(lightsaleOrder.isEmpty(row.street));
					$("#postcode").text(lightsaleOrder.isEmpty(row.postcode));
					$("#telephone").text(lightsaleOrder.isEmpty(row.telephone));
					var totalPrice = 0;
					$.each(data.rows,function(){
						totalPrice += this.rowTotal;
					});
					$("#t_total").text(totalPrice.toFixed(2));
				} else {
					$("#t_total").text(0);
				}
			}
		});
		
		$("#lightSaleOrderDetial").dialog("open");
	};
	/**
	 * 创建申请退货
	 * @author wangqiang
	 * @date   2018/03/21
	 */
	lightsaleOrder.backGoods = function(index,value) {
		var message = '';
		if(value == 'back') {
			message = '您确定要申请退货吗？';
		}  
		$.messager.confirm('提示', message, function(r){
			if(r) {
				$("#lightSaleOrderdatagrid").datagrid('selectRow',index);
				var row = $("#lightSaleOrderdatagrid").datagrid('getSelected');
				var rmaSeq="";
				var urlSeq   = '/returnOrder/findOrderReturnSeq';
				ocs.ajax({
					url:urlSeq,
					async:true,
					data : null,
					type: "POST",
					success: function(result) {
						rmaSeq=result;
					}
				});
				
				ocs.ajax({
					url:'/lightSaleOrder/getSaleOrderInformationByParentId/'+ row.entityId,
					async:true,
					type: "POST",
					success: function(result) {
						var $row = {
							platform : 'light',
							entityId:row.entityId,
							account : row.platform,
							site : '',
							orderId : row.orderId,
							currencyCode : row.orderCurrencyCode,
							rmaSeq :rmaSeq,
							items : result.rows
						};
						saleOrderRefund.saleOrderBackGoods($row,'','');
					}
				});
			}
		});
	};
	
	lightsaleOrder.createRefundOrReissueOrder = function(index,value) {
		var message = '';
		if(value == 'refund') {
			message = '您确定要创建退款单吗？';
		} else if(value == 'reissue') {
			message = '您确定要创建补发单吗？';
		}
		$.messager.confirm('提示', message, function(r){
			if(r) {
				$("#lightSaleOrderdatagrid").datagrid('selectRow',index);
				var row = $("#lightSaleOrderdatagrid").datagrid('getSelected');
				ocs.ajax({
					url:'/lightSaleOrder/getSaleOrderRefundByParentId',
					async:true,
					type: "POST",
					data: {id:row.entityId,account:row.platform,platform:'light',orderId:row.orderId},
					success: function(result) {
						var $row = {
							platform : 'light',
							account : row.platform,
							site : '',
							orderId : row.orderId,
							currencyCode : row.orderCurrencyCode,
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
	
	lightsaleOrder.cancelOrder = function(id) {
		$.messager.confirm('提示', "您确定取消订单吗？", function(r){
			if (r){
				ocs.ajax({
					url:'/lightSaleOrder/cancelOrderById/'+id,
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
					type: "GET",
					success: function(result) {
						if(result.errorCode == 0){
							$("#lightSaleOrderdatagrid").datagrid('reload');
							$("#lightSaleOrderDetailDatagrid").datagrid('reload');
							$.messager.alert("信息","取消成功！");	
						} else {
							$.messager.alert("信息","取消失败！");
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						//$.messager.alert("信息", "服务器发生错误！");		
					}
			   });
			}
		});
	};
	
	lightsaleOrder.updateSale = function(orderId,platform,entityId) {
		$.messager.confirm('提示', "您确定更新订单吗？", function(r){
			if (r){
				ocs.ajax({
					url:'/lightSaleOrder/updateOrderByOrderId/'+orderId+'/'+platform+"/"+entityId,
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
							$("#lightSaleOrderdatagrid").datagrid('reload');
							$.messager.alert("信息","更新成功！");	
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						$.messager.alert("信息", "服务器发生错误！");		
					}
			   });
			}
		});
	};
	
	lightsaleOrder.uploadSaleTranNumber = function(orderId,platform) {
		$.messager.confirm('提示', "您确定要上传跟踪号吗？", function(r){
			if(r) {
				$("#lightSaleOrderSaleTranNumber").dialog({
					buttons:[{
						text:'确定',
						handler:function(){
							if ($('#lightSaleOrderSaleTranNumberDatagrid').datagrid('validateRow', lightsaleOrder.editIndex)){
								$('#lightSaleOrderSaleTranNumberDatagrid').datagrid('endEdit', lightsaleOrder.editIndex);
							} else {
								return;
							}
							var rows = $("#lightSaleOrderSaleTranNumberDatagrid").datagrid('getRows')
							if(rows.length == 0) {
								return;
							}
							ocs.ajax({
								url:'/lightSaleOrder/uploadLightSaleTranNumber',
								async:true,
								data : {"param":rows,"orderId":orderId,"platform":platform},
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
										lightsaleOrder.orderId = undefined;
										$("#lightSaleOrderdatagrid").datagrid('reload');
										$.messager.alert("信息","上传成功！");	
									}
								}
							});
						}
					},{
						text:'取消',
						handler:function(){
							$("#lightSaleOrderSaleTranNumber").dialog("close");
						}
					}]
				});
				$('#lightSaleOrderSaleTranNumberDatagrid').datagrid("loadData", []);
				lightsaleOrder.orderId = orderId;
				$("#lightSaleOrderSaleTranNumber").dialog("open");
			}
		});
	}
	
	$("#lightSaleOrderQuery").click(function(){
		var formData = $("#lightSaleOrderSearchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#lightSaleOrderdatagrid').datagrid('load',{
			param : param
		});
	});
	
	$("#lightSaleOrderReset").on('click',function(){
		$("#lightSaleOrderSearchForm").form("clear");
	});
	
	lightsaleOrder.isEmpty = function(value) {
		return value == null ? "" : value;
	}
	
	//英国未发货订单导出
	$("#btn_exportUKNoShipOrders").on("click", function() {
		window.location.href = "/ocs/excel/export/UKNoShipOrderExportService?platform=light";
	});
	
	var TARGETS = {
		MARKSHIPMENT: 'markLight{site}Shipment',
		UPLOADTRACKNO : 'uploadLight{site}TrackNo'
	};
	
	var $dialogFileupload = null;
	
	//进入标记发货界面
	$('#btn_beforeMarkShipment').on('click', function() {
		lightsaleOrder.beforeUploadFile(TARGETS.MARKSHIPMENT);
	});
	
	//导入跟踪号
	$("#btn_beforeUploadTrackNo").on("click", function() {
		lightsaleOrder.beforeUploadFile(TARGETS.UPLOADTRACKNO);
	});
	
	lightsaleOrder.formatTnUploadStatus = function(value, row, index) {
		if('waiting' === value) {
			return '待上传';
		} else if('uploaded' === value) {
			return '已上传';
		} else if('failed' === value) {
			return '上传失败';
		} else if('canceled' === value) {
			return '已取消';
		} else {
			return 'Unknown';
		}
	}
	
	lightsaleOrder.formatOperate = function(value, row, index) {
		return 'waiting' !== row['tnUploadStatus'] 
				? '' 
				: '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="lightsaleOrder.cancelUpload(' + row.id + ')" data-options="plain:true" title="取消上传跟踪号">取消</a>';
	}
	
	lightsaleOrder.formatActions = function(value, row, index) {
		if(!value) return '';
		var actions = value.split(';')
			, texts = [];
		for(var i = 0, l = actions.length; i < l; i++) {
			if('uk-mark-shipment' === actions[i]) {
				texts.push('标记发货');
			} else if('uk-upload-tn' === actions[i]) {
				texts.push('上传跟踪号');
			}
		}
		return texts.join(';');
	}
	
	lightsaleOrder.textIndent5px = function(value, row, index) {
		return 'text-indent: 5px;';
	}
	
	lightsaleOrder.formatDate = function(value, row, index) {
		if(!value) {
			return '';
		}
		var date = new Date(value);
		return date && date.format('yyyy-MM-dd hh:mm:ss') || '';
	}
	
	lightsaleOrder.cancelUpload = function(id) {
		$.messager.confirm({ title: '确认', msg: '您确认要取消上传跟踪号吗？', 
			fn: function(result) {
				if(result) {
					$.ajax({
						url: '/ocs/markshipment/cancel/light-' + id,
						type: 'POST',
						dataType: 'json',
						success:function(data) {
							if(data) {
								$.messager.alert("提示", data.description || ('取消' + ('0' == data.errorCode ? '成功' : '失败') + '！'));	
								$("#dg_light_upload_record").datagrid("reload");
							} else {
								$.messager.alert("提示", "Unknown！");
							}
						},
						error: function() {
							$.messager.alert('消息提示', '取消失败，请联系管理员！', 'warning');
						}
					});
				}
			}
		});
	}
	
	lightsaleOrder.getSearchParam = function(formSelector) {
		var formData = $(formSelector).serializeArray();
		var param = {};
		$.each(formData, function() {
			param[this.name] = this.value;
		});
		return param;
	}

	$(".upload-record-filter .search").click(function() {
		$("#dg_light_upload_record").datagrid({
			queryParams: {
				param: lightsaleOrder.getSearchParam('#form_upload_record_filter')
			}
		});
	});
	
	$(".upload-record-filter .export").click(function() {
		var downloadUrl = '/ocs/excel/export/UKUploadRecordExportService'
			, paramUrl = ''
			, filter = lightsaleOrder.getSearchParam('#form_upload_record_filter');
		
		for(var key in filter) {
			paramUrl += '&' + key +'=' + filter[key];
		}
		
		if(paramUrl) {
			paramUrl = '?' + paramUrl.substr(1);
		}
		
		window.location.href = downloadUrl + paramUrl;
	});
	
	$(".upload-record-filter .reset").on('click',function() {
		$("#form_upload_record_filter").form("clear");
	});
	
	$('#btn_beforeUploadRecord').on('click', function() {
		$("#dialog_tn_upload_record").dialog("open");
		$("#dg_light_upload_record").datagrid({
			url: '/ocs/markshipment/lightlist',
			queryParams: {
				param: lightsaleOrder.getSearchParam('#form_upload_record_filter')
			},
			columns: [[
				{ field: 'id', hidden: true },
				{ field: 'operate', title: '操作', halign: 'center', align: 'center', width: 40, formatter: lightsaleOrder.formatOperate },
				{ field: 'actions', title: '动作轨迹', halign: 'center', align: 'left', width: 130, formatter: lightsaleOrder.formatActions, styler: lightsaleOrder.textIndent5px },
				{ field: 'account', title: '账号', halign: 'center', align: 'center', width: 40 },
				{ field: 'orderId', title: '订单ID', halign: 'center', align: 'left', width: 130, styler: lightsaleOrder.textIndent5px },
				{ field: 'itemId', title: '明细ID', halign: 'center', align: 'left', width: 130, styler: lightsaleOrder.textIndent5px },
				{ field: 'tn01', title: '运输号', halign: 'center', align: 'left', width: 150, styler: lightsaleOrder.textIndent5px },
				{ field: 'carrier01', title: '运输服务', halign: 'center', align: 'center', width: 80 },
				{ field: 'tnUploadStatus', title: '上传状态', halign: 'center', align: 'center', width: 80, formatter: lightsaleOrder.formatTnUploadStatus },
				{ field : 'shippedAt', title: '成功上传时间', halign: 'center', align: 'center', width: 120, formatter: lightsaleOrder.formatDate },
				{ field : 'tnInitAt', title: '跟踪号导入时间', halign: 'center', align: 'center', width: 120, formatter: lightsaleOrder.formatDate },
				{ field : 'createdAt', title: '数据导入时间', halign: 'center', align: 'center', width: 120, formatter: lightsaleOrder.formatDate },
				{ field : 'cause', title: '异常说明', halign: 'center', align: 'left', width: 280, styler: lightsaleOrder.textIndent5px }
			]],
			idField : 'id',
			singleSelect : false,
			rownumbers : true,
			pagination : true,
			fit: true,
			pageSize : 30,
			border : true,
			toolbar: '.upload-record-filter'
		});
	});
	
	$('#btn_upload_record_close').on('click', function() {
		$("#dialog_tn_upload_record").dialog("close");
	});
	
	lightsaleOrder.beforeUploadFile = function(target) {
		$dialogFileupload = ($dialogFileupload || $("#dialog_fileupload")).attr('target', target);
		$dialogFileupload.dialog("open");
		$dialogFileupload.find('#select_site').combobox('setValue','UK');
		$dialogFileupload.find('#file_path').filebox({
		    buttonText: '文件选择',
		    buttonAlign: 'right'
		});
	}

	$('#btn_fileupload').on('click', function() {
		var $form = $dialogFileupload.find('#form_fileupload');
		$form.form('submit', {
			onSubmit: function() {
			    if(!$("#file_path").filebox('getValue')) {
					$.messager.alert("提示信息", "请选择导入的文件");
					return false;
				}
			    var site = $("#select_site").combobox('getValue');
			    var target = $dialogFileupload.attr('target').replace(/{site}/g, site);
			    $form.attr('action', '/ocs/excel/import/' + target);
				$.messager.progress({ title: '请稍后', msg: '导入中...' });
			},
			success: function(response) {
				$dialogFileupload.dialog("close");
				$.messager.progress('close');
				var data = 'string' === typeof response ? $.parseJSON(response) : response
					, msg = data.data.message || '';
				if($.isArray(msg)) {
					if(msg.length) {
		    			var errorHtmls = [];
		    			for(var i = 0; i < msg.length; i++) {
		    				errorHtmls.push('<li>' + msg[i] +'</li>');
		    			}
		    			var $result = $('#dialog_fileupload_result');
		    			$result.find('ul.error').html(errorHtmls.join(''));
		    			$result.dialog('open');
					} else {
		    			$.messager.show({
		    				title: '消息',
		    				msg: '导入成功',
		    				timeout: 3000,
		    				showType: 'slide'
		    			});
		    			if(value != null) {
		    				$("#lightSaleOrderdatagrid").datagrid('reload');
		    			}
					}
				} else {
		    		$.messager.alert('信息', msg || '导入失败！');
				}
		    }
		});
	});

	$(document).ready(function() {
		var buttons = $.extend([], $.fn.datebox.defaults.buttons);
		buttons.splice(0, 0, {
			text: '清空',
			handler: function(target) {
				$(target).datebox('clear').datebox("panel").panel('close');
			}
		});
		$(".easyui-datebox").datebox({ buttons: buttons });
		$("span.datebox .textbox-text").attr("readonly","true");
	});
})(lightsaleOrder, jQuery)