<@FTL.admin id="CustomerRefund" title="CustomerRefund"  add_script_files=['admin/ocs/mainDate.js','admin/ocs/main.js','admin/customerCenter/RefundRecord.js','admin/customerCenter/datagrid-cellediting.js'] >

<div data-options="region:'center',border:false">
    <table id="CustomerRefundTable" class="easyui-datagrid" 
		data-options="
	    url:'${FTL.X.global_domain}/RefundRecord/list',
	    idField: 'report_id',
        rownumbers: true,
		singleSelect: true,
		 fit: true,
		nowrap:false,
		pagination: true,
        onClickCell:refundRecord.onClickCell,
        onAfterEdit:refundRecord.onAfterEdit, 
        onBeforeEdit:refundRecord.onBeforeEdit,
        queryParams:{
        	param:{
        		startDate:'',
        		endDate:'',
        		sku:'',
        		web_site:'',
        		order_id:'',
        		sort:'',
        		order:''
        	}
        },
        toolbar:'#CustomerRefundToolbar',
	    columns:[[
                {field: 'report_id', title: 'report_id',hidden:true},
                
                {field: 'platform', title: 'Platform',
                editor:{
                	type:'combobox',
                	options:{
                	  	url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=platform',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'web_site', title: 'Country',
                editor:{
                	type:'combobox',
                	options:{
                	    url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=country',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'order_date',sortable:true, title: 'Order Date',width:'7%',editor: 'datebox'},
                {field: 'refund_date',sortable:true, title: 'Refund Date',width:'7%',editor: 'datebox'},
                {field: 'order_id', title: 'Order ID',editor:'textbox'},
                {field: 'sku', title: 'SKU',editor:'textbox'},
                {field: 'qty', title: 'Qty',width:'5%',editor:'numberspinner'},
                {field: 'currency', title: 'Currency',editor:{
                	type:'combobox',
                	options:{
                	    url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=currency',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'amount', title: 'Amount',editor:'textbox'},
                {field: 'payment_menthod', title: 'PaymentMenthod',editor:{
                	type:'combobox',
                	options:{
                	    url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=payment_menthod',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'full_or_partial', title: 'Full/Partial',
                editor:{
                	type:'combobox',
                	options:{
                	    url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=full_or_partial',
                		valueField:'id',
						textField:'text'
                	}
                }},
                 {field: 'refund_times', title: 'RefundTimes',editor:'textbox'},
                 {field: 'returns', title: 'Return',
                editor:{
                	type:'combobox',
                	options:{
                		url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=return',
                		valueField:'id',
						textField:'text'
                	}
                }},
                
                {field: 'replace_times', title: 'ReplaceTimes',editor:'textbox'},
                {field: 'reason', title: 'Reason',editor:'textbox'},
                {field: 'problem_types', title: 'ProblemTypes',
                editor:{
                	type:'combobox',
                	options:{
                	url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=problemTypes',
                		valueField:'id',
						textField:'text'
                	}
                }},
                 {field: 'approval', title: 'Approval',editor:'textbox'}
                
		]]">
	</table>
</div>
<div id="CustomerRefundToolbar" >
	<div style="width: 100%;">
	    <div style="padding:10px;">
			<form id="CustomerRefundCondition">
				<table style="float:left; min-width:710px;">
					<tr style="min-width:1000px;">
						<td align="right"><label>退款日期:</label></td>
						<td>
							<input type="text" id="startDate" name="startDate" class="easyui-datebox" style="width: 100px;"  data-options="validType:'checkDate'" /> 至 
							<input type="text" id="endDate" name="endDate" class="easyui-datebox" style="width: 100px;"  data-options="validType:'checkDate'" />
						</td>
						<td align="right"><label style="">SKU:</label></td>
						<td>
							<input type="text" name="sku" data-options="prompt:'请输入SKU'" class="easyui-textbox" style="width: 220px;" />
						</td>
						</tr>
						<tr>
						<td align="right"><label style="">国家:</label></td>
						<td>
							<input type="text" name="web_site" class="easyui-textbox" style="width: 220px;" />
						</td>
						<td align="right"><label style="">订单:</label></td>
						<td>
							<input type="text" name="order_id" data-options="prompt:'请输入订单'" class="easyui-textbox" style="width: 220px;"  />
							<a  href="javascript:void(0);" id="refundClear" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;">重置</a>
							<a  href="javascript:void(0);" id="refundSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">查询</a>
						</td>
					</tr>
				</table>
				<br clear="all"/>
			</form>
		</div>
		<a id="addLinkbutton" href="javascript:void(0);" target="_blank" onclick="refundRecord.append()" class="easyui-linkbutton" iconCls="icon-add"
	       plain="true">增加</a>
	    <a id="removeLinkbutton" href="javascript:void(0);" onclick="refundRecord.remove()" class="easyui-linkbutton" iconCls="icon-remove"
	       plain="true">删除</a>
	    <a id="saveLinkbutton" href="javascript:void(0);" onclick="refundRecord.saveRefund()" class="easyui-linkbutton" iconCls="icon-edit"
	       plain="true">保存</a>
	    <a id="refundLinkbutton" href="javascript:void(0);" onclick='refundRecord.exportRefund()' class="easyui-linkbutton" iconCls="icon-redo"  
	    plain="true">导出</a>
	</div>
</div>
</@FTL.admin>