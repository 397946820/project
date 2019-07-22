<@FTL.admin id="OrderDetails" title="销售订单列表"
add_script_files=['admin/salesStatistics/orderDetails.js']
add_style_files=['orderDetails.css']>
<div data-options="region:'center',border:false">
	<table id="orderDetailsDatagrid"
		data-options="
           url:'',
           fitColumns:true,
           columns: [
            [
                {field: 'updatedat', title: '时间',formatter:getTime},
                {field: 'details', title: '订单详情'},
                {field: 'shipping', title: '运输方式'},
                {field: 'status', title: '订单状态'}
            ]
        ],
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        striped : true, 
        border:false,
        showFooter: true,
        fit:true,
        onDblClickRow : doDblClickRow,
        toolbar:'#orderDetailsToolbar'">
	</table>
</div>
<div id="orderDetailsToolbar" style="padding-left: 15px;">
	<div>
		<form id="searchFrom">
		<table>
			<tr>
				<td style="text-align:right; min-width: 235px;">
					Sku: <input type="text" id="sku" class="easyui-textbox" style="width: 170px;" />
				</td>
				<td style="text-align:right; min-width: 235px;">
					Asin: <input type="text" id="asin" class="easyui-textbox" style="width: 170px;"/>
				</td>
			</tr>
			<tr>
				<td style="text-align:right; min-width: 235px;">
					OrderId：<input type="text" id="orderId" class="easyui-textbox" style="width: 170px;"/>
				</td style="text-align:right; min-width: 235px;">
				
				<td style="text-align:right; min-width: 271px;">
				Sales Channel：
					<select id="station" class="easyui-combobox" name="platform" style="width: 170px;" editable="false">
						<option value="">-- 请选择 --</option>
						<option value="Amazon.com">Amazon.com</option>
						<option value="Amazon.de">Amazon.de</option>
						<option value="Amazon.fr">Amazon.fr</option>
						<option value="Amazon.co.uk">Amazon.co.uk</option>
						<option value="Amazon.es">Amazon.es</option>
						<option value="Amazon.it">Amazon.it</option>
						<option value="Amazon.co.jp">Amazon.co.jp</option>
						<option value="Amazon.ca">Amazon.ca</option>
					</select>
				</td>
				<td align="right" style=" min-width: 252px;padding-right: 8px;">时间：
					<input type="text" id="starttime"  style="width:90px;" class="easyui-datebox"  editable="false"/> - 
					<input type="text" id="endtime" style="width:90px;" class="easyui-datebox"  editable="false"/>
				</td>
				<td align="right" style="text-align:right; min-width:150px; padding-right: 8px;">
					<a id="query" href="javascript:void(0)"  class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
					<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
				</td>
			</tr>
		</table>
		</form>
	</div>
</div>
<div id="orderDetailsDialog" class="easyui-dialog" title="订单详情" closed="true"
	style="width: 100%; height: 100%; padding: 10px;" buttons="#rate-buttons">
	<div>
			<header class="newPage-header">
				<h1>Order Details</h1>
				<a href="javascript:void(0)">learn more</a>
				<span>|</span>
				<a href="javascript:void(0)">Video tutorials</a>
				<div id="orderId_">
				</div>
			</header>
			<div class="section-body after">
					<table class="section-left left" border="0" cellspacing="0" cellpadding="10">
						<tbody id="details">
						
						</tbody>
					</table>
				
				<div class="section-right-first left">
					<div class="section-right-second ">
						<table class="section-right" border="0" cellspacing="0" cellpadding="10">
							<tbody id="prices">
							
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="footer">
				<table border="0" cellspacing="0" cellpadding="10" align="right">
					<tr class="footerth">
						<td style="width: 7%;">Status</td>
						<td class="text-right" style="width: 10%;">Quantity ordered</td>
						<td class="text-right"style="width:10%;">Quantity shiped</td>
						<td style="width: 35%;">Product Details</td>
						<td class="text-right"style="width: 7%;">Unit price</td>
						<td style="width: 25%;">Totals</td>
					</tr>
					<tr id="details_">
						
					</tr>
				</table>
			</div>
		</div>
</div>

</@FTL.admin>
