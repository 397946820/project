var vc = {
	confirm: function(message, fn) {
		$.messager.confirm({
			title: '确认',
			msg: message || '您确认要执行该操作吗？',
			fn: function(result) {
				if(result && 'function' === typeof fn) {
					fn();
				}
			}
		});
	},
	failedAlert: function() {
		$.messager.alert('消息提示', '操作失败', 'warning');
	},
	uploading:function(event) {
		var formData = new FormData();
		var myfile = document.getElementById("file").files[0];
		if(myfile == '' || myfile == null) {
			$.messager.alert("提示信息", "请选择导入的文件");
			 return;
		}
		debugger;
	    formData.append("file", myfile);
		$.ajax({
		      type: "POST",
		      url: GLOBAL.domain + event.data.url,
		      enctype: 'multipart/form-data',
		      data:formData,
		      async: false,
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
						debugger;
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
	},
	alert: function(data) {
		$.messager.alert('消息提示', data.description || 'Unknown', data && data.errorCode == 0 ? 'info' : 'warning');
	},
	progress: function() {
		$.messager.progress({ interval: 500, msg: '正在处理，请稍后...' });
	},
	closeProgress: function() {
		$.messager.progress('close');
	},
	timestampToDate: function(timestamp) {
		return !timestamp ? null : new Date(timestamp);
	},
	formatDate: function(value, row, index) {
		var date = vc.timestampToDate(value);
		return date && date.format('yyyy-MM-dd hh:mm:ss') || '';
	},
	formatStrToDate: function(time) {
		return time && new Date(time.replace(new RegExp("\\-","gi"), "/")) || null;
	},
	ajax: function(relativeUrl, data, successCallback, errorCallback, type, dataType, async) {
		$.ajax({
			url : GLOBAL.domain + relativeUrl,
			data: data,
			type: type || 'post',
			dataType: dataType || 'json',
			async: 'undefined' === typeof async ? true : async,
			success: successCallback,
		    error: 'function' === typeof errorCallback && errorCallback || vc.failedAlert
		});
	},
	collectForm: function(formSelector) {
		var data = {};
		$.each($(formSelector).serializeArray(), function() {
			data[this.name] = this.value;
		});
		return data;
	},
	clearForm: function(formSelector) {
		$(formSelector).form("clear");
	},
	action: {
		search: function() {
			var param = vc.collectForm('#form_search_vc');
			$("#dg_vc").datagrid({
				queryParams:{
					param :param
				}
			});
		}
	}
};
var beforCodeType;
var clickRow;
var editRowIndex;
$(function() {
	$('#actionUpload').on('click',{ grid: '#dg_vc', dialog: '#codeTypeuploadDialog',url:'/excel/import/amazonVcImport?catch=true'},vc.uploading);
	var columns = [[
		{ field: 'id', hidden: true }
		, { field: 'shippingDate', title: '发货时间', halign: 'center', align: 'center', width: 80 }
		, { field: 'deliveryFrom', title: '发货国家', halign: 'center', align: 'center', width: 60 }
		, { field: 'po', title: '订单号', halign: 'center', align: 'left', width: 100 }
		, { field: 'vendor', title: '发货帐号代码', halign: 'center', align: 'center', width: 80 }
		, { field: 'addressCode', title: '地址代码', halign: 'center', align: 'center', width: 80 }
		, { field: 'shipToName', title: '收件公司名称', halign: 'center', align: 'left', width: 150 }
		, { field: 'shipToAddr1', title: '地址1', halign: 'center', align: 'left', width: 150 }
		, { field: 'shipToAddr2', title: '地址2', halign: 'center', align: 'left', width: 150 }
		, { field: 'shipToAddr3', title: '城市', halign: 'center', align: 'left', width: 150 }
		, { field: 'shipToCountry', title: '省份/州', halign: 'center', align: 'right', width: 80 }
		, { field: 'shipPC', title: '邮编', halign: 'center', align: 'right', width: 70 }
		, { field: 'modelNumber', title: '产品型号', halign: 'center', align: 'right', width: 130 }
		, { field: 'asin', title: 'ASIN码', halign: 'center', align: 'center',width: 100}
		, { field: 'sku', title: 'SKU', halign: 'center', align: 'center',width: 130}
		, { field: 'deliverFrom', title: '接收快递送开始时间', halign: 'center', align: 'right', width: 85 }
		, { field: 'deliverTo', title: '接收快递送结束时间', halign: 'center', align: 'right', width: 85}
		, { field: 'submittedQuantity', title: '提交订单数量(亚马逊)', halign: 'center', align: 'center', width: 130}
		, { field: 'acceptedQuantity', title: '接收数量(计划部)', halign: 'center', align: 'right', width: 130}
		, { field: 'unitCost', title: '单价', halign: 'center', align: 'right', width: 130}
		, { field: 'totalCost', title: '总价', halign: 'center', align: 'right', width: 130}
		, { field: 'shipMethod', title: '运输方式', halign: 'center', align: 'right', width: 130}
		, { field: 'serviceType', title: '服务类型', halign: 'center', align: 'right', width: 130}
		, { field: 'trackingNO', title: '货物追踪号', halign: 'center', align: 'right', width: 130}
		, { field: 'actualQuantityReceived', title: '实际收货数量', halign: 'center', align: 'right', width: 130}
		, { field: 'receiveDate', title: '收货时间', halign: 'center', align: 'right', width: 130}
		, { field: 'deliveryTo', title: '目的国', halign: 'center', align: 'right', width: 130}
		, { field: 'carton', title: '件数', halign: 'center', align: 'right', width: 130}
		, { field: 'netWeight', title: '净重', halign: 'center', align: 'right', width: 130}
		, { field: 'grossWeight', title: '毛重', halign: 'center', align: 'right', width: 130}
	]];
	
	var maxDays = 31;
	
	$("#dg_vc").datagrid({
		url: '/ocs/vc/list',
		queryParams: {
			param: vc.collectForm('#form_search_vc')
		},
		columns: columns,
		singleSelect: true,
		autoEditing:true,
		extEditing:true,
		singleEditing:true,
		rownumbers: true,
		border: true,
		pagination: true,
		pageSize: 50,
		showFooter: true,
		onLoadSuccess: function(data){
			$(".datagrid-ftable tbody tr").each(function() {
			    this.style.backgroundColor="#E1EDFF";
			    this.style.color="blue";
			    this.style.fontWeight="bold";
			})
		},
		toolbar: [{
			text: '导出',
			iconCls: 'icon-redo',
			handler: function() {
				var downloadUrl = "/ocs/excel/dynamicExport/amazonVcExport";
				var param = vc.collectForm('#form_search_vc');
				var startTime = vc.formatStrToDate(param["startDate"])
					, endTime = vc.formatStrToDate(param["endDate"]);
				var paramUrl = '?';
				for(var key in param) {
					paramUrl += key + '=' + param[key] + '&';
				}
				
				var includeFields = 'unqualifiedCode,unqualifiedQuantity';
				for(var i = 0, l = columns[0].length; i < l; i++) {
					includeFields += ',' + columns[0][i].field;
				} 
				paramUrl += 'includeFields=' + includeFields;

				window.location.href = downloadUrl + paramUrl;
			}
		},{
			text: '导入',
			iconCls: 'icon-undo',
			handler: function() {
				$('#vcDatauploadDialog').dialog("open");
			}
		}],
		onClickCell : function(index,field,value){
			$('#dg_vc').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
		},
		onBeforeEdit:function(index,row){ 
			clickRow = row;
			editRowIndex = index;
			beforCodeType = row.codeTypeDetail;
			row.editing = true; 
		},
		onCancelEdit:function(index,row){  
			row.editing = false;  
			$('#dg_vc').datagrid('refreshRow', index);  
		}
	});
	
	$("#btn_vc_search").bind('click', vc.action.search);
	$("#btn_vc_reset").bind('click', function() { vc.clearForm('#form_search_vc'); });

	$(document).ready(function() {
		$('#form_search_vc table tr td.title').css('padding-left', '10px');
		var buttons = $.extend([], $.fn.datebox.defaults.buttons);
		buttons.splice(0, 0, {
			text: '清空',
			handler: function(target) {
				$(target).datebox('clear').datebox("panel").panel('close');
			}
		});
		$(".easyui-datebox").datebox({ buttons: buttons });
	});
});