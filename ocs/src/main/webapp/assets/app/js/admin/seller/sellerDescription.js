function formatOper(val,row,index){  
	
    return '<a href="#" onclick="editseller('+index+')">编辑</a> &nbsp; <a href="#" onclick="destroyseller()">删除</a>';  
}  

function newseller(){
	clear();
    $('#sellerDialog').dialog('open').dialog('center').dialog('setTitle','添加');
}
function reloadTitle(siteId){
	if(siteId == 77){
		var tab = $('#sellerDescTabs').tabs('getTab',0);
		$('#sellerDescTabs').tabs('update', {
    	        tab: tab,
    	        options: {
    	           title: 'Zahlung'
    	        }
    	});
		tab = $('#sellerDescTabs').tabs('getTab',1);
		$('#sellerDescTabs').tabs('update', {
    	        tab: tab,
    	        options: {
    	           title: 'Lieferzeit'
    	        }
    	});
		tab = $('#sellerDescTabs').tabs('getTab',2);
		$('#sellerDescTabs').tabs('update', {
    	        tab: tab,
    	        options: {
    	           title: 'Feedback'
    	        }
    	});
		tab = $('#sellerDescTabs').tabs('getTab',3);
		$('#sellerDescTabs').tabs('update', {
    	        tab: tab,
    	        options: {
    	           title: 'Kunenservice'
    	        }
    	});
		tab = $('#sellerDescTabs').tabs('getTab',4);
		$('#sellerDescTabs').tabs('update', {
    	        tab: tab,
    	        options: {
    	           title: 'Über Uns'
    	        }
    	});
	}else{
		var tab = $('#sellerDescTabs').tabs('getTab',0);
		$('#sellerDescTabs').tabs('update', {
    	        tab: tab,
    	        options: {
    	           title: 'Payment'
    	        }
    	});
		tab = $('#sellerDescTabs').tabs('getTab',1);
		$('#sellerDescTabs').tabs('update', {
    	        tab: tab,
    	        options: {
    	           title: 'Shipment'
    	        }
    	});
		tab = $('#sellerDescTabs').tabs('getTab',2);
		$('#sellerDescTabs').tabs('update', {
    	        tab: tab,
    	        options: {
    	           title: 'Return'
    	        }
    	});
		tab = $('#sellerDescTabs').tabs('getTab',3);
		$('#sellerDescTabs').tabs('update', {
    	        tab: tab,
    	        options: {
    	           title: 'About us'
    	        }
    	});
		tab = $('#sellerDescTabs').tabs('getTab',4);
		$('#sellerDescTabs').tabs('update', {
    	        tab: tab,
    	        options: {
    	           title: 'FAQ'
    	        }
    	});
	}
}
function editseller(index){
	$('#sellerDescriptionTable').datagrid('selectRow',index);
    var row = $('#sellerDescriptionTable').datagrid('getSelected');
    if (row){
    	clear();
    	$("#sellerTemplateId").val(row.id);
    	$("#sellerTemplateName").textbox("setValue",row.name);
    	$("#sellerTemplateSiteid").combobox('setValue',row.siteId);
    	reloadTitle(row.siteId);
    	
    	if(row.description1 != null){
    		editor0.html(row.description1);
    		$("#editor_0").val(row.description1);
    		editor0.sync();
    	}
    	if(row.description2 != null){
    		editor1.html(row.description2);
    		$("#editor_1").val(row.description2);
    		editor1.sync();
    	}
    	if(row.description3 != null){
    		editor2.html(row.description3);
    		$("#editor_2").val(row.description3);
    		editor2.sync();
    	}
    	if(row.description4 != null){
    		editor3.html(row.description4);
    		$("#editor_3").val(row.description4);
    		editor3.sync();
    	}
    	if(row.description5 != null){
    		editor4.html(row.description5);
    		$("#editor_4").val(row.description5);
    		editor4.sync();
    	}
        $('#sellerDialog').dialog('open').dialog('center').dialog('setTitle','编辑');
    }
}

function clear(){
	$("#sellerTemplateId").val("");
	$("#sellerTemplateName").textbox("setValue","");
	$("#sellerTemplateSiteid").combobox('setValue',null);
	editor0.html("");
	$("#editor_0").val("");
	editor0.sync();
	editor1.html("");
	$("#editor_1").val("");
	editor1.sync();
	editor2.html("");
	$("#editor_2").val("");
	editor2.sync();
	editor3.html("");
	$("#editor_3").val("");
	editor3.sync();
	editor4.html("");
	$("#editor_4").val("");
	editor4.sync();
}
    
