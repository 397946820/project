<@FTL.admin id="operatingProfitFeeList" title="亚马逊SKU广告费" 
add_script_files=['admin/sys/operatingProfitFee.js','admin/sys/date.js','admin/taximeter/common.js']>

<div data-options="region:'center',border:false">
	<table id="operatingProfitFeeDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/operatingProfitFee/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'freightUs', title: '运费-美国外仓',sortable:true},
                {field: 'freightUk', title: '运费-英国外仓',sortable:true},
                {field: 'freightDe', title: '运费-德国外仓',sortable:true},
                {field: 'customerFee1', title: '客服-60K',sortable:true},
                {field: 'customerFee2', title: '客服-通讯费',sortable:true},
                {field: 'customerFee3', title: '客服-ZENDESK-INC',sortable:true},
                {field: 'customerFee4', title: '客服-RESELLERRATINGS.COM',sortable:true},
                {field: 'customerFee5', title: '客服-TrustedShopsGmbH',sortable:true},
                {field: 'feeEbay', title: '平台费-eBay',sortable:true},
                {field: 'packingCharge', title: '打包费-Onlinepack',sortable:true},
                {field: 'clearFee', title: '清关费',sortable:true},
                {field: 'otherFee', title: '其他费用',sortable:true},
                {field: 'monthOfYear', title: '年月',sortable:true},
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
        toolbar:'#operatingProfitFeeToolbar'">
	</table>
</div>

<div id="operatingProfitFeeToolbar">
	<div style="height:40px;">
		<form id="feeSearchForm">
			<table style="float:left; min-width:400px;">
				<tr style="min-width:400px;">
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
	<hr/>
	<div>
		<div>
			<a id="operatingProfitFeeUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入总费用</a>
			<a id="operatingProfitFeeExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
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

<div id="operatingProfitFee-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="operatingProfitFeeSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="operatingProfitFeeCancelLinkbutton">关闭</a>
</div>

<div id="operatingProfitFeeDialog" class="easyui-dialog" title="编辑广告费" closed="true"
	style="width: 500px; height: 450px; padding: 10px;" buttons="#operatingProfitFee-buttons" modal="true">
	<form id="operatingProfitFeeForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>运费-美国外仓：</td>
					<td>
						<input type="text" name="freightUs" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>运费-英国外仓：</td>
					<td>
						<input type="text" name="freightUk" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>运费-德国外仓：</td>
					<td>
						<input type="text" name="freightDe" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>客服-60K：</td>
					<td>
						<input type="text" name="customerFee1" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>客服-通讯费：</td>
					<td>
						<input type="text" name="customerFee2" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>客服-ZENDESK-INC：</td>
					<td>
						<input type="text" name="customerFee3" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>客服-RESELLERRATINGS.COM：</td>
					<td>
						<input type="text" name="customerFee4" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>客服-TrustedShopsGmbH：</td>
					<td>
						<input type="text" name="customerFee5" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>平台费-eBay：</td>
					<td>
						<input type="text" name="feeEbay" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>打包费-Onlinepack：</td>
					<td>
						<input type="text" name="packingCharge" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>清关费：</td>
					<td>
						<input type="text" name="clearFee" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>其他费用：</td>
					<td>
						<input type="text" name="otherFee" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
