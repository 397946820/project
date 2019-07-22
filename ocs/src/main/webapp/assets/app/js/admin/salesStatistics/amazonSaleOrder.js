var amazonsaleOrder = {};
(function(amazonsaleOrder, $) {
	$("#amazonSaleOrderdatagrid").datagrid({
		url : GLOBAL.domain + '/amazonSaleOrder/findAll',
		columns : [ [
				{
					field : 'id',
					checkbox : true
				},
				{
					field : 'orderId',
					title : '订单号',
					align : 'center',
					sortable : true
				},
				{
					field : 'platform',
					title : '账号',
					align : 'center',
					sortable : true
				},
				{
					field : 'channel',
					title : '站点',
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
					field : 'purchaseAt',
					title : '购买时间(UTC)',
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
					field : 'customerName',
					title : '买家姓名',
					align : 'center',
					sortable : true
				},
				{
					field : 'lastestShipDate',
					title : '发货时间(UTC)',
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
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="amazonsaleOrder.detail('
								+ index
								+ ')" data-options="plain:true">详情</a> &nbsp;&nbsp;'
								+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="amazonsaleOrder.createRefundOrReissueOrder(\''
								+ index+'\',\'refund\')" data-options="plain:true">退款</a> &nbsp;&nbsp;'
								+ '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="amazonsaleOrder.createRefundOrReissueOrder(\''
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
		toolbar : "#amazonSaleOrderToolbar",
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        }
	});
	
	amazonsaleOrder.detail = function(index){
		$("#amazonSaleOrderdatagrid").datagrid('selectRow',index);
		var row = $("#amazonSaleOrderdatagrid").datagrid('getSelected');
		$("#name").text(amazonsaleOrder.isEmpty(row.name));
		$("#stateOrRegion").text(amazonsaleOrder.isEmpty(row.stateOrRegion));
		$("#postalCode").text(amazonsaleOrder.isEmpty(row.postalCode));
		$("#phone").text(amazonsaleOrder.isEmpty(row.phone));
		$("#city").text(amazonsaleOrder.isEmpty(row.city));
		$("#street").text(amazonsaleOrder.isEmpty(row.street));
		$("#amazonSaleOrderDetailDatagrid").datagrid({
			url : GLOBAL.domain + '/amazonSaleOrder/getAmazonOrderItemById/'+ row.id + "/" + row.platform,
			columns : [ [
				{
					field : 'title',
					title : '物品标题',
					align : 'center',
					width : 200,
					formatter : function(value,row,index){
						if(typeof(row.url) && row.url) {
							return "<a href='" + row.url + "' target='_blank'>" + value + "</a>";
						}
						return value;
					}
				},
				{
					field : 'sku',
					title : 'SKU',
					align : 'center',
					width : 150
				},
				{
					field : 'asin',
					title : 'ASIN',
					align : 'center',
					width : 150
				},
				{
					field : 'price',
					title : '价格',
					align : 'center',
					width : 80
				},
				{
					field : 'qty',
					title : '数量',
					align : 'center',
					width : 60
				},
				{
					field : 'TOTAL',
					title : '合计',
					align : 'center',
					width : 80,
					formatter : function(value,row,index){
						return (row.price * row.qty).toFixed(2);
					}
				}] ],
			singleSelect : true,
			rownumbers : true,
			border : true,
			onLoadSuccess : function(data) {
				if(data){
					var totalPrice = 0;
					$.each(data.rows,function(){
						totalPrice += this.price * this.qty;
					});
					$("#t_total").text(totalPrice.toFixed(2));
				}
			}
		});
		
		$("#amazonSaleOrderDetial").dialog("open");
	};
	
	amazonsaleOrder.createRefundOrReissueOrder = function(index,value) {
		var message = '';
		if(value == 'refund') {
			message = '您确定要创建退款单吗？';
		} else if(value == 'reissue') {
			message = '您确定要创建补发单吗？';
		}
		$.messager.confirm('提示', message, function(r){
			if(r) {
				$("#amazonSaleOrderdatagrid").datagrid('selectRow',index);
				var row = $("#amazonSaleOrderdatagrid").datagrid('getSelected');
				ocs.ajax({
					url:'/amazonSaleOrder/getSaleOrderRefundByParentId/'+ row.id + "/" + row.platform,
					async:true,
					type: "POST",
					success: function(result) {
						var $row = {
							platform : 'amazon',
							account : row.platform,
							site : row.channel,
							orderId : row.orderId,
							currencyCode : row.currencyCode,
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
	
	//平台改变事件
	$("#platform").combobox({
		onChange: function (newValue,oldValue) {
			if(typeof(newValue) && newValue) {
				$('#channel').combobox({
			        url: GLOBAL.domain + '/amazonSaleOrder/getChannel/'+newValue,
			        textField:'TEXT',
			        valueField:'VALUE'
				});
				$('#channel').combobox('setValue','');
			} else {
				$('#channel').combobox('loadData',[]);
			}
		}
	});
	
	amazonsaleOrder.isEmpty = function(value) {
		return value == null ? "" : value;
	}
	
	$("#amazonSaleOrderQuery").click(function(){
		var formData = $("#amazonSaleOrderSearchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#amazonSaleOrderdatagrid').datagrid('load',{
			param : param
		});
	});
	
	$("#amazonSaleOrderReset").on('click',function(){
		$("#amazonSaleOrderSearchForm").form("clear");
	});

	//英国未发货订单导出
	$("#btn_exportUKNoShipOrders").on("click", function() {
		window.location.href = "/ocs/excel/export/UKNoShipOrderExportService?platform=amazon";
	});
	
	var TARGETS = {
		MARKSHIPMENT: 'markAmazon{site}Shipment',
		UPLOADTRACKNO : 'uploadAmazon{site}TrackNo'
	};
	
	var $dialogFileupload = null;
	
	//进入标记发货界面
	$('#btn_beforeMarkShipment').on('click', function() {
		amazonsaleOrder.beforeUploadFile(TARGETS.MARKSHIPMENT);
	});
	
	//导入跟踪号
	$("#btn_beforeUploadTrackNo").on("click", function() {
		amazonsaleOrder.beforeUploadFile(TARGETS.UPLOADTRACKNO);
	});
	
	amazonsaleOrder.formatTnUploadStatus = function(value, row, index) {
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
	
	amazonsaleOrder.formatOperate = function(value, row, index) {
		return 'waiting' !== row['tnUploadStatus'] 
				? '' 
				: '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="amazonsaleOrder.cancelUpload(' + row.id + ')" data-options="plain:true" title="取消上传跟踪号">取消</a>';
	}
	
	amazonsaleOrder.formatActions = function(value, row, index) {
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
	
	amazonsaleOrder.textIndent5px = function(value, row, index) {
		return 'text-indent: 5px;';
	}
	
	amazonsaleOrder.formatDate = function(value, row, index) {
		if(!value) {
			return '';
		}
		var date = new Date(value);
		return date && date.format('yyyy-MM-dd hh:mm:ss') || '';
	}
	
	amazonsaleOrder.cancelUpload = function(id) {
		$.messager.confirm({ title: '确认', msg: '您确认要取消上传跟踪号吗？', 
			fn: function(result) {
				if(result) {
					$.ajax({
						url: '/ocs/markshipment/cancel/'+id,
						type: 'POST',
						dataType: 'json',
						success:function(data) {
							if(data) {
								$.messager.alert("提示", data.description || ('取消' + ('0' == data.errorCode ? '成功' : '失败') + '！'));	
								$("#dg_upload_record").datagrid("reload");
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

	amazonsaleOrder.getSearchParam = function(formSelector) {
		var formData = $(formSelector).serializeArray();
		var param = {};
		$.each(formData, function() {
			param[this.name] = this.value;
		});
		return param;
	}
	
	$('#customerName').combobox({
		prompt: '',
		//required: true,
		mode: 'remote',
		url: GLOBAL.domain + '/amazonSaleOrder/likeCustomerName',
		method: 'get',
		valueField: 'value',
	    textField: 'text',
		editable: true,
		hasDownArrow: false,
		onBeforeLoad: function(param) {
			if(param && param.q && param.q !== $(this).data('prev-q')) {
				$(this).data('prev-q', param.q);
				param.keyword = param.q;
				return true;
			} else {
				return false;
			}
		}
	});
	
	$(".upload-record-filter .search").click(function() {
		$("#dg_upload_record").datagrid({
			queryParams: {
				param: amazonsaleOrder.getSearchParam('#form_upload_record_filter')
			}
		});
	});
	
	$(".upload-record-filter .reset").on('click',function() {
		$("#form_upload_record_filter").form("clear");
	});
	
	$('#btn_beforeUploadRecord').on('click', function() {
		$("#dialog_tn_upload_record").dialog("open");
		$("#dg_upload_record").datagrid({
			url: '/ocs/markshipment/amazonlist',
			queryParams: {
				param: amazonsaleOrder.getSearchParam('#form_upload_record_filter')
			},
			columns: [[
				{ field: 'id', hidden: true },
				{ field: 'operate', title: '操作', halign: 'center', align: 'center', width: 40, formatter: amazonsaleOrder.formatOperate },
				{ field: 'actions', title: '动作轨迹', halign: 'center', align: 'left', width: 130, formatter: amazonsaleOrder.formatActions, styler: amazonsaleOrder.textIndent5px },
				{ field: 'account', title: '账号', halign: 'center', align: 'center', width: 40 },
				{ field: 'orderId', title: '订单ID', halign: 'center', align: 'left', width: 130, styler: amazonsaleOrder.textIndent5px },
				{ field: 'itemId', title: '明细ID', halign: 'center', align: 'left', width: 130, styler: amazonsaleOrder.textIndent5px },
				{ field: 'tn01', title: '运输号', halign: 'center', align: 'left', width: 150, styler: amazonsaleOrder.textIndent5px },
				{ field: 'carrier01', title: '运输服务', halign: 'center', align: 'center', width: 80 },
				{ field: 'tnUploadStatus', title: '上传状态', halign: 'center', align: 'center', width: 80, formatter: amazonsaleOrder.formatTnUploadStatus },
				{ field : 'shippedAt', title: '成功上传时间', halign: 'center', align: 'center', width: 120, formatter: amazonsaleOrder.formatDate },
				{ field : 'tnInitAt', title: '跟踪号导入时间', halign: 'center', align: 'center', width: 120, formatter: amazonsaleOrder.formatDate },
				{ field : 'createdAt', title: '数据导入时间', halign: 'center', align: 'center', width: 120, formatter: amazonsaleOrder.formatDate },
				{ field : 'cause', title: '异常说明', halign: 'center', align: 'left', width: 280, styler: amazonsaleOrder.textIndent5px }
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
	
	amazonsaleOrder.beforeUploadFile = function(target) {
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
})(amazonsaleOrder, jQuery)