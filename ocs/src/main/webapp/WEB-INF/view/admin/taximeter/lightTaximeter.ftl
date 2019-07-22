<@FTL.admin id="lightTaximeterList" title="官网计价器"
add_script_files=['admin/taximeter/lightTaximeter.js','admin/taximeter/common.js','admin/taximeter/datagrid-cellediting.js']>
<table id="lightTaximeterDataGrid" class="easyui-datagrid" style="width: 100%; height: 100%"
	url=""
	toolbar="#lightTaximeterToolbar" pagination="true" idField="id"
	rownumbers="true" fitColumns="true" pageSize="50" fit="true"
	singleSelect="true" border="false" striped="true"  autoEditing="true"
	extEditing="true" singleEditing="true">
	<thead>
		<@shiro.hasPermission name="GWJJQ_SKU">
		<th field="sku" sortable="true">sku</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_PT">
		<th field="platform" sortable="true" formatter="getPlatform">平台</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_GJ">
		<th field="country" sortable="true" formatter="getCountry">国家</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_YSFS">
		<th field="shippingType" sortable="true" formatter="getShippingType">运输方式</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_CGCB">
		<th field="purchaseCost" sortable="true">采购成本</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_CIF">
		<th field="cif" sortable="true">CIF</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_CZF">
		<th field="storageCharges" sortable="true">仓租费</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_ZF">
		<th field="sundryCharges" sortable="true">杂费</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_RKF">
		<th field="wec" sortable="true">入库费</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_CKF">
		<th field="warehouseOutCharge" sortable="true">出库费</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_HC">
		<th field="consumable" sortable="true">耗材</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_RG">
		<th field="labour" sortable="true">人工</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_DDF">
		<th field="orderCost" sortable="true">订单费</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_BZF">
		<th field="packingExpense" sortable="true">包装费</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_BDPSF">
		<th field="localDeliveryFee" sortable="true">本地配送费</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_ZZCB">
		<th field="finalCost" sortable="true">最终成本</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_TJFL">
		<th field="referralRate" sortable="true">平台费率</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_PAYPALFL">
		<th field="paypalFee" sortable="true">PayPal费率</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_SHL">
		<th field="unfulliableRate" sortable="true">不可用率</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_BFL">
		<th field="replacementRate" sortable="true">补发率</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_LRL">
		<th field="profitRate" sortable="true">利润率</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_SL">
		<th field="vat" sortable="true">税率</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_GGFL">
		<th field="advertisingRate" sortable="true">广告费率</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_LRXS">
		<th field="profitRateAction" sortable="true" editor="{type:'numberbox',options:{precision:4,min:0}}">利润系数</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_JYSJ">
		<th field="finalPrice" sortable="true" editor="{type:'numberbox',options:{precision:4,min:0}}">建议售价</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_JYMS">
		<th field="transactionMode" sortable="true" formatter="getTransactionMode">交易模式</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_SFYZYFB">
		<th field="isCostOf" sortable="true" formatter="getIsCostOf">是否用占用费比</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_SFYCZF">
		<th field="isStorageCharges" sortable="true" formatter="getIsCostOf">是否用仓租费</th>
		</@shiro.hasPermission>
		
		<@shiro.hasPermission name="GWJJQ_GXSJ">
		<th field="updatedAt" sortable="true" formatter="getTime">更新日期</th>
		</@shiro.hasPermission>
	</thead>
</table>
<div>

