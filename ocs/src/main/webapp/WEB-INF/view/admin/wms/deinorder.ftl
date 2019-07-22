<@FTL.admin id="deinorder" title="德国仓入库订单数据"
add_script_files=['admin/wms/deinorder.js']>
<style type="text/css">
	.filter .title, .filter .oper-btn {
		padding-left: 15px;
	}
	
	.filter .title {
		text-align: right;
	}
	
	.in-filter .tips {
		color: red;
		font-size:12px; 
	}
	
	.filter .title.reset {
		padding-left: 0px;
	}
	
	.info-tbl {
		margin: 5px auto;
	}
	
	.info-tbl.reduce-space {
		margin: 1px auto;
	}
	
	.info-tbl td {
		padding: 3px 0px;
	}
	
	.info-tbl.reduce-space td {
		padding: 0px 0px;
	}
	
	.info-tbl td.title {
		width: 100px;
		text-align: right;
	}
	
	.info-tbl td.content {
		width: 220px;
		text-align: left; 
	}
	
	.info-tbl td.content, .underline {
		text-decoration: underline;
	}
	
	.info-tbl .ar-history {
		margin-left: 20px;
	}
	
	.info-tbl td.cancel-underline {
		text-decoration: none;
	}
	
	.in-info .detail-wrap {
		margin-bottom: 10px;
	}
	
	.in-ar .reason-list-wrap, .in-claim .claim-list-wrap {
		margin: 0px 0px;
	}
	
	.in-order-wrap {
		height: 100%;
	}
	
	.hide {
		display: none;
	}
</style>
<div class="in-order-wrap">
	<div class="easyui-panel in-filter filter">
		<form id="deinorder_filter">
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
						</select>
					</td>
					<td class="title"><label>入库类型：</label></td>
					<td>
						<select id="filter_orderType" class="easyui-combobox" data-options="panelHeight: 120" name="orderType">
							<option value="">--请选择--</option>
							<option value="1">退货入库</option>
						</select>
					</td>
					<td class="title"><label>入库单状态：</label></td>
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
					<td class="title"><label>认领属性：</label></td>
					<td>
						<div>
							<select class="easyui-combobox" data-options="panelHeight: 140" name="waitClaim">
								<option value="">--请选择--</option>
								<option value="oms_nonclaim">未关联认领单</option>
								<option value="oms_bindclaim">已关联认领单</option>
							</select>
						</div>
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
							<a href="javascript:void(0);" class="easyui-linkbutton btn reset" iconCls="icon-clear">重置</a>
							<a href="javascript:void(0);" class="easyui-linkbutton btn search" iconCls="icon-search">搜索</a>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<table id="deinorder_dataset" class="easyui-datagrid dg" style="width: 100%; height: 86%;">        
	</table>
</div>
<div class="in-info-btns">
	<a id="btn-save-changes" href="#" class="easyui-linkbutton editable-show" data-options="iconCls:'icon-save', onClick: deinorder.action.saveChanges"> 保存 </a>
	<a id="btn-close-dialog" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', onClick: deinorder.action.closeDialog"> 关闭 </a>
</div>
<div id="deinorder_info" class="easyui-dialog in-info" title="入库单详情" closed="true" style="width: 720px; height: auto;" 
	data-options="modal: true, buttons: '.in-info-btns', top: 150,	closable: false">
	<form method="post" action="${FTL.X.global_domain}/wms/deinorder/saveChanges">
		<input type="hidden" name="id" />
		<table class="main info-tbl">
		</table>
		<div class="detail-wrap">
			<table class="detail">
			</table>
		</div>
	</form>
</div>
<div class="in-ar-btns">
	<a id="btn-close-ar-dialog" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', onClick: deinorder.action.closeARDialog"> 关闭 </a>
</div>
<div id="deinorder_ar" class="easyui-dialog in-ar" title="异常原因" closed="true" style="width: 720px; height: auto;" 
	data-options="modal: true, buttons: '.in-ar-btns', top: 150, closable: false, resizable: true">
	<div class="reason-list-wrap">
		<table class="reasons-list">
		</table>
	</div>
