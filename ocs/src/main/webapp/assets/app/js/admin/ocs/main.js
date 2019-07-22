function beforeSendFunction(progressBar) {
    $.messager.progress({
        title: '请稍后',
        msg: progressBar
    });
}
var completeFunction = function () {
    $.messager.progress('close');
}
var successFunction = function(result) {
    $.messager.alert({
   	 title:'消息',
   	 msg:result.description,
   	 width:'600px',
   	 height:'500px'
   	 
    });
}
var errorFunction= function(jqXHR, textStatus, errorThrown) {
	$.messager.alert("信息", "服务器出现异常!");		
}
function mainAjax(url,data,progressBar,requestMethod,success){

    if(data==null&&progressBar==null){
    	if(success!=null){
    	$.ajax({
    		url: GLOBAL.domain+url,
    		dataType: "json",
    		
    		contentType: "application/json; charset=UTF-8",
    		type: requestMethod,
    		success: success,
    		error: errorFunction
       });
      }else{
    	  $.ajax({
      		url: GLOBAL.domain+url,
      		dataType: "json",
      		
      		contentType: "application/json; charset=UTF-8",
      		type: requestMethod,
      		success: successFunction,
      		error: errorFunction
         });
    	  
      }
    	
    }else if(data==null&&progressBar!=null){
    
    	if(success!=null){
	    	$.ajax({
	    		url: GLOBAL.domain+url,
	    		dataType: "json",
	    		contentType: "application/json; charset=UTF-8",
	    		type: requestMethod,
	    		
	    		beforeSend: beforeSendFunction(progressBar),
	            complete: completeFunction,
	    		success: success,
	    		error: errorFunction
	       });
    	}else{
    		$.ajax({
	    		url: GLOBAL.domain+url,
	    		dataType: "json",
	    		contentType: "application/json; charset=UTF-8",
	    		type: requestMethod,
	    		
	    		beforeSend: beforeSendFunction(progressBar),
	            complete: completeFunction,
	    		success: successFunction,
	    		error: errorFunction
	       });
    		
    	}
    	
    }else if(data!=null&&progressBar==null){
    	if(success!=null){
	    	$.ajax({
	    		url: GLOBAL.domain+url,
	    		dataType: "json",
	    		data:data,
	    		
	    		contentType: "application/json; charset=UTF-8",
	    		type: requestMethod,
	    		success: success,
	    		error: errorFunction
	       });
    	}else{
    		$.ajax({
	    		url: GLOBAL.domain+url,
	    		dataType: "json",
	    		data:data,
	    		
	    		contentType: "application/json; charset=UTF-8",
	    		type: requestMethod,
	    		success: successFunction,
	    		error: errorFunction
	       });
    		
    	}
    	
    }else if(data!=null&&progressBar!=null){
    	if(success!=null){
    	$.ajax({
    		url: GLOBAL.domain+url,
    		dataType: "json",
    		data:data,
    		
    		contentType: "application/json; charset=UTF-8",
    		type: requestMethod,
    		beforeSend: beforeSendFunction(progressBar),
            complete: completeFunction,
    		success: success,
    		error: errorFunction
       });
    }else{
    	$.ajax({
    		url: GLOBAL.domain+url,
    		dataType: "json",
    		data:data,
    		
    		contentType: "application/json; charset=UTF-8",
    		type: requestMethod,
    		beforeSend: beforeSendFunction(progressBar),
            complete: completeFunction,
    		success: successFunction,
    		error: errorFunction
       });
    }
    }
    
}
function mainSynAjax(url,data,progressBar,requestMethod,success){

    if(data==null&&progressBar==null){
    	if(success!=null){
    	$.ajax({
    		url: GLOBAL.domain+url,
    		dataType: "json",
    	    async:false,
    		contentType: "application/json; charset=UTF-8",
    		type: requestMethod,
    		success: success,
    		error: errorFunction
       });
      }else{
    	  $.ajax({
      		url: GLOBAL.domain+url,
      		dataType: "json",
      		async:false,
      		contentType: "application/json; charset=UTF-8",
      		type: requestMethod,
      		success: successFunction,
      		error: errorFunction
         });
    	  
      }
    	
    }else if(data==null&&progressBar!=null){
    	if(success!=null){
	    	$.ajax({
	    		url: GLOBAL.domain+url,
	    		dataType: "json",
	    		contentType: "application/json; charset=UTF-8",
	    		type: requestMethod,
	    		async:false,
	    		beforeSend: beforeSendFunction(progressBar),
	            complete: completeFunction,
	    		success: success,
	    		error: errorFunction
	       });
    	}else{
    		$.ajax({
	    		url: GLOBAL.domain+url,
	    		dataType: "json",
	    		contentType: "application/json; charset=UTF-8",
	    		type: requestMethod,
	    		async:false,
	    		beforeSend: beforeSendFunction(progressBar),
	            complete: completeFunction,
	    		success: successFunction,
	    		error: errorFunction
	       });
    		
    	}
    	
    }else if(data!=null&&progressBar==null){
    	if(success!=null){
	    	$.ajax({
	    		url: GLOBAL.domain+url,
	    		dataType: "json",
	    		data:data,
	    		async:false,
	    		contentType: "application/json; charset=UTF-8",
	    		type: requestMethod,
	    		success: success,
	    		error: errorFunction
	       });
    	}else{
    		$.ajax({
	    		url: GLOBAL.domain+url,
	    		dataType: "json",
	    		data:data,
	    		async:false,
	    		contentType: "application/json; charset=UTF-8",
	    		type: requestMethod,
	    		success: successFunction,
	    		error: errorFunction
	       });
    		
    	}
    	
    }else if(data!=null&&progressBar!=null){
    	if(success!=null){
    	$.ajax({
    		url: GLOBAL.domain+url,
    		dataType: "json",
    		data:data,
    		async:false,
    		contentType: "application/json; charset=UTF-8",
    		type: requestMethod,
    		beforeSend: beforeSendFunction(progressBar),
            complete: completeFunction,
    		success: success,
    		error: errorFunction
       });
    }else{
    	$.ajax({
    		url: GLOBAL.domain+url,
    		dataType: "json",
    		data:data,
    		async:false,
    		contentType: "application/json; charset=UTF-8",
    		type: requestMethod,
    		beforeSend: beforeSendFunction(progressBar),
            complete: completeFunction,
    		success: successFunction,
    		error: errorFunction
       });
    }
    }
    
}

function getType(o) { 
	var _t; return ((_t = typeof(o)) == "object" ? o==null && "null" || Object.prototype.toString.call(o).slice(8,-1):_t).toLowerCase(); 
} 