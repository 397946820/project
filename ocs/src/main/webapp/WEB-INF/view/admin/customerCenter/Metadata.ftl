<@FTL.admin id="metadata" title="metadata"  add_script_files=['admin/customerCenter/metadata.js','admin/customerCenter/datagrid-cellediting.js','admin/ocs/mainDate.js','admin/ocs/main.js'] >
	<div data-options="region:'center',border:false">
	<table id="skuTable" class="easyui-datagrid"  style="width: 500px;height:300px"
		data-options="
	    url:'${FTL.X.global_domain}/BaseCombobox/skuList',
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
		onClickCell:skuModel.onClickCell,
		queryParams:{
			param:{
			sku :''
			}
		},
	    columns:[[
                {  field: 'id', title: 'id',hidden:true},
                {  field: 'sku', title: '映射sku',editor:'textbox',width:200},
                {  field: 'catagories', title:'Catagories',width:200,editor:{
                	type:'combobox',
                	options:{
                	    url:'${FTL.X.global_domain}/BaseCombobox/selectCatagories',
                		valueField:'catagories',
						textField:'catagories'
                	}
                }},
                {  field: 'parentCatagories', title:'销售大类',width:200,
                 editor:{
                	type:'combobox',
                	options:{
                	    url:'${FTL.X.global_domain}/BaseCombobox/selectParents',
                		valueField:'parentCatagories',
						textField:'parentCatagories'
                	}
                }},
                {
                   field:'operation',
                   title:'操作',
                   formatter:skuModel.getOperation,
                   width:250
                }
		]],
		toolbar:'#metadataToolbar'">
	</table>
	</div>
	<div id="metadataToolbar" >
	    <div>
	       	<a id="metadataDdd" href="javascript:void(0);" onclick="skuModel.append()" class="easyui-linkbutton" iconCls="icon-edit"
	       plain="true">增加</a>
	    </div>
  		<hr>
  		<div style="height:30px;">
		  		<form id="metadataCondition">
			  	<table style="float:left; min-width:1000px;">
			  		<tr style="min-width:1000px;">
							<td><label style="">sku:</label></td>
							<td>
								<input type="text" name="sku" style="float:right" class="easyui-textbox" />
							</td>
							<td >
								<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
							    	<a  href="javascript:void(0);" id="metadataReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
									<a  href="javascript:void(0);" id="metadataSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
								</div>
							</td>
					</tr>
			  	</table>
			  	</form>
  		</div>
    </div>

</@FTL.admin>