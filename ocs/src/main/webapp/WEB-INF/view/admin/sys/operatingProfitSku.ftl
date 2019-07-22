<@FTL.admin id="operatingProfitSkuList" title="SKU库存维护" 
add_script_files=['admin/sys/operatingProfitSku.js','admin/sys/date.js','admin/taximeter/common.js']>

<div data-options="region:'center',border:false">
	<table id="operatingProfitSkuDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/operatingProfitSku/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'platform', title: '平台',sortable:true,
                	formatter:function(value,row){
	                	if(value == 'amazon') {
	                		return '亚马逊';
	                	} else if(value == 'ebay') {
	                		return 'Ebay';
	                	} else if(value == 'light') {
	                		return '官网';
	                	} else if(value == 'walmart') {
	                		return '沃尔玛';
	                	}
	                	return value;
	                }
                },
                {field: 'country', title: '国家',sortable:true,
                	formatter:function(value,row){
	                	if(value == 'US') {
	                		return 'United States';
	                	} else if(value == 'GB') {
	                		return 'United Kingdom';
	                	} else if(value == 'DE') {
	                		return 'German';
	                	} else if(value == 'FR') {
	                		return 'France';
	                	} else if(value == 'IT') {
	                		return 'Italy';
	                	} else if(value == 'ES') {
	                		return 'Spain';
	                	} else if(value == 'JP') {
	                		return 'Japan';
	                	} else if(value == 'CA') {
	                		return 'Canada';
	                	} else if(value == 'AU') {
	                		return 'Australia';
	                	}
	                	return value;
	                }
                },
                {field: 'sku', title: 'sku',sortable:true},
                {field: 'stock', title: '库存数量',sortable:true},
                {field: 'shippingType', title: '运输方式',sortable:true},
                {field: 'monthOfYear', title: '年月',sortable:true},
                {field: 'status', title: '产品状态',sortable:true,hidden:true},
                {field: 'createdAt', title: '创建时间',formatter:getTime,sortable:true},
                {field: 'updatedAt', title: '更新时间',formatter:getTime,sortable:true}
                
            ]
        ],
        idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#operatingProfitSkuToolbar'">
	</table>
</div>

<div id="operatingProfitSkuToolbar">
	<div style="height:30px;">
		<form id="searchForm">
			<table style="float:left; min-width:1000px;">
				<tr style="min-width:1000px;">
					<td><label>平台：</label></td>
					<td>
						<select name="platform" class="easyui-combobox" style="width: 150px"  editable="false" panelHeight="auto">
							<option value=""></option>
							<option value="amazon">亚马逊</option>
							<option value="ebay">Ebay</option>
							<option value="light">官网</option>
							<option value="walmart">沃尔玛</option>
						</select>
					</td>
					<td><label>国家：</label></td>
					<td>
						<select name="country" class="easyui-combobox" style="width: 150px"  editable="false" panelHeight="auto">
							<option value=""></option>
							<option value="US" >United States</option>
							<option value="GB" >United Kingdom</option>
							<option value="DE" >German</option>
							<option value="FR" >France</option>
							<option value="IT" >Italy</option>
							<option value="ES" >Spain</option>
							<option value="JP" >Japan</option>
							<option value="CA" >Canada</option>
							<option value="AU" >Australia</option>
						</select>
					</td>
					<td><label>sku：</label></td>
					<td>
						<input type="text" name="sku" class="easyui-textbox" style="width: 150px">
					</td>
					<td><label>年月：</label></td>
					<td>
						<input id="date" name="monthOfYear" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 180, panelAlign: 'right', editable: false">
					</td>
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
			<a id="operatingProfitSkuUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入海外库存</a>
			<a id="operatingProfitSkuUploadNewSkuLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入新品sku</a>
			<a id="operatingProfitSkuExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载海外库存模板</a>
			<a id="operatingProfitSkuNewExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载新品SKU模板</a>
			<a id="operatingProfitCostLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-readed" plain="true">成本修正比例</a>
			<a id="operatingProfitStockLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-readed" plain="true">深圳仓库存</a>
			<a id="operatingProfit_S_E" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出深圳仓无SOURCING_COST</a>
			<a id="operatingProfit_H_E" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出海外仓无CIF</a>
			<a id="operatingProfit_S_I" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入深圳仓SOURCING_COST</a>
			<a id="operatingProfit_H_I" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入海外仓CIF</a>
		</div>
	</div>
</div>

<div id="operatingProfitStock" class="easyui-dialog" title="深圳仓库存" closed="true" style="width:800px;height:550px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#operatingProfitStock').dialog('close');
					}
				}]">
		<table id="operatingProfitStockDataGrid" style="width:100%;height:100%" class='easyui-datagrid'
			data-options="
	           url:'',
	           fitColumns:true,
	           columns: [
	            [
	                {field: 'sku', title: 'SKU',sortable:true},
	                {field: 'stock', title: '库存',sortable:true},
	                {field: 'monthOfYear', title: '年月',sortable:true},
	                {field: 'createdAt', title: '创建时间',formatter:getTime,sortable:true},
	                {field: 'updatedAt', title: '更新时间',formatter:getTime,sortable:true}
	                
	            ]
	        ],
	        singleSelect: true,
	        rownumbers: true,
	        pagination: true,
	        pageSize: 50,
	        border:false,
	        fit:true,
	        toolbar:'#operatingProfitStockToolbar'">
		</table>
