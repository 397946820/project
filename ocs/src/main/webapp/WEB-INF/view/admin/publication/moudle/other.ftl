<#macro other>
                    <div class="blue_madison" id="tabbox18" >
                        <div class="model_title">
                            <div class="caption" id="Other">
                                <span>其它</span>
                            </div>
                        </div>
                        <div class="model_form">
                            <form class="form-horizontal">
                                <div id="dlSalesTax" class="form-group" style="display:none;">
                                    <label class="form_label col-md-2">销售税</label>
                                    <div class="col-md-4 form_inline">
                                        <select id="salesTaxType" name="ddlSalesTaxState"  class="form-control input_small">
                                            <option selected="selected" value="">-- 选择 --</option>
                                            <option value="AL">Alabama</option>
                                            <option value="AK">Alaska</option>
                                            <option value="AS">American Samoa</option>
                                            <option value="AZ">Arizona</option>
                                            <option value="AR">Arkansas</option>
                                            <option value="CA">California</option>
                                            <option value="CO">Colorado</option>
                                            <option value="CT">Connecticut</option>
                                            <option value="DE">Delaware</option>
                                            <option value="DC">District of Columbia</option>
                                            <option value="FL">Florida</option>
                                            <option value="GA">Georgia</option>
                                        </select>
                                        <input name="txtSalesTaxPercent" type="text" value="0.00" id="txtSalesTaxPercent" class="form-control input_xsmall"> %
                                        <input id="chkSalesTaxShippingIncludedInTax" type="checkbox" name="chkSalesTaxShippingIncludedInTax"> 
                                        <label for="chkSalesTaxShippingIncludedInTax"> 运费包括销售税</label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="form_label col-md-2">标签</label>
                                    <div class="col-md-4 form_inline">
                                        <div id="upMarkingTag">
                                            <div class="profile_tag">
                                                
                                            </div>       
                                        </div>
                                        <span id="upNotMarkTag">
                                            <div id="spMoveTo" class="btn-group" style="margin-left: 0;">
                                                <button type="button" class="btn default btn-sm dropdown-toggle" data-toggle="dropdown">标签</button>
                                                <ul class="dropdown-menu" role="menu">
                                                    <li>
                                                        <input type="hidden" name="" value="UK 固价">
                                                        <a>UK 固价</a>
                                                    </li>  
                                                    <li>
                                                        <input type="hidden" name="" id="" value="UK 多属性">
                                                        <a>UK 多属性</a>
                                                    </li>
                                                    <li>
                                                        <input type="hidden" name="" id="" value="UK 拍卖">
                                                        <a>UK 拍卖</a>
                                                    </li>
                                                   
                                                </ul>
                                            </div>
                                        </span>   
                                    </div>
                                </div>
                                
                                <div class="form-group last" >
                                    <label class="form_label col-md-2">eBay 建议</label>
                                    <div class="col-md-4 form_inline" style="margin-bottom:50px;">
                                    <input id="otherDescription" class="easyui-textbox" data-options="multiline:true"  style="width:600px;height:100px;">
                                       
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>	         	                                    
	
</#macro>
