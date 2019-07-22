<#import "common/saleOrder.ftl" as saleOrder />
<@FTL.admin id="SalesStatisticsList" title="退款单"
add_script_files=['admin/salesStatistics/returnOrder.js','admin/salesStatistics/saleOrderRefund.js']>

<@saleOrder.saleOrderRefund></@>

<div id="returnOrderToolbar" style="height: 130px">
	<div>
		<form id="returnOrderForm">
			<table style="float: left; min-width: 800px;">
				<tr style="min-width: 800px;">
					<td><label>平台:</label></td>
					<td>
						<select name="platform" id="platform" class="easyui-combobox" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="amazon">亚马逊</option>
							<option value="ebay">Ebay</option>
							<option value="light">官网</option>
							<option value="walmart">沃尔玛</option>
						</select>
					</td>
					<td><label>账号:</label></td>
					<td>
						<select name="account" id="account" class="easyui-combobox" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
						</select>
					</td>
					<td><label>站点:</label></td>
					<td>
						<select name="site" id="site" class="easyui-combobox" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
						</select>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>订单号:</label></td>
					<td>
						<input type="text" name="orderId" id="orderId" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>创建人:</label></td>
					<td>
						<input type="text" name="createName" id="createName" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>订单类型:</label></td>
					<td>
						<select name="orderType" id="orderType" class="easyui-combobox" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="0">退货退款单</option>
							<option value="1">补发单</option>
							<option value="2">线下单</option>
						</select>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>原因:</label></td>
					<td>
						<input type="text" name="cause" id="cause" class="easyui-textbox" style="width: 150px;" />
					</td>
					<!-- <td><label>是否确认退款收货:</label></td>
					<td>
						<select name="isReceiving" class="easyui-combobox" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
					<td><label>是否确认补发:</label></td>
					<td>
						<select name="isReissue" class="easyui-combobox" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td> -->
					<td></td>
					<td>
						<a id="returnOrderQuery" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="returnOrderReset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
			<br clear="all"/>
		</form>
	</div>
	<hr/>
	<div>
		<a id="returnOrderExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出</a>
		<a id="offlineOrderAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加线下订单</a>
	</div>
</div>

<div id="saleOrderOfflineToolbar" style="height: 180px">
	<div>
		<form id="saleOrderOfflineForm">
			<input type="hidden" name="id" id="id_1">
			<table style="float:left; min-width:800px;">
				<tr style="min-width: 800px;">
					<td><label>平台:</label></td>
					<td>
						<select name="platform" id="platform_1" class="easyui-combobox" required="true" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="amazon">亚马逊</option>
							<option value="ebay">Ebay</option>
							<option value="light">官网</option>
							<option value="walmart">沃尔玛</option>
						</select>
					</td>
					<td><label>账号:</label></td>
					<td>
						<select name="account" id="account_1" class="easyui-combobox" required="true" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
						</select>
					</td>
					<td><label>站点:</label></td>
					<td>
						<select name="site" id="site_1" class="easyui-combobox" required="true" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
						</select>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>收款交易号:</label></td>
					<td>
						<input type="text" name="cause" id="cause_1"  class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>实收金额:</label></td>
					<td>
						<input type="text" name="deliveryWarehouse" id="deliveryWarehouse_1" required="true" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>币种:</label></td>
					<td>
						<select name="currencyCode" id="currencyCode_1" class="easyui-combobox" required="true" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="USD">USD</option>
							<option value="EUR">EUR</option>
							<option value="GBP">GBP</option>
							<option value="JPY">JPY</option>
							<option value="CAD">CAD</option>
							<option value="AUD">AUD</option>
						</select>
					</td>					
				</tr>
				<tr style="min-width: 800px;">
					<td><label>收货人:</label></td>
					<td>
						<input type="text" name="name" id="name_1" required="true" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>邮编:</label></td>
					<td>
						<input type="text" name="postalCode" id="postalCode_1" required="true" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>电话:</label></td>
					<td>
						<input type="text" name="phone" id="phone_1" class="easyui-textbox" style="width: 150px;" />
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>国家:</label></td>
					<td>
						 <!--<input type="text" name="country" id="country_1" required="true" class="easyui-textbox" style="width: 150px;" />-->
						<select  id="country_1" required="true" class="easyui-combobox" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" editable="false">
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
					<td><label>城市:</label></td>
					<td>
						<input type="text" name="city" id="city_1" required="true" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>州:</label></td>
					<td>
						<input type="text" name="provState" id="provState_1"  class="easyui-textbox" style="width: 150px;" />
					</td>
				</tr>
				<tr>
					<td><label>街道1:</label></td>
					<td>
						<input type="text" name="addressLine1" id="addressLine1_1" required="true" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>街道2:</label></td>
					<td>
						<input type="text" name="addressLine2" id="addressLine2_1" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td id="approveDescription1_"><label>审核未通过理由:</label></td>
					<td id="approveDescription2_">
						<input type="text" name="approveDescription" id="approveDescription_1" class="easyui-textbox" style="width: 150px;" />
					</td>
				</tr>
			</table>
			<br clear="all"/>
		</form>
	</div>
	<hr/>
	<div>
		<a id="offlineOrderAdd" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a id="offlineOrderRemove" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		<a id="offlineOrderAddressImport" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">发货地址导入</a>
	</div>
</div>

<div id="saleOrderOfflineDialog" class="easyui-dialog" title="线下订单" closed="true" style="width:900px;height:450px;" 
		data-options="modal:true">
		<table id="saleOrderOfflineDatagrid" class="easyui-datagrid" style="width:100%;height:100%">
		</table>
</div>

<div id="offlineOrderAddressImportDialog" class="easyui-dialog" title="发货地址导入" closed="true" style="width:300px;height:200px;" 
		data-options="resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false">
		<div style="margin-bottom:20px;width:80%;padding-left: 20px;margin-top: 40px;">
	    	<div>
				<span style="height: 24px;line-height: 24px;padding-right: 10px;">订单号:</span>
				<input type="text" id="orderIdImport" class="easyui-textbox" style="width: 150px;" />
			</div>
		</div>
</div>

<table id="returnOrderDatagrid" style="width: 100%; height: 100%"></table>

</@FTL.admin>
