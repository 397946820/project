<@FTL.admin id="deoutorder" title="德国仓出库订单数据"
add_script_files=['admin/wms/deoutorder.js']>
<style type="text/css">
	.out-filter .title, .out-filter .oper-btn {
		padding-left: 15px;
	}
	
	.out-filter .title {
		text-align: right;
	}
	
	.out-filter .tips {
		color: red;
		font-size:12px; 
	}
	
	.out-info .main {
		margin: 5px auto;
	}
	
	.out-info .main td {
		padding: 3px 0px;
	}
	
	.out-info .main td.title {
		width: 100px;
		text-align: right;
	}
	
	.out-info .main td.content {
		width: 220px;
		text-align: left; 
	}
	
	.out-info .main td.content, .underline {
		text-decoration: underline;
	}
	
	.out-info .main .ar-history {
		margin-left: 20px;
	}
	
	.out-info .main td.cancel-underline {
		text-decoration: none;
	}
	
	.out-info .detail-wrap {
		margin-bottom: 10px;
	}
	
	.out-ar .reason-list-wrap {
		margin: 0px 0px;
	}
</style>
<div class="easyui-panel out-filter">
	<form id="deoutorder_filter">
		<table>
			<tr>
				<td class="title"><label>OrderId：</label></td>
				<td colspan="5">
					<input class="easyui-textbox" name="orderId" style="width: 300px;"><span class="tips">（*多个请使用特殊符号隔开）</span>
				</td>
				<td class="title"><label>OrderOcsId：</label></td>
				<td colspan="3">
					<input class="easyui-textbox" name="orderOcsId" style="width: 300px;"><span class="tips">（*多个请使用特殊符号隔开）</span>
				</td>
			</tr>
			<tr>
				<td class="title"><label>订单来源：</label></td>
				<td>
					<select id="filter_platform" class="easyui-combobox" data-options="panelHeight: 120" name="platform">
						<option value="">--请选择--</option>
						<option value="1">官网</option>
						<option value="2">ebay</option>
						<option value="3">亚马逊</option>
						<option value="4">沃尔玛</option>
						<option value="5">VC</option>
					</select>
				</td>
				<td class="title"><label>订单类型：</label></td>
				<td>
					<select id="filter_orderOutType" class="easyui-combobox" data-options="panelHeight: 120" name="orderOutType">
						<option value="">--请选择--</option>
						<option value="HME_INV_SHIPMENT">销售出库</option>
						<option value="Reissue_Normal">补发出库</option>
						<option value="Transfer_Out">换货出库</option>
						<option value="Reissue_Correct">补发矫正</option>
					</select>
				</td>
				<td class="title"><label>出库单状态：</label></td>
				<td>
					<select id="filter_isSendWms" class="easyui-combobox" data-options="panelHeight: 140" name="isSendWms">
						<option value="">--请选择--</option>
						<option value="0">待推送</option>
						<option value="1">已推送</option>
						<option value="2">已反馈</option>
						<option value="3">异常单</option>
						<option value="4">已取消</option>
					</select>
				</td>
				<td class="title"><label>跟踪号状态：</label></td>
				<td>
					<select id="filter_isUpload" class="easyui-combobox" data-options="panelHeight: 140" name="isUpload">
						<option value="">--请选择--</option>
						<option value="0">尚未上传</option>
						<option value="1">已经上传</option>
						<option value="2">上传失败</option>
						<option value="3">关闭上传</option>
					</select>
				</td>
				<td class="title"><label>SKU：</label></td>
				<td>
					<input class="easyui-textbox" name="sku" style="width: 210px;">
				</td>
			</tr>
			<tr>
				<td class="title"><label>订单创建时段：</label></td>
				<td colspan="3">
					<input type="text" id="filter_startOcsOrderCreateDate" name="startOcsOrderCreateDate" class="easyui-datebox" style="width: 115px;"/> 
					~ 
					<input type="text" id="filter_endOcsOrderCreateDate" name="endOcsOrderCreateDate" class="easyui-datebox" style="width: 115px;" />
				
				</td>
				<td class="title"><label>推送WMS时段：</label></td>
				<td colspan="3">
					<input type="text" id="filter_startSendDate" name="startSendDate" class="easyui-datebox" style="width: 115px;" /> 
					~ 
					<input type="text" id="filter_endSendDate" name="endSendDate" class="easyui-datebox" style="width: 115px;" />
				</td>
				<td class="title">
					<label>WMS反馈时段：</label>
				</td>
				<td>
					<input type="text" id="filter_startFeedbackDate" name="startFeedbackDate" class="easyui-datebox" style="width: 115px;" /> 
					~ 
					<input type="text" id="filter_endFeedbackDate" name="endFeedbackDate" class="easyui-datebox" style="width: 115px;" />
				</td>
			</tr>
			<tr>
				<td class="title"><label>异常产生时段：</label></td>
				<td colspan="9">
					<input type="text" id="filter_startAbnormalDate" name="startAbnormalDate" class="easyui-datebox" style="width: 115px;"/> 
					~ 
					<input type="text" id="filter_endAbnormalDate" name="endAbnormalDate" class="easyui-datebox" style="width: 115px;" />
				
				</td>
				<td class="oper-btn">
					<div>
						<a href="javascript:void(0);" id="filter_btn_reset" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
						<a href="javascript:void(0);" id="filter_btn_search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<table id="deoutorder_dataset" class="easyui-datagrid" style="width: 100%; height: 86%;">        
</table>
<div class="out-info-btns">
	<a id="btn-save-changes" href="#" class="easyui-linkbutton editable-show" data-options="iconCls:'icon-save', onClick: deoutorder.action.saveChanges"> 保存 </a>
	<a id="btn-close-dialog" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', onClick: deoutorder.action.closeDialog"> 关闭 </a>
</div>
<div id="deoutorder_info" class="easyui-dialog out-info" title="出库单详情" closed="true" style="width: 720px; height: auto;" 
	data-options="modal: true, buttons: '.out-info-btns', top: 150,	closable: false">
	<form method="post" action="${FTL.X.global_domain}/wms/deoutorder/saveChanges">
		<input type="hidden" name="id" />
		<table class="main">
		</table>
		<div class="detail-wrap">
			<table class="detail">
			</table>
		</div>
	</form>
</div>
<div class="out-ar-btns">
	<a id="btn-close-ar-dialog" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', onClick: deoutorder.action.closeARDialog"> 关闭 </a>
</div>
<div id="deoutorder_ar" class="easyui-dialog out-ar" title="异常原因" closed="true" style="width: 720px; height: auto;" 
	data-options="modal: true, buttons: '.out-ar-btns', top: 150, closable: false, resizable: true">
	<div class="reason-list-wrap">
		<table class="reasons-list">
		</table>
	</div>
</div>
</@FTL.admin>
