<#import "list.ftl" as AREACONTENT />
<@FTL.admin id="NoArriveRegionList" title="不运送地区"
add_script_files=['admin/noArriveRegion/noArriveRegion.js']
add_style_files=['noShip.css']>


<#macro transportChoose>
   <div class="blue_madison" id="tabbox14" Link="">
        <div class="model_title">
            <div class="caption" id="Shipping">
                <span>运送选项</span>
            </div>
            <div class="actions">
                <span class="btn btn_default btn_sm" id="transportChooseSelect" title="选择">选择 </span>
                <span class="btn btn_default btn_sm" id="transportChooseSelectSave" title="另存为">另存为 </span>
            </div>
        </div>
        <div class="model_form">
            <h4 id="DomesticShipping">国内运输<span id="tabbox15"></span></h4>
            <form class="form-horizontal" link="domesticTrans">
                <div class="form-body">
                    <div class="form-group" style="display:none;">
                        <label class="form_label col-md-2">处理时间</label>
                        <div class="col-md-4 form_inline">
                            <p class="setitle green" style="padding-left: 0; display: block; width: 100%;">处理时间 0 代表同一工作日</p>
                            <select name="" id="ddlDispatchTimeMax" class="form-control input_xsmall">

                                <option value="0">0</option>
                                <option value="1" selected>1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="10">10</option>
                                <option value="15">15</option>
                                <option value="20">20</option>
                                <option value="30">30</option>
                                <option value="40">40</option>
                            </select>工作日
                            <input id="chkGetItFast" type="checkbox" name="Shipping1$chkGetItFast">
                            <label for="chkGetItFast">快速寄货</label>
                        </div>
                    </div>
                </div>
                <div class="DomesticShipping_wrap">
                    <div class="form-body shippingFirst">
                    	 <div class="standard d_trans_flag">
                            <div class="form-group">
                                <label class="form_label col-md-2"></label>
                                <div class="col-md-4 form_inline"><p class="shippingtitle">第一运输</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form_label col-md-2">运输方式</label>
                                <div class="col-md-10 form_inline">
                                    <select id="domesticShipType" name="domesticShipType" class="form-control width_auto ocs_required" message="运输方式">
                                        <optgroup id="domesticShipTypeOptions" label="Economy services - 经济服务">
                                           
                                        </optgroup>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form_label col-md-2">运费</label>
                                <div class="col-md-4 form_inline">
                                    <input name="domesticShipCost" id="domesticShipCost" type="text" message="运费" value="0.00" class="ocs_number form-control input_small"> 
                                    <span class="CSymbol help-inline">USD</span>
                                    <input id="domesticShipCostOpt"  type="checkbox" name="" checked="checked"> <label class="free" for="chkShippingFreeDom1">免费</label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form_label col-md-2">额外每件加收</label>
                                <div class="col-md-4 form_inline">
                                    <input id="domesticExtraCost" name="domesticExtraCost" message="额外每件加收" type="text" value="0.00"  class="ocs_number form-control input_small"> 
                                    <span class="CSymbol help-inline">USD</span>
                                </div>
                            </div>
                            <div class="form-group" style="display:none;">
                                <label class="form_label col-md-2">AK,HI,PR 额外收费</label>
                                <div  class="col-md-4 form_inline">
                                    <input id="domesticShipAkHiPr" name="domesticShipAkHiPr" type="text" value="0.00" class="form-control input_small"> 
                                    <span class="CSymbol help-inline">USD</span>
                                </div>
                            </div>
                        </div>
                           
                        <div class="form-group add_wrap" style="display: none;" >
                            <label class="form_label col-md-2"></label>
                            <div class="col-md-4 form_inline" style="display:none;">
                                <a id="hrefAddShippingDom" class="createnew">添加</a>
                                <a id="hrefRemoveShippingDom" class="createnew" style="display: none;">移除</a>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <h4 id="InternationalShipping">国际运输<span id="tabbox16"><input type="checkbox" id="InternationalShippingIsUse" checked="checked"></span></h4>
            <form class="form-horizontal" id="InternationalShippingFrom" link="calCulateTrans">
            	
                <div class="form-body InternationalShipping_wrap">
                	
					  <div class="add_internationalShipping d_trans_flag2" >
					   <div class="form-group" style="display:none;">
					    <label class="form_label col-md-2"></label>
					    <div class="col-md-4 form_inline">
					     <p class="shippingtitle">第一运输</p>
					    </div>
					   </div>
					   <div class="form-group">
					    <label class="form_label col-md-2">运输方式</label>
					    <div class="col-md-10 form_inline">
					     <select id="domesticShipType1" name="domesticShipType" class="form-control width_auto"> <option value="" selected="selected">-- 选择 --</option></select>
					    </div>
					   </div>
					   <div class="form-group">
					    <label class="form_label col-md-2">运费</label>
					    <div class="col-md-4 form_inline">
					     <input id="domesticShipCost1" name="domesticShipCost" message="运费" type="text" value="0.00" class="ocs_number form-control input_small" />
					     <span class="CSymbol help-inline">USD</span>
					     <input type="checkbox" id="chkShippingFreeInt1" name="chkShippingFreeInt1" checked="checked" />
					     <label class="free" for="chkShippingFreeInt1">免费</label>
					    </div>
					   </div>
					   <div id="dtShippingFeeAddInt1" class="form-group">
					    <label class="form_label col-md-2">额外每件加收</label>
					    <div id="ddShippingFeeAddInt1" class="col-md-4 form_inline">
					     <input id="domesticExtraCost1" name="domesticExtraCost" type="text" message="额外每件加收" value="0.00" class="form-control input_small ocs_number" />
					     <span class="CSymbol help-inline">USD</span>
					    </div>
					   </div>
					   <div class="form-group">
					    <label class="form_label col-md-2"> 运到</label>
					    <div class="col-md-10 form_inline">
					     <div>
					      <input name="chkShipWorldWide1" type="checkbox" id="chkShip2WorldWide1" checked="checked" value="Worldwide" /> 
					      <label for="chkShip2WorldWide1">全球 </label>
					     </div>
					      <div id="shipwideOtherDiv">
					     
					     </div>
					    </div>
					   </div>
					  </div>
                	
                	
                	
                    <div class="form-group addInternation_wrap" id="hrefAddShippingInt1">
                        <label class="form_label col-md-2"></label>
                        <div class="col-md-4 form_inline" >
                            <a id="hrefAddShippingInt" class="createnew" style="display:none;"> 添加</a> 
                            <a id="hrefRemoveShippingInt" class="createnew" style="display: none;">  移除</a>
                        </div>
                    </div>  
                </div>
                
            </form>
             <h4 id="noShippingLocationChoose">不运送区域<span id="tabbox17"></span></h4>
            <form class="form-horizontal" id="" link="SHIP_LOCATION_OVER">
            <div class="form-body">
                    <div class="form-group">
                    
                        <label class="form_label col-md-2">不运送地区</label>
                        <div class="col-md-4 form_inline">
                            <select name="ddlExclusionListType" id="ddlExclusionListType" class="form-control input_medium">
                                <option value="0" selected="selected">运输至所有国家</option>
                                <option value="1">使用 eBay 站点设置</option>
                                <option value="2">选择不运送地区</option>
                            </select>
                        </div>
                    </div>  
                    <div class="form-body" id="dlEeclusionList" style="display:none;">
                       <div class="form-group last">
                            <label class="form_label col-md-2"></label>
                            <div class="col-md-10 form_inline">
                            	</br>
                            	<div class="showSelected" style="text-align:left;margin:0;">
									<span class="noArray-noSelected">您尚未选择国家或地区</span> 
									<span class="noArray-selected"style="display: none;">您已经选择的国家或地区:&nbsp;&nbsp;&nbsp;								
										 <span id="noArriveRegion" style="font-weight:bold;"></span>
										[<a class="noArray-cancelSelected" href="javascript:void(0);">取消已选</a>]
									</span>
								</div>
								</br>
                                <p class="setitle green" style="padding-left: 0; display: block; width: 100%;">在您创建了不运送地区列表之后，系统会自动开启【买家条件】功能。 在不运送列表国家或地区注册的用户，将被自动屏蔽掉。不过，您可以随时做更改。</p>
                                <a class="createnew"><span id="allCreate">编辑不运送地区列表</span></a>
                            </div>
                            
                        </div>
                    </div>
                </div>
	            
            </form>
        </div>
    </div> 
    <@AREACONTENT.areaName></@>
   
</#macro>

</@FTL.admin>