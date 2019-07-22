<@FTL.admin id="edaStockHisManager" title="eda库存管理"
add_script_files=['admin/eda/edaStockHisManager.js']>
<div class="easyui-panel" id="edaStockManagerSearchParam-panel">
		<form id="edaStockManagerSearchParam">
		<table style="float:left; min-width:1000px;padding:10px;">
			
			<tr style="min-width:1000px;">
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
					    	<a  href="javascript:void(0);" id="edaStockManagerReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="edaStockManagerSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
<table id="edaStockManagerList"  style="width:100%;height:95%" class="easyui-datagrid">        
</table>
</@FTL.admin>
