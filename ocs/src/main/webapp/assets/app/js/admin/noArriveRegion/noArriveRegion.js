//打开添加的窗口
var add = function() {
	$('#noArriveRegionForm').form('clear');
	$("#noArriveRegion").html("");
	$('#noArriveRegionDialog').window('open');
};

// 打开修改的窗口
var edit = function() {
	var rows = $("#noArriveRegionDataGrid").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert("提示信息", "请选中一行编辑");
		return;
	} else if (rows.length > 1) {
		$.messager.alert("提示信息", "只能选择一行进行编辑");
		return;
	}
	var $row = $('#noArriveRegionDataGrid').datagrid('getSelected');
	$('#noArriveRegionForm').form('clear');
	$('#noArriveRegionForm').form('load', $row);
	var noArriveRegions = $row.noArriveRegions;
	$("#noArriveRegion").html(noArriveRegions);
	$("#noArriveRegion").show();
	$("#create").hide();
	$("#edit").show();
	$('#noArriveRegionDialog').window('open');
};
// 保存和修改
var noArriveRegionFormSave = function() {
	if(!$("#noArriveRegionForm").form('validate')) {
		return;
	}
	var id = $("#hid").val();
	var name = $("#name").val();
	var siteId = $("#siteId").val();
	var noArriveRegions = $("#noArriveRegion").html();
	$(".showAll").html("显示所有国家");
	$(".content").hide();
	if(noArriveRegions == '' || noArriveRegions == null) {
		$.messager.alert('消息提示', '请选择不运送区域', 'warning');
		return;
	}
	var data = {id:id, name:name, siteId:siteId, noArriveRegions:noArriveRegions};
	var url = GLOBAL.domain + "/noArriveRegion/saveEdit";
	$.ajax({
		url : url,
		type : 'POST',
		data : data,
		success : function(response) {
			var $data = JSON.parse(response);
			if ($data && $data.errorCode == 0) {
				$.messager.alert('消息提示', $data.description, 'info');
				$('#noArriveRegionDialog').window('close');
				$("#noArriveRegionDataGrid").datagrid("reload");
			} else if ($data && $data.errorCode == 1) {
				$.messager.alert('消息提示', $data.description, 'warning');
			}
			$('#noArriveRegionDataGrid').datagrid('clearSelections');
		},
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '操作失败', 'warning');
	    }
	});
};

var noArriveRegionCotextMenu = function(e, rowIndex, rowData) {
	e.preventDefault();
	$("#noArriveRegionDataGrid").datagrid('selectRow', rowIndex);
	$('#noArriveRegionCotextMenu').menu('show', {
		left : e.pageX,
		top : e.pageY
	});
};

var noArriveRegionFormNo = function() {
	$('#noArriveRegionDialog').window('close');
}
var getImage = function(value,row,index){
	 return "<div class='icon3 country_size num_"+row.siteId+"'><div>";
}
var noShipNo = function() {
	$('#noShipDialog').window('close');
}

var noShipSave = function() {
	//看是否有选择不运送地区
	
	var noShip = $("#noShip").html();
	if(noShip != ''){
		$("#noArriveRegion").html(noShip);
		$("#noArriveRegion").show();
		$("#create").hide();
		$("#edit").show();
	} else {
		$("#create").show();
		$("#edit").hide();
	}
//	var noArriveRegions = $("#noArriveRegion").text();
//	var data = {noArriveRegions:noArriveRegions}
	$('#noShipDialog').window('close');
	
}


