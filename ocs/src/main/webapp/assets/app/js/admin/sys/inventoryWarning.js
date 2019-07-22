var inventoryWarning = {};
(function(inventoryWarning,$){
	
	//导出
	$("#exportDataBtn").click(function(){
		var t = $("#template").val();
		if(t == 2){
			window.location.href = "/ocs/excel/template/inventoryWarningTimeImportService";
		}else if( t == 3){
			window.location.href = "/ocs/excel/template/inventoryWarningCoeffImportService";
		}else if( t == 4){
			window.location.href = "/ocs/excel/template/inventoryWarningSetImportService";
		}else{
			var formData = $("#inventoryWarningSearchParam").serializeArray();
			var param = [];
			param.push("?");
			$.each(formData,function(){
				param.push(this.name+"="+this.value+"&");
			});
			window.location.href = "/ocs/excel/export/inventoryWarningExport"+param.join("");
		}

	});
	
	inventoryWarning.uploadFile = function() {
		$("#fileUpload").dialog("open");
		$("#importType").combobox('setValue','1');
		$('#fileUploadInput').filebox({
		    buttonText: '文件选择',
		    buttonAlign: 'right'
		});
	}
	//导入跟踪号
	$("#addInventoryWarningBaseData").on("click",function(){
		inventoryWarning.uploadFile();
	})
	$("#uploadSubmit").click(function(){
		$('#ImportForm').form('submit', {
			onSubmit: function(){
				var file = $("#fileUploadInput").filebox('getValue'); 
			    if(file == '' || file == null) {
					$.messager.alert("提示信息", "请选择导入的文件");
					return false;
				}
			    var type = $("#importType").combobox('getValue');
		    	if(type == "1") {
		    		$('#ImportForm').attr("action","/ocs/excel/import/inventoryWarningTimeImportService");
		    	} else if(type == "2") {
		    		$('#ImportForm').attr("action","/ocs/excel/import/inventoryWarningCoeffImportService");
		    	}else if(type == "3"){
		    		$('#ImportForm').attr("action","/ocs/excel/import/inventoryWarningSetImportService");
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
			    			$("#importErrorList").html(error.join(""));
			    			$("#importMessageWin").dialog("open");
			    		}else{
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
	//重新计算
	$("#updateInventoryWarning").on("click",function(){
		$.messager.confirm('提示', '系统每天会定时刷新。手工刷新的时间过长（35分钟左右）,确认手工刷新吗?', function(r){
			if (r){
				ocs.ajax({
					url:'/inventoryWarning/updateInventoryWarning',
					async:true,
				    beforeSend: function () {
		                   $.messager.progress({
		                       title: '请稍后',
		                       msg: '正在计算中...（需35分钟左右）'
		                   });
		               },
		           complete: function () {
		               $.messager.progress('close');
		           },
					type: "GET",
					success: function(result) {
						if(result.data){
							$("#inventoryWarningList").datagrid('reload');
							$.messager.alert("信息","计算完成！");	
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						//$.messager.alert("信息", "服务器发生错误！");		
					}
			   });
			}
		});
		
		
	})
	$("#inventoryWarningList").datagrid({
		url : '/ocs/inventoryWarning/list',
		queryParams : {
			param : {
				sku:'',
				scode:'',
				dayTime : '',
				ship_type :'',
				platform:''
			}
		},
		columns : [ [
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'sku',
					title : 'SKU',
					align : 'center',
					width : 150
				},
				{
					field : 'sku_and_scode',
					title : 'SKU&仓库代码',
					align : 'center',
					width : 200
				},		
				{
					field : 'scode',
					title : '仓库代码',
					align : 'center',
					width : 100
				},
				{
					field : 'has_sale',
					title : '是否有到货记录',
					align : 'center',
					width : 100
				},
				{
					field : 'ship_type',
					title : '运输方式',
					align : 'center',
					width : 100
				},
				{
					field : 'product_level',
					title : '产品等级',
					align : 'center',
					width : 100
				},
				{
					field : 'field',
					title : '系列',
					align : 'center',
					width : 150
				},
				
				{
					field : 'platform',
					title : '平台',
					align : 'center',
					width : 100
				},
				{
					field : 'oversize',
					title : '尺寸标准',
					align : 'center',
					width : 100
				},
				{
					field : 'scode_ship_oversize',
					title : '仓库代码&运输方式&oversize',
					align : 'center',
					width : 150
				},
				{
					field : 'opt_time',
					title : '缓冲时长',
					align : 'center',
					width : 100
				},
				{
					field : 'ship_time',
					title : '物流时长',
					align : 'center',
					width : 100
				},
				{
					field : 'listing_time',
					title : '上架时长',
					align : 'center',
					width : 100
				},
				{
					field : 'ship_cycle_time',
					title : '物流周期',
					align : 'center',
					width : 100
				},
				{
					field : 'purchase_cycle_time',
					title : '采购周期',
					align : 'center',
					width : 100
				},
				{
					field : 'cycle_time',
					title : '供应链总时长',
					align : 'center',
					width : 100
				},
				{
					field : 'coeff',
					title : '系列预警系数',
					align : 'center',
					width : 100
				},
				{
					field : 'coeff1',
					title : '品类运营系数',
					align : 'center',
					width : 100
				},
				{
					field : 'save_ity',
					title : '安全库存量-国外库存',
					align : 'center',
					width : 130
				},
				{
					field : 'sale_day',
					title : '可销售天数',
					align : 'center',
					width : 100
				},
				{
					field : 'qty_one_day',
					title : '日均销售量',
					align : 'center',
					width : 100
				},
				{
					field : 'avg_3_day',
					title : '最近3天销量',
					align : 'center',
					width : 100
				},
				{
					field : 'avg_7_day',
					title : '最近7天销量',
					align : 'center',
					width : 100
				},
				{
					field : 'avg_30_day',
					title : '最近30天销量',
					align : 'center',
					width : 100
				},
				{
					field : 'is_dis',
					title : '是否续卖',
					align : 'center',
					width : 100
				},
				{
					field : 'outer_stock',
					title : '海外库存',
					align : 'center',
					width : 100
				},
				{
					field : 'quantity',
					title : 'quantity',
					align : 'center',
					width : 100
				},
				{
					field : 'inbound',
					title : 'inbound',
					align : 'center',
					width : 100
				},
				{
					field : 'receiving',
					title : 'receiving',
					align : 'center',
					width : 100
				},
				{
					field : 'reserved',
					title : 'reserved',
					align : 'center',
					width : 100
				},
				{
					field : 'reserved_sale',
					title : 'reserved能卖',
					align : 'center',
					width : 100
				},
				{
					field : 'month_days_sales',
					title : 'Mon-Days Sales',
					align : 'center',
					width : 100
				},
				{
					field : 'avg_days_sales',
					title : 'Avg. Days Sales',
					align : 'center',
					width : 100
				},
				{
					field : 'bad_day',
					title : '本月断货天数',
					align : 'center',
					width : 100
				},
				{
					field : 'is_bad',
					title : '是否断货',
					align : 'center',
					width : 100
				},
				{
					field : 'total_sales',
					title : '累计销量',
					align : 'center',
					width : 100
				},
				{
					field : 'last_month_bad',
					title : '上月断货天数',
					align : 'center',
					width : 100
				},
				{
					field : 'createDate',
					title : '生成时间',
					align : 'center',
					width : 150
				}] ],
		//idField : 'id',
		singleSelect : true,
		toolbar:"#inventoryWarningSearchParam-panel",
		rownumbers : true,
		//fitColumns : true,
		border : true,
		nowrap:false,
		fit: true,
		pagination : true,
		pageSize : 50,
		showFooter:true,
		onLoadSuccess:function(data){
			$(".datagrid-ftable tbody tr").each(function(){
			    this.style.backgroundColor="#E1EDFF";
			    this.style.color="blue";
			    this.style.fontWeight="bold";
			   })
		}
	});
	$("#inventoryWarningSearch").click(function(){
		var formData = $("#inventoryWarningSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#inventoryWarningList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#inventoryWarningList").datagrid("reload");
	});
	
	$("#inventoryWarningReset").on('click',function(){
		$("#inventoryWarningSearchParam").form("clear");
	});
})(inventoryWarning,jQuery)