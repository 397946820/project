var contructBaseModel = function(){
	var baseInfoModel = {
			templateName:'dasfdasfdsaf',
			ebayAccount:'le.deutschland',
			siteId:'4',
			publicationType:'paimai',
			sku:'dasfdasf',
			productTitle:'',
			productSubtitle:'',
			productFirstCategoryId:'dasf',
			productSecondCategoryId:'dasf',
			storeFirstCategoryId:'dasf',
			storeSecondCategoryId:'dsaf',
			productStatus :'1000',
			productEAN:'',
			productISBN:'',
			productUPC:'',
			productProperties:'',
			variations : '',
			product_mpn:'Does not apply',
			product_brand:'Does not apply',
			product_ust:''
		};
	baseInfoModel.productFirstCategoryId = $('#productFirstCategory').val();
	baseInfoModel.productSecondCategoryId = $('#productSecondCategory').val();
	baseInfoModel.storeFirstCategoryId = $('#storeFirstCategory').val();
	baseInfoModel.storeSecondCategoryId = $('#storeSecondCategory').val();
	baseInfoModel.templateName = $('#baseInfoName').val();
	baseInfoModel.itemId = $('#baseInfoItemId').val();
	baseInfoModel.ebayAccount = $('#ebayAccount').combobox("getValue");;
	
	baseInfoModel.siteId = $('#site').combobox("getValue");
	baseInfoModel.sku = $('#productSKU').val();
	
	if($("#rbleBayListType1").attr("checked")){
		baseInfoModel.publicationType = $("#rbleBayListType1").val();
	}else if($("#rbleBayListType2").attr("checked")){
		baseInfoModel.publicationType = $("#rbleBayListType2").val();
	}else{
		baseInfoModel.publicationType = $("#rbleBayListType20").val();
	};
	baseInfoModel.productTitle = $("#itemTitle").val();
	baseInfoModel.productSubtitle = $("#itemSubtitle").val();
	baseInfoModel.productStatus = $("#itemCondition").attr("value");
	baseInfoModel.product_mpn=$("#productMpnId").val();
	baseInfoModel.product_brand=$("#productBrandId").val();
	baseInfoModel.product_ust=$("#productUinId").val();
	var type = $("label[name=productCode]").text();
	if(type){
		var type_value = $("#productEAN_ISBN_UPC").val();
		if(type == 'UPC'){
			baseInfoModel.productUPC= type_value;
		}else if(type == 'EAN'){
			baseInfoModel.productEAN = type_value;
		}else if(type == 'ISBN'){
			baseInfoModel.productISBN = type_value;
		}
	}
	var required=function(obj){
		if(obj.disable==false){
			obj.required=true;
		}
	}
	
	//获取物品属性
	var porpertis = [];
	$("#upItemSpecifics").find(".linerow").each(function(){
		var first = $(this).find(":first");
		var sencond = $(first).next();
		var third =$(sencond).next();
		var typeName = $(sencond).find("span").text();
		var typeValue = "";
		var inputs = $(third).find("input");
		if(inputs.length > 1){
			var idName = typeName.sTrim();
			idName = idName.replace("/",'');
			typeValue = $("#productAttrValue"+idName).combo("getText");//$(third).find("input").val();
		}else{
			typeName = $(sencond).find("input").val();
			typeValue = $(third).find("input").val();
		}
		
		if(typeValue){
			var typeValues = typeValue.split(",");
			var data = {};
			data[typeName] = typeValues;
			porpertis.push(data);
		}
	});
	baseInfoModel.productProperties = JSON.stringify(porpertis);
	var moreAttr = moreAttrModel(baseInfoModel.publicationType);
	if(null != moreAttr){
		baseInfoModel.variations = JSON.stringify(moreAttr);
	}

	return baseInfoModel;
}

