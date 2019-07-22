var outw = {
	cache: {
		mergeRow: {}
	},
	editableFields: [ 'description', 'fullName', 'countryCode', 'street'],
	isEditable: function(field) {
		for (var i = 0, l = outw.editableFields.length; i < l; i++) {
			if(outw.editableFields[i] === field) {
				return true;
			}
		}
		return false;
	},
	detailFields: [ 'detailId', 'sku', 'quantity', 'skuLabelCode', 'skuValue' ],
	belongDetail: function(field) {
		for (var i = 0, l = outw.detailFields.length; i < l; i++) {
			if(outw.detailFields[i] === field) {
				return true;
			}
		}
		return false;
	},
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
	alert: function(data) {
		$.messager.alert('消息提示', data.description || 'Unknown', data && data.errorCode == 0 ? 'info' : 'warning');
	},
	progress: function() {
		$.messager.progress({ interval: 500, msg: '正在处理，请稍后...' });
	},
	closeProgress: function() {
		$.messager.progress('close');
	},
	textIndent5px: function(value, row, index) {
		return 'text-indent: 5px;';
	},
	timestampToDate: function(timestamp) {
		return !timestamp ? null : new Date(timestamp);
	},
	formatDate: function(value, row, index) {
		var date = outw.timestampToDate(value);
		return date && date.format('yyyy-MM-dd hh:mm:ss') || '';
	},
	formatStrToDate: function(time) {
		return time && new Date(time.replace(new RegExp("\\-","gi"), "/")) || null;
	},
	linkbuttonHtml: function(clickHtml, text, title, clsClass) {
		return '<a href="javascript:void(0);" class="easyui-linkbutton outw-action ' + (clsClass || '') + '"'
			 + (!!clickHtml ? (' onClick="' + clickHtml + '"') : '') + ' data-options="plain: true" title="' + (title || '') + '">' + text + '</a>';
	},
	showOperateOptions: function(value, row, index) {
		var status = row['status']
			//, sendable = outw.status.abnormal === status
			, editable = outw.status.pending === status || outw.status.abnormal === status
			, l_btn_disabled = editable ? '' : ' l-btn-disabled';
		return outw.linkbuttonHtml('outw.action.info(' + index + ')', '详情', '查看出库单详细信息', 'info')
		 	 + outw.linkbuttonHtml(editable ? ('outw.action.modify(' + index + ')') : '', '修改', '修改当前行出库单相关信息', 'modify' + l_btn_disabled)
			 //+ outw.linkbuttonHtml(sendable ? ('outw.action.push(' + index + ')') : '', '推送', '将当前行出库单推送至4PX', 'send' + (sendable ? '' : ' l-btn-disabled'))
		 	 + outw.linkbuttonHtml(editable ? ('outw.action.push(' + index + ')') : '', '推送', '将当前行出库单推送至4PX', 'send' + (editable ? '' : ' l-btn-disabled'))
			 + outw.linkbuttonHtml(editable ? ('outw.action.cancel(' + index + ')') : '', '取消', '取消（即关闭）当前行出库单', 'cancel' + l_btn_disabled);
	},
	ajax: function(relativeUrl, data, successCallback, errorCallback, type, dataType, async) {
		$.ajax({
			url : GLOBAL.domain + relativeUrl,
			data: data,
			type: type || 'post',
			dataType: dataType || 'json',
			async: 'undefined' === typeof async ? true : async,
			success: successCallback,
		    error: 'function' === typeof errorCallback && errorCallback || outw.failedAlert
		});
	},
	collectForm: function(formSelector) {
		var data = {};
		$.each($(formSelector).serializeArray(), function() {
			data[this.name] = this.value;
		});
		return data;
	},
	orderLink: function(value, row, index) {
		return '<a href="javascript:void(0)" class="order-link" title="查看出（入）库单详细信息">' + value + '</a>';
	},
	imitateTooltip: function(value, row, index) {
		return '<span class="imitate-tooltip">' + value + '</span>';
	},
	clearForm: function(formSelector) {
		$(formSelector).form("clear");
	},
	seeAbnormals: function(index) {
		var $ar = $('#outw_ar')
			, currRow = $("#dg_outw").datagrid('getRows')[index];
		
		$ar.find('table.dg-abnormal').datagrid({
			url: '/ocs/fourpx/abnormal/list',
			queryParams: {
				param: { parentId: currRow['id'], parentType: 'out-warehouse' }
			},
			columns: [[
				{ field: 'id', hidden: true }
				, { field: 'parentOrderId', title: '单号', halign: 'center', align: 'left', width: 120, styler: outw.textIndent5px, formatter: outw.orderLink }
				, { field: 'action', title: '产生源', halign: 'center', width: 120, align: 'left', styler: outw.textIndent5px  }
				, { field: 'reason', title: '具体原因', halign: 'center', width: 300, align: 'left', styler: outw.textIndent5px, formatter: outw.imitateTooltip }
				, { field: 'createdat', title: '产生时间', align: 'center', width: 130, formatter: outw.formatDate }
			]],
			singleSelect: true,
			rownumbers: true,
			border: true,
			pagination: false,
			pageSize: 50,
			showFooter: false,
			height: 300,
			emptyMsg: '<h3>没有数据</h3>',
			onLoadSuccess: function(data) {
				$ar.find('.imitate-tooltip').each(function() {
					$(this).tooltip({
					    position: 'bottom',
					    content: '<span style="color: black;">' + $(this).text() + '</span>',
					    onShow: function() {
					        $(this).tooltip('tip').css({ backgroundColor: '#FFE48D', borderColor: '#95B8E7' });
					    }
					});
				})

				$ar.find('.order-link').bind('click', function() { outw.action.info(index); });
			}
		});
		
		$ar.dialog('open');
	},
	seeDetails: function(index, editable) {
		var $dialog = $('#outw-details')
			, rows = $("#dg_outw").datagrid('getRows')
			, row = rows[index]
			, $main = $dialog.find('table.main');
		
		$dialog.find('form input[type="hidden"]').each(function() {
			var name = $(this).attr('name');
			if(name) {
				$(this).val(null !== row[name] && undefined !== row[name] && row[name] || '');
			}
		});
		
		var $td, text, value;
		for(var name in row) {
			if(!outw.belongDetail(name)) {
				$td = $main.find('td.content.' + name).empty();
				value = row[name] || '';
				text = outw.formatText(value, row, index, name);
				if($td.length) {
					if(editable) {
						if(outw.isEditable(name)) {
							$td.append('<input class="easyui-textbox" type="text" name="' + name + '" value="' + text + '" />');
						} else {
							$td.append('<span class="underline" for="' + name + '">' + text + '</span>');
							$td.append('<input type="hidden" name="' + name + '" value="' + value + '" />');
						}
					} else {
						$td.append('<span class="underline" for="' + name + '">' + text + '</span>');
					}

					//有可能出库单目前状态正常，但是以前出现过异常情况，所以在此提供查看出库单历史异常信息的入口
					if(outw.status.abnormal !== value && 'status' === name) {
						$td.append(outw.linkbuttonHtml('outw.seeAbnormals(' + index + ')', '异常历史？', '可能以往发生过异常，点击查看异常详情', 'abnormal-history'));
					}
				}
			}
		}
		
		var detailColumns = outw.detailDatagridColumns()
			, mergeRow = outw.cache.mergeRow[row['id']]
			, detailData = [];
		
		for(var i = index, l = index + (mergeRow && mergeRow.rowspan || 1), tmpd = null; i < l; i++) {
			tmpd = {};
			for (var j = 0, k = detailColumns.length; j < k; j++) {
				tmpd[detailColumns[j].field] = rows[i][detailColumns[j].field];
			}
			detailData.push(tmpd);
		}
		
		$dialog.find('table.detail').datagrid({
			columns: [ detailColumns ],
			data: detailData,
			singleSelect: true,
			rownumbers: true
		});

		$('div.outw-details .editable-show')[editable ? 'show' : 'hide']();
		$main.find('input.easyui-textbox').textbox({ width: 200 });
		$dialog.dialog('open');
	},
	hasChanges: function() {
		return true;
	},
	action: {
		info: function(index) {
			outw.seeDetails(index, false);
		},
		modify: function(index) {
			outw.seeDetails(index, true);
		},
		closeDetails: function() {
			$('#outw-details').window('close');
		},
		closeAbnormal: function() {
			$('#outw_ar').window('close');
		},
		saveChanges: function() {
			if(!outw.hasChanges()) {
				$.messager.alert('提示', '数据没有发生变更，不需要保存！', 'info');
				return;
			}

			$('#outw-details').find('form').form('submit', {
			    onSubmit: function(param) {
			        // do some check
			        // return false to prevent submit;
			    },
			    success: function(response) {
					var data = JSON.parse(response);
					outw.alert(data);
					if (data && data.errorCode == 0) {
						outw.action.closeDetails();
						$("#dg_outw").datagrid('clearSelections').datagrid("reload");
					}
			    }
			});
		},
		push: function(index) {
			outw.confirm('您确认要将当前出库单推送至4PX吗？', function() {
				var $dg = $("#dg_outw")
					, row = $dg.datagrid('getRows')[index]
					, data = { id: row['id'] };
				outw.progress();
				outw.ajax('/fourpx/outw/push', data, function(data) {
					outw.closeProgress();
					outw.alert(data);
					$dg.datagrid('clearSelections').datagrid("reload");
				}, function() {
					outw.closeProgress();
					outw.errorCallback();
				});
			});
		},
		cancel: function(index) {
			outw.confirm('被取消的出库单将成为废单，无法再继续操作。您确认要将当前出库单取消吗？', function() {
				var $dg = $("#dg_outw")
					, row = $dg.datagrid('getRows')[index]
					, data = { id: row['id'] };
				outw.ajax('/fourpx/outw/cancel', data, function(data) {
					outw.closeProgress();
					outw.alert(data);
					$dg.datagrid('clearSelections').datagrid("reload");
				});
			});
		},
		search: function() {
			var param = outw.collectForm('#form_search_outw');
			$("#dg_outw").datagrid({
				queryParams:{
					param :param
				}
			});
		},
		syncOutFromUnshippedOrder: function() {
			outw.confirm('同步未发货订单到4PX管理平台形成待推送出库单数据，您确认要执行此操作吗？', function() {
				outw.progress();
				outw.ajax('/fourpx/outw/syncOutsFromOrder', {}, function(resp) {
					outw.closeProgress();
					outw.alert(outw.formatAjaxResponse(resp));
					outw.action.search();
				}, function() {
					outw.closeProgress();
					outw.failedAlert();
				});
			});
		},
		batchPush4px: function() {
			outw.confirm('将OMS待推送出库单推送至4PX平台，您确认要执行此操作吗？', function() {
				outw.progress();
				outw.ajax('/fourpx/outw/batchPush', {}, function(resp) {
					outw.closeProgress();
					outw.alert(outw.formatAjaxResponse(resp));
					outw.action.search();
				}, function() {
					outw.closeProgress();
					outw.failedAlert();
				});
			});
		},
		syncOutFrom4px: function() {
			outw.confirm('将4PX出库单的状态等相关信息同步到OMS（即更新OMS对应4PX出库单、源订单状态相关信息），您确认要执行此操作吗？', function() {
				outw.progress();
				outw.ajax('/fourpx/outw/syncOutFrom4px', {}, function(resp) {
					outw.closeProgress();
					outw.alert(outw.formatAjaxResponse(resp));
					outw.action.search();
				}, function() {
					outw.closeProgress();
					outw.failedAlert();
				});
			});
		}
	},
	formatAjaxResponse: function(resp) {
		if(resp.data && 'object' === typeof resp.data) {
			var s = resp.data.success
				, sl = s && s.length || 0
				, sm = sl ? s.join(' ') : ''
				, f = resp.data.failed
				, fl = f && f.length || 0
				fm =  fl ? f.join(' ') : '';
			resp.description = '同步4PX库存流水：总共' + (sl + fl) + '条，成功' + sl + '条，失败' + fl + '条。';
			resp.description += '</br>';
			resp.description += sl ? ('同步成功详情：</br>' + sm) : '';
			resp.description += '</br>';
			resp.description += fl ? ('同步失败详情：</br>' + fm) : '';
		}
		return resp;
	},
	formatText: function(value, row, index, field) {
		for (var i = 0, l = outw.datagridColumns.length, col = null; i < l; i++) {
			col = outw.datagridColumns[i];
			if(col.field === field) {
				if(col.formatter) {
					return col.formatter(value, row, index);
				}
				break;
			}
		}
		return value;
	},
	detailColumns: null,
	detailDatagridColumns: function() {
		if(null !== outw.detailColumns) {
			return outw.detailColumns;
		}
		
		outw.detailColumns = [ { field: 'detailId', hidden: true } ];
		for(var i = 0, l = outw.datagridColumns.length; i < l; i++) {
			if(outw.belongDetail(outw.datagridColumns[i].field)) {
				outw.detailColumns.push($.extend(true, {}, outw.datagridColumns[i], { hidden: false }));
			}
		}
		return outw.detailColumns;
	}
};

