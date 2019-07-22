var getStatrDate = function(value,row,index){
	if(value==null){
		value='';
	}
	return "<span style='color:green'>"+value+"</span>";
}
var getEndDate=function(value,row,index){
	if(value==null){
		value='';
	}
	return "<span style='color:red'>"+value+"</span>";
}

var getTitleAndItemNum =function(value,row,index){
	var title = row.productTitle;
	if(title == null){
		title = '';
	}
	var item = row.itemId;
	if(item == null){
		item = "";
	}
	if(row.ebayProductURL){
		return "<span style='color:blue'><a href='"+row.ebayProductURL+"' target='_blank'>"+title+"</a></span><br/><span style='color:green'>"+item+"</span>";
	}else{
		return "<span style='color:blue'>"+title+"</span><br/><span style='color:green'>"+item+"</span>";
	}
	
}
var getSoldAvailable = function(value,row,index){
	var sold = row.quantity_sold;
	var reslut = null;
	var available = row.quantity_available
	if(sold==null){
		sold=0;
	}
	if(available==null){
		available=0;
		return '<span style="color:red">'+sold+'/'+available+'</span>';
	}else{
		return sold+'/'+available+'</span>';
	}
}
var getEbayImage = function(value,row,index){
	if(value){
		var imgUrl = undefined;
		var index1 = value.indexOf(",");
		if(index1 > 0){
			imgUrl = value.substring(0,index1)
		}else{
			imgUrl = value;
		}
		return '<img src="'+imgUrl+'" style="width:80px;height:80px;"/>';
	}
	return "";
}

