<#macro returnPolicy>
                    <div class="blue_madison" id="tabbox12" link="RETUEN_POLOCY">
                        <div class="model_title">
                            <div class="caption" id="Return">
                                <span>退货政策</span>
                            </div>
                            <div class="actions">
                                <span class="btn btn_default btn_sm" id="returnPolicySelect" title="选择">选择 </span>
                                <span class="btn btn_default btn_sm" id="returnPolicySelectSave" title="另存为">另存为 </span>
                            </div>
                        </div>
                        <div class="model_form">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label class="form_label col-md-2">退货政策</label>
                                    <div class="col-md-4 form_inline">
                                        <select id="policyType" name=""  class="form-control input_medium">
                                            
                                        </select>
                                    </div>
                                </div>
                                <div class="form-body" id="dlReturnPolicyDetail">
                                    <div id="dtReturnDays" class="form-group">
                                        <label class="form_label col-md-2">退货天数</label>
                                        <div id="ddReturnDays" class="col-md-4 form_inline">
                                            <select id="returnDays" name=""  class="form-control input_medium">
                                                
                                            </select>
                                        </div>
                                    </div>
                                    <div id="dtExtendedHolidayReturns" class="form-group">
                                        <label class="form_label col-md-2"></label>
                                        <div id="ddExtendedHolidayReturns" class="col-md-4 form_inline">
                                            <input id="isAllowDelay" type="checkbox" name=""/> 
                                            <label for="chkExtendedHolidayReturns"> 提供节假日延期退货至12月31日。</label>
                                        </div>
                                    </div>
                                    <div id="dtReturnType" class="form-group">
                                        <label class="form_label col-md-2">退款方式</label>
                                        <div id="ddReturnType" class="col-md-4 form_inline">
                                            <select id="returnType" name="" class="form-control input_medium">
                                               
                                            </select>
                                        </div>
                                    </div>
                                    <div id="dtReturnShippingPayBy" class="form-group">
                                        <label class="form_label col-md-2">退货运费由谁负担</label>
                                        <div id="ddReturnShippingPayBy" class="col-md-4 form_inline">
                                            <select id="fareTakeInHander" name="" class="form-control input_medium">
                                               
                                            </select>
                                        </div>
                                    </div>
                                    <div id="dtReturnRestockingFee" class="form-group">
                                        <label class="form_label col-md-2">折旧费</label>
                                        <div id="ddReturnRestockingFee" class="col-md-4 form_inline">
                                            <select id="depreciationRate" name="" class="form-control input_medium">
                                                
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group last" id="returnPolicyDescriptionDiv" style="display:none">
                                        <label class="form_label col-md-2">退货政策详情</label>
                                        <div class="col-md-4 form_inline">
                                        	<input id="returnPolicyDescription" class="easyui-textbox" data-options="multiline:true"  style="width:600px;height:100px">
                                       
                                            <span class="help-block help_block_bottom"> <b class="numoutput returnsPolicyNum" id="returnPolicyDescriptionLength">0</b> / ( 最多 4000 字符. 不支持 HTML )</span>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div> 
  
</#macro>
