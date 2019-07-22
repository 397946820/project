var skuLinkManager = {};
(function(skuLinkManager,$){
	var curEditIndex = undefined;

	$("#pSkuLinkListGrid").datagrid({
		url : '',
		queryParams : {
			param : {
				sku:'',
				pSku:''
			}
		},
		columns : [ [
				{
					field : 'ck',
					checkbox:true
				},
				{
					field : 'sku',
					title : '原始SKU',
					align : 'center',
					width : 100,
					editor: 'skuSelect'
				},
				{
					field : 'qty',
					title : '数量',
					align : 'center',
					width : 100,
					editor:'numberbox'
				}] ],
		singleSelect : false,
		rownumbers : true,
		fitColumns : true,
		border : true,
		nowrap:false,
		toolbar: [{
			iconCls: 'icon-add',
			text:'新增SKU',
			handler: function(){
				if(curEditIndex != undefined){
					$("#pSkuLinkListGrid").datagrid('endEdit',curEditIndex);
				}
				$("#pSkuLinkListGrid").datagrid('insertRow',{
					index:0,
					row:{
						sku:'',
						qty:1
					}
				});
				curEditIndex = 0;
				$("#pSkuLinkListGrid").datagrid('beginEdit',0);
				
			}
		},'-',{
			iconCls: 'icon-remove',
			text:'删除',
			handler: function(){
				var rows = $("#pSkuLinkListGrid").datagrid('getChecked');
				if(rows){
					$.each(rows,function(){
						var index = $("#pSkuLinkListGrid").datagrid('getRowIndex',this);
						$("#pSkuLinkListGrid").datagrid('deleteRow',index);
					});
				}
			}
		}],
		onClickRow : function(rowIndex, rowData){
			if(curEditIndex != undefined){
				$("#pSkuLinkListGrid").datagrid('endEdit',curEditIndex);
			}
			curEditIndex = rowIndex;
			$("#pSkuLinkListGrid").datagrid('beginEdit',rowIndex);
		}
	});
	
	function checkDataQTY(data){
		var flag = false;
		if(data){
			$.each(data,function(){
				if(this.qty == undefined ||this.qty == null||this.qty == ''|| this.qty <= 0){
					flag = true;
				}
			});
		}
		return flag;
	}
	
	function checkDataOtherRule(psku,data){
		//验证包含关系
		var isContain = true;
		$.each(data,function(){
			var sku = this.sku;
			if(psku.indexOf(sku) == -1){
				isContain = false;
				return false;
			}
		});
		return isContain;
	}
	
	skuLinkManager.pSkuSave = function(){
		var psku = $("#pSkuInput").textbox("getValue");
		if(psku){
			//验证特殊字符
			var reg = /^[0-9a-zA-Z\-\*,]*$/g;
			if(!reg.test(psku)){
				$.messager.alert('提示','您输入的销售sku不符合规则（只能包含字母、数字、英文横杆、英文逗号、星号）!','warning');
				return;
			}
			//验证销售sku不能与产品sku相同
			var sameFlag = false;
			ocs.ajax({
				url:"/edaManager/pSkuIsLikeSku/"+psku,
				async:false,
				type:"POST",
				data : "",
				success : function(result){
					if(result.data > 0){
						sameFlag = true;
					}
				}
			});
			if(sameFlag){
				$.messager.alert('提示','您输入的销售sku不能与已存在的的原始SKU同名!','warning');
				return;
			}
			if(curEditIndex != undefined){
				$("#pSkuLinkListGrid").datagrid('endEdit',curEditIndex);
			}
			var saveData = {};
			var insertData = $("#pSkuLinkListGrid").datagrid("getChanges","inserted");
			var updateData = $("#pSkuLinkListGrid").datagrid("getChanges","updated");
			var deleteData = $("#pSkuLinkListGrid").datagrid("getChanges","deleted");
			if(checkDataQTY(insertData)||checkDataQTY(updateData)||checkDataQTY(deleteData)){
				$.messager.alert('提示','SKU数量不能为空且必须大于0!','warning');
				return ;
			}
			saveData["pSku"] = psku;
			var otherRuleFlag = true;
			var c = 0;
			saveData["insertData"] = insertData;
			if(insertData){
				//验证包含关系
				otherRuleFlag = otherRuleFlag&&checkDataOtherRule(psku,insertData);
				c = c + insertData.length;
			}
			saveData["updateData"] = updateData;
			if(updateData){
				//验证包含关系
				otherRuleFlag = otherRuleFlag&&checkDataOtherRule(psku,updateData);
				c = c + updateData.length;
			}
			if(!otherRuleFlag){
				$.messager.alert('提示','销售SKU名称必须包含原始SKU名称!','warning');
				return ;
			}
			//验证sku长度
			var pi = psku.split(",");
			if(c != pi.length){
				$.messager.alert('提示','销售SKU映射的原始sku种类数不正确!','warning');
				return ;
			}
			
			saveData["deleteData"] = deleteData;
			ocs.ajax({
				url:"/edaManager/skuLinkSave",
				async:true,
				type:"POST",
				data : saveData,
				success : function(result){
					if(result){
						//$("#pSkuLinkListGrid").datagrid("acceptChanges");
						
						$("#skuLinkManagerDia").dialog("close");
						$("#skuLinkManagerList").datagrid("reload");
						$('#pSkuLinkListGrid').datagrid('loadData', { total: 0, rows: [] }); 
						$("#pSkuLinkListGrid").datagrid("acceptChanges");
						$("#pSkuInput").textbox("setValue","");
						$.messager.alert('提示','保存成功!','info');
					}else{
						$.messager.alert('提示','保存失败!','warning');
					}
				}
			});
		}else{
			$.messager.alert('提示','平台SKU名称不能为空!','warning');
			return ;
		}
	}
	
	$("#skuLinkManagerList").datagrid({
		url : '/ocs/edaManager/skuLinkList',
		queryParams : {
			param : {
				sku:'',
				pSku:''
			}
		},
		columns : [ [
				
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'pSku',
					title : '平台SKU',
					align : 'center',
					width : 100
				},
				{
					field : 'sku',
					title : '原始sku',
					align : 'center',
					width : 100,
					formatter:function(value,row,index){
						if(value.indexOf(",")>-1){
							var vs = value.split(",");
							var vss = [];
							for(var i= 0;i<vs.length;i++){
								if(i < vs.length -1){
									vss.push('<div style="text-align:center;white-space:normal;height:auto;border-bottom: dotted 1px #ccc;width: 99%;" class="datagrid-cell">'+vs[i]+'</div>');
								}else{
									vss.push('<div style="text-align:center;white-space:normal;height:auto;width: 99%;" class="datagrid-cell">'+vs[i]+'</div>');
								}
									
							}
							return vss.join("");
						}else{
							return value;
						}	
					}
				},
				{
					field : 'qty',
					title : '数量',
					align : 'center',
					width : 100,
					formatter:function(value,row,index){
						if(value.indexOf(",")>-1){
							var vs = value.split(",");
							var vss = [];
							for(var i= 0;i<vs.length;i++){
								if(i < vs.length -1){
									vss.push('<div style="text-align:center;white-space:normal;height:auto;border-bottom: dotted 1px #ccc;width: 99%;" class="datagrid-cell">'+vs[i]+'</div>');
								}else{
									vss.push('<div style="text-align:center;white-space:normal;height:auto;width: 99%;" class="datagrid-cell">'+vs[i]+'</div>');
								}
							}
							return vss.join("");
						}else{
							return value;
						}	
					}
				},
				{
					field : 'createDate',
					title : '创建时间',
					align : 'center',
					width : 100
				},
				{
					field : 'updateDate',
					title : '最后更新时间',
					align : 'center',
					width : 100
				}] ],
		singleSelect : true,
		rownumbers : true,
		fitColumns : true,
		border : true,
		nowrap:false,
		pagination : true,
		pageSize : 50,
		toolbar: [{
			iconCls: 'icon-add',
			text:'新增',
			handler: function(){
				$("#pSkuLinkListGrid").datagrid({
					url : ''
				});
				$('#pSkuLinkListGrid').datagrid('loadData', { total: 0, rows: [] }); 
				//$("#pSkuLinkListGrid").datagrid("reload");
				$("#skuLinkManagerDia").dialog("open");
				
			}
		},'-',{
			iconCls: 'icon-edit',
			text:'编辑',
			handler: function(){
				var row = $("#skuLinkManagerList").datagrid("getSelected");
				if(row){
					$("#pSkuInput").textbox("setValue",row.pSku);
					$("#pSkuLinkListGrid").datagrid({
						url : '/ocs/edaManager/getSkusByPSku/'+row.pSku
					});
					$("#pSkuLinkListGrid").datagrid("reload");
					$("#skuLinkManagerDia").dialog("open");
				}else{
					$.messager.alert('提示','请选择一条数据编辑!','warning');
					return;
				}
			}
		},'-',{
			iconCls: 'icon-redo',
			text:'导出全部',
			handler: function(){
				window.location.href = "/ocs/excel/export/pSkuLinkExport";
			}
		},'-',{
			iconCls: 'icon-undo',
			text:'导入数据',
			handler: function(){
				$("#fileUpload").dialog("open");
				$('#fileUploadInput').filebox({
				    buttonText: '文件选择',
				    buttonAlign: 'right'
				});
				$("#uploadSubmit").unbind("click").click(function(){
					$('#ImportForm').form('submit', {
						onSubmit: function(){
							$.messager.progress({
	                           title: '请稍后',
	                           msg: '导入中...'
	                        });
						},
						success:function(data){
							$("#fileUpload").dialog("close");
							$.messager.progress('close');
							var resu = undefined;
							var message = undefined;
							if(data&&data.indexOf("{")==0){
								data = eval('('+data+')');
								resu = data.data;
							}
							
							if(resu == undefined){
								$.messager.alert("信息","导入失败！");
								return ;
							}else{
								message = resu.message;
							}
					    	if(message){
					    		if(message instanceof Array){
						    		if(message.length > 0){
						    			//message
						    			var error = [];
						    			$.each(message,function(){
						    				error.push("<li>"+this+"</li>");
						    			});
						    			$("#saleTranImportErrorList").html(error.join(""));
						    			$("#saleTranImportMessageWin").dialog("open");
						    		}else{
						    			$("#skuLinkManagerList").datagrid("reload");
						    			$.messager.show({
						    				title:'消息',
						    				msg:'导入成功',
						    				timeout:3000,
						    				showType:'slide'
						    			});
						    		}
						    	}else{
						    		$.messager.alert("信息",message);	
						    	}
					    	}else{
					    		$.messager.alert("信息","导入失败！");	
					    	}
					    }
					});
				});
			}
		}]
	});
	
	$("#skuLinkManagerSearch").click(function(){
		var formData = $("#skuLinkManagerSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#skuLinkManagerList").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#skuLinkManagerList").datagrid("reload");
	});
	
	$("#skuLinkManagerReset").on('click',function(){
		$("#skuLinkManagerSearchParam").form("clear");
	});
	

	//sku选择器
	$.extend($.fn.datagrid.defaults.editors, {
	    skuSelect: {
			init: function(container, options){
				var input = $('<input type="text" class="datagrid-editable-input" readOnly="readOnly"/>').appendTo(container);
				input.click(function(){
					$('#skuListDia').dialog({
					    title: 'SKU选择',
					    width: 460,
					    height: 450,
					    closed: true,
					    cache: false,
					    modal: true,
					    buttons:[{
					    	iconCls: 'icon-ok',
					    	text:'确定',
							handler:function(){
								var row = $("#skuListGrid").datagrid("getChecked");
								if(row&&row.length>0){
									var exist = false;
									var data = $("#pSkuLinkListGrid").datagrid("getData");
									if(data){
										$.each(data["rows"],function(){
											if(row[0].sku == this.sku){
												exist = true;
												return false;
											}
										});
									}
									if(exist){
										$.messager.alert('提示','SKU已存在!','warning');
										return;
									}else{
										input.val(row[0].sku);
										$('#skuListDia').dialog("close");
									}
									
								}
							}
						},{
							iconCls: 'icon-cancel',
							text:'取消',
							handler:function(){
								$('#skuListDia').dialog("close");
							}
						}]
					});
					$("#skuListGrid").datagrid({
						height: 333,
						url : '/ocs/edaManager/productList',
						queryParams : {
							param : {
								sku:'',
							}
						},
						columns : [ [
								{
									field : 'ck',
									checkbox:true
								},
								{
									field : 'sku',
									title : '原始SKU',
									align : 'center',
									width : 100,
									editor: 'skuSelect'
								}
								] ],
						singleSelect : true,
						rownumbers : true,
						fitColumns : true,
						border : true,
						nowrap:false,
						pagination : true,
						pageSize : 50
					});
					$('#skuListDia').dialog("open");
				});
				$("#skuChooseSearch").click(function(){
					var sku = $("#skuChooseSku").textbox("getValue");
					$("#skuListGrid").datagrid({
						queryParams:{
							param :{sku: sku}
						}
					});
					$("#skuListGrid").datagrid("reload");
				});
				return input;
			},
			destroy: function(target){
				$(target).remove();
			},
			getValue: function(target){
				return $(target).val();
			},
			setValue: function(target, value){
				$(target).val(value);
			},
			resize: function(target, width){
				$(target).css("width","100%");
				$(target).css("height","18px");
			}
	    }
	});
})(skuLinkManager,jQuery)