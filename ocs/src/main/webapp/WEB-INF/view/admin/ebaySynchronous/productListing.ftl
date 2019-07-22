<@FTL.admin id="productListing" title="产品标识" add_script_files=['admin/synchronou/productListing.js']>
 <table id="productListingDataGrid" class="easyui-datagrid" style="height:610px;overflow:auto;"
		data-options="
        title:'产品标识',
	    url:'${FTL.X.global_domain }/ProductListing/selectProductListings',
	    treeField:'name',
	    animate:true,
	    toolbar:'#productListingBar',
	    fit:true,
	    pagination:true,
	    idField:'category_id',
	    fitColumns:true,
	    columns:[[{field:'marketplace_id',title:'站点ID',width:200},
	    {field:'category_id',title:'类目ID',width:240},
	    {field:'ean',title:'EAN',width:240},
	    {field:'isbn',title:'ISBN',width:240},
	    {field:'upc',title:'UPC',width:240},
	    {field:'conditions',title:'ConditionNew',width:240},
	     {field:'condition_help_url',title:'ConditionHelpUrl',width:240}
		]],
		rownumbers:true">
	</table>
	<div id="productListingBar" style="padding:10px;background:#fff;border-bottom:1px solid #95b8e7;">
		
		<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a> -->
		<div id="tb" style="float: left;"> 
		
		站点：<select class="easyui-combobox" id="selectMarketplace" name="marketplace_id"  style="width:200px;" >
						<option value="0" selected>美国</option>
						<option value="3">英国</option>
						<option value="77">德国</option>
						<option value="100">eBay汽车</option>
		</select>
		<input id="searchProduct" class="easyui-searchbox"  
	                         prompt="请输入查询的商品类目ID"
	                         searcher="doSearch"
	                         style="width: 200px; vertical-align: middle;"></input>
		<input class="easyui-textbox" id="category_id" data-options="prompt:'请输入同步商品类目ID'," >
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="synchronouProductListing()" style="width:120px;margin-left:100px;">同步产品标识</a>
       <!--  <input  class="easyui-searchbox"  
                        data-options="prompt:'请输入搜索商品类目ID',searcher:doSearch"></input>   -->             
        </div>
        <br clear="all"/>
        
	</div>
</@FTL.admin>