var baseInfoSetValue = function(pubModel){
	$("#baseInfoName").val(pubModel.templateName);
	$("#baseInfoItemId").val(pubModel.itemId);
	//$("#ebayAccount").attr("value", pubModel.ebayAccount);
	$('#ebayAccount').combobox('setValue', pubModel.ebayAccount);
	$('#site').combobox("setValue",pubModel.siteId);
	$("#publictionType").val(pubModel.publicationType);
	if(pubModel.publicationType == "FixedPriceItem"){
		$("#rbleBayListType2").attr("checked",true);
	}else if(pubModel.publicationType == "Chinese"){
		$("#rbleBayListType1").attr("checked",true);
	}else{
		$("#rbleBayListType20").attr("checked",true);
	}
	//$("input[name='rbleBayListType'][value="+pubModel.publicationType+"]").attr("checked",true); 
	$("#productSKU").val(pubModel.sku);
	$("#itemTitle").val(pubModel.productTitle);
	$("#itemSubtitle").val(pubModel.productSubtitle);
	$("#productFirstCategory").val(pubModel.productFirstCategoryId);
	queryCategory(pubModel.productFirstCategoryId,1);
	$("#productSecondCategory").val(pubModel.productSecondCategoryId);
	queryCategory(pubModel.productSecondCategoryId,2);
	$("#storeFirstCategory").val(pubModel.storeFirstCategoryId);
	queryCategory(pubModel.storeFirstCategoryId,3);
	$("#storeSecondCategory").val(pubModel.storeSecondCategoryId);
	queryCategory(pubModel.storeSecondCategoryId,4);
	$("#ddlItemCondition").attr("value", pubModel.productStatus);
	$("#productMpnId").val(pubModel.product_mpn);
	$("#productUinId").val(pubModel.product_ust);
	$("#productBrandId").val(pubModel.product_brand);
	if(pubModel.productUPC != null){		
		 createUPC_ISBN_EAN("UPC",pubModel.productUPC);
	}else{
		if(pubModel.productEAN != null){
			createUPC_ISBN_EAN("EAN",pubModel.productEAN);
		}else{
			if(pubModel.productISBN != null){
				createUPC_ISBN_EAN("ISBN",pubModel.productISBNs);
			}
		}
	}
	if(pubModel.productProperties){
		var properties = eval('('+pubModel.productProperties+')');
		addProductProperties(pubModel.siteId,pubModel.productFirstCategoryId,properties);
	}
	if(pubModel.variations){
		moreAttrReset(pubModel.variations);
	}
}

var rendarBasePropertie = function(pubModel){
	
	if(pubModel.productProperties){
		var properties = eval('('+pubModel.productProperties+')');
		addProductProperties(pubModel.siteId,pubModel.productFirstCategoryId,properties);
	}
	if(pubModel.variations){
		moreAttrReset(pubModel.variations);
	}
}
function createUPC_ISBN_EAN(type,value){
	
	$("#productCode").text(type);
	$("#dlCatalog").show();
	$("#productEAN_ISBN_UPC").val(value);
}

function addProductProperties(siteId,productFirstCategoryId,properties){
	var selectName = "";
	//反选属性
	for(var m=0;m<properties.length;m++){
		var obj = properties[m];
		var typeName= "";
		var typeValue = "";
		for(var key in obj){
			typeName = key;
			typeValue = obj[key];
		}
		var valueUrl = "/CategorySpecificsV/internalFindSpecificsVByNameAanCIDAanMID?marketplace_id="+siteId+"&category_Id="+productFirstCategoryId+"&name="+typeName;
    	page.prospecificsTr(false,typeName,valueUrl);
    	//$.parser.parse('.specificslist');
    	var idName = typeName.sTrim();
    	$("#productAttrValue"+idName).combo("setText",typeValue);
    	//$("#productAttrValue"+typeName).next().find("input[type=text]").val(typeValue);
    	selectName = selectName + typeName+"-";
	}
	
	//初始化未选择属性
	$.ajax({	
			url: GLOBAL.domain+'/ProductListing/internalSelectProductListingByMIDOrCID',
			data: {marketplace_id:siteId,category_Id:productFirstCategoryId},
			dataType: "json",
			contentType: "application/json; charset=UTF-8",
			type: "get",
			success: function(result) {
				if(result){
	 				var jsonArray2 = eval(result.rows.rows);
	 				var resultString = [];
	 				if(jsonArray2){
	 					$.each(jsonArray2,function(){
	 						if(selectName.indexOf(this.name+"-") < 0){
	 							resultString.push('<span class="specifics">');
		 						resultString.push('		<a class="addself" href="javascript:void(0);">+</a>');
		 						resultString.push('		<span>'+this.name+'</span>');
		 						resultString.push('</span>');
	 						}
	 					});
	 				}
	 				$("#prospecificsList").html(resultString.join(""));
				}
           
			}
      });
}

