<@FTL.admin id="contrast" title="数据对比"
add_script_files=['admin/salesStatistics/contrast.js']>
<div id="cc" class="easyui-layout" style="width: 100%; height: 100%;">
	<div data-options="region:'north',split:false" style="height: 11%;">
		<div>
			<a id="contrastUploadLinkbutton" href="javascript:void(0)"
				class="easyui-linkbutton" iconCls="icon-undo" plain="true">导入</a>
			<div id="tool" style="display: none">
				<div style="height: 50px;">
					<form>
						<table style="float: left; min-width: 1000px;">
							<tr style="min-width: 1000px;">
								<td><label>数据展示的方式:</label></td>
								<td><select id="aa" class="easyui-combobox"
									style="width: 240px;">
										<option value="">-- 请选择 --</option>
										<option value="1">导入的在oracle服务器没有的</option>
										<option value="2">oracle服务器有的在导入的没有的</option>
										<option value="3">导入的有在mysql服务器没有的</option>
										<option value="4">mysql服务器有的在导入的没有的</option>
										<option value="8">mysql服务器有的在Oracle服务器没有的</option>
										<option value="5">导入的数据</option>
										<option value="6">oracle服务器的数据</option>
										<option value="7">mysql服务器的数据</option>
								</select></td>
								<td><label>orderId：</label></td>
								<td><input type="text" id="orderId" class="easyui-textbox"
									style="width: 200px;" /></td>
								<td><label>sku：</label></td>
								<td><input type="text" id="sku" class="easyui-textbox"
									style="width: 200px;" /></td>
							</tr>
							<tr style="min-width: 1000px;">
								<td><label>order-status：</label></td>
								<td><select id="status" class="easyui-combobox"
									style="width: 240px;" editable="false">
										<option value="">-- 请选择 --</option>
										<option value="Shipped">Shipped</option>
										<option value="Unshipped">Unshipped</option>
										<option value="Cancelled">Cancelled</option>
								</select></td>
								<td><label>sales-channel：</label></td>
								<td><select id="station" class="easyui-combobox"
									style="width: 200px;" editable="false">
										<option value="">-- 请选择 --</option>
										<option value="Amazon.com">Amazon.com</option>
										<option value="Non-Amazon">Non-Amazon</option>
										<option value="Amazon.de">Amazon.de</option>
										<option value="Amazon.fr">Amazon.fr</option>
										<option value="Amazon.it">Amazon.it</option>
										<option value="Amazon.es">Amazon.es</option>
										<option value="Amazon.co.uk">Amazon.co.uk</option>
										<option value="Amazon.ca">Amazon.ca</option>
										<option value="Amazon.co.jp">Amazon.co.jp</option>
								</select></td>
								<td></td>
								<td><a id="query" href="javascript:void(0)"
									class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
									<a id="reset" href="javascript:void(0)"
									class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="oracle_"
		data-options="region:'east',title:'Oracle的数据',split:true"
		style="width: 50%;">
		<table id="oracle"></table>
	</div>
	<div id="mysql_"
		data-options="region:'west',title:'Mysql的数据',split:true"
		style="width: 50%;">
		<table id="mysql"></table>
	</div>
	<div id="original_" data-options="region:'center',title:'导入的数据'">
		<table id="original"></table>
	</div>
</div>
<div id="uploadDialog" class="easyui-dialog" title="订单数据导入"
	closed="true" style="width: 450px; height: 90px; padding: 10px;">
	<form id="uploadForm">
		选择文件：<input id="file" type="file" style="width: 250px" /> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-save" style="height: 20px" id="upload">导入</a>
	</form>
</div>

</@FTL.admin>
