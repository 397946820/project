<@FTL.admin id="productEntityList" title="产品管理"
add_script_files=['admin/taximeter/productEntity.js','admin/taximeter/common.js','admin/publication/bootstrap.min.js']
add_style_files=['index.css']>
<table id="productEntityDataGrid"  style="width:100%;height:100%"
            url="${FTL.X.global_domain}/productEntity/findAll"
            toolbar="#productEntityToolbar" pagination="true" idField="id"
            rownumbers="true" fitColumns="true" pageSize="50" fit="true" singleSelect="true"
            border="true" striped="true" data-options="onDblClickRow:doDblClickRow">
	 <thead>
        <tr>
        	<th data-options="field:'ck',checkbox:true"></th>
        	<@shiro.hasPermission name="CPGL_SKU">
        	<th field="sku" sortable="true">sku</th>
        	</@shiro.hasPermission>
        	<@shiro.hasPermission name="CPGL_VSKU">
        	<th field="vSku" sortable="true">sku版本</th>
        	</@shiro.hasPermission>
        	<@shiro.hasPermission name="CPGL_YHM">
            <th field="username" sortable="true">用户名</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_C">
            <th field="length" sortable="true">长(m)</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_G">
            <th field="width" sortable="true">宽(m)</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_G">
            <th field="height" sortable="true">高(m)</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_JZ">
            <th field="netWeight" sortable="true">净重(kg)</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_MZ">
            <th field="grossWeight" sortable="true">毛重(kg)</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_CB">
            <th field="price" sortable="true">成本</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_HB">
            <th field="currency" sortable="true">货币</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_ZXSL">
            <th field="packingQty" sortable="true">装箱数量</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_WXTJ">
            <th field="outerVolume" sortable="true">外箱体积</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_WXZL">
            <th field="outerWeight" sortable="true">外箱重量</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_SFDYGB">
            <th field="isMultiOne" sortable="true" formatter="getFlag">是否多一个包</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_SFPEU">
            <th field="isPeu" sortable="true" formatter="getFlag">是否PEU</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_CPZT">
            <th field="status" sortable="true" formatter="getStatus">产品状态</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_TSL">
            <th field="taxRebateRate" sortable="true">退税率</th>
          	</@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_LL">
            <th field="interestRate" sortable="true">利率</th>
           	</@shiro.hasPermission>
            <@shiro.hasPermission name="GPGL_CJSJ">
            <th field="createdAt" sortable="true" formatter="getTime">创建时间</th>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="CPGL_GXSJ">
            <th field="updatedAt" sortable="true" formatter="getTime">更新时间</th>
            </@shiro.hasPermission>
        </tr>
    </thead>
