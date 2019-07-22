var itemPromote = {};
(function(itemPromote,$){
	var curType = 0;
	var siteData = undefined;
	var curSiteId = undefined;
	$("#itemPromoteList").datagrid({
		url : '/ocs/ItemPromote/findItemPromoteList',
		columns : [ [
				{
					field : 'ck',
					checkbox:true
				},
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'itemId',
					hidden:true
				},
				{
					field : 'rules',
					hidden:true
				},
				{
					field : 'templateName',
					title : '推广模板名称',
					align : 'center',
					width : 100
				},
				{
					field : 'account',
					title : '账号',
					align : 'center',
					width : 100
				},
				{
					field : 'siteId',
					title : '站点',
					align : 'center',
					width : 100,
					formatter:function(val,row,index){
						var view = val;
						if(siteData){
							$.each(siteData,function(){
								if(val == this.value){
									view = this.displayName;
									return false;
								}
							});
						}else{
							ocs.ajax({
								url: "/publication/getSiteList",
						        type: "POST",
						        data: "",
						        success:function(data){
						        	if(data){
						        		siteData = data;
						        		$.each(data,function(){
							        		if(val == this.value){
							        			view = this.displayName;
							        			return false;
							        		}
							        	});
						        	}
						        }
							}); 
						}
						return view;
					}
				},
				{
					field : 'promoteType',
					title : '推广位置',
					align : 'center',
					width : 100,
					formatter:function(val,row,index){
						if(val == 0){
							return "顶部推广";
						}else if(val == 1){
							return "底部推广";
						}else{
							return "内部推广";
						}
					}
				},
				{
					field : 'dataType',
					title : '推广数据方式',
					align : 'center',
					width : 100,
					formatter:function(val,row,index){
						if(val == 1){
							return "规则方式";
						}else{
							return "指定方式";
						}
					}
				},
				{
					field : 'createUser',
					title : '创建人',
					align : 'center',
					width : 100
				},
				{
					field : 'createDate',
					title : '创建时间',
					align : 'center',
					width : 100
				},
				{
					field : 'updateUser',
					title : '修改人',
					align : 'center',
					width : 100
				},
				{
					field : 'updateDate',
					title : '修改时间',
					align : 'center',
					width : 100
				},
				{
					field : 'opt',
					title : '操作',
					align : 'center',
					width : 100,
					formatter : function(value,row,index){
						 return '<a href="javascript:void(0);" onclick="itemPromote.editPromote('+index+')">编辑</a>';  
					}
				}] ],
		singleSelect : true,
		rownumbers : true,
		fitColumns : true,
		border : true,
		nowrap:false,
		pagination :true,
		toolbar: [{
			iconCls: 'icon-add',
			text:'新增',
			handler: function(){
				$('#sellerForm').form('reset');
				$("#itemPromoteChooseResult").datagrid("loadData",[]);
			    $('#sellerDialog').dialog('open').dialog('center').dialog('setTitle','添加');
			    $('#dataForRuleTr').hide();
			    $("#dataForRuleButtonTr").hide();
				//禁用信息
				//$("#templateName").textbox({ readonly: false });
				//$("#templateAccount").combobox({ readonly: false });
				//$("#templateSite").combobox({ readonly: false });
				//$("#promoteType").combobox({ readonly: false });
				
			    $("#itemPromoteChooseResult").datagrid({
					url:'',
					rownumbers:true ,
					singleSelect : true,
					fitColumns : true,
				    columns:[[
						{field:'sku',hidden:true},
				    	{field:'productFirstCategoryId',hidden:true},
				    	{field:'ebayProductURL',hidden:true},
				    	{field:'ebayImages',title:'图片',width:80,align:'center',formatter:function(value,row,index){
				    		if(value){
				    			var imgs = value.split(",");
								return '<img style="height:50px;" src='+imgs[0]+'/>';
				    		}
				    		return '';
						}},
						{field:'itemId',title:'SKU/物品号/分类',width:115,align:'center',formatter:function(value,row,index){
							return '<span style="color:green;">'+row.sku+'</span><br/>'+value+'<br/>'+row.productFirstCategoryId;
						}},
						{field:'templateName',title:'名称',width:130,align:'center',formatter:function(value,row,index){
							return '<a href="'+row.ebayProductURL+'" target="_blank">'+value+'</a>';
						}},
						{field:'publicationType',title:'刊登类型',width:130,align:'center',formatter:function(value,row,index){
							var view = value;
							if(value == "Chinese"){
								view = "拍卖";
							}else if(value == "FixedPriceItem"){
								view = "固价";
							}else{
								view = "多属性";
							}
							return view;
						}},
						{field:'quantity_sold',title:'销售数量',width:80,align:'center'},
						{field:'price',title:'价格',width:80,align:'center'},
						{field:'opt',title:'操作',width:50,align:'center',formatter:function(value,row,index){
							return '<a href="javascript:void(0);" onclick="itemPromote.deleteSeletedItem(\''+row.itemId+'\')">删除</a>';
						}}
				    ]]
				});
			    $('#sellerDialog').dialog('resize');
			}
		},'-',{
			iconCls: 'icon-remove',
			text:'删除',
			handler: function(){
				itemPromote.destroyItemPromote();
			}
		}]
	});
	
	$("#dataForRuleTr").find("input[type=radio]").click(function(){
		if($(this).val() == 1){
			$(this).parents("td").next().show();
		}else{
			$(this).parents("td").next().hide();
		}
	});
	
	 $("input[name=dataType]").click(function(){
	    	switch($("input[name=dataType]:checked").attr("id")){
	    	case "dataForChoose":
	    		$('#dataForRuleTr').hide();
	    		$("#dataForRuleButtonTr").hide();
	    		$("#dataForChooseTr").show();
	    		$("#dataForSelected").show();
	    		var rules = {
	    				itemStore:"",
	    				itemType:"",
	    				publictionType:"",
	    				keyword:"",
	    				orderKey:"",
	    				priceEnd:"",
	    				priceStart:""
	    		};
	    		$("#sellerForm").form("load",rules);
	    		curType = 0;
	    		break;
	    	case "dataForRule":
	    		$("#dataForChooseTr").hide();
	    		$("#dataForSelected").hide();
	    		$('#dataForRuleTr').show();
	    		$("#dataForRuleButtonTr").show();
	    		$("#itemPromoteChooseResult").datagrid("loadData",[]);
	    		curType = 1;
	    		break;
	    	default:
	    		break;
	    	}
	    	$('#sellerDialog').dialog('resize');
	    });
	$("#itemDataChoose").click(function(){
		$("#productSelectLink").dialog('open');
		$("#selectProList").datagrid({
			url:'/ocs/ItemPromote/searchProduct',
			queryParams : {
				param:{templateName : $("#searchTemplateName").val(),
					   templateSKU :$("#searchTemplateSKU").val(),
					   templateItemId:$("#searchTemplateItemId").val(),
					   templateField:$("#searchTemplateField").val(),
					   siteId:$("#templateSite").combobox("getValue"),
					   account:$("#templateAccount").combobox("getValue")
					   }
			},
			pagination:true,
			rownumbers:true ,
			fitColumns : true,
			singleSelect : false,
		    columns:[[
		    	{field:'sku',hidden:true},
		    	{field:'ck',checkbox:true},
		    	{field:'productFirstCategoryId',hidden:true},
		    	{field:'ebayProductURL',hidden:true},
		    	{field:'ebayImages',title:'图片',width:80,align:'center',formatter:function(value,row,index){
		    		if(value){
		    			var imgs = value.split(",");
						return '<img style="height:50px;" src='+imgs[0]+'/>';
		    		}
		    		return '';
				}},
				{field:'itemId',title:'SKU/物品号/分类',width:125,align:'center',formatter:function(value,row,index){
					return '<span style="color:green;">'+row.sku+'</span><br/>'+value+'<br/>'+row.productFirstCategoryId;
				}},
				{field:'templateName',title:'名称',width:130,align:'center',formatter:function(value,row,index){
					return '<a href="'+row.ebayProductURL+'" target="_blank">'+value+'</a>';
				}},
				{field:'publicationType',title:'刊登类型',width:130,align:'center',formatter:function(value,row,index){
					var view = value;
					if(value == "Chinese"){
						view = "拍卖";
					}else if(value == "FixedPriceItem"){
						view = "固价";
					}else{
						view = "多属性";
					}
					return view;
				}},
				{field:'quantity_sold',title:'销售数量',width:80,align:'center'},
				{field:'price',title:'价格',width:80,align:'center'}
	
		    ]]
		});
	});
	
	//根据规则获取数据
	$("#dataForRuleLoad").click(function(){
		var formData = $("#sellerForm").serializeArray();
		var rules = {
				itemStore:"",
				itemType:"",
				publictionType:"",
				keyword:"",
				orderKey:"",
				priceEnd:"",
				priceStart:""
		};
		$.each(formData,function(){
			if(rules[this.name] == ""){
				rules[this.name] = this.value;
			}
		});
		var publictionType = [];
		//增加刊登类型的值
		$.each($('input[name="publictionType"]:checked'),function(){
			publictionType.push($(this).val()+";");
        });
		rules["publictionType"] = publictionType.join("");
		rules["siteId"] = $("#templateSite").combobox("getValue");
		rules["account"] = $("#templateAccount").combobox("getValue");
		ocs.ajax({
			url: "/ItemPromote/getItemsByRule",
	        type: "POST",
	        data: rules,
	        success:function(re){
	        	if(re){
	        		$("#itemPromoteChooseResult").datagrid("loadData",re);
	        	}
	        }
		});     
	});
	var first = false;
	$("#templateSite").combobox({
		onChange : function(newValue,oldValue){
			if(newValue != oldValue){
				curSiteId = newValue;
				if(null == newValue||newValue == ""){
					return;
				}
				ocs.ajax({
					url: "/ItemPromote/getItemType/"+curSiteId,
			        type: "POST",
			        data: "",
			        success:function(re){
			        	if(re){
			        		$('#itemTypeSelect').combotree('loadData',re);
			        	}
			        }
				});     
				ocs.ajax({
					url: "/ItemPromote/getItemStoreType/"+curSiteId,
			        type: "POST",
			        data: "",
			        success:function(re){
			        	if(re){
			        		$('#itemStoreSelect').combotree('loadData',re);
			        	}
			        }
				});    
				first = true;
				if(first){
					$("#itemPromoteChooseResult").datagrid("loadData",[]);
				}
				
			}
		}
	});
	
	//产品分类
	$("#itemTypeSelect").combotree({
		url : '',
		editable:true,
	    onSelect : function(node) {  
	        var tree = $(this).tree;  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            $('#itemTypeSelect').combotree('clear');  
	        }  
	    }  
	});
	
	//商店分类
	$("#itemStoreSelect").combotree({
		url : '',
		editable:true,
	    onSelect : function(node) {  
	        var tree = $(this).tree;  
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            //清除选中  
	            $('#itemStoreSelect').combotree('clear');  
	            return false;
	        }  
	    }  
	});
	
	//选择item数据
	itemPromote.selectItemOK = function(){
		var rows = $("#selectProList").datagrid("getChecked");
		if(rows&&rows.length> 0){
			$.each(rows,function(){
				var row = this;
				var resultRows = $("#itemPromoteChooseResult").datagrid("getData");
				var isExist = false;
				$.each(resultRows.rows,function(){
					if(this.itemId == row.itemId){
						isExist = true;
						return false;
					}
				});
				if(!isExist){
					$("#itemPromoteChooseResult").datagrid("appendRow",row);
				}
			});
			$("#productSelectLink").dialog('close');
		}else{
			$.messager.alert('提示','请至少选择一条数据','warning');
			return ;
		}
		
		
	}
	
	//编辑
	itemPromote.editPromote =function(index){
		$("#itemPromoteList").datagrid("selectRow",index);
		var row = $("#itemPromoteList").datagrid("getSelected");
		$('#sellerDialog').dialog('open').dialog('center').dialog('setTitle','编辑');
		curType = row.dataType; 
		if(row.dataType == 0){
			$('#dataForRuleTr').hide();
    		$("#dataForRuleButtonTr").hide();
    		$("#dataForChooseTr").show();
    		$("#dataForSelected").show();
		}else{
			$("#dataForChooseTr").hide();
			$("#dataForSelected").hide();
    		$('#dataForRuleTr').show();
    		$("#dataForRuleButtonTr").show();
		}
		//禁用信息
		//$("#templateName").textbox({ readonly: true });
		//$("#templateAccount").combobox({ readonly: true });
		//$("#templateSite").combobox({ readonly: true });
		//$("#promoteType").combobox({ readonly: true });
		var formData = {};
		for(var key in row){
			if(key =="rules"){
				if(row[key]){
					var rule = eval("("+row[key]+")");
					for(var rKey in rule){
						formData[rKey] = rule[rKey];
						if(rKey.indexOf("_check")> -1 ){
							if(rule[rKey] == "1"){
								$("#"+rKey+"0").parents("td").next().show();
							}else{
								$("#"+rKey+"0").parents("td").next().hide();
							}
						}
					}
				}
			}else{
				formData[key] = row[key];
			}
		}
		$("#sellerForm").form("load",formData);
		var publictionType = formData["publictionType"];
		if(publictionType){
			$.each($('input[name="publictionType"]'),function(){
				var cValue = $(this).val();
				if(publictionType.indexOf(cValue+";")> -1){
					$(this).attr("checked",true);
				}
	        });
		}else{
			$.each($('input[name="publictionType"]:checked'),function(){
				$(this).attr("checked",false);
	        });
		}
		$('#sellerDialog').dialog('resize');
		$("#itemPromoteChooseResult").datagrid({
			url:'',
			rownumbers:true ,
			singleSelect : true,
			fitColumns : true,
		    columns:[[
				{field:'sku',hidden:true},
		    	{field:'productFirstCategoryId',hidden:true},
		    	{field:'ebayProductURL',hidden:true},
		    	{field:'ebayImages',title:'图片',width:80,align:'center',formatter:function(value,row,index){
		    		if(value){
		    			var imgs = value.split(",");
						return '<img style="height:50px;" src='+imgs[0]+'/>';
		    		}
		    		return '';
				}},
				{field:'itemId',title:'SKU/物品号/分类',width:115,align:'center',formatter:function(value,row,index){
					return '<span style="color:green;">'+row.sku+'</span><br/>'+value+'<br/>'+row.productFirstCategoryId;
				}},
				{field:'templateName',title:'名称',width:130,align:'center',formatter:function(value,row,index){
					return '<a href="'+row.ebayProductURL+'" target="_blank">'+value+'</a>';
				}},
				{field:'publicationType',title:'刊登类型',width:130,align:'center',formatter:function(value,row,index){
					var view = value;
					if(value == "Chinese"){
						view = "拍卖";
					}else if(value == "FixedPriceItem"){
						view = "固价";
					}else{
						view = "多属性";
					}
					return view;
				}},
				{field:'quantity_sold',title:'销售数量',width:80,align:'center'},
				{field:'price',title:'价格',width:80,align:'center'},
				{field:'opt',title:'操作',width:50,align:'center',formatter:function(value,row,index){
					if(curType == 0){
						return '<a href="javascript:void(0);" onclick="itemPromote.deleteSeletedItem(\''+row.itemId+'\')">删除</a>';
					}else{
						return '';
					}
					
				}}
		    ]]
		});
		ocs.ajax({
			url: "/ItemPromote/getTemplateItems/"+row.id,
	        type: "get",
	        data: "",
	        success:function(re){
	        	if(re){
	        		$("#itemPromoteChooseResult").datagrid("loadData",re);
	        		
	        	}
	        }
		});     
	}
	
	//保存
	itemPromote.saveItemPromote = function(){
		var isOk = $("#sellerForm").form("validate");
		if(!isOk){
			$.messager.alert('提示','数据填充不完整！','warning');
			return;
		}
		var saveData = $("#sellerForm").serializeArray();
		var save = {};
		var rules = {
				itemStore:"",
				itemType:"",
				publictionType:"",
				keyword:"",
				orderKey:"",
				priceEnd:"",
				priceStart:"",
				account1 : "",
				siteId1 :"",
				account_check :"",
				site_check :"",
				publictionType_check:"",
				itemType_check:"",
				itemStore_check : ""
		};
		$.each(saveData,function(){
			if(rules[this.name] == ""){
				rules[this.name] = this.value;
			}else{
				save[this.name] = this.value;
			}
		});
		var publictionType = [];
		$.each($('input[name="publictionType"]:checked'),function(){
			publictionType.push($(this).val()+";");
        });
		rules["publictionType"] = publictionType.join("");
		
		save["rules"] = "";
		var itemIds = [];
		if(curType == 0){
			var rows = $("#itemPromoteChooseResult").datagrid("getData");
			if(rows.rows){
				$.each(rows.rows,function(){
					itemIds.push(this.itemId);
				});
			}
		}else{
			save["rules"] = JSON.stringify(rules);
		}
		save["itemId"] = itemIds.join(",");
		ocs.ajax({
			url: "/ItemPromote/save",
	        type: "POST",
	        data: save,
	        success:function(re){
	        	if(re){
	        		$.messager.alert('提示','保存成功','info');
	        		$('#sellerForm').form("clear");
	                $('#sellerDialog').dialog('close');   
	                $('#itemPromoteList').datagrid('reload');
	        	}
	        }
		});     
	}
	
	itemPromote.deleteSeletedItem = function(itemId){
		var resultRows = $("#itemPromoteChooseResult").datagrid("getData");
		var row = undefined;
		$.each(resultRows.rows,function(){
			if(this.itemId == itemId){
				row = this;
				return false;
			}
		});
		if(row){
			var index =  $("#itemPromoteChooseResult").datagrid("getRowIndex",row)
			$("#itemPromoteChooseResult").datagrid("deleteRow",index);
		}
		
	}
	
	itemPromote.searchProduct = function(){
		var param ={};
		var templateName = $("#searchTemplateName").val();
		if(!ocs.stringIsNull(templateName)){
			param["templateName"] = templateName;
		}
		param["siteId"] = $("#templateSite").combobox("getValue");
		param["account"] = $("#templateAccount").combobox("getValue");
		param["templateSKU"] = $("#searchTemplateSKU").val();
		param["templateItemId"] = $("#searchTemplateItemId").val();
		param["templateField"] = $("#searchTemplateField").val();
		$("#selectProList").datagrid("options").queryParams["param"] = param;
		$("#selectProList").datagrid("reload");
	}
	
	itemPromote.destroyItemPromote = function(){
		var row = $('#itemPromoteList').datagrid('getChecked');
		if(row && row.length > 0){
			$.messager.confirm('删除', '你确定要删除数据?', function(r) {
				if (r) {
					var ids = [];
					$.each(row,function(){
						ids.push(this.id);
					});
					ocs.ajax({
						url: "/ItemPromote/deleteItemPromoteByIds?ids="+ids.join(","),
				        type: "post",
				        data:"",
				        success:function(){
				        	$('#itemPromoteList').datagrid('reload');
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
})(itemPromote,jQuery);
