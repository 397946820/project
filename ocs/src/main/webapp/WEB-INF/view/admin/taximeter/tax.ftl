<@FTL.admin id="taxList" title="税率管理"
add_script_files=['admin/taximeter/tax.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="taxDataGrid" class="easyui-dataGrid"
		data-options="
           url:'${FTL.X.global_domain}/tax/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'countryId', title: '国家',sortable:true},
                {field: 'type', title: '关税类型',sortable:true},
                {field: 'vat', title: '销价增值税',sortable:true},
                {field: 'afFluctuation', title: '空运关税浮动',sortable:true},
                {field: 'sfFluctuation', title: '海运关税浮动',sortable:true},
                {field: 'coFluctuation', title: '快递关税浮动',sortable:true},
                {field: 'clearCoefficient', title: '清关系数',sortable:true},
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
        toolbar:'#taxToolbar'">
	</table>
</div>
<div id="taxToolbar">
<div style="padding:10px;">
	<form id="tax_Form">
		<table style="float:left; min-width:1000px;">
			<tr style="min-width:1000px;">
				<td align="right"><label>国家:</label></td>
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
				<td align="right" style="padding-left:5px;"><label>关税类型:</label></td>
				<td>
					<select id="type_" class="easyui-combobox" style="width: 150px;" editable="false">
						<option value="">-- 请选择 --</option>
						<option value="duty">Duty</option>
						<option value="vat">Vat</option>
						<option value="duty & tax">Duty & Tax</option>
						<option value="duty & vat">Duty & VAT</option>
					</select>
				</td>
				<td align="right" style="padding-left:5px;"><label>销价增值税:</label></td>
				<td>
					<input type="text" id="vat" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;"><label>创建时间:</label> </td>
				<td>
					<input type="text" id="cstarttime"  class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="cendtime" class="easyui-datebox" style="width: 150px;" editable="false" data-options="validType:'checkDate'"/>
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td align="right"><label>空运关税浮动:</label>  </td>
				<td>
					<input type="text" id="afFluctuation" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;"><label>海运关税浮动:</label>  </td>
				<td>
					<input type="text" id="sfFluctuation" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;"><label>快递关税浮动:</label>  </td>
				<td>
					<input type="text" id="coFluctuation" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;"><label>更新时间:</label> </td>
				<td>
					<input type="text" id="ustarttime"  class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="uendtime" class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/>
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td align="right"><label>清关系数:</label></td>
				<td>
					<input type="text" id="clearCoefficient" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td></td>
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
		<a id="taxAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a id="taxUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
		
		<select id="template" class="easyui-combobox" style="width: 150px" editable="false">
			<option value="">导出全部</option>
			<option value="template">导出模板</option>
		</select>
		<a id="taxExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
	</div>
</div>
</div>
<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="taxSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="taxCancelLinkbutton">关闭</a>
</div>

<div id="uploadDialog" class="easyui-dialog" title="货币汇率导入" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">  
	  		 选择文件：<input id="file" type="file" style="width:250px" />  
	       　　	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="height:20px" id="upload">导入</a> 　
	</form>
</div>

<div id="taxDialog" class="easyui-dialog" title="添加/编辑税率" closed="true"
	style="width: 500px; height: 300px; padding: 10px;" buttons="#rate-buttons">
	<form id="taxForm" action="${FTL.X.global_domain}/tax/saveEdit" method="post">
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
					<td>关税类型：</td>
					<td>
						<select id="type" name="type" class="easyui-combobox" style="width: 200px;" editable="false">
							<option value="duty">Duty</option>
							<option value="vat">Vat</option>
							<option value="duty & tax">Duty & Tax</option>
							<option value="duty & vat">Duty & VAT</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>增值税：</td>
					<td>
						<input type="text" name="vat" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>空运关税浮动：</td>
					<td>
						<input type="text" name="afFluctuation" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>海运关税浮动：</td>
					<td>
						<input type="text" name="sfFluctuation" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>快递关税浮动：</td>
					<td>
						<input type="text" name="coFluctuation" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>清关系数：</td>
					<td>
						<input type="text" name="clearCoefficient" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
