var curParam = undefined;
var conditions=null;
var pre_id=null;



$(function(){
	/*$('#timingPlanLinkbutton').linkbutton('disable');
	if( window.parent.getPreId()!=null){
		$('#timingPlanLinkbutton').linkbutton('enable');
	}*/
	
	$('#timingPlanLinkbutton').on('click',timingPlanPublication);
	$('#singleTimingPlan').on('click',singleTimingPlan);
	var params = searchParam();
	$('#preTimingPlanTable').datagrid({

		url:GLOBAL.domain+'/TimingPlan/list',
		queryParams:{param:params}
		
	});
	$(".con-button li").each(function(){
		var siteId = $(this).find("input").val();
		var count = countPub(siteId);
		$(this).find("span").html("("+count+")");
	});
	$("#preListReset").on('click',function(){
		$("#preTimingPlanCondition").form("clear");
	});
	$("#preListSearch").on('click',function(){
		var params = searchParam();
		$("#preTimingPlanTable").datagrid({
			queryParams:{
				param :params
			}
		});
		$("#preTimingPlanTable").datagrid("reload");
		$(".con-button li").each(function(){
			var siteId = $(this).find("input").val();
			var count = countPub(siteId);
			$(this).find("span").html("("+count+")");
		});
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
		$("#preTimingPlanTable").datagrid("load",{
			param :params
		});
	})
	
});


