var packageSkuFBAlist = {};
(function(packageSkuFBAlist,$){
	var greatData = [];
	// init datagrid
	$("#packageSkuFBAlist").datagrid({
		columns : [ [
				{
					field : 'qty',
					title : '装箱数量',
					align : 'center',
					width : 100
				},
				{
					field : 'lqty',
					title : '长排列方式',
					align : 'center',
					width : 100
				},
				{
					field : 'wqty',
					title : '宽排列方式',
					align : 'center',
					width : 100
				},
				{
					field : 'hqty',
					title : '高排列方式',
					align : 'center',
					width : 100
				},
				{
					field : 'fLength',
					title : '组合长',
					align : 'center',
					width : 100
				},
				{
					field : 'fWidth',
					title : '组合宽',
					align : 'center',
					width : 100
				},
				{
					field : 'fHeight',
					title : '组合高',
					align : 'center',
					width : 100
				},
				{
					field : 'weight',
					title : '组合重量',
					align : 'center',
					width : 100
				},
				{
					field : 'fba',
					title : 'FBA费用',
					align : 'center',
					width : 100
				}
				 ] ],
		singleSelect : true,
		rownumbers : true,
		pagination : false,
		border : true,
		toolbar:"#packageSkuFBAToolbar"
	});
	//开始计算
	$("#jsbutton").click(function(){
		//验证
		var isValid = $("#ruleForm").form('validate');
		if(!isValid){
			$.messager.alert('Warning','数据必须填充完整！');
			return;
		}
		//获取数据
		var frmData = $("#ruleForm").serializeArray();
		var da = {};
		$.each(frmData,function(){
			da[this.name] = this.value;
		});
		//验证sku
		var f = true;
		ocs.ajax({
			url:'/productEntity/skuExist/'+da["sku"],
			async:false,
			type: "POST",
			data: "",
			success: function(result) {
				if(result.data && result.data == 1){
					f=false;
				}
			}
	    });
		if(f){
			$.messager.alert('Warning','sku不存在或者存在多个同时在售！');
			return;
		}
		ocs.ajax({
			url:'/productEntity/calculate',
			async:true,
		    beforeSend: function () {
                   $.messager.progress({
                       title: '请稍后',
                       msg: '正在计算中...'
                   });
               },
            complete: function () {
               $.messager.progress('close');
            },
			type: "POST",
			data: da,
			success: function(result) {
				var rd = result.data;
				if(rd){
					greatData = rd.great;
					$("#packageSkuFBAlist").datagrid("loadData",rd.list);
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//$.messager.alert("信息", "服务器发生错误！");		
			}
	   });
	});
	
	$("#viewBestbutton").click(function(){
		$("#greatFbaList").datagrid({
			columns : [ [
				{
					field : 'qty',
					title : '装箱数量',
					align : 'center',
					width : 120
				},
				{
					field : 'fba',
					title : 'FBA费用',
					align : 'center',
					width : 120
				}
				 ] ],
		singleSelect : true,
		rownumbers : true,
		pagination : false,
		border : true
		});
		$("#greatFbaList").datagrid("loadData",greatData);
		$("#greatFbaWin").dialog("open");
	});
})(packageSkuFBAlist,jQuery)