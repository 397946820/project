var usWestShipping = {};
(function(usWestShipping,$){
	var curOrderId = undefined;
	var curAccount = undefined;
	usWestShipping.modifyAddress = function(){
		var vf = $("#addressModifyfm").form("validate");
		if(vf){
			var formData = $("#addressModifyfm").serializeArray();
			var fdata = {};
			$.each(formData,function(){
				var cname = this.name;
				cname = cname.slice(0, 1).toUpperCase() + cname.slice(1);  
				fdata[cname] = this.value;
			});
			fdata["orderId"] = curOrderId;
			fdata["account"] = curAccount;
			//更新数据
			ocs.ajax({
				url:"/edaManager/addressUpdate",
				async:true,
				type:"POST",
				data : fdata,
				success : function(result){
					if(result){
						$("#westShippingAddressModifyWin").dialog("close");
						$("#westShippingList").datagrid('reload');
						$.messager.alert("信息","修改成功！");	
					}
				}
			});
		}
	}
	
	$("#modifyAddressBtn").click(function(){
		var rows = $("#westShippingList").datagrid("getChecked");
		if(rows&&rows.length == 1){
			if(rows[0].status != 0){
				$.messager.alert('提示','此订单已发货，不能修改地址!','warning');
				return;
			}
			curOrderId = rows[0].merchantFulfillmentOrderID;
			curAccount = rows[0].account;
			$("#addressModifyfm").form("clear");
			$("#addressModifyfm").form("load",rows[0]);
			$("#westShippingAddressModifyWin").dialog("open");
		}else{
			$.messager.alert('提示','请您只能勾选一条数据修改!','warning');
		}
	});
	
	/*
	 * 转东仓发货
	 */
	usWestShipping.toEastShipping = function(id){
		$.messager.confirm('确认', '是否确定使用东仓发货?', function(r){
			if (r){
				ocs.ajax({
					url:'/edaManager/toEastShipping/'+id,
					async:true,
					beforeSend: function () {
	                   $.messager.progress({
	                       title: '请稍后',
	                       msg: '正在转仓中...'
	                   });
	                },
		            complete: function () {
		               $.messager.progress('close');
		            },
					type: "POST",
					success: function(result) {
						if(result.data){
							$("#westShippingList").datagrid('reload');
							$.messager.alert("信息","转换成功！");	
						}else{
							$.messager.alert("信息","转东仓失败，请稍后重试！");	
						}
					}
			   });
			}
		});
	}
	
	/*
	 * 取消
	 */
	usWestShipping.cancelOrder = function(id){
		$.messager.confirm('确认', '是否确定取消发货?', function(r){
			if (r){
				ocs.ajax({
					url:'/edaManager/cancelWestOrderById/'+id,
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
							$("#westShippingList").datagrid('reload');
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
		});
		
	}
	
	$("#westShippingList").datagrid({
		url : '/ocs/edaManager/westOrderList',
		queryParams : {
			param : {
				platform:'',
				status:'',
				MerchantSKU : '',
				CarrierServiceTypeCode : '',
				orderId :'',
				timeStart:'',
				timeEnd :'',
				optTtimeStart : '',
				optTimeEnd : ''
			}
		},
		columns : [ [
				
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'is_hand',
					hidden:true
				},
				{
					field : 'ck',
					checkbox:true
				},
				{
					field : 'opt',
					title : '操作',
					align : 'center',
					width : 80,
					formatter : function(value,row,index){
						var orderNum = row.status;
						if(orderNum == 0){
							var isHand = row.is_hand;
							if(isHand == 0){
								return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="usWestShipping.cancelOrder(\''+row.id+'\')"  data-options="plain:true">取消</a>&nbsp;&nbsp;'+
								'<a href="javascript:void(0);" class="easyui-linkbutton" onClick="usWestShipping.toEastShipping(\''+row.orderId+'\')"  data-options="plain:true">转东仓</a>';
					
							}else{
								return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="usWestShipping.cancelOrder(\''+row.id+'\')"  data-options="plain:true">取消</a>&nbsp;&nbsp;';
					
							}
						}
					}
				},
				{
					field : 'status',
					title : 'Status',
					align : 'center',
					width : 80,
					formatter:function(value,row,index){
						//0 未发货 1 已发货 2已上传 3上传失败 4已取消 5转东部仓库
						if(value == 0){
							return '未发货 '
						}else if(value == 1){
							return '已发货'
						}else if(value == 2){
							return '上传平台成功'
						}else if(value == 3){
							return '上传平台失败'
						}else if(value == 4){
							return '已取消'
						}else if(value == 5){
							return '已转东仓'
						}else if(value == 6){
							return '已手工分仓'
						}else if(value == 7){
							return '补发已完成'
						}
					}
				},
				{
					field : 'platform',
					title : 'platform',
					align : 'center',
					width : 80
				},
				{
					field : 'account',
					title : 'account',
					align : 'center',
					width : 100
				},
				{
					field : 'orderId',
					title : 'orderId',
					align : 'center',
					width : 90
				},
				{
					field : 'merchantFulfillmentOrderID',
					title : 'MerchantFulfillmentOrderID',
					align : 'center',
					width : 180
				},
				{
					field : 'edaId',
					title : 'EDA东仓ID',
					align : 'center',
					width : 120,
					formatter : function(value,row,index){
						if(row.status == 5){
							var view = "EDA未创建发货单";
							//获取eda订单号
							ocs.ajax({
								url:"/edaManager/getEdaInfoForToEastOrder/"+row.merchantFulfillmentOrderID,
								async:false,
								type:"POST",
								data : "",
								success : function(result){
									if(result.data){
										view = result.data;
									}
								}
							});
							return '<span style="color:green;">'+view+'</span>';
						}else{
							return "";
						}
					}
				},
				{
					field : 'isMPS',
					title : 'isMPS',
					align : 'center',
					width : 80
				},
				{
					field : 'displayableOrderID',
					title : 'DisplayableOrderID',
					align : 'left',
					width : 180
				},
				{
					field : 'displayableOrderDate',
					title : 'DisplayableOrderDate',
					align : 'center',
					width : 150,
					formatter : function(value,row,index){
						return value.replace(".0","");
					}
				},
				{
					field : 'merchantSKU',
					title : 'MerchantSKU',
					align : 'center',
					width : 100
				},
				{
					field : 'quantity',
					title : 'Quantity',
					align : 'center',
					width : 80
				},
				{
					field : 'merchantFulfillmentOrderItemID',
					title : 'MerchantFulfillmentOrderItemID',
					align : 'center',
					width : 180
				},
				{
					field : 'giftMessage',
					title : 'GiftMessage',
					align : 'center',
					width : 80
				},
				{
					field : 'displayableComment',
					title : 'DisplayableComment',
					align : 'center',
					width : 100
				},
				{
					field : 'perUnitDeclaredValue',
					title : 'PerUnitDeclaredValue',
					align : 'center',
					width : 120
				},
				{
					field : 'displayableOrderComment',
					title : 'DisplayableOrderComment',
					align : 'center',
					width : 160
				},
				{
					field : 'deliverySLA',
					title : 'DeliverySLA',
					align : 'center',
					width : 90
				},
				{
					field : 'addressName',
					title : 'AddressName',
					align : 'center',
					width : 120
				},
				{
					field : 'addressFieldOne',
					title : 'AddressFieldOne',
					align : 'center',
					width : 150
				},
				{
					field : 'addressFieldTwo',
					title : 'AddressFieldTwo',
					align : 'center',
					width : 120
				},
				{
					field : 'addressFieldThree',
					title : 'AddressFieldThree',
					align : 'center',
					width : 80
				},
				{
					field : 'addressCity',
					title : 'AddressCity',
					align : 'center',
					width : 100
				},
				{
					field : 'addressCountryCode',
					title : 'AddressCountryCode',
					align : 'center',
					width : 100
				},
				{
					field : 'addressStateOrRegion',
					title : 'AddressStateOrRegion',
					align : 'center',
					width : 100
				},
				{
					field : 'addressPostalCode',
					title : 'AddressPostalCode',
					align : 'center',
					width : 100
				},
				{
					field : 'addressPhoneNumber',
					title : 'AddressPhoneNumber',
					align : 'center',
					width : 100
				},
				{
					field : 'notificationEmail',
					title : 'NotificationEmail',
					align : 'center',
					width : 100
				},
				{
					field : 'carrierServiceTypeCode',
					title : 'CarrierServiceTypeCode',
					align : 'center',
					width : 100
				},
				{
					field : 'packageLength',
					title : 'PackageLength',
					align : 'center',
					width : 100
				},
				{
					field : 'packageWidth',
					title : 'PackageWidth',
					align : 'center',
					width : 100
				},
				{
					field : 'packageHeight',
					title : 'PackageHeight',
					align : 'center',
					width : 100
				},
				{
					field : 'packageWeight',
					title : 'PackageWeight',
					align : 'center',
					width : 100
				},
				{
					field : 'tranckingNumber',
					title : 'TranckingNumber',
					align : 'center',
					width : 120
				}] ],
		//singleSelect : true,
		rownumbers : true,
		//fitColumns : true,
		border : true,
		nowrap:false,
		pagination : true,
		pageSize : 50,
		toolbar : "#westShippingSearchParam-panel"
	});
	
	$("#orderImport").click(function(){
		$("#fileUpload").dialog("open");
		$('#fileUploadInput').filebox({
		    buttonText: '文件选择',
		    buttonAlign: 'right'
		});
		$("#uploadSubmit").unbind("click").click(function(){
			$('#ImportForm').form('submit', {
				onSubmit: function(){
					$.messager.progress({
                       title: '请稍后',
                       msg: '导入中...'
                    });
				},
				success:function(data){
					$("#fileUpload").dialog("close");
					$.messager.progress('close');
					var resu = undefined;
					var message = undefined;
					if(data&&data.indexOf("{")==0){
						data = eval('('+data+')');
						resu = data.data;
					}
					
					if(resu == undefined){
						$.messager.alert("信息","导入失败！");
						return ;
					}else{
						message = resu.message;
					}
			    	if(message){
			    		if(message instanceof Array){
				    		if(message.length > 0){
				    			//message
				    			var error = [];
				    			$.each(message,function(){
				    				error.push("<li>"+this+"</li>");
				    			});
				    			$("#saleTranImportErrorList").html(error.join(""));
				    			$("#saleTranImportMessageWin").dialog("open");
				    		}else{
				    			$("#westShippingList").datagrid("reload");
				    			$.messager.show({
				    				title:'消息',
				    				msg:'导入成功',
				    				timeout:3000,
				    				showType:'slide'
				    			});
				    		}
				    	}else{
				    		$.messager.alert("信息",message);	
				    	}
			    	}else{
			    		$.messager.alert("信息","导入失败！");	
			    	}
			    }
			});
		});
	});
	
	$("#westShippingSearch").click(function(){
		var formData = $("#westShippingSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#westShippingList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#westShippingList").datagrid("reload");
	});
	
	$("#westShippingReset").on('click',function(){
		$("#westShippingSearchParam").form("clear");
	});
	
	function exportOrderByCondition(t){
		var formData = $("#westShippingSearchParam").serializeArray();
		var param = [];
		param.push("?");
		$.each(formData,function(){
			param.push(this.name+"="+this.value+"&");
		});
		param.push("exportType="+t);
		window.location.href = "/ocs/excel/export/orderShippingWestExport"+param.join("");
	}
	
	//导出未发货
	$("#orderExport").click(function(){
		exportOrderByCondition(1);
	});
	
	//根据条件导出全部
	$("#allOrderByComditionExport").click(function(){
		exportOrderByCondition(2);
	});
	
	$("#chooseOrderExport").click(function(){
		var rows = $("#westShippingList").datagrid("getChecked");
		if(rows&&rows.length > 0){
			var ids = [];
			$.each(rows,function(){
				ids.push(this.id);
			})
			window.location.href = "/ocs/excel/export/orderShippingWestChooseExport?ids="+ids.join(",");
		}else{
			$.messager.alert('提示','请至少选择一条数据导出!','warning');
		}
		
	});

})(usWestShipping,jQuery);