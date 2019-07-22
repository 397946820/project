<@FTL.admin id="messageAnswer" title="消息回复" add_script_files=['admin/synchronou/syncMemberMessageInfo.js','admin/synchronou/messageAnswer.js','admin/ocs/main.js','admin/ocs/baseDate.js']>
<div id="messageHeight">
<div style="margin:10px;padding:10px;padding-bottom: 28px;float:right;width:48%;font-size:14px;background:#95b8e7;border-radius:5px;position:relative;">
<!--这里回复内容-->
	<div style=" width:100%; height:300px;overflow:auto;border:1px solid #95b8e7;border-radius:5px;background:#fff;" >
		<ul id="oldMessage" style="padding-left:20px;">
		</ul>
	</div>
	
	 <!--回复框-->
	<div style="position: relative;">
		<input class="easyui-textbox" style="width:101.5%;height:100px;padding-top:10px;font-size:14px;" data-options="multiline:true" id="memberMessageAnswer" />
	</div>
	<div>
		<ul class="imgPreview">
		</ul>
	</div>
   
	<div style="width:100%;" >
		<input type="file" id="textBtn" style="display:none;" onChange="previewFile()" multiple/>
		<div class="messageAddImgBtn"><div class="icon3 add-image-icon"></div><span>添加图片</span></div></br>
		<input type="checkbox" id="isView"/><label for="isView" style="cursor: pointer;">是否显示在物品详情页</label>
	</div>
	<div style="height:40px;position:absolute;bottom:4px;right:26px;">
		<!--<a  href="javascript:void(0);" id="memberMessageAnswerCancel" class="easyui-linkbutton"  iconCls="icon-no" style="float:right;margin-left:20px;" >关闭</a>-->
    	<a  href="javascript:void(0);" id="memberMessageAnswerOk" class="easyui-linkbutton c8"  iconCls="icon-ok"  style="float:right;">回复</a>
	</div>
	
</div>


	   
<!--这里是产品详情-->	
<div style="float:left; width:45%; height:514px;overflow:auto;margin:10px 15px 0px 15px; border:1px solid #95b8e7;border-radius:5px; font-size:12px;"  title="">
	<div style="margin-right: 50px;margin-left: 56px;margin-top: 5px;">
		<img style="border:1px solid #95b8e7;width:100px;height:150px;"  src="" id="imageValue" name="imageValue">
 	</div>
 	<div>
     	<div style="margin-top: 15px;padding-left:50px;" >
     		<a target="_blank" id="itemUrl"></a>
     	</div>
     	<div  style="margin-top: 15px;padding-left:50px;" >
     		SKU: <span id="itemSKU"></span>
     	</div>
     	<div  style="margin-top: 15px;padding-left:50px;" >
     		<span id="itemIdSpan"></span>
     	</div>
     	<div  style="margin-top: 15px;padding-left:50px;" >
     		<span id="itemMInfo"></span>
     	</div>
 	</div>
</div>
	
<!--这里是订单详情-->
<table id="syncSaleInfoTable" class="easyui-datagrid" style="width:100%;height:300px;float:right;margin-left: 50px;font-size:12px;overflow:auto;">
</table>
<div id="saleCancelOrderWin"class="easyui-dialog" title="取消订单" style="width:400px;height:150px;display:none;"data-options="iconCls:'icon-save',
	resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	<label>取消原因：</label>
	<select class="easyui-combobox" id="orderCancelCause" style="width:300px;">
		<option value="BuyerNoLongerWantsItem">This value indicates that the buyer no longer wants the item (buyer remorse), and the seller is willing to cancel the order line item. (买家不在需要此物品)</option>
		<option value="BuyerPurchasingMistake">This value indicates that the buyer made a mistake by purchasing the item, and the seller is willing to cancel the order line item.(买家犯了一个小错误)</option>
		<option value="OtherExplanation">This value can be used when no other explanation in DisputeExplanationCodeType is appropriate for the situation. (其他原因)</option>
		<option value="SellerDoesntShipToCountry">This value indicates that the buyer is requesting shipment of the item to a country that is on the seller's ship-to exclusion list. (不在运送范围内)</option>
		<option value="SellerRanOutOfStock">This value indicates that the seller ran out of stock on the item, cannot fulfill the order, and has to cancel the order line item. (卖家没有库存)</option>
		<option value="ShippingAddressNotConfirmed">This value indicates that the buyer is requesting shipment of the item to an unconfirmed (not on file with eBay) address. (买家提供的地址有错或为确认)</option>
		<option value="UnableToResolveTerms">This value indicates that the buyer and seller were unable to resolve a disagreement over terms, and the seller is willing to cancel the order line item. (买卖双方没有达成协议，需要取消订单)</option>
	</select>
</div>

<div id="saleDetial" class="easyui-dialog" title="订单详情" closed="true" style="width:1000px;height:500px;padding-bottom:50px;font-size:12px;" 
	data-options="modal:true,buttons: [{
				text:'关闭',
				
				handler:function(){
					$('#saleDetial').window('close');
				}
			}]">
	<div class="transadress" style="width:500px; height:200px;border-right:1px solid #ccc; float:left;clear:both;">
		<div style="padding:0 30px;float:left;"><p style="font-weight:300;">运输地址</p></div>
		<div style="padding:10px;float:left;line-height:20px;">
			<span id="t_Name">Name</span><br/>
			<span id="t_Street1">Street1</span>&nbsp;
			<span id="t_Street2">Street2</span><br/>
			<span id="t_CityName">CityName</span><br/>
			<span id="t_StateOrProvnce">StateOrProvnce</span><br/>
			<span id="t_posttalCode">posttalCode</span><br/>
			<span id="t_Phone">Phone</span><br/>
		</div>
	</div>
	<div class="baseInfomation" style="display:inline-block; width:400px;height:200px; padding:0px 30px 30px 30px;float:left;">
		<table style="padding:10px 30px 30px 30px;">
				<tr>
					<td height="20" width="50">订单号</td>
					<td id="d_OrderID">OrderID</td>
				</tr>
				<tr>
					<td height="20"  width="50">销售号</td>
					<td id="d_SalesRecoreNumber">SalesRecoreNumber</td>
				</tr>
				<tr>
					<td height="20"  width="50">买家ID</td>
					<td id="d_BuyerUserID">BuyerUserID</td>
				</tr>
				<tr>
					<td height="20"  width="50">卖家ID</td>
					<td id="d_SellerUserID">SellerUserID</td>
				</tr>
		</table>
	</div>
	 <table id="saleDetailTable" class="easyui-datagrid" style="width:100%;height:auto;">
		
	 </table>
	 <div class="total"  style="padding:10px;padding-right:40px;float:right;font-weight:300;">合计:&nbsp<span id="t_total" style="">0</span></div>
	
</div>
</div>
</@FTL.admin>