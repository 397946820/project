<#macro saleOrderRefund>
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

<div id="saleOrderRefund" class="easyui-dialog" title="退款退货单" closed="true" style="width:900px;height:400px;" 
		data-options="modal:true">
		<table id="saleOrderRefundDatagrid" class="easyui-datagrid" style="width:100%;height:230px">
			
		</table>
	<div style="height: 90px">
		<form id="saleOrderRefundBackForm">
			<input type="text" hidden="true" class="easyui-textbox" name="id" id="id_">
			<table style="float:left; min-width:800px;">
				<tr style="min-width: 800px;">
					<td><label>退款原因:</label></td>
					<td>
						<select name="cause" id="cause_" class="easyui-combobox" required="true" style="width: 150px;" panelMaxHeight="100" panelHeight="auto">
							<option value="退货退款">退货退款</option>
							<option value="不退货退款">不退货退款</option>
							<option value="未发货退款">未发货退款</option>
						</select>
					</td>
					<td><label>是否退还运费:</label></td>
					<td>
						<select name="isConsumerPaid" id="isConsumerPaid_" required="true" class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false">
							<option value="0" >否</option>
							<option value="1" >是</option>
						</select>
						<span style="color:red;padding-left: 10px;" id="shipCost">0</span>
					</td>
					<td id="trackingNum1"><label>退款跟踪号:</label></td>
					<td id="trackingNum2">
						<input type="text" name="trackingNum" id="trackingNum_" class="easyui-textbox" style="width: 150px;" />
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>RMA单号:</label></td>
					<td>
						<input type="text" name="edaOrderNum" id="edaOrderNum_" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td id="adjustmentPositive1"><label>调解费用:</label></td>
					<td id="adjustmentPositive2">
						<input type="text" name="adjustmentPositive" id="adjustmentPositive_" required="true" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td id="approveDescription1"><label>审核未通过理由:</label></td>
					<td id="approveDescription2">
						<input type="text" name="approveDescription" id="approveDescription_" required="true" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td id="receiptNo1"><label>入库单号:</label></td>
					<td id="receiptNo2">
						<input type="text" name="receiptNo" id="receiptNo_" class="easyui-textbox" style="width: 150px;" />
					</td>
				</tr>
				<tr>
					<td>
						退款支付方式:
					</td>
					<td>
						<select name="payMethod" id="payMethod" class="easyui-combobox" style="width: 180px;" panelMaxHeight="100" panelHeight="auto">
							<option value="Bank_Transfer">Bank_Transfer</option>
							<option value="paypal_express">paypal_express</option>
							<option value="iways_paypalplus_payment">iways_paypalplus_payment</option>
							<option value="paypal_direct">paypal_direct</option>
						</select>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td id="receivingTime1"><label>收货日期:</label></td>
					<td id="receivingTime2">
						<input type="text" name="receivingTime" id="receivingTime_" class="easyui-datetimebox" style="width: 150px;" editable="false" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div id="saleOrderReissueToolbar" style="height: 60px">
	<div>
		<form id="saleOrderReissueBeforeForm">
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
					<td id="Site3"><label>站点:</label></td>
					<td id="Site4">
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

