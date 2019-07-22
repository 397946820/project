<@FTL.admin id="orderApprove" title="订单审批列表" add_script_files=['admin/sys/orderApprove.js']>
<div style="height:100%;width:100%;">
	<div class="easyui-panel" style="padding:10px;padding-left: 15px;height:10%;">
		<form id="orderApproveSearchParam" >
		<table style="float:left; min-width:680px;">
			
			<tr style="min-width:1000px;">
				<td width="67"><label>平台:</label></td>
				<td>
				<select  class="easyui-combobox" name="platform" style="width:173px;">
					<option value="">--请选择--</option>
					<option value="amazon">亚马逊</option>
					<option value="light">官网</option>
					<option value="walmart">沃尔玛</option>
					<option value="ebay">EBay</option>
				</select>
				</td>
				<td width="67"><label>订单ID:</label></td>
				<td><input type="text" name="orderId"  class="easyui-textbox" />
				</td>
				<td></td>
			</tr>
			<tr style="min-width:1000px;">
				<td width="67"><label>申请类型:</label></td>
				<td>
				<select name="orderType" style="width:173px;">
					<option value="">--请选择--</option>
					<option value="0">退货退款单</option>
					<option value="1">补发单</option>
					<option value="2">线下单</option>
				</select>
				</td>
				<td><label>申请时间:</label></td>
				<td>
					<input type="text" id="filter_startCreateDate" name="startCreateDate" class="easyui-datebox" style="width: 115px;" /> 
					~ 
					<input type="text" id="filter_endCreateDate" name="endCreateDate" class="easyui-datebox" style="width: 115px;" />
				</td>
				<td>
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="orderApproveReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="orderApproveSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
<div style="height:90%;width:100%;">
	<table id="orderApproveList"  style="width:100%;height:100%" class="easyui-datagrid">        
</div>
</table>
</div>


<div id="saleOrderRefundToolbar" style="height: 60px">
	<div>
		<form id="saleOrderRefundBeforeForm">
			<table style="float:left; min-width:800px;">
				<tr style="min-width: 800px;">
					<td><label>平台:</label></td>
					<td>
						<input type="text" name="platform" id="platform_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>账号:</label></td>
					<td>
						<input type="text" name="account" id="account_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;"/>
					</td>
					<td id="Site1"><label>站点:</label></td>
					<td id="Site2">
						<input type="text" name="site" id="site_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;"/>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>订单号:</label></td>
					<td>
						<input type="text" name="orderId" id="orderId_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>货币单位:</label></td>
					<td>
						<input type="text" name="currencyCode" id="currencyCode_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div id="saleOrderRefund" class="easyui-dialog" title="退款退货单" closed="true" style="width:850px;height:400px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#saleOrderRefund').dialog('close');
					}
				}]">
		<table id="saleOrderRefundDatagrid" class="easyui-datagrid" style="width:100%;height:230px">
			
		</table>
	<div style="height: 90px">

			<table style="float:left; min-width:800px;">
				<tr style="min-width: 800px;">
					<td><label>退款原因:</label></td>
					<td>
						<select name="cause" id="cause_" class="easyui-combobox"  data-options="disabled:'true'" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
						</select>
					</td>
					<td><label>是否客户付款:</label></td>
					<td>
						<select name="isConsumerPaid" id="isConsumerPaid_"   data-options="disabled:'true'" class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false">
							<option value="0" >否</option>
							<option value="1" >是</option>
						</select>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>RMA单号:</label></td>
					<td>
						<input type="text" name="edaOrderNum" id="edaOrderNum_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td id="adjustmentPositive1"><label>调解费用:</label></td>
					<td id="adjustmentPositive2">
						<input type="text" name="adjustmentPositive" id="adjustmentPositive_" data-options="disabled:'true'" class="easyui-textbox" style="width: 150px;" />
					</td>
				</tr>
				<tr  id="approveMessage1" style="min-width: 800px;display:none;">
					<td><label>是否通过:</label></td>
					<td>
						<select name="isPass" id="isPass_"    class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false">
							<option value="">--请选择--</option>
							<option value="0" >否</option>
							<option value="1" >是</option>
						</select>
					</td>
					<td><label>理由:</label></td>
					<td>
						<input type="text" name="approveCase"  id="approveCase_" class="easyui-textbox" style="width: 150px;"/>
					</td>
				</tr>
			</table>
	
	</div>
