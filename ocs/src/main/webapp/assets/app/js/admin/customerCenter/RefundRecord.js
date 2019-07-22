var refundRecord = {};
(function(refundRecord,$){
	var beforeOrderId = null;
	var beforeWebSite= null;
	var beforeOrderDate = null;
	var beforeRefundDate= null;
	
	refundRecord.onAfterEdit = function(index,row,changes){
		var orderDate = row.order_date;
		var refundDate= row.refund_date;
		if(orderDate!=beforeOrderDate&&orderDate!=""){
			if(orderDate.indexOf("-")==-1){
				var dateType = new Date(orderDate);
				var dateStr = formatDateTime(dateType);
				row.order_date=dateStr;
				$('#CustomerRefundTable').datagrid('refreshRow', index); 
			}
		}
		if(refundDate!=beforeRefundDate&&refundDate!=""){
			if(refundDate.indexOf("-")==-1){
				var dateType = new Date(refundDate);
				var dateStr = formatDateTime(dateType);
				row.refund_date=dateStr;
				$('#CustomerRefundTable').datagrid('refreshRow', index); 
			}
			
		}
		var order = row.order_id
		var webSite = row.web_site;
		if(order!=beforeOrderId){
			mainAjax('/Tickets/selectTicketByOrderID?orderId='+row.order_id,null,null,"get",function(result) {
				if(result!=null){
			    row.platform=result.platform;
			    row.web_site=result.country;
			    row.order_date = result.order_date;
			    $('#CustomerRefundTable').datagrid('refreshRow', index); 
				}	
				});
			
			
		}
		if(webSite!=beforeWebSite){
		 	 	var value;
		 	 	if(webSite=="US"){
		 	 		value="$";
		 	 	}else if(webSite=="UK"){
		 	 		value="£";
		 	 	}else if(webSite=="DE"||webSite=="FR"||webSite=="ES"||webSite=="IT"){
		 	 		value="€";
		 	 	}else if(webSite=="JP"){
		 	 		value="￥";
		 	 	}else if(webSite=="CA"){
		 	 		value= "CanCDN$";
		 	 	}
		 	 	row.currency=value;
			    $('#CustomerRefundTable').datagrid('refreshRow', index); 
	}
			
		

		
       
	}
	refundRecord.onBeforeEdit =function(index,row){ 
		beforeOrderDate = row.order_date;
		beforeRefundDate= row.refund_date;
		beforeOrderId = row.order_id; 
		row.editing = true; 
	}
	
	$("#refundClear").click(function(){
		$("#CustomerRefundCondition").form("clear")
	})

	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#CustomerRefundTable').datagrid('validateRow', editIndex)){
			$('#CustomerRefundTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	refundRecord.onClickCell = function(index, field,value){
	
		if (endEditing()){
			editIndex = index;
			
				var va = $('#CustomerRefundTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
				
				
			
		}
	}
	function searchParam(){
		var formData = $("#CustomerRefundCondition").serializeArray();
		var param = {};
		
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	}
	var editIndex = undefined;
	$("#refundSearch").on('click',function(){
		var params = searchParam();
		$("#CustomerRefundTable").datagrid({
			queryParams:{
				param :params
			}
		});
		$("#CustomerRefundTable").datagrid("reload");
	});

	function rowEndEditing() {

		if(editIndex == undefined) {
			return true
		}
		if($('#CustomerRefundTable').datagrid('validateRow', editIndex)) {
			
			$('#CustomerRefundTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}

	refundRecord.onClickRow= function(index,row) {
	   
		if(editIndex != index) {
			if(rowEndEditing()) {
				$('#CustomerRefundTable').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#CustomerRefundTable').datagrid('selectRow', editIndex);
			}
		}
	}

	refundRecord.append = function(){
		if(rowEndEditing()) {
			$('#CustomerRefundTable').datagrid('insertRow', {
				index:0,
				row:{}
			});
			
			//$('#CustomerRefundTable').datagrid("beginEdit",0);
			editIndex=0;
		}
	}

	refundRecord.remove= function() {
		
		 var row = $('#CustomerRefundTable').datagrid('getSelected');
		 if(row!=null){
				 var r=confirm("你确定要删除该数据？");
				 if (r==true){
					 var id = row.report_id;
					 mainAjax('/RefundRecord/remove',{report_id:id},null,"get",function(result) {
						    $.messager.alert({
						      	 title:'消息',
						      	 msg:result.description,
						      	 width:'200px',
						      	 height:'150px'
						      	 
						       });

							 if(editIndex == undefined) {
								return
							 }
							 $('#CustomerRefundTable').datagrid('cancelEdit', editIndex)
								.datagrid('deleteRow', editIndex);
							 editIndex = undefined;
						   })
					
			 }
	   }else{
				 $.messager.alert({
			      	 title:'消息',
			      	 msg:'请选择删除的数据！',
			      	 width:'200px',
			      	 height:'180px'
			      	 
			       });
	   }
	}
	refundRecord.onEndEdition=function(index, row){
		
	    var ed = $(this).datagrid('getEditor', {
	        index: index,
	        field: 'site'
	    });
	    //row.country = $(ed.target).combobox('getText');
	}
	function accept() {
		if(endEditing()) {
			$('#CustomerRefundTable').datagrid('acceptChanges');
		}
	}

	function reject() {
		$('#CustomerRefundTable').datagrid('rejectChanges');
		editIndex = undefined;
	}

	function getChanges() {
		var rows = $('#CustomerRefundTable').datagrid('getChanges');
	}
	refundRecord.exportRefund=function(){
		
		var formData = $("#CustomerRefundCondition").serializeArray();
		var param = "";
		$.each(formData,function(){
			param = param + this.name+'='+ this.value+'&';
		});
		param = param.substr(0,param.length);
		 window.location.href = GLOBAL.domain+"/excel/export/exportRefnd?"+param;
	}
	refundRecord.saveRefund = function(){
		//var rows=$('#CustomerRefundTable').datagrid('getChanges');
		accept();
		var rows = $('#CustomerRefundTable').datagrid("getRows");
		var jsonArray = JSON.stringify(rows);
	    mainAjax('/RefundRecord/batchSaveRefund',jsonArray,'正在保存中......',"post",function(result) {
	    	 if(result.data==0){
	 	    	$.messager.alert({
	 		      	 title:'消息',
	 		      	 msg:'保存失败!',
	 		      	 width:'200px',
	 		      	 height:'150px'
	 		      	 
	 		       });
	 		    }else{
	    	 $('#CustomerRefundTable').datagrid('reload');
	 		    }
		   });
		
	}
})(refundRecord,jQuery);


$.extend($.fn.datagrid.defaults.editors, {
    timespinner: {
        init: function (container, options) {
            var input = $('<input/>').appendTo(container);
            input.timespinner(options);
            return input
        },
        getValue: function (target) {
            var val = $(target).timespinner('getValue');
        },
        setValue: function (target, value) {
            $(target).timespinner('setValue', value);
        },
        resize: function (target, width) {
            var input = $(target);
            if ($.boxModel == true) {
                input.resize('resize', width - (input.outerWidth() - input.width()));
            } else {
                input.resize('resize', width);
            }
        }
    }
});
