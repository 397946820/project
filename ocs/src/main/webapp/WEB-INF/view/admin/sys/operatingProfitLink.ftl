<@FTL.admin id="operatingProfitLinkList" title="类目与运营人员关系" 
add_script_files=['admin/sys/operatingProfitLink.js','admin/sys/date.js','admin/taximeter/common.js']>

<div data-options="region:'center',border:false">
	<table id="operatingProfitLinkDataGrid"
		data-options="
           url:'${FTL.X.global_domain}/operatingProfitLink/findAll',
           fitColumns:true,
           columns: [
            [
                {field: 'platform', title: '平台',sortable:true,
                	formatter:function(value,row){
	                	if(value == 'amazon') {
	                		return '亚马逊';
	                	} else if(value == 'ebay') {
	                		return 'Ebay';
	                	} else if(value == 'light') {
	                		return '官网';
	                	}
	                	return value;
	                }
                },
                {field: 'category', title: '类目',sortable:true},
                {field: 'country', title: '站点/国家',sortable:true},
                {field: 'nick', title: '用户',sortable:true},
                {field: 'monthOfYear', title: '年月',sortable:true},
                {field: 'createdAt', title: '创建时间',formatter:getTime,sortable:true},
                {field: 'updatedAt', title: '更新时间',formatter:getTime,sortable:true}
                
            ]
        ],
        idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#operatingProfitLinkToolbar'">
	</table>
</div>

<div id="operatingProfitLinkToolbar">
	<div style="height:30px;">
		<form id="searchForm">
			<table style="float:left; min-width:800px;">
				<tr style="min-width:800px;">
					<td><label>平台：</label></td>
					<td>
						<select name="platform" class="easyui-combobox" style="width: 150px"  editable="false" panelHeight="auto">
							<option value=""></option>
							<option value="amazon">亚马逊</option>
							<option value="ebay">Ebay</option>
							<option value="light">官网</option>
						</select>
					</td>
					<td><label>类目：</label></td>
					<td>
						<input type="text" name="category" class="easyui-textbox" style="width: 150px">
					</td>
					<td><label>年月：</label></td>
					<td>
						<input id="date" name="monthOfYear" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 180, panelAlign: 'right', editable: false">
					</td>
					<td>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<hr>
	<div>
		<div>
			<a id="operatingProfitLinkUploadLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" plain="true">导入</a>
			<a id="operatingProfitLinkExportLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">下载模板</a>
		</div>
	</div>
</div>

<div id="uploadDialog" class="easyui-dialog" title="文件导入" closed="true" style="width: 450px; height: 200px; padding: 10px;" modal="true">
	<form id="uploadForm" method="post" enctype="multipart/form-data">
		<input class="easyui-filebox" name="file" id="file" data-options="prompt:'请选择文件...'" buttonText="选择文件"
			style="height: 30px; width: 350px;"> 
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="height: 30px" id="upload">导入</a>
	</form>
	<hr>
	<div id="messages" class="easyui-panel" style="width:410px;height:100px;color: red;"  closed="true">
		<ul id="showMessages_">
		</ul>
	</div>
</div>

</@FTL.admin>
