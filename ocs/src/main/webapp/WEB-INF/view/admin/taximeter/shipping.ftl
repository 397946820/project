<@FTL.admin id="shippingList" title="运费管理"
add_script_files=['admin/taximeter/shipping.js','admin/taximeter/common.js']>
<div data-options="region:'center',border:false">
	<table id="shippingDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/shipping/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'countryId', title: '国家',sortable:true,formatter:changeCountry},
                {field: 'afShippingFee', title: '空运运费信息',formatter:shippingFee},
                {field: 'sfShippingFee', title: '海运运费信息',formatter:shippingFee},
                {field: 'coShippingFee', title: '快递运费信息',formatter:shippingFee},
                {field: 'storageCost', title: '仓储成本费',formatter:storageCost},
                {field: 'operatingFee', title: '操作费',sortable:true},
                {field: 'unexpectedLoss', title: '额外损坏费'},
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
        toolbar:'#shippingToolbar'">
	</table>
</div>
<div id="shippingToolbar">
<div style="padding:10px;">
	<form id="searchForm">
		<table style="float:left; min-width: 735px;">
			<tr style="min-width:1000px;">
				<td align="right" style="paddig-left:5px;"><label>国家:</label></td>
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
				<td align="right" style="paddig-left:5px;"><label>创建时间:</label></td>
				<td>
					<input type="text" id="cstarttime"  class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="cendtime" class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/>
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td align="right" style="paddig-left:5px;"><label>操作费:</label> </td>
				<td>
					<input type="text" id="operating_Fee" class="easyui-numberbox" style="width: 150px;" data-options="precision:4" />
				</td>
				<td align="right" style="paddig-left:5px;"><label>更新时间:</label></td>
				<td>
					<input type="text" id="ustarttime"  class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/> - 
					<input type="text" id="uendtime" class="easyui-datebox" style="width: 150px;"  editable="false" data-options="validType:'checkDate'"/>
				</td>
				<td width="200px;" align="right">
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
		<a id="shippingAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a id="shippingUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
		<select id="template" class="easyui-combobox" style="width: 150px" editable="false">
			<option value="">导出全部</option>
			<option value="template">导出模板</option>
		</select>
		<a id="shippingExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
	</div>
</div>
</div>
<div id="shipping-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" id="shippingSaveLinkbutton">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="shippingCancelLinkbutton">关闭</a>
</div>

<div id="uploadDialog" class="easyui-dialog" title="运费导入" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">  
	  		 选择文件：<input id="file" type="file" style="width:250px" />  
	       　　	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="height:20px" id="upload">导入</a> 　
	</form>
</div>

