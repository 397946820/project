<@FTL.admin id="NoArriveRegionList" title="不运送地区"
add_script_files=['admin/noArriveRegion/noArriveRegion.js']
add_style_files=['noShip.css']>




<!--div id="noArriveRegionCotextMenu" class="easyui-menu"
	style="width: 120px;">
	<div id="noArriveRegionEditContextMenu"
		data-options="iconCls:'icon-edit'">编辑</div>
	<div id="noArriveRegionRemoveContextMenu"
		data-options="iconCls:'icon-remove'">删除</div>
</div-->



</@FTL.admin>

<#macro areaName>
<div class="noShipChooseWin" style="display: none;height:100%" >
                 <div class=" dialogbox">
                    <table class="dialog" style="width:800px"  cellspacing="0" cellpadding="0" onselectstart="return false;">    
                        <tbody>
                            <tr valign="top">
                                <td>
                                    <table width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"> 
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <div class="dialoghead" style="cursor:move" id="_Draghandle_0">
                                                        <div id="_Title_0" class="dialog_title">不运送区域选择</div>
                                                        <div class="dialog_close" id="_ButtonClose_0" style="">x</div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>           
                                                <td valign="top" class="dialogmsghead" >
                                                    <div id="_MessageRow_0" class="dialogmsghead1" style="height:550px;overflow:auto;">
                                         
															<div class="domestic" style="margin-left:58px"  >
																<div class="domesticChoose">
																	<div style=" margin=10px 0;">Domestic</div>
																	<div  class="subRegion">
																		
																	</div>
																</div>
																<div class="domesticChoose">
																	<div style=" margin=10px 0;">Domestic</div>
																	<div  class="subRegion">
																	</div>
																</div>
																<div class="domesticChoose">
																	<div style=" margin=10px 0;">Domestic</div>
																	<div  class="subRegion">
																	</div>
																</div>
															</div>
															<div class="international" style="margin-left:58px;width: 790px;">
															<div style=" margin=10px 0;">International</div>
																<div class="left">
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
																			<input type="checkbox" value="Europe" id="Europe"> <label for="Europe">欧洲</label> <span>
																			[<a
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
																<div class="center">
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
																			<input type="checkbox" value="Southeast Asia" id="Southeast Asia"> <label for"Southeast Asia">东南亚</label>
																			<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
																			</span>
																		</div>
																		<div class="content">
																			<ul>
																				
																			</ul>
																		</div>
																	</div>
																</div>
																<div class="right">
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
																			<input type="checkbox" value="North America" id="North America"> <label for=value="North America">北美洲</label>
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
															</div>
															<div id="divAdditionalLocations" style="margin-left: 58px;">
																<p>Additional Locations</p>
				                                        		<div class="subRegion">
				                                        			<input type="checkbox" id="POBox" value="PO Box"> <label for="POBox">PO Box</label>
				                                        			<div id="Packstationen2" style="display:none;display: inline-block;margin-left: 40px;"><input type="checkbox" id="Packstationen" value="Packstation"> <label for="Packstationen">Packstationen</label></div>
				                                        		</div>
				                                        		<!--<div class="subRegion">
				                                        			<input type="checkbox" id="Boîte postale" value="PO Box"> <label for="Boîte postale">Boîte postale</label>
				                                        		</div>
				                                        		<div class="subRegion">
				                                        			<input type="checkbox" id="Filialen" value="PO Box"> <label for="Filialen">Filialen und Shops der Versanddienstleister</label>
				                                        			<input type="checkbox" id="Boîte postale" value="Packstation"> <label for="Packstationen">Packstationen</label>
				                                        		</div>
				                                        		<div class="subRegion">
				                                        			<input type="checkbox" id="Apartado postal" value="PO Box"> <label for="Apartado postal">Apartado postal</label>
				                                        		</div>
				                                        		<div class="subRegion">
				                                        			<input type="checkbox" id="Skrytki pocztowe" value="PO Box"> <label for="Skrytki pocztowe">Skrytki pocztowe</label>
				                                        		</div>-->
				                                        	</div>
															<div class="showSelected" style="position:fixed;bottom:43px;left:58px;" >
																<span class="noSelected">您尚未选择国家或地区</span> <span class="selected"
																	style="display: none;">您已经选择的国家或地区:&nbsp;&nbsp;&nbsp; <span id="noShip"></span>
																	[<a class="cancelSelected" href="javascript:void(0);">取消已选</a>]
																</span>
															</div>
														</div>
                                          
                                            	</td>          
                                        	</tr>      
                                        	<tr>
	                                        	
                                        	</tr>    
                                            <tr id="_ButtonRow_0" style="">
                                                <td>
                                                    <div id="_DialogButtons_0" class="dialog_button" style="position:fixed;bottom:10px;right:12px;">
                                                        <input type="button" value="保存" class="dialogbutton" id="noshipAreaSave">
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
