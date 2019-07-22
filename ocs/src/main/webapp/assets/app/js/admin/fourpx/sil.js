var sil = {
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
		var date = sil.timestampToDate(value);
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
		    error: 'function' === typeof errorCallback && errorCallback || sil.failedAlert
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
	unqualifieds: function(value, row, index) {
		var unqualifieds = $("#dg_sil").datagrid('getRows')[index]['unqualifieds'];
		if(unqualifieds && unqualifieds.length) {
			return '<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain: true" onClick="sil.action.openUnqualifieds(' + index + ')">详情</a>'
		} else {
			return '';
		}
	},
	action: {
		openUnqualifieds: function(rowIndex) {
			var row = $("#dg_sil").datagrid('getRows')[rowIndex];
			$('#dg_unqualifieds').datagrid({
				columns: [[
					{ field: 'id', hidden: true }
					, { field: 'unqualifiedCode', title: 'unqualifiedCode', halign: 'center', align: 'right', width: 150 }
					, { field: 'unqualifiedQuantity', title: 'unqualifiedCode', halign: 'center', align: 'right', width: 150 }
				]],
				emptyMsg: '<h3>没有数据</h3>',
				data: row['unqualifieds'] || [],
				singleSelect: true,
				rownumbers: true
			});
			$('#sil_unqualifieds').dialog('open');
		},
		closeUnqualifieds: function() {
			$('#sil_unqualifieds').window('close');
		},
		search: function() {
			var param = sil.collectForm('#form_search_sil');
			$("#dg_sil").datagrid({
				queryParams:{
					param :param
				}
			});
		},
		sync: function(busarea) {
			sil.confirm('同步4PX库存流水需要一定的时间，您确认要执行此操作吗？', function() {
				sil.progress();
				var url;
				if('us' === busarea) {
					url = '/fourpx/sil/syncFrom4pxSil';
				} else if('de' === busarea) {
					url = '/fourpx/sil/syncDeFrom4pxSil';
				} else {
					console.log('Error: This value is not supported. (busarea=' + busarea + ')');
					return;
				}
				
				sil.ajax(url, {}, function(resp) {
					sil.closeProgress();
					if(resp.data && 'object' === typeof resp.data) {
						var s = resp.data.success
							, sl = s && s.length || 0
							, sm = sl ? s.join('') : ''
							, f = resp.data.failed
							, fl = f && f.length || 0
							fm =  fl ? f.join('') : '';
						resp.description = '同步4PX库存流水：总共' + (sl + fl) + '条，成功' + sl + '条，失败' + fl + '条。';
						resp.description += '</br>';
						resp.description += sl ? ('同步成功详情：</br>' + sm) : '';
						resp.description += '</br>';
						resp.description += fl ? ('同步失败详情：</br>' + fm) : '';
					} 
					sil.alert(resp);
					sil.action.search();
				}, function() {
					sil.closeProgress();
					sil.failedAlert();
				});
			});
		},
		syncus: function() {
			sil.action.sync('us');
		},
		syncde: function() {
			sil.action.sync('de');
		}
	}
};
var beforCodeType;
var clickRow;
var editRowIndex;
(function(sil, $) {
	$('#actionUpload').on('click',{ grid: '#dg_sil', dialog: '#codeTypeuploadDialog',url:'/excel/import/inventoryFlowCodeTypeUpload?catch=true'},sil.uploading);
	var columns = [[
		{ field: 'id', hidden: true }
		, { field: 'swlId', title: 'swlId', halign: 'center', align: 'center', width: 80 }
		, { field: 'warehouseCode', title: '仓库代码', halign: 'center', align: 'center', width: 60 }
		, { field: 'skuId', title: 'skuId', halign: 'center', align: 'center', width: 60 }
		, { field: 'sku', title: 'sku', halign: 'center', align: 'left', width: 110 }
		, { field: 'skuName', title: '产品名称', halign: 'center', align: 'left', width: 110 }
		, { field: 'skuBarCode', title: '产品条码', halign: 'center', align: 'left', width: 160 }
		, { field: 'skuBarno', title: '产品数字条码', halign: 'center', align: 'center', width: 100 }
		, { field: 'refCode', title: '单据号', halign: 'center', align: 'center', width: 140 }
		, { field: 'inQuantity', title: '待入库库存', halign: 'center', align: 'right', width: 70 }
		, { field: 'actualQuantity', title: '库内库存', halign: 'center', align: 'right', width: 60 }
		, { field: 'availableQuantity', title: '可销售库存', halign: 'center', align: 'right', width: 70 }
		, { field: 'shippingQuantity', title: '待出库库存', halign: 'center', align: 'right', width: 70 }
		, { field: 'codeType', title: '业务类型', halign: 'center', align: 'center',width: 100}
		, { field: 'codeTypeDetail', title: '业务类型细分', halign: 'center', align: 'center',width: 160,editor:{
			type:'combobox',
			options:{
				valueField:'codeTypeKey',
				textField:'codeTypeValue',
				method:'get',
				url:'/ocs/fourpx/sil/getCodeType',
				onSelect:function(record) {
					beforCodeType=record.codeTypeValue;
					$('#dg_sil').datagrid('endEdit', editRowIndex);
				}
			}
		}}
		, { field: 'warehouseQuantity', title: '数量', halign: 'center', align: 'right', width: 40 }
		, { field: 'unqualifiedGross', title: '不合格库存', halign: 'center', align: 'right', width: 70 }
		, { field: 'unqualifieds', title: '不合格货品', halign: 'center', align: 'right', width: 80, formatter: sil.unqualifieds }
		, { field: 'createDate', title: '4PX创建时间', halign: 'center', align: 'center', width: 130, formatter: sil.formatDate }
		, { field: 'createdat', title: '首次同步时间', halign: 'center', align: 'right', width: 130, formatter: sil.formatDate }
		//, { field: 'updatedat', title: '最后同步时间', halign: 'center', align: 'right', width: 130, formatter: sil.formatDate }
	]];
	
	var maxDays = 31;
	
	$("#dg_sil").datagrid({
		url: '/ocs/fourpx/sil/list',
		queryParams: {
			param: sil.collectForm('#form_search_sil')
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
				var downloadUrl = "/ocs/excel/dynamicExport/sellerInventoryLogExport";
				var param = sil.collectForm('#form_search_sil');
				var startTime = sil.formatStrToDate(param["startDate"])
					, endTime = sil.formatStrToDate(param["endDate"])
					, validInterval = startTime && endTime && (endTime.getTime() - startTime.getTime()) < maxDays * 24 * 60 * 60 * 1000;
				
				if(!validInterval) {
					$.messager.alert('提示', '时间段过大，最多一次只能导出' + maxDays + '天数据，请分段导出！', 'info');
					return;
				}
				
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
			text: '更新类型',
			iconCls: 'icon-undo',
			handler: function() {
				$('#codeTypeuploadDialog').dialog("open");
			}
		},{
			text: '同步US库存流水',
			iconCls: 'icon-update',
			handler: sil.action.syncus
		},{
			text: '同步DE库存流水',
			iconCls: 'icon-update',
			handler: sil.action.syncde
		}],
		onClickCell : function(index,field,value){
			$('#dg_sil').datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
		},
		onBeforeEdit:function(index,row){ 
			clickRow = row;
			editRowIndex = index;
			beforCodeType = row.codeTypeDetail;
			row.editing = true; 
		},  
		onAfterEdit:function(index,row){
			row.editing = false;
			var id = row.id;
			var url = GLOBAL.domain + "/fourpx/sil/modifyCodeType";
			$.ajax({
				url : url,
				type : 'POST',
				data : {param:{id:id, codeTypeDetail:beforCodeType}},
				success : function(data) {
					var rows = JSON.parse(data);
					row.codeTypeDetail = rows.data;
					$('#dg_sil').datagrid('refreshRow', index);
				}
			});
		},  
		onCancelEdit:function(index,row){  
			row.editing = false;  
			$('#dg_sil').datagrid('refreshRow', index);  
		}
	});
	
	$("#btn_sil_search").bind('click', sil.action.search);
	$("#btn_sil_reset").bind('click', function() { sil.clearForm('#form_search_sil'); });

	$(document).ready(function() {
		$('#form_search_sil table tr td.title').css('padding-left', '10px');
		var buttons = $.extend([], $.fn.datebox.defaults.buttons);
		buttons.splice(0, 0, {
			text: '清空',
			handler: function(target) {
				$(target).datebox('clear').datebox("panel").panel('close');
			}
		});
		$(".easyui-datebox").datebox({ buttons: buttons });
		
	});
	
})(sil, jQuery);