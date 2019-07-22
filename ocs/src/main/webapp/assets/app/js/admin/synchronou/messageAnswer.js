function cancelOrder(id){
		$.messager.confirm('提示', "您确定取消订单吗？", function(r){
			if (r){
				$("#saleCancelOrderWin").dialog({
					buttons:[{
						text:'确定',
						handler:function(){
							
							var casuse = $("#orderCancelCause").combobox("getValue");
							ocs.ajax({
				    			url:'/ebaySeller/cancelOrder/'+id+'/'+casuse,
				    			async:true,
				    		    beforeSend: function () {
			                       $.messager.progress({
			                           title: '请稍后',
			                           msg: '正在发送中...'
			                       });
				                },
				                complete: function () {
				                   $.messager.progress('close');
				                },
				    			type: "POST",
				    			success: function(result) {
				    				var me = result.data;
				    				if(me=="success"){
				    					$("#syncSaleInfoTable").datagrid('reload');
										$.messager.alert("信息","操作成功！","info");
										$("#saleCancelOrderWin").dialog("close");
									}else{
										$.messager.alert("信息",me,"warning");	
									}
				    			}
				    	    });
						}
					},{
						text:'取消',
						handler:function(){
							$("#saleCancelOrderWin").dialog("close");
						}
					}]
				});
				$("#saleCancelOrderWin").dialog("open");
			}
		});
	}

function  previewFile(){
	var files = document.getElementById("textBtn").files;
	var formData = new FormData();
	$.each(files,function(){
		//验证图片名称，大小
		if(this.type.toLowerCase() != "image/jpeg".toLowerCase()&&this.type.toLowerCase() != "image/jpg".toLowerCase()){
			$.messager.alert('提示','只能选择JPG格式图片！');
			return false;
		}
		if(this.size > 1024*1024){
			$.messager.alert('提示','只能小于1M的图片！');
			return false;
		}
		formData.append("file",this);
      
	});
	$.ajax({
        type:"post",
        url:"/ocs/upload/ebayImgSync",
        data:formData,
        beforeSend: function () {
            $.messager.progress({
                title: '请稍后',
                msg: '正在上传...'
            });
        },
        complete: function () {
           $.messager.progress('close');
        },
        processData : false,
        contentType : false,
        success:function(data){
        	//上传成功更新上传图片名称和分类
        	if(data){
        		var returnFiles = eval("("+data+")");
        		$.each(returnFiles,function(){
        			$(".imgPreview").append('<li><div class="delete-button">x</div><img src="/ocs/upload/image/'+this.id+'" class="previewImg" alt="Image preview..."/></li>');
        		});
        	}
        },
        error:function(e){

        }
    });

}
$(".messageAddImgBtn").click(function(){
	if ($("#textBtn")) {
		$("#textBtn").click();
	  }
})
$(".imgPreview").on("click",".delete-button",function(){
 $(this).parent("li").remove();
})
function detail(index){
		$("#syncSaleInfoTable").datagrid('selectRow',index);
		var row = $("#syncSaleInfoTable").datagrid('getSelected');
		$("#d_OrderID").text(row.orderId);
		$("#d_SalesRecoreNumber").text(row.saleRecordId);
		$("#d_BuyerUserID").text(row.buyerUserId);
		$("#d_SellerUserID").text(row.account);
		$("#saleDetial").dialog("open");
		//获取运输信息
		ocs.ajax({
			url:'/ebaySeller/getOrderTranInfo/'+row.id,
			async:false,
			type: "GET",
			success: function(result) {
				var tranInfo = result.data;
				if(tranInfo){
					$("#t_Name").text(tranInfo.NAME);
					$("#t_Street1").text(tranInfo.STREET1);
					$("#t_Street2").text(tranInfo.STREET2);
					if(tranInfo.CITYNAME){
						$("#t_CityName").text(tranInfo.CITYNAME);
					}else{
						$("#t_CityName").parent("span").next().hide();
						$("#t_CityName").parent("span").hide();
					}
					if(tranInfo.StateOrProvince){
						$("#t_StateOrProvnce").text(tranInfo.StateOrProvince);
					}else{
						$("#t_StateOrProvnce").parent("span").next().hide();
						$("#t_StateOrProvnce").parent("span").hide();
					}
					$("#t_posttalCode").text(tranInfo.POSTALCODE);
					$("#t_Phone").text(tranInfo.PHONE);
				}
			}
		});
		$("#saleDetailTable").datagrid({
			url : '/ocs/ebaySeller/getOrderItemById/'+row.id,
			columns : [ [
				{
					field : 'SKU',
					hidden:true
				},
				{
					field : 'TITLE',
					title : '物品标题',
					align : 'center',
					width : 180
				},
				{
					field : 'SITE',
					title : '站点、刊登类型',
					align : 'center',
					width : 100,
					formatter : function(value,row,index){
						var viewStr = '';
						ocs.ajax({
							url:'/ebaySeller/getItemInfo/'+row.ITEMID,
							async:true,
							type: "GET",
							success: function(result) {
								if(result.data){
									viewStr = result.data;
								}
							}
						});
						return viewStr;
					}
				},
				{
					field : 'ITEMID',
					title : '物品号/SKU',
					align : 'center',
					width : 120,
					formatter : function(value,row,index){
						return value+'<br/><span style="color:green">'+row.SKU+'</span>';
					}
				},
				{
					field : 'PRICE',
					title : '价格',
					align : 'center',
					width : 50
				},
				{
					field : 'QTY',
					title : '数量',
					align : 'center',
					width : 50
				},
				{
					field : 'CARRIER',
					title : '承运商',
					align : 'center',
					width : 100
				},
				{
					field : 'TRACKNUMBER',
					title : '跟踪号',
					align : 'center',
					width : 100
				},
				{
					field : 'SHIPTIME',
					title : '发货日期',
					align : 'center',
					width : 100
				},
				{
					field : 'TOTAL',
					title : '合计',
					align : 'center',
					width : 80,
					formatter : function(value,row,index){
						return row.PRICE * row.QTY;
					}
				}] ],
			idField : 'ID',
			singleSelect : true,
			rownumbers : true,
			border : true,
		    loadFilter: function(data){
		    	
				if(data){
					
					var totalPrice = 0;
					$.each(data,function(){
						totalPrice = totalPrice + this.PRICE * this.QTY;
					});
					$("#t_total").text(totalPrice);
				}
				return data;
			}
		});
		
	};
	
