<@FTL.admin id="sil" title="库存流水"
add_script_files=['admin/taximeter/datagrid-cellediting.js','admin/fourpx/sil.js']>
<div id="codeTypeuploadDialog" class="easyui-dialog" title="类型更新" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">
		选择文件：<input id="file" type="file" style="width: 250px" /> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-save" style="height: 20px" id="actionUpload">导入</a>
	</form>
</div>
<div class="easyui-panel">
	<form id="form_search_sil">
		<table>
			<tr>
				<td class="title">业务区域：</td>
				<td>
					<select id="busarea" class="easyui-combobox" name="busarea" style="width:173px;">
						<option value="US">美国（US）</option>
						<option value="DE">欧洲（DE）</option>
					</select>
				</td>
				<td class="title">仓库代码：</td>
				<td>
					<select id="siteCombobox" class="easyui-combobox" name="warehouseCode" style="width:173px;">
						<option value="">--请选择--</option>
						<option value="USLA">美国洛杉矶仓（美西仓）</option>
						<option value="USNY">美东纽约仓（美东仓）</option>
					</select>
				</td>
				<td class="title"><label>SKU:</label></td>
				<td><input type="text" name="sku" class="easyui-textbox" /></td>
				<td class="title"><label>单据号:</label></td>
				<td><input type="text" name="refCode" class="easyui-textbox" /></td>
				<td class="title"><label>创建时间：</label></td>
				<td><input class="easyui-datebox" type="text"name="startDate" /> ~ <input class="easyui-datebox" type="text" name="endDate" /></td>
				<td class="title">
					<div>
						<a href="javascript:void(0);" id="btn_sil_reset" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
						<a href="javascript:void(0);" id="btn_sil_search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<table id="dg_sil" style="width:100%; height:93%;" class="easyui-datagrid">        
</table>
<div class="sil-unqualifieds-btns">
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', onClick: sil.action.closeUnqualifieds"> 关闭 </a>
</div>
<div id="sil_unqualifieds" class="easyui-dialog sil-unqualifieds" title="不合格货品详情" closed="true" style="width: 600px; height: 300px;" 
	data-options="modal: true, buttons: '.sil-unqualifieds-btns', top: 150, closable: false, resizable: true">
	<table id="dg_unqualifieds" style="width:100%; height:93%;" class="easyui-datagrid">        
	</table>
</div>
</@FTL.admin>
