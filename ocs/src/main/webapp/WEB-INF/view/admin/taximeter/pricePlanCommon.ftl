<#macro pricePlan>

<table id="pricePlanDataGrid" style="width: 100%; height: 100%"
	url=""
	toolbar="#pricePlanToolbar" pagination="true" idField="id"
	rownumbers="true" fitColumns="true" pageSize="50" fit="true"
	singleSelect="true" border="false" striped="true" autoEditing="true"
	extEditing="true" singleEditing="true">
	<thead>
		<tr>
			<@shiro.hasPermission name="JGJHGL_GJ">
			<th field="countryId" sortable="true">国家</th> </@shiro.hasPermission>
			<@shiro.hasPermission name="JGJHGL_WLFS">
			<th field="shippingType" sortable="true">物流方式</th>
			</@shiro.hasPermission> <@shiro.hasPermission name="JGJHGL_SKU">
			<th field="sku" sortable="true">sku</th> </@shiro.hasPermission>
			<th field="productType" sortable="false">产品分类</th>
			<th field="discontinue" sortable="false">是否discontinue</th>
			<@shiro.hasPermission name="JGJHGL_SFOVERSIZE">
			<th field="isOversize" sortable="true" formatter="getIsOversize">是否Oversize</th>
			</@shiro.hasPermission> <@shiro.hasPermission name="JGJHGL_CIF">
			<th field="cif">CIF</th> </@shiro.hasPermission>
			<@shiro.hasPermission name="JGJHGL_CIFUSD">
			<th field="cifusd">CIF(USD)</th> </@shiro.hasPermission>
			<@shiro.hasPermission name="JGJHGL_CIFRMB">
			<th field="cifrmb">CIF(RMB)</th> </@shiro.hasPermission>
			<@shiro.hasPermission name="JGJHGL_CGCB">
			<th field="sourcingCost">采购成本</th> </@shiro.hasPermission>
			<@shiro.hasPermission name="JGJHGL_FBAFY">
			<th field="fbaFee" sortable="true">FBA推算费用</th> </@shiro.hasPermission>
			<@shiro.hasPermission name="JGJHGL_YMXFBAFY">
			<th field="amzFba" sortable="true">FBA实际费用</th>
			</@shiro.hasPermission> <@shiro.hasPermission name="JGJHGL_CCF">
			<th field="storageFee" sortable="true">仓储费</th>
			</@shiro.hasPermission> <@shiro.hasPermission name="JGJHGL_ZZCB">
			<th field="finalCost" sortable="true">最终成本</th>
			</@shiro.hasPermission> <@shiro.hasPermission name="JGJHGL_TJFL">
			<th field="referralRate" sortable="true">推介费率</th>
			</@shiro.hasPermission> 
			<@shiro.hasPermission name="JGJHGL_SHL">
			<th field="unfulliableRate" sortable="true">损坏率</th>
			</@shiro.hasPermission>
			<@shiro.hasPermission name="JGJHGL_SHL">
			<th field="returnRate" sortable="false">退货率</th>
			</@shiro.hasPermission>
			 <@shiro.hasPermission name="JGJHGL_BFL">
			<th field="replacementRate" sortable="true">补发率</th>
			</@shiro.hasPermission> <@shiro.hasPermission name="JGJHGL_LRL">
			<th field="profitRate" sortable="true">利润率</th>
			</@shiro.hasPermission> <@shiro.hasPermission name="JGJHGL_KYLRL">
			<th field="profitRateAction" sortable="true"
				editor="{type:'numberbox',options:{precision:4,min:0}}">利润系数</th>
			</@shiro.hasPermission> <@shiro.hasPermission name="JGJHGL_SL">
			<th field="vat" sortable="true">税率</th> </@shiro.hasPermission>
			<@shiro.hasPermission name="JGJHGL_ZZJG">
			<th field="finalPriceStr" sortable="true"
				editor="{type:'numberbox',options:{precision:4,min:0}}">建议售价</th>
			</@shiro.hasPermission> <@shiro.hasPermission name="JGJHGL_GXSJ">
			<th field="updatedAt" sortable="true" formatter="getTime">更新时间</th>
			</@shiro.hasPermission>
		</tr>
	</thead>
</table>

