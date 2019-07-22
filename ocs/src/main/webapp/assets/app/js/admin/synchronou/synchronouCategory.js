	function doSearch(){
	   var marketplace_id = $("#selectMarketplace").val();
		$('#advertisementFeaturesDataGrid').treegrid('load',{
			marketplace_id:marketplace_id
		});
	  
	   
   }
   
   function synchronouCategory(){
	var queryArray = $("#categoryForm").serializeArray();  
	        	var jsonString= '{';  
	        	for (var i = 0; i < queryArray.length; i++) {  
	        	    jsonString+= JSON.stringify(queryArray[i].name) + ':' + JSON.stringify(queryArray[i].value) + ',';  
	        	}  
	        	jsonString = jsonString.substring(0, (jsonString.length - 1)); 
	        	jsonString =jsonString + "}"; 
	        	var da= JSON.parse(jsonString) ;
	        	
	        	 if( $('#buyerfm').form('validate')) { 
	        		$.ajax({
						url: GLOBAL.domain+'/IndexSynchronou/synchronouCategory',
						
						data: da,
						
						dataType: "json",
						
						contentType: "application/json; charset=UTF-8",
						
						type: "get",
						
						success: function(result) {
						 
			                    	$('#buyerfm').form('reset');
			                        $('#buyerDialog').dialog('close');        // close the dialog
			                        $('#buyerTable').datagrid('reload');    // reload the user data
			                 	
						},
						error: function(jqXHR, textStatus, errorThrown) {
										
						}
					}); 
	        	} 
      };