<div id="EXW-FOBtoolbar" style="height: 110px">
	<div>
		<form id="EXW-FOB">
			<table style="float:left; min-width:800px;">
				<tr style="min-width: 800px;">
					<td>清关手续费</td>
					<td>
						<input type="text" name="clearPrice" required="true" 
							class="easyui-numberbox" style="width: 120px;" data-options="min:0,precision:4" />
					</td>
					<td>清关货币</td>
					<td>
						<select name="clearPriceCurrency" required="true" panelHeight="auto" class="easyui-combobox" style="width: 120px;" editable="false">
							<option value="CNY">CNY</option>
							<option value="USD">USD</option>
							<option value="GBP">GBP</option>
							<option value="EUR">EUR</option>
							<option value="JPY">JPY</option>
							<option value="CAD">CAD</option>
						</select>
					</td>
					<td>物流费</td>
					<td><input type="text" name="logisticFeePrice" required="true" class="easyui-numberbox" style="width: 120px;" data-options="min:0,precision:4" /></td>
					<td>物流货币</td>
					<td>
						<select name="logisticFeeCurrency" required="true" panelHeight="auto" class="easyui-combobox" style="width: 120px;" editable="false">
							<option value="CNY">CNY</option>
							<option value="USD">USD</option>
							<option value="GBP">GBP</option>
							<option value="EUR">EUR</option>
							<option value="JPY">JPY</option>
							<option value="CAD">CAD</option>
						</select>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td>是否退税</td>
					<td>
						<select name="refundDuty" class="easyui-combobox" required="true" panelHeight="auto" style="width: 120px;" editable="false">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
					<td>报价货币</td>
					<td>
						<select name="quoteCurrency" class="easyui-combobox" required="true" panelHeight="auto" style="width: 120px;" editable="false">
							<option value="CNY">CNY</option>
							<option value="USD">USD</option>
							<option value="GBP">GBP</option>
							<option value="EUR">EUR</option>
							<option value="JPY">JPY</option>
							<option value="CAD">CAD</option>
						</select>
					</td>
					<td>目的国</td>
					<td>
						<select name="country" class="easyui-combobox" required="true" panelHeight="auto" style="width: 120px;" editable="false">
							<option value="US">US</option>
							<option value="UK">UK</option>
							<option value="DE">DE</option>
						</select>
					</td>
					<td colspan="2" align="center" id="resultLinkbutton_">
						<a id="resultLinkbutton" href="javascript:void(0)" class="easyui-linkbutton c8"  plain="true" style="width:50%;">推算</a>
					</td>
				</tr>
			</table>
			<div>
				<a id="setLinkbutton" href="javascript:void(0)" class="easyui-linkbutton c8" plain="true" style="padding:0 10px;">设置</a>
				<a href="javascript:void(0)" class="easyui-linkbutton c8"  plain="true" style="padding:0 10px;">结果</a>
			</div>
			<div id="setButtons">
				<a id="addEXW-FOBLinkbutton" href="javascript:void(0)" class="easyui-linkbutton " iconCls="icon-add" plain="true">添加</a>
				<a id="removeEXW-FOBLinkbutton" href="javascript:void(0)" class="easyui-linkbutton " iconCls="icon-remove" plain="true">删除</a>
			</div>
			<div id="resultButtons" style="display:none;" >
				<a id="deriveEXW-FOBLinkbutton"  href="javascript:void(0)" class="easyui-linkbutton " iconCls="icon-download" plain="true">导出</a>
			</div>
		</form>
	</div>
</div>


<div id="EXW-FOBDialod" class="easyui-dialog" title="EXW-FOB推算器" style="width:850px;height:400px;" closed="true" modal="true">
		<table id="EXW-FOBForm" class="easyui-datagrid" style="width:100%;height:325px;padding:10px;overflow:auto;">
			
		</table>
</div>


