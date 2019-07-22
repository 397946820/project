<@FTL.admin id="productOtherList" title="产品其他管理"
add_script_files=['admin/taximeter/productOther.js','admin/taximeter/common.js']>
<table id="productOtherDataGrid"  style="width:100%;height:100%"
            url="${FTL.X.global_domain}/productOther/findAll"
            toolbar="#productOtherToolbar" pagination="true" idField="id"
            rownumbers="true" fitColumns="true" pageSize="50" fit="true" singleSelect="true"
            border="false" striped="true" data-options="onDblClickRow:doDblClickRow">
	 <thead>
        <tr>
        	<@shiro.hasPermission name="CPQTGL_GJ">
        	<th field="countryId" sortable="true">国家</th>
        	</@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_SKU">
            <th field="sku" sortable="true">sku</th>
            </@shiro.hasPermission>
        	<@shiro.hasPermission name="CPQTGL_YHM">
            <th field="username" sortable="true">用户名</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_CPFL">
            <th field="category" sortable="true">产品分类</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_KCZZL">
            <th field="turnoverRate" sortable="true">库存周转率</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_DDSL">
            <th field="qtyOrdered" sortable="true">订单数量</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_PJCCYF">
            <th field="averageMonth" sortable="true">平均存储月份</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_BKYL">
            <th field="unfulliableRate" sortable="true">不可用率</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_BFBL">
            <th field="replacementRate" sortable="true">补发比率</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_QGJ">
            <th field="clearPrice" sortable="true">清关价</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_YMXFBAFY">
            <th field="amzFba" sortable="true">亚马逊FBA费用</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_EFNFY">
            <th field="efnFee" sortable="true">EFN费用</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_GSSL">
            <th field="dutyRate" sortable="true">关税税率</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_CJSJ">
            <th field="createdAt" sortable="true" formatter="getTime">创建时间</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPQTGL_GXSJ">
            <th field="updatedAt" sortable="true" formatter="getTime">更新时间</th>
            </@shiro.hasPermission>
        </tr>
    </thead>
</table>
<div id="productOtherToolbar">
<div>
	<form id="productOther_Form" style="padding:10px;">
		<table>
			<tr>
				<td align="right">国家:</td>
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
							<option value="CN" >China</option>
					</select>
				</td>
				<td align="right" style="padding-left:5px;">sku: </td>
				<td style="vertical-align:middle; text-align:center;">
					<input type="text" id="sku_" class="easyui-textbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;">用户名:</td>
				<td style="vertical-align:middle; text-align:center;">
					<input type="text" id="username" class="easyui-textbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;">产品分类:</td>
				<td style="vertical-align:middle; text-align:center;">
					 <input type="text" id="category" class="easyui-textbox" style="width: 150px;" data-options="precision:4" />
				</td>
			</tr>
			<tr>
				<td align="right" style="padding-left:5px;">库存周转率:</td>
				<td>
					 <input type="text" id="turnoverRate" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;">订单数量:</td>
				<td style="vertical-align:middle; text-align:center;">
					 <input type="text" id="qtyOrdered" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;">平均储存月份:</td>
				<td style="vertical-align:middle; text-align:center;">
					 <input type="text" id="averageMonth" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;">不可用率:</td>
				<td style="vertical-align:middle; text-align:center;">
					 <input type="text" id="unfulliableRate" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
			</tr>
			<tr>
				<td align="right" style="padding-left:5px;">补发比率:</td>
				<td>
					<input type="text" id="replacementRate" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;">清关价:</td>
				<td style="vertical-align:middle; text-align:center;">
					<input type="text" id="clearPrice" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;">FBA费用: </td>
				<td style="vertical-align:middle; text-align:center;">
					<input type="text" id="amzFba" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;">EFN费用: </td>
				<td style="vertical-align:middle; text-align:center;">
					 <input type="text" id="efnFee" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				
			</tr>
		</table>
		<table>
			<tr>
				<td align="right" style="width: 64px;" >关税税率: </td>
				<td style="vertical-align:middle; text-align:center;">
					 <input type="text" id="dutyRate" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="padding-left:5px;">创建时间: </td>
				<td>
					<input type="text" id="cstarttime"  style="width:90px;"  class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="cendtime"  style="width:90px;" class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/>
				</td>
				<td align="right" style="padding-left:5px;">更新时间:</td>
				<td>
					<input type="text" id="ustarttime"  style="width:90px;" class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="uendtime"  style="width:90px;" class="easyui-datebox"  editable="false" data-options="validType:'checkDate'"/>
				</td>
				<td align="right" width="150">
					<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
					<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<hr>
<div>
	<div>
		<a id="productOtherUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
		<select id="template" class="easyui-combobox" style="width: 200px;padding-left:100px;" editable="false">
			<option value="">导出全部</option>
			<option value="template">导出模板</option>
		</select>
		<a id="productOtherExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
	</div>
</div>
</div>
<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="productOtherSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="productOtherCancelLinkbutton">关闭</a>
</div>

<div id="uploadDialog" class="easyui-dialog" title="产品其他导入" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">  
	  		 选择文件：<input id="file" type="file" style="width:250px" />  
	       　　	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="height:20px" id="upload">导入</a> 　
	</form>
</div>

<div id="productOtherDialog" class="easyui-dialog" title="编辑产品其他" closed="true"
	style="width: 600px; height: 500px; padding: 10px;" buttons="#rate-buttons">
	<form id="productOtherForm" action="${FTL.X.global_domain}/productOther/edit" method="post">
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
							<option value="CN">China</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>sku：</td>
					<td>
						<input type="text" id="sku" name="sku" class="easyui-textbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>产品分类：</td>
					<td>
						<input type="text" name="category" class="easyui-textbox" style="width: 200px;"/>
					</td>
				</tr>
				<tr>
					<td>库存周转率：</td>
					<td>
						<input type="text" name="turnoverRate" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>订单数量：</td>
					<td>
						<input type="text" name="qtyOrdered" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>平均存储月份：</td>
					<td>
						<input type="text" name="averageMonth" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>不可用率：</td>
					<td>
						<input type="text" name="unfulliableRate" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>补发比率：</td>
					<td>
						<input type="text" name="replacementRate" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>清关价：</td>
					<td>
						<input type="text" name="clearPrice" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>亚马逊FBA费用：</td>
					<td>
						<input type="text" name="amzFba" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>EFN费用：</td>
					<td>
						<input type="text" name="efnFee" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
				<tr>
					<td>关税税率：</td>
					<td>
						<input type="text" name="dutyRate" class="easyui-numberbox" style="width: 200px;" data-options="required:true,precision:4"/>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
