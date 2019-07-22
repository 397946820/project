var source = '';
var permission = '';

//双击效果
function doDblClickRow(rowIndex, rowData){
	var row = getParam("");
	if(source == 'allSource') {
		return;
	}
	if(rowData.sku == null) {
		$.messager.alert("提示信息", "sku为空无法查询详细信息!");
		return;
	}
	var p1 = {
		source : source,
		sku : rowData.sku,
		platform : rowData.platform,
		currencycode : rowData.currencycode,
		status : rowData.status,
		whichTime : row.whichTime,
		timeQuantum : row.timeQuantum,
		starttime : row.starttime,
		endtime : row.endtime	
	};
	var p2 = {};
	if(source == 'amazon') {
		p2 = {
			asin : rowData.asin,
			station : rowData.station	
		}
	} else if(source == 'ebay') {
		p2 = {
			station : rowData.station	
		}
	}
	var param = Object.assign(p1,p2);
	
	var options = $('#details').datagrid('options');
	//url 定义
	options.url = GLOBAL.domain + '/salesStatistics/querySkuDetails';
	$('#details').datagrid('load',{
		param : param
	});
	$('#skuDialog').window('open');
}

//关闭sku详情
var cancle = function() {
	$('#skuDialog').window('close');
}

$(function() {
	
	$("#skuCancelLinkbutton").on('click', cancle);
	$("#salesStatisticsExportLinkbutton").on('click', {url: '/salesStatistics/exportExcel',grid: '#salesStatisticsDatagrid'},export_);
	$("#detailsExportLinkbutton").on('click', {url: '/salesStatistics/detailsExcel',grid: '#details'},export_);
	$("#salesStatisticsDetailsExportLinkbutton").on('click',function(){
		$("#ExportForm").form("clear");
		$("#platform_1").combobox('loadData',$("#platform").combobox('getData'));
		$("#station_1").combobox('loadData',$("#station").combobox('getData'));
		$("#fileExport").dialog('open');
	});
	
	$("#exportSubmit").on('click',function(){
		if($("#whichTime_1").combobox('getValue') == '') {
			$.messager.alert("提示信息", "请选择时间分类!");
			return;
		}
		if($("#date1").datebox('getValue') == '' && $("#date2").datebox('getValue') == '') {
			$.messager.alert("提示信息", "请选择时间!");
			return;
		}
		$("#fileExport").dialog("close");
		window.location.href = GLOBAL.domain + '/excel/export/salesStatisticsExcel?date1='
				+$("#date1").datebox('getValue')+'&date2='+$("#date2").datebox('getValue')+
				'&platform='+$("#platform_1").combobox('getValue')+'&station='+$("#station_1").combobox('getValue')+
				'&whichTime='+$("#whichTime_1").combobox('getValue');
			
	});
	
	 $('#details').datagrid({
		 onLoadSuccess : function(data) {
			 onload('#details',"details");
		 },
		 
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	 });
	 
	$("#salesStatisticsDatagrid").datagrid({
		//成功加载之后触发
		onLoadSuccess : function(data) {
			
			if(source == 'amazon') {
				 var rows = $("#salesStatisticsDatagrid").datagrid('getFooterRows');
				 if(rows.length > 0) {
					 var row = rows[0];
					 if(row.actualSalesPrice != null) {
						 $("#salesStatisticsDatagrid").datagrid('showColumn', 'actualSalesPrice');
					 }
					 if(row.actualYield != null) {
						 $("#salesStatisticsDatagrid").datagrid('showColumn', 'actualYield');
					 }
					 if(row.actualProfitCoefficient != null) {
						 $("#salesStatisticsDatagrid").datagrid('showColumn', 'actualProfitCoefficient');
					 }
				 }
			 } else {
				 $("#salesStatisticsDatagrid").datagrid('hideColumn', 'actualSalesPrice');
				 $("#salesStatisticsDatagrid").datagrid('hideColumn', 'actualYield');
				 $("#salesStatisticsDatagrid").datagrid('hideColumn', 'actualProfitCoefficient');
			 }
			
			onload('#salesStatisticsDatagrid',"");
			//改变footer的样式
			$(".datagrid-ftable tbody tr").each(function(){
				this.style.backgroundColor="#E1EDFF";
				this.style.color="blue";
				this.style.fontWeight="bold";
			})
		},
		
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	});
	
	//来源改变事件
	$("#source").combobox({
		onChange: function (newValue,oldValue) {
			source = newValue;
			reset(newValue,"");
			echo(newValue);
			var rows = $('#salesStatisticsDatagrid').datagrid('options');
			rows.sortName = '';
			rows.sortOrder = '';
			init();
			if(newValue == 'ebay') {
				 $('#detailsExport').show();
			 } else {
				 $('#detailsExport').hide();
			 }
			if(newValue != 'allSource') {
				rows.queryParams = {param : {}};
				$('#salesStatisticsDatagrid').datagrid('loadData', { total: 0, rows: [], footer:[] }); 
			} else {
				rows.url = GLOBAL.domain + '/salesStatistics/findAllSoure';
				rows.queryParams = {param : {timeQuantum:"-30"}};
				$('#salesStatisticsDatagrid').datagrid('reload');
			}
		}
	});
	
	//平台改变事件
	$("#platform").combobox({
		onChange: function (newValue,oldValue) {
			if(source == 'amazon') {
				$('#station').combobox('setValue','');
				if(newValue != '') {
					//站点显示
					$("#station_").show();
					setPlatform(permission,newValue);
				} else {
					$("#station_").hide();
				}
			}
		}
	});
	
	//时间段改变事件
	$("#timeQuantum").combobox({
		onChange: function (newValue,oldValue) {
			$('#starttime').datebox('setValue','');
			$('#endtime').datebox('setValue','');
			if(newValue == '0') {
				$("#time_").show();
			} else {
				$("#time_").hide();
			}
		}
	});
	
	//搜索
	$("#query").click(function(){
		if(!$("#searchFrom").form('validate')) {
			return;
		}
		var starttime = $('#starttime').datebox('getValue');
		var endtime =  $('#endtime').datebox('getValue');
		if(endtime != '') {
			if(endtime < starttime) {
				$.messager.alert("提示信息", "结束时间应大于开始时间");
				return;
			}
		}
		var timeQuantum =  $('#timeQuantum').combobox('getValue');
		if(timeQuantum == '0') {
			if(starttime == '' && endtime == '') {
				$.messager.alert("提示信息", "请选择时间");
				return;
			}
		}
		/*if(source == 'ebay' && $('#platform').combobox('getValue') == '' && $('#station').combobox('getValue') == '') {
			$.messager.alert("提示信息", "请选择一个账号或者站点进行统计!");
			$("#salesStatisticsDatagrid").datagrid('loadData', { total: 0, rows: [], footer:[]  });
			return;
		}*/
		var param = getParam("");
		if(source == 'amazon' || source == 'walmart') {
			$("#salesStatisticsDatagrid").datagrid('options').url = GLOBAL.domain + '/orderQuery/findAll';
		} else if(source == 'allSource') {
			$("#salesStatisticsDatagrid").datagrid('options').url = GLOBAL.domain + '/salesStatistics/findAllSoure';
		} else {
			$("#salesStatisticsDatagrid").datagrid('options').url = GLOBAL.domain + '/salesStatistics/findAll';
		}
		
		$('#salesStatisticsDatagrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		reset(source,"");
		echo(source);
	});
	
	//绑定enter键搜索
	$(document).keydown(function(event){ 
		if (event.keyCode == "13") {//keyCode=13是回车键
			if($(".easyui-dialog").parent().is(":hidden")){
				$("#query").click();
			}
		}
	});
})


function init() {
	
	//根据平台不同加载不同的数据
	reset_(source);
	if(source != 'allSource') {
		//是否隐藏时间选择框
		var timeQuantum =  $('#timeQuantum').combobox('getValue');
		if(timeQuantum == '0') {
			$("#time_").show();
		} else {
			$("#time_").hide();
		}
		var platform = $('#platform').combobox('getValue');
		if(source == 'amazon' || source == '') {
			if(platform == '') {
				//隐藏站点
				$("#station_").hide();
			} else {
				$("#station_").show();
			}
		} else if(source == 'ebay') {
			$.ajax({
				url : GLOBAL.domain + "/salesStatistics/getEbayStation",
				type : 'POST',
				success : function(data) {
					if(data.length  > 0) {
						data = eval(data);
						var platform_ = [{text:'-- 请选择 --', value:''}];
						for (var z = 0; z < data.length; z++) {
							var platform = {
									text : data[z], 
									value: data[z]
							};
							platform_.push(platform);
						}
						$('#station').combobox('loadData',platform_);
					}
				}
			})
		}
	} else {
		$("#platform_").hide();
		$("#station_").hide();
		$("#asin_").hide();
		$("#status_").hide();
		$("#whichTime_").hide();
	}
	
	onload("#salesStatisticsDatagrid","");
	
}

function changeTerrace(data, row, index) {
	if(data == 'amazon'){
		return '亚马逊';
	} else if(data == 'ebay'){
		return "Ebay";
	} else if(data == 'light'){
		return "官网";
	} else if(data == 'walmart'){
		return "沃尔玛";
	}
	return data;
}

function changeValue(data, row, index) {
	 if(!row.footer){
		 if(data == 0 || data == null) {
			 return '';
		 } else {
			 if((data+'').indexOf("%") < 0) {
				 return  (data * 100).toFixed(2) + '%';
			 } else {
				 return data;
			 }
		 }
	 } else {
		 return data;
	 }
}
