$.extend($.fn.validatebox.defaults.rules, {
    projectNameExist:{
    	validator:function(value,param) {
    		var isExit = false;
    		$.ajax({
    			url : GLOBAL.domain+'/project/exist',
    			dataType:'json',
    			type:'post',
    			data:{
    				name:value,
    				id:$('#projectId').val()
    			},
    			success:function(data) {
    				isExit = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return !isExit;
    	},
    	message: '项目名称已经存在'
    },
    projectCodeExist:{
    	validator:function(value,param) {
    		var isExit = false;
    		$.ajax({
    			url : GLOBAL.domain+'/project/exist',
    			dataType:'json',
    			type:'post',
    			data:{
    				code:value,
    				id:$('#projectId').val()
    			},
    			success:function(data) {
    				isExit = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return !isExit;
    	},
    	message:'项目编号已经存在'
    }
});
var addProject = function () {
    $('#projectForm').form('clear');
    $('#projectDialog').window('open');
};
var addListProject = function () {
    $('#listProjectForm').form('clear');
    $('#listProjectDialog').window('open');
}
var editProject = function () {
    var $row = $('#projectDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一行编辑");
        return;
    }
    $('#projectForm').form('clear');
    $('#projectForm').form('load', $row);
    $('#projectDialog').window('open');
};

var projectFormSave = function () {
    $('#projectForm').form('submit', {
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (response) {
            var $data = JSON.parse(response);
            if ($data && $data.errorCode == 0) {
                $.messager.alert('消息提示', $data.description, 'info');
                $('#projectDialog').window('close');
                $('#projectDatagrid').datagrid('reload');
            }
        },
        error: function () {
            $.messager.alert('消息提示', '系统开小差，不给力', 'error');
        }
    });
};
//var projectFormNo = function(){
//	debugger
//	$("#projectDialog").window("close");
//};
var roleFormNo = function() {
	debugger
	$('#projectDialog').window('close');
};
var projectCotextMenu = function (e, rowIndex, rowData) {
    e.preventDefault();
    $("#projectDatagrid").datagrid('selectRow', rowIndex);
    $('#projectCotextMenu').menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};

$(function () {
    $('#editeProjectContextMenu').on('click', editProject);
    $("#projectAddLinkbutton").on('click', addProject);
    $("#projectEditLinkbutton").on('click', editProject);
    $("#projectRemoveLinkbutton").on('click', {grid: '#projectDatagrid', type: 'datagrid', url: '/project/remove'}, remove);
    $("#removeProjectContextMenu").on('click', {grid: '#projectDatagrid', type: 'datagrid', url: '/project/remove'}, remove);
});