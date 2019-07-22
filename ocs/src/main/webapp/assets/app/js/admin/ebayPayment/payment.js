var renderPaymentModel = function(paymentModel) {
	var supportInfo = paymentModel.supportPaypaiInfo;
	if(supportInfo){
		$("input[name=payMentType]").removeAttr("checked");
		$('input[name=payMentType]').each(function(index,obj){
			if (supportInfo.indexOf($(this).val()) >= 0) {
				$(this).attr("checked","checked");
			}
		});
	}else{
		$("input[name=payMentType]").removeAttr("checked");
	}
	//setSelectValue('#paypaiAccount',paymentModel.paypaiAccount);
	$("#paypaiAccount").combobox("setValue",paymentModel.paypaiAccount);
	$("#payMentDescription").text(paymentModel.payDescription);
	if(eval(paymentModel.autoPay)){
		$("#autoPay").attr('checked',true);
	}else{
		$("#autoPay").attr('checked',false);
	}
}
var contructPaymentModel = function(){
	var payMentModel = {
			paypaiAccount:'dasfdasfdsaf',
			supportPaypaiInfo:'',
			payDescription:'',
			autoPay : ''
		};
	payMentModel.paypaiAccount = $('#paypaiAccount').val();
	payMentModel.payDescription = $('#payMentDescription').val();
	$('input[name="payMentType"]:checked').each(function(index,obj){
		payMentModel.supportPaypaiInfo += $(this).val() + ",";
	});
	if($("#autoPay").is(":checked")){
		payMentModel.autoPay = "true";
	}else{
		payMentModel.autoPay = "false";
	}
	return payMentModel;
}
function newPayment(){
	$('#paymentfm').form('reset');
    $('#paymentdlg').dialog('open').dialog('center').dialog('setTitle','添加');
    $("#paymentTypeList").html("");
    $('#paymentdlg').dialog("resize");
  
}
        
function editPayment(index){
	$('#payMentdg').datagrid('selectRow',index);
	var row = $('#payMentdg').datagrid('getSelected');
	$('#paymentdlg').dialog('open').dialog('center').dialog('setTitle', '编辑');
	//$('#buyerfm').form('reset');
	$("#saveAsId").val(row.id);
	$("#siteId").combobox("setValue",row.siteId);
	$("#title").textbox("setValue",row.title);
	renderPaymentModel(eval('('+row.data+')'));
}
        
function savePayment(){
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
	var saveData = contructPaymentModel();
	saveAsMode["id"] = id;
	saveAsMode["data"] = JSON.stringify(saveData);
	saveAsMode["title"] = title;
	saveAsMode["siteId"] = siteId;
	saveAsMode["dataType"] = 1;
	ocs.ajax({
		url: "/publication/saveAs",
        type: "post",
        data:saveAsMode,
        success:function(data){
        	if(data){
        		$('#paymentdlg').dialog('close');
        		$('#payMentdg').datagrid('reload');
        		$.messager.show({
        			title:'提示',
        			msg:'保存成功',
        			timeout:1000,
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
        
function destroyPayment(){
	var row = $('#payMentdg').datagrid('getSelections');
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
			        	$('#payMentdg').datagrid('reload');
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
	//0 物品描述  1 支付方式  2 买家要求 3 退货政策 4 物品所在地  5 运输选项
	$("#payMentdg").datagrid(
	{
		url : '/ocs/publication/modelManagerlist',
		fitColumns : true,
		queryParams : {
			param : {
				"dataType" : 1
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
					title : '站点23423',
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
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="editPayment('+index+');" data-options="plain：true">编辑</a>';
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
			/*debugger
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
					width : 100
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
						if(flag){
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
										width : 100
									};
								columns.push(column);
							}
							
						}
					}
					
				});
				if(flag){
					columns.push({
						field : 'stock',
						title : '动作',
						width : 100,
						align : 'center',
						formatter : function(value, row, index) {
							return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="editPayment('+index+');" data-options="plain：true">编辑</a>';
						}
					});
					datagridColumns.push(columns);
					$("#payMentdg").datagrid({
						columns:datagridColumns
					});
				}
				flag = false;
				data["rows"] = rows;
			}*/
			return data;
		},
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				newPayment();
			}
		}, {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				destroyPayment();
			}
		} ]
	});
	
	$('#siteId').combobox({
		valueField :"value",
		textField :"displayName",
		url : "/ocs/publication/getSiteList",
		onChange : function(newValue,oldValue){
			if(newValue != oldValue){
				//付款
	        	ocs.ajax({
	        		async:false,
	        		url:"/publication/getPaymentSupportData/"+newValue,
	        		type:"POST",
	        		data:"",
	        		success:function(returnData){
	        			var paymentSupportHtml = [];
	        			if(returnData){
	        				var i = 1;
	        				$.each(returnData,function(){
	        					/**
	        					 * <input type="checkbox" name="payMentType" value="American express"  >
	            			<span>American express</span>
	            			<br/>
	        					 */
	        					paymentSupportHtml.push('<input id="paymentSupportList'+i+'"  type="checkbox" name="payMentType" value="'+this.value+'"><label for="paymentSupportList'+i+'">'+this.displayName+'</label><br/>');
	        					i++;
	        				});
	        				$("#paymentTypeList").html(paymentSupportHtml.join(""));
	        			}
	        		}
	        	});
			}
		}
	});
});