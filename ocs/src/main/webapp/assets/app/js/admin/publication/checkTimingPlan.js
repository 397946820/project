var curParam = undefined;
var conditions=null;
var pre_id=null;
$(function(){
	var params = searchParam();
	$('#checkTimingPlanTable').datagrid({

		url:GLOBAL.domain+'/TimingPlan/checkList',
		queryParams:{param:params}
		
	});
	
	$(".con-button li").each(function(){
		var siteId = $(this).find("input").val();
		var count = countPub(siteId);
		$(this).find("span").html("("+count+")");
	});
	 $(".con-button li").click(function(){
			
			$(this).css( "background",'#ccc').siblings().css( "background",'#fff');
			$(this).find("input").prop("checked","checked");
			$(this).siblings().find("input").prop("checked",false);
			var params = searchParam();
			if(curParam){
				if(curParam['siteId'] == params['siteId']){
					return;
				}
			}
			curParam = params;
			$("#checkTimingPlanTable").datagrid("load",{
				param :params
			});
		});
	$("#cheListSearch").on('click',function(){
		var params = searchParam();
		$("#checkTimingPlanTable").datagrid({
			queryParams:{
				param :params
			}
		});
		$(".con-button li").each(function(){
			var siteId = $(this).find("input").val();
			var count = countPub(siteId);
			$(this).find("span").html("("+count+")");
		});
	})
})
function doDblClickRow(rowIndex, rowData){
	 $.messager.alert({
      	 title:'消息',
      	 msg:rowData.publication_info,
      	 width:'600px',
      	 height:'500px'
      	 
       });
	
}
var getItemId = function(value,row,index){
	return '<a href='+GLOBAL.domain + '/publication/toEdit?id='+row.new_template_id+'&conditions=all target="_blank">'+row.itemId+'</a>';
  
}
var getIsSuccess = function(value,row,index){
	if(value==0){
		return '<span style="color: green;">成功</span>';
	}else{
		return '<span style="color: red;">失败</span>';
	}
}

function countPub(siteId){
	if(ocs.stringIsNull(siteId))siteId = 'all';
	var params = searchParam();
	params["siteId"] = siteId;
	 params = JSON.stringify(params);
	var count=null;
	mainSynAjax('/TimingPlan/countLinePub/site',params,null,"post",function(result) {
		
		count = result.data;
	   });
	return count;
}
var getNameSku = function(value,row,index){
	return '<div style="color: green;">'+row.template_name+'</div><div style="color: green;">'+row.sku+'</div><a href="'+row.ebayProductURL+'" target="_blank"><div style="color: green;">'+row.productTitle+'</div></a>';
}
var getLocalSiteDate = function(value,row,index){
	
	return '<span>'+row.startDate+'</span><br/>'+'<span>'+row.endDate+'</span>';
}
var getTypeImage = function(value,row,index) {
	return "<img style='width:16px;' src='"+GLOBAL.domain+ "/assets/app/images/publication/"+value+".png'/>";
}
var getEbayImage = function(value,row,index){
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
var getSiteImage = function(value,row,index){
	if((value == "" || value == null) && value!= 0){
		value=22;
	}
    return "<img src='"+ GLOBAL.domain + "/assets/app/images/publication/"+value+".png'/>";
}
var conditions=null;
function searchParam(){
	var formData = $("#checkTimingPlanCondition").serializeArray();
	var param = {};
	if(conditions==null){
		conditions = parent.ocsPublication.getConditions();
	}
	param['day']=conditions;
	$.each(formData,function(){
		param[this.name] = this.value;
	});
	return param;
}