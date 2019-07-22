var selectCategory1 = 0;
var selectCategory2 = 0;
var selectCategory3 = 0;
var selectCategory4 = 0;
var txteBay1 = '';
var txteBay2 = '';
var txteBay3 = '';
var txteBay4 = '';
var boolean;
var siteIdAll = "";
var attrFlage = 1;
var files = [];
var curFieldId = "";

function checkOption(){
	if(ocs.stringIsNull(siteIdAll)){
		siteIdAll = $('#site').combobox("getValue");
	}
	if(ocs.stringIsNull(siteIdAll)){
		$.messager.alert("信息","请选择站点后再进行操作！");	
		return true;
	}else{
		return false;
	}
}

function selectCategory(marketplaceId,categoryId,catText,category) {
	if(marketplaceId == null||marketplaceId == "" || marketplaceId== undefined){
		return;
	}
	var url = '';
	if(category == 1 || category == 2) {
		url = GLOBAL.domain + '/selectCategory/echo';
		
	} else if (category == 3 || category == 4) {
		url = GLOBAL.domain + '/storeCategory/echo';
		
	}
	var ebayAccount = $("#ebayAccount").val();
	var ids = categoryId.split(",");
	$.ajax({
        url: url,
        data: {marketplaceId:marketplaceId,categoryId:categoryId,userId:ebayAccount},
        type: 'POST',
        success: function(data){
        	data = eval(data);
        	for (var i = 0; i < data.length; i++) {
				var res = data[i];
				var option = '';
				for (var j = 0; j < res.length; j++) {
					if(ids[i] == res[j].category_id) {
						option += '<option value='+res[j].category_id+' selected="selected">'+res[j].name+'</option>';
					} else {
						option += '<option value='+res[j].category_id+'>'+res[j].name+'</option>';
					}
					
				}
				$("#select_category ul").append('<li><select size="4" class="select marginright">'+option+'</select></li>');
			}
        }
	});
	$("#lbleBayCatText").html(catText);
    $("#lbleBayCatId").html(categoryId);
    $(".dialog_button input[name='category']").val(category);
    $("input[name='txteBayCatID']").val(ids[ids.length - 1]);
    $(".SuccessMsg").show();
    $(".ErrorMsg").hide();
}

function queryCategory(txteBay,category) {
	if(txteBay==null||txteBay==""){
		return;
	}
	
	var urlCategory = "";
	if(category==1||category==2){
		urlCategory= "queryCategory";
	}else{
		urlCategory="queryStoreCategory";
	}
	var ebayAccount = $("#ebayAccount").val();
	$.ajax({
        url: GLOBAL.domain + '/selectCategory/'+urlCategory,
        data: {categoryId:txteBay,marketplaceId:siteIdAll,userId:ebayAccount},
        type: 'POST',
        success: function(data){
        	if(data){
        		data = JSON.parse(data);
            	var name = '<span style="display:block;" id="catText'+ category +'">'+data.name+'</span>';
                var id = '<span style="display:none;" id="selectedCategoryId'+ category +'">'+data.id+'</span>';
            	if(category == 1) {
            		 $("#upeBayCatText1").html(name);
                     $("#upeBayCatId1").html(id);
            	} else if(category == 2) {
            		 $("#upeBayCatText2").html(name);
                     $("#upeBayCatId2").html(id);
            	}else if(category == 3){
            		 $("#UpdatePanel3").html(name);
                     $("#upeBayCatId3").html(id);
            	}else if(category == 4){
            		 $("#UpdatePanel4").html(name);
                     $("#upeBayCatId4").html(id);
            	}
            	
            	queryCategoryAtt(siteIdAll,txteBay);
        	}
        }
	});
}

