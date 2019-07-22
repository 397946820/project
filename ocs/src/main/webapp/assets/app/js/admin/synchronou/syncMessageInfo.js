var messageManager = {};
(function(messageManager,$){
	var curMessageId = '';
	//预览
	messageManager.view = function(index){
		$("#ebayMessageView").dialog("open");
		$("#syncMessageInfoTable").datagrid("selectRow",index);
		var row =  $("#syncMessageInfoTable").datagrid("getSelected");
		$("#ebayMessageViewDiv").html(row.text);
	}
	//备注
	messageManager.remark = function(id){
		curMessageId = id;
		$("#ebayMessageRemark").dialog("open");
	}
	
	//标记已读
	$("#readLinkbutton").click(function(){
		messageManager.updateOcsReadStatus(1);
	});
	
	//标记未读
	$("#unReadEditLinkbutton").click(function(){
		messageManager.updateOcsReadStatus(0);
	});
	
	messageManager.updateOcsReadStatus = function(value) {
		var rows= $('#syncMessageInfoTable').datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert('信息','请选择标记的消息！');
			return ;
		}else{
			$.messager.confirm('更改','你确定更改选择的消息标记吗？',function(r){
				if(r){
					var arr = new Array();
		    		for (var i = 0; i < rows.length; i++) {
		    			arr.push(rows[i].id);
		    		}
		    		var ids = arr.join(",");
					ocs.ajax({
						url:'/message/updateOcsReadStatus',
						type:'POST',
						data : {'ids' : ids , 'ocsStatus' : value},
						success:function(data){
							if(data.errorCode == 0){
								$("#syncMessageInfoTable").datagrid('reload');
								$.messager.alert("信息","操作成功！");	
							}
						}
					});
				}
			});
		}
	}
	
	$("#messageRemarkOk").click(function(){
		var remark = $("#messageRemark").textbox("getValue");
		if(remark){
			var messageModel = {};
			messageModel['remark'] = remark;
			messageModel['id'] = curMessageId;
			ocs.ajax({
				url:'/message/remark',
				type:'POST',
				data:messageModel,
				success:function(data){
					if(data.data){
						$("#messageRemark").textbox("setValue","");
						$("#ebayMessageRemark").dialog("close");
						$("#syncMessageInfoTable").datagrid("reload");
					}
				}
			});
			
		}else{
			$.messager.alert('提示','备注内容为空!','warning');
		}
	});
	$("#messageRemarkCancel").click(function(){
		$("#messageRemark").textbox("setValue","");
		$("#ebayMessageRemark").dialog("close");
	});
	
	//初始化页面
	$("#syncMessageInfoTable").datagrid(
	{
		url : '/ocs/message/list',
		queryParams : {
			param : {
				sender:'',
				account :'',
				read:'',
				replied:'',
				messageType:'',
				dateType:'',
				startDate:'',
				endDate:''
				
				
			}
		},
		columns : [ [
				{
				    field:'check',
				    checkbox:true
			    },
				{
					field : 'messageId',
					hidden:true
				},
				{
					field : 'itemId',
					hidden:true
				},
				{
					field : 'context',
					hidden:true
				},
				{
					field : 'text',
					hidden:true
				},
				{
					field : 'sender',
					title : '发送人',
					align : 'center',
					width : 60
				},
				{
					field : 'recipientUserId',
					title : '收件账号',
					align : 'center',
					width : 60
				},
				{
					field : 'subject',
					title : '主题',
					align : 'left',
					width : 300,
					formatter: function(value,row,index){
						return '<a href="javascript:void(0);" onclick="messageManager.view('+index+')">'+value+'</a>';
					}
				},
				
				{
					field : 'read',
					title : '是否已读',
					align : 'center',
					width : 60,
					hidden:true,
					formatter: function(value,row,index){
						return value==0?"<div class='icon3 unread'></div>": "<div class='icon3 read'></div>";
					}
				},
				{
					field : 'receiveDate',
					title : '消息接收时间',
					align : 'center',
					width : 70,
					formatter : function(value, row, index) {
						return messageManager.format(value,8);
					}
				},
				{
					field : 'expirationDate',
					title : '消息失效时间',
					align : 'center',
					width : 80,
					formatter : function(value, row, index) {
						return messageManager.format(value,8);
					}
				},
				{
					field : 'messageType',
					title : '消息类型',
					align : 'center',
					width : 80
				},
				{
					field : 'replied',
					title : '是否回复',
					align : 'center',
					width : 60,
					formatter: function(value,row,index){
						return  value==1? "<div class='icon3 replied'></div>"
								: row.read == 0 ?"<div class='icon3 unreply'></div>"
										: "<div class='icon3 reply'></div>";
					}
				},
				{
					field : 'remark',
					title : '备注',
					align : 'center',
					width : 80,
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
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="messageManager.remark('+row.id+')" data-options="plain:true">备注</a>';
					}
				} ] ],
		idField : 'id',
		fit:true,
		singleSelect : false,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		pageSize : 30,
		border : true,
		onLoadSuccess : function(data) {
			$(this).datagrid('fixRowHeight'); 
			$(this).datagrid("clearSelections");
		},
		toolbar: "#yncMessageInfoBar"
	});
	$("#syncMessageInfoSync").on("click",function(){
		ocs.ajax({
			url:'/message/syncEbayMessgaeList',
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
					$("#syncMessageInfoTable").datagrid('reload');
					$.messager.alert("信息","同步成功！");	
				}
			},
			error: function(jqXHR, textStatus, errorThrown) {
				//$.messager.alert("信息", "服务器发生错误！");		
			}
	   });
	})
	$("#ebayAccount").combobox({
		valueField :"account",
		textField :"account",
		url : "/ocs/publication/getAccounts"
	});
	$("#messageListSearch").click(function(){
		var formData = $("#messageListSearchParam").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		if(param['dateType'] != '' && param['startDate'] == '' && param['endDate'] == '') {
			$.messager.alert("信息", "请选择时间！");
			return;
		}
		if(param['startDate'] != '') {
			param['startDate'] = messageManager.format(param['startDate'],-8);
		}
		if(param['endDate'] != '') {
			param['endDate'] = messageManager.format(param['endDate'],-8);
		}
		
		$('#syncMessageInfoTable').datagrid('load',{
			param : param
		});
		/*$("#syncMessageInfoTable").datagrid({
			queryParams:{
				param :param
			}
		});
		$("#syncMessageInfoTable").datagrid("reload");*/
	});
	
	messageManager.format = function(value,num) {
		if(value != null && value != '') {
			var date=new Date(value);
			date.setHours(date.getHours()+(num));
			date = date.format("yyyy-MM-dd hh:mm:ss");
			return date;
		} else {
			return value;
		}
	}
	
	$("#messageListReset").on('click',function(){
		$("#messageListSearchParam").form("clear");
	});
})(messageManager,jQuery)