</div>
<div id="saleOrderRefundToolbar1" style="height: 60px">
	<div>
		<form id="saleOrderRefundBeforeForm1">
			<table style="float:left; min-width:800px;">
				<tr style="min-width: 800px;">
					<td><label>平台:</label></td>
					<td>
						<input type="text" name="platform" id="platform__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>账号:</label></td>
					<td>
						<input type="text" name="account" id="account__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;"/>
					</td>
					<td id="Site1"><label>站点:</label></td>
					<td id="Site2">
						<input type="text" name="site" id="site__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;"/>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>订单号:</label></td>
					<td>
						<input type="text" name="orderId" id="orderId__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>货币单位:</label></td>
					<td>
						<input type="text" name="currencyCode" id="currencyCode__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
<div id="saleOrderReissue" class="easyui-dialog" title="补发单" closed="true" style="width:850px;height:400px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#saleOrderReissue').dialog('close');
					}
				}]">
		<table id="saleOrderReissueDatagrid" class="easyui-datagrid" style="width:100%;height:180px;overflow:auto;margin-left: 20px;">
			
		</table>
	<div style="height: 120px">

			<table style="float:left; min-width:560px;padding-left: 35px;padding: 10px;">
				<tr >
					<td><label>补发原因:</label></td>
					<td>
						<input type="text" name="cause" required="true" id="cause__" data-options="disabled:'true'" class="easyui-textbox" style="width: 130px;"/>
					</td>
					<td><label>发货仓库:</label></td>
					<td>
						<select name="deliveryWarehouse" id="deliveryWarehouse__" data-options="disabled:'true'" required="true" class="easyui-combobox" style="width: 130px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="US03">美东仓</option>
							<option value="US02">美西仓</option>
							<option value="DE02">DE02</option>
							<option value="UK02">UK02</option>
							<option value="FBA">FBA</option>
						</select>
					</td>
					<td id="isPass__1"><label>是否通过:</label></td>
					<td id="isPass__2">
						<select name="isPass" id="isPass__"    class="easyui-combobox" style="width: 130px;" panelHeight="auto" editable="false">
							<option value="">--请选择--</option>
							<option value="0" >否</option>
							<option value="1" >是</option>
						</select>
					</td>
					<td id="approveCase__1"><label>理由:</label></td>
					<td id="approveCase__2">
						
						<select name="approveCase" id="approveCase__"    class="easyui-combobox" style="width: 130px;" panelHeight="auto" editable="false">
							<option value="退货已收到" >退货已收到</option>
							<option value="产品问题" >产品问题</option>
							<option value="跟客户协商退款改差评" >跟客户协商退款改差评</option>
							<option value="漏发错发" >漏发错发</option>
							<option value="地址错误" >地址错误</option>
							<option value="物流问题" >物流问题</option>
						</select>
					</td>
				</tr>
				<tr >
					<td><label>是否补发到新地址:</label></td>
					<td>
						<select id="newAddress" required="true" class="easyui-combobox" data-options="disabled:'true'" style="width: 130px;" panelHeight="auto" editable="false">
							<option value="0" >否</option>
							<option value="1" >是</option>
						</select>
					</td>
				</tr>
				<tr style="min-width: 1000px;" id="newAddress1">
					<td><label>收货人:</label></td>
					<td>
						<input type="text" id="name_" required="true" data-options="disabled:'true'" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>邮编:</label></td>
					<td>
						<input type="text" id="postalCode_" required="true" data-options="disabled:'true'" class="easyui-numberbox" style="width: 130px;" />
					</td>
					<td><label>电话:</label></td>
					<td>
						<input type="text" id="phone_" class="easyui-numberbox" data-options="disabled:'true'" style="width: 130px;" />
					</td>
					<td><label>国家:</label></td>
					<td>
						<input type="text" id="country_" class="easyui-textbox" data-options="disabled:'true'" style="width: 130px;" />
					</td>
				</tr>
				<tr style="min-width: 1000px;" id="newAddress2">
					<td><label>城市:</label></td>
					<td>
						<input type="text" id="city_" class="easyui-textbox" data-options="disabled:'true'" style="width: 130px;" />
					</td>
					<td><label>州:</label></td>
					<td>
						<input type="text" id="provState_" class="easyui-textbox" data-options="disabled:'true'" style="width: 130px;" />
					</td>
					<td><label>街道1:</label></td>
					<td>
						<input type="text" id="addressLine1_" class="easyui-textbox" data-options="disabled:'true'" style="width: 130px;" />
					</td>
					<td><label>街道2:</label></td>
					<td>
						<input type="text" id="addressLine2_" class="easyui-textbox" data-options="disabled:'true'" style="width: 130px;" />
					</td>
				</tr>
			</table>

	</div>
