<@FTL.admin id="operatingProfitViewList" title="经营利润" 
add_script_files=['admin/sys/operatingProfitView.js','admin/sys/date.js','admin/taximeter/common.js']>

<div id="operatingProfitViewToolbar">
	<div style="height:85px;">
		<form id="operatingProfitViewSearchForm">
			<table style="float:left; min-width:800px;">
				<tr style="min-width:800px;">
					<td><label>分类：</label></td>
					<td>
						<select id="category" name="category" class="easyui-combobox" style="width: 200px" multiple="true">
						</select>
					</td>
					<td><label>sku：</label></td>
					<td>
						<select id="sku" name="sku" class="easyui-combobox" style="width: 200px" multiple="true" disabled="true">
						</select>
					</td>
					<td><label>季度：</label></td>
					<td>
						<select id="quarter" name="quarter" class="easyui-combobox" multiple="true" style="width: 200px"  editable="false" panelHeight="auto">
							<option value="">全选/反选</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
						</select>
					</td>
					<td>
						<a id="query" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="reset" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
				<tr style="min-width:800px;">
					<td><label>年：</label></td>
					<td>
						<select id="year" name="year" class="easyui-combobox" multiple="true" style="width: 200px">
						</select>
					</td>
					<td><label>月：</label></td>
					<td>
						<select id="month" name="month" class="easyui-combobox" multiple="true" style="width: 200px">
							<option value="">全选/反选</option>
							<option value="01">1</option>
							<option value="02">2</option>
							<option value="03">3</option>
							<option value="04">4</option>
							<option value="05">5</option>
							<option value="06">6</option>
							<option value="07">7</option>
							<option value="08">8</option>
							<option value="09">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
						</select>
					</td>
					<td id="site_1" style="display:none"><label>国家：</label></td>
					<td id="site_2" style="display:none">
						<select id="site" name="site" class="easyui-combobox" style="width: 200px" multiple="true"  editable="false" panelHeight="auto">
						</select>
					</td>
				</tr>
				<tr style="min-width:800px;">
					<td id="platform_1" style="display:none"><label>平台：</label></td>
					<td id="platform_2" style="display:none">
						<select id="platform" name="platform" class="easyui-combobox" style="width: 200px" multiple="true" editable="false" panelHeight="auto">
						</select>
					</td>
					<td id="department_1" style="display:none"><label>部门：</label></td>
					<td id="department_2" style="display:none">
						<select id="department" name="department" class="easyui-combobox" style="width: 200px"  editable="false" panelHeight="auto">
							
						</select>
					</td>
					<td id="personnel_1" style="display:none"><label>人员：</label></td>
					<td id="personnel_2" style="display:none">
						<select id="personnel" name="personnel" multiple="true" class="easyui-combobox" style="width: 200px" disabled="true">
							
						</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<hr>
	<div>
		<div>
			<@shiro.hasAnyRoles name="SWZG,CWZJ,ADMINISTRATOR">
			<!-- <a id="operatingProfitViewGenerateData" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-readed" plain="true">生成该月订单汇总数据</a>
			<a id="operatingProfitViewMappingSku" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出没有映射或者产品表找不到的SKU</a>
			<a id="operatingProfitViewCategorySku" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出找不到分类的SKU</a>
			<a id="operatingProfitViewSyncCategory" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true">同步ERP分类</a> -->
			<a id="operatingProfitViewRefresh" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新数据</a>
			<a id="operatingProfitViewSure" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true">确定该月数据</a>
			<a id="operatingProfitViewAmazonFee" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-readed" plain="true">亚马逊FEE</a>
			<a id="operatingProfitViewExport" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">历史计算数据导出</a>
			<a id="operatingProfitViewExportAmazon" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出亚马逊报表汇总数据</a>
			<a id="operatingProfitViewFeeExport" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出费用分摊</a>
			</@shiro.hasAnyRoles>
			<a id="operatingProfitViewExport_" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" plain="true">导出透视数据</a>
		</div>
	</div>
</div>

