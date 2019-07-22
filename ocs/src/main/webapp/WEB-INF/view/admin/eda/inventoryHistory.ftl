<@FTL.admin id="inventoryHistory" title="eda库存流水"
add_script_files=['admin/eda/inventoryHistory.js']>
<div class="easyui-panel" id="inventoryHistorySearchParam-panel">
		<div>
		<form id="inventoryHistorySearchParam">
		<table style=" min-width:1000px;padding:10px;">
			<tr style="min-width:1000px;">
				<td><label style="">EDA业务单号:</label></td>
				<td><input type="text" name="billNum"  class="easyui-textbox" />
				</td>
				<td><label style="">类型:</label></td>
				<td>
					<input id="recordTypeCombobox" type="text" name="recordType"  class="easyui-combobox" />
				</td>
				<td><label style="">时间:</label></td>
				<td><input type="text" name="timeStart"  class="easyui-datebox" style="width:150px;" />
				&nbsp;~&nbsp;
				<input type="text" name="timeEnd" style="float:right;width:150px;" class="easyui-datebox" />
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td><label style="">SKU:</label></td>
				<td><input type="text" name="sku"  class="easyui-textbox" />
				</td>
				
				<td><label style="">仓库:</label></td>
				<td>
					<select class="easyui-combobox" name="warehouseId" value=""  >
						<option ></option>
						<option value="2">Los Angeles Warehouse</option>
						<option value="7">US New Jersey Warehouse</option>
					</select>
				</td>
				<td colspan='2'>
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="inventoryHistoryReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="inventoryHistorySearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
		</div>
		<div>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="exportDataBtn" plain="true">导出</a>
		</div>
	</div>
<table id="inventoryHistoryList"  style="width:100%;height:95%" class="easyui-datagrid">        
</table>
</@FTL.admin>