var getPushStatus = function(value,row,index){
	if(row.itemId){
		return "<span style='color:blue'>已发布</span>";
	}else{
		return "<span style='color:red'>未发布</span>";
	}
}
var getNameOrSku = function(value,row,index){
	
	var sku = "<span style='color:green'>"+row.sku+"</span>";
	var	name = "<span style='color:blue'>"+row.name+"</span><br/>";
	
	var content = name+sku;
			
    return content;
}
var getSiteImage = function(value,row,index){
	if((value == "" || value == null) && value!= 0){
		value=22;
	}
    return "<div class='icon3 country_size num_"+value+"'></div>";
}
var getTypeImage = function(value,row,index) {
	return "<div class='icon3 "+value+"'></div>";
}
var getItemBaseInfo = function(value,row,index) {
	var itemId = '';
   if(row.itemId!=null){
	   itemId=row.itemId;
   }
	
	var siteId = "<img style=' margin:0 auto;' src='"+ GLOBAL.domain + "/assets/app/images/publication/"+row.siteId+".png'/>";
	var type = "<img style='width: 16px;padding: 0 10px;' src='"+GLOBAL.domain+ "/assets/app/images/publication/"+row.publicationType+".png'/>";
	var currency =null;
	if(row.siteId==0){
		currency='USD';
	}else if(row.siteId==3){
		currency='GBP';
	}else if(row.siteId==77){
		currency='EUR';
	}
	var user = row.ebayAccount;
	return '<div>'+itemId+'</div>'+'<div>'+siteId+type+currency+'</div>'+'<div>'+user+'</div>';
}
var editPublication = function() {
	var $row = $('#publicationDatagrid').datagrid('getSelected');
    if (!$row) {
        $.messager.alert("信息", "请选中一行编辑");
        return;
    }
    window.open(GLOBAL.domain+'/publication/toEdit?id='+$row.id+'&conditions='+conditions,'_blank');
}
function savePlanPublication(){
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
			 $('#publicationDatagrid').datagrid('reload');
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
function serializeToJson(data){
        data=data.replace(/&/g,"\",\"");  
        data=data.replace(/=/g,"\":\"");  
        data="{\""+data+"\"}";  
        return data;  
}
var sequencePublication = function(){
	
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
var checkCostPublication = function(){
	
	var row = $('#publicationDatagrid').datagrid('getSelected');
	if (!row) {
        $.messager.alert("信息", "请选择添检查费用的产品！");
        return;
    }else{
    	
    			var id = row.id;
    			var data={id:id};
    			mainAjax('/Item/verifyAddItem',data,'正在检查费用中...',"get",null);
       }
}

var savePublication = function() {

	
	var row = $('#publicationDatagrid').datagrid('getSelected');
    if (!row) {
        $.messager.alert("信息", "请选择发布的产品！");
        return;
    }else{

        var itemId = row.itemId;
    	if(itemId==null){
		    	var id = row.id;
		    	var data={id:id};
		    	mainAjax('/Item/publishedItem',data,'正在发布中......',"get",function(result) {
				    $.messager.alert({
				      	 title:'消息',
				      	 msg:result.description,
				      	 width:'600px',
				      	 height:'500px'
				      	 
				       });

				    $('#publicationDatagrid').datagrid('reload');
				   });
		    	
		    	
	     }else{
	 	   $.messager.alert("信息", "产品已在线，不能发布该产品！");	
	    }
    }
}
var getCorrelation = function(value,row,index){
	if(value==null){
		return '<a href="javascript:void(0);" onclick="correlationPu('+row.id+')">0</a>';
	}else{ 
		return '<a href="javascript:void(0);" onclick="correlationPu('+row.id+')">'+ value + '</a>';
	}
}
var getPreCount = function(value,row,index){
	if(value==null){
		return '<a href="javascript:void(0);" onclick="prePu('+row.id+')">0</a>';
	}else{ 
		return '<a href="javascript:void(0);" onclick="prePu('+row.id+')">'+ value + '</a>';
	}
}

var correlationPu = function(id){
	window.parent.setCorrelationId(id);
	window.parent.addParentArticleTab("关联产品 ","/publication/lineIndex/main");
}

var  prePu = function(id){
	window.parent.setPreId(id);
	window.parent.addParentArticleTab("关联预刊登","/TimingPlan/show/all");
}


var addTimingPublication = function(){
	
	var row = $('#publicationDatagrid').datagrid('getSelected');
    if (!row) {
        $.messager.alert("信息", "请选择定时发布的产品！");
        return;
    }else{
    	var itemId = row.itemId;
    	if(itemId==null){
    		$('#timingForm').form('reset');
    		var siteId = row.siteId;
    		$("#siteId").combobox("setValue",siteId);
    		$("#productNameId").textbox("setValue",row.name);
    		$("#templateId").val(row.id);
    		var siteName;
    		if(siteId==0){
    			siteName="美国";
    		}else if(siteId==3){
    			siteName="英国";
    		}else if(siteId==77){
    			siteName="德国";
    		}else if(siteId==15){
    			siteName="澳大利亚";
    		}else if(siteId==101){
    			siteName="美国汽车";
    		}
    		var siteDate = "";
    		mainSynAjax('/Item/getSiteDateById',{siteId:row.siteId},null,"get",function(result) {
    			siteDate = result.data;
 		   });
    		$("#siteName").html(siteName);
    		/* $('#site_date').datetimebox('setValue', siteDate); */
    		$("#siteDate").html(siteDate);
    	    $('#timingDialog').dialog('open').dialog('center').dialog('setTitle','定时刊登');
    	    
    	}else{
    		 $.messager.alert("信息", "产品已在线，不能定时发布该产品！");
    	}
    }
}
function getSiteDate(siteId){
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
  
function timingPublication(){
	
	var timingDate = $('#timingDate').datetimebox('getValue')
	/* var siteDate = $('#site_date').datetimebox('getValue'); */
	var siteDate = $("#siteDate").text();
	
	if(timingDate==null||timingDate==""){
		$.messager.alert("信息", "定时发布的时间不能为空！");
	}else if(timingDate<siteDate){
		$.messager.alert("信息", "定时发布的时间不能小于站点的当前时间！");
	}else{
		var id = $("#templateId").val();
		var siteName = $("#siteName").html();
		var siteId = null;
		 if(siteName="美国"){
			 siteId=0;
		 }else if(siteName="英国"){
			 siteId=3;
		 }else if(siteName="德国"){
			 siteId=77;
		 }else if(siteName="澳大利亚"){
			 siteId=15;
		 }else if(siteName="美国汽车"){
			 siteId=101;
		 }
		 var data ={id:id,siteId:siteId,timingDate:timingDate};
		 mainAjax('/Item/timingAddItem',data,'正在定时发布中......',"get",function(result) {
			    $.messager.alert({
			      	 title:'消息',
			      	 msg:result.description,
			      	 width:'600px',
			      	 height:'500px'
			      	 
			       });
			    $('#timingDialog').dialog('close');
			    $('#publicationDatagrid').datagrid('reload');
			     
			   });
		
		
	}
	
	
}
var conditions=null;
function searchParam(){
	var formData = $("#publicationListCondition").serializeArray();
	var param = {};
	if(conditions==null){
		conditions = parent.ocsPublication.getConditions();
	}
	param['conditions']=conditions;
	$.each(formData,function(){
		param[this.name] = this.value;
	});
	return param;
}
function countPub(siteId){
	if(ocs.stringIsNull(siteId))siteId = 'all';
	var params = searchParam();
	params["siteId"] = siteId;
	params = JSON.stringify(params);
	var count=null;
	
	mainSynAjax('/publication/countPub/site',params,null,"post",function(result) {
		
		count = result.data;
	   });
	return count;
}
function copyItemPublication(){
	
	var row = $('#publicationDatagrid').datagrid('getSelected');
    if (!row) {
        $.messager.alert("信息", "请选择复制的范本！");
        return;
    }else{
    	//var id = row.id;
    	//var data = {id:id};
    	$('#copyItemId').val(row.id)
    	$('#copyNameId').textbox('setValue',row.name+"_copy");
    	$('#copyItemDialog').dialog('open').dialog('center').dialog('setTitle','复制');
    	
    	
    	
    	
    }
}
function copySubmit(){
	
	var id = $('#copyItemId').val()
	var name = $('#copyNameId').textbox('getValue');
	var data = {id:id,name:name};
	
	mainAjax('/publication/copyItemPublication',data,'正在复制范本中......',"get",function(result) {
    $.messager.alert({
      	 title:'消息',
      	 msg:result.description,
      	 width:'300px',
      	 height:'250px'
      	 
       });
    $('#publicationDatagrid').datagrid('reload');
    $('#copyItemDialog').dialog('close');
   });
}


function timingPlanPublication(){
	 $('#sequenceDatagrid').datagrid('loadData', {total:0,rows:[]}); 
	var row = $('#publicationDatagrid').datagrid('getSelected');
    if (!row) {
        $.messager.alert("信息", "请选择定时计划发布的产品！");
        return;
    }else{
    	var itemId = row.itemId;
    	if(itemId==null){
    		$("#sequenceDatagrid").hide();
    		$('#timingPlanForm').form('reset');
    		$("#timingSiteId").val(row.siteId);
    		$("#timingTemplateId").val(row.id);
    		$("#templateNameId").val(row.name);
    		
    		var siteDate = "";
    		mainSynAjax('/Item/getSiteDateById',{siteId:row.siteId},null,"get",function(result) {
    			siteDate = result.data;
    		   });
    		/* $('#site_date').datetimebox('setValue', siteDate); */
    		$("#timingSiteDate").html(siteDate);
    		 $('#timingPlanDialog').dialog('open').dialog('center').dialog('setTitle','定时刊登计划');
    	    
    	}else{
    		 $.messager.alert("信息", "产品已在线，不能定时发布该产品！");
    	}
    }
}
function editItemVariations(){
	
	 var variationArray = $("#editVariationForm").serializeArray();
	 var params={};
	 $.each(variationArray,function(){
		 params[this.name] = this.value;
	});
	 var string = JSON.stringify(params);
	 mainAjax('/Item/reviseInventoryStatus',string,'正在修改库存中......',"post",function(result) {
		    $.messager.alert({
		      	 title:'消息',
		      	 msg:result.description,
		      	 width:'600px',
		      	 height:'500px'
		      	 
		       });

			 $('#editVariationsDialog').dialog('close');
		    $('#publicationDatagrid').datagrid('reload');
		   });
	
}
function batchUpdates(){
	
	// var rows=$('#fastEditorDatagrid').datagrid('getChanges');
	accept();
	var rows = $('#fastEditorDatagrid').datagrid("getRows");
	var jsonArray = JSON.stringify(rows);
    mainAjax('/publication/batchUpdates',jsonArray,'正在编辑中......',"post",function(result) {
	    $.messager.alert({
	      	 title:'消息',
	      	 msg:result.description,
	      	 width:'600px',
	      	 height:'500px'
	      	 
	       });
	    $('#fastEditorDialog').dialog('close');
		$('#publicationDatagrid').datagrid('reload');
	   });
   
	
}

function fastEditor(){
	
	$('#fastEditorDialog').dialog('open').dialog('center').dialog('setTitle','快速编辑');
	var editorData = $('#publicationDatagrid').datagrid('getSelections');
	
	$("#fastEditorDatagrid").datagrid({
		rownumbers: true,
		data:editorData,
		singleSelect: true,
		nowrap:false,
		columns: [
            [
            	{field: 'id', title: 'id',hidden:true},
            	{field: 'publicationType', title: 'publicationType',hidden:true},
            	{field: 'ebayImages',width:'13%', title: '图片',formatter:getEbayImage},
            	{field: 'itemId',width:'15%', title: '物品号',formatter:getItemBaseInfo},
            	{field: 'sku',width:'15%', title: 'SKU',editor:{type:'validatebox',options:{required:true,validType:['length[5,80]']}}},
            	{field: 'productTitle',width:'15%', title: '标题',editor:{type:'validatebox',options:{required:true,validType:['length[5,80]']}}},
            	{field: 'productSubtitle',width:'15%', title: '子标题',editor:{type:'validatebox',options:{required:false,validType:['length[0,80]']}}},
            	{field: 'price',width:'7%', title: '起始价',editor:{type:'numberbox',options:{required:true,precision:2}}},
            	{field: 'reserverPrice',width:'7%', title: '保留价',editor:{type:'numberbox',options:{precision:2}}},
            	{field: 'buyoutPrice',width:'7%', title: '一口价',editor:{type:'numberbox',options:{precision:2}}},
            	{field: 'quantity_available',width:'8%', title: '可售数量',editor:{type:'numberbox'}}
               
            ]
        ]
		
	});

}

var editIndex = undefined;

function endEditing() {
	if(editIndex == undefined) {
		return true
	}
	if($('#fastEditorDatagrid').datagrid('validateRow', editIndex)) {
		
		$('#fastEditorDatagrid').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickRow(index,row) {
   
	if(editIndex != index) {
		if(endEditing()) {
			$('#fastEditorDatagrid').datagrid('selectRow', index)
				.datagrid('beginEdit', index);
			// 得到单元格对象,index指哪一行,field跟定义列的那个一样
			var cellEdit = $('#fastEditorDatagrid').datagrid('getEditor', {
				index: index,
				field: 'buyoutPrice'
			});
			var cellEdit2 = $('#fastEditorDatagrid').datagrid('getEditor', {
				index: index,
				field: 'reserverPrice'
			});
			
			var $buyoutPrice = cellEdit.target; // 得到文本框对象
			var $reserverPrice = cellEdit2.target; 
			/*
			 * var p=$buyoutPrice.closest('td[field]');//编辑器容器 p.html('')
			 */
			// $input.val('aaa'); // 设值
			if(row.publicationType =='FixedPriceItem' || row.publicationType =='FixedPriceItem1'){

				$buyoutPrice.numberspinner('destroy');
				$reserverPrice.numberspinner('destroy');
			}
			// editIndex = index;
		} else {
			$('#fastEditorDatagrid').datagrid('selectRow', editIndex);
		}
	}
}

function append() {
	if(endEditing()) {
		$('#fastEditorDatagrid').datagrid('appendRow', {
			status: 'P'
		});
		editIndex = $('#fastEditorDatagrid').datagrid('getRows').length - 1;
		$('#fastEditorDatagrid').datagrid('selectRow', editIndex)
			.datagrid('beginEdit', editIndex);
	}
}

function removek() {
	if(editIndex == undefined) {
		return
	}
	$('#fastEditorDatagrid').datagrid('cancelEdit', editIndex)
		.datagrid('deleteRow', editIndex);
	editIndex = undefined;
}

function accept() {
	if(endEditing()) {
		$('#fastEditorDatagrid').datagrid('acceptChanges');
	}
}

function reject() {
	$('#fastEditorDatagrid').datagrid('rejectChanges');
	editIndex = undefined;
}

function getChanges() {
	var rows = $('#fastEditorDatagrid').datagrid('getChanges');
	alert(rows.length + ' rows are changed!');
}
var curParam = undefined;
function againSearch(){
	$('#publicationDatagrid').datagrid('clearSelections');
	var params = searchParam();
	$("#publicationDatagrid").datagrid({
		queryParams:{
			param :params
		}
	});
	$("#publicationDatagrid").datagrid("reload");
	$(".con-button li").each(function(){
		var siteId = $(this).find("input").val();
		var count = countPub(siteId);
		$(this).find("span").html("("+count+")");
	});
}
function initLabelOp(){
	$("#labelState").combobox({
		valueField:"id",
		textField:"labelName",
		url:"/ocs/publication/getItemLabelByState/N",
		onChange:function(record){
			var id = record;
			var idArray="";
			if(id!=1){
				var rows= $('#publicationDatagrid').datagrid('getSelections');
				    if (rows.length<=0) {
				    	
				    }else{
				           
				        
				        for(var i=0; i<rows.length; i++){
				        	idArray=idArray+rows[i].id+",";
				        }
				        idArray= idArray.substr(0,idArray.length-1);
				        mainSynAjax('/publication/relevancyItemLable',{ids:idArray,id:id},'正在关联中。。。。。',"get",function(result) {
						         $.messager.alert({
						         title:'消息',
						         msg:result.description,
						         width:'300px',
						         height:'240px'
				                	      	 
				               });
					         
				        });
				    }
			    againSearch();
			}else{
				
				labelEditor.InitLebelTable();
				$('#labelEditorDialog').dialog('open').dialog('center').dialog('setTitle','标签');
				
			}
			
		}
	});

}



$(function(){
	
	$.ajaxSetup({  
	    contentType: "application/x-www-form-urlencoded; charset=utf-8"  
	}); 
	$("#ebayAccount").combobox({
		valueField :"account",
		textField :"account",
		url : "/ocs/publication/getAccounts"
	});
	
	var params = searchParam();
	$('#publicationDatagrid').datagrid({

		url:GLOBAL.domain+'/publication/list',
		queryParams:{param:params}
		
	});
	initLabelOp();
	$('#publicationTimingPlanLinkbutton').on('click',timingPlanPublication);
	$('#publicationEditLinkbutton').on('click',editPublication);
	$('#publicationRemoveLinkbutton').on('click',publicationMain.remove);
	$('#publicationSaveLinkbutton').on('click',savePublication);
	$('#publicationCheckCostLinkbutton').on('click',checkCostPublication);
	$('#publicationTimingLinkbutton').on('click',addTimingPublication);
	$('#publicationCopyLinkbutton').on('click',copyItemPublication);
	
	$(".con-button li").each(function(){
		var siteId = $(this).find("input").val();
		var count = countPub(siteId);
		$(this).find("span").html("("+count+")");
	});
	
	$("#publicationDatagrid").datagrid({});
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
		$("#publicationDatagrid").datagrid("load",{
			param :params
		});
	})
	$("#pubListSearch").on('click',function(){
		againSearch();
		
	});
	
	
	$("#pubListReset").on('click',function(){
		$("#publicationListCondition").form("clear");
	});
	
	
})
var publicationMain={};
(function(publicationMain,$){
	
	publicationMain.cancelTimingPlan = function(){
		
		var rows= $('#publicationDatagrid').datagrid('getSelections');
	    if (rows.length<=0) {
	        $.messager.alert("信息", "请选择取消定时计划的范本！");
	        return;
	    }else{
	    	 $.messager.confirm('信息','你确定要取消定时计划?',function(r){
	                if (r){
	                	var id="";
	                	for(var i=0; i<rows.length; i++){
	                		id=id+rows[i].id+",";
	                	}
	                	id = id.substr(0,id.length-1);
	                	mainAjax('/publication/cancelTimingPlan',{id:id},'正在取消范本的定时计划中......',"get",function(result) {
	                	    $.messager.alert({
	                	      	 title:'消息',
	                	      	 msg:result.description,
	                	      	 width:'300px',
	                	      	 height:'240px'
	                	      	 
	                	       });
	                	    $('#publicationDatagrid').datagrid('clearSelections');
	                		$('#publicationDatagrid').datagrid('reload');
	                	   });
	                }
	    	 });
	      }
	}
	publicationMain.searchLineCorre = function(){
		
		var params = correParam();
		$('#searchCorrelationDatagrid').datagrid({

			url:GLOBAL.domain+'/publication/lineList',
			queryParams:{param:params}
			
		});
		
	}
	var getNameOrSku = function(value,row,index){
		
		var name = null;
		
		if(row.tNmae==null){
			name = "<span style='color:blue'>"+row.name+"</span><br/>"
		}else{
			name= '<a href="'+ GLOBAL.domain + '/publication/toEdit?id='+row.correlation_id+'&conditions=main" target="_blank">'+"<span style='color:blue'>"+row.tNmae+"</span></a><br/>";
		}
		var content = name;
	    return content;
	}
	function correParam(){
		var formData = $("#searchCorreCondition").serializeArray();
		var param = {};
		param['conditions']='line';
		param['correlation_id']=null;
		param['productTitle']=null;
		$.each(formData,function(){
			param[this.name] = this.value;
		});
		return param;
	};
	publicationMain.correlationLine = function(){
		
		var rows= $('#publicationDatagrid').datagrid('getSelections');
	    if (rows.length!=1) {
	        $.messager.alert("信息", "请选择一个关联的范本！");
	        return;
	    }else{
	    	var row = $('#publicationDatagrid').datagrid('getSelected');
	    	$('#coEbayAccount').textbox('setValue',row.ebayAccount);
	    	$('#coId').val(row.id);
	    	//$('#coEbayAccount').textbox('disable');
	    	//$("#coPublicationType").combobox({disabled: true}); 
	    	$("#coPublicationType").combobox('setValue','\''+row.publicationType+'\''); 
	    	//$("#coSiteId").combobox({disabled: true}); 
	    	$("#coSiteId").combobox('setValue',row.siteId); 
	    	$('#coName').textbox('setValue',row.name); 
	    	$('#coName').textbox('disable');
	    	$('#correlationDialog').dialog('open').dialog('center').dialog('setTitle','关联');
	    	$("#searchCorrelationDatagrid").datagrid({
				rownumbers: true,
				singleSelect:true,
				nowrap:false,
				pagination:true,
				columns: [
		            [
		            	{field: 'id', title: 'id',hidden:true},
		            	 {field: 'ebayProductURL', title: 'ebayProductURL',hidden:true},
		            	{field: 'ebayImages',width:'9%', title: '图片',formatter:getEbayImage},
		            	{field: 'itemId',width:'13%',title: '物品号'},
		            	{field: 'tName',width:'25%', title: '范本名称',formatter:getNameOrSku},
		            	{field: 'sku', title: 'SKU',width:'15%'},
		            	{field: 'productTitle',width:'25%', title: '物品标题'},
		            	{field: 'operation',width:'5%', title: '操作',formatter:function(value,row,index){
		            		return '<a onclick="publicationMain.correlationEditor('+row.id+')" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true">关联刊登</a>';
		            	}}
		           ]
			   ]
			});
	    	
	    	$("#correlationDatagrid").datagrid({
				rownumbers: true,
				singleSelect: true,
				nowrap:false,
				pagination: true,
				columns: [
		            [
		            	{field: 'id', title: 'id',hidden:true},
		            	{field: 'ebayProductURL', title: 'ebayProductURL',hidden:true},
		            	{field: 'ebayImages',width:'9%', title: '图片',formatter:getEbayImage},
		            	{field: 'itemId',width:'13%',title: '物品号'},
		            	{field: 'tName',width:'22%', title: '范本名称',formatter:getNameOrSku},
		            	{field: 'sku', title: 'SKU',width:'15%'},
		            	{field: 'productTitle',width:'25%', title: '物品标题'},
		            	{field: 'publicationDateEnd',width:'7%', title: '结束时间',formatter:getEndDate},
		            	{field: 'state',width:'5%', title: '状态',formatter:getState},
		            	{field: 'operation',width:'5%', title: '操作',formatter:function(value,row,index){
		            		return '<a onclick="publicationMain.cancelEditor('+row.id+')" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true">取消关联</a>';
		            	}}
		            	
		           ]
			   ]
			});
	    	
	    	var params = allCorreParam();
	    	params['correlation_id']=row.id;
			$('#correlationDatagrid').datagrid({

				url:GLOBAL.domain+'/publication/lineList',
				queryParams:{param:params}
				
			});
	    	
	    
	    }
	}
	publicationMain.correlationEditor = function(cId){
		var id = $('#coId').val();
		mainAjax('/publication/cancelCorrelation',{id:cId,correlation_id:id},'正在关联中......',"get",function(result) {
    	    $.messager.alert({
    	      	 title:'消息',
    	      	 msg:result.description,
    	      	 width:'300px',
    	      	 height:'240px'
    	      	 
    	       });
    		$('#correlationDatagrid').datagrid('reload');
    		$('#publicationDatagrid').datagrid('reload');
    	   });
	}
	publicationMain.cancelEditor = function(id){
		mainAjax('/publication/cancelCorrelation',{id:id},'正在取消中......',"get",function(result) {
    	    $.messager.alert({
    	      	 title:'消息',
    	      	 msg:result.description,
    	      	 width:'300px',
    	      	 height:'240px'
    	      	 
    	       });
    		$('#correlationDatagrid').datagrid('reload');
    		$('#publicationDatagrid').datagrid('reload');
    	   });
	}
	var getState=function(value,row,index){
		
		var endDate = row.publicationDateEnd;
		var sysDate = getStringByDateformat(new Date(),'yyyy-MM-dd HH:mm:ss');
		if(endDate>sysDate){
			return '<span style="color:green">在线</span>';
		}else{
			return '<span style="color:red">下线</span>';
		}
		
	}
	function allCorreParam(){
		var param = {};
		param['conditions']='main';
    	param['templateName']=null;
    	param['sku']=null;
    	param['itemId']=null;
    	param['productTitle']=null;
    	param['publicationType']=null;
    	param['ebayAccount']=null;
    	return param;
    	
	}
	var getEndDate=function(value,row,index){
		if(value==null){
			value='';
		}
		return "<span style='color:red'>"+value+"</span>";
	}
	publicationMain.remove = function(){
		var rows= $('#publicationDatagrid').datagrid('getSelections');
	    if (rows.length<=0) {
	        $.messager.alert("信息", "请选择删除的范本！");
	        return;
	    }else{
	    	 $.messager.confirm('删除','你确定要删除数据?',function(r){
	                if (r){
	                	var id="";
	                	for(var i=0; i<rows.length; i++){
	                		id=id+rows[i].id+",";
	                	}
	                	id = id.substr(0,id.length-1);
	                	mainAjax('/publication/remove',{id:id},'正在删除中......',"get",function(result) {
	                	    $.messager.alert({
	                	      	 title:'消息',
	                	      	 msg:result.description,
	                	      	 width:'300px',
	                	      	 height:'240px'
	                	      	 
	                	       });
	                		$('#publicationDatagrid').datagrid('reload');
	                	   });
	                }
	    	 });
	    	
	     }
	}
	
})(publicationMain,jQuery);
//批量编辑物品属性
var attribute={};
(function(attribute,$){
	attribute.InitEdit = function(){
		
	}
})(attribute,jQuery);
//标签 js
var labelEditor = {};
(function(labelEditor,$){
	function accept() {
		if(endEditing()) {
			$('#lebelEditorTable').datagrid('acceptChanges');
		}
	}

	function reject() {
		$('#lebelEditorTable').datagrid('rejectChanges');
		editIndex = undefined;
	}

	function getChanges() {
		var rows = $('#lebelEditorTable').datagrid('getChanges');
		alert(rows.length + ' rows are changed!');
	}
	
	labelEditor.save = function(index){
		accept();
		$('#lebelEditorTable').datagrid('selectRow',index);
		var row = $("#lebelEditorTable").datagrid('getSelected');
		row["labelState"]="N";
		var json = JSON.stringify(row);
		mainSynAjax('/publication/labelSave',json,'正在保存中......',"post",function(result) {
	    	    
		         $('#lebelEditorTable').datagrid('reload');
		         $.messager.alert({
	 		      	 title:'消息',
	 		      	 msg:result.description,
	 		      	 width:'200px',
	 		      	 height:'150px'
	 		      	 
	 		       });
		         
		   });
	    initLabelOp();
	}
	labelEditor.InitLebelTable =function(){
		$("#lebelEditorTable").datagrid(
				{
					url : '/ocs/publication/getItemLabelPage',
					queryParams : {
						param : {
							labelState:'N'
						}
					},
					columns : [ [
							{    field:'labelState',
								 title:"",
								 hidden:true,
								 editor:{type:'textbox',options:{}},
								 formatter:function(value, row, index) {
									 return "N";
								 }
							},
							{
								field : 'labelName',
								title : '名称',
								editor:{type:'textbox',options:{}},
								width : 240
							},
							{
								field : 'labelNote',
								title : '备注',
								editor:{type:'textbox',options:{}},
								width : 320
							},
							{
								field : 'amountPaid',
								title : '数量',
								align : 'center',
								width : 100
							},
							{
								field : 'operation',
								formatter:function(value, row, index) {
									 var dicker = '<a href="javascript:void(0);" onclick="labelEditor.save('+index+');" class="easyui-linkbutton" style="margin-left:5px">保存</a>';
									 var repulse = '<a href="javascript:void(0);" onclick="labelEditor.remove('+index+');" class="easyui-linkbutton" style="margin-left:5px">删除</a>';
									 result = dicker+repulse;
									return result;
									},
								title : '动作',
								width : 200
							}] ],
					idField : 'id',
					rownumbers: true,
					singleSelect: true,
			     	fit: true,
					nowrap:false,
					pagination: true,
					extEditing:true,
					singleEditing:true,
					striped:true,
					autoEditing:true,
					pageSize: 30,
					toolbar: [{ text: '增加', iconCls: 'icon-add', handler: function () { labelEditor.append(); } }],
					onClickCell:labelEditor.onClickCell,
					onAfterEdit:labelEditor.onAfterEdit, 
			        onBeforeEdit:labelEditor.onBeforeEdit
					
				});
	}
	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#lebelEditorTable').datagrid('validateRow', editIndex)){
			$('#lebelEditorTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}

	labelEditor.onAfterEdit = function(index,row,changes){
	}
	labelEditor.onBeforeEdit =function(index,row){
	}
	labelEditor.onClickCell = function(index, field,value){

		if (endEditing()){
			   editIndex = index;
				var va = $('#lebelEditorTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		}
	}
	
	
	function rowEndEditing() {
		if(editIndex == undefined) {
			return true
		}
		if($('#lebelEditorTable').datagrid('validateRow', editIndex)) {
			
			$('#lebelEditorTable').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}

	
	labelEditor.onClickRow=function(index,row) {
	   
		if(editIndex != index) {
			if(rowEndEditing()) {
				$('#lebelEditorTable').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#lebelEditorTable').datagrid('selectRow', editIndex);
			}
		}
	}

	labelEditor.append=function() {
		if(rowEndEditing()) {
			$('#lebelEditorTable').datagrid('insertRow', {
				index:0,
				row:{}
			});
			
			//$('#feedbacksTable').datagrid("beginEdit",0);
			editIndex=0;
		}
	}
	
	labelEditor.remove=function(index) {
		 $('#lebelEditorTable').datagrid('selectRow',index);
		 accept();
		 var row = $('#lebelEditorTable').datagrid('getSelected');
		 if(row!=null){
				 var r=confirm("你确定要删除该数据？");
				 if (r==true){
					 var id = row.id;
					 mainAjax('/publication/labelRemove',{id:id},null,"get",function(result) {
						 $('#lebelEditorTable').datagrid('cancelEdit', editIndex)
							.datagrid('deleteRow', editIndex);
						   editIndex = undefined;
						    $.messager.alert({
						      	 title:'消息',
						      	 msg:result.description,
						      	 width:'200px',
						      	 height:'150px'
						       });
						    initLabelOp();
							 if(editIndex == undefined) {
								return
							 }
						   }); 
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
	labelEditor.onEndEdit=function(index, row){
	    var ed = $(this).datagrid('getEditor', {
	        index: index,
	        field: 'site'
	    });
	    row.country = $(ed.target).combobox('getText');
	}
	
	
	
})(labelEditor,jQuery);
// 批量更新范本js
var aLotModify = {};

(function(aLotModify,$){

	var curCondition = undefined;
	var curEditor = undefined;
	var comboboxData = {};
	var updateKey = [];
	var siteId = 0;
	var hasCheckedKey = {};
	var curTempIds = [];
	
	function getBuyerRequir(){
		var buyerRequired = {
				ALLOW_ALLBUYER:"",
				NO_PAYPAL:"",
				OUT_SHIP_COUNTRY:"",
				BUYER_REQ_1:"",
				BUYER_REQ_2:"",
				BUYER_REQ_3:"",
				BUYER_REQ_4:"",
				BUYER_REQ_4_1:""
			};

		if($("input[name=rbtnBuyerRequirementSpecified]:checked").val()== 0){
			buyerRequired.ALLOW_ALLBUYER = 'true';
		}else{
			buyerRequired.ALLOW_ALLBUYER = 'false';
			buyerRequired.NO_PAYPAL = $('input[name="isHavePaypalAccount"]').is(':checked')?'true':'false';
			buyerRequired.OUT_SHIP_COUNTRY =$('input[name="isOffscale"]').is(':checked')?'true':'false';
			if($('input[name="chkMaxUnpaidItemStrikesInfoSpecified"]').is(':checked')){
				buyerRequired.BUYER_REQ_1 = $("select[name='ddlMaxUnpaidItemStrikesInfoCount']").val()+"|"+$("select[name='ddlMaxUnpaidItemStrikesInfoPeriod']").val();
			}else{
				buyerRequired.BUYER_REQ_1 = '';
			}
			if($('input[name="chkMaxBuyerPolicyViolationsSpecified"]').is(':checked')){
				buyerRequired.BUYER_REQ_2 = $("#ddlMaxBuyerPolicyViolationsCount").val()+"|"+$("#ddlMaxBuyerPolicyViolationsPeriod").val();
			}else{
				buyerRequired.BUYER_REQ_2 = '';
			}
			if($('input[name="chkMinFeedbackScoreSpecified"]').is(':checked')){
				buyerRequired.BUYER_REQ_3= $("#ddlMinFeedbackScore").val();
			}else{
				buyerRequired.BUYER_REQ_3 = '';
			}
			if($('input[name="chkMaxItemRequirementsMaxItemCountSpecified"]').is(':checked')){
				buyerRequired.BUYER_REQ_4 = $("#ddlMaxItemRequirementsMaxItemCount").val();
				if(($('input[name="chkMaxItemRequirementsMinFeedbackScoreSpecified"]').is(':checked'))){
					buyerRequired.BUYER_REQ_4_1 = $("#ddlMaxItemRequirementsMinFeedbackScore").val();
				}else{
					buyerRequired.BUYER_REQ_4_1 = '';
				}
			}else{
				buyerRequired.BUYER_REQ_4 = '';
			}
		}
		
		return buyerRequired;
	}
	
	function initComboboxData(){
		comboboxData["publicationDays"]=[  {
			value:'Days_1',
			displayName:'1'
			},
			{
				value:'Days_3',
				displayName:'3'
			},
			{
				value:'Days_5',
				displayName:'5'
			},
			{
				value:'Days_7',
				displayName:'7'
			},
			{
				value:'Days_10',
				displayName:'10'
			},
			{
				value:'Days_30',
				displayName:'30'
			},
			{
				value:'GTC',
				displayName:'GTC'
			}];
		comboboxData["payPalAccount"]=[{
										value:'lightingorient@gmail.com',
										displayName:'lightingorient@gmail.com'
									  },{
										value:'payment@lightingever.com',
										displayName:'payment@lightingever.com'
									  }];
		comboboxData["returnPolicyHander"]=[{value:'Buyer',displayName:'Buyer'},{value:'Seller',displayName:'Seller'}];
		comboboxData["returnPolicy"] = [];
		comboboxData["returnPolicyDay"] = [];
		ocs.ajax({
			url:"/publication/getReturnPolicyData/"+siteId,
			type:"POST",
			data:"",
			success:function(returnData){
				if(returnData){
					var option = [];
					var retunType = returnData.returns_accepted;
					if(retunType){
						retunType = eval('('+retunType+')');
						$.each(retunType,function(){
							option.push({value:this.returnsAcceptedOption,displayName:this.description});
						});
					}
					comboboxData["returnPolicy"] = option;
					option = [];
					var returnDays = returnData.returns_within;
					if(returnDays){
						returnDays = eval('('+returnDays+')');
						$.each(returnDays,function(){
							option.push({value:this.returnsWithinOption,displayName:this.description});
						});
					}
					comboboxData["returnPolicyDay"] = option
				}	
			}
		});
		comboboxData["0_trans"] = [];
		comboboxData["1_trans"] = [];
		//运输方式数据初始化
		ocs.ajax({
			async:false,
			url:"/publication/getTransTypeData/"+siteId,
			type:"POST",
			data:"",
			success:function(returnData){
				var dom = [];
				var cu = [];
				var firstSelect = true;
				if(returnData){
					$.each(returnData,function(){
						if(this.dataType == 1){
							cu.push({value:this.value,displayName:this.displayName});
						}else{
							dom.push({value:this.value,displayName:this.displayName});
						}
					});
				}
				comboboxData["0_trans"] = cu;
				comboboxData["1_trans"] = dom;
			}
		});	
		comboboxData["templateBanner"] = [];
		ocs.ajax({
			async:false,
			url:"/publication/getTemplateBanner/"+siteId,
			type:"POST",
			data:"",
			success:function(returnData){
				if(returnData){
					comboboxData["templateBanner"] = returnData
				}
			}
		});	
	}
	
	function getSetKeyValue(){
		var data = {};
		$.each(updateKey,function(){
			if(this == '_buyer_require'){
				var buyData = getBuyerRequir();
				if(buyData){
					for(var key in buyData){
						data[key] =  buyData[key];
					}
				}
				return true;
			}
			var clazz = $("#vi_"+this).attr("class");
			var value = "";
			if(clazz){
				if(clazz.indexOf("easyui-textbox") > -1){
					value = $("#vi_"+this).textbox("getValue");
				}else{
					value = $("#vi_"+this).combobox("getValue");
				}
			}
			data[this] = value;
			
		});
		return data;
	}
	
	aLotModify.modifyOk = function(){
		$.messager.confirm('确认', '是否保存当前页的数据?', function(r){
			if (r){
				$("#aLotModifyListGrid").datagrid("endEdit", curEditor);
				curEditor = undefined;
				var changeData = $("#aLotModifyListGrid").datagrid("getChanges","updated");
				var saveData = {};
				var list = [];
				if(changeData&&changeData.length>0){
					$.each(changeData,function(){
						var one = {};
						var curRowData = this;
						one["TEMPLATE_ID"] = curRowData["TEMPLATE_ID"];
						$.each(updateKey,function(){
							if(this == "_buyer_require"){
								one["ALLOW_ALLBUYER"] = curRowData["ALLOW_ALLBUYER"];
								one["NO_PAYPAL"] = curRowData["NO_PAYPAL"];
								one["OUT_SHIP_COUNTRY"] = curRowData["OUT_SHIP_COUNTRY"];
								one["BUYER_REQ_1"] = curRowData["BUYER_REQ_1"];
								one["BUYER_REQ_2"] = curRowData["BUYER_REQ_2"];
								one["BUYER_REQ_3"] = curRowData["BUYER_REQ_3"];
								one["BUYER_REQ_4"] = curRowData["BUYER_REQ_4"];
								one["BUYER_REQ_4_1"] = curRowData["BUYER_REQ_4_1"];
							}else{
								one[this]  = curRowData[this];
							}
							
						});
						list.push(one);
					});
					saveData["data"]= list;
					// 保存数据
					ocs.ajax({
						url:'/publication/aLotModifySave',
						async:true,
						data:saveData,
					    beforeSend: function () {
			                   $.messager.progress({
			                       title: '请稍后',
			                       msg: '正在保存中...'
			                   });
			               },
			           complete: function () {
			               $.messager.progress('close');
			           },
						type: "POST",
						success: function(result) {
							var flag=result.data;
							if(flag){
								$("#aLotModifyListGrid").datagrid('acceptChanges');
								$("#aLotModifyListGrid").datagrid('reload');
								$('#aLotModifyList').dialog('close');
								$('#aLotModifyCondition').dialog('close');
								aLotModify.clear();
								$("#publicationDatagrid").datagrid('reload');
								$.messager.alert("信息","保存成功！");	
							}else{
								$.messager.alert("信息","保存失败,请重试！");	
							}
						},
						error: function(jqXHR, textStatus, errorThrown) {
							// $.messager.alert("信息", "服务器发生错误！");
						}
				   });
				}
			}
		});
	}
	aLotModify.clear = function(){
		$("#aLotModifyColumnSet").find("input").each(function(){
			$(this).attr("checked",false);
		});
	}
	aLotModify.conditionNext = function(){
		var keys = [];
		var columns = [];
		updateKey = [];
		keys.push("TEMPLATE_NAME");
		keys.push("PRODUCT_TITLE");
		keys.push("SKU");
		keys.push("PUBLICATION_TYPE");
		columns.push({field:'ck',checkbox:true});
		columns.push({field:'TEMPLATE_ID',title:'模板ID',width:60});
		/*
		 * columns.push({field:'EBAY_ACCOUNT',title:'eBay账号/站点',width:80,formatter:function(value,row,index){
		 * return value + '<br/>'+row.SITE_ID; }});
		 */
		columns.push({field:'SITE_ID',hidden:true});
		// columns.push({field:'TEMPLATE_NAME',title:'模板名称',width:100});
		columns.push({field:'PRODUCT_TITLE',title:'产品标题',width:100});
		columns.push({field:'SKU',title:'SKU',width:80});
		columns.push({field:'PUBLICATION_TYPE',title:'刊登类型',width:60});
		$("#aLotModifyAttrSetTable").html("");
		$("#aLotModifyColumnSet").find("input").each(function(){
			
			if($(this).is(":checked")){
				var valueName = $(this).attr("name");
				var viewnName = $(this).parent('li').text();
				keys.push(valueName)
				var clazz = $(this).attr("class");
				if(clazz.indexOf("is_text_box")> -1){
					if(clazz.indexOf("is_text_box1")> -1){
						$("#aLotModifyAttrSetTable").append('<tr id="vs_'+valueName+'"><td style="width: 100px;">'+viewnName+':</td><td><input style="width: 150px;" id="vi_'+valueName+'" type="text" name="'+valueName+'" class="easyui-textbox"  data-options="multiline:true" /> </td></tr>');
					}else{
						$("#aLotModifyAttrSetTable").append('<tr id="vs_'+valueName+'"><td style="width: 100px;">'+viewnName+':</td><td><input style="width: 150px;" id="vi_'+valueName+'" type="text" name="'+valueName+'" class="easyui-textbox" /> </td></tr>');
					}
					$.parser.parse("#vs_"+valueName);
					columns.push({field:valueName,title:viewnName,width:60,editor:'text'});
				}else if(clazz.indexOf("is_combo_box")> -1){
					var url = $(this).attr("url");
					var valueField = $(this).attr("valueField");
					var textField = $(this).attr("textField");
					
					if(url){
						$("#aLotModifyAttrSetTable").append('<tr id="vs_'+valueName+'"><td style="width: 100px;">'+viewnName+':</td><td><input id="vi_'+valueName+'" style="width: 150px;" type="text" name="'+valueName+'" class="easyui-combobox" data-options="valueField:\''+valueField+'\',textField:\''+textField+'\',url:\''+url+'\'"/> </td></tr>');
						columns.push({field:valueName,title:viewnName,width:60,editor:{  
		                    type: 'combobox',  
		                    options: {  
		                       url: url,  
		                       valueField: valueField,  
		                       textField: textField,  
		                       editable: false,  
		                       panelHeight:70
		                    } 
						}});
					}else{
						var dataKey = $(this).attr("dataKey");
						var comData = comboboxData[dataKey];
						$("#aLotModifyAttrSetTable").append('<tr id="vs_'+valueName+'"><td style="width: 100px;">'+viewnName+':</td><td><input id="vi_'+valueName+'" style="width: 150px;" type="text" name="'+valueName+'" class="easyui-combobox" data-options="valueField:\''+valueField+'\',textField:\''+textField+'\'"/> </td></tr>');
						$("#vi_"+valueName).combobox({
							valueField: valueField,  
		                    textField: textField, 
		                    data:comData
						});
						columns.push({field:valueName,title:viewnName,width:60,editor:{  
		                    type: 'combobox',  
		                    options: {  
		                       data: comData,  
		                       valueField: valueField,  
		                       textField: textField,  
		                       editable: false,  
		                       panelHeight:70
		                    } 
						}});
					}
					$.parser.parse("#vs_"+valueName);
				}else if(clazz.indexOf("is_html")> -1){
					$("#aLotModifyAttrSetTable").append('<tr id="vs_'+valueName+'"><td style="width: 100px;">'+viewnName+':</td><td>'+$("#aLotModifyBuyerRequire").html()+'</td></tr>');
					columns.push({field:"NO_PAYPAL",hidden:true});
					columns.push({field:"OUT_SHIP_COUNTRY",hidden:true});
					columns.push({field:"BUYER_REQ_1",hidden:true});
					columns.push({field:"BUYER_REQ_2",hidden:true});
					columns.push({field:"BUYER_REQ_3",hidden:true});
					columns.push({field:"BUYER_REQ_4",hidden:true});
					columns.push({field:"BUYER_REQ_4_1",hidden:true});
					columns.push({field:"ALLOW_ALLBUYER",title:viewnName,width:60,editor:'text'});
				}else{
					columns.push({field:valueName,title:viewnName,width:60,editor:'text'});
				}
				updateKey.push(valueName);
			}
		});
		var conditons = {};
		
		$.each(keys,function(){
			conditons[this] = "";
		});

		
		//TODO 应用选择数据
		var checkedData = $("#publicationDatagrid").datagrid("getChecked");
		var ids = [];
		$.each(checkedData,function(){
			ids.push(this.id);
		});
		conditons["ids"] = ids.join(",");
		conditons["tableFlag"] = "unline";
		curCondition = conditons;
		var finalColumns = [];
		finalColumns.push(columns);
		$("#aLotModifyListGrid").datagrid({
			width:1300,
			url:'/ocs/publication/aLotModifyList',
			queryParams : {
				param : conditons
			},
		    columns:finalColumns,
			//idField : 'TEMPLATE_ID',
			singleSelect : false,
			rownumbers : true,
			fitColumns : true,
			border : true,
			nowrap:false,
			//pagination : true,
			fit : true,
			//pageSize : 50,
			toolbar: [/*{
				iconCls: 'icon-search',
				text:'应用所选',
				handler: function(){
					var checkRows = $("#aLotModifyListGrid").datagrid("getChecked");
					if(checkRows&&checkRows.length>0){
						var data = getSetKeyValue();
						$.each(checkRows,function(){
							var rowIndex = $("#aLotModifyListGrid").datagrid("getRowIndex",this);
							$("#aLotModifyListGrid").datagrid('updateRow',{
								index: rowIndex,
								row:data
							})
						});
					}else{
						$.messager.alert('提示','请至少选择一条应用的数据!','warning');
					}
				}
			},'-',*/{
				iconCls: 'icon-search',
				text:'应用',
				handler: function(){
					var checkRows = $("#aLotModifyListGrid").datagrid("getRows");
					if(checkRows&&checkRows.length>0){
						var data = getSetKeyValue();
						$.each(checkRows,function(){
							var rowIndex = $("#aLotModifyListGrid").datagrid("getRowIndex",this);
							$("#aLotModifyListGrid").datagrid('updateRow',{
								index: rowIndex,
								row:data
							})
						});
					}else{
						$.messager.alert('提示','当前页没有数据!','warning');
					}
				}
			}/*,'-',{
				iconCls: 'icon-search',
				text:'应用全部',
				handler: function(){
					$.messager.confirm('确认', '是否应用到所有匹配到的数据?', function(r){
						if (r){
							var saveData = {};
							saveData["data"]= getSetKeyValue();
							saveData["condition"]= curCondition;
							ocs.ajax({
								url:'/publication/aLotModifyAllSave',
								async:true,
								data:saveData,
							    beforeSend: function () {
					                   $.messager.progress({
					                       title: '请稍后',
					                       msg: '正在保存中...'
					                   });
					               },
					           complete: function () {
					               $.messager.progress('close');
					           },
								type: "POST",
								success: function(result) {
									var flag=result.data;
									if(flag){
										$("#aLotModifyListGrid").datagrid('acceptChanges');
										$("#aLotModifyListGrid").datagrid('reload');
										$.messager.alert("信息","保存成功！");	
									}else{
										$.messager.alert("信息","保存失败,请重试！");	
									}
								},
								error: function(jqXHR, textStatus, errorThrown) {
									// $.messager.alert("信息", "服务器发生错误！");
								}
						   });
						}
					});
				}
			},'-',{
				iconCls: 'icon-save',
				text:'更新',
				handler: function(){
					$.messager.confirm('确认', '是否更新产品信息?', function(r){
						if (r){
							$("#aLotModifyListGrid").datagrid("endEdit", curEditor);
							curEditor = undefined;
							
							var changeData = $("#aLotModifyListGrid").datagrid("getChanges","updated");
							
							var list = [];
							if(changeData&&changeData.length>0){
								$.each(changeData,function(){
									var one = {};
									var curRowData = this;
									one["TEMPLATE_ID"] = curRowData["TEMPLATE_ID"];
									$.each(updateKey,function(){
										if(this == "_buyer_require"){
											one["ALLOW_ALLBUYER"] = curRowData["ALLOW_ALLBUYER"];
											one["NO_PAYPAL"] = curRowData["NO_PAYPAL"];
											one["OUT_SHIP_COUNTRY"] = curRowData["OUT_SHIP_COUNTRY"];
											one["BUYER_REQ_1"] = curRowData["BUYER_REQ_1"];
											one["BUYER_REQ_2"] = curRowData["BUYER_REQ_2"];
											one["BUYER_REQ_3"] = curRowData["BUYER_REQ_3"];
											one["BUYER_REQ_4"] = curRowData["BUYER_REQ_4"];
											one["BUYER_REQ_4_1"] = curRowData["BUYER_REQ_4_1"];
										}else{
											one[this]  = curRowData[this];
										}
										
									});
									list.push(one);
								});
								saveData["data"]= list;
								// 保存数据
								ocs.ajax({
									url:'/publication/aLotModifySaveAndUpdate',
									async:true,
									data:saveData,
								    beforeSend: function () {
						                   $.messager.progress({
						                       title: '请稍后',
						                       msg: '正在更新线上产品中...'
						                   });
						               },
						           complete: function () {
						               $.messager.progress('close');
						           },
									type: "POST",
									success: function(result) {
										 $.messager.alert({
									      	 title:'消息',
									      	 msg:result.description,
									      	 width:'600px',
									      	 height:'500px'
									      	 
									       });
											$("#aLotModifyListGrid").datagrid('acceptChanges');
											$("#aLotModifyListGrid").datagrid('reload');
											
											 
									},
									error: function(jqXHR, textStatus, errorThrown) {
										// $.messager.alert("信息", "服务器发生错误！");
									}
							   });
							}
						}
					});
				}
			},'-',{
				iconCls: 'icon-save',
				text:'更新到范本',
				handler: function(){
					$.messager.confirm('确认', '是否更新到范本?', function(r){
						if (r){
							$("#aLotModifyListGrid").datagrid("endEdit", curEditor);
							curEditor = undefined;
							var changeData = $("#aLotModifyListGrid").datagrid("getChanges","updated");
							var saveData = {};
							var list = [];
							if(changeData&&changeData.length>0){
								$.each(changeData,function(){
									var one = {};
									var curRowData = this;
									one["TEMPLATE_ID"] = curRowData["TEMPLATE_ID"];
									$.each(updateKey,function(){
										if(this == "_buyer_require"){
											one["ALLOW_ALLBUYER"] = curRowData["ALLOW_ALLBUYER"];
											one["NO_PAYPAL"] = curRowData["NO_PAYPAL"];
											one["OUT_SHIP_COUNTRY"] = curRowData["OUT_SHIP_COUNTRY"];
											one["BUYER_REQ_1"] = curRowData["BUYER_REQ_1"];
											one["BUYER_REQ_2"] = curRowData["BUYER_REQ_2"];
											one["BUYER_REQ_3"] = curRowData["BUYER_REQ_3"];
											one["BUYER_REQ_4"] = curRowData["BUYER_REQ_4"];
											one["BUYER_REQ_4_1"] = curRowData["BUYER_REQ_4_1"];
										}else{
											one[this]  = curRowData[this];
										}
										
									});
									list.push(one);
								});
								saveData["data"]= list;
								// 保存数据
								ocs.ajax({
									url:'/publication/aLotModifySaveToCorrelation',
									async:true,
									data:saveData,
								    beforeSend: function () {
						                   $.messager.progress({
						                       title: '请稍后',
						                       msg: '正在更新到范本中...'
						                   });
						               },
						           complete: function () {
						               $.messager.progress('close');
						           },
									type: "POST",
									success: function(result) {
										 $.messager.alert({
									      	 title:'消息',
									      	 msg:result.description,
									      	 width:'600px',
									      	 height:'500px'
									      	 
									       });
											$("#aLotModifyListGrid").datagrid('acceptChanges');
											$("#aLotModifyListGrid").datagrid('reload');
											
											 
									},
									error: function(jqXHR, textStatus, errorThrown) {
										// $.messager.alert("信息", "服务器发生错误！");
									}
							   });
							}
						}
					});
				}
			}*/],
			onDblClickRow : function(rowIndex, rowData){
				if(curEditor != undefined){
					if(curEditor != rowIndex){
						$("#aLotModifyListGrid").datagrid("endEdit", curEditor);
						
						$("#aLotModifyListGrid").datagrid("beginEdit", rowIndex);
						curEditor = rowIndex;
					}
				}else{
					$("#aLotModifyListGrid").datagrid("beginEdit", rowIndex);
					curEditor = rowIndex;
				}
			}
		});
		$("#aLotModifyList").dialog("open");
	}
	
	$(".is_text_box").on("click",function(){
		var valueName = $(this).attr("name");
		if($(this).is(":checked")){
			var viewnName = $(this).parent('li').text();
			$("#aLotModifyAttrTable").append('<tr id="'+valueName+'"><td>'+viewnName+':</td><td><input id="id_'+valueName+'" type="text" name="'+valueName+'" class="easyui-textbox" /> </td></tr>');
			$.parser.parse("#"+valueName);
		}else{
			$("#"+valueName).remove();
		}
	});
	$(".is_combo_box").on("click",function(){
		var valueName = $(this).attr("name");
		if($(this).is(":checked")){
			var viewnName = $(this).parent('li').text();
			var url = $(this).attr("url");
			var valueField = $(this).attr("valueField");
			var textField = $(this).attr("textField");
			if(url){
				$("#aLotModifyAttrTable").append('<tr id="'+valueName+'"><td>'+viewnName+':</td><td><input id="id_'+valueName+'" type="text" name="'+valueName+'" class="easyui-combobox" data-options="valueField:\''+valueField+'\',textField:\''+textField+'\',url:\''+url+'\'"/> </td></tr>');
				$.parser.parse("#"+valueName);
			}
		}else{
			$("#"+valueName).remove();
		}
	});
	$(".is_text_box1").on("click",function(){
		
		var valueName = $(this).attr("name");
		if($(this).is(":checked")){
			var viewnName = $(this).parent('li').text();
			$("#aLotModifyAttrTable").append('<tr id="'+valueName+'"><td>'+viewnName+':</td><td><input id="id_'+valueName+'" type="text" name="'+valueName+'" class="easyui-textbox" data-options="multiline:true" /> </td></tr>');
			$.parser.parse("#"+valueName);
		}else{
			$("#"+valueName).remove();
		}
	});
	$("#aLotModify").click(function(){
		$("#aLotModifyCondition").dialog("open");
		
		/*$("#aLotEbayAccount").combobox({
			valueField :"account",
			textField :"account",
			url : "/ocs/publication/getAccounts"
		});  
		$('#aLotEbaySite').combobox({
			valueField :"value",
			textField :"displayName",
			url : "/ocs/publication/getSiteList",
			onChange : function(newValue,oldValue){
				if(newValue != oldValue){
					siteId = newValue;
					initComboboxData();
				}
			}
		});
		$("#aLotEbaySite").combobox("setValue",siteId);*/
		
		initComboboxData();
	});
	////////////////批量修改新的方式//////////////////////////////////////////
	//批量修改按钮点击，打开选择修改位置窗口
	$("#aLotModifyNew").click(function(){
		curTempIds = [];
		var rows = $("#publicationDatagrid").datagrid('getChecked')
		if(rows&&rows.length>0){
			//curTempIds
			$.each(rows,function(){
				curTempIds.push(this.id);
			});
			$("#aLotModifyConditionNew").dialog("open");
		}else{
			$.messager.alert("信息","请至少选择一条数据哦！");	
		}
	});
	//选择后点击下一步
	aLotModify.conditionNextNew = function(){
		var modfifyKey = [];
		var viewFlag = [];
		$("#aLotModifyColumnSetNew").find("input").each(function(){
			if($(this).is(":checked")){
				var name = $(this).attr("name");
				if(name == "BUYER_REQ" || name == "RETUEN_POLOCY" || name == "TRANKING_INFO"||name=="ACCEPT_BUYER_COUNTER"){
					var link = $(this).attr("link");
					var keys = link.split(",");
					for(var i=0;i<keys.length;i++){
						modfifyKey.push(keys[i]);
					}
				}else{
					modfifyKey.push(name);
				}
				var view =  $(this).attr("group");
				if(viewFlag.toString().indexOf(view)== -1){
					viewFlag.push(view);
				}
			}
		});
		hasCheckedKey["keys"]=modfifyKey;
		hasCheckedKey["viewFlag"]=viewFlag;
		$("#aLotModifypage").html('<iframe src="/ocs/publication/toALotModify" frameborder="0"  id="external-frame" style="width:100%;height:100%" onload="aLotModify.modifyPageLoadSuccess();"></iframe>');
		$.messager.progress({
            title: '请稍后',
            msg: '正在玩命加载中...'
        });
	}
	aLotModify.modifyPageLoadSuccess = function(){
		document.getElementById('external-frame').contentWindow.loadSuccess(hasCheckedKey); 
		$("#aLotModifyWindow").dialog("open");
		$.messager.progress('close');
	}
	aLotModify.saveUpdateData = function(){
		$.messager.confirm('确认', '是否保存当前页的数据?', function(r){
			if (r){
				var data = document.getElementById('external-frame').contentWindow.getData();
				data["ids"] = curTempIds.join(",");
				data["type"] = "template";
				// 保存数据
				ocs.ajax({
					url:'/publication/aLotModifySaveNew',
					async:true,
					data:data,
				    beforeSend: function () {
	                   $.messager.progress({
	                       title: '请稍后',
	                       msg: '正在保存中...一会就好！'
	                   });
		            },
		            complete: function () {
		               $.messager.progress('close');
		            },
					type: "POST",
					success: function(result) {
						var flag=result.data;
						if(flag){
							$('#aLotModifyConditionNew').dialog('close');
							$("#publicationDatagrid").datagrid('reload');
							$("#aLotModifyWindow").dialog("close");
							aLotModify.clearNew();
							$.messager.alert("信息","保存成功！");	
						}else{
							$.messager.alert("信息","保存失败,请重试！");	
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						// $.messager.alert("信息", "服务器发生错误！");
					}
			   });
			}
		});
	}
	aLotModify.clearNew = function(){
		$("#aLotModifyColumnSetNew").find("input").each(function(){
			$(this).attr("checked",false);
		});
	}
	/////////////////////////////////////////////////////////////////////
})(aLotModify,jQuery);