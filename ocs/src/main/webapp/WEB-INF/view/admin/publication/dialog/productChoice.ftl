<#macro productChoice>
 <div class="productChoiceModel" style="display: none;height:100%" >
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
                                                        <div id="_Title_0" class="dialog_title">产品选择</div>
                                                        <div class="dialog_close" id="_ButtonClose_0" style="">x</div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>           
                                                <td valign="top" align="center" class="dialogmsghead" style="height:600px">
                                                    <div id="_MessageRow_0" class="dialogmsghead1" style="height:100%">
                                                    	<div style="padding-bottom: 10px;">
															<table>
																<tr>
																	<td>sku：<input type="text" id="productChooseSku" /></td>
																	<td>&nbsp;&nbsp;<td>
																	<td>
																		<a id="query_product" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
																		<a id="reset_product" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">重置</a>
																	</td>
																</tr>
															</table>
															<div id="skuValue" style="display: none">
																	当前选中的 SKU： <span id="skuChecked"></span><input type="hidden" id="skuid"/> <a href="javascript:void(0)" id="clearSku">清空</a>
															</div>
														</div>
                                                        <table id="productDatagrid" class="easyui-datagrid" data-options="" style="height:540px"> </table>
                                                	</div>
                                            	</td>          
                                        	</tr>          
                                            <tr id="_ButtonRow_0" style="">
                                                <td>
                                                    <div id="_DialogButtons_0" class="dialog_button">
                                                         <input id="skuSure" type="button" value="确定" class="dialogbutton">
                                                        <input id="skuCancel" type="button" value="关闭" class="dialogbutton">
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