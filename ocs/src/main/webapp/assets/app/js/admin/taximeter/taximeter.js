$(function() {
	
	$("#taximeterExportLinkbutton").on('click', {url : '/taximeter/exportExcel',grid: '#taximeterDataGrid'},derive);
	
	$("#taximeterDataGrid").datagrid({
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	});
	
	
	//查询
	$("#query").click(function(){
		if(!$("#rateForm").form('validate')) {
			return;
		}
		var boolean = moreTime();
		if(boolean) {
			$.messager.alert("提示信息", "结束时间应大于开始时间");
			return;
		}
		var param = {
				country : $('#country').combobox('getValue'),
				sku : $('#sku').textbox('getValue'),
				description : $('#description').textbox('getValue'),
				batch : $('#batch').textbox('getValue'),
				shippingtype : $('#shippingtype').combobox('getValue'),
				qty : $('#qty').numberbox('getValue'),
				price : $('#price').numberbox('getValue'),
				balance : $('#balance').numberbox('getValue'),
				cstarttime : $('#cstarttime').datebox('getValue'),
				cendtime : $('#cendtime').datebox('getValue'),
				ustarttime : $('#ustarttime').datebox('getValue'),
				uendtime : $('#uendtime').datebox('getValue')
		}
		
		$('#taximeterDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#country').combobox('setValue','');
		$('#sku').textbox('setValue','');
		$('#description').textbox('setValue','');
		$('#batch').textbox('setValue','');
		$('#shippingtype').combobox('setValue','');
		$('#qty').numberbox('setValue','');
		$('#price').numberbox('setValue','');
		$('#balance').numberbox('setValue','');
		$('#cstarttime').datebox('setValue','');
		$('#cendtime').datebox('setValue','');
		$('#ustarttime').datebox('setValue','');
		$('#uendtime').datebox('setValue','');
	});
	
});
