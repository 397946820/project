<@FTL.admin id="userList" title="部门列表" add_script_files=['admin/sys/user.js']>
<div data-options="region:'center',border:false">
    <table id="userDatagrid" class="easyui-datagrid"
           data-options="
           url:'${FTL.X.global_domain}/user/list',
           fitColumns:true,
           columns: [
            [
                {field: 'id', title: 'id',hidden:true},
                {field: 'departmentId', title: 'departmentId',hidden:true},
                {field: 'userName', title: '用户名',width:'12%'},
                {field: 'userCode', title: '用户编号',width:'12%'},
                {field: 'nick', title: '昵称',width:'12%'},
                {field: 'departmentName', title: '部门名称',width:'12%'},
                {field: 'roleName', title: '角色名称',width:'12%'},
                {field: 'creationDate', title: '创建时间',formatter:getTime,width:'12%'},
                {field: 'lastUpdationDate', title: '最后更新时间',formatter:getTime,width:'12%'}
            ]
        ],
        idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#userToolbar',
        onRowContextMenu:userCotextMenu">
    </table>
</div>
<div id="userCotextMenu" class="easyui-menu" style="width:120px;">
    <div id="editeUserContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removeUserContextMenu" data-options="iconCls:'icon-remove'">删除</div>
    <div id="castingUserContextMenu" data-options="iconCls:'icon-remove'">角色分配</div>
</div>
<div id="userToolbar">
    <a id="userAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">添加</a>
    <a id="userEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
    <a id="userRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
    <a id="userCastingLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
       plain="true">角色分配</a>
    <@shiro.hasAnyRoles name="ADMINISTRATOR"> 
    	<a id="userResetPwdLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
       		plain="true">重置密码</a>
    </@shiro.hasAnyRoles>
       
</div>
<div id="userEditDialog" class="easyui-dialog" title="添加用户" style="width:500px;height:300px;padding:10px;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
       handler:userFormEdit
    },{
    	text:'取消',
        iconCls:'icon-no',
     	handler:userFormNo
    }]">
    <form id="userFormEdit" action="${FTL.X.global_domain}/user/save" method="post">
        <input type="hidden" id="userId" name="id"/>
        <table cellspacing="10" style="margin:10px auto;">
            <tbody>
	            <tr>
	                <td>部门：</td>
	                <td><input type="text" id="departEditCombobox" name="departmentId" class="easyui-validate easyui-textbox" data-options="required:true" style="width: 250px"/></td>
	            </tr>
	            <tr>
	                <td>用户名：</td>
	                <td><input type="text" name="userName" class="easyui-validate easyui-textbox" data-options="required:true,validType:'userNameExist'" style="width: 250px"/></td>
	            </tr>
	            <tr>
	                <td>用户编码：</td> 
	                <td><input type="text" name="userCode" class="easyui-validate easyui-textbox" data-options="required:true,validType:'userCodeExist'" style="width: 250px"/></td>
	            </tr>
	            <tr>
	                <td>昵称：</td>
	                <td><input type="text" name="nick" class="easyui-validate easyui-textbox" data-options="required:true" style="width: 250px"/></td>
	            </tr>
            </tbody>
        </table>
    </form>
</div>
<div id="userDialog" class="easyui-dialog" title="添加用户" style="width:500px;height:350px;padding:10px;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:userFormSave
    },{
    	 text:'取消',
    	iconCls:'icon-no',
    	handler:userFormEditNo
    }]">
    <form id="userForm" action="${FTL.X.global_domain}/user/save" method="post">
        <table cellspacing="10" style="margin:10px auto;">
            <tbody>
	            <tr>
	                <td>部门：</td>
	                <td><input type="text" id="departCombobox" name="departmentId" class="easyui-validate easyui-textbox" data-options="required:true" style="width: 250px"/></td>
	            </tr>
	            <tr>
	                <td>用户名：</td>
	                <td><input type="text" name="userName" class="easyui-validate easyui-textbox" data-options="required:true,validType:'userNameExist'" style="width: 250px"/></td>
	            </tr>
	            <tr>
	                <td>用户编码：</td>
	                <td><input type="text" name="userCode" class="easyui-validate easyui-textbox" data-options="required:true,validType:'userCodeExist'" style="width: 250px"/></td>
	            </tr>
	            <tr>
	                <td>昵称：</td>
	                <td><input type="text" name="nick" class="easyui-validate easyui-textbox" data-options="required:true" style="width: 250px"/></td>
	            </tr>
	            <tr>
	                <td>密码：</td>
	                <td><input type="password" id="userpassword" name="password" class="easyui-validate easyui-textbox" data-options="required:true" style="width: 250px"/></td>
	            </tr>
	            <tr>
	                <td>确认密码：</td>
	                <td><input type="password" id="repassword" class="easyui-validate easyui-textbox" data-options="required:true,validType:'repassword'" style="width: 250px"/></td>
	            </tr>
            </tbody>
        </table>
    </form>
</div>
<div id="userCastingRoleDialog" class="easyui-dialog" title="用户分配角色" style="width:800px;height:400px;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:castingRoleSave
    },{
    	text:'取消',
    	iconCls:'icon-no',
    	handler:castingRoleNo
    }]">
   <table id="userCastingDatagrid">
   </table>
</div>
</@FTL.admin>