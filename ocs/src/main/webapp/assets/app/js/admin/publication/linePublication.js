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
		return "<span style='color:blue'><a href='"+row.ebayProductURL+"' target='_blank'>"+title+"<a></span><br/><span style='color:green'>"+item+"</span>";
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
		var index = value.indexOf(",");
		if(index > 0){
			imgUrl = value.substring(0,index)
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
	
	var name = null;
	var sku = "<span style='color:green'>"+row.sku+"</span>";
	if(row.tNmae==null){
		name = "<span style='color:blue'>"+row.name+"</span><br/>"
	}else{
		name= '<a href="'+ GLOBAL.domain + '/publication/toEdit?id='+row.correlation_id+'&conditions=main" target="_blank">'+"<span style='color:blue'>"+row.tNmae+"</span></a><br/>";
	}
	var content = name+sku;
			
    return content;
}
var getFormateQty = function(value,row,index){
	return value;
}

var formateQty = function(value,row,index){
	if(value){
		return value;
	}else{
		return 0;
	}
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
var getdiscount = function(value,row,index){
	var oP = row.original_price;
	var result = '';
	if(oP==null||oP==""){
		result="<p>无优惠</>";
	}else{
		result = (oP-row.price)/oP;
		result = result.toFixed(2)*100;
		var startD= new Date(row.discount_start_date);  
        var endD = new Date(row.discount_end_date);
		var dateStr = '<div style="color:green">'+startD.toLocaleString()+' ~ </div>'+'<div style="color:green">'+endD.toLocaleString()+'</div>'
		result = result + dateStr;
	}
	return result;
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
				json["siteDate"]=formatDate(siteDate2,'yyyy-MM-dd HH:mm:ss');
				 siteDate2 = new Date(siteDate2);
				 var hour = getZone(siteId);
				 siteDate2.setHours (siteDate2.getHours () + hour);
			    json["localDate"]=formatDate(siteDate2,'yyyy-MM-dd HH:mm:ss');
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
	var rows = $('#publicationDatagrid').datagrid('getSelections');
	if (!row||rows.length>1) {
        $.messager.alert("信息", "请选择添检查费用的产品或者选择一个产品！");
        return;
    }else{
    	
    			var id = row.id;
    			var data={id:id};
    			mainAjax('/Item/verifyLineItem',data,'正在检查费用中...',"get",null);
       }
}
var updatePublication = function(){
	var row = $('#publicationDatagrid').datagrid('getSelected');
	if (!row) {
        $.messager.alert("信息", "请选择添更新的产品！");
        return;
    }else{
    	var itemId = row.itemId;
     	if(itemId!=null){
		    	var id = row.id;
		    	var data={id:id};
		    	mainAjax('/Item/updatePublishedItem',data,'正在更新中......',"get",function(result) {
				    $.messager.alert({
				      	 title:'消息',
				      	 msg:result.description,
				      	 width:'600px',
				      	 height:'500px'
				      	 
				       });

				    $('#publicationDatagrid').datagrid('reload');
				   });
		    	
		    	
     	}else{
 	 	   $.messager.alert("信息", "产品未发布，不能更新该产品！");	
 	    }
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
var endItemPublication = function(){
	var row = $('#publicationDatagrid').datagrid('getSelected');
	var rows = $('#publicationDatagrid').datagrid('getSelections');
	if(!row){
		$.messager.alert("信息", "请选择结束的产品！");
	}else if (rows.length>1) {
        
        return;
    }else{
    	var itemId = row.itemId;
    	if(itemId!=null){
    		$("#itemId").val(itemId);
    	    $('#endDialog').dialog('open').dialog('center').dialog('setTitle','立即结束');
    	    
    	}else{
    		 $.messager.alert("信息", "产品不在线，不能结束改产品！");
    	}
    }
	
}
function endPublication(){
	
	var itemId = $("#itemId").val();
	var endingReason = $("#endingReasonId").combobox("getValue");
	var data={};
	data["itemId"]=itemId;
	data["endingReason"]=endingReason;
	
	data = JSON.stringify(data)
	mainAjax('/Item/endItem',data,'正在结束产品中......',"post",function(result) {
	    $.messager.alert({
	      	 title:'消息',
	      	 msg:result.description,
	      	 width:'600px',
	      	 height:'500px'
	      	 
	       });

	    $('#publicationDatagrid').datagrid('reload');
	    $('#endDialog').dialog('close');
	   });
	
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
function relistItemPublication(){
	var row = $('#publicationDatagrid').datagrid('getSelected');
	if (!row) {
        $.messager.alert("信息", "请选择添重新刊登的产品！");
        return;
    }else{
    	var itemId = row.itemId;
     	if(itemId!=null){
		    	var id = row.id;
		    	var data = {id:id}
		    	mainAjax('/Item/relistPublicationItem',data,'正在重新刊登中......',"get",function(result) {
		    	    $.messager.alert({
		   	      	 title:'消息',
		   	      	 msg:result.description,
		   	      	 width:'600px',
		   	      	 height:'500px'
		   	      	 
		   	       });
		   	    $('#publicationDatagrid').datagrid('reload');
		   	   });
		    	
     	}else{
 	 	   $.messager.alert("信息", "产品未发布，不能更新该产品！");	
 	    }
    }
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
		 }
		 var data ={id:id,siteId:siteId,timingDate:timingDate};
		 mainAjax('/Item/timingAddItem',data,'正在定时发布中......',"get",null);
		 $('#publicationDatagrid').datagrid('reload');
	     $('#timingDialog').dialog('close');
		
	}
	
	
}
var conditions=null;
var correlationId =null;
function searchParam(){
	var formData = $("#publicationListCondition").serializeArray();
	var param = {};
	if(conditions==null){
		conditions = parent.ocsPublication.getConditions();
	}
	if(correlationId==null){
		correlationId=window.parent.getCorrelationId();
	}
	param['correlation_id']=correlationId;
	window.parent.setCorrelationId(null);
	param['conditions']=conditions;
	$.each(formData,function(){
		param[this.name] = this.value;
	});
	
	if(param.other=='0'||param.other=='1'){
		param.conditions='line';
	}
	return param;
}
function countPub(siteId){
	if(ocs.stringIsNull(siteId))siteId = 'all';
	var params = searchParam();
	params["siteId"] = siteId;
	params = JSON.stringify(params);
	var count=null;
	mainSynAjax('/publication/countLinePub/site',params,null,"post",function(result) {
		
		count = result.data;
	   });
	return count;
}
/**
 * 同步库存
 * @returns
 */
function synchronouVariations(){
	/*mainAjax('/Item/synchronouVariations',null,'正在同步产品库存中......',"get",function(result) {
	    $.messager.alert({
	      	 title:'消息',
	      	 msg:result.description,
	      	 width:'600px',
	      	 height:'500px'
	      	 
	       });
	    $('#publicationDatagrid').datagrid('reload');
	   });*/
	ocs.ajax({
		url:'/publication/syncStock',
		async:true,
	    beforeSend: function () {
               $.messager.progress({
                   title: '请稍后',
                   msg: '正在更新中...'
               });
           },
        complete: function () {
           $.messager.progress('close');
        },
		type: "GET",
		success: function(result) {
			if(result.data){
				$('#publicationDatagrid').datagrid('reload');
				$.messager.alert("信息","更新成功！");	
			}
		},
		error: function(jqXHR, textStatus, errorThrown) {
			//$.messager.alert("信息", "服务器发生错误！");		
		}
   });
}
var  copyItemPublication =function (){
	
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
	var correlation_id=null;
	if($("#correlationId").is(":checked")){
		correlation_id=$("#correlationId").val();
	}
	
	var data = {id:id,name:name,correlation_id:correlation_id};
	mainAjax('/publication/copyLineItemPublication',data,'正在复制范本中......',"get",function(result) {
    $.messager.alert({
      	 title:'消息',
      	 msg:result.description,
      	 width:'300px',
      	 height:'250px'
      	 
       });
    $('#copyItemDialog').dialog('close');
    $('#publicationDatagrid').datagrid('reload');
   });
}
function editVariations(){
	var row = $('#publicationDatagrid').datagrid('getSelected');
    if (!row) {
        $.messager.alert("信息", "请选择修改库存的产品！");
        return;
    }else{
    	
    	var itemId = row.itemId;
    	var sku = row.sku;
    	var variations=row.variations;
    	
    	if(itemId!=null&&itemId!=''){
    		$('#variationArray').html("")
    		$("#variationItemId").val(itemId);
    		
    		if(variations!=null){
	    		var variationsJson =  JSON.parse(variations);
	        	var vt = variationsJson.variations;
	    		for(var i in vt){
	    			var skuValueList = vt[i].SKU;
	    			var nameValue = vt[i].NameValueList;
	    			var nameValueString = "(";
	    			for(var k in nameValue){
	    				
	    				nameValueString=nameValueString+nameValue[k].Name+":"+nameValue[k].Value+","
	    				
	    			}
	    			nameValueString = nameValueString+")";
	    			var htmlObject = $('<div style="float:right"><span>'+nameValueString+'</span></div>'
	    								+'<div style="margin-bottom:10px"  >'
	    								+'<input style="display:none" id="skuVariationH'+i+'" type="easyui-validatebox" name="skuVariation'+i+'">'
	    								+'SKU: <input class="easyui-validatebox" id="skuVariation'+i+'" name="skuVariation'+i+'" data-options="disabled:true"  style="width:200px">'
	    								+'</div>'
	    								+'<div style="margin-bottom:10px"  >'
	    								+'数量: <input class="easyui-validatebox" id="quantityVariation" name="quantity'+i+'"  style="width:200px"><div class="datagrid-toolbar"></div>'
	    								+'</div>').appendTo("#variationArray");
	    		
	    			$.parser.parse(htmlObject);
	    			$("#skuVariation"+i).val(skuValueList);
	    			$("#skuVariationH"+i).val(skuValueList);
	    		}
    		}else{
    			
    			var htmlObject = $('</span></div>'
    								+'<input style="display:none" id="skuVariationH'+i+'" type="easyui-validatebox" name="skuVariation">'
    								+'<div style="margin-bottom:10px"  > SKU: <input class="easyui-validatebox" id="skuVariation" name="skuVariation" data-options="disabled:true"  style="width:300px"></div><div style="margin-bottom:10px"  >数量: <input class="easyui-validatebox" id="quantityVariation" name="quantity"  style="width:300px"></div>').appendTo("#variationArray");
    			
    			$.parser.parse(htmlObject);
    			
    			$("#skuVariation").val(sku);
    			
    			
    		}
    		$('#editVariationsDialog').dialog('open').dialog('center').dialog('setTitle','修改库存');
    	    
    	}else{
    		 $.messager.alert("信息", "只能修改刊登过的产品库存！");
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

var fastEditorO = {};
(function(fastEditorO,$){

	var editIndex = undefined;
	fastEditorO.batchUpdates=function(){
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
			$('#publicationDatagrid').datagrid('clearSelections');
		   });
	}
	fastEditorO.onClickRow=function(index,row) {
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
				
				 var p=$buyoutPrice.closest('td[field]');//编辑器容器 p.html('')
				 
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
	fastEditorO.fastEditor=function(){
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
	            	{field: 'productCount',width:'8%', title: '可售数量',editor:{type:'numberbox'}}
	               
	            ]
	        ]
			
		});
		
	}
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
})(fastEditorO,jQuery);



