$.extend($.fn.validatebox.defaults.rules, {
    roleNameExist:{
    	validator:function(value,param) {
    		var isExit = false;
    		$.ajax({
    			url : GLOBAL.domain+'/role/exist',
    			dataType:'json',
    			type:'post',
    			data:{
    				name:value,
    				departmentId:$('#departCombobox').combobox('getValue'),
    				id:$('#roleId').val()
    			},
    			success:function(data) {
    				isExit = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return !isExit;
    	},
    	message: '角色名已经存在'
    },
    roleCodeExist:{
    	validator:function(value,param) {
    		var isExit = false;
    		$.ajax({
    			url : GLOBAL.domain+'/role/exist',
    			dataType:'json',
    			type:'post',
    			data:{
    				code:value,
    				id:$('#roleId').val()
    			},
    			success:function(data) {
    				isExit = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return !isExit;
    	},
    	message:'角色编号已经存在'
    }
});
var addRole = function () {
    $('#roleForm').form('clear');
    $('#roleDialog').window('open');
};
var addListRole = function () {
    $('#listRoleForm').form('clear');
    $('#listRoleDialog').window('open');
}
var editRole = function () {
    var $row = $('#roleDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一行编辑");
        return;
    }
    $('#roleForm').form('clear');
    $('#roleForm').form('load', $row);
    $('#roleDialog').window('open');
};

var authorizeRole = function(){
	var $row = $('#roleDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一个要授权的角色");
        return;
    }
    $('#authorizeTree').tree({
    	checkbox:true,
    	url:GLOBAL.domain+'/permission/queryAuthorize',
		queryParams:{
			roleId:$row.id
		},
		formatter:function(node){
			return node.name;
		}
	});
    $('#authorizeDialog').window('open');
}
var authorizeSave = function() {
	var $row = $('#roleDatagrid').datagrid('getSelected');
	var nodes = $('#authorizeTree').tree('getChecked');
	if (null != nodes && $(nodes).length == 0) {
		$('#authorizeDialog').window('close');
	} else {
		var permissionIds = '';
		$(nodes).each(function(index,obj){
			if (typeof(obj.id)!='undefined' && typeof(obj.attributes.groupId) == 'undefined') {
				permissionIds+=obj.id+',';
			}
		});
		var requestdata = {
				id:$row.id,
				permissionIds:permissionIds
		};
		$.ajax({
			url:GLOBAL.domain + '/role/authorize',
			type: 'post',
			dataType: 'json',
			contentType: "application/json",
			data:JSON.stringify(requestdata),
			success: function (response) {
				 $.messager.alert('消息提示', response.description, (response.errorCode == 0) ? 'info' : 'error');
				 $('#authorizeDialog').window('close');
	        },
	        error: function () {
	        	$.messager.alert('消息提示', '授权失败', 'error');
	        }
		});
	}
}
var roleFormSave = function () {
    $('#roleForm').form('submit', {
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (response) {
            var $data = JSON.parse(response);
            if ($data && $data.errorCode == 0) {
                $.messager.alert('消息提示', $data.description, 'info');
                $('#roleDialog').window('close');
                $('#roleDatagrid').datagrid('reload');
            }
        },
        error: function () {
            $.messager.alert('消息提示', '系统开小差，不给力', 'error');
        }
    });
};
var roleFormNo = function() {
	$('#roleDialog').window('close');
}
var authorizeNo = function() {
	$('#authorizeDialog').window('close');
}
var roleCotextMenu = function (e, rowIndex, rowData) {
    e.preventDefault();
    $("#roleDatagrid").datagrid('selectRow', rowIndex);
    $('#roleCotextMenu').menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};

$(function () {
	$('#departCombobox').combobox({
		url:GLOBAL.domain+'/department/findAll',
		valueField:'id',
		textField:'name'
	});
	$('#roleCastPermissionLinkbutton').on('click',authorizeRole);
    $('#editeRoleContextMenu').on('click', editRole);
    $("#roleAddLinkbutton").on('click', addRole);
    $("#roleEditLinkbutton").on('click', editRole);
    $("#roleRemoveLinkbutton").on('click', {grid: '#roleDatagrid', type: 'datagrid', url: '/role/remove'}, remove);
    $("#removeRoleContextMenu").on('click', {grid: '#roleDatagrid', type: 'datagrid', url: '/role/remove'}, remove);
});