<table id="operatingProfitViewDataGrid" style="width:100%;height:100%"></table>

<div id="operatingProfitViewAmazonFeeDialog" class="easyui-dialog" title="亚马逊FEE" closed="true" style="width:800px;height:450px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#operatingProfitViewAmazonFeeDialog').dialog('close');
					}
				}]">
		<table id="operatingProfitViewAmazonFeeDataGrid" style="width:100%;height:100%" class='easyui-datagrid'
			data-options="
	           url:'',
	           fitColumns:true,
	           columns: [
	            [
	                {field: 'SITE', title: '国家',
	                	formatter:function(value,row){
		                	if(value == 'US') {
		                		return 'United States';
		                	} else if(value == 'GB') {
		                		return 'United Kingdom';
		                	} else if(value == 'DE') {
		                		return 'German';
		                	} else if(value == 'FR') {
		                		return 'France';
		                	} else if(value == 'IT') {
		                		return 'Italy';
		                	} else if(value == 'ES') {
		                		return 'Spain';
		                	} else if(value == 'JP') {
		                		return 'Japan';
		                	} else if(value == 'CA') {
		                		return 'Canada';
		                	} 
		                	return value;
		                }
	                },
	                {field: 'AMAZONFEE', title: '报表FEE'},
	                {field: 'FEEAMAZON', title: '导入FEE'},
	                {field: 'TOTAL', title: '合计'},
	                {field: 'MONTHOFYEAR', title: '年月'}
	            ]
	        ],
	        singleSelect: true,
	        rownumbers: true,
	        pagination: true,
	        pageSize: 50,
	        border:false,
	        fit:true,
	        showFooter:true,
	        toolbar:'#operatingProfitViewAmazonFeeToolbar'">
		</table>
</div>

<div id="operatingProfitViewAmazonFeeToolbar">
	<div style="height:40px;">
		<form id="feeSearchForm">
			<table style="float:left; min-width:400px;">
				<tr style="min-width:400px;">
					<td><label>国家：</label></td>
					<td>
						<select id="site_" name="site" class="easyui-combobox" style="width: 150px" multiple="true" editable="false" panelHeight="auto">
							<option value="">全选/反选</option>
							<option value="US" >United States</option>
							<option value="GB" >United Kingdom</option>
							<option value="DE" >German</option>
							<option value="FR" >France</option>
							<option value="IT" >Italy</option>
							<option value="ES" >Spain</option>
							<option value="JP" >Japan</option>
							<option value="CA" >Canada</option>
						</select>
					</td>
					<td><label>年月：</label></td>
					<td>
						<input id="date_1" name="monthOfYear" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 180, panelAlign: 'right', editable: false">
					</td>
					<td>
						<a id="queryFee" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						<a id="resetFee" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div id="fileExport" class="easyui-dialog" title="数据导出" style="width:535px;height:215px;display:none;"data-options="iconCls:'icon-save',
	resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
    <form id="ExportForm" enctype="multipart/form-data" method="POST">
	    <div style="margin-bottom:20px;width:80%;padding-left: 35px;margin-top: 40px;">
	    	<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">年月:</div>
			<input id="date1" name="fromDate" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 180, panelAlign: 'right', editable: false">
			-
			<input id="date2" name="toDate" class="easyui-datebox" data-options="
						　　buttonAlign: 'left', width: 180, panelAlign: 'right', editable: false">
			<br/><br/>
			<div style="display: none" id="export_type">
				<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">类型:</div>
				<select class="easyui-combobox" id="type" name="type" panelHeight="auto" style="width: 180px;" required="required" editable="false">
					<option value="0">计价器数据</option>
					<option value="1">产品数据</option>
					<option value="2">FBA规则数据</option>
					<option value="3">汇率运费税率数据</option>
				</select>
			</div>
		</div>
		<div style="width:80%;padding-left: 35px;">
			<a href="javascript:void(0);"  id="exportSubmit" class="easyui-linkbutton" style="width:100%">Export</a>
		</div>
	</form>
</div>

</@FTL.admin>
