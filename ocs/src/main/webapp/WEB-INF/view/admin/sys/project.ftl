<@FTL.admin id="projectList" title="部门列表" add_script_files=['admin/sys/project.js']>
<div data-options="region:'center',border:false">
    <table id="projectDatagrid" class="easyui-datagrid"
           data-options="
           url:'${FTL.X.global_domain}/project/list',
           fitColumns:true,
           columns: [
            [
                {field: 'id', title: 'id',hidden:true},
                {field: 'name', title: '项目名称',width:'25%'},
                {field: 'code', title: '项目编码',width:'25%'},
                {field: 'creationDate', title: '创建时间',width:'25%',formatter:getTime},
                {field: 'lastUpdationDate', title: '最后更新时间',width:'25%',formatter:getTime}
            ]
        ],
        idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#projectToolbar',
        onRowContextMenu:projectCotextMenu">
    </table>
</div>
<div id="projectCotextMenu" class="easyui-menu" style="width:120px;">
    <div id="editeProjectContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removeProjectContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="projectToolbar">
    <a id="projectAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">添加</a>
    <a id="projectEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
    <a id="projectRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
</div>
<div id="projectDialog" class="easyui-dialog" title="添加项目" style="width:500px;height:200px;padding:10px;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:projectFormSave
    },{
    	text:'取消',
    	iconCls:'icon-no',
    	handler:roleFormNo
    }]">
    <form id="projectForm" action="${FTL.X.global_domain}/project/save" method="post">
        <input type="hidden" id="projectId" name="id"/>
        <table cellspacing="10" style="margin:10px auto;">
            <tbody>
            <tr>
                <td>项目名称：</td>
                <td><input type="text" name="name" class="easyui-validate easyui-textbox" data-options="required:true,validType:'projectNameExist'" style="width: 250px"/></td>
            </tr>
            <tr>
                <td>项目编码：</td>
                <td><input type="text" name="code" class="easyui-validate easyui-textbox" data-options="required:true,validType:'projectCodeExist'" style="width: 250px"/></td>
            </tr>
        </table>
    </form>
</div>

</@FTL.admin>