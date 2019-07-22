<@FTL.admin id="departmentList" title="部门列表" add_script_files=['admin/sys/department.js']>
<div data-options="region:'center',border:false">
    <table id="departmentDatagrid" class="easyui-datagrid"
           data-options="
           url:'${FTL.X.global_domain}/department/list',
           fitColumns:true,
           idField: 'id',
           singleSelect: true,
           rownumbers: true,
           pagination: true,
           pageSize: 50,
           border:false,
           fit:true,
           toolbar:'#departmentToolbar',
           onRowContextMenu:departmentCotextMenu">
        <thead>
        	<tr>
        		<th data-options="field: 'id', title: 'id',hidden:true"></th>
        		<th data-options="field: 'name',width:'25%'">部门名称</th>
        		<th data-options="field: 'code',width:'25%'">部门编码</th>
        		<th data-options="field: 'creationDate',width:'25%',formatter:getTime">创建时间</th>
        		<th data-options="field: 'lastUpdationDate',width:'25%',formatter:getTime">最后更新时间</th>
        	</tr>
        </thead>
    </table>
</div>
<div id="departmentCotextMenu" class="easyui-menu" style="width:120px;">
    <div id="editeDepartmentContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removeDepartmentContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="departmentToolbar">
    <a id="departmentAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">添加</a>
    <a id="departmentEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
    <a id="departmentRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
</div>
<div id="departmentDialog" class="easyui-dialog" title="添加部门" style="width:500px;height:200px;padding:10px;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:departmentFormSave
    },{
    	text:'取消',
    	iconCls:'icon-no',
    	handler:depertmentFomNo
    }]">
    <form id="departmentForm" action="${FTL.X.global_domain}/department/save" method="post">
        <input type="hidden" id="departmentId" name="id"/>
        <table cellspacing="10" style="margin:10px auto;">
            <tbody>
            <tr>
                <td>部门名称：</td>
                <td><input type="text" name="name" class="easyui-validate easyui-textbox" data-options="required:true,validType:'projectNameExist'" style="width: 250px;"/></td>
            </tr>
            <tr>
                <td>部门编码：</td>
                <td><input type="text" data-options="required:true,validType:'projectCodeExist'" name="code" class="easyui-validate easyui-textbox" style="width: 250px"/></td>
            </tr>
        </table>
    </form>
</div>

</@FTL.admin>