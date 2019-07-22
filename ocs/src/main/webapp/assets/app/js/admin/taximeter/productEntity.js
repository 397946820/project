//打开添加的窗口
var add = function() {
	$('#productEntityForm').form('clear');
	$('#dg').datagrid("loadData", []);
	$("#isMultiOne").combobox('setValue','0');
	$("#isPeu").combobox('setValue','0');
	$("#status").combobox('setValue','1');
	$("#currency").combobox('setValue','RMB');
	boxRead(new Array('length','width','height','netWeight','grossWeight','packingQty'),'enable','numberbox');
	boxRead(new Array('sku'),'enable','textbox');
	$('#productEntityDialog').window('open');
};

//只读 或者 启动
function boxRead(data,value,box) {
	for (var i = 0; i < data.length; i++) {
		if(box == 'numberbox') {
			$('#' + data[i]).numberbox(value);
		} else if(box == 'textbox') {
			$('#' + data[i]).textbox(value);
		} else if(box == 'combobox') {
			$('#' + data[i]).combobox(value);
		}
	}
}

function editOpen($row) {
	$('#productEntityForm').form('clear');
	$('#productEntityForm').form('load', $row);
	$('#entityId').val($row.entityId);
	if($row.status == '1') {
		boxRead(new Array('length','width','height','netWeight','grossWeight','packingQty'),'disable','numberbox');
		boxRead(new Array('sku'),'disable','textbox');
	} else {
		boxRead(new Array('length','width','height','netWeight','grossWeight'),'enable','numberbox');
		boxRead(new Array('sku'),'enable','textbox');
	}
	var param = {
		parentId : $row.entityId
	}
	var options = $('#dg').datagrid('options');
	//url 定义
	options.url = GLOBAL.domain + '/productOther/findByParentId';
	$('#dg').datagrid('load',{
		param : param
	});
	$('#productEntityDialog').window('open');
}

function getData(){
	var entityId = $('#entityId').val(), 
	sku = $('#sku').textbox('getValue'),
	length = $('#length').numberbox('getValue'),
	width = $('#width').numberbox('getValue'),
	height = $('#height').numberbox('getValue'),
	netWeight = $('#netWeight').numberbox('getValue'),
	grossWeight = $('#grossWeight').numberbox('getValue'),
	packingQty = $('#packingQty').numberbox('getValue'),
	outerVolume = $('#outerVolume').numberbox('getValue'),
	outerWeight = $('#outerWeight').numberbox('getValue'),
	isMultiOne = $('#isMultiOne').combobox('getValue'),
	isPeu = $('#isPeu').combobox('getValue'),
	status = $('#status').combobox('getValue'),
	price = $('#price').numberbox('getValue'),
	currency = $('#currency').combobox('getValue'),
	taxRebateRate = $('#taxRebateRate').numberbox('getValue'),
	interestRate = $('#interestRate').numberbox('getValue');
	//获取产品其他信息
	var others = $("#dg").datagrid("getRows");
	
	return {entityId : entityId, sku : sku, length : length, width : width, height : height, netWeight : netWeight,
		grossWeight : grossWeight, packingQty : packingQty, outerVolume : outerVolume, outerWeight : outerWeight,
		isMultiOne : isMultiOne, isPeu : isPeu, status : status, price : price, currency : currency, 
		taxRebateRate : taxRebateRate, interestRate : interestRate , others : JSON.stringify(others)
	}
}

// 保存和修改
var save = function() {
	if(!$("#productEntityForm").form('validate')) {
		return;
	}
	//获取产品其他信息
	var data = $("#dg").datagrid("getRows");
	for (var i = 0; i < data.length; i++) {
		if(data[i].countryId == '') {
			$.messager.alert('消息提示', "产品其他的第" + (i+1) + "行的国家未选择", 'warning');
			return;
		}
	}
	data = getData();
	var url = GLOBAL.domain + "/productEntity/saveEdit";
	$.ajax({
		url : url,
		type : 'POST',
		data : data,
		beforeSend: function () {
            $.messager.progress({
                title: '请稍后',
                msg: '正在操作...'
            });
        },
        complete: function () {
            $.messager.progress('close');
        },
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#productEntityDialog').window('close');
				$("#productEntityDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#productEntityDataGrid').datagrid('clearSelections');
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	})
};

var cancle = function() {
	$('#productEntityDialog').window('close');
}

var beforeUpload = function(url) {
	$('#uploadForm').form('clear');
	$('#upload').data('url', url);
	$('#uploadDialog').window('open');
}

var productEntityUpload = function() {
	beforeUpload('/productEntity/uploadExcel');
}

var beforeImportProductPrice = function() {
	beforeUpload('/productEntity/importProductPrice');
}

function doAdd() {
	$("#productOther").datagrid('insertRow',{
		index : 0,
		row : {}
	});
	$("#productOther").datagrid('beginEdit',0);
}

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#dg').datagrid('validateRow', editIndex)){
		$('#dg').datagrid('endEdit', editIndex);
		return true;
	} else {
		return false;
	}
}

