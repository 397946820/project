var saleManager = {};
(function(saleManager,$){
	var curRow = undefined;
	var curStatus = 0;
	var curLabelId = undefined;
	var value = null;
	var curId = '';
	var hasLoadLabel = false;
	var viewMoreChoose = false;//javascript:$('#moreSeachTa').show();
	//备注
	saleManager.remark = function(id,index){
		curId = id;
		var rows = $('#syncSaleInfoTable').datagrid('getRows');
		var row = rows[index];
		if(row.remark){
			$("#orderRemark").textbox("setValue",row.remark);
		}
		$("#ebayOrderRemark").dialog("open");
	}
	//更新单个订单
	saleManager.updateSale = function(orderId,account){
		ocs.ajax({
			url:'/ebaySeller/syncOrderByOrderId/'+orderId+'/'+account+'/1',
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
				if(result.data){
					$("#syncSaleInfoTable").datagrid('reload');
					$.messager.alert("信息","更新成功！");	
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//$.messager.alert("信息", "服务器发生错误！");		
			}
	   });
	}
	
	//上传跟踪运输号
	saleManager.uploadSaleTranNumber = function(id){
		$("#saleTranNumberUpload").dialog({
			buttons:[{
				text:'确定',
//				iconCls:'icon-ok',
				handler:function(){
					//保存数据
					$("#saleTranNumberUploadTable").datagrid('acceptChanges');
					var data = $("#saleTranNumberUploadTable").datagrid('getRows');
					ocs.ajax({
						async:true,
						type:'POST',
						url:'/ebaySeller/uploadTranNumber',
						beforeSend: function () {
		                       $.messager.progress({
		                           title: '请稍后',
		                           msg: '正在努力上传中...'
		                       });
		                },
		                complete: function () {
		                   $.messager.progress('close');
		                },
						data:{"row":data},
						success:function(result){
							if(result.data){
								$.messager.alert("信息","上传失败，请确认信息是否填充正确，确认后请重试！");	
							}else{
								$("#saleTranNumberUpload").dialog("close");
								$.messager.show({
				    				title:'消息',
				    				msg:'上传成功',
				    				timeout:3000,
				    				showType:'slide'
				    			});
							}
						}
					});
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$("#saleTranNumberUpload").dialog("close");
				}
			}]
		});
		
		$("#saleTranNumberUpload").dialog("open");
		$("#saleTranNumberUploadTable").datagrid({
			url : '/ocs/ebaySeller/getSubItemById/'+id,
			frozenColumns:[[
				{
					field : 'orderId',
					hidden : true
				},
				{
					field : 'itemId',
					hidden : true
				},
				{
					field : 'ebayAccount',
					hidden : true
				},
				{
					field : 'transactionId',
					title : '交易号',
					align : 'center',
					width : 80
				},
				{
					field : 'title',
					title : '标题/SKU',
					align : 'center',
					width : 150,
					formatter: function(value,row,index){
						return '<span>'+row.title+'</span><br/><span style="color:green;">'+row.sku+'</span>';
					}
				},
				{
					field : 'quantity',
					title : '数量',
					align : 'center',
					width : 40
				},
				{
					field : 'saleRecordNumber',
					title : '销售编号',
					align : 'center',
					width : 80
				}
			]],
			columns : [ [
				{
					field : 'trackingNumber',
					title : '跟踪号',
					align : 'center',
					width : 100,
					editor:'text'
				},
				{
					field : 'carrier',
					title : '承运商',
					align : 'center',
					width : 100,
					editor:'text'
				},
				{
					field : 'method',
					title : '运输方式',
					align : 'center',
					width : 100,
					editor:'text'
				},
				{
					field : 'trackingNumber2',
					title : '跟踪号2',
					align : 'center',
					width : 100,
					editor:'text'
				},
				{
					field : 'carrier2',
					title : '承运商2',
					align : 'center',
					width : 100,
					editor:'text'
				},
				{
					field : 'method2',
					title : '运输方式2',
					align : 'center',
					width : 100,
					editor:'text'
				},
				{
					field : 'trackingNumber3',
					title : '跟踪号3',
					align : 'center',
					width : 100,
					editor:'text'
				},
				{
					field : 'carrier3',
					title : '承运商3',
					align : 'center',
					width : 100,
					editor:'text'
				},
				{
					field : 'method3',
					title : '运输方式3',
					align : 'center',
					width : 100,
					editor:'text'
				}] ],
			idField : 'id',
			singleSelect : true,
			rownumbers : true,
			border : true,
			onSelect:function(index,row){
				$(this).datagrid('beginEdit', index);
			},
			onUnselect:function(index,row){
				$(this).datagrid('endEdit', index);
			}
		});
	}
	
	saleManager.cancelOrder = function(id){
		$.messager.confirm('提示', "您确定取消订单吗？", function(r){
			if (r){
				$("#saleCancelOrderWin").dialog({
					buttons:[{
						text:'确定',
						handler:function(){
							var casuse = $("#orderCancelCause").combobox("getValue");
							ocs.ajax({
				    			url:'/ebaySeller/cancelOrder/'+id+'/'+casuse,
				    			async:true,
				    		    beforeSend: function () {
			                       $.messager.progress({
			                           title: '请稍后',
			                           msg: '正在发送中...'
			                       });
				                },
				                complete: function () {
				                   $.messager.progress('close');
				                },
				    			type: "POST",
				    			success: function(result) {
				    				var me = result.data;
				    				if(me=="success"){
				    					$("#syncSaleInfoTable").datagrid('reload');
										$.messager.alert("信息","操作成功！","info");
										$("#saleCancelOrderWin").dialog("close");
									}else{
										$.messager.alert("信息",result.description,"warning");	
									}
				    			}
				    	    });
						}
					},{
						text:'取消',
						iconCls:'icon-no',
						handler:function(){
							$("#saleCancelOrderWin").dialog("close");
						}
					}]
				});
				$("#saleCancelOrderWin").dialog("open");
			}
		});
	}
	$("#syncOrderList").on("click",function(){
		ocs.ajax({
			url:'/ebaySeller/syncOrderList',
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
					$("#syncSaleInfoTable").datagrid('reload');
					$.messager.alert("信息","同步成功！");	
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//$.messager.alert("信息", "服务器发生错误！");		
			}
	   });
		
	})
	$("#leadingOut").on("click",function(){
		window.location.href = "/ocs/excel/export/saleItemInfoExport";
	});
	//德国未发货订单导出
	$("#noShipOrderOutPutDE").on("click",function(){
		window.location.href = "/ocs/excel/export/DENoShipOrderExportService";
	});
	//英国未发货订单导出
	$("#noShipOrderOutPutUK").on("click",function(){
		window.location.href = "/ocs/excel/export/UKNoShipOrderExportService";
	});
	$("#addMoreTempDownload").on("click",function(){
		window.location.href = "/ocs/excel/template/saleShipInfoImport";
	});
	//美国OS订单导出
	$("#osNoShipOrderOutPutUS").on("click",function(){
		window.location.href = "/ocs/excel/export/ebayOSOrderNoShipExportService";
	});
	//小包订单导出
	$("#smallPKNoShipOrderOutput").on("click",function(){
		window.location.href = "/ocs/excel/export/ebaySmallPackageOrderExportService";
	});
	saleManager.uploadFile = function() {
		$("#fileUpload").dialog("open");
		$("#importSite").combobox('setValue','UK');
		$('#fileUploadInput').filebox({
		    buttonText: '文件选择',
		    buttonAlign: 'right'
		});
	}
	
	$("#uploadSubmit").click(function(){
		$('#ImportForm').form('submit', {
			onSubmit: function(){
				var file = $("#fileUploadInput").filebox('getValue'); 
			    if(file == '' || file == null) {
					$.messager.alert("提示信息", "请选择导入的文件");
					return false;
				}
			    var site = $("#importSite").combobox('getValue');
			    if(value != null) {
			    	if(site == 'UK') {
			    		$('#ImportForm').attr("action","/ocs/excel/import/shippingMarkUkImport");
			    	} else if(site == 'DE') {
			    		$('#ImportForm').attr("action","/ocs/excel/import/shippingMarkDeImport");
			    	}else if(site == 'OS'){
			    		$('#ImportForm').attr("action","/ocs/excel/import/ebayOSOrderShipInfoMarkService");
			    	}else if(site == 'smallPackage'){
			    		$('#ImportForm').attr("action","/ocs/excel/import/ebaySmallPackageMarkService");
			    	}
			    } else {
			    	if(site == 'UK') {
			    		$('#ImportForm').attr("action","/ocs/excel/import/saleShipInfoImport");
			    	} else if(site == 'DE') {
			    		$('#ImportForm').attr("action","/ocs/excel/import/saleShipInfoDeImport");
			    	}else if(site == 'OS'){
			    		$('#ImportForm').attr("action","/ocs/excel/import/ebayOSOrderShipInfoImport");
			    	}else if(site == 'smallPackage'){
			    		$('#ImportForm').attr("action","/ocs/excel/import/ebaySmallPackageShipService");
			    	}
			    }
				$.messager.progress({
                   title: '请稍后',
                   msg: '导入中...'
                });
			},
			success:function(data){
				$("#fileUpload").dialog("close");
				$.messager.progress('close');
				data = eval('('+data+')');
		    	var message = data.data.message;
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
			    			$.messager.show({
			    				title:'消息',
			    				msg:'导入成功',
			    				timeout:3000,
			    				showType:'slide'
			    			});
			    			if(value != null) {
			    				$("#syncSaleInfoTable").datagrid('reload');
			    			}
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
	//导入跟踪号
	$("#addMore").on("click",function(){
		value = null;
		saleManager.uploadFile();
	})
	//标记发货
	$("#shippingMark").on("click",function(){
		value = 'value';
		saleManager.uploadFile();
	});
	
	$("#checkOutModel").on("click",function(){
		window.location.href = "/ocs/excel/template/saleShipInfoImport";
	})
	$("#setBill").on("click",function(){
		var data = []
		var rows = $("#syncSaleInfoTable").datagrid("getChecked");
		if(rows){
			$.each(rows,function(){
				var order = {};
				order["orderId"] = this.orderId;
				order["siteId"] = this.siteId;
				order["account"] = this.account;
				data.push(order);
			});
		}
		if(data.length > 0){
			//提示是否发送订单
			$.messager.confirm('提示', '是否发送账单?', function(r){
				if (r){
					ocs.ajax({
		    			url:'/ebaySeller/sendBill',
		    			async:true,
		    			data : data,
		    		    beforeSend: function () {
		                       $.messager.progress({
		                           title: '请稍后',
		                           msg: '正在发送中...'
		                       });
		                   },
		               complete: function () {
		                   $.messager.progress('close');
		               },
		    			type: "POST",
		    			success: function(result) {
		    				var me = result.data;
		    				if(me=="success"){
		    					$("#syncSaleInfoTable").datagrid('reload');
								$.messager.alert("信息","发送成功！","info");	
							}else{
								$.messager.alert("信息",me,"warning");	
							}
		    			}
		    	    });
				}
			});
			
		}else{
			$.messager.alert("信息","请选择一个订单！","warning");	
		}
	
	})
	
	//初始化页面
	$("#syncSaleInfoTable").datagrid(
	{
		url : '/ocs/ebaySeller/list',
		queryParams : {
			param : {
				saleRecordNumber:'',
				orderId : '',
				account :'',
				buyerId:'',
				payTimeStart:'',
				payTimeEnd:'',
				shipTimeStart:'',
				shipTimeEnd:'',
				orderTimeStart:'',
				orderTimeEnd:'',
				orderStatus :'',
				orderAllStatus:'',
				sku :'',
				itemId:'',
				title:'',
				paypalCode:'',
				shipNumber:'',
				transactionId:'',
				remark:'',
				email:'',
				buyerName:'',
				postCode:'',
				street:'',
				ocsId:''
			}
		},
		  
		columns : [ [
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'ck',
					checkbox:true
				},
				{
					field : 'saleRecordId',
					title : '销编售号',
					align : 'center',
				},
				{
					field : 'orderId',
					title : '订单号',
					align : 'center',
				},
				{
					field : 'itemId',
					title : '图片',
					align : 'center',
					formatter:function(value,row,index){
						var view = "";
						ocs.ajax({
							url:'/ebaySeller/getItemEbayImage/'+value,
							async:false,
							type: "GET",
							success: function(result) {
								if(result.data){
									var urls = result.data;
									var imgUrl = undefined;
									var index = urls.indexOf(",");
									if(index > 0){
										imgUrl = urls.substring(0,index)
										view = '<img src="'+imgUrl+'" style="width:80px;height:80px;"/>';
									}
								}
							}
						});
						
						return view;
					}
				},
				{
					field : 'sku',
					title : 'SKU',
					align : 'center',
				},
				{
					field : 'siteId',
					title : '站点',
					align : 'center',
				},
				{
					field : 'orderStatus',
					title : '订单状态',
					align : 'center',
				},
				{
					field : 'total',
					title : '订单总额',
					align : 'center',
				},
				{
					field : 'amountPaid',
					title : '已收总额',
				},
				{
					field : 'buyerUserId',
					title : '买家ID',
					align : 'center',
				},
				{
					field : 'account',
					title : 'ebay账号',
					align : 'center',
				},
				{
					field : 'aboutStatus',
					title : '相关状态',
					align : 'center',
					width : 165,
					formatter:function(value,row,index){
						var result = '';
						var temp1 ="<div class='message-sate'><div class='icon3 size_16 temp.png'></div><br /><div class='message_tip'>temp</div></div>";
						var temp = "<div class='message-sate'><div class='icon3 size_16 temp.png'></div><br /><div class='message_tip'>temp</div></div>";
							//"<img style='margin:0 5px;' src='"+ GLOBAL.domain + "/assets/app/images/publication/temp.png' title='temp'/>&nbsp;&nbsp;";
						if(row.hasPushed > 0) {
							result += temp1.replace('temp.png','has_pushed').replace('temp','已发账单');
						} else {
							result += temp1.replace('temp.png','no-has_pushed').replace('temp','未发账单');
						}
						if(row.paidTime) {
							result += temp.replace('temp.png','paid_time').replace('temp','已付款');
						} else {
							result += temp.replace('temp.png','no-paid_time').replace('temp','未付款');
						}
						if(row.shippedTime) {
							result += temp.replace('temp.png','shipped_time').replace('temp','已发货');
						} else {
							result += temp.replace('temp.png','no-shipped_time').replace('temp','未发货');
						}
						if(row.orderStatus == 'Completed') {
							result += temp.replace('temp.png','Completed').replace('temp','已完成');
						} else if(row.orderStatus == 'Cancelled') {
							result += temp.replace('temp.png','Cancelled').replace('temp','已取消');
						} else if(row.orderStatus == 'CancelPending') {
							result += temp.replace('temp.png','CancelPending').replace('temp','等待取消');
						} else {
							result += temp.replace('temp.png','Active').replace('temp','未完成');
						}
						return result;
					}
					
				},
				{
					field : 'createdTime',
					title : '创建时间',
					align : 'center',
					formatter : function(value, row, index) {
						return saleManager.format(value,8);
					}
				},
				{
					field : 'paidTime',
					title : '支付时间',
					align : 'center',
					formatter : function(value, row, index) {
						return saleManager.format(value,8);
					}
				},
				{
					field : 'shippedTime',
					title : '发货时间',
					align : 'center',
					formatter : function(value, row, index) {
						return saleManager.format(value,8);
					}
				},
				{
					field : 'remark',
					title : '备注',
					align : 'center',
					formatter : function(value, row, index) {
						if(value){
							return "<span style='color:green;'>"+value+"</span>";
						}else{
							return "";
						}
					}
				},
				{
					field : 'stock',
					title : '动作',
					align : 'center',
					formatter : function(value, row, index) {
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="saleManager.detail('+index+')" data-options="plain:true">详情</a> &nbsp;&nbsp;'+
							   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="saleManager.remark('+row.id+','+index+')" data-options="plain:true">备注</a> &nbsp;&nbsp;' +
							   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="saleManager.updateSale(\''+row.orderId+'\',\''+row.account+'\')"  data-options="plain:true">更新订单</a> &nbsp;&nbsp;'+
							   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="saleManager.uploadSaleTranNumber('+row.id+')" data-options="plain:true">上传跟踪号</a> &nbsp;&nbsp;' + 
							   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="saleManager.createRefundOrReissueOrder(\''+ index+'\',\'refund\')" data-options="plain:true">退款</a> &nbsp;&nbsp;' +
							   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="saleManager.createRefundOrReissueOrder(\''+ index+'\',\'reissue\')" data-options="plain:true">复制订单</a> &nbsp;&nbsp;'+
							   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="saleManager.cancelOrder('+row.id+')" data-options="plain:true">取消订单</a>&nbsp;&nbsp;' ;
					}
				} ] ],
		//idField : 'id',
		singleSelect : false,
		selectOnCheck :true,
		rownumbers : true,
		pagination : true,
		fit :true,
		pageSize : 30,
		//border : true,
		onDblClickRow: function(rowIndex) {
			saleManager.detail(rowIndex);
			
		},
		onLoadSuccess : function(data) {
			if(!hasLoadLabel){
				//初始化移动菜单
				ocs.ajax({
					url:'/ebaySeller/getMoveMenuList',
					async:false,
					data : "",
					type: "POST",
					success: function(result) {
						if(result){
							$.each(result,function(){
								$("#orderLabelOptMenu").append('<div class="menu-item labelMenu" labelValue="'+this.id+'"><div class="menu-text" style="height: 20px; line-height: 20px;">'+this.name+'</div></div>');
								$("#conButtonList").append('<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">'
										+'<input type="radio" name="orderAllStatus" id="'+this.id+'" value="'+this.id+'" style="display: none;"/>'
										+'	<label for="'+this.id+'">'+this.name+'<span></span></label></li>');
							});
						}
					}
			    });
				hasLoadLabel = true;
			}
			$(".con-button li").each(function(){
				var num = $(this).find("input").val();
				if(num == 0){
					return;
				}
				var count=null;
				ocs.ajax({
					type:"POST",
					data : searchParam(),
					url:"/ebaySeller/countOrderByStatus/"+num,
					success:function(data){
						count = data.data;
					}
				});
				$(this).find("span").html("("+count+")");
			});
			var mark=1;                                                
		　　　for (var i=1; i <data.rows.length; i++) {   
		　　　　　　if (data.rows[i]['orderId'] == data.rows[i-1]['orderId']) {  
		　　　　　　　　mark += 1; 
		           $(this).datagrid('mergeCells',{ 
		　　　　　　　　　　index: i+1-mark,                 
		　　　　　　　　　　field: 'ck',                 
		　　　　　　　　　　rowspan:mark                  
		　　　　　　　　});
		　　　　　　　　$(this).datagrid('mergeCells',{ 
		　　　　　　　　　　index: i+1-mark,                 
		　　　　　　　　　　field: 'orderId',                 
		　　　　　　　　　　rowspan:mark                  
		　　　　　　　　}); 
				   $(this).datagrid('mergeCells',{ 
		　　　　　　　　　　index: i+1-mark,                 
		　　　　　　　　　　field: 'saleRecordId',                 
		　　　　　　　　　　rowspan:mark                  
		　　　　　　　　}); 
				   $(this).datagrid('mergeCells',{ 
				　　　　　　　　　　index: i+1-mark,                 
				　　　　　　　　　　field: 'orderStatus',                 
				　　　　　　　　　　rowspan:mark                  
				　　　　　　　　}); 
				   
				   $(this).datagrid('mergeCells',{ 
				　　　　　　　　　　index: i+1-mark,                 
				　　　　　　　　　　field: 'total',                 
				　　　　　　　　　　rowspan:mark                  
				　　　　　　　　}); 
				   $(this).datagrid('mergeCells',{ 
				　　　　　　　　　　index: i+1-mark,                 
				　　　　　　　　　　field: 'amountPaid',                 
				　　　　　　　　　　rowspan:mark                  
				　　　　　　　　}); 
				   $(this).datagrid('mergeCells',{ 
				　　　　　　　　　　index: i+1-mark,                 
				　　　　　　　　　　field: 'buyerUserId',                 
				　　　　　　　　　　rowspan:mark                  
				　　　　　　　　}); 
				   $(this).datagrid('mergeCells',{ 
				　　　　　　　　　　index: i+1-mark,                 
				　　　　　　　　　　field: 'account',                 
				　　　　　　　　　　rowspan:mark                  
				　　　　　　　　}); 
				   $(this).datagrid('mergeCells',{ 
				　　　　　　　　　　index: i+1-mark,                 
				　　　　　　　　　　field: 'aboutStatus',                 
				　　　　　　　　　　rowspan:mark                  
				　　　　　　　　}); 
				   $(this).datagrid('mergeCells',{ 
				　　　　　　　　　　index: i+1-mark,                 
				　　　　　　　　　　field: 'createdTime',                 
				　　　　　　　　　　rowspan:mark                  
				　　　　　　　　}); 
				   $(this).datagrid('mergeCells',{ 
				　　　　　　　　　　index: i+1-mark,                 
				　　　　　　　　　　field: 'paidTime',                 
				　　　　　　　　　　rowspan:mark                  
				　　　　　　　　}); 
				   $(this).datagrid('mergeCells',{ 
				　　　　　　　　　　index: i+1-mark,                 
				　　　　　　　　　　field: 'shippedTime',                 
				　　　　　　　　　　rowspan:mark                  
				　　　　　　　　}); 
				   $(this).datagrid('mergeCells',{ 
				　　　　　　　　　　index: i+1-mark,                 
				　　　　　　　　　　field: 'stock',                 
				　　　　　　　　　　rowspan:mark                  
				　　　　　　　　}); 
		　　　　　　}else{
		　　　　　　　　mark=1;                                        
		　　　　　　}
		　　　　}
				$(".message-sate .size_16").on("mouseover",function(){
					$(this).parent(".message-sate").find(".message_tip").show();
				});
				$(".message-sate .size_16").on("mouseout",function(){
					$(this).next().next(".message_tip").hide();
				});
				
		},
		toolbar : "#syncSaleInfoBar",
		onRowContextMenu : function(e,rowIndex, rowData){
			 e.preventDefault(); //阻止浏览器捕获右键事件
             $(this).datagrid("clearSelections"); //取消所有选中项
             $(this).datagrid("selectRow", rowIndex); //根据索引选中该行
             $('#orderLabelOptMenu').menu('show', {                        
                 left: e.pageX,//在鼠标点击处显示菜单
                 top: e.pageY
             });
		}
	});
	$("#orderLabelOptMenu").on('click','.menu-item',function(){
		var label = $(this).attr("labelValue");
		var row = $("#syncSaleInfoTable").datagrid("getSelected");
		$('#orderLabelOptMenu').menu('hide');
		var updateData = {};
		updateData["label"] = label;
		updateData["account"] = row.account;
		updateData["orderId"] = row.orderId;
		ocs.ajax({
			type:"POST",
			data : updateData,
			url:"/ebaySeller/labelOrder",
			success:function(data){
				if(data){
					$.messager.show({
	    				title:'消息',
	    				msg:'操作成功',
	    				timeout:3000,
	    				showType:'slide'
	    			});
					$("#syncSaleInfoTable").datagrid("reload");
				}
			}
		});
	});
	//标签的增删查改
	$("#editLabel").click(function(){
		//初始化
		$("#ebayLabelListDatagrid").datagrid({
			url : '/ocs/ebaySeller/getMoveMenuList',
			columns : [ [
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'ck',
					checkbox:true
				},
				{
					field : 'name',
					title : '名称',
					align : 'center',
					width : 290
				},
				{
					field : 'updateDate',
					title : '最后修改时间',
					align : 'center',
					width : 160
				}] ],
			singleSelect : true,
			rownumbers : true,
			border : true,
			toolbar: [{
				iconCls: 'icon-add',
				handler: function(){
					curLabelId = undefined;
					$("#ebayLabelAddWin").dialog("open");
				}
			},'-',{
				iconCls: 'icon-edit',
				handler: function(){
					var row = $("#ebayLabelListDatagrid").datagrid("getChecked");
					if(row.length > 0){
						var d = row[0];
						$("#labelName").textbox("setValue",d.name);
						curLabelId = d.id;
						$("#ebayLabelAddWin").dialog("open");
					}else{
						$.messager.alert("信息","请选择编辑的数据！","warning");	
					}
					
				}
			},'-',{
				iconCls: 'icon-delete',
				handler: function(){
					$.messager.confirm('Confirm','确定删除此标签吗?',function(r){
					    if (r){
					    	var row = $("#ebayLabelListDatagrid").datagrid("getChecked");
							if(row.length > 0){
								var d = row[0];
								ocs.ajax({
									type:"POST",
									data : "",
									url:"/ebaySeller/deleteLabel/"+d.id,
									success:function(data){
										if(data){
											$("#ebayLabelListDatagrid").datagrid("reload");
										}
									}
								});
							}else{
								$.messager.alert("信息","请选择删除的数据！","warning");	
							}
					    }
					});
				}
			}]
		});
		$("#ebayLabelListWin").dialog("open");
	});
	//新增标签
	$("#ebayLabelAddOK").click(function(){
		var lableName = $("#labelName").textbox("getValue");
		if(lableName){
			var label = {};
			label["name"] = lableName;
			if(curLabelId){
				label["id"] = curLabelId;
			}
			ocs.ajax({
				type:"POST",
				data : label,
				url:"/ebaySeller/addLabel",
				success:function(data){
					if(data){
						$.messager.alert("信息","操作成功！","info");	
						$("#ebayLabelListDatagrid").datagrid("reload");
						$("#ebayLabelAddWin").dialog("close");
					}
				}
			});
		}
	});
	
	$("#ebayLabelListClose").click(function(){
		$("#ebayLabelListWin").dialog("close");
	});
	$("#ebayLabelAddClose").click(function(){
		$("#ebayLabelAddWin").dialog("close");
	});
	saleManager.format = function(value,num) {
		if(value == null || value == '') {
			return value;
		} else {
			var date=new Date(value);
			date.setHours(date.getHours()+(num));
			date = date.format("yyyy-MM-dd hh:mm:ss");
			return date;
		}
	}
	
	saleManager.createRefundOrReissueOrder = function(index,value) {
		var message = '';
		if(value == 'refund') {
			message = '您确定要创建退款单吗？';
		} else if(value == 'reissue') {
			message = '您确定要创建补发单吗？';
		}
		$.messager.confirm('提示', message, function(r){
			if(r) {
				//$("#syncSaleInfoTable").datagrid('selectRow',index);
				//var row = $("#syncSaleInfoTable").datagrid('getSelected');
				var rows = $('#syncSaleInfoTable').datagrid('getRows');
				var row = rows[index];
				ocs.ajax({
					url:'/ebaySeller/getSaleOrderRefundByParentId',
					async:true,
					type: "POST",
					data: {id:row.id,orderId:row.orderId,account:row.account,platform:'ebay'},
					success: function(result) {
						var shipCost = 0;
						$.each(result.rows,function(){
							shipCost += this.rowTotal;
						})
						shipCost = row.amountPaid.substring(4) - shipCost;
						var $row = {
							platform : 'ebay',
							account : row.account,
							site : result.rows[0].transactionSiteId,
							orderId : row.orderId,
							currencyCode : row.total.substr(0,3),
							items : result.rows,
							edaOrderNum : result.edaOrderNum,
							shipCost : shipCost.toFixed(2)
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

	saleManager.detail = function(index){
		//取消所有的选择
		var rows = $('#syncSaleInfoTable').datagrid('getRows');
		var row = rows[index];
		$("#d_OrderID").text(row.orderId);
		$("#d_SalesRecoreNumber").text(row.saleRecordId);
		$("#d_BuyerUserID").text(row.buyerUserId);
		$("#d_SellerUserID").text(row.account);
		$("#saleDetial").dialog("open");
		//获取运输信息
		ocs.ajax({
			url:'/ebaySeller/getOrderTranInfo/'+row.id,
			async:false,
			type: "GET",
			success: function(result) {
				var tranInfo = result.data;
				if(tranInfo){
					$("#t_Name").text(tranInfo.NAME);
					$("#t_Street1").text(tranInfo.STREET1);
					$("#t_Street2").text(tranInfo.STREET2);
					if(tranInfo.CITYNAME){
						$("#t_CityName").text(tranInfo.CITYNAME);
						$("#t_CityName").next().show();
						$("#t_CityName").show();
					}else{
						$("#t_CityName").next().hide();
						$("#t_CityName").hide();
					}
					if(tranInfo.StateOrProvince){
						$("#t_StateOrProvnce").text(tranInfo.StateOrProvince);
						$("#t_StateOrProvnce").next().show();
						$("#t_StateOrProvnce").show();
					}else{
						$("#t_StateOrProvnce").next().hide();
						$("#t_StateOrProvnce").hide();
					}
					
					$("#t_posttalCode").text(tranInfo.POSTALCODE);
					$("#t_Phone").text(tranInfo.PHONE);
					$("#d_paypalTranNum").text(tranInfo.paypayTranNum);
					$("#t_email").text(tranInfo.email);
					//t_Country
					var countryName = tranInfo.COUNTRY;
					$("#t_Country").text(formartCountry(countryName));
				}
			}
		});
		
		function formartCountry(countryName){
		   var cLink = {};
		   cLink["LV"]="LATVIA"; 
		   cLink["NL"]="NETHERLANDS";  
		   cLink["NO"]="NORWAY";  
		   cLink["PL"]="POLAND";  
		   cLink["PT"]="PORTUGAL"; 
		   cLink["SE"]="SWEDEN";  
		   cLink["SI"]="SLOVENIA";  
		   cLink["SK"]="SLOVAKIA";  
		   cLink["AT"]="AUSTRIA"; 
		   cLink["AU"]="AUSTRALIA";  
		   cLink["BE"]="BELGIUM"; 
		   cLink["BG"]="BULGARIA";  
		   cLink["BO"]="BOLIVIA"; 
		   cLink["CH"]="SWITZERLAND";  
		   cLink["CL"]="CHILE"; 
		   cLink["CS"]="CZECH REPUBIC";  
		   cLink["HR"]="Croatia"; 
		   cLink["DZ"]="ALGERIA";  
		   cLink["EE"]="ESTONIA";  
		   cLink["ES"]="SPAIN";   
		   cLink["FI"]="FINLAND";   
		   cLink["FR"]="FRANCE";   
		   cLink["GR"]="GREECE";  
		   cLink["HU"]="HUNGARY"; 
		   cLink["US"]="UNITED STATES"; 
		   cLink["GB"]="UNITED KINGDOM"; 
		   cLink["UK"]="UNITED KINGDOM"; 
		   cLink["DE"]="GERMANY";
			if(null == countryName || countryName == ""){
				return "";
			}else{
				return cLink[countryName];
			}
		}
		
		$("#saleDetailTable").datagrid({
			url : '/ocs/ebaySeller/getOrderItemById/'+row.id,
			columns : [ [
				{
					field : 'SKU',
					hidden:true
				},
				{
					field : 'siteId',
					hidden:true
				},
				{
					field : 'publicationType',
					hidden:true
				},
				{
					field : 'productUrl',
					hidden:true
				},
				{
					field : 'ebayImages',
					title : 'ebay图片',
					align : 'center',
					width : 90,
					formatter : function(value,row,index){
						var view = "";
						if(value){
							var imgUrl = undefined;
							var index = value.indexOf(",");
							if(index > 0){
								imgUrl = value.substring(0,index)
								view = '<img src="'+imgUrl+'" style="width:80px;height:80px;"/>';
							}
						}
						
						return view;
					}
					
				},
				{
					field : 'TITLE',
					title : '物品标题',
					align : 'center',
					width : 180,
					formatter : function(value,row,index){
						if(row.productUrl){
							return '<a href="'+row.productUrl+'" target="blank">'+value+'</a>';
						}
						return value;
					}
				},
				{
					field : 'SITE',
					title : '站点、刊登类型',
					align : 'center',
					width : 100,
					formatter : function(value,row,index){
						var viewStr = '';
						var siteId = row.siteId;
						if((siteId == "" || siteId == null)&&siteId != 0){
							siteId=22;
						}
						viewStr =  "<div class='icon3 country_size num_"+siteId+"'></div><br/><div class='icon3 "+row.publicationType+"'></div>";
						return viewStr;
					}
				},
				{
					field : 'ITEMID',
					title : '物品号/SKU',
					align : 'center',
					width : 120,
					formatter : function(value,row,index){
						return value+'<br/><span style="color:green">'+row.SKU+'</span>';
					}
				},
				{
					field : 'PRICE',
					title : '价格',
					align : 'center',
					width : 50
				},
				{
					field : 'QTY',
					title : '数量',
					align : 'center',
					width : 50
				},
				{
					field : 'CARRIER',
					title : '承运商',
					align : 'center',
					width : 100
				},
				{
					field : 'TRACKNUMBER',
					title : '跟踪号',
					align : 'center',
					width : 100
				},
				{
					field : 'SHIPTIME',
					title : '发货日期',
					align : 'center',
					width : 100
				},
				{
					field : 'TOTAL',
					title : '合计',
					align : 'center',
					width : 80,
					formatter : function(value,row,index){
						return row.PRICE * row.QTY;
					}
				},
				{
					field : 'opt',
					title : '操作',
					align : 'center',
					width : 60,
					formatter : function(value,row,index){
						return '';
					}
				}] ],
			idField : 'ID',
			singleSelect : true,
			rownumbers : true,
			border : true,
		    loadFilter: function(data){
		    	
				if(data){
					
					var totalPrice = 0;
					$.each(data,function(){
						totalPrice = totalPrice + this.PRICE * this.QTY;
					});
					$("#t_total").text(totalPrice);
				}
				return data;
			}
		});
		
	};
	function searchParam(){
		var formData = $("#saleListSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	};
	
	
	$("#saleListSearch").click(function(){
		$("#syncSaleInfoTable").datagrid('load',{
			param : searchParam()
		});
	});
	
	$("#saleListReset").on('click',function(){
		$("#saleListSearchParam").form("clear");
	});
	
	$(".con-button").on("click","li",function(){
		$(this).css( "background",'#ccc').siblings().css( "background",'#fff');
		$(this).find("input").prop("checked","checked");
		$(this).siblings().find("input").prop("checked",false);
		var params = searchParam();
		if(curStatus == params['orderAllStatus']){
			return;
		}
		curStatus = params['orderAllStatus'];
		$("#syncSaleInfoTable").datagrid("load",{
			param :params
		});
	});
	
	//发送站内消息
	$("#addUserCaseMessage").on("click",function(){
		var rows = $("#syncSaleInfoTable").datagrid("getChecked");
		if(rows&&rows.length > 0){
			curRow = rows;
			var ids = [];
			$.each(rows,function(){
				ids.push(this.id);
			});
			var param = {};
			param["ids"]= ids.join(",");
			//初始化物品选择
			ocs.ajax({
				async : false,
				type:"POST",
				url:'/ebaySeller/getItemInfoByOrderSEQId',
				data : param,
				success:function(data){
					$('#itemChoose').combogrid({
					    data: data,
					    multiple: true,
					    idField: 'ITEMID',
					    textField: 'ITEMID',
					    fitColumns: true,
					    columns: [[
					    	{field:'ck',checkbox:true},
							{field:'ITEMID',title:'物品号',width:100},
							{field:'ITEMIMG',title:'物品图片',width:100,
								formatter:function(value,row,index){
									var view = "";
									if(value){
										var imgUrl = undefined;
										var index = value.indexOf(",");
										if(index > 0){
											imgUrl = value.substring(0,index)
											view = '<img src="'+imgUrl+'" style="width:80px;height:80px;"/>';
										}
									}
									return view;
								}
							},
							{field:'ITEMSKU',title:'物品SKU',width:100}
					    ]]
					});
					//$('#itemChoose').combogrid("loadData",data);
				}
			});
			
			//$('#itemChoose').combobox('reload','/ocs/ebaySeller/getItemIdById/'+row.id);
			$("#addUserMessageWin").dialog("open");
			
		}else{
			$.messager.alert("信息","请选择要发送消息的订单！");
		}
	});
	
	// 添加图片
	$("#addImgButton").on('click',function(){
		$("#addUserMessageAddImgWin").dialog("open");
	});
	$("#userCaseMessageImgUl li").on("mouseover",function(){
		$("#userCaseMessageImgUl li div").css("background","#cfdef7");
	});
	$("#userCaseMessageImgUl li").on("mouseout",function(){
		$("#userCaseMessageImgUl li div").css("background","none")
	});
	$("#addUserMessageAddImgOk").on('click',function(){
		var name = $("#addMessageimageName").textbox("getValue");
		var url = $("#addMessageimageUrl").textbox("getValue");
		if(name&&url){
			$("#userCaseMessageImgUl").append('<li  style="float:left;list-style:none;height:92px;width:100px;"><div  style="position:relative;"><img style="height:92px;width: 100px;" name="'+name+'" src="'+url+'"></div></li>');
		}
		$("#addUserMessageAddImgWin").dialog("close");
	});
	$("#addUserMessageAddImgCancel").on('click',function(){
		$("#addUserMessageAddImgWin").dialog("close");
	});
	//发送消息
	$("#addUserMessageOk").on("click",function(){
		$.messager.confirm('确认','您确定发送私信给此批订单用户吗?',function(r){
		    if (r){
		    	var form = $("#addUserMessageFrm").serializeArray();
		    	var data = {};
		    	var orderInfo = [];
		    	$.each(curRow,function(){
		    		var order = {};
		    		order["account"] = this.account;
		    		order["id"] = this.id;
		    		order["orderId"] = this.orderId;
		    		order["buyerId"] = this.buyerUserId;
		    		orderInfo.push(order);
		    	});
		    	data["orderInfo"] = orderInfo;
		    	var checkFlag = false;
		    	$.each(form, function(i, field){
		    		if(field.value){
		    			data[field.name] = field.value;
		    		}else{
		    			checkFlag = true;
		    			return false;
		    		}
	    	    });
		    	var itemIds = [];
		    	$("#addUserMessageFrm").find("input[name='itemId']").each(function(){
		    		itemIds.push($(this).val());
		    	});
		    	data["itemId"] = itemIds.join(",");
		    	if(checkFlag){
		    		$.messager.alert("信息","请填写完整信息！");
		    		return;
		    	}
		    	var imgs = [];
		    	//图片信息
		    	$("#userCaseMessageImgUl li").each(function(){
		    		if($(this).attr("id") != "addImgButton"){
		    			var img = $(this).find("img");
			    		var imgData = {};
			    		imgData["name"] = $(img).attr("name");
			    		imgData["url"] = $(img).attr("src");
			    		imgs.push(imgData);
		    		}
		    	});
		    	data["media"] = JSON.stringify(imgs);
		    	ocs.ajax({
					type:"POST",
		    		url:'/ebaySeller/sendUseMessage',
					async:true,
					data:data,
				    beforeSend: function () {
		                   $.messager.progress({
		                       title: '请稍后',
		                       msg: '正在发送中...'
		                   });
		               },
		           complete: function () {
		               $.messager.progress('close');
		           },
				   success: function(result) {
						if(result.data){
							//初始化页面
							$("#sendMessageResultListGrid").datagrid({
								data:result.data,
								columns : [ [
										{
											field : 'account',
											title : '账号',
											align : 'center',
											width : 150
												
										},
										{
											field : 'orderId',
											title : '订单ID',
											align : 'center',
											width : 150
										},
										{
											field : 'itemId',
											title : '物品号',
											align : 'center',
											width : 150
										},
										{
											field : 'result',
											title : '结果',
											align : 'center',
											width : 300
										}
										] ],
								fit:true,
								singleSelect : true,
								border : true,
								nowrap:false,
								fitColumns: true,
								onLoadSuccess: function () {  
								     $('#sendMessageResultListGrid').datagrid('fixRowHeight');
								} 
							});
							$("#sendMessageResult").dialog("open");
						}
				   },
				   error: function(jqXHR, textStatus, errorThrown) {
						$.messager.alert("信息", "服务器发生错误,请稍后重试！");		
				   }
			   });
		    }
		});
	});
	
	$("#addUserMessageCancel").on("click",function(){
		$("#addUserMessageWin").dialog("close");
		$("#addUserMessageFrm").form("clear");
		$('#itemChoose').combobox('reload',"");
		$("#userCaseMessageImgUl li").each(function(){
    		if($(this).attr("id") != "addImgButton"){
    			$(this).remove();
    		}
    	});
	});
	
	//标记缺货
	$("#remarkNoItem").on("click",function(){
		var rows = $("#syncSaleInfoTable").datagrid("getChecked");
		var ids = [];
		if(rows&&rows.length > 0){
			$.messager.confirm('提示', "您确定移动到缺货列表？", function(r){
				if(r){
					$.each(rows,function(){
						ids.push(this.id);
					});
					//标记
					var param = {};
					param["ids"] = ids;
					param["flag"] = "tag";
					ocs.ajax({
						async : false,
						type:"POST",
						url:'/ebaySeller/remarkNoItem',
						data : param,
						success:function(data){
							if(data.data){
								$.messager.alert("提示", "操作成功！");
								//刷新页面
								//刷新页面
								$("#syncSaleInfoTable").datagrid('load',{
									param : searchParam()
								});
								
							}
						}
					});
				}
			});
		}else{
			$.messager.alert("提示", "请选择一个订单！");		
		}
	});
	
	//取消标记缺货
	$("#unRemarkNoItem").on("click",function(){
		var rows = $("#syncSaleInfoTable").datagrid("getChecked");
		var ids = [];
		if(rows&&rows.length > 0){
			$.messager.confirm('提示', "您确定取消缺货标示？", function(r){
				if(r){
					$.each(rows,function(){
						ids.push(this.id);
					});
					//取消标记
					var param = {};
					param["ids"] = ids;
					param["flag"] = "unTag";
					ocs.ajax({
						async : false,
						type:"POST",
						url:'/ebaySeller/remarkNoItem',
						data : param,
						success:function(data){
							if(data.data){
								$.messager.alert("提示", "操作成功！");
								//刷新页面
								$("#syncSaleInfoTable").datagrid('load',{
									param : searchParam()
								});
								
							}
						}
					});
				}
			});
		}else{
			$.messager.alert("提示", "请选择一个订单！");		
		}
	});
	

	$("#ebayOrderRemarkOK").click(function(){
		var remark = $("#orderRemark").textbox("getValue");
		if(remark){
			var model = {};
			model['remark'] = remark;
			model['id'] = curId;
			ocs.ajax({
				url:'/ebaySeller/remarkContent',
				type:'POST',
				data:model,
				success:function(data){
					if(data.data){
						$("#orderRemark").textbox("setValue","");
						$("#ebayOrderRemark").dialog("close");
						$("#syncSaleInfoTable").datagrid("reload");
					}
				}
			});
			
		}else{
			$.messager.alert('提示','备注内容为空!','warning');
		}
	});
	$("#ebayOrderRemarkCancel").click(function(){
		$("#orderRemark").textbox("setValue","");
		$("#ebayOrderRemark").dialog("close");
	});
	
	$("#shippingUploadRecord").click(function(){
		$("#ebayShipUploadRecord").dialog("open");
		$("#ebayShipUploadRecordDatagrid").datagrid({
			url : '/ocs/ebaySeller/shipUploadRecord',
			columns : [ [
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'type',
					title : '上传类型',
					align : 'center',
					width : 120
				},
				{
					field : 'account',
					title : '账号',
					align : 'center',
					width : 90
				},
				{
					field : 'orderId',
					title : '订单id',
					align : 'center',
					width : 200
				},
				{
					field : 'transactionId',
					title : '交易ID',
					align : 'center',
					width : 100
				},
				{
					field : 'carrier',
					title : '运输服务',
					align : 'center',
					width : 100
				},
				{
					field : 'trackingNumber',
					title : '运输号',
					align : 'center',
					width : 120
				},
				{
					field : 'toLine',
					title : '上传状态',
					align : 'center',
					width : 80,
					formatter : function(value,row,index){
						var view = "";
						if(value == "0"){
							view ='<span style="color:green;">上传中</span>';
						}else if(value == "1"){
							view ='<span style="color:green;">上传成功</span>';
						}else{
							view ='<span style="color:red;">已取消</span>';
						}
						return view;
					}
				},
				{
					field : 'uploadDate',
					title : '上传时间',
					align : 'center',
					width : 120
				},
				{
					field : 'cause',
					title : '失败原因',
					align : 'center',
					width : 280,
					formatter : function(value,row,index){
						if(value){
							return '<span style="color:red;">'+value+'</span>';
						}else{
							return '';
						}
					}
				},
				{
					field : 'opt',
					title : '操作',
					align : 'center',
					width : 60,
					formatter : function(value,row,index){
						if(row.toLine == "0"){
							return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="saleManager.cancelUpload('+row.id+')" data-options="plain:true">取消</a>&nbsp;&nbsp;';
						}
						return '';
					}
				}] ],
			idField : 'id',
			singleSelect : false,
			rownumbers : true,
			pagination : true,
			fit: true,
			pageSize : 30,
			border : true
		});
	});
	$("#ebayShipUploadRecordCancel").click(function(){
		$("#ebayShipUploadRecord").dialog("close");
	});
	
	saleManager.cancelUpload = function(id){
		ocs.ajax({
			url:'/ebaySeller/cancelUpload/'+id,
			type:'POST',
			data:"",
			success:function(data){
				if(data.data){
					$.messager.alert("提示", "取消成功！");	
					$("#ebayShipUploadRecordDatagrid").datagrid("reload");
				}
			}
		});
	}
	
	$("#viewMoreSearchButton").click(function(){
		if(viewMoreChoose){
			$('#moreSeachTa').hide();
			viewMoreChoose = false;
			$(this).text("高级搜索");
		}else{
			$('#moreSeachTa').show();
			viewMoreChoose = true;
			$(this).text("隐藏高级搜索");
			
		}
		$("#syncSaleInfoTable").datagrid("resize");
		
	});
})(saleManager,jQuery)
