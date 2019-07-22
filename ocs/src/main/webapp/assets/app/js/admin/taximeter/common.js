function comboboxInit(id, url) {
	if (url == '') {
		$('#' + id).combobox({
			valueField : 'VALUE',
			textField : 'NAME'
		});
	} else {
		$('#' + id).combobox({
			url : GLOBAL.domain + url,
			valueField : 'VALUE',
			textField : 'NAME'
		});
	}
}

function comboboxChange(id1, id2) {
	$('#' + id1).combobox({
		onChange : function(newValue, oldValue) {
			if (newValue) {
				$('#' + id2).combobox('enable');
				ocs.ajax({
					url : '/smallTypeItem/getShippingTypeByTypeName',
					async : true,
					data : {
						'typeName' : newValue
					},
					type : "POST",
					success : function(result) {
						if (result) {
							$('#' + id2).combobox('loadData', result);
						} else {
							$('#' + id2).combobox('loadData', []);
						}
					}
				});
			} else {
				$('#' + id2).combobox('loadData', []);
				$('#' + id2).combobox('disable');
			}
			$('#' + id2).combobox('setValue', '');
		}
	});
}

$(function() {
	
	// 遮罩
	$('#uploadDialog').dialog({
		modal:true
	});
	
	// 不能选择未来时间
	$.extend($.fn.datebox.defaults.rules,{  
	    checkDate:{  
	         validator:function(value, param){      
	            var nowDate = new Date();  
	            var arr1 = value.split(" "); 
	            var sdate = arr1[0].split('-'); 
	            var date = new Date(sdate[0], sdate[1]-1, sdate[2]);
	            return date<=nowDate;  
	         },  
	         message:"不能选择未来时间"  
	    }     
	});
	
})

// 时间比较
function moreTime() {
	var boolean = false;
	var cstarttime = $('#cstarttime').datebox('getValue');
	var cendtime = $('#cendtime').datebox('getValue');
	var ustarttime = $('#ustarttime').datebox('getValue');
	var uendtime = $('#uendtime').datebox('getValue');
	if(cstarttime !='' && cendtime != '') {
		if(cstarttime > cendtime) {
			boolean = true;
		}
	}
	if(ustarttime != '' && uendtime != '') {
		if(ustarttime > uendtime) {
			boolean = true;
		}
	}
	return boolean;
}

// 双击效果
function doDblClickRow(rowIndex, rowData){
	editOpen(rowData);
}

var lightEbayUpload = function() {
	$('#uploadForm').form('clear');
	$("#messages").panel('close');
	$('#uploadDialog').window('open');
};

var lightEbayExport = function(event) {
	 $.messager.confirm('请先确认', '确定要下载模板吗？', function (r) {
	        if (r) {
	        	window.location.href = GLOBAL.domain + event.data.url;
	        }
	 });
}

//恢复可用利润率
var recover = function(event) {
	var url = GLOBAL.domain + event.data.url;
	$.ajax({
		url : url,
		type : 'POST',
		data : {status : event.data.status},
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$(event.data.grid).datagrid("reload");
				$.messager.alert('消息提示', $data.description, 'info');
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	})
}

//刷新价格计划
var refresh = function(event) {
	$.messager.confirm('请先确认', '确定要刷新吗？', function (r) {
        if (r) {
        	$.ajax({
        		url : GLOBAL.domain + event.data.url,
        		type : 'POST',
        		data : {status : event.data.status},
        		beforeSend: function () {
                    $.messager.progress({
                        title: '请稍后',
                        msg: '正在刷新记录...'
                    });
                },
                complete: function () {
                    $.messager.progress('close');
                },
        		success : function(response) {
        			var $data = JSON.parse(response);
        			if ($data && $data.errorCode == 0) {
        				$(event.data.grid).datagrid("reload");
        				$.messager.alert('消息提示', $data.description, 'info');
        			} else if ($data && $data.errorCode == 1) {
        				$.messager.alert('消息提示', $data.description, 'warning');
        			}
        		},
        	    error: function(XMLHttpRequest, textStatus, errorThrown) {
        	    	  $.messager.alert('消息提示', '刷新失败', 'warning');
        	    }
        	});
        }
	});
}