<div class="easyui-dialog" id="lightTaximeterDialog" closed="true" 
	style="width:800px;height:450px;" title="价格推算器" modal="true">
	 <form action="" method="post" style="height:auto;padding:10px;" id="TaximeterDialogToolbar">
		  <table border="0" cellspacing="0" cellpadding="5" style="width:550px;float:left;">
		   <tr>
		    <td align="right">平台 ：</td>
		    <td>
			     <select id="platform_"  class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false"  required="required">
			     	<@shiro.hasPermission name="GWJJQ_SJGLX_PT_EBAY">
					<option value="ebay">Ebay</option>
					</@shiro.hasPermission>
					
					<@shiro.hasPermission name="GWJJQ_SJGLX_PT_GW">
					<option value="light">官网</option>
					</@shiro.hasPermission>
			     </select>
		    </td>
		    <td align="right">国家 ：</td>
			    <td>
				     <select id="country_"  class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false"  required="required">
				     </select>
			    </td>
		   </tr>
		   <tr>
			    <td align="right">是否用占用费比：</td>
			    <td>
				     <select id="isCostOf_" class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false"  required="required">
				      <option value="0">否</option>
				      <option value="1">是</option>
				     </select>
			    </td>
		    	<td align="right">是否用仓租费：</td>
			    <td>
				     <select id="isStorageCharges_" class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false"  required="required">
				      	<option value="0">否</option>
				        <option value="1">是</option>
				     </select>
			    </td>
		   </tr>
		   <tr>
		    <td align="right">交易方式：</td>
	       <td>
		        <select id="transactionMode_" style="width:150px;" class="easyui-combobox"  panelHeight="auto" editable="false"  required="required"> 
		         <option value="0">线下</option>
				 <option value="1">线上</option>
		        </select>
	       </td>
		    <td align="right">系数：</td>
		    <td><input id="profitRateAction" class="easyui-numberbox" style="width:150px;" data-options="precision:4"/></td>
		   </tr>
		   <tr>
		    <td align="right">价格：</td>
		    <td><input id="finalPrice" class="easyui-numberbox" style="width:150px;" data-options="precision:4"/></td>
		   </tr>
	  </table>
	  <div style="float:left; margin-left:20px;">
		   <div  class="easyui-linkbutton c8" style="padding:5px 10px;margin-top:10px;" id="queryProfitRateAction">查询系数</div><br/>
		   <div  class="easyui-linkbutton c8" style="padding:5px 10px; margin-top:10px;" id="queryFinalPrice">查询售价</div>
	  </div>
	  <br clear="all"/>
	  <div>
	  	<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-add" plain="true" onclick="append()">添加</a>
	  	<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-remove" plain="true" onclick="removeit()">删除</a>
	  </div>
	 </form>
 
 <table  id="TaximeterDialogTable"  class="easyui-datagrid" style="width:100%;height:100%;overflow: auto;"
 	data-options="
					iconCls: 'icon-edit',
					singleSelect: false,
					toolbar: '#TaximeterDialogToolbar',
					onClickCell: onClickCell
				">
   <thead>
   		<th data-options="field:'entityId',width:80,checkbox:'true'">
		<th	align="center"
			data-options="field:'sku',width:187.5,
						editor:{
							type:'combobox',
							options:{
								panelHeight: 'auto',
							    panelMaxHeight: '100',
							    url: '${FTL.X.global_domain}/lightTaximeter/findAllSku',
							    valueField:'value',
								textField:'name',
								onChange : changeSku,
							    required:true	
							}
						}">SKU</th>
		<th	align="center"
			data-options="field:'shippingType',width:187.5,
						editor:{
							type:'combobox',
							options:{
								panelHeight: 'auto',
							    panelMaxHeight: '100',
							    editable:false,
								valueField:'shippingType',
								textField:'shippingTypeName',
								url:'${FTL.X.global_domain}/assets/app/json/shippingType.json',
								onChange : changeShippingType,
								required:true
							}
						}">运输方式</th>
	   	<th align="center" data-options="field:'qty',title:'捆绑数量',width:187.5,editor:{type:'numberbox',options:{required:true,precision:0,min:0}}"></th>
	   	<th align="center" data-options="field:'finalPrice',title:'单个建议售价',width:187.5,editor:{type:'numberbox',options:{precision:4}}"></th>
   </thead>
 </table>
 
</div>

