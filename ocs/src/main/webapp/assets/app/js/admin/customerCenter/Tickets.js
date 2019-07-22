var cTickets={};

(function(cTickets,$){
	var beforeSku=null;
	var beforeOrderDate=null
	var beforeProblemDate= null;
	var beforeFrequency=null;
	var beforeWebSite= null;
	
	cTickets.onAfterEdit = function(index,row,changes){
		var sku = row.sku;
		var orderDateZ = row.order_date;
		var problemDateZ = row.problem_date;
		
		if(orderDateZ!=beforeOrderDate&&orderDateZ!=""){
			if(orderDateZ.indexOf("-")==-1){
				var dateType = new Date(orderDateZ);
				var dateStr = formatDateTime(dateType);
				row.order_date=dateStr;
				$('#TicketsTable').datagrid('refreshRow', index); 
			}
		}
		if(problemDateZ!=beforeProblemDate&&problemDateZ!=""){
			if(problemDateZ.indexOf("-")==-1){
				var dateType = new Date(problemDateZ);
				var dateStr = formatDateTime(dateType);
				row.problem_date=dateStr;
				$('#TicketsTable').datagrid('refreshRow', index); 
			}
		}
		var webSite=row.country;
		if(webSite!=beforeWebSite&&webSite!=""){
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
		    $('#TicketsTable').datagrid('refreshRow', index); 
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
		
		var orderDate = row.order_date;
		var problemDate = row.problem_date;
		var frequency = row.frequency;
		if(beforeOrderDate!=orderDate||beforeProblemDate!=problemDate||frequency!=beforeFrequency){
			
			
			var orderDate = new Date(orderDate);
	        var problemDate = new Date(problemDate);
	        var seconds = (problemDate.getTime()-orderDate.getTime())/1000;
	        if(!isNaN(seconds)){
		        var totalDays = (seconds/(24*60*60)).toFixed(0);
		        row.total_days=totalDays;
		        $('#TicketsTable').datagrid('refreshRow', index); 
	        }
	       
			}
	        $('#TicketsTable').datagrid('refreshRow', index); 
       
	}
	$("#TicketsClear").click(function(){
		$("#TicketsCondition").form("clear")
	})
cTickets.onBeforeEdit =function(index,row){ 
		
		beforeSku = row.sku; 
		beforeOrderDate = row.order_date;
		beforeProblemDate = row.problem_date;
		beforeFrequency = row.frequency;
		row.editing = true; 
	}
	
	

	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#TicketsTable').datagrid('validateRow', editIndex)){
			$('#TicketsTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	cTickets.onClickCell = function(index, field,value){
	
		if (endEditing()){
			editIndex = index;
			
				var va = $('#TicketsTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
				
				
			
		}
	}
	function searchParam(){
		var formData = $("#TicketsCondition").serializeArray();
		var param = {};
		
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	}
	var editIndex = undefined;
	$("#TicketsSearch").on('click',function(){
		var params = searchParam();
		$("#TicketsTable").datagrid({
			queryParams:{
				param :params
			}
		});
	});


	function rowEndEditing() {
		if(editIndex == undefined) {
			return true
		}
		if($('#TicketsTable').datagrid('validateRow', editIndex)) {
			
			$('#TicketsTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}

	cTickets.onClickRow = function(index,row) {
	   
		if(editIndex != index) {
			if(rowEndEditing()) {
				$('#TicketsTable').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#TicketsTable').datagrid('selectRow', editIndex);
			}
		}
	}

	cTickets.append=function() {
		if(rowEndEditing()) {
			$('#TicketsTable').datagrid('insertRow', {
				index:0,
				row:{}
			});
			
			//$('#TicketsTable').datagrid("beginEdit",0);
			editIndex=0;
		}
	}
	cTickets.exportCTickets= function(){
		var formData = $("#TicketsCondition").serializeArray();
		var param = "";
		$.each(formData,function(){
			param = param + this.name+'='+ this.value+'&';
		});
		param = param.substr(0,param.length);
		 window.location.href = GLOBAL.domain+"/excel/export/exportCTickets?"+param;
	}
	cTickets.remove=function() {
		 var row = $('#TicketsTable').datagrid('getSelected');
		 if(row!=null){
				 var r=confirm("你确定要删除该数据？");
				 if (r==true){
					 var id = row.tickets_id;
					 mainAjax('/Tickets/remove',{tickets_id:id},null,"get",function(result) {
						    $.messager.alert({
						      	 title:'消息',
						      	 msg:result.description,
						      	 width:'200px',
						      	 height:'150px'
						      	 
						       });

							 if(editIndex == undefined) {
								return
							 }
							 $('#TicketsTable').datagrid('cancelEdit', editIndex)
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
	cTickets.onEndEdit = function(index, row){
	    var ed = $(this).datagrid('getEditor', {
	        index: index,
	        field: 'site'
	    });
	   // row.country = $(ed.target).combobox('getText');
	}
	function accept() {
		if(rowEndEditing()) {
			$('#TicketsTable').datagrid('acceptChanges');
		}
	}

	function reject() {
		$('#TicketsTable').datagrid('rejectChanges');
		editIndex = undefined;
	}

	function getChanges() {
		var rows = $('#TicketsTable').datagrid('getChanges');
		alert(rows.length + ' rows are changed!');
	}
	cTickets.saveTickets=function(){
		//var rows=$('#TicketsTable').datagrid('getChanges')
		accept();
		var rows = $('#TicketsTable').datagrid("getRows");
		var jsonArray = JSON.stringify(rows);
	    mainAjax('/Tickets/batchSaveTickets',jsonArray,'正在保存中......',"post",function(result) {
		    if(result.data==0){
	    	$.messager.alert({
		      	 title:'消息',
		      	 msg:'保存失败!',
		      	 width:'200px',
		      	 height:'150px'
		      	 
		       });
		    }else{
		    $('#TicketsTable').datagrid('reload');
		    }
		   });
		
	}
})(cTickets,jQuery);

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