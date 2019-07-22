<#import "upload/upload.ftl" as fileUpload />
<@FTL.admin id="handlingChargesList" title="US和DE费用维护"
add_script_files=['admin/taximeter/handlingCharges.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="handlingChargesDataGrid"  class="easyui-datagrid"
		data-options="
           url:'${FTL.X.global_domain}/handlingCharges/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'fromWeight', title: '起始重量',sortable:true},
                {field: 'toWeight', title: '结束重量',sortable:true},
                {field: 'cost', title: '费用',sortable:true},
                {field: 'accountingRules', title: '计费规则',sortable:true},
                {field: 'ultimateCost', title: '最高费用',sortable:true},
                {field: 'sortOrder', title: '排序',sortable:true},
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
        toolbar:'#handlingChargesToolbar'">
	</table>
</div>

<div id="handlingChargesToolbar">
	<div style="padding:10px;">
		<select id="type_" style="width: 150px" class="easyui-combobox" editable="false">
			<option value="1">US入库费</option>
			<option value="2">US出库费</option>
			<option value="3">US本地派送费</option>
			<option value="4">US仓租费</option>
			<option value="5">DE本地派送费</option>
		</select>
	</div>
	<hr>
	<div>
		<a id="handlingChargesAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a id="handlingChargesUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
		<a id="handlingChargesExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
		<a id="handlingChargesExportAllLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出全部</a>
	</div>
</div>
<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="handlingChargesSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="handlingChargesCancelLinkbutton">关闭</a>
</div>

<@fileUpload.upload></@>

<div id="handlingChargesDialog" class="easyui-dialog" title="添加/编辑费用规则" closed="true"
	style="width: 500px; height: 350px; padding: 10px;" buttons="#rate-buttons" modal="true">
	<form id="handlingChargesForm" method="post">
		<input type="hidden" name="id" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>起始重量：</td>
					<td>
						<input type="text" name="fromWeight" id="fromWeight" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:2"/>
					</td>
				</tr>
				<tr>
					<td>结束重量：</td>
					<td>
						<input type="text" name="toWeight" id="toWeight" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:2"/>
					</td>
				</tr>
				<tr>
					<td>费用：</td>
					<td>
						<input type="text" name="cost" id="cost" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:2"/>
					</td>
				</tr>
				<tr>
					<td>计费规则：</td>
					<td>
						<input type="text" name="accountingRules" id="accountingRules" class="easyui-textbox" style="width: 200px;"/>
					</td>
				</tr>
				<tr>
					<td>最高费用：</td>
					<td>
						<input type="text" name="ultimateCost" id="ultimateCost" class="easyui-numberbox" style="width: 200px;" data-options="precision:2"/>
					</td>
				</tr>
				<tr>
					<td>排序：</td>
					<td>
						<input type="text" name="sortOrder" id="sortOrder" class="easyui-numberbox" style="width: 200px;" data-options="required:true,min:1,precision:0"/>
					</td>
				</tr>
				<tr>
					<td>类型：</td>
					<td>
						<select name="type" id="type" class="easyui-combobox" style="width: 200px"  editable="false">
							<option value="1">US入库费</option>
							<option value="2">US出库费</option>
							<option value="3">US本地派送费</option>
							<option value="4">US仓租费</option>
							<option value="5">DE本地派送费</option>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>