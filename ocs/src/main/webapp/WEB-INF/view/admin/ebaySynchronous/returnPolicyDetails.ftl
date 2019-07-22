<@FTL.admin id="ReturnPolicy" title="退货政策" >
 
 <table id="storeProductDataGrid" class="easyui-datagrid" style="height:610px;overflow:auto;"
		data-options="
        title:'退货政策',
	    url:'${FTL.X.global_domain}/ReturnPolicyDetails/selectReturnPolicyDetails',
	    treeField:'name',
	    animate:true,
	    singleSelect:true,
	    toolbar:'#toolSearch',
	    pagination:true,
	    fit:true,
	    idField:'id',
	    fitColumns:true,
	    iconCls:'icon-edit',
	    columns:[[{field:'site_id',title:'站点ID',width:100},
	    {field:'refund',title:'退货方式',width:240,editor:{type:'combobox'}},
	    {field:'returns_within',title:'退货天数',width:240,editor:{type:'combobox'}},
	    {field:'description',title:'退货详情',width:140},
	    {field:'shippingcost_paidby',title:'退货运费负担角色',width:400,editor:{type:'combobox'}},
	    {field:'restocking_feevalue',title:'折旧费',width:140,editor:{type:'combobox'}},
	    {field:'returns_accepted',title:'退货政策',width:400,editor:{type:'combobox'}}
		]],
		rownumbers:true">
	</table>
	<div id="toolSearch" style="padding:10px;background:#fff;border-bottom:1px solid #95b8e7;">
		<div id="tb" style="float: left;"> 
		站点：<select class="easyui-combobox" id="selectMarketplace" name="site_id"  style="width:200px;" >
						<option value="0" selected>美国</option>
						<option value="3">英国</option>
						<option value="77">德国</option>
						<option value="100">eBay汽车</option>
		</select>
		
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="doSearch()" style="width:120px;margin-left:100px;">查找</a>
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="synchronouReturnPolicyDetail()" style="width:150px">同步退货政策数据</a> 
        </div>
        <br clear="all"/>
        
	</div>
	<script type="text/javascript">
	function refundFucation(value,rowData,rowIndex){
		alert(value);
	}
	function doSearch(){
		var global_id = $("#selectMarketplace").val();
		var store_name = $("#store_name").textbox('getValue');
		$('#storeProductDataGrid').datagrid('load',{
			global_id:global_id,
			store_name:store_name
		});
	}
	
	function synchronouReturnPolicyDetail() {
		var site_id = $("#selectMarketplace").val();
		$.ajax({
			url: GLOBAL.domain+'/ReturnPolicyDetails/synchronouReturnPolicyDetail',
			
			data: {site_id:site_id},
			
			dataType: "json",
			
			contentType: "application/json; charset=UTF-8",
			
			type: "get",
			
			success: function(result) {
			 
                    	    // reload the user data
				$.messager.alert("信息",result.description);	
			},
			error: function(jqXHR, textStatus, errorThrown) {
				$.messager.alert("信息","同步失败");		
			}
		}); 
	}
	</script>
</@FTL.admin>