var indexNum=0;
var probundskuTr = function(idNum){
    var probundskuTr = 
        '<tr>'+
            '<td>'+
                '<input id="bundleProductName_'+idNum+'" name="bundleProduct" type="text" class="form-control">'+
                '<span class="skustatus help-inline"></span>'+
                '<a class="select" style="display:none;">选择产品</a>'+
            '</td>'+
            '<td>'+
                '<input id="bundleProductQty_'+idNum+'" name="bundleProductQty" type="text" value="1" class="form-control">'+
            '</td>'+
            '<td>'+
                '<a class="remove" href="javascript:void(0);">移除</a>'+
            '</td>'+
        '</tr>';
    return probundskuTr;
}
var proTitleNum=0;
//物品标题动态添加模板
var proTitle = function(idNum) {
    var proTitle = 
        '<dl>'+
            '<dt>标题</dt>'+
            '<dd>'+
                '<input id="itemTitle_'+idNum+'" name="itemTitle" type="text" maxlength="80" class="form-control">'+
                '<span style="display:none;"></span>'+
                '<a class="select" href="javascript:void(0);" style="display:none;">移除</a>'+
            '</dd>'+
            '<dt>子标题</dt>'+
            '<dd>'+
                '<input id="itemSubTitle_'+idNum+'" name="itemSubtitle" type="text" maxlength="55" class="form-control">'+
                '<span style="display:none;"></span>'+
            '</dd>'+
        '</dl>';  
    return proTitle;                                           
}
var curMoreAttr = {
		variations :[],//{SKU:"",StartPrice:"",Quantity:"",NameValueList:[{Name:"",Value:""}]}
		pictures : [],//{VariationSpecificName:"",VariationSpecificValue:"",PictureURL:""}
		variationSpecificsSet:[]//{Name:"",Values:[]}
	};
