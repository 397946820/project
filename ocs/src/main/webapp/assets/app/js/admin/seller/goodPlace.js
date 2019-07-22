var renderProductPlaceModel = function(productPlaceModel) {
	$("#productAddress").textbox("setValue",productPlaceModel.productAddress);
	$("#region").combobox("setValue",productPlaceModel.region);
	$("#postCode").textbox("setValue",productPlaceModel.postCode);
}
var contructProductPlaceModel = function(){
	var productPlaceModel = {
			productAddress:'',
			region:'',
			postCode:''
		};
	productPlaceModel.productAddress = $('#productAddress').val();
	productPlaceModel.region = $("#region").combobox("getValue");
	productPlaceModel.postCode = $('#postCode').val();
	return productPlaceModel;
}

function newGood(){
	$('#goodForm').form('reset');
    $('#goodDialog').dialog('open').dialog('center').dialog('setTitle','添加');
    
}
        
function editGood(index){
	$('#goodTable').datagrid('selectRow',index);
	var row = $('#goodTable').datagrid('getSelected');
	$('#goodDialog').dialog('open').dialog('center').dialog('setTitle', '编辑');
	//$('#buyerfm').form('reset');
	$("#saveAsId").val(row.id);
	$("#siteId").combobox("setValue",row.siteId);
	$("#title").textbox("setValue",row.title);
	renderProductPlaceModel(eval('('+row.data+')'));
}
        
function saveGood(){
	var siteId = $("#siteId").combobox("getValue");
	if(ocs.stringIsNull(siteId)){
		$.messager.alert('提示','请先选择站点!','warning');
		return;
	}
	var id = $("#saveAsId").val();
	var title = $("#title").textbox("getValue");
	if(ocs.stringIsNull(title)){
		$.messager.alert('提示','请输入标题!','warning');
		return;
	}
	var saveAsMode = {};
	var saveData = contructProductPlaceModel();
	saveAsMode["id"] = id;
	saveAsMode["data"] = JSON.stringify(saveData);
	saveAsMode["title"] = title;
	saveAsMode["siteId"] = siteId;
	saveAsMode["dataType"] = 4;
	ocs.ajax({
		url: "/publication/saveAs",
        type: "post",
        data:saveAsMode,
        success:function(data){
        	if(data){
        		$('#goodDialog').dialog('close');
        		$('#goodTable').datagrid('reload');
        		$.messager.show({
        			title:'提示',
        			msg:'保存成功',
        			timeout:500,
        			showType:'slide',
        			style:{
        				top:20,
        				left:document.body.offsetWidth/2 - 150,
        				right:''
        			}
        		});
        	}
        }
	});

}
        
function destroyGood(){
	var row = $('#goodTable').datagrid('getSelections');
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
			        	$('#goodTable').datagrid('reload');
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

$(function() {
	var flag = true;
	var on = true;
	//0 物品描述  1 支付方式  2 买家要求 3 退货政策 4 物品所在地  5 运输选项
	$("#goodTable").datagrid(
	{
		url : '/ocs/publication/modelManagerlist',
		fitColumns : true,
		queryParams : {
			param : {
				"dataType" : 4
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
					formatter : function(value, row, index) {
						if((value == "" || value == null) && value!= 0){
							value=22;
						}
					    return "<div class='icon3 country_size num_"+value+"'></div>";
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
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="editGood('+index+');" data-options="plain：true">编辑</a>';
					}
				} ] ],
		idField : 'id',
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		pageSize : 50,
		border : true,
		loadFilter : function(data) {
			var rows = data.rows;
			if(rows){
				var datagridColumns = [];
				var columns = [];
				columns.push({
					field : 'title',
					title : '名称',
					align : 'center',
					width : 100
				});
				columns.push({
					field : 'siteId',
					title : '站点',
					align : 'center',
					width : 100,
					formatter : function(value, row, index) {
						if((value == "" || value == null) && value!= 0){
							value=22;
						}
					    return "<div class='icon3 country_size num_"+value+"'></div>";
					}
				});
				columns.push({
					field : 'data',
					hidden : true
				});
				columns.push({
					field : 'dataType',
					hidden : true
				});
				
				
				$.each(rows,function(){
					var views = this.data;
					views = eval('('+views+')');
					for(var key in views){
						this[key] = views[key];
						if(on){
							if(key =="productAddress"){
								var column = {
										field : "productAddress",
										title : "物品所在地",
										align : 'center',
										width : 100
									};
								columns.push(column);
							}
							if(key =="region"){
								var column = {
										field : "region",
										title : "国家或地区",
										align : 'center',
										width :100
									};
								columns.push(column);
							}
							
						}
					}
					on=false;
				});
				if(flag){
					columns.push({
						field : 'stock',
						title : '动作',
						width : 100,
						align : 'center',
						formatter : function(value, row, index) {
							return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="editGood('+index+');" data-options="plain：true">编辑</a>';
						}
					});
					datagridColumns.push(columns);
					$("#goodTable").datagrid({
						columns:datagridColumns
					});
				}
				flag = false;
				data["rows"] = rows;
			}
			return data;
		},
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				newGood();
			}
		}, {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				destroyGood();
			}
		} ]
	});
});