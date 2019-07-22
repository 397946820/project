var returnPoliy = {};
(function(returnPoliy,$){
	var flag = true;

	var contructReturnPolicyModel = function(){
		var returnPolicyModel = {
				policyType:'',
				returnDays:'',
				allowDelay:'',
				returnType:'',
				faretakeinhander:'',
				depreciationRate:'',
				returnDescription:''
			};
		returnPolicyModel.policyType = $('#policyType').combobox("getValue");
		returnPolicyModel.returnDays =$('#returnDays').combobox("getValue");
		if($('#isAllowDelay').is(":checked")){
			returnPolicyModel.allowDelay = 'true';
		}else{
			returnPolicyModel.allowDelay = 'false';
		}
		returnPolicyModel.returnType = $('#returnType').combobox("getValue");
		returnPolicyModel.faretakeinhander = $('#fareTakeInHander').combobox("getValue");
		returnPolicyModel.depreciationRate = $('#depreciationRate').combobox("getValue");
		returnPolicyModel.returnDescription = $('#returnPolicyDescription').textbox("getValue");
		return returnPolicyModel;
	}
	
	function newReturnPoliy(){
		$('#returnPoliyfm').form('reset');
		$('#returnPolicyDescription').textbox("setValue","");
	    $('#returnPoliydlg').dialog('open').dialog('center').dialog('setTitle','添加');
	    $('#returnPoliydlg').dialog("resize");
	  
	}
	
	returnPoliy.editReturnPoliy = function(index){
		$('#returnPoliyfm').form('reset');
		$('#returnPolicyDescription').textbox("setValue","");
		$('#returnPoliydg').datagrid('selectRow',index);
		var row = $('#returnPoliydg').datagrid('getSelected');
		$('#returnPoliydlg').dialog('open').dialog('center').dialog('setTitle', '编辑');
		var returnPolicyModel = eval('('+row.data+')');
		$('#returnPoliyfm').form('load',returnPolicyModel);
		$('#returnPolicyDescription').textbox("setValue",returnPolicyModel.returnDescription);
		$('#isAllowDelay').attr('checked',eval(returnPolicyModel.allowDelay)?true:false);
		$("#saveAsId").val(row.id);
		$("#siteId").combobox("setValue",row.siteId);
		$("#title").textbox("setValue",row.title);
		//renderReturnPlicyModel(eval('('+row.data+')'));
	}
	        
	returnPoliy.saveReturnPoliy = function(){
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
		var saveData = contructReturnPolicyModel();
		saveAsMode["id"] = id;
		saveAsMode["data"] = JSON.stringify(saveData);
		saveAsMode["title"] = title;
		saveAsMode["siteId"] = siteId;
		saveAsMode["dataType"] = 3;
		ocs.ajax({
			url: "/publication/saveAs",
	        type: "post",
	        data:saveAsMode,
	        success:function(data){
	        	if(data){
	        		$('#returnPoliydlg').dialog('close');
	        		$('#returnPoliydg').datagrid('reload');
	        		
	        	}
	        }
		});

	}
	function destroyReturnPoliy(){
		var row = $('#returnPoliydg').datagrid('getSelections');
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
				        	$('#returnPoliydg').datagrid('reload');
				        }
					});
				}
			});
		}else{
			$.messager.alert('提示','请先选择一条数据后操作!','warning');
			return;
		}     	
	}
	

	//0 物品描述  1 支付方式  2 买家要求 3 退货政策 4 物品所在地  5 运输选项
	$("#returnPoliydg").datagrid(
	{
		url : '/ocs/publication/modelManagerlist',
		fitColumns : true,
		queryParams : {
			param : {
				"dataType" : 3
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
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="returnPoliy.editReturnPoliy('+index+');" data-options="plain：true">编辑</a>';
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
			return data;
		},
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				newReturnPoliy();
			}
		}, {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				destroyReturnPoliy();
			}
		} ]
	});
	
	$('#siteId').combobox({
		valueField :"value",
		textField :"displayName",
		url : "/ocs/publication/getSiteList",
		onChange : function(newValue,oldValue){
			if(newValue != oldValue){
				if(newValue == 0){
					$(".usClass").each(function(){
						$(this).show();
					});
				}else{
					$(".usClass").each(function(){
						$(this).hide();
						$("#returnType").combobox("setValue","");
						$("#depreciationRate").combobox("setValue","");
					});
				}
				//退货政策
				ocs.ajax({
					url:"/publication/getReturnPolicyData/"+newValue,
					type:"POST",
					data:"",
					success:function(returnData){
						if(returnData){
							var optionHtml = ["<option selected value></option>"];
							var retunType = returnData.returns_accepted;
							if(retunType){
								retunType = eval('('+retunType+')');
								$("#policyType").combobox("loadData",retunType);
							}else{
								$("#policyType").combobox("loadData",[]);
							}
							var returnDays = returnData.returns_within;
							if(returnDays){
								returnDays = eval('('+returnDays+')');
								$("#returnDays").combobox("loadData",returnDays);
							}else{
								$("#returnDays").combobox("loadData",[]);
							}
							var returnKind = returnData.refund;
							if(returnKind){
								//$("#dtReturnType").show();
								returnKind = eval('('+returnKind+')');
								$("#returnType").combobox("loadData",returnKind);
								
							}else{
								$("#returnType").combobox("loadData",[]);
								//$("#dtReturnType").hide();
							}
							var returnPayCost = returnData.shippingcost_paidby;
							if(returnPayCost){
								returnPayCost = eval('('+returnPayCost+')');
								$("#fareTakeInHander").combobox("loadData",returnPayCost);
								
							}else{
								$("#fareTakeInHander").combobox("loadData",[]);
							}
							var returnOldCost = returnData.restocking_feevalue;
							if(returnOldCost){
								//$("#dtReturnRestockingFee").show();
								returnOldCost = eval('('+returnOldCost+')');
								$("#depreciationRate").combobox("loadData",returnOldCost);
								
							}else{
								$("#depreciationRate").combobox("loadData",[]);
								//$("#dtReturnRestockingFee").hide();
							}
							/*var returnDesc = eval(returnData.description)?true:false;
							if(returnDesc){
								$("#returnPolicyDescriptionDiv").show();
							}else{
								$("#returnPolicyDescriptionDiv").hide();
							}*/
						}
					}
				});
			}
		}
	});
	$("#policyType").combobox({
		onChange : function(newValue,oldValue){
			if(newValue != oldValue){
				if(newValue == "ReturnsAccepted"){
					$(".hideClass").each(function(){
						$(this).show();
					});
				}else{
					$(".hideClass").each(function(){
						$(this).hide();
					});
					$("#returnDays").combobox("setValue","");
					$("#returnType").combobox("setValue","");
					$("#fareTakeInHander").combobox("setValue","");
					$("#depreciationRate").combobox("setValue","");
					$('#returnPolicyDescription').textbox("setValue","");
					$('#isAllowDelay').attr('checked',false);
				}
				
				//
				var curSiteId = $("#siteId").combobox("getValue");
				if(curSiteId == 0){
					$(".usClass").each(function(){
						$(this).show();
					});
				}else{
					$(".usClass").each(function(){
						$(this).hide();
						$("#returnType").combobox("setValue","");
						$("#depreciationRate").combobox("setValue","");
					});
				}
			}
		}
	});
})(returnPoliy,jQuery);