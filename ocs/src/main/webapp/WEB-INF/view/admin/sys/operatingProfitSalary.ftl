<@FTL.admin id="operatingProfitSalaryList" title="工资费用" 
add_script_files=['admin/sys/operatingProfitSalary.js','admin/sys/date.js','admin/taximeter/common.js']>

<div data-options="region:'center',border:false">
	<table id="operatingProfitSalaryDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/operatingProfitSalary/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'department', title: '部门',sortable:true},
                {field: 'personnel', title: '人员',sortable:true},
                {field: 'wageBonuses', title: '工资奖金',sortable:true},
                {field: 'accumulationFund', title: '公积金',sortable:true},
                {field: 'socialSecurity', title: '社保',sortable:true},
                {field: 'individualIncomeTax', title: '个人所得税',sortable:true},
                {field: 'otherFee', title: '其他费用',sortable:true},
                {field: 'monthOfYear', title: '年月',sortable:true},
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
        toolbar:'#operatingProfitSalaryToolbar'">
	</table>
</div>

<div id="operatingProfitSalaryToolbar">
	<div style="height:40px;">
		<form id="salarySearchForm">
			<table style="float:left; min-width:400px;">
				<tr style="min-width:400px;">
					<td><label>年月：</label></td>
					<td>
						<input id="date" name="monthOfYear" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 180, panelAlign: 'right', editable: false">
					</td>
					<td>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<hr/>
	<div>
		<div>
			<a id="operatingProfitSalaryUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入工资费用</a>
			<a id="operatingProfitSalaryExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
		</div>
	</div>
</div>

<div id="uploadDialog" class="easyui-dialog" title="文件导入" closed="true" style="width: 450px; height: 200px; padding: 10px;" modal="true">
	<form id="uploadForm" method="post" enctype="multipart/form-data">
		<input class="easyui-filebox" name="file" id="file" data-options="prompt:'请选择文件...'" buttonText="选择文件"
			style="height: 30px; width: 350px;"> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="height: 30px" id="upload">导入</a>
	</form>
	<hr>
	<div id="messages" class="easyui-panel" style="width:410px;height:100px;color: red;"  closed="true">
		<ul id="showMessages_">
		</ul>
	</div>
</div>

<div id="operatingProfitSalary-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="operatingProfitSalarySaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="operatingProfitSalaryCancelLinkbutton">关闭</a>
</div>

<div id="operatingProfitSalaryDialog" class="easyui-dialog" title="编辑广告费" closed="true"
	style="width: 500px; height: 375px; padding: 10px;" buttons="#operatingProfitSalary-buttons" modal="true">
	<form id="operatingProfitSalaryForm" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>部门：</td>
					<td>
						<input type="text" name="department" class="easyui-textbox" style="width: 150px;" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td>人员：</td>
					<td>
						<input type="text" name="personnel" class="easyui-textbox" style="width: 150px;" disabled="true"/>
					</td>
				</tr>
				<tr>
					<td>工资奖金：</td>
					<td>
						<input type="text" name="wageBonuses" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>公积金：</td>
					<td>
						<input type="text" name="accumulationFund" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>社保：</td>
					<td>
						<input type="text" name="socialSecurity" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>个人所得税：</td>
					<td>
						<input type="text" name="individualIncomeTax" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
				<tr>
					<td>其他费用：</td>
					<td>
						<input type="text" name="otherFee" class="easyui-numberbox" style="width: 150px;" data-options="min:0,precision:4" required="required"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
