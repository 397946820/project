	var transModel = function(){
	var trans ={};
	//国内运输
	var domesticTrans = [];
	//国外运输
	var calCulateTrans = [];
	var i = 1;
	$(".d_trans_flag").each(function(){
		var tranOne = {};
		tranOne["domesticShipType"] = $(this).find("select[name=domesticShipType]").val();
		tranOne["domesticShipCost"] = $(this).find("input[name=domesticShipCost]").val();
		tranOne["domesticExtraCost"] = $(this).find("input[name=domesticExtraCost]").val();
		tranOne["domesticShipAkHiPr"] = $(this).find("input[name=domesticShipAkHiPr]").val();
		tranOne["tranKind"] = 0;
		tranOne["tranOrder"] = i;
		domesticTrans.push(tranOne);
		i++;
	});
	trans["domesticTrans"] = domesticTrans;
	i = 1
	$(".d_trans_flag2").each(function(){
		var tranOne = {};
		tranOne["domesticShipType"] = $(this).find("select[name=domesticShipType]").val();
		tranOne["domesticShipCost"] = $(this).find("input[name=domesticShipCost]").val();
		tranOne["domesticExtraCost"] = $(this).find("input[name=domesticExtraCost]").val();
		tranOne["shipLocationIn"] = "Worldwide";
		tranOne["tranKind"] = 1;
		tranOne["tranOrder"] = i;
		if(!ocs.stringIsNull($(this).find("select[name=domesticShipType]").val())){
			calCulateTrans.push(tranOne);
		}
		i++;
	});
	trans["calCulateTrans"] = calCulateTrans;
	trans["domesticOptDay"] = $('#ddlDispatchTimeMax').val();
	var noShip = [];
	var noShipVal="";
	$("#noArriveRegion").children("span").each(function(){
		noShipVal = $(this).attr("value");
		if($.trim(noShipVal)<=0){
			$(this).remove();
			return true;
		}
		noShip.push(noShipVal)
	});
	trans["shipLocationOver"]= noShip.join(",");
	debugger
	return trans;
}
	$("#domesticShipCost1").on("change",function(){
		
		if($(this).val()>0){
			$("input[name='chkShippingFreeInt1']").attr("checked",false);
		}else{
			$("input[name='chkShippingFreeInt1']").attr("checked",true);
		}
	});
	$("#domesticShipCost").on("change",function(){
		if($(this).val()>0){
			$("#domesticShipCostOpt").attr("checked",false);
		}else{
			$("#domesticShipCostOpt").attr("checked",true);
		}
	});
	
