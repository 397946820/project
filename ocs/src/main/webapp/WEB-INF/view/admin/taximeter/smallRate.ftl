<@FTL.admin id="taxList" title="税管理"
add_script_files=['admin/taximeter/smallRate.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="smallRateDataGrid" class="easyui-datagrid"
		data-options="
           url:'${FTL.X.global_domain}/smallRate/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'platform', title: '平台',formatter:getPlatform},
                {field: 'referralRate', title: '平台费率'},
                {field: 'grossProfitRate', title: '毛利率'},
                {field: 'paypalFee', title: 'paypal手续费'},
                {field: 'vat', title: '增值率'},
                {field: 'advertisingRate', title: '广告费率'},
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
        onDblClickRow : doDblClickRow">
	</table>
</div>

<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="smallRateSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="smallRateCancelLinkbutton">关闭</a>
</div>

<div id="smallRateDialog" class="easyui-dialog" title="添加/编辑费税" closed="true"
	style="width: 500px; height: 380px; padding: 10px;" buttons="#rate-buttons" modal="true">
	<form id="smallRateForm" action="${FTL.X.global_domain}/smallRate/saveEdit" method="post">
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
					<td>平台费率：</td>
					<td>
						<input type="text" name="referralRate" class="easyui-numberbox" style="width: 200px;" data-options="precision:4,min:0"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>毛利率：</td>
					<td>
						<input type="text" name="grossProfitRate" class="easyui-numberbox" style="width: 200px;" data-options="precision:4,min:0"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>paypal手续费：</td>
					<td>
						<input type="text" name="paypalFee" class="easyui-numberbox" style="width: 200px;" data-options="precision:4,min:0"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>增值率：</td>
					<td>
						<input type="text" name="vat" class="easyui-numberbox" style="width: 200px;" data-options="precision:4,min:0"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>广告费率：</td>
					<td>
						<input type="text" name="advertisingRate" class="easyui-numberbox" style="width: 200px;" data-options="precision:4,min:0"  required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>


</@FTL.admin>
