<@FTL.admin id="salesOrderList" title="销售订单"
add_script_files=['admin/salesStatistics/salesOrder.js','admin/salesStatistics/common.js']>
<table id="salesOrderDatagrid"  style="width:100%;height:100%" class="easyui-datagrid"
            url=""
            toolbar="#salesOrderToolbar" pagination="true" idField="id"
            rownumbers="true" fitColumns="true" pageSize="50" fit="true" singleSelect="true"
            border="false" striped="true">
	<thead>
		<tr>
			<th field="sku">sku</th>
			<th field="asin">Asin</th>
			<th field="platform">账号</th>
			<th field="station">站点</th>
			<th field="currencycode">币种</th>
			<th field="deduction">折扣额</th>
			<th field="taxrate">税额</th>
			<th field="price">金额</th>
			<th field="count">数量</th>
			<th field="unitprice">单价</th>
			<th field="status">订单状态</th>
			<th field="purchaseat" formatter="getTime">购买时间</th>
			<th field="lastestshipdate" formatter="getTime">发货时间</th>
			<th field="createdat" formatter="getTime">订单创建时间</th>
			<th field="updatedat" formatter="getTime">订单更新时间</th>
			<th field="lastfetchtime" formatter="getTime">最新拉取数据时间</th>
			<th field="paidtime" formatter="getTime">支付时间</th>
		</tr>
	</thead>
</table>
<div id="salesOrderToolbar" style="padding-left: 15px;">
	<div style="margin: 6px 33px;">
		<span id="source_">
			平台：<select id="source" class="easyui-combobox" name="source" style="width: 170px;" editable="false">
				<@shiro.hasPermission name="SJGLX_PF_YMX">
				<option value="amazon">亚马逊</option>
				</@shiro.hasPermission>
				
				<@shiro.hasPermission name="SJGLX_PF_EBAY">
				<option value="ebay">Ebay</option>
				</@shiro.hasPermission>
				
				<@shiro.hasPermission name="SJGLX_PF_GW">
				<option value="light">官网</option>
				</@shiro.hasPermission>
				
				<@shiro.hasPermission name="SJGLX_PF_WEM">
				<option value="walmart">沃尔玛</option>
				</@shiro.hasPermission>
			</select>
		</span>
		
		<a style="margin-left:31px;" id="salesOrderExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
	</div>
	<div>
		<form id="searchFrom">
		<table>
			<tr>
				<td style="text-align:right; min-width: 235px;">
					sku：<input type="text" id="sku" class="easyui-textbox" style="width: 170px;" />
				</td>
				<td id="platform_" style="text-align:right;  min-width: 265px;">账号：
					<select id="platform" class="easyui-combobox" name="platform" style="width: 170px;" editable="false">
						
					</select>
				</td>
				
				<td id="station_" align="right" style="text-align:right;min-width: 237px;padding-right: 10px;">站点：
					<select id="station" class="easyui-combobox" name="station" style="width: 190px;" editable="false">
						
					</select>
				</td>
				<td align="right" id="asin_" style="text-align:left;  min-width: 237px; padding-left:20px;">
					Asin：<input type="text" id="asin" class="easyui-textbox" style="width: 190px;"/>
				</td>
				
			</tr>
			<tr>
				<td style="text-align:right; min-width: 235px;max-height:24px">订单状态：
					<select id="status" class="easyui-combobox" name="status" style="width: 170px;" editable="false">
						
					</select>
				</td>
				
				<td style="text-align:right;  min-width: 265px; margin-left:40px;max-height:24px">
					按哪个时间：
					<select id="whichTime" class="easyui-combobox" name="whichTime" style="width: 170px;" editable="false">
						
					</select>
				</td>
				<!-- <td>
					&nbsp;&nbsp;
					时间段：
					<select id="timeQuantum" class="easyui-combobox" name="timeQuantum" style="width: 200px;" editable="false">
						<option value="-30">近一个月销量</option>
						<option value="-15">近半个月销量</option>
						<option value="-7">近7天销量</option>
						<option value="0">自定义时间区间</option>
					</select>
				</td> -->
			
				<td align="right" style="padding-left: 53px; min-width: 195px;padding-right: 8px;">
					<input type="text" id="starttime"  style="width:90px;" class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="endtime"  style="width:90px;" class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/>
				</td>
				<td style="text-align:right; min-width:150px; padding-right: 8px;">
					<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
					<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>

</@FTL.admin>
