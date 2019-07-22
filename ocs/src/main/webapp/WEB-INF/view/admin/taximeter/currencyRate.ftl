<@FTL.admin id="currencyRateList" title="货币汇率管理"
add_script_files=['admin/taximeter/currencyRate.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="currencyRateDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/currencyRate/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'countryId', title: '国家',sortable:true},
                {field: 'currencyCode', title: '货币代码',sortable:true},
                {field: 'from', title: '美元从'},
                {field: 'currencyRate', title: '交换到当前国家',sortable:true},
                {field: 'riskFactor', title: '风险因素',sortable:true},
                {field: 'currencySymbol', title: '货币符号',sortable:true},
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
        toolbar:'#currencyRateToolbar'">
	</table>
</div>
<div id="currencyRateToolbar">
<div style="padding:10px;">
	<form id="rateForm">
		<table style="float:left; min-width:1000px;">
			<tr style="min-width:1000px;">
				<td><label>国家:</label></td>
				<td>
					<select id="country_Id" class="easyui-combobox" style="width: 150px;" editable="false">
							<option value="" >-- 请选择 --</option>
							<option value="US" >United States</option>
							<option value="GB" >United Kingdom</option>
							<option value="DE" >German</option>
							<option value="FR" >France</option>
							<option value="IT" >Italy</option>
							<option value="ES" >Spain</option>
							<option value="JP" >Japan</option>
							<option value="CA" >Canada</option>
							<option value="AU" >Australia</option>
							<option value="CN" >China</option>
					</select>
				</td>
				<td><label>货币代码:</label></td>
				<td>
					<input type="text" id="currencyCode" class="easyui-textbox" style="width: 150px;" />
				</td>
				<td><label>创建时间:</label></td>
				<td>
					<input type="text" id="cstarttime"  class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="cendtime" class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'"/>
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td><label>货币符号:</label></td>
				<td>
					<input type="text" id="currencySymbol" class="easyui-textbox" style="width: 150px;" />
				</td>
				<td><label>风险因素:</label></td>
				<td>
					<input type="text" id="riskFactor" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td><label>更新时间:</label></td>
				<td>
					<input type="text" id="ustarttime"  class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="uendtime" class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/>
				</td>
				<td width="200" align="right">
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
		<a id="currencyRateAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a id="currencyRateUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
		<select id="template" class="easyui-combobox" style="width: 150px;" editable="false">
			<option value="">导出全部</option>
			<option value="template">导出模板</option>
		</select>
		<a id="currencyRateExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
	</div>
</div>
</div>
<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="currencyRateSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="currencyRateCancelLinkbutton">关闭</a>
</div>

<div id="uploadDialog" class="easyui-dialog" title="货币汇率导入" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">  
	  		 选择文件：<input id="file" type="file" style="width:250px" />  
	       　　	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="height:20px" id="upload">导入</a> 　
	</form>
</div>

<div id="currencyRateDialog" class="easyui-dialog" title="添加/编辑货币汇率" closed="true"
	style="width: 500px; height: 300px; padding: 10px;" buttons="#rate-buttons">
	<form id="currencyRateForm" action="${FTL.X.global_domain}/currencyRate/saveEdit" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>国家：</td>
					<td>
						<select id="countryId" name="countryId" class="easyui-combobox" style="width: 200px;" editable="false">
							<option value="US">United States</option>
							<option value="GB">United Kingdom</option>
							<option value="DE">German</option>
							<option value="FR">France</option>
							<option value="IT">Italy</option>
							<option value="ES">Spain</option>
							<option value="JP">Japan</option>
							<option value="CA">Canada</option>
							<option value="AU" >Australia</option>
							<option value="CN">China</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>货币代码：</td>
					<td>
						<input type="text" name="currencyCode" class="easyui-textbox" style="width: 200px;" required="required"/>
					</td>
				</tr>
				<tr>
					<td>货币符号：</td>
					<td>
						<input type="text" name="currencySymbol" class="easyui-textbox" style="width: 200px;" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>汇率：</td>
					<td>
						<input type="text" name="currencyRate" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>风险因素：</td>
					<td>
						<input type="text" name="riskFactor" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>排序：</td>
					<td>
						<input type="text" name="sortOrder" class="easyui-numberbox" style="width: 200px;" data-options="required:true,min:1,precision:0"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
