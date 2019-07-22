<@FTL.admin id="taximeterList" title="动态计价器管理"
add_script_files=['admin/taximeter/taximeter.js','admin/taximeter/common.js']>
<table id="taximeterDataGrid"  style="width:100%;height:100%"
            url="${FTL.X.global_domain}/taximeter/findAll"
            toolbar="#taximeterToolbar" pagination="true" idField="id"
            rownumbers="true" fitColumns="true" pageSize="50" fit="true" singleSelect="true"
            border="false" striped="true">
	 <thead>
        <tr>
        	<@shiro.hasPermission name="DTJJQ_FHRQ">
        	<th field="deliveryTime" sortable="true">发货日期</th>
        	</@shiro.hasPermission>
        	<@shiro.hasPermission name="DTJJQ_DHRQ">
            <th field="arrivalTime" sortable="true">到货日期</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_SKU">
            <th field="sku" sortable="true">sku</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_ZY">
            <th field="description" sortable="true">摘要</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_FHPC">
            <th field="batch" sortable="true">发货/到货批次</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_YSFS">
            <th field="shippingtype" sortable="true">运输方式</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_FHSL">
            <th field="qty" sortable="true">发货数量</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_GJ">
            <th field="country" sortable="true">国家</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_CGDJ">
            <th field="price" sortable="true">采购单价</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_CGJQDJ">
            <th field="weightedprice">采购加权单价</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_ZZCBAF">
            <th field="af">最终成本AF</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_ZZCBSF">
            <th field="sf">最终成本SF</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_ZZCBCO">
            <th field="co">最终成本CO</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_SJXL">
            <th field="actualSales">实际销量</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_DRJC">
            <th field="balance" sortable="true">当日结存</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_DRZT">
            <th field="onpassage" sortable="true">当日在途</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_KCJE">
            <th field="amount">库存金额</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_SJKC">
            <th field="balanceamount">实际库存</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_ERPKC">
            <th field="repertory" sortable="true">ERP库存</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_JCKCDJ">
            <th field="balanceprice">结存库存单价</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_OLR">
            <th field="oprofit">15%利润</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="DTJJQ_TLR">
            <th field="tprofit">22%利润</th> 
            </@shiro.hasPermission>
        </tr>
    </thead>
</table>

<div id="taximeterToolbar">
<div style="padding:10px;">
	<form id="rateForm">
		<table style="float:left; min-width:1000px;">
			<tr style="min-width:1000px;">
				<td align="right"><label>国家:</label></td>
				<td>
					<select id="country" class="easyui-combobox" style="width: 150px;" editable="false">
							<option value="" >-- 请选择 --</option>
							<option value="US01" >US01</option>
							<option value="CA01" >CA01</option>
							<option value="JP01" >JP01</option>
							<option value="EU01" >EU01</option>
							<option value="UK0R" >UK0R</option>
							<option value="US02" >US02</option>
							<option value="DE02" >DE02</option>
							<option value="UK02" >UK02</option>
					</select>
				</td>
				<td align="right" style="padding-left:10px;"><label>sku:</label></td>
				<td>
					<input type="text" id="sku" class="easyui-textbox" style="width: 150px;" />
				</td>
				<td align="right" style="padding-left:10px;"><label>摘要:</label> </td>
				<td>
					<input type="text" id="description" class="easyui-textbox" style="width: 150px;" />
				</td>
				<td align="right" style="padding-left:10px;"><label>发货时间:</label></td>
				<td>
					<input type="text" id="cstarttime"  class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="cendtime" class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'"/>
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td align="right"><label>批次:</label></td>
				<td>
					<input type="text" id="batch" class="easyui-textbox" style="width: 150px;" />
				</td>
				<td align="right" style="padding-left:10px;"><label>运输方式:</label></td>
				<td>
					<select id="shippingtype" class="easyui-combobox" style="width: 150px;" editable="false">
							<option value="" >-- 请选择 --</option>
							<option value="af" >af</option>
							<option value="sf" >sf</option>
							<option value="co" >co</option>
					</select>
				</td>
				<td align="right" style="padding-left:10px;"><label>发货数量:</label> </td>
				<td>
					<input type="text" id="qty" class="easyui-numberbox" style="width: 150px;" data-options="precision:0,min:0"/>
				</td>
				<td align="right" style="padding-left:10px;"><label>到货时间:</label></td>
				<td>
					<input type="text" id="ustarttime"  class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="uendtime" class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'"/>
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td align="right" ><label>采购单价:</label> </td>
				<td>
					<input type="text" id="price" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:10px;"><label>当日结存:</label> </td>
				<td>
					<input type="text" id="balance" class="easyui-numberbox" style="width: 150px;" data-options="precision:0,min:0" />
				</td>
				<td></td>
				<td align="right">
					<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
					<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
				</td>
			</tr>
		</table>
		<br clear="all"/>
	</form>
</div>
<hr>
<div>
	<div style="padding:5px 10px;">
		<select id="template" class="easyui-combobox" style="width: 150px;" editable="false">
			<option value="">导出全部</option>
		</select>
		<a id="taximeterExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
	</div>
</div>
</div>

</@FTL.admin>
