<#import "upload/upload.ftl" as fileUpload />
<@FTL.admin id="lightExpensesList" title="费用管理"
add_script_files=['admin/taximeter/lightExpenses.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="lightExpensesDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/lightExpenses/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'country', title: '国家',formatter:getCountry,sortable:true},
                {field: 'rentalFeeTotal', title: '仓租费总额',sortable:true},
                {field: 'sundryChargesTotal', title: '杂费总额',sortable:true},
                {field: 'costTotal', title: '耗材费总额',sortable:true},
                {field: 'labourCostTotal', title: '人工费总额',sortable:true},
                {field: 'feeTotal', title: '入库费总额',sortable:true},
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
        toolbar:'#lightExpensesToolbar'">
	</table>
</div>

<div id="lightExpensesToolbar">
	<!-- <div style="height:30px;">
		<form id="taxForm">
			<table style="float:left; min-width:1000px;">
				<tr style="min-width:1000px;">
					<td><label>国家：</label></td>
					<td>
						<select id="country_" class="easyui-combobox" style="width: 150px"  editable="false">
							<option value="">--请选择--</option>
							<option value="US">United States</option>
							<option value="UK">United Kingdom</option>
							<option value="DE">German</option>
						</select>
					</td>
					<td><label>sku：</label></td>
					<td>
						<input type="text" id="sku" class="easyui-textbox" style="width: 150px">
					</td>
					<td>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<hr> -->
	<div>
		<div>
			<a id="lightExpensesAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
			<!-- <a id="lightExpensesUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
			<a id="lightExpensesExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
			<a id="lightExpensesExportAllLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出全部</a> -->
		</div>
	</div>
</div>

<@fileUpload.upload></@>

<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="lightExpensesSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="lightExpensesCancelLinkbutton">关闭</a>
</div>

<div id="lightExpensesDialog" class="easyui-dialog" title="添加/编辑费用" closed="true"
	style="width: 500px; height: 300px; padding: 10px;" buttons="#rate-buttons" modal="true">
	<form id="lightExpensesForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>国家：</td>
					<td>
						<select id="country" name="country" class="easyui-combobox" style="width: 200px;" editable="false">
								<option value="US" >United States</option>
								<option value="UK" >United Kingdom</option>
								<option value="DE" >German</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>仓租费总额：</td>
					<td>
						<input type="text" name="rentalFeeTotal" class="easyui-numberbox" style="width: 200px;" data-options="precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>杂费总额：</td>
					<td>
						<input type="text" name="sundryChargesTotal" class="easyui-numberbox" style="width: 200px;" data-options="precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>耗材费总额：</td>
					<td>
						<input type="text" name="costTotal" class="easyui-numberbox" style="width: 200px;" data-options="precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>人工费总额：</td>
					<td>
						<input type="text" name="labourCostTotal" class="easyui-numberbox" style="width: 200px;" data-options="precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>入库费总额：</td>
					<td>
						<input type="text" name="feeTotal" class="easyui-numberbox" style="width: 200px;" data-options="precision:4" required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>