var moreAttrReset = function(data){
	debugger
	var moreAttr = eval('('+data+')');
	curMoreAttr = moreAttr;
	var variationSpecificsSet = moreAttr.variationSpecificsSet
	if(variationSpecificsSet){
		var attr = [];
		$.each(variationSpecificsSet,function(){

    		addOption("ddlPictureVariationName",this.Name);
		});
	}
	var pictures = moreAttr.pictures;
	if(pictures){
		reloadImg(moreAttr,null);
	}
	var variations = moreAttr.variations;
	if(variations){
		var rows = [];
		var addhead = true;
		var upcOrEan = false;
		$.each(variations,function(){
			var row = [];
			row.push('<tr>');
			row.push('<td>'+lineNum+'</td>');
			lineNum++;
			row.push('<td><input type="checkbox" name="ck"/></td></td>');
			row.push('<td><input type="text"  name="SKU" value="'+this.SKU+'"/></td>');//sku
			if(this.UPC){
				row.push('<td><input type="text"  name="UPC" value="'+formatUndefind(this.UPC)+'"/></td>');//UPC
			}
			if(this.EAN){
				upcOrEan = true;
				row.push('<td><input type="text"  name="EAN" value="'+formatUndefind(this.EAN)+'"/></td>');//EAN
			}
			row.push('<td><input type="text"  name="StartPrice" value="'+formatUndefind(this.StartPrice)+'" style="width:50px" /></td>');//价格
			row.push('<td><input type="text"  name="Quantity" value="'+formatUndefind(this.Quantity)+'" style="width:50px" /></td>');//数量
			var nameValueList = this.NameValueList;
			$.each(nameValueList,function(){
				if(addhead){
					$("#moreAttrTableAddHead").before("<th head='"+this.Name+"'>"+this.Name+"<a class='delete headDelete' style='border: #f9f6f6 solid 1px;margin-left: 10px;'>X</a></th>");
				}
				row.push('<td><input name="'+this.Name+'" type="text" value=\''+formatUndefind(this.Value)+'\' style="width:100px" /></td>');
			});
			row.push('<td><a class="moreAttrValueDel">移除</a></td>');
			row.push('</tr>');
			rows.push(row.join(""));
			addhead = false; 
		});
		if(upcOrEan){
			$("#upcmpnCode").text("EAN");
         	$("#upcmpnCode").attr("head","EAN");
		}
		$(".moreAttrValueTable tbody").html(rows.join(""));
	}
}
function formatUndefind (v){
	if(v){
		return v;
	}else{
		return "";
	}
}
var moreAttrModel = function(type){
	if(type=="FixedPriceItem1"){
		var moreAttr = {
			variations :[],//{SKU:"",StartPrice:"",Quantity:"",NameValueList:[{Name:"",Value:""}]}
			pictures : [],//{VariationSpecificName:"",VariationSpecificValue:"",PictureURL:""}
			variationSpecificsSet:[]//{Name:"",Values:[]}
		};
		var newAttrValue = [];
		var newVariationSpecificsSet = [];
		//variations
		$(".moreAttrValueTable tbody").find("tr").each(function(){
			var variation = {};
			var NameValueList =[];
			$(this).find("input").each(function(){
				//num ck opt
				if(this.name == "num" || this.name =="ck" || this.name == "opt"){
					return true;
				}
				if(this.name != "SKU"&&this.name != "StartPrice"&&this.name != "Quantity"&&this.name != "UPC"&&this.name != "EAN"){
					var nameValue = {};
					nameValue["Name"] = this.name;
					var thisInputValue = $(this).val();
					nameValue["Value"] = thisInputValue;
					NameValueList.push(nameValue);
					if(thisInputValue){
						var keyValue = this.name+"_"+$(this).val();
						//初始化属性值
						var isAdd = true;
						$.each(newAttrValue,function(){
							if(this == keyValue){
								isAdd = false;
								return false;
							}
						});
						if(isAdd){
							newAttrValue.push(keyValue);
						}
					}
					
				}else{
					variation[this.name] = $(this).val();
				}
			});
			
			variation["NameValueList"] = NameValueList;
			moreAttr.variations.push(variation);
			
		});
		$.each(newAttrValue,function(){
			var isAdd = true;
			var str = this.split("_");
			$.each(newVariationSpecificsSet,function(){
				if(this.Name == str[0]){
					if(!arryDataExist(this.Values,str[1])){
						this.Values.push(str[1]);
					}
					isAdd = false;
				}
			});
			if(isAdd){
				var n = {};
				n["Name"] = str[0];
				n["Values"] = [str[1]];
				newVariationSpecificsSet.push(n);
			}
		});
		moreAttr.variationSpecificsSet = newVariationSpecificsSet;
		//pictures
		//清理已经删除不存在的属性
		var pictures = [];
		$.each(curMoreAttr.pictures,function(){
			var pname = this.VariationSpecificName;
			var pValue = this.VariationSpecificValue;
			var p = this;
			$.each(newVariationSpecificsSet,function(){
				var kname = this.Name;
				var kvalues = this.Values.toString();
				if(pname == kname && kvalues.indexOf(pValue) > -1){
					pictures.push(p);
				}
			});
		});
		
		moreAttr.pictures = pictures;
		return moreAttr;
	}
	return null;
}
changeWords("#itemTitle");
changeWords("#itemSubtitle");

function arryDataExist(arry,value){
	var e = false;
	$.each(arry,function(){
		if(this == value){
			e = true;
			return false;
		}
	});
	return e;
}

