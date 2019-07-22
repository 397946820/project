var feedback = {};
var getCommentType =  function(value,row,index){
	
	return "<img  src='"+ GLOBAL.domain + "/assets/app/images/synchronou/"+value+".gif'/>";
}
var getOperation= function(value,row,index){
	 var comments = '<a href="javascript:void(0);"  onclick="feedback.commentsLink('+index+')" class="easyui-linkbutton" >评价</a>';
	 var reply = '<a href="javascript:void(0);" onclick="feedback.replyLink('+index+');" class="easyui-linkbutton" style="margin-left:5px">回复</a>';
	 var followUp = '<a href="javascript:void(0);" onclick="feedback.followUpLink('+index+')" class="easyui-linkbutton" style="margin-left:5px">追加</a>';
	 //var note = '<a href="javascript:void(0);" onclick="feedback.note('+index+')" class="easyui-linkbutton" style="margin-left:5px">发送消息</a>';
	 var noteLink = '<a href="javascript:void(0);" onclick="feedback.noteLink('+index+')" class="easyui-linkbutton" style="margin-left:5px">备注</a>';
	 return comments+reply+followUp+noteLink;
}
var getImagesUrl= function(value,row,index){
	if(value){
		var imgUrl = undefined;
		var index = value.indexOf(",");
		if(index > 0){
			imgUrl = value.substring(0,index-1)
		}else{
			imgUrl = value;
		}
		return '<img src="'+imgUrl+'" style="width:80px;height:80px;"/>';
	}
	return "";
}
//卖家评论
var getSeller = function(value,row,index){
	
	var sellerId='';
	var leftCommentText='';
	var leftCommentTime='';
	if(row.seller_id!=null){
		
		sellerId='<a target="_blank" href="https://feedback.ebay.com/ws/eBayISAPI.dll?ViewFeedback2&userid='+row.seller_id+'&ftab=AllFeedback"><span>'+row.seller_id+'</span></a>';
	}
	if(row.left_comment_text!=null){
		leftCommentText='<span>:'+row.left_comment_text+'</span>';
		
	}
	if(row.left_comment_time!=null){
		 var createDate= new Date(row.left_comment_time);  
		leftCommentTime='<div>'+createDate.toLocaleString()+'</div>';
	}
	return sellerId+leftCommentText+leftCommentTime;
}
//物品号/订单号
var get

//标题/物品号/订单号
var getItemTitle = function(value,row,index){
	var itemTitle='';
	if(row.item_title!=null){
		
		if(row.product_url==''||row.product_url==null){
			itemTitle='<div>'+row.item_title+'</div>';
		}else{
			itemTitle='<a href="'+row.product_url+'" target="_blank"><div>'+row.item_title+'</div></a>';
		}
	}
	return itemTitle;
}
var getIdTitle = function(value,row,index){
	var itemId='';
	var transactionId='';
	var sku='';
	if(row.item_id!=null){
		itemId='<div style="color:green;">'+row.item_id+'</div>';
	}
	if(row.transaction_id!=null){
		transactionId='<div>'+row.order_line_item_id+'</div>';
	}
	if(row.sku!=null){
		sku='<div>'+row.sku+'</div>';
	}
	return itemId+transactionId+sku;
}

//买家评价
var getBuyer = function(value,row,index){
	var buyerId = '';
	var commentingUserScore='';
	var receivedCommentText='';
	var receivedCommentTime='';
	if(row.buyer_id!=null){
		buyerId='<a target="_blank" href="https://feedback.ebay.com/ws/eBayISAPI.dll?ViewFeedback2&userid='+row.buyer_id+'&ftab=AllFeedback"><span>'+row.buyer_id;
	}
	if(row.commenting_user_score!=null){
		commentingUserScore ='('+row.commenting_user_score+')</span></a>';
	}else{
		commentingUserScore ='(0)</span></a>';
	}
	if(row.received_comment_text!=null){
		receivedCommentText='<span>:'+row.received_comment_text+'</span>';
	}
	if(row.received_comment_time!=null){
		 var createDate= new Date(row.received_comment_time);  
          
		receivedCommentTime = '<div>'+createDate.toLocaleString()+'</div>'
	}
	return buyerId+commentingUserScore+receivedCommentText+receivedCommentTime;
}
function searchParam(){
	var formData = $("#feedbackToolbarListCondition").serializeArray();
	var param = {};
	$.each(formData,function(){
		param[this.name] = this.value;
	});
	return param;
}