var curParam = undefined;
function initLabelOp(){
	$("#labelState").combobox({
		valueField:"id",
		textField:"labelName",
		url:"/ocs/publication/getItemLabelByState/Y",
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
	initLabelOp();
	labelEditor.save = function(){
		accept();
		var row = $("#lebelEditorTable").datagrid('getSelected');
		row["labelState"]="Y";
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
							labelState:'Y'
						}
					},
					columns : [ [
							{    field:'labelState',
								 title:"",
								 hidden:true,
								 editor:{type:'textbox',options:{}},
								 formatter:function(value, row, index) {
									 return "Y";
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
									 var dicker = '<a href="javascript:void(0);" onclick="labelEditor.save();" class="easyui-linkbutton" style="margin-left:5px">保存</a>';
									 var repulse = '<a href="javascript:void(0);" onclick="labelEditor.remove();" class="easyui-linkbutton" style="margin-left:5px">删除</a>';
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

	
	/*labelEditor.onClickRow=function(index,row) {
	   debugger;
		if(editIndex != index) {
			if(rowEndEditing()) {
				$('#lebelEditorTable').datagrid('selectRow', index).datagrid('beginEdit', index);
				editIndex = index;
			} else {
				$('#lebelEditorTable').datagrid('selectRow', editIndex);
			}
		}
	}*/

	labelEditor.append=function() {
		if(rowEndEditing()) {
			$('#lebelEditorTable').datagrid('insertRow', {
				index:0,
				row:{}
			});
			editIndex=0;
		}
	}
	
	labelEditor.remove=function() {
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
		$('#publicationDatagrid').datagrid('clearSelections');
		var siteId = $(this).find("input").val();
		var count = countPub(siteId);
		$(this).find("span").html("("+count+")");
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

		url:GLOBAL.domain+'/publication/lineList',
		queryParams:{param:params}
		
	});
	
	$('#publicationEditLinkbutton').on('click',editPublication);
	$('#publicationRemoveLinkbutton').on('click',linePublication.remove);
	$('#publicationSaveLinkbutton').on('click',savePublication);
	$('#publicationUpdateLinkbutton').on('click',updatePublication);
	$('#publicationCheckCostLinkbutton').on('click',checkCostPublication);
	$('#publicationEndLinkbutton').on('click',endItemPublication);
	$('#publicationRelistLinkbutton').on('click',relistItemPublication);
	$('#publicationCopyLinkbutton').on('click',copyItemPublication);
	$('#editLinkVariations').on('click',editVariations);
	
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
var linePublication = {};
(function(linePublication,$){
	
	linePublication.updateLineZero = function(){
		
		var updateZeroData = $('#publicationDatagrid').datagrid('getSelections');
		if(updateZeroData.length>0){
			 updateZeroData = JSON.stringify(updateZeroData);
			mainAjax('/publication/updateLineZero',updateZeroData,'正在隐藏中......',"post",function(result) {
			    $.messager.alert({
			      	 title:'消息',
			      	 msg:result.description,
			      	 width:'600px',
			      	 height:'500px'
			      	 
			       });
				$('#publicationDatagrid').datagrid('reload');
			   });
		}else {
			 $.messager.alert("信息", "请选择操作的数据！");
		}
		
		
	}
	linePublication.sysPuHitCount = function(){
		var params = searchParam();
		var rows=$('#publicationDatagrid').datagrid('getSelections');
		if(rows.length<=0){
			params = JSON.stringify(params);
			mainAjax('/publication/sysPuHitCount',params,'正在同步产品信息中....',"post",function(result) {
    	    $.messager.alert({
    	      	 title:'消息',
    	      	 msg:result.description,
    	      	 width:'300px',
    	      	 height:'240px'
    	      	 
    	       });
    		$('#publicationDatagrid').datagrid('reload');
    	   });
		}else{
			var jsonArray = JSON.stringify(rows);
			 mainAjax('/publication/synSelectItems',jsonArray,'正在同步产品信息中......',"post",function(result) {
				    $.messager.alert({
				      	 title:'消息',
				      	 msg:result.description,
				      	 width:'600px',
				      	 height:'500px'
				      	 
				       });
					$('#publicationDatagrid').datagrid('reload');
				   });
			
		}
	}

	linePublication.copyEditor = function(){
		$('#copyEditorDialog').dialog('open').dialog('center').dialog('setTitle','复制');
		var copyEditorData = $('#publicationDatagrid').datagrid('getSelections');
		$("#copyEditorDatagrid").datagrid({
			rownumbers: true,
			data:copyEditorData,
			singleSelect: false,
			nowrap:false,
			onSelectAll:function(rows){
				$.each(rows,function(i,row){
					accept();
					row.correlationId="关联";
					$('#copyEditorDatagrid').datagrid('refreshRow', i); 
					});
			},
			onUnselectAll:function(rows){
				$.each(rows,function(i,row){
					accept();
					row.correlationId="未关联";
					$('#copyEditorDatagrid').datagrid('refreshRow', i); 
					});
			},
			columns: [
	            [
	            	{field: 'id', title: 'id',hidden:true},
	            	{field: 'productTitle', title: 'mainTitle',hidden:true},
	            	{field: 'itemId',width:'30%', title: '物品',formatter:linePublication.getItemName},
	            	{field: 'ebayAccount',width:'13%',title: 'eBay 账户'},
	            	{field: 'siteId',width:'5%', sortable:true,title: '站点',formatter:getSiteImage},
	            	{field: 'sku', title: 'SKU',width:'15%',editor:'textbox'},
	            	{field: 'templateName',width:'30%', title: '范本名称',editor:'textbox'},
	            	{field: 'correlationId', width:'15%',editor:{type:'checkbox',options:{on:'关联',off:'未关联'}},title: '<input id="correlationBox" type="checkbox">关联'}
	           ]
		   ]
		});
	}
	function accept() {
		if(endEditing()) {
			$('#copyEditorDatagrid').datagrid('acceptChanges');
		}
	}
	
	linePublication.copyPus= function(){
		accept();
		var rows = $('#copyEditorDatagrid').datagrid("getRows");
		var jsonArray = JSON.stringify(rows);
	    mainAjax('/publication/copyLinePus',jsonArray,'正在复制中......',"post",function(result) {
		    $.messager.alert({
		      	 title:'消息',
		      	 msg:result.description,
		      	 width:'600px',
		      	 height:'500px'
		      	 
		       });
		    $('#copyEditorDialog').dialog('close');
			$('#publicationDatagrid').datagrid('reload');
		   });
	   
		
	}
	linePublication.getItemName = function(value,row,index) {
		return '<div style="color: blue;">'+row.productTitle+'</div><div>'+value+'</div>';
	}
	$.extend($.fn.datagrid.methods, {
		editCell: function(jq,param){
			return jq.each(function(){
				
				var opts = $(this).datagrid('options');
				var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
				for(var i=0; i<fields.length; i++){
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor1 = col.editor;
					if (fields[i] != param.field){
						col.editor = null;
					}
				}
				$(this).datagrid('beginEdit', param.index);
				for(var i=0; i<fields.length; i++){
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor = col.editor1;
				}
			});
		}
	});
	
	var editIndex = undefined;
	function endEditing(){
		if (editIndex == undefined){return true}
		if ($('#copyEditorDatagrid').datagrid('validateRow', editIndex)){
			$('#copyEditorDatagrid').datagrid('endEdit', editIndex);
			editIndex = undefined;
			return true;
		} else {
			return false;
		}
	}
	linePublication.onClickCell = function(index, field,value){
		if (endEditing()){
			editIndex = index;
			
				var va = $('#copyEditorDatagrid').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
				
				
			
		}
	}
	linePublication.synchronouPublication = function(){
		var params = searchParam();
		params = JSON.stringify(params);
		mainAjax('/publication/synchronouPublication',params,'正在同步产品信息中....',"post",function(result) {
    	    $.messager.alert({
    	      	 title:'消息',
    	      	 msg:result.description,
    	      	 width:'300px',
    	      	 height:'240px'
    	      	 
    	       });
    		$('#publicationDatagrid').datagrid('reload');
    	   });
	};
	linePublication.remove = function(){
		
		var rows= $('#publicationDatagrid').datagrid('getSelections');
	    if (rows.length<=0) {
	        $.messager.alert("信息", "请选择删除的刊登！");
	        return;
	    }else{
	    	 $.messager.confirm('删除','你确定要删除数据?',function(r){
	                if (r){
	                	
	                	var id="";
	                	for(var i=0; i<rows.length; i++){
	                		id=id+rows[i].id+",";
	                	}
	                	id = id.substr(0,id.length-1);
	                	mainAjax('/publication/lineRemove',{id:id},'正在删除中......',"get",function(result) {
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
})(linePublication,jQuery);
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
					saveData["type"]= "line";
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
							$("#aLotModifyListGrid").datagrid('acceptChanges');
							$("#aLotModifyListGrid").datagrid('reload');
							$('#aLotModifyList').dialog('close');
							$('#aLotModifyCondition').dialog('close');
							aLotModify.clear();
							$("#publicationDatagrid").datagrid('reload');
							var message = result.description;
							if(message){
								//展示信息
								 $.messager.alert({
							      	 title:'消息',
							      	 msg:result.description,
							      	 width:'600px',
							      	 height:'500px'
							      	 
							     });
							}else{
								$.messager.alert("信息","更新成功！");	
							}
							
							/*var flag=result.data;
							if(flag){
								$("#aLotModifyListGrid").datagrid('acceptChanges');
								$("#aLotModifyListGrid").datagrid('reload');
								$('#aLotModifyList').dialog('close');
								$('#aLotModifyCondition').dialog('close');
								aLotModify.clear();
								$.messager.alert("信息","保存成功！");	
							}else{
								$.messager.alert("信息","保存失败,请重试！");	
							}*/
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
		conditons["tableFlag"] = "line";
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
	aLotModify.saveUpdateDataToPublication = function(){
		$.messager.confirm('确认', '是否更新到相关范本?', function(r){
			if (r){
				var data = document.getElementById('external-frame').contentWindow.getData();
				data["ids"] = curTempIds.join(",");
				// 保存数据
				ocs.ajax({
					url:'/publication/aLotModifySaveToPublication',
					async:true,
					data:data,
				    beforeSend: function () {
	                   $.messager.progress({
	                       title: '请稍后',
	                       msg: '正在关联更新中...一会就好！'
	                   });
		            },
		            complete: function () {
		               $.messager.progress('close');
		            },
					type: "POST",
					success: function(result) {
						var flag=result.data;
						if(flag == "success"){
							$.messager.alert("信息","更新成功！");	
						}else{
							$.messager.alert("信息","更新失败,"+flag+"！");	
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						// $.messager.alert("信息", "服务器发生错误！");
					}
			   });
			}
		});
	}
	aLotModify.saveUpdateData = function(){
		$.messager.confirm('确认', '是否保存当前页的数据?', function(r){
			if (r){
				var data = document.getElementById('external-frame').contentWindow.getData();
				data["ids"] = curTempIds.join(",");
				data["type"] = "line";
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
							//初始化页面
							$("#aLotModifyResultListGrid").datagrid({
									columns : [ [
										{
											field : 'id',
											hidden:true
										},
										{
											field : 'productTitle',
											hidden:true
										},
										{
											field : 'sku',
											hidden:true
										},
										{
											field : 'ebayProductURL',
											hidden:true
										},
										{
											field : 'itemId',
											title : '物品号',
											align : 'center',
											width : 200,
											formatter :function(value,row,index){
												return "<span style='color:blue'><a href='"+row.ebayProductURL+"' target='_blank'>"+row.productTitle+"<a></span><br/><span style='color:green'>"+row.itemId+"</span>";
											}
										},
										{
											field : 'ebayAccount',
											title : 'ebay账户',
											align : 'center',
											width : 120
										},
										{
											field : 'siteId',
											title : '站点',
											align : 'left',
											width : 70,
											formatter:getSiteImage
										},
										
										{
											field : 'publicationType',
											title : '刊登类型',
											align : 'center',
											width : 70,
											formatter:getTypeImage
										},
										{
											field : 'tName',
											title : '范本/SKU',
											align : 'center',
											width : 150,
											formatter :function(value,row,index){
												if(value){
													return "<span style='color:black'>"+value+"</span><br/><span style='color:green'>"+row.sku+"</span>";
												}else{
													return "<span style='color:green'>"+row.sku+"</span>";
												}
											}
										},
										{
											field : 'onlineStatus',
											title : '在线更新状态',
											align : 'center',
											width : 420
										}] ],
								fit:true,
								singleSelect : true,
								//rownumbers : true,
								border : true,
								nowrap:false,
								onLoadSuccess: function () {  
								     $('#aLotModifyResultListGrid').datagrid('fixRowHeight');
								} 
							});
							//组合数据
							var newRows = [];
							var rows = $("#publicationDatagrid").datagrid('getChecked');
							$.each(rows,function(){
								var newRow = {};
								newRow["id"] = this.id;
								newRow["productTitle"] = this.productTitle;
								newRow["sku"] = this.sku;
								newRow["itemId"] = this.itemId;
								newRow["ebayProductURL"] = this.ebayProductURL;
								newRow["ebayAccount"] = this.ebayAccount;
								newRow["siteId"] = this.siteId;
								newRow["publicationType"] = this.publicationType;
								newRow["tName"] = this.tNmae;
								//newRow["ocsStatus"] = "成功";
								newRow["onlineStatus"] = flag[this.id];
								newRows.push(newRow);
							});
							$("#aLotModifyResultListGrid").datagrid("loadData",newRows);
							$("#aLotModifyResultWindow").dialog("open");
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