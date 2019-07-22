var modelManager = {};
(function(modelManager,$){
	$('#m_siteId').combobox({
		onChange : function(newValue,oldValue){
			if(newValue != oldValue){
				$('#sellerDescriptionTemp').combobox("clear");
				$('#sellerDescriptionTemp').combobox("reload", "/ocs/publication/getSellerDescription/"+newValue);
				//初始化数据
				$('#m_payment').combobox("clear");
				$('#m_payment').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/1");
				
				$('#m_buyRequire').combobox("clear");
				$('#m_buyRequire').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/2");
				
				$('#m_returnPolicy').combobox("clear");
				$('#m_returnPolicy').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/3");
				
				$('#m_itemPlace').combobox("clear");
				$('#m_itemPlace').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/4");
				
				$('#m_tran').combobox("clear");
				$('#m_tran').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/5");
				
				//$('#m_noShipPlace').combobox("clear");
				//$('#m_noShipPlace').combobox("reload", "/ocs/publication/getModelTempData/"+newValue+"/1");
			}
		}
	});
	$("#modelManagerList").datagrid(
	{
		url : '/ocs/publication/modelManagerlist',
		fitColumns : true,
		queryParams : {
			param : {
				"dataType" : 7
			}
		},
		columns : [ [
				{
					field : 'title',
					title : '名称',
					align : 'center',
					width : 100
				},
				{
					field : 'siteId',
					title : '站点',
					align : 'center',
					width : 100,
					formatter : function(a, row, index) {
						if((a == "" || a == null) && a!= 0){
							a=22;
						}
						return "<div class='icon3 country_size num_"+a+"'></div>";
					}
				},
				{
					field : 'data',
					hidden : true
				},
				{
					field : 'dataType',
					hidden : true
				},
				{
					field : 'stock',
					title : '动作',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="modelManager.editModelManager('+index+');" data-options="plain：true">编辑</a>';
					}
				} ] ],
		idField : 'id',
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		pageSize : 50,
		border : true,
		onLoadSuccess : function(data) {
			return data;
		},
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				modelManager.newModelManager();
			}
		}, {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				modelManager.destroyModelManager();
			}
		} ]
	});
	
	modelManager.newModelManager = function(){
		$("#modelManagerAddfm").form("clear");
		$("#modelManagerAddDialog").dialog("open");
	}
	
	modelManager.editModelManager = function(index){
		$("#modelManagerAddfm").form("clear");
		var rows = $('#modelManagerList').datagrid('getRows');
		var row = rows[index];
		var data = eval('(' + row.data + ')');
		$("#modelManagerAddfm").form("load",data);
		$("#modelManagerAddDialog").dialog("open");
	}

	modelManager.destroyModelManager = function(){
		var row = $('#modelManagerList').datagrid('getSelections');
		if(row && row.length > 0){
			$.messager.confirm('删除', '你确定要删除数据?', function(r) {
				if (r) {
					var ids = [];
					$.each(row,function(){
						ids.push(this.id);
					});
					ocs.ajax({
						url: "/publication/saveAsDelete/"+ids.join("-"),
				        type: "post",
				        data:"",
				        success:function(){
				        	$('#modelManagerList').datagrid('reload');
							//$('#buyerTable').datagrid('clearSelections');
				        }
					});
				}
			});
		}else{
			$.messager.alert('提示','请先选择一条数据后操作!','warning');
			return;
		}
	}
	modelManager.saveModel = function(){
		var ispass = $("#modelManagerAddfm").form("validate");
		if(ispass){
			var saveAsMode = {};
			var title = "";
			var siteId = "";
			var saveData = $("#modelManagerAddfm").serializeArray();
			var set = {};
			$.each(saveData,function(){
				if(this.name == "m_name"){
					title = this.value;
				}
				if(this.name == "m_siteId"){
					siteId = this.value;
				}
				set[this.name] = this.value;
			});
			
	    	saveAsMode["data"] = JSON.stringify(set);
	    	saveAsMode["title"] = title;
	    	saveAsMode["siteId"] = siteId;
	    	saveAsMode["dataType"] = 7;
			ocs.ajax({
				url:"/publication/saveAs",
		        type: "post",
		        data:saveAsMode,
		        success:function(data){
		        	$("#modelManagerAddfm").form("clear");
		        	$('#modelManagerList').datagrid('reload');
		        	$("#modelManagerAddDialog").dialog("close");
		        }
			});
		}else{
			$.messager.alert('提示','红色为必填项！','warning');
			return;
		}
	}
})(modelManager,jQuery);