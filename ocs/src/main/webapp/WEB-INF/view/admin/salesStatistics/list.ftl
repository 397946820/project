<@FTL.admin id="SalesStatisticsList" title="销售分析"
add_script_files=['admin/salesStatistics/common.js','admin/salesStatistics/salesStatistics.js']>
<table id="salesStatisticsDatagrid"  style="width:100%;height:100%"
            url=""
            toolbar="#salesStatisticsToolbar" pagination="true" idField="id"
            rownumbers="true" fitColumns="true" pageSize="50" fit="true" singleSelect="true"
            border="false" striped="true" showFooter="true"  data-options="onDblClickRow:doDblClickRow">
	<thead>
		<tr>
			<th field="terrace" hidden="true" formatter="changeTerrace">平台</th>
			<th field="sku" sortable="true">sku</th>
			<th field="asin" sortable="true">Asin</th>
			<th field="platform" sortable="true">账号</th>
			<th field="station" sortable="true">站点</th>
			<th field="qty" sortable="true">订单数量</th>
			<th field="currencycode" sortable="true">币种</th>
			<th field="deduction" sortable="true">折扣额</th>
			<th field="taxrate" sortable="true">税额</th>
			<th field="price" sortable="true">金额（含税）</th>
			<th field="priceTax" sortable="true">金额(含税)</th>
			<th field="priceExcludingTax" sortable="true">金额(不含税)</th>
			<th field="count" sortable="true">总数</th>
			<th field="priceUsd" hidden="true">美元统计</th>
			<th field="priceRmb" hidden="true">人民币统计</th>
			<th field="totalAf" hidden="true">af总毛利额(美元)</th>
			<th field="totalSf" hidden="true">sf总毛利额(美元)</th>
			<th field="totalCo" hidden="true">co总毛利额(美元)</th>
			<th field="totalAfRate" hidden="true">af总毛润率</th>
			<th field="totalSfRate" hidden="true">sf总毛润率</th>
			<th field="totalCoRate" hidden="true">co总毛润率</th>
			<th field="sametermrate" sortable="true" formatter="changeValue">同比</th>
			<th field="weekrate" sortable="true" formatter="changeValue">周环比</th>
			<th field="monthrate" sortable="true" formatter="changeValue">月环比</th>
			<th field="status" sortable="true">订单状态</th>
			<@shiro.hasPermission name="XSTJ_AFMLR">
			<th field="af">af毛利额(15%)</th>
			</@shiro.hasPermission>
			
			<@shiro.hasPermission name="XSTJ_SFMLR">
			<th field="sf">sf毛利额(22%)</th>
			</@shiro.hasPermission>
			
			<@shiro.hasPermission name="XSTJ_COMLR">
			<th field="co">co毛利额(15%)</th>
			</@shiro.hasPermission>
			
			<@shiro.hasPermission name="XSTJ_AFLRL">
			<th field="afRate" formatter="changeValue">af利润率</th>
			</@shiro.hasPermission>
			
			<@shiro.hasPermission name="XSTJ_SFLRL">
			<th field="sfRate" formatter="changeValue">sf利润率</th>
			</@shiro.hasPermission>
			
			<@shiro.hasPermission name="XSTJ_COLRL">
			<th field="coRate" formatter="changeValue">co利润率</th>
			</@shiro.hasPermission>
			
			<th field="actualSalesPrice" hidden="true">真实售价</th>
			<th field="actualYield" hidden="true">真实售价的利润率</th>
			<th field="actualProfitCoefficient" hidden="true">利润系数</th>
		</tr>
	</thead>
</table>
<div id="salesStatisticsToolbar" style="padding-left: 15px;">
	<div style="margin: 6px 33px;">
		<span id="source_" style="text-align:right; min-width: 275px;">
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
				
				<@shiro.hasAnyRoles name="ADMINISTRATOR,CEO">
				<option value="allSource">全平台</option>
				</@shiro.hasAnyRoles>
			</select>
		</span>
		<a style="margin-left:31px;" id="salesStatisticsExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
		<span style="display:none" id="detailsExport">
			<a style="margin-left:31px;" id="salesStatisticsDetailsExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出明细</a>
		</span>
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
				
				<td id="station_" style="text-align:right;  min-width: 245px;">站点：
					<select id="station" class="easyui-combobox" name="station" style="width: 170px;" editable="false">
						
					</select>
				</td>
				
				<td id="asin_" style="text-align:right;  min-width: 245px;">
					Asin：<input type="text" id="asin" class="easyui-textbox" style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<td id="status_" style="text-align:right; min-width: 235px;max-height:24px">订单状态：
					<select id="status" class="easyui-combobox" name="status" style="width: 170px;" editable="false">
						
					</select>
				</td>
				<td id="whichTime_" style="text-align:right;  min-width: 265px; margin-left:40px;max-height:24px">
					按哪个时间：
					<select id="whichTime" class="easyui-combobox" name="whichTime" style="width: 170px;" editable="false">
						
					</select>
				</td>
				<td style="text-align:right;  min-width: 245px; margin-left:40px;max-height:24px">
					时间段：
					<select id="timeQuantum" class="easyui-combobox" name="timeQuantum" style="width: 170px;" editable="false">
						<option value="-30">近一个月销量</option>
						<option value="-15">近半个月销量</option>
						<option value="-7">近7天销量</option>
						<option value="0">自定义时间区间</option>
					</select>
				</td>
				<td id="time_" style="text-align:right; width: 275px; margin-left:40px;">
					<input type="text" id="starttime"  class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="endtime" class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/>
				</td>
				<td style="text-align:right; min-width:150px; margin-left:40px;">
					<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
					<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>

