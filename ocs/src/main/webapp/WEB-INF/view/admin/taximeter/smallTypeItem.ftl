<#import "upload/upload.ftl" as fileUpload />
<@FTL.admin id="smallTypeItemList" title="类型与运输方式"
add_script_files=['admin/taximeter/smallTypeItem.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="smallTypeItemDataGrid"  class="easyui-datagrid"
		data-options="
           url:'${FTL.X.global_domain}/smallTypeItem/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'typeName', title: '类型名称'},
                {field: 'shippingType', title: '运输方式',sortable:true},
                {field: 'minWeight', title: '最小重量',sortable:true},
                {field: 'fromWeight', title: '开始重量',sortable:true},
                {field: 'toWeight', title: '结束重量',sortable:true},
                {field: 'operatingExpenses', title: '操作费/挂号费',sortable:true},
                {field: 'kilogramWeight', title: '公斤重',sortable:true},
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
        toolbar:'#smallTypeItemToolbar'">
	</table>
</div>

<div id="smallTypeItemToolbar">
	<div style="padding:10px;">
		<form id="smallTypeItemSearchForm">
			<table style="float:left; min-width:600px;">
				<tr style="min-width:600px;">
					<td><label>类型名称：</label></td>
					<td>
						<select id="typeName" name="typeName" class="easyui-combobox" style="width: 150px"  editable="false">
							
						</select>
					</td>
					<td><label>运输方式：</label></td>
					<td>
						<select id="shippingType" name="shippingType" class="easyui-combobox" style="width: 150px"  disabled="true" editable="false">
							
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
		<a id="smallTypeItemAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a id="smallTypeItemUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
		<a id="smallTypeItemExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
		<a id="smallTypeLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-readed" plain="true">小包类型</a>
		<a id="smallTariffRateLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-readed" plain="true">欧洲专线关税</a>
	</div>
</div>

<div id="smallTariffRateDialog" class="easyui-dialog" title="欧洲专线关税" closed="true" style="width:800px;height:550px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#smallTariffRateDialog').dialog('close');
					}
				}]">
		<table id="smallTariffRateDataGrid" style="width:100%;height:100%" class='easyui-datagrid'
			data-options="
	           url:'',
	           fitColumns:true,
	           columns: [
	            [
	                {field: 'fromWeight', title: '开始重量',sortable:true},
	                {field: 'toWeight', title: '结束重量',sortable:true},
	                {field: 'minimumDeclaredPrice', title: '最低申报价格',sortable:true},
	                {field: 'updatedAt', title: '更新时间',formatter:getTime,sortable:true}
	                
	            ]
	        ],
	        singleSelect: true,
	        rownumbers: true,
	        pagination: true,
	        pageSize: 50,
	        border:false,
	        fit:true,
	        toolbar:'#smallTariffRateToolbar'">
		</table>
</div>

<div id="smallTariffRateToolbar">
	<div>
		<a id="smallTariffRateExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
		<a id="smallTariffRateUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
	</div>
</div>

<div id="smallTypeDialog" class="easyui-dialog" title="小包类型" closed="true" style="width:800px;height:550px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#smallTypeDialog').dialog('close');
					}
				}]">
		<table id="smallTypeDataGrid" style="width:100%;height:100%" class='easyui-datagrid'
			data-options="
	           url:'',
	           fitColumns:true,
	           columns: [
	            [
	                {field: 'typeName', title: '类型名称',sortable:true},
	                {field: 'gst', title: 'GST',sortable:true},
	                {field: 'tariffRate', title: '关税',sortable:true},
	                {field: 'clearPriceAdjustmentRatio', title: '清关价调整比例',sortable:true},
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
	        toolbar:'#smallTypeToolbar'">
		</table>
</div>

<div id="smallTypeToolbar">
	<div>
		<a id="smallTypeExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
		<a id="smallTypeUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
	</div>
</div>

<div id="smallTypeItem-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="smallTypeItemSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="smallTypeItemCancelLinkbutton">关闭</a>
</div>

<div id="smallType-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="smallTypeSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="smallTypeCancelLinkbutton">关闭</a>
</div>

<div id="smallTariffRate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="smallTariffRateSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="smallTariffRateCancelLinkbutton">关闭</a>
</div>

<@fileUpload.upload></@>

<div id="smallTariffRateDialog_" class="easyui-dialog" title="编辑欧洲专线关税" closed="true"
	style="width: 500px; height: 300px; padding: 10px;" buttons="#smallTariffRate-buttons" modal="true">
	<form id="smallTariffRateForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>开始重量：</td>
					<td>
						<input type="text" name="fromWeight" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>结束重量：</td>
					<td>
						<input type="text" name="toWeight" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>最低申报价格：</td>
					<td>
						<input type="text" name="minimumDeclaredPrice" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<div id="smallTypeDialog_" class="easyui-dialog" title="编辑小包类型" closed="true"
	style="width: 500px; height: 300px; padding: 10px;" buttons="#smallType-buttons" modal="true">
	<form id="smallTypeForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>类型名称：</td>
					<td>
						<select name="typeName" id="typeName_1" class="easyui-combobox" data-options="required:true" style="width: 200px" disabled="true" editable="false">
						
						</select>
					</td>
				</tr>
				<tr>
					<td>GST：</td>
					<td>
						<input type="text" name="gst" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>关税：</td>
					<td>
						<input type="text" name="tariffRate" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>清关价调整比例：</td>
					<td>
						<input type="text" name="clearPriceAdjustmentRatio" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<div id="smallTypeItemDialog" class="easyui-dialog" title="添加/编辑运输方式" closed="true"
	style="width: 500px; height: 380px; padding: 10px;" buttons="#smallTypeItem-buttons" modal="true">
	<form id="smallTypeItemForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>类型名称：</td>
					<td>
						<select name="typeName" id="typeName_" class="easyui-combobox" data-options="required:true" style="width: 200px"  editable="false">
						
						</select>
					</td>
				</tr>
				<tr>
					<td>运输方式：</td>
					<td>
						<select name="shippingType" id="shippingType_"  disabled="true" class="easyui-combobox" data-options="required:true" style="width: 200px">
							
						</select>
					</td>
				</tr>
				<tr>
					<td>最小重量：</td>
					<td>
						<input type="text" name="minWeight" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>开始重量：</td>
					<td>
						<input type="text" name="fromWeight" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>结束重量：</td>
					<td>
						<input type="text" name="toWeight" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>操作费/挂号费：</td>
					<td>
						<input type="text" name="operatingExpenses" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
				<tr>
					<td>公斤重：</td>
					<td>
						<input type="text" name="kilogramWeight" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"  required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>