function addOption(id,name){
	var option = '<option value="'+name+'">'+name+'</option>';
	$("#"+id).append(option);
}
function removeOption(id,name){
	$("#"+id).find("option").each(function(){
		if($(this).text() == name){
			$(this).remove();
			return false;
		}
	});
}
var moreAttrImgNum = 1;
function getImagesTabString(value,imgli){
	var imageTab = [];
	imageTab.push('<div class="varselectpicture" style="width:792px;" >');
	imageTab.push('		<div class="vargallery" style="border-top: none;width:790px">');
	imageTab.push('			<p>');
	imageTab.push('				 <a class="moreAttrImgCheck" forId="moreAttrImgUl'+moreAttrImgNum+'">添加图片</a>');
	imageTab.push('				 <span style=" height: 31px;line-height: 31px;padding-left: 15px;color: green;">'+value+'</span>');
	imageTab.push('				 <u> 最多 1 张图片</u>');
	imageTab.push('			 </p>');
	imageTab.push('			 <ul class="ui-sortable " id="moreAttrImgUl'+moreAttrImgNum+'">'+imgli+'</ul>');
	imageTab.push('		</div>');
	imageTab.push('</div>');
	moreAttrImgNum++;
	return imageTab.join("");
}
function addImagesTabForMoreAttr(name){
	$("#moreAttrImagesList").html("");
	$(".attr_one_kind").each(function(){
		if($(this).find("span:first").text() == name){
			$(this).find("input").each(function(){
				var value = $(this).val();
				if(value){
					$("#moreAttrImagesList").append(getImagesTabString(value,""));
				}
			});
		}
	});
}
function reoveImagesTabForMoreAttr(name,value){
	var selectName =  $("#ddlPictureVariationName").val();
	if(selectName == name){
		$("#moreAttrImagesList").find(".varselectpicture").each(function(){
			if($(this).find("span").text() == value){
				$(this).remove();
			}
		});
	}
}

function reloadRows(rows,values,name){
	var newRows = [];
	$.each(values,function(){
		var value = this;
		if(rows.length > 0){
			$.each(rows,function(){
				var curRow = this;
				//curRow.push('<td><input type="hidden" value="'+value+'"/>'+value+'</td>');
				newRows.push(curRow+'<td><input type="hidden" name="'+name+'" value="'+value+'"/>'+value+'</td>');
			});
		}else{
			var row = getBaseRow();
			newRows.push(row.join("")+'<td><input type="hidden" name="'+name+'" value="'+value+'"/>'+value+'</td>');
		}
	});
	return newRows;
}

function tableHeadIsExist(headName){
	var f = false;
	$("#moreAttrTableValueHead").find("th").each(function(){
		if($(this).text() == headName){
			f = true;
			return false;
		}
	});
	return f;
}

function reloadTable(data){
	if(data){
		var rows = [];
		for(var key in data){
			var values = data[key];
			if(values.length>0){
				//插入表头
				if(!tableHeadIsExist(key)){
					$("#moreAttrTableAddHead").before("<th>"+key+"</th>");
				}
				rows = reloadRows(rows,values,key);
			}
		}
		if(rows.length>0){
			var rowsFinal = [];
			$.each(rows,function(){
				rowsFinal.push('<tr>'+this+'<td><a class="moreAttrValueDel">移除</a></td></tr>');
			});
		
			//插入值
			$(".moreAttrValueTable tbody").append(rowsFinal.join(""));
		}
	}
}

function deleteMoreAttrTable(name,value,isDeleteAll){
	if(isDeleteAll){
		$(".moreAttrValueTable tbody").html("");
		updateMoreAttrTable("","");
	}else{
		$(".moreAttrValueTable tbody").find("td").each(function(){
			var text = $(this).text();
			if(text == value){
				$(this).parents("tr").remove();
			}
		});
	}
}

function updateMoreAttrTable(curName,curValue){
	var attrKind = {};
	$(".attr_one_kind").each(function(){
		var name = $(this).find("span:first").text();
		var values = [];
		$(this).find("input").each(function(){
			var value = $(this).val();
			if(value){
				if(name != curName){
					values.push(value);
				}else{
					if(value == curValue){
						values.push(value);
					}
				}
			}
		});
		attrKind[name] = values;
	});
	reloadTable(attrKind);
}
function getBaseRow(){
	var base = [];
	base.push('<td><input name="SKU" type="text" /></td>');//sku
	base.push('<td><input name="StartPrice" type="text" /></td>');//价格
	base.push('<td><input name="Quantity" type="text" /></td>');//数量
	return base;
}