$(function(){
	var date =null;
	var id = window.parent.getCorrelationId(id);
	mainAjax('/message/memberMessageById',{id:id},null,'get',function(result){
		date=result;
		var imageUrl = getImage(result.image);
		$('#itemUrl').attr('href',result.viewItemURL); 
		$("#itemUrl").text(result.title);
		$("#itemSKU").html(result.sku);
		$("#itemIdSpan").html(result.itemID);
		var buyerUserId = result.senderID;
		var price="";
		if(result.price!=null){
			price = result.price;
		}
		var itemMinfo =  price+ " " + baseDate.getSiteCurrencyById(result.siteId)+"("+baseDate.getSiteNameById(result.siteId)+")";
		$("#itemMInfo").html(itemMinfo)
		if(imageUrl==undefined){
			$("#imageValue").attr("src","");
		}else{
			$("#imageValue").attr("src",imageUrl);
		}
		
		$("#syncSaleInfoTable").datagrid(
				{
					url : '/ocs/ebaySeller/list',
					queryParams : {
						param : {
							saleRecordNumber:'',
							orderId : '',
							account :'',
							buyerId:buyerUserId,
							payTimeStart:'',
							payTimeEnd:'',
							shipTimeStart:'',
							shipTimeEnd:'',
							orderTimeStart:'',
							orderTimeEnd:''
						}
					},
					  
					columns : [ [
							
							{
								field : 'account',
								title : 'ebay账号',
								align : 'center',
								hidden:true
							},
							{
								field:'saleRecordId',
								hidden:true
							},
							{
								field : 'buyerUserId',
								title : '买家ID',
								align : 'center',
								width : 120
							},
							{
								field : 'orderId',
								title : '订单号',
								align : 'center',
								width : 160
							},
							{
								field : 'amountPaid',
								title : '订单金额',
								align : 'center',
								width : 100
							},
							
							{
								field : 'createdTime',
								title : '下单时间',
								align : 'center',
								width : 100,
								formatter : function(value, row, index) {
									return formatTime(value,8);
										  
								}
							},
							{
								field : 'paidTime',
								title : '付款时间',
								align : 'center',
								width : 100,
								formatter : function(value, row, index) {
									return formatTime(value,8);
										  
								}
							},
							{
								field : 'shippedTime',
								title : '发货时间',
								align : 'center',
								width : 100,
								formatter : function(value, row, index) {
									return formatTime(value,8);
										  
								}
								
							},
							
							{
								field : 'stock',
								title : '操作',
								width : 100,
								align : 'center',
								formatter : function(value, row, index) {
									return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="detail('+index+')" data-options="plain:true">查看</a> &nbsp;&nbsp;';
										  
								}
							} ] ],
					idField : 'id',
					singleSelect :true,
					rownumbers : true,
//					pagination : true,
					fitColumns : true,
//					pageSize : 50,
					border : true
				});
	});
	
	ocs.ajax({
		async : true,
		type:'POST',
		url:'/message/getOldQuestion/'+id,
		data:'',
		success:function(data){
			if(data.data){
				var question = [];
				$.each(data.data,function(){
					
					if(this.senderID=="Me"){
						var imgs = [];
						var media = this.messagemedia;
						if(media != null && media != ''){
							var urls = media.split(",");
							$.each(urls,function(){
								imgs.push('<div style="float:left;width:100%;padding:10px;"><img style="width: 150px;" src="/ocs/upload/image/' + this +'"/></div>');
							});
							
						}
						question.push('<li style="list-style:none;float:right;margin-right:20px;margin-top:15px;">'+
										'<div style="margin-bottom:10px;">'+
											'<span style="font-weight:bold;float:right;margin-top:10px;text-align:right;">&nbsp;&nbsp;:'+this.senderID+'</span>'+
											'<div style="float:right;border-top:7px solid transparent; border-bottom:7px solid transparent;border-left:10px solid #e4effe;margin-top:10px;"></div>'+
											'<div style="max-width:65%;float:right">'+
												'<div style="background:#e4effe;float:right;max-width:100%;padding:10px;border-radius:5px;">'+this.body+imgs.join("")+'</div>'+
												
												'<br clear="all"/>'+
												'<div style="text-align:left;margin-right:-20px;color:#a7a3a3;min-width: 150px;">'+this.creationDateStr+'</div>'+
											'</div>'+
											'<br clear="all"/>'+
										'</div>'+
									'</li>'+
									'<br clear="all"/>');
					}else{
						var temp = '<span style="font-weight:bold;float:left;margin-top:10px;">'+this.senderID+':&nbsp;&nbsp;</span>'+
									'<div style="margin-bottom:10px;">'+
										'<div style="float:left;border-top:7px solid transparent; border-bottom:7px solid transparent;border-right:10px solid #e4effe;margin-top:10px;"></div>'+
										'<div style="max-width:65%;float:left">'+
											'<div style="background:#e4effe;float:left;width:100%;padding:10px;border-radius:5px;">'+this.body+'</div>'+
											'<br clear="all"/>'+
											'<div style="text-align:right;margin-right:-20px;color:#a7a3a3;min-width: 150px;">'+this.creationDateStr+'</div>'+
										'</div>'+
										'<br clear="all"/>'+
									'</div>';
						if(this.messagemedia != null && this.messagemedia != '') {
							temp += '<span style="font-weight:bold;float:left;margin-top:10px;">'+this.senderID+':&nbsp;&nbsp;</span>'+
									'<div style="margin-bottom:10px;">'+
										'<div style="float:left;border-top:7px solid transparent; border-bottom:7px solid transparent;border-right:10px solid #e4effe;margin-top:10px;"></div>'+
										'<div style="max-width:65%;float:left">'+
											'<div style="background:#e4effe;float:left;width:100%;padding:10px;border-radius:5px;"><img style="max-height:200px;min-width: 150px;" src="' + this.messagemedia +'"/></div>'+
											'<br clear="all"/>'+
											'<div style="text-align:right;margin-right:-20px;color:#a7a3a3;min-width: 150px;">'+this.creationDateStr+'</div>'+
										'</div>'+
										'<br clear="all"/>'+
									'</div>';
						}
						question.push('<li style="list-style:none;margin-top:15px;">'+ temp + '</li>');
					}
				});
				$("#oldMessage").html(question.join(""));
			}
		}
	});
	$("#memberMessageAnswerOk").click(function(){
//		(this).css({"height":$("#messageHeight").height()+"px",overflow:"hidden"});
		var answer = $("#memberMessageAnswer").textbox("getValue");
		if(answer){
			
			var answerModel = {};
			answerModel['answer'] = answer;
			answerModel['id'] = date.id;
			answerModel['recipientID'] = date.recipientID;
			answerModel['itemID'] = date.itemID;
			answerModel['messageId'] = date.messageID;
			answerModel['senderID'] = date.senderID;
			answerModel['subject'] = date.subject;
			if($("#isView").is(":checked")){
				answerModel['isView'] = 1;
			}else{
				answerModel['isView'] = 0;
			}
			//添加图片
			var imgUrl = [];
			var viewImgDiv = [];

			$(".imgPreview img").each(function(){
				var srcValue = $(this).attr("src");
				imgUrl.push(srcValue);
				viewImgDiv.push('<div style="float:left;width:100%;padding:10px;"><img style="width: 150px;" src="' + srcValue +'"/></div>');
			});
			answerModel['imgs'] = imgUrl.join(",");
			ocs.ajax({
				url:'/message/memberAnswer',
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
					if(data.data){
						$("#memberMessageAnswer").textbox("setValue","");
						$("#oldMessage").append('<li style="list-style:none;float:right;margin-right:20px;margin-top:15px;">'+
								'<div style="margin-bottom:10px;">'+
								'<span style="font-weight:bold;float:right;margin-top:10px;text-align:right;">&nbsp;&nbsp;:Me</span>'+
								'<div style="float:right;border-top:7px solid transparent; border-bottom:7px solid transparent;border-left:10px solid #e4effe;margin-top:10px;"></div>'+
								'<div style="max-width:65%;float:right">'+
									'<div style="background:#e4effe;float:right;max-width:100%;padding:10px;border-radius:5px;">'+answer+viewImgDiv.join("")+'</div>'+
									'<br clear="all"/>'+
									'<div style="text-align:left;margin-right:-20px;color:#a7a3a3;min-width: 150px;">'+dateToString()+'</div>'+
								'</div>'+
								'<br clear="all"/>'+
							'</div>'+
						'</li>'+
						'<br clear="all"/>');
						$.messager.alert("信息","回复成功！");
						$(".imgPreview").html("");
					}
				}
			});
			
		}else{
			$.messager.alert('提示','备注内容为空!','warning');
		}
	});
	
	function formatTime(value,num) {
		var date=new Date(value);
		date.setHours(date.getHours()+(num));
		date = date.format("yyyy-MM-dd hh:mm:ss");
		return date;
	}
	
	function getImage(value){
		
		if(value){
			var imgUrl = undefined;
			var index = value.indexOf(",");
			if(index > 0){
				imgUrl = value.substring(0,index)
			}else{
				imgUrl = value;
			}
			
			return imgUrl;
			
		}
		return "";
	}
	
	 function dateToString(){  
		var now=new Date(); 
		var millisec = now.getTime()-8*60*60*1000;
	    now.setTime(millisec)
	    year = now.getFullYear();  
	    month = (now.getMonth() + 1).toString();  
	    day =  (now.getDate()).toString();  
	    hour =  (now.getHours()).toString();  
	    minute =  (now.getMinutes()).toString();  
	    second =  (now.getSeconds()).toString(); 
	    if (month.length == 1) {  
	        month = "0" + month;  
	    }  
	    if (day.length == 1) {  
	        day = "0" + day;  
	    }  
	    if (hour.length == 1) {  
	        hour = "0" + hour;  
	    }  
	    if (minute.length == 1) {  
	        minute = "0" + minute;  
	    }  
	    if (second.length == 1) {  
	        second = "0" + second;  
	    }  
	     var dateTime = year + "-" + month + "-" + day +" "+ hour +":"+minute+":"+second;  
	     return dateTime;  
	  } 

})