var inventoryGlobal = {
	uploading:function(event) {
		var formData = new FormData();
		var myfile = document.getElementById("file").files[0];
		if(myfile == '' || myfile == null) {
			$.messager.alert("提示信息", "请选择导入的文件");
			 return;
		}
	    formData.append("file", myfile);
		$.ajax({
		      type: "POST",
		      url: GLOBAL.domain + event.data.url,
		      enctype: 'multipart/form-data',
		      data:formData,
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
	timestampToDate: function(timestamp) {
		return !timestamp ? null : new Date(timestamp);
	},
	search: function() {
		var param = inventoryGlobal.collectForm('#inventory_Form');
		$("#inventoryDatagrid").datagrid({
			queryParams:{
				param :param
			}
		});
	},
	formatDate: function(value, row, index) {
		var date = inventoryGlobal.timestampToDate(value);
		return date && date.format('yyyy-MM-dd hh:mm:ss') || '';
	},
	formatStrToDate: function(time) {
		return time && new Date(time.replace(new RegExp("\\-","gi"), "/")) || null;
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
	}
};
$(function() {
	$('#inventoryExportLinkbutton').bind('click',function(){
		var downloadUrl = "/ocs/excel/dynamicExport/inventoryExport";
		var param = inventoryGlobal.collectForm('#inventory_Form');
		var paramUrl = '?';
		for(var key in param) {
			paramUrl += key + '=' + param[key] + '&';
		}
		window.location.href = downloadUrl + paramUrl;
	});
	$('#query').bind('click', inventoryGlobal.search);
	$('#reset').bind('click', function() { inventoryGlobal.clearForm('#inventory_Form'); });
	$('#inventoryUploadLinkbutton').on('click',function(){
		$('#uploadDialog').dialog("open");
	});
	$('#actionUpload').on('click',{ grid: '#inventoryDatagrid', dialog: '#uploadDialog',url:'/excel/import/inventoryUpload'},inventoryGlobal.uploading);
	var columns = [ [ {
		field : 'id',
		hidden : true
	}, {
		field : 'sku',
		title : 'sku',
		halign : 'center',
		align : 'center',
		width : '33%'
	}, {
		field : 'qty',
		title : '数量',
		halign : 'center',
		align : 'center',
		width : '33%'
	}, {
		field : 'uploadDate',
		title : '导入时间',
		halign : 'center',
		align : 'center',
		width : '33%',
		formatter: inventoryGlobal.formatDate
	} ] ];
	$("#inventoryDatagrid")
			.datagrid(
					{
						url : GLOBAL.domain + '/uk_inventory_day/list',
						columns : columns,
						singleSelect : true,
						rownumbers : true,
						border : true,
						pagination : true,
						pageSize : 50,
						showFooter : true,
						onLoadSuccess : function(data) {
							$(".datagrid-ftable tbody tr").each(function() {
								this.style.backgroundColor = "#E1EDFF";
								this.style.color = "blue";
								this.style.fontWeight = "bold";
							})
						},
						toolbar : '#inventoryToolbar'
					});
});