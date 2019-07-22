<@FTL.admin id="StoreCategoryInfo" title="商店分类信息" >
 
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
        toolbar:'#storeCategoryToolbar',
        onContextMenu:storeCategoryCotextMenu">
    </table>
</div>

<div id="storeCategoryToolbar">
         用户：<select class="easyui-combobox" id="userName" name="userName"  style="width:250px" >
						<option value="uk.le" selected>uk.le</option>
						<option value="le.deutschland" >le.deutschland</option>
						<option value="nm.deutschland">nm.deutschland</option>
						<option value="uk.nm">uk.nm</option>
	站点：<input   class="easyui-combobox" id="marketplace_id" name="marketplace_id"  style="width:250px;"  data-options="valueField:'site_id',textField:'site',value:'0',url:'${FTL.X.global_domain }/SiteDetails/internalSelectSiteDetails'"/>
    <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="synchronouStoreCategory()" style="width:120px">同步店铺类目</a>
</div>
   
</div>
<script type="text/javascript">

	function synchronouStoreCategory(){
		 debugger;
		 var user_name =$("#userName").val();
		 var marketplace_id = $('#marketplace_id').combobox("getValue");
		 $.ajax({
				url: GLOBAL.domain+'/StoreCategory/synchronouStoreCategory',
				
				data: {user_name:user_name,marketplace_id:marketplace_id},
				
				dataType: "json",
				
				contentType: "application/json; charset=UTF-8",
				
				type: "get",
				
				success: function(result) {
				 
	                    	    // reload the user data
					$.messager.alert("信息",result.description);	
				},
				error: function(jqXHR, textStatus, errorThrown) {
					$.messager.alert("信息","同步失败");		
				}
			}); 
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