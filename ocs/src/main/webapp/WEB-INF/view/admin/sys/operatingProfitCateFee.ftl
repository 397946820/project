<@FTL.admin id="operatingProfitCateFeeList" title="亚马逊SKU广告费" 
add_script_files=['admin/sys/operatingProfitCateFee.js','admin/sys/date.js','admin/taximeter/common.js']>

<div data-options="region:'center',border:false">
	<table id="operatingProfitCateFeeDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/operatingProfitCateFee/findAll',
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
                {field: 'category', title: '类目',sortable:true},
                {field: 'advertisingFeeGoogle', title: '广告费-google',sortable:true},
                {field: 'advertisingFeeBing', title: '广告费-BING',sortable:true},
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
        toolbar:'#operatingProfitCateFeeToolbar'">
	</table>
</div>


<div id="operatingProfitFeeCate" class="easyui-dialog" title="推广费" closed="true" style="width:800px;height:550px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#operatingProfitFeeCate').dialog('close');
					}
				}]">
	<table id="operatingProfitFeeCateDataGrid" class="easyui-datagrid"
		data-options="
           url:'',
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
                {field: 'category', title: '类目',sortable:true},
                {field: 'promotionFee', title: '推广费-市场部',sortable:true},
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
        toolbar:'#operatingProfitFeeCateToolbar'">
	</table>
</div>

<div id="operatingProfitFeeCateToolbar">
	<div style="height:40px;">
		<form id="feeCateSearchForm">
			<table style="float:left; min-width:600px;">
				<tr style="min-width:600px;">
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
					<td><label>类目：</label></td>
					<td>
						<input name="category" class="easyui-textbox" style="width: 150px">
					</td>
					<td><label>年月：</label></td>
					<td>
						<input id="date1" name="monthOfYear" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 180, panelAlign: 'right', editable: false">
					</td>
					<td>
						<a id="queryFeeCate" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="resetFeeCate" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<hr/>
	<div>
		<div>
			<a id="operatingProfitFeeCateUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入费用</a>
			<a id="operatingProfitFeeCateExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
		</div>
	</div>
</div>

<div id="operatingProfitCateFeeToolbar">
	<div style="height:40px;">
		<form id="cateFeeSearchForm">
			<table style="float:left; min-width:800px;">
				<tr style="min-width:800px;">
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
					<td><label>类目：</label></td>
					<td>
						<input name="category" class="easyui-textbox" style="width: 150px">
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
			<a id="operatingProfitCateFeeUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入分类费用</a>
			<a id="operatingProfitCateFeeExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
			<a id="operatingProfitFeeCateLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-readed" plain="true">推广费费用</a>
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

<div id="operatingProfitCateFee-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="operatingProfitCateFeeSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="operatingProfitCateFeeCancelLinkbutton">关闭</a>
</div>

<div id="operatingProfitCateFeeDialog" class="easyui-dialog" title="编辑广告费" closed="true"
	style="width: 500px; height: 300px; padding: 10px;" buttons="#operatingProfitCateFee-buttons" modal="true">
	<form id="operatingProfitCateFeeForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>国家：</td>
					<td>
						<select id="country" name="country" class="easyui-combobox" style="width: 150px;" disabled="true">
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
					<td>类目：</td>
					<td>
						<input type="text" name="category" class="easyui-textbox" style="width: 150px;" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td>广告费-google：</td>
					<td>
						<input type="text" name="advertisingFeeGoogle" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>广告费-BING：</td>
					<td>
						<input type="text" name="advertisingFeeBing" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
