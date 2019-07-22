var operlog = {
	$: {
		dgdataset: null
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
		var date = operlog.timestampToDate(value);
		return date && date.format('yyyy-MM-dd hh:mm:ss') || '';
	},
	linkbuttonHtml: function(clickHtml, text, title, clsClass) {
		return '<a href="javascript:void(0);" class="easyui-linkbutton operlog-action ' + (clsClass || '') + '"'
			 + (!!clickHtml ? (' onClick="' + clickHtml + '"') : '') + ' data-options="plain: true" title="' + (title || '') + '">' + text + '</a>';
	},
	omit: function(value, max) {
		if(!value || value.length < max) {
			return value;
		}
		return value.substr(0, max) + ' ' + operlog.imitateTooltip(value, '......');
	},
	imitateTooltip: function(content, value) {
		return '<span style="display: none;">' + content + '</span><a href="javascript:void(0)" class="imitate-tooltip">' + value + '</a>';
	},
	omitDescription: function(value, row, index) {
		return operlog.omit(value, 50);
	}
};

(function(operlog, $) {
	function getSearchParam() {
		var formData = $("#operlog_filter").serializeArray();
		var param = {};
		$.each(formData, function() {
			param[this.name] = this.value;
		});
		return param;
	}
	
	operlog.$.dgdataset = $("#operlog_dataset").datagrid({
		url: '/ocs/wms/operlog/list',
		queryParams: {
			param: getSearchParam()
		},
		columns: [[
			{ field: 'id', hidden: true }
			, { field: 'target', title: '操作方式', halign: 'center', align: 'left', width: 180, styler: operlog.textIndent5px }
			, { field: 'orderOcsId', title: 'OrderOcsId', halign: 'center', align: 'center', width: 90 }
			, { field: 'orderId', title: 'OrderId', halign: 'center', align: 'left', width: 200, styler: operlog.textIndent5px }
			, { field: 'operatorText', title: '操作人', halign: 'center', align: 'left', width: 80, styler: operlog.textIndent5px }
			, { field: 'result', title: '操作结果', halign: 'center', align: 'center', width: 80 }
			, { field: 'description', title: '操作详情', halign: 'center', align: 'left', width: 400, styler: operlog.textIndent5px, formatter: operlog.omitDescription }
			, { field: 'createdAt', title: '操作时段', halign: 'center', align: 'center', width: 150, formatter: operlog.formatDate }
		]],
		singleSelect: true,
		rownumbers: true,
		border: true,
		pagination: true,
		pageSize: 50,
		loadMsg: '数据正在加载中......',
		emptyMsg: '<h2>没有数据</h2>',
		showFooter: true,
		onLoadError: function() {
			$.messager.alert('消息提示', '数据加载失败', 'warning');
		},
		onLoadSuccess: function(data) {
			$(".datagrid-ftable tbody tr").each(function() {
			    this.style.backgroundColor="#E1EDFF";
			    this.style.color="blue";
			    this.style.fontWeight="bold";
			});

			$('.imitate-tooltip').each(function() {
				$(this).tooltip({
				    position: 'bottom',
				    content: '<span style="color: black;">' + $(this).prev().text()+ '</span>',
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
		}
		/*,
		toolbar: [{
			text: '导出',
			iconCls: 'icon-redo',
			handler: function() {
				var downloadUrl = "/ocs/excel/dynamicExport/deOutOrder"
					, param = getSearchParam()
					, maxDays = 31
					, startDate = operlog.formatStrToDate(param["startCreatedAt"])
					, endDate = operlog.formatStrToDate(param["endCreatedAt"])
					, validInterval = startDate && endDate && (endDate.getTime() - startDate.getTime()) < maxDays * 24 * 60 * 60 * 1000;
				
				if(!validInterval) {
					$.messager.alert('提示', '操作时段过大，最多一次只能导出' + maxDays + '天的数据，请分段导出！', 'info');
					return;
				}
				
				var paramUrl = '?';
				for(var key in param) {
					paramUrl += key + '=' + param[key] + '&';
				}
				
				var includeFields = ' ';
				paramUrl += 'includeFields=' + includeFields.substr(0, includeFields.length - 1);

				window.location.href = downloadUrl + paramUrl;
			}
		}]*/
	});
	
	$("#filter_btn_search").click(function() {
		operlog.$.dgdataset.datagrid({
			queryParams: {
				param :getSearchParam()
			}
		});
	});
	
	$("#filter_btn_reset").on('click',function() {
		$("#operlog_filter").form("clear");
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
	});
	
})(operlog, jQuery)