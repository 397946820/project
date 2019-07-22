<@FTL.admin id="TemplateSettingList" title="模板设置"
add_script_files=['admin/templateSetting/templateSetting.js']>
<div data-options="region:'center',border:false">
	<table id="templateSettingDataGrid" class="easyui-datagrid"
		data-options="
           url:'${FTL.X.global_domain}/templateSetting/findAll',
           fitColumns:true,
           columns: [
            [
                {field : 'id',checkbox : true},
                {field: 'carryTemplateUrl', title: '刊登模板', width: 10},
                {field: 'name', title: '名称', width: 15},
                {field: 'siteId', title: '卖家描述', width: 15},
                {field: 'topPromotionType', title: '顶部推广', width: 20},
                {field: 'cs', title: '模板内置推广', width: 15},
                {field: 'footerPromotionType', title: '底部推广', width: 20},
                {field: 'scaler', title: '计数器', width: 5},
                {field: 'enablePageProtection', title: '保护网页', width: 10,
                	formatter:function(value,row,index){ 
                		if(value == '0') {
                			return '是';
                		} else {
                			return '否';
                		}
                	}
                }
                
            ]
        ],
        idField: 'id',
        singleSelect: false,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#templateSettingToolbar',
        onRowContextMenu:templateSettingCotextMenu">
	</table>
</div>
<div id="templateSettingCotextMenu" class="easyui-menu"
	style="width: 120px;">
	<div id="templateSettingEditContextMenu"
		data-options="iconCls:'icon-edit'">编辑</div>
	<div id="templateSettingRemoveContextMenu"
		data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="templateSettingToolbar">
	<a id="templateSettingAddLinkbutton" href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> <a
		id="templateSettingEditLinkbutton" href="javascript:void(0);"
		class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a> <a
		id="templateSettingRemoveLinkbutton" href="javascript:void(0);"
		class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
</div>
<div id="templateSettingDialog" class="easyui-dialog" title="添加/编辑模板设置"
	style="width: 500px; height: 300px; padding: 10px;"
	data-options="modal:true,
     closed:true,
     toolbar: [{
        text:'保存',
        iconCls:'icon-save',
        handler:templateSettingFormSave
    },{
        text:'关闭',
        iconCls:'icon-no',
        handler:templateSettingFormNo
    }]">
	<form id="templateSettingForm" action="${FTL.X.global_domain}/templateSetting/saveEdit"
		method="post">
		<input type="hidden" name="id" id="hid" />
		<table align="center">
			<tbody>
				<tr>
					<td>名称：</td>
					<td><input type="text" name="name" id="name"
						class="easyui-validate" required="required" style="width: 250px" /></td>
				</tr>
				<tr>
					<td>ebay账户：</td>
					<td><select style="width: 250px" name="ebayAccount" id="ebayAccount">
							<option value="le.deutschland">le.deutschland</option>
							<option value="uk.le">uk.le</option>
					</select></td>
				</tr>
				<tr>
					<td>刊登模板：</td>
					<td><input type="text" class="easyui-validate" name="carryTemplateUrl" required="required" style="width: 250px"/></td>
				</tr>
				<tr>
					<td>顶部推广：</td>
					<td><select style="width: 250px" name="topPromotionType" id="topPromotionType">
							<option value="0">-- 选择 --</option>
							<option value="ebay分类同类目推荐（最热卖）">ebay分类同类目推荐（最热卖）</option>
							<option value="Other light may you like">Other light may you like</option>
							<option value="PA_Ending_Soonest">PA_Ending_Soonest</option>
							<option value="PA_Newly_Listed">PA_Newly_Listed</option>
							<option value="uk.le灯条">uk.le灯条</option>
							<option value="台灯">台灯</option>
							<option value="店铺分类 同类目推荐（最热卖）">店铺分类 同类目推荐（最热卖）</option>
							<option value="野营灯">野营灯</option>
					</select></td>
				</tr>
				<tr>
					<td>底部推广：</td>
					<td><select style="width: 250px" name="footerPromotionType" id="footerPromotionType">
							<option value="0">-- 选择 --</option>
							<option value="ebay分类同类目推荐（最热卖）">ebay分类同类目推荐（最热卖）</option>
							<option value="Other light may you like">Other light may you like</option>
							<option value="PA_Ending_Soonest">PA_Ending_Soonest</option>
							<option value="PA_Newly_Listed">PA_Newly_Listed</option>
							<option value="uk.le灯条">uk.le灯条</option>
							<option value="台灯">台灯</option>
							<option value="店铺分类 同类目推荐（最热卖）">店铺分类 同类目推荐（最热卖）</option>
							<option value="野营灯">野营灯</option>
					</select></td>
				</tr>
				<tr>
					<td>计数器：</td>
					<td><select style="width: 250px" name="scaler" id="scaler">
							<option value="0">没有计数器</option>
							<option value="1">计数器1</option>
							<option value="2">计数器2</option>
							<option value="3">计数器3</option>
							<option value="4">计数器4</option>
							<option value="5">计数器5</option>
							<option value="6">计数器6</option>
							<option value="7">计数器7</option>
							<option value="8">计数器8</option>
					</select></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="checkbox" name="enablePageProtection" id="enablePageProtection" value="0" />启动网页保护</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
