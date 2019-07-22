var edaManager = {};
(function(edaManager,$){
	var edaStatus = {
			'UNPROCESSED':'未处理',
			'INV_CONFIRM_WL':'INV_CONFIRM_WL',
			'INV_CONFIRM_WOL':'INV_CONFIRM_WOL',
			'INV_ERROR'	:'库存不足',
			'SHIPMENT_ERROR_WOL':'发货错误',
			'SHIPMENT_ERROR_WL':'发货错误',
			'WAIT_CNFM_PCKNG_WOL':'等待确认打包',
			'LABELLED_WOL':'标签已生成',
			'WT_CNFM_SHPMNT':'待确认发货',
			'SHIPMENT_CONFIRMED':'发货确认',
			'UPLOAD_TRACKING_NUM':'追踪号上传',
			'UPLOAD_TRACKING_NUM_ERROR':'追踪号上传失败',
			'SHIPPED':'已发货',
			'SAVE_ERROR':'保存发票失败',
			'INVOICED':'发票已保存',
			'PAID':'已付款发票',
			'DISTR_ERROR':'<span style="color:red">分仓错误</span>',
			'NO_MAPPING':'<span style="color:red">SKU没有映射</span>',
			'INSUF_BALANCE':'<span style="color:red">余额不足</span>',
			'CANCELLED':'已取消'
	};
	var edaStatusOrder = {
			'UNPROCESSED':1,
			'INV_CONFIRM_WL':2,
			'INV_CONFIRM_WOL':3,
			'INV_ERROR'	:4,
			'SHIPMENT_ERROR_WOL':5,
			'SHIPMENT_ERROR_WL':6,
			'WAIT_CNFM_PCKNG_WOL':7,
			'LABELLED_WOL':8,
			'WT_CNFM_SHPMNT':9,
			'SHIPMENT_CONFIRMED':10,
			'UPLOAD_TRACKING_NUM':11,
			'UPLOAD_TRACKING_NUM_ERROR':12,
			'SHIPPED':13,
			'SAVE_ERROR':14,
			'INVOICED':15,
			'PAID':16,
			'DISTR_ERROR':1,
			'NO_MAPPING':1,
			'INSUF_BALANCE':1,
			'CANCELLED':99
	};
	var curId = undefined;
	var curOrderId = "";
	edaManager.modifyAddress = function(){
		var vf = $("#addressModifyfm").form("validate");
		if(vf){
			var formData = $("#addressModifyfm").serializeArray();
			var fdata = {};
			$.each(formData,function(){
				fdata[this.name] = this.value;
			});
			var saveData = {};
			saveData["id"] = curId;
			saveData["shipAddress"] = JSON.stringify(fdata);
			//更新数据
			ocs.ajax({
				url:"/edaManager/edaAddressUpdate",
				async:true,
				type:"POST",
				data : saveData,
				success : function(result){
					if(result){
						$("#edaShippingAddressModifyWin").dialog("close");
						$("#edaManagerList").datagrid('reload');
						$.messager.alert("信息","修改成功！");	
					}
				}
			});
		}
	}
	$("#modifyAddressBtn").click(function(){
		var rows = $("#edaManagerList").datagrid("getChecked");
		if(rows&&rows.length == 1){
			if(rows[0].isCreate != 0){
				$.messager.alert('提示','此订单已发货，不能修改地址!','warning');
				return;
			}
			curId = rows[0].id;
			var addresses = eval("("+rows[0].shippingAddress+")");
			var ad1 = addresses["addressLine1"];
			//去掉首尾空白
			ad1 = ad1.replace(/(^\s*)|(\s*$)/g,"");
			//根据换行符分开数据
			var ad1Split = ad1.split(/\n/);
			addresses["addressLine1"] = ad1Split[0];
			var ad2 = addresses["addressLine2"];
			if(!ad2){
				ad2="";
				addresses["addressLine2"] = ad2;
			}else{
				ad2 = " " + ad2;
			}
			if(ad1Split.length > 1){
				addresses["addressLine2"] = ad1Split[1]+ad2 ;
			}
			
			$("#addressModifyfm").form("clear");
			$("#addressModifyfm").form("load",addresses);
			$("#edaShippingAddressModifyWin").dialog("open");
		}else{
			$.messager.alert('提示','请您只能勾选一条数据修改!','warning');
		}
	});
	edaManager.syncEADOrderInfo = function(edaId){
		ocs.ajax({
			url:'/edaManager/getEDAOrderInfoById/'+edaId,
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
					$("#edaManagerList").datagrid('reload');
					$.messager.alert("信息","同步成功！");	
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//$.messager.alert("信息", "服务器发生错误！");		
			}
	   });
	};
	
	edaManager.cancelEDAOrder = function(id){
		ocs.ajax({
			url:'/edaManager/cancelEDAOrderById/'+id,
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
				var flag=result.data;
				if(flag){
					$("#edaManagerList").datagrid('reload');
					$.messager.alert("信息","取消成功！");	
				}else{
					$.messager.alert("信息","取消失败,请重试！");	
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//$.messager.alert("信息", "服务器发生错误！");		
			}
	   });
	}
	
	$("#edaManagerList").datagrid({
		url : '/ocs/edaManager/list',
		queryParams : {
			param : {
				edaPlatformOrderId:'',
				orderId:'',
				timeStart : '',
				timeEnd : ''
				
			}
		},
		columns : [ [
				{
					field : 'ck',
					checkbox:true
				},
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'shippingAddress',
					hidden:true
				},
				{
					field : 'isCreate',
					title : 'EDA发货单',
					align : 'center',
					width : 60,
					formatter : function(value,row,index){
						if(value == 1){
							return '【<span">已创建</span>】';
						}else if(value == 0){
							return '【<span style="color:red">未创建</span>】';
						}else if(value == 2){
							return '【<span style="color:green">已取消</span>】';
						}
					}
				},
				{
					field : 'platform',
					title : '平台',
					align : 'center',
					width : 60
				},
				{
					field : 'orderId',
					title : '订单ID',
					align : 'center',
					width : 160
				},
				{
					field : 'edaOrderId',
					title : 'EDA发货单ID',
					align : 'center',
					width : 100,
					formatter : function(value,row,index){
						if(value){
							return value;
						}else{
							return '--'
						}
					}
				},
				{
					field : 'edaPlatformOrderId',
					title : 'EDA发货单平台ID',
					align : 'center',
					width : 80,
					formatter : function(value,row,index){
						if(value){
							return value;
						}else{
							return '--'
						}
					}
				},
				{
					field : 'edaAccount',
					title : 'EDA平台账号',
					align : 'center',
					width : 100
				},
				{
					field : 'skuInfo',
					title : 'SKU/数量',
					align : 'left',
					width : 120,
					formatter : function(value,row,index){
						if(value){
							var items = eval("("+value+")");
							var str = [];
							$.each(items,function(){
								var sku = this.channelSku;
								if(!sku){
									sku = this.warehouseSku;
								}
								str.push('<span style="color:green">'+sku+'</span>&nbsp;:&nbsp;'+this.quantity+'<br/>');
							});
							return str.join("");
						}
						return "";
					}
				},
				{
					field : 'buyerName',
					title : '收件人',
					align : 'center',
					width : 100,
					remarkFormatter:function(value,row,index){
						return value;
					}
				},
				{
					field : 'shipDate',
					title : '发货时间',
					align : 'center',
					width : 100
				},
				{
					field : 'shipInfo',
					title : '快递信息',
					align : 'center',
					width : 100
				},
				{
					field : 'shipStatus',
					title : '发货单状态',
					align : 'center',
					width : 80,
					formatter : function(value,row,index){
						if(value){
							var str = edaStatus[value];
							if(str){
								return "["+str+"]";
							}else{
								if(row.isCreate == 2){
									return "[已取消]";
								}else{
									return "["+value+"]";
								}
								
							}
						}else{
							return "[ -- ]";
						}
						
						
					}
				},
				{
					field : 'edaCreateDate',
					title : 'EDA发货单创建时间',
					align : 'center',
					width : 120
				},
				{
					field : 'opt',
					title : '操作',
					align : 'center',
					width : 80,
					formatter : function(value,row,index){
						var orderNum = edaStatusOrder[row.shipStatus];
						var isCreateStatus = row.isCreate;
						if(orderNum){
							if(orderNum < 7){
								return  '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="edaManager.cancelEDAOrder(\''+row.id+'\')"  data-options="plain:true">取消</a> &nbsp;&nbsp;'+
								   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="edaManager.syncEADOrderInfo(\''+row.edaPlatformOrderId+'\')" data-options="plain:true">同步</a>';
							}else{
								if(isCreateStatus == 1){
									return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="edaManager.syncEADOrderInfo(\''+row.edaPlatformOrderId+'\')" data-options="plain:true">同步</a>';
								}else{
									if(row.isCreate != 2){
										return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="edaManager.cancelEDAOrder(\''+row.id+'\')"  data-options="plain:true">取消</a>';
									}else{
										return '';
									}
								}
							}
						}else{
							if(isCreateStatus == 1){
								return 
								   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="edaManager.syncEADOrderInfo(\''+row.edaPlatformOrderId+'\')" data-options="plain:true">同步</a>';
							}else{
								if(row.isCreate != 2){
									return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="edaManager.cancelEDAOrder(\''+row.id+'\')"  data-options="plain:true">取消</a>';
								}else{
									return '';
								}
							}
						}
						
					}
				}] ],
		singleSelect : true,
		rownumbers : true,
		fitColumns : true,
		border : true,
		nowrap:false,
		pagination : true,
		pageSize : 50,
		toolbar : "#edaManagerSearchParam-panel"
	});
	$("#edaManagerSearch").click(function(){
		var formData = $("#edaManagerSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#edaManagerList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#edaManagerList").datagrid("reload");
	});
	
	$("#edaManagerReset").on('click',function(){
		$("#edaManagerSearchParam").form("clear");
	});
	
	$("#noShipOrderOutByEast").click(function(){
		window.location.href = "/ocs/excel/export/orderShippingEastExport";
	});
	
	$("#timeExport").click(function(){
		var formData = $("#edaManagerSearchParam").serializeArray();
		var param = [];
		param.push("?");
		$.each(formData,function(){
			if(param.join("") == "?"){
				param.push(this.name+"="+this.value);
			}else{
				param.push("&"+this.name+"="+this.value);
			}
		});
		window.location.href = "/ocs/excel/export/orderShippingEastByConditionExport"+param.join("");
	});
	//新增发货单
	$("#addEDAShipOrder").click(function(){
		$("#skuListGrid").datagrid({
			height: 250,
			url : '',
			queryParams : {
				param : {
					sku:'',
				}
			},
			columns : [ [
					{
						field : 'isEast',
						title : '发货仓库',
						width : 100,
						editor : {
							type:'combobox',
							options:{
								valueField:'iid',
								textField:'iname',
								data :[{'iid':0,'iname':'东仓'},{'iid':1,'iname':'西仓'},{'iid':2,'iname':'不发货'}] ,
								required:true
							}
						},
						formatter : function(value,row,index){
							if(value == 0){
								return '东仓';
							}else if(value == 1){
								return '西仓'
							}else if(value == 2){
								return '不发货';
							}else{
								return '';
							}
						}
					},
					{
						field : 'wstatus',
						hidden:true
					},
					{
						field : 'PLATFORM',
						hidden:true
					},
					{
						field : 'ACCOUNT',
						hidden:true
					},
					{
						field : 'CREATETIME',
						hidden:true
					},
					{
						field : 'OCSID',
						hidden:true
					},
					{
						field : 'OCSITEMID',
						hidden:true
					}, 
					{
						field : 'SKU',
						title : 'SKU',
						align : 'center',
						width : 100
					},
					{
						field : 'QTY',
						title : '数量',
						align : 'center',
						width : 100
					},
					{
						field : 'estatus',
						title : '状态',
						align : 'center',
						width : 100,
						formatter : function(value,row,index){
							var str = "";
							if(value || row.wstatus){
								if(value){
									str = '<span style="color:red">EDA推送列表有此数据.</span><br/>';
								}
								if(row.wstatus){
									str = str + '<span style="color:red">美西仓发货列表有此数据.</span><br/>';
								}
							}
							return str;
						}
					}
					] ],
			singleSelect : false,
			rownumbers : true,
			fitColumns : true,
			border : true,
			nowrap:false,
			buttons : '#orderSkuList',
			onClickRow : function(index){
				$('#skuListGrid').datagrid('selectRow', index)
				.datagrid('beginEdit', index);
			}
		});
		$("#addEdaOrderWin").dialog('open');
		$("#addEdaOrderWin").dialog('center');
	});
	$("#orderSearch").click(function(){
		//获取平台和sku信息
		var formData = $("#orderSearchFrm").serializeArray();
		var param = {};
		var f = false;
		$.each(formData,function(){
			if(this.value){
				param[this.name] = this.value;
			}else{
				f = true;
				return false;
			}
		});
		if(f){
			$.messager.alert("信息","请输入订单号后搜索！");	
			return ;
		}
		curOrderId = param["orderId"];
		//拉取数据
		ocs.ajax({
			url:'/edaManager/getOrder',
			data:param,
			async:true,
			type: "POST",
			success: function(result) {
				if(result){
					$("#skuListGrid").datagrid("loadData",result);
					var address = eval("("+result[0].address+")");
					$("#addEdaOrderfm").form("load",address);
				}else{
					$("#skuListGrid").datagrid("load",[]);
					$("#addEdaOrderfm").form("clear");
				}
			}
		});
	});
	
	edaManager.addEdaOrderOk = function(){
		$("#skuListGrid").datagrid("acceptChanges");
		var skuData = $("#skuListGrid").datagrid("getData");
		var skus = skuData["rows"];
		//验证选择
		for(var i = 0;i<skus.length;i++){
			var row = skus[i];
			if(!row.isEast){
				$.messager.alert("信息","请为每一个sku选择发货仓库！");	
				return;
			}
		}
		//验证发货地址信息
		var f = $("#addEdaOrderfm").form("validate");
		if(!f){
			$.messager.alert("信息","发货地址红色输入框为必填项！");	
			return;
		}
		var address = {};
		var addressformData = $("#addEdaOrderfm").serializeArray();
		$.each(addressformData,function(){
			if(this.value){
				address[this.name] = this.value;
			}else{
				address[this.name] = '';
			}
		});
		var orderData = {};
		orderData["skus"] = skus;
		orderData["address"] = JSON.stringify(address);
		orderData["orderId"] = curOrderId;
		//保存数据
		ocs.ajax({
			url:'/edaManager/addOrder',
			data:orderData,
			async:true,
			type: "POST",
			success: function(result) {
				if(result.data){
					if(result.data == "success"){
						$("#skuListGrid").datagrid("load",[]);
						$("#addEdaOrderfm").form("clear");
						$("#edaManagerList").datagrid("reload");
						$("#addEdaOrderWin").dialog('close');
					}else{
						$.messager.alert("信息",result.description);	
						return;
					}
					
				}
			}
		});
	}
})(edaManager,jQuery)