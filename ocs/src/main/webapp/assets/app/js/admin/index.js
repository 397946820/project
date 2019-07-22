var $XBCB_TABS;
$.extend($.fn.validatebox.defaults.rules, {
	confirmpassword: {
        validator: function (value, param) {
        	return $('#newPwd').val() == value;
        }, message: '两次密码输入不一致'
    },
    pwdIsTrue:{
    	validator:function(value,param) {
    		var isTrue = false;
    		$.ajax({
    			url : GLOBAL.domain+'/user/valPwdIsTrue',
    			dataType:'json',
    			type:'post',
    			data:{
    				password:$('#oldPwd').val()
    			},
    			success:function(data) {
    				isTrue = data
    			},
    			async:false//true为异步，false为同步
    		});
    		return isTrue;
    	},
    	message: '旧密码输入错误'
    }
});
var addParentTab = function(title, href) {
	setPreId(null);
	if (!$XBCB_TABS)
		return;
	$XBCB_TABS.tabs('add', {
		title : title,
		content : '<iframe scrolling="no" frameborder="0" src="' +GLOBAL.domain+ href
				+ '" width="100%" height="99.5%"></iframe>',
		closable : true
	});
}; 

function addDate(date, days) {
    /* if (days == undefined || days == '') {
         days = 1;
     }*/
     var date = new Date(date);
     date.setDate(date.getDate() + parseInt(days));
     var month = date.getMonth() + 1;
     var day = date.getDate();
     return date;
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

var addParentArticleTab = function(title, href) {
	if (!$XBCB_TABS)
		return;
	$XBCB_TABS.tabs('add', {
		title : title,
		content : '<iframe scrolling="no" frameborder="0" src="'+GLOBAL.domain + href
				+ '" width="100%" height="99.5%"></iframe>',
		closable : true
	});
};

var closeParentTab = function(title) {
	if (!$XBCB_TABS)
		return;
	$XBCB_TABS.tabs('close', title);
};
var nodeName = null;
var nodeUrl = null;
var ocsPublication={};
(function(ocsPublication){
	var conditions=0;
	ocsPublication.setConditions=function(value){
		conditions=value;
	}
	ocsPublication.getConditions=function(){
		return conditions;
		
	}
})(ocsPublication);
var menuClickHandler = function(title, href) {
	
	var start = href.lastIndexOf('/')+1;
	var end = href.length;
	var conditions = href.slice(start,end)
	ocsPublication.setConditions(conditions);
	$XBCB_TABS = $('#mainTabs');
	if ($XBCB_TABS.tabs('exists', title)) {
	
		$XBCB_TABS.tabs('select', title);
	} else {
		addParentTab(title, href);
	}
};
var extensionClickHandler = function() {
	
	var start = nodeUrl.lastIndexOf('/')+1;
	var end = nodeUrl.length;
	var conditions = nodeUrl.slice(start,end)
	ocsPublication.setConditions(conditions);
	$XBCB_TABS = $('#mainTabs');
	addParentTab(nodeName, nodeUrl);
};
var correlationId=null;
var preId = null;
var setCorrelationId=function(id){
	correlationId=id;
}
var getCorrelationId = function(){
	return correlationId;
}
var setPreId=function(id){
	preId=id;
}
var getPreId = function(){
	return preId;
}
var pwdChangeSave = function() {
	if ($('#pwdChangeForm').form('validate')) {
		$.ajax({
			url:GLOBAL.domain+'/user/changePassword',
			type:'post',
			dataType:'json',
			data:{
				newPassword:$('#newPwd').val()
			},
			beforeSend: function () {
                 $.messager.progress({
                     title: '请稍后',
                     msg: '正在修改密码...'
                 });
             },
             complete: function () {
                 $.messager.progress('close');
             },
             success: function (data) {
                 if (data) {
                     $.messager.alert('消息提示', data.description, (data.errorCode == 0) ? 'info' : 'error');
                     $('#pwdChangeDialog').window('close');
                 }
             },
             error: function () {
                 $.messager.alert('消息提示', '密码修改失败', 'error');
             }
		})
	}
}
$(function() {
	$('#changePwd').click(function() {
		$('#pwdChangeForm').form('clear');
		$('#pwdChangeDialog').window('open');
	});
	$('#logout').click(function(){
		window.location=GLOBAL.domain+'/user/doLogout';
	});
	$('.system li').click(function() {
		$(this).addClass('active').siblings().removeClass('active');
		var projectInfoId = $('#project_'+$(this).context.innerText).val();
	
		$('#mainTree').tree(
				{
					url : GLOBAL.domain + '/permission/menu',
					queryParams:{
						projectId:projectInfoId
					},
					formatter : function(node) {
						
						if (node.permissionType == "MENU") {
							return '<a href="javascript:menuClickHandler(\''
									+ node.name + '\',\'' + node.url + '\');">'
									+ node.name + '</a>';
						} else {
							return node.name;
						}
					}
					
				});
		location.reload();
	});
	$('.info li').mouseover(function() {
		$(this).css('color', 'black');
	});
	$('.info li').mouseout(function() {
		$(this).css('color', '#808080');
	})
	$('#mainTree').tree(
			{
				url : GLOBAL.domain + '/permission/menu',
				onContextMenu:function(e,node){
					e.preventDefault();
					nodeName = node.name;
					nodeUrl = node.url;
					$(this).tree('select',node.target);
					$('#menuEvent').menu('show',{
	                    left: e.pageX,
	                    top: e.pageY
	                });
				},
				formatter : function(node) {
					
					if (node.permissionType == "MENU") {
						var name = node.name;
						var nodeUrl = node.url;
						if(nodeUrl!=null||nodeUrl!=''){
							if(nodeUrl.indexOf("/TimingPlan/show/")>=0||nodeUrl.indexOf("/TimingPlan/lineShow/")>=0){
								var index = nodeUrl.lastIndexOf('/');
								var subResult = nodeUrl.substring(index+1,nodeUrl.length);  
								if(subResult!="all"&&(subResult<-1||subResult>1)){
									var date = new Date();
									date = formatDate(date,"yyyy-MM-dd");
									date = addDate(date,subResult);
									name = formatDate(date,"yyyy-MM-dd");
								}
								
							}
						}
						return '<a href="javascript:menuClickHandler(\''
								+ name + '\',\'' + node.url + '\');">'
								+ name + '</a>';
					} else {
						return node.name;
					}
				}
			});
	
})