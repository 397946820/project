<@FTL.admin id="amazonOrderReport" title="eda发货管理"
add_script_files=['admin/salesStatistics/amazonOrderReport.js']>
<div class="easyui-panel">
		<form id="amazonOrderReportSearchParam">
		<table style="float:left; min-width:1000px;">
			
			<tr style="min-width:1000px;">
				<td><label style="">国家站点:</label></td>
				<td>
				<select id="siteCombobox" class="easyui-combobox" name="marketplace" style="width:173px;">
					<option value="">--请选择--</option>
					<option value="amazon.com">US(美国)</option>
					<option value="amazon.ca">CA(加拿大)</option>
					<option value="amazon.jp">JP(日本)</option>
					<option value="amazon.de">DE(德国)</option>
					<option value="amazon.co.uk">UK(英国)</option>
					<option value="amazon.es">ES(西班牙)</option>
					<option value="amazon.fr">FR(法国)</option>
					<option value="amazon.it">IT(意大利)</option>
					<option value="amazon.com.au">AU(澳大利亚)</option>
				</select>
				</td>
				<td><label style="">起始时间:</label></td>
				<td><input type="text" name="startTime"  class="easyui-datetimebox" />
				</td>
				<td><label style="">结束时间:</label></td>
				<td><input type="text" name="endTime"  class="easyui-datetimebox" />
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td><label style="">类型:</label></td>
				<td><input id="orderTypeCombobox" type="text" name="orderType"  class="easyui-combobox" />
				<td><label style="">订单ID:</label></td>
				<td><input type="text" name="orderId"  class="easyui-textbox" />
				</td>
				<td><label style="">sku:</label></td>
				<td><input type="text" name="sku"  class="easyui-textbox" />
				</td>
				<td>
					
				</td>
				<td>
				<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="amazonOrderReportReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="amazonOrderReportSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
<table id="amazonOrderReportList"  style="width:100%;height:93%" class="easyui-datagrid">        
</table>
</@FTL.admin>
