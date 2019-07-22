<@FTL.admin id="lightEbaySundryList" title="官网Uk杂项维护"
add_script_files=['admin/taximeter/lightEbaySundry.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="lightEbaySundryDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/lightEbaySundry/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'country', title: '国家',formatter:getCountry},
         		{field: 'overWeight', title: '超重重量'},
                {field: 'overLength', title: '超重长度'},
                {field: 'overWidth', title: '超重宽度'},
                {field: 'overweightFee', title: '超重费'},
                {field: 'packingCharge', title: '纸箱包装费'},
                {field: 'handlingFee', title: '订单处理费'},
                {field: 'extraFee', title: '额外加收订单费'},
                {field: 'tpsDeliveryFee', title: 'TPS发货费'},
                {field: 'tpsProportion', title: 'TPS占比'},
                {field: 'tpnDeliveryFee', title: 'TPN发货费'},
                {field: 'tpnProportion', title: 'TPN占比'},
                {field: 'pfPrice', title: 'PF24发货费单价'},
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
        toolbar:'#lightEbaySundryToolbar'">
	</table>
</div>

<div id="lightEbaySundryToolbar" >
	<div style="padding:10px;">国家:
		<select id="country_" class="easyui-combobox" data-options="required:true" style="width: 150px"  editable="false">
			<option value="US">United States</option>
			<option value="UK">United Kingdom</option>
			<option value="DE">German</option>
		</select>
	</div>
	<hr>
</div>
<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="lightEbaySundrySaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="lightEbaySundryCancelLinkbutton">关闭</a>
</div>

<div id="lightEbaySundryDialog" class="easyui-dialog" title="添加/编辑杂费" closed="true"
	style="width: 500px; height: 380px; padding: 10px;" buttons="#rate-buttons" modal="true">
	<form id="lightEbaySundryForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
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
				<tr id="overWeight_">
					<td>超重重量：</td>
					<td>
						<input type="text" name="overWeight" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="overLength_">
					<td>超重长度：</td>
					<td>
						<input type="text" name="overLength" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="overWidth_">
					<td>超重宽度：</td>
					<td>
						<input type="text" name="overWidth" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="overweightFee_">
					<td>超重费：</td>
					<td>
						<input type="text" name="overweightFee" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="packingCharge_">
					<td>纸箱包装费：</td>
					<td>
						<input type="text" name="packingCharge" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="handlingFee_">
					<td>订单处理费：</td>
					<td>
						<input type="text" name="handlingFee" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="extraFee_">
					<td>额外加收订单费：</td>
					<td>
						<input type="text" name="extraFee" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="tpsDeliveryFee_">
					<td>TPS发货费：</td>
					<td>
						<input type="text" name="tpsDeliveryFee" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="tpsProportion_">
					<td>TPS占比：</td>
					<td>
						<input type="text" name="tpsProportion" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="tpnDeliveryFee_">
					<td>TPN发货费：</td>
					<td>
						<input type="text" name="tpnDeliveryFee" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="tpnProportion_">
					<td>TPN占比：</td>
					<td>
						<input type="text" name="tpnProportion" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="pfPrice_">
					<td>PF24发货单价：</td>
					<td>
						<input type="text" name="pfPrice" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>