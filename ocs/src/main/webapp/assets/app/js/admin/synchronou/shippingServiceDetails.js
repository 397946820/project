var shippingServiceDetails = {};
(function(shippingServiceDetails,$){
	//初始化页面
	$("#shippingServiceDetailsGrid").datagrid(
	{
		url : '/ocs/ShippingServiceDetails/selectShippingServiceDetails',
		queryParams : {
			param : {
				siteId:'',
				shippingService :'',
				shippingCategory:''
			}
		},
		columns : [ [
				{
				    field:'isUse',
				    checkbox:true
			    },
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'shippingServiceId',
					hidden:true
				},
				{
					field : 'siteId',
					title : '站点',
					align : 'center',
					width : 60,
					formatter:function(value,row,index){
						return "<div class='icon3 country_size num_"+value+"'></div>";
					}
				},
				{
					field : 'shippingService',
					title : '服务名称',
					align : 'left',
					width : 120
				},
				{
					field : 'description',
					title : '服务描述',
					align : 'left',
					width : 260
				},
				{
					field : 'internationalService',
					title : '运输范围',
					align : 'center',
					width : 100,
					formatter:function(value,row,index){
						if(null != value&&value=="true"){
							return "国际运输";
						}else{
							return "国内运输";
						}
					}
				},
				{
					field : 'shippingTimeMax',
					title : '运输最大时间',
					align : 'center',
					width : 100,
					formatter:function(value,row,index){
						if(value){
							return value;
						}else{
							return "N/A";
						}
					}
				},
				{
					field : 'shippingTimeMin',
					title : '运输最小时间',
					align : 'center',
					width : 100,
					formatter:function(value,row,index){
						if(value){
							return value;
						}else{
							return "N/A";
						}
					}
				},
				{
					field : 'shippingCategory',
					title : '服务类型',
					align : 'center',
					width : 80
					
				},
				{
					field : 'stock',
					title : '动作',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						if(row.isUse == 0){
							return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="shippingServiceDetails.onOff('+row.id+',1);" data-options="plain:true">启用</a>';
						}else{
							return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="shippingServiceDetails.onOff('+row.id+',0);" data-options="plain:true">停用</a>';
						}
						
					}
				} ] ],
		fit:true,
		singleSelect : false,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		pageSize : 30,
		border : true,
		toolbar: "#toolSearch"
	});
	
	$("#syncShippingService").click(function(){
		ocs.ajax({
			url:'/ShippingServiceDetails/synchronouShippingServiceDetails',
			async:true,
			data : "",
		    beforeSend: function () {
                   $.messager.progress({
                       title: '请稍后',
                       msg: '正在同步中...'
                   });
               },
           complete: function () {
               $.messager.progress('close');
           },
			type: "GET",
			success: function(result) {
				$.messager.alert("信息",result.description);	
				$("#shippingServiceDetailsGrid").datagrid('reload');
			}
	    });

	});
	function searchParam(){
		var formData = $("#shippingServiceDetailsSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	}
	$("#shippingServiceDetailSearch").click(function(){
		$("#shippingServiceDetailsGrid").datagrid('load',{
			param : searchParam()
		});
	});
	
	$("#shippingServiceDetailReset").on('click',function(){
		$("#shippingServiceDetailsSearchParam").form("clear");
	});
	
	shippingServiceDetails.onOff = function(id,type){
		ocs.ajax({
			url:'/ShippingServiceDetails/onOff/'+id+"/"+type,
			async:true,
			data : "",
			type: "GET",
			success: function(result) {
				$.messager.alert("信息",result.description);	
				$("#shippingServiceDetailsGrid").datagrid('reload');
			}
	    });
	}
})(shippingServiceDetails,jQuery)
