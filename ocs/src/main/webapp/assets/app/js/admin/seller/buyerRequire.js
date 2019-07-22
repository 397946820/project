function InitializeC() {
	for (var i = 1; i < 6; i++) {
		for (var j = 1; j < 3; j++) {
			$("#" + "case" + i + j).combobox('disable');
		}
	}
}
function formatOper(val, row, index) {
	return '<a href="#" onclick="editBuyerRequire(' + index
			+ ')">编辑</a>    <a href="#" onclick="destroyBuyerRequire()">删除</a>';

}
function newBuyerRequire() {
	$('#panel').panel('close');
	$('#buyerDialog').dialog('open').dialog('center').dialog('setTitle', '添加');
	$('#buyerfm').form('reset');
	InitializeC();
	$("#buyerfm").on("change","input[type=checkbox]",function(){
		var div = $(this).parent("div");
		if($(this).is(":checked")){
			$(div).find("select").each(function(){
				$(this).attr('disabled',false);
			});
		}else{
			$(div).find("select").each(function(){
				$(this).attr('disabled',true);
			});
		}
	})
}

function editBuyerRequire(index) {
	$('#buyerTable').datagrid('selectRow',index);
	var row = $('#buyerTable').datagrid('getSelected');
	$('#buyerDialog').dialog('open').dialog('center').dialog('setTitle', '编辑');
	//$('#buyerfm').form('reset');
	$("#buyerId").val(row.id);
	$("#siteId").combobox("setValue",row.siteId);
	$("#title").textbox("setValue",row.title);
	renderBuyerRequireModel(eval('('+row.data+')'));

}
function saveBuyerRequire() {
	var siteId = $("#siteId").combobox("getValue");
	if(ocs.stringIsNull(siteId)){
		$.messager.alert('提示','请先选择站点!','warning');
		return;
	}
	var id = $("#buyerId").val();
	var title = $("#title").textbox("getValue");
	if(ocs.stringIsNull(title)){
		$.messager.alert('提示','请输入标题!','warning');
		return;
	}
	var saveAsMode = {};
	var saveData = contructBuyerRequireModel();
	saveAsMode["id"] = id;
	saveAsMode["data"] = JSON.stringify(saveData);
	saveAsMode["title"] = title;
	saveAsMode["siteId"] = siteId;
	saveAsMode["dataType"] = 2;
	ocs.ajax({
		url: "/publication/saveAs",
        type: "post",
        data:saveAsMode,
        success:function(data){
        	if(data){
        		$('#buyerDialog').dialog('close');
        		$('#buyerTable').datagrid('reload');
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
function destroyBuyerRequire() {
	var row = $('#buyerTable').datagrid('getSelections');
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
			        	$('#buyerTable').datagrid('reload');
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

var renderBuyerRequireModel = function(buyerRequireModel) {
	
	if(eval(buyerRequireModel.allowAllbuyer)) {
		$("#rbtnBuyerRequirementSpecified1").attr('checked',true);
	}else{
		$('#rbtnBuyerRequirementSpecified2').attr('checked',true);
		$(".buyerrequirement").show();
		$('#isHavePaypalAccount').attr('checked',eval(buyerRequireModel.noPaypai)?true:false);
		$('#isOffscale').attr('checked',eval(buyerRequireModel.outShipCountry)?true:false);
		//多少天有多少弃标方案
		var buyerReq1 = buyerRequireModel.buyerReq1;
		if(!ocs.stringIsNull(buyerReq1)){
			$('#chkMaxUnpaidItemStrikesInfoSpecified').attr('checked',true);
			var bys = buyerReq1.split("|");
			setSelectValue('#ddlMaxUnpaidItemStrikesInfoCount',bys[0]);
			setSelectValue('#ddlMaxUnpaidItemStrikesInfoPeriod',bys[1]);
			$('#ddlMaxUnpaidItemStrikesInfoCount').attr('disabled',false);
			$('#ddlMaxUnpaidItemStrikesInfoPeriod').attr('disabled',false);
		}else{
			$('#chkMaxUnpaidItemStrikesInfoSpecified').attr('checked',false);
			$('#ddlMaxUnpaidItemStrikesInfoCount').attr('disabled',true);
			$('#ddlMaxUnpaidItemStrikesInfoPeriod').attr('disabled',true);
		}
		//多少天内违反政策检举
		var buyerReq2 = buyerRequireModel.buyerReq2;
		if(!ocs.stringIsNull(buyerReq2)){
			$('#chkMaxBuyerPolicyViolationsSpecified').attr('checked',true);
			var bys = buyerReq2.split("|");
			setSelectValue('#ddlMaxBuyerPolicyViolationsCount',bys[0]);
			setSelectValue('#ddlMaxBuyerPolicyViolationsPeriod',bys[1]);
			$('#ddlMaxBuyerPolicyViolationsCount').attr('disabled',false);
			$('#ddlMaxBuyerPolicyViolationsPeriod').attr('disabled',false);
		}else{
			$('#chkMaxBuyerPolicyViolationsSpecified').attr('checked',false);
			$('#ddlMaxBuyerPolicyViolationsCount').attr('disabled',true);
			$('#ddlMaxBuyerPolicyViolationsPeriod').attr('disabled',true);
		}
		//信用指数
		var buyerReq3 = buyerRequireModel.buyerReq3;
		if(!ocs.stringIsNull(buyerReq3)){
			$('#chkMinFeedbackScoreSpecified').attr('checked',true);
			setSelectValue('#ddlMinFeedbackScore',buyerReq3);
			$('#ddlMinFeedbackScore').attr('disabled',false);
		}else{
			$('#chkMinFeedbackScoreSpecified').attr('checked',false);
			$('#ddlMinFeedbackScore').attr('disabled',true);
		}
		//在过去10天内曾出价或购买我的物品，已达到我所设定的限制
		var buyerReq4 = buyerRequireModel.buyerReq4;
		if(!ocs.stringIsNull(buyerReq4)){
			$('#chkMaxItemRequirementsMaxItemCountSpecified').attr('checked',true);
			setSelectValue('#ddlMaxItemRequirementsMaxItemCount',buyerReq4);
			$('#ddlMaxItemRequirementsMaxItemCount').attr('disabled',false);
			var buyerReq41 = buyerRequireModel.buyerReq41;
			if(!ocs.stringIsNull(buyerReq41)){
				$('#chkMaxItemRequirementsMinFeedbackScoreSpecified').attr('checked',true);
				setSelectValue('#ddlMaxItemRequirementsMinFeedbackScore',buyerReq41);
				$('#ddlMaxItemRequirementsMinFeedbackScore').attr('disabled',false);
			}else{
				$('#chkMaxItemRequirementsMinFeedbackScoreSpecified').attr('checked',false);
				$('#ddlMaxItemRequirementsMinFeedbackScore').attr('disabled',true);
			}
		}else{
			$('#chkMaxItemRequirementsMaxItemCountSpecified').attr('checked',false);
			$('#ddlMaxItemRequirementsMaxItemCount').attr('disabled',true);
		}
	}
}
var contructBuyerRequireModel = function(){
//	$('input:radio:checked').val()；
	var buyerRequireModel = {
			allowAllbuyer:"",
			noPaypai:"",
			outShipCountry:"",
			buyerReq1:"",
			buyerReq2:"",
			buyerReq3:"",
			buyerReq4:"",
			buyerReq41:""
		};
	if($('#rbtnBuyerRequirementSpecified1').is(':checked')){
		buyerRequireModel.allowAllbuyer = 'true';
	}else{
		buyerRequireModel.allowAllbuyer = 'false';
		buyerRequireModel.noPaypai = $('#isHavePaypalAccount').is(':checked')?'true':'false';
		buyerRequireModel.outShipCountry =$('#isOffscale').is(':checked')?'true':'false';
		if($('#chkMaxUnpaidItemStrikesInfoSpecified').is(':checked')){
			buyerRequireModel.buyerReq1 = $("#ddlMaxUnpaidItemStrikesInfoCount").val()+"|"+$("#ddlMaxUnpaidItemStrikesInfoPeriod").val();
		}else{
			buyerRequireModel.buyerReq1 = '';
		}
		if($('#chkMaxBuyerPolicyViolationsSpecified').is(':checked')){
			buyerRequireModel.buyerReq2 = $("#ddlMaxBuyerPolicyViolationsCount").val()+"|"+$("#ddlMaxBuyerPolicyViolationsPeriod").val();
		}else{
			buyerRequireModel.buyerReq2 = '';
		}
		if($('#chkMinFeedbackScoreSpecified').is(':checked')){
			buyerRequireModel.buyerReq3= $("#ddlMinFeedbackScore").val();
		}else{
			buyerRequireModel.buyerReq3 = '';
		}
		if($('#chkMaxItemRequirementsMaxItemCountSpecified').is(':checked')){
			buyerRequireModel.buyerReq4 = $("#ddlMaxItemRequirementsMaxItemCount").val();
			if(($('#chkMaxItemRequirementsMinFeedbackScoreSpecified').is(':checked'))){
				buyerRequireModel.buyerReq41 = $("#ddlMaxItemRequirementsMinFeedbackScore").val();
			}else{
				buyerRequireModel.buyerReq41 = '';
			}
		}else{
			buyerRequireModel.buyerReq4 = '';
		}
	}
	
	return buyerRequireModel;
}


$(function() {
	$("#buyerTable").datagrid(
	{
		url : '/ocs/publication/modelManagerlist',
		fitColumns : true,
		queryParams : {
			param : {
				"dataType" : 2
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
					formatter : function(a, row, index) {
						if((a == "" || a == null) && a!= 0){
							a=22;
						}
						return "<div class='icon3 country_size num_"+a+"'></div>";
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
						return '<a href="javascript:void(0);" class="easyui-linkbutton" onclick="editBuyerRequire('+index+');" data-options="plain：true">编辑</a>';
					}
				} ] ],
		idField : 'id',
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		fitColumns : true,
		pageSize : 50,
		border : true,
		onLoadSuccess : function(data) {
			return data;
		},
		toolbar : [ {
			text : '新增',
			iconCls : 'icon-add',
			handler : function() {
				newBuyerRequire();
			}
		}, {
			text : '删除',
			iconCls : 'icon-remove',
			handler : function() {
				destroyBuyerRequire();
			}
		} ]
	});
	
});