</table>
<div id="productEntityToolbar">
	<div style="padding:10px;">
		<form id="productEntity_Form">
			<table style="float:left;">
				<tr style="min-width:1000px;">
					<td align="right" style="padding-left:0;"><label>sku:</label></td>
					<td>
						<input type="text" id="sku_" class="easyui-textbox" style="width: 120px;" />
					</td>
					<td align="right"><label>用户名:</label></td>
					<td>
						<input type="text" id="username_" class="easyui-textbox" style="width: 120px;"/>
					</td>
					<td align="right"><label>长(m):</label></td>
					<td>
						<input type="text" id="length_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
					</td>
					<td align="right"><label>宽(m):</label></td>
					<td>
						<input type="text" id="width_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
					</td>
				</tr>
				<tr style="min-width:1000px;">
					<td align="right" style="padding-left:0;"><label>高(m):</label></td>
					<td>
						<input type="text" id="height_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
					</td>
					<td align="right"><label>净重(kg):</label> </td>
					<td>
						<input type="text" id="netWeight_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
					</td>
					<td align="right"><label>毛重(kg):</label> </td>
					<td>
						<input type="text" id="grossWeight_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
					</td>
					<td align="right"><label>成本:</label></td>
					<td>
						<input type="text" id="price_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
					</td>
				</tr>
				<tr style="min-width:1000px;">
					<td align="right"><label>装箱数量:</label> </td>
					<td>
						<input type="text" id="packingQty_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
					</td>
					<td align="right" style="padding-left:5px;"><label>外箱体积:</label></td>
					<td>
						<input type="text" id="outerVolume_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
					</td>
					<td align="right" style="padding-left:5px;"><label>外箱重量:</label></td>
					<td>
						<input type="text" id="outerWeight_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
					</td>
					<td align="right" style="padding-left:5px;"><label>创建时间:</label> </td>
					<td>
						<input type="text" id="cstarttime" class="easyui-datebox" style="width: 100px;" editable="false" data-options="validType:'checkDate'" /> - 
						<input type="text" id="cendtime" class="easyui-datebox" style="width: 100px;" editable="false" data-options="validType:'checkDate'" />
					</td>
				</tr>
				<tr style="min-width:1000px;">
					<td align="right"><label>是否多一个包:</label> </td>
					<td>
						<select class="easyui-combobox" id="isMultiOne_" style="width: 120px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
					<td align="right"><label>是否PEU:</label> </td>
					<td>
						<select class="easyui-combobox" id="isPeu_" style="width: 120px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</td>
					<td align="right"><label>产品状态:</label></td>
					<td>
						<select class="easyui-combobox" id="status_" style="width: 120px;" editable="false">
							<option value="">-- 请选择 --</option>
							<option value="0">未启用</option>
							<option value="1">启用</option>
							<option value="2">测试</option>
						</select>
					</td>
					<td align="right"><label>更新时间:</label> </td>
					<td>
						<input type="text" id="ustarttime" class="easyui-datebox" style="width: 100px;" editable="false" data-options="validType:'checkDate'" /> - 
						<input type="text" id="uendtime" class="easyui-datebox" style="width: 100px;" editable="false" data-options="validType:'checkDate'" />
					</td>
				</tr>
				<tr style="min-width:1000px;">
					<td align="right"><label>退税率:</label></td>
					<td>
						<input type="text" id="taxRebateRate_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
					</td>
					<td align="right"><label>利率:</label></td>
					<td>
						<input type="text" id="interestRate_" class="easyui-numberbox" style="width: 120px;" data-options="precision:4" />
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
			<a id="productEntityAddLinkbutton" href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> <a
				id="productEntityUploadLinkbutton" href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
			<a id="btnImportProductPrice" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入价格</a>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			<select id="template" class="easyui-combobox" style="width: 150px;" editable="false">
				<option value="">导出全部</option>
				<option value="template">导出模板</option>
			</select> 
			<a id="productEntityExportLinkbutton" href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-redo" plain="true">导出</a>
			<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" id="proInfoUploadBtn" plain="true">数据导入[<span style="color:red;">新</span>]</a>
			&nbsp;&nbsp;&nbsp;&nbsp; 
			<select id="templateNew" class="easyui-combobox" style="width: 150px;" editable="false">
				<option value="CW">财务模板</option>
				<option value="CP">产品模板</option>
			</select> 
			<a id="downloadModelExcel" href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-redo" plain="true">下载模板</a>
			<a id="modifyDiscontinue" href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改DIS状态</a>
		</div>
	</div>
</div>
<div id="rate-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-save" id="productEntitySaveLinkbutton">保存</a> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-cancel" id="productEntityCancelLinkbutton">关闭</a>
</div>

<div id="uploadDialog" class="easyui-dialog" title="产品导入" closed="true"
	style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">
		选择文件：<input id="file" type="file" style="width: 250px" /> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-save" style="height: 20px" id="upload">导入</a>
	</form>
</div>

<div id="productEntityDialog" class="easyui-dialog" title="添加/编辑产品信息"
	closed="true" style="width: 950px; height: 550px;over-flow:hidden;"
	buttons="#rate-buttons">
