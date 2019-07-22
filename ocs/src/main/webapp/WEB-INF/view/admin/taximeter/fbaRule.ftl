<@FTL.admin id="fbaRuleList" title="fba规则管理"
add_script_files=['admin/taximeter/fbaRule.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="fbaRuleDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/fbaRule/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'countryId', title: '国家',sortable:true},
                {field: 'title', title: 'FBA标题',sortable:true},
                {field: 'handlingFee', title: '处理费',sortable:true},
                {field: 'pickpackFee', title: '包装费',sortable:true},
                {field: 'size', title: '尺寸',formatter:size},
                {field: 'flag', title: '标志',sortable:true},
                {field: 'condition', title: '条件',formatter:condition},
                {field: 'price', title: '价格',sortable:true},
                {field: 'other', title: '其他',formatter:other},
                {field: 'isMonth', title: '使用月份',sortable:true},
                {field: 'sortOrder', title: '排序',sortable:true},
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
        toolbar:'#fbaRuleToolbar'">
	</table>
</div>
<div id="fbaRuleToolbar">
<div style="padding:10px;">
	<form id="ruleForm">
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
				<td><label>标题:</label> </td>
				<td>
					<input type="text" id="title" class="easyui-textbox" style="width: 150px;" />
				</td>
				<td><label>处理费:</label> </td>
				<td>
					<input type="text" id="handlingFee" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td><label>创建时间:</label></td>
				<td>
					<input type="text" id="cstarttime"  class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="cendtime" class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/>
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td><label>包装费:</label></td>
				<td>
					<input type="text" id="pickpackFee" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td><label>标志:</label> </td>
				<td>
					<input type="text" id="flag" class="easyui-textbox" style="width: 150px;"/>
				</td>
				<td><label>价格:</label></td>
				<td>
					<input type="text" id="price" class="easyui-textbox" style="width: 150px;" />
				</td>
				<td><label>更新时间:</label></td>
				<td>
					<input type="text" id="ustarttime"  class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="uendtime" class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/>
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td><label>适用月份:</label> </td>
				<td>
					<input type="text" id="isMonth" class="easyui-textbox" style="width: 150px;" />
				</td>
				<td><label>排序:</label> </td>
				<td>
					<input type="text" id="sortOrder" class="easyui-numberbox" style="width: 150px;" data-options="min:1,precision:0"/>
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
		<a id="fbaRuleAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a id="fbaRuleUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
		<select id="template" class="easyui-combobox" style="width: 150px;" editable="false">
			<option value="">导出全部</option>
			<option value="template">导出模板</option>
		</select>
		<a id="fbaRuleExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
	</div>
</div>
</div>
<div id="fba-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="fbaRuleSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="fbaRuleCancelLinkbutton">关闭</a>
</div>

<div id="uploadDialog" class="easyui-dialog" title="FBA规则导入" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">  
	  		 选择文件：<input id="file" type="file" style="width:250px">  
	       　　	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="height:20px" id="upload">导入</a> 　
	</form>
</div>

<div id="fbaRuleDialog" class="easyui-dialog" title="添加/编辑FBA规则" closed="true"
	style="width: 500px; height: 400px; padding: 10px;" buttons="#fba-buttons">
	<form id="fbaRuleForm" action="${FTL.X.global_domain}/fbaRule/saveEdit" method="post">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="center">
			<tbody>
				<tr>
					<td>国家：</td>
					<td>
						<select id="countryId" name="countryId" class="easyui-combobox" style="width: 200px;" editable="false">
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
				</tr>
				<tr>
					<td>FBA标题：</td>
					<td>
						<input type="text" name="title" class="easyui-textbox" style="width: 200px;" required="required"/>
					</td>
				</tr>
				<tr>
					<td>处理费：</td>
					<td>
						<input type="text" name="handlingFee" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>包装费：</td>
					<td>
						<input type="text" name="pickpackFee" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>长：</td>
					<td>
						<input type="text" name="length" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>宽：</td>
					<td>
						<input type="text" name="width" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>高：</td>
					<td>
						<input type="text" name="height" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>标志：</td>
					<td>
						<input type="text" name="flag" class="easyui-textbox" style="width: 200px;" required="required"/>
					</td>
				</tr>
				<tr>
					<td>起始重量：</td>
					<td>
						<input type="text" name="fromWeight" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>结束重量：</td>
					<td>
						<input type="text" name="toWeight" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>最大重量：</td>
					<td>
						<input type="text" name="maxWeight" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>价格：</td>
					<td>
						<input type="text" name="price" class="easyui-textbox" style="width: 200px;" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<td>EFN费用：</td>
					<td>
						<input type="text" name="efnFee" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>FBA额外费用：</td>
					<td>
						<input type="text" name="extraFee" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
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
						<input type="text" name="sortOrder" class="easyui-numberbox" style="width: 200px;" data-options="required:true,min:1,precision:0"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
