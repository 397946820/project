var cClaimProduct = {};
(function(cClaimProduct,$){
	cClaimProduct.getDate=function(value,row,index){
    	var date = new Date(value).toLocaleString();
    	var va =  '<input class="easyui-datetimebox"   value="'+date+'" style="width:150px">';
    	return va;
    }
	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#claimProductTable').datagrid('validateRow', editIndex)){
			$('#claimProductTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	var beforeOrderId = null;
	var beforeClaimDate = null;
	cClaimProduct.onAfterEdit = function(index,row,changes){
		var claimDate=row.claim_date;
		if(claimDate!=beforeClaimDate&&claimDate!=""){
			if(claimDate.indexOf("-")==-1){
				var dateType = new Date(claimDate);
				var dateStr = formatDateTime(dateType);
				row.claim_date=dateStr;
				$('#claimProductTable').datagrid('refreshRow', index); 
			}
		}
		var orderID =row.order_id;
		if(beforeOrderId!=orderID){
			
			 mainAjax('/ClaimProduct/selectClaimProductByOID',{id:row.id,orderId:orderID},null,"get",function(result) {
				 if(result.data>0){ 
					var info = "订单号:"+result.description+"已经存在";
				 $.messager.alert({
				      	 title:'消息',
				      	 msg:info,
				      	 width:'200px',
				      	 height:'150px'
				      	 
				       });
			      }
			})
		}
	}
	var formatDateTime = function (date) {  
        var y = date.getFullYear();  
        var m = date.getMonth() + 1;  
        m = m < 10 ? ('0' + m) : m;  
        var d = date.getDate();  
        d = d < 10 ? ('0' + d) : d;  
        var h = date.getHours();  
        h=h < 10 ? ('0' + h) : h;  
        var minute = date.getMinutes();  
        minute = minute < 10 ? ('0' + minute) : minute;  
        var second=date.getSeconds();  
        second=second < 10 ? ('0' + second) : second;  
        return y + '-' + m + '-' + d;  
    }; 
	cClaimProduct.onBeforeEdit = function(index,row){
		beforeOrderId = row.order_id; 
		beforeClaimDate=row.claim_date;
		row.editing = true; 
	}
	cClaimProduct.onClickCell = function(index, field,value){

		if (endEditing()){
			editIndex = index;
			
				var va = $('#claimProductTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
				
				
			
		}
	}
	
	function searchParam(){
		var formData = $("#claimProductCondition").serializeArray();
		var param = {};
		
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	}
	$("#claimProductSearch").on('click',function(){
		var params = searchParam();
		$("#claimProductTable").datagrid({
			queryParams:{
				param :params
			}
		});
		$("#claimProductTable").datagrid("reload");
	});
	$("#claimProductClear").click(function(){
		$("#claimProductCondition").form("clear")
	})
	function rowEndEditing() {
		if(editIndex == undefined) {
			return true
		}
		if($('#claimProductTable').datagrid('validateRow', editIndex)) {
			
			$('#claimProductTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}

	cClaimProduct.exportCClaimProduct=function(){
		var formData = $("#claimProductCondition").serializeArray();
		var param = "";
		$.each(formData,function(){
			param = param + this.name+'='+ this.value+'&';
		});
		param = param.substr(0,param.length);
		 window.location.href = GLOBAL.domain+"/excel/export/exportCClaimProduct?"+param;
	  }
	cClaimProduct.onClickRow=function(index,row) {
	   
		if(editIndex != index) {
			if(rowEndEditing()) {
				$('#claimProductTable').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#claimProductTable').datagrid('selectRow', editIndex);
			}
		}
	}

	cClaimProduct.append=function() {
		if(rowEndEditing()) {
			$('#claimProductTable').datagrid('insertRow', {
				index:0,
				row:{}
			});
			
			//$('#feedbacksTable').datagrid("beginEdit",0);
			editIndex=0;
		}
	}

	cClaimProduct.remove=function() {
		 var row = $('#claimProductTable').datagrid('getSelected');
		 if(row!=null){
				 var r=confirm("你确定要删除该数据？");
				 if (r==true){
					 var id = row.id;
					 mainAjax('/ClaimProduct/remove',{id:id},null,"get",function(result) {
						    $.messager.alert({
						      	 title:'消息',
						      	 msg:result.description,
						      	 width:'200px',
						      	 height:'150px'
						      	 
						       });

							 if(editIndex == undefined) {
								return
							 }
							 $('#claimProductTable').datagrid('cancelEdit', editIndex)
								.datagrid('deleteRow', editIndex);
							 editIndex = undefined;
						   })
						   $('#claimProductTable').datagrid('reload');
					
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
	cClaimProduct.onEndEdit=function(index, row){
	    var ed = $(this).datagrid('getEditor', {
	        index: index,
	        field: 'site'
	    });
	    row.country = $(ed.target).combobox('getText');
	}
	function accept() {
		if(endEditing()) {
			$('#feedbacksTable').datagrid('acceptChanges');
		}
	}

	function reject() {
		$('#feedbacksTable').datagrid('rejectChanges');
		editIndex = undefined;
	}

	function getChanges() {
		var rows = $('#claimProductTable').datagrid('getChanges');
		alert(rows.length + ' rows are changed!');
	}
	cClaimProduct.saveClaimProduct=function(){
		accept();
		var rows = $('#claimProductTable').datagrid("getRows");
		var jsonArray = JSON.stringify(rows);
	    mainAjax('/ClaimProduct/batchSaveClaimProduct',jsonArray,'正在保存中......',"post",function(result) {
	    	 if(result.data==0){
	 	    	$.messager.alert({
	 		      	 title:'消息',
	 		      	 msg:'保存失败!',
	 		      	 width:'200px',
	 		      	 height:'150px'
	 		      	 
	 		       });
	 		    }else{
		          $('#claimProductTable').datagrid('reload');
	 		    }
		   });
		
	}
})(cClaimProduct,jQuery);