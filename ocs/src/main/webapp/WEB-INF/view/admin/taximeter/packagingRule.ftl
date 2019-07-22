<#import "upload/upload.ftl" as fileUpload />
<@FTL.admin id="packagingRuleList" title="包装规则管理"
add_script_files=['admin/taximeter/packagingRule.js','admin/taximeter/common.js']>

<table id="packagingRuleDataGrid" style="width:100%;height:100%"></table>
		

<div id="packagingRuleToolbar">
	<div style="padding:10px;">
		<select id="type_" style="width: 150px" class="easyui-combobox" panelHeight="auto" editable="false">
			<option value="1">AU_包装重量规则</option>
			<option value="2">AU_FBA规则</option>
			<option value="3">US_包装重量规则</option>
		</select>
	</div>
	<hr>
	<div>
		<a id="packagingRuleAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a id="packagingRuleUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
		<a id="packagingRuleExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
		<a id="packagingRuleExportAllLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出全部</a>
	</div>
</div>

<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="packagingRuleSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="packagingRuleCancelLinkbutton">关闭</a>
</div>

<@fileUpload.upload></@>

<div id="packagingRuleDialog" class="easyui-dialog" title="添加/编辑规则" closed="true"
	style="width: 500px; height: 400px; padding: 10px;" buttons="#rate-buttons" modal="true">
	<form id="packagingRuleForm" method="post" action="">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr id="title_">
					<td>标题：</td>
					<td>
						<input type="text" name="title" id="title" class="easyui-textbox" style="width: 200px;"/>
					</td>
				</tr>
				<tr>
					<td>重量：</td>
					<td>
						<input type="text" name="weight" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr>
					<td>长：</td>
					<td>
						<input type="text" name="length" id="length" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr>
					<td>宽：</td>
					<td>
						<input type="text" name="width" id="width" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr>
					<td>高：</td>
					<td>
						<input type="text" name="height" id="height" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="packagingWeight_">
					<td>包装重量：</td>
					<td>
						<input type="text" name="packagingWeight" id="packagingWeight" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="girth_">
					<td>周长：</td>
					<td>
						<input type="text" name="girth" id="girth" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="regular_">
					<td>价格：</td>
					<td>
						<input type="text" name="regular" id="regular" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="discount_">
					<td>折扣价格：</td>
					<td>
						<input type="text" name="discount" id="discount" class="easyui-numberbox" style="width: 200px;" data-options="precision:4"/>
					</td>
				</tr>
				<tr id="isMonth_">
					<td valign="top">适用月份：</td>
					<td>
						<div>
			    			<input type="checkbox" value="1" name="isMonth" /><label>January</label>
		    			</div>			    		
			    		<div>
			    			<input type="checkbox" value="2" name="isMonth" /><label>February</label>
		    			</div>
			    		<div>
			    			<input type="checkbox" value="3" name="isMonth" /><label>March</label>
		    			</div>
			    		<div>
			    			<input type="checkbox" value="4" name="isMonth" /><label>April</label>
		    			</div>
			    		<div>
			    			<input type="checkbox" value="5" name="isMonth" /><label>May</label>
		    			</div>
			    		<div>
			    			<input type="checkbox" value="6" name="isMonth" /><label>June</label>
		    			</div>
			    		<div>
			    			<input type="checkbox" value="7" name="isMonth" /><label>July</label>
		    			</div>
			    		<div>
			    			<input type="checkbox" value="8" name="isMonth" /><label>August</label>
		    			</div>
			    		<div>
			    			<input type="checkbox" value="9" name="isMonth" /><label>September</label>
		    			</div>
			    		<div>
			    			<input type="checkbox" value="10" name="isMonth" /><label>October</label>
		    			</div>
			    		<div>
			    			<input type="checkbox" value="11" name="isMonth" /><label>November</label>
		    			</div>
			    		<div>
			    			<input type="checkbox" value="12" name="isMonth" /><label>December</label>
		    			</div>     
					</td>
				</tr>
				<tr>
					<td>排序：</td>
					<td>
						<input type="text" name="sortOrder" class="easyui-numberbox" style="width: 200px;" data-options="precision:0"/>
					</td>
				</tr>
				<tr>
					<td>类型：</td>
					<td>
						<select name="type" id="type" class="easyui-combobox" style="width: 200px" disabled="true"  editable="false">
							<option value="1">AU_包装重量规则</option>
							<option value="2">AU_FBA规则</option>
							<option value="3">US_包装重量规则</option>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
