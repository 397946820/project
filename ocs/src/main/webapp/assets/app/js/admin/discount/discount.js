//打开添加的窗口
var add = function() {
	$('#discountForm').form('clear');
	$("#ebayAccount").val("le.deutschland");
	$('#discountDialog').window('open');
};

// 打开修改的窗口
var edit = function() {
	var rows = $("#discountDataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示信息", "请选中一行编辑");
		return;
	} else if (rows.length > 1) {
		$.messager.alert("提示信息", "只能选择一行进行编辑");
		return;
	}
	var $row = $('#discountDataGrid').datagrid('getSelected');
	$('#discountForm').form('clear');
	$('#discountForm').form('load', $row);
	var time = $row.validity.split(",");
	$("#start").datetimebox("setValue", time[0]);
	$("#end").datetimebox("setValue", time[1]);
	//折扣详情
	var json = JSON.parse($row.discountDetail);
	if(json.priceDiscount != null && json.priceDiscount != '') {
		$("input[type=checkbox][name=priceDiscount]").prop("checked", true);
	}
	if(json.freeShipping != null  && json.freeShipping != '') {
		$("input[type=checkbox][name=freeShipping]").prop("checked", true);
	}
	if(json.rdobtnPercent != null  && json.rdobtnPercent != '') {
		$("#rdobtnPercent").attr("checked", "checked");
	}
	if(json.rdobtnValue != null  && json.rdobtnValue != '') {
		$("#rdobtnValue").attr("checked", "checked");
	}
	if(json.percent != null  && json.percent != '') {
		$("#percent").val(json.percent);
	} 
	if (json.txt != null  && json.txt != '') {
		$("#txt").val(json.txt);
	}
	$('#discountDialog').window('open');
};

// 保存和修改
var discountFormSave = function() {
	if(!$("#discountForm").form('validate')) {
		return;
	}
	var discountDetail = getDiscountDetail();
	var id = $("#hid").val();
	var name = $("#name").val();
	var ebayAccount = $("#ebayAccount").val();
	var start = $("#start").val();
	var end = $("#end").val();
	var data = {id : id, name : name,ebayAccount : ebayAccount,start : start,end : end,discountDetail :discountDetail};
	var url = GLOBAL.domain + "/discount/saveEdit";
	$.ajax({
		url : url,
		type : 'POST',
		data : data,
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#discountDialog').window('close');
				$("#discountDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#discountDataGrid').datagrid('clearSelections');
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	})
};

// 折扣明细
function getDiscountDetail() {
	var priceDiscount = '';
	var rdobtnPercent = '';
	var rdobtnValue = '';
	var freeShipping = '';
	if ($('#priceDiscount').attr('checked')) {
		priceDiscount = $('#priceDiscount').val();
	}

	if ($('#rdobtnPercent').attr('checked')) {
		rdobtnPercent = $('#rdobtnPercent').val();
	}

	if ($('#rdobtnValue').attr('checked')) {
		rdobtnValue = $('#rdobtnValue').val();
	}

	if ($('#freeShipping').attr('checked')) {
		freeShipping = $('#freeShipping').val();
	}

	var percent = $("#percent").val();
	var txt = $("#txt").val();
	var discountDetail = '{"priceDiscount":"' + priceDiscount +'","rdobtnPercent":"' + rdobtnPercent +'","rdobtnValue":"'+ rdobtnValue +
							'","freeShipping":"' + freeShipping +'","percent":"' + percent +'","txt":"' + txt + '"}';

	return discountDetail;
}

// 同步到ebay
var discountFormReload = function() {

}

var discountFormNo = function() {
	$('#discountDialog').window('close');
}


var discountCotextMenu = function(e, rowIndex, rowData) {
	e.preventDefault();
	$("#discountDataGrid").datagrid('selectRow', rowIndex);
	$('#discountCotextMenu').menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};

$(function() {
	$('#discountEditContextMenu').on('click', edit);
	$("#discountAddLinkbutton").on('click', add);
	$("#discountEditLinkbutton").on('click', edit);
	$("#discountRemoveLinkbutton").on('click', {grid: '#discountDataGrid', type: 'datagrid', url: '/discount/remove'}, removeAll);
	$("#discountRemoveContextMenu").on('click',{grid: '#discountDataGrid', type: 'datagrid', url: '/discount/remove'}, removeAll);
	$("#rdobtnPercent").click(function() {
		$("#rdobtnValue").attr("checked", false);
		$("#rdobtnPercent").attr("checked", "checked");
		if ($("input[name='txt']").val() != null) {
			$("input[name='txt']").val("");
		}
	})

	$("#rdobtnValue").click(function() {
		$("#rdobtnPercent").attr("checked", false);
		$("#rdobtnValue").attr("checked", "checked");
		if ($("input[name='percent']").val() != null) {
			$("input[name='percent']").val("");
		}
	})
});

function formatterTime(data, row, index) {
	var time = data.split(",");
	return time[0] + " -<br/>" + time[1];
}

function formatterPercent(data,row,index) {
	if(data != null) {
		var json = JSON.parse(data);
		if(json.percent != null) {
			return json.percent + "%";
		} else if (json.txt != null) {
			return json.txt;
		}
	}
	return null;
}
