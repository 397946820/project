var deoutorder = {
	$: {
		dgdataset: null,
		dialogInfo: null,
		dialogAR: null
	},
	cache: {
		mergeRow: {},
		main: {
			id: { field: 'id', hidden: true },
			operate: { field: 'operate', title: '操作', halign: 'center', align: 'center', width: 130, formatter: function(v, r, i) { return deoutorder.showOperateOptions(v, r, i); } },
			platform: { field: 'platform', title: '来源', halign: 'center', align: 'center', width: 50, formatter: function(v, r, i) { return deoutorder.showPlatform(v, r, i); } },
			orderOcsId: { field: 'orderOcsId', title: 'OrderOcsId', halign: 'center', align: 'center', width: 80 },
			orderId: { field: 'orderId', title: 'OrderId', halign: 'center', align: 'left', width: 200, styler: function(v, r, i) { return deoutorder.textIndent5px(v, r, i); } },
			isSendWms: { field: 'isSendWms', title: '状态', halign: 'center', align: 'center', width: 50, formatter: function(v, r, i) { return deoutorder.showStatus(v, r, i); } },
			isUpload: { field: 'isUpload', title: '跟踪号状态', halign: 'center', align: 'center', width: 90, formatter: function(v, r, i) { return deoutorder.showIsUpload(v, r, i); } },
			trackingNumber: { field: 'trackingNumber', title: '跟踪号', halign: 'center', align: 'left', width: 150, styler: function(v, r, i) { return deoutorder.textIndent5px(v, r, i); } },
			ocsOrderCreateDate: { field: 'ocsOrderCreateDate', title: '订单创建日期', halign: 'center', align: 'center', width: 130, formatter: function(v, r, i) { return deoutorder.formatDate(v, r, i); } },
			sendDate: { field: 'sendDate', title: '推送WMS日期', halign: 'center', align: 'center', width: 130, formatter: function(v, r, i) { return deoutorder.formatDate(v, r, i); } },
			feedbackDate: { field: 'feedbackDate', title: 'WMS反馈日期', halign: 'center', align: 'center', width: 130, formatter: function(v, r, i) { return deoutorder.formatDate(v, r, i); } },
			createdDate: { field: 'createdDate', title: '创建时间', halign: 'center', align: 'center', width: 130, formatter: function(v, r, i) { return deoutorder.formatDate(v, r, i); } },
			updateDate: { field: 'updateDate', title: '更新时间', halign: 'center', align: 'center', width: 130, formatter: function(v, r, i) { return deoutorder.formatDate(v, r, i); } },
			shipDate: { field: 'shipDate', title: '发货时间', halign: 'center', align: 'center', width: 130, formatter: function(v, r, i) { return deoutorder.formatDate(v, r, i); } },
			shipBy: { field: 'shipBy', title: '操作人', halign: 'center', align: 'left', width: 70, styler: function(v, r, i) { return deoutorder.textIndent5px(v, r, i); } },
			storeCode: { field: 'storeCode', title: '发货仓库代码' },
			carrierId: { field: 'carrierId', title: '物流承运商代码' },
			customerName: { field: 'customerName', title: '客户名称', editable: true },
			customerCountry: { field: 'customerCountry', title: '客户国家', editable: true },
			customerProvince: { field: 'customerProvince', title: '客户省份', editable: true },
			customerCity: { field: 'customerCity', title: '客户城市', editable: true },
			customerZip: { field: 'customerZip', title: '客户邮编', editable: true },
			customerEmail: { field: 'customerEmail', title: '客户Email', editable: true },
			customerPhone: { field: 'customerPhone', title: '客户电话', editable: true },
			customerAddress: { field: 'customerAddress', title: '客户地址1', editable: true },
			customerAddress2: { field: 'customerAddress2', title: '客户地址2', editable: true }
		},
		detail: {
			detailId: { field: 'detailId', hidden: true },
			sku: { field: 'sku', title: 'SKU', halign: 'center', align: 'left', width: 130, styler: function(v, r, i) { return deoutorder.textIndent5px(v, r, i); } },
			itemQty: { field: 'itemQty', title: 'SKU数量', halign: 'center', align: 'right', width: 60 },
			skuProperty: { field: 'skuProperty', title: '货品属性', halign: 'center', align: 'left', width: 80 },
			item: { field: 'item', title: '版本', halign: 'center', align: 'left', width: 100, styler: function(v, r, i) { return deoutorder.textIndent5px(v, r, i); } },
			actualQty: { field: 'actualQty', title: '实际SKU数量', halign: 'center', align: 'right', width: 80  },
			price: { field: 'price', title: 'SKU售价', halign: 'center', align: 'right', width: 80  }
		}
	},
	infoRender: {
		mainSort: [ 
			'platform', 'orderOcsId', 'orderId', 'ocsOrderCreateDate', 'isSendWms', 'sendDate', 'isUpload', 'trackingNumber', 'feedbackDate',
			'createdDate', 'updateDate', 'shipDate', 'shipBy', 'storeCode', 'carrierId', 'customerName', 'customerCountry', 'customerProvince',
			'customerCity', 'customerZip', 'customerEmail', 'customerPhone', 'customerAddress', 'customerAddress2'
		],
		getCache: function() {
			var cache = deoutorder.cache['currInfo'];
			if(!cache) {
				cache = [];
				deoutorder.cache['currInfo'] = cache;
			}
			return cache;
		},
		caching: function(dataRow) {
			var currInfo = deoutorder.infoRender.getCache()
				, idField = deoutorder.cache.detail.detailId.field
				, exists = false;
			for(var i = 0, l = currInfo.length; i < l; i++) {
				if(dataRow[idField] === currInfo[i][idField]) {
					exists = true;
					break;
				}
			}
			if(!exists) {
				currInfo.push(dataRow);
			}
		},
		cleanCache: function() {
			deoutorder.cache.currInfo = [];
		}
	},
	isDetailField: function(field) {
		for(var key in deoutorder.cache.detail) {
			if(deoutorder.cache.detail[key].field === field) {
				return true;
			}
		}
		return false;
	},
	timestampToDate: function(timestamp) {
		return !timestamp ? null : new Date(timestamp);
	},
	formatStrToDate: function(time) {
		return time && new Date(time.replace(new RegExp("\\-","gi"), "/")) || null;
	},
	textIndent5px: function(value, row, index) {
		return 'text-indent: 5px;';
	},
	formatDate: function(value, row, index) {
		var date = deoutorder.timestampToDate(value);
		return date && date.format('yyyy-MM-dd hh:mm:ss') || '';
	},
	showPlatform: function(value, row, index) {
		if('1' === value) {
			return '官网';
		} else if('2' === value) {
			return 'ebay';
		} else if('3' === value) {
			return '亚马逊';
		} else if('5' === value) {
			return 'VC';
		} else {
			return '';
		}
	},
	showIsUpload: function(value, row, index) {
		if('0' === value) {
			return '尚未上传';
		} else if('1' === value) {
			return '已经上传';
		} else if('2' === value) {
			return '上传失败';
		} else if('3' === value) {
			return '关闭上传';
		} else {
			return '';
		}
	},
	showStatus: function(value, row, index) {
		if('0' === value) {
			return '待推送';
		} else if('1' === value) {
			return '已推送';
		} else if('2' === value) {
			return '已反馈';
		} else if('3' === value) {
			return deoutorder.linkbuttonHtml('deoutorder.reanderAbnormalReason(' + index + ')', '异常单', '点击查看异常原因', 'abnormal');
		} else if('4' === value) {
			return '已取消';
		} else {
			return '';
		}
	},
	linkbuttonHtml: function(clickHtml, text, title, clsClass) {
		return '<a href="javascript:void(0);" class="easyui-linkbutton deoutorder-action ' + (clsClass || '') + '"'
			 + (!!clickHtml ? (' onClick="' + clickHtml + '"') : '') + ' data-options="plain: true" title="' + (title || '') + '">' + text + '</a>';
	},
	showOperateOptions: function(value, row, index) {
		var status = row[deoutorder.cache.main.isSendWms.field]
			, editable = '0' === status || '3' === status
			, sendable = editable//'3' === status
			, l_btn_disabled = editable ? '' : ' l-btn-disabled';
		return deoutorder.linkbuttonHtml('deoutorder.action.info(' + index + ')', '详情', '查看出库单详细信息', 'info')
		 	 + deoutorder.linkbuttonHtml(editable ? ('deoutorder.action.modify(' + index + ')') : '', '修改', '修改当前行出库单相关信息', 'modify' + l_btn_disabled)
			 + deoutorder.linkbuttonHtml(sendable ? ('deoutorder.action.send(' + index + ')') : '', '推送', '将当前行出库单推送至WMS仓库', 'send' + (sendable ? '' : ' l-btn-disabled'))
			 + deoutorder.linkbuttonHtml(editable ? ('deoutorder.action.cancel(' + index + ')') : '', '取消', '取消（即关闭）当前行出库单', 'cancel' + l_btn_disabled);
	},
	omit: function(value, max) {
		if(!value || value.length < max) {
			return value;
		}
		return value.substr(0, max) + ' ' + deoutorder.imitateTooltip(value, '......');
	},
	imitateTooltip: function(content, value) {
		return '<span style="display: none;">' + content + '</span><a href="javascript:void(0)" class="imitate-tooltip">' + value + '</a>';
	},
	omitActionText: function(value, row, index) {
		return deoutorder.omit(value, 5);
	},
	omitReason: function(value, row, index) {
		return deoutorder.omit(value, 28);
	},
	orderLink: function(value, row, index) {
		return '<a href="javascript:void(0)" class="order-link" title="查看出库单详细信息">' + value + '</a>';
	},
	humpToUnderline: function(humpValue) {
		if(!humpValue) {
			return humpValue;
		}
		var field = '';
		for(var i = 0; i < humpValue.length; i++) {
			var c = humpValue.charAt(i);
			//数字也将与大写一样（类似"驼峰"）需要在前面加下划线
			if((c >= 'A' && c <= 'Z') || !isNaN(parseInt(c, 10))) {
				field += '_';
			}
			field += c.toLowerCase();
		}
		return field;
	},
	reanderAbnormalReason: function(rowIndex) {
		if(!deoutorder.$.dialogAR) {
			deoutorder.$.dialogAR = $('#deoutorder_ar');
		}
		
		var $ar = deoutorder.$.dialogAR
			, currRow = deoutorder.$.dgdataset.datagrid('getRows')[rowIndex];
		
		$ar.find('table.reasons-list').datagrid({
			url: '/ocs/wms/deoutorder/abnormalReasonList',
			queryParams: {
				param: { parentId: currRow[deoutorder.cache.main.id.field], parentType: 'out' }
			},
			columns: [[
				{ field: 'id', hidden: true }
				, { field: 'parentOrderId', title: '原始订单OrderId', halign: 'center', align: 'left', width: 200, styler: deoutorder.textIndent5px, formatter: deoutorder.orderLink }
				//, { field: 'parentTypeText', title: '类别', align: 'center', width: 50 }
				, { field: 'actionText', title: '产生源', halign: 'center', width: 150, align: 'left', styler: deoutorder.textIndent5px, formatter: deoutorder.omitActionText }
				, { field: 'reason', title: '具体原因', halign: 'center', width: 300, align: 'left', styler: deoutorder.textIndent5px, formatter: deoutorder.omitReason }
				, { field: 'createdAt', title: '产生时间', align: 'center', width: 130, formatter: deoutorder.formatDate }
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
					    content: '<span style="color: black;">' + $(this).prev().text() + '</span>',
					    onShow: function() {
					        $(this).tooltip('tip').css({ backgroundColor: '#FFE48D', borderColor: '#95B8E7' });
					    },
					    showEvent: 'click',
					    hideEvent: 'click'
					});
				}).attr('title', '单击显示/隐藏详情').unbind('click').bind('click', function() {
					var status = $(this).data('tooltip-current-status');
					if('show' === status) {
						$(this).tooltip('hide').data('tooltip-current-status', 'hide');
					} else {
						$(this).tooltip('show').data('tooltip-current-status', 'show');
					}
				});
				
				$ar.find('.order-link').bind('click', function() { deoutorder.action.info(rowIndex); });
			}
		});
		$ar.dialog('open');
	},
	renderingInfo: function(rowIndex, editable) {
		if(!deoutorder.$.dialogInfo) {
			deoutorder.$.dialogInfo = $("#deoutorder_info");
		}
		
		var $info = deoutorder.$.dialogInfo
			, $form = $info.find('form')
			, $main = $form.find('table.main')
			, rows = deoutorder.$.dgdataset.datagrid('getRows')
			, currRow = rows[rowIndex]
			, mainSort = deoutorder.infoRender.mainSort
			, htmls = []
			, columnCount = 2;

		deoutorder.infoRender.cleanCache();
		$main.find("tr").remove();
		for(var i = 0, l = mainSort.length; i < l; i++) {
			if(i % columnCount == 0) {
				htmls.push('<tr>');
			}
			if(mainSort[i]) {
				var column = deoutorder.cache.main[mainSort[i]]
					, tmpv = currRow[column.field]
					, value = 'undefined' === typeof tmpv || null == tmpv ? '' : tmpv
					, hasFormatter = 'function' === typeof column.formatter
					, text = hasFormatter ? column.formatter(value, currRow, rowIndex) : value
					, isStautsField = column.field === deoutorder.cache.main.isSendWms.field
					, cancel_underline = isStautsField ? ' cancel-underline' : ''
					, underline = isStautsField ? 'underline' : '';
					
				htmls.push('<td class="title">');
				htmls.push('<label for="' + column.field + '">' + column.title + '：</label>');
				htmls.push('</td>');
				htmls.push('<td class="content' + cancel_underline + '">');
				if(editable && column.editable) {
					if(!hasFormatter) {
						htmls.push('<input class="easyui-textbox" type="text" name="' + column.field + '" value="' + text + '" />');
					} else {
						//等待需要时实现....
						//htmls.push('<input class="easyui-textbox" type="text" name="' + column.field + '_text" value="' + text + '" />');
						//htmls.push('<input type="hidden" name="' + column.field + '" value="' + value + '" />');
					}
				} else {
					htmls.push('<span class="' + underline + '" for="' + column.field + '">' + text + '</span>');
					if(editable) {
						htmls.push('<input type="hidden" name="' + column.field + '" value="' + value + '" />');
					} 
					//有可能出库单目前状态正常，但是以前出现过异常情况，所以在此提供查看出库单历史异常信息的入口
					if('0' !== value && '3' !== value && isStautsField) {
						htmls.push(deoutorder.linkbuttonHtml('deoutorder.reanderAbnormalReason(' + rowIndex + ')', '异常历史？', '可能以往发生过异常，点击查看异常详情', 'ar-history'));
					}
				}
				htmls.push('</td>');
			} else {
				htmls.push('<td></td><td></td>');
			}
			if(i > (columnCount - 1) && i % columnCount === (columnCount - 1)) {
				htmls.push('</tr>');
			}
		}
		$form.find('input[name="id"]').val(currRow[deoutorder.cache.main.id.field]);
		$main.append(htmls.join(''));
		$main.find('input.easyui-textbox').textbox({ width: 200 });
		
		var detail = deoutorder.cache.detail
			, detailColumns = [[ detail.detailId, detail.sku, detail.price, detail.itemQty, detail.actualQty, detail.skuProperty, detail.item ]]
			, orderId = currRow[deoutorder.cache.main.orderId.field]
			, mergeRow = deoutorder.cache.mergeRow[orderId]
			, detailData = [];
		
		for(var i = rowIndex, l = rowIndex + (mergeRow && mergeRow.rowspan || 1), tmpd = null; i < l; i++) {
			tmpd = {};
			for(var key in detail) {
				tmpd[detail[key].field] = rows[i][detail[key].field];
			}
			detailData.push(tmpd);
			deoutorder.infoRender.caching(rows[i]);
		}
		
		var $detail = $form.find('table.detail').datagrid({
			columns: detailColumns,
			data: detailData,
			singleSelect: true,
			rownumbers: true
		});
		
		var $editable = $('div.out-info-btns > .editable-show');
		editable ? $editable.show() : $editable.hide();
		$info.dialog("open");
	},
	busEquals: function(o1, o2) {
		return (!o1 && !o2) || o1 == o2;
	},
	hasChanges: function() {
		var cache = deoutorder.infoRender.getCache()
			, flag = false;
		deoutorder.$.dialogInfo.find('form input[name]').each(function() {
			if(!deoutorder.busEquals($(this).val(), cache[0][$(this).attr('name')])) {
				flag = true
				return false;
			}
		});
		return flag;
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
	ajax: function(relativeUrl, data, successCallback, errorCallback, type, dataType, async) {
		$.ajax({
			url : GLOBAL.domain + relativeUrl,
			data: data,
			type: type || 'post',
			dataType: dataType || 'json',
			async: 'undefined' === typeof async ? true : async,
			success: successCallback,
		    error: 'function' === typeof errorCallback && errorCallback || deoutorder.errorCallback
		});
	},
	errorCallback: function() {
		$.messager.alert('消息提示', '操作失败', 'warning');
	},
	dgdatasetOperSuccessCallback: function(data) {
		$.messager.alert('消息提示', data.description || 'Unknown', data && data.errorCode == 0 ? 'info' : 'warning');
		deoutorder.$.dgdataset.datagrid('clearSelections').datagrid("reload");
	},
	progress: function() {
		$.messager.progress({ interval: 500, msg: '正在处理，请稍后...' });
	},
	closeProgress: function() {
		$.messager.progress('close');
	},
	action: {
		info: function(rowIndex) {
			deoutorder.renderingInfo(rowIndex, false);
		},
		modify: function(rowIndex) {
			deoutorder.renderingInfo(rowIndex, true);
		},
		send: function(rowIndex) {
			deoutorder.confirm('您确认要将当前出库单推送至WMS仓库吗？', function() {
				var main = deoutorder.cache.main 
					, row = deoutorder.$.dgdataset.datagrid('getRows')[rowIndex]
					, data = { id: row[main.id.field], orderId: row[main.orderId.field], orderOcsId: row[main.orderOcsId.field] };
				deoutorder.progress();
				deoutorder.ajax('/wms/deoutorder/send', data, function(data) {
					deoutorder.closeProgress();
					deoutorder.dgdatasetOperSuccessCallback(data);
				}, function() {
					deoutorder.closeProgress();
					deoutorder.errorCallback();
				});
			});
		},
		cancel: function(rowIndex) {
			deoutorder.confirm('被取消的出库单将成为废单，无法再继续操作。您确认要将当前出库单取消吗？', function() {
				var main = deoutorder.cache.main 
					, row = deoutorder.$.dgdataset.datagrid('getRows')[rowIndex]
					, data = { id: row[main.id.field], orderId: row[main.orderId.field], orderOcsId: row[main.orderOcsId.field] };
				deoutorder.ajax('/wms/deoutorder/cancel', data, deoutorder.dgdatasetOperSuccessCallback);
			});
		},
		saveChanges: function() {
			if(!deoutorder.hasChanges()) {
				$.messager.alert('提示', '数据没有发生变更，不需要保存！', 'info');
				return;
			}

			//var formData = deoutorder.$.dialogInfo.find('form').serializeArray();
			deoutorder.$.dialogInfo.find('form').form('submit', {
			    onSubmit: function(param) {
			        // do some check
			        // return false to prevent submit;
			    },
			    success: function(response) {
					var data = JSON.parse(response);
					if (data && data.errorCode == 0) {
						$.messager.alert('消息提示', data.description, 'info');
						deoutorder.$.dialogInfo.window('close');
						deoutorder.$.dgdataset.datagrid('clearSelections').datagrid("reload");
					} else {
						$.messager.alert('消息提示', data.description || 'Unknown', 'warning');
					}
			    }
			});
		},
		closeDialog: function() {
			if(deoutorder.hasChanges()) {
				deoutorder.confirm('数据已经发生变更，您确定要放弃本次更改吗？', function() { 
					deoutorder.$.dialogInfo.window('close') 
				});
			} else {
				deoutorder.$.dialogInfo.window('close');
			}
		},
		closeARDialog: function() {
			deoutorder.$.dialogAR.window('close');
		}
	}
};

