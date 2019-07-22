<@FTL.admin id="AllProductDetails" title="产品详情" >

 <div data-options="region:'center',border:false">
	<table id="allProductDetailsGrid" class="easyui-datagrid" 
		data-options="
	    url:'${FTL.X.global_domain}/AllProductDetails/selectAllProductDetails',
	    fitColumns:true,
	    idField: 'id',
        singleSelect: false,
        rownumbers: true,
        pagination: true,
        pageSize: 10,
        border:false,
        fit:true,
        toolbar:'#toolSearch',
	    columns:[[{field:'uk_product',title:'英国产品',width:500,editor:{type:'combobox'}},
	    {field:'de_product',title:'德国产品',width:500,editor:{type:'combobox'}},
	    {field:'us_product',title:'美国产品',width:500,editor:{type:'combobox'}}
		]]">
	</table>
	
</div>
	<div id="toolSearch" style="padding:10px;background:#fff;border-bottom:1px solid #95b8e7;">
		<table>
			<tr>
				<td>
					ebay用户：<select class="easyui-combobox" id="user_id" name="user_id"  style="width:170px;" >
							<option value="uk.le" selected>uk.le</option>
							<option value="le.deutschland">le.deutschland</option>
							<option value="nm.deutschland">nm.deutschland</option>
							<option value="uk.nm">uk.nm</option>
						</select>
				</td>
				<td>站点：<select class="easyui-combobox" id="selectMarketplace" name="site_id"  style="width:200px;" >
						<option value="0" selected>美国</option>
						<option value="3">英国</option>
						<option value="77">德国</option>
					</select>
		       </td>
			   <td>
				<!-- <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="doSearch()" style="width:120px">查找</a> -->
       			 <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="synchronouAllProductDetails()" style="width:150px;margin-left:100px;">同步用户产品</a> 
        
			   </td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
	
	function refundFucation(value,rowData,rowIndex){
		alert(value);
	}
	function doSearch(){
		debugger;
		var site_id = $("#selectMarketplace").val();
		var user_id = $("#user_id").val();
		$('#storeProductDataGrid').datagrid('load',{
			site_id:site_id,
			user_id:user_id
		});
	}
	
	function synchronouAllProductDetails() {
		debugger;
		var site_id = $("#selectMarketplace").val();
		var user_id = $("#user_id").val();
		$.ajax({
			url: GLOBAL.domain+'/AllProductDetails/synchronouAllProductDetail',
			
			data: {site_id:site_id,user_id:user_id},
			
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
