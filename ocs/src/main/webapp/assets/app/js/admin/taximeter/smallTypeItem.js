var saveEdit = '';
var upload = '';

function add(formId,url,grid,dialog) {
	if (!$("#"+formId).form('validate')) {
		return;
	}
	var jsonData = $("#"+formId).serializeArray();
	var data = [];
	jsonData.forEach(function(e) {
		data.push(e);
	});
	if(formId == 'smallTypeItemForm' && saveEdit == 'edit') {
		data.push({name: "typeName", value: $('#typeName_').combobox('getValue')});
		data.push({name: "shippingType", value: $('#shippingType_').combobox('getValue')});
	} else if(formId == 'smallTypeForm') {
		data.push({name: "typeName", value: $('#typeName_1').combobox('getValue')});
	}
	var url = GLOBAL.domain + url;
	$.ajax({
		url : url,
		type : 'POST',
		data : data,
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#'+dialog).window('close');
				$("#"+grid).datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert('消息提示', '操作失败', 'warning');
		}
	});
}

$(function() {
	comboboxInit('shippingType', '');
	comboboxInit('shippingType_', '');
	comboboxInit('typeName', '/smallTypeItem/getTypeName');
	comboboxInit('typeName_', '/smallTypeItem/getTypeName');
	comboboxChange('typeName', 'shippingType');
	comboboxChange('typeName_', 'shippingType_');
	
	$("#smallTypeLinkbutton").click(function(){
		$("#smallTypeDataGrid").datagrid({
	        url: GLOBAL.domain + '/smallType/findAll',
	        queryParams : {
				param : {
					
				}
			},
			onDblClickRow : function(rowIndex, rowData) {
				$('#smallTypeForm').form('clear');
				$('#smallTypeForm').form('load', rowData);
				//下拉框不可选
				$('#smallTypeDialog_').window('open');
			}
		});
		$("#smallTypeDialog").dialog("open");
	});
	
	$("#smallTariffRateLinkbutton").click(function(){
		$("#smallTariffRateDataGrid").datagrid({
	        url: GLOBAL.domain + '/smallTariffRate/findAll',
	        queryParams : {
				param : {
					
				}
			},
			onDblClickRow : function(rowIndex, rowData) {
				$('#smallTariffRateForm').form('clear');
				$('#smallTariffRateForm').form('load', rowData);
				//下拉框不可选
				$('#smallTariffRateDialog_').window('open');
			}
		});
		$("#smallTariffRateDialog").dialog("open");
	});
	
	$("#smallTypeItemAddLinkbutton").on('click', function(){
		$('#smallTypeItemForm').form('clear');
		$('#typeName_').combobox('enable');
    	$('#shippingType_').combobox('disable');
		$('#smallTypeItemDialog').window('open');
		saveEdit = 'save';
	});

	$("#smallTypeItemDataGrid").datagrid({
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
		},
		onDblClickRow : function(rowIndex, rowData) {
			$('#smallTypeItemForm').form('clear');
			$('#smallTypeItemForm').form('load', rowData);
			//下拉框不可选
        	$('#typeName_').combobox('disable');
        	$('#shippingType_').combobox('disable');
			$('#smallTypeItemDialog').window('open');
			saveEdit = 'edit';
		}
	})

	$("#smallTypeItemSaveLinkbutton").on('click', function() {
		add('smallTypeItemForm','/smallTypeItem/saveEdit','smallTypeItemDataGrid','smallTypeItemDialog');
	});
	
	$("#smallTypeSaveLinkbutton").on('click', function() {
		add('smallTypeForm','/smallType/saveEdit','smallTypeDataGrid','smallTypeDialog_');
	});
	
	$("#smallTariffRateSaveLinkbutton").on('click', function() {
		add('smallTariffRateForm','/smallTariffRate/saveEdit','smallTariffRateDataGrid','smallTariffRateDialog_');
	});

	$("#smallTypeItemCancelLinkbutton").on('click', function() {
		$('#smallTypeItemDialog').window('close');
	});
	
	$("#smallTypeCancelLinkbutton").on('click', function() {
		$('#smallTypeDialog_').window('close');
	});
	
	$("#smallTariffRateCancelLinkbutton").on('click', function() {
		$('#smallTariffRateDialog_').window('close');
	});

	$("#smallTypeItemUploadLinkbutton").on('click', function() {
		upload = '1';
		lightEbayUpload();
	});
	
	$("#smallTypeUploadLinkbutton").on('click', function() {
		upload = '2';
		lightEbayUpload();
	});
	
	$("#smallTariffRateUploadLinkbutton").on('click', function() {
		upload = '3';
		lightEbayUpload();
	});

	$("#smallTypeItemExportLinkbutton").unbind().on('click', function() {
		lightEbayExport({data : {url : '/excel/template/smallTypeItemImport'}});
	});
	
	$("#smallTypeExportLinkbutton").unbind().on('click', function() {
		lightEbayExport({data : {url : '/excel/template/smallTypeImport'}});
	});
	
	$("#smallTariffRateExportLinkbutton").unbind().on('click', function() {
		lightEbayExport({data : {url : '/excel/template/smallTariffRateImport'}});
	});

	$("#upload").unbind().on('click', function() {
		var grid = '';
		var url = '';
		if(upload == '1') {
			grid ='#smallTypeItemDataGrid';
			url = '/excel/import/smallTypeItemImport';
		} else if(upload == '2') {
			grid ='#smallTypeDataGrid';
			url = '/excel/import/smallTypeImport';
		} else if(upload == '3') {
			grid ='#smallTariffRateDataGrid';
			url = '/excel/import/smallTariffRateImport';
		}
		newUpload({
			data : {
				grid : grid,
				dialog : '#uploadDialog',
				url : url
			}
		});
	});

	$("#query").click(function() {
		var formData = $("#smallTypeItemSearchForm").serializeArray();
		var param = {};
		$.each(formData, function() {
			param[this.name] = this.value;
		});
		$('#smallTypeItemDataGrid').datagrid('load', {
			param : param
		});
	});

	$("#reset").on('click', function() {
		$("#smallTypeItemSearchForm").form("clear");
	});
})