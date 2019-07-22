var addSite = function () {
    $('#siteForm').form('clear');
    $('#siteDialog').window('open');
};
var addListSite = function () {
    $('#listSiteForm').form('clear');
    $('#listSiteDialog').window('open');
}
var editSite = function () {
    var $row = $('#siteDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一行编辑");
        return;
    }
    $('#siteForm').form('clear');
    $('#siteForm').form('load', $row);
    $('#siteDialog').window('open');
};

var siteFormSave = function () {
    $('#siteForm').form('submit', {
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (response) {
            var $data = JSON.parse(response);
            if ($data && $data.errorCode == 0) {
                $.messager.alert('消息提示', $data.description, 'info');
                $('#siteDialog').window('close');
                $('#siteDatagrid').datagrid('reload');
            }
        }
    });
};
var siteCotextMenu = function (e, rowIndex, rowData) {
    e.preventDefault();
    $("#siteDatagrid").datagrid('selectRow', rowIndex);
    $('#siteCotextMenu').menu('show', {
        left: e.pageX,
        top: e.pageY
    });
};

$(function () {
    $('#editeSiteContextMenu').on('click', editSite);
    $("#siteAddLinkbutton").on('click', addSite);
    $("#siteEditLinkbutton").on('click', editSite);
    $("#siteRemoveLinkbutton").on('click', {grid: '#siteDatagrid', type: 'datagrid', url: '/site/remove'}, remove);
    $("#removeSiteContextMenu").on('click', {grid: '#siteDatagrid', type: 'datagrid', url: '/site/remove'}, remove);
});