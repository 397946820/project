<@FTL.admin id="permissionTree" title="图片类目" add_script_files=['admin/sys/permission.js']>
<div data-options="region:'center',border:false">
    <table id="permissionTreegrid" class="easyui-treegrid"
           data-options="
           url:'${FTL.X.global_domain}/permission/list',
           columns: [
            [
                {field: 'id', title: 'id',hidden:true},
                {field: 'parentName', title: 'parentName',hidden:true},
                {field: 'projectId', title: 'projectId',hidden:true},
                {field: 'name', title: '权限名称'},
                {field: 'code', title: '权限编码'},
                {field: 'projectName', title: '项目名称'},
                {field: 'permissionType', title: '权限类型',formatter:getPermissionType},
                {field: 'url', title: '链接地址'},
                {field: 'orderNum', title: '排序'},
                {field: 'creationDate', title: '创建时间',formatter:getTime},
                {field: 'lastUpdationDate', title: '最后更新时间',formatter:getTime}
            ]
        ],
        treeField:'name',
        idField: 'id',
        singleSelect: true,
        border:false,
        toolbar:'#permissionToolbar',
        onContextMenu:permissionCotextMenu">
    </table>
</div>
<div id="permissionCotextMenu" class="easyui-menu" style="width:120px;">
	<div id="addPermissionContextMenu" data-options="iconCls:'icon-edit'">添加</div>
    <div id="editePermissionContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removePermissionContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="permissionToolbar">
    <a id="permissionAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">添加</a>
    <a id="permissionEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
    <a id="permissionRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
</div>
<div id="permissionDialog" class="easyui-dialog" title="添加权限" style="width:500px;height:400px;padding:10px;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:permissionFormSave
    },{
    	text:'取消',
    	iconCls:'icon-no',
    	handler:permissionFormNo
    }]">
    <form id="permissionForm" action="${FTL.X.global_domain}/permission/save" method="post">
        <input type="hidden" id="permissionId" name="id"/>
        <table cellspacing="10" style="margin:10px auto;">
            <tbody id="formTbody">
            <tr>
                <td>项目名称：</td>
                <td><input type="text" name="projectId" id="projectCombobox" class="easyui-validate" data-options="required:true" style="width: 250px"/></td>
            </tr>
            <tr>
                <td>父权限：</td>
                <td>
                	<input type="text" id="parentId" hidden="true" name="parentId" class="easyui-validate" style="width: 250px" readonly="true" />
                	<input type="text" id="parentName" name="parentName" class="easyui-validate easyui-textbox" style="width: 250px;" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>权限名称：</td>
                <td><input type="text" name="name" class="easyui-validate easyui-textbox" data-options="required:true,validType:'permissionNameExist'" style="width: 250px;"/></td>
            </tr>
            <tr>
                <td>权限编号：</td>
                <td>
                    <input type="text" class="easyui-validate easyui-textbox" name="code"
                           data-options="required:true,validType:'permissionCodeExist'" style="width: 250px"/>
                </td>
            </tr>
            <tr>
                <td>权限类型：</td>
                <td>
                    <input type="text" id="permissionTypeCombobox" class="easyui-validate" name="permissionType"
                           data-options="required:true" style="width: 250px"/>
                </td>
            </tr>
            <tr>
                <td>排序：</td>
                <td>
                    <input type="text" class="easyui-validate" name="orderNum" style="width: 240px; border:1px solid #95B8E7;padding:4px;border-radius:5px;"/>
                </td>
            </tr>
            <tr id="menu_url">
                <td>链接地址：</td>
                <td>
                    <input type="text" class="easyui-validate" name="url" style="width: 240px; border:1px solid #95B8E7;padding:4px;border-radius:5px;"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</@FTL.admin>