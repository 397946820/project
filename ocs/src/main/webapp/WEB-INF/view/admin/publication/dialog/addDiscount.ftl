<#macro addDiscount>
            <div class="addDiscountModel" style="display: none;">
                <div class="dialogbox">
                    <div class="dialog">        
                        <div class="dialoghead" style="cursor:move">
                            <div class="dialog_title">折扣</div>      
                            <div class="dialog_close">X</div>
                        </div>
                        <div class="dialogmsghead">    
                            <form>
                                <div class="form-horizontal form-bordered">
                                    <div class="form-group">
                                        <label class="form_label col-md-2">名称</label>
                                        <div class="col-md-4 form_inline">
                                            <input type="text" maxlength="30" class="form-control">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="form_label col-md-2">eBay 账户</label>
                                        <div class="col-md-4 form_inline"> 
                                            <div class="input_ebayuser">
                                                <select name="eBayUser1$ddleBayUser" id="eBayUser1_ddleBayUser" class="form-control input_medium">
                                                    <option selected="selected" value="">le.deutschland</option>
                                                    <option value="">uk.le</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="form_label col-md-2">开始日期</label>
                                        <div class="col-md-4 form_inline">
                                            <div class="input-group date" id="datetimepicker1">  
                                                <input type="text" class="form-control input_small" />  
                                                <span class="input-group-addon">  
                                                    <span class="glyphicon glyphicon-calendar"></span>  
                                                </span>  
                                            </div> 
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="form_label col-md-2">结束日期</label>
                                        <div class="col-md-4 form_inline">
                                            <div class="input-group date" id="datetimepicker2">  
                                                <input type="text" class="form-control input_small" />  
                                                <span class="input-group-addon">  
                                                    <span class="glyphicon glyphicon-calendar"></span>  
                                                </span>  
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="form_label col-md-2">优惠明细</label>
                                        <div class="col-md-4 form_inline">
                                            <ul class="element-markdown-ul">
                                                <li>
                                                    <input id="chkDiscount" type="checkbox" name="chkDiscount" checked="checked">
                                                    <label for="chkDiscount">价格折扣（不包括拍卖物品）</label></li>
                                                <li>
                                                    <input id="rdobtnPercent" type="radio" name="rdobtnPercent" value="rdobtnPercent" checked="checked"> 
                                                    <label for="rdobtnPercent">在原价上给予折扣</label>
                                                    <input name="txtPercent" type="text" maxlength="5" id="txtPercent" class="form-control input_small"> %
                                                </li>
                                                <li>
                                                    <input id="rdobtnValue" type="radio" name="rdobtnValue" value="rdobtnValue"> 
                                                    <label for="rdobtnValue">在原价上降价</label>
                                                    <input name="txtValue" type="text" maxlength="5" id="txtValue" class="form-control input_small">
                                                </li>
                                                <li>
                                                    <input id="chkFreeShipping" type="checkbox" name="chkFreeShipping">
                                                    <label for="chkFreeShipping" class="free">免运费（用于第一运输方法）</label>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div  class="dialog_button">
                                    <input type="hidden" name="category">               
                                    <input type="button" value="确定" class="dialogbutton">
                                    <input type="button" value="更新eBay" class="dialogbutton">
                                    <input type="button" value="关闭" class="dialogbutton">
                                </div>
                            </form>
                        </div> 
                    </div>
                </div>
            </div>
  
</#macro>
