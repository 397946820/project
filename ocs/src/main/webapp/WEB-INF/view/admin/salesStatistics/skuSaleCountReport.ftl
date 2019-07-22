<@FTL.admin id="skuSaleCountReport" title="sku销售统计"
add_script_files=['admin/salesStatistics/skuSaleCountReport.js']>
<div style="height:100%;width:100%;">
	<div class="easyui-panel" style="padding:10px;padding-left: 15px;height:10%;">
		<form id="skuSaleCountSearchParam" >
		<table style="float:left; min-width:680px;">
			
			<tr style="min-width:1000px;">
				<td width="67"><label>付款时间段:</label></td>
				<td><input type="text" name="startTime"  class="easyui-datebox" />
				&nbsp;~&nbsp;
				<input type="text" name="endTime" style="float:right" class="easyui-datebox" />
				</td>
				
				<td>
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="skuSaleCountReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="skuSaleCountSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
<div style="height:90%;width:100%;">
	<table id="skuSaleCountReportList"  style="width:100%;height:100%" class="easyui-datagrid">        
</div>
</table>
</div>
</@FTL.admin>