function saveseller(){
	var saveData = {};
	saveData["id"] = $("#sellerTemplateId").val();
	saveData["name"] = $("#sellerTemplateName").textbox("getValue");
	saveData["siteId"] = $("#sellerTemplateSiteid").combobox('getValue');
	saveData["description1"] = $("#editor_0").val();
	saveData["description2"] = $("#editor_1").val();
	saveData["description3"] = $("#editor_2").val();
	saveData["description4"] = $("#editor_3").val();
	saveData["description5"] = $("#editor_4").val();
	ocs.ajax({
		url: "/SellerDescription/addOrEdit",
        type: "POST",
        data: saveData,
        success:function(data){
        	$("#sellerDescriptionTable").datagrid("reload");
        	$('#sellerDialog').dialog('close');
        	$.messager.alert('消息','保存成功');
        }
	});    
}
getSiteImage = function(value,row,index){
	if((value == ""||value == null) && value != 0){
		value=22;
	}
	  return "<div class='icon3 country_size num_"+value+"'></div>";
}
    
function destroyseller(){
    	
	var ids='';
        var row = $('#sellerDescriptionTable').datagrid('getSelections');
        for(var i=0; i<row.length;i++){
        	ids=ids+row[i].id+',';
        }
         if (row && row.length!=0){
        	
            $.messager.confirm('删除','你确定要删除数据?',function(r){
                if (r){
                	
                	$.ajax({
						url: GLOBAL.domain+'/SellerDescription/deleteSellerDescriptionByIds',
						
						data: {ids:ids},
						
						dataType: "json",
						
						contentType: "application/json; charset=UTF-8",
						
						type: "get",
						
						success: function(date) {
							
							$('#sellerDescriptionTable').datagrid('reload'); 
							$('#sellerDescriptionTable').datagrid('clearSelections');
						},
						error: function(jqXHR, textStatus, errorThrown) {
					
							$.messager.show({    // show error message
                                title: '出现错误',
                                msg: '删除失败！'
                            });		
						}
					});
                }
            });
        } 
    
  
}
function copySeller(){

	var row = $('#sellerDescriptionTable').datagrid('getSelected');
	
	if(row){
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth()+1;
		var dated = date.getDate();
		var hours = date.getHours();
		var minutes = date.getMinutes();
		var seconds = date.getSeconds();
		var dateString = year+""+month+dated+hours+minutes+seconds;
		row.id="";
		row.name= row.name+"-copy" + dateString
		delete row["createBy"];
		delete row["creationDate"];
		delete row["lastUpdateBy"];
		delete row["lastUpdationDate"];
		$.ajax({
			url: GLOBAL.domain+'/SellerDescription/addOrEdit',
			
			data: row,
			
			dataType: "json",
			
			contentType: "application/json; charset=UTF-8",
			
			type: "get",
			
			success: function(date) {
			
				 $('#sellerDescriptionTable').datagrid('reload'); 
                
			},
			error: function(jqXHR, textStatus, errorThrown) {
		
				$.messager.show({    // show error message
                    title: '复制失败数据',
                    msg: '复制失败！'
                });		
			}
		});
	}
}

var curEditors ={};
$(function(){
	$("#sellerTemplateSiteid").combobox({
		onChange : function(newValue,oldValue){
			if(oldValue != newValue){
				reloadTitle(newValue);
			}
		}
	});
	var K;
	KindEditor.ready(function(K) {
		k = K;
        window.editor0 = K.create('#editor_0', {
     		afterCreate : function() { 
    			this.sync(); 
            }, 
            afterBlur:function(){ 
                this.sync(); 
            }        
    	});
        window.editor1 = K.create('#editor_1', {
     		afterCreate : function() { 
    			this.sync(); 
            }, 
            afterBlur:function(){ 
                this.sync(); 
            }        
    	});
        window.editor2 = K.create('#editor_2', {
     		afterCreate : function() { 
    			this.sync(); 
            }, 
            afterBlur:function(){ 
                this.sync(); 
            }        
    	});
        window.editor3 = K.create('#editor_3', {
     		afterCreate : function() { 
    			this.sync(); 
            }, 
            afterBlur:function(){ 
                this.sync(); 
            }        
    	});
        window.editor4 = K.create('#editor_4', {
     		afterCreate : function() { 
    			this.sync(); 
            }, 
            afterBlur:function(){ 
                this.sync(); 
            }        
    	});
	 });
	/*curEditors["0"] = K.create('#editor_0', {
		afterCreate : function() { 
			this.sync(); 
        }, 
        afterBlur:function(){ 
            this.sync(); 
        }        
	});*/
});
