<@FTL.admin id="ProductList" title="产品列表"
add_script_files=['admin/product/product.js']>
<div data-options="region:'center',border:false">
	<table id="productDatagrid" class="easyui-datagrid"
		data-options="
           url:'${FTL.X.global_domain}/product/findAll',
           fitColumns:true,
           columns: [
            [
                {field : 'id',checkbox : true},
                {field: 'picture', title: '图片', width: 15},
                {field: 'sku', title: 'sku', width: 30},
                {field: 'name', title: '名称', width: 40},
                {field: 'category', title: '分类', width: 15},
                {field: 'stock', title: '库存', width: 10},
                {field: 'stockWarning', title: '库存预警', width: 10},
                {field: 'weight', title: '重量(kg)', width: 10},
                {field: 'sourceArea', title: '原产地', width: 15}
            ]
        ],
        idField: 'id',
        singleSelect: false,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#productToolbar',
        onRowContextMenu:productCotextMenu">
	</table>
</div>
<div id="productCotextMenu" class="easyui-menu" style="width: 120px;">
	<div id="editProductContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
	<div id="removeProductContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="productToolbar" >
<form id="ProductListForm">
	<div style="padding:5px;background:#fff;border-bottom:1px solid #95b8e7;">
			<table>
				<tr >
					<td width="220">sku：<input type="text" id="sku" class="easyui-textbox" /></td>
					<td >名称：<input type="text" id="name" class="easyui-textbox" /></td>
					<!-- <td style="width: 250px; padding-left: 20px;">库存：<input type="text" id="stock" /></td> -->
					<td width="200" align="rights" style="padding-left:100px;">
						<a id="query_product" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> 
						<a id="reset_product" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
		</div>
	</form>
<div>
	<a id="productAddLinkbutton" href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> <a
		id="productEditLinkbutton" href="javascript:void(0);"
		class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a> <a
		id="productRemoveLinkbutton" href="javascript:void(0);"
		class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		</div>
</div>
<div id="productDialog" class="easyui-dialog" title="添加/编辑产品"
	style="width: 500px; height: 350px; padding: 10px;"
	data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:productFormSave
    },{
        text:'关闭',
        iconCls:'icon-no',
        handler:productFormNo
    }]">
	<form id="productForm" action="${FTL.X.global_domain}/product/saveEdit"
		method="post">
		<input type="hidden" name="id" />
		<table align="center">
			<tbody>
				<tr>
					<td>图片：</td>
					<td><input type="text" name="picture" class="easyui-validate easyui-textbox"
						style="width: 250px" /></td>
				</tr>
				<tr>
					<td>sku：</td>
					<td><input type="text" name="sku" class="easyui-validate easyui-textbox"
						style="width: 250px" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>名称：</td>
					<td><input type="text" class="easyui-validate easyui-textbox" name="name"
						data-options="required:true" style="width: 250px" /></td>
				</tr>
				<tr>
					<td>分类：</td>
					<td><input type="text" name="category" class="easyui-validate easyui-textbox"
						style="width: 250px" /></td>
				</tr>
				<tr>
					<td>库存：</td>
					<td><input type="text" name="stock" class="easyui-validate easyui-textbox"
						style="width: 250px" /></td>
				</tr>
				<tr>
					<td>重量(kg)：</td>
					<td><input type="text" name="weight" class="easyui-validate easyui-textbox"
						style="width: 250px" /></td>
				</tr>
				<tr>
					<td>原产地：</td>
					<td><input type="text" name="sourceArea"
						class="easyui-validate easyui-textbox" style="width: 250px" /></td>
				</tr>
		</table>
	</form>
</div>

</@FTL.admin>
