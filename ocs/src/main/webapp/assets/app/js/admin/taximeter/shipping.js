//打开添加的窗口
var add = function() {
	//处理表单
	resetTable("add");
	
	$('#shippingForm').form('clear');
	$('#countryId').combobox('enable');
	$("#countryId").combobox('setValue','US');
	$('#shippingDialog').window('open');
};

function resetTable(flag) {
	if(flag == 'add') {
		$("#af_ tr:gt(0)").remove();
		$("#sf_ tr:gt(0)").remove();
		$("#co_ tr:gt(0)").remove();
		$("#cost_ tr:gt(0)").remove();
		$("#loss_ tr:gt(0)").remove();
	} else if(flag == 'edit') {
		$("#af_").empty();
		$("#sf_").empty();
		$("#co_").empty();
		$("#cost_").empty();
	}
}

//打开修改窗口
function editOpen($row) {
	//处理表单
	resetTable("edit");
	$('#shippingForm').form('clear');
	//下拉框不可选
	$('#countryId').combobox('disable');
	//回显运输以及仓储成本费
	var af = $row.afShippingFee;
	var sf = $row.sfShippingFee;
	var co = $row.coShippingFee;
	var storageCost = $row.storageCost;
	var unexpectedLoss = $row.unexpectedLoss;
	af = eval(af);
	sf = eval(sf);
	co = eval(co);
	storageCost = eval(storageCost);
	echoShippingFee(af,"#af_");
	echoShippingFee(sf,"#sf_");
	echoShippingFee(co,"#co_");
	echoStorageCost(storageCost,"#cost_");
	
	if(unexpectedLoss != null && unexpectedLoss != '') {
		$("#loss_").empty();
		unexpectedLoss = eval(unexpectedLoss);
		echoUnexpectedLoss(unexpectedLoss,"#loss_");
	} else {
		$("#loss_ tr:gt(0)").remove();
	}
	
	$('#shippingDialog').window('open');
	//重新渲染
	$.parser.parse("#addTable");
	$('#shippingForm').form('load', $row);
}

function echoShippingFee(shippingFee,feeId) {
	for (var i = 0; i < shippingFee.length; i++) {
		$(feeId).append(addValueFee(shippingFee[i]));
	}
}

function echoStorageCost(storageCost,costId) {
	for (var i = 0; i < storageCost.length; i++) {
		$(costId).append(addValueStorageCost(storageCost[i]));
	}
}

function echoUnexpectedLoss(unexpectedLoss,lossId) {
	for (var i = 0; i < unexpectedLoss.length; i++) {
		$(lossId).append(addValueUnexpectedLoss(unexpectedLoss[i]));
	}
}

// 保存和修改
var save = function() {
	if(!$("#shippingForm").form('validate')) {
		return;
	}
	var entityId = $("#entityId").val();
	var countryId = $('#countryId').combobox('getValue');
	var afShippingFee = getShippingFee("#af_ tr");
	var sfShippingFee = getShippingFee("#sf_ tr");
	var coShippingFee = getShippingFee("#co_ tr");
	var storageCost = getStorageCost("#cost_ tr");
	var operatingFee = $('#operatingFee').textbox('getValue');
	var data = {
		entityId : entityId,
		countryId : countryId,
		afShippingFee : JSON.stringify(afShippingFee),
		sfShippingFee : JSON.stringify(sfShippingFee),
		coShippingFee : JSON.stringify(coShippingFee),
		storageCost : JSON.stringify(storageCost),
		operatingFee : operatingFee
	};
	var url = GLOBAL.domain + "/shipping/saveEdit";
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
				$('#shippingDialog').window('close');
				$("#shippingDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#shippingDataGrid').datagrid('clearSelections');
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	});
};

//拼凑三种运输方式的数据
function getShippingFee(flag){
	var result = [];
	$(flag).each(function(){
		var obj = { 
				cost: $(this).find("td").eq(0).find("input").val() ,
				currency: $(this).find("td").eq(1).find("input").val() ,
				cost_fluctuation: $(this).find("td").eq(2).find("input").val() ,
				profit_rate: $(this).find("td").eq(3).find("input").val() ,
				multi_profit_rate: $(this).find("td").eq(4).find("input").val()
		};
		result.push(obj);
	})
	return result;
}

