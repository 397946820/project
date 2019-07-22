<@FTL.admin id="Feedbacks" title="Feedbacksd"  add_script_files=['admin/ocs/mainDate.js','admin/ocs/main.js','admin/customerCenter/datagrid-cellediting.js','admin/customerCenter/Feedbacks.js'] >

<div data-options="region:'center',border:false">
    <table id="feedbacksTable" class="easyui-datagrid" 
		data-options="
	    url:'${FTL.X.global_domain}/Feedbacks/list',
	    idField: 'feedbacks_id',
        rownumbers: true,
		singleSelect: true,
		 fit: true,
		nowrap:false,
		pagination: true,
		extEditing:true,
		singleEditing:true,
		striped:true,
		autoEditing:true,
		onClickCell:cFeedbacks.onClickCell,
		onAfterEdit:cFeedbacks.onAfterEdit, 
        onBeforeEdit:cFeedbacks.onBeforeEdit,
        queryParams:{
        	param:{
        		startDate:'',
        		endDate:'',
        		sku:'',
        		site:'',
        		orders :'',
        		sort:'',
        		order:''
        	}
        },
        toolbar:'#FeedbacksToolbar',
	    columns:[[
                {field: 'feedbacks_id', title: 'feedbacks_id',hidden:true},
                {field: 'site', width:'5%',title: '国家',formatter:function(value,row){
                	return value;
                },
                editor:{
                	type:'combobox',
                	options:{
                     	url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=country',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'orders', title: '订单号',editor:'textbox'},
                {field: 'sku', title: '产品sku',editor:{type:'textbox',options:{}}},
                {field: 'order_date',sortable:true, title: '下单日期',width:'7%',editor: 'datebox'},
                {field:'catagories',title:'Catagories',
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
                {field: 'comments', width:'22%',title: '评论内容',fitColumns:true,editor:{type:'textbox',options:{multiline:true,height:70}}},
                {field: 'translates', title: '中文翻译',width:'10%',editor:{type:'textbox',options:{multiline:true,height:70}}},
                {field: 'reason',title:'原因',
               		 editor:{
                	type:'combobox',
                	options:{
                	 url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=problemType1',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'is_delete', title: '是否删除', width:'5%',formatter:function(value,row){
                	return value;
                },
                editor:{
                	type:'combobox',
                	options:{
                     	url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=yes_or_no',
                		valueField:'id',
						textField:'text'
                	}
                	
                }},
                {field: 'remarks', width:'8%', title: '备注',editor:'textbox'},
                {field: 'problem_time', sortable:true,title: '问题日期',width:'7%',editor: 'datebox' }
		]]">
	</table>
</div>
<div id="FeedbacksToolbar"  >
	<div style="width: 100%">
	    <div style="padding:10px;">
			<form id="FeedbacksCondition">
				<table style="float:left; min-width:710px;">
					<tr>
						<td align="right"><label>问题日期:</label></td>
						<td>
							<input type="text" id="startDate" name="startDate" class="easyui-datebox" style="width: 100px;"  data-options="validType:'checkDate'" /> &nbsp~&nbsp 
							<input type="text" id="endDate" name="endDate" class="easyui-datebox" style="width: 100px;"  data-options="validType:'checkDate'" />
						</td>
						<td align="right"><label style="">SKU:</label></td>
						<td>
							<input type="text" name="sku" data-options="prompt:'请输入SKU'" class="easyui-textbox" style="width:220px;"/>
						</td>
					</tr>
					<tr>
						
						<td align="right" width="70"><label style="">国家:</label></td>
						<td>
							<input type="text" name="site" class="easyui-textbox" style="width:220px;" />
						</td>
						<td align="right"><label style="">订单:</label></td>
						<td>
							<input type="text" name="orders" data-options="prompt:'请输入订单'" class="easyui-textbox" style="width:220px;"/>
							<a  href="javascript:void(0);" id="feedbackClear" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;">重置</a>
							<a  href="javascript:void(0);" id="feedbackSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">查询</a>
							<br clear = "both"/>
						</td>
					</tr>
				</table>
				<br clear="all"/>
			</form>
		</div>
		 <a id="addLinkbutton" href="javascript:void(0);" target="_blank" onclick="cFeedbacks.append()" class="easyui-linkbutton" iconCls="icon-add"
	       plain="true">增加</a>
	    <a id="removeLinkbutton" href="javascript:void(0);" onclick="cFeedbacks.remove()" class="easyui-linkbutton" iconCls="icon-remove"
	       plain="true">删除</a>
	    <a id="saveLinkbutton" href="javascript:void(0);" onclick="cFeedbacks.saveFeedacks()" class="easyui-linkbutton" iconCls="icon-edit"
	       plain="true">保存</a>
	    <a id="cFeedbacksLinkbutton" onclick="cFeedbacks.exportCFeedbacks()" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo"  
	    plain="true">导出</a>
	</div>
</div>
</@FTL.admin>