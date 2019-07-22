<@FTL.admin id="roleList" title="角色列表" add_script_files=['admin/sys/role.js']>
<div data-options="region:'center',border:false">
    <table id="roleDatagrid" class="easyui-datagrid"
           data-options="
           url:'${FTL.X.global_domain}/role/list',
           fitColumns:true,
           columns: [
            [
                {field: 'id', title: 'id',hidden:true},
                {field: 'name', title: '角色名称',width:'20%'},
                {field: 'departmentName', title: '部门名称',width:'20%'},
                {field: 'code', title: '角色编号',width:'20%'},
                {field: 'creationDate', title: '创建时间',width:'20%',formatter:getTime},
                {field: 'lastUpdationDate', title: '最后更新时间',width:'20%',formatter:getTime}
            ]
        ],
        idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#roleToolbar',
        onRowContextMenu:roleCotextMenu">
    </table>
</div>
<div id="roleCotextMenu" class="easyui-menu" style="width:120px;">
    <div id="editeRoleContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removeRoleContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="roleToolbar">
    <a id="roleAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">添加</a>
    <a id="roleEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
    <a id="roleRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
    <a id="roleCastPermissionLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
       plain="true">授权</a>
</div>
<div id="roleDialog" class="easyui-dialog" title="添加角色" style="width:500px;height:250px;padding:10px;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:roleFormSave
    },{
        text:'取消',
        iconCls:'icon-no',
        handler:roleFormNo
    }]">
    <form id="roleForm" action="${FTL.X.global_domain}/role/save" method="post">
        <input type="hidden" id="roleId" name="id"/>
        <table cellspacing="10" style="margin:10px auto;">
            <tbody >
            <tr>
                <td>部门：</td>
                <td><input type="text" id="departCombobox" name="departmentId" data-options="required:true" class="easyui-validate easyui-textbox" style="width: 250px"/></td>
            </tr>
            <tr>
                <td>角色名称：</td>
                <td><input type="text" name="name"  class="easyui-validate easyui-textbox" data-options="required:true,validType:'roleNameExist'" style="width: 250px"/></td>
            </tr>
            <tr>
                <td>角色编号：</td>
                <td><input type="text" name="code" class="easyui-validate easyui-textbox" data-options="required:true,validType:'roleCodeExist'" style="width: 250px"/></td>
            </tr>
        </table>
    </form>
</div>
<div id="authorizeDialog" class="easyui-dialog" title="授权" style="width:500px;height:400px;padding:20px 20px 20px 60px;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:authorizeSave
    },{
        text:'取消',
        iconCls:'icon-no',
        handler:authorizeNo
    }]">
        <ul id="authorizeTree">
                
        </ul>
</div>

</@FTL.admin>