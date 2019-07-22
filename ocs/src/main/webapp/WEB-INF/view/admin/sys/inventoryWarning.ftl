<@FTL.admin id="inventoryWarning" title="库存预警"
add_script_files=['admin/sys/inventoryWarning.js']>
<div class="easyui-panel" id="inventoryWarningSearchParam-panel">
		<div>
		<form id="inventoryWarningSearchParam">
		<table style=" min-width:1000px;padding:10px;">
			<tr style="min-width:1000px;">
				<td><label style="">SKU:</label></td>
				<td><input type="text" name="sku"  class="easyui-textbox" style="width:150px;"/>
				</td>
				<td><label style="">仓库代码:</label></td>
				<td>
					<select class="easyui-combobox" name="scode" value=""  style="width:150px;">
						<option ></option>
						<option value="US01">US01</option>
						<option value="US02">US02</option>
						<option value="US03">US03</option>
						<option value="CA01">CA01</option>
						<option value="JP01">JP01</option>
						<option value="DE01">DE01</option>
						<option value="DE02">DE02</option>
						<option value="UK01">UK01</option>
						<option value="UK02">UK02</option>
					</select>
				</td>
				<td><label style="">时间:</label></td>
				<td><input type="text" name="dayTime"  class="easyui-datebox" style="width:150px;" />
				</td>
			</tr>
			<tr style="min-width:1000px;">
				<td><label style="">运输方式:</label></td>
				<td>
					<select class="easyui-combobox" name="ship_type" value=""  style="width:150px;">
						<option ></option>
						<option value="AF">AF</option>
						<option value="SF">SF</option>
						<option value="CO">CO</option>
					</select>
				</td>
				
				<td><label style="">平台:</label></td>
				<td>
					<select class="easyui-combobox" name="platform" value=""  style="width:150px;">
						<option ></option>
						<option value="US">US</option>
						<option value="EU">EU</option>
					</select>
				</td>
				<td colspan='2'>
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="inventoryWarningReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="inventoryWarningSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
		</div>
		<div>
			<select id="template" class="easyui-combobox" style="width: 180px;"
				editable="false">
				<option value="1">导出数据</option>
				<option value="2">下载物流时长导入模板</option>
				<option value="3">下载系列预警系数导入模板</option>
				<option value="4">数据设置导入模板</option>
			</select> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="exportDataBtn" plain="true">导出</a>
			<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-update" id="updateInventoryWarning" plain="true">重新计算</a>
			<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" id="addInventoryWarningBaseData" plain="true">上传库存预警基础参数</a>
		</div>
	</div>
<table id="inventoryWarningList"  style="width:100%;height:95%" class="easyui-datagrid">        
</table>

<div id="fileUpload" class="easyui-dialog" title="文件上传" style="width:400px;height:215px;display:none;"data-options="iconCls:'icon-save',
	resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
    <form id="ImportForm" enctype="multipart/form-data" method="POST">
	    <div style="margin-bottom:20px;width:80%;padding-left: 35px;margin-top: 40px;">
			<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">数据类型:</div>
			<select class="easyui-combobox" id="importType" panelHeight="auto" style="width: 120px;" editable="false">
				<option value="1">物流时长导入</option>
				<option value="2">系列预警系数导入</option>
				<option value="3">数据设置导入模板</option>
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
<div id="importMessageWin" class="easyui-dialog" title="导入结果" style="width:400px;height:200px;"data-options="iconCls:'icon-save',
	resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
   <div>
   		<ul id="importErrorList"></ul>
   </div>
</div>
</@FTL.admin>
