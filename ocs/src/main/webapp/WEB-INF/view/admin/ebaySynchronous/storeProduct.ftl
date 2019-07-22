<@FTL.admin id="StoreProduct" title="店铺产品" >
 
 <table id="storeProductDataGrid" class="easyui-datagrid"
		data-options="
        title:'店铺产品',
	    url:'${FTL.X.global_domain }/StoresProduct/selectStoreProdcuts',
	    treeField:'name',
	    animate:true,
	    toolbar:'#toolSearch',
	    pagination:true,
	    idField:'id',
	    fit:true,
	    fitColumns:true,
	    columns:[[{field:'item_id',title:'产品ID',width:200},
	    {field:'global_id',title:'站点编码',width:240},
	    {field:'item_search_url',title:'店铺URL',width:240},
	    {field:'store_name',title:'店铺名称',width:240}
		]],
		rownumbers:true">
	</table>
	<div id="toolSearch" style="padding:10px;background:#fff;border-bottom:1px solid #95b8e7;">
		
	
		<div id="tb" style="float: left;"> 
		
		站点：<select class="easyui-combobox" id="selectMarketplace" name="global_id"  style="width:200px;" >
						<option value="EBAY-US" selected>美国</option>
						<option value="EBAY-GB">英国</option>
						<option value="EBAY-DE">德国</option>
		</select>
		<!-- <input id="searchStoreProduct" class="easyui-searchbox"  
	                         prompt="请输入同步数据的店铺名"
	                         searcher="doSearch"
	                         style="width: 200px; vertical-align: middle;"></input>  -->
		<input class="easyui-textbox" id="store_name" data-options="prompt:'请输入同步数据的店铺名'," style="margin-left:15px;">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="doSearch()" style="width:120px;margin-left:100px;">查找</a>
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="synchronouStoreProduct()" style="width:120px">同步店铺</a> 
        </div>
        <br clear="all"/>
        
	</div>
	<script type="text/javascript">
	function doSearch(){
		debugger;
		var global_id = $("#selectMarketplace").val();
		var store_name = $("#store_name").textbox('getValue');
		$('#storeProductDataGrid').datagrid('load',{
			global_id:global_id,
			store_name:store_name
		});
	}
	
	function synchronouStoreProduct() {
		var global_id = $("#selectMarketplace").val();
		var store_name = $("#store_name").textbox('getValue');
		$.ajax({
			url: GLOBAL.domain+'/StoresProduct/synchronouStoresProduct',
			
			data: {global_id:global_id,store_name:store_name},
			
			dataType: "json",
			
			contentType: "application/json; charset=UTF-8",
			
			type: "get",
			
			success: function(result) {
			 
                    	    // reload the user data
				$.messager.alert("信息",result.description);	
			},
			error: function(jqXHR, textStatus, errorThrown) {
				$.messager.alert("信息","同步失败");		
			}
		}); 
	}
	</script>
</@FTL.admin>
