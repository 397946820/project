<@FTL.admin id="fpxStockManager" title="fpx库存管理"
add_script_files=['admin/fourpx/fpxStockHisManager.js']>
<div class="easyui-panel" id="fpxStockManagerSearchParam-panel">
		<form id="fpxStockManagerSearchParam">
		<table style="float:left; min-width:1000px;padding:10px;">
			
			<tr style="min-width:1000px;">
				<td class="title">业务区域：</td>
				<td>
					<select id="busarea" class="easyui-combobox" name="busarea" style="width:173px;">
						<option value="US">美国（US）</option>
						<option value="DE">欧洲（DE）</option>
					</select>
				</td>
				<td><label style="">SKU:</label></td>
				<td><input type="text" name="sku"  class="easyui-textbox" />
				</td>
				<td><label style="">仓库:</label></td>
				<td><input type="text" name="warehouseName"  class="easyui-textbox" />
				</td>
				<td><label style="">日期:</label></td>
				<td><input type="text" name="dayTime"  class="easyui-datebox" style="width:150px;" />
				</td>
				<td>
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="fpxStockManagerReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="fpxStockManagerSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
<table id="fpxStockManagerList"  style="width:100%;height:95%" class="easyui-datagrid">        
</table>
</@FTL.admin>
