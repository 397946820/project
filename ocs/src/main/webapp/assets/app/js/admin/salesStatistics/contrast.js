var oracle = null;
var mysql = null;
var original = null;

var contrastRateUpload = function() {
	$('#aa').combobox('setValue','');
	$('#uploadForm').form('clear');
	$('#uploadDialog').window('open');
}

var upload = function() {
	var formData = new FormData();
	var myfile = document.getElementById("file").files[0];
	if (myfile == '' || myfile == null) {
		$.messager.alert("提示信息", "请选择导入的文件");
		return;
	}
	formData.append("myfile", myfile);
	$.ajax({
		type : "POST",
		url : GLOBAL.domain + '/contrast/uploadExcel',
		enctype : 'multipart/form-data',
		data : formData,
		contentType : false,
		processData : false,
		beforeSend: function () {
             $.messager.progress({
                 title: '请稍后',
                 msg: '正在导入记录...'
             });
         },
         complete: function () {
             $.messager.progress('close');
         },
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#uploadDialog').window('close');
				show2();
				$("#tool").show();
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$.messager.alert('消息提示', '导入失败', 'warning');
		}
	});
}

function show(newValue,temp) {
	//查询条件置为空
	oracle.datagrid('options').queryParams = {};
	mysql.datagrid('options').queryParams = {};
	original.datagrid('options').queryParams = {};
	var param = null;
	if(temp == '') {
		param = {
				flag : newValue
		}
	} else if(temp == 'query') {
		var param = {
				flag : newValue,
				orderId : $('#orderId').textbox('getValue'),
				sku : $('#sku').textbox('getValue'),
				status : $('#status').combobox('getValue'),
				station : $('#station').combobox('getValue')
		}
	}
	if(newValue == '1' || newValue == '2') {
		
		original.datagrid('load',{
			param : param
		});
		oracle.datagrid('load',{
			param : param
		});
		
		if(newValue == '1') {
			$('#oracle_').panel({title: "Oracle没有的数据"});
			$('#original_').panel({title: "导入的数据"});
		} else if (newValue == '2') {
			$('#oracle_').panel({title: "Oracle的数据"});
			$('#original_').panel({title: "导入没有的的数据"});
		}
		$('#mysql_').panel({title: "Mysql的数据"});
		
		mysql.datagrid('loadData', { total: 0, rows: [], footer:[] }); 
		$('#cc').layout('expand','east');
		$('#cc').layout('collapse','west');
		$('#cc').layout('expand','west');
		$('#cc').layout('collapse','west');
	} else if(newValue == '3' || newValue == '4') {
		
		original.datagrid('load',{
			param : param
		});
		mysql.datagrid('load',{
			param : param
		});
		
		
		if(newValue == '3') {
			$('#mysql_').panel({title: "Mysql没有的数据"});
			$('#original_').panel({title: "导入的数据"});
		} else if (newValue == '4') {
			$('#mysql_').panel({title: "Mysql的数据"});
			$('#original_').panel({title: "导入没有的数据"});
		}
		$('#oracle_').panel({title: "Oracle的数据"});
		
		oracle.datagrid('loadData', { total: 0, rows: [], footer:[]  });
		$('#cc').layout('expand','west');
		$('#cc').layout('collapse','east');
		$('#cc').layout('expand','east');
		$('#cc').layout('collapse','east');
	} else if(newValue == '5' || newValue == '') {
		original.datagrid('load',{
			param : param
		});
		
		$('#mysql_').panel({title: "Mysql的数据"});
		$('#original_').panel({title: "导入的数据"});
		$('#oracle_').panel({title: "Oracle的数据"});
		
		oracle.datagrid('loadData', { total: 0, rows: [], footer:[]  });
		mysql.datagrid('loadData', { total: 0, rows: [], footer:[]  });
		$('#cc').layout('collapse','east');
		$('#cc').layout('collapse','west');
	} else if ( newValue == '6') {
		oracle.datagrid('load',{
			param : param
		});
		$('#mysql_').panel({title: "Mysql的数据"});
		$('#original_').panel({title: "导入的数据"});
		$('#oracle_').panel({title: "Oracle的数据"});
		
		original.datagrid('loadData', { total: 0, rows: [], footer:[]  });
		mysql.datagrid('loadData', { total: 0, rows: [], footer:[]  });
		$('#cc').layout('expand','east');
		$('#cc').layout('collapse','west');
		$('#cc').layout('expand','west');
		$('#cc').layout('collapse','west');
	} else if ( newValue == '7') {
		mysql.datagrid('load',{
			param : param
		});
		$('#mysql_').panel({title: "Mysql的数据"});
		$('#original_').panel({title: "导入的数据"});
		$('#oracle_').panel({title: "Oracle的数据"});
		
		oracle.datagrid('loadData', { total: 0, rows: [], footer:[]  });
		original.datagrid('loadData', { total: 0, rows: [], footer:[]  });
		$('#cc').layout('expand','west');
		$('#cc').layout('collapse','east');
		$('#cc').layout('expand','east');
		$('#cc').layout('collapse','east');
	} else if ( newValue == '8') {
		mysql.datagrid('load',{
			param : param
		});
		oracle.datagrid('load',{
			param : param
		});
		$('#mysql_').panel({title: "Mysql的数据"});
		$('#original_').panel({title: "导入的数据"});
		$('#oracle_').panel({title: "Oracle没有的数据"});
		original.datagrid('loadData', { total: 0, rows: [], footer:[]  });
		$('#cc').layout('expand','west');
		$('#cc').layout('expand','east');
	}
}

