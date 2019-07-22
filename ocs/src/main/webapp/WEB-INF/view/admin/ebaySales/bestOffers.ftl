<@FTL.admin id="bestOffers" title="议价" add_script_files=['admin/ebaySales/bestOffers.js','admin/ocs/main.js']>
<div data-options="region:'center',border:false">
	<table id="bestOffersTable" class="easyui-datagrid" 
		data-options="
	    url:'${FTL.X.global_domain}/BestOffers/selectGroupbyBestOffers',
	    fitColumns:true,
	    idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 30,
        fit:true,
        nowrap:false,
        toolbar:'#toolSearch',
        queryParams:{
        	param:{
        		conditions:'',
        		siteIdSort:'asc',
        		itemIdSort:'asc',
        		endTimeSort:'asc',
        		seller_id:'',
        		email:'',
        		item_id:'',
        		user_id:'',
        		titel:''
        		
        	}
        },
	    columns:[[
                	 {field:'currency',title:'币种',align:'center' ,hidden:true},
                	 {field:'productUrl',title:'url',align:'center' ,hidden:true},
                	 {field:'country_name',title:'站点',align:'center' ,hidden:true},
                	 {field:'buyit_now_price',title:'原价',align:'center' ,hidden:true},
                	  {field:'image',title:'图片',align:'center',formatter:getImger},
                	 {field:'item_id',title:'物品ID',align:'center'},
                	 {field:'sku',title:'SKU',align:'center'},
                	 {field:'titel',title:'物品标题',width:120,align:'left',formatter:getItemInfo},
                     {field:'best_offer_id',title:'议价ID',align:'center'},
                     {field:'user_id',title:'买家ID',align:'left'},
                     {field:'email',title:'买家邮箱',align:'left'},
                     {field:'quantity',title:'数量',align:'center'},
                     {field:'price',title:'原价/议价',width:30,align:'left',formatter:getPrices},
                     {field:'message',title:'留言',width:50,align:'left'},
                     {field:'endTime',title:'结束时间',width:50,align:'center'},
                     {field:'siteid',title:'卖家账号',align:'left' },
                     {field:'best_offer_code_type',title:'议价类型',width:50,align:'center',hidden:true},
                     {field:'operation',title:'操作',width:30,align:'center',formatter:getOperation}
		]]">
	</table>

</div>
 
<div id="toolSearch">
<div style="padding:10px;">
	<form id="bestOffersCondition">
		<table style="float:left; min-width:850px;">
		<div>
			<tr style="min-width:1000px;">
				<td align="right"><label>卖家账号:</label></td>
				<td>
				<input type="text"  id="sellerId" name="seller_id"   style="width:200px;"  />
					
				</td>
				<td align="right">
					<label style="">买家ID:</label>
				</td>
				
				<td>
					<input type="text" name="user_id" style="float:right;width:200px;" class="easyui-textbox" />
					<br clear="all"/>
				</td>
				<td align="right">
					<label style="">买家邮箱:</label>
				</td>
				<td>
					
					<input type="text" name="email" style="float:right;width:200px;" class="easyui-textbox" />
					<br clear="all"/>
				</td>
				
			</tr>
			<tr>
				<td align="right">
					<label style="">物品ID:</label>
				</td>
				
				<td>
					<input type="text" name="item_id" style="float:right;width:200px;" class="easyui-textbox" />
					<br clear="all"/>
				</td>
				<td align="right">
					<label style="">物品标题:</label>
				</td>
				
				<td>
					<input type="text" name="titel" style="float:right;width:200px;" class="easyui-textbox" />
					<br clear="all"/>
					
				</td>
				<td align="right" colspan="2" style="padding-right: 21px;">
					<a  href="javascript:void(0);" id="bestOffersSearch" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
					<a  href="javascript:void(0);" id="bestOffersReset" class="easyui-linkbutton" iconCls="icon-clear"  style="margin-right: 32px;">重置</a>
					<a href="javascript:void(0)" onclick="synchronousBestOffers()" class="easyui-linkbutton c8" data-options="menu:'#mm2',iconCls:'icon-ok',plain:true">同步</a>
				</td>

					
			</tr>
		</table>
		<br clear="all"/>
		</div>
		
	</form>
	</div>
</div>
<div id="menu" class="easyui-menu" style="width: 50px; display: none;">
 <a href="javascript:void(0)" onclick="synchronousBestOffer()" class="easyui-linkbutton" data-options="menu:'#mm2',iconCls:'icon-success',plain:true">同步eBay</a>
</div>
<div id="bestOffersDialog" class="easyui-dialog" style="width:500px"
            closed="true" buttons="#bo-buttons">
              <form id="bestOffersfm" method="post" novalidate style="margin:0;padding:10px 35px">
              	<div >
              	<input style="display:none" type="easyui-textbox" name="seller_id" id="seller_id">
              	<input style="display:none" type="easyui-textbox" name="none_quantity" id="noneQuantity">
              	<input style="display:none" type="easyui-textbox" name="operate_Type" id="operateTypeId">
              	<input style="display:none" type="easyui-textbox" name="none_item_id" id="noneItemID">
              	<input style="display:none" type="easyui-textbox" name="none_best_id" id="noneBestId">
              	<input style="display:none" type="easyui-textbox" name="none_currency" id="noneCurrency">
              	<div id="counterDiv" class="easyui-panel" data-options="border:false">
              	<label>价格 :</label>
              	<input type="text" name='counterOfferPrice' class="easyui-numberbox"  style="width:200px" data-options="min:0,precision:2" >
              	<span id="siteCurrency" style="color:green;"></span>
              	</div>
              	</div>
              	<div style="margin-top:10px;">
	              	<div>留言 :</div>
	             	<input class="easyui-textbox" name="sellerResponse" data-options="multiline:true"  style="width:420px;height:120px" >
              	</div>
              </form>
 </div>
 <div id="bo-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="bestOffers()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#bestOffersDialog').dialog('close')" style="width:90px">取消</a>
 </div>
</@FTL.admin>