function initCombobox(id){  
	var select = $("#"+id);
    select.combobox({ 
        onClick:function(record){  
        	var valueField = select.combobox("options").valueField;
            if(!record[valueField]){
                var data = select.combobox("getData");
                var values = select.combobox("getValues");
                var selectVaues = [];
                if((data.length - 1) != values.length){
                    data.reduce(function(prev, current, index, array){
                        selectVaues.push(current[valueField]);
                    },selectVaues);
                }else{
                    selectVaues.push(record[valueField]);
                }
                select.combobox('setValues', selectVaues);
            }
        },  
    });    
}  

function exportBefore() {
	$("#export_type").hide();
	$("#type").combobox({required: false});
	$("#fileExport").dialog("open");
	$("#ExportForm").form('clear');
}
var export_ = '';
$(function() {
	formatterDate('date1');
	formatterDate('date2');
	formatterDate('date_1');
	initCombobox('platform');
	initCombobox('site');
	initCombobox('category');
	initCombobox('sku');
	initCombobox('year');
	initCombobox('month');
	initCombobox('quarter');
	initCombobox('personnel');
	initCombobox('site_');
	
	$("#month").combobox('setValues',[]);
	$("#quarter").combobox('setValues',[]);
	$("#site_").combobox('setValues',[]);
	
	$("#year").combobox({   
		valueField:'value',
	    textField:'text',
    });
    var data = [{"text":"全选/反选","value":""}];
    var endYear=new Date().getUTCFullYear();
    for(var startYear = 2016;startYear<=endYear;startYear++){
        data.push({"text":startYear,"value":startYear});
    }
    $("#year").combobox("loadData",data);//下拉框加载数据
	
	
	$("#operatingProfitViewDataGrid").datagrid({
		url:  GLOBAL.domain + '/operatingProfitView/findAll',
		nowrap: true,                   //内容不换行
		singleSelect : true,
		rownumbers : true,
		fit : true,
		border : true,
		pagination : true,
		pageSize : 50,
	    autoRowHeight: true,
	    loadMsg: '请稍等...',
	    toolbar: '#operatingProfitViewToolbar',
	    columns: [[
	    	{ field: 'nick', title: '用户', align:"right"},
	    	{ field: 'platform', title: '平台', align:"right",
				formatter : function(value,row,index){
					if(value == 'amazon') {
                		return '亚马逊';
                	} else if(value == 'ebay') {
                		return 'Ebay';
                	} else if(value == 'light') {
                		return '官网';
                	} else if(value == 'walmart') {
                		return '沃尔玛';
                	}
                	return value;
				}
	    	},
	    	{ field: 'site', title: '国家',align:"right",
	    		formatter:function(value,row){
                	if(value == 'US') {
                		return 'United States';
                	} else if(value == 'GB') {
                		return 'United Kingdom';
                	} else if(value == 'DE') {
                		return 'German';
                	} else if(value == 'FR') {
                		return 'France';
                	} else if(value == 'IT') {
                		return 'Italy';
                	} else if(value == 'ES') {
                		return 'Spain';
                	} else if(value == 'JP') {
                		return 'Japan';
                	} else if(value == 'CA') {
                		return 'Canada';
                	} else if(value == 'AU') {
                		return 'Australia';
                	}
                	return value;
                }
	    	},
	    	{ field: 'year', title: '年',align:"right"},
	    	{ field: 'month', title: '月',align:"right"},
	    	{ field: 'quarter', title: '季度',align:"right"},
	    	{ field: 'category', title: '品类',align:"right"},
	    	{ field: 'sku', title: 'SKU',align:"right"},
	        { field: 'price', title: '销售额',align:"right"},
	        { field: 'salesCost', title: '销售成本',align:"right"},
	        { field: 'saleOfMaori', title: '毛利额',align:"right"},
	        { field: 'grossMarginSales', title: '毛利率',align:"right"},
	        { field: 'operatingProfit', title: '经营利润',align:"right"},
	        { field: 'operatingProfitMargin', title: '经营利润率',align:"right"},
	        { field: 'newSales', title: '新品销售额',hidden:true,align:"right"},
	        { field: 'newProductRatio', title: '新品占比',align:"right"},
	        { field: 'inventoryTurnover', title: '库存周转率',align:"right"},
	        { field: 'bottomPrice', title: '保底售价',hidden:true,align:"right"}
        ]],
        onLoadSuccess:function(data){
        	var arr = new Array('nick','platform','site','year','month','quarter','category','sku');
        	if(data.rows.length > 0) {
        		var row = data.rows[0];
        		$.each(arr,function(){
        			if(row[this]) {
        				$("#operatingProfitViewDataGrid").datagrid('showColumn',this);
        			} else {	
        				$("#operatingProfitViewDataGrid").datagrid('hideColumn',this);
        			}
        		});
        	} else {
        		$.each(arr,function(){
        			$("#operatingProfitViewDataGrid").datagrid('hideColumn',this);
        		});
        	}
        	if($('#sku').combobox('getValues').toString()) {
        		$("#operatingProfitViewDataGrid").datagrid('showColumn','bottomPrice');
			} else {	
				$("#operatingProfitViewDataGrid").datagrid('hideColumn','bottomPrice');
			}
        	if(data.source){
        		if(data.source != '2' && data.source != '3' && data.source != '5') {
        			$("#platform_1").show();
        			$("#platform_2").show();
        			if(data.source == '4') {
            			$("#platform").combobox("loadData",[{text: "全选/反选",value : ""},{text: "Ebay",value : "ebay"},{text: "沃尔玛",value : "walmart"}]);
            		} else {
            			$("#platform").combobox("loadData",[{text: "全选/反选",value : ""},{text: "亚马逊",value : "amazon"},{text: "Ebay",value : "ebay"},
            				{text: "官网",value : "light"},{text: "沃尔玛",value : "walmart"}]);
            		}
        		} 
        		if(data.source == '1' || data.source == '2' || data.source == '6') {
        			$("#department_1").show();
        			$("#department_2").show();
        			$("#personnel_1").show();
        			$("#personnel_2").show();
        			if(data.source == '1') {
        				$("#department").combobox('loadData',[{text:'-- 请选择 --',value:''},{text:'亚马逊运营部',value:'1'},{text:'产品部',value:'2'}]);
        			} else if(data.source == '2') {
        				$("#department").combobox('loadData',[{text:'-- 请选择 --',value:''},{text:'亚马逊运营部',value:'1'}]);
        			} else if(data.source == '6') {
        				$("#department").combobox('loadData',[{text:'-- 请选择 --',value:''},{text:'产品部',value:'2'}]);
        			}
        		}
        		if(data.source == '1' || data.source == '6' || data.source == '7') {
        			$("#operatingProfitViewDataGrid").datagrid('showColumn','newSales');
        		} else {
	     			$("#operatingProfitViewDataGrid").datagrid('hideColumn','newSales');
        		}
        	}
		},
        onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		},
		rowStyler:function(index,row){
			if (row.platform == '汇总'){
				return 'background-color:#E1EDFF;color:blue;font-weight:bold;';
			}
		}
	});
	
	$('#category').combobox({
		url : GLOBAL.domain + '/operatingProfitView/getCategory',
		valueField : 'NAME',
		textField : 'VALUE',
		onChange: function(newValue,oldValue) {
			if (newValue && newValue.length > 0) {
				$('#sku').combobox('enable');
				ocs.ajax({
					url:'/operatingProfitView/getSkuByCategory',
					async:true,
					data: {'category' : newValue},
					type: "POST",
					success: function(result) {
						if(result) {
							$("#sku").combobox('loadData',result);
						} else {
							$("#sku").combobox('loadData',[]);
						}
					}
				});
			} else {
				$("#sku").combobox('loadData',[]);
				$('#sku').combobox('disable');
			}
			$("#sku").combobox('setValues',[]);
		} 
	});
	
	$('#sku').combobox({
		valueField : 'NAME',
		textField : 'VALUE'
	});
	
	$('#personnel').combobox({
		valueField : 'NAME',
		textField : 'VALUE'
	});
	
	$('#site').combobox({
		url : GLOBAL.domain + '/operatingProfitView/getSite',
		valueField : 'NAME',
		textField : 'VALUE',
		onLoadSuccess: function(row){
			if(row.length > 2) {
				$("#site_1").show();
				$("#site_2").show();
			}
		}
	});
	
	$('#department').combobox({
		onChange: function(newValue,oldValue) {
			if(newValue) {
				$('#personnel').combobox('enable');
				ocs.ajax({
					url:'/operatingProfitView/getPersonnelByDepartment',
					async:true,
					data: {'department' : newValue},
					type: "POST",
					success: function(result) {
						if(result) {
							$("#personnel").combobox('loadData',result);
						} else {
							$("#personnel").combobox('loadData',[]);
						}
					}
				});
			} else {
				$("#personnel").combobox('loadData',[]);
				$('#personnel').combobox('disable');
			}
			$("#personnel").combobox('setValues',[]);
		}
	});
	
	$("#operatingProfitViewRefresh").click(function(){
		ocs.ajax({
			url:'/operatingProfitView/refreshBefore',
			type: "POST",
			async:true,
			success: function(result) {
				if(result == 0) {
					$.messager.confirm('提示', "您确定要刷新吗？", function(r){
						if (r){
							ocs.ajax({
								url:'/operatingProfitView/refresh',
								type: "POST",
								async:true,
								beforeSend: function () {
				                    $.messager.progress({
				                        title: '请稍后',
				                        msg: '正在刷新数据...'
				                    });
				                },
				                complete: function () {
				                    $.messager.progress('close');
				                },
								success: function(result) {
									if (result && result.errorCode == 0) {
				        				$("#operatingProfitViewDataGrid").datagrid("reload");
				        				$.messager.alert('消息提示', result.description, 'info');
				        			} else if (result && result.errorCode == 1) {
				        				$.messager.alert('消息提示', result.description, 'warning');
				        			}
								}
							});
						}
					});
				} else if(result == 1) {
					$.messager.alert('消息提示', '该月数据已确认,无法刷新', 'warning');
					return;
				} else if(result == null) {
					$.messager.alert('消息提示', '该月还未生成数据,无法刷新', 'warning');
					return;
				}
			}
		});
	});
	
	$("#operatingProfitViewSure").click(function(){
		ocs.ajax({
			url:'/operatingProfitView/refreshBefore',
			type: "POST",
			async:true,
			success: function(result) {
				if(result == 0) {
					$.messager.confirm('提示', "确认后无法再次刷新该月数据,您确定要确认该月数据吗？", function(r){
						if (r){
							ocs.ajax({
								url:'/operatingProfitView/sure',
								type: "POST",
								async:true,
								beforeSend: function () {
				                    $.messager.progress({
				                        title: '请稍后',
				                        msg: '正在确认数据...'
				                    });
				                },
				                complete: function () {
				                    $.messager.progress('close');
				                },
								success: function(result) {
									if (result && result.errorCode == 0) {
				        				$("#operatingProfitViewDataGrid").datagrid("reload");
				        				$.messager.alert('消息提示', result.description, 'info');
				        			} else if (result && result.errorCode == 1) {
				        				$.messager.alert('消息提示', result.description, 'warning');
				        			}
								}
							});
						}
					});
				} else if(result == 1) {
					$.messager.alert('消息提示', '该月数据已确认!', 'warning');
					return; 
				} else if(result == null) {
					$.messager.alert('消息提示', '该月还未生成数据,无法确认!', 'warning');
					return; 
				}
			}
		});
	});
	
	$("#operatingProfitViewExport").on("click",function(){
		export_ = '';
		$("#export_type").show();
		$("#type").combobox({required: true});
		$("#fileExport").dialog("open");
		$("#ExportForm").form('clear');
	});
	$("#operatingProfitViewExport_").on("click",function(){
		export_ = '';
		exportBefore();
	});
	
	$("#operatingProfitViewExportAmazon").on("click",function(){
		export_ = 'value';
		exportBefore();
	});
	
	$("#operatingProfitViewFeeExport").on("click",function(){
		export_ = 'export';
		exportBefore();
	});
	
	$("#exportSubmit").on('click',function(){
		if(!$("#ExportForm").form('validate')) {
			return;
		}
		if($("#date1").datebox('getValue') == '' && $("#date2").datebox('getValue') == '') {
			$.messager.alert("提示信息", "请选择年月!");
			return;
		}
		if(export_ == '') {
			ocs.ajax({
				url:'/operatingProfitView/exportBefore',
				type: "POST",
				async:true,
				data: {'fromDate': $("#date1").datebox('getValue'),'toDate':$("#date2").datebox('getValue')},
				success: function(result) {
					if(result.data != null) {
						$.messager.alert("提示信息", result.data+'数据还未确认,无法导出,请重新选择!');
						return;
					} else {
						$("#fileExport").dialog("close");
						window.location.href = GLOBAL.domain + '/excel/export/operatingProfitViewExport?type='
						+$("#type").combobox('getValue')+'&date1='+$("#date1").datebox('getValue')+'&date2='+$("#date2").datebox('getValue');
					}
					
				}
			});
		} else {
			var type = '';
			if(export_ == 'value') {
				type = 1;
			} else if(export_ == 'export') {
				type = '2';
			}
			$("#fileExport").dialog("close");
			window.location.href = GLOBAL.domain + '/excel/export/operatingProfitViewAmazonExport?type='+ type+'&date1='
			+$("#date1").datebox('getValue')+'&date2='+$("#date2").datebox('getValue');
		}
	})
	
	$("#operatingProfitViewGenerateData").click(function(){
		ocs.ajax({
			url:'/operatingProfitView/generateDataBefore',
			type: "POST",
			async:true,
			success: function(result) {
				if(result.errorCode != 0) {
					$.messager.alert("提示信息", '该月订单汇总数据已生成,无需再次生成!');
					return;
				} else {
					ocs.ajax({
						url:'/operatingProfitView/mappingSku',
						type: "POST",
						async:true,
						beforeSend: function () {
		                    $.messager.progress({
		                        title: '请稍后',
		                        msg: '正在生成数据...'
		                    });
		                },
		                complete: function () {
		                    $.messager.progress('close');
		                },
						success: function(result) {
							if(result.errorCode != 0) {
								$.messager.alert("提示信息", '存在没有映射或者产品表找不到的SKU,请导出维护!');
								return;
							} else {
								ocs.ajax({
									url:'/operatingProfitView/categorySku',
									type: "POST",
									async:true,
									success: function(result) {
										if(result.errorCode != 0) {
											$.messager.alert("提示信息", '存在找不到分类的SKU,请导出维护后重新同步ERP分类!');
											return;
										} else {
											$.messager.confirm('提示', "您确定生成该月订单汇总数据吗？", function(r){
												if (r){
													ocs.ajax({
														url:'/operatingProfitView/generateData',
														type: "POST",
														async:true,
														success: function(result) {
															if(result.errorCode == 0) {
																$.messager.alert("提示信息", '该月订单汇总数据生成成功!');
															} 
														}
													});
												}
											});
										}
									}
								});
							}
						}
					});
				}
			}
		});
	});
	
	$("#operatingProfitViewMappingSku").click(function(){
		window.location.href = GLOBAL.domain + '/excel/export/operatingProfitViewSkuExport?type=0';
	})
	$("#operatingProfitViewCategorySku").click(function(){
		window.location.href = GLOBAL.domain + '/excel/export/operatingProfitViewSkuExport?type=1';
	})
	
	$("#operatingProfitViewSyncCategory").click(function(){
		ocs.ajax({
			url:'/operatingProfitView/refreshBefore',
			type: "POST",
			async:true,
			success: function(result) {
				if(result == 1) {
					$.messager.alert('消息提示', '该月数据已确认,无需同步分类!', 'warning');
					return; 
				} else {
					$.messager.confirm('提示', "您确定同步ERP分类吗？", function(r){
						if (r){
							ocs.ajax({
								url:'/operatingProfitView/syncCategory',
								type: "POST",
								async:true,
								beforeSend: function () {
				                    $.messager.progress({
				                        title: '请稍后',
				                        msg: '正在同步分类...'
				                    });
				                },
				                complete: function () {
				                    $.messager.progress('close');
				                },
								success: function(result) {
									if(result.errorCode == 0) {
										$.messager.alert("提示信息", '同步ERP分类成功!');
									} 
								}
							});
						}
					});
				}
			}
		})
	})
	
	$("#operatingProfitViewAmazonFee").click(function(){
		$("#operatingProfitViewAmazonFeeDataGrid").datagrid({
	        url: GLOBAL.domain + '/operatingProfitView/findAmazonFee',
	        queryParams : {
				param : {
					
				}
			},
			onLoadSuccess : function(data) {
				//改变footer的样式
				$(".datagrid-ftable tbody tr").each(function(){
					this.style.backgroundColor="#E1EDFF";
					this.style.color="blue";
					this.style.fontWeight="bold";
				})
			}
		});
		$('#feeSearchForm').form('clear');
		$("#operatingProfitViewAmazonFeeDialog").dialog("open");
	});
	
	$("#queryFee").click(function(){
		var formData = $("#feeSearchForm").serializeArray();
		var param = {};
		param['site'] = $('#site_').combobox('getValues').toString();
		param['monthOfYear'] = $('#date_1').datebox('getValue');
		$('#operatingProfitViewAmazonFeeDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#resetFee").on('click',function(){
		$("#feeSearchForm").form("clear");
	});
	
	$("#query").click(function(){
		if($('#department').combobox('getValue') != '' && $('#personnel').combobox('getValues').length == 0) {
			$.messager.alert("提示信息", "请选择具体人员!");
			return;
		}
		var param = {};
		param['platform'] = $('#platform').combobox('getValues').toString();
		param['site'] = $('#site').combobox('getValues').toString();
		param['category'] = $('#category').combobox('getValues').toString();
		param['sku'] = $('#sku').combobox('getValues').toString();
		param['year'] = $('#year').combobox('getValues').toString();
		param['month'] = $('#month').combobox('getValues').toString();
		param['quarter'] = $('#quarter').combobox('getValues').toString();
		param['personnel'] = $('#personnel').combobox('getValues').toString();
		param['department'] = $('#department').combobox('getValue');
		$('#operatingProfitViewDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#reset").on('click',function(){
		$("#operatingProfitViewSearchForm").form("clear");
	});
})
