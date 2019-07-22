<#import "common/saleOrder.ftl" as saleOrder />
<@FTL.admin id="walmartSaleOrder" title="沃尔玛销售订单"
add_script_files=['admin/salesStatistics/walmartSaleOrder.js','admin/salesStatistics/saleOrderRefund.js']>
<table id="walmartSaleOrderdatagrid" style="width: 100%; height: auto"></table>

<div id="walmartSaleOrderToolbar" style="height: 90px">
	<div>
		<form id="walmartSaleOrderSearchForm">
			<table style="float: left; min-width: 600px;">
				<tr style="min-width: 600px;">
					<td><label>订单号:</label></td>
					<td><input type="text" name="customerOrderId" class="easyui-textbox" style="width: 150px;" /></td>
					
					<td><label>运输方式:</label></td>
					<td><input type="text" name="methodCode" class="easyui-textbox" style="width: 150px;"/></td>
				</tr>
				
				<tr style="min-width: 600px;">
				
					<td><label>买家姓名:</label></td>
					<td><input type="text" name="name" class="easyui-textbox" style="width: 150px;" /></td>
					
					<td><label>国家:</label></td>
					<td><input type="text" name="country" class="easyui-textbox" style="width: 150px;" /></td>
					
					<td align="right">
						<a id="walmartSaleOrderQuery" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="walmartSaleOrderReset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
				<tr style="min-width:600px;">
					<td colspan="5">
						<ul class="con-button" style="text-align: center;float: left;margin:0;padding:0; cursor: pointer;" >
							<li style="background:#ccc; list-style: none;border:1px solid #95b8e7;float: left;height: 26px;line-height: 26px;padding: 0 5px;">
								<input type="radio" name="orderAllStatus" value="0" style="display: none;" checked="checked"/>
								<label for="total">所有<span></span></label>
							</li>
							<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
								<input type="radio" name="orderAllStatus" value="1" style="display: none;"/>
								<label for="noShipped">未发货<span></span></label>
							</li>
							<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
								<input type="radio" name="orderAllStatus" value="2" style="display: none;"/>
								<label for="shipped">已发货<span></span></label>
							</li>
							<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
								<input type="radio" name="orderAllStatus" value="3" style="display: none;"/>
								<label for="shipped">已取消<span></span></label>
							</li>
							<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
								<input type="radio" name="orderAllStatus" value="4" style="display: none;"/>
								<label for="shipped">已确认<span></span></label>
							</li>
						</ul>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<@saleOrder.saleOrderRefund></@>

<div id="walmartSaleOrderCancleOrder" class="easyui-dialog" title="取消订单" style="width:400px;height:150px;display:none;"data-options="
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	<div style="padding-top: 15px;padding-left: 5px;"><label>取消原因：</label>
		<select class="easyui-combobox" id="cancelOrderCase" style="width:300px;" editable="false" panelHeight="auto">
			<option value="CANCEL_BY_SUPPLIER">CANCEL_BY_SUPPLIER</option>
		</select>
	</div>
</div>

<div id="walmartSaleOrderDetialRefund" class="easyui-dialog" title="退款" closed="true" style="width:860px;height:385px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#walmartSaleOrderDetialRefund').window('close');
					}
				}]">
		<table id="walmartSaleOrderDetialRefundDatagrid" class="easyui-datagrid" style="width:100%;height:385px">
			
		</table>
</div>

<div id="walmartSaleOrderSaleTranNumber" class="easyui-dialog" title="上传跟踪号" closed="true" style="width:500px;height:150px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('walmartSaleOrderSaleTranNumber').window('close');
					}
				}]">
		<table id="walmartSaleOrderSaleTranNumberDatagrid" class="easyui-datagrid" style="width:100%;height:100%">
			
		</table>
</div>

<div id="walmartSaleOrderDetial" class="easyui-dialog" title="订单详情" closed="true" style="width:1000px;height:auto;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#walmartSaleOrderDetial').window('close');
					}
				}]">
		
		<div class="transadress" style="width:500px; height:150px;float:left;">
			<div style="padding:0 30px;float:left;"><p style="font-weight:300;">运输地址</p></div>
			<div style="padding:10px;float:left;line-height:20px;">
				<span id="name"></span><br/>
				<span id="address1"></span><br/>
				<span id="address1"></span><br/>
				<span id="city"></span><br/>
				<span id="state"></span><br/>
				<span id="postalCode"></span><br/>
				<span id="phone"></span><br/>
			</div>
		</div>
		
		<table id="walmartSaleOrderDetailDatagrid" class="easyui-datagrid" style="width:100%;height:auto">
			
		</table>
		<div class="total" style="padding:10px;padding-right:40px;float:right;font-weight:300;">合计:&nbsp<span id="t_total" style="">0</span></div>
</div>

</@FTL.admin>
