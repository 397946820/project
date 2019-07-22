<@FTL.admin id="Tickets" title="Tickets"  add_script_files=['admin/ocs/main.js','admin/ocs/mainDate.js','admin/customerCenter/datagrid-cellediting.js','admin/customerCenter/Tickets.js'] >

<div data-options="region:'center',border:false">
    <table id="TicketsTable" class="easyui-datagrid" 
		data-options="
	    url:'${FTL.X.global_domain}/Tickets/list',
	    idField: 'tickets_id',
        rownumbers: true,
		singleSelect: true,
		 fit: true,
		nowrap:false,
		pagination: true,
        onClickCell:cTickets.onClickCell,
        onAfterEdit:cTickets.onAfterEdit, 
        onBeforeEdit:cTickets.onBeforeEdit,
        queryParams:{
        	param:{
        		startDate:'',
        		endDate:'',
        		sku:'',
        		site:'',
        		order_number :'',
        		sort:'',
        		order:''
        	}
        },
        toolbar:'#TicketsToolbar',
	    columns:[[
                {field: 'tickets_id', title: 'tickets_id',hidden:true},
                {field: 'platform',title: 'Platform',
                editor:{
                	type:'combobox',
                	options:{
                	url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=platform',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'country', width:'3%',title: 'Country',
                editor:{
                	type:'combobox',
                	options:{
                	   url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=country',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'order_number', width:'10%',title: 'Order ID',editor:'textbox'},
                {field: 'sku', title: '产品sku',width:'10%',editor:{type:'textbox',options:{}}},
                {field: 'order_date',sortable:true, title: 'OrderDate',width:'5%',editor: 'datebox'},
                {field: 'defective_quantity',width:'3%',title: 'DefectiveQuantity',fitColumns:true,editor:{type:'numberspinner'}},
                {field: 'catagories', title: 'Catagories',
                editor:{
                	type:'combobox',
                	options:{
                	     url:'${FTL.X.global_domain}/BaseCombobox/selectCatagories',
                		valueField:'catagories',
						textField:'catagories'
                	}
                }},
                {field:'parent_category',title:'销售大类',
	                editor:{
	                	type:'combobox',
	                	options:{
	                	   url:'${FTL.X.global_domain}/BaseCombobox/selectParents',
                		   valueField:'parentCatagories',
						   textField:'parentCatagories'
	                	}
	                }},
                {field: 'problem_type_lvl2',title:'ProblemType',
                editor:{
                	type:'combobox',
                	options:{
                	    url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=problemType2',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'total_days',title:'TotalDays',editor:'textbox'},
                 {field: 'problem_date',sortable:true,title: 'ProblemDate',width:'5%',editor: 'datebox'},
                {field: 'solution',title:'Solution',
                editor:{
                	type:'combobox',
                	options:{
                		url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=solution',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'replacement_times', title:'ReplacementTimes',editor:'numberspinner'},
                {field: 'operators',title:'Operator',editor:'textbox'},
                {field: 'remark',title:'使用场景补发退款原因',width:'10%',editor:'textbox'},
                {field: 'amount', title: 'Amount',editor:'textbox'},
                {field: 'currency', title: 'Currency',editor:{
                	type:'combobox',
                	options:{
                	    url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=currency',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'payMethod', title: '支付方式',editor:'textbox'},
		]]">
	</table>
</div>
<div id="TicketsToolbar" >
<div style="width: 100%">
    
    <div style="padding:10px;">
		<form id="TicketsCondition">
			<table style="float:left; min-width:710px;">
				<tr style="min-width:1000px;">
					<td align="right"><label>问题日期:</label></td>
					<td>
						<input type="text" id="startDate" name="startDate" class="easyui-datebox" style="width: 100px;"  data-options="validType:'checkDate'" /> 至 
						<input type="text" id="endDate" name="endDate" class="easyui-datebox" style="width: 100px;"  data-options="validType:'checkDate'" />
					</td>
					<td align="right"><label style="">SKU:</label></td>
					<td>
						<input type="text" name="sku" data-options="prompt:'请输入SKU'" class="easyui-textbox" />
					</td>
				</tr>
				<tr>
					<td align="right"><label style="">国家:</label></td>
					<td>
						<input type="text" name="country" class="easyui-textbox"  style="width:220px;"/>
					</td>
					<td align="right"><label style="">订单:</label></td>
						<td>
							<input type="text" name="order_number" data-options="prompt:'请输入订单'" class="easyui-textbox" />
							<a  href="javascript:void(0);" id="TicketsClear" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;">重置</a>
							<a  href="javascript:void(0);" id="TicketsSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">查询</a>
							
						</td>
					
				</tr>
				
			</table>
			<br clear="all"/>
		</form>
		</div>
		<a id="addLinkbutton" href="javascript:void(0);" target="_blank" onclick="cTickets.append()" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">增加</a>
    <a id="removeLinkbutton" href="javascript:void(0);" onclick="cTickets.remove()" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
    <a id="saveLinkbutton" href="javascript:void(0);" onclick="cTickets.saveTickets()" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">保存</a>
    
    <a id="ticketsLinkbutton" href="javascript:void(0);" onclick="cTickets.exportCTickets()" class="easyui-linkbutton" iconCls="icon-redo"  
    plain="true">导出</a>
	</div>
</div>
</@FTL.admin>