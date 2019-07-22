function reset_(value) {
	if(value == 'reckon') {
		$("#reckonCount").hide();
		$("#reckon").show();
	} else if(value == 'reckonCount') {
		$("#reckonCount").show();
		$("#reckon").hide();
	}
	$("#reckon_").hide();
	$("#messages_").hide();
	$("#messages").panel('close');
	$("#showMessages_").html('');
}
var flag = '';
var pricePlanReckon = function() {
	flag = 'reckon';
	reset_("reckon");
	$('#reckonForm').form('clear');
	$("#reckonPrice").show();
	$("#reckonRate").show();
	$('#reckonDialog').window('open');
}

var pricePlanLinkVariant = function() {
	flag = 'reckon';
	reset_("reckon");
	$('#reckonForm').form('clear');
	$("#reckonPrice").show();
	$("#reckonRate").hide();
	$('#reckonDialog').window('open');
}

var pricePlanCost = function() {
	flag = 'reckonCount';
	reset_("reckonCount");
	$('#reckonForm').form('clear');
	$('#reckonDialog').window('open');
}

var reckonPrice = function() {
	reset_("reckon");
	reckonUpload('price');
}

var reckonRate = function() {
	reset_("reckon");
	reckonUpload('rate');
}

var reckonCount = function() {
	reset_("reckonCount");
	reckonUpload('');
}

function reckonUpload(string) {
	var formData = new FormData();
	var myfile = document.getElementById("file").files[0];
	if(myfile == '' || myfile == null) {
		$.messager.alert("提示信息", "请选择导入的文件");
		 return;
	}
    formData.append("myfile", myfile);
    var url = '';
    if(string == '') {
    	url = GLOBAL.domain + '/pricePlan/uploadGetCount?status='+status;
    } else {
    	url = GLOBAL.domain + '/pricePlan/uploadExcel?string='+string+'&status='+status;
    }
	$.ajax({
	      type: "POST",
	      url: url,
	      enctype: 'multipart/form-data',
	      data:formData,
	      contentType: false,
	      processData: false,
	      beforeSend: function () {
	        $.messager.progress({
                title: '请稍后',
                msg: '正在计算...'
	        });
	      },
	      complete: function () {
	        $.messager.progress('close');
	      },
	      success: function (response) {
	    	  var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					if($data.data == '你没有权限,请联系管理员') {
						$("#messages_").html("失败!")
						$("#showMessages_").html($data.data);
						$("#messages").panel('open');
						$("#messages_").show();
					} else {
						var json = $data.data;
						if(json.length > 1) {
							var flag = '';
							for (var i = 0; i < json.length; i++) {
								flag += ('<li>' + json[i] + '</li>');
							}
							$("#showMessages_").html(flag);
							$("#messages").panel('open');
							$("#messages_").show();
						}
						$("#reckon_").show();
					}
				} 
	      },
	      error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '导入失败', 'warning');
	      }
	 });
}

// 导出
var reckonExport = function () {
    
    $.messager.confirm('请先确认', '确定要导出吗？', function (r) {
        if (r) {
        	$("#reckon_").hide();
        	window.location.href = GLOBAL.domain + '/pricePlan/reckonExport?string='+flag+"&status="+status;
        }
    });
   
};

var pricePlanTest = function() {
	$('#pricePlanTestShow').html('');
	$('#pricePlanTestForm').form('clear');
	var countrys =$('#countryId').combobox('getData'); 
	if(countrys.length > 0) {
		for (var i = 0; i < countrys.length; i++) {
			var country = countrys[i];
			if(country.value == '') {
				countrys.elremove(i);
			}
		}
		$('#countryId_').combobox('loadData',countrys);
	}
	$('#pricePlanTest').window('open');
}

