<@FTL.admin id="productCostList" title="产品成本管理"
add_script_files=['admin/taximeter/productCost.js','admin/taximeter/common.js']>
<table id="productCostDataGrid"  style="width:100%;height:100%"
            url="${FTL.X.global_domain}/productCost/findAll"
            toolbar="#productCostToolbar" pagination="true" idField="id"
            rownumbers="true" fitColumns="true" pageSize="50" fit="true" singleSelect="true"
            border="false" striped="true" data-options="onDblClickRow:doDblClickRow">
	 <thead>
        <tr>
        	<@shiro.hasPermission name="CPCBGL_SKU">
        	<th field="sku" sortable="true">sku</th>
        	</@shiro.hasPermission>
        	<@shiro.hasPermission name="CPCBGL_YHM">
            <th field="username" sortable="true">用户名</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPCBGL_GMJG">
            <th field="purchasePrice" sortable="true">购买价格</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPCBGL_GMJG(Y)">
            <th field="purchasePriceRMB" sortable="true">购买价格(元)</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPCBGL_TSL">
            <th field="taxRebateRate" sortable="true">退税率</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPCBGL_LL">
            <th field="interestRate" sortable="true">利率</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPCBGL_CJSJ">
            <th field="createdAt" sortable="true" formatter="getTime">创建时间</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPCBGL_GXSJ">
            <th field="updatedAt" sortable="true" formatter="getTime">更新时间</th>
            </@shiro.hasPermission>
        </tr>
    </thead>
</table>
<div id="productCostToolbar">
<div>
	<form id="productCost_Form">
		<table>
			<tr>
				<td>
					sku: <input type="text" id="sku_" class="easyui-textbox" style="width: 200px;"  />
				</td>
				<td style="vertical-align:middle; text-align:center;">
					用户名: <input type="text" id="username" class="easyui-textbox" style="width: 200px;"  />
				</td>
				<td style="vertical-align:middle; text-align:center;">
					购买价格: <input type="text" id="price" class="easyui-numberbox" style="width: 200px;" data-options="precision:4" />
				</td>
			</tr>
			<tr>
				<td>创建时间:
					<input type="text" id="cstarttime"  class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="cendtime" class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/>
				</td>
				<td></td>
				<td>更新时间:
					<input type="text" id="ustarttime"  class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="uendtime" class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/>
				</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
		<a id="productCostUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<select id="template" class="easyui-combobox" style="width: 200px;" editable="false">
			<option value="">导出全部</option>
			<option value="template">导出模板</option>
		</select>
		<a id="productCostExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
	</div>
</div>
</div>
<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="productCostSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="productCostCancelLinkbutton">关闭</a>
</div>

<div id="uploadDialog" class="easyui-dialog" title="产品成本导入" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">  
	  		 选择文件：<input id="file" type="file" style="width:250px" />  
	       　　	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="height:20px" id="upload">导入</a> 　
	</form>
</div>

<div id="productCostDialog" class="easyui-dialog" title="编辑产品成本" closed="true"
	style="width: 500px; height: 300px; padding: 10px;" buttons="#rate-buttons">
	<form id="productCostForm" action="${FTL.X.global_domain}/productCost/edit" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>sku：</td>
					<td>
						<input type="text" name="sku" id="sku" class="easyui-textbox" style="width: 200px;"  />
					</td>
				</tr>
				<tr>
					<td>采购货币：</td>
					<td>
						<select id="currency" name="currency" class="easyui-combobox" style="width: 200px;" editable="false">
							<option value="RMB">RMB</option>
							<option value="USD">USD</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>采购金额：</td>
					<td>
						<input type="text" name="price" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>退税率：</td>
					<td>
						<input type="text" name="taxRebateRate" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>利率：</td>
					<td>
						<input type="text" name="interestRate" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
