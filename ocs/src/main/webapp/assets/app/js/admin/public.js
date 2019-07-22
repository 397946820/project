var remove = function (event) {
    var $row = undefined;
    if('datagrid' == event.data.type) $row = $(event.data.grid).datagrid('getSelected');
    if('treegrid' == event.data.type) $row = $(event.data.grid).treegrid('getSelected');
    if (!$row) {
        $.messager.alert('消息提示', '请先选择记录', 'error');
    } else {
    	//刊登列表特殊处理
    	if($row.itemId){
    		$.messager.alert('消息提示', '已经刊登的范本不能删除', 'error');
    	}else{
    		 $.messager.confirm('请先确认', '确定要删除记录吗？', function (r) {
	            if (r) {
	                $.ajax({
	                    url: GLOBAL.domain + event.data.url,
	                    type: 'post',
	                    dataType: 'json',
	                    data: 'id=' + $row.id,
	                    beforeSend: function () {
	                        $.messager.progress({
	                            title: '请稍后',
	                            msg: '正在删除记录...'
	                        });
	                    },
	                    complete: function () {
	                        $.messager.progress('close');
	                    },
	                    success: function (data) {
	                        if (data) {
	                            $.messager.alert('消息提示', data.description, (data.errorCode == 0) ? 'info' : 'error');
	                            if('datagrid' == event.data.type){
	                                $(event.data.grid).datagrid('clearSelections');
	                                $(event.data.grid).datagrid('reload');
	                            }

	                            if('treegrid' == event.data.type){
	                                $(event.data.grid).treegrid('clearSelections');
	                                $(event.data.grid).treegrid('reload');
	                            }
	                        }
	                    },
	                    error: function () {
	                        $.messager.alert('消息提示', '系统开小差，不给力', 'error');
	                    }
	                });
	            }
	        });
    	}
    }
    event.preventDefault();
};

var removeAll = function (event) {
    var rows = undefined;
    if('datagrid' == event.data.type) rows = $(event.data.grid).datagrid('getSelections');
    if('treegrid' == event.data.type) rows = $(event.data.grid).treegrid('getSelections');
    if (rows.length == 0) {
        $.messager.alert('消息提示', '请先选择记录', 'error');
    } else {
        $.messager.confirm('请先确认', '确定要删除记录吗？', function (r) {
            if (r) {
        		var param = {};
        		//支持对删除操作的参数进行扩展：othersParam = {}, example: { a: 'A', b: 'B' }
        		if(event.data.othersParam && 'object' === typeof event.data.othersParam) {
        			for(var key in event.data.othersParam) {
        				param[key] = event.data.othersParam[key];
        			}
        		} 

            	var arr = new Array();
        		for (var i = 0; i < rows.length; i++) {
        			arr.push(rows[i].id);
        		}
        		param.ids = arr.join(",");
        		
                $.ajax({
                    url: GLOBAL.domain + event.data.url,
                    type: 'post',
                    dataType: 'json',
                    data: param,
                    beforeSend: function () {
                        $.messager.progress({
                            title: '请稍后',
                            msg: '正在删除记录...'
                        });
                    },
                    complete: function () {
                        $.messager.progress('close');
                    },
                    success: function (data) {
                        if (data) {
                            $.messager.alert('消息提示', data.description, (data.errorCode == 0) ? 'info' : 'error');
                            if('datagrid' == event.data.type){
                                $(event.data.grid).datagrid('clearSelections');
                                $(event.data.grid).datagrid('reload');
                            }

                            if('treegrid' == event.data.type){
                                $(event.data.grid).treegrid('clearSelections');
                                $(event.data.grid).treegrid('reload');
                            }
                        }
                    },
                    error: function () {
                        $.messager.alert('消息提示', '系统开小差，不给力', 'error');
                    }
                });
            }
        });
    }
    event.preventDefault();
};

var export_ = function(event) {
	$.messager.confirm('请先确认', '确定要导出吗？', function(r) {
		if (r) {
			var arr = $(event.data.grid).datagrid('getRows');
			if (arr.length == 0) {
				$.messager.alert("提示信息", "没有数据,无法导出");
				return;
			}
			var rows = $(event.data.grid).datagrid('options').queryParams;
			
			var json = JSON.stringify(rows);
			
			var form = $("<form>");   //定义一个form表单

		    form.attr('style','display:none');   //在form表单中添加查询参数

		    form.attr('method','post');

		    form.attr('action',GLOBAL.domain + event.data.url); 
		    
		    var input1 = $('<input>'); 

		    input1.attr('type','hidden'); 

		    input1.attr('name','json'); 

		    input1.attr('value',json);  

	        $('body').append(form);  //将表单放置在web中

	        form.append(input1);   //将查询参数控件提交到表单上

	        form.submit();   //表单提交
		}
	});
}

Date.prototype.format = function (format) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    if (!format) {
        format = "yyyy-MM-dd hh:mm:ss";
    }

    var o = {
        "M+" : this.getMonth() + 1, // month
        "d+" : this.getDate(), // day
        "h+" : this.getHours(), // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" : this.getMilliseconds()
        // millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};
var getTime = function(value, row, index){
	if(value != '' && value != null) {
		var time = new Date(value);
		time = time.format("yyyy-MM-dd hh:mm:ss");
		return time;
	}
	return '';
}
var getImage = function(value,row,index){
    return "<img src='"+value+"'/>";
}

var le = le || {};
le.utils = le.utils || {};
/**
 * 对数组进行遍历操作
 * @param arr 待操作的数组
 * @param fn  执行操作的函数（函数带一个参数：当前遍历的数组元素），例如：fn = function(obj) { console.log(obj); }
 * @returns void
 */
le.utils.eachArray = function(arr, fn) {
	if($.isArray(arr) && $.isFunction(fn)) {
		for(var i = 0, l = arr.length; i < l; i++) {
			fn(arr[i]);
		}
	}
}

/**
 * 对一个纯粹的Object对象（使用new Object()或者{}创建的对象）进行遍历操作
 * @param arr 待操作的数组
 * @param fn  执行操作的函数（函数带一个参数：当前遍历的数组元素），例如：fn = function(obj) { return true; }
 * @returns void
 */
le.utils.eachObject = function(obj, fn, enabledBreak) {
	if($.isPlainObject(obj) && $.isFunction(fn)) {
		for(var k in obj) {
			if(fn(obj[k]) && enabledBreak) {
				break;
			}
		}
	}
}

	