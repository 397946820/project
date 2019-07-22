var productReview = {};
(function(productReview,$){
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#productTable').datagrid('validateRow', editIndex)){
			$('#productTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	var beforeSolutionDate=null;
	var beforeSku=null;
	productReview.onAfterEdit = function(index,row,changes){
		var solutionDate= row.solution_date;
		var sku = row.sku;
		if(solutionDate!=beforeSolutionDate&&solutionDate!=""){
			if(solutionDate.indexOf("-")==-1){
				var dateType = new Date(solutionDate);
				var dateStr = formatDateTime(dateType);
				row.solution_date=dateStr;
				$('#productTable').datagrid('refreshRow', index); 
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
	}
	productReview.onBeforeEdit = function(index,row,changes){
		beforeSku = row.sku; 
		beforeSolutionDate = row.solution_date;
		row.editing = true; 
	}
	productReview.onClickCell = function(index, field,value){
	
		if (endEditing()){
			editIndex = index;
			
				var va = $('#productTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
				
				
			
		}
	}
	function searchParam(){
		var formData = $("#ProductCondition").serializeArray();
		var param = {};
		
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	}
	var editIndex = undefined;
	$("#productSearch").on('click',function(){
		var params = searchParam();
		$("#productTable").datagrid({
			queryParams:{
				param :params
			}
		});
		
	});
	function rowEndEditing() {
		if(editIndex == undefined) {
			return true
		}
		if($('#productTable').datagrid('validateRow', editIndex)) {
			
			$('#productTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	productReview.exportPReview=function(){
		var formData = $("#ProductCondition").serializeArray();
		var param = "";
		$.each(formData,function(){
			param = param + this.name+'='+ this.value+'&';
		});
		param = param.substr(0,param.length);
		 window.location.href = GLOBAL.domain+"/excel/export/exportCProduct?"+param;
	}
	productReview.onClickRow=function(index,row) {
	   
		if(editIndex != index) {
			if(rowEndEditing()) {
				$('#productTable').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#productTable').datagrid('selectRow', editIndex);
			}
		}
	}
	$("#productClear").click(function(){
		$("#ProductCondition").form("clear")
	})

	productReview.append=function() {
		if(rowEndEditing()) {
			$('#productTable').datagrid('insertRow', {
				index:0,
				row:{}
			});
			
		//	$('#productTable').datagrid("beginEdit",0);
			editIndex=0;
		}
	}

	productReview.remove=function() {
		
		 var row = $('#productTable').datagrid('getSelected');
		 if(row!=null){
				 var r=confirm("你确定要删除该数据？");
				 if (r==true){
					 var id = row.product_id;
					 mainAjax('/ProductReview/remove',{product_id:id},null,"get",function(result) {
						    $.messager.alert({
						      	 title:'消息',
						      	 msg:result.description,
						      	 width:'200px',
						      	 height:'150px'
						      	 
						       });

							 if(editIndex == undefined) {
								return
							 }
							 $('#productTable').datagrid('cancelEdit', editIndex)
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
	productReview.onEndEdit=function(index, row){
	    var ed = $(this).datagrid('getEditor', {
	        index: index,
	        field: 'site'
	    });
	    row.country = $(ed.target).combobox('getText');
	}
	accept=function() {
		if(rowEndEditing()) {
			$('#productTable').datagrid('acceptChanges');
		}
	}

	function reject() {
		$('#productTable').datagrid('rejectChanges');
		editIndex = undefined;
	}

	function getChanges() {
		var rows = $('#productTable').datagrid('getChanges');
		alert(rows.length + ' rows are changed!');
	}
	productReview.saveProduct=function(){
		//var rows=$('#productTable').datagrid('getChanges')
		accept();
		var rows = $('#productTable').datagrid("getRows");
		var jsonArray = JSON.stringify(rows);
	    mainAjax('/ProductReview/batchSaveProduct',jsonArray,'正在保存中......',"post",function(result) {
	    	 if(result.data==0){
	 	    	$.messager.alert({
	 		      	 title:'消息',
	 		      	 msg:'保存失败!',
	 		      	 width:'200px',
	 		      	 height:'150px'
	 		      	 
	 		       });
	 		    }else{
	    	 $('#productTable').datagrid('reload');
	 		    }
		   });
		
	}
})(productReview,jQuery);

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

