<@FTL.admin id="smallTaximeterList" title="小包计价器"
add_script_files=['admin/taximeter/smallTaximeter.js','admin/taximeter/common.js','admin/taximeter/datagrid-cellediting.js']>
<table id="smallTaximeterDataGrid" style="width: 100%; height: 100%"
	url='',
	toolbar="#smallTaximeterToolbar" pagination="true" idField="id"
	rownumbers="true" fitColumns="true" pageSize="50" fit="true"
	singleSelect="true" border="false" striped="true"  autoEditing="true"
	extEditing="true" singleEditing="true">
	<thead>
		<th field="platform" sortable="true" formatter="getPlatform">平台</th>
		<th field="typeName" sortable="true">小包类型</th>
		<th field="shippingType" sortable="true">运输方式</th>
		<th field="sku" sortable="true">SKU</th>
		<th field="qty" sortable="true" editor="{type:'numberbox',options:{precision:0,min:1}}">数量</th>
		<th field="tradingMode" sortable="true" formatter="getTransactionMode" hidden="true">交易模式</th>
		<th field="finalCost" sortable="true">最终成本</th>
		<th field="profitRate" sortable="true">利润率</th>
		<th field="profitRateAction" sortable="true" editor="{type:'numberbox',options:{precision:4,min:0}}">利润系数</th>
		<th field="finalPrice" sortable="true" editor="{type:'numberbox',options:{precision:4,min:0.1}}">最终价格</th>
		<th field="createdAt" sortable="true" formatter="getTime">更新日期</th>
		<th field="updatedAt" sortable="true" formatter="getTime">更新日期</th>
	</thead>
</table>

<div id="smallTaximeterToolbar">
	<div style="padding:10px;">
		<form id="smallTaximeterSearchForm">
			<table style="float:left; min-width:700px;">
				<tr style="min-width:700px;">
					<td><label>类型名称：</label></td>
					<td>
						<select id="typeName" name="typeName" panelHeight="auto" class="easyui-combobox" style="width: 150px"  editable="false">
							
						</select>
					</td>
					<td><label>运输方式：</label></td>
					<td>
						<select id="shippingType" name="shippingType" class="easyui-combobox" style="width: 150px"  disabled="true">
							
						</select>
					</td>
					<td id="currencyCode_1" style="display: none"><label>货币：</label></td>
					<td id="currencyCode_2" style="display: none">
						<select name="currencyCode" id="currencyCode" class="easyui-combobox" style="width: 150px" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="US">USD</option>
							<option value="GB">GBP</option>
							<option value="DE">EUR</option>
						</select>
					</td>
				</tr>
				<tr style="min-width:700px;">
					<td><label>平台：</label></td>
					<td> 
						<select id="platform" name="platform" class="easyui-combobox" style="width: 150px;" editable="false">
							<@shiro.hasPermission name="SMALLJJQ_SJGLX_EBAY">
							<option value="ebay">Ebay</option>
							</@shiro.hasPermission>
							<@shiro.hasPermission name="SMALLJJQ_SJGLX_LIGHT">
							<option value="light">官网</option>
							</@shiro.hasPermission>
						</select>
					</td>
					<td><label>SKU：</label></td>
					<td>
						<input type="text" name="sku" class="easyui-textbox" style="width: 150px">
					</td>
					<td></td>
					<td>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
			<br clear="all"/>
		</form>
	</div>
	<@shiro.hasAnyRoles name="ADMINISTRATOR,CEO,SWZG,CWZG">
	<hr>
		<div>
			<a id="smallTaximeterRefreshLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>
		</div>
	</@shiro.hasAnyRoles>
</div>
</@FTL.admin>