<div id="shippingDialog" class="easyui-dialog" title="添加/编辑运费" closed="true"
	style="width: 850px; height: 450px; padding: 10px;" buttons="#shipping-buttons">
	<form id="shippingForm" style="width:100%">
		<input type="hidden" name="entityId" id="entityId"/>
		<table align="left" id="addTable" style="width:800px">
			<tbody>
				<tr>
					<td width="100" >国家:</td>
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
					<td valign="top" width="100">空运运费信息 :</td>
					<td>
						<table id="subTable">
							<thead>
								<tr>
									<th>Cost</th>
									<th>Currency</th>
									<th>Cost Fluctuation</th>
									<th>Profit Rate</th>
									<th>Multi Profit Rate</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="af_">
								<tr>
									<td >
										<input type="text" name="cost" class="easyui-numberbox" required="required" data-options="precision:4" style="width:120px;">
									</td>
									<td >
										<input type="text" name="currency" class="easyui-textbox" required="required" style="width:120px;">
									</td>
									<td >
										<input type="text" name="cost_fluctuation" class="easyui-numberbox" required="required" data-options="precision:4" style="width:120px;">
									</td>
									<td >
										<input style="width:120px;" type="text" name="profit_rate" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<input style="width:120px;" type="text" name="multi_profit_rate" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td >
										<a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
									</td>
								</tr>
							</tbody>
							<tfoot>
					            <tr>
					                <td align="right" colspan="6"><button id="addTr" value="0" class="easyui-linkbutton" iconCls="icon-add">增加</button></td>
					            </tr>
				            </tfoot>
						</table>
					</td>
				</tr>
				<tr>
					<td valign="top" width="100">海运运费信息 :</td>
					<td>
						<table id="subTable">
							<thead>
								<tr>
									<th>Cost</th>
									<th>Currency</th>
									<th>Cost Fluctuation</th>
									<th>Profit Rate</th>
									<th>Multi Profit Rate</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="sf_">
								<tr>
									<td>
										<input type="text" style="width:120px;" name="cost" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<input type="text" style="width:120px;" name="currency" class="easyui-textbox" required="required">
									</td>
									<td>
										<input type="text" style="width:120px;" name="cost_fluctuation" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<input type="text" style="width:120px;" name="profit_rate" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<input type="text" style="width:120px;" name="multi_profit_rate" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
									</td>
								</tr>
							</tbody>
				            <tfoot>
					            <tr>
					               <td align="right" colspan="6"><button id="addTr" value="1" class="easyui-linkbutton" iconCls="icon-add">增加</button></td>
					            </tr>
				            </tfoot>
						</table>
					</td>
				</tr>
				<tr>
					<td  valign="top" width="100">快递运费信息 :</td>
					<td>
						<table id="subTable">
							<thead>
								<tr>
									<th>Cost</th>
									<th>Currency</th>
									<th>Cost Fluctuation</th>
									<th>Profit Rate</th>
									<th>Multi Profit Rate</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="co_">
								<tr>
									<td>
										<input style="width:120px;" type="text" name="cost" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<input style="width:120px;" type="text" name="currency" class="easyui-textbox" required="required">
									</td>
									<td>
										<input style="width:120px;" type="text" name="cost_fluctuation" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<input style="width:120px;" type="text" name="profit_rate" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<input style="width:120px;" type="text" name="multi_profit_rate" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
									</td>
								</tr>
							</tbody>
				            <tfoot>
					            <tr>
					                <td align="right" colspan="6"><button id="addTr" value="2" class="easyui-linkbutton" iconCls="icon-add">增加</button></td>
					            </tr>
				            </tfoot>
						</table>
					</td>
				</tr>
				<tr>
					<td valign="top" width="100">仓储成本费:</td>
					<td>
						<table id="subTable">
							<thead>
								<tr>
									<th>Is Month</th>
									<th>Standard Size</th>
									<th>Over Size</th>
									<th>Unit/Vat</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="cost_">
								<tr>
									<td>
										<input style="width:120px;" type="text" name="month" id="cost_month" class="easyui-textbox" required="required" >
									</td>
									<td>
										<input style="width:120px;" type="text" name="standard_size" id="cost_standard_size" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<input style="width:120px;" type="text" name="over_size" id="cost_over_size" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<input style="width:120px;" type="text" name="param_one" id="cost_param_one" class="easyui-numberbox" required="required" data-options="precision:4">
									</td>
									<td>
										<a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
									</td>
								</tr>
							</tbody>
							<tfoot>
					            <tr>
					                <td align="right" colspan="6"><button id="addTr" value="3" class="easyui-linkbutton" iconCls="icon-add">增加</button></td>
					            </tr>
				            </tfoot>
						</table>
					</td>
				</tr>
				<tr>
					<td width="100">操作费:</td>
					<td>
						<input type="text" name="operatingFee" id="operatingFee" data-options="precision:4"  class="easyui-numberbox" required="required"> 
					</td>
				</tr>
				<tr>
					<td valign="top" width="100">额外损坏费:</td>
					<td>
						<table id="subTable">
							<thead>
								<tr>
									<th>Amazon Category</th>
									<th>Referral Fee</th>
									<th>Minimum</th>
									<th></th>
								</tr>
							</thead>
							<tbody id="loss_">
								<tr>
									<td>
										<input style="width:120px;" type="text" name="amazonCategory"  class="easyui-textbox">
									</td>
									<td>
										<input style="width:120px;" type="text" name="referralFee" class="easyui-textbox">
									</td>
									<td>
										<input style="width:120px;" type="text" name="minimum"  class="easyui-textbox">
									</td>
									<td>									
										<a href="javascript:void(0)" id="delTr" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
									</td>
								</tr>
							</tbody>
							<tfoot>
					            <tr>
					                <td align="right" colspan="6"><button id="addTr" value="4" class="easyui-linkbutton" iconCls="icon-add">增加</button></td>
					            </tr>
				            </tfoot>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
