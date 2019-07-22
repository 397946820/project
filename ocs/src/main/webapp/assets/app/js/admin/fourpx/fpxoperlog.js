var fpxoperlog = {
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
		var date = fpxoperlog.timestampToDate(value);
		return date && date.format('yyyy-MM-dd hh:mm:ss') || '';
	},
	linkbuttonHtml: function(clickHtml, text, title, clsClass) {
		return '<a href="javascript:void(0);" class="easyui-linkbutton fpxoperlog-action ' + (clsClass || '') + '"'
			 + (!!clickHtml ? (' onClick="' + clickHtml + '"') : '') + ' data-options="plain: true" title="' + (title || '') + '">' + text + '</a>';
	},
	imitateTooltip: function(value, row, index) {
		return '<span class="imitate-tooltip">' + value + '</span>';
	}
};

(function(fpxoperlog, $) {
	function getSearchParam() {
		var formData = $("#operlog_filter").serializeArray();
		var param = {};
		$.each(formData, function() {
			param[this.name] = this.value;
		});
		return param;
	}
	
	fpxoperlog.$.dgdataset = $("#operlog_dataset").datagrid({
		url: '/ocs/fourpx/operlog/list',
		queryParams: {
			param: getSearchParam()
		},
		columns: [[
			{ field: 'id', hidden: true }
			, { field: 'target', title: '操作方式', halign: 'center', align: 'left', width: 200, styler: fpxoperlog.textIndent5px }
			, { field: 'orderId', title: 'OrderId', halign: 'center', align: 'left', width: 120, styler: fpxoperlog.textIndent5px }
			, { field: 'operatorText', title: '操作人', halign: 'center', align: 'left', width: 60, styler: fpxoperlog.textIndent5px }
			, { field: 'result', title: '结果', halign: 'center', align: 'center', width: 60 }
			, { field: 'request', title: '输入', halign: 'center', align: 'left', width: 300, styler: fpxoperlog.textIndent5px, formatter: fpxoperlog.imitateTooltip }
			, { field: 'response', title: '输出', halign: 'center', align: 'left', width: 300, styler: fpxoperlog.textIndent5px, formatter: fpxoperlog.imitateTooltip }
			, { field: 'createdat', title: '操作时段', halign: 'center', align: 'center', width: 130, formatter: fpxoperlog.formatDate }
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
				    content: '<span style="color: black;">' + $(this).text() + '</span>',
				    onShow: function() {
				        $(this).tooltip('tip').css({ backgroundColor: '#FFE48D', borderColor: '#95B8E7' });
				    }
				});
			})
		}
	});
	
	$("#filter_btn_search").click(function() {
		fpxoperlog.$.dgdataset.datagrid({
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
	
})(fpxoperlog, jQuery)