<@FTL.admin id="categorySpecifics" title="产品类别属性">
 <table id="advertisementFeaturesDataGrid" class="easyui-datagrid" style=""
		data-options="
        title:'商品类目',
	    url:'${FTL.X.global_domain }/CategorySpecifics/findCategorySpecificsList',
	    treeField:'name',
	    animate:true,
	    pagination:true,
	    toolbar:'#toolSearch',
	    idField:'category_id',
	    fitColumns:true,
	    columns:[[{field:'name',title:'商品类目属性',width:200},
	    {field:'value',title:'类目属性值',width:240,resizable:false,editor:{type:'combobox',options:
      {valueField:'value',textField:'value'}}},
	    {field:'category_id',title:'类目ID',width:240},
	    {field:'marketplace_id',title:'站点',width:240}
		]],
		fit:true,
		rownumbers:true">
	</table>
	<div id="toolSearch" style="padding:10px;background:#fff;border-bottom:1px solid #95b8e7;">
		
		<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a> -->
		<div id="tb" style="float: left;"> 
		
		站点：<select class="easyui-combobox" id="selectMarketplace" name="marketplace_id"  style="width:220px" >
						<option value="0" selected>美国</option>
						<option value="3">英国</option>
						<option value="77">德国</option>
						<option value="100">eBay汽车</option>
		</select>
		<input id="searchSpecifics" class="easyui-searchbox"  
                         prompt="请输商品类目ID"
                         searcher="doSearch"
                         style="width: 130px; vertical-align: middle;margin: 0 14px;";></input>
       
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="synchronouCategorySpecifics()" style="margin-left:100px;width:120px;">同步分类属性</a>             
        </div>
        <br clear="all"/>
        
	</div>
<script type="text/javascript">  
	function doSearch(value,name){
		var marketplace_id = $("#selectMarketplace").val();
		var category_Id = value;
		
		$('#advertisementFeaturesDataGrid').datagrid('load',{
			marketplace_id:marketplace_id,
			category_Id:category_Id
		});
		
	}
	
	function synchronouCategorySpecifics() {
		  var da = $("#selectMarketplace").val();
		$.ajax({
			url: GLOBAL.domain+'/CategorySpecifics/synchronouCategorySpecifics',
			
			data: {marketplace_id:da},
			
			dataType: "json",
			
			contentType: "application/json; charset=UTF-8",
			
			type: "get",
			
			success: function(result) {
			 
                    	    // reload the user data
				alert("同步成功！");	
			},
			error: function(jqXHR, textStatus, errorThrown) {
					alert("同步失败！");		
			}
		});   
	}
</script>
</@FTL.admin>
