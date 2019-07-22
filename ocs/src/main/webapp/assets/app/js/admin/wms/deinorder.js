var deinorder = {
	$: {
		dgdataset: null,
		dialogInfo: null,
		dialogAR: null,
		dialogClaim: null,
		dialogInMatchs: null
	},timestampToDate: function(timestamp) {
		return !timestamp ? null : new Date(timestamp);
	},
	formatStrToDate: function(time) {
		return time && new Date(time.replace(new RegExp("\\-","gi"), "/")) || null;
	},
	textIndent5px: function(value, row, index) {
		return 'text-indent: 5px;';
	},
	formatDate: function(value, row, index) {
		var date = deinorder.timestampToDate(value);
		return date && date.format('yyyy-MM-dd hh:mm:ss') || '';
	},
	showPlatform: function(value, row, index) {
		if('1' === value) {
			return '官网';
		} else if('2' === value) {
			return 'ebay';
		} else if('3' === value) {
			return '亚马逊';
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
			return deinorder.linkbuttonHtml('deinorder.reanderAbnormalReason(' + index + ')', '异常单', '点击查看异常原因', 'abnormal');
		} else if('4' === value) {
			return '已取消';
		} else {
			return '';
		}
	},
	linkbuttonHtml: function(clickHtml, text, title, clsClass) {
		return '<a href="javascript:void(0);" class="easyui-linkbutton deinorder-action ' + (clsClass || '') + '"'
			 + (!!clickHtml ? (' onClick="' + clickHtml + '"') : '') + ' data-options="plain: true" title="' + (title || '') + '">' + text + '</a>';
	},
	showOperateOptions: function(value, row, index) {
		var status = row[deinorder.cache.main.isSendWms.field]
			, editable = '0' === status || '3' === status
			, sendable = '3' === status
			, l_btn_disabled = editable ? '' : ' l-btn-disabled';
		return deinorder.linkbuttonHtml('deinorder.action.info(' + index + ')', '详情', '查看入库单详细信息', 'info')
		 	 //+ deinorder.linkbuttonHtml(editable ? ('deinorder.action.modify(' + index + ')') : '', '修改', '修改当前行入库单相关信息', 'modify' + l_btn_disabled)
			 + deinorder.linkbuttonHtml(sendable ? ('deinorder.action.send(' + index + ')') : '', '推送', '将当前行入库单推送至WMS仓库', 'send' + (sendable ? '' : ' l-btn-disabled'))
			 + deinorder.linkbuttonHtml(editable ? ('deinorder.action.cancel(' + index + ')') : '', '取消', '取消（即关闭）当前行入库单', 'cancel' + l_btn_disabled);
	},
	showOrderType: function(value, row, index) {
		if('1' === value) {
			return '退货入库';
		} else {
			return '';
		}
	},
	showWaitClaim: function(value, row, index) {
		if('oms_nonclaim' === value) {
			return '未关联认领单';
		} else if('oms_bindclaim' === value) {
			return '已关联认领单';
		} else if('wms_unclaimed' === value) {
			return '待认领';
		} else if('wms_claimed' === value) {
			return '已认领';
		} else {
			return '';
		}
	},
	omit: function(value, max) {
		if(!value || value.length < max) {
			return value;
		}
		return value.substr(0, max) + ' ' + deinorder.imitateTooltip(value, '......');
	},
	imitateTooltip: function(content, value) {
		return '<span style="display: none;">' + content + '</span><a href="javascript:void(0)" class="imitate-tooltip">' + value + '</a>';
	},
	omitActionText: function(value, row, index) {
		return deinorder.omit(value, 5);
	},
	omitReason: function(value, row, index) {
		return deinorder.omit(value, 28);
	},
	orderLink: function(value, row, index) {
		return '<a href="javascript:void(0)" class="order-link" title="查看入库单详细信息">' + value + '</a>';
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
	cache: {
		mergeRow: {},
		claimMergeRow: {},
		claimOwnerMergeRow: {},
		main: {
			id: { field: 'id', hidden: true },
			operate: { field: 'operate', title: '操作', halign: 'center', align: 'center', width: 120, formatter: function(v, r, i) { return deinorder.showOperateOptions(v, r, i); } },
			platform: { field: 'platform', title: '来源', halign: 'center', align: 'center', width: 50, formatter: function(v, r, i) { return deinorder.showPlatform(v, r, i); } },
			orderOcsId: { field: 'orderOcsId', title: 'OrderOcsId', halign: 'center', align: 'center', width: 80 },
			orderId: { field: 'orderId', title: 'OrderId', halign: 'center', align: 'left', width: 200, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			isSendWms: { field: 'isSendWms', title: '入库单状态', halign: 'center', align: 'center', width: 80, formatter: function(v, r, i) { return deinorder.showStatus(v, r, i); } },
			trackingNumber: { field: 'trackingNumber', title: '原始运单号', halign: 'center', align: 'left', width: 150, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			newTrackingNumber: { field: 'newTrackingNumber', title: '新运单号', halign: 'center', align: 'center', width: 160, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			ownerCode: { field: 'ownerCode', title: '货主代码', halign: 'center', align: 'center', width: 60 },
			storeCode: { field: 'storeCode', title: '仓库代码', halign: 'center', align: 'center', width: 60 },
			sendDate: { field: 'sendDate', title: '推送WMS时间', halign: 'center', align: 'center', width: 140, formatter: function(v, r, i) { return deinorder.formatDate(v, r, i); } },
			feedbackDate: { field: 'feedbackDate', title: 'WMS反馈时间', halign: 'center', align: 'center', width: 140, formatter: function(v, r, i) { return deinorder.formatDate(v, r, i); } },
			warehouseId: { field: 'warehouseId', title: 'WMS单号', halign: 'center', align: 'left', width: 100, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			account: { field: 'account', title: '账号' },
			orderType: { field: 'orderType', title: '入库类型', halign: 'center', align: 'center', width: 80, formatter: function(v, r, i) { return deinorder.showOrderType(v, r, i); } },
			createDate: { field: 'createDate', title: '创建日期', halign: 'center', align: 'center', width: 140, formatter: function(v, r, i) { return deinorder.formatDate(v, r, i); } },
			updateDate: { field: 'updateDate', title: '更新日期', halign: 'center', align: 'center', width: 140, formatter: function(v, r, i) { return deinorder.formatDate(v, r, i); } },
			//createBy: { field: 'createBy', title: '创建人' },
			remark: { field: 'remark', title: '备注', halign: 'center', align: 'left', width: 130, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			rma: { field: 'rma', title: 'RMA', halign: 'center', align: 'left', width: 100, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			waitClaim: { field: 'waitClaim', title: '认领属性', halign: 'center', align: 'center', width: 130, formatter: function(v, r, i) { return deinorder.showWaitClaim(v, r, i); }  },
			returnOrderId: { field: 'returnOrderId', title: '退货退款ID' },
			claimId: { field: 'claimId', hidden: true }
		},
		detail: {
			detailId: { field: 'detailId', hidden: true },
			sku: { field: 'sku', title: 'SKU', halign: 'center', align: 'left', width: 130, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			qty: { field: 'qty', title: 'SKU数量', halign: 'center', align: 'right', width: 60 },
			skuProperty: { field: 'skuProperty', title: '货品属性', halign: 'center', align: 'center', width: 60 },
			item: { field: 'item', title: '版本', halign: 'center', align: 'left', width: 100, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			actualQty: { field: 'actualQty', title: '实际SKU数量', halign: 'center', align: 'right', width: 80, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); }  },
			packageCode: { field: 'packageCode', title: '包装代码', halign: 'center', align: 'left', width: 60, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			unit: { field: 'unit', title: '单位', halign: 'center', align: 'left', width: 60, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			returnReason: { field: 'returnReason', title: '退货原因', halign: 'center', align: 'left', width: 100, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			customerName: { field: 'customerName', title: '退货客户', halign: 'center', align: 'left', width: 100, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			mobile: { field: 'mobile', title: '联系电话', halign: 'center', align: 'left', width: 100, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			address: { field: 'address', title: '地址', halign: 'center', align: 'left', width: 150, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			picUrl: { hidden: true, field: 'picUrl', title: '产品图片', halign: 'center', align: 'left', width: 80, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
			badReason: { field: 'badReason', title: '不良原因', halign: 'center', align: 'left', width: 100, styler: function(v, r, i) { return deinorder.textIndent5px(v, r, i); } },
		}
	},
	infoRender: {
		mainSort: [ 
			'platform', 'orderOcsId', 'isSendWms', 'orderId', 'sendDate', 'feedbackDate', 'trackingNumber', 'newTrackingNumber', 'ownerCode', 'storeCode',
			'warehouseId', 'account', 'orderType', 'waitClaim', 'rma', 'returnOrderId', 'createDate', 'updateDate', 'remark'
		],
		getCache: function() {
			var cache = deinorder.cache['currInfo'];
			if(!cache) {
				cache = [];
				deinorder.cache['currInfo'] = cache;
			}
			return cache;
		},
		caching: function(dataRow) {
			var currInfo = deinorder.infoRender.getCache()
				, idField = deinorder.cache.detail.detailId.field
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
			deinorder.cache.currInfo = [];
		}
	},
	isDetailField: function(field) {
		for(var key in deinorder.cache.detail) {
			if(deinorder.cache.detail[key].field === field) {
				return true;
			}
		}
		return false;
	},
	reanderAbnormalReason: function(rowIndex) {
		if(!deinorder.$.dialogAR) {
			deinorder.$.dialogAR = $('#deinorder_ar');
		}
		
		var $ar = deinorder.$.dialogAR
			, currRow = deinorder.$.dgdataset.datagrid('getRows')[rowIndex];
		
		$ar.find('table.reasons-list').datagrid({
			url: '/ocs/wms/deinorder/abnormalReasonList',
			queryParams: {
				param: { parentId: currRow[deinorder.cache.main.id.field], parentType: 'in' }
			},
			columns: [[
				{ field: 'id', hidden: true }
				, { field: 'parentOrderId', title: '原始订单OrderId', halign: 'center', align: 'left', width: 200, styler: deinorder.textIndent5px, formatter: deinorder.orderLink }
				//, { field: 'parentTypeText', title: '类别', align: 'center', width: 50 }
				, { field: 'actionText', title: '产生源', halign: 'center', width: 100, align: 'left', styler: deinorder.textIndent5px, formatter: deinorder.omitActionText }
				, { field: 'reason', title: '具体原因', halign: 'center', width: 240, align: 'left', styler: deinorder.textIndent5px, formatter: deinorder.omitReason }
				, { field: 'createdAt', title: '产生时间', align: 'center', width: 130, formatter: deinorder.formatDate }
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
				deinorder.adjustDatagridStyle(data);
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
				
				$ar.find('.order-link').bind('click', function() { deinorder.action.info(rowIndex); });
			}
		});
		$ar.dialog('open');
	},
	renderingInfo: function(rowIndex, editable) {
		if(!deinorder.$.dialogInfo) {
			deinorder.$.dialogInfo = $("#deinorder_info");
		}
		
		var $info = deinorder.$.dialogInfo
			, $form = $info.find('form')
			, $main = $form.find('table.main')
			, rows = deinorder.$.dgdataset.datagrid('getRows')
			, currRow = rows[rowIndex]
			, mainSort = deinorder.infoRender.mainSort
			, htmls = []
			, columnCount = 2;

		deinorder.infoRender.cleanCache();
		$main.find("tr").remove();
		for(var i = 0, l = mainSort.length; i < l; i++) {
			if(i % columnCount == 0) {
				htmls.push('<tr>');
			}
			if(mainSort[i]) {
				var column = deinorder.cache.main[mainSort[i]]
					, tmpv = currRow[column.field]
					, value = 'undefined' === typeof tmpv || null == tmpv ? '' : tmpv
					, hasFormatter = 'function' === typeof column.formatter
					, text = hasFormatter ? column.formatter(value, currRow, rowIndex) : value
					, isStautsField = column.field === deinorder.cache.main.isSendWms.field
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
					//有可能入库单目前状态正常，但是以前出现过异常情况，所以在此提供查看入库单历史异常信息的入口
					if('0' !== value && '3' !== value && isStautsField) {
						htmls.push(deinorder.linkbuttonHtml('deinorder.reanderAbnormalReason(' + rowIndex + ')', '异常历史？', '可能以往发生过异常，点击查看异常详情', 'ar-history'));
					}
				}
				htmls.push('</td>');
			} else {
				htmls.push('<td></td><td></td>');
			}
			if(i > (columnCount - 1) && i % columnCount == (columnCount - 1)) {
				htmls.push('</tr>');
			}
		}
		$form.find('input[name="id"]').val(currRow[deinorder.cache.main.id.field]);
		$main.append(htmls.join(''));
		$main.find('input.easyui-textbox').textbox({ width: 200 });
		
		var detail = deinorder.cache.detail
			, detailColumns = [[ 
				detail.detailId, detail.sku, detail.qty, detail.actualQty, detail.skuProperty, detail.item, detail.packageCode,
				detail.unit, detail.returnReason, detail.customerName, detail.mobile, detail.address, detail.picUrl, detail.badReason 
			]]
			, orderId = currRow[deinorder.cache.main.orderId.field]
			, mergeRow = deinorder.cache.mergeRow[orderId]
			, detailData = [];
		
		for(var i = rowIndex, l = rowIndex + (mergeRow && mergeRow.rowspan || 1), tmpd = null; i < l; i++) {
			tmpd = {};
			for(var key in detail) {
				tmpd[detail[key].field] = rows[i][detail[key].field];
			}
			detailData.push(tmpd);
			deinorder.infoRender.caching(rows[i]);
		}
		
		var $detail = $form.find('table.detail').datagrid({
			columns: detailColumns,
			data: detailData,
			singleSelect: true,
			rownumbers: true,
			onLoadSuccess: function(data) {
				deinorder.adjustDatagridStyle(data);
				$(this).datagrid('getPanel').panel({ height: detailData.length * 25 + 45 });
			}
		});
		
		var $editable = $('div.in-info-btns > .editable-show');
		editable ? $editable.show() : $editable.hide();
		$info.dialog("open");
	},
	busEquals: function(o1, o2) {
		return (!o1 && !o2) || o1 == o2;
	},
	hasChanges: function() {
		var cache = deinorder.infoRender.getCache()
			, flag = false;
		deinorder.$.dialogInfo.find('form input[name]').each(function() {
			if(!deinorder.busEquals($(this).val(), cache[0][$(this).attr('name')])) {
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
		    error: 'function' === typeof errorCallback && errorCallback || deinorder.errorCallback
		});
	},
	errorCallback: function() {
		$.messager.alert('消息提示', '操作失败', 'warning');
	},
	dgdatasetOperSuccessCallback: function(data) {
		$.messager.alert('消息提示', data.description || 'Unknown', data && data.errorCode == 0 ? 'info' : 'warning');
		deinorder.$.dgdataset.datagrid('clearSelections').datagrid("reload");
	},
	progress: function() {
		$.messager.progress({ interval: 500, msg: '正在处理，请稍后...' });
	},
	closeProgress: function() {
		$.messager.progress('close');
	},
	action: {
		info: function(rowIndex) {
			deinorder.renderingInfo(rowIndex, false);
		},
		modify: function(rowIndex) {
			deinorder.renderingInfo(rowIndex, true);
		},
		send: function(rowIndex) {
			deinorder.confirm('您确认要将当前入库单推送至WMS仓库吗？', function() {
				var main = deinorder.cache.main 
					, row = deinorder.$.dgdataset.datagrid('getRows')[rowIndex]
					, data = { id: row[main.id.field], orderId: row[main.orderId.field], orderOcsId: row[main.orderOcsId.field] };
				deinorder.progress();
				deinorder.ajax('/wms/deinorder/send', data, function(data) {
					deinorder.closeProgress();
					deinorder.dgdatasetOperSuccessCallback(data);
				}, function() {
					deinorder.closeProgress();
					deinorder.errorCallback();
				});
			});
		},
		cancel: function(rowIndex) {
			deinorder.confirm('被取消的入库单将成为废单，无法再继续操作。您确认要将当前入库单取消吗？', function() {
				var main = deinorder.cache.main 
					, row = deinorder.$.dgdataset.datagrid('getRows')[rowIndex]
					, data = { id: row[main.id.field], orderId: row[main.orderId.field], orderOcsId: row[main.orderOcsId.field] };
				deinorder.ajax('/wms/deinorder/cancel', data, deinorder.dgdatasetOperSuccessCallback);
			});
		},
		saveChanges: function() {
			if(!deinorder.hasChanges()) {
				$.messager.alert('提示', '数据没有发生变更，不需要保存！', 'info');
				return;
			}

			//var formData = deinorder.$.dialogInfo.find('form').serializeArray();
			deinorder.$.dialogInfo.find('form').form('submit', {
			    onSubmit: function(param) {
			        // do some check
			        // return false to prevent submit;
			    },
			    success: function(response) {
					var data = JSON.parse(response);
					if (data && data.errorCode == 0) {
						$.messager.alert('消息提示', data.description, 'info');
						deinorder.$.dialogInfo.window('close');
						deinorder.$.dgdataset.datagrid('clearSelections').datagrid("reload");
					} else {
						$.messager.alert('消息提示', data.description || 'Unknown', 'warning');
					}
			    }
			});
		},
		closeDialog: function() {
			if(deinorder.hasChanges()) {
				deinorder.confirm('数据已经发生变更，您确定要放弃本次更改吗？', function() { 
					deinorder.$.dialogInfo.window('close') 
				});
			} else {
				deinorder.$.dialogInfo.window('close');
			}
		},
		closeARDialog: function() {
			deinorder.$.dialogAR.window('close');
		},
		closeClaimDialog: function() {
			$('div.in-claim-btns .btn.view-match-inorder').hide();
			$('div.in-claim-btns .btn.match-inorder').hide();
			deinorder.$.dialogClaim.window('close');
		},
		closeInMatchsDialog: function() {
			$('div.in-matchs-btns .btn.claim').hide();
			$('#claim_owner_filter table').hide();
			deinorder.$.dialogInMatchs.window('close');
		},
		matchInOrder: function() {
			deinorder.openMatchInOrderDialog({
				url: '/ocs/wms/deinorder/claimOwnerList',
				queryParams: {
					param: deinorder.getSearchParam('#claim_owner_filter')
				}
			});
		},
		viewMatchInOrder: function() {
			deinorder.openMatchInOrderDialog({ 
				url: '/ocs/wms/deinorder/main',
				queryParams: {
					id: deinorder.$.dialogClaim.find('table.dg').datagrid('getSelected')[deinorder.cache.main.claimId.field]
				}
			}, true);
		},
		claim: function() {
			var claimSelectedRow = deinorder.$.dialogClaim.find('table.dg').datagrid('getSelected')
				, orderId = claimSelectedRow['orderId']
				, main = deinorder.cache.main;
			deinorder.confirm('您确认使用入库单（orderId=' + orderId + '）来匹配认领当前认领单（认领单详情）吗？', function() {
				deinorder.ajax('/wms/deinorder/claim', { 
					claim: claimSelectedRow[main.id.field], 
					owner: deinorder.$.dialogInMatchs.find('table.dg').datagrid('getSelected')[main.id.field] 
				}, function(data) {
					var success = data && data.errorCode == 0;
					$.messager.alert('消息提示', data.description || 'Unknown', success ? 'info' : 'warning');
					if(success) {
						deinorder.$.dialogInMatchs.window('close');
						deinorder.$.dialogClaim.find('table.dg').datagrid('clearSelections').datagrid("reload");
					}
				});
			});
		}
	},
	openMatchInOrderDialog: function(options, readonly) {
		if(!deinorder.$.dialogInMatchs) {
			deinorder.$.dialogInMatchs = $('#deinorder_in_matchs'); 
		}
		deinorder.$.dialogInMatchs.dialog('open');
		deinorder.loadClaimOwnerDatagrid(options, readonly);
	},
	getSearchParam: function(formSelector) {
		var formData = $(formSelector).serializeArray();
		var param = {};
		$.each(formData, function() {
			param[this.name] = this.value;
		});
		return param;
	},
	loadDetailForClaim: function(rowIndex, row, option) {
		var editable = false
			, rows = option.$dg.datagrid('getRows')
			, currRow = rows[rowIndex]
			, mainSort = deinorder.infoRender.mainSort.concat()
			//认领单的orderType/isSendWms/sendDate/feedbackDate字段没有意义，忽略这些字段，不需要显示它们
			, ignoreFields = 'orderType,isSendWms,sendDate,feedbackDate'
			, htmls = []
			, columnCount = 3
			, $wrap = option.$dialog.find('div.info-wrap')
			, $info = $wrap.find('table.info');
		
		$info.find("tr").remove();
		for(var i = 0, l = mainSort.length; i < l; i++) {
			if(i % columnCount == 0) {
				htmls.push('<tr>');
			}
			if(mainSort[i]) {
				if(ignoreFields.indexOf(mainSort[i]) >= 0) {
					mainSort.splice(i, 1);
					i--;
					l--;
					continue;
				}
				var column = deinorder.cache.main[mainSort[i]]
					, tmpv = row[column.field]
					, value = 'undefined' === typeof tmpv || null == tmpv ? '' : tmpv
					, hasFormatter = 'function' === typeof column.formatter
					, text = hasFormatter ? column.formatter(value, row, rowIndex) : value
					, isStautsField = column.field === deinorder.cache.main.isSendWms.field
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
				}
				htmls.push('</td>');
			} else {
				htmls.push('<td></td><td></td>');
			}
			if(i > (columnCount - 1) && i % columnCount == (columnCount - 1)) {
				htmls.push('</tr>');
			}
		}
		if('</tr>' !== htmls[htmls.length - 1]) {
			htmls.push('</tr>');
		}
		$info.append(htmls.join(''));
		$wrap.show();
		
		var detail = deinorder.cache.detail
			, detailColumns = [[ 
				detail.detailId, detail.sku, detail.qty, detail.actualQty, detail.skuProperty, detail.item, detail.packageCode,
				detail.unit, detail.returnReason, detail.customerName, detail.mobile, detail.address, detail.picUrl, detail.badReason 
			]]
			, mergeRow = deinorder.cache[option.mergeRowCache][currRow[option.cacheField]]
			, rowspan = mergeRow && mergeRow.rowspan || 1
			, index = rowspan > 1 ? mergeRow.index : rowIndex
			, detailData = [];
		
		for(var i = index, l = index + rowspan, tmpd = null; i < l; i++) {
			tmpd = {};
			for(var key in detail) {
				tmpd[detail[key].field] = rows[i][detail[key].field];
			}
			detailData.push(tmpd);
		}
		
		$wrap.find('table.details').datagrid({
			columns: detailColumns,
			data: detailData,
			singleSelect: true,
			rownumbers: true,
			height: 95,
			onLoadSuccess: function(data) {
				deinorder.adjustDatagridStyle(data);
			}
		});
	},
	loadClaimOwnerDatagrid: function(options, readonly) {
		var main = deinorder.cache.main
			, detail = deinorder.cache.detail
			, $matchs = deinorder.$.dialogInMatchs
			, claimOwnerColumns = [[ 
				main.id, main.platform, main.orderOcsId, main.orderId, main.rma, detail.sku, detail.qty, main.trackingNumber, main.createDate
			]];

		$matchs.find('table.dg').datagrid($.extend(true, {
			columns: claimOwnerColumns,
			singleSelect: true,
			rownumbers: true,
			border: true,
			pagination: true,
			pageSize: 10,
			pageList: [ 10, 20, 30, 40, 50 ],
			showFooter: false,
			height: 135,
			loadMsg: '数据正在加载中......',
			emptyMsg: '<h2>没有数据</h2>',
			toolbar: '.claim-owner-toolbar',
			onLoadError: function() {
				$.messager.alert('消息提示', '认领可选项（入库单）加载失败', 'warning');
			},
			onLoadSuccess: function(data) {
				deinorder.adjustDatagridStyle(data);
				deinorder.cacheMergeRow(data, { mergeRowCache: 'claimOwnerMergeRow', cacheKey: main.orderId.field, columns: claimOwnerColumns, $dg: $(this) });
				$(this).datagrid('selectRow', 0);
				var method = readonly ? 'hide' : 'show';
				$('div.in-matchs-btns .btn.claim')[method]();
				$('#claim_owner_filter table')[method]();
			},
			onSelect: function(rowIndex, row) {
				deinorder.loadDetailForClaim(rowIndex, row, { $dg: $(this), $dialog: $matchs, mergeRowCache: 'claimOwnerMergeRow', cacheField: deinorder.cache.main.orderId.field });
			}
		}, options || {}));
	},
	adjustDatagridStyle: function(data) {
		$('a.deinorder-action').css('margin-left', '3px').css('margin-right', '3px');
		$(".datagrid-ftable tbody tr").each(function() {
		    this.style.backgroundColor="#E1EDFF";
		    this.style.color="blue";
		    this.style.fontWeight="bold";
		});
	},
	cacheMergeRow: function(data, option) {
		var cache = deinorder.cache[option.mergeRowCache] = {};
		for(var i = 1, l = data.rows.length; i < l; i++) {
			var key = data.rows[i][option.cacheKey];
			if(key === data.rows[i - 1][option.cacheKey]) {
				if(!cache[key]) {
					cache[key] = { index: i - 1, rowspan: 1 };
				}
				cache[key].rowspan++;
			}
		}
		
		for(var k1 in cache) {
			for(var i = 0, l = option.columns[0].length; i < l; i++) {
				if(!option.columns[0][i].hidden && !deinorder.isDetailField(option.columns[0][i].field)) {
					option.$dg.datagrid('mergeCells', {
						index: cache[k1].index,
						field: option.columns[0][i].field,
						rowspan: cache[k1].rowspan
					});
				}
			}
		}
	}
};

(function(deinorder, $) {
	var main = deinorder.cache.main
		, detail = deinorder.cache.detail
		, columns = [[ 
			main.id, main.operate, main.platform, main.orderOcsId, main.orderId, main.rma, main.isSendWms, detail.sku, detail.qty,
			main.trackingNumber, main.newTrackingNumber, main.sendDate, main.feedbackDate, main.orderType, main.createDate
		]];
	
	deinorder.$.dgdataset = $("#deinorder_dataset").datagrid({
		url: '/ocs/wms/deinorder/list',
		queryParams: {
			param: deinorder.getSearchParam('#deinorder_filter')
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
			deinorder.adjustDatagridStyle(data);
			deinorder.cacheMergeRow(data, { mergeRowCache: 'mergeRow', cacheKey: main.orderId.field, columns: columns, $dg: $(this) });
		},
		toolbar: [{
			text: '导出',
			iconCls: 'icon-redo',
			handler: function() {
				var downloadUrl = "/ocs/excel/dynamicExport/deInOrder"
					, param = deinorder.getSearchParam('#deinorder_filter')
					, maxDays = 31
					, startDate = deinorder.formatStrToDate(param["startOcsOrderCreateDate"])
					, endDate = deinorder.formatStrToDate(param["endOcsOrderCreateDate"])
					, startSendDate = deinorder.formatStrToDate(param["startSendDate"])
					, endSendDate = deinorder.formatStrToDate(param["endSendDate"])
					, startFeedbackDate = deinorder.formatStrToDate(param["startFeedbackDate"])
					, endFeedbackDate = deinorder.formatStrToDate(param["endFeedbackDate"])
					, startAbnormalDate = deinorder.formatStrToDate(param["startAbnormalDate"])
					, endAbnormalDate = deinorder.formatStrToDate(param["endAbnormalDate"])
					, validInterval = (startDate && endDate && (endDate.getTime() - startDate.getTime()) < maxDays * 24 * 60 * 60 * 1000)
			   							|| (startSendDate && endSendDate && (endSendDate.getTime() - startSendDate.getTime()) < maxDays * 24 * 60 * 60 * 1000)
								   		|| (startFeedbackDate && endFeedbackDate && (endFeedbackDate.getTime() - startFeedbackDate.getTime()) < maxDays * 24 * 60 * 60 * 1000)
								   		|| (startAbnormalDate && endAbnormalDate && (endAbnormalDate.getTime() - startAbnormalDate.getTime()) < maxDays * 24 * 60 * 60 * 1000);
				
				if(!validInterval) {
					$.messager.alert('提示', '订单创建时段过大，最多一次只能导出' + maxDays + '天的数据，请分段导出！', 'info');
					return;
				}
				
				var paramUrl = '?';
				for(var key in param) {
					paramUrl += key + '=' + param[key] + '&';
				}
				
				var includeFields = '';
				for(var key in deinorder.cache.main) {
					includeFields +=  deinorder.humpToUnderline(deinorder.cache.main[key].field) + ',';
				} 
				for(var key in deinorder.cache.detail) {
					includeFields +=  deinorder.humpToUnderline(deinorder.cache.detail[key].field) + ',';
				}
				paramUrl += 'includeFields=' + includeFields.substr(0, includeFields.length - 1);

				window.location.href = downloadUrl + paramUrl;
			}
		}, {
			text: 'WMS认领单',
			iconCls: 'icon-update',
			handler: function() {
				if(!deinorder.$.dialogClaim) {
					deinorder.$.dialogClaim = $('#deinorder_claim');
				}
				var $claim = deinorder.$.dialogClaim
					, claimColumns = [[
						main.id, $.extend(true, main.waitClaim, { title: '认领状态' }), main.warehouseId, main.rma, main.trackingNumber, detail.sku, detail.qty, detail.actualQty,
						detail.returnReason, detail.badReason
					]];
				
				var $claimDatagrid = $claim.find('table.dg').datagrid({
					url: '/ocs/wms/deinorder/claimList',
					queryParams: {
						param: deinorder.getSearchParam('#claim_list_filter')
					},
					columns: claimColumns,
					singleSelect: true,
					rownumbers: true,
					border: true,
					pagination: true,
					pageSize: 20,
					pageList: [ 10, 20, 30, 40, 50 ],
					showFooter: false,
					height: 435,
					emptyMsg: '<h3>没有数据</h3>',
					toolbar: '.claim-list-toolbar',
					onLoadSuccess: function(data) {
						deinorder.adjustDatagridStyle(data);
						deinorder.cacheMergeRow(data, { mergeRowCache: 'claimMergeRow', cacheKey: main.id.field, columns: claimColumns, $dg: $(this) });
						$claimDatagrid.datagrid('selectRow', 0);
					},
					onSelect: function(rowIndex, row) {
						deinorder.loadDetailForClaim(rowIndex, row, { 
							$dg: $claimDatagrid, 
							$dialog: $claim, 
							mergeRowCache: 'claimMergeRow',
							cacheField: deinorder.cache.main.id.field 
						});
						var waitClaim = row[deinorder.cache.main.waitClaim.field];
						$('div.in-claim-btns .btn.view-match-inorder')['wms_claimed' === waitClaim ? 'show' : 'hide']();
						$('div.in-claim-btns .btn.match-inorder')['wms_unclaimed' === waitClaim ? 'show' : 'hide']();
					}
				});
				$claim.dialog('open');
			}
		}]
	});
	
	$.each([
		{ $wrap: $('div.in-order-wrap'), filter: '#deinorder_filter' },
		{ $wrap: $('#deinorder_claim'), filter: '#claim_list_filter' },
		{ $wrap: $('#deinorder_in_matchs'), filter: '#claim_owner_filter' }
	], function(i, item) {
		item.$wrap.find('.btn.search').on('click', function(e) {
			item.$wrap.find('table.dg').datagrid({
				queryParams: {
					param: deinorder.getSearchParam(item.filter)
				}
			});
		});
		item.$wrap.find('.btn.reset').on('click', function(e) {
			$(item.filter).form("clear");
		});
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
		$('#claim_owner_filter, #claim_list_filter').find('input[name="orderId"], input[name="orderOcsId"]').prev().attr('placeholder', '*多个请使用特殊符号隔开');
	});
})(deinorder, jQuery)