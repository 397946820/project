
$(function () {
	$("#ebayAccount").combobox({
		valueField :"account",
		textField :"account",
		url : "/ocs/publication/getAccounts"
	});
	 $("#removeLinkbutton").on('click', {grid: '#advertTemplateDatagrid', type: 'datagrid', url: '/AdvertTemplates/remove'}, remove);
});
function saveAdvertForm(){
	
	$('#advertForm').form('submit', {
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (date) {
         
                $.messager.alert('消息提示','操作成功', 'info');
                $('#advertDialog').dialog('close');
                $('#advertTemplateDatagrid').datagrid('reload');
        }
    });
}
var getTitleAndItemNum =function(value,row,index){
	
	   if(value!=null){
		   return "<span style='color:blue'><a href='"+value+"' target='_blank'>有<a></span>";
	   }else{
		   return "无";
	   }
		
	
	
}
var getPicServlet = function (value,row,index) {
	return "<img width=60 height=60 src='"+row.url+"'/>";
}
var getSiteImage = function(value,row,index){
	if((value == "" || value == null) && value!= 0){
		value=22;
	}
    return "<div class='icon3 country_size num_"+value+"'></div>";
}
function getProductUrlById(){
	var url = $("#externavTextId").textbox('getValue');
	$("#productUrlId").combogrid("setValue",url);
	 $('#externalDialog').dialog('close');
}
function change_photo(){
    PreviewImage($("input[name='file']")[0], 'Img', 'Imgdiv');
}
function editAdvert(){
    var row = $('#advertTemplateDatagrid').datagrid('getSelected');
    if (row){
    	
        $('#advertDialog').dialog('open').dialog('center').dialog('setTitle','编辑');
        $('#advertForm').form('load',row);
        $("#Img").attr("src",row.url);
    }
}
function addExternalUrl(){
	$("#productUrlId").combogrid("setValue","");
	$('#externalDialog').dialog('open').dialog('center').dialog('setTitle','添加外部链接');
}
var getEbayImage = function(value,row,index){
	if(value){
		var imgUrl = undefined;
		var index = value.indexOf(",");
		if(index > 0){
			imgUrl = value.substring(0,index-1)
		}else{
			imgUrl = value;
		}
		return '<img src="'+imgUrl+'" style="width:80px;height:80px;"/>';
	}
	return "";
}
function saveAdvert(){
var saveData = $("#sellerForm").serializeArray();
var save = {};
$.each(saveData,function(){
	save[this.name] = this.value;
});

ocs.ajax({
	url: "/ItemPromote/save",
    type: "POST",
    data: save,
    success:function(re){
    	if(re){
    		$.messager.alert('提示','保存成功','info');
    		$('#advertForm').form("clear");
            $('#advertDialog').dialog('close');   
            $('#advertTemplate').datagrid('reload');
    	}
    }
});     
}


function newAdvert(){
	
	$('#advertForm').form('reset');
    $('#advertDialog').dialog('open').dialog('center').dialog('setTitle','添加');
    
}