<div id="pricePlanToolbar">
	<div style="height:160px;">
		<form id="rateForm">
			<table style="float:left; min-width:1000px;">
				<tr style="min-width:1000px;">
					<td><label>国家:</label></td>
					<td>
						<select id="countryId" class="easyui-combobox" style="width: 150px;" editable="false">
								
						</select>
					</td>
					<td><label>运输方式:</label></td>
					<td>
						<select id="shippingType" class="easyui-combobox" style="width: 150px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="af">BY AF</option>
							<option value="sf">BY SF</option>
							<option value="co">BY CO</option>
							<option value="af_efn">BY AF EFN</option>
							<option value="sf_efn">BY SF EFN</option>
							<option value="co_efn">BY CO EFN</option>
							<option value="af_peu">BY AF PEU</option>
							<option value="sf_peu">BY SF PEU</option>
							<option value="co_peu">BY CO PEU</option>
						</select>
					</td>
					<td><label>sku:</label></td>
					<td>
						<input type="text" id="sku" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>是否Oversize:</label></td>
					<td> 
						<select id="isOversize" class="easyui-combobox" style="width: 150px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
				</tr>
				<tr style="min-width:1000px;">
					<td><label>CIF:</label></td>
					<td>
						<input type="text" id="cif" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td><label>FBA推算费用:</label></td>
					<td>
						<input type="text" id="fbaFee" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
					<td><label>FBA实际费用 :</label></td>
					<td>
						<input type="text" id="amzFba" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
					<td><label>仓储费:</label></td>
					<td>
						<input type="text" id="storageFee" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
				</tr>
				<tr style="min-width:1000px;">
					<td><label>最终成本:</label></td>
					<td>
						<input type="text" id="finalCost" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
					<td><label>推介费率:</label></td>
					<td>
						<input type="text" id="referralRate" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
					<td><label>损坏率:</label></td>
					<td>
						<input type="text" id="unfulliableRate" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
					<td><label>创建时间:</label></td>
					<td>
						<input type="text" id="cstarttime" class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'" /> - 
						<input type="text" id="cendtime" class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'" />
					</td>
				</tr>
				<tr style="min-width:1000px;">
					<td><label>补发率:</label></td>
					<td>
						<input type="text" id="replacementRate" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
					<td><label>利润率:</label></td>
					<td>
						<input type="text" id="profitRate" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
					<td><label>可用系数:</label></td>
					<td>
						<input type="text" id="profitRateAction" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
					<td><label>更新时间:</label></td>
					<td>
						<input type="text" id="ustarttime" class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'" /> - 
						<input type="text" id="uendtime" class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'" />
					</td>
				</tr>
				<tr style="min-width:1000px;">
					<td><label>税率:</label></td>
					<td>
						<input type="text" id="vat" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
					<td><label>建议售价:</label></td>
					<td>
						<input type="text" id="finalPrice" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
					</td>
					<td><label>产品分类:</label></td>
					<td>
						<input id="productType" class="easyui-combobox" style="width: 150px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/pricePlan/getProductField'">
					</td>
					<td><label>是否DIS:</label></td>
					<td> 
						<select id="discontinue" class="easyui-combobox" style="width: 150px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="0">否</option>
							<option value="1">是</option>
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
	<hr>
	<div>
		<div>
			<select id="template" class="easyui-combobox" style="width: 180px;"
				editable="false">
				<option value="">导出数据</option>
				<option value="template">下载价格推算模板</option>
				<option value="template_variant">下载价格推算捆绑销售模板</option>
				<option value="count">下载保本销量模板</option>
				<option value="variant">下载保本销量捆绑销售模板</option>
			</select> 
			<a id="pricePlanExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
			<a id="pricePlanReckonLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true">价格推算器</a>
			<a id="pricePlanLinkVariantbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true">捆绑销售推算器</a>
			<a id="pricePlanCostLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true">计算保本销量</a>
			<a id="pricePlanRecoverLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-back" plain="true">恢复可用利润率</a>
			<@shiro.hasAnyRoles name="ADMINISTRATOR,CEO,SWZG,CWZG">
				<a id="pricePlanRefreshLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新价格计划</a>
				<a id="pricePlanTestLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain="true">测试</a>
			</@shiro.hasAnyRoles>
		</div>
	</div>
</div>

<div id="pricePlanTest" class="easyui-dialog" title="测试" closed="true"
	style="width: 100%; height: 100%; padding: 10px;" modal="true">
		<div style="height: 5%;">
			<form id="pricePlanTestForm">
				<table style="float: left; min-width: 1000px;">
				<tr style="min-width: 1000px;">
			    <td>国家 ：</td>
			    <td>
				     <select id="countryId_"  class="easyui-combobox" style="width: 200px;" panelHeight="auto" editable="false"  required="required">
				     </select>
			    </td>
			    <td>SKU：</td>
			    <td>
			    	<select id="sku_" class="easyui-combobox" style="width: 200px;" panelMaxHeight="150"  required="required">
				     
				    </select>
			    </td>
			    <td>
			    	<a id="pricePlanTestQuery" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">测试</a>
			    	<a id="pricePlanTestReset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
			    </td>
			   </tr>
		  </table>
		 </form>
		</div>
	 <div style="width:99%;height:95%;" class="easyui-panel">
	   <table border="" cellspacing="" cellpadding="" style="width:100%;height:100%;border-collapse:collapse;border:1px solid #ccc;"> 
	    	<tr>
	    		<td width="100%"><div id="pricePlanTestShow"></div></td>
	    	</tr>
	   </table>
	 </div>
	</div>

<div id="reckonDialog" class="easyui-dialog" title="计算导入" closed="true"
	style="width: 600px; height: 300px; padding: 10px;" modal="true">
	<form id="reckonForm">
		选择文件：<input id="file" type="file" style="width: 260px" /> 
		<span style="display: none;" id="reckon"> 
			<span style="display: none;" id="reckonPrice"> 
				<input type="button" style="height: 22px" id="reckonPrice_" value="获取最终价格" />
			</span>
			<span style="display: none;" id="reckonRate"> 
				<input type="button" style="height: 22px" id="reckonRate_" value="获取可用利润系数">
			</span>
		</span>
		<span style="display: none;" id="reckonCount"> 
			<input type="button" style="height: 22px" id="reckonCount_" value="获取保本销量" />
		</span>
	</form>
	<hr/>
	<p>
		执行结果:	<span style="color: red;display: none;" id="messages_"></span> 
		
		<span style="display: none;" id="reckon_"> 
			<a id="reckonExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
		</span>
	</p>
	
	<div id="messages" class="easyui-panel" style="width:550px;height:150px;color: red;"  closed="true">
		<ul id="showMessages_">
		</ul>
	</div>
</div>

</#macro>