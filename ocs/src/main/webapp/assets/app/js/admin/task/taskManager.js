var taskManager = {};
(function(taskManager,$){
	
	//初始化页面
	$("#taskManagerListTable").datagrid(
	{
		url : '/ocs/schedule/list',
		queryParams : {
			param : {
				jobName:'',
			}
		},
		columns : [ [
				
				{
					field : 'jobName',
					title : '任务名称',
					align : 'center',
					width : 150
				},
				{
					field : 'jobGroup',
					title : '任务分组',
					align : 'center',
					width : 100
				},
				{
					field : 'jobStatus',
					title : '任务状态',
					align : 'center',
					width : 60,
					formatter : function(value, row, index) {
						if(value == 1){
							return "运行";
						}
						return "停止";
					}
				},
				{
					field : 'cronExpression',
					title : 'cron表达式',
					align : 'center',
					width : 80
				},
				{
					field : 'isConcurrent',
					title : '是否并行',
					align : 'center',
					width : 80,
					formatter : function(value, row, index) {
						if(value == 1){
							return "是";
						}
						return "否";
					}
				},
				{
					field : 'springId',
					title : 'Bean名称',
					align : 'center',
					width : 100
				},
				{
					field : 'methodName',
					title : '方法名',
					align : 'center',
					width : 100
				},
				{
					field : 'description',
					title : '描述',
					align : 'center',
					width : 140
				}] ],
		idField : 'jobId',
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		pageSize : 50,
		border : true,
		nowrap:false,
		onLoadSuccess : function(data) {
			return data;
		},
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				
			}
		},
		 {
			text : '删除',
			iconCls : 'icon-add',
			handler : function() {
				
			}
		},
		{
			text : '立即调度',
			iconCls : 'icon-add',
			handler : function() {
				var row = $("#taskManagerListTable").datagrid("getSelected");
				debugger
				if(row == null){
					$.messager.alert('提示','请选择一个任务!','warning');
					return;
				}else{
					ocs.ajax({
						type:"GET",
						url:"/schedule/runImmediately/"+row.jobId,
						data:"",
						success:function(data){
							var meassage = data.data;
							if(meassage&&meassage=="success"){
								$.messager.alert('提示','调度成功!','info');
							}else{
								$.messager.alert('提示','调度失败：'+meassage,'warning');
							}
						}
					});
				}
			}
		},
		 {
			text : '启动',
			iconCls : 'icon-add',
			handler : function() {
				var row = $("#taskManagerListTable").datagrid("getSelected");
				if(row == null){
					$.messager.alert('提示','请选择一个任务!','warning');
					return;
				}else{
					ocs.ajax({
						type:"GET",
						url:"/schedule/start/"+row.jobId,
						data:"",
						success:function(data){
							var meassage = data.data;
							if(meassage&&meassage=="success"){
								$.messager.alert('提示','启动成功!','info');
								$("#taskManagerListTable").datagrid("reload");
							}else{
								$.messager.alert('提示','启动失败，请重试!','warning');
							}
						}
					});
				}
			}
		},
		 {
			text : '停止',
			iconCls : 'icon-add',
			handler : function() {
				var row = $("#taskManagerListTable").datagrid("getSelected");
				if(row == null){
					$.messager.alert('提示','请选择一个任务!','warning');
					return;
				}else{
					ocs.ajax({
						type:"GET",
						url:"/schedule/stop/"+row.jobId,
						data:"",
						success:function(data){
							var meassage = data.data;
							if(meassage&&meassage=="success"){
								$.messager.alert('提示','停止成功!','info');
								$("#taskManagerListTable").datagrid("reload");
							}else{
								$.messager.alert('提示','停止失败，请重试!','warning');
							}
						}
					});
				}
			}
		}
		]
	});
	
	$("#taskManagerListSearch").click(function(){
		var formData = $("#taskManagerListSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#taskManagerListTable").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#taskManagerListTable").datagrid("reload");
	});
	
	$("#taskManagerListReset").on('click',function(){
		$("#taskManagerListSearchParam").form("clear");
	});
})(taskManager,jQuery)