function ifThereIs(txteBay,category) {
	var ebayAccount = $("#ebayAccount").val();
	if(txteBay != '') {
		var url = '';
		if(category == 1 || category == 2) {
			url = GLOBAL.domain + '/selectCategory/ifThereIs';
			
		} else if (category == 3 || category == 4) {
			url = GLOBAL.domain + '/storeCategory/ifThereIs';
			
		}
		$.ajax({
			url: url,
			data: {categoryId:txteBay,marketplaceId:siteIdAll,userId:ebayAccount},
			type: 'POST',
			async:false, //false代表只有在等待ajax执行完毕后执行后面的
			success: function(data){
				data = JSON.parse(data);
				if(data.errorCode == 1) {
					boolean = true;
				}
			}
		});
	} else {
		boolean = false;
	}
}
function queryCategoryAtt(marketplace_id,selectedVal){
	$.ajax({	
			url: GLOBAL.domain+'/ProductListing/internalSelectProductListingByMIDOrCID',
			data: {marketplace_id:marketplace_id,category_Id:selectedVal},
			dataType: "json",
			contentType: "application/json; charset=UTF-8",
			type: "get",
			success: function(result) {
				if(result){
					var productCode='' ;
                if(result.ean=="Required"||result.ean=="Enabled"){
                	productCode="EAN";
                }else if(result.isbn=="Required"||result.isbn=="Enabled"){
                	productCode="ISBN";
                }else if(result.upc=="Required"||result.upc=="Enabled"){
                	productCode="UPC";
                }
             	$("#productCode").text(productCode);
             	$("#dlCatalog").show();
 				var jsonArray2 = eval(result.rows.rows);
 				var resultString = [];
 				if(jsonArray2){
 					$.each(jsonArray2,function(){
 						resultString.push('<span class="specifics">');
 						resultString.push('		<a class="addself" href="javascript:void(0);">+</a>');
 						resultString.push('		<span>'+this.name+'</span>');
 						resultString.push('</span>');
 					});
 				}
 				$("#prospecificsList").html(resultString.join(""));
				}
           
			},
			error: function(jqXHR, textStatus, errorThrown) {
				$.messager.alert("信息","分类关联的物品状况不存在！");	  		
			}
      });
}
var page = {
    init: function(){
        this.eventBind();
    },
    deleteDate:function(id){
    	mainAjax('/publication/saveAsDelete/'+id,null,'正在删除中......',"post",function(result) {
    	   /* $.messager.alert({
    	      	 title:'消息',
    	      	 msg:result.description,
    	      	 width:'300px',
    	      	 height:'240px'
    	      	 
    	       });*/
    		$('#saveAsListGrid').datagrid('reload');
    	   });
    },
    getData: function(url,data,successCallback,errorCallback){
        var self = this;
        $.ajax({
            url: url,
            data: data,
            type: 'get',
            dataType: 'json',
            success: function(res){
                successCallback(res);//请求成功之后的操作
            },
            error: function(){
                errorCallback();//请求失败的操作
            }
        });
    },
    //事件绑定
    eventBind: function(){
        var self = this;

        //点击右下角关闭按钮
        $(".close_btn").on("click",function(){
            if (confirm("您确定要关闭此页面吗？")) {
                window.opener=null;
                window.open('','_self');
                window.close();
            }
        });  

        //鼠标滑动事件
        $(document).ready(this.loadmenu());

        // 一般信息

        //点击一般信息右侧备注按钮
        $("#spShowRemark").on("click",function(){
            $("#dlRemark").toggle();
        });

        //点击选择产品
        $("#tbBundleSKU").on("click","tr .select",function(){
            self.layerOpen("96%","90%",".selectProModel");
        });

        $("#productChoice").on("click",function(){
        	self.layerOpen("890px","690px",".productChoiceModel");
        	$("#productDatagrid").datagrid({
        		height:550,
        		url:'/ocs/product/findAll',
    	        fitColumns:true,
    	        queryParams:{
    	        	param : {
    	        		"sku":$("#productChooseSku").val()
    	        	}
    	        },
                columns: [
    	            [
    	            
    	                {field: 'sku', title: 'sku',align:'center',width:100},
    	                {field: 'updateDate', title: '更新时间',align:'center',width:100}
    	               
    	                /*{field: 'stock', title: '动作',width:100,align:'center',
    	                	formatter: function(value,row,index){
    	        				return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="page.chooseSaveAs('+index+');" data-options="plain：true">选择</a>';
    	        			}
    	                }*/
    	            ]
    	        ],
    	        idField: 'id',
    	        singleSelect: true,
    	        rownumbers: true,
    	        pagination: true,
    	        pageSize: 50,
    	        border:true,
    	        onSelect:function(index, row) {
    				$("#skuChecked").text(row.sku);
    				$("#skuid").val(row.id);
    			}
        	});

        });
        //input框中文本长度的显示
        $(".protitle").on("input propertychange","input[name='txtItemTitle']",function(){
            self.showTextLength(this,80);
        });
        $(".protitle").on("input propertychange","input[name='txtItemSubtitle1']",function(){
            self.showTextLength(this,55);
        });

        //分类
        //点击第一分类 选择分类
        $("#hrefBrowseCatID1").on("click",function(){
        	if(checkOption()){
        		return;
        	};
        	$("#select_category ul").empty();
        	$(".SuccessMsg").hide();
        	$("#lbleBayCatText").html(null);
        	$("input[name='txteBayCatID']").val(null);
     
    		if($("#selectedCategoryId1").text() == '') {
    			self.selectCategoryInit(1,GLOBAL.domain + "/selectCategory/selectCategorysByMarketplaceId",{categoryId:null , marketplaceId:siteIdAll});
        		selectCategory1++;
    		} else {
    			selectCategory(siteIdAll,$("#selectedCategoryId1").text(),$("#catText1").text(),1);
    			self.layerOpen("890px","690px",".selectCategoryModel");
    		}
        	
            $(".storeCategory_header").hide();
            $(".selectCategory_header").show();
        });

        //点击第二分类 选择分类
        $("#hrefBrowseCatID2").on("click",function(){
        	if(checkOption()){
        		return;
        	};
        	$("#select_category ul").empty();
        	$(".SuccessMsg").hide();
        	$("#lbleBayCatText").html(null);
        	$("input[name='txteBayCatID']").val(null);

    		if($("#selectedCategoryId2").text() == '') {
    			self.selectCategoryInit(2,GLOBAL.domain + "/selectCategory/selectCategorysByMarketplaceId",{categoryId:null , marketplaceId:siteIdAll});
        		selectCategory2++;
    		} else {
    			selectCategory(siteIdAll,$("#selectedCategoryId2").text(),$("#catText2").text(),2);
    			self.layerOpen("890px","690px",".selectCategoryModel");
    		}
    	
            $(".storeCategory_header").hide();
            $(".selectCategory_header").show();
        });

        //选择分类项
        $("#select_category").on("change","select",function(){
        	var ebayAccount = $("#ebayAccount").val();
            var _this = this;
            var categoryId = $(this).val();
            var url = '';
            var categoryVal = $(".dialog_button input[name='category']").val();
            if(categoryVal == 1 || categoryVal == 2) {
            	url = GLOBAL.domain + "/selectCategory/selectCategorysByMarketplaceId";
            } else if (categoryVal == 3 || categoryVal == 4) {
            	url = GLOBAL.domain + "/storeCategory/selectChildStoreCategorysByCIDAndMID";
            }
            
            var lbleBayCatText = "";
            var lbleBayCatId = "";
            self.getData(url,{categoryId:categoryId , marketplaceId:siteIdAll,userId:ebayAccount},function(res){
                if(res != null && res.length > 0) {
                	var option = "";
                	res = eval(res);
                	if($(_this).parent().next("li").length>0){
                		$(_this).parent().nextAll().remove();
                	}
	                for(var i = 0;i<res.length;i++){
	                	
	                   option += '<option value='+res[i].category_id+'>'+res[i].name+'</option>';
	                    
	                }
	                $("#select_category ul").append(self.selectCategoryLi(option));
	                $("#select_category select").find("option:selected").each(function(){
	                    if(lbleBayCatText != '') {
	                    	lbleBayCatText += (' > ' + $(this).text());
	                    	lbleBayCatId += (',' + $(this).val());
	                    } else {
	                    	lbleBayCatText = lbleBayCatText + $(this).text();
	                    	lbleBayCatId = lbleBayCatId + $(this).val();
	                    }
	                    $("#lbleBayCatText").html(lbleBayCatText);
	                    $("#lbleBayCatId").html(lbleBayCatId);
	                }); 
	                $(".SuccessMsg").hide();
	                $("input[name='txteBayCatID']").val(categoryId);
                } else {
                	 if($(_this).parent().nextAll().length>0){
                         $(_this).parent().nextAll().remove();                  
                     }
                     $("#select_category select").find("option:selected").each(function(){
                    	 if(lbleBayCatText != '') {
 	                    	lbleBayCatText += (' > ' + $(this).text());
 	                    	lbleBayCatId += (',' + $(this).val());
 	                     } else {
 	                    	lbleBayCatText = lbleBayCatText + $(this).text();
 	                    	lbleBayCatId = lbleBayCatId + $(this).val();
 	                     }
 	                     $("#lbleBayCatText").html(lbleBayCatText);
 	                     $("#lbleBayCatId").html(lbleBayCatId);
                     });
                     $("input[name='txteBayCatID']").val(categoryId);
                     $(".SuccessMsg").show();
                     $(".ErrorMsg").hide();
                }
            },function(){
                //errorCallback();
            });
            
        });

        //点击SuccessMsg中的close
        $(".SuccessMsg .close").on("click",function(){
            $("input[name='txteBayCatID']").val("");
            $(".SuccessMsg").hide();
        });

        //点击选择分类确认
        $(".selectCategoryModel input[value='确定']").on("click",function(){
        	
        	if($(".SuccessMsg").css("display") == "none"){
        		$(".ErrorMsg").show();
        		//messager.alert("信息","您所选择的分类还有子分类可选！");
        		return;
        	}else{
        		$(".ErrorMsg").hide();
        	}
            var selectedCategory = $("#lbleBayCatText").text();
            var selectedCategoryId = $("#lbleBayCatId").text();
            layer.closeAll();
            var categoryVal = $(".dialog_button input[name='category']").val();
            var selectedVal = $("input[name='txteBayCatID']").val();
            var marketplace_id = $('#site').combobox("getValue");
            var span = '<span style="display:block;" id="catText'+ categoryVal +'">'+selectedCategory+'</span>';
            var id = '<span style="display:none;" id="selectedCategoryId'+ categoryVal +'">'+selectedCategoryId+'</span>';
            if(categoryVal == 1){
                $("input[name='txteBayCatID1']").val(selectedVal);
                txteBay1 = selectedVal;
                $("#upeBayCatText1").html(span);
                $("#upeBayCatId1").html(id);
            }else if(categoryVal == 2){
                $("input[name='txteBayCatID2']").val(selectedVal);
                txteBay2 = selectedVal;
                $("#upeBayCatText2").html(span);
                $("#upeBayCatId2").html(id);
            }else if(categoryVal == 3){
                $("input[name='txteBayStoreCatID1']").val(selectedVal);
                txteBay3=selectedVal;
                $("#UpdatePanel3").html(span);
                $("#upeBayCatId3").html(id);
            }else if(categoryVal == 4){
            	 txteBay4=selectedVal;
                $("input[name='txteBayStoreCatID2']").val(selectedVal);
                $("#UpdatePanel4").html(span);
                $("#upeBayCatId4").html(id);
            } 
            if(categoryVal==1){
                 	 var searchCategory = $(".searchCategoryModel input[type='radio']:checked").parent().siblings(".category_td").text();
                      layer.closeAll();    
                      /*$("#dtProdISBN").show();
                      $("#dlCustomItemSpecifics").show();
                      $("#upc").show();
                      $("div").remove("#productCode div");
                      $("div").remove("#itemCondition div");
                      $("div").remove("#prospecificsList div");*/
                      $(".specificslist .prospecificsremove").each(function(){
                			$(this).parents("tr").remove();
              		  });
                      
                      queryCategoryAtt(marketplace_id,selectedVal);
            }
        });

        
        //点击取消
        $("input[value='关闭']").on("click",function(){
            layer.closeAll();
        });

        //点击 X
        $(".dialog_close").on("click",function(){
            layer.closeAll();
        });

        //点击第一分类 搜索
        $("#hrefSearchCatID1").on("click",function(){
        	var txteBay = $("input[name='txteBayCatID1']").val();
        	if(txteBay != txteBay1 && txteBay != '') {
        		queryCategory(txteBay,1);
        		txteBay1 = txteBay;
        		selectCategory1++;
        	} else {
        		self.layerOpen("720px","500px",".searchCategoryModel");
        		$(".searchCategoryModel input[name='category']").val(1);
        	}
        });
        //点击商店第一分类 搜索
        $("#hrefSearchStoreCatID1").on("click",function(){
        	
        	var txteBay = $("input[name='txteBayStoreCatID1']").val();
        	if(txteBay != txteBay3 && txteBay != '') {
        		queryCategory(txteBay,3);
        		txteBay3 = txteBay;
        		selectCategory3++;
        	} else {
        		self.layerOpen("720px","500px",".searchCategoryModel");
        		$(".searchCategoryModel input[name='category']").val(1);
        	}
        });
        //点击第二分类 搜索
        $("#hrefSearchCatID2").on("click",function(){
        	var txteBay = $("input[name='txteBayCatID2']").val();
        	if(txteBay != txteBay2 && txteBay != '') {
        		queryCategory(txteBay,2);
        		txteBay2 = txteBay;
        		selectCategory2++;
        	} else {
	            self.layerOpen("720px","500px",".searchCategoryModel");
	            $(".searchCategoryModel input[name='category']").val(2);
        	}
        });
      //点击商店二分类 搜索
        $("#hrefSearchStoreCatID2").on("click",function(){
        	var txteBay = $("input[name='txteBayStoreCatID2']").val();
        	if(txteBay != txteBay4 && txteBay != '') {
        		queryCategory(txteBay,4);
        		txteBay4 = txteBay;
        		selectCategory4++;
        	} else {
        		self.layerOpen("720px","500px",".searchCategoryModel");
        		$(".searchCategoryModel input[name='category']").val(1);
        	}
        });
        //点击弹出层中的搜索
        $(".searchCategoryModel .search_btn").on("click",function(){
            $(".searchCategoryModel .emptymessage").hide();
            self.getData("../json/searchCategory.json","",function(res){
                var tr = "";
                for(var i = 0;i<res.lists.length;i++){
                    tr += '<tr>'+
                        '<td>'+
                            '<input type="radio" name="rdoCatID" value='+res.lists[i].id+'>'+
                        '</td>'+
                        '<td>'+res.lists[i].id+'</td>'+
                        '<td class="category_td">'+res.lists[i].category+'</td>'+
                        '<td>'+res.lists[i].proximity+'</td>'+
                    '</tr>';
                }
                $(".searchCategoryModel .datagrid tbody").append(tr);
            },function(){
                //errorCallback();
            });
        });

        //点击所搜分类确认
        $(".searchCategoryModel input[value='确定']").on("click",function(){
            var searchCategory = $(".searchCategoryModel input[type='radio']:checked").parent().siblings(".category_td").text();
            layer.closeAll();
            if($(".searchCategoryModel input[name='category']").val() == 1){
                $("input[name='txteBayCatID1']").val($(".searchCategoryModel input[type='radio']:checked").val());
                $("#upeBayCatText1").html('<span style="display:block;">'+searchCategory+'</span>');
            }else{
                $("input[name='txteBayCatID2']").val($(".searchCategoryModel input[type='radio']:checked").val());
                $("#upeBayCatText2").html('<span style="display:block;">'+searchCategory+'</span>');
            }
            $("#dtProdISBN").show();
            $("#ddlItemCondition").prop("disabled","");
        });

        //商店分类
        //第一分类 选择分类
        $("#selectStore1").on("click",function(){
        	if(checkOption()){
        		return;
        	};
        	$("#select_category ul").empty();
        	$(".SuccessMsg").hide();
        	$("#lbleBayCatText").html(null);
        	$("input[name='txteBayCatID']").val(null);

    		if($("#selectedCategoryId3").text() == '') {
    				var ebayAccount = $("#ebayAccount").val();
        			self.selectCategoryInit(3,GLOBAL.domain + "/storeCategory/selectChildStoreCategorysByCIDAndMID",{categoryId:null , marketplaceId:siteIdAll,userId:ebayAccount});
            		selectCategory3++;
        		} else {
        			selectCategory(siteIdAll,$("#selectedCategoryId3").text(),$("#catText3").text(),3);
        			self.layerOpen("890px","690px",".selectCategoryModel");
        		}
        	
            $(".selectCategory_header").hide();
            $(".storeCategory_header").show();
        });
        $("#selectStore2").on("click",function(){
        	var ebayAccount = $("#ebayAccount").val();
        	if(checkOption()){
        		return;
        	};
        	$("#select_category ul").empty();
        	$(".SuccessMsg").hide();
        	$("#lbleBayCatText").html(null);
        	$("input[name='txteBayCatID']").val(null);
        	
    		if($("#selectedCategoryId4").text() == '') {
    			self.selectCategoryInit(4,GLOBAL.domain + "/storeCategory/selectChildStoreCategorysByCIDAndMID",{categoryId:null , marketplaceId:siteIdAll,userId:ebayAccount});
        		selectCategory4++;
    		} else {
    			selectCategory(siteIdAll,$("#selectedCategoryId4").text(),$("#catText4").text(),4);
    			self.layerOpen("890px","690px",".selectCategoryModel");
    		}
        	
            $(".selectCategory_header").hide();
            $(".storeCategory_header").show();
        });

        //物品属性与状况
        //物品属性 选择物品属性
        $("#prospecificsList").on("click","a",function(){
        	var marketplace_id = $('#site').combobox("getValue");
        	var selectedVal = $("#productFirstCategory").val();
        	var name = $(this).next().text();
        	var valueUrl = "/CategorySpecificsV/internalFindSpecificsVByNameAanCIDAanMID?marketplace_id="+marketplace_id+"&category_Id="+selectedVal+"&name="+name;
        	self.prospecificsTr(false,name,valueUrl);
        	$(this).parent(".specifics").remove();
        });
        $(".notApply").on("click",function(){
        	var siteId = $('#site').combobox("getValue");
        	if(siteId==77){
        		$("input[name='txtProdReferenceID']").val('Nicht zutreffend');
        	}else{
        		 $("input[name='txtProdReferenceID']").val('Does not apply');
        	}
           
           })
        $(".notApply2").on("click",function(){
            var siteId = $('#site').combobox("getValue");
        	if(siteId==77){
        		$("input[name='product_mpn']").val('Nicht zutreffend');
        	}else{
        		 $("input[name='product_mpn']").val('Does not apply');
        	}
           })
        $(".notApply3").on("click",function(){
        	var siteId = $('#site').combobox("getValue");
        	
            $("input[name='product_brand']").val('Lighting EVER');
           })
            $(".notApply4").on("click",function(){
        	var siteId = $('#site').combobox("getValue");
        	
            $("input[name='product_ust_idnr']").val('Nicht zutreffend');
           })
         
        //删除单个物品属性
        $(".specificslist").on("click",".prospecificsremove",function(){
            var name = $(this).parents("td").next().text();
            if(name){
            	//清空组件
    			var conbName = name.sTrim();
    			conbName = conbName.replace("/",'');
    			$("#productAttrValue"+conbName).combo("destroy");
    			//$("#productAttrValue"+conbName).parent(".combo-p").remove();
                var resultString = [];
                resultString.push('<span class="specifics">');
    			resultString.push('		<a class="addself" href="javascript:void(0);">+</a>');
    			resultString.push('		<span>'+name+'</span>');
    			resultString.push('</span>');
    			$("#prospecificsList").append(resultString.join(""));
            }
			$(this).parents("tr").remove();
			
        });
        //点击分类清除
        $(".category_clear").click(function(){
        	$(this).parent("span").prev().find("input").val("");
        	$(this).parent("span").next().find("span").text("");
        	$(this).parent("span").next().next().find("span").text("");
        });
        //添加全部
        $(".specifics_addall").on("click","a",function(){
            $(".prospecifics_list a").each(function(){
            	var marketplace_id = $('#site').combobox("getValue");
            	var selectedVal = $("#productFirstCategory").val();
            	var name = $(this).next().text();
            	var valueUrl = "/CategorySpecificsV/internalFindSpecificsVByNameAanCIDAanMID?marketplace_id="+marketplace_id+"&category_Id="+selectedVal+"&name="+name;
            	self.prospecificsTr(false,name,valueUrl);
            	$(this).parent(".specifics").remove();
            });
        });

        //添加新的物品属性
        $(".add_property").on("click",function(){
            self.prospecificsTr(true,attrFlage+"",""); 
        });

        //移除所有物品属性
        $(".remove_property").on("click",function(){
        	$(".specificslist .prospecificsremove").each(function(){
        		var name = $(this).parents("td").next().text();
        		 if(name){
                 	//清空组件
         			var conbName = name.sTrim();
         			conbName = conbName.replace("/",'');
         			$("#productAttrValue"+conbName).combo("destroy");
         			//$("#productAttrValue"+conbName).parent(".combo-p").remove();
                     var resultString = [];
                     resultString.push('<span class="specifics">');
         			resultString.push('		<a class="addself" href="javascript:void(0);">+</a>');
         			resultString.push('		<span>'+name+'</span>');
         			resultString.push('</span>');
         			$("#prospecificsList").append(resultString.join(""));
                }
      			$(this).parents("tr").remove();
      			
        	});
        });

        //物品状况
        $("#ddlItemCondition").on("change",function(){
            if($(this).val() != 1000){
                $("#ConditionDescription").show();
            }else{
                $("#ConditionDescription").hide();
            }
        });
      
        //物品状况描述
        $("#txtConditionDescription").on("input propertychange",function(){
            if($(this).val().length>0){
                $(".descriptionNum").html($(this).val().length);
            }
            $(".descriptionNum").html($(this).val().length);
        });

        //eBay 物品描述
        //选择图片
        $(".eBaySelectImg .selectImg").on("click",function(){
        	
            //self.layerOpen("96%","90%","#selectImgModel");
        	$("#picCategoryTreegrid").tree("reload");
        	page.imageListLoad();
            $('#selectImgModel').dialog('open').dialog('center').dialog('setTitle','选择图片');
            $("input[name='selectImg']").val("eBay");
        });
        $("#imgUploadDialog").dialog({
        	onClose :function(){
        		$("#imgUploadList").datagrid('loadData',[]);
        		files = [];
        	}
        });
        $("#selectImgModel").dialog({
        	onClose :function(){
        		$("#imagesListSearchName").textbox("setValue","");
        		$("input[name='categoryIdsType']:last").attr("checked", true);
        	}
        });
      //初始化图片分类
    	$("#picCategoryTreegrid").tree({
    		url: '/ocs/picCategory/list',
			method: 'POST',
			animate: true,
			onClick: function(node){
				$("input[name='categoryIdsType']:last").attr("checked", true);
				page.imageListLoad();
			}
    	});
    	//初始化列表
    	/*$("#ebayImgListGrid").datagrid({
    		url:'/ocs/pic/categoryPicList',
    		singleSelect : false,
			rownumbers : true,
			pagination : true,
			fit: true,
			pageSize : 30,
			border : true,
			queryParams : {
				param : {
					categoryIds :'',
    				name : ''
				}
			},
    	    columns:[[
    	    	{field:'ck',checkbox:true},
    	    	{field:'id',hidden:true},
    	    	{field:'serverUrl',hidden:true},
    			{field:'realUrl',title:'图片',width:110,
    	    		formatter:function(value,row,index){
    	    			if(value){
    	    				return '<img src="'+value+'" style="width:100px;height:100px;"/>';
    	    			}else{
    	    				if(row.serverUrl){
    	    					return '<img src="'+row.serverUrl+'" style="width:100px;height:100px;"/>';
    	    				}
    	    				return  '<img src="/ocs/upload/image/'+row.id+'" style="width:100px;height:100px;"/>';
    	    			}
    	    		}
    			},
    			{field:'name',title:'名称',width:280},
    			{field:'fileSize',title:'大小',width:100},
    			{field:'uploadDate',title:'上传时间',width:130}
    	    ]],
			toolbar: "#ebayImgListTb"
    	});*/
    	$('#ebayImgListPage').pagination({
    		onSelectPage:function(pageNumber, pageSize){
    			
    			$(this).pagination('loading');
    			page.uploadSearch(pageNumber,pageSize);
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
            					return  '<a href="javascript:void(0);" class="easyui-linkbutton" onClick="page.uploadRemove('+index+')" data-options="iconCls:\'icon-delete\'">删除</a>' 
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
                	
                		page.uploadSearch(1,50);
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
        	page.uploadSearch(1,50);
        });
        $("#ebayImgListGrid").on("click",".image-del-btn",function(){
        	var id = $(this).parents(".image-box").find("input").val();
        	ocs.ajax({
    			type:"POST",
    			url :"/upload/imageDelete?ids="+id,
    			data:"",
    			success:function(data){
    				if(data.data){
    					page.uploadSearch(1,50);
    				}
    			}
    		});
        });
        $("#imagListDelete").click(function(){
        	var rows = page.getImageChecked();
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
        					page.uploadSearch(1,50);
        				}
        			}
        		});
        	}else{
        		$.messager.alert('提示','请您先选择要删除的图片！');
        	}
        });
        $("#imagesChooseOk").on("click",function(){
        	var rows = page.getImageChecked();
        	if(rows){
        		if($("input[name='selectImg']").val() == "eBay"){
        			var imgHtml = [];
            		$.each(rows,function(){
            			if(this.realUrl){
            				imgHtml.push(self.imgLi(this.realUrl));
            			}else{
            				imgHtml.push(self.imgLi("/ocs/upload/image/"+this.id));
            			}
            		});
                    $(".eBayPicture").append(imgHtml.join(""));
                }else if($("input[name='selectImg']").val() == "teml"){
                	var imgHtml = [];
            		$.each(rows,function(){
            			if(this.serverUrl){
            				imgHtml.push(self.imgLi(this.serverUrl));
            			}else{
            				imgHtml.push(self.imgLi("/ocs/upload/image/"+this.id));
            			}
            		});
                    $(".templatePicture").append(imgHtml.join(""));
                }else{//多属性图片
                	var url = "";
                	if(rows[0].realUrl){
                		url = rows[0].realUrl;
                	}else{
                		url = "/ocs/upload/image/"+rows[0].id;
                	}
                	$("#"+ul).html(self.imgLi(url));
                	var isAdd = true;
                	$.each(curMoreAttr.pictures,function(){
                		if(curSelectedName == this.VariationSpecificName&&curSelectedValue == this.VariationSpecificValue){
    	    				this.PictureURL = url;
    	    				isAdd = false;
    	    				return false;
    	    			}
                	});
                	if(isAdd){
                		var pic = {};
                		pic["VariationSpecificName"] = curSelectedName;
                		pic["VariationSpecificValue"] = curSelectedValue;
                		pic["PictureURL"] = url;
                		curMoreAttr.pictures.push(pic);
                	}
                }
        		$('#selectImgModel').dialog('close');
        	}
        	
        });
        //模板图片
        //选择图片
        $(".tempSelectImg .selectImg").on("click",function(){
        	
           // self.layerOpen("96%","90%","#selectImgModel");
        	//$("#ebayImgListGrid").datagrid("reload");
        	$("#picCategoryTreegrid").tree("reload");
        	page.imageListLoad();
        	$('#selectImgModel').dialog('open').dialog('center').dialog('setTitle','选择图片');
            $("input[name='selectImg']").val("teml");
        });

        //点击图片上的选择按钮
        $(".selectImgModel").on("click",".ui-icon-trash",function(){
            var imgUrl = $(this).siblings(".small_img").find("img").attr("src");
            var header = $(this).siblings("h5").html();
            $("#trash").find("ul").append(self.selectedImgLi(header,imgUrl,true));
            $(this).parents("li").remove();
        });

        //点击已选图片中的取消按钮
        $(".selectImgModel").on("click",".ui-icon-refresh",function(){
            var imgUrl = $(this).siblings(".small_img").find("img").attr("src");
            var header = $(this).siblings("h5").html();
            $("#gallery").append(self.selectedImgLi(header,imgUrl,false));
            $(this).parents("li").remove();
        });

        //点击选择图片确认
        $(".selectImgModel input[value='确定']").on("click",function(){
            $("#trash li").each(function(){
                var imgUrl = $(this).find("img").attr("src");
                if($("input[name='selectImg']").val() == "eBay"){
                    $(".eBayPicture").append(self.imgLi(imgUrl));
                }else if($("input[name='selectImg']").val() == "teml"){
                    $(".templatePicture").append(self.imgLi(imgUrl));
                }else{//多属性图片
                	$("#"+ul).append(self.imgLi(imgUrl));
                }
            });
            layer.closeAll();
        });

        //点击table中移除图片按钮
        $(".varselectpicture ").on("click",".delete",function(){
            $(this).parents("li").remove();
        });
        //绑定右键设置橱窗图片功能 eBayPicture
        var curImgLi = ""
        $(".eBayPicture").on('contextmenu','li',function(e){
        	curImgLi = this;
        	e.preventDefault();
			$('#imgSetMainMenu').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
		});
        $("#imgSetMainOpt").on('click',function(){
        	$(".eBayPicture").prepend(curImgLi.outerHTML);
        	$(curImgLi).remove();
        });
        //eBay 物品描述
        //使用外部图片 弹出层
        $(".eBaySelectImg .useExternalImg").on("click",function(){
            self.layerOpen("650px","500px",".useExternalImgModel");
            $("input[name='selectImg']").val("eBay");
            //清空原来数据
            $(".ecternalImgUl li").each(function(){
            	var liClass = $(this).attr("class");
            	if(liClass != "add_ecternalImg"){
            		$(this).remove();
            	}
            });
            
            $(".eBaySelectImg img").each(function(){
                var imgUrl = $(this).attr("src");
                if(imgUrl !== ""){
                	$(".add_ecternalImg").before(self.ecternalImgLi(imgUrl));
                }  
            });
           
        });

        //模板图片
        //使用外部图片 弹出层
        $(".tempSelectImg .useExternalImg").on("click",function(){
            self.layerOpen("650px","500px",".useExternalImgModel");
            $("input[name='selectImg']").val("teml");
            //清空原来数据
            $(".ecternalImgUl li").each(function(){
            	var liClass = $(this).attr("class");
            	if(liClass != "add_ecternalImg"){
            		$(this).remove();
            	}
            });
            $(".tempSelectImg img").each(function(){
                var imgUrl = $(this).attr("src");
                if(imgUrl !== ""){
                	$(".add_ecternalImg").before(self.ecternalImgLi(imgUrl));
                }  
            });
        });


        //使用外部图片 输入图片地址input失去焦点后,显示图片
        $(".ecternalImgUl").on("blur","input[name='ecternalImg']",function(){
            //将input的value值赋给img的src
            $(this).parents().siblings().children(".ecternalImg").attr("src",$(this).val());
        });

        //使用外部图片 点击添加
        $(".add_ecternalImg").on("click",function(){
            $(".add_ecternalImg").before(self.ecternalImgLi());
        });

        //使用外部图片 点击确定 
        $(".useExternalImgModel input[value='确定']").on("click",function(){
        	//遍历已上传的外部图片
            $(".ecternalImgUl input[name='ecternalImg']").each(function(){
                var imgUrl = $(this).val();
                if(imgUrl !== ""){
                    if($("input[name='selectImg']").val() == "eBay"){
                        $(".eBayPicture").append(self.imgLi(imgUrl));
                    }else{
                        $(".templatePicture").append(self.imgLi(imgUrl));
                    }  
                }  
            });
            layer.closeAll();
        });

        //eBay 物品描述
        //清空所选图片
        $(".eBaySelectImg .emptyImg").on("click",function(){
            $(".eBayPicture").empty();
        });

        //模板图片
        //清空所选图片
        $(".tempSelectImg .emptyImg").on("click",function(){
            $(".templatePicture").empty();
        });
        //copyTempImgs
        $(".eBaySelectImg .copyTempImgs").on("click",function(){
        	$(".eBayPicture").html( $(".templatePicture").html());
        });
        //copyEbayImgs
        $(".tempSelectImg .copyEbayImgs").on("click",function(){
        	$(".templatePicture").html( $(".eBayPicture").html());
        });
        
        //zTree
        var setting = {
            view: {
                dblClickExpand: false,
                showLine: true,
                selectedMulti: false,
                showIcon: false
            },
            data: {
                simpleData: {
                    enable:true,
                    idKey: "id",
                    pIdKey: "pId",
                    rootPId: ""
                }
            },
            callback: {
                beforeClick: function(treeId, treeNode) {
                    var zTree = $.fn.zTree.getZTreeObj("tree");
                    if (treeNode.isParent) {
                        zTree.expandNode(treeNode);
                        return false;
                    } else {
                        demoIframe.attr("src",treeNode.file + ".html");
                        return true;
                    }
                }
            }
        };
        var nodes = [
            {name: "My Folder",open: true,children: [
                {name: "LEUK",open: true,children:[
                    {name: "EDM"}
                ]},
                {name: "LE US"},
                {name: "LE DE"},
                {name: "US高压灯条"},
                {name: "US站点",open: true,children:[
                    { name: "2700005"}
                ]}
            ]},
            {name: "My Folder2",open: true,children: [
                {name: "LEUK",open: true,children:[
                    {name: "EDM"}
                ]},
                {name: "LE US",value:"1"},
                {name: "LE DE",value:"2"},
                {name: "US高压灯条",value:"3"},
                {name: "US站点",open: true,children:[
                    { name: "2700005"}
                ]}
            ]},
           
        ];
        $(document).ready(function(){
            var t = $("#tree");
            t = $.fn.zTree.init(t, setting, nodes);
        });


        //拍卖
        //点击添加
        $("#hrefMarkdownAdd").on("click",function(){
            self.layerOpen("96%","90%",".addDiscountModel");
        });
        //日历插件
        $('#datetimepicker1').datetimepicker({  
            format: 'YYYY-MM-DD hh:mm'
        });  
        $('#datetimepicker2').datetimepicker({  
            format: 'YYYY-MM-DD hh:mm'
        });
        //点击弹出层 确定 
        $(".addDiscountModel input[value='确定']").on("click",function(){
            layer.closeAll();
        });
        //单独物品 批量物品
        $(".proNumSelect").on("change",function(){
            if($(this).val() == 0){
                $(".base").show();
                $(".proNum").hide();
            }else{
                $(".base").hide();
                $(".proNum").show();
            }
        });
        
        
        //付款
        //付款说明
        $("#txtPaymentInstructions").on("input propertychange",function(){
            if($(this).val().length>0){
                $(".paymentNum").html($(this).val().length);
            }
            $(".paymentNum").html($(this).val().length);
        });

        //退货政策
        $("#txtReturnPolicy").on("input propertychange",function(){
            if($(this).val().length>0){
                $(".returnsPolicyNum").html($(this).val().length);
            }
            $(".returnsPolicyNum").html($(this).val().length);
        });
        //不运送地区
        $("#ddlExclusionListType").on("change",function(){
            if($(this).val() == 2){
                $("#dlEeclusionList").show();
            }else{
                $("#dlEeclusionList").hide();
            };
            
        });
        

        //其它
        //删除标签
        $(".profile_tag").on("click",".delete_profile",function(){
            $(this).parents(".profile_tag_span").remove();
        });
        
        /*******************************选择、另存为 start*******************************************/
        //0 物品描述  1 支付方式  2 买家要求 3 退货政策 4 物品所在地  5 运输选项
        //article comment dialog
       
        $("#articleCommentSelect").on('click',function(){
        	self.saveAsList(siteIdAll,0,$(this));
    	});
        //article comment save
    	$("#articleCommentSelectSave").on('click',function(){
    		$.messager.prompt('另存为', '请输入名称:', function(title){
    			if (title){
    				//save option
    				var commentModel = constructCommentModel();
    				commentModel["ebayImages"] = "";
    				commentModel["templateImages"] = "";
					page.saveAs(commentModel,0,title);
    			}
    		});
    	});
    	//物品属性选择
    	$("#itemPropertiesSelect").on('click',function(){
        	self.saveAsList(siteIdAll,6,$(this));
    	});
    	//物品属性另存为
    	$('#itemPropertiesSave').on('click',function(){
    		$.messager.prompt('另存为', '请输入名称:', function(title){
    			if (title){
    				//save option
    				var baseModel = contructBaseModel();
					page.saveAs(baseModel,6,title);
    			}
    		});
    	});
        //payment dialog
        $("#PaymentSelect").on('click',function(){
        	self.saveAsList(siteIdAll,1,$(this));
        	
    	});
        //payment save
    	$("#PaymentSelectSave").on('click',function(){
    		$.messager.prompt('另存为', '请输入名称:', function(title){
    			if (title){
    				//save option
					var paymentModel = contructPaymentModel();
					page.saveAs(paymentModel,1,title);
    			}
    		});
    	});
    	
        //buyerRequire dialog
    	$("#buyerRequireSelect").on('click',function(){
    		self.saveAsList(siteIdAll,2,$(this));
    	});
    	//buyer Require save
    	$("#buyerRequireSelectSave").on('click',function(){
    		$.messager.prompt('另存为', '请输入名称:', function(title){
    			if (title){
					var buyerModel = contructBuyerRequireModel();
    				page.saveAs(buyerModel,2,title);
    			}
    		});
    	});
    	
    	 //return policy dialog
    	$("#returnPolicySelect").on('click',function(){
    		self.saveAsList(siteIdAll,3,$(this));
    	});
    	//return policy save
    	$("#returnPolicySelectSave").on('click',function(){
    		$.messager.prompt('另存为', '请输入名称:', function(title){
    			if (title){
    				var returnPolicyModel = contructReturnPolicyModel();
    				page.saveAs(returnPolicyModel,3,title);
    			}
    		});
    	});
    	
    	//productPlaceSelect
    	$("#productPlaceSelect").on('click',function(){
    		self.saveAsList(siteIdAll,4,$(this));
    	});
    	//productPlace save
    	$("#productPlaceSelectSave").on('click',function(){
    		$.messager.prompt('另存为', '请输入名称:', function(title){
    			if (title){
    				var productPlaceModel = contructProductPlaceModel();
    				page.saveAs(productPlaceModel,4,title);
    			}
    		});
    	});
    	
    	//transportChoose Select
    	$("#transportChooseSelect").on('click',function(){
    		self.saveAsList(siteIdAll,5,$(this));
    	});
    	
    	
    	var areaSelect = function(){
    		var returnData = undefined;
    		var international=  $(".international").find(".subRegion content");
    		var Ul = $(".international").find(".content ul");
    		$.ajax({
    			url:"/ocs/assets/app/json/internationalArea.json",
    			type:"get",
    			data:"",
    			async:false,
    			success:function(data){
    					for(var i in data){
    						returnData = data;
    						for(var j = 0;j<data[i].length;j++){
    							var index = data[i][j].code;
    							var name = 
    								"<li>" +
    									"<input type='checkbox' id='"+data[i][j].id+"' value='"+data[i][j].value+"'>"+
    									"<label style='padding-left:5px;' for='"+data[i][j].id+"'>"+data[i][j].country +"</label>"+
    								"</li>"
    							Ul.eq(index).append(name)		
    						}
    					}
    			},
    			error:function(){
    			}
    		})
    		return returnData;
    	};
    	var areaNoship = function(){
    		var domestic=  $(".domestic").find(".domesticChoose .subRegion");
    		$.ajax({
    			url:"/ocs/assets/app/json/Domestic.json",
    			type:"get",
    			data:"",
    			async:false,
    			success:function(data){
    					for(var i in data){
    						for(var j = 0;j<data[i].length;j++){
    							var index = data[i][j].code;
    							var name = 
    								"<input style='padding-right:5px;line-height:20px;' type='checkbox' id='"+data[i][j].value+"' value='"+data[i][j].value+"'>"+
    								"<label style='padding-right:5px;line-height:20px;' for='"+data[i][j].value+"'>"+data[i][j].areaName+"</label>"
    							domestic.eq(index).append(name)		
    						}
    					}
    			},
    			error:function(){
    			}
    		})
    	};