var beforeProfitRateAction = null;
var beforeFinalPrice = null;
var permission = null;
$(function() {
	$("#pricePlanRecoverLinkbutton").on('click', {url : '/pricePlan/recover',grid: '#pricePlanDataGrid',status:status},recover);
	$("#pricePlanRefreshLinkbutton").on('click', {url : '/pricePlan/refresh',grid: '#pricePlanDataGrid',status:status},refresh);
	$("#pricePlanExportLinkbutton").on('click', {url : '/pricePlan/exportExcel',grid: '#pricePlanDataGrid'},derive);
	$("#pricePlanReckonLinkbutton").on('click', pricePlanReckon);
	$("#pricePlanLinkVariantbutton").on('click', pricePlanLinkVariant);
	$("#pricePlanCostLinkbutton").on('click', pricePlanCost);
	$("#reckonPrice_").on('click', reckonPrice);
	$("#reckonRate_").on('click', reckonRate);
	$("#reckonCount_").on('click', reckonCount);
	$("#reckonExportLinkbutton").on('click', reckonExport);
	$("#pricePlanTestLinkbutton").on('click', pricePlanTest);
	
	Array.prototype.elremove = function(m){  
　　     	if(isNaN(m)||m>this.length){return false;}  
　　      	this.splice(m,1);  
　   	}
	
	$('#sku_').combobox({
        url: GLOBAL.domain + '/pricePlan/findAllSku?status='+status,
        valueField: 'name',
        textField: 'value'
	})
	
	// 测试查询
	$('#pricePlanTestQuery').click(function() {
		if(!$("#pricePlanTestForm").form('validate')) {
			return false;
		}
		var data = {
			countryId : $('#countryId_').combobox('getValue'),
			sku : $('#sku_').textbox('getValue')
		};
		$.ajax({
			type : "POST",
			url : GLOBAL.domain + '/pricePlan/lePricePlanTest',
			data : data,
			success : function(response) {
				$('#pricePlanTestShow').html(response);
			}
		});
	})
	
	// 测试重置
	$('#pricePlanTestReset').click(function() {
		$('#pricePlanTestShow').html('');
		$('#country_').combobox('setValue', '');
		$('#sku_').textbox('setValue', '');
	})
	
	if(permission == null) {
		$.ajax({
			url : GLOBAL.domain + "/pricePlan/getPermission",
			type : 'POST',
			success : function(data) {
				permission = data;
				setCountryId(permission);
			}
		})
	}
	
	$('#pricePlanDataGrid').datagrid({
		url : GLOBAL.domain + '/pricePlan/findAll/'+status,
		
		onBeforeEdit:function(index,row){  
			beforeProfitRateAction = row.profitRateAction;
			beforeFinalPrice = row.finalPriceStr;
			row.editing = true; 
		},  
		
		onAfterEdit:function(index,row){  
			row.editing = false; 
			if(beforeProfitRateAction != row.profitRateAction || beforeFinalPrice != row.finalPriceStr) {
				var entityId = row.entityId;
				var profitRateAction = row.profitRateAction;
				var finalPrice = 0;
				if (!isNaN(row.finalPriceStr)) {
					finalPrice = row.finalPriceStr;
				} else {
					finalPrice = row.finalPriceStr.substring(1);
				}
				 
				var url = GLOBAL.domain + "/pricePlan/editPricePlan";
				$.ajax({
					url : url,
					type : 'POST',
					data : {entityId:entityId, profitRateAction:profitRateAction, finalPrice:finalPrice},
					success : function(data) {
						var rows = JSON.parse(data);
						row.profitRateAction = rows.profitRateAction;
						//row.finalPrice = rows.finalPrice;
						row.finalPriceStr=rows.currencySymbol+rows.finalPrice;
						row.updatedAt = rows.updatedAt;
						$('#pricePlanDataGrid').datagrid('refreshRow', index); 
					}
				});
			}
		},  
		onCancelEdit:function(index,row){  
			row.editing = false;  
			$('#pricePlanDataGrid').datagrid('refreshRow', index);  
		},
		onClickCell : function(index,field,value){
			$('#pricePlanDataGrid').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});;
		}
	});
	
	// 查询
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
				countryId : $('#countryId').combobox('getValue'),
				shippingType : $('#shippingType').combobox('getValue'),
				sku : $('#sku').textbox('getValue'),
				isOversize : $('#isOversize').combobox('getValue'),
				cif : $('#cif').textbox('getValue'),
				fbaFee : $('#fbaFee').numberbox('getValue'),
				amzFba : $('#amzFba').numberbox('getValue'),
				storageFee : $('#storageFee').numberbox('getValue'),
				finalCost : $('#finalCost').numberbox('getValue'),
				referralRate : $('#referralRate').numberbox('getValue'),
				unfulliableRate : $('#unfulliableRate').numberbox('getValue'),
				replacementRate : $('#replacementRate').numberbox('getValue'),
				profitRate : $('#profitRate').numberbox('getValue'),
				profitRateAction : $('#profitRateAction').numberbox('getValue'),
				vat : $('#vat').numberbox('getValue'),
				finalPrice : $('#finalPrice').numberbox('getValue'),
				cstarttime : $('#cstarttime').datebox('getValue'),
				cendtime : $('#cendtime').datebox('getValue'),
				ustarttime : $('#ustarttime').datebox('getValue'),
				uendtime : $('#uendtime').datebox('getValue'),
				productType :$('#productType').textbox('getValue'),
				discontinue:$('#discontinue').combobox('getValue')
		}
		
		$('#pricePlanDataGrid').datagrid('load',{
			param : param
		});
	});
	
	// 重置
	$("#reset").click(function(){
		$('#countryId').combobox('setValue','');
		$('#shippingType').combobox('setValue','');
		$('#sku').textbox('setValue','');
		$('#isOversize').combobox('setValue','');
		$('#cif').textbox('setValue','');
		$('#fbaFee').numberbox('setValue','');
		$('#amzFba').numberbox('setValue','');
		$('#storageFee').numberbox('setValue','');
		$('#finalCost').numberbox('setValue','');
		$('#referralRate').numberbox('setValue','');
		$('#unfulliableRate').numberbox('setValue','');
		$('#replacementRate').numberbox('setValue','');
		$('#profitRate').numberbox('setValue','');
		$('#profitRateAction').numberbox('setValue','');
		$('#vat').numberbox('setValue','');
		$('#finalPrice').numberbox('setValue','');
		$('#cstarttime').datebox('setValue','');
		$('#cendtime').datebox('setValue','');
		$('#ustarttime').datebox('setValue','');
		$('#uendtime').datebox('setValue','');
		$('#productType').textbox('setValue','');
		$('#discontinue').combobox('setValue','');
	});
	
});

