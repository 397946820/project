var source = '';
var permission = '';

$(function() {
	$("#salesOrderExportLinkbutton").on('click', {url: '/salesOrder/salesOrderExport',grid: '#salesOrderDatagrid'},export_);
	
	$("#salesOrderDatagrid").datagrid({
		//成功加载之后触发
		onLoadSuccess : function(data) {
			
		},
		
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	});
	
	$("#salesOrderDatagrid").datagrid('options').url = GLOBAL.domain + '/salesOrder/findAll';
	//来源改变事件
	$("#source").combobox({
		onChange: function (newValue,oldValue) {
			source = newValue;
			reset(newValue,"flag");
			echo(newValue);
			var rows = $('#salesOrderDatagrid').datagrid('options');
			rows.sortName = '';
			rows.sortOrder = '';
			rows.queryParams = {param : {}};
			init();
			$('#salesOrderDatagrid').datagrid('loadData', { total: 0, rows: [], footer:[] }); 
		}
	});
	
	//平台改变事件
	$("#platform").combobox({
		onChange: function (newValue,oldValue) {
			$('#station').combobox('setValue','');
			if(source != 'light' && source != 'walmart') {
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
		
		var param = getParam("flag");
		
		$('#salesOrderDatagrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		reset(source,"flag");
		echo(source);
	});
	
})

function init() {
	var platform = $('#platform').combobox('getValue');
	//根据平台不同加载不同的数据
	reset_(source);
	
	if(source == 'amazon' || source == 'ebay' || source == '') {
		if(platform == '') {
			//隐藏站点
			$("#station_").hide();
		} else {
			$("#station_").show();
		}
	}
	onload("#salesOrderDatagrid","flag");
}

