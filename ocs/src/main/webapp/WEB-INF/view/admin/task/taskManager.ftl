<@FTL.admin id="taskManagerList" title="销售数据同步" add_script_files=['admin/task/taskManager.js']>
	<div class="easyui-panel">
		<form id="taskManagerListSearchParam">
		<table style="float:left; min-width:1000px;">
			<tr style="min-width:1000px;">
				<td><label style="">任务名称:</label></td>
				<td><input type="text" name="jobName" style="float:right" class="easyui-textbox" /></td>
				
				<td>
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="taskManagerListReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="taskManagerListSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<table id="taskManagerListTable" class="easyui-datagrid" style="width:100%;height:95%">
	</table>

	
</@FTL.admin>