</div>
<div class="in-claim-btns">
	<a href="#" class="easyui-linkbutton btn view-match-inorder hide" title="点击查看认领当前选择的认领单的入库单详情"
		data-options="iconCls:'icon-read', onClick: deinorder.action.viewMatchInOrder"> 查看认领详情 </a>
	<a href="#" class="easyui-linkbutton btn match-inorder hide" title="点击对当前选择的认领单进行认领操作"
	   	data-options="iconCls:'icon-synchoron-message', onClick: deinorder.action.matchInOrder"> 匹配入库单 </a>
	<a href="#" class="easyui-linkbutton btn close" data-options="iconCls:'icon-cancel', onClick: deinorder.action.closeClaimDialog"> 关闭 </a>
</div>
<div id="deinorder_claim" class="easyui-dialog in-claim" title="WMS认领单" closed="true" style="width: 1000px; height: 735px;" 
	data-options="modal: true, buttons: '.in-claim-btns', top: 10, closable: false, resizable: true">
	<div class="claim-list-wrap">
		<div class="claim-list-toolbar filter">
			<form id="claim_list_filter">
				<table>
					<tr>
						<td class="title reset"><label>认领属性：</label></td>
						<td>
							<select class="easyui-combobox" data-options="panelHeight: 150" name="waitClaim">
								<option value="">--请选择--</option>
								<option value="wms_unclaimed">待认领</option>
								<option value="wms_claimed">已认领</option>
							</select>
						</td>
						<td class="title"><label>RMA：</label></td>
						<td>
							<input class="easyui-textbox" name="rma" style="width: 200px;">
						</td>
						<td class="title"><label>SKU：</label></td>
						<td>
							<input class="easyui-textbox placeholder" name="sku" style="width: 200px;" />
						</td>
						<td class="oper-btn">
							<div>
								<a href="javascript:void(0);" class="easyui-linkbutton btn reset" iconCls="icon-clear">重置</a>
								<a href="javascript:void(0);" class="easyui-linkbutton btn search" iconCls="icon-search">搜索</a>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<table class="dg">
		</table>
	</div>
	<div class="easyui-panel info-wrap" style="border-color:#95B8E7; display: none;" data-options="title: '认领单详情', height: 224">
		<table class="info info-tbl reduce-space">
		</table>
		<table class="details">
		</table>
	</div>
</div>
<div class="in-matchs-btns">
	<a href="#" class="easyui-linkbutton btn claim hide" title="点击将入库单与认领单关联起来，实现对认领单的认领"
		data-options="iconCls:'icon-save', onClick: deinorder.action.claim"> 关联认领单 </a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', onClick: deinorder.action.closeInMatchsDialog"> 关闭 </a>
</div>
<div id="deinorder_in_matchs" class="easyui-dialog in-claim" title="OMS入库单" closed="true" style="width: 1000px; height: 390;" 
	data-options="modal: true, buttons: '.in-matchs-btns', top: 10, closable: false, resizable: true">
	<div class="claim-owner-wrap" style="border-color:#95B8E7; height: 165px;">
		<div class="claim-onwer-toolbar filter">
			<form id="claim_owner_filter">
				<table>
					<tr>
						<td class="title reset"><label>OrderId：</label></td>
						<td>
							<input class="easyui-textbox placeholder orderId" name="orderId" style="width: 150px;" />
						</td>
						<td class="title"><label>OrderOcsId：</label></td>
						<td>
							<input class="easyui-textbox placeholder" name="orderOcsId" style="width: 150px;" />
						</td>
						<td class="title"><label>SKU：</label></td>
						<td>
							<input class="easyui-textbox placeholder" name="sku" style="width: 150px;" />
						</td>
						<td class="title"><label>RMA：</label></td>
						<td>
							<input class="easyui-textbox placeholder" name="rma" style="width: 100px;" />
						</td>
						<td class="oper-btn">
							<div>
								<a href="javascript:void(0);" class="easyui-linkbutton btn reset" iconCls="icon-clear">重置</a>
								<a href="javascript:void(0);" class="easyui-linkbutton btn search" iconCls="icon-search">搜索</a>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<table class="dg">
		</table>
	</div>
	<div class="easyui-panel info-wrap" style="border-color:#95B8E7; display: none;" data-options="title: '入库单详情', height: 224">
		<table class="info info-tbl reduce-space">
		</table>
		<table class="details">
		</table>
	</div>
</div>
</@FTL.admin>