//不运送地区开始
var noShipNo =function(){
	$("#noShipDialog").dialog("close");
}
var noShipSave = function() {
//	//看是否有选择不运送地区
//	
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
//	var noArriveRegions = $("#noArriveRegion").text();
//	var data = {noArriveRegions:noArriveRegions}
	$('#noShipDialog').window('close');
	
}
var areaSelect = function(){
	var international=  $(".international").find(".subRegion content");
	var Ul = $(".international").find(".content ul");
	$.ajax({
		url:"/ocs/assets/app/json/internationalArea.json",
		type:"get",
		data:"",
		async:false,
		success:function(data){
				for(var i in data){
					for(var j = 0;j<data[i].length;j++){
						var index = data[i][j].code;
						var name = 
							"<li>" +
								"<input type='checkbox' id='"+data[i][j].id+"' value='"+data[i][j].value+"'>"+
								"<label style='padding-left:5px;' for='"+data[i][j].id+"'>"+data[i][j].country +"</label>"+
							"</li>"
						Ul.eq(index).append(name);
//						Ul.hide();
						$(".region .content").hide();
					}
				}
		},
		error:function(){
			console.log("请求失败")
		}
	})
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
			console.log("请求失败")
		}
	})
}; 
//不运送地区结束
var renderSaveAsData = function(transChooseModel){
	setSelectValue('#ddlDispatchTimeMax',transChooseModel.domesticOptDay);
	var domesticTran = transChooseModel.domesticTrans[0];
	if(domesticTran){
		setSelectValue('#domesticShipType',domesticTran.domesticShipType);
		$("#domesticShipCost").val(domesticTran.domesticShipCost);
		if(domesticTran.domesticShipCost == 0){
			$("#domesticShipCostOpt").attr("checked",true);
		}else{
			$("#domesticShipCostOpt").attr("checked",false);
		}
		$("#domesticExtraCost").val(domesticTran.domesticExtraCost);
		$("#domesticShipAkHiPr").val(domesticTran.domesticShipAkHiPr);
	}
	
	var calCulateTran = transChooseModel.calCulateTrans[0];
	if(calCulateTran){
		setSelectValue('#domesticShipType1',calCulateTran.domesticShipType);
		$("#domesticShipCost1").val(calCulateTran.domesticShipCost);
		if(calCulateTran.domesticShipCost == 0){
			$("#chkShippingFreeInt1").attr("checked",true);
		}else{
			$("#chkShippingFreeInt1").attr("checked",false);
		}
		$("#domesticExtraCost1").val(calCulateTran.domesticExtraCost);
	}
	//不运输区域回选
	var noShipData = transChooseModel.shipLocationOver;
	var sideIdText=$("#siteId").combobox('getValue');
	if(noShipData==null){

		 $("#dlEeclusionList").hide();
		 $("#ddlExclusionListType").val(0).attr("checked",true);
		 
   }else{
	$("#ddlExclusionListType").val(2).attr("checked",true);
	   if(!$(".international").attr("data-got")){
			areaSelect();
			$(".international").attr("data-got","got");
//			if(sideIdText==0||sideIdText==3||sideIdText==77){
				areaNoship();
//			}
		}
	 stateChange($("#siteId"))
   	$("#noArriveRegion").html("");
   	var noShipChoose =noShipData.split(",");
   	var noShipHtml ="";
		for (var i = 0; i < noShipChoose.length; i++) {
			 $("input[type=checkbox][value='" + noShipChoose[i] +"']").attr("checked",'true');
			 var a =  $("input[type=checkbox][value='" + noShipChoose[i] +"']").next("label").text();
			  noShipHtml +="<span value='"+noShipChoose[i]+"' style='margin-left:4px;'>"+a+"</span>,";
			
		}
		if(noShipHtml!=null||noShipHtml!=""){
			noShipHtml = noShipHtml.substr(0, noShipHtml.length - 1); 
		}
		$("#noArriveRegion").append(noShipHtml);
       $("#dlEeclusionList").show();
//       $("#noArriveRegion").text(returnPolicyModel.shipLocationOver);
       $(".noArray-noSelected").hide();
       $(".noArray-selected").show();
       
   };
}
function stateChange(site){
	 var sideIdText=$("#siteId").combobox('getValue');
		if(sideIdText==203||sideIdText==207||sideIdText==3){
			$("#divAdditionalLocations").hide();
		}else if(sideIdText==77){
			$("#POBox").next("label").text("Filialen und Shops der Versanddienstleister");
			$("#Packstationen2").show();
		}else if(sideIdText==186){
			$("#POBox").next("label").text("Apartado postal");
		}else if(sideIdText==212){
			$("#POBox").next("label").text("Skrytki pocztowe");
		}else if(sideIdText==210||sideIdText==23||sideIdText==71){
			$("#POBox").next("label").text("Boîte postale");
		}else{
			$("#POBox").next("label").text("PO Box");
		} 
}

function newTransport(){
	$("#title").textbox('setValue','');
	$("#siteId").combobox('setValue','');
	$('#DomesticShippingFrom').form('reset');
	$("#InternationalShippingFrom").form('reset');
    $('#transportSettingDialog').dialog('open').dialog('center').dialog('setTitle','添加');
    $("#noArriveRegion").html("");
    $(".noArray-selected").hide();
    $(".noArray-noSelected").show();
    $("#dlEeclusionList").hide();
}
        
