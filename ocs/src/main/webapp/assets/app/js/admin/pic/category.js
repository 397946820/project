
var picCategory = {};
(function(picCategory,$){
	var first = true;
	var isCreate = true;
	$("#picCategoryTreegrid").tree({
		url: '/ocs/picCategory/list',
		method: 'POST',
		animate: true,
		onClick: function(node){
			$("input[name='categoryIdsType']:last").attr("checked", true);
			picCategory.imageListLoad();
		}
	});
	$('#ebayImgListPage').pagination({
		onSelectPage:function(pageNumber, pageSize){
			$(this).pagination('loading');
			picCategory.uploadSearch(pageNumber,pageSize);
			$(this).pagination('loaded');
		}
	});
	 //上传功能
    $("#imageUploadSubmit").click(function(){
    	var node = $('#picCategoryTreegrid').tree('getSelected'); 
    	if(node){
    		curFieldId = node.id;
    		//初始化上传列表 imgUploadList
        	$("#imgUploadList").datagrid({
        		url:'',
        		singleSelect : false,
    			rownumbers : true,
    			fit: true,
    			border : true,
    			inline : true,
        	    columns:[[
        	    	{field:'name',hidden:true},
        	    	{field:'url',title:'图片',width:100,formatter:function(value,row,index){
        	    		return '<img id="url_'+value+'"  style="width:100px;height:100px;" />';
        	    	}},
        			{field:'fileName',title:'文件名称',width:200,editor:{type:"text"}},
        			{field:'fileSize',title:'文件大小',width:100,formatter:function(value,row,index){
        				var num = Math.round(eval(value/1024));
        				return num +"KB";
        			}},
        			{field:'fileType',title:'文件格式',width:100},
        			{field:'progress',title:'上传进度',width:180,
        				formatter:function(value,row,index){
        					return  '<progress id="progressBar'+index+'" value="0" max="100" style="width: 100%;height: 10px; "> </progress> ' 
        				}
        			},
        			{field:'opt',title:'操作',width:60,
        				formatter:function(value,row,index){
        					return  '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="picCategory.uploadRemove('+index+')" data-options="iconCls:\'icon-delete\'">删除</a>' 
        				}
        			},
        			
        	    ]],
        	    onClickRow :function(index,row){
        	    	$(this).datagrid('beginEdit', index);
        	    },
        	    toolbar: "#imgUploadListTb"
        	});
        	$("#imgUploadDialog").dialog("open");
        	$('#imgUploadDialog').window('center');
        	files = [];
    	}else{
    		$.messager.alert('提示','请您先选择上传的分类！');
    	}
    	
    });

    $("#fileChoose").click(function(){
    	 $("#file").click();
    });
    $("#file").change(function(){
    	var f = true;
    	$.each(this.files,function(){
    		//验证图片名称，大小
    		if(this.type.toLowerCase() != "image/jpeg".toLowerCase()&&this.type.toLowerCase() != "image/jpg".toLowerCase()){
    			f =false;
    			$.messager.alert('提示','只能选择JPG格式图片！');
    			return false;
    		}
    		if(this.size > 1024*1024){
    			f =false;
    			$.messager.alert('提示','只能小于1M的图片！');
    			return false;
    		}
    		//验证重复
    		/*var curName = this.name;
    		$.each(files,function(){
    			if(curName == this.name){
    				f =false;
        			$.messager.alert('提示','您选择的图片已经存在！');
        			return false;
    			}
    		});
    		if(!f){
    			return false;
    		}*/
    	});
    	if(f){
    		//插入列表
        	$.each(this.files,function(){
        		files.push(this);
        		var date = new Date();
        		var ctime = date.getTime();
        		var row = {};
        		row["url"] = ctime;
        		row["name"] = this.name;
        		var cName = this.name;
        		row["fileName"] = this.name;
        		row["fileSize"] = this.size;
        		row["fileType"] = this.type;
        		$("#imgUploadList").datagrid('appendRow',row);
        		
        		var reader = new FileReader();
        	    // 读取文件作为URL可访问地址
        	    reader.readAsDataURL(this);
        	    //异步加载文件成功
        	    reader.onload = function(e){
        	    	document.getElementById("url_"+ctime).src = reader.result;
        	    	
        	    }

        	   
        	});
    	}
    	
    });
    //开始上传
    $("#fileUploadRun").click(function(){
    	$("#imgUploadList").datagrid("acceptChanges");
        var formData = new FormData();
        for(var i=0;i<files.length;i++){
            formData.append("file",files[i]);
        }
        var intervalId = setInterval(function(){
            $.get("/ocs/upload/getProcessStatus",{},function(data,status){
               if(data){
            	   var value = data.value;
                   var max = data.max;
                   var index = data.item;
                   if(value >= max){
                       clearInterval(intervalId);
                       value = max;//不能大于max
                   }
                   $("#progressBar"+index).attr("max",max);
                   $("#progressBar"+index).attr("value",max);
               }
           },"json");
        },100);
        $.ajax({
            type:"post",
            url:"/ocs/upload/ebayImg",
            data:formData,
            beforeSend: function () {
                $.messager.progress({
                    title: '请稍后',
                    msg: '正在上传...'
                });
            },
            complete: function () {
               $.messager.progress('close');
            },
            processData : false,
            contentType : false,
            success:function(data){
            	//上传成功更新上传图片名称和分类
            	if(data){
            		var returnFiles = eval("("+data+")");
            		var gridData = $("#imgUploadList").datagrid("getData");
            		gridData = gridData["rows"];
            		for(var i = 0;i<returnFiles.length;i++){
            			for(var j = 0;j<gridData.length;j++){
            				var da = returnFiles[i];
            				if(da.name == gridData[j].name){
            					da["resetName"] = gridData[j].fileName;
            					da["fieldId"] = curFieldId;
            					ocs.ajax({
            		    			url:"/upload/updateEbayImageInfo",
            		    			async: true,
            		    			type:"POST",
            		    			data:da,
            		    			success:function(returns){
            		    				
            		    			}
            		    		});
            				}
            			}
            		}
            	
            		picCategory.uploadSearch(1,50);
					//关闭窗口
					$("#imgUploadDialog").dialog("close");
					files = [];
					$.messager.show({
						title:'提示',
						msg:'上传成功.',
						timeout:3000,
						showType:'slide'
					});
            	}
            },
            error:function(e){

            }
        });
      
        
    });
    $("#imagListSearch").click(function(){
    	picCategory.uploadSearch(1,50);
    });
    $("#ebayImgListGrid").on("click",".image-del-btn",function(){
    	var id = $(this).parents(".image-box").find("input").val();
    	ocs.ajax({
			type:"POST",
			url :"/upload/imageDelete?ids="+id,
			data:"",
			success:function(data){
				if(data.data){
					picCategory.uploadSearch(1,50);
				}
			}
		});
    });
    $("#imagListDelete").click(function(){
    	var rows = picCategory.getImageChecked();
    	if(rows){
    		var ids = [];
    		$.each(rows,function(){
    			ids.push(this.id);
    		});
    		ocs.ajax({
    			type:"POST",
    			url :"/upload/imageDelete?ids="+ids.join(","),
    			data:"",
    			success:function(data){
    				if(data.data){
    					picCategory.uploadSearch(1,50);
    				}
    			}
    		});
    	}else{
    		$.messager.alert('提示','请您先选择要删除的图片！');
    	}
    });
    
    
    picCategory.uploadRemove = function(index,name){
    	$('#imgUploadList').datagrid('acceptChanges');  
    	var newFiles = [];
		$.each(files,function(){
			if(name != this.name){
				newFiles.push(this);
			}
		});
		files = newFiles;
		$('#imgUploadList').datagrid('deleteRow', index);  
		var rows = $('#imgUploadList').datagrid("getRows");
		$('#imgUploadList').datagrid("loadData", rows);
    };
    picCategory.uploadSearch = function(page,rows){
    	var param ={};
		var f = $("input[name='categoryIdsType']:checked").val();
		if(f == 0){
			var node = $('#picCategoryTreegrid').tree('getSelected'); 
			if(node){
				param["categoryIds"] = node.id;
			}else{
				param["categoryIds"] = "";
			}
			
		}else{
			param["categoryIds"] = "";
		}
		if(first){
			param["name"] = "";
		}else{
			param["name"] = $("#imagesListSearchName").textbox("getValue");
		}
		var params = {};
		params["param"] = param;
		params["page"] = page;
		params["rows"] = rows;
		ocs.ajax({
			type:"POST",
			url:"/pic/categoryPicList",
			data : params,
			success:function(result){
				//清空列表
		    	$('#ebayImgListGrid .img-box').find(".image-box").remove();
				$.each(result.rows,function(){
					var imgUrl = ""
					if(this.realUrl){
	    				imgUrl = this.realUrl;
	    			}else{
	    				if(this.serverUrl){
	    					imgUrl = this.serverUrl;
	    				}else{
	    					imgUrl = "/ocs/upload/image/"+this.id;
	    				}
	    			}
					 var imgDiv = '<div class="image-box">'
							+		"<img src='"+imgUrl+"' />"
							+		'<p class="image-title" name="name">'  + this.name + '</p>'
							+		'<p> <input class="image-cb"  type="checkbox" value="'+this.id+'"   name="imgList"/>' 
							+       '<a  href="javascript:void(0);"   ><span class="image-edit-btn"></span></a>' 
							+    '<a  href="javascript:void(0);"   ><span  class="image-del-btn">删除</span></a>' + '</p>'  
					 		+ '</div>'
					$('#ebayImgListGrid .img-box').append(imgDiv);
				});
				$('#ebayImgListPage').pagination({total:result.total});
			}
		});
    };
    picCategory.imageListLoad = function(){
    	//重新加载数据
    	//$("#picCategoryTreegrid").tree("reload");
    	picCategory.uploadSearch(1,50);
    }
    
    picCategory.getImageChecked = function(){
    	var rows = [];
    	$('#ebayImgListGrid .image-box').each(function(){
    		if($(this).find("input").is(':checked')){
    			var row = {};
    			row["id"] = $(this).find("input").val();
    			row["realUrl"] = $(this).find("img").attr("src");
    			rows.push(row);
    		}
    		
    	});
    	
    	return rows;
    }
  //绑定点图片点击事件
    $(document).on('click', '.image-box', function() {
    	
    	if($(this).find("input[type='checkbox']").is(':checked')){
    		$(this).find("input[type='checkbox']").attr("checked",false);
    		$(this).css('background',"");
    	}else{
    		$(this).find("input[type='checkbox']").attr("checked",true);
    		$(this).css('background',"blanchedalmond");
    	}
        
    });

    //绑定点图片点击事件
    $(document).on('click', '.image-cb', function() {
    	if($(this).is(':checked')){
    		$(this).attr("checked",false);
    		$(this).parents(".image-box").css('background',"");
    	}else{
    		$(this).attr("checked",true);
    		$(this).parents(".image-box").css('background',"blanchedalmond");
    	}
        
    });
    
    $("#allChoose").click(function(){
    	if($(this).is(":checked")){
    		 $(".image-cb").each(function(){
    			
    			 $(this).attr("checked",true);
    			 $(this).css('background',"blanchedalmond");
    			
    		 });
    	}else{
    		$(".image-cb").each(function(){
    			 $(this).attr("checked",false);
    			 $(this).css('background',"blanchedalmond");
    		 });
    	}
    	
    });
    
    $("#contraryChoose").click(function(){
   	 $(".image-cb").each(function(){
   		 if($(this).is(':checked')){
   			 $(this).attr("checked",false);
   			 $(this).css('background',"");
   		 }else{
   			 $(this).attr("checked",true);
   			 $(this).css('background',"blanchedalmond");
   		 }
   		
   		
   	 });
   });
   $("#picCategoryAddLinkbutton").click(function(){
	   var node = $('#picCategoryTreegrid').tree('getSelected');  
       if (node){
    	   isCreate = true;
    	   $("#fieldName").textbox("setValue","");
    	   $('#picCategoryDialog').dialog('open');
       }else{
    	   $.messager.alert('Warning','请选择一个类目作为父类目！');
       }
	   
   });
   $("#picCategoryEditLinkbutton").click(function(){
	   var node = $('#picCategoryTreegrid').tree('getSelected');  
       if (node){
    	   isCreate = false;
    	   $("#fieldName").textbox("setValue",node.text);
    	   $('#picCategoryDialog').dialog('open');
       }else{
    	   $.messager.alert('Warning','请选择要编辑的类目！');
       }
   });
   picCategory.picFormSave1 = function () {
	    var node = $('#picCategoryTreegrid').tree('getSelected');  
    	var fieldName = $("#fieldName").textbox("getValue");
    	if(fieldName){
    		var param = {};
    		if(isCreate){
    			param["pid"]= node.id;
    		}else{
    			param["id"]= node.id;
    		}
    		
    		param["name"]= fieldName;
    		ocs.ajax({
    			type:'POST',
    			url:'/picCategory/save',
    			data :param,
    			success:function(data){
    				if(data){
    					$("#fieldName").textbox("setValue","");
    					$('#picCategoryTreegrid').tree('reload');
    					$("#picCategoryDialog").dialog("close");
    				}
    			}
    		});
    	}else{
    		$.messager.alert('Warning','请填入类目名称！');
    	}
     
    };

    $("#picCategoryRemoveLinkbutton").click(function(){
    	$.messager.confirm('确认', '确定删除类目吗，删除后类目下的图片也被删除?', function(r){
			if (r){
				var node = $('#picCategoryTreegrid').tree('getSelected');  
		        if (node){
		        	debugger
		        	if(!node.children||node.children.length == 0){
		        		var param = {};
		        		param["id"]= node.id;
		        		ocs.ajax({
		        			type:'POST',
		        			url:'/picCategory/remove',
		        			data :param,
		        			success:function(data){
		        				if(data){
		        					$('#picCategoryTreegrid').tree('reload');
		        					$("#picCategoryDialog").dialog("close");
		        				}
		        			}
		        		});
		        	}else{
		        		$.messager.alert('提示','请先删除此类目下所有的子类目后再删除！');
		        	}
		        }else{
		        	$.messager.alert('提示','请选择删除的类目！');
		        }
			}
		});
    	
    });

    picCategory.imageListLoad();
})(picCategory,jQuery);