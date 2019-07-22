<@FTL.admin id="indexCategory" title="类目主页" add_script_files=['admin/synchronou/synchronouCategory.js']>

		<form id="categoryForm"  method="post" style="padding:5px;">
		<table>
			<tr>
				<td style="font-size:12px;">站点  :</td>
				<td>
					<select class="easyui-combobox" id="selectMarketplace" name="marketplace_id"  style="width:100%" >
							<option value="0" selected>美国</option>
							<option value="3">英国</option>
							<option value="77">德国</option>
							<option value="100">eBay汽车</option>
				    </select>
				</td>
				<td style="padding-left: 15px;font-size:12px;">Authorization :</td>
				<td><input name="authorization" class="easyui-textbox" type="text" style="width: 100%"></input></td>
				<td align="center" style="padding-left: 100px;"><a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="doSearch()" style="width:130px">展现选择站点类目</a></td>
				<td align="center"><a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="synchronouCategory()" style="width:90px">同步分类</a></td>
			</tr>
		</table>
	</form>
	
   

      <table id="advertisementFeaturesDataGrid" class="easyui-treegrid" style="height:560px;overflow:auto;"
		data-options="
        title:'商品类目',
	    url:'${FTL.X.global_domain }/IndexSynchronou/treeByMarketplaceId',
	    treeField:'name',
	    animate:true,
	    idField:'category_id',
	    columns:[[{field:'name',title:'类目名',width:200},
	    {field:'parent_category_id',title:'父ID',width:240},
	    {field:'marketplace_id',title:'市场ID',width:100},
	    {field:'categorytreeversion',title:'市场版本',width:120},
	    ]],
		rownumbers:true">
	</table>

</@FTL.admin>