<div id="lightTaximeterToolbar">
	<div style="height: 60px;">
		<form>
			<table style="float: left; min-width: 1000px;">
				<tr style="min-width: 1000px;">
					<td><label>平台：</label></td>
					<td> 
						<select id="platform" class="easyui-combobox" style="width: 150px;" editable="false">
							<@shiro.hasPermission name="GWJJQ_SJGLX_PT_EBAY">
							<option value="ebay">Ebay</option>
							</@shiro.hasPermission>
							
							<@shiro.hasPermission name="GWJJQ_SJGLX_PT_GW">
							<option value="light">官网</option>
							</@shiro.hasPermission>
						</select>
					</td>
					<td><label>国家：</label></td>
					<td> 
						<select id="country" class="easyui-combobox" style="width: 150px;" editable="false">
							
						</select>
					</td>
					<td><label>是否用占用费比：</label></td>
					<td> 
						<select id="isCostOf" class="easyui-combobox" style="width: 150px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
					<td><label>运输方式：</label></td>
					<td> 
						<select id="shippingType" class="easyui-combobox" style="width: 150px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="af">BY AF</option>
							<option value="sf">BY SF</option>
							<option value="co">BY CO</option>
						</select>
					</td>
				</tr>
				<tr style="min-width: 1000px;">
					<td><label>sku：</label></td>
					<td>
						<input type="text" id="sku" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>是否用仓租费：</label></td>
					<td> 
						<select id="isStorageCharges" class="easyui-combobox" style="width: 150px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
					<td><label>交易模式：</label></td>
					<td> 
						<select id="transactionMode" class="easyui-combobox" style="width: 150px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="0">线下</option>
							<option value="1">线上</option>
						</select>
					</td>
					<td></td>
					<td>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<hr/>
	<div>
		<select id="template" class="easyui-combobox" style="width: 180px;" editable="false">
			<option value="">导出数据</option>
			<option value="template">下载价格推算模板</option>
			<option value="template_variant">下载价格推算捆绑销售模板</option>
			<option value="count">下载大客户价格推算模板</option>
			<!-- <option value="variant">下载保本销量捆绑销售模板</option> -->
		</select>
		<a id="lightTaximeterExportAllLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出</a>
		<a id="lePricePlanReckonLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true">批量价格推算器</a>
		<a id="lePricePlanLinkVariantbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true">价格推算器</a>
		<a id="lePricePlanLinkCustomerbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true">大客户价格推算器</a>
		<a id="lightTaximeterRecoverLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true">恢复利润系数</a>
		<a id="lightTaximeterExwfobLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true">EXW&FOB推算器</a>
		<@shiro.hasAnyRoles name="ADMINISTRATOR,CEO,SWZG,CWZG">
		<a id="lightTaximeterRefreshLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新价格计划</a>
		<a id="lePricePlanTestLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain="true">测试</a>
		</@shiro.hasAnyRoles>
	</div>
	
	<div id="lePricePlanTest" class="easyui-dialog" title="测试" closed="true"
	style="width: 100%; height: 100%; padding: 10px;" modal="true">
		<div style="height: 8%;">
			<form id="lePricePlanTestForm">
				<table style="float: left; min-width: 1000px;">
				<tr style="min-width: 1000px;">
			    <td>平台 ：</td>
			    <td>
				     <select id="platform__"  class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false"  required="required">
				     	<@shiro.hasPermission name="GWJJQ_SJGLX_PT_EBAY">
						<option value="ebay">Ebay</option>
						</@shiro.hasPermission>
						
						<@shiro.hasPermission name="GWJJQ_SJGLX_PT_GW">
						<option value="light">官网</option>
						</@shiro.hasPermission>
				     </select>
			    </td>
			    <td>国家 ：</td>
			    <td>
				     <select id="country__"  class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false"  required="required">
				     </select>
			    </td>
			    <td>SKU：</td>
			    <td>
			    	<select id="sku__" class="easyui-combobox" style="width: 150px;" panelMaxHeight="100" panelHeight="auto" required="required">
				     
				    </select>
			    </td>
			    <td>数量：</td>
			    <td>
			    	<input id="qty__" class="easyui-numberbox" style="width: 150px;" required="required" data-options="precision:0,min:1"/>
			    </td>
			    </tr>
			    <tr style="min-width: 1000px;">
			    <td>交易方式：</td>
			    <td>
				     <select id="transactionMode__" class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false"  required="required">
				      <option value="0">线下</option>
				      <option value="1">线上</option>
				     </select>
			    </td>
			    <td>是否用占用费比：</td>
			    <td>
				     <select id="isCostOf__" class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false"  required="required">
				      <option value="0">否</option>
				      <option value="1">是</option>
				     </select>
			    </td>
		    	<td>是否用仓租费：</td>
			    <td>
				     <select id="isStorageCharges__" class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false"  required="required">
				      	<option value="0">否</option>
				        <option value="1">是</option>
				     </select>
			    </td>
			    <td></td>
			    <td>
			    	<a id="lePricePlanTestQuery" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">测试</a>
			    	<a id="lePricePlanTestReset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
			    </td>
			   </tr>
		  </table>
		 </form>
		</div>
	 <div style="width:99%;height:92%;" class="easyui-panel">
	   <table border="" cellspacing="" cellpadding="" style="width:100%;height:100%;border-collapse:collapse;border:1px solid #ccc;"> 
	    	<tr>
	    		<td width="65%"><div id="lePricePlanTestShow1"></div></td>
	    		<td><div id="lePricePlanTestShow2"></div> </td>
	    	</tr>
	   </table>
	 </div>
	</div>
	
	
	<div id="leReckonDialog" class="easyui-dialog" title="计算导入" closed="true"
	style="width: 600px; height: 300px; padding: 10px;" modal="true">
	<form id="leReckonForm">
		选择文件：<input id="file" type="file" style="width: 260px" /> 
			<input type="button" style="height: 22px" id="leReckonPrice_" value="获取最终价格" />
			<input type="button" style="height: 22px" id="leReckonRate_" value="获取可用利润系数">
		</span>
	</form>
	<hr/>
	<p>
		执行结果:	<span style="color: red;display: none;" id="leMessages_"></span> 
		<span style="display: none;" id="leReckon_"> 
			<a id="leReckonExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
		</span>
	</p>
	<div id="leMessages" class="easyui-panel" style="width:550px;height:150px;color: red;"  closed="true">
		<ul id="leShowMessages_">
		</ul>
	</div>
	</div>
</div>
</@FTL.admin>