(function(outw, $) {
	outw.ajax('/fourpx/outw/enums', {}, function(resp) {
		outw.enums = resp;
		outw.status = {};
		for(var key in outw.enums['status']) {
			outw.status[key] = key;
		}
		
		outw['showFpxstatus'] = function(value, row, index) {
			return value && (value + ' - ' + outw.enums['fpxstatus'][value]) || '';
		}

		outw['showStatus'] = function(value, row, index) {
			var text = outw.enums['status'][value];
			return value !== 'abnormal' ?  text : outw.linkbuttonHtml('outw.seeAbnormals(' + index + ')', text, '点击查看异常原因', 'abnormal');
		}
	}, function() {
		outw.failedAlert();
	}, 'post', 'json', false);

	outw.datagridColumns = [
		{ field: 'id', hidden: true }
		, { field: 'operate', title: '操作', halign: 'center', align: 'center', width: 130, formatter: outw.showOperateOptions }
		, { field: 'platform', title: '平台', halign: 'center', align: 'center', width: 60 }
		, { field: 'referenceCode', title: '订单参考号', halign: 'center', align: 'left', width: 110 }
		, { field: 'warehouseCode', title: '仓库代码', halign: 'center', align: 'center', width: 60 }
		, { field: 'status', title: '业务状态', halign: 'center', align: 'center', width: 60, formatter: outw.showStatus }
		, { field: 'carrierCode', title: '渠道代号', halign: 'center', align: 'center', width: 60 }
		, { field: 'insureType', title: '保险类型', halign: 'center', align: 'center', width: 60, hidden: true }
		, { field: 'remoteArea', title: '是否ODA', halign: 'center', align: 'center', width: 60, hidden: true }
		, { field: 'weight', title: '重量', halign: 'center', align: 'right', width: 60 }
		, { field: 'sku', title: 'SKU', halign: 'center', align: 'left', width: 100 }
		, { field: 'quantity', title: 'SKU数量', halign: 'center', align: 'right', width: 60 }
		, { field: 'skuLabelCode', title: 'skuLabelCode', halign: 'center', align: 'left', width: 130, hidden: true }
		, { field: 'skuValue', title: 'skuValue', halign: 'center', align: 'left', width: 60, hidden: true }
		, { field: 'fullName', title: '收件人', halign: 'center', align: 'left', width: 120 }
		, { field: 'countryCode', title: '国家', halign: 'center', align: 'center', width: 50 }
		, { field: 'state', title: '省份', halign: 'center', align: 'center', width: 50, hidden: true }
		, { field: 'city', title: '城市', halign: 'center', align: 'left', width: 80, hidden: true }
		, { field: 'street', title: '街道', halign: 'center', align: 'left', width: 150 }
		, { field: 'postalCode', title: '邮编', halign: 'center', align: 'center', width: 60, hidden: true }
		, { field: 'phone', title: '联系电话', halign: 'center', align: 'center', width: 80, hidden: true }
		, { field: 'createdat', title: '创建时间', halign: 'center', align: 'center', width: 130, formatter: outw.formatDate }
		, { field: 'updatedat', title: '最后更新时间', halign: 'center', align: 'center', width: 130, formatter: outw.formatDate, hidden: true }
		, { field: 'pushedat', title: '推送时间', halign: 'center', align: 'center', width: 130, formatter: outw.formatDate }
		, { field: 'feedbackat', title: '反馈时间', halign: 'center', align: 'center', width: 130, formatter: outw.formatDate }
		, { field: 'documentCode', title: '4PX参考号', halign: 'center', align: 'left', width: 130 }
		, { field: 'fpxstatus', title: '4PX业务状态', halign: 'center', align: 'center', width: 80, formatter: outw.showFpxstatus }
	];
	
	$("#dg_outw").datagrid({
		url: '/ocs/fourpx/outw/list',
		queryParams: {
			param: outw.collectForm('#form_search_outw')
		},
		columns: [ outw.datagridColumns ],
		singleSelect: true,
		rownumbers: true,
		border: true,
		pagination: true,
		pageSize: 50,
		showFooter: true,
		onLoadSuccess: function(data) {
			$('a.outw-action').css('margin-left', '3px').css('margin-right', '3px');
			$(".datagrid-ftable tbody tr").each(function() {
			    this.style.backgroundColor="#E1EDFF";
			    this.style.color="blue";
			    this.style.fontWeight="bold";
			});
			
			outw.cache.mergeRow = {};
			for(var i = 1, l = data.rows.length; i < l; i++) {
				var id = data.rows[i]['id'];
				if(id === data.rows[i - 1]['id']) {
					if(!outw.cache.mergeRow[id]) {
						outw.cache.mergeRow[id] = { index: i - 1, rowspan: 1 };
					}
					outw.cache.mergeRow[id].rowspan++;
				}
			}
			
			for(var k1 in outw.cache.mergeRow) {
				for(var i = 0, l = outw.datagridColumns.length; i < l; i++) {
					var field = outw.datagridColumns[i].field;
					if(!outw.belongDetail(field)) {
						$(this).datagrid('mergeCells', {
							index: outw.cache.mergeRow[k1].index,
							field: field,
							rowspan: outw.cache.mergeRow[k1].rowspan
						});
					}
				}
			}
		},
		toolbar: [{
			text: '导出',
			iconCls: 'icon-redo',
			handler: function() {
				var downloadUrl = "/ocs/excel/dynamicExport/fpxOutWarehouseExport"
					, param = outw.collectForm('#form_search_outw')
					, maxDays = 31
					, beginCreatedat = outw.formatStrToDate(param["beginCreatedat"])
					, endCreatedat = outw.formatStrToDate(param["endCreatedat"])
					, beginPushedat = outw.formatStrToDate(param["beginPushedat"])
					, endPushedat = outw.formatStrToDate(param["endPushedat"])
					, beginFeedbackat = outw.formatStrToDate(param["beginFeedbackat"])
					, endFeedbackat = outw.formatStrToDate(param["endFeedbackat"])
					, beginAbnormalat = outw.formatStrToDate(param["beginAbnormalat"])
					, endAbnormalat = outw.formatStrToDate(param["endAbnormalat"])
					, validInterval = (beginCreatedat && endCreatedat && (endCreatedat.getTime() - beginCreatedat.getTime()) < maxDays * 24 * 60 * 60 * 1000)
								   		|| (beginPushedat && endPushedat && (endPushedat.getTime() - beginPushedat.getTime()) < maxDays * 24 * 60 * 60 * 1000)
								   		|| (beginFeedbackat && endFeedbackat && (endFeedbackat.getTime() - beginFeedbackat.getTime()) < maxDays * 24 * 60 * 60 * 1000)
								   		|| (beginAbnormalat && endAbnormalat && (endAbnormalat.getTime() - beginAbnormalat.getTime()) < maxDays * 24 * 60 * 60 * 1000);
				
				if(!validInterval) {
					$.messager.alert('提示', '时间段过大，最多一次只能导出' + maxDays + '天数据，请分段导出！', 'info');
					return;
				}
				
				var paramUrl = '?';
				for(var key in param) {
					paramUrl += key + '=' + param[key] + '&';
				}
				
				var includeFields = 'abnormal';
				for(var i = 0, l = outw.datagridColumns.length; i < l; i++) {
					includeFields += ',' + outw.datagridColumns[i].field;
				} 
				paramUrl += 'includeFields=' + includeFields;

				window.location.href = downloadUrl + paramUrl;
			}
		}, {
			text: '导出（基于US线下发货模板）',
			iconCls: 'icon-redo',
			handler: function() {
				var downloadUrl = "/ocs/excel/export/fpxOutWarehouseOfflineExport"
					, param = outw.collectForm('#form_search_outw');
				
				var paramUrl = '?';
				for(var key in param) {
					paramUrl += key + '=' + param[key] + '&';
				}

				window.location.href = downloadUrl + paramUrl.substr(0, paramUrl.length - 1);
			}
		}, {
			text: '同步待推送订单到OMS',
			iconCls: 'icon-update',
			handler: outw.action.syncOutFromUnshippedOrder
		}, {
			text: '批量推送出库单到4PX',
			iconCls: 'icon-update',
			handler: outw.action.batchPush4px
		}, {
			text: '同步4PX订单状态到OMS',
			iconCls: 'icon-update',
			handler: outw.action.syncOutFrom4px
		}]
	});
	
	$("#btn_outw_search").bind('click', outw.action.search);
	$("#btn_outw_reset").bind('click', function() { outw.clearForm('#form_search_outw'); });

	$(document).ready(function() {
		$('#form_search_outw table tr td.title').css('padding-left', '10px');
		var buttons = $.extend([], $.fn.datebox.defaults.buttons);
		buttons.splice(0, 0, {
			text: '清空',
			handler: function(target) {
				$(target).datebox('clear').datebox("panel").panel('close');
			}
		});
		$(".easyui-datebox").datebox({ buttons: buttons });
	});
	
})(outw, jQuery);