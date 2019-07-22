var itemDataLinkModel = function(){
	var item = {
			id:'',
			name:'',
			displayName:'',
			link:''
		};
	var formDatas = $("#itemDataTreeNodeForm").serializeArray();
	$.each(formDatas,function(){
		item[this.name] = this.value;
	});
	return item;
}
function submitForm(){
	var data = itemDataLinkModel();
	if(data.name == 'undefined' || data.name == ''||data.name == null){
		$.messager.alert('提示','请至少填充name字段数据','info');
		return ;
	}
	ocs.ajax({
		url: "/itemDataStructureLink/itemSetSave",
        type: "POST",
        data:data,
        success:function(data){
        	if(data){
        		$.messager.alert('提示','操作成功','info');
        		$("#itemDataTree").tree("options").url = "/ocs/itemDataStructureLink/itemDataTree?id=0";
        		$("#itemDataTree").tree("reload");
        		$("#itemDataTree").tree("options").url = "/ocs/itemDataStructureLink/itemDataTree";
        	}
        }
	});
}

function itemDataadd(){
	var parentNode = itemDataLinkModel();
	$("#itemDataTreeNodeForm").form("clear");
	$("#itemDataTreeNodeForm").find("input[name=parentId]").val(parentNode.id);
	$("#itemDataDescId").hide();
	$("#itemDataDescName").textbox('textbox').attr('readonly',false);
}

$(function(){
	$("#itemDataTree").tree({
		url:"/ocs/itemDataStructureLink/itemDataTree?id=0",
		method:"GET",
		animate : true,
		loadFilter: function(data){
			return data;
	    },
		onSelect : function(node){
			$("#itemDataDescId").show();
			$("#itemDataDescName").textbox('textbox').attr('readonly',true);
			$('#itemDataTreeNodeForm').form('load','/ocs/itemDataStructureLink/getItemNodeInfo?id='+node.id);
		}
	});
	$("#itemDataTree").tree("options").url = "/ocs/itemDataStructureLink/itemDataTree";
	$("#checkLinkOpt").click(function(){
		$("#selectitemDataWin").dialog({
			toolbar:[{
				text:'确定',
				iconCls:'icon-ok',
				handler:function(){
					var node = $("#selectitemDataTree").tree("getSelected");
					if(node){
						$("#linkView").textbox("setValue",node.name);
						$("#link").val(node.id);
						$("#selectitemDataWin").dialog("close");
					}else{
						$.messager.alert('提示','请选择关联数据','info');
						return ;
					}
					
				}
			},{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function(){
					$("#selectitemDataWin").dialog("close");
				}
			}]
		});
		$("#selectitemDataWin").dialog("open");
		$("#selectitemDataTree").tree({
			url:"/ocs/itemDataStructureLink/itemDataTree?id=1",
			method:"GET",
			animate : true
		});
		$("#selectitemDataTree").tree("options").url = "/ocs/itemDataStructureLink/itemDataTree";
	});
});