// 拼凑仓储成本费
function getStorageCost(flag) {
	var result = [];
	$(flag).each(function(){
		var obj = { 
				month: $(this).find("td").eq(0).find("input").val() ,
				standard_size: $(this).find("td").eq(1).find("input").val() ,
				over_size: $(this).find("td").eq(2).find("input").val() ,
				param_one: $(this).find("td").eq(3).find("input").val()
		};
		result.push(obj);
	})
	return result;
}

//关闭窗口
var cancle = function() {
	$('#shippingDialog').window('close');
}

//打开导入窗口
var shippingUpload = function() {
	$('#uploadForm').form('clear');
	$('#uploadDialog').window('open');
}

$(function() {
	$("#shippingAddLinkbutton").on('click', add);
	$("#shippingSaveLinkbutton").on('click', save);
	$("#shippingCancelLinkbutton").on('click', cancle);
	$("#shippingExportLinkbutton").on('click',  {url : '/shipping/exportExcel',grid: '#shippingDataGrid'},derive);
	$("#shippingUploadLinkbutton").on('click', shippingUpload);
	$("#upload").on('click', {grid: '#shippingDataGrid', dialog: '#uploadDialog', url: '/shipping/uploadExcel'}, upload);
	
	$("#shippingDataGrid").datagrid({
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	});
	
	// 遮罩
	$('#shippingDialog').dialog({
		modal:true
	});
	
	//查询
	$("#query").click(function(){
		if(!$("#searchForm").form('validate')) {
			return;
		}
		var boolean = moreTime();
		if(boolean) {
			$.messager.alert("提示信息", "结束时间应大于开始时间");
			return;
		}
		var param = {
				countryId : $('#country_Id').combobox('getValue'),
				operatingFee : $('#operating_Fee').numberbox('getValue'),
				cstarttime : $('#cstarttime').datebox('getValue'),
				cendtime : $('#cendtime').datebox('getValue'),
				ustarttime : $('#ustarttime').datebox('getValue'),
				uendtime : $('#uendtime').datebox('getValue')
		}
		
		$('#shippingDataGrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#country_Id').combobox('setValue','');
		$('#operating_Fee').numberbox('setValue','');
		$('#cstarttime').datebox('setValue','');
		$('#cendtime').datebox('setValue','');
		$('#ustarttime').datebox('setValue','');
		$('#uendtime').datebox('setValue','');
	});
	
	//添加tr
	$("#addTable").on("click","#addTr",function(){
		if($(this).val() == '3') {
			$(this).parents("#subTable").find("tbody").append(addStorageCost());
		} else if($(this).val() == '4') {
			$(this).parents("#subTable").find("tbody").append(addUnexpectedLoss());
		} else {
			$(this).parents("#subTable").find("tbody").append(addFee());
		}
		var countryId = $('#countryId').combobox('getValue');
		$.parser.parse("#addTable");
		$('#countryId').combobox('setValue',countryId);
	});
	
	//删除tr
	$("#addTable").on("click","#delTr",function(){
		$(this).parent().parent("tr").remove();
	});
	
});

//添加运输信息
function addFee() {
	return '<tr>'+
				'<td><input style="width:120px;" type="text" name="cost"  class="easyui-numberbox" required="required" data-options="precision:4"></td>'+
				'<td><input style="width:120px;"  type="text" name="currency" class="easyui-textbox" required="required"></td>'+
				'<td><input style="width:120px;" type="text" name="cost_fluctuation"  class="easyui-numberbox" required="required" data-options="precision:4"></td>'+
				'<td><input style="width:120px;" type="text" name="profit_rate"  class="easyui-numberbox" required="required" data-options="precision:4"></td>'+
				'<td><input style="width:120px;" type="text" name="multi_profit_rate"  class="easyui-numberbox" required="required" data-options="precision:4"></td>'+
				'<td><a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a></td>'+
			'</tr>';
}


//添加回显运输信息
function addValueFee(shippingFee) {
	return '<tr>'+
				'<td><input style="width:120px;" type="text" name="cost" value="'+ shippingFee.cost +'" class="easyui-numberbox" required="required" data-options="precision:4"></td>'+
				'<td><input style="width:120px;" type="text" name="currency" value="'+ shippingFee.currency +'" class="easyui-textbox" required="required"></td>'+
				'<td><input style="width:120px;" type="text" name="cost_fluctuation" value="'+ shippingFee.cost_fluctuation +'"  class="easyui-numberbox" required="required" data-options="precision:4"></td>'+
				'<td><input style="width:120px;" type="text" name="profit_rate" value="'+ shippingFee.profit_rate +'" class="easyui-numberbox" required="required" data-options="precision:4"></td>'+
				'<td><input style="width:120px;" type="text" name="multi_profit_rate" value="'+ shippingFee.multi_profit_rate +'"  class="easyui-numberbox" required="required" data-options="precision:4"></td>'+
				'<td><a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a></td>'+
			'</tr>';
}