<div id="tb" style="height: 100%;background:#fff;padding:10px;width:880px;"><!--dialog toolbar的开始-->
	<form id="productEntityForm" method="post" >
		<input type="hidden" name="entityId" id="entityId" />
		<div class="product_wrap">
			<div class="title_wrap">
				<div class="field_title">产品</div>
			</div>
			<div class="group">
				<div class="inp_wrap">
					<label class="field_name sku">SKU</label> 
					<input type="text" class="easyui-textbox" name="sku" id="sku" required="required" style="width:120px;">
				</div>
				<div class="inp_wrap">
					<label class="field_name one">长</label> 
					<input type="text" class="easyui-numberbox" name="length" id="length" data-options="required:true,precision:4" style="width:120px;">
				</div>
				<div class="inp_wrap">
					<label class="field_name one">宽</label> 
					<input type="text" class="easyui-numberbox" name="width" id="width" data-options="required:true,precision:4" style="width:120px;">
				</div>
				<div class="inp_wrap">
					<label class="field_name one">高</label> 
					<input type="text" class="easyui-numberbox" name="height" id="height" data-options="required:true,precision:4" style="width:120px;">
				</div>
			</div>
			<div class="group">
				<div class="inp_wrap">
					<label class="field_name two">净重</label> 
					<input type="text" class="easyui-numberbox" name="netWeight" id="netWeight" data-options="required:true,precision:4" style="width:120px;">
				</div>
				<div class="inp_wrap">
					<label class="field_name two">毛重</label> 
					<input type="text" class="easyui-numberbox" name="grossWeight" id="grossWeight" data-options="required:true,precision:4" style="width:120px;">
				</div>
				<div class="inp_wrap">
					<label class="field_name four">装箱数量</label> 
					<input type="text" class="easyui-numberbox" name="packingQty" id="packingQty" data-options="required:true,precision:4" style="width:120px;">
				</div>
				<div class="inp_wrap">
					<label class="field_name four">外箱体积</label> 
					<input type="text" class="easyui-numberbox" name="outerVolume" id="outerVolume" data-options="required:true,precision:4" style="width:120px;">
				</div>
			</div>
			<div class="group">
				<div class="inp_wrap">
					<label class="field_name four">外箱重量</label> 
					<input type="text" class="easyui-numberbox" name="outerWeight" id="outerWeight" data-options="required:true,precision:4" style="width:120px;">
				</div>
				<div class="inp_wrap">
					<label class="field_name five">是否多一个包</label> 
					<select id="isMultiOne" class="easyui-combobox" style="width: 120px;" name="isMultiOne" editable="false" >
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</div>
				<div class="inp_wrap">
					<label class="field_name three peu">是否PEU</label> 
					<select id="isPeu" class="easyui-combobox" style="width: 120px;" name="isPeu" editable="false" >
						<option value="1">是</option>
						<option value="0">否</option>
					</select>
				</div>
				<div class="inp_wrap">
					<label class="field_name four">产品状态</label> 
					<select id="status" class="easyui-combobox" style="width: 120px;" name="status" editable="false">
						<option value="1">启用</option>
						<option value="0">未启用</option>
						<option value="2">测试</option>
					</select>
				</div>
			</div>
		</div>
		<!--product结束-->
		<!--产品成本开始-->
		<div class="product_wrap">
			<div class="title_wrap">
				<div class="field_title four">产品成本</div>
			</div>
			<div class="group">
				<div class="inp_wrap">
					<label class="field_name four">购买价格</label> 
					<input class="easyui-numberbox" type="text" name="price" id="price" data-options="required:true,precision:4" style="width:120px;">
				</div>
				<div class="inp_wrap">
					<label class="field_name four">购买货币</label> 
					<select id="currency" class="easyui-combobox" style="width: 120px;" name="currency" editable="false">
						<option value="RMB">RMB</option>
						<option value="CNY">CNY</option>
						<option value="USD">USD</option>
					</select>
				</div>
				<div class="inp_wrap">
					<label class="field_name three">退税率</label> 
					<input class="easyui-numberbox" type="text" name="taxRebateRate" id="taxRebateRate" data-options="required:true,precision:4" style="width:120px;">
				</div>
				<div class="inp_wrap">
					<label class="field_name two">利率</label> 
					<input class="easyui-numberbox" type="text" name="interestRate" id="interestRate" data-options="required:true,precision:4" style="width:120px;">
				</div>
			</div>
		</div>

		<!--产品成本结束-->
		<!--其他产品开始-->
		<div class="product_wrap">
			<div class="title_wrap">
				<div class="field_title">产品其他</div>
			</div>
		</div>
		
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
</div>	<!--dialog toolbar的结束-->		
			<table id="dg" class="easyui-datagrid" title=""
				style="width:auto;min-height:470px;overflow:auto"
				data-options="
								rownumbers:true,
								iconCls: 'icon-edit',
								singleSelect: false,
								toolbar: '#tb',
								onClickCell: onClickCell,
								onEndEdit: onEndEdit
							">
				<thead>
					<tr>
						<th data-options="field:'entityId',width:80,checkbox:'true'">
						<th
							data-options="field:'countryId',width:100,
										editor:{
											type:'combobox',
											options:{
												valueField:'country',
												textField:'countryname',
												url:'${FTL.X.global_domain}/assets/app/json/country.json',
												required:true
											}
										}">国家</th>
						<th
							data-options="field:'category',title:'产品分类',width:110,editor:{type:'validatebox',options:{required:true}}"></th>
						<th
							data-options="field:'turnoverRate',title:'库存周转率',width:110,editor:{type:'validatebox',options:{required:true,precision:4,min:0}}"></th>
						<th
							data-options="field:'qtyOrdered',title:'订单数量',width:110,editor:{type:'validatebox',options:{required:true,precision:4,min:0}}"></th>
						<th
							data-options="field:'averageMonth',title:'平均储存月份',width:110,editor:{type:'validatebox',options:{required:true,precision:4,min:0}}"></th>
						<th
							data-options="field:'unfulliableRate',title:'不可用率',width:110,editor:{type:'validatebox',options:{required:true,precision:4,min:0}}"></th>
						<th
							data-options="field:'replacementRate',title:'补发率',width:100,editor:{type:'validatebox',options:{required:true,precision:4,min:0}}"></th>
						<th
							data-options="field:'clearPrice',title:'清关价',width:100,editor:{type:'validatebox',options:{required:true,precision:4,min:0}}"></th>
						<th
							data-options="field:'amzFba',title:'亚马逊FBA费用',width:117,editor:{type:'validatebox',options:{required:true,precision:4,min:0}}"></th>
						<th
							data-options="field:'efnFee',title:'EFN费用',width:100,editor:{type:'validatebox',options:{required:true,precision:4,min:0}}"></th>
						<th
							data-options="field:'dutyRate',title:'关税税率',width:100,editor:{type:'validatebox',options:{required:true,precision:4,min:0}}"></th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>

			
		
	</form>
