<@FTL.admin id="ProductReview" title="ProductReviews"  add_script_files=['admin/ocs/mainDate.js','admin/ocs/main.js','admin/customerCenter/ProductReview.js','admin/customerCenter/datagrid-cellediting.js'] >

<div data-options="region:'center',border:false">
    <table id="productTable" class="easyui-datagrid" 
		data-options="
	    url:'${FTL.X.global_domain}/ProductReview/list',
	    idField: 'product_id',
        rownumbers: true,
		singleSelect: true,
		fit: true,
		nowrap:false,
		pagination: true,
       onClickCell:productReview.onClickCell,
       onAfterEdit:productReview.onAfterEdit, 
        onBeforeEdit:productReview.onBeforeEdit,
        queryParams:{
        	param:{
        		startDate:'',
        		endDate:'',
        		sku:'',
        		comment_content:'',
        		orders :'',
        		sort:'',
        		order:''
        	}
        },
        toolbar:'#ProductToolbar',
	    columns:[[
                {field: 'product_id', title: 'product_id',hidden:true},
                {field: 'site', width:'4%',title: '国家',formatter:function(value,row){
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
                {field: 'sku', title: '产品sku',editor:'textbox'},
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
                {field: 'comment_content', width:'20%',title: '评论内容',fitColumns:true,editor:{type:'textbox',options:{multiline:true,height:60}}},
                {field: 'chinese_translation', width:'20%',title: '中文翻译',fitColumns:true,editor:{type:'textbox',options:{multiline:true,height:60}}},
                {field: 'problem_type',title:'问题类型',
               		 editor:{
                	type:'combobox',
                	options:{
                	 url:'${FTL.X.global_domain}/BaseCombobox/getValueByField?field=problemType1',
                		valueField:'id',
						textField:'text'
                	}
                }},
                {field: 'solution',title:'解决方案',width:'20%',fitColumns:true,editor:{type:'textbox',options:{multiline:true,height:60}}},
                {field: 'solution_date', sortable:true,title: '解决日期',width:'7%',editor: 'datebox'},
                {field: 'present_people', title: '提出人',width:'5%',editor:'textbox'}
		]]">
	</table>
</div>
<div id="ProductToolbar" >
	<div style="width: 100%;">
	    <div style="padding:10px;">
			<form id="ProductCondition">
				<table style="float:left; min-width:710px;">
					<tr style="min-width:1000px;">
						<td align="right"><label>解决日期:</label></td>
						<td>
							<input type="text" id="startDate" name="startDate" class="easyui-datebox" style="width: 100px;"  data-options="validType:'checkDate'" /> 至 
							<input type="text" id="endDate" name="endDate" class="easyui-datebox" style="width: 100px;"  data-options="validType:'checkDate'" />
						</td>
						<td align="right"><label style="">SKU:</label></td>
						<td>
							<input type="text" name="sku" data-options="prompt:'请输入SKU'" class="easyui-textbox"  style="width:220px;" />
						</td>
					</tr>
					<tr>
						<td align="right"><label style="">评论内容:</label></td>
						<td>
							<input type="text" name="comment_content" class="easyui-textbox"  style="width:220px;"/>
						</td>
						<td align="right"><label style="">订单:</label></td>
						<td>
							<input type="text" name="orders" data-options="prompt:'请输入订单'" class="easyui-textbox"  style="width:220px;"/>
							<a  href="javascript:void(0);" id="productClear" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;">重置</a>
							<a  href="javascript:void(0);" id="productSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">查询</a>
						</td>
					</tr>
				</table>
				<br clear="all"/>
			</form>
		</div>
		<a id="addLinkbutton" href="javascript:void(0);" target="_blank" onclick="productReview.append()" class="easyui-linkbutton" iconCls="icon-add"
	       plain="true">增加</a>
	    <a id="removeLinkbutton" href="javascript:void(0);" onclick="productReview.remove()" class="easyui-linkbutton" iconCls="icon-remove"
	       plain="true">删除</a>
	    <a id="saveLinkbutton" href="javascript:void(0);" onclick="productReview.saveProduct()" class="easyui-linkbutton" iconCls="icon-edit"
	       plain="true">保存</a>
	    
	    <a id="pReviewLinkbutton" href="javascript:void(0);" onclick='productReview.exportPReview()' class="easyui-linkbutton" iconCls="icon-redo"  
	    plain="true">导出</a>
	</div>
</div>
</@FTL.admin>