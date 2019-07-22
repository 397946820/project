<@FTL.admin id="TransportSettingList" title="运送选项列表" add_script_files=['admin/transportSetting/transportSetting.js','easyui/jquery.easyui.min.js','admin/publication/bootstrap.min.js'] add_style_files=['publication/bootstrap.min.css','publication/model.css','publication/easyui.css']>

<table id="transportSettingDataGrid" class="easyui-datagrid"  style="width:100%;height:100%">
	
</table>

<!--不运送地区数据-->
<div id="noShipDialog" class="easyui-dialog" title="不运送地区"
	style="width: 900px; height: 450px; padding: 10px;position: relative;"
	data-options="modal:true,
     closed:true,
     buttons: [{
        text:'确认',
        iconCls:'icon-save',
        handler:noShipSave
    },{
        text:'关闭',
        iconCls:'icon-no',
        handler:noShipNo
    }]">
	<div class="domestic" style="margin-left: 61px;" >
		<div class="domesticChoose">
			<div style=" margin=10px 0;font-weight:300;">Domestic</div>
			<div  class="subRegion">
				
			</div>
		</div>
		<div class="domesticChoose">
			<div style=" margin=10px 0;font-weight:300;">Domestic</div>
			<div  class="subRegion">
			</div>
		</div>
		<div class="domesticChoose">
			<div style=" margin=10px 0;font-weight:300;">Domestic</div>
			<div  class="subRegion">
			</div>
		</div>
	</div>
	<div class="international" data-got="" style="height: 230px;overflow: auto;">
	<div style=" margin=10px 0;font-weight:300;margin:0 0 5px 61px;">International</div>
		<div class="left" style="margin-left:60px">
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Africa" id="Africa"> <label for="Africa">非洲</label> <span>[<a
						class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Europe" id="Europe"> <label for="Europe">欧洲</label> <span>[<a
						class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Oceania" id="Oceania"> <label for="Oceania">大洋洲</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
		</div>
		<div class="center" style="display:inline-block; margin-left: 130px;">
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" id="Asia" value="Asia"> <label for="Asia">亚洲</label> <span>[<a
						class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" id="Middle East" value="Middle East"> <label for="Middle East">中东</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Southeast Asia" id="Southeast Asia"> <label for="Southeast Asia">东南亚</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
		</div>
		<div class="right" style="margin-right:100px;">
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Central America and Caribbean" id="Central America and Caribbean">
					<label for="Central America and Caribbean">中美洲和加勒比海</label> <span>[<a class="showAll"
						href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="North America" id="North America"> <label for="North America">北美洲</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="South America" id="South America"> <label for="South America">南美洲</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
		</div>
		<div id="divAdditionalLocations" style="margin-left: 58px;">
			<p>Additional Locations</p>
			<div class="subRegion">
				<input type="checkbox" id="POBox" value="PO Box"> <label for="POBox">PO Box</label>
			<div id="Packstationen2" style="display:none;display: inline-block;margin-left: 40px;"><input type="checkbox" id="Packstationen" value="Packstation"> <label for="Packstationen">Packstationen</label></div>
		</div>
	</div>	
	</div>
	
	<div class="showSelected" style="position: absolute;bottom: 0px;">
		<span class="noSelected">您尚未选择国家或地区</span> 
		<span class="selected"
			style="display: none;">您已经选择的国家或地区:&nbsp;&nbsp;&nbsp; <span id="noShip" style="font-weight:bold;"></span>
			[<a class="cancelSelected" href="javascript:void(0);">取消已选</a>]
		</span>
	</div>
</div>
<!--不运送地区数据结束-->
<div id="transportSettingDialog" class="easyui-dialog" style="width:800px;height:450px;" closed="true" buttons="#dlg-buttons">
		 <div  id="tabbox14">

                        <div class="model_form">
                        	<div style="margin:10px;margin-left:88px;">
				            	<input style="display:none" id="saveAsId" type="easyui-textbox" name="id">
				                <input name="name" class="easyui-textbox" id="title" required="true"  label="名称:" style="width:250px;margin-right:-30px">
				            </div>
				            <div>
								<div style="margin-bottom:20px;margin-left:88px;">
									<input type="text"  class="easyui-combobox" id="siteId" required="true"  name="ddleBaySiteID" label="站点:"  style="width:250px;margin-right:-30px"/>
								</div>
							</div>
                            <div style="margin:0 20px 20px 20px;border-bottom:1px solid #ccc;"><b id="DomesticShipping" class="model-style">国内运输<span id="tabbox15"></span></b></div>
                            <form class="form-horizontal" id="DomesticShippingFrom">
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
                                            <div class="form-group" style="display:none;">
                                                <label class="form_label col-md-2"></label>
                                                <div class="col-md-4 form_inline"><p class="shippingtitle">第一运输</p>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="form_label col-md-2">运输方式</label>
                                                <div class="col-md-10 form_inline">
                                                    <select id="domesticShipType" name="domesticShipType" class="form-control width_auto">
                                                        <option value="" selected="selected">-- 选择 --</option>
                                                        <optgroup id="domesticShipTypeOptions" label="Economy services - 经济服务">
                                                           
                                                        </optgroup>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="form_label col-md-2">运费</label>
                                                <div class="col-md-4 form_inline" style="padding:7px;">
                                                    <input name="domesticShipCost" id="domesticShipCost" type="text" value="0.00" class="form-control input_small"> 
                                                    <span class="CSymbol help-inline">USD</span>
                                                    <input id="domesticShipCostOpt"  type="checkbox" name="" checked="checked"> <label class="free" for="chkShippingFreeDom1">免费</label>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="form_label col-md-2">额外每件加收</label>
                                                <div class="col-md-4 form_inline">
                                                    <input id="domesticExtraCost" name="domesticExtraCost" type="text" value="0.00"  class="form-control input_small"> 
                                                    <span class="CSymbol help-inline">USD</span>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="form_label col-md-2">AK,HI,PR 额外收费</label>
                                                <div  class="col-md-4 form_inline">
                                                    <input id="domesticShipAkHiPr" name="domesticShipAkHiPr" type="text" value="0.00" class="form-control input_small"> 
                                                    <span class="CSymbol help-inline">USD</span>
                                                </div>
                                            </div>
                                        </div>
                                           
                                        <div class="form-group add_wrap">
                                            <label class="form_label col-md-2"></label>
                                            <div class="col-md-4 form_inline" style="display:none;">
                                                <a id="hrefAddShippingDom" class="createnew">添加</a>
                                                <a id="hrefRemoveShippingDom" class="createnew" style="display: none;">移除</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            
                            <div style="margin:0 20px 20px 20px;border-bottom:1px solid #ccc;"><b id="InternationalShipping" class="model-style">国际运输<span id="tabbox16"></span></b></div>
                            <form class="form-horizontal" id="InternationalShippingFrom">
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
									    <div class="col-md-4 form_inline" style="padding:7px;">
									     <input id="domesticShipCost1" name="domesticShipCost" type="text" value="0.00" class="form-control input_small" />
									     <span class="CSymbol help-inline">USD</span>
									     <input type="checkbox" name="chkShippingFreeInt1" checked="checked" />
									     <label class="free" for="chkShippingFreeInt1">免费</label>
									    </div>
									   </div>
									   <div id="dtShippingFeeAddInt1" class="form-group">
									    <label class="form_label col-md-2">额外每件加收</label>
									    <div id="ddShippingFeeAddInt1" class="col-md-4 form_inline">
									     <input id="domesticExtraCost1" name="domesticExtraCost" type="text" value="0.00" class="form-control input_small" />
									     <span class="CSymbol help-inline">USD</span>
									    </div>
									   </div>
									   <div class="form-group">
									    <label class="form_label col-md-2"> 运到</label>
									    <div class="col-md-10 form_inline">
									     <div>
									      <input name="chkShipWorldWide1" type="checkbox" id="chkShip2WorldWide1" checked="checked" readonly="" value="Worldwide" /> 
									      <label for="chkShip2WorldWide">全球 </label>
									     </div>
									    </div>
									   </div>
									  </div>
                                </div>
                                <div class="form-body">
                                    <div class="form-group">
                                        <label class="form_label col-md-2">不运送地区</label>
                                        <div class="col-md-4 form_inline">
                                            <select name="ddlExclusionListType" id="ddlExclusionListType" class="form-control input_medium">
                                                <option selected="selected" value="0">运输至所有国家</option>
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
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="saveTransport()" style="width:90px">确定</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#transportSettingDialog').dialog('close')" style="width:90px">关闭</a>
</div>
</@FTL.admin>
