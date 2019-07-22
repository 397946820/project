var addPolicy = function () {
    $('#policyForm').form('clear');
    $('#policyDialog').window('open');
};
var addListPolicy = function () {
    $('#listPolicyForm').form('clear');
    $('#listPolicyDialog').window('open');
    var pt = $("#policyType").val();
	if (pt=='ReturnsAccepted') {
		$("#policyEditer").show();
	}else{
		$("#policyEditer").hide();
	}
}
var editPolicy = function () {
    var $row = $('#policyDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一行编辑");
        return;
    }
    $('#policyForm').form('clear');
    $('#policyForm').form('load', $row);
    $('#policyDialog').window('open');
    var pt = $row.policyType;
	if (pt=='ReturnsAccepted') {
		$("#policyEditer").show();
	}else{
		$("#policyEditer").hide();
	}
	
};

var policyFormSave = function () {
	if ($("#returnPolicyName").val()==null||$("#returnPolicyName").val()=='') {
		$.messager.alert("名称", "请输入名称");
		return;
	}
	if ($("#siteSelectes").val()==null||$("#siteSelectes").val()=='') {
		$.messager.alert("站点", "请选择站点");
		return;
	}
	if ($("#policyType").val()==null||$("#policyType").val()=='') {
		$.messager.alert("支付方式", "请选择支付方式");
		return;
	}
    $('#policyForm').form('submit', {
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (response) {
                $('#policyDialog').window('close');
                $('#policyDatagrid').datagrid('reload');
            
        }
    });
};


var removePolicy =function (){
    var rows = $('#policyDatagrid').datagrid('getSelections');
    var idarr = new Array();
    for (var i = 0; i < rows.length; i++) {
    	idarr.push(rows[i].returnPolicyId);
	}
    var ids = idarr.join(",");
    if (rows){
        $.messager.confirm('删除','你确定要删除数据?',function(r){
            if (r){
            	$.ajax({
					url: "/returnpolicy/remove",
					data: {ids:ids},
					type: "post",
					success: function(date) {
	                     $('#policyDatagrid').datagrid('reload'); 	
					},
					error: function(jqXHR, textStatus, errorThrown) {
						$.messager.alert("错误", "删除出错");	
					}
				});
            }
        });
    }else{
    	$.messager.alert("信息", "请选择删除行");
        return;
    }
}

var policyCotextMenu = function (e, rowIndex, rowData) {
    e.preventDefault();
    $("#policyDatagrid").datagrid('selectRow', rowIndex);
    $('#policyCotextMenu').menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};

var changePolicyType = function(){
	var pt = $("#policyType").val();
	
	if (pt=='ReturnsAccepted') {
		$("#policyEditer").show();
	}else{
		$("#policyEditer").hide();
	}
}

$(function () {
    $('#editePolicyContextMenu').on('click', editPolicy);
    $("#policyAddLinkbutton").on('click', addPolicy);
    $("#policyEditLinkbutton").on('click', editPolicy);
    $("#policyRemoveLinkbutton").on('click', removePolicy);
    $("#removePolicyContextMenu").on('click', removePolicy);
    $("#policyType").on('change', changePolicyType);
    
    $.ajax({
		url: "/site/list",
		data:{rows:500},
		type: "post",
		success: function(date) {
			var rows=JSON.parse(date).rows;
			$.each(rows, function(idx, obj) {
				$("#siteSelectes").append('<option value="'+obj.id+'">'+obj.name+'</option>');
			})
		/*	var list=[];
			$.each(rows, function(idx, obj) {
				list.push({"text": obj.name, "id": obj.id });
			})
			$("#siteSelectes").combobox("loadData", list);*/
		},
		error: function(jqXHR, textStatus, errorThrown) {
			$.messager.alert("错误", "查询站点列表出错");	
		}
	});
});