<#macro categorySearch>
            <div class="searchCategoryModel" style="display: none">
                <div class=" dialogbox">
                    <table id="_DialogTable_0" width="720" class="dialog" cellspacing="0" cellpadding="0">    
                        <tbody>
                            <tr valign="top">      
                            <td align="center">
                                <table width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff">      
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="dialoghead" style="cursor:move">
                                                    <div class="dialog_title">搜索分类</div>      
                                                    <div class="dialog_close">X</div>
                                                </div>
                                            </td>
                                        </tr>          
                                        <tr>            
                                            <td valign="top" align="center" class="dialogmsghead">            
                                                <div style="position: relative; width: 688px; height: 396px;top:0px; left:0px; background:#fff;">             
                                                    <table cellpadding="0" cellspacing="1" style="width: 100%; height: 100%;">
                                                        <tbody>
                                                            <tr>
                                                                <td style="height: 40px;">
                                                                    <div class="dialog_head">
                                                                        <span>搜索</span>
                                                                        <input name="t_SearchWord" type="text" class="search_txt">
                                                                        <input type="submit" name="btnSearch" value="搜索" class="search_btn">
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td style="height: 100%; vertical-align: top;">
                                                                    <div class="table_wrap">
                                                                        <table class="datagrid" cellpadding="0" cellspacing="0">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th></th>
                                                                                    <th>分类号</th>
                                                                                    <th>名称</th>
                                                                                    <th>接近度</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                            </tbody>
                                                                        </table>
                                                                        <span class="emptymessage">没有记录！</span> 
                                                                    </div>               
                                                                </td>
                                                            </tr>
                                                        </tbody>
                                                    </table>          
                                                </div>
                                            </td>          
                                        </tr>          
                                        <tr>            
                                            <td>
                                                <div  class="dialog_button">
                                                    <input type="hidden" name="category">               
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
