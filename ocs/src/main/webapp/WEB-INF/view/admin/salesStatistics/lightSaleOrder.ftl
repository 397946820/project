<#import "common/saleOrder.ftl" as saleOrder />
<@FTL.admin id="lightSaleOrder" title="官网销售订单"
add_script_files=['admin/salesStatistics/lightSaleOrder.js','admin/salesStatistics/saleOrderRefund.js']>

<div id="lightSaleOrderToolbar">
	<div>
		<form id="lightSaleOrderSearchForm">
			<table >
				<tr style="min-width: 600px;">
					<td><label>订单号:</label></td>
					<td><input type="text" name="orderId" class="easyui-textbox" style="width: 150px;" /></td>
					<td><label>快递方式:</label></td>
					<td><input type="text" name="shippingDescription" class="easyui-textbox" style="width: 150px;"/></td>
				</tr>
				
				<tr style="min-width: 600px;">
					<td><label>买家姓名:</label></td>
					<td><input type="text" name="name" class="easyui-textbox" style="width: 150px;" /></td>
					<td><label>国家:</label></td>
					<td>
						<select name="platform" class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false">
							<option value="" ></option>
							<option value="US" >US</option>
							<option value="UK" >UK</option>
							<option value="DE" >DE</option>
						</select>
					</td>
					<td align="right">
						<a id="lightSaleOrderQuery" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="lightSaleOrderReset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div>
		<a id="btn_exportUKNoShipOrders" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">未发货订单导出(UK)</a>
		<a id="btn_beforeMarkShipment" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true">标记发货</a>
		<a id="btn_beforeUploadTrackNo" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">批量上传跟踪号</a>
		<a id="btn_beforeUploadRecord" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true">上传记录</a>
	</div>
</div>
<table id="lightSaleOrderdatagrid" style="width: 100%;"></table>
<@saleOrder.saleOrderRefund></@>
<div id="dialog_tn_upload_record" class="easyui-dialog" title="上传记录" style="width:930px;height:500px;"data-options="iconCls:'icon-save',
	resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#upload_record_btns'" >
	<div class="upload-record-filter">
		<form id="form_upload_record_filter">
			<table>
				<tr>
					<td><label>上传状态：</label></td>
					<td>
						<select id="filter_tnUploadStatus" class="easyui-combobox" data-options="panelHeight: 120" name="tnUploadStatus">
							<option value="">--请选择--</option>
							<option value="waiting">等待上传</option>
							<option value="uploaded">上传成功</option>
							<option value="failed">上传失败</option>
						</select>
					</td>
					<td><label>OrderId：</label></td>
					<td>
						<input class="easyui-textbox placeholder" name="orderId" style="width: 150px;" />
					</td>
					<td class="title"><label>跟踪号导入时段：</label></td>
					<td colspan="3">
						<input type="text" id="filter_startTnInitAt" name="startTnInitAt" class="easyui-datebox" style="width: 115px;"/> 
						~ 
						<input type="text" id="filter_endTnInitAt" name="endTnInitAt" class="easyui-datebox" style="width: 115px;" />
					</td>
					<td class="oper-btn">
						<div>
							<a href="javascript:void(0);" class="easyui-linkbutton reset" iconCls="icon-clear">重置</a>
							<a href="javascript:void(0);" class="easyui-linkbutton search" iconCls="icon-search">搜索</a>
							<a href="javascript:void(0);" class="easyui-linkbutton export" iconCls="icon-redo">导出</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<table id="dg_light_upload_record" class="easyui-datagrid" style="width: 100%; height: 90%;"></table>
</div>
<div id="upload_record_btns" style="width: 100%;text-align:right;">
	<a href="javascript:void(0);" id="btn_upload_record_close" class="easyui-linkbutton" iconCls="icon-clear" >关闭</a>
</div>
<div id="dialog_fileupload" class="easyui-dialog" title="文件上传" style="width:400px;height:215px;display:none;"data-options="iconCls:'icon-save',
	resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
    <form id="form_fileupload" enctype="multipart/form-data" method="POST">
	    <div style="margin-bottom:20px;width:80%;padding-left: 35px;margin-top: 40px;">
			<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">站点：</div>
			<select class="easyui-combobox" id="select_site" panelHeight="auto" style="width: 120px;" editable="false">
				<option value="UK">UK</option>
				<!-- <option value="DE">DE</option> -->
				<!-- <option value="OS">OS</option> -->
			</select>
			<br clear="all"/>
			<br/>
			<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">文件：</div>
			<input id="file_path" name="file" data-options="prompt:'Choose a file...'" style="width:80%">
		</div>
		<div style="width:80%;padding-left: 35px;">
			<a href="javascript:void(0);"  id="btn_fileupload" class="easyui-linkbutton" style="width:100%">上传数据</a>
		</div>
	</form>
</div>
<div id="dialog_fileupload_result" class="easyui-dialog" title="导入结果" style="width:400px;height:200px;"data-options="iconCls:'icon-save',
	resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
   <div>
   		<ul class="error"></ul>
   </div>
</div>
<div id="lightSaleOrderSaleTranNumber" class="easyui-dialog" title="上传跟踪号" closed="true" style="width:650px;height:280px;" 
	data-options="modal:true,buttons: [{
				text:'关闭',
				handler:function(){
					$('#lightSaleOrderSaleTranNumber').window('close');
				}
			}]">
	<table id="lightSaleOrderSaleTranNumberDatagrid" class="easyui-datagrid" style="width:100%;height:100%">
		
	</table>
</div>

<div id="lightSaleOrderDetial" class="easyui-dialog" title="订单详情" closed="true" style="width:1000px;height:auto;padding-bottom:50px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#lightSaleOrderDetial').window('close');
					}
				}]">
		
		<div class="transadress" style="width:500px; height:150px;float:left;">
			<div style="padding:0 30px;float:left;"><p style="font-weight:300;">运输地址</p></div>
			<div style="padding:10px;float:left;line-height:20px;">
				<span id="name"></span><br/>
				<span id="region"></span><br/>
				<span id="city"></span><br/>
				<span id="street"></span><br/>
				<span id="postcode"></span><br/>
				<span id="telephone"></span><br/>
			</div>
		</div>
		
		<table id="lightSaleOrderDetailDatagrid" class="easyui-datagrid" style="width:100%;height:auto;">
			
		</table>
		<div class="total" style="padding:10px;padding-right:40px;float:right;font-weight:300;">合计:&nbsp<span id="t_total" style="">0</span></div>
</div>

</@FTL.admin>
