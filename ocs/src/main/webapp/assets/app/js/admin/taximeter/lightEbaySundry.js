var country = '';
var addOrEdit = '';
function editOpen(rowData) {
	$('#lightEbaySundryForm').form('clear');
	$('#lightEbaySundryForm').form('load', rowData);
	//下拉框不可选
	$('#country').combobox('disable');
	//隐藏不需要的文本框
	if(country == 'US') {
		$('#overWeight_').show();
		$('#overLength_').show();
		$('#overWidth_').show();
		$('#overweightFee_').show();
		$('#packingCharge_').hide();
		$('#handlingFee_').hide();
		$('#extraFee_').hide();
		$('#tpsDeliveryFee_').hide();
		$('#tpsProportion_').hide();
		$('#tpnDeliveryFee_').hide();
		$('#tpnProportion_').hide();
		$('#pfPrice_').hide();
	} else if(country == 'UK') {
		$('#packingCharge_').show();
		$('#handlingFee_').show();
		$('#extraFee_').show();
		$('#tpsDeliveryFee_').show();
		$('#tpsProportion_').show();
		$('#tpnDeliveryFee_').show();
		$('#tpnProportion_').show();
		$('#pfPrice_').show();
		$('#overWeight_').hide();
		$('#overLength_').hide();
		$('#overWidth_').hide();
		$('#overweightFee_').hide();
	} else if(country == 'DE') {
		$('#overWeight_').show();
		$('#overweightFee_').show(); 
		$('#overLength_').hide();
		$('#overWidth_').hide();
		$('#packingCharge_').hide();
		$('#handlingFee_').hide();
		$('#extraFee_').hide();
		$('#tpsDeliveryFee_').hide();
		$('#tpsProportion_').hide();
		$('#tpnDeliveryFee_').hide();
		$('#tpnProportion_').hide();
		$('#pfPrice_').hide();
	}
	$('#lightEbaySundryDialog').window('open');
	addOrEdit = 'edit';
}

var cancle = function() {
	$('#lightEbaySundryDialog').window('close');
}

var save = function() {
	if(!$("#lightEbaySundryForm").form('validate')) {
		return;
	}
	var jsonData = $("#lightEbaySundryForm").serializeArray();
	var data = [];
	jsonData.forEach(function(e){  
		data.push(e);
	});
	if(addOrEdit == 'edit') {
		data.push({name: "country", value: $('#country').combobox('getValue')});
	}
	var url = GLOBAL.domain + "/lightEbaySundry/saveEdit";
	$.ajax({
		url : url,
		type : 'POST',
		beforeSend: function () {
            $.messager.progress({
                title: '请稍后',
                msg: '正在操作...'
            });
        },
        complete: function () {
            $.messager.progress('close');
        },
		data : data,
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#lightEbaySundryDialog').window('close');
				$("#lightEbaySundryDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#lightEbaySundryDataGrid').datagrid('clearSelections');
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	});
}

$(function() {
	$("#lightEbaySundrySaveLinkbutton").on('click', save);
	$("#lightEbaySundryCancelLinkbutton").on('click', cancle);

	$('#lightEbaySundryDataGrid').datagrid({
		
		onLoadSuccess : function(data) {
			country = data.source;
			if(country == 'US') {
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'overWeight');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'overLength');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'overWidth');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'overweightFee');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'packingCharge');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'handlingFee');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'extraFee');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'tpsDeliveryFee');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'tpsProportion');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'tpnDeliveryFee');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'tpnProportion');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'pfPrice');
			} else if(country == 'UK') {
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'packingCharge');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'handlingFee');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'extraFee');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'tpsDeliveryFee');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'tpsProportion');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'tpnDeliveryFee');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'tpnProportion');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'pfPrice');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'overWeight');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'overLength');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'overWidth');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'overweightFee');
			} else if(country == 'DE') {
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'overWeight');
				$('#lightEbaySundryDataGrid').datagrid('showColumn', 'overweightFee');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'overLength');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'overWidth');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'packingCharge');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'handlingFee');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'extraFee');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'tpsDeliveryFee');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'tpsProportion');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'tpnDeliveryFee');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'tpnProportion');
				$('#lightEbaySundryDataGrid').datagrid('hideColumn', 'pfPrice');
			}
		}
	});
	
	//来源改变事件
	$("#country_").combobox({
		onChange: function (newValue,oldValue) {
			var rows = $('#lightEbaySundryDataGrid').datagrid('options');
			rows.sortName = '';
			rows.sortOrder = '';
			var param = {
					country : newValue
			};
			$('#lightEbaySundryDataGrid').datagrid('load',{
				param : param
			});
		}
	});
})