<div id="saleOrderReissue" class="easyui-dialog" title="补发单" closed="true" style="width:900px;height:400px; " 
		data-options="modal:true">
		<table id="saleOrderReissueDatagrid" class="easyui-datagrid" style="width:100%;height:180px;overflow:auto;margin-left: 20px;">
			
		</table>
	<div style="height: 120px">
		<form id="saleOrderReissueBackForm">
			<input type="text" hidden="true" class="easyui-textbox"  name="id" id="id__">
			<table style="float:left; min-width:560px;padding-left: 35px;padding: 10px;">
				<tr >
					<td><label>补发原因:</label></td>
					<td>
						<input type="text" name="cause" required="true" id="cause__" class="easyui-textbox" style="width: 130px;"/>
					</td>
					<td><label>发货仓库:</label></td>
					<td>
						<select name="deliveryWarehouse" id="deliveryWarehouse__" required="true" class="easyui-combobox" style="width: 130px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="US03">美东仓</option>
							<option value="US02">美西仓</option>
							<option value="DE02">DE02</option>
							<option value="UK02">UK02</option>
							<option value="FBA">FBA</option>
						</select>
					</td>
					<td id="approveDescription3"><label>审核未通过理由:</label></td>
					<td id="approveDescription4">
						<input type="text" name="approveDescription" id="approveDescription__" required="true" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td id="trackingNum3"><label>补发跟踪号:</label></td>
					<td id="trackingNum4">
						<input type="text" name="trackingNum" id="trackingNum__" class="easyui-textbox" style="width: 130px;" />
					</td>
				</tr>
				<tr >
					<td><label>是否补发到新地址:</label></td>
					<td>
						<select id="newAddress" required="true" class="easyui-combobox" style="width: 130px;" panelHeight="auto" editable="false">
							<option value="0" >否</option>
							<option value="1" >是</option>
						</select>
					</td>
				</tr>
				<tr style="min-width: 1000px;" id="newAddress1">
					<td><label>收货人:</label></td>
					<td>
						<input type="text" id="name_" required="true" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>邮编:</label></td>
					<td>
						<input type="text" id="postalCode_" required="true" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>电话:</label></td>
					<td>
						<input type="text" id="phone_" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>国家:</label></td>
					<td>
						<!--<input type="text" id="country_" class="easyui-textbox" style="width: 130px;" />-->
						<select  id="country_" required="true" class="easyui-combobox" style="width: 130px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="US">美国</option>
							<option value="DE">德国</option>
							<option value="UK">英国</option>
							<option value="FR">法国</option>
							<option value="IT">意大利</option>
							<option value="ES">西班牙</option>
							<option value="CA">加拿大</option>
							<option value="JP">日本</option>
							<option value="AT">奥地利</option>
							<option value="IE">爱尔兰</option>
							<option value="BE">比利时</option>
														<option value="BG">保加利亚</option>
							<option value="PL">波兰</option>
							<option value="DK">丹麦</option>
							<option value="FI">芬兰</option>
							<option value="NL">荷兰</option>
							<option value="CZ">捷克</option>
							<option value="HR">克罗地亚</option>
							<option value="LV">拉脱维亚</option>
							<option value="LT">立陶宛</option>
							<option value="LU">卢森堡</option>
							<option value="RO">罗马尼亚</option>
							<option value="MT">马耳他</option>
							<option value="PT">葡萄牙</option>
							<option value="SE">瑞典</option>
							<option value="CY">塞浦路斯</option>
							<option value="SK">斯洛伐克</option>
							<option value="SI">斯洛文尼亚</option>
							<option value="GR">希腊</option>
							<option value="HU">匈牙利</option>
							<option value="IS">冰岛</option>
							<option value="MC">摩纳哥</option>
							<option value="AD">安道尔</option>
							<option value="NO">挪威</option>
							<option value="CH">瑞士</option>
							<option value="LI">列支敦士登</option>
							<option value="AU">澳大利亚</option>
							<option value="IN">印度</option>
							<option value="SA">沙特阿拉伯</option>
							<option value="SG">新加坡</option>
							<option value="ZA">南非</option>
							<option value="KP">韩国</option>
							<option value="TW">中国台湾</option>
							<option value="TR">土耳其</option>
							<option value="IL">以色列</option>
							<option value="NZ">新西兰</option>
						</select>
					</td>
				</tr>
				<tr style="min-width: 1000px;" id="newAddress2">
					<td><label>城市:</label></td>
					<td>
						<input type="text" id="city_" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>州:</label></td>
					<td>
						<input type="text" id="provState_" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>街道1:</label></td>
					<td>
						<input type="text" id="addressLine1_" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>街道2:</label></td>
					<td>
						<input type="text" id="addressLine2_" class="easyui-textbox" style="width: 130px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>





<div id="saleOrderBackGoods" class="easyui-dialog" title="退货申请单" closed="true" style="width:1200px;height:450px; " 
		data-options="modal:true">
		<table id="saleOrderBackGoodsDatagrid" class="easyui-datagrid" style="width:100%;height:180px;overflow:auto;margin-left: 20px;">
		</table>
		<table id="applyBackGoodsDatagrid" class="easyui-datagrid" style="width:100%;height:120px;overflow:auto;margin-left: 20px;">
		</table>
	<div style="height: 30px">
		<form id="saleOrderBackGoodsBackForm">
 			<table style="float:left; min-width:560px;padding-left: 35px;padding: 10px;">
				<tr >
					<td><label>退货原因:</label></td>
					<td>
						<!--<input type="text" name="cause" required="true" id="cause__back" class="easyui-textbox" style="width: 130px;"/>-->
						<input type="text" name="cause" required="true" id="cause__back" class="easyui-combobox" style="width: 130px;"
						data-options="editable:false,panelHeight:'auto',
						 valueField: 'PRODUCTCASETYPE',textField: 'PRODUCTCASETYPENAME',panelMaxHeight: '120'"/>
					</td>
					<!--
					<td><label>sku:</label></td>
					<td>
						<input type="text" name="sku" required="true" id="sku__" class="easyui-textbox" data-options="prompt:'请选择SKU'" style="width: 130px;"/>
					</td>
					<td><label>sku数量:</label></td>
					<td>
						<input type="text" name="skuTotal" required="true" id="skuTotal__" class="easyui-numberbox" style="width: 130px;"/>
					</td>
					-->
					<td><label>运单号:</label></td>
					<td>
						<input type="text" name="trackingNum"   id="trackingNum_return__" class="easyui-textbox" style="width: 130px;"/>
					</td>
				</tr>
				 
			</table>
		</form>
	</div>
</div>
<div id="saleOrderBackGoodsToolbar" style="height: 40px">
	 <div>
		<form id="saleOrderBackGoodsBeforeForm">
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
					<td id="Site3"><label>站点:</label></td>
					<td id="Site4">
						<input type="text" name="site" id="site__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;"/>
					</td>
				 
					<td><label>订单号:</label></td>
					<td>
						<input type="text" name="orderId" id="orderId__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>RMA:</label></td>
					<td>
						<input type="text" name="rmaSeq" id="rmaSeq__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
</#macro>