function editTransport(index){
	$('#transportSettingDataGrid').datagrid('selectRow',index);
	var row = $('#transportSettingDataGrid').datagrid('getSelected');
	$('#transportSettingDialog').dialog('open').dialog('center').dialog('setTitle', '编辑');
	//$('#buyerfm').form('reset');
	$("#saveAsId").val(row.id);
	$("#siteId").combobox("setValue",row.siteId);
	$("#title").textbox("setValue",row.title);
	renderSaveAsData(eval('('+row.data+')'));
	if($("#domesticShipCost").val()>0){
		$("#domesticShipCostOpt").prop("checked",false)
	}else{
		$("#domesticShipCostOpt").prop("checked",true)
	}
	if($("#domesticShipCost1").val()>0){
		$("input[name='chkShippingFreeInt1']").prop("checked",false);
	}else{
		$("input[name='chkShippingFreeInt1']").prop("checked",true)
	}
	
}
     $("#domesticShipCostOpt").on("change",function(){
    		 $("#domesticShipCost").val("0.00")
     })
      $("input[name='chkShippingFreeInt1']").on("change",function(){
    		 $("#domesticShipCost1").val("0.00")
     })
function saveTransport(){
	var siteId = $("#siteId").combobox("getValue");
	if(ocs.stringIsNull(siteId)){
		$.messager.alert('提示','请先选择站点!','warning');
		return;
	}
	var id = $("#saveAsId").val();
	var title = $("#title").textbox("getValue");
	if(ocs.stringIsNull(title)){
		$.messager.alert('提示','请输入标题!','warning');
		return;
	}
	var saveAsMode = {};
	var saveData = transModel();
	saveAsMode["id"] = id;
	saveAsMode["data"] = JSON.stringify(saveData);
	saveAsMode["title"] = title;
	saveAsMode["siteId"] = siteId;
	saveAsMode["dataType"] = 5;
	ocs.ajax({
		url: "/publication/saveAs",
        type: "post",
        data:saveAsMode,
        success:function(data){
        	if(data){
        		$('#transportSettingDialog').dialog('close');
        		$('#transportSettingDataGrid').datagrid('reload');
        		$.messager.show({
        			title:'提示',
        			msg:'保存成功',
        			timeout:500,
        			showType:'slide',
        			style:{
        				top:20,
        				left:document.body.offsetWidth/2 - 150,
        				right:''
        			}
        		});
        	}
        }
	});

}
        
function destroyTransport(){
	var row = $('#transportSettingDataGrid').datagrid('getSelections');
	if(row && row.length > 0){
		$.messager.confirm('删除', '你确定要删除数据?', function(r) {
			if (r) {
				var ids = [];
				$.each(row,function(){
					ids.push(this.id);
				});
				ocs.ajax({
					url: "/publication/saveAsDelete/"+ids.join("-"),
			        type: "post",
			        data:"",
			        success:function(){
			        	$('#transportSettingDataGrid').datagrid('reload');
						//$('#buyerTable').datagrid('clearSelections');
			        }
				});
			}
		});
	}else{
		$.messager.alert('提示','请先选择一条数据后操作!','warning');
		return;
	}     	
}

