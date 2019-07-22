document.write('<script language="javascript" src="'+ GLOBAL.domain +'/assets/app/js/easyui/locale/datagrid-detailview.js"></script>');
$(function(){
	$('#recycleBestOffersTable').datagrid({
		 view: detailview,
         detailFormatter:function(index,row){
        	 var result = '<div style="padding:2px;position:relative;"><table  id="recycleBestOffersTable'+index+'"></table></div>';
             return result;
         },
         onExpandRow: function(index,row){
        	 var subIndex = index;
        	 var subDatagriId = "#recycleBestOffersTable"+index
             $(subDatagriId).datagrid({
                 url: GLOBAL.domain+'/BestOffers/selectRecycleEBayBestOffersByItemId?itemId='+row.itemId,
                 fitColumns:true,
                 singleSelect:true,
                 rownumbers:true,
                 loadMsg:'',
                 height:'auto',
                 columns:[[
                	 {field:'quantity',title:'数量',width:50,align:'center' ,hidden:true},
                	 {field:'currency',title:'币种',width:50,align:'center' ,hidden:true},
                     {field:'best_offer_id',title:'议价ID',width:50,align:'center'},
                     {field:'user_id',title:'买家ID',width:50,align:'center'},
                     {field:'email',title:'买家邮箱',width:50,align:'center'},
                     {field:'prices',title:'原价/议价',width:50,align:'center'},
                     {field:'message',title:'留言',width:50,align:'center'},
                     {field:'endTime',title:'结束时间',width:50,align:'center'},
                     {field:'best_offer_code_type',title:'议价类型',width:50,align:'center'},
                     {field:'operation',title:'操作',width:50,align:'center'
                     ,formatter : function(value, row, index) {
                    	 var result='<a href="javascript:void(0);"  onclick="recycle('+subIndex+');" class="easyui-linkbutton" >还原</a>';
 						return result;
 					}
                    },
                 ]],
                 onResize:function(){
                     $('#recycleBestOffersTable').datagrid('fixDetailRowHeight',index);
                 },
                 onLoadSuccess:function(){
                     setTimeout(function(){
                         $('#recycleBestOffersTable').datagrid('fixDetailRowHeight',index);
                     },0);
                 }
             });
             $('#recycleBestOffersTable').datagrid('fixDetailRowHeight',index);
         }
     });
	
	});
function recycle(index){
	debugger;
	var tableId = "#recycleBestOffersTable"+index;
	var row = $(tableId).datagrid('getSelected');
	var best_offer_id = row.best_offer_id;
	if(row!=null){
		$.messager.confirm('删除', '你确定要还原改数据?', function(r) {
			if (r) {
				$.ajax({
					
					url:GLOBAL.domain+'/BestOffers/restoreBestOfferByBestId',
					
					data:{best_offer_id:best_offer_id},
					
					dataType:"json",
					
					contentType:"application/json; charset=UTF-8",
				    beforeSend: function () {
			               $.messager.progress({
			                   title: '请稍后',
			                   msg: '正在还原中...'
			               });
			           },
			          complete: function () {
			               $.messager.progress('close');
			           },
					type: "get",
				
					success: function(result) {
						  $(tableId).datagrid('reload');
						     $.messager.alert({
						    	 title:'消息',
						    	 msg:result.description,
						    	 width:'300px',
						    	 height:'250px'
						    	 
						     });
				
					},
					error: function(jqXHR, textStatus, errorThrown) {
						$.messager.alert("信息", "服务器发生错误！");		
					}
			   });
			}
		});
	}else{
		$.messager.alert('提示','请先选择一条数据后操作!','warning');
		return;
	}
	
}