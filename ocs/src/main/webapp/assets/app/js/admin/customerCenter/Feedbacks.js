var cFeedbacks = {};

(function(cFeedbacks,$){
	cFeedbacks.getDate=function(value,row,index){
    	var date = new Date(value).toLocaleString();
    	var va =  '<input class="easyui-datetimebox"   value="'+date+'" style="width:150px">';
    	return va;
    }
	var beforeSku=null;
	var beforeOrderDate = null;
	var beforeProblemTime = null;
	
	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#feedbacksTable').datagrid('validateRow', editIndex)){
			$('#feedbacksTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}

	cFeedbacks.onAfterEdit = function(index,row,changes){
		var sku = row.sku;
		var orderDate = row.order_date;
		var problemTime = row.problem_time;
		if(orderDate!=beforeOrderDate&&orderDate!=""){
			if(orderDate.indexOf("-")==-1){
				var dateType = new Date(orderDate);
				var dateStr = formatDateTime(dateType);
				row.order_date=dateStr;
				$('#feedbacksTable').datagrid('refreshRow', index); 
			}
		}
		if(typeof(sku) != "undefined"&&sku!=""){
		   var index = sku.indexOf("-");
		   var skuCode="";
			if(index>-1){
				 skuCode = sku.substring(0,index);
			}else{
				skuCode = sku;
			}
			mainSynAjax('/BaseCombobox/selectMetadatesBySku',{sku:skuCode},null,"get",function(result) {
   			 		    row.parent_category = result.parentCatagories;
       			 		row.catagories=result.catagories;
       			 		$('#feedbacksTable').datagrid('refreshRow', index); 
			   });
		}
		
		if(problemTime!=beforeProblemTime&&problemTime!=""){
			if(problemTime.indexOf("-")==-1){
				var dateType = new Date(problemTime);
				var dateStr = formatDateTime(dateType);
				row.problem_time=dateStr;
				$('#feedbacksTable').datagrid('refreshRow', index); 
			}
		}
	}
	cFeedbacks.onBeforeEdit =function(index,row){
		beforeSku = row.sku;
		beforeOrderDate = row.order_date;
		beforeProblemTime = row.problem_time;
		row.editing = true; 
	}
	cFeedbacks.onClickCell = function(index, field,value){

		if (endEditing()){
			   editIndex = index;
				var va = $('#feedbacksTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		}
	}
	
	function searchParam(){
		var formData = $("#FeedbacksCondition").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	}
	$("#feedbackSearch").on('click',function(){
		var params = searchParam();
		$("#feedbacksTable").datagrid({
			queryParams:{
				param :params
			}
		});
		$("#feedbacksTable").datagrid("reload");
	});
	$("#feedbackClear").click(function(){
		$("#FeedbacksCondition").form("clear")
	})
	function rowEndEditing() {
		if(editIndex == undefined) {
			return true
		}
		if($('#feedbacksTable').datagrid('validateRow', editIndex)) {
			
			$('#feedbacksTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}

	cFeedbacks.exportCFeedbacks=function(){
		var formData = $("#FeedbacksCondition").serializeArray();
		var param = "";
		$.each(formData,function(){
			param = param + this.name+'='+ this.value+'&';
		});
		param = param.substr(0,param.length);
		 window.location.href = GLOBAL.domain+"/excel/export/exportCFeedback?"+param;
	  }
	cFeedbacks.onClickRow=function(index,row) {
	   
		if(editIndex != index) {
			if(rowEndEditing()) {
				$('#feedbacksTable').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#feedbacksTable').datagrid('selectRow', editIndex);
			}
		}
	}

	cFeedbacks.append=function() {
		if(rowEndEditing()) {
			$('#feedbacksTable').datagrid('insertRow', {
				index:0,
				row:{}
			});
			
			//$('#feedbacksTable').datagrid("beginEdit",0);
			editIndex=0;
		}
	}

	cFeedbacks.remove=function() {
		 var row = $('#feedbacksTable').datagrid('getSelected');
		 if(row!=null){
				 var r=confirm("你确定要删除该数据？");
				 if (r==true){
					 var id = row.feedbacks_id;
					 mainAjax('/Feedbacks/remove',{feedbacks_id:id},null,"get",function(result) {
						    $.messager.alert({
						      	 title:'消息',
						      	 msg:result.description,
						      	 width:'200px',
						      	 height:'150px'
						      	 
						       });
						     
							 if(editIndex == undefined) {
								return
							 }
							 $('#feedbacksTable').datagrid('cancelEdit', editIndex)
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
	cFeedbacks.onEndEdit=function(index, row){
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
		var rows = $('#feedbacksTable').datagrid('getChanges');
		alert(rows.length + ' rows are changed!');
	}
	cFeedbacks.saveFeedacks=function(){
		//var rows=$('#feedbacksTable').datagrid('getChanges');
		accept();
		var rows = $('#feedbacksTable').datagrid("getRows");
		var jsonArray = JSON.stringify(rows);
	    mainAjax('/Feedbacks/batchSaveFeedbacks',jsonArray,'正在保存中......',"post",function(result) {
	    	 if(result.data==0){
	 	    	$.messager.alert({
	 		      	 title:'消息',
	 		      	 msg:'保存失败!',
	 		      	 width:'200px',
	 		      	 height:'150px'
	 		      	 
	 		       });
	 		    }else{
		    $('#feedbacksTable').datagrid('reload');
	 		    }
		   });
		
	}
})(cFeedbacks,jQuery);

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