//添加额外损失费
function addUnexpectedLoss() {
	return '<tr>'+
				'<td><input type="text" name="amazon_category" class="easyui-textbox"></div></td>'+
				'<td><input type="text" name="referral_fee" class="easyui-textbox"></div></td>'+
				'<td><input type="text" name="minimum" class="easyui-textbox"></div></td>'+
				'<td><a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a></td>'+
			'</tr>';
}

//添加回显额外损失费
function addValueUnexpectedLoss(loss) {
	return '<tr>'+
				'<td><input style="width:120px;" type="text" name="amazon_category" value="'+ loss.amazon_category +'" class="easyui-textbox"></div></td>'+
				'<td><input  style="width:120px;"type="text" name="referral_fee" value="'+ loss.referral_fee +'" class="easyui-textbox"></div></td>'+
				'<td><input style="width:120px;" type="text" name="minimum" value="'+ loss.minimum +'" class="easyui-textbox"></div></td>'+
				'<td><a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a></td>'+
			'</tr>';
}

//添加仓储费
function addStorageCost() {
	return '<tr>'+
				'<td><input style="width:120px;" type="text" name="month" class="easyui-textbox" required="required"></div></td>'+
				'<td><input style="width:120px;" type="text" name="standard_size" class="easyui-numberbox" required="required" data-options="precision:4" ></td>'+
				'<td><input style="width:120px;" type="text" name="over_size" class="easyui-numberbox" required="required" data-options="precision:4" ></td>'+
				'<td><input style="width:120px;" type="text" name="param_one" class="easyui-numberbox" required="required" data-options="precision:4" ></td>'+
				'<td><a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a></td>'+
			'</tr>';
}

//添加回显仓储费
function addValueStorageCost(cost) {
	return '<tr>'+
				'<td><input style="width:120px;" type="text" name="month" value="'+ cost.month +'" class="easyui-textbox" required="required"></div></td>'+
				'<td><input style="width:120px;" type="text" name="standard_size" value="'+ cost.standard_size +'" class="easyui-numberbox" required="required" data-options="precision:4" ></td>'+
				'<td><input style="width:120px;" type="text" name="over_size" value="'+ cost.over_size +'" class="easyui-numberbox" required="required" data-options="precision:4" ></td>'+
				'<td><input style="width:120px;" type="text" name="param_one" value="'+ cost.param_one +'" class="easyui-numberbox" required="required" data-options="precision:4" ></td>'+
				'<td><a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a></td>'+
			'</tr>';
}

function changeCountry(data, row, index) {
	if(data == 'US') {
		return 'United States';
	} else if(data == 'GB') {
		return 'United Kingdom';
	} else if(data == 'DE') {
		return 'German';
	} else if(data == 'FR') {
		return 'France';
	} else if(data == 'IT') {
		return 'Italy';
	} else if(data == 'ES') {
		return 'Spain';
	} else if(data == 'JP') {
		return 'Japan';
	} else if(data == 'CA') {
		return 'Canada';
	} else if(data == 'AU') {
		return 'Australia';
	}
	return data;
}

function shippingFee(data, row, index) {
	var rows = JSON.parse(data);
	var result = '';
	for (var i = 0; i < rows.length; i++) {
		var obj = rows[i];
		result += ('Cost: ' + obj.cost + '<br/>Currency: ' + obj.currency + '<br/>Cost Fluctuation: ' + obj.cost_fluctuation + 
					'<br/>Profit Rate: ' + obj.profit_rate + '<br/>Multi Profit Rate: ' + obj.multi_profit_rate + '<br/>');
	}
	return result;
}

function storageCost(data, row, index) {
	var rows = JSON.parse(data);
	result = '';
	for (var i = 0; i < rows.length; i++) {
		var obj = rows[i];
		result += ('Month: ' + obj.month + '<br/>Standard Size: ' + obj.standard_size + '<br/>Over Size: ' + obj.over_size +
				'<br/>Param One: ' + obj.param_one + '<br/>');
	}
	return result;
}