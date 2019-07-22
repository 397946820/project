$.extend($.fn.validatebox.defaults.rules, {
    permissionNameExist:{
    	validator:function(value,param) {
    		var isExit = false;
    		$.ajax({
    			url : GLOBAL.domain+'/permission/exist',
    			dataType:'json',
    			type:'post',
    			data:{
    				name:value,
    				id:$('#permissionId').val(),
    				projectId:$('#projectCombobox').combobox('getValue'),
    				parentId:$('#parentId').val()
    			},
    			success:function(data) {
    				isExit = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return !isExit;
    	},
    	message: '权限名称已经存在'
    },
    permissionCodeExist:{
    	validator:function(value,param) {
    		var isExit = false;
    		$.ajax({
    			url : GLOBAL.domain+'/permission/exist',
    			dataType:'json',
    			type:'post',
    			data:{
    				code:value,
    				id:$('#permissionId').val()
    			},
    			success:function(data) {
    				isExit = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return !isExit;
    	},
    	message:'权限编号已经存在'
    }
});
var addPermission = function () {
    $('#permissionForm').form('clear');
    var $row = $('#permissionTreegrid').treegrid('getSelected');
    if ($row) {
    	$('#parentId').val($row.id);
    	$('#parentName').textbox("setValue",$row.name);
    	$('#projectCombobox').combobox('setValue',$row.projectId);
    }
    $('#permissionDialog').window('open');
};
var editPermission = function () {
    var $row = $('#permissionTreegrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一行编辑");
        return;
    }
    $('#permissionForm').form('clear');
    $('#permissionForm').form('load', $row);
    $('#permissionDialog').window('open');
};
var getPermissionType = function(value, row, index) {
	if (value=="MODULE") {
		return "模块";
	}
	if (value=="MENU") {
		return "菜单";
	}
	if (value=="FUNCTION") {
		return "功能";
	}
	if (value=="COLUMN") {
		return "列";
	}
	if (value=="DATA") {
		return "数据";
	}
	if (value="DATAITEM") {
		return "数据项";
	}
}
var permissionFormSave = function () {
    $('#permissionForm').form('submit', {
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (response) {
            var $data = JSON.parse(response);
            if ($data && $data.errorCode == 0) {
                $.messager.alert('消息提示', $data.description, 'info');
                $('#permissionDialog').window('close');
                $('#permissionTreegrid').treegrid('reload');
                $('#permissionTreegrid').treegrid('clearSelections');
            }
        }
    });
};
var permissionFormNo = function(){
	$("#permissionDialog").window("close")
}
var permissionCotextMenu = function (e, row) {
    e.preventDefault();
    $('#permissionTreegrid').treegrid("select",row.id);
    $('#permissionCotextMenu').menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};

$(function () {
	$('#projectCombobox').combobox({
		url:GLOBAL.domain+'/project/findAll',
		valueField:'id',
		textField:'name'
	});
	$('#permissionTypeCombobox').combobox({
		valueField:'label',
		textField:'value',
		data:[{
			label:'FUNCTION',
			value:'功能'
		},{
			label:'MENU',
			value:'菜单'
		},{
			label:'MODULE',
			value:'模块'
		},{
			label:'COLUMN',
			value:'列'
		},{
			label:'DATA',
			value:'数据'
		},{
			label:'DATAITEM',
			value:'数据项'
		}],
		onChange:function(newValue,oldValue) {
			if (newValue=='MENU') {
				$('#menu_url').show();
			} else {
				$('#menu_url').hide();
			}
		}
	});
	$('#menu_url').hide();
	$("#addPermissionContextMenu").on('click', addPermission);
	$("#editePermissionContextMenu").on('click', editPermission);
	$("#removePermissionContextMenu").on('click', {grid: '#permissionTreegrid', type: 'treegrid', url: '/permission/remove'}, remove);
    $("#permissionAddLinkbutton").on('click', addPermission);
    $("#permissionEditLinkbutton").on('click', editPermission);
    $("#permissionRemoveLinkbutton").on('click', {grid: '#permissionTreegrid', type: 'treegrid', url: '/permission/remove'}, remove);
});