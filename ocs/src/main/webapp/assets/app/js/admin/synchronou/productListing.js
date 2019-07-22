function doSearch(value,name){
		var marketplace_id = $("#selectMarketplace").val();
		var category_Id = value;
		$('#productListingDataGrid').datagrid('load',{
			marketplace_id:marketplace_id,
			category_Id:category_Id
		});
	}
	
	function synchronouProductListing() {
		var marketplace_id = $("#selectMarketplace").val();
		var category_id = $("#category_id").textbox('getValue');
		$.ajax({
			url: GLOBAL.domain+'/ProductListing/synchronouProductListing',
			
			data: {marketplace_id:marketplace_id,category_id:category_id},
			
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
