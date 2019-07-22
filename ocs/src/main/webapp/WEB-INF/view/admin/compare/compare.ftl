<@FTL.admin id="compareDataList" title="平台数据对比"
add_script_files=['admin/compare/compare.js','admin/taximeter/common.js']>


<div id="compareToolbar">
	<div style="margin: 6px 33px;">
		<a id="importLinkButton" href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
	</div>
	<div id="importCompareDataDialog" class="easyui-dialog" title="导入平台数据" closed="true"
	style="width: 500px; padding: 10px;" modal="true">
		<form id="leReckonForm">
			平台：
			<select id="plateFormSource" class="easyui-combobox" name="source" style="width: 170px;" editable="false">
				<option value="">--请选择--</option>
				<option value="amazon">亚马逊</option>
				<option value="ebay">Ebay</option>
				<option value="light">官网</option>
			</select>
			帐号：
			<select id="importAccount" class="easyui-combobox" name="source" style="width: 170px;" editable="false">
				
			</select>
			<br />
			<div id="siteInfo" style="display:none">
				站点：
			<select id="importSite" class="easyui-combobox" name="site" style="width: 170px;" editable="false">
				<option value="">--请选择--</option>
				<option value="UK">UK</option>
				<option value="US">US</option>
				<option value="Germany">Germany</option>
			</select>
			</div>
			
			<p></p>
			选择文件：<input id="file" type="file" style="width: 160px" /> 
			<input type="button" style="height: 22px" id="importButton" value="上传" />
		</form>
		<br />
		<a id="compareExportLinkButton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出</a>
	</div>
</div>
</@FTL.admin>