</div>

<div id="operatingProfitStockToolbar">
	<div style="height:40px;">
		<form id="stockSearchForm">
			<table style="float:left; min-width:400px;">
				<tr style="min-width:400px;">
					<td><label>SKU：</label></td>
					<td>
						<input name="sku" style="width: 200px;" class="easyui-textbox">
					</td>
					<td><label>年月：</label></td>
					<td>
						<input id="date_2" name="monthOfYear" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 180, panelAlign: 'right', editable: false">
					</td>
					<td>
						<a id="queryStock" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="resetStock" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<hr>
	<div>
		<div>
			<a id="operatingProfitStockExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出模板</a>
			<a id="operatingProfitStockUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
		</div>
	</div>
</div>


<div id="operatingProfitCost" class="easyui-dialog" title="成本修正比例" closed="true" style="width:800px;height:450px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#operatingProfitCost').dialog('close');
					}
				}]">
		<table id="operatingProfitCostDataGrid" style="width:100%;height:100%" class='easyui-datagrid'
			data-options="
	           url:'',
	           fitColumns:true,
	           columns: [
	            [
	                {field: 'cost', title: '成本修正比例',sortable:true},
	                {field: 'meter', title: '计价器修正比例',sortable:true},
	                {field: 'monthOfYear', title: '年月',sortable:true},
	                {field: 'createdAt', title: '创建时间',formatter:getTime,sortable:true},
	                {field: 'updatedAt', title: '更新时间',formatter:getTime,sortable:true}
	                
	            ]
	        ],
	        singleSelect: true,
	        rownumbers: true,
	        pagination: true,
	        pageSize: 50,
	        border:false,
	        fit:true,
	        toolbar:'#operatingProfitCostToolbar'">
		</table>
</div>

<div id="operatingProfitCostToolbar">
	<div style="height:40px;">
		<form id="costSearchForm">
			<table style="float:left; min-width:400px;">
				<tr style="min-width:400px;">
					<td><label>年月：</label></td>
					<td>
						<input id="date_1" name="monthOfYear" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 180, panelAlign: 'right', editable: false">
					</td>
					<td>
						<a id="queryCost" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="resetCost" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<hr>
	<div>
		<div>
			<a id="operatingProfitCostAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		</div>
	</div>
</div>

<div id="uploadDialog" class="easyui-dialog" title="文件导入" closed="true" style="width: 450px; height: 200px; padding: 10px;" modal="true">
	<form id="uploadForm" method="post" enctype="multipart/form-data">
		<input class="easyui-filebox" name="file" id="file" data-options="prompt:'请选择文件...'" buttonText="选择文件"
			style="height: 30px; width: 350px;"> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="height: 30px" id="upload">导入</a>
	</form>
	<hr>
	<div id="messages" class="easyui-panel" style="width:410px;height:100px;color: red;"  closed="true">
		<ul id="showMessages_">
		</ul>
	</div>
</div>

<div id="operatingProfitSku-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="operatingProfitSkuSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="operatingProfitSkuCancelLinkbutton">关闭</a>
</div>

<div id="operatingProfitCost-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="operatingProfitCostSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="operatingProfitCostCancelLinkbutton">关闭</a>
</div>

<div id="operatingProfitSkuDialog" class="easyui-dialog" title="添加/编辑SKU库存维护" closed="true"
	style="width: 500px; height: 350px; padding: 10px;" buttons="#operatingProfitSku-buttons" modal="true">
	<form id="operatingProfitSkuForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>平台：</td>
					<td>
						<select id="platform" name="platform" class="easyui-combobox" style="width: 200px;">
							<option value="amazon">亚马逊</option>
							<option value="ebay">Ebay</option>
							<option value="light">官网</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>国家：</td>
					<td>
						<select id="country" name="country" class="easyui-combobox" style="width: 200px;">
							<option value="US">United States</option>
							<option value="GB">United Kingdom</option>
							<option value="DE">German</option>
							<option value="FR">France</option>
							<option value="IT">Italy</option>
							<option value="ES">Spain</option>
							<option value="JP">Japan</option>
							<option value="CA">Canada</option>
							<option value="AU">Australia</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>sku：</td>
					<td>
						<input type="text" id="sku" name="sku" class="easyui-textbox" style="width: 200px;" required="required"/>
					</td>
				</tr>
				<tr>
					<td>库存数量：</td>
					<td>
						<input type="text" name="stock" class="easyui-numberbox" style="width: 200px;" data-options="min:0,precision:0"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>运输方式：</td>
					<td>
						<select name="shippingType" class="easyui-combobox" style="width: 200px;" panelHeight="auto" editable="false" required="required">
							<option value="af">BY AF</option>
							<option value="sf">BY SF</option>
							<option value="co">BY CO</option>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<div id="operatingProfitCostDialog" class="easyui-dialog" title="添加/编辑成本修正比例" closed="true"
	style="width: 300px; height: 200px; padding: 10px;" buttons="#operatingProfitCost-buttons" modal="true">
	<form id="operatingProfitCostForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>成本修正比例：</td>
					<td>
						<input type="text" name="cost" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>计价器修正比例：</td>
					<td>
						<input type="text" name="meter" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
