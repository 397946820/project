<#import "upload/upload.ftl" as fileUpload />
<@FTL.admin id="lightEbayTaxList" title="产品其他"
add_script_files=['admin/taximeter/lightEbayTax.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="lightEbayTaxDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/lightEbayTax/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'sku', title: 'sku',sortable:true},
                {field: 'country', title: '国家',formatter:getCountry,sortable:true},
                {field: 'inventoryQuantity', title: '累计库存数量',sortable:true},
                {field: 'saleTotal', title: '累计销售量',sortable:true},
                {field: 'unavailability', title: '不可用率',sortable:true},
                {field: 'replacementRate', title: '补发率',sortable:true},
                {field: 'storageDays', title: '存储天数',sortable:true},
                {field: 'costThan', title: '费用占比',sortable:true},
                {field: 'volatilityFactor', title: '波动因子',sortable:true},
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
        onDblClickRow : doDblClickRow,
        toolbar:'#lightEbayTaxToolbar'">
	</table>
</div>

<div id="lightEbayTaxToolbar">
	<div style="height:30px;">
		<form id="taxForm">
			<table style="float:left; min-width:1000px;">
				<tr style="min-width:1000px;">
					<td><label>国家：</label></td>
					<td>
						<select id="country_" class="easyui-combobox" style="width: 150px"  editable="false">
							<option value="">--请选择--</option>
							<option value="US">United States</option>
							<option value="UK">United Kingdom</option>
							<option value="DE">German</option>
						</select>
					</td>
					<td><label>sku：</label></td>
					<td>
						<input type="text" id="sku" class="easyui-textbox" style="width: 150px">
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
			<a id="lightEbayTaxAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<a id="lightEbayTaxUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
			<a id="lightEbayTaxExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
			<a id="lightEbayTaxExportAllLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出全部</a>
		</div>
	</div>
</div>

<@fileUpload.upload></@>

<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="lightEbayTaxSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="lightEbayTaxCancelLinkbutton">关闭</a>
</div>

<div id="lightEbayTaxDialog" class="easyui-dialog" title="添加/编辑产品其他" closed="true"
	style="width: 500px; height: 350px; padding: 10px;" buttons="#rate-buttons" modal="true">
	<form id="lightEbayTaxForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>国家：</td>
					<td>
						<select id="country" name="country" class="easyui-combobox" style="width: 200px;" editable="false">
								<option value="US" >United States</option>
								<option value="UK" >United Kingdom</option>
								<option value="DE" >German</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>sku：</td>
					<td>
						<input type="text" id="sku_" name="sku" class="easyui-textbox" style="width: 200px;" required="required"/>
					</td>
				</tr>
				<tr>
					<td>库存总量：</td>
					<td>
						<input type="text" name="inventoryQuantity" class="easyui-numberbox" style="width: 200px;" data-options="min:0,precision:0"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>销售总量：</td>
					<td>
						<input type="text" name="saleTotal" class="easyui-numberbox" style="width: 200px;" data-options="min:0,precision:0"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>不可用率：</td>
					<td>
						<input type="text" name="unavailability" class="easyui-numberbox" style="width: 200px;" data-options="precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>补发率：</td>
					<td>
						<input type="text" name="replacementRate" class="easyui-numberbox" style="width: 200px;" data-options="precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>存储天数：</td>
					<td>
						<input type="text" name="storageDays" class="easyui-numberbox" style="width: 200px;" data-options="min:1,precision:0" required="required"/>
					</td>
				</tr>
				<tr>
					<td>费用占比：</td>
					<td>
						<input type="text" name="costThan" class="easyui-numberbox" style="width: 200px;" data-options="precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>波动因子：</td>
					<td>
						<input type="text" name="volatilityFactor" class="easyui-numberbox" style="width: 200px;" data-options="precision:4" required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>