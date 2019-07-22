$(function() {
	formatterDate('date');
	
	$("#operatingProfitLinkDataGrid").datagrid({
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
        }
	})
	
	
	
	$("#operatingProfitLinkUploadLinkbutton").on('click', function(){
		lightEbayUpload();
	});
	
	$("#operatingProfitLinkExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/operatingProfitLinkImport'}});
	});
	
	
	
	$("#upload").unbind().on('click', function(){
		newUpload({data:{grid: '#operatingProfitLinkDataGrid', dialog: '#uploadDialog', url: '/excel/import/operatingProfitLinkImport'}});
	});
	
	$("#query").click(function(){
		var formData = $("#searchForm").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$('#operatingProfitLinkDataGrid').datagrid('load',{
			param : param
		});
	});
	
	$("#reset").on('click',function(){
		$("#searchForm").form("clear");
	});
})