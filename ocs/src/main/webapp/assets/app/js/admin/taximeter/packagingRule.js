$(function() {
	$("#packagingRuleDataGrid").datagrid({
		url : GLOBAL.domain + '/packagingRule/findAll',
		columns : [ [
				{
					field : 'title',
					title : '标题',
					align : 'center',
					sortable : true
				},
				{
					field : 'weight',
					title : '重量',
					align : 'center',
					sortable : true
				},
				{
					field : 'length',
					title : '长',
					align : 'center',
					sortable : true
				},
				{
					field : 'width',
					title : '宽',
					align : 'center',
					sortable : true
				},
				{
					field : 'height',
					title : '高',
					align : 'center',
					sortable : true
				},
				{
					field : 'packagingWeight',
					title : '包装重量',
					align : 'center',
					sortable : true
				},
				{
					field : 'girth',
					title : '周长',
					align : 'center',
					sortable : true
				},
				{
					field : 'regular',
					title : '价格',
					align : 'center',
					sortable : true
				},
				{
					field : 'discount',
					title : '折扣价格',
					align : 'center',
					sortable : true
				},
				{
					field : 'isMonth',
					title : '适用月份',
					align : 'center',
					sortable : true
				},
				{
					field : 'sortOrder',
					title : '排序',
					align : 'center',
					sortable : true
				},
				{
					field : 'createdAt',
					title : '创建时间',
					align : 'center',
					sortable : true,
					formatter : getTime
				},
				{
					field : 'updatedAt',
					title : '更新时间',
					align : 'center',
					sortable : true,
					formatter : getTime
				}
				] ],
		singleSelect : false,
		selectOnCheck : false,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		fit : true,
		pageSize : 50,
		border : true,
		toolbar : "#packagingRuleToolbar",
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
			var type = $("#type_").combobox('getValue');
			if(type == 1) {
				showOrHide_(new Array('title','packagingWeight'),'show','');
				showOrHide_(new Array('regular','discount','isMonth','girth'),'hide','');
			} else if(type == 2) {
				showOrHide_(new Array('regular','discount','isMonth'),'show','');
				showOrHide_(new Array('title','packagingWeight','girth'),'hide','');
			} else if(type == 3) {
				showOrHide_(new Array('title','packagingWeight','girth'),'show','');
				showOrHide_(new Array('regular','discount','isMonth'),'hide','');
			} 
        },
        onDblClickRow : function(rowIndex, rowData) {
        	$('#packagingRuleForm').form('clear');
        	init();
        	$('#packagingRuleForm').form('load', rowData);
        	//处理月份
        	var isMonth = rowData.isMonth;
        	if(isMonth) {
        		var months = isMonth.split(",");
        		var checkArry = document.getElementsByName("isMonth");
        		for (var i = 0; i < months.length; i++) {
        			for (var j = 0; j < checkArry.length; j++) {
        				if(months[i] == checkArry[j].value) {
        					$("input[type=checkbox][value='"+checkArry[j].value+"']").prop("checked", true);
        				}
        			}
        		}
        	}
        	$('#packagingRuleDialog').window('open');
        }
	})
	
	$("#packagingRuleAddLinkbutton").on('click', function(){
		$('#packagingRuleForm').form('clear');
		init();
		$('#packagingRuleDialog').window('open');
	});
	
	$("#packagingRuleSaveLinkbutton").on('click', function(){
		if(!$("#packagingRuleForm").form('validate')) {
			return;
		}
		var jsonData = $("#packagingRuleForm").serializeArray();
		var data = [];
		jsonData.forEach(function(e){  
			data.push(e);
		});
		data.push({name : 'type' , value : $('#type').combobox('getValue')});
		var url = GLOBAL.domain + "/packagingRule/saveEdit";
		$.ajax({
			url : url,
			type : 'POST',
			data : data,
			success : function(response) {
				var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$('#packagingRuleDialog').window('close');
					$("#packagingRuleDataGrid").datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
			},
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	  $.messager.alert('消息提示', '操作失败', 'warning');
		    }
		});
		
	});
	
	$("#packagingRuleCancelLinkbutton").on('click', function(){
		$('#packagingRuleDialog').window('close');
	});
	
	//来源改变事件
	$("#type_").combobox({
		onChange: function (newValue,oldValue) {
			var rows = $('#packagingRuleDataGrid').datagrid('options');
			rows.sortName = '';
			rows.sortOrder = '';
			var param = {
					type : newValue
			};
			$('#packagingRuleDataGrid').datagrid('load',{
				param : param
			});
		}
	});
	
	$("#packagingRuleUploadLinkbutton").on('click', lightEbayUpload);
	
	$("#packagingRuleExportLinkbutton").unbind().on('click', function(){
		lightEbayExport({data:{url: '/excel/template/packagingRuleImport?type='+$("#type_").combobox('getValue')}});
	});
	
	$("#packagingRuleExportAllLinkbutton").unbind().on('click', function(){
		export_({data:{url: '/excel/export/packagingRuleExport?type='+$("#type_").combobox('getValue'),grid: '#packagingRuleDataGrid'}});
	});
	
	$("#upload").unbind().on('click', function(){
		newUpload({data:{grid: '#packagingRuleDataGrid', dialog: '#uploadDialog', url: '/excel/import/packagingRuleImport?type='+$("#type_").combobox('getValue')}});
	});
})

function showOrHide_(array,value1,value2) {
	$.each(array,function(){
		if(value1 == 'show') {
			value2 == '' ? $('#packagingRuleDataGrid').datagrid('showColumn', this) : $('#'+this).show();
		} else if(value1 == 'hide') {
			value2 == '' ? $('#packagingRuleDataGrid').datagrid('hideColumn', this) : $('#'+this).hide();
		}
	})
}

function required_(array,value1,value2) {
	$.each(array,function(){
		if(value2 = 'textbox') {
			$('#' + this).textbox({required:value1});
		} else if(value2 = 'numberbox') {
			$('#' + this).numberbox({required:value1});
		}
	})
}

function init() {
	var type = $("#type_").combobox('getValue');
	$("#type").combobox('setValue', type);
	if(type == 1) {
		showOrHide_(new Array('title_','packagingWeight_'),'show','value');
		showOrHide_(new Array('regular_','discount_','isMonth_','girth_'),'hide','value');
	} else if(type == 2) {
		showOrHide_(new Array('regular_','discount_','isMonth_'),'show','value');
		showOrHide_(new Array('title_','packagingWeight_','girth_'),'hide','value');
	} else if(type == 3) {
		showOrHide_(new Array('title_','packagingWeight_','girth_'),'show','value');
		showOrHide_(new Array('regular_','discount_','isMonth_'),'hide','value');
	}
}