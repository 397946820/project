var addPic = function () {
	
    $('#picForm').form('clear');
    $("#Imgdiv").find("img").removeAttr("src");
    $('#picDialog').window('open');
};
var addListPic = function () {
    $('#listPicForm').form('clear');
    $('#listPicDialog').window('open');
}
var editPic = function () {
    var $row = $('#picDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一行编辑");
        return;
    }
    $('#picForm').form('clear');
    $('#picForm').form('load', $row);
    $('#categoryId').combotree('setValue',{
    	id:$row.categoryId,
    	text:$row.categoryName
    });
    $('#Img').attr('src','data:image/'+$row.imgType+';base64,'+$row.url);
    $('#picDialog').window('open');
};

var picFormSave = function () {
    $('#picForm').form('submit', {
        onSubmit: function () {
        	$.messager.progress({
                title: '请稍后',
                msg: '正在玩命加载中...'
            });
            return $(this).form('validate');
        },
        success: function (response) {
        	
            var $data = JSON.parse(response);
            if ($data && $data.errorCode == 0) {
                $.messager.alert('消息提示', $data.description, 'info');
                $('#picDialog').window('close');
                $('#paramCategory').combotree('clear');
                $('#picDatagrid').datagrid('reload');
                refreshPic();
                $.messager.progress('close');
            }
        }
    });
};
var picFormNo = function(){
	$('#picForm').form('clear')
	$('#picDialog').window("close");
}
function change_photo(){
	
    PreviewImage($("input[name='file']")[0], 'Img', 'Imgdiv');
}
var picCotextMenu = function (e, rowIndex, rowData) {
    e.preventDefault();
    $("#picDatagrid").datagrid('selectRow', rowIndex);
    $('#picCotextMenu').menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};
var getPicServlet = function (value,row,index) {
	return "<img width=60 height=60 src='data:image/"+row.imgType+";base64,"+row.url+"'/>";
}
$(function () {
	$('#categoryId').combotree({
		url:GLOBAL.domain+'/picCategory/combotree'
	});
	$('#paramCategory').combotree({
		url:GLOBAL.domain+'/picCategory/conditionCombotree',
		onChange:function (newValue,oldValue) {
			$('#picDatagrid').datagrid({
				queryParams:{
					param:{
						categoryId:newValue
					}
				}
			});
		}
	});
    $('#editePicContextMenu').on('click', editPic);
    $("#picAddLinkbutton").on('click', addPic);
    $("#picEditLinkbutton").on('click', editPic);
    $("#picRemoveLinkbutton").on('click', {grid: '#picDatagrid', type: 'datagrid', url: '/pic/remove'}, remove);
    $("#removePicContextMenu").on('click', {grid: '#picDatagrid', type: 'datagrid', url: '/pic/remove'}, remove);
});