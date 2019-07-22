<#macro publicationBaseInfo>
			<div class="blue_madison" id="tabbox1">
				<input type="hidden" id="publicationId" value="${(publicationVO.id)!''}">
				<input type="hidden" id="publicationItemId" value="${(publicationVO.itemId)!''}">
				<input type="hidden" id="endPublicationDate" value="${(publicationVO.end_publication_date)!''}">
						<div class="model_title">
							<div class="caption">
	                            <span id="GeneralInfo">一般信息</span>
	                        </div>
	                        <div class="actions">
                                <span id="spShowRemark" class="btn btn_default btn_sm" title="备注">备注 </span>
                            </div>
						</div>
						<div class="model_form">
							<div class="generalInfo">
								<form class="form-horizontal">
									<div class="form-group" link="TEMPLATE_NAME">
                                        <label class="form_label col-md-2">名称</label>
                                        <div class="col-md-4 form_inline">
                                            <input id="baseInfoName" class="ocs_required" message="名称" name="txteBayProfileName" value="${(publicationVO.templateName)!''}" type="text" maxlength="60" style="width: 550px" class="form-control" >
                                        </div>
                                    </div>
                                    <div class="form-group" id="dlRemark" style="display: none;">
                                        <label class="form_label col-md-2">备注</label>
                                        <div class="col-md-4 form_inline">
                                            <textarea name="txtRemark" id="txtRemark" class="form-control" style="overflow: hidden; height: 69px;"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-group" link="EBAY_ACCOUNT">
                                        <label class="form_label col-md-2">eBay 账户</label>
                                        <div class="col-md-10 form_inline"> 
                                        	<input type="text"  id="ebayAccount" name="eBayUserList" required="true" style="width:250px;"  />
                                        </div>
                                    </div>
                                    <div class="form-group" link="SITE_ID">
                                        <label class="form_label col-md-2">站点</label>
                                        <div class="col-md-4 form_inline" id="site2">
                                        	<input type="text"  id="site" required="true" style="width:250px;"/>
                                        </div>
                                    </div>
                                    <div class="form-group" link="PUBLICATION_TYPE">
                                        <label class="form_label col-md-2">刊登类型</label>
                                        <div class="col-md-6 form_inline">
                                            <div id="upeBayListType">
                                            	 <input id="publictionType" type="text" name="publictionType" value="Chinese" style="display:none;">
                                                <ul id="rbleBayListType">
													<li class="tosell">
                                                        <input id="rbleBayListType1" type="radio" name="rbleBayListType" value="Chinese" checked="checked">
                                                        <label for="rbleBayListType1">拍卖</label>
                                                    </li>
													<li class="price">
                                                        <input id="rbleBayListType2" type="radio" name="rbleBayListType" value="FixedPriceItem">
                                                        <label for="rbleBayListType2">固价</label>
                                                    </li>
													<li class="propert2">
                                                        <input id="rbleBayListType20" type="radio" name="rbleBayListType" value="FixedPriceItem1">
                                                        <label for="rbleBayListType20">多属性</label>
                                                    </li>
												</ul>                                                        
											</div>
                                        </div>
                                    </div>
                                    <div class="form-group" id="dtProduct" link="SKU">
                                        <label class="form_label col-md-2">SKU</label>
                                        <div class="col-md-4 form_inline" id="ddProduct">
                                            <input id="productSKU" class="ocs_required" message="SKU" value="" name="txtSKU" type="text" id="txtSKU" class="form-control input_medium">
                                            <input id="skuId" name="skuId" type="hidden"/>
                                            <a href="javascript:void(0);" id='productChoice'>选择产品</a>
                                        </div>
                                        <span class="skustatus help-inline"></span>
                                    </div>
                                 <div class="form-group">
                                        <label class="form_label col-md-2">物品号</label>
                                        <div class="col-md-4 form_inline">
                                            <input id="baseInfoItemId"  message="物品号" name="itemId" value="${(publicationVO.itemId)!''}" readonly="true" type="text" maxlength="60" style="width: 250px"  >
                                        </div>
                                    </div>
                                    <div class="form-group" link="PRODUCT_TITLE,PRODUCT_SUBTILTE">
                                        <label class="form_label col-md-2">物品标题</label>
                                        <div class="col-md-10 form_inline">
                                            <div id="UpdatePanel1">
                                                <div class="protitle" id="tbItemTitle">
                     
                                                			<dl>
			                                                    <dt>标题</dt>
			                                                    <dd>
			                                                        <input  id="itemTitle" message="标题" name="itemTitle" type="text" maxlength="80" message="标题" style="width:550px;" class=" ocs_required form-control">
			                                                        <span style="color:green">(<span class="overWords">0</span>/80)</span>
			                                                        <a class="select" href="javascript:void(0);" style="display: none;">移除</a>
		                                                        </dd>
			                                                    <dt>
			                                                                                                                                                                  子标题
			                                                    </dt>
			                                                    <dd>
			                                                        <input  id="itemSubtitle" name="itemSubtitle" type="text" maxlength="55" style="width:550px;" class="form-control">
			                                                         <span style="color:green">(<span class="overWords">0</span>/55)$收费</span>
			                                                    </dd>
		                                               	 	</dl>
                                            
	                                                
                                                </div>
                                        	</div>
                                   		</div>
                                    </div>
                                   
								</form>
							</div>
							<h4 id="Category">分类</h4>
							<div class="form-horizontal form-bordered">
                                <div class="form-group" link="PRODUCT_FIRST_CATEGORY_ID">
                                    <label class="form_label col-md-2">第一分类</label>
                                    <div class="col-md-10 form_inline">
                                        <span id="upeBayCatID1">
                                            <input value="" id="productFirstCategory" class="ocs_required" message="分类" name="txteBayCatID1" type="text" value="" class="form-control input_medium"><span></span>
                                        </span>
                                        <span class="help-inline">
                                            <a id="hrefBrowseCatID1" class="select">选择分类</a>
                                            <a id="hrefSearchCatID1" class="select" >搜索</a>
                                            <a  class="select category_clear" >清除</a>
                                        </span>
                                        <span id="upeBayCatText1">
                                            
                                        </span>
                                        <span style="display:none;" id="upeBayCatId1"></span>
                                    </div>
                                </div>
                                <div class="form-group" link="PRODUCT_SECOND_CATEGORY_ID">
                                    <label class="form_label col-md-2" id="tabbox3">第二分类</label>
                                    <div class="col-md-10 form_inline">
                                        <span id="upeBayCatID2">
                                                <input value="" id="productSecondCategory" name="txteBayCatID2" type="text" class="form-control input_medium">
                                        </span>
                                        <span class="help-inline">
	                                        <a id="hrefBrowseCatID2" class="select">选择分类</a>
	                                        <a id="hrefSearchCatID2" class="select" >搜索</a>
	                                        <a  class="select category_clear" >清除</a></span>
                                        <span id="upeBayCatText2">
                                            
                                        </span>
                                        <span style="display:none;" id="upeBayCatId2"></span>
                                    </div>
                                </div>
                            </div>	
                            <h4 id="StoreCategory">商店分类</h4>
                            <div class="form-horizontal form-bordered">
                                <div class="form-group" >
                                    <label class="form_label col-md-2"></label>
                                    <div class="col-md-4 form_inline">
                                        <span class="green">请确认您的eBay账号开通了商店，否则无法使用商店分类。 </span>
                                    </div>
                                </div>
                                <div class="form-group" link="STORE_FIRST_CATEGORY_ID">
                                    <label class="form_label col-md-2">第一分类</label>
                                    <div class="col-md-10 form_inline">
                                        <span id="upeBayStoreCat1">
                                            <input value="" id="storeFirstCategory" name="txteBayStoreCatID1" type="text" id="txteBayStoreCatID1" class="form-control input_medium">
                                        </span>
                                        <span class="help-inline">
                                        	<a class="select" id="selectStore1">选择分类</a>
                                        	 <a id="hrefSearchStoreCatID1" class="select" >搜索</a>
                                        	 <a  class="select category_clear" >清除</a>
                                        </span>
                                      <span id="UpdatePanel3">
                                            <span id="lbleBayStoreCatText1" class="categoryname"></span>
                                        </span>
                                        <span style="display:none;" id="upeBayCatId3"></span>
                                    </div>
                                </div>
                                <div class="form-group" link="STORE_SECOND_CATEGORY_ID">
                                    <label class="control-label col-md-2">第二分类</label>
                                    <div class="col-md-10 form_inline" id="tabbox4">
                                        <span id="upeBayStoreCat2">
                                            <input value="" id="storeSecondCategory" name="txteBayStoreCatID2" type="text" id="txteBayStoreCatID2" class="form-control input_medium">
                                        </span>
                                        <span class="help-inline">
                                        	<a class="select" id="selectStore2"> 选择分类</a>
                                        	 <a id="hrefSearchStoreCatID2" class="select" >搜索</a>
                                        	  <a  class="select category_clear" >清除</a>
                                        </span>
                                        <span id="UpdatePanel4">
                                            <span id="lbleBayStoreCatText2" class="categoryname"></span>
                                        </span>
                                        <span style="display:none;" id="upeBayCatId4"></span>
                                       
                                    </div>
                                </div>
                            </div>	
                            <div class="model_title" id="productAttrTabDiv">
	                             <div class="caption">
	                          	 	 <span id="ItemSpecificsandCondition">物品属性与状况 </span>
	                          	  </div>
	                          	  <div class="actions">
	                          	    <span class="btn btn_default btn_sm" id="itemPropertiesSelect" title="选择">选择 </span>
	                                <span class="btn btn_default btn_sm"  id="itemPropertiesSave" title="另存为">另存为 </span>
	                               </div>
                             </div>
                            
                            <div class="form-horizontal form-bordered" ">
                            	<div id="UpdatePanel8">
                                    <div class="form-group">
                                    	<!--<br class="form_label " /><br class="form_label " />-->
                                        <div> 
	                                        <label class="form_label col-md-2"  id="productCode" name="productCode">UPC&EAN&ISBN</label>
	                                        <div class="col-md-4 form_inline after" >
	                                        	<div style="display:none;" id="dlCatalog" >           	
													<div class="left">
														<input id="productEAN_ISBN_UPC" class='ocs_required' message="UPC" name="txtProdReferenceID" type="text">
														<div class="notApply" style="float:right"> 
															<a href="javascript:void(0);">Does not apply</a>
														</div>
														
													</div> 
													     
													<img src="" class="code" alt="codeImg" style="display:none;"/>
												</div>
												<div class="code_tip" style="display:none;">
													<a href="http://4.pushauction.com/Help/HelpDetail.aspx?MenuID=102006&amp;id=7173bc60-74ce-43e5-bcac-7ab1ee31f926">查看产品识别码使用帮助</a>
												</div>
											</div>
										</div>
                                    </div>
								</div>
							</div>	
							<div class="form-horizontal form-bordered" id="dlCompatibility" style="display:none;">
                                <div class="form-group">
                                    <label class="form_label col-md-2">自定义兼容信息</label>
                                    <div class="col-md-10 form_inline">
                                        <div id="upCompatibility">
                                            <div>
                                                <input id="btnEditNote" type="button" class="button" value="Edit Notes">
                                                <input type="submit" name="btnAllCompatibility" value="删除" id="btnAllCompatibility" class="button">
                                                <span>    
													<table class="prosaveas" cellpadding="0" cellspacing="0">
													    <tbody>
													    <tr>
													    	<th></th>
														    <td>
														        <a class="btn btn-default btn-sm">选择</a>   
														     	<a href="javascript:void(0);"></a>
														    </td>
														    <td>
														     	<input type="hidden" name="SaveAsCompatibility$hfElementKey">
														        <a class="btn btn-default btn-sm">另存为</a>
													        </td>     
													    </tr>
													    <tr style="display: none;"> 
														    <th>名称</th>
														    <td>
														    	<input name="SaveAsCompatibility$txtName" type="text" class="form-control">
													    	</td>
														    <td>
														    	<input type="submit" name="SaveAsCompatibility$btnSave" value="保存" class="btn btn-default btn-sm">
													    	</td>
														    <td>
														        <a class="btn btn-default btn-sm">取消</a>
														    </td>
													    </tr>
													</tbody></table>
                                                </span>
                                            </div>
                                            <table cellpadding="0" cellspacing="0" id="tbCompatibility" class="datagrid table table-bordered table-striped table-condensed flip-content">
                                                <tbody>
	                                                <tr>
	                                                    <th class="chbox">
	                                                        <input type="checkbox" id="chkAllCompatibility" name="chkAllCompatibility"></th>
	                                                    <th>
	                                                        <span id="lblPropertyName1"></span>
	                                                    </th>
	                                                    <th>
	                                                        <span id="lblPropertyName2"></span>
	                                                    </th>
	                                                    <th>
	                                                        <span id="lblPropertyName3"></span>
	                                                    </th>
	                                                    <th id="trPropertyName4">
	                                                        <span id="lblPropertyName4"></span>
	                                                    </th>

	                                                    <th id="trPropertyName5">
	                                                        <span id="lblPropertyName5"></span>
	                                                    </th>

	                                                    <th id="trPropertyName6">
	                                                        <span id="lblPropertyName6"></span>
	                                                    </th>

	                                                    <th id="trPropertyName7">
	                                                        <span id="lblPropertyName7"></span>
	                                                    </th>
	                                                    <th>Notes</th>
	                                                    <th></th>
	                                                </tr>
	                                                <tr>
	                                                    <th></th>
	                                                    <th>
	                                                        <select name="ddlProperty1" id="ddlProperty1" class="form-control"> 
	                                                        </select>
	                                                    </th>
	                                                    <th>
	                                                        <select size="4" name="lbProperty2" multiple="multiple" id="lbProperty2" class="form-control" style="min-width:120px">
	                                                        </select>  
	                                                    </th>
	                                                    <th>
	                                                        <select size="4" name="lbProperty3" multiple="multiple" id="lbProperty3" class="form-control" style="min-width:80px">
	                                                        </select>  
	                                                    </th>
	                                                    <th id="trProperty4">
	                                                        <select size="4" name="lbProperty4" multiple="multiple" id="lbProperty4" class="form-control">	
	                                                        </select>    
	                                                    </th>
	                                                    <th id="trProperty5">
	                                                        <select size="4" name="lbProperty5" multiple="multiple" id="lbProperty5" class="form-control">
	                                                        </select>  
	                                                    </th>
	                                                    <th id="trProperty6">  
	                                                    </th>
	                                                    <th id="trProperty7">   
	                                                    </th>
	                                                    <th>
	                                                        <input name="txtNotes" type="text" id="txtNotes" class="form-control">
	                                                    </th>
	                                                    <th>
	                                                        <input type="submit" name="btnCompatibilityAdd" value="Add" id="btnCompatibilityAdd" class="btn btn-sm">
	                                                    </th>
	                                                </tr> 
                                            	</tbody>
                                        	</table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                             <div class="form-horizontal form-bordered" ">
                            	<div id="UpdatePanel9">
                                    <div class="form-group">
                                    	<!--<br class="form_label " /><br class="form_label " />-->
                                        <div> 
	                                        <label class="form_label col-md-2"  id="productCode2" message="MPN" name="productCode2">MPN</label>
	                                        <div class="col-md-4 form_inline after" >
	                                        	<div  id="dlCatalog2" >           	
													<div class="left">
														<input id="productMpnId"  name="product_mpn" type="text" style="width: 250px">
														<div class="notApply2" style="float:right"> 
															<a href="javascript:void(0);">Does not apply</a>
														</div>
														
													</div> 
													<img src="" class="code" alt="codeImg" style="display:none;"/>
												</div>
												<div class="code_tip" style="display:none;">
													<a href="http://4.pushauction.com/Help/HelpDetail.aspx?MenuID=102006&amp;id=7173bc60-74ce-43e5-bcac-7ab1ee31f926">查看产品识别码使用帮助</a>
												</div>
											</div>
										</div>
                                    </div>
								</div>
							</div>	
							<div class="form-horizontal form-bordered" ">
                            	<div id="UpdatePanel8">
                                    <div class="form-group">
                                    	<!--<br class="form_label " /><br class="form_label " />-->
                                        <div> 
	                                        <label class="form_label col-md-2"  id="productCode3" message="Brand" name="productCode3">Brand</label>
	                                        <div class="col-md-4 form_inline after" >
	                                        	<div  id="dlCatalog3" >           	
													<div class="left">
														<input id="productBrandId"  name="product_brand" type="text" style="width: 250px">
														<div class="notApply3" style="float:right"> 
															<a href="javascript:void(0);" style="padding-left:10px;">Lighting EVER</a>
														</div>
														
													</div> 
													<img src="" class="code" alt="codeImg" style="display:none;"/>
												</div>
												<div class="code_tip" style="display:none;">
													<a href="http://4.pushauction.com/Help/HelpDetail.aspx?MenuID=102006&amp;id=7173bc60-74ce-43e5-bcac-7ab1ee31f926">查看产品识别码使用帮助</a>
												</div>
											</div>
										</div>
                                    </div>
								</div>
							</div>	
							<div class="form-horizontal form-bordered" id="productUinVidId">
                            	<div id="UpdatePanel9">
                                    <div class="form-group">
                                    	<!--<br class="form_label " /><br class="form_label " />-->
                                        <div> 
	                                        <label class="form_label col-md-2"  id="productCode3" message="UIN" name="productCode2">Ust-IdNr</label>
	                                        <div class="col-md-4 form_inline after" >
	                                        	<div  id="dlCatalog4" >           	
													<div class="left">
														<input id="productUinId"  name="product_ust" type="text" style="width: 250px">
														<div class="notApply4" style="float:right"> 
															<a href="javascript:void(0);"></a>
														</div>
													</div> 
													<img src="" class="code" alt="codeImg" style="display:none;"/>
												</div>
												<div class="code_tip" style="display:none;">
													<a href="http://4.pushauction.com/Help/HelpDetail.aspx?MenuID=102006&amp;id=7173bc60-74ce-43e5-bcac-7ab1ee31f926">查看产品识别码使用帮助</a>
												</div>
											</div>
										</div>
                                    </div>
								</div>
							</div>	
                            <div class="form-horizontal form-bordered">
                                <form>
                                	<div class="form-group" id="dlCustomItemSpecifics" >
                                        <label class="form_label col-md-2">物品属性</label>
                                        <div class="col-md-6 form_inline">
                                            <div id="upItemSpecifics">
                                                <span style="padding-bottom:10px; display:block; width:100%; float:left;">
    	                                            <table class="prosaveas" cellpadding="0" cellspacing="0">
    												    <tbody>
    													    <tr style="display:none;">
    													    	<th></th>
    													   		<td>
    													        	<a class="btn btn-default btn-sm" id="choose"><span>&laquo;&nbsp;</span>选择</a> 
    													    	</td>
    													    	<td id="property-save">
    													        	<a class="btn btn-default btn-sm" >另存为</a>
                                                                </td>     
    												    	</tr>
    													    <tr style="display:none" id="property-save-choose">
    													    	<th>名称</th>
    													    	<td>
    													    		<input type="text" class="form-control">
    													    	</td>
    													    	<td>
    													    		<input type="submit" value="保存"  class="btn btn-default btn-sm"></td>
    													    	<td  id="property-save-cancel">
    													        	<a class="btn btn-default btn-sm" >取消</a>
    													    	</td>
    													    </tr>
    													</tbody>
    												</table>  
                                                </span>
                                                <!--选择按钮表格-->
                                             
                                                <div class="recospecifics">   
                                                    <table cellpadding="0" cellspacing="0" class="specificslist datagrid table table-striped table-bordered">
                                                        <thead>
                                                            <tr>
                                                            	<th></th>
                                                                <th style="width: 400px;">名称</th>
                                                                <th style="padding-left: 10px;">值</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                    	</tbody>
                                                    </table>          
                                                </div>
                                                <div class="prospecifics_list" id="prospecificsList">
            							
                                                </div>
                                                <div class="specifics_addall">
                                                    <a href="javascript:void(0);">添加全部</a>
                                                </div>
                                                <div>
                                                    <a class="createnew add_property" href="javascript:void(0);">添加新的物品属性</a>
                                                    <a class="createnew remove_property" href="javascript:void(0);">移除所有物品属性</a>
                                                </div>
                                                </div>
                                        	</div>
                                    	</div>
                                    <div class="form-group" style="display:none">
                                        <label class="form_label col-md-2">物品状况</label>
                                        <div class="col-md-4 form_inline">
                                            <span id="upItemCondition">
                                                 <select name="ddlItemCondition" id="itemCondition" value="1000"  class="form-control input_medium">
    												<option value="1000" selected>Brand New</option>
    												<option value="2750">Like New</option>
    												<option value="4000">Very Good</option>
    												<option value="5000">Good</option>
    												<option value="6000">Acceptable</option>
    											</select>
                                            </span>
                                        </div>
                                    </div>
                                   
                                    <div class="form-group conditionDescription" id="ConditionDescription" style="display: none">
                                        <label class="form_label col-md-2">物品状况描述</label>
                                        <div class="col-md-4 form_inline">
                                            <textarea name="txtItemConditionDescription" id="txtConditionDescription" rows="2" cols="20" maxlength="1000" class="remark"></textarea>
                                        	<div><b class="numoutput descriptionNum">0</b>/ (最多 1000 字符. 不支持 HTML)</div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                             <h4 id="ItemSpecificsandCondition1">多属性</h4>
                            <div class="form-horizontal form-bordered" ">
                            	 <div class="form-horizontal form-bordered" id="dlVariation" style="display:none;">
                                <div id="upVariations">
                                      
                                        <div class="form-group">
                                            <label class="form_label col-md-2">多属性</label>
                                            <div class="col-md-10 form_inline" id="datashowbox2">
                                                <span class="help-inline">
                                                <a id="lbtnAddNewVariationRow" class="createnew">添加新的属性</a></span>
                                                <div id="moreAttrList">
                                                </div>
                                            </div>
                                        </div>                                                    
									</div>
                              
                                
                                <div class="form-group">
                                    <label class="form_label col-md-2">多属性值</label>
                                    <div class="col-md-10 form_inline">
                                    	<!--多属性添加属性表格-->
										 <div >   
                                                <table cellpadding="0" cellspacing="0" class=" moreAttrValueTable datagrid table table-striped table-bordered">
                                                    <thead>
                                                        <tr id="moreAttrTableValueHead">
                                                        	<th head="num"></th>
                                                        	<th head="ck"><input type="checkbox" name="ck" id="moreAttrSelectedAll"/></td></th>
                                                            <th head="SKU">Sub SKU</th>
                                                            <th head="UPC" id="upcmpnCode" >UPC</th>
															<th head="StartPrice">价格</th>
															<th head="Quantity">数量</th>
															<th head="opt" id="moreAttrTableAddHead">操作</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                	</tbody>
                                                </table>          
                                            </div>
											<div style="padding-bottom: 10px;"><a href="javascript:void(0);" class="add_line" id="addMoreAttrLine">+ 添加一行</a></div>
                                    </div>
                                </div>
                                
                                  <div class="form-group">
                                    <label class="form_label col-md-2">多属性图片</label>
                                    <div class="col-md-10 form_inline">
                                        <span id="upPictureVariationName">
                                            <select id="ddlPictureVariationName" class="form-control input_medium">
                                            	<option style="height:0px;display:none;"></option>
                                            </select>
                                        </span>
                                        <span class="help-inline" style="display:none;">
                                            <a class="createnew" >重设图片</a>
                                        </span>
                                        <div id="upVariationPic">
                                            <div id="divVariationsPic">
                                                <div class="verhead ">
                                                    <p class="green">eBay图片展示物品，多属性图片展示物品的区别。</p>
                                                    <p class="green">例子：在刊登里添加一个通用的图片来展示所有的物品； 然后添加多属性图片来展示红、绿和蓝色的物品；最多添加12张图片（第一张图片免费；其后每张可能需要收费）。</p>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="moreAttrImagesList">
                                        </div>
                                    </div>
                                </div>
                        	</div>	
                            </div>
						</div>
					</div>
					
	<div class="moreAttrAddNameWin" style="display: none;height:100%" >
         <div class=" dialogbox">
            <table class="dialog" cellspacing="0" cellpadding="0" onselectstart="return false;">    
                <tbody>
                    <tr valign="top">
                        <td align="center">
                            <table width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"> 
                                <tbody>
                                    <tr>
                                        <td>
                                            <div class="dialoghead" style="cursor:move" id="_Draghandle_0">
                                                <div id="_Title_0" class="dialog_title">多属性名称</div>
                                                <div class="dialog_close" id="_ButtonClose_0" style="">x</div>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>           
                                        <td valign="top" align="center" class="dialogmsghead" style="height:40px">
                                            <div id="_MessageRow_0" class="dialogmsghead1" style="height:100%">
                                                <input type="text" id="moreAttrAddName" name="addName"/>
                                        	</div>
                                    	</td>          
                                	</tr>          
                                    <tr id="_ButtonRow_0" style="">
                                        <td>
                                            <div id="_DialogButtons_0" class="dialog_button">
                                             	<input type="button" id="moreAttrAddNameOk" value="确定" class="dialogbutton">
                                                <input type="button" value="关闭" class="dialogbutton">
                                            </div>
                                        </td>          
                                    </tr>        
                                </tbody>
                            </table>
                        </td>    
                    </tr>    
                </tbody>
            </table>
        </div>
    </div>
    
  <div class="moreAttrImgModel" style="display: none;">
        <div class=" dialogbox">
            <table class="dialog" cellspacing="0" cellpadding="0" onselectstart="return false;">    
                <tbody>
                    <tr valign="top">      
                        <td align="center">
                        <table width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff">      
                        <tbody>
                            <tr>
                                <td>
                                    <div class="dialoghead" style="cursor:move">
                                        <div class="dialog_title">图片选择</div>      
                                        <div class="dialog_close">X</div>
                                    </div>
                                </td>
                            </tr>          
                            <tr class="ecternalImgTr" style="height:auto">            
                                <td valign="top" align="center" class="dialogmsghead">   
                                    <ul class="ecternalImgUl" style="height:auto">
                                        <li>
                                            <div>
                                                <img class="ecternalImg" />
                                            </div>
                                            <div>
                                                <label>URL</label>
                                                <input type="text" id="moreAttrImgInput" name="ecternalImg" />
                                            </div>
                                        </li>
                                    </ul>
                                </td>          
                            </tr>          
                            <tr>            
                                <td>
                                    <div class="dialog_button"> 
                                        <input type="button" value="确定" id="moreAttrCheckImg" class="dialogbutton">                
                                        <input type="button" value="关闭" class="dialogbutton">              
                                    </div>
                                </td>          
                            </tr>        
                            </tbody>
                        </table>
                    </td>    
                </tr> 
            </tbody>
            </table>
        </div>
    </div>
    
<div id="imgView" class="easyui-window" title="图片预览" style="width:800px;height:600px"
    data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false">
    <img id="imgViewTag" src="" style="width:100%"/>
</div>
</#macro>