function show2() {
	original.datagrid('options').queryParams = {};
	original.datagrid('load',{});
	oracle.datagrid('loadData', { total: 0, rows: [], footer:[]  });
	mysql.datagrid('loadData', { total: 0, rows: [], footer:[]  });
	//加载隐藏东西两侧
	$('#cc').layout('collapse','east');
	$('#cc').layout('collapse','west');
}

function reset_() {
	$('#orderId').textbox('setValue','');
	$('#sku').textbox('setValue','');
	$('#status').combobox('setValue','');
	$('#station').combobox('setValue','');
}

$(function() {
	$("#contrastUploadLinkbutton").on('click', contrastRateUpload);
	$("#upload").on('click', upload);
	
	//加载隐藏东西两侧
	$('#cc').layout('collapse','east');
	$('#cc').layout('collapse','west');
	$("#tool").hide();
	$('#original_').panel({title: "导入的数据"});
	//搜索
	$("#query").click(function(){
		var newValue = $('#aa').combobox('getValue');
		show(newValue,'query');
	});
	
	//重置
	$("#reset").click(function(){
		reset_();
	});
	
	//数据展示事件
	$("#aa").combobox({
		onChange: function (newValue,oldValue) {
			reset_();
			if(newValue == '') {
				show2();
			} else {
				show(newValue,'');
			}
			//订单状态的转换
			if(newValue == '' || newValue == '1' || newValue == '3' || newValue == '5') {
				$('#status').combobox('loadData', [{text:'-- 请选择 --', value:''},{text:'Shipped', value:'Shipped'},
												{text:'Cancelled', value:'Cancelled'},{text:'Unshipped', value:'Unshipped'}]);
			} else {
				$('#status').combobox('loadData', [{text:'-- 请选择 --', value:''},{text:'Shipped', value:'Shipped'},
					{text:'Unshipped', value:'Unshipped'},{text:'Canceled', value:'Canceled'},{text:'Pending', value:'Pending'}]);
			}
		}
	});

	oracle = $('#oracle').datagrid({  
        url: '', 
        fit : true,  
        rownumbers : true,
		pagination : true,
        fitColumns : true,  
        striped : true,  
        border : false, 
        showFooter: true,
        pageSize : 30,
		pageList : [ 20, 30, 50 ],
        columns : [[  
        	{field: 'orderId', title: 'amazon-order-id'},
            {field: 'purchaseat', title: 'purchase-date',formatter:getTime},
            {field: 'updatedat', title: 'last-updated-date',formatter:getTime,sortable:true},
            {field: 'status', title: 'order-status'},
            {field: 'station', title: 'sales-channel'},
            {field: 'sku', title: 'sku'},
            {field: 'asin', title: 'asin'},
            {field: 'count', title: 'quantity'},
            {field: 'currencycode', title: 'currency'},
            {field: 'price', title: 'item-price',sortable:true}
        ]],
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
    });
	
	//url 定义
	oracle.datagrid('options').url = GLOBAL.domain + '/contrast/findAllOracle';
	
	mysql = $('#mysql').datagrid({  
        url: '', 
        fit : true,  
        rownumbers : true,
		pagination : true,
        fitColumns : true,  
        striped : true,  
        border : false,  
        showFooter: true,
        pageSize : 30,
		pageList : [ 20, 30, 50 ],
        columns : [[  
        	{field: 'orderId', title: 'amazon-order-id'},
            {field: 'purchaseat', title: 'purchase-date',formatter:getTime},
            {field: 'updatedat', title: 'last-updated-date',formatter:getTime,sortable:true},
            {field: 'status', title: 'order-status'},
            {field: 'station', title: 'sales-channel'},
            {field: 'sku', title: 'sku'},
            {field: 'asin', title: 'asin'},
            {field: 'count', title: 'quantity'},
            {field: 'currencycode', title: 'currency'},
            {field: 'price', title: 'item-price',sortable:true}
        ]],
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
    });
	
	//url 定义
	mysql.datagrid('options').url = GLOBAL.domain + '/contrast/findAllMysql';
	
	original = $('#original').datagrid({  
		url: '', 
		fit : true,  
		rownumbers : true,
		pagination : true,
		fitColumns : true,  
		striped : true,  
		border : false, 
		showFooter: true,
		pageSize : 30,
		pageList : [ 20, 30, 50 ],
		columns : [[  
			{field: 'orderId', title: 'amazon-order-id'},
			{field: 'purchaseat', title: 'purchase-date',formatter:getTime},
			{field: 'updatedat', title: 'last-updated-date',formatter:getTime,sortable:true},
			{field: 'status', title: 'order-status'},
			{field: 'station', title: 'sales-channel'},
            {field: 'sku', title: 'sku'},
            {field: 'asin', title: 'asin'},
			{field: 'count', title: 'quantity'},
			{field: 'currencycode', title: 'currency'},
			{field: 'price', title: 'item-price',sortable:true}
			]],
			//取消单行选中效果
			onClickRow: function (rowIndex, rowData) {
				$(this).datagrid('unselectRow', rowIndex);
			}
	});
	
	original.datagrid('options').url = GLOBAL.domain + '/contrast/findAllOriginal';
})

