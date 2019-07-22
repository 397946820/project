<@FTL.admin id="vc" title="库存流水"
add_script_files=['admin/taximeter/datagrid-cellediting.js','admin/vc/vc_list.js']>
<div id="vcDatauploadDialog" class="easyui-dialog" title="类型更新" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">
		选择文件：<input id="file" type="file" style="width: 250px" /> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-save" style="height: 20px" id="actionUpload">导入</a>
	</form>
</div>
<div class="easyui-panel">
	<form id="form_search_vc">
		<table>
			<tr>
				<td class="title"><label>产品型号:</label></td>
				<td><input type="text" name="modelNumber" class="easyui-textbox" /></td>
				<td class="title"><label>订单号:</label></td>
				<td><input type="text" name="po" class="easyui-textbox" /></td>
				<td class="title"><label>发货时间：</label></td>
				<td><input class="easyui-datebox" type="text" name="startDate" /> ~ <input class="easyui-datebox" type="text" name="endDate" /></td>
				<td class="title">
					<div>
						<a href="javascript:void(0);" id="btn_vc_reset" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
						<a href="javascript:void(0);" id="btn_vc_search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<table id="dg_vc" style="width:100%; height:97%;" class="easyui-datagrid">        
</table>
</@FTL.admin>
