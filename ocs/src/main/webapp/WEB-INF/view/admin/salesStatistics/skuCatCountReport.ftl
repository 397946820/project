<@FTL.admin id="skuCatCountReport" title="sku销售统计"
add_script_files=['admin/salesStatistics/skuCatCountReport.js']>
<div style="height:100%;width:100%;">
	<div class="easyui-panel" style="padding-left: 15px;height:5%;">
		<form id="skuCatCountSearchParam" >
		<table style="float:left; min-width:680px;">
			<tr style="min-width:1000px;">
				<td width="67"><label>发货时间段:</label></td>
				<td><input type="text" name="startTime"  class="easyui-datebox" />
				&nbsp;~&nbsp;
				<input type="text" name="endTime" style="float:right" class="easyui-datebox" />
				</td>
				<td width="40">
					<label>帐号:</label>
				</td>
				<td>
					<select id="account" class="easyui-combobox" name="account">
						<option value="" align="center">---请选择---</option>
						<option value="DE">DE</option>
						<option value="JP">JP</option>
						<option value="US">US</option>
						<option value="CA">CA</option>
					</select>
				</td>
				<td width="40">
					<label>站点:</label>
				</td>
				<td>
					<input type="text" id="site" name="site" class="easyui-combobox" />
				</td>
				<td width="40">
					<label>类目:</label>
				</td>
				<td>
					<input type="text" id="productType" name="productType" class="easyui-combobox" />
				</td>
				<td>
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="skuCatCountReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="skuCatCountSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
<div style="height:96%;width:100%;">
	<table id="skuCatCountReportList"  style="width:100%;height:100%" class="easyui-datagrid">        
</div>
</table>
</div>
</@FTL.admin>
