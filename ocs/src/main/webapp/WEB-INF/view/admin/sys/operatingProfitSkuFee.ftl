<@FTL.admin id="operatingProfitSkuFeeList" title="亚马逊SKU广告费" 
add_script_files=['admin/sys/operatingProfitSkuFee.js','admin/sys/date.js','admin/taximeter/common.js']>

<div data-options="region:'center',border:false">
	<table id="operatingProfitSkuFeeDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/operatingProfitSkuFee/findAll',
           fitColumns:true,
           columns: [
            [
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
                {field: 'fee', title: '广告费',sortable:true},
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
        toolbar:'#operatingProfitSkuFeeToolbar'">
	</table>
</div>

<div id="operatingProfitSkuFeeToolbar">
	<div style="height:40px;">
		<form id="skuFeeSearchForm">
			<table style="float:left; min-width:1000px;">
				<tr style="min-width:1000px;">
					<td><label>国家：</label></td>
					<td>
						<select name="country" class="easyui-combobox" style="width: 200px"  editable="false" panelHeight="auto">
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
						<input type="text" name="sku" class="easyui-textbox" style="width: 200px">
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
	<hr/>
	<div>
		<div>
			<a id="operatingProfitSkuFeeUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入广告费</a>
			<a id="operatingProfitSkuFeeExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
			<a id="operatingProfitSkuFeeExportAllLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出没有参与运算的sku</a>
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

<div id="operatingProfitSkuFee-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="operatingProfitSkuFeeSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="operatingProfitSkuFeeCancelLinkbutton">关闭</a>
</div>

<div id="operatingProfitSkuFeeDialog" class="easyui-dialog" title="编辑广告费" closed="true"
	style="width: 300px; height: 200px; padding: 10px;" buttons="#operatingProfitSkuFee-buttons" modal="true">
	<form id="operatingProfitSkuFeeForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>国家：</td>
					<td>
						<input type="text" name="country" class="easyui-textbox" style="width: 150px;" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td>sku：</td>
					<td>
						<input type="text" name="sku" class="easyui-textbox" style="width: 150px;" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td>广告费：</td>
					<td>
						<input type="text" name="fee" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
