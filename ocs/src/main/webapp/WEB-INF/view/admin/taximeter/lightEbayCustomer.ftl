<#import "upload/upload.ftl" as fileUpload />
<@FTL.admin id="lightEbayCustomerList" title="物流规则"
add_script_files=['admin/taximeter/lightEbayCustomer.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="lightEbayCustomerDataGrid"  class="easyui-datagrid"
		data-options="
           url:'${FTL.X.global_domain}/lightEbayCustomer/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'country', title: '国家',formatter:getCountry},
                {field: 'region', title: '地区'},
                {field: 'shippingType', title: '运输方式',formatter:getShippingType},
                {field: 'fromWeight', title: '起始重量'},
                {field: 'toWeight', title: '结束重量'},
                {field: 'unitPrice', title: '单价'},
                {field: 'currencyCode', title: '货币'},
                {field: 'volumeWeightCoefficient', title: '体积重系数'},
                {field: 'remarks', title: '备注'},
                {field: 'createdAt', title: '创建时间',formatter:getTime},
                {field: 'updatedAt', title: '更新时间',formatter:getTime}
                
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
        toolbar:'#lightEbayCustomerToolbar'">
	</table>
</div>

<div id="lightEbayCustomerToolbar">
	<div style="padding:10px;">
		<form id="rateForm">
			<table style="float:left; min-width:1000px;">
				<tr style="min-width:1000px;">
					<td><label>国家：</label></td>
					<td>
						<select id="country_" class="easyui-combobox" style="width: 200px;" editable="false">
							<option value="">--请选择--</option>
							<option value="US">United States</option>
							<option value="UK">United Kingdom</option>
							<option value="DE">German</option>
						</select>
					</td>
					<td><label>地区：</label></td>
					<td>
						<select type="text" id="region_" class="easyui-combobox" style="width: 200px;" editable="false">
							<option value="">--请选择--</option>
							<option value="US美东">US美东</option>
							<option value="US美西">US美西</option>
							<option value="欧洲">欧洲</option>
						</select>
					</td>
					<td><label>运输方式：</label></td>
					<td>
						<select type="text" id="shippingType_" class="easyui-combobox" style="width: 200px;" editable="false">
							<option value="">--请选择--</option>
							<option value="af">BY AF</option>
							<option value="sf">BY SF</option>
							<option value="co">BY CO</option>
						</select>
					</td>
					<td>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
			<br clear="all"/>
		</form>
	</div>
	<hr>
	<div>
		<a id="lightEbayCustomerAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a id="lightEbayCustomerUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
		<a id="lightEbayCustomerExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
		<a id="lightEbayCustomerExportAllLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出全部</a>
	</div>
</div>
<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="lightEbayCustomerSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="lightEbayCustomerCancelLinkbutton">关闭</a>
</div>

<@fileUpload.upload></@>

<div id="lightEbayCustomerDialog" class="easyui-dialog" title="添加/编辑物流规则" closed="true"
	style="width: 500px; height: 350px; padding: 10px;" buttons="#rate-buttons" modal="true">
	<form id="lightEbayCustomerForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>国家：</td>
					<td>
						<select name="country" id="country" class="easyui-combobox" style="width: 200px;" editable="false">
							<option value="US">United States</option>
							<option value="UK">United Kingdom</option>
							<option value="DE">German</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>地区：</td>
					<td>
						<select type="text" name="region" id="region" class="easyui-combobox" style="width: 200px;" editable="false">
							<option value="US美东">US美东</option>
							<option value="US美西">US美西</option>
							<option value="欧洲">欧洲</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>运输方式：</td>
					<td>
						<select type="text" name="shippingType" id="shippingType" class="easyui-combobox" style="width: 200px;" editable="false">
							<option value="af">BY AF</option>
							<option value="sf">BY SF</option>
							<option value="co">BY CO</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>起始重量：</td>
					<td>
						<input type="text" name="fromWeight" id="fromWeight" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>结束重量：</td>
					<td>
						<input type="text" name="toWeight" id="toWeight" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>单价：</td>
					<td>
						<input type="text" name="unitPrice" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>货币：</td>
					<td>
						<input type="text" name="currencyCode" class="easyui-textbox" style="width: 200px;" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>体积重系数：</td>
					<td>
						<input type="text" name="volumeWeightCoefficient" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:2"/>
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td>
						<input type="text" name="remarks" class="easyui-textbox" style="width: 200px;"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>