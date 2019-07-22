<@FTL.admin id="ClaimProduct" title="ClaimProduct"  add_script_files=['admin/ocs/mainDate.js','admin/ocs/main.js','admin/customerCenter/datagrid-cellediting.js','admin/customerCenter/ClaimProduct.js'] >

<div data-options="region:'center',border:false">
    <table id="claimProductTable" class="easyui-datagrid" 
		data-options="
	    url:'${FTL.X.global_domain}/ClaimProduct/list',
	    idField: 'id',
        rownumbers: true,
		singleSelect: true,
		 fit: true,
		nowrap:false,
		pagination: true,
		extEditing:true,
		singleEditing:true,
		striped:true,
		autoEditing:true,
		onClickCell:cClaimProduct.onClickCell,
		onAfterEdit:cClaimProduct.onAfterEdit, 
		 onBeforeEdit:cClaimProduct.onBeforeEdit,
        queryParams:{
        	param:{
        		startDate:'',
        		endDate:'',
        		sku:'',
        		order_id:'',
        		product_number :'',
        		after_reason:'',
        		handling:'',
        		clone:''
        	}
        },
        toolbar:'#ClaimProductToolbar',
	    columns:[[
	           {field: 'id', title: 'id',hidden:true},
                {field: 'claim_date',sortable:true, title: '日期',width:'7%',editor: 'datebox'},
                {field: 'order_id', width:'10%',title: '售后单号',editor:'textbox'},
                {field: 'sku', width:'10%',title: 'SKU',editor:'textbox'},
                {field: 'trace_code', width:'12%',title: '追溯码',editor:'textbox'},
                {field: 'product_number',width:'4%', title: '数量',editor:'numberspinner'},
                {field: 'after_reason', width:'30%',title: '售后原因',editor:'textbox'},
                {field: 'handling',width:'10%', title: '处理方式',editor:'textbox'},
                {field: 'clone', width:'7%',title: '负责人',editor:'textbox'}
		]]">
	</table>
</div>
<div id="ClaimProductToolbar"  >
	<div style="width: 100%">
	    <div style="padding:10px;">
			<form id="claimProductCondition">
				<table style="float:left; min-width:710px;">
					<tr>
						<td align="right"><label>日期:</label></td>
						<td>
							<input type="text" id="startDate" name="startDate" class="easyui-datebox" style="width: 100px;"  data-options="validType:'checkDate'" /> &nbsp~&nbsp 
							<input type="text" id="endDate" name="endDate" class="easyui-datebox" style="width: 100px;"  data-options="validType:'checkDate'" />
						</td>
						<td align="right"><label style="">数量:</label></td>
						<td>
							<input type="text" name="product_number" data-options="prompt:'请输入数量'" class="easyui-textbox" style="width:220px;"/>
						</td>
						<td align="right"><label style="">售后原因:</label></td>
						<td>
							<input type="text" name="after_reason" data-options="prompt:'请输入原因'" class="easyui-textbox" style="width:220px;"/>
						</td>
					</tr>
					<tr>
						
						<td align="right" width="70"><label style="">SKU:</label></td>
						<td>
							<input type="text" name="sku" data-options="prompt:'请输入SKU'" class="easyui-textbox" style="width:220px;"/>
						</td>
						<td align="right" width="70"><label style="">处理方式:</label></td>
						<td>
							<input type="text" name="handling" data-options="prompt:'请输入处理方式'" class="easyui-textbox" style="width:220px;"/>
						</td>
						<td align="right" width="70"><label style="">负责人:</label></td>
						<td>
							<input type="text" name="clone" data-options="prompt:'请输入负责人'" class="easyui-textbox" style="width:220px;"/>
						</td>
						
					</tr>
					<tr> 
						<td align="right"><label style="">售后单号:</label></td>
						<td><input type="text" name="order_id" data-options="prompt:'请输入订单'" class="easyui-textbox" style="width:220px;"/></td>
						<td><label style="">追溯码:</label></td>
						<td><input type="text" name="trace_code" data-options="prompt:'请输入追溯码'" class="easyui-textbox" style="width:220px;"/></td>
						<td></td>
						<td>
							<a  href="javascript:void(0);" id="claimProductClear" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;">重置</a>
							<a  href="javascript:void(0);" id="claimProductSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">查询</a>
							<br clear = "both"/>
						</td>
					</tr>
					
				</table>
				<br clear="all"/>
			</form>
		</div>
		 <a id="addLinkbutton" href="javascript:void(0);" target="_blank" onclick="cClaimProduct.append()" class="easyui-linkbutton" iconCls="icon-add"
	       plain="true">增加</a>
	    <a id="removeLinkbutton" href="javascript:void(0);" onclick="cClaimProduct.remove()" class="easyui-linkbutton" iconCls="icon-remove"
	       plain="true">删除</a>
	    <a id="saveLinkbutton" href="javascript:void(0);" onclick="cClaimProduct.saveClaimProduct()" class="easyui-linkbutton" iconCls="icon-edit"
	       plain="true">保存</a>
	    
	    <a id="claimProductLinkbutton" onclick="cClaimProduct.exportCClaimProduct()" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"  
	    plain="true">导出</a>
	</div>
</div>
</@FTL.admin>