(function(feedback,$){
	feedback.reset= function(){
		$("#feedbackToolbarListCondition").form("clear");
	}
	feedback.replyFollowUp = function(){
		var replyAddObject = $("#replyAdditionalForm").serializeArray();
		var responseType = $("#responseType").html();
		var param = {};
		param["response_type"] = responseType;
		$.each(replyAddObject,function(){
			param[this.name] = this.value;
		});
		
		mainAjax('/Feedback/replyFollowUp',param,'正在回复......',"get",null);
		$('#replyAdditionalDialog').dialog('close');
	}
	feedback.replyLink= function(index){
		var row = feedback.getRow(index);;
		$("#formId").val(row.id);
		$("#targetUserID").val(row.buyer_id);
		$("#feedbackID").val(row.received_feedback_id);
		$("#orderLineItemID").val(row.order_line_item_id);
		$("#responseType").html("Reply");
		$("#targetSellerId").val(row.seller_id);
		$('#replyAdditionalDialog').dialog('open').dialog('center').dialog('setTitle', '回复');
	}
	feedback.noteLink = function(index){
		var row = feedback.getRow(index);
		$("#remarkId").val(row.id);

		$('#noteDialog').dialog('open').dialog('center').dialog('setTitle', '备注');
			
	}
	feedback.followUpLink=function (index){
		var row = feedback.getRow(index);
		$("#formId").val(row.id);
		$("#targetUserID").val(row.buyer_id);
		$("#feedbackID").val(row.left_feedback_id);
		$("#orderLineItemID").val(row.order_line_item_id);
		$("#responseType").html("FollowUp");
		$('#replyAdditionalDialog').dialog('open').dialog('center').dialog('setTitle', '追加');
	}
	feedback.commentsLink=function(index){
		var row = feedback.getRow(index);
		$("#commentsFormId").val(row.id);
		$("#commentsOrderLineItemID").val(row.order_line_item_id);
		$("#commentsResponseType").html("Positive");
		$("#commentsTargetUserID").val(row.buyer_id);
		$("#commentsItemId").val(row.item_id);
		$("#commentsSellerId").val(row.seller_id);
		$('#commentsDialog').dialog('open').dialog('center').dialog('setTitle', '评论');
	}
	feedback.getRow=function(index){
		$("#feedbackDatagrid").datagrid('selectRow',index);
		return $("#feedbackDatagrid").datagrid('getSelected');
	}
	feedback.comments= function(){
		
		var replyAddObject = $("#commentsForm").serializeArray();
		var responseType = $("#commentsResponseType").html();
		var param = {};
		param["response_type"] = responseType;
		$.each(replyAddObject,function(){
			param[this.name] = this.value;
		});
		
		mainAjax('/Feedback/comments',param,'正在评论......',"get",null);
		$('#commentsDialog').dialog('close');
		
	}
	feedback.note = function(index){
		var noteObject = $("#noteForm").serializeArray();
		var param = {};
		$.each(noteObject,function(){
			param[this.name] = this.value;
		});
		mainAjax('/Feedback/note',param,'正在备注......',"get",null);
		$('#noteDialog').dialog('close');
		$('#noteForm').form('reset');
	}
	$('#feedbackDatagrid').datagrid({
		onRowContextMenu: function(e, rowIndex, rowData) {
	   	 //右键时触发事件
	        //三个参数：e里面的内容很多，真心不明白，rowIndex就是当前点击时所在行的索引，rowData当前行的数据
	        e.preventDefault(); //阻止浏览器捕获右键事件
	        $(this).datagrid("clearSelections"); //取消所有选中项
	        $(this).datagrid("selectRow", rowIndex);
	        $('#menu').menu('show', {
	            left: e.pageX,
	            top: e.pageY
	        });
	    }
	});
	document.write('<script language="javascript" src="'+ GLOBAL.domain +'/assets/app/js/admin/ocs/main.js"></script>');
	
	feedback.feedbackSynchronous = function(){
		mainAjax('/Feedback/feedbackSynchronous',null,'正在同步反馈信息中......',"get",function(result) {
		    $.messager.alert({
		      	 title:'消息',
		      	 msg:result.description,
		      	 width:'600px',
		      	 height:'500px'
		      	 
		       });

		    $('#feedbackDatagrid').datagrid('reload');
		   });
		
		
	}

	$("#feedbackSearch").on('click',function(){
		
		$('#feedbackDatagrid').datagrid('load',{
			param : searchParam()
		});
		/*$("#feedbackDatagrid").datagrid({
			queryParams:{
				param :params
			}
		});
		$("#feedbackDatagrid").datagrid("reload");*/
	});
	
	
})(feedback,jQuery)