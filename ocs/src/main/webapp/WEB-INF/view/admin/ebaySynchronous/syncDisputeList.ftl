<@FTL.admin id="syncDisputeList" title="销售数据同步" add_script_files=['admin/ocs/main.js','admin/synchronou/syncDisputeList.js']>
	<div class="easyui-panel"  id="syncDisputeListBar" >
		<form id="disputeListSearchParam" style="background:#fff;padding:10px;border-bottom:1px solid #95B8E7;">
		<table style="float:left; min-width:710px;">
			<tr style="min-width:1000px;">
				<td><label style="">物品号:</label></td>
				<td><input type="text" name="itemID" style="float:right;width:170px;" class="easyui-textbox" /></td>
				<td><label style="">eBay账号:</label></td>
				<td>
					<input type="text"  id="ebayAccount" name="account"   style="width:173px;"  />
				</td>
				
			</tr>
			<tr style="min-width:1000px;">
				<td><label style="">交易号:</label></td>
				<td><input type="text" name="transactionID" style="float:right;width:170px;" class="easyui-textbox" /></td>
				<td><label style="">买家ID:</label></td>
				<td><input type="text" name="buyerUserID" style="float:right;width:170px;" class="easyui-textbox" /></td>
				<td>
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="disputeListReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="disputeListSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		<br clear="all"/>
		</form>
		<div>
			<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-update" id="disputeListSearchParamsync"
      		 plain="true">同步</a>
		</div>
	</div>
	
		<table id="syncDisputeInfoTable" class="easyui-datagrid" style="width:100%;height:100%">
		</table>
		
	<div id="ebayDisputeRemark" class="easyui-dialog" title="备注" style="width:500px;height:300px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#ebayDisputeRemarkButton'">
	    <div style="width:97%;margin:10px auto;" >
	    	<input class="easyui-textbox" style="width:100%;height:200px" data-options="multiline:true" id="disputeRemark" />
	    </div>
	    <div style="width: 100%;text-align:right;" id="ebayDisputeRemarkButton">
	    	<a  href="javascript:void(0);" id="disputeRemarkOk" class="easyui-linkbutton" iconCls="icon-ok"  >确定</a>
			<a  href="javascript:void(0);" id="disputeRemarkCancel" class="easyui-linkbutton" iconCls="icon-clear" >关闭</a>
		</div>
	</div>
	
	<div id="ebayDisputeAnswer" class="easyui-dialog" title="回复" style="width:500px;height:500px;display:none;"data-options="iconCls:'icon-edit',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#ebayDisputeAnswerButtons'">
	    <div style="margin-left:10px;">
	    	<form id="disputeAnswerForm">
	    	<div style="width:95%; height:280px;overflow:auto;margin:5px auto; border:1px solid #95b8e7;border-radius:5px;padding:10px;">
		    	<div style="width:100%;margin-top:10px;" >
		    		<label style="float: left;">如何处理：</label>
		    		<div>
		    			<ul style="list-style: none;margin-left: 20px;">
		    				<li id="disputeActivityHide1">
		    					<input type="radio" name="DisputeActivity" value="SellerPaymentNotReceived" /> I haven't received payment or the payment has not yet cleared.
		    				</li>
		    				<li id="disputeActivityHide2">
		    					<input type="radio" name="DisputeActivity" value="SellerShippedItem" /> I already shipped the item.
		    				</li>
		    				<li>
		    					<input type="radio" name="DisputeActivity" value="SellerOffersRefund" /> I'd like to offer the buyer a full refund.
		    				</li>
		    				<li>
		    					<input type="radio" name="DisputeActivity" value="SellerComment" /> I'd like to communicate with the buyer to resolve this case.
		    				</li>
		    			</ul>
		    		</div>
		    	</div>
		    	<div style="width:100%;margin-top:10px;" >
		    		<div style="padding-bottom:10px;">
		    		<label style="padding-right: 13px;">承运商：</label>
		    		<input class="easyui-textbox" id="ShippingCarrierUsed" name="ShippingCarrierUsed" data-options="disabled: true" style="width:40%;"  />
		    		</div>
		    		<div style="padding-bottom:10px;">
		    			<label style="padding-right: 1px;">发货时间：</label>
		    			<input class="easyui-datetimebox" id="ShippingTime" name="ShippingTime" data-options="disabled: true" style="width:40%;"  />
		    		</div>
		    		<div>
		    			<label style="padding-right: 13px;">运单号：</label>
		    		<input class="easyui-textbox" id="ShipmentTrackNumber" name="ShipmentTrackNumber" data-options="disabled: true" style="width:40%;"  />
		    		</div>
		    	</div>
		    	<div style="width:100%;margin-top:20px;" >
		    		<ul id="oldMessage" style="padding:0px;">
		    		</ul>
		    	</div>
		    </div>	
	    	<div style="width:98%;margin-top:10px;" >
	    		
	    		<input class="easyui-textbox" style="width:100%;height:75px" name="MessageText" data-options="multiline:true" id="MessageText" />
	    	</div>
	    	</form>
	    	
	    </div>
	    <div >
		    <div id="ebayDisputeAnswerButtons"style="width:100%;text-align:right;">
		    	<a  href="javascript:void(0);" id="disputeAnswerOk" class="easyui-linkbutton c8" iconCls="icon-ok"  >回复</a>
				<a  href="javascript:void(0);" id="disputeAnswerCancel" class="easyui-linkbutton" iconCls="icon-no" >关闭</a>
			</div>
		</div>
	</div>
	
	<div id="ebayDisputeView" class="easyui-dialog" title="详情" style="width:800px;height:400px;display:none;"data-options="
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	 
    	<div  class="easyui-accordion" style="width:800px;height:400px;" data-options="fit:true">
		    <div title="Appeal" data-options="iconCls:'icon-reload',selected:true" >
		        <table id="userCaseAppealTable" class="easyui-datagrid" style="width:100%;height:100%" data-options="fit:true">
				</table>
		    </div>
		    <div title="Money Movement" data-options="iconCls:'icon-reload'">
		         <table id="userCaseMoneyMovementTable" class="easyui-datagrid" style="width:100%;height:100%" data-options="fit:true">
				</table>
		    </div>
		    <div title="Response History" data-options="iconCls:'icon-reload'">
		         <table id="userCaseResponseHistoryTable" class="easyui-datagrid" style="width:100%;height:100%" data-options="fit:true">
				</table>
		    </div>
		</div>
	</div>
	
</@FTL.admin>