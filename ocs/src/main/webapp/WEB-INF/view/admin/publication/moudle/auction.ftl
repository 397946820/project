<#macro auction>
<div class="blue_madison" id="tabbox9">
  <div class="model_title">
    <div class="caption" id="Auction">
      <span>拍卖</span>
    </div>
  </div>
  <div class="model_form">
   <form class="form-horizontal">
                                <div class="form_body"  style="display:none">
                                    <div class="form-group">
                                        <label class="form_label col-md-2"></label>
                                        <div class="col-md-4 form_inline">
                                            <span class="green">请确认您的eBay账号开通了商店，否则无法使用折扣。</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-body">
                                    <div class="form-group" style="display:none">
                                        <label class="form_label col-md-2">折扣</label>
                                        <div class="col-md-4 form_inline">
                                            <span id="UpdatePanel6">
                                                    <select id="discountId" name="ddlMarkDown" id="ddlMarkDown" class="form-control input_small">
                                                        <option value="" selected="selected">-- 选择 --</option>
                                                        <optgroup label="Active">
                                                            <option value="长期提价折扣  10% discount">长期提价折扣  10% discount</option>
                                                            <option value="Weekly Deals 10% （原价打折）">Weekly Deals 10% （原价打折）</option>
                                                            <option value="Weekly Deals 5%（原价折扣）">Weekly Deals 5%（原价折扣）</option>
                                                        </optgroup>
                                                    </select>
                                                </span>
                                            <a id="hrefMarkdownEdit" > 编辑</a> 
                                            <a id="hrefMarkdownAdd"> 添加</a>
                                        </div>
                                    </div>
                                    <div class="form-group"  style="display:none">
                                        <label class="form_label col-md-2">私人拍卖</label>
                                        <div class="col-md-4 form_inline">
                                            <input id="chkPrivateAuction" type="checkbox" name="chkPrivateAuction"> <label for="chkPrivateAuction"> 不向公众显示买家的名称</label>
                                        </div>
                                    </div>
                                    <div class="form-group" link="PUBLICATION_DAYS">
                                        <label class="form_label col-md-2">刊登天数</label>
                                        <div class="col-md-4 form_inline">
                                            <div id="UpdatePanel2">
                                                <select id="publicationDays" name="ddlDuration" id="ddlDuration" class="form-control input_xsmall">
                                                    <option value="Days_1">1</option>
                                                    <option value="Days_3">3</option>
                                                    <option value="Days_5">5</option>
                                                    <option value="Days_7">7</option>
                                                    <option value="Days_10">10</option>
                                                </select>天  
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-body">
                                    <div class="form-group" link="PRICE">
                                        <label class="form_label col-md-2" id="auctionPriceLabelName">起始价格</label>
                                        <div class="col-md-4 form_inline">
                                            <input value="" id="auctionPrice" type="text" value="0.00" message="起始价格/价格" class="form-control input_xsmall ocs_number" >
                                            <span class="CSymbol">USD</span>
                                        </div>
                                    </div>
                                    <div class="form-group" id="dtReservePrice">
                                        <label class="form_label col-md-2">保留价</label>
                                        <div class="col-md-4 form_inline" id="">
                                            <input value="" id="reserverPrice" type="text" message="保留价格" value="" class="form-control input_xsmall ocs_number">
                                            <span class="CSymbol">USD</span>
                                        </div>
                                    </div>
                                    <div class="form-group" id="dtBINPrice" link="PRODUCT_COUNT">
                                        <label class="form_label col-md-2">数量</label>
                                        <div class="col-md-4 form_inline" i>
                                            <input  id="productCount" type="text" value="1" message="数量" class="form-control input_xsmall ocs_number ocs_required">
                                            
                                        </div>
                                    </div>
                                    <div class="form-group" id="">
                                        <label class="form_label col-md-2">销售比基数</label>
                                        <div class="col-md-4 form_inline" >
                                            <input  id="sellerBaseCount" type="text" value="1" message="销售比基数" class="form-control input_xsmall ocs_number">
                                            
                                        </div>
                                    </div>
                                    <div class="form-group" id="isOrNotChangePriceDiv" style="display:none;" link="ACCEPT_BUYER_COUNTER">
                                        <label class="form_label col-md-2">是否接受议价</label>
                                        <div class="col-md-4 form_inline" >
                                            <input  id="isOrNotChangePrice" type="checkbox" value="" >
                                        </div>
                                    </div>
                                    <div class="form-group" id="ChangePriceDivAccept" style="display:none;" >
                                        <label class="form_label col-md-2">自动接受价格</label>
                                        <div class="col-md-4 form_inline" >
                                            <input  id="ChangePriceAccept" type="text" value="" message="自动接受价格" class="form-control input_xsmall ocs_number">
                                             <span class="CSymbol">USD</span>
                                        </div>
                                    </div>
                                    <div class="form-group" id="ChangePriceDivRefuse" style="display:none;" >
                                        <label class="form_label col-md-2">自动拒绝价格</label>
                                        <div class="col-md-4 form_inline" >
                                            <input  id="ChangePriceRefuse" type="text" value="" message="自动拒绝价格" class="form-control input_xsmall ocs_number">
                                             <span class="CSymbol">USD</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group" id="dlSecondChanceOfferLabel" style="display: none;">
                                    <label class="form_label col-md-2"></label>
                                    <div class="col-md-4 form_inline">
                                        <input id="chkSecondChanceOfferEnabled" type="checkbox" name="chkSecondChanceOfferEnabled"> 
                                        <label for="chkSecondChanceOfferEnabled">二次交易机会</label>
                                    </div>
                                </div>
                                <div class="form-body" id="dlSecondChanceOffer" style="display: none;">
                                    <div class="form-group">
                                        <label class="form_label col-md-2">价格</label> 
                                        <div class="col-md-4 form_inline">
                                            <input id="secondAuctionPrice"  name="txtSecondChanceOfferPrice" type="text" id="txtSecondChanceOfferPrice"  class="form-control input_small ">
                                            <span class="CSymbol">USD</span>
                                            <select id="secondAuctionDays" name="ddlSecondChanceOfferDuration" id="ddlSecondChanceOfferDuration" class="form-control input_xsmall">
                                                <option value="Days_1">1</option>
                                                <option value="Days_3">3</option>
                                                <option value="Days_5">5</option>
                                                <option value="Days_7">7</option>
                                            </select>天
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="form_label col-md-2">消息</label>
                                        <div class="col-md-4 form_inline">
                                            <textarea id="secondAuctionMessage" name="txtSecondChanceOfferMsg" rows="2" cols="20" id="txtSecondChanceOfferMsg" class="form-control"></textarea>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>	
 
</#macro>
