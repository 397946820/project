var disputeManager = {};
(function(disputeManager,$){
	var curDisputeId = '';
	var curDisputeIndex = '';
	var curUserCaseId = "";
	//回复
	disputeManager.answer = function(orderLineItemId,index){
		curDisputeIndex = index;
		$("#syncDisputeInfoTable").datagrid('selectRow',index);
		var row = $("#syncDisputeInfoTable").datagrid('getSelected');
		if(row.disputeReason == "SignificantlyNotAsDescribed"){
			$("#disputeActivityHide1").hide();
			$("#disputeActivityHide2").hide();
		}else{
			$("#disputeActivityHide1").show();
			$("#disputeActivityHide2").show();
		}
		$("#ebayDisputeAnswer").dialog("open");
		ocs.ajax({
			async : true,
			type:'POST',
			url:'/dispute/getOrderTransInfo/'+orderLineItemId,
			data:'',
			success:function(data){
				var tran = data.data;
				if(tran){
					$("#ShippingCarrierUsed").textbox("setValue",tran.carrier);
					$("#ShipmentTrackNumber").textbox("setValue",tran.trackingNumber);
					$("#ShippingTime").datetimebox("setValue",tran.shipTime);
				}else{
					$("#ShippingCarrierUsed").textbox("setValue","");
					$("#ShipmentTrackNumber").textbox("setValue","");
					$("#ShippingTime").datetimebox("setValue","");
				}
			}
		});
		//同步消息
		ocs.ajax({
			async : true,
			type:'POST',
			url:'/dispute/getOldDisputeMessage/'+row.dId,
			data:'',
			success:function(data){
				if(data.data){
					var question = [];
					$.each(data.data,function(){
						if(this.account=="testuser_yangguanbao"){
							question.push('<li style="list-style:none;float:right;width:100%">'+
												'<span style="font-weight:bold; float:right;max-width: 85px;word-break: break-all;">&nbsp;&nbsp;:'+this.account+'</span>'+
												'<div style="float:right;width:65%;">'+
													'<div style="float:right;border-top:7px solid transparent; border-bottom:7px solid transparent;border-left:10px solid #e4effe;margin-top:10px;"></div>'+
													'<div style="max-width:90%;float:right;">'+
														'<div style="background:#e4effe;float:right;max-width:100%;padding:10px;border-radius:5px;">'+this.disputeMessage+'</div>'+
														'<br clear="all"/>'+
														'<div style="text-align:left;margin-right:-20px;color:#a7a3a3;min-width: 150px;">'+this.disputeCreatedTime+'</div>'+
													'</div>'+
													'<br clear="all"/>'+
												'</div>'+
												'<br clear="all"/>'+
										'</li><br clear="all"/>');
						}else{
							question.push('<li style="list-style:none;width:100%">'+
											'<span style="font-weight:bold; float:left;max-width: 85px;word-break: break-all;">'+this.account+':&nbsp;&nbsp;</span>'+
//											'<div style="margin-bottom:10px;">'+
												'<div style="float:left;border-top:7px solid transparent; border-bottom:7px solid transparent;border-right:10px solid #e4effe;margin-top:10px;"></div>'+
												'<div style="max-width:65%;float:left">'+
													'<div style="background:#e4effe;float:left;width:100%;padding:10px;border-radius:5px;">'+this.disputeMessage+'</div>'+
													'<br clear="all"/>'+
													'<div style="text-align:right;margin-right:-20px;color:#a7a3a3;min-width: 150px;">'+this.disputeCreatedTime+'</div>'+
												'</div>'+
												'<br clear="all"/>'+
//											'</div>'+
										'</li><br clear="all"/>');
						}
					});
					$("#oldMessage").html(question.join(""));
				}
			}
		});
	}
	$('input:radio[name="DisputeActivity"]').change(function(){  
        var value = $(this).val();
        if(value == 'SellerShippedItem'){
        	$("#ShippingCarrierUsed").textbox({disabled: false});
			$("#ShipmentTrackNumber").textbox({disabled: false});
			$("#ShippingTime").datetimebox({disabled: false});
        }else{
        	$("#ShippingCarrierUsed").textbox({disabled: true});
			$("#ShipmentTrackNumber").textbox({disabled: true});
			$("#ShippingTime").datetimebox({disabled: true});
        }
		/*if($(this).is(":checked")){  
             alert($(this).attr("autocomplete"));  
        }else{  
             alert(0);  
        }  */
     }); 
	$("#disputeAnswerOk").click(function(){
		var answer = $("#MessageText").textbox("getValue");
		if(answer){
			$("#syncDisputeInfoTable").datagrid('selectRow',curDisputeIndex);
			var row = $("#syncDisputeInfoTable").datagrid('getSelected');
			var answerModel = {};
			answerModel["disputeID"]= row.disputeID;
			answerModel['account'] = row.account;
			answerModel['dId'] = row.dId;
			answerModel['DisputeActivity'] = $("input[name='DisputeActivity']:checked").val();
			answerModel['ShippingCarrierUsed'] =$("#ShippingCarrierUsed").textbox("getValue");
			answerModel['ShippingTime'] =$("#ShippingTime").datetimebox("getValue");
			answerModel['ShipmentTrackNumber'] =$("#ShipmentTrackNumber").textbox("getValue");
			answerModel['MessageText'] =$("#MessageText").textbox("getValue");
			/*var formData = $("#disputeAnswerForm").serializeArray();
			$.each(formData,function(){
				formData[this.name] = this.value;
			});*/
			ocs.ajax({
				url:'/dispute/answer',
				type:'POST',
				async:true,
    		    beforeSend: function () {
                   $.messager.progress({
                       title: '请稍后',
                       msg: '正在发送...'
                   });
                },
                complete: function () {
                   $.messager.progress('close');
                },
				data:answerModel,
				success:function(data){
					if(data.data == "success"){
						resetWindow();
						$("#syncDisputeInfoTable").datagrid("reload");
						$.messager.alert("信息","回复成功！");	
					}else{
						$.messager.alert("信息",data.data );	
					}
				}
			});
			
		}else{
			$.messager.alert('提示','备注内容为空!','warning');
		}
	});
	$("#disputeAnswerCancel").click(function(){
		resetWindow();
	});
	
	function resetWindow(){
		$("#disputeAnswerForm").form('clear');
		$("#ShippingCarrierUsed").textbox({disabled: true});
		$("#ShipmentTrackNumber").textbox({disabled: true});
		$("#ShippingTime").datetimebox({disabled: true});
		$("#ebayDisputeAnswer").dialog("close");
	}
	
	disputeManager.syncUpdate = function(index){
		$("#syncDisputeInfoTable").datagrid('selectRow',index);
		var row = $("#syncDisputeInfoTable").datagrid('getSelected');
		var disputeModel = {};
		disputeModel['id'] = row.id;
		disputeModel['caseId'] = row.caseId;
		disputeModel['caseType'] = row.caseType;
		disputeModel['account'] = row.account;
		ocs.ajax({
			url:'/dispute/syncUpdate',
			type:'POST',
			async:true,
		    beforeSend: function () {
                   $.messager.progress({
                       title: '请稍后',
                       msg: '正在更新...'
                   });
               },
           complete: function () {
               $.messager.progress('close');
           },
			data:disputeModel,
			success:function(data){
				if(data.data){
					$("#syncDisputeInfoTable").datagrid("reload");
					$.messager.alert("信息","更新成功！");	
				}
			}
		});
	}
	
	//备注
	disputeManager.remark = function(id){
		curDisputeId = id;
		$("#ebayDisputeRemark").dialog("open");
	}
	$("#disputeRemarkOk").click(function(){
		var remark = $("#disputeRemark").textbox("getValue");
		if(remark){
			var disputeModel = {};
			disputeModel['remark'] = remark;
			disputeModel['id'] = curDisputeId;
			ocs.ajax({
				url:'/dispute/remark',
				type:'POST',
				data:disputeModel,
				success:function(data){
					if(data.data){
						$("#disputeRemark").textbox("setValue","");
						$("#ebayDisputeRemark").dialog("close");
						$("#syncDisputeInfoTable").datagrid("reload");
					}
				}
			});
			
		}else{
			$.messager.alert('提示','备注内容为空!','warning');
		}
	});
	$("#disputeRemarkCancel").click(function(){
		$("#disputeRemark").textbox("setValue","");
		$("#ebayDisputeRemark").dialog("close");
	});
	$("#disputeListSearchParamsync").on("click",function(){
		ocs.ajax({
			url:'/dispute/syncEbayDisputeList',
			async:true,
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
				if(result.data){
					$("#syncDisputeInfoTable").datagrid('reload');
					$.messager.alert("信息","同步成功！");	
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//$.messager.alert("信息", "服务器发生错误！");		
			}
	   });
	})
	
	//初始化页面
	$("#syncDisputeInfoTable").datagrid(
	{
		url : '/ocs/dispute/list',
		queryParams : {
			param : {
				itemID:'',
				account :'',
				buyerUserID:'',
				transactionID:''
			}
		},
		columns : [ [
				{
					field : 'id',
					hidden:true
				},
				{
					field : 'itemId',
					hidden:true
				},
				{
					field : 'transactionId',
					hidden:true
				},
				{
					field : 'account',
					title : 'ebay账号',
					align : 'center',
					width : 60
				},
				{
					field : 'globalid',
					title : '站点',
					align : 'center',
					width : 60
				},
				{
					field : 'itemTitle',
					title : '物品标题/物品号/交易号',
					align : 'left',
					width : 200,
					formatter : function(value, row, index) {
						return '<span>'+value+'</span><br/><span style="color:blue">'+row.itemId+'</span><br/><span style="color:green">'+row.transactionId+'</span>';
					}
				},
				{
					field : 'caseId',
					title : '纠纷ID',
					align : 'center',
					width : 60
				},
				{
					field : 'caseType',
					title : '类型',
					align : 'center',
					width : 80
				},
				{
					field : 'status',
					title : '状态',
					align : 'center',
					width : 80
				},
				{
					field : 'buyerId',
					title : '买家',
					align : 'center',
					width : 80
				},
				{
					field : 'amount',
					title : '金额',
					align : 'center',
					width : 70
				},
				{
					field : 'quantity',
					title : '数量',
					align : 'center',
					width : 70
				},
				{
					field : 'transactionPrice',
					title : '交易价格',
					align : 'center',
					width : 80
				},
				{
					field : 'transactionDate',
					title : '交易时间(UTC)',
					align : 'center',
					width : 80
				},
				{
					field : 'creationDateStr',
					title : '创建时间(UTC)',
					align : 'center',
					width : 80
				},
				{
					field : 'lastModifiedDateStr',
					title : '最后修改时间(UTC)',
					align : 'center',
					width : 80
				},
				{
					field : 'remark',
					title : '备注',
					align : 'center',
					width : 80
				},
				{
					field : 'stock',
					title : '动作',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						var optHtml = [];
						optHtml.push('[<a href="javascript:void(0);" class="easyui-linkbutton" onClick="disputeManager.remark('+row.id+')" data-options="plain:true">备注</a>]');
						if(row.status.indexOf("CLOSED") == -1){
							optHtml.push('&nbsp;&nbsp;[<a href="javascript:void(0);" class="easyui-linkbutton" onClick="disputeManager.syncUpdate('+index+')" data-options="plain:true">更新</a>]');
						}
						
						return optHtml.join('');
						/*if(row.disputeState == "Closed"){
							return  '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="disputeManager.remark('+row.dId+')" data-options="plain:true">备注</a>';
						}
						if(row.disputeRecordType == "UnpaidItem"){
							return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="disputeManager.syncUpdate('+index+')" data-options="plain:true">更新</a> &nbsp;&nbsp;'+
							   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="disputeManager.remark('+row.dId+')" data-options="plain:true">备注</a>';
						}
						
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="disputeManager.answer(\''+row.orderLineItemID+'\','+index+')" data-options="plain:true">回复</a> &nbsp;&nbsp;'+
							   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="disputeManager.syncUpdate('+index+')" data-options="plain:true">更新</a> &nbsp;&nbsp;'+
							   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="disputeManager.remark('+row.dId+')" data-options="plain:true">备注</a>';*/
					}
				} ] ],
		//idField : 'dId',
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		pageSize : 20,
		border : true,
		nowrap:false,
		onLoadSuccess : function(data) {
			return data;
		},
		onDblClickRow : function(index,row){
			$("#ebayDisputeView").dialog("open");
			var userCaseId = row.id;
			//初始化信息
			$("#userCaseAppealTable").datagrid({
				url : '/ocs/dispute/getAppealListByParentId/'+userCaseId,
				columns : [ [
						{
							field : 'ID',
							title : 'ID',
							align : 'center',
							width : 60
						},
						{
							field : 'DECISION',
							title : '决策',
							align : 'center',
							width : 100
						},
						{
							field : 'REASONDETAIL_CODE',
							title : '说明CODE',
							align : 'left',
							width : 150
						},
						{
							field : 'REASONDETAIL_CONTENT',
							title : '说明内容',
							align : 'center',
							width : 200
						},
						{
							field : 'REASONDETAIL_DESCRIPTION',
							title : '说明描述',
							align : 'center',
							width : 250
						} ] ],
				rownumbers : true
			});
			
			$("#userCaseMoneyMovementTable").datagrid({
				url : '/ocs/dispute/getMoneyMovementListByParentId/'+userCaseId,
				columns : [ [
						{
							field : 'ID',
							title : 'ID',
							align : 'center',
							width : 100
						},
						{
							field : 'FROMPARTY_ROLE',
							title : '转账人',
							align : 'center',
							width : 100
						},
						{
							field : 'TOPARTY_ROLE',
							title : '收款人',
							align : 'center',
							width : 100
						},
						{
							field : 'AMOUNT',
							title : '金额',
							align : 'center',
							width : 100
						},
						{
							field : 'PAYMENTMETHOD',
							title : '支付方式',
							align : 'center',
							width : 100
						},
						{
							field : 'PAYPALTRANSACTIONID',
							title : 'PAYPAL交易ID',
							align : 'center',
							width : 160
						} ,
						{
							field : 'STATUS',
							title : '状态',
							align : 'center',
							width : 90
						} ,
						{
							field : 'TYPE',
							title : '类型',
							align : 'center',
							width : 120
						},
						{
							field : 'TRANSACTIONDATE',
							title : '交易时间',
							align : 'center',
							width : 120
						}  ] ],
				rownumbers : true
			});
			/**
			 * ACTIVITYDETAIL_DESCRIPTION,
                       AUTHOR_ROLE,
                       NOTE,
                       to_char(CREATIONDATE, 'yyyy-MM-dd hh24:mi:ss') CREATIONDATE
			 */
			$("#userCaseResponseHistoryTable").datagrid({
				url : '/ocs/dispute/getResponseHistoryListByParentId/'+userCaseId,
				columns : [ [
						{
							field : 'AUTHOR_ROLE',
							title : '发送人',
							align : 'center',
							width : 60
						},
						{
							field : 'ACTIVITYDETAIL_DESCRIPTION',
							title : '描述',
							align : 'center',
							width : 250
						},
						{
							field : 'NOTE',
							title : '内容',
							align : 'center',
							width : 300
						},
						{
							field : 'CREATIONDATE',
							title : '创建时间',
							align : 'center',
							width : 120
						}  ] ],
				rownumbers : true
			});
		
		}
		
	});
	
	$("#disputeListSearch").click(function(){
		var formData = $("#disputeListSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		$("#syncDisputeInfoTable").datagrid("load",{param :param});
	});
	$("#ebayAccount").combobox({
		valueField :"account",
		textField :"account",
		url : "/ocs/publication/getAccounts"
	});
	$("#disputeListReset").on('click',function(){
		$("#disputeListSearchParam").form("clear");
	});
})(disputeManager,jQuery)
