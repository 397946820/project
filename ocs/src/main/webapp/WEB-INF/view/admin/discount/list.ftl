<@FTL.admin id="DiscountList" title="折扣列表"
add_script_files=['admin/discount/discount.js']>
<div data-options="region:'center',border:false">
	<table id="discountDataGrid" class="easyui-datagrid"
		data-options="
           url:'${FTL.X.global_domain}/discount/findAll',
           fitColumns:true,
           columns: [
            [
                {field : 'id',checkbox : true},
                {field: 'name', title: '名称', width: 40},
                {field: 'ebayAccount', title: 'ebay账户', width: 50},
                {field: 'validity', title: '有效期', width: 50,formatter:formatterTime},
                {field: 'discountDetail', title: '折扣', width: 40,formatter:formatterPercent},
                {field: 'siteNam', title: '运输', width: 50},
                {field: 'siteNa', title: '状态', width: 50},
                {field: 'siteN', title: '范本', width: 50}
                
            ]
        ],
        idField: 'id',
        singleSelect: false,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#discountToolbar',
        onRowContextMenu:discountCotextMenu">
	</table>
</div>
<div id="discountCotextMenu" class="easyui-menu" style="width: 120px;">
	<div id="discountEditContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
	<div id="discountRemoveContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="discountToolbar">
	<a id="discountAddLinkbutton" href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> <a
		id="discountEditLinkbutton" href="javascript:void(0);"
		class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a> <a
		id="discountRemoveLinkbutton" href="javascript:void(0);"
		class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
</div>
<div id="discountDialog" class="easyui-dialog" title="添加/编辑折扣"
	style="width: 800px; height: 400px; padding: 10px;"
	data-options="modal:true,
     closed:true,
     toolbar: [{
        text:'保存',
        iconCls:'icon-save',
        handler:discountFormSave
    },{
        text:'更新ebay',
        iconCls:'icon-reload',
        handler:discountFormReload
    },{
        text:'关闭',
        iconCls:'icon-no',
        handler:discountFormNo
    }]">
	<form id="discountForm">
		<input type="hidden" name="id" id="hid"/>
		<table align="center">
			<tbody>
				<tr>
					<td>名称：</td>
					<td><input type="text" name="name" id="name" class="easyui-validate"
						required="required" style="width: 250px" /></td>
				</tr>
				<tr>
					<td>ebay账户：</td>
					<td><select style="width: 250px" name="ebayAccount" id="ebayAccount">
							<option value="le.deutschland">le.deutschland</option>
							<option value="uk.le">uk.le</option>
					</select></td>
				</tr>
				<tr>
					<td>开始日期：</td>
					<td><input name="start" id="start" value="" class="easyui-datetimebox" required="required"
						style="width: 150px" /></td>
				</tr>
				<tr>
					<td>结束日期：</td>
					<td><input name="end" id="end" value="" class="easyui-datetimebox" required="required"
						style="width: 150px" /></td>
				</tr>
				<tr>
					<td valign="top">优惠明细：</td>
					<td>
						<div>
							<input type="checkbox" name="priceDiscount" id="priceDiscount" value="priceDiscount"/><label>价格折扣(不包括拍卖物品)</label>
						</div>
						<div>
							<input type="radio" name="rdobtnPercent" id="rdobtnPercent" value="rdobtnPercent"/><label>在原价上给予折扣</label> <input type="text" id="percent" name="percent"><label> %</label>
						</div>
						<div>
							<input type="radio" name="rdobtnValue" id="rdobtnValue" value="rdobtnValue"/><label>在原价上降价</label> <input type="text" name="txt" id="txt" />
						</div>
						<div>
							<input type="checkbox" name="freeShipping" id="freeShipping" value="freeShipping"/><label><font color="#FF7256">免运费(用于第一运输方法)</font></label>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