</div>


<div id="fileUpload" class="easyui-dialog" title="文件上传" style="width:400px;height:215px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	    <form id="ImportForm" enctype="multipart/form-data" method="POST">
		    <div style="margin-bottom:20px;width:80%;padding-left: 35px;margin-top: 40px;">
				<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">数据部门:</div>
				<select class="easyui-combobox" id="importSite" panelHeight="auto" style="width: 120px;" editable="false">
					<option value="CW">财务部</option>
					<option value="CP">产品部</option>
				</select>
				<br clear="all"/>
				<br/>
				<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">File:</div>
				<input id="fileUploadInput" name="file" data-options="prompt:'Choose a file...'" style="width:80%">
			</div>
			<div style="width:80%;padding-left: 35px;">
				<a href="javascript:void(0);"  id="uploadSubmit" class="easyui-linkbutton" style="width:100%">Upload</a>
			</div>
		</form>
	</div>
	<div id="saleTranImportMessageWin" class="easyui-dialog" title="导入结果" style="width:400px;height:200px;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	   <div>
	   		<ul id="saleTranImportErrorList"></ul>
	   </div>
	</div>
	
	<div id="disWin" class="easyui-dialog" title="Discontinue设置" style="width:300px;height:450px;"
	    data-options="iconCls:'icon-save',resizable:false,closed:true,modal:true,buttons:'#disWinButton'">
	    <div>
	    	<form id="skuDisForm">
	    		<div style="padding-bottom: 10px;padding-top: 10px;">
	    			<span id="disSkuName" style="font-weight:bold;padding-left: 100px;"></span>
	    			<input type="text" id="disSku" style="display: none;" name = "sku"/>
	    		</div>
	    	<table style="padding-left: 20px;">
	    		
	    		<tr>
	    			<td>平台:</td>
	    			<td>
	    				<select id="platform" class="easyui-combobox" name="platform" style="width:200px;">
						    <option value="amazon">亚马逊</option>
						    <option value="ebay">eBay</option>
						    <option value="light">官网</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td colspan="2" style="font-style: italic;font-weight: bold;padding-top: 5px;text-align: center;">是否discontinue？</td>
	    		</tr>
	    		<tr>
	    			<td>US:</td>
	    			<td>
	    				<select id="usIsDis" class="easyui-combobox" name="US" style="width:200px;">
	    				 <option value="-1">未设置</option>
						    <option value="1">是</option>
						    <option value="0">否</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>CA:</td>
	    			<td>
	    				<select id="caIsDis" class="easyui-combobox" name="CA" style="width:200px;">
	    				 <option value="-1">未设置</option>
						    <option value="1">是</option>
						    <option value="0">否</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>JP:</td>
	    			<td>
	    				<select id="jpIsDis" class="easyui-combobox" name="JP" style="width:200px;">
	    				 <option value="-1">未设置</option>
						    <option value="1">是</option>
						    <option value="0">否</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>DE:</td>
	    			<td>
	    				<select id="deIsDis" class="easyui-combobox" name="DE" style="width:200px;">
	    				 <option value="-1">未设置</option>
						    <option value="1">是</option>
						    <option value="0">否</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>UK:</td>
	    			<td>
	    				<select id="ukIsDis" class="easyui-combobox" name="UK" style="width:200px;">
	    					 <option value="-1">未设置</option>
						    <option value="1">是</option>
						    <option value="0">否</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>FR:</td>
	    			<td>
	    				<select id="frIsDis" class="easyui-combobox" name="FR" style="width:200px;">
	    				 <option value="-1">未设置</option>
						    <option value="1">是</option>
						    <option value="0">否</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>IT:</td>
	    			<td>
	    				<select id="itIsDis" class="easyui-combobox" name="IT" style="width:200px;">
	    					 <option value="-1">未设置</option>
						    <option value="1">是</option>
						    <option value="0">否</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>ES:</td>
	    			<td>
	    				<select id="esIsDis" class="easyui-combobox" name="ES" style="width:200px;">
						     <option value="-1">未设置</option>
						    <option value="1">是</option>
						    <option value="0">否</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>AU:</td>
	    			<td>
	    				<select id="auIsDis" class="easyui-combobox" name="AU" style="width:200px;">
						    <option value="-1">未设置</option>
						    <option value="1">是</option>
						    <option value="0">否</option>
						</select>
	    			</td>
	    		</tr>
	    	</table>
	    	</form>
	    </div>
	    <div id="disWinButton">
			<a href="javascript:void(0);" id="disOptSave" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true">Save</a>
			<a href="javascript:void(0);" id="disOptCancel"class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">Close</a>
		</div>
	</div>
</@FTL.admin>
