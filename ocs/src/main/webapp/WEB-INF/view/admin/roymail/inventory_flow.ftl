<@FTL.admin id="inventoryFlowList" title="UK库存流水列表" add_script_files=['admin/roymail/inventory_flow.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
    <table id="inventoryFlowDatagrid" style="height:100%" class="easyui-datagrid">
    </table>
</div>
<div id="uploadDialog" class="easyui-dialog" title="UK流水导入" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">
		选择文件：<input id="file" type="file" style="width: 250px" /> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-save" style="height: 20px" id="actionUpload">导入</a>
	</form>
</div>
<div id="inventoryFlowToolbar">
	<div style="padding:10px;">
		<form id="inventoryFlow_Form">
			<table style="float:left;">
				<tr style="min-width:1000px;">
					<td align="right"><label>SKU:</label></td>
					<td>
						<input type="text" name="sku" class="easyui-textbox" style="width: 120px;"/>
					</td>
					<td align="right"><label>流水类型:</label></td>
					<td>
						<select class="easyui-combobox" name="bType" style="width: 120px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="R">R--良品转入</option>
							<option value="C">C--客户良品退货</option>
							<option value="A">A--亚马逊转入</option>
							<option value="S">S--盘点</option>
							<option value="O">O--订单出库</option>
							<option value="T">T--转出给亚马逊</option>
						</select>
					</td>
					<td align="right"><label>业务单号:</label></td>
					<td>
						<input type="text" name="buOrder" class="easyui-textbox" style="width: 120px;"/>
					</td>
					<td align="right" style="padding-left:0;"><label>流水时间:</label></td>
					<td>
						<input type="text" name="flowStarTime" class="easyui-datebox" style="width: 100px;" editable="false" data-options="validType:'checkDate'" /> - 
						<input type="text" name="flowEndTime" class="easyui-datebox" style="width: 100px;" editable="false" data-options="validType:'checkDate'" />
					</td>
					<td align="right" style="padding-left:0;"><label>流水导入时间:</label></td>
					<td>
						<input type="text" name="uploadStarTime" class="easyui-datebox" style="width: 100px;" editable="false" data-options="validType:'checkDate'" /> - 
						<input type="text" name="uploadEndTime" class="easyui-datebox" style="width: 100px;" editable="false" data-options="validType:'checkDate'" />
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
		 <a id="inventoryFlowUploadLinkbutton" href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
			<a id="inventoryFlowExportLinkbutton" href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
		</div>
	</div>
</div>
</@FTL.admin>