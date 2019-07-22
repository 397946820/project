$.extend($.fn.validatebox.defaults.rules, {
	repassword: {
        validator: function (value, param) {
        	return $('#userpassword').val() == value;
        }, message: '两次密码输入不一致'
    },
    userNameExist:{
    	validator:function(value,param){
    		var isExit = false;
    		$.ajax({
    			url : GLOBAL.domain+'/user/exist',
    			dataType:'json',
    			type:'post',
    			data:{
    				userName:value,
    				id:$('#userId').val()
    			},
    			success:function(data) {
    				isExit = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return !isExit;
    	},
    	message: '用户名已经存在'
    },
    userCodeExist:{
    	validator:function(value,param) {
    		var isExit = false;
    		$.ajax({
    			url : GLOBAL.domain+'/user/exist',
    			dataType:'json',
    			type:'post',
    			data:{
    				userCode:value,
    				id:$('#userId').val()
    			},
    			success:function(data) {
    				isExit = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return !isExit;
    	},
    	message:'用户编号已经存在'
    }
});
//弹窗关闭
var userFormNo = function(){
	$("#userEditDialog").window("close");
};
var userFormEditNo = function(){
	$("#userDialog").window("close");
};
var castingRoleNo = function(){
	$("#userCastingRoleDialog").window("close")
}

var addUser = function () {
    $('#userForm').form('clear');
    $('#userDialog').window('open');
};
var addListUser = function () {
    $('#listUserForm').form('clear');
    $('#listUserDialog').window('open');
}
var editUser = function () {
    var $row = $('#userDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一行编辑");
        return;
    }
    $('#userFormEdit').form('clear');
    $('#userFormEdit').form('load', $row);
    $('#userEditDialog').window('open');
};

var castingRole = function() {
	var $row = $('#userDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一个用户进行角色分配");
        return;
    }
    $('#userCastingDatagrid').datagrid({
    	url:GLOBAL.domain + '/role/findByDepartmentId',
    	columns:[[
    		{field: 'id', title: 'id',checkbox:'true'},
            {field: 'name', title: '角色名称',width:'20%'},
            {field: 'departmentName', title: '部门名称',width:'20%'},
            {field: 'code', title: '角色编号',width:'20%'},
            {field: 'creationDate', title: '创建时间',width:'20%',formatter:getTime},
            {field: 'lastUpdationDate', title: '最后更新时间',width:'18%',formatter:getTime}
    		]],
    	queryParams:{
    		departmentId:$row.departmentId,
    		userId:$row.id
    	},
    	onLoadSuccess:function(data) {
    		var rowData = data.rows;
    		$.each(rowData,function(idx,val) {
    			if (val.checked) {
    				$('#userCastingDatagrid').datagrid("selectRow",idx);
    			}
    		});
    	}
    });
    $('#userCastingRoleDialog').window('open');
}

var castingRoleSave = function() {
	var roleId='';
	var selectRows = $('#userCastingDatagrid').datagrid('getChecked');
	if (!selectRows || $(selectRows).length==0) {
		$('#userCastingRoleDialog').window('close');
	} else {
		$(selectRows).each(function(index,obj){
			roleId += obj.id+',';
		});
		var $row = $('#userDatagrid').datagrid('getSelected');
		var requestdata = {
				id:$row.id,
				roleIds:roleId
		};
		$.ajax({
			url:GLOBAL.domain + '/user/castRole',
			type: 'post',
			dataType: 'json',
			contentType: "application/json",
			data:JSON.stringify(requestdata),
			success: function (response) {
				 $.messager.alert('消息提示', response.description, (response.errorCode == 0) ? 'info' : 'error');
				 $('#userCastingRoleDialog').window('close');
				 $('#userDatagrid').datagrid('reload');
	        },
	        error: function () {
	        	$.messager.alert('消息提示', '角色分配失败', 'error');
	        }
		});
	}
}
var userFormEdit = function () {
    $('#userFormEdit').form('submit', {
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (response) {
            var $data = JSON.parse(response);
            if ($data && $data.errorCode == 0) {
                $.messager.alert('消息提示', $data.description, 'info');
                $('#userEditDialog').window('close');
                $('#userDatagrid').datagrid('reload');
            }
        },
        error: function () {
            $.messager.alert('消息提示', '系统开小差，不给力', 'error');
        }
    });
};
var userFormSave = function () {
    $('#userForm').form('submit', {
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (response) {
            var $data = JSON.parse(response);
            if ($data && $data.errorCode == 0) {
                $.messager.alert('消息提示', $data.description, 'info');
                $('#userDialog').window('close');
                $('#userDatagrid').datagrid('reload');
            }
        },
        error: function () {
            $.messager.alert('消息提示', '系统开小差，不给力', 'error');
        }
    });
};
var userCotextMenu = function (e, rowIndex, rowData) {
    e.preventDefault();
    $("#userDatagrid").datagrid('selectRow', rowIndex);
    $('#userCotextMenu').menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};
var resetPwd = function(){
    var $row = $('#userDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一个用户进行密码重置");
        return;
    }
    $.messager.confirm('请先确认', '是否需要重置'+$row.userName+'的密码', function (r) {
        if (r) {
            $.ajax({
                url: GLOBAL.domain + '/user/resetPwd',
                type: 'post',
                dataType: 'json',
                data: 'id=' + $row.id,
                beforeSend: function () {
                    $.messager.progress({
                        title: '请稍后',
                        msg: '正在重置密码...'
                    });
                },
                complete: function () {
                    $.messager.progress('close');
                },
                success: function (data) {
                    if (data) {
                        $.messager.alert('消息提示', data.description, (data.errorCode == 0) ? 'info' : 'error');
                    }
                },
                error: function () {
                    $.messager.alert('消息提示', '系统开小差，不给力', 'error');
                }
            });
        }
    });
}

$(function () {
	$('#departCombobox').combobox({
		url:GLOBAL.domain+'/department/findAll',
		valueField:'id',
		textField:'name'
	});
	$('#departEditCombobox').combobox({
		url:GLOBAL.domain+'/department/findAll',
		valueField:'id',
		textField:'name'
	});
	if ($('#userResetPwdLinkbutton')) {
		$('#userResetPwdLinkbutton').on('click',resetPwd);
	}
	$('#castingUserContextMenu').on('click', castingRole);
	$('#userCastingLinkbutton').on('click', castingRole);
    $('#editeUserContextMenu').on('click', editUser);
    $("#userAddLinkbutton").on('click', addUser);
    $("#userEditLinkbutton").on('click', editUser);
    $("#userRemoveLinkbutton").on('click', {grid: '#userDatagrid', type: 'datagrid', url: '/user/remove'}, remove);
    $("#removeUserContextMenu").on('click', {grid: '#userDatagrid', type: 'datagrid', url: '/user/remove'}, remove);
});