function countPub(siteId){
	
	if(ocs.stringIsNull(siteId))siteId = 'all';
	var params = searchParam();
	 params["siteId"] = siteId;
	 params = JSON.stringify(params);
	var count=null;
	mainSynAjax('/TimingPlan/countPub/site',params,null,"post",function(result) {
			
			count = result.data;
		   });
	return count;
}
var getNameSku = function(value,row,index){
	return '<a href='+GLOBAL.domain + '/publication/toEdit?id='+row.template_id+'&conditions=main target="_blank"><div style="color: green;">'+row.template_name+'</div></a><div style="color: green;">'+row.sku+'</div><div style="color: green;">'+row.productTitle+'</div>';
}
var getLocalSiteDate = function(value,row,index){
	
	return '<span>'+row.startDate+'</span><br/>'+'<span>'+row.endDate+'</span>';
}
var getTypeImage = function(value,row,index) {
	return "<img style='width:16px;heigh:16px' src='"+GLOBAL.domain+ "/assets/app/images/publication/"+value+".png'/>";
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
var singleTimingPlan =function(){
	timingPlanPublication();
	$("#mumberOfDayId").numberbox('setValue',0);
	 $("#mumberOfId").numberbox('setValue',1);
	
}
var timingPlanPublication =function(){
	
	 $('#sequenceDatagrid').datagrid('loadData', {total:0,rows:[]}); 
	 $("#sequenceDatagrid").hide();
	 $('#timingPlanForm').form('reset');
	 if(pre_id==null||pre_id==''){
		var row = $('#preTimingPlanTable').datagrid('getSelected');
	    if (!row) {
	        $.messager.alert("信息", "请选择定时计划发布的产品！");
	        return;
	    }else{
	  		
	  		$("#timingSiteId").val(row.site_id);
	  		$("#timingTemplateId").val(row.template_id);
	  		$("#templateNameId").val(row.template_name);
	  		var siteDate = "";
			mainSynAjax('/Item/getSiteDateById',{siteId:row.site_id},null,"get",function(result) {
				siteDate = result.data;
			   });
			/* $('#site_date').datetimebox('setValue', siteDate); */
			 $("#timingSiteDate").html(siteDate);
			 
	    }
	}else{
		 mainSynAjax('/publication/selectPubById',{id:pre_id},null,"get",function(row) {
			   $("#timingSiteId").val(row.siteId);
	    		$("#timingTemplateId").val(row.id);
	    		$("#templateNameId").val(row.name);
		  		var siteDate = "";
				mainSynAjax('/Item/getSiteDateById',{siteId:row.siteId},null,"get",function(result) {
					siteDate = result.data;
				   });
				/* $('#site_date').datetimebox('setValue', siteDate); */
				 $("#timingSiteDate").html(siteDate);
			   });
		
	}
  
	 $('#timingPlanDialog').dialog('open').dialog('center').dialog('setTitle','定时刊登计划');
}
var getSiteImage = function(value,row,index){
	if((value == "" || value == null) && value!= 0){
		value=22;
	}
    return "<img style='width:16px;heigh:16px;' src='"+ GLOBAL.domain + "/assets/app/images/publication/"+value+".png'/>";
}

function searchParam(){
	var formData = $("#preTimingPlanCondition").serializeArray();
	var param = {};
	if(conditions==null){
		conditions = parent.ocsPublication.getConditions();
	}
	if(conditions=='main'){
		conditions='all';
	}
	
	param['pre_id']=window.parent.getPreId();
	pre_id = window.parent.getPreId();
	param['day']=conditions;
	$.each(formData,function(){
		param[this.name] = this.value;
	});
	return param;
}
function getSiteDate(siteId){
	debugger;
	var zone=null;
	if(siteId==0){
		zone=-7;
	}else if(siteId==3){
		zone=1;
	}else if(siteId==77||siteId==71||siteId==101){
		zone=2;
	}else{
		$.messager.alert("信息", "不能转换改站点的时间！");
	}
	var localDate = new Date();
	var utc = localDate.getTime()+(localDate.getTimezoneOffset()*60000);
	var siteDate = new Date(utc+(3600000*zone));
	
	return siteDate;
}
function getZone(siteId){
	var zone=0;
	if(siteId==0){
		zone=-7;
	}else if(siteId==3){
		zone=1;
	}else if(siteId==77||siteId==71||siteId==101){
		zone=2;
	}else{
		$.messager.alert("信息", "站点无效！");
	}
	return 8-zone;
	
}
function serializeToJson(data){
    data=data.replace(/&/g,"\",\"");  
    data=data.replace(/=/g,"\":\"");  
    data="{\""+data+"\"}";  
    return data;  
}
function savePlanPublication(){
	debugger;
	var timingPlanForm = $("#timingPlanForm").serialize();
	timingPlanForm = decodeURIComponent(timingPlanForm,true); 
	 var json = serializeToJson(timingPlanForm);
	 mainAjax('/publication/savePlanPublication',json,null,'post',function(result) {
		    $.messager.alert({
		      	 title:'消息',
		      	 msg:result.description,
		      	 width:'600px',
		      	 height:'500px'
		      	 
		       });

			 $('#timingPlanDialog').dialog('close');
			 $('#preTimingPlanTable').datagrid('reload');
		   });
	
}
function formatDate(date, format) {   
    if (!date) return;   
    if (!format) format = "yyyy-MM-dd";   
    switch(typeof date) {   
        case "string":   
            date = new Date(date.replace(/-/, "/"));   
            break;   
        case "number":   
            date = new Date(date);   
            break;   
    }    
    if (!date instanceof Date) return;   
    var dict = {   
        "yyyy": date.getFullYear(),   
        "M": date.getMonth() + 1,   
        "d": date.getDate(),   
        "H": date.getHours(),   
        "m": date.getMinutes(),   
        "s": date.getSeconds(),   
        "MM": ("" + (date.getMonth() + 101)).substr(1),   
        "dd": ("" + (date.getDate() + 100)).substr(1),   
        "HH": ("" + (date.getHours() + 100)).substr(1),   
        "mm": ("" + (date.getMinutes() + 100)).substr(1),   
        "ss": ("" + (date.getSeconds() + 100)).substr(1)   
    };       
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
        return dict[arguments[0]];   
    });                   
} 
var sequencePublication = function(){
	debugger;
	 var timingPlanDate = $('#timingPlanDate').datetimebox('getValue');
	 var day = $("#mumberOfDayId").numberbox('getValue');
	 var number = $("#mumberOfId").numberbox('getValue');
	 var minutes = $("#delayMinutesId").numberbox('getValue');
	 if(minutes==null||minutes==''){
		 minutes=0;
	 }
	 var siteId = $("#timingSiteId").val();
	 var templateId = $("#timingTemplateId").val();
	 var name = $("#templateNameId").val();

		if(timingPlanDate=="" || day=="" || number==""){
			$.messager.alert("信息", "必填项不能为空！");
		}else{
			
			var startDate = new Date(timingPlanDate);
			
			
		// startDate=addDate(startDate, day*number);
			var jsonDate = [];
			
			for(var i=0; i<number;i++){
				var json ={};
				var dateNumber = day*i;
				var siteDate2 = addDate(startDate, dateNumber);
				var siteSD = formatDate(siteDate2,'yyyy-MM-dd HH:mm:ss');
				json["siteDate"]=siteSD;
				 var localDate ="";
				 mainSynAjax('/Item/getLocalBySiteId',{siteDate:siteSD,siteId:siteId},null,"get",function(result) {
					
			    	  localDate = new Date(result.data);
			    	  localDate.setMinutes(parseInt(localDate.getMinutes()) + parseInt(minutes)*(i+1), localDate.getSeconds(), 0);
			    	  
					   });
				 siteDate2 = new Date(siteDate2);
				
			     
			    json["localDate"]=formatDate(localDate,'yyyy-MM-dd HH:mm:ss');
			    json["siteId"]=siteId;
			    json["templateId"]=templateId;
			    json["name"]=name;
				jsonDate.push(json);
			}
			// data= JSON.stringify(jsonDate);
		
			$("#sequenceDatagrid").datagrid({
				data:jsonDate,
				columns:[[
					{field:'templateId',title:'id',width:100,hidden:true}, 
					{field:'name',title:'范本名称',width:'40%'}, 
					{field:'siteId',title:'站点',width:'10%',formatter:getSiteImage},
					{field:'siteDate',title:'预刊登时间（站点）',width:'25%'},
					{field:'localDate',title:'预刊登时间（本地）',width:'25%'}
				]]
			});
	    }
}