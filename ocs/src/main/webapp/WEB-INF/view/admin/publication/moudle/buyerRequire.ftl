<#macro buyerRequire>
                   <div class="blue_madison" id="tabbox11"  link="BUYER_REQ">
                        <div class="model_title">
                            <div class="caption" id="Buyer">
                                <span>买家要求</span>
                            </div>
                            <div class="actions">
                                <span class="btn btn_default btn_sm" id="buyerRequireSelect" title="选择">选择 </span>
                                <span class="btn btn_default btn_sm" id="buyerRequireSelectSave" title="另存为">另存为 </span>
                            </div>
                        </div>
                        <div class="model_form">
                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label class="form_label col-md-2">买家要求</label>
                                    <div class="col-md-4 form_inline">
                                        <input type="radio" name="BuyerRequirementSpecified" id="rbtnBuyerRequirementSpecified1" value="0" checked="checked"> 
                                        <label for="rbtnBuyerRequirementSpecified1"> 允许所有买家购买我的物品</label> <br>
                                        <input type="radio" name="BuyerRequirementSpecified" id="rbtnBuyerRequirementSpecified2" value="1"> 
                                        <label for="rbtnBuyerRequirementSpecified2">不允许以下买家购买我的物品</label>
                                        <ul id="ulBuyerRequirementDetail" class="buyerrequirement" style="display: none;">
                                            <li id="liLinkedPayPalAccount">
                                                <input id="isHavePaypalAccount" type="checkbox" name="chkLinkedPayPalAccount"> 
                                                <label for="isHavePaypalAccount">没有 PayPal 账户</label></li>
                                            <li>
                                                <input id="isOffscale" type="checkbox" name="chkShipToRegistrationCountry"> 
                                                <label for="isOffscale">主要运送地址在我的运送范围之外</label>
                                             </li>
                                            <li>
                                                <input id="chkMaxUnpaidItemStrikesInfoSpecified" type="checkbox" name="chkMaxUnpaidItemStrikesInfoSpecified"> <label for="chkMaxUnpaidItemStrikesInfoSpecified">曾收到</label>
                                                <select name="ddlMaxUnpaidItemStrikesInfoCount" disabled id="ddlMaxUnpaidItemStrikesInfoCount" class="form-control input_xsmall">
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                </select>个弃标个案，在过去
                                                <select name="ddlMaxUnpaidItemStrikesInfoPeriod" disabled id="ddlMaxUnpaidItemStrikesInfoPeriod" class="form-control input_xsmall">
                                                    <option value="Days_30">30</option>
                                                    <option value="Days_180">180</option>
                                                    <option value="Days_360">360</option>
                                                </select>天
                                            </li>                    
                                            <li>
                                                <input id="chkMaxBuyerPolicyViolationsSpecified" type="checkbox" name="chkMaxBuyerPolicyViolationsSpecified"> 
                                                <label for="chkMaxBuyerPolicyViolationsSpecified">曾收到</label>
                                                <select name="ddlMaxBuyerPolicyViolationsCount" disabled id="ddlMaxBuyerPolicyViolationsCount" class="form-control input_xsmall">
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                    <option value="6">6</option>
                                                    <option value="7">7</option>
                                                </select>个违反政策检举，在过去
                                                <select name="ddlMaxBuyerPolicyViolationsPeriod" disabled id="ddlMaxBuyerPolicyViolationsPeriod" class="form-control input_xsmall">
                                                    <option value="Days_30">30</option>
                                                    <option value="Days_180">180</option>
                                                </select>天
                                            </li>
                                            <li>
                                                <input id="chkMinFeedbackScoreSpecified" type="checkbox" name="chkMinFeedbackScoreSpecified"> 
                                                <label for="chkMinFeedbackScoreSpecified">信用指标等于或低于：</label>
                                                <select name="ddlMinFeedbackScore" disabled id="ddlMinFeedbackScore" class="form-control input_xsmall">
                                                    <option value="-1">-1</option>
                                                    <option value="-2">-2</option>
                                                    <option value="-3">-3</option>
                                                </select>
                                            </li>
                                            <li>
                                                <input id="chkMaxItemRequirementsMaxItemCountSpecified" type="checkbox" name="chkMaxItemRequirementsMaxItemCountSpecified"> 
                                                <label for="chkMaxItemRequirementsMaxItemCountSpecified">在过去10天内曾出价或购买我的物品，已达到我所设定的限制</label>
                                                <select name="ddlMaxItemRequirementsMaxItemCount" disabled id="ddlMaxItemRequirementsMaxItemCount" class="form-control input_xsmall">
                                                    <option value="1">1</option>
                                                    <option value="2">2</option>
                                                    <option value="3">3</option>
                                                    <option value="4">4</option>
                                                    <option value="5">5</option>
                                                    <option value="6">6</option>
                                                    <option value="7">7</option>
                                                    <option value="8">8</option>
                                                    <option value="9">9</option>
                                                    <option value="10">10</option>
                                                    <option value="25">25</option>
                                                    <option value="50">50</option>
                                                    <option value="75">75</option>
                                                    <option value="100">100</option>
                                                </select>
                                            </li>
                                            <li>
                                                <ul>
                                                    <li>
                                                        <input id="chkMaxItemRequirementsMinFeedbackScoreSpecified" type="checkbox" name="chkMaxItemRequirementsMinFeedbackScoreSpecified"> 
                                                        <label for="chkMaxItemRequirementsMinFeedbackScoreSpecified">这项限制只适用于买家信用指数等于或低于</label>
                                                        <select name="ddlMaxItemRequirementsMinFeedbackScore" disabled id="ddlMaxItemRequirementsMinFeedbackScore" class="form-control input_xsmall">
                                                            <option value="5">5</option>
                                                            <option value="4">4</option>
                                                            <option value="3">3</option>
                                                            <option value="2">2</option>
                                                            <option value="1">1</option>
                                                            <option selected="selected" value="0">0</option>
                                                        </select>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
  
</#macro>
