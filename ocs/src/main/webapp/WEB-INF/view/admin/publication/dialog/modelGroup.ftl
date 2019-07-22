<#macro modelGroup>
            <div class="modelGroupModel" style="display: none;height:100%" >

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
                                                        <div id="_Title_0" class="dialog_title">模块组合选择</div>
                                                        <div class="dialog_close" id="_ButtonClose_0" style="">x</div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>           
                                                <td valign="top" align="center" class="dialogmsghead" style="height:450px">
                                                    <div id="_MessageRow_0" class="dialogmsghead1" style="height:100%">
                                                    		<div>
                                                    		<span>模块组合：</span>
                                                    		<input type="text"  class="easyui-combobox" name= "m_modeSeleted" id="m_modeSeleted"  style="width:250px;" data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/all/7'" />
                                                    		</div>
                                                    		<br/>
														  <form id="modelManagerAddfm" method="post" novalidate style="margin:0;padding:20px 50px">
																<div>
																<table cellpadding="5">
														    		<tr style="height: 40px;">
														    			<td>名称:</td>
														    			<td> 
														    			<input class="easyui-textbox" type="text" id="m_name" name="m_name" data-options="required:true" style="width:250px;"></input>
																		</td>
														    		</tr>
														    		<tr style="height: 40px;">
														    			<td>站点:</td>
														    			<td>
														    			<input type="text"   id="m_siteId" name="m_siteId"  style="width:250px;"  data-options="required:true,valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/>
														    			</td>
														    		</tr>
														    		<tr style="height: 40px;">
														    			<td>ebay账户:</td>
														    			<td>
														    			<input type="text" style="width:250px;" class="easyui-combobox" id="m_account" name="m_account" data-options="required:true,valueField:'value',textField:'displayName',url:'/ocs/publication/getAccountList'"/>
														    			</td>
														    		</tr>
														    		<tr style="height: 40px;">
														    			<td>顶部推广:</td>
														    			<td>
														    			<input type="text"  class="easyui-combobox" name="m_topPromotionType" id="m_topPromotionType"   style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getPromoteList/0'"/>
																		</td>
														    		</tr>
														    		<tr style="height: 40px;">
														    			<td>底部推广:</td>
														    			<td>
														    			<input type="text"  class="easyui-combobox" name="m_footerPromotionType" id="m_footerPromotionType"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getPromoteList/1'"/>
														    			</td>
														    		</tr>
														    		<tr style="height: 40px;">
														    			<td>卖家描述:</td>
														    			<td>
														    			<input type="text"  class="easyui-combobox" name="m_sellerDescriptionTemp" id="m_sellerDescriptionTemp"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getSellerDescription/0'"/>
														    			</td>
														    		</tr>
														    		<tr style="height: 40px;">
														    			<td>付款:</td>
														    			<td>
														    				<input type="text"  class="easyui-combobox" name="m_payment" id="m_payment"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/0/0'"/>
														    			</td>
														    		</tr>
														    		<tr style="height: 40px;">
														    			<td>买家要求:</td>
														    			<td>
														    				<input type="text"  class="easyui-combobox" name="m_buyRequire" id="m_buyRequire"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/0/0'"/>
														    			</td>
														    		</tr>
														    		<tr style="height: 40px;">
														    			<td>退货政策:</td>
														    			<td>
														    				<input type="text"  class="easyui-combobox" name= "m_returnPolicy" id="m_returnPolicy"  style="width:250px;" data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/0/0'" />
														    			</td>
														    		</tr>
														    		<tr style="height: 40px;">
														    			<td>物品所在地:</td>
														    			<td>
														    				<input type="text"  class="easyui-combobox" name= "m_itemPlace" id="m_itemPlace"  style="width:250px;" data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/0/0'" />
														    			</td>
														    		</tr>
														    		<tr style="height: 40px;">
														    			<td>运输选项:</td>
														    			<td>
														    				<input type="text"  class="easyui-combobox" name= "m_tran" id="m_tran"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/0/0'"/>
														    			</td>
														    		</tr>
														    		
													    	</table>
																</div>
												        </form>
                                                	</div>
                                            	</td>          
                                        	</tr>          
                                            <tr id="_ButtonRow_0" style="">
                                                <td>
                                                    <div id="_DialogButtons_0" class="dialog_button">
                                                    	<input type="button" id="modeSaveAs" value="另存为" class="dialogbutton">
                                                        <input type="button" id="modeSelectOk" value="确定" class="dialogbutton">
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
</#macro>
