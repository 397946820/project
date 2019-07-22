<@FTL.admin id="outw" title="4PX出库单管理"
add_script_files=['admin/fourpx/outw.js']>
<style type="text/css">
	table .title {
		text-align: right;
	}
	
	.underline {
		text-decoration: underline;
	} 
	
	.cancel-underline {
		text-decoration: none;
	}
	
	table.main {
		margin: 5px auto;
	}
	
	table.main td {
		padding: 3px 0px;
	}
	
	table.main td.title {
		width: 100px;
	}
	
	table.main td.content {
		width: 220px;
		text-align: left; 
	}
	
	.abnormal-history {
		margin-left: 20px;
	}
</style>
<div class="easyui-panel">
	<form id="form_search_outw">
		<table>
			<tr>
				<td class="title">仓库代码：</td>
				<td>
					<select id="siteCombobox" class="easyui-combobox" name="warehouseCode">
						<option value="">--请选择--</option>
						<option value="USLA">美国洛杉矶仓</option>
						<option value="USNY">美东纽约仓</option>
					</select>
				</td>
				<td class="title">平台：</td>
				<td>
					<select id="siteCombobox" class="easyui-combobox" name="platform">
						<option value="">--请选择--</option>
						<option value="light">light</option>
						<option value="walmart">walmart</option>
						<option value="ebay">ebay</option>
					</select>
				</td>
				<td class="title"><label>订单参考号：</label></td>
				<td>
					<input type="text" name="referenceCode" class="easyui-textbox" style="width: 235px;" />
				</td>
				<td class="title"><label>创建时段：</label></td>
				<td>
					<input class="easyui-datebox" type="text"name="beginCreatedat" style="width: 110px;" />
					~
					<input class="easyui-datebox" type="text" name="endCreatedat" style="width: 110px;" />
				</td>
				<td class="title"><label>SKU：</label></td>
				<td>
					<input type="text" name="sku" class="easyui-textbox" style="width: 135px;" />
				</td>
			</tr>
			<tr>
				<td class="title"><label>推送4PX时段：</label></td>
				<td colspan="3">
					<input class="easyui-datebox" type="text"name="beginPushedat" style="width: 110px;" />
					~
					<input class="easyui-datebox" type="text" name="endPushedat" style="width: 110px;" />
				</td>
				<td class="title"><label>4PX反馈时段：</label></td>
				<td>
					<input class="easyui-datebox" type="text"name="beginFeedbackat" style="width: 110px;" />
					~
					<input class="easyui-datebox" type="text" name="endFeedbackat" style="width: 110px;" />
				</td>
				<td class="title"><label>异常产生时段：</label></td>
				<td>
					<input class="easyui-datebox" type="text"name="beginAbnormalat" style="width: 110px;" />
					~
					<input class="easyui-datebox" type="text" name="endAbnormalat" style="width: 110px;" />
				</td>
				<td class="title">业务状态：</td>
				<td>
					<select id="siteCombobox" class="easyui-combobox" name="status">
						<option value="">--请选择--</option>
						<option value="pending">待推送</option>
						<option value="pushed">已推送</option>
						<option value="feedback">已反馈</option>
						<option value="abnormal">异常单</option>
						<option value="overweight">超重单</option>
						<option value="cancelled">已取消</option>
					</select>
				</td>
				<td class="title">
					<div>
						<a href="javascript:void(0);" id="btn_outw_reset" class="easyui-linkbutton" iconCls="icon-clear">重置</a>
						<a href="javascript:void(0);" id="btn_outw_search" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
<table id="dg_outw" style="width:100%; height:93%;" class="easyui-datagrid">        
</table>

<div class="outw-details btns">
	<a href="#" class="easyui-linkbutton editable-show" data-options="iconCls:'icon-save', onClick: outw.action.saveChanges"> 保存 </a>
	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', onClick: outw.action.closeDetails"> 关闭 </a>
</div>
<div id='outw-details' class="easyui-dialog outw-details" title="出库单详情" closed="true" style="width: 720px; height: auto;" 
	data-options="modal: true, buttons: '.outw-details.btns', top: 150,	closable: false">
	<form method="post" action="${FTL.X.global_domain}/fourpx/outw/saveChanges">
		<input type="hidden" name="id" />
		<table class="main">
			<tr>
				<td class="title"><label for="platform">平台：</label></td>
				<td class="content platform"></td>
				<td class="title"><label for="warehouseCode">仓库代码：</label></td>
				<td class="content warehouseCode"></td>
			</tr>
			<tr>
				<td class="title"><label for="referenceCode">订单参考号：</label></td>
				<td class="content referenceCode"></td>
				<td class="title"><label for="status">业务状态：</label></td>
				<td class="content status"></td>
			</tr>
			<tr>
				<td class="title"><label for="carrierCode">渠道代码：</label></td>
				<td class="content carrierCode"></td>
				<td class="title"><label for="insureType">保险类型：</label></td>
				<td class="content insureType"></td>
			</tr>
			<tr>
				<td class="title"><label for="description">描述：</label></td>
				<td class="content description"></td>
				<td class="title"><label for="insureType">重量：</label></td>
				<td class="content weight"></td>
			</tr>
			<tr>
				<td class="title"><label for="fullName">收件人：</label></td>
				<td class="content fullName"></td>
				<td class="title"><label for="countryCode">国家代码：</label></td>
				<td class="content countryCode"></td>
			</tr>
			<tr>
				<td class="title"><label for="state">省份：</label></td>
				<td class="content state"></td>
				<td class="title"><label for="city">城市：</label></td>
				<td class="content city"></td>
			</tr>
			<tr>
				<td class="title"><label for="postalCode">邮编：</label></td>
				<td class="content postalCode"></td>
				<td class="title"><label for="phone">电话：</label></td>
				<td class="content phone"></td>
			</tr>
			<tr>
				<td class="title"><label for="street">街道：</label></td>
				<td class="content street"></td>
				<td class="title"></td>
				<td class="content"></td>
			</tr>
		</table>
		<table class="detail">
		</table>
	</form>
</div>

<div class="outw-ar-btns">
	<a id="btn-close-ar-dialog" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', onClick: outw.action.closeAbnormal"> 关闭 </a>
</div>
<div id="outw_ar" class="easyui-dialog outw-ar" title="异常原因" closed="true" style="width: 720px; height: auto;" 
	data-options="modal: true, buttons: '.outw-ar-btns', top: 150, closable: false, resizable: true">
	<div class="abnormal-wrap">
		<table class="dg-abnormal">
		</table>
	</div>
</div>
</@FTL.admin>
