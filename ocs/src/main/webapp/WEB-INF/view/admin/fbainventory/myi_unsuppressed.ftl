<@FTL.admin id="myi_unsuppressed" title="亚马逊物流管理库存"
add_script_files=['admin/fbainventory/myi_unsuppressed.js']>
<div class="easyui-panel">
		<form id="myi_unsuppressedSearchParam">
			<table>
				<tr>
					<td class="title"><label>国家站点:</label></td>
					<td>
						<select id="siteCombobox" class="easyui-combobox" name="marketplace" style="width:173px;">
							<option value="">--请选择--</option>
							<option value="amazon.com">US(美国)</option>
							<option value="amazon.ca">CA(加拿大)</option>
							<option value="amazon.jp">JP(日本)</option>
							<option value="amazon.de">DE(德国)</option>
							<option value="amazon.co.uk">UK(英国)</option>
							<option value="amazon.com.au">AU(澳大利亚)</option>
							<!--
							<option value="amazon.es">ES(西班牙)</option>
							<option value="amazon.fr">FR(法国)</option>
							<option value="amazon.it">IT(意大利)</option>
							-->
						</select>
					</td>
					<td class="title"><label>SKU:</label></td>
					<td><input type="text" id="fba_sku" name="sku" class="easyui-textbox" /></td>
					<td class="title"><label>日期:</label></td>
					<td><input type="text" id="fba_startTime" name="startTime" /></td>
					<td class="title" style="display: none"><label>结束时间:</label></td>
					<td style="display: none"><input type="text" id="fba_endTime" name="endTime" /></td>
					<td class="title">
						<div>
							<a href="javascript:void(0);" id="myi_unsuppressedReset" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
							<a href="javascript:void(0);" id="myi_unsuppressedSearch" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
<table id="myi_unsuppressedList" style="width:100%;height:93%" class="easyui-datagrid">        
</table>
</@FTL.admin>
