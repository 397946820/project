<@FTL.admin id="storeCategory" title="商店类目" add_script_files=['admin/ocs/main.js']>
<div data-options="region:'center',border:false">
    <table id="storeCategoryTable" class="easyui-treegrid" 
           data-options="
           url:'${FTL.X.global_domain }/StoreCategory/selectStoreCategorys',
           columns: [
            [
                {field: 'category_id', title: 'category_id',hidden:true},
                {field: 'parentName', title: 'parentName',hidden:true},
                {field: 'name', title: '类目名称'},
                {field: 'category_order', title: '商店类目排序'},
                {field: 'creationDate', title: '创建时间',formatter:getTime},
                {field: 'lastUpdationDate', title: '最后更新时间',formatter:getTime}
            ]
        ],
        treeField:'name',
        idField: 'category_id',
        singleSelect: true,
        border:false,
        fit:true,
        toolbar:'#storeCategoryToolbar',
        onContextMenu:storeCategoryCotextMenu">
    </table>
</div>
<div id="storeCategoryCotextMenu" class="easyui-menu" style="width:120px;">
	<div id="addStoreCategoryContextMenu" data-options="iconCls:'icon-edit'">添加</div>
    <div id="editeStoreCategoryContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removeStoreCategoryContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="storeCategoryToolbar">
    <a id="storeCategoryAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
       plain="true" onclick="addStoreCategory()">添加</a>
    <a id="storeCategoryEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true" onclick="editstoreCategory()">编辑</a>
    <a id="storeCategoryRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true" onclick="deleteStoreCategory()">删除</a>
    <a id="storeCategoryRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-update"
       plain="true" onclick="synStoreCategory()">同步</a>
</div>
<div id="storeCategoryDialog" class="easyui-dialog" title="添加类目" style="width:500px;height:300px;padding:10px;"
     data-options="modal:true,
     closed:true,
     toolbar: [{
        text:'保存',
        iconCls:'icon-save',
        handler:storeCategoryFormSave
    }]">
    <form id="storeCategoryForm" action="${FTL.X.global_domain }/StoreCategory/addOrEdit" method="post">
        <input type="hidden" name="category_id"/>
        <table>
            <tbody>
            <tr>
                <td>父类目：</td>
                <td>
                	<input type="text" id="parent_category_id" hidden="true" name="parent_category_id" class="easyui-validate" style="width: 250px" readonly="true" />
                	<input type="text" id="parentName" name="parentName" class="easyui-validate" style="width: 250px" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>类目名称：</td>
                <td><input type="text" name="name" class="easyui-validate" data-options="required:true" style="width: 250px"/></td>
            </tr>
            <tr>
                <td>商店类目排序：</td>
                <td>
                    <input type="text" class="easyui-validate" name="category_order"
                           data-options="required:true" style="width: 250px"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">

	function synStoreCategory(){
		mainAjax("/StoreCategory/insertStoreCategorys",null,"正在同步中....","get",null);
	}
	function addStoreCategory(){
		
	    $('#storeCategoryForm').form('clear');
	    var row = $('#storeCategoryTable').treegrid('getSelected');
	    
	    if (row) {
	    	$('#parentName').val(row.name);
	    	$('#parent_category_id').val(row.category_id);
	    }
	    $('#storeCategoryDialog').window('open');
	};
	function editstoreCategory(){
	    var row = $('#storeCategoryTable').datagrid('getSelected');
	    if (!row) {
	        $.messager.alert("信息", "请选中一行编辑");
	        return;
	    }
	    $('#parentName').val(row.parentName);
	    $('#storeCategoryForm').form('clear');
	    $('#storeCategoryForm').form('load', row);
	    $('#storeCategoryDialog').window('open');
	   
	};
	function deleteStoreCategory(){
		alert("短时停用删除功能！");
	}
	function storeCategoryFormSave(){
	    $('#storeCategoryForm').form('submit', {
	        onSubmit: function () {
	            return $(this).form('validate');
	        },
	        success: function (response) {
	            var $data = JSON.parse(response);
	            if ($data && $data.errorCode == 0) {
	                $.messager.alert('消息提示', $data.description, 'info');
	                $('#storeCategoryDialog').window('close');
	                $('#storeCategoryTable').treegrid('reload');
	                $('#storeCategoryTable').treegrid('clearSelections');
	            }
	        }
	    });
	};
	var storeCategoryCotextMenu = function (e, row) {
	    e.preventDefault();
	    $('#storeCategoryTable').treegrid("select",row.id);
	    $('#storeCategoryCotextMenu').menu('show', {
	        left: e.pageX,
	        top: e.pageY
	    });
	};
	

</script>

</@FTL.admin>