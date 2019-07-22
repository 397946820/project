$(function() {
	formatterDate('date');
	
	$("#operatingProfitSalaryDataGrid").datagrid({
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        },
        onDblClickRow : function(rowIndex, rowData) {
        	$('#operatingProfitSalaryForm').form('clear');
        	$('#operatingProfitSalaryForm').form('load', rowData);
        	$('#operatingProfitSalaryDialog').window('open');
        }
	})
	
	$("#operatingProfitSalarySaveLinkbutton").on('click', function(){
		if(!$("#operatingProfitSalaryForm").form('validate')) {
			return;
		}
		var jsonData = $("#operatingProfitSalaryForm").serializeArray();
		var data = [];
		jsonData.forEach(function(e){  
			data.push(e);
		});
		var url = GLOBAL.domain + "/operatingProfitSalary/saveEdit";
		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			success : function(response) {
				var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$('#operatingProfitSalaryDialog').window('close');
					$("#operatingProfitSalaryDataGrid").datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
			},
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	  $.messager.alert('消息提示', '操作失败', 'warning');
		    }
		});
	});
	
	$("#operatingProfitSalaryCancelLinkbutton").on('click', function(){
		$('#operatingProfitSalaryDialog').window('close');
	});
	
	$("#operatingProfitSalaryUploadLinkbutton").on('click', function(){
		lightEbayUpload();
	});
	
	$("#operatingProfitSalaryExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/operatingProfitSalaryImport'}});
	});
	
	$("#upload").unbind().on('click', function(){
		newUpload({data:{grid: '#operatingProfitSalaryDataGrid', dialog: '#uploadDialog', url: '/excel/import/operatingProfitSalaryImport'}});
	});
	
	$("#query").click(function(){
		var formData = $("#salarySearchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#operatingProfitSalaryDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#reset").on('click',function(){
		$("#salarySearchForm").form("clear");
	});
})