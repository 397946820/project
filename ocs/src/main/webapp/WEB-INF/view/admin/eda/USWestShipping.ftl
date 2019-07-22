<@FTL.admin id="usWestShipping" title="美国西部仓库发货管理"
add_script_files=['admin/eda/USWestShipping.js']>
<div class="easyui-panel" id="westShippingSearchParam-panel" >
		<form id="westShippingSearchParam">
		<table style="float:left;padding:10px;width: 100%;">
			
			<tr >
				<td><label style="">平台:</label></td>
				<td>
				<select class="easyui-combobox" name="platform"  style="width:150px;">
					    <option value=""></option>
					    <option value="ebay">ebay</option>
					    <option value="walmart">Walmart</option>
					    <option value="light">Lightting EVER</option>
					    <option value="amazon">Amazon</option>
			    </select>
				</td>
				<td><label style="">状态:</label></td>
				<td>
				<select class="easyui-combobox" name="status" style="width:150px;" >
					    <option value=""></option>
					    <option value="0">未发货</option>
					    <option value="1">已发货</option>
					    <option value="2">上传平台成功</option>
					    <option value="3">上传平台失败</option>
					    <option value="4">已取消</option>
					    <option value="5">已转东仓</option>
					    <option value="6">已手工分仓</option>
					    <option value="7">补发已完成</option>
			    </select>
				</td>
				<td><label style="">订单时间:</label></td>
				<td ><input type="text" name="timeStart"  class="easyui-datebox" style="width:150px;" />
				&nbsp;~&nbsp;
				<input type="text" name="timeEnd" style="float:right;width:150px;" class="easyui-datebox" />
				</td>
				
				<td><label style="">最后操作时间:</label></td>
				<td ><input type="text" name="optTtimeStart"  class="easyui-datebox" style="width:150px;" />
				&nbsp;~&nbsp;
				<input type="text" name="optTimeEnd" style="float:right;width:150px;" class="easyui-datebox" />
				</td>
				
				
			</tr>
			<tr >
				<td><label style="">平台订单ID:</label></td>
				<td><input type="text" name="orderId"  class="easyui-textbox" style="width:150px;"/>
				</td>
				<td><label style="">物流方式:</label></td>
				<td>
				<select class="easyui-combobox" name="CarrierServiceTypeCode" style="width:150px;" >
					    <option value=""></option>
					    <option value="USPS01">USPS</option>
					    <option value="USPS02">USPS Priority Mail</option>
					    <option value="FDX01">Fedex smart post</option>
					    <option value="FDX02">Fedex Ground</option>
			    </select>
				</td>
				<td><label style="">sku:</label></td>
				<td><input type="text" name="MerchantSKU"  class="easyui-textbox" style="width:150px;" />
				</td>
				<td  colspan="2">
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="westShippingReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="westShippingSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		<br clear="both"/>
		</form>
		<div>
	     	<a id ="modifyAddressBtn" href="javascript:void(0);" onclick = "" class="easyui-linkbutton" iconCls = "icon-edit" plain="true">修改发货地址</a>
	     	<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="orderExport" plain="true">未发货导出</a>
	     	<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" id="orderImport" plain="true">导入</a>
	     	<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="chooseOrderExport" plain="true">选择导出</a>
	     	<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="allOrderByComditionExport" plain="true">根据条件导出全部</a>
		</div>
	</div>
<table id="westShippingList"  style="width:100%;height:100%" class="easyui-datagrid">        
</table>

	<div id="fileUpload" class="easyui-dialog" title="文件上传" style="width:400px;height:200px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	    <form id="ImportForm" enctype="multipart/form-data" action="/ocs/excel/import/orderShippingWestImportService" method="POST">
		    <div style="margin-bottom:20px;width:80%;padding-left: 35px;margin-top: 40px;">
				<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">File:</div>
				<input id="fileUploadInput" name="file" data-options="prompt:'Choose a file...'" style="width:80%">
			</div>
			<div style="width:80%;padding-left: 35px;">
				<a href="javascript:void(0);"  id="uploadSubmit" class="easyui-linkbutton" style="width:100%">Upload</a>
			</div>
		</form>
	</div>
	
	
	<div id="westShippingAddressModifyWin" class="easyui-dialog" style="width:500px" title="编辑"
            closed="true" buttons="#dlg-buttons" data-options="modal:true">
        <form id="addressModifyfm" method="post" novalidate style="margin:0;padding:20px 50px">
			<div>
				<table cellpadding="5">
		    		<tr>
		    			<td>收件人:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressName" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>地址一:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressFieldOne" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>地址二:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressFieldTwo" data-options="" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>地址三:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressFieldThree" data-options="" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>城市:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressCity" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>省:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressStateOrRegion" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>国家:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressCountryCode" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		
		    		<tr>
		    			<td>邮编:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressPostalCode" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>电话:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressPhoneNumber" data-options="" style="width:250px;"></input></td>
		    		</tr>
		    		
	    		</table>
			</div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="usWestShipping.modifyAddress()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#westShippingAddressModifyWin').dialog('close')" style="width:90px">关闭</a>
    </div>
</@FTL.admin>