</div>

<div id="saleOrderOfflineToolbar" style="height: 180px">
	<div>
		<form id="saleOrderOfflineForm">
			<table style="float:left; min-width:800px;">
				<tr style="min-width: 800px;">
					<td><label>平台:</label></td>
					<td>
						<select name="platform" id="platform_1" class="easyui-combobox" data-options="disabled:'true'" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="amazon">亚马逊</option>
							<option value="ebay">Ebay</option>
							<option value="light">官网</option>
							<option value="walmart">沃尔玛</option>
						</select>
					</td>
					<td><label>账号:</label></td>
					<td>
						<select name="account" id="account_1" class="easyui-combobox"  data-options="disabled:'true'" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
						</select>
					</td>
					<td><label>站点:</label></td>
					<td>
						<select name="site" id="site_1" class="easyui-combobox" data-options="disabled:'true'" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
						</select>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>收款交易号:</label></td>
					<td>
						<input type="text" name="cause" id="cause_1" data-options="disabled:'true'" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>实收金额:</label></td>
					<td>
						<input type="text" name="deliveryWarehouse" id="deliveryWarehouse_1" data-options="disabled:'true'" class="easyui-textbox" style="width: 150px;" />
					</td>	
					<td><label>收货人:</label></td>
					<td>
						<input type="text" id="name_1" required="true" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>			
				</tr>
				<tr style="min-width: 800px;">
					<td><label>邮编:</label></td>
					<td>
						<input type="text" id="postalCode_1" required="true" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>电话:</label></td>
					<td>
						<input type="text" id="phone_1" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>国家:</label></td>
					<td>
						<input type="text" id="country_1" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>城市:</label></td>
					<td>
						<input type="text" id="city_1" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>州:</label></td>
					<td>
						<input type="text" id="provState_1" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>街道1:</label></td>
					<td>
						<input type="text" id="addressLine1_1" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>街道2:</label></td>
					<td>
						<input type="text" id="addressLine2_1" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td id="isPass1"><label>是否通过:</label></td>
					<td id="isPass2">
						<select name="isPass" id="isPass_1"    class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false">
							<option value="">--请选择--</option>
							<option value="0" >否</option>
							<option value="1" >是</option>
						</select>
					</td>
					<td id="approveCase1"><label>理由:</label></td>
					<td id="approveCase2">
						<input type="text" name="approveCase"  id="approveCase_1" class="easyui-textbox" style="width: 150px;"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div id="saleOrderOfflineDialog" class="easyui-dialog" title="线下订单" closed="true" style="width:900px;height:450px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#saleOrderOfflineDialog').dialog('close');
					}
				}]">
		<table id="saleOrderOfflineDatagrid" class="easyui-datagrid" style="width:100%;height:100%">
		</table>
</div>
</@FTL.admin>