$(function() {
	var flag = true;
	//0 物品描述  1 支付方式  2 买家要求 3 退货政策 4 物品所在地  5 运输选项
	$("#transportSettingDataGrid").datagrid(
	{
		url : '/ocs/publication/modelManagerlist',
		fitColumns : true,
		queryParams : {
			param : {
				"dataType" : 5
			}
		},
		columns : [ [
				{
					field : 'title',
					title : '名称',
					align : 'center',
					width : 100
				},
				{
					field : 'siteId',
					title : '站点',
					align : 'center',
					width : 100,
					formatter : function(value, row, index) {
						if((value == "" || value == null) && value!= 0){
							value=22;
						}
						return "<div class='icon3 country_size num_"+value+"'></div>";
					}
				},
				{
					field : 'data',
					hidden : true
				},
				{
					field : 'dataType',
					hidden : true
				},
				{
					field : 'stock',
					title : '动作',
					width : 100,
					align : 'center',
					formatter : function(value, row, index) {
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="editTransport('+index+');" data-options="plain：true">编辑</a>';
					}
				} ] ],
		idField : 'id',
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		pageSize : 50,
		border : true,
		loadFilter : function(data) {
			/*debugger
			var rows = data.rows;
			if(rows){
				var datagridColumns = [];
				var columns = [];
				columns.push({
					field : 'title',
					title : '名称',
					align : 'center',
					width : 100
				});
				columns.push({
					field : 'siteId',
					title : '站点',
					align : 'center',
					width : 100
				});
				columns.push({
					field : 'data',
					hidden : true
				});
				columns.push({
					field : 'dataType',
					hidden : true
				});
				
				
				$.each(rows,function(){
					var views = this.data;
					views = eval('('+views+')');
					for(var key in views){
						this[key] = views[key];
						if(flag){
							if(key =="productAddress"){
								var column = {
										field : "productAddress",
										title : "物品所在地",
										align : 'center',
										width : 100
									};
								columns.push(column);
							}
							if(key =="region"){
								var column = {
										field : "region",
										title : "国家或地区",
										align : 'center',
										width : 100
									};
								columns.push(column);
							}
							
						}
					}
					
				});
				if(flag){
					columns.push({
						field : 'stock',
						title : '动作',
						width : 100,
						align : 'center',
						formatter : function(value, row, index) {
							return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="editTransport('+index+');" data-options="plain：true">编辑</a>';
						}
					});
					datagridColumns.push(columns);
					$("#transportSettingDataGrid").datagrid({
						columns:datagridColumns
					});
				}
				flag = false;
				data["rows"] = rows;
			}*/
			return data;
		},
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				newTransport();
			}
		}, {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				destroyTransport();
			}
		} ]
	});
	
	$('#siteId').combobox({
		url : GLOBAL.domain + '/publication/getSiteList',
		valueField : 'value',
		textField : 'displayName',
		onChange : function(newValue,oldValue){
			$("#domesticShipAkHiPr").parents(".form-group").hide();
			if(newValue==0){
				$(".CSymbol").text("USD");
				$("#domesticShipAkHiPr").parents(".form-group").show();
			}else if(newValue==3){
				$(".CSymbol").text("GBP");
			}else if(newValue==77){
				$(".CSymbol").text("EUR");
				
			}
			if(newValue != oldValue && newValue != ''){
				//运输方式
				ocs.ajax({
					async:false,
					url:"/publication/getTransTypeData/"+newValue,
					type:"POST",
					data:"",
					success:function(returnData){
						var dom = [];
						var cu = [];
						cu.push(' <option value="" selected="selected">-- 选择 --</option>');
						if(returnData){
							$.each(returnData,function(){
								if(this.dataType == 1){
									dom.push('<option value="'+this.value+'">'+this.displayName+'</option>');
								}else{
									cu.push('<option value="'+this.value+'">'+this.displayName+'</option>');
								}
							});
						}
						$("#domesticShipTypeOptions").html(dom.join(""));
						$("#domesticShipType1").html(cu.join(""));
						 if(newValue!=oldValue){
					           $("#noArriveRegion").html("");
					           $(".noArray-selected").hide();
					           $(".noArray-noSelected").show();
					       }
						 
					}
				
				});
			}
		}
	});
//	编辑不运送地区
	$("#ddlExclusionListType").on("change",function(){
		if($(this).val()==2){
			$("#dlEeclusionList").show();
		}else{
			$("#dlEeclusionList").hide();
		}
	});
	$("#allCreate").click(function(){
		$("#noShipDialog").dialog("open");
		var sideIdText=$("#siteId").combobox('getValue');
		$("#Packstationen2").hide();
		$("#divAdditionalLocations").show();
//		$(".international['data-got'],.domestic['data-got']")
		 if(!$(".international").attr("data-got")){
				areaSelect();
				$(".international").attr("data-got","got");
//				if(sideIdText==0||sideIdText==3||sideIdText==77){
					areaNoship();
//				}
			}
		$(".domesticChoose").hide();
		if(sideIdText==0 && sideIdText!=""){
			$(".domesticChoose").eq(0).show().siblings().hide();
		}else if(sideIdText==3){
			$(".domesticChoose").eq(1).show().siblings().hide();
		}else if(sideIdText==77){
			$(".domesticChoose").eq(2).show().siblings().hide();
		}else{
			$(".domesticChoose").hide()
		};
//		divAdditionalLocations的值
		stateChange($("#siteId"))
		
		if($("#noArriveRegion span").length>0){
			$("#noShip").html($("#noArriveRegion").html());
			$(".selected").show();
			$(".noSelected").hide();
		}else{
			$("#noShip").html("");
			$(".selected").hide();
			$(".noSelected").show();
		}
		
		$("#noShipDialog").find("input").attr("checked",false);
		var noShip = $("#noArriveRegion").text().split(",");
//		console.log($(".subRegion").find("label"));
		var labelLength = $("#noShipDialog").find("label");
		for (var i = 0; i < noShip.length; i++) {
			if(noShip[i]!=""){
				$("label:contains('"+$.trim(noShip[i])+"')").prev("input:checkbox").attr("checked",true);
			}
		}
		
	})
});
$(".noArray-cancelSelected").on("click",function(){
	$("#_MessageRow_0").find("input:checkbox").attr("checked",false);
	 $(".noArray-noSelected").show();
	 $(".noArray-selected").hide();
	 $(".noArray-selected #noArriveRegion").html("");
});