$(function() {
	
	$('#noArriveRegionEditContextMenu').on('click', edit);
	$("#noArriveRegionAddLinkbutton").on('click', add);
	$("#noArriveRegionEditLinkbutton").on('click', edit);
	$("#noArriveRegionRemoveLinkbutton").on('click', {
		grid : '#noArriveRegionDataGrid',
		type : 'datagrid',
		url : '/noArriveRegion/remove'
	}, removeAll);
	$("#noArriveRegionRemoveContextMenu").on('click', {
		grid : '#noArriveRegionDataGrid',
		type : 'datagrid',
		url : '/noArriveRegion/remove'
	}, removeAll);
	
	$("#create").click(function(){
		var sideIdText=$("#siteId").combobox('getValue');
		$('#noShipDialog').window('open');
		reset();
		$(".domestic").find(".domesticChoose .subRegion").html("");
		$(".international").find(".content ul").html("");
		if(sideIdText==0||sideIdText==3||sideIdText==77){
			areaNoship();
		}
		areaSelect();
//		divAdditionalLocations的值
		$("#Packstationen2").hide();
		$("#divAdditionalLocations").show();
		$(".domesticChoose").hide();
		stateChange($("#siteId"));
	})
	$('#siteId').combobox({
	    onChange:function(newValue,oldValue){
	       if(newValue!=oldValue){
	           $("#noArriveRegion").html("");
	           $("#edit").hide();
	           $("#create").show();
	       }
	    }
	})

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
								"<li style='padding:5px 0 5px 0'>" +
									"<input type='checkbox' id='"+data[i][j].id+"' value='"+data[i][j].value+"'>"+
									"<label style='padding-left:5px;' for='"+data[i][j].id+"'>"+data[i][j].country +"</label>"+
								"</li>"
							Ul.eq(index).append(name)		
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
	var setId=function(){
//		给选项添加id值和label for的值          
		var interId = $(".international input");
		var interLabel = $(".international label");
		var domId = $(".domestic input");
		var domLabel = $(".domestic label");
		for(var i = 0 ;i < interId.length;i++){
			var iD="sideId_"+i;
			interId[i].setAttribute("id",iD);
			interLabel[i].setAttribute("for",iD);
		}
		for(var a = 0 ;a < domId.length;a++){
			var domiD="domsideId_"+a;
			domId[a].setAttribute("id",domiD);
			domLabel[a].setAttribute("for",domiD);
		}
		
	};
	$("#edit").click(function(){
		debugger;
		$(".domestic").find(".domesticChoose .subRegion").html("");
		$(".international").find(".content ul").html("");
		var sideIdText=$("#siteId").combobox('getValue');
			areaSelect();
		if(sideIdText==0||sideIdText==3||sideIdText==77){
			areaNoship();
		}
		
//		divAdditionalLocations的值
		$("#Packstationen2").hide();
		$("#divAdditionalLocations").show();
		stateChange($("#siteId"));
//		var noArriveRegion = $("#noShip");
		$("input[type=checkbox]").removeAttr("checked");
		var noArriveRegion = $("#noArriveRegion").html();
		$("#noShip").html(noArriveRegion);
		var noShip = noArriveRegion.split(",");
		var selectedNoship = $("#noArriveRegion span");
//		点击编辑反选之前选中的值
		var noShip = $("#noArriveRegion span").each(function(){
			$("label:contains('"+$(this).text()+"')").prev("input:checkbox").attr("checked",true);
		});
//		for (var i = 0; i < noShip.length; i++) {
//			
//		}
		var checkArry = $("#noShipDialog .subRegion input[type='checkbox']");
		checkArry.each(function(){
			if($(this).is(':checked')) {
				$(this).parents(".subRegion").next(".content").find("input").attr("checked",'true');
			}
		});
		$(".noSelected").hide();
		$(".selected").show();
		$('#noShipDialog').window('open');
	})
});
function reset() {
	$(".noArriveRegion").text("");
	$("#noShip").text("");
	$("input[type=checkbox]").removeAttr("checked");
	$(".noSelected").show();
	$(".selected").hide();
}
function domesticChoose(site){
	debugger;
	var sideIdText=site.combobox('getValue');
	if(sideIdText==0 && sideIdText!=""){
		$(".domesticChoose").eq(0).show().siblings().hide();
	}else if(sideIdText==3){
		$(".domesticChoose").eq(1).show().siblings().hide();
	}else if(sideIdText==77){
		$(".domesticChoose").eq(2).show().siblings().hide();
	}else{
		$(".domesticChoose").hide()
	};
}
var stateChange = function(site){
	debugger;
	domesticChoose(site);
	var sideIdText2=site.combobox('getValue');
	if(sideIdText2==203||sideIdText2==207||sideIdText2==3){
		$("#divAdditionalLocations").hide();
	}else if(sideIdText2==77){
		$("#POBox").next("label").text("Filialen und Shops der Versanddienstleister");
		$("#Packstationen2").show();
	}else if(sideIdText2==186){
		$("#POBox").next("label").text("Apartado postal");
	}else if(sideIdText2==212){
		$("#POBox").next("label").text("Skrytki pocztowe");
	}else if(sideIdText2==210||sideIdText2==23||sideIdText2==71){
		$("#POBox").next("label").text("Boîte postale");
	}else{
		$("#POBox").next("label").text("PO Box");
	}
}

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
							debugger;
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
		debugger;
		var checkLength = $(".international").find("input:checked").length;
		var allInp = $(".subRegion input");
//		var allInp = $(".international").find(".subRegion input");
		var checkLength2 = $(".domesticChoose").find("input:checked").length;
//		var allInp2 = $(".domesticChoose").find(".subRegion input");
		
		var check_val = [];// 存放被选中的元素的值
		if (checkLength <= 0 && checkLength2 <= 0) {
			$(".noSelected").show();
			$(".selected").hide();
			$(".selected").find("span").html("");
		} else {
			$(".noSelected").hide();
			$(".selected").show();
			allInp.each(function() {
				$(".domesticChoose:hidden").find("input:checkbox").attr("checked",false);
				$("#divAdditionalLocations:hidden").find("input:checkbox").attr("checked",false);
				$("#Packstationen:hidden").attr("checked",false);
				if ($(this).is(':checked')) {
					debugger;
					console.log()
//					var Li = "<span  value='"+$(this).val()+"'>"+$(this).siblings("label").html()+"</span>"
					var Li = "<span value='"+$(this).val()+"'>"+$(this).next("label").html()+"</span>"
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
//			$.each(allInp2,allInp3,function(){
//				$(".domesticChoose:hidden").find("input:checkbox").attr("checked",false);
//				if ($(this).is(':checked')) {
//					var Li = "<span value='"+$(this).val()+"'>"+$(this).next("label").html()+"</span>"
//					check_val.push(Li);
//				}
//			})
//			allInp2.each(function(){
//				$(".domesticChoose:hidden").find("input:checkbox").attr("checked",false);
//				if ($(this).is(':checked')) {
//					var Li = "<span value='"+$(this).val()+"'>"+$(this).next("label").html()+"</span>"
//					check_val.push(Li);
//				}
//			})
			check_val = check_val.join(",");
			$(".selected").find("#noShip").html(check_val).css("font-weight","bold");
		}
	}
};
noShip.init();
