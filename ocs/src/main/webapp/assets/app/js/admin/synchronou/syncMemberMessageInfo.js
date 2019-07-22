var memberMessageManager = {};
(function(memberMessageManager,$){
	var curMessageId = '';
	var curMessageIndex = '';
	memberMessageManager.updateSelectToRead = function(){
		
		updateRead(1);
	}
	
	memberMessageManager.updateSelectToUnRead = function(){
		updateRead(0);
	}
	
	function updateRead(read){
		var rows= $('#syncMemberMessageInfoTable').datagrid('getSelections');
		if(rows.length<=0){
			$.messager.alert('信息','请选择标记的消息！');
			return ;
		}else{
			$.messager.confirm('更改','你确定更改选择的消息标记为已读吗？',function(r){
				if(r){
						var ids="";
						for(var i=0;i<rows.length;i++){
							ids=ids+rows[i].id+',';
						}
						ids=ids.substr(0,ids.length-1);
						mainAjax('/message/updateRead',{id:ids,read:read},null,"get",function(result){
							$.messager.alert({
								title:'消息',
								msg:result.description,
								width:'300px',
								height:'240px'
							});
							$("#syncMemberMessageInfoTable").datagrid("reload");
							memberMessageManager.getCount();
							
						});
				}
			});
			
		}
		
	}
	//回复
	memberMessageManager.answer = function(id,index){
		ocs.ajax({
			type:"GET",
			url:'/message/updateRead?id='+id+'&read=1',
			async:false,
			success:function(data){
				//$("#syncMemberMessageInfoTable").datagrid("reload");
				//memberMessageManager.getCount();
			}
		});
		window.parent.setCorrelationId(id);
		window.parent.addParentArticleTab("消息回复("+id+")","/message/messageAnswer");
	}
	
	$("#ebayAccount").combobox({
		valueField :"account",
		textField :"account",
		url : "/ocs/publication/getAccounts"
	});
	
	
	$("#memberMessageAnswerCancel").click(function(){
		$("#memberMessageAnswer").textbox("setValue","");
		$("#ebayMemberMessageAnswer").dialog("close");
	});
	
	
	//备注
	memberMessageManager.remark = function(id){
		curMessageId = id;
		$("#ebayMemberMessageRemark").dialog("open");
	}
	$("#memberMessageRemarkOk").click(function(){
		var remark = $("#memberMessageRemark").textbox("getValue");
		if(remark){
			var messageModel = {};
			messageModel['remark'] = remark;
			messageModel['id'] = curMessageId;
			ocs.ajax({
				url:'/message/memberRemark',
				type:'POST',
				data:messageModel,
				success:function(data){
					if(data.data){
						$("#memberMessageRemark").textbox("setValue","");
						$("#ebayMemberMessageRemark").dialog("close");
						$("#syncMemberMessageInfoTable").datagrid("reload");
					}
				}
			});
			
		}else{
			$.messager.alert('提示','备注内容为空!','warning');
		}
	});
	$("#memberMessageRemarkCancel").click(function(){
		$("#memberMessageRemark").textbox("setValue","");
		$("#ebayMemberMessageRemark").dialog("close");
	});
	$("#syncMemberMessageInfosync").on("click",function(){
		ocs.ajax({
	    			url:'/message/syncEbayMemberMessgaeList',
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
	    					$("#syncMemberMessageInfoTable").datagrid('reload');
							$.messager.alert("信息","同步成功！");	
						}
	    			},
	    			error: function(jqXHR, textStatus, errorThrown) {
	    				//$.messager.alert("信息", "服务器发生错误！");		
	    			}
	    	   });
	})
	//初始化页面
	$("#syncMemberMessageInfoTable").datagrid(
	{
		url : '/ocs/message/memberMessagelist',
		singleSelect: false,
		queryParams : {
			param : {
				sender:'',
				account :'',
				startDate:'',
				endDate:'',
				messageStatus:'',
				recipientID:'',
				senderID:'',
				title:'',
				subject:'',
				body:'',
				sku:''
			}
		},
		columns : [ [
			{
				   field:'check',
				   checkbox:true
			   },
				{
					field : 'viewItemURL',
					hidden:true
				},
				{
					field : 'price',
					hidden:true
				},
				
				{
					field : 'currentPrice',
					hidden:true
				},
				{
					field : 'displayToPublic',
					hidden:true
				},
				
				{
					field : 'senderEmail',
					hidden:true
				},
				{
					field : 'startTime',
					hidden:true
				},
				{
					field : 'endTime',
					hidden:true
				},
				{
					field : 'messageID',
					hidden:true
				},
				{
					field:'imageValue',
					hidden:true
				},
				{
					field:'image',
					title:'图片',
					formatter:function(value,row,index){
						if(value){
							var imgUrl = undefined;
							var index = value.indexOf(",");
							if(index > 0){
								imgUrl = value.substring(0,index-1)
							}else{
								imgUrl = value;
							}
							row.imageValue=imgUrl;
							$('#syncMemberMessageInfoTable').datagrid('refreshRow', index); 
							return '<img src="'+imgUrl+'" style="width:80px;height:80px;"/>';
							
						}
						return "";
					}
				},
				{
					field : 'sku',
					title : 'SKU',
					align : 'center'
				},
				{
					field : 'siteId',
					title:'站点',
					align : 'center',
					formatter:function(value,row,index){
						
						if((value == "" || value == null) && value!= 0){
							value=22;
						}
					    return "<div class='icon3 country_size num_"+value+"'></div>";

					}
				},
				{
					field : 'itemID',
					title : 'itemID',
					align : 'center'
				},
				{
					field : 'title',
					title : '物品标题',
					align : 'left',
					formatter: function(value,row,index){
						return '<a href="'+row.viewItemURL+'" target="_blank">'+value+'</a>';
					}
				},
				{
					field:'messageType',
					title:"消息类型"
					
				},
				{
					field:'read',
					title:"阅读状态",
					hidden:true,
					align : 'center',
					formatter: function(value,row,index){
						/*var result ="";
						if(value==0){
							result="<div class='icon3 unread'></div>";
						}else if(value==1){
							result = "<div class='icon3 read></div>";
						}
						return result;*/
					}
				},
				{
					field : 'subject',
					title : '主题',
					align : 'left'
				},
				{
					field : 'body',
					title : '消息内容',
					align : 'left',
					formatter: function(value,row,index){
						var view = value;
						if(value.indexOf("@media")> -1){
							ocs.ajax({
								type:"GET",
								async :false,
								url:"/message/findMessageBodyText/"+row.messageID,
								data:"",
								success:function(data){
									if(data.data){
										view = data.data;
									}
								}
							});
						}
						return view;
					}
				},
				{
					field : 'senderID',
					title : '发送人',
					align : 'center'
				},
				{
					field : 'recipientID',
					title : '收件人',
					align : 'center'
				},
				{
					field : 'messageStatus',
					title : '状态',
					align : 'center',
					formatter:function(value,row,index){//
						return value=='Unanswered'? row.read == 0?"<div class='icon3 unreply'></div>"
								: "<div class='icon3 reply'></div>"
								: "<div class='icon3 replied'></div>";
					}
				},
				{
					field : 'creationDateStr',
					title : '创建时间',
					align : 'center',
					formatter : function(value, row, index) {
						return memberMessageManager.format(value,8);
					}
				},
				{
					field : 'remark',
					title : '备注',
					align : 'center',
					formatter : function(value, row, index) {
						if(value){
							return "<span style='color:red;'>"+value+"</span>";
						}else{
							return "";
						}
					}
				},
				{
					field : 'stock',
					title : '动作',
					align : 'center',
					width : 50,
					formatter : function(value, row, index) {
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="memberMessageManager.answer('+row.id+','+index+')" data-options="plain:true">回复</a> &nbsp;&nbsp;'+
							   '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="memberMessageManager.remark('+row.id+')" data-options="plain:true">备注</a>';
					}
				} ] ],
		idField : 'id',
		rownumbers : true,
		fit:true,
		pagination : true,
		fitColumns : true,
		pageSize : 20,
		border : true,
		nowrap:false,
		onLoadSuccess : function(data) {
			$(this).datagrid("clearSelections");
		},
		toolbar : "#syncMemberMessageInfoBar"
	});
	
	$("#memberMessageListSearch").click(function(){
		var param = memberMessageManager.searchParam();
		$('#syncMemberMessageInfoTable').datagrid('load',{
			param : param
		});
		memberMessageManager.getCount();
	});
	
	memberMessageManager.format = function(value,num) {
		if(value != null && value != '') {
			var date=new Date(value);
			date.setHours(date.getHours()+(num));
			date = date.format("yyyy-MM-dd hh:mm:ss");
			return date;
		} else {
			return value;
		}
	}
	
	$("#memberMessageListReset").on('click',function(){
		$("#memberMessageListSearchParam").form("clear");
		$(".con-button li").each(function(){
			var value = $(this).find("input").val();
			if(value == '') {
				$(this).find("input").prop("checked","checked");
				$(this).css( "background",'#ccc').siblings().css( "background",'#fff');
			} else {
				$(this).siblings().find("input").prop("checked",false);
			}
		});
	});
	
	$(".con-button li").click(function(){
		$(this).find("input").prop("checked","checked");
		$(this).css( "background",'#ccc').siblings().css( "background",'#fff');
		$(this).siblings().find("input").prop("checked",false);
		var count = $(this).find("label").find("span").html();
		if(count == '(0)') {
			$("#syncMemberMessageInfoTable").datagrid('loadData', { total: 0, rows: [], footer:[] }); 
		} else {
			var params = memberMessageManager.searchParam();
			$("#syncMemberMessageInfoTable").datagrid("load",{
				param :params
			});
		}
	});
	
	memberMessageManager.searchParam = function(){
		var formData = $("#memberMessageListSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		if(param['startDate'] != '') {
			param['startDate'] = memberMessageManager.format(param['startDate'],-8);
		}
		if(param['endDate'] != '') {
			param['endDate'] = memberMessageManager.format(param['endDate'],-8);
		}
		return param;
	}
	
	memberMessageManager.getCount = function() {
		$(".con-button li").each(function(){
			var this_ = this;
			var param = memberMessageManager.searchParam();
			param['messageStatus'] = $(this).find("input").val();
			ocs.ajax({
				type:"POST",
				data : param,
				url:"/message/countEmm",
				success:function(data){
					$(this_).find("span").html("("+data.data+")");
				}
			});
		});
	}
	memberMessageManager.getCount();
	
})(memberMessageManager,jQuery);

