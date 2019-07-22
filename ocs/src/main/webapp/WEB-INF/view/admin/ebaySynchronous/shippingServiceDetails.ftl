<@FTL.admin id="ShippingServiceDetails" title="运输服务" add_script_files=['admin/synchronou/shippingServiceDetails.js'] >

	<table id="shippingServiceDetailsGrid" class="easyui-datagrid" ></table>

	<div class="easyui-panel" id="toolSearch">
		<form id="shippingServiceDetailsSearchParam" style="background:#fff;padding:10px;border-bottom:1px solid #95B8E7;">
			<table style="float:left; min-width:710px;">
				<tr style="min-width:1000px;">
					<td><label style="">站点:</label></td>
					<td><input type="text" name="siteId" style="float:right" class="easyui-combobox" data-options="editable:false,valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList' " /></td>
					<td><label style="">服务名称:</label></td>
					<td><input type="text" name="description" style="float:right" class="easyui-textbox" /></td>
					<td><label style="">运送范围:</label></td>
					<td>
					<select class="easyui-combobox" name="internationalService" style="float:right" >
						<option ></option>
						<option value="true">国际运输</option>
						<option value="false">国内运输</option>
					</select></td>
					<td>
						<div style="clear:both;text-align: left;width:175px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="shippingServiceDetailReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="shippingServiceDetailSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
						</div>
					</td>
				</tr>
			</table>
			<br clear="both"/>
		</form>
		<div>
			<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="syncShippingService" plain="true">同步</a>
		</div>
	</div>
</@FTL.admin>
