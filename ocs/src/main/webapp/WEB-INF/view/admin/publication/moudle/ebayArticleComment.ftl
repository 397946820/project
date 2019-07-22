<#macro ebayArticleComment> 
					<div class="blue_madison" id="tabbox5">
						<div class="model_title">
							<div class="caption">
	                            <span id="Template">eBay 物品描述</span>
	                        </div>
	                        <div class="actions">
	                         	<span class="btn btn_default btn_sm" id="oldDescriptionView" title="旧模板预览">旧模板预览 </span>
                                <span class="btn btn_default btn_sm" id="articleCommentSelect" title="选择">选择 </span>
                                <span class="btn btn_default btn_sm"  id="articleCommentSelectSave" title="另存为">另存为 </span>
                            </div>
						</div>
						<div class="model_form" id="tabbox6">
					   		<h4 id="ListTemplateSetting">模板设置</h4>
					   		<div id="upListTemplateSetting">
					   			<form class="form-horizontal">
									
									<div class="form-group" link="TOP_PROMOTION_TYPE">
									    <label class="form_label col-md-2">顶部推广</label>
									    <div class="col-md-4 form_inline">
									    	<span id="upCrossPromotion1">
									    		<input type="text"  class="easyui-combobox" id="topPromotionType"   style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getPromoteList/0'"/>
									           
											</span>
									    </div>
									</div>
									<div class="form-group" link="IN_PROMOTION_TYPE">
									    <label class="form_label col-md-2">内部推广</label>
									    <div class="col-md-4 form_inline">
									    	<span id="upCrossPromotion2">
									    		<input type="text"  class="easyui-combobox" id="inPromotionType"   style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getPromoteList/2'"/>
											</span>
									    </div>
									</div>
									<div class="form-group" link="FOOTER_PROMOTION_TYPE">
									    <label class="form_label col-md-2">底部推广</label>
									    <div class="col-md-4 form_inline">
									        <span id="upCrossPromotion3">
									        	<input type="text"  class="easyui-combobox" id="footerPromotionType"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getPromoteList/1'"/>
										        
											</span>
									    </div>
									</div>
									<div class="form-group" link="SELLER_DESCRIPTION">
											<label class="form_label col-md-2">卖家描述</label>
											<div class="col-md-4 form_inline">
												<span id="upCrossPromotion3">
												<input type="text"  class="easyui-combobox" id="sellerDescriptionTemp"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getSellerDescription/0'"/>
											</span>
											</div>
										</div>
									<div class="form-group" link="ADVERT_ID">
									    <label class="form_label col-md-2">模板广告</label>
									    <div class="col-md-4 form_inline">
									        <span id="upCrossPromotion3">
									        	<select  name="advertTemplates" id="advert_id" class="easyui-combogrid" style="width:250px;" data-options="
													panelWidth: 600,
													panelHeight: 300,
													idField: 'id',
													textField: 'name',
													url: '${FTL.X.global_domain}/AdvertTemplates/list',
													method: 'post',
													columns: [[
														{field:'id',title:'ID',width:80,hidden:true},
														{field:'img_name',title:'img_name',width:80,hidden:true},
														{field:'pid_id',title:'pid_id',width:80,hidden:true},
														{field:'url',title:'广告图片',width:120,formatter:getPicServlet},
														{field:'name',title:'广告名称',width:120,align:'right'},
														{field:'product_url',title:'关联产品',width:200,align:'right',formatter:getTitleAndItemNum}
													]],
													queryParams:{
													param:{
										        		ebay_account:'',
										        		site_id:''
										        		
										        	}
										        	},
													fitColumns: true,
													pagination: true,
									        		pageSize: 20,
									        		
													">							
												</select>								
											</span>
									    </div>
									</div>
									<div class="form-group" style="display:none;">
									    <label class="form_label col-md-2">计数器</label>
									    <div class="col-md-4 form_inline">
									        <select id="counterType" name="ListTemplateSetting$ddlCounter" id="ddlCounter" class="form-control input_medium">
												<option selected="selected" value="0">没有计数器</option>
												<option value="1">计数器1</option>
												<option value="2">计数器2</option>
												<option value="3">计数器3</option>
												<option value="4">计数器4</option>
												<option value="5">计数器5</option>
												<option value="6">计数器6</option>
												<option value="7">计数器7</option>
												<option value="8">计数器8</option>
											</select>
									    </div>
									</div>
									<div class="form-group" style="display:none;">
									    <label class="form_label col-md-2"></label>
									    <div class="col-md-4 form_inline">
									        <input id="chkProtectWebPage" type="checkbox" name="ListTemplateSetting$chkProtectWebPage"> 
                                            <label for="chkProtectWebPage">启用网页保护</label>
									    </div>
									</div>
								</form>		
							</div> 
							<h4 style="display:none;">模板标题</h4>
							<form class="form-horizontal" style="display:none;">
								<div class="form-group" id="tabbox7" >
		                            <label class="form_label col-md-2">模板标题</label>
		                            <div class="col-md-5 form_inline">
		                                <input id="templateTitle" name="txtListTemplateTitle" type="text" maxlength="128" class="form-control input_block" style="width:540px">
		                                <span class="help-inline">
		                                   <a>复制刊登标题</a>
	                                    </span>
		                            </div>
		                        </div> 
	                        </form>
	                        <h4 id="eBayPicture">eBay 图片</h4>
	                        <form class="form-horizontal">
		                        <div class="form-group" >
		                            <label class="form_label col-md-2"></label>
		                            <div class="col-md-10 form_inline">
		                            	<span class="green">此处图第一张图片自动被用作 eBay 橱窗图（Gallery），所有图片将在刊登时上传到eBay。请不要把多属性图片放在此处。</span>
	                                </div>
	                            </div> 
	                            <div class="form-group" link="EBAY_IMAGES">
	                                <label class="form_label col-md-2"></label>
	                                <div class="col-md-10 form_inline">
	                                    <div class="varselectpicture"  id="tabbox8">
	                                        <div class="vargallery eBaySelectImg" style="border-top: none">
	                                            <p>
	                                                <a class="selectImg"  >选择图片</a>
	                                                <a class="SelectSKUImg"  style="display:none">选择SKU图片</a>
	                                                <a class="useExternalImg">使用外部图片</a>
	                                                <a class="copyTempImgs">复制下方的模板图片</a>
	                                                <a class="emptyImg">清空所选图片</a>
	                                                <u>已选择图片： <span class="varPicCount">1</span> | 最多 12 张图片</u>
	                                            </p>
	                                            <ul class="ui-sortable eBayPicture">
                                                    
                                                </ul>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div> 
                            </form> 
                            <h4 id="ListTemplatePicture">模板图片</h4>
                            <form class="form-horizontal">
	                            <div class="form-group">
	                            	<label class="form_label col-md-2"></label>
	                            	<div class="col-md-10 form_inline"><span class="green">此处图片将会在刊登模板中使用。</span>
	                                </div>
	                            </div>
                            	<div class="form-group" link="TEMPLATE_IMAGES">
	                                <label class="form_label col-md-2"></label>
	                                <div class="col-md-10 form_inline">
	                                    <div class="varselectpicture">
	                                        <div class="vargallery tempSelectImg" style="border-top: none">
                                                <p>
                                                    <a class="selectImg">选择图片</a>
                                                    <a class="SelectSKUImg"  style="display:none">选择SKU图片</a>
                                                    <a class="useExternalImg">使用外部图片</a>
                                                    <a class="copyEbayImgs">复制上方的eBay图片</a>
                                                    <a class="emptyImg">清空所选图片</a>
                                                    <u>已选择图片： <span class="varPicCount">1</span> | 最多 12 张图片</u>
                                                </p>
	                                            <ul class="ui-sortable templatePicture">
                                                    
                                                </ul>
	                                        </div>
	                                    </div>
	                                </div>
	                            </div>
	                            <div class="form-group" style="display:block;">
                                    <label class="form_label col-md-2">移动端描述</label>
                                    <div class="col-md-4 form_inline">
                                        <textarea id="appComment" name="txtMobileDescription" rows="2" cols="20"  class="form-control" style="overflow: hidden;">   
                                        	
                                        </textarea>
                                    	<span class="help-block"> <b class="numoutput mobileNum">0</b>/ ( 最多 800 字符. 不支持 HTML )</span>
                                    </div>
                                </div>
           
                                	<input type="hidden" id="articleCommentCount" value="1">
                     
                                		<div class="description_wrap" >
	
			                                <div class="form-group" link="COMMENTS">
			                                    <label class="form_label col-md-2">描述</label>
			                                    <div class="col-md-10 form_inline">
			                                        <textarea id="commentContent" class="form-control" >
			                                        	
			                                        </textarea>
			                                    </div>
			                                </div> 
                                		</div>
                           
                                
                               
                            </form>                                        
						</div>
						<div id="imgSetMainMenu" class="easyui-menu" style="width:120px;">
							<div id="imgSetMainOpt" >设为橱窗图片</div>
						</div>
					</div>	

</#macro>
