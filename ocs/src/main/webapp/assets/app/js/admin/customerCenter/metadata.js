var skuModel = {};

(function(skuModel,$){

	
	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#skuTable').datagrid('validateRow', editIndex)){
			$('#skuTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}

	
	
	skuModel.onClickCell = function(index, field,value){

		if (endEditing()){
			   editIndex = index;
				var va = $('#skuTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		}
	}
	
	function rowEndEditing() {
		if(editIndex == undefined) {
			return true
		}
		if($('#skuTable').datagrid('validateRow', editIndex)) {
			
			$('#skuTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
    skuModel.getOperation = function(value,row,index){
    	var result;
        var dicker = '<a href="javascript:void(0);" onclick="skuModel.saveSkuModel('+index+');" class="easyui-linkbutton" style="margin-left:5px">保存</a>';
		var repulse = '<a href="javascript:void(0);" onclick="skuModel.remove('+index+');" class="easyui-linkbutton" style="margin-left:5px">删除</a>';
		result = dicker+repulse;
		return result;
    	
    }
	skuModel.append=function() {
		if(rowEndEditing()) {
			$('#skuTable').datagrid('insertRow', {
				index:0,
				row:{}
			});
			editIndex=0;
		}
	}

	skuModel.remove=function(index) {
		accept();
		$('#skuTable').datagrid('selectRow',index);
		 var row = $('#skuTable').datagrid('getSelected');
		 if(row!=null){
				 var r=confirm("你确定要删除该数据？");
				 if (r==true){
					 var id = row.id;
					 mainAjax('/BaseCombobox/skuRemove',{id:id},null,"get",function(result) {
						    $.messager.alert({
						      	 title:'消息',
						      	 msg:result.description,
						      	 width:'200px',
						      	 height:'150px'
						      	 
						       });
						     
							 if(editIndex == undefined) {
								return
							 }
							 $('#skuTable').datagrid('cancelEdit', editIndex)
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
	
	function accept() {
		if(endEditing()) {
			$('#skuTable').datagrid('acceptChanges');
		}
	}

	function reject() {
		$('#skuTable').datagrid('rejectChanges');
		editIndex = undefined;
	}

	function getChanges() {
		var rows = $('#skuTable').datagrid('getChanges');
		alert(rows.length + ' rows are changed!');
	}
	skuModel.saveSkuModel=function(index){
		accept();
		$('#skuTable').datagrid('selectRow',index);
		var row = $('#skuTable').datagrid("getSelected");
		var json = JSON.stringify(row);
		mainAjax("/BaseCombobox/skuSave",json,"正在保存中...","post",function(result){
			 $('#skuTable').datagrid('reload');
	         $.messager.alert({
 		      	 title:'消息',
 		      	 msg:result.description,
 		      	 width:'200px',
 		      	 height:'150px'
 		       });
		});
	}
})(skuModel,jQuery);
$(function(){
	$("#metadataReset").on('click',function(){
		$("#metadataCondition").form("clear");
	});
	$("#metadataSearch").on('click',function(){
		againSearch();
		
	});
	function againSearch(){
		var params = searchParam();
		$("#skuTable").datagrid({
			queryParams:{
				param :params
			}
		});
		$("#skuTable").datagrid("reload");
	}
	function searchParam(){
		var formData = $("#metadataCondition").serializeArray();
		var param = {};
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	}
	
});

