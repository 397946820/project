<@FTL.admin id="packageSku" title="SKU包装计算FBA费用" add_script_files=['admin/taximeter/packageSkuFBAlist.js']>

<div id="packageSkuFBAlist"  style="width:100%;height:100%" class="easyui-datagrid"> </div>
<div id="packageSkuFBAToolbar">

		<form id="ruleForm">
			<table style="min-width:1000px;">
				<tr style="min-width:1000px;">
					<td><label>SKU:</label></td>
					<td><input class="easyui-textbox" name="sku" style="width: 150px;" data-options="required:true"></td>
					<td><label>装箱数量:</label></td>
					<td><input class="easyui-numberbox" name="qty" style="width: 150px;" data-options="min:1,max:20,required:true"></td>
					<td><label>国家:</label></td>
					<td>
						<select id="countryId" name="countryId" class="easyui-combobox" style="width: 150px;" editable="false" data-options="required:true">
								<option value="US" >United States</option>
								<option value="GB" >United Kingdom</option>
								<option value="DE" >German</option>
								<option value="FR" >France</option>
								<option value="IT" >Italy</option>
								<option value="ES" >Spain</option>
								<option value="JP" >Japan</option>
								<option value="CA" >Canada</option>
								<option value="AU" >Australia</option>
						</select>
					</td>
					<td>
						<a id="jsbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" >开始计算</a>
					</td>
				</tr>
			</table>
		</form>

	<div>
		<a id="viewBestbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">查看最优</a>
	</div>

</div>

<div id="greatFbaWin" class="easyui-dialog" title="最优FBA包装" style="width:300px;height:400px;"
	    data-options="iconCls:'icon-save',resizable:false,closed:true,modal:true">

	 <table id="greatFbaList" style="padding-left: 20px;"></table>

</div>

</@FTL.admin>