function onClickCell(index) { 
	$('#dg').datagrid('clearSelections');
    if (endEditing()) {  
        $('#dg').datagrid('selectRow', index)  
                .datagrid('beginEdit', index);  
		//获取编辑行  
		var editors = $('#dg').datagrid('getEditors', index); 
		for (var i = 0; i < editors.length; i++) {
			var editor = editors[i];  
			//绑定失焦事件并取消可编辑状态  
			editor.target.bind('blur', function () { 
				if ($('#dg').datagrid('validateRow', index)){
					$('#dg').datagrid('selectRow', index).datagrid('endEdit', index);  
					editIndex = undefined;  
				}
			});  
		}
        editIndex = index;  
    } else {  
        $('#dg').datagrid('selectRow', editIndex);  
    }  
}  

function onEndEdit(index, row){
	var ed = $(this).datagrid('getEditor', {
		index: index,
		field: 'productid'
	});
	
}
function append(){
	var rows = $("#dg").datagrid("getRows");
	if(rows.length < 9) {
		if (editIndex != undefined) {
			$('#dg').datagrid('unselectRow', editIndex);
			$('#dg').datagrid("endEdit", editIndex);
		}
		if (endEditing()){
			$('#dg').datagrid('appendRow',{});
			editIndex = $('#dg').datagrid('getRows').length-1;
			$('#dg').datagrid('selectRow', editIndex)
			.datagrid('beginEdit', editIndex);
		}
	}
}
function removeit(){
	//删除时先获取选择行
    var rows = $("#dg").datagrid("getSelections");
    //选择要删除的行
    if (rows.length > 0) {
        $.messager.confirm("提示", "你确定要删除吗?", function (r) {
            if (r) {
                for (var i = 0; i < rows.length; i++) {
                	var rowIndex = $('#dg').datagrid('getRowIndex', rows[i]);
                	$("#dg").datagrid('deleteRow', rowIndex);
                }
            }
        });
    }
    else {
        $.messager.alert("提示", "请选择要删除的行", "error");
    }
}
$(function() {
	$("#productEntityAddLinkbutton").on('click', add);
	$("#productEntitySaveLinkbutton").on('click', save);
	$("#productEntityCancelLinkbutton").on('click', cancle);
	$("#productEntityExportLinkbutton").on('click', { url : '/productEntity/exportExcel',grid: '#productEntityDataGrid'},derive);
	$("#productEntityUploadLinkbutton").on('click', productEntityUpload);
	$('#btnImportProductPrice').on('click', beforeImportProductPrice);
	//$("#upload").on('click', {grid: '#productEntityDataGrid', dialog: '#uploadDialog', url: '/productEntity/uploadExcel'}, upload);
	var uploading = function(event) {
		event.data.url = $("#upload").data('url');
		upload.apply(this, [ event ]);
	}
	$("#upload").on('click', { grid: '#productEntityDataGrid', dialog: '#uploadDialog' }, uploading);
	
	$("#productEntityDataGrid").datagrid({
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
            editIndex = undefined;
		}
	});
	
	//绑定结束编辑
	$(document).keydown(function(event){ 
		if (event.keyCode == "13") {//keyCode=13是回车键
			if ($('#dg').datagrid('validateRow', editIndex)){
				$('#dg').datagrid('endEdit', editIndex);
			}
		}
	});
	
	// 遮罩
	$('#productEntityDialog').dialog({
		modal:true
	});
	
	//查询
	$("#query").click(function(){
		if(!$("#productEntity_Form").form('validate')) {
			return;
		}
		var boolean = moreTime();
		if(boolean) {
			$.messager.alert("提示信息", "结束时间应大于开始时间");
			return;
		}
		var param = {
				sku : $('#sku_').textbox('getValue'),
				username : $('#username_').textbox('getValue'),
				length : $('#length_').numberbox('getValue'),
				width : $('#width_').numberbox('getValue'),
				height : $('#height_').numberbox('getValue'),
				netWeight : $('#netWeight_').numberbox('getValue'),
				grossWeight : $('#grossWeight_').numberbox('getValue'),
				price : $('#price_').numberbox('getValue'),
				packingQty : $('#packingQty_').numberbox('getValue'),
				outerVolume : $('#outerVolume_').numberbox('getValue'),
				outerWeight : $('#outerWeight_').numberbox('getValue'),
				isMultiOne : $('#isMultiOne_').combobox('getValue'),
				isPeu : $('#isPeu_').combobox('getValue'),
				status : $('#status_').combobox('getValue'),
				taxRebateRate : $('#taxRebateRate_').numberbox('getValue'),
				interestRate : $('#interestRate_').numberbox('getValue'),
				cstarttime : $('#cstarttime').datebox('getValue'),
				cendtime : $('#cendtime').datebox('getValue'),
				ustarttime : $('#ustarttime').datebox('getValue'),
				uendtime : $('#uendtime').datebox('getValue')
		}
		
		$('#productEntityDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#sku_').textbox('setValue',''),
		$('#username_').textbox('setValue',''),
		$('#length_').numberbox('setValue','');
		$('#width_').numberbox('setValue','');
		$('#height_').numberbox('setValue','');
		$('#netWeight_').numberbox('setValue','');
		$('#grossWeight_').numberbox('setValue','');
		$('#price_').numberbox('setValue','');
		$('#packingQty_').numberbox('setValue','');
		$('#outerVolume_').numberbox('setValue','');
		$('#outerWeight_').numberbox('setValue','');
		$('#isMultiOne_').combobox('setValue','');
		$('#isPeu_').combobox('setValue','');
		$('#status_').combobox('setValue','');
		$('#taxRebateRate_').numberbox('setValue',''),
		$('#interestRate_').numberbox('setValue','');
		$('#cstarttime').datebox('setValue','');
		$('#cendtime').datebox('setValue','');
		$('#ustarttime').datebox('setValue','');
		$('#uendtime').datebox('setValue','');
	});
	
	$("#downloadModelExcel").on("click",function(){
		var cv = $("#templateNew").combobox('getValue'); 
		if(cv =="CW"){
			window.location.href = "/ocs/excel/template/productInfoImport";
		}else if(cv =="CP"){
			window.location.href = "/ocs/excel/template/productInfoAvgMonthImport";
		}
	})
	
	$("#proInfoUploadBtn").on("click",function(){
		$("#fileUpload").dialog("open");
		$('#fileUploadInput').filebox({
		    buttonText: '文件选择',
		    buttonAlign: 'right'
		});
	})
	$("#uploadSubmit").click(function(){
		$('#ImportForm').form('submit', {
			onSubmit: function(){
				var file = $("#fileUploadInput").filebox('getValue'); 
			    if(file == '' || file == null) {
					$.messager.alert("提示信息", "请选择导入的文件");
					return false;
				}
			    var site = $("#importSite").combobox('getValue');
			    if(site != null) {
			    	if(site == "CW") {
			    		$('#ImportForm').attr("action","/ocs/excel/import/productInfoImport");
			    	} else if(site == "CP") {
			    		$('#ImportForm').attr("action","/ocs/excel/import/productInfoAvgMonthImport");
			    	}
			    } else {
			    	$.messager.alert("提示信息", "请选择导入那个部门数据");
					return false;
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
	
	$("#modifyDiscontinue").on("click",function(){
		var rows = $("#productEntityDataGrid").datagrid("getChecked");
		if(rows&&rows.length > 0){
			var row = rows[0];
			$("#disSkuName").text(row.sku);
			$("#disSku").val(row.sku);
			initDisSKU(row.sku,'amazon');
			$("#disWin").dialog("open");
		}else{
			$.messager.alert("信息","请选择一个SKU！");	
		}
	});
	$('#platform').combobox({
	    onChange: function (newValue, oldValue) {
	       var sku = $("#disSku").val();
	       initDisSKU(sku,newValue);
	    }
	});

	function initDisSKU(sku,platform){
		ocs.ajax({
			url: "/productEntity/getSkuDisInfo/"+sku+"/"+platform,
	        type: "POST",
	        data: "",
	        success:function(re){
	        	if(re.data){
	        		$("#skuDisForm").form("load",re.data);
	        	}
	        }
		});     
	}
	
	$("#disOptSave").on("click",function(){
		var sku = $("#disSku").val();
		var platform = $("#platform").combobox("getValue");
		var data = $("#skuDisForm").serializeArray();
		var v = false;
		var saveData = {};
		var skuDis = [];
		
		$.each(data,function(){
			if(this.name != "sku" && this.name != "platform"){
				if(this.value == -1){
					v = true;
					return false;
				}
				var c = {};
				c["sku"] = sku;
				c["platform"] = platform;
				c["countryId"] = this.name;
				c["isDis"] = this.value;
				skuDis.push(c);
			}
			
		});
		if(v){
			$.messager.alert("信息","请为每一个国家设置DIS状态！");	
			return;
		}
		saveData["skuDisInfo"] = skuDis;
		//保存
		ocs.ajax({
			url: "/productEntity/saveSkuDisInfo",
	        type: "POST",
	        data: saveData,
	        success:function(re){
	        	if(re){
	        		$("#skuDisForm").form("reset");
	        		$("#disWin").dialog("close");
	        	}
	        }
		});     
	});
	
	$("#disOptCancel").on("click",function(){
		$("#skuDisForm").form("reset");
		$("#disWin").dialog("close");
		
	});
});

function getFlag(value,row,index){
	if (value == '0'){
		return '否';
	} else {
		return '是';
	}
}

function getStatus(value,row,index){
	if (value == '0'){
		return '不启用';
	} else if (value == '1') {
		return '启用';
	} else if (value == '2') {
		return '测试';
	}
	return value;
}