<div id="fileExport" class="easyui-dialog" title="数据导出" style="width:585px;height:215px;display:none;"data-options="iconCls:'icon-save',
	resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
    <form id="ExportForm" enctype="multipart/form-data" method="POST">
	    <div style="margin-bottom:20px;width:80%;padding-left: 20px;margin-top: 40px;">
	    	<div>
				<span style="height: 24px;line-height: 24px;padding-right: 10px;">账号:</span>
				<select class="easyui-combobox" id="platform_1" panelHeight="auto" style="width: 100px;" editable="false">
				</select>
				&nbsp;&nbsp;
				<span style="height: 24px;line-height: 24px;padding-right: 10px;">站点:</span>
				<select class="easyui-combobox" id="station_1" panelHeight="auto" style="width: 80px;" editable="false">
				</select>
				&nbsp;&nbsp;
				<span style="height: 24px;line-height: 24px;padding-right: 10px;">导出时间分类:</span>
				<select class="easyui-combobox" id="whichTime_1" panelHeight="auto" style="width: 70px;" editable="false">
					<option value=""></option>
					<option value="createdTime">下单时间</option>
					<option value="paidTime">支付时间</option>
					<option value="shippedTime">发货时间</option>
				</select>
			</div>
			<br/>
	    	<span style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">时间:</span>
			<input id="date1" name="fromDate" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 200, panelAlign: 'right', editable: false">
			-
			<input id="date2" name="toDate" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 200, panelAlign: 'right', editable: false">
		</div>
		<div style="width:80%;padding-left: 35px;">
			<a href="javascript:void(0);"  id="exportSubmit" class="easyui-linkbutton" style="width:100%">Export</a>
		</div>
	</form>
</div>

<div id="detailsToolbar">
	<a id="detailsExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>	
</div>

<div id="skuDialog" class="easyui-dialog" title="sku详情" closed="true" modal="true"
	style="width: 1200px; height: 650px; padding: 10px;" buttons="#rate-buttons">
	<table id="details" style="width:100%;height:100%" toolbar="#detailsToolbar"
            url="" idField="id"
            rownumbers="true" fitColumns="true" fit="true" singleSelect="true"
            border="false" striped="true" pagination="true">
	<thead>
		<tr>
			<th field="sku">sku</th>
			<th field="asin">Asin</th>
			<th field="platform">账号</th>
			<th field="station">站点</th>
			<th field="qty">订单数量</th>
			<th field="unitprice">单价</th>
			<th field="currencycode">币种</th>
			<th field="deduction">折扣额</th>
			<th field="taxrate">税额</th>
			<th field="price">金额</th>
			<th field="priceTax">金额(含税)</th>
			<th field="priceExcludingTax">金额(不含税)</th>
			<th field="count">总数</th>
			<@shiro.hasPermission name="XSTJXQ_XQAFLRL">
			<th field="afRate" formatter="changeValue">af利润率</th>
			</@shiro.hasPermission>
			
			<@shiro.hasPermission name="XSTJXQ_XQSFLRL">
			<th field="sfRate" formatter="changeValue">sf利润率</th>
			</@shiro.hasPermission>
			
			<@shiro.hasPermission name="XSTJXQ_XQCOLRL">
			<th field="coRate" formatter="changeValue">co利润率</th>
			</@shiro.hasPermission>
			
			<th field="fromtime" formatter="getTime">开始时间</th>
			<th field="totime" formatter="getTime">结束时间</th>
		</tr>
	</thead>
</table>
</div>

<div id="rate-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="skuCancelLinkbutton">关闭</a>
</div>

</@FTL.admin>
