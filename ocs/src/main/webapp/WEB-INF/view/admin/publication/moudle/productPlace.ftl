<#macro productPlace>
                    <div class="blue_madison" id="tabbox13" link="PRODUCT_ADDRESS">
                        <div class="model_title">
                            <div class="caption" id="Loaction">
                                <span>物品所在地</span>
                            </div>
                            <div class="actions">
                                <span class="btn btn_default btn_sm" id="productPlaceSelect" title="选择">选择 </span>
                                <span class="btn btn_default btn_sm" id="productPlaceSelectSave" title="另存为">另存为 </span>
                            </div>
                        </div>
                        <div class="model_form">
                            <form class="form-horizontal">
                                <div class="form-group" link="PRODUCT_ADDRESS">
                                    <label class="form_label col-md-2">物品所在地</label>
                                    <div class="col-md-4 form_inline">
                                        <input value="" id="productAddress" name="" type="text"  class="form-control input_medium">
                                    </div>
                                </div>
                                <div class="form-group" link="REGION">
                                    <label class="form_label col-md-2">国家或地区</label>
                                    <div class="col-md-4 form_inline">
                                    <input type="text"  class="easyui-combobox" id="region" name="ddleBaySiteID"  style="width:250px;"  data-options="valueField:'country',textField:'description',url:'/ocs/publication/getRegionList'"/>
										
                                    </div>
                                </div>
                                <div class="form-group" link="POST_CODE" >
                                    <label class="form_label col-md-2">邮编</label>
                                    <div class="col-md-4 form_inline">
                                        <input value="" id="postCode" name="" type="text" class="form-control input_medium">
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
 
</#macro>