var noShip = {
		init : function() {
			this.eventBind();
		},
		data : {

		},
		eventBind : function() {
			var self = this;
			// 显示/隐藏所有国家
			$(".showAll").on("click", function() {
				var content = $(this).parents(".subRegion").next(".content");
				if ($(this).html() == "显示所有国家") {
					$(this).html("隐藏所有国家");
					content.show();
				} else {
					$(this).html("显示所有国家");
					content.hide();
				}
			});

			// 全选、反选
			$(".subRegion input").on("change", function() {
				var selectContent = $(this).parent(".subRegion").next(".content");
				if ($(this).is(':checked')) {
					selectContent.find("input").prop("checked", true);
					self.showSelected();
				} else {
					selectContent.find("input").prop("checked", false);
					self.showSelected();
				}
			});
			$(".domesticChoose").on("change","input",function(){
				self.showSelected();
			});
			$(".content ")
					.on(
							"change","input",
							function() {
								var this_ = this;
								var contentInp = $(this_).parents(".content").find(
										"input");
								var contentInps = contentInp.length;
								var selectedInps = 0;
								var subRegionInp = $(this_).parents(".content")
										.prev(".subRegion").find("input");
								contentInp.each(function() {
									if ($(this).is(':checked')) {
										selectedInps++;
									}else{
										$(this).parents(".content").prev(".subRegion").find("input:checkbox").attr("checked",false);
									}
								});
								if (selectedInps >= contentInps) {
									subRegionInp.prop("checked", true);
									self.showSelected();
								} else {
									subRegionInp.prop("checked", false);
									self.showSelected();
								}
							});

			// 取消已选
			$(".cancelSelected").on("click", function() {
				$(".international").find("input").prop("checked", false);
				$(".domestic").find("input").prop("checked", false);
				self.showSelected();
			});
		},
		showSelected : function() {
			var checkLength = $(".international").find("input:checked").length;
			var allInp = $(".international").find(".subRegion input");
			var checkLength2 = $(".domesticChoose").find("input:checked").length;
			var allInp2 = $(".domesticChoose").find(".subRegion input");
			
			var check_val = [];// 存放被选中的元素的值
			if (checkLength <= 0 && checkLength2 <= 0) {
				$(".noSelected").show();
				$(".selected").hide();
				$(".selected").find("span").html("");
			} else {
				$(".noSelected").hide();
				$(".selected").show();
				allInp.each(function() {
					if ($(this).is(':checked')) {
						var Li = "<span  value='"+$(this).val()+"'>"+$(this).siblings("label").html()+"</span>"
						check_val.push(Li);
					} else {
						var contentInp = $(this).parent(".subRegion").next(".content").find("input");
						contentInp.each(function() {
							if ($(this).is(':checked')) {
								var Li = "<span  value='"+$(this).val()+"'>"+$(this).siblings("label").html()+"</span>"
								check_val.push(Li);
							}
						});
					}
				});
				allInp2.each(function(){
					$(".domesticChoose:hidden").find("input:checkbox").attr("checked",false);
					if ($(this).is(':checked')) {
						var Li = "<span value='"+$(this).val()+"'>"+$(this).next("label").html()+"</span>"
						check_val.push(Li);
					}
				})
				check_val = check_val.join(",");
				$(".selected").find("#noShip").html(check_val).css("font-weight","bold");
			}
		}
	};
	noShip.init();
