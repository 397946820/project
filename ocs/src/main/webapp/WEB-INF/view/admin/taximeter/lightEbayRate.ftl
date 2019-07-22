<#import "upload/upload.ftl" as fileUpload />
<@FTL.admin id="lightEbayRateList" title="费税管理"
add_script_files=['admin/taximeter/lightEbayRate.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="lightEbayRateDataGrid"  class="easyui-datagrid"
		data-options="
           url:'${FTL.X.global_domain}/lightEbayRate/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'platform', title: '平台',formatter:getPlatform,sortable:true},
                {field: 'country', title: '国家',formatter:getCountry,sortable:true},
                {field: 'shippingType', title: '运输方式',sortable:true, formatter:getShippingType},
                {field: 'referralRate', title: '平台费率',sortable:true},
                {field: 'grossProfitRate', title: '毛利率',sortable:true},
                {field: 'paypalFee', title: 'paypal手续费',sortable:true},
                {field: 'vat', title: '增值率',sortable:true},
                {field: 'advertisingRate', title: '广告费率',sortable:true},
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
        toolbar:'#lightEbayRateToolbar'">
	</table>
</div>

<div id="lightEbayRateToolbar">
	<div style="padding:10px;">
		<form id="rateForm">
			<table style="float:left; min-width:1000px;">
				<tr style="min-width:1000px;">
					<td><label>平台：</label></td>
					<td>
						<select id="platform_" class="easyui-combobox" style="width: 150px"  editable="false">
							<option value="">--请选择--</option>
							<option value="ebay">Ebay</option>
							<option value="light">官网</option>
						</select>
					</td>
					<td><label>国家：</label></td>
					<td>
						<select id="country_" class="easyui-combobox" style="width: 150px"  editable="false">
							<option value="">--请选择--</option>
							<option value="US">United States</option>
							<option value="UK">United Kingdom</option>
							<option value="DE">German</option>
						</select>
					</td>
					<td><label>运输方式：</label></td>
					<td>
						<select id="shippingType_" class="easyui-combobox" style="width: 150px"  editable="false">
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
		<a id="lightEbayRateAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a id="lightEbayRateUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
		<a id="lightEbayRateExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
		<a id="lightEbayRateExportAllLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出全部</a>
	</div>
</div>
<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="lightEbayRateSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="lightEbayRateCancelLinkbutton">关闭</a>
</div>

<@fileUpload.upload></@>

<div id="lightEbayRateDialog" class="easyui-dialog" title="添加/编辑费税" closed="true"
	style="width: 500px; height: 380px; padding: 10px;" buttons="#rate-buttons" modal="true">
	<form id="lightEbayRateForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>平台：</td>
					<td>
						<select name="platform" id="platform" class="easyui-combobox" data-options="required:true" style="width: 200px"  editable="false">
							<option value="ebay">Ebay</option>
							<option value="light">官网</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>国家：</td>
					<td>
						<select name="country" id="country" class="easyui-combobox" data-options="required:true" style="width: 200px"  editable="false">
							<option value="US">United States</option>
							<option value="UK">United Kingdom</option>
							<option value="DE">German</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>运输方式：</td>
					<td>
						<select name="shippingType" id="shippingType" class="easyui-combobox" 
								data-options="required:true" style="width: 200px"  editable="false">
							<option value="af">af</option>
							<option value="sf">sf</option>
							<option value="co">co</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>平台费率：</td>
					<td>
						<input type="text" name="referralRate" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>毛利率：</td>
					<td>
						<input type="text" name="grossProfitRate" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>paypal手续费：</td>
					<td>
						<input type="text" name="paypalFee" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>增值率：</td>
					<td>
						<input type="text" name="vat" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>广告费率：</td>
					<td>
						<input type="text" name="advertisingRate" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>