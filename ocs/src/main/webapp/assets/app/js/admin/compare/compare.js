var openImport = function(){
	$("#importCompareDataDialog").window("open");
};
$(function () {
	$('#importLinkButton').on('click', openImport);
	$("#importButton").on('click', {grid: '#compareDataGrid', dialog: '#importCompareDataDialog', url: '/dataCompareController/data_uploadExcel'}, uploadPlateExcel);
	$("#compareExportLinkButton").on('click',exportCompareResult);
	$("#plateFormSource").combobox({
		onChange: function (newValue,oldValue) {
			$('#importAccount').combobox({
				url: GLOBAL.domain+"/dataCompareController/query_account?pf=" + newValue,
				valueField:'text',
			    textField:'text'
			});
			if (newValue=="ebay") {
				$('#siteInfo').show();
			} else {
				$('#siteInfo').hide();
			}
		}
	});
});

var exportCompareResult = function(){
	var form = $("<form>");   // 定义一个form表单
	
    form.attr('style','display:none');   // 在form表单中添加查询参数

    form.attr('method','post');

    form.attr('action',GLOBAL.domain + '/dataCompareController/ebay_exportExcel'); 
    var account = $('#importAccount').combobox("getValue");
	var plateForm = $('#plateFormSource').combobox("getValue");
	var site = $('#importSite').combobox("getValue");
    var input1 = $('<input>'); 
    input1.attr('name','platForm'); 
    input1.attr('value',plateForm);
    var input2 = $('<input>');
    input2.attr('name','site'); 
    input2.attr('value',site);
    var input3 = $('<input>');
    input3.attr('name','account'); 
    input3.attr('value',account);
    $('body').append(form);  // 将表单放置在web中
    form.append(input1);   // 将查询参数控件提交到表单上
    form.append(input2);   // 将查询参数控件提交到表单上
    form.append(input3);   // 将查询参数控件提交到表单上
    form.submit();   // 表单提交
}
//上传
var uploadPlateExcel = function (event) {
	var formData = new FormData();
	var myfile = document.getElementById("file").files[0];
	if(myfile == '' || myfile == null) {
		$.messager.alert("提示信息", "请选择导入的文件");
		 return;
	}
	if($('#plateFormSource').val()=='') {
		$.messager.alert("提示信息", "请选择平台");
		 return;
	}
	if ($('#plateFormSource').val()=='ebay' && $('#importSite').val()=='') {
		$.messager.alert("提示信息","请选择站点");
		return;
	}
	var account = $('#importAccount').combobox("getValue");
	var plateForm = $('#plateFormSource').combobox("getValue");
	var site = $('#importSite').combobox("getValue");
    formData.append("myfile", myfile);
    formData.append("account",account);
    formData.append("platForm",plateForm);
    formData.append("site",site);
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
	    	  debugger;
	    	  var $data = JSON.parse(response);
				if ($data && $data.errorCode == 0) {
					$.messager.alert('消息提示', $data.description, 'info');
//					$(event.data.dialog).window('close');
//					$(event.data.grid).datagrid("reload");
				} else if ($data && $data.errorCode == 1) {
					$.messager.alert('消息提示', $data.description, 'warning');
				}
	      },
	      error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	  $.messager.alert('消息提示', '导入失败', 'warning');
	      }
	 });
}