// 导出
var derive = function (event) {
	var template = $('#template').combobox('getValue');
	if(template == '') {
	    $.messager.confirm('请先确认', '确定要导出吗？', function (r) {
	        if (r) {
	        		var arr = $(event.data.grid).datagrid('getRows');
	        		if(arr.length == 0) {
	        			$.messager.alert("提示信息", "没有数据,无法导出");
	        			return;
	        		}
	        		var rows = $(event.data.grid).datagrid('options').queryParams;
	    			
	        		template = JSON.stringify(rows);
	    			
	    			var form = $("<form>");   // 定义一个form表单
	
	    		    form.attr('style','display:none');   // 在form表单中添加查询参数
	
	    		    form.attr('method','post');
	
	    		    form.attr('action',GLOBAL.domain + event.data.url); 
	    		    
	    		    var input1 = $('<input>'); 
	
	    		    input1.attr('type','hidden'); 
	
	    		    input1.attr('name','template'); 
	
	    		    input1.attr('value',template);  
	
	    	        $('body').append(form);  // 将表单放置在web中
	
	    	        form.append(input1);   // 将查询参数控件提交到表单上
	    	        if(status) {
	    		    	var input2 = $('<input>'); 
	    		    	
	    		    	input2.attr('type','hidden'); 
	    		    	
	    		    	input2.attr('name','status'); 
	    		    	
	    		    	input2.attr('value',status);  
	    		    	
	    		    	form.append(input2);
	    		    }
	
	    	        form.submit();   // 表单提交
	        	} 
	        });
		} else {
			 $.messager.confirm('请先确认', '确定要下载模板吗？', function (r) {
			        if (r) {
			        	window.location.href = GLOBAL.domain + event.data.url + "?template="+template+'&status=';
			        }
			 });
		}
    event.preventDefault();
};

// 上传
var upload = function (event) {
	var formData = new FormData();
	var myfile = document.getElementById("file").files[0];
	if(myfile == '' || myfile == null) {
		$.messager.alert("提示信息", "请选择导入的文件");
		 return;
	}
    formData.append("myfile", myfile);
	$.ajax({
	      type: "POST",
	      url: GLOBAL.domain + event.data.url,
	      enctype: 'multipart/form-data',
	      data:formData,
	      contentType: false,
	      processData: false,
	      beforeSend: function () {
	            $.messager.progress({
	                title: '请稍后',
	                msg: '正在导入...'
	            });
	        },
	        complete: function () {
	            $.messager.progress('close');
	        },
	      success: function (response) {
	    	  var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
					$(event.data.dialog).window('close');
					$(event.data.grid).datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
	      },
	      error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '导入失败', 'warning');
	      }
	 });
}

//上传
var newUpload = function (event) {
	// 获取上传文件控件内容
    var file = $("input[type=file]")[0].files[0]; 
   
    if(file == '' || file == null) {
		$.messager.alert("提示信息", "请选择导入的文件");
		return;
	}
    // 获取文件名称
    var fileName = file.name;
    
    // 获取文件类型名称
    var file_typename = fileName.substring(fileName.lastIndexOf('.'), fileName.length);
   
    // 限定上传文件文件类型必须为.xlsx，如果文件类型不符，提示错误信息
    if (file_typename == '.xlsx') {
        // 如果文件大小大于1024字节X1024字节，则显示文件大小单位为MB，否则为KB
        if (file.size > 1024 * 1024) {
　　　　　		var fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;
	　　　　　if (fileSize > 2) {
			   $.messager.alert("提示信息", "文件超过2MB，禁止上传！");
	           return;
	        }
        } 
        var formData = new FormData();
        formData.append("file", file);
    	$.ajax({
		      type: "POST",
		      url: GLOBAL.domain + event.data.url,
		      enctype: 'multipart/form-data',
		      data:formData,
		      contentType: false,
		      processData: false,
		      beforeSend: function () {
		            $.messager.progress({
		                title: '请稍后',
		                msg: '正在导入...'
		            });
		        },
		        complete: function () {
		            $.messager.progress('close');
		        },
		      success: function (response) {
		    	  var $data = JSON.parse(response);
					if ($data && $data.errorCode == 0) {
						if($data.data.message.length == 0) {
							$.messager.alert('消息提示', $data.description, 'info');
							$(event.data.dialog).window('close');
							$(event.data.grid).datagrid("reload");
						} else {
							var json = $data.data.message;
							var flag = '';
							for (var i = 0; i < json.length; i++) {
								flag += ('<li>' + json[i] + '</li>');
							}
							$("#showMessages_").html(flag);
							$("#messages").panel('open');
							$(event.data.grid).datagrid("reload");
						}
					}
		      },
		      error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	  $.messager.alert('消息提示', '导入失败', 'warning');
		      }
        });
    } else {
    	$.messager.alert("提示信息", "文件类型错误");
        return;
    }
};

var map = {"US":"United States",
		   "UK":"United Kingdom",
		   "DE":"German",
		   "United States":"US",
		   "United Kingdom":"GB",
		   "German":"DE",
		   "France":"FR",
		   "Spain":"ES",
		   "Italy":"IT",
		   "Canada":"CA",
		   "Japan":"JP",
		   "Australia":"AU"};

function getCountry_(data) {
	return map[data];
}

function getCountry(data, row, index) {
	return map[data];
}

function getShippingType(value,row,index){
	if (value == 'af'){
		return 'BY AF';
	} else if(value == 'sf'){
		return 'BY SF';
	} else if(value == 'co') {
		return 'BY CO';
	}  
	return value;
}

function getPlatform(data, row, index) {
	if(data == 'ebay') {
		return 'Ebay';
	} else if(data == 'light') {
		return '官网';
	}
	return data;
}