function reloadImg(curMoreAttr1,selectName){
	var variationSpecificsSet = curMoreAttr1.variationSpecificsSet
	if(variationSpecificsSet){
		var attr = [];
		$("#ddlPictureVariationName").html("");
		$.each(variationSpecificsSet,function(){
    		addOption("ddlPictureVariationName",this.Name);
    		//没有指定默认第一个
    		if(!selectName){
				selectName = this.Name;
			}
    		//设置这个属性图片
    		if(selectName==this.Name){
    			picList = [];
    			if(this.Values){
    				var pictures = curMoreAttr1.pictures;
    				$.each(this.Values,function(){
    					var value = this;
    					var noPic = true;
    					if(pictures){
    			    		$.each(pictures,function(){
    			    			if(selectName == this.VariationSpecificName&&value == this.VariationSpecificValue){
    			    				picList.push(getImagesTabString(this.VariationSpecificValue,page.imgLi(this.PictureURL))); 
    			    				noPic = false;
    			    				return false;
    			    			}
    			    		});

    			    	}
    					if(noPic){
    						picList.push(getImagesTabString(value,""));
    					}
    					
    				});
    				$("#moreAttrImagesList").html(picList.join(""));
    			}
    		}
		});
		$.each(variationSpecificsSet,function(){
    		
		});
		setSelectValue('#ddlPictureVariationName',selectName);
	}
}
function reloadAttrImg(selectName){
	curMoreAttr = moreAttrModel("FixedPriceItem1");
	reloadImg(curMoreAttr,selectName);
}
//多属性集合
var moreAttrData = {};
var addAttr = [];
var ul = undefined;
var curSelectedName = undefined;
var curSelectedValue = undefined;
var lineNum = 1;
$(function(){
	proTitleNum = $('#proTitleCount').val();
	indexNum = $('#bundleProductCount').val();
	//捆绑产品
    //点击添加
    $("#lbtnBundleSKUAdd").on("click",function(){
    	indexNum++;
        $("#tbBundleSKU tbody").append(probundskuTr(indexNum));
    });

    //点击移除
    $("#tbBundleSKU").on("click","tr .remove",function(){
        $(this).parents("tr").remove();
    });
    
    //物品标题
    //点击添加新的标题
    $("#addNewTitle").on("click",function(){
    	proTitleNum++;
        $("#tbItemTitle").append(proTitle(proTitleNum));
        $("#tbItemTitle").find(".select").show();//显示移除按钮
    });

    //点击移除
    $("#tbItemTitle").on("click",".select",function(){
        $(this).parents("dl").remove();
        if($("#tbItemTitle dl").length == 1){
            $("#tbItemTitle dl").find(".select").hide();
        }
    });
   
    $("#lbtnAddNewVariationRow").click(function(){
    	//moreAttrAddNameWin
    	page.layerOpen("350px","130px",".moreAttrAddNameWin");
    });
    //添加属性名称确认
    $("#moreAttrAddNameOk").click(function(){
    	var name = $("#moreAttrAddName").val();
    	if(name){
    		//增加多属性
    		addAttr.push(name);
    		$("#moreAttrTableAddHead").before("<th head='"+name+"'>"+name+"<a class='delete headDelete' style='border: #f9f6f6 solid 1px;margin-left: 10px;'>X</a></th>");
    		$(".moreAttrValueTable tbody").find("tr").each(function(){
    			$(this).find(".moreAttrValueDel").parents("td").before('<td><input style="width:100px" type="text" name="'+name+'"/></td>');
    		});
    	}
    	$("#moreAttrAddName").val("");
    	layer.closeAll();
    });
    //新增一行
    $("#addMoreAttrLine").on("click",function(){
    	var lineHtml = [];
    	lineHtml.push('<tr>')
    	$("#moreAttrTableValueHead").find("th").each(function(){
			var attrName = $(this).attr("head");
			if(attrName == "num"){
				lineHtml.push('<td>'+lineNum+'</td>');
				lineNum++;
			}else if(attrName == "ck"){
				lineHtml.push('<td><input type="checkbox" name="ck"/></td></td>');
			}else if(attrName == "opt"){
				lineHtml.push('<td><a class="moreAttrValueDel">移除</a></td>');
			}else if(attrName == "StartPrice"||attrName == "Quantity"){
				lineHtml.push('<td><input type="text" style="width:50px" name="'+attrName+'"/></td>');
			}else if(attrName == "SKU"||attrName == "UPC"||attrName == "EAN"){
				lineHtml.push('<td><input type="text"  name="'+attrName+'"/></td>');
			}else{
				lineHtml.push('<td><input type="text" style="width:100px" name="'+attrName+'"/></td>');
			}
		});
    	lineHtml.push('</tr>');
    	//插入值
		$(".moreAttrValueTable tbody").append(lineHtml.join(""));
    });
    //图片预览
    $("body").on("click","img",function(){
    	var url = $(this).attr("src");
    	$("#imgViewTag").attr("src",url);
    	$("#imgView").window("center");
    	$("#imgView").window("open");
    });
    $("#moreAttrList").on("click",".del_line",function(){
    	var inputCount = $(this).parents(".attr_one_kind").find("input");
    	var name = $(this).parents(".attr_one_kind").find("span:first").text();
    	var value = $(this).parents(".atrr_delete_flag").find("input").val();
    	reoveImagesTabForMoreAttr(name,value);
    	if(inputCount.length < 2){
    		removeOption("ddlPictureVariationName",name);
    		$(this).parents(".attr_one_kind").remove();
    		$("#moreAttrTableValueHead").find("th").each(function(){
    			if($(this).text() == name){
    				$(this).remove();
    				return false;
    			}
    		});
    		deleteMoreAttrTable(name,value,true);
    	}else{
    		$(this).parents(".atrr_delete_flag").remove();
    		deleteMoreAttrTable(name,value,false);
    	}
    	
    });
    $("#moreAttrList").on("click",".add_line",function(){
    	$(this).parent("div").before('<div class="atrr_delete_flag" style="padding-bottom: 5px;"><input type="text" style="width:240px;"><span style="padding-left:10px;"><a href="javascript:void(0);" class="del_line">移除</a></span></div>');
    });
    
    $("#ddlPictureVariationName").on("change",function(){
    	var name = $(this).val();
    	reloadAttrImg(name);
    });
    $(".moreAttrValueTable").on("blur","input",function(){
    	var value = $(this).val();
    	if(value){
    		reloadAttrImg();
    	}
    });
    
    //点击移除属性按钮
    $("#moreAttrTableValueHead").on("click",".delete",function(){
        var deName = $(this).parents("th").attr("head");
        $(".moreAttrValueTable tbody").find("input").each(function(){
        	if(this.name == deName){
        		 $(this).parents("td").remove();
        	}
        });
        $(this).parents("th").remove();
    	//清除属性
        reloadAttrImg();
    });
    
   //点击移除图片按钮
    $("#moreAttrImagesList").on("click",".delete",function(){
        //清除属性
        var name = $("#ddlPictureVariationName").val();
        var value = $(this).parents("ul").prev().find("span").text();
        var pictures = [];
        $.each(curMoreAttr.pictures,function(){
			if(this.VariationSpecificName == name && this.VariationSpecificValue == value){
				return true;
			}else{
				pictures.push(this);
			}
		});
        curMoreAttr.pictures = pictures;
        $(this).parents("li").remove();
    });

    //moreAttrImgModel
    
    $("#moreAttrImagesList").on('click',".moreAttrImgCheck",function(){
    	curSelectedName =  $("#ddlPictureVariationName").val();
    	curSelectedValue = $(this).next().text();
    	ul = $(this).attr("forId");
    	//$("#ebayImgListGrid").datagrid("reload");
    	$("#picCategoryTreegrid").tree("reload");
    	page.imageListLoad();
    	$('#selectImgModel').dialog('open').dialog('center').dialog('setTitle','选择图片');
        $("input[name='selectImg']").val("moreAttr");
    });
    
    $(".moreAttrValueTable").on('click',".moreAttrValueDel",function(){
    	$(this).parents("tr").remove();
    	reloadAttrImg();
    });
    $("#moreAttrCheckImg").on('click',function(){
    	var url = $("#moreAttrImgInput").val();
    	if(url){
    		$(ul).html(page.imgLi(url));
    		$("#moreAttrImgInput").val("");
    	}
    	layer.closeAll();
    });
});
//物品属性显示

