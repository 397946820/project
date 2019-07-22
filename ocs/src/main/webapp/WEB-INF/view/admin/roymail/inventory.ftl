<@FTL.admin id="inventoryList" title="UK库存列表" add_script_files=['admin/roymail/inventory.js']>
<div data-options="region:'center',border:false">
    <table id="inventoryDatagrid" style="height:100%" class="easyui-datagrid">
    </table>
</div>
<div id="uploadDialog" class="easyui-dialog" title="UK库存导入" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">
		选择文件：<input id="file" type="file" style="width: 250px" /> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-save" style="height: 20px" id="actionUpload">导入</a>
	</form>
</div>
<div id="inventoryToolbar">
	<div style="padding:10px;">
		<form id="inventory_Form">
			<table style="float:left;">
				<tr style="min-width:1000px;">
					<td align="right"><label>SKU:</label></td>
					<td>
						<input type="text" name="sku" class="easyui-textbox" style="width: 120px;"/>
					</td>
					<td align="right" style="padding-left:0;"><label>导入时间:</label></td>
					<td>
						<input type="text" name="inventoryUploadDate" class="easyui-datebox" style="width: 100px;" editable="false" data-options="validType:'checkDate'" /> 
					</td>
					<td align="right"> 
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
		<div>
		 <a id="inventoryUploadLinkbutton" href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
			<a id="inventoryExportLinkbutton" href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
		</div>
	</div>
</div>
</@FTL.admin>