//    	不运送地区弹出框，点击保存
    	$("#noshipAreaSave").click(function(){
    		 layer.closeAll();
    		 
    		 if($("#noShip span").length>0){
    			 $(".noArray-noSelected").hide();
    			 $(".noArray-selected").show();
    			 $(".noArray-selected #noArriveRegion").html($("#noShip").html());
    		 }else{
    			 $(".noArray-noSelected").show();
    			 $(".noArray-selected").hide();
    			 $(".noArray-selected #noArriveRegion").html("");
    		 }
    		 noShip.showSelected();
    		 $("#noArriveRegion").html($("#noShip").html());
    	});
    	$(".noArray-cancelSelected").on("click",function(){
    		$("#_MessageRow_0").find("input:checkbox").attr("checked",false);
    		 $(".noArray-noSelected").show();
			 $(".noArray-selected").hide();
			 $(".noArray-selected #noArriveRegion").html("");
    	});
//    	点击 创建不运送地区
    	$("#allCreate").click(function(){
//    		self.saveAsList();
    		$(".domestic").find(".domesticChoose .subRegion").html("");
    		var sideIdText=$("#site").combobox('getValue');
    		$("#Packstationen2").hide();
    		$("#divAdditionalLocations").show();
    		if(sideIdText==0||sideIdText==3||sideIdText==77){
    			areaNoship()
    		}
//    		$(".domesticChoose").hide();
//    		divAdditionalLocations的值
    		stateChange($("#site"));
    		if(!$(".international").find(".content ul").html()){
    			areaSelect();
    		}
    		$('#noShipDialog').window('open');
    		self.layerOpen("890px","690px",".noShipChooseWin");
    		$("#noShip").html($("#noArriveRegion").html());
    		if($("#noShip span").length<=0){
    			$(".selected").hide();
    			$(".noSelected").show();
    		}else{
    			$(".selected").show();
    			$(".noSelected").hide();
    		}
    		var selectedNoship = $("#noShip").find("span");
    		var noShip2 = [];
//    		判断选中的模板是否已经有编辑过不运送地区，有的话将选中的地区的的checkbox变为checked
    		if($(this).text()!==""&&!null){
    			$("input").attr("checked",false);
    			$("#noShip span").each(function(){
    				var checkedLabel = $("label:contains('"+$(this).text()+"')");
    				checkedLabel.prev("input").attr("checked",true);
    				checkedLabel.parents(".content").show();
    				checkedLabel.parents(".content").prev(".subRegion").find(".showAll").text("隐藏所有国家")
    			})
    		}
//    		判断是否是选中
    		$(".showAll").each(function(){
    			if($(this).parents(".subRegion").find("input:first").is(":checked")){
    				$(this).parents(".subRegion").next(".content").find("input").attr("checked",true);
    			}
    		})
    		
    	})
    	
    	//transportChoose save
    	$("#transportChooseSelectSave").on('click',function(){
    		$.messager.prompt('另存为', '请输入名称:', function(title){
    			if (title){
    				var transChooseModel = transModel();//运输选项
    				page.saveAs(transChooseModel,5,title);
    			}
    		});
    	});
    	 /*******************************选择、另存为 end*******************************************/
    	$("#oldDescriptionView").click(function(){
    		var templateId = $('#publicationId').val();
    		page.layerOpen("1350px","690px",".templateViewModel");
    		if(oldDesciprionData){
    			$(document.getElementById('templateViewDiv').contentWindow.document.body).html(oldDesciprionData);
    		}
    	});
    	
    	//模板预览
    	$("#btnPreviewTemplate").click(function(){
    		page.layerOpen("1350px","690px",".templateViewModel");
    		ocs.ajax({
    			url:"/publication/templateView",
    			type:"post",
    			data:getPageData(),
    			success:function(returns){
    				//$("#templateViewDiv").html(returns.data);
    				$(document.getElementById('templateViewDiv').contentWindow.document.body).html(returns.data);
    			}
    		});
    	});
    	
    	//检查ebay费
    	$("#btnCheckeBayFee").on('click',function(){
    		var data = getPageData();
    		ocs.ajax({
    			url:'/Item/verifyItem',
    			data: data,
    			async:true,
    		    beforeSend: function () {
                       $.messager.progress({
                           title: '请稍后',
                           msg: '正在检查费用中...'
                       });
                   },
               complete: function () {
                   $.messager.progress('close');
               },
    			type: "POST",
    			success: function(result) {
    				//ebayImages : "",
    				//templateImages : "",
    				var ebayImages = data["ebayImages"];
    				var templateImages = data["templateImages"];
    				if(ebayImages.indexOf("/ocs/upload/image") > -1 || templateImages.indexOf("/ocs/upload/image") > -1){
    					//刷新页面
    					var newData = result.data;
    					ebayImages = newData["ebayImages"];
    					templateImages = newData["templateImages"];
    					
    					var imgs = ebayImages;
    					var imgListHtml = [];
    					if(imgs){
    						var imgUrls = imgs.split(",");
    						$.each(imgUrls,function(){
    							//$(".eBayPicture").append(imgLi(this));
    							imgListHtml.push(imgLi(this));
    						});
    						$(".eBayPicture").html(imgListHtml.join(""));
    					}
    					imgs = templateImages;
    					imgListHtml = [];
    					if(imgs){
    						var imgUrls = imgs.split(",");
    						$.each(imgUrls,function(){
    							//$(".templatePicture").append(imgLi(this));
    							imgListHtml.push(imgLi(this));
    						});
    						$(".templatePicture").html(imgListHtml.join(""));
    					}
    					
    				}
				     $.messager.alert({
				    	 title:'消息',
				    	 msg:result.description,
				    	 width:'600px',
				    	 height:'500px'
				    	 
				     });
    			},
    			error: function(jqXHR, textStatus, errorThrown) {
    				$.messager.alert("信息", "服务器发生错误！");		
    			}
    	   });
    	});
    },
    //弹出层方法
    layerOpen: function(w,h,className){
        layer.open({
            type: 1,
            title: false,
            area: [w,h],
            shade: 0.5,
            resize : false,
            closeBtn: 0,
            shadeClose: true, //点击遮罩关闭
            content: $(className),
            success: function(index, layero){
                $('.cancel').on("click",function(){
                    layer.close(layer.index); 
                });
                
            }
        });
    },
    //右侧导航事件
    showAnchor: function(obj){
        $("html,body").animate({ scrollTop: $("#" + obj).offset().top-10 }, 1000);
        $("#AccordionContainer_" + obj).show();
        $("#" + obj).next().show();
        $("#AccordionMsg_" + obj).hide();
    },
    showAnchor2: function(obj){
        $("html,body").animate({ scrollTop: $("#" + obj).offset().top - 10 }, 1000);
    },
    loadmenu: function(){
        $(window).bind("scroll", function () {
            var e = $("#floatnav").offset().top, t = 0;
            e = e + 10;
            e < $("#tabbox1").offset().top ? $("#floatnav").fadeIn() :
          /*  e < $("#tabbox2").offset().top ? (t = 0, $("#floatnav").fadeIn()) :*/
            e < $("#tabbox3").offset().top ? t = 1 :
            e < $("#tabbox4").offset().top ? t = 2 :
            e < $("#tabbox5").offset().top ? t = 3 :
            e < $("#tabbox6").offset().top ? t = 4 :
            e < $("#tabbox7").offset().top ? t = 5 :
            e < $("#tabbox8").offset().top ? t = 6 :
            e < $("#tabbox9").offset().top ? t = 7 :
            e < $("#tabbox10").offset().top ? t = 8 :
            e < $("#tabbox11").offset().top ? t = 9 :
            e < $("#tabbox12").offset().top ? t = 10 :
            e < $("#tabbox13").offset().top ? t = 11 :
            e < $("#tabbox14").offset().top ? t = 12 :
            e < $("#tabbox15").offset().top ? t = 13 :
            e < $("#tabbox16").offset().top ? t = 14 :
            e < $("#tabbox17").offset().top ? t = 15 :
            e < $("#tabbox18").offset().top ? t = 16 : t = 17, $("#floatnav p").removeClass("floatnav_a_cur").eq(t).addClass("floatnav_a_cur");
        });
    },
    //物品标题input框中文本长度的显示
    showTextLength: function(obj,len){//obj:当前的input;len:限制字符长度
        if($(obj).val().length>0){
            $(obj).siblings("span").show().html('('+$(obj).val().length+'/'+len+')');
        }
        $(obj).siblings("span").html('('+$(obj).val().length+'/'+len+')');
    },
    //分类 选择分类弹出层动态添加模板
    selectCategoryLi: function(option){
        var li = '<li>'+
                    '<select size="4" class="select marginright">'+option+
                    '</select>'+
                '</li>';
        return li;
    },
    //点击分类中的选择分类后，初始化弹出层
    selectCategoryInit: function(category,url,data){
        var self = this;
        self.getData(url,data,function(res){
            var option = "";
            res = eval(res);
            for(var i = 0;i<res.length;i++){
            	option += '<option value='+res[i].category_id+'>'+res[i].name+'</option>';
            }
            $("#select_category ul").html(self.selectCategoryLi(option));
            $(".dialog_button input[name='category']").val(category);
        },function(){
            //errorCallback();
        });
        self.layerOpen("890px","690px",".selectCategoryModel");
    },
    
    //物品属性动态添加模板
    prospecificsTr: function(isAdd,name,valueUrl){//isAdd:(true,false)是否为添加新的物品属性;prospecifics:当前所选的物品属性值
    	var viewName = name;
    	name = name.sTrim();
    	var prospecificsTr = '<tr class="linerow">'+
	        						'<td>'+
	        							'<a class="prospecificsremove" href="javascript:void(0);">X</a>'+
    								'</td>'+
									'<td>'+
							            (isAdd?'<input type="text" value="" maxlength="40" class="form-control">':
							            '<span><font color="green">'+viewName+'</font></span>')+
							        '</td>'+
							        '<td>' +
							        	'<div class="varcontrol">'+
						        		'<input type="text"  id="productAttrValue'+name+'" style="width:282px;" />'+
					        			'</div></td></tr>';
    		$(".specificslist tbody").append(prospecificsTr);
    		if(isAdd){
    			attrFlage ++;
    			return;
    		}
    		//$.parser.parse('.specificslist');
    		$('#productAttrValue'+name).combo({
    		    multiple:true,
    		    editable:true
    		});
    		//获取属性值
    		ocs.ajax({
    			url:valueUrl,
    			data:"",
    			success : function(result){
    				if(result){
    					var options = [];
    					options.push('<div style="padding:10px" id="productAttrValueList'+name+'"> ');
    					options.push('<div class="writeSelf2"><span style="color:#99BBE8;background:#fafafa;padding:5px;">Add your self</span><a href="javascript:void(0)" class="addNewbtn">+</a></div>');
    					var MaxValues = undefined;
    					$.each(result,function(){
    						if(MaxValues == undefined){
    							MaxValues = this.MaxValues; 
    						}
    						options.push('<div><input type="checkbox" name="'+name+'" value="'+this.value+'"><span>'+this.value+'</span></div>');
    					});
    					options.push('</div>');
    					$(options.join("")).appendTo($('#productAttrValue'+name).combo('panel'));
    					$("#productAttrValueList"+name).on("click",".addNewbtn",function(){
    						$(this).parent("div").after('<div><input type="checkbox" class="addAttr" name="'+name+'" value=""><span><input type="text" /></span></div>');
    					});
    					//物品属性，手动添加值;
    					var addPropretype = function(selectOptions,starVal){
		    				var oldSelect = $("#productAttrValueList"+name).find("input");
		    				oldSelect.attr("checked",false);
		    				$("#productAttrValueList"+name).find("div").not(".writeSelf2").remove(); 
		    				var newOption ="" ;
		    				for(var i = 0;i<oldSelect.length;i++){
		    					var oldSelectVal = oldSelect[i].value;
		    					selectOptions.push(oldSelectVal)
		    				}
		    				var newArr = [];
	    			        for (var i = 0; i < selectOptions.length; i++) {
	    			            if (newArr.indexOf(selectOptions[i]) == -1 ) {
	    			            	newArr.push(selectOptions[i]);
	    			            }
	    			        }
		    				for(var i=0;i<newArr.length;i++){
		    					if($.trim(newArr[i]).length>0){
	    							newOption = '<div><input type="checkbox" name="'+name+'" value="'+$.trim(newArr[i])+'"/>'
								 					+'<span>'+$.trim(newArr[i])+'</span>'
										 			+'</div>';
	    							$("#productAttrValueList"+name).append(newOption);
		    					}
		    				}
		    				for(var j=0;j<starVal.length;j++){
		    					$("#productAttrValueList"+name).find("input[value='"+starVal[j]+"']").attr("checked",true)
	    					}
		    				
    					}
    					$('#productAttrValue'+name).combobox('textbox').bind('blur',function(e){
    						var starVal=e.currentTarget.value.split(",")
    						var selectOptions = e.currentTarget.value.split(",")
    						addPropretype(selectOptions,starVal);
    		    		});
    		    		$('#productAttrValue'+name).combobox('textbox').bind('focus',function(e){
    		    			var starVal=e.currentTarget.value.split(",")
    						var selectOptions = e.currentTarget.value.split(",")
    						addPropretype(selectOptions,starVal);
    		    		});
    					
    					$("#productAttrValueList"+name).on("change","input[type=checkbox]",function(){
    						var text = $('#productAttrValue'+name).combo('getText');
    						if($(this).is(':checked')){
    							var curValue = $(this).val();
    							if(ocs.stringIsNull(curValue)){
    								curValue = $(this).next().find("input").val();
    							}
    							var curInput = this;
    							if(MaxValues > 1){
    								//清空选择
    								$('input[name="'+name+'"]:checked').each(function(){
    									$(this).attr("checked",false);	
    								});
    								$(curInput).attr("checked",true);
    								$('#productAttrValue'+name).combo('setText',curValue);
    							}else{
    								if(text){
        								$('#productAttrValue'+name).combo('setText',text+","+curValue);
        							}else{
        								$('#productAttrValue'+name).combo('setText',curValue);
        							}
    							}
    							
    						}else{
    							if(MaxValues == 1){
    								$('#productAttrValue'+name).combo('setText',"");
    							}else{
    								var replaceText = [];
        							$('input[name="'+name+'"]:checked').each(function(){
    									var curValue = $(this).val();
    	    							if(ocs.stringIsNull(curValue)){
    	    								curValue = $(this).next().find("input").val();
    	    							}
    									replaceText.push(curValue);
    								});
    								$('#productAttrValue'+name).combo('setText',replaceText.join(","));
    								$('#productAttrValue'+name).combo('resize');
    							}
    							
    						}
    					});
    				}
    			}
    		});                
    },
    ajax:function(){
    	
    	   $(".specificslist tbody").on("click",'.varcontrolMore',function(){
    	       $(this).parent().find(".controlMoreVal").show();
    	        
    	     })
    	 },
    addtext:function(){
    	  $(".specificslist tbody").on("click",".controlMoreVal option",function(){
    	   
    	   $(this).parents(".varcontrol").find(".form-control").val($(this).text());
    	   $(this).parent(".controlMoreVal").hide();
    	 });
    },
    //eBay图片 模板图片 动态添加li模
    imgLi: function(imgUrl){
        var imgLi = '<li class="pa ui-widget">'+
                        '<div>'+
                            '<a rel="example_group" target="_blank">'+
                                '<img src='+imgUrl+'>'+
                            '</a>'+
                        '</div>'+
                        '<span class="delete">X</span>'+
                    '</li>';
        return imgLi;
    },
    //已选图片弹出层 动态添加li模板
    selectedImgLi: function(header,imgUrl,isSelect){
        var selectedImgLi = '<li class="ui-widget-content ui-corner-tr ui-draggable ui-draggable-disabled">'+
                            '<h5 class="ui-widget-header">'+header+'</h5>'+
                            '<a class="gallery_img small_img" rel="example_group"><img src='+imgUrl+'></a>'+
                            (isSelect?'<a class="ui-icon-refresh">X</a>':'<a class="ui-icon-trash">选择</a>')+
                            '</li>';
        return selectedImgLi;
    },
    //使用外部图片弹出层 动态添加li模板
    ecternalImgLi: function(url){
    	if(!url){
    		url = "";
    	}
        var ecternalImgLi = '<li>'+
                                '<div>'+
                                    '<img class="ecternalImg" src="'+url+'"/>'+
                                '</div>'+
                                '<div>'+
                                    '<label>URL</label>'+
                                    '<input type="text" name="ecternalImg"  value="'+url+'"/>'+
                                '</div>'+
                            '</li>';
        return ecternalImgLi;
    },
    saveAsList:function(siteId,type,_this){
    	//saveAsListGrid
    	 if(ocs.stringIsNull(siteIdAll)){
    		 siteId = $('#site').combobox("getValue");
         }
    	if(ocs.stringIsNull(siteId)){
    		$.messager.alert('提示','请先选择站点!','warning');
    		return;
    	}
    	page.layerOpen("890px","550px",".saveAsListModel");
//    
    	var  titleText=  _this.parents(".model_title").find(".caption span").text();
    	$(".saveAsListModel #_Title_0").text(titleText);
    	$("#saveAsListGrid").datagrid({
    		height:450,
    		url:'/ocs/publication/saveAslist',
	        fitColumns:true,
	        queryParams:{
	        	param : {
	        		"dataType":type,
	        		"siteId" : siteId
	        	}
	        },
            columns: [
	            [
	                {field: 'title', title: '名称',align:'center',width:100},
	                {field: 'siteId', title: '站点',align:'center',width:100,
	                	formatter: function(value,row,index){
	                		var viewName = value;
	                		
	                		ocs.ajax({
	                			url: "/publication/getSiteList",
	                	        type: "post",
	                	        data:"",
	                	        success:function(data){
	                	        	$.each(data,function(){
	                	        		if(this.value == value){
	                	        			viewName = this.displayName;
	                	        			return false;
	                	        		}
	                	        	});
	                	        }
	                		});
	                		return viewName;
	                	}
	                },
	                {field: 'data', hidden:true},
	                {field: 'dataType', hidden:true},
	                {field: 'stock', title: '动作',width:80,align:'center',
	                	formatter: function(value,row,index){
	        				return '<a href="javascript:void(0);" style="margin:0 15px;border-bottom:1px solid #002398;"  class="easyui-linkbutton" onclick="page.chooseSaveAs('+index+');" data-options="plain：true">选择</a>'
	        				+'<a href="javascript:void(0);" style="margin:0 15px;border-bottom:1px solid #002398;" class="easyui-linkbutton" onclick="page.deleteDate('+row.id+');" data-options="plain：true">删除</a>';
	        			}
	                }
	            ]
	        ],
	        idField: 'id',
	        singleSelect: true,
	        rownumbers: true,
	        pagination: true,
	        pageSize: 50,
	        border:true
    	});
    	$.parser.parse('#saveAsListGrid'); 
    },
    saveAs:function(saveData,type,title){
    	if(ocs.stringIsNull(siteIdAll)){
    		siteIdAll = $('#site').combobox("getValue");
        }
    	if(ocs.stringIsNull(siteIdAll)){
    		$.messager.alert('提示','请先选择站点!','warning');
    		return;
    	}
    	var saveAsMode = {};
    	saveAsMode["data"] = JSON.stringify(saveData);
    	saveAsMode["title"] = title;
    	saveAsMode["siteId"] = siteIdAll;
    	saveAsMode["dataType"] = type;
		ocs.ajax({
			url:"/publication/saveAs",
	        type: "post",
	        data:saveAsMode
		});
    },
    //选择方式
    chooseSaveAs:function (index){
    	$("#saveAsListGrid").datagrid("selectRow",index);
    	var row =$("#saveAsListGrid").datagrid("getSelected");
    	var dataType = row.dataType;
    	var data = eval('('+row.data+')');
    	//0 物品描述  1 支付方式  2 买家要求 3 退货政策 4 物品所在地  5 运输选项 6物品属性
    	//根据不同类型，填充相应模块数据
    	if(data){
    		if(dataType == 0){
        		renderCommentModel(data);
        	}else if(dataType ==1){
        		renderPaymentModel(data);
        	}else if(dataType ==2){
        		renderBuyerRequireModel(data);
        	}else if(dataType ==3){
        		renderReturnPlicyModel(data);
        	}else if(dataType ==4){
        		renderProductPlaceModel(data);
        	}else if(dataType ==5){
        		renderSaveAsData(data);
        	}else if(dataType ==6){
        		$(".recospecifics .specificslist").find("tbody").html("");
        		rendarBasePropertie(data);
        	}
    	}
    	layer.closeAll();
    },
    uploadRemove : function(index,name){
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
    },
    uploadSearch :function(page,rows){
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
		param["name"] = $("#imagesListSearchName").textbox("getValue");
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
    },
    imageListLoad : function(){
    	//重新加载数据
    	//$("#picCategoryTreegrid").tree("reload");
    	page.uploadSearch(1,50);
    },
    getImageChecked : function(){
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
};
page.init();
page.ajax();
page.addtext();