function setCountryId(data) {
	if(data != '') {
		var rows = JSON.parse(data);
		var children = rows.children;
		if(children != null) {
			var countryId_ = [{text:'-- 请选择 --', value:''}];
			for (var i = 0; i < children.length; i++) {
				var row = children[i];
				var countryId = {
						text : row.name, 
						value: getName(row.name)
				};
				countryId_.push(countryId);
			}
			$('#countryId').combobox('loadData',countryId_);
		}
	} else {
		$.ajax({
			url : GLOBAL.domain + "/pricePlan/isCPB",
			type : 'POST',
			success : function(flag) {
				if(flag == 'true') {
					$('#countryId').combobox('loadData',[{text:'-- 请选择 --', value:''},{text:'United States', value:'US'},
						{text:'United Kingdom', value:'GB'},{text:'German', value:'DE'},
						{text:'France', value:'FR'},{text:'Italy', value:'IT'},
						{text:'Spain', value:'ES'},{text:'Japan', value:'JP'},{text:'Canada', value:'CA'},{text:'Australia', value:'AU'}]);
				}else {
					$('#countryId').combobox('loadData',[{text:'-- 请选择 --', value:''}]);
				}
			}
		})
	}
}

function getName(name) {
	return map[name];
}

function getIsOversize(value, row, index) {
	if (value == '0'){
		return '否';
	} else {
		return '是';
	}
}
