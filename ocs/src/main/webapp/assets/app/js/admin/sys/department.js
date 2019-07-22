$.extend($.fn.validatebox.defaults.rules, {
    projectNameExist:{
    	validator:function(value,param) {
    		var isExit = false;
    		$.ajax({
    			url : GLOBAL.domain+'/department/exist',
    			dataType:'json',
    			type:'post',
    			data:{
    				name:value,
    				id:$('#departmentId').val()
    			},
    			success:function(data) {
    				isExit = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return !isExit;
    	},
    	message: '部门名称已经存在'
    },
    projectCodeExist:{
    	validator:function(value,param) {
    		var isExit = false;
    		$.ajax({
    			url : GLOBAL.domain+'/department/exist',
    			dataType:'json',
    			type:'post',
    			data:{
    				code:value,
    				id:$('#departmentId').val()
    			},
    			success:function(data) {
    				isExit = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return !isExit;
    	},
    	message:'部门编号已经存在'
    }
});
var depertmentFomNo = function(){
	$("#departmentDialog").window('close');
};
var addDepartment = function () {
    $('#departmentForm').form('clear');
    $('#departmentDialog').window('open');
};
var addListDepartment = function () {
    $('#listDepartmentForm').form('clear');
    $('#listDepartmentDialog').window('open');
}
var editDepartment = function () {
    var $row = $('#departmentDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一行编辑");
        return;
    }
    $('#departmentForm').form('clear');
    $('#departmentForm').form('load', $row);
    $('#departmentDialog').window('open');
};

var departmentFormSave = function () {
    $('#departmentForm').form('submit', {
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (response) {
            var $data = JSON.parse(response);
            if ($data && $data.errorCode == 0) {
                $.messager.alert('消息提示', $data.description, 'info');
                $('#departmentDialog').window('close');
                $('#departmentDatagrid').datagrid('reload');
            }
        },
        error: function () {
            $.messager.alert('消息提示', '系统开小差，不给力', 'error');
        }
    });
};

var departmentCotextMenu = function (e, rowIndex, rowData) {
    e.preventDefault();
    $("#departmentDatagrid").datagrid('selectRow', rowIndex);
    $('#departmentCotextMenu').menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};

$(function () {
    $('#editeDepartmentContextMenu').on('click', editDepartment);
    $("#departmentAddLinkbutton").on('click', addDepartment);
    $("#departmentEditLinkbutton").on('click', editDepartment);
    $("#departmentRemoveLinkbutton").on('click', {grid: '#departmentDatagrid', type: 'datagrid', url: '/department/remove'}, remove);
    $("#removeDepartmentContextMenu").on('click', {grid: '#departmentDatagrid', type: 'datagrid', url: '/department/remove'}, remove);
});