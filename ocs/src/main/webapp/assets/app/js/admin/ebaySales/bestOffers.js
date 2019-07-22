var conditions=null;
function synchronousBestOffers(){

	mainAjax('/BestOffers/synchronousBestOffers',null,'正在更新议价信息中...','get',function(result){
		$('#bestOffersTable').datagrid('reload');
		$.messager.alert({
	         title:'消息',
	         msg:result.description,
	         width:'300px',
	         height:'240px'
               	      	 
        });
	});
	
}
function searchParam(){
	var formData = $("#bestOffersCondition").serializeArray();
	var param = {};
	if(conditions==null){
		conditions = parent.ocsPublication.getConditions();
	}
	param['conditions']=conditions;
	param['siteIdSort']='asc';
	param['itemIdSort']='asc';
	param['endTimeSort']='asc';
	$.each(formData,function(){
		param[this.name] = this.value;
	});
	return param;
}
var getPrices = function(value, row, index){
	return row.buyit_now_price+"/"+value+" " + row.currency;
}
var getImger = function(value,row,index){
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
var getItemInfo = function(value,row,index){
	if(value == null){
		value = '';
	}
	if(row.productUrl==null||row.productUrl==''){
		return "<span style='color:blue'>"+value+"</span>";
	}else{	
		return "<span style='color:blue'><a href='"+row.productUrl+"' target='_blank'>"+value+"<a></span>";
	}
}
var getOperation = function(value, row, index) {
	 var result='';
	 if(conditions=='Pending'){
	 var accept = '<a href="#"  onclick="accept('+index+');"  >接受</a>';
	 var repulse = '<a href="#" onclick="repulse('+index+');"  style="margin-left:5px">拒绝</a>';
	 var dicker = '<a href="#" onclick="dicker('+index+');"  style="margin-left:5px">还价</a>';
	 /*var ignore = '<a href="javascript:void(0);" onclick="ignore('+index+');" class="easyui-linkbutton" style="margin-left:5px">忽略</a>';*/
	 result = accept+repulse+dicker;
	 }
	return result;
	}

$(function(){
	$("#sellerId").combobox({
		valueField :"account",
		textField :"account",
		url : "/ocs/publication/getAccounts"
	});
	$("#bestOffersReset").on('click',function(){
		$("#bestOffersCondition").form("clear");
	});
	
	$("#bestOffersSearch").on('click',function(){
		$('#bestOffersTable').datagrid('load',{
			param : searchParam()
		});
		var params = searchParam();
		$("#bestOffersTable").datagrid({
			queryParams:{
				param :params
			}
		});
		$("#bestOffersTable").datagrid("reload");
	});
	
	var params = searchParam();
	$('#bestOffersTable').datagrid({
		onRowContextMenu: function(e, rowIndex, rowData) {
				       	 //右键时触发事件
				            //三个参数：e里面的内容很多，真心不明白，rowIndex就是当前点击时所在行的索引，rowData当前行的数据
				            e.preventDefault(); //阻止浏览器捕获右键事件
				            $(this).datagrid("clearSelections"); //取消所有选中项
				            $(this).datagrid("selectRow", rowIndex);
				            $('#menu').menu('show', {
				                left: e.pageX,
				                top: e.pageY
				            });},
		url:GLOBAL.domain+'/BestOffers/selectGroupbyBestOffers',
		queryParams:{param:params}
		
	});
	/*$('#bestOffersTable').datagrid({
		 view: detailview,
         detailFormatter:function(index,row){
        	 var result = '<div style="padding:2px;position:relative;"><table  id="bestOffersTable'+index+'"></table></div>';
             return result;
         },
         onExpandRow: function(index,row){
        	 var subIndex = index;
        	 var subDatagriId = "#bestOffersTable"+index
        	 var itemId = row.itemId;
             $(subDatagriId).datagrid({
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
                    	 var result=null;
                    	 var accept = '<a href="javascript:void(0);"  onclick="accept('+subIndex+');" class="easyui-linkbutton" >接受</a>';
                    	 var repulse = '<a href="javascript:void(0);" onclick="repulse('+subIndex+');" class="easyui-linkbutton" style="margin-left:5px">拒绝</a>';
                    	 var dicker = '<a href="javascript:void(0);" onclick="dicker('+subIndex+');" class="easyui-linkbutton" style="margin-left:5px">还价</a>';
                    	 var ignore = '<a href="javascript:void(0);" onclick="ignore('+subIndex+');" class="easyui-linkbutton" style="margin-left:5px">忽略</a>';
                    	 result = accept+repulse+dicker+ignore;
 						return result;
 					}
                    },
                 ]],
                 onResize:function(){
                     $('#bestOffersTable').datagrid('fixDetailRowHeight',index);
                 },
                 onLoadSuccess:function(){
                     setTimeout(function(){
                         $('#bestOffersTable').datagrid('fixDetailRowHeight',index);
                     },0);
                 }
             });
        	 
        	 var params = {};
        	 if(conditions==null){
        		 conditions =  parent.ocsPublication.getConditions();
        	 }
        	 params["itemId"] = itemId;
        	 params["conditions"]=conditions;
        	 
        	 $(subDatagriId).datagrid({

        			url:GLOBAL.domain+'/BestOffers/selectEBayBestOffersByItemId',
        			queryParams:{param:params}
        			
        		});
             $('#bestOffersTable').datagrid('fixDetailRowHeight',index);
         },
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
     });*/
	
	});
function synchronousBestOffer(){
	var row = $("#bestOffersTable").datagrid('getSelected');
	var itemiId = row.item_id;
	var siteName = row.country_name;
	var sellerId=row.siteid;
	var data={itemId:itemiId,siteName:siteName,sellerId:sellerId};
	mainAjax('/BestOffers/synchronousBestOffer',data,'正在更新议价信息中...','get',null);
	$('#bestOffersTable').datagrid('reload');

}

function bestOffers(){
	var jsonArray = $("#bestOffersfm").serializeArray();
	var jsonString  = jsonArrayToObjec(jsonArray);
	 var data =  JSON.stringify(jsonString);
	//var jsonObject = JSON.parse(jsonString);
	mainAjax('/BestOffers/bestOfferMain',data,'正在更新议价信息中...','post',function(result){
		$('#bestOffersTable').datagrid('reload');
		 $.messager.alert({
	         title:'消息',
	         msg:result.description,
	         width:'300px',
	         height:'240px'
                	      	 
        });
		
	});
	 $('#bestOffersDialog').dialog('close');
	
}
function accept(index){
	$('#bestOffersfm').form('reset');
	$('#bestOffersTable').datagrid('selectRow',index);
	var row = $("#bestOffersTable").datagrid('getSelected');
		$("#noneQuantity").val(row.quantity);
		$("#noneItemID").val(row.item_id);
		$("#noneBestId").val(row.best_offer_id);
		$("#siteCurrency").html(row.currency);
		$("#noneCurrency").val(row.currency);
		$("#seller_id").val(row.siteid);
		$("#operateTypeId").val("Accept");
		$('#counterDiv').panel('close'); 
		$('#bestOffersDialog').dialog('open').dialog('center').dialog('setTitle', '接受');
	
	
}
function repulse(index){
	$('#bestOffersfm').form('reset');
	$('#bestOffersTable').datagrid('selectRow',index);
	var row = $("#bestOffersTable").datagrid('getSelected');
		$("#noneQuantity").val(row.quantity);
		$("#noneItemID").val(row.item_id);
		$("#noneBestId").val(row.best_offer_id);
		$("#siteCurrency").html(row.currency);
		$("#noneCurrency").val(row.currency);
		$("#seller_id").val(row.siteid);
		$("#operateTypeId").val("Decline");
		$('#counterDiv').panel('close'); 
		$('#bestOffersDialog').dialog('open').dialog('center').dialog('setTitle', '拒绝');
}
function dicker(index){
	$('#bestOffersfm').form('reset');
	$('#bestOffersTable').datagrid('selectRow',index);
	var row = $("#bestOffersTable").datagrid('getSelected');
		$("#noneQuantity").val(row.quantity);
		$("#noneItemID").val(row.item_id);
		$("#noneBestId").val(row.best_offer_id);
		$("#siteCurrency").html(row.currency);
		$("#noneCurrency").val(row.currency);
		$("#operateTypeId").val("Counter");
		$("#seller_id").val(row.siteid);
		$('#counterDiv').panel('open');
		$('#bestOffersDialog').dialog('open').dialog('center').dialog('setTitle', '还价');
	
}
function ignore(index){
	var row = $("#bestOffersTable").datagrid('getSelected');
	var best_offer_id = row.best_offer_id;
	if(row!=null){
		$.messager.confirm('删除', '你确定要忽略改数据?', function(r) {
			if (r) {
				var data = {best_offer_id:best_offer_id};
				mainAjax('/BestOffers/deleteBestOfferByBestId',data,'正在忽略中...','get',function(result){
					$('#bestOffersTable').datagrid('reload')
				});
			}
		});
	}else{
		$.messager.alert('提示','请先选择一条数据后操作!','warning');
		return;
	}
	
}
function jsonArrayToObjec(array){
	   var o = {};
	   $.each(array, function() {    
	       if (o[this.name]) {    
	           if (!o[this.name].push) {    
	               o[this.name] = [o[this.name]];    
	           }    
	           o[this.name].push(this.value || '');    
	       } else {    
	           o[this.name] = this.value || '';    
	       }    
	   });    
	   return o;    
}