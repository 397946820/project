<#macro categorySelect>
            <div class="selectProModel" style="display: none;">
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
                                                        <div id="_Title_0" class="dialog_title">选择产品</div>
                                                        <div class="dialog_close" id="_ButtonClose_0" style="">x</div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>           
                                                <td valign="top" align="center" class="dialogmsghead">
                                                    <div id="_MessageRow_0" class="dialogmsghead1">
                                                        <table cellpadding="0" cellspacing="0" style="width:100%; height:100%; ">
                                                            <tbody>
                                                                <tr>
                                                                    <td class="searchArea" colspan="2" style=" height:40px; background:#fff;">
                                                                        <div class="dialog_head">
                                                                            <span>
                                                                                <select name="ddlType" id="ddlType" style="width:90px;">
                                                                                    <option selected="selected" value="SKU">SKU </option>
                                                                                    <option value="ProductName">名称</option>
                                                                                </select>
                                                                            </span>
                                                                            <input name="txtSearch" type="text" id="txtSearch" class="search_txt">
                                                                            <input name="chkGlobal" type="checkbox" id="chkGlobal" groupname="globalsearch" class="global" checked="checked">
                                                                            <span class="globaltxt">全局</span>
                                                                            <input type="submit" name="btnSearch" value="搜索" id="btnSearch" class="search_btn">&nbsp;&nbsp; 
                                                                            <a class="moreSearchItem">  显示高级搜索</a>
                                                                        </div>
                                                                        <input type="hidden" name="hfShowAdvance" id="hfShowAdvance" value="hide">
                                                                        <div id="moreSearchCond" class="advsearch dialog_advsearch" style="display: none;">
                                                                            <dl>
                                                                                <dt>产品</dt>
                                                                                <dd>
                                                                                    <select name="ddlItemProperty" id="ddlItemProperty" class="searchselect">
                                                                                        <option value="StockQTY">库存</option>
                                                                                    </select>
                                                                                    <select name="ddlItemPropertyOp" id="ddlItemPropertyOp" class="searchselect">
                                                                                        <option value=">">大于</option>
                                                                                        <option value="<">小于</option>
                                                                                        <option value="=">等于</option>
                                                                                    </select>
                                                                                    <input name="txtItemProperty" type="text" id="txtItemProperty" oninput="IsNumber(this);" onpropertychange="IsNumber(this);" onblur="IsNumber(this);">
                                                                                </dd>
                                                                                <dt>&nbsp;</dt>
                                                                                <dd>
                                                                                    <input name="chkCurrentAndSubcategories" type="checkbox" id="chkCurrentAndSubcategories" groupname="globalsearch">当前分类以及子分类
                                                                                </dd>
                                                                            </dl>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td style="height:93%; width:190px; vertical-align:top;" class="dialog_left_cnt">
                                                                        <div class="treehead">
                                                                            <a class="all_tree" title="收起所有"></a>
                                                                            <a class="node_tree" title="展开所有"></a>
                                                                            <a class="select_tree" title="展开所选"></a>
                                                                        </div>
                                                                        <div class="jsontree">
                                                                            <input name="hdCheckedText" type="hidden" id="hdCheckedText">
                                                                            <input name="hdCheckedValue" type="hidden" id="hdCheckedValue" value="">
                                                                            <ul id="treeDemo" class="tree"></ul>
                                                                        </div>
                                                                    </td>
                                                                    <td style="vertical-align:top;position:relative; padding-bottom:30px;"> 
                                                                        <div id="UP">
                                                                            <div id="selTips" class="mainselectall" style="display: none;">
                                                                                <span id="spanSelTips">当前选中的 SKU：<b></b></span>
                                                                                <a href="#">清除</a>
                                                                            </div>
                                                                            <table class="datagrid" cellpadding="0" cellspacing="0" style="border-left:none;border-top:none; border-right:none;border-bottom:1px solid #ccc; float:left;">
                                                                                <tbody>
                                                                                    <tr>
                                                                                        <th></th>
                                                                                        <th id="Repeater1_Picture">图片</th>
                                                                                        <th><a id="Repeater1_lnkSKU">SKU</a> </th>
                                                                                        <th><a id="Repeater1_lnkProductName">名称</a></th>
                                                                                        <th id="Repeater1_Folder"><a id="Repeater1_lbkFolderID">分类</a></th>
                                                                                        <th><a id="Repeater1_lnkStockQTY">库存</a></th>
                                                                                        <th><a id="Repeater1_lnkMinStockQTY">库存预警</a></th>
                                                                                        <th><a id="Repeater1_lnkWeightlnk">重量(kg)</a></th>
                                                                                        <th><a id="Repeater1_lnkOriginCountryName">原产地</a></th>
                                                                                    </tr>
                                                                                    <tr class=" datarow1">
                                                                                        <td>
                                                                                            <input name="Repeater1$ctl01$chk" type="checkbox" id="Repeater1_chk_0" value="A-3100017-DW-UK">
                                                                                        </td>
                                                                                        <td id="Repeater1_Picture_0" class="imgtitle">
                                                                                            <a id="Repeater1_viewImage_0" class="viewpicture">
                                                                                                <img src="https://secure.pushauction.com/20170505/Skins/V1/images/Blank.jpg" alt="" title="">
                                                                                            </a>
                                                                                        </td>
                                                                                        <td>A-3100017-DW-UK</td>
                                                                                        <td>White 8W 7-Level Dimmer LED Desk Lamp table light Touch Sensitiv</td>
                                                                                        <td id="Repeater1_Folder_0"></td>
                                                                                        <td id="Stock"></td>
                                                                                        <td id="MiniStock">-</td>
                                                                                        <td>0.000</td>
                                                                                        <td></td>
                                                                                    </tr>
                                                                                    <tr class="datarow">
                                                                                        <td>
                                                                                            <input name="Repeater1$ctl01$chk" type="checkbox" id="Repeater1_chk_1" value="A-3100017-DW-UK">
                                                                                        </td>
                                                                                        <td id="Repeater1_Picture_1" class="imgtitle">
                                                                                            <a id="Repeater1_viewImage_1" class="viewpicture">
                                                                                                <img src="https://secure.pushauction.com/20170505/Skins/V1/images/Blank.jpg" alt="" title="">
                                                                                            </a>
                                                                                        </td>
                                                                                        <td>A-3100017-DW-UK</td>
                                                                                        <td>White 8W 7-Level Dimmer LED Desk Lamp table light Touch Sensitiv</td>
                                                                                        <td id="Repeater1_Folder_1"></td>
                                                                                        <td id="Stock1"></td>
                                                                                        <td id="MiniStock1">-</td>
                                                                                        <td>0.000</td>
                                                                                        <td></td>
                                                                                    </tr>

                                                                                </tbody>
                                                                            </table>
                                                                        <div class="diogpage" style="border:none; bottom:-10px; position:absolute;right:0;">
                                                                            <div id="AspNetPager1">
                                                                                <div class="pagerecordes">
                                                                                    <b>2588</b> Records 
                                                                                </div>
                                                                                <div class="pagenavagation" style="width:60%;">
                                                                                    <a disabled="disabled" style="margin-right:5px;">
                                                                                        <img >
                                                                                    </a>
                                                                                    <a disabled="disabled" style="margin-right:5px;">
                                                                                        <img >
                                                                                    </a>
                                                                                    <span style="margin-right:5px;font-weight:Bold;color:red;">1</span>
                                                                                    <a style="margin-right:5px;">2</a>
                                                                                    <a style="margin-right:5px;">3</a>
                                                                                    <a style="margin-right:5px;">4</a>
                                                                                    <a style="margin-right:5px;">5</a>
                                                                                    <a style="margin-right:5px;">6</a>
                                                                                    <a style="margin-right:5px;">7</a>
                                                                                    <a style="margin-right:5px;">8</a>
                                                                                    <a style="margin-right:5px;">9</a>
                                                                                    <a style="margin-right:5px;">10</a>
                                                                                    <a style="margin-right:5px;">
                                                                                        <img >
                                                                                    </a>
                                                                                    <a style="margin-right:5px;">
                                                                                        <img >
                                                                                    </a>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table> 
                                                </div>
                                            </td>          
                                        </tr>          
                                            <tr id="_ButtonRow_0" style="">
                                                <td>
                                                    <div id="_DialogButtons_0" class="dialog_button">
                                                        <input type="button" value="确定" class="dialogbutton">
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