(function(deoutorder, $) {
	function getSearchParam() {
		var formData = $("#deoutorder_filter").serializeArray();
		var param = {};
		$.each(formData, function() {
			param[this.name] = this.value;
		});
		return param;
	}
	
	var main = deoutorder.cache.main
		, detail = deoutorder.cache.detail
		, columns = [[ 
			main.id, main.operate, main.platform, main.orderOcsId, main.orderId, main.isSendWms, main.isUpload, main.trackingNumber,
			main.ocsOrderCreateDate, main.sendDate, main.feedbackDate, detail.sku, detail.itemQty, main.shipDate, main.shipBy, main.createdDate
		]];
	
	deoutorder.$.dgdataset = $("#deoutorder_dataset").datagrid({
		url: '/ocs/wms/deoutorder/list',
		queryParams: {
			param: getSearchParam()
		},
		columns: columns,
		singleSelect: true,
		rownumbers: true,
		border: true,
		pagination: true,
		pageSize: 50,
		loadMsg: '数据正在加载中......',
		emptyMsg: '<h2>没有数据</h2>',
		showFooter: true,
		onLoadError: function() {
			$.messager.alert('消息提示', '操作失败', 'warning');
		},
		onLoadSuccess: function(data) {
			$('a.deoutorder-action').css('margin-left', '3px').css('margin-right', '3px');
			$(".datagrid-ftable tbody tr").each(function() {
			    this.style.backgroundColor="#E1EDFF";
			    this.style.color="blue";
			    this.style.fontWeight="bold";
			});
			
			deoutorder.cache.mergeRow = {};
			for(var i = 1, l = data.rows.length; i < l; i++) {
				var orderId = data.rows[i][main.orderId.field];
				if(orderId === data.rows[i - 1][main.orderId.field]) {
					if(!deoutorder.cache.mergeRow[orderId]) {
						deoutorder.cache.mergeRow[orderId] = { index: i - 1, rowspan: 1 };
					}
					deoutorder.cache.mergeRow[orderId].rowspan++;
				}
			}
			
			for(var k1 in deoutorder.cache.mergeRow) {
				for(var i = 0, l = columns[0].length; i < l; i++) {
					if(!columns[0][i].hidden && !deoutorder.isDetailField(columns[0][i].field)) {
						$(this).datagrid('mergeCells', {
							index: deoutorder.cache.mergeRow[k1].index,
							field: columns[0][i].field,
							rowspan: deoutorder.cache.mergeRow[k1].rowspan
						});
					}
				}
			}
		},
		toolbar: [{
			text: '导出',
			iconCls: 'icon-redo',
			handler: function() {
				var downloadUrl = "/ocs/excel/dynamicExport/deOutOrder"
					, param = getSearchParam()
					, maxDays = 31
					, ignoreInterval = param["orderId"] || param["orderOcsId"]
					, startDate = deoutorder.formatStrToDate(param["startOcsOrderCreateDate"])
					, endDate = deoutorder.formatStrToDate(param["endOcsOrderCreateDate"])
					, startSendDate = deoutorder.formatStrToDate(param["startSendDate"])
					, endSendDate = deoutorder.formatStrToDate(param["endSendDate"])
					, startFeedbackDate = deoutorder.formatStrToDate(param["startFeedbackDate"])
					, endFeedbackDate = deoutorder.formatStrToDate(param["endFeedbackDate"])
					, startAbnormalDate = deoutorder.formatStrToDate(param["startAbnormalDate"])
					, endAbnormalDate = deoutorder.formatStrToDate(param["endAbnormalDate"])
					, validInterval = (startDate && endDate && (endDate.getTime() - startDate.getTime()) < maxDays * 24 * 60 * 60 * 1000)
								   		|| (startSendDate && endSendDate && (endSendDate.getTime() - startSendDate.getTime()) < maxDays * 24 * 60 * 60 * 1000)
								   		|| (startFeedbackDate && endFeedbackDate && (endFeedbackDate.getTime() - startFeedbackDate.getTime()) < maxDays * 24 * 60 * 60 * 1000)
								   		|| (startAbnormalDate && endAbnormalDate && (endAbnormalDate.getTime() - startAbnormalDate.getTime()) < maxDays * 24 * 60 * 60 * 1000);
				
				if(!ignoreInterval && !validInterval) {
					$.messager.alert('提示', '时段过大，最多一次只能导出' + maxDays + '天的数据，请分段导出！', 'info');
					return;
				}
				
				var paramUrl = '?';
				for(var key in param) {
					paramUrl += key + '=' + param[key] + '&';
				}
				
				var includeFields = '';
				for(var key in deoutorder.cache.main) {
					includeFields +=  deoutorder.humpToUnderline(deoutorder.cache.main[key].field) + ',';
				} 
				for(var key in deoutorder.cache.detail) {
					includeFields +=  deoutorder.humpToUnderline(deoutorder.cache.detail[key].field) + ',';
				}
				paramUrl += 'includeFields=' + includeFields.substr(0, includeFields.length - 1);

				window.location.href = downloadUrl + paramUrl;
			}
		}, {
			text: '同步WMS异常单',
			iconCls: 'icon-update',
			handler: function() {
				deoutorder.confirm('同步WMS异常单将需要一定的时间，您确认要执行此操作吗？', function() {
					deoutorder.progress();
					deoutorder.ajax('/wms/deoutorder/syncWmsDisease', {}, function(resp) {
						deoutorder.closeProgress();
						if(resp.data && 'object' === typeof resp.data) {
							var s = resp.data.success
								, sl = s && s.length || 0
								, sm = sl ? s.join('') : ''
								, f = resp.data.failed
								, fl = f && f.length || 0
								fm =  fl ? f.join('') : '';
							resp.description = '同步WMS异常单：总共' + (sl + fl) + '条，成功' + sl + '条，失败' + fl + '条。';
							resp.description += '</br>';
							resp.description += sl ? ('同步成功详情：</br>' + sm) : '';
							resp.description += '</br>';
							resp.description += fl ? ('同步失败详情：</br>' + fm) : '';
						}
						deoutorder.dgdatasetOperSuccessCallback(resp);
					}, function() {
						deoutorder.closeProgress();
						deoutorder.errorCallback();
					});
				});
			}
		}]
	});
	
	$("#filter_btn_search").click(function() {
		deoutorder.$.dgdataset.datagrid({
			queryParams: {
				param :getSearchParam()
			}
		});
		//deoutorder.$.dgdataset.datagrid("reload");
	});
	
	$("#filter_btn_reset").on('click',function() {
		$("#deoutorder_filter").form("clear");
	});
	
	$(document).ready(function() {
		var buttons = $.extend([], $.fn.datebox.defaults.buttons);
		buttons.splice(0, 0, {
			text: '清空',
			handler: function(target) {
				$(target).datebox('clear').datebox("panel").panel('close');
			}
		});
		$(".easyui-datebox").datebox({ buttons: buttons });
		$("span.datebox .textbox-text").attr("readonly","true");

		//去掉默认日期
		/*
		var date = new Date();
		var month = date.getMonth() + 1;
		var day = date.getDate();
		var today = date.getFullYear() + '-' + (month > 9 ? '' : '0') + month + '-' + (day > 9 ? '' : '0') + day;
		date.setDate(date.getDate() - 7);
		month = date.getMonth() + 1;
		day = date.getDate();
		var daysAgo = date.getFullYear() + '-' + (month > 9 ? '' : '0') + month + '-' + (day > 9 ? '' : '0') + day;
		$('#filter_startOcsOrderCreateDate').datebox('setValue', daysAgo);
		$('#filter_endOcsOrderCreateDate').datebox('setValue', today);
	 	*/
	});
})(deoutorder, jQuery)