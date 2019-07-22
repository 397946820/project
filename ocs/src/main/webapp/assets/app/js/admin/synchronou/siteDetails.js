function synchronouSite(){
		$.ajax({
	
			url: GLOBAL.domain+'/SiteDetails/synchronouSiteDetails',
			
			dataType: "json",
			
			contentType: "application/json; charset=UTF-8",
			
			type: "get",
		
			success: function(result) {
			 
				$.messager.alert("信息",result.description);	 
		         	
			},
			error: function(jqXHR, textStatus, errorThrown) {
				$.messager.alert("信息","同步数据失败！");		
			}
	   });
}
