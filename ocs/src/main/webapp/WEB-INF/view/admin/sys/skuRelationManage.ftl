<@FTL.admin id="skuRelationManageList" title="产品关系维护" add_script_files=['admin/product/skuRelationManage.js']>
<div data-options="region:'center',border:false">
    <table id="userDatagrid" class="easyui-datagrid"
           data-options="
           url:'${FTL.X.global_domain}/user/query',
           fitColumns:true,
	       idField: 'id',
	       singleSelect: true,
	       rownumbers: true,
	       pagination: true,
	       pageSize: 50,
	       border:false,
	       fit:true,
	       toolbar:'#userToolbar'">
        <thead>
			<tr>
				<th field="id" hidden="true">id</th>
				<th field="departmentId" hidden="true">departmentId</th>
				<th field="userName" width="12%">用户名</th>
				<th field="userCode" width="12%">用户编号</th>
				<th field="nick" width="12%">昵称</th>
				<@shiro.hasPermission name="ADMINISTRATOR">
					<th field="departmentName" width="12%">部门名称</th>
				</@shiro.hasPermission>
				<th field="roleName" width="12%">角色名称</th>
				<th field="creationDate" width="12%" formatter="getTime">创建时间</th>
				<th field="lastUpdationDate" width="12%" formatter="getTime">最后更新时间</th>
			</tr>
        </thead>
    </table>
</div>
<div id="userToolbar">
    <a id="skuCastingLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"
       plain="true">分配SKU</a>
</div>
<div id="relationManageDialog" class="easyui-dialog" title="产品-用户关系关联" style="width:80%;height:80%;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:castingSkuSave
    },{
    	text:'取消',
    	iconCls:'icon-no',
    	handler:closeRelationDialog
    }]">

   <table id="skuCastingDatagrid" class="easyui-datagrid" 
   		  data-options="
           fitColumns:true,
	       idField: 'id',
	       rownumbers: true,
	       border:false,
	       fit:true,
	       toolbar:'#skuToolbar'">
   		<thead>
        <tr>
        	<th field="entityId" checkbox="true">entityId</th>
        	<th field="userId" hidden="true">userId</th>
        	<th field="sku">sku</th>
            <th field="length">长(m)</th>
        </tr>
    </thead>
    <div id="skuToolbar">
	    sku：<input type="text" id="relationSku" class="easyui-textbox" style="width: 200px;" />
	    <a id="relationQuery" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
   </table>
</div>
</@FTL.admin>