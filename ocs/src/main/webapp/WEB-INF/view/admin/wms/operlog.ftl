<@FTL.admin id="operlog" title="德国出入库单操作日志"
add_script_files=['admin/wms/operlog.js']>
<style type="text/css">
	.log-filter .title, .log-filter .oper-btn {
		padding-left: 15px;
	}
	
	.log-filter .title {
		text-align: right;
	}
	
	.log-filter .tips {
		color: red;
		font-size:12px; 
	}
</style>
<div class="easyui-panel log-filter">
	<form id="operlog_filter">
		<table>
			<tr>
				<td class="title"><label>操作用户：</label></td>
				<td>
					<input class="easyui-textbox" name="operatorText" style="width: 120px;"><span class="tips">（*可输入用户名或昵称）</span>
				</td>
				<td class="title"><label>订单信息：</label></td>
				<td>
					<input class="easyui-textbox" name="order" style="width: 300px;"><span class="tips">（*可输入OrderId或OrderOcsId）</span>
				</td>
			</tr>
			<tr>
				<td class="title"><label>操作时段：</label></td>
				<td>
					<input type="text" id="filter_startCreatedAt" name="startCreatedAt" class="easyui-datebox" style="width: 120px;"/> 
					~ 
					<input type="text" id="filter_endCreatedAt" name="endCreatedAt" class="easyui-datebox" style="width: 120px;" />
				</td>
				<td class="title"><label>日志详情：</label></td>
				<td>
					<input class="easyui-textbox" name="description" style="width: 300px;"><span class="tips">（*支持模糊匹配日志详情）</span>
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
<table id="operlog_dataset" class="easyui-datagrid" style="width: 100%; height: 90%;">        
</table>
</@FTL.admin>
