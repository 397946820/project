<#macro payment>
                    <div class="blue_madison" id="tabbox10">
                        <div class="model_title">
                            <div class="caption" id="Payment">
                                <span>付款</span>
                            </div>
                            <div class="actions">
                                <span class="btn btn_default btn_sm" id="PaymentSelect" title="选择">选择 </span>
                                <span class="btn btn_default btn_sm" id="PaymentSelectSave" title="另存为">另存为 </span>
                            </div>
                        </div>
                        <div class="model_form">
                            <form class="form-horizontal">
                                <div class="form-group" link="PAYPAI_ACCOUNT">
                                    <label class="form_label col-md-2">PayPal</label>
                                    <div class="col-md-4 form_inline">
                                        <select id="paypaiAccount" class="form-control input-medium">
                                            <option selected="selected" value="">-- 选择 --</option>
                                            <option value="lightingorient@gmail.com">lightingorient@gmail.com</option>
									       <option value="payment@lightingever.com">payment@lightingever.com</option>
									       <option value="payment@lightingever.co.uk">payment@lightingever.co.uk</option>
									       <option value="payment@lightingever.de">payment@lightingever.de</option>
									       <option value="lightingallright@gmail.com">lightingallright@gmail.com</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group" link="AUTO_PAY">
                                    <label class="form_label col-md-2"></label>
                                    <div class="col-md-4 form_inline">
                                       	<input type="checkbox" name="autoPay" id="autoPay" /><label for="autoPay">Require immediate payment when buyer uses Buy It Now</label>
                                    </div>
                                </div>
                                <div class="form-group" link="SUPPORT_PAYPAI_INFO">
                                    <label class="form_label col-md-2">Other Payment</label>
                                    <div class="col-md-4 form_inline">
                                        <ul class="enhancementli" id="paymentTypeList">
                                            
                                        </ul>
                                    </div>
                                </div> 
                                 <div class="form-group" link="PAY_DESCRIPTION">
                                    <label class="form_label col-md-2">付款说明</label>
                                    <div class="col-md-4 form_inline">
                                    <input id="payMentDescription" class="easyui-textbox" data-options="multiline:true"  style="width:600px;height:100px">
                                        <span class="help-block help_block_bottom"> <b class="numoutput paymentNum"><span class="overWords" id="payMentDescriptionLength">0</span></b>/ ( 最多 500 字符. 不支持 HTML )</span>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>  
 
</#macro>
