<#macro footer>

                <input type="hidden" name="hfListTemplateSettingKey" id="hfListTemplateSettingKey">
                <input type="hidden" name="hfPaymentKey" id="hfPaymentKey">
                <input type="hidden" name="hfBuyerRequirementKey" id="hfBuyerRequirementKey">
                <input type="hidden" name="hfReturnPolicyKey" id="hfReturnPolicyKey">
                <input type="hidden" name="hfItemLocationKey" id="hfItemLocationKey">
                <input type="hidden" name="hfShippingOptionKey" id="hfShippingOptionKey">
                <input type="hidden" name="hfExclusionListKey" id="hfExclusionListKey">
                <input type="hidden" name="hfListringUpgradeKey" id="hfListringUpgradeKey">
                <a id="lbtnRefresh" href="javascript:__doPostBack('lbtnRefresh','')"></a>
                <ul>
                    <li class="left">
                        <!-- <input type="submit" name="btnPreview" value="预览" id="btnPreview" class="btn btn-sm blue-madison disabled"> -->
                        <input id="btnPreviewTemplate" type="button" value="预览模板" class="btn btn-sm blue-madison">
                        <input type="button" name="btnElementSetting" value="模块组合" id="btnElementSetting" style="display:none;" class="btn btn-sm blue-madison"/>
                        <span id="upButton1">
<!--                        
 -->                        <input type="button" name="btnCheckeBayFee" value="保存并检查 eBay 费" id="btnCheckeBayFee" class="btn btn-sm blue-madison"/>
                        	<input type="button"  state="1" name="saveAndPublication" value="保存并发布" id="saveAndPublication" class="btn btn-sm blue-madison"/>
                        	<input type="button"   style="display:none" name="endPublication" value="立即结束" id="endPublication" class="btn btn-sm blue-madison"/>
                        </span>
                    </li>
                    <li class="right">
                        <span id="upButton2">
                            <input type="button" state="0" name="btnSave" value="保存" id="btnSave" class="btn btn-sm blue-madison">
                            <input type="submit" name="btnCheckeBayFeeAndSave" value="检查 eBay 费并保存" id="btnCheckeBayFeeAndSave" class="btn btn-sm blue-madison disabled">
                            <input id="btnClose" type="button" value="关闭" class="btn btn-sm blue-madison close_btn">
                        </span>
                    </li>
                </ul>
       <div id="endDialog" class="easyui-dialog" style="width:500px"
            closed="true" buttons="#end-buttons">
        <form id="endForm" method="post" novalidate style="margin:0;padding:20px 50px">
			<div style="margin-bottom:10px">
				<input style="display:none" id="itemId" type="easyui-textbox" name="id">            	
            </div>
            <div>
				<div style="margin-bottom:10px">
					 <select class="easyui-combobox" id="endingReasonId" name="endingReason" label="下线原因:"  style="width:250px;">
                	 	<option value="Incorrect">Incorrect</option>
                	 	<option value="LostOrBroken">LostOrBroken</option>
                	 	<option value="NotAvailable">NotAvailable</option>
                	 	<option value="OtherListingError">OtherListingError</option>
                	 	<option value="SellToHighBidder">SellToHighBidder</option>
                	 	<option value="Sold">Sold</option>
                	  </select>
				</div>
			</div>
        </form>
    </div>
    <div id="end-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="endPublication()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#endDialog').dialog('close')" style="width:90px">取消</a>
    </div>
</#macro>
