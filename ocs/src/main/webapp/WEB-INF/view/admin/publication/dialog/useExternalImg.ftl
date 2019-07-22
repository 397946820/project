<#macro useExternalImg>
            <div class="useExternalImgModel" style="display: none;">
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
                                                <div class="dialog_title">使用外部图片</div>      
                                                <div class="dialog_close">X</div>
                                            </div>
                                        </td>
                                    </tr>          
                                    <tr class="ecternalImgTr">            
                                        <td valign="top" align="center" class="dialogmsghead">   
                                            <ul class="ecternalImgUl">
                                                <li>
                                                    <div>
                                                        <img class="ecternalImg" />
                                                    </div>
                                                    <div>
                                                        <label>URL</label>
                                                        <input type="text" name="ecternalImg" />
                                                    </div>
                                                </li>
                                                <li>
                                                    <div>
                                                        <img class="ecternalImg" />
                                                    </div>
                                                    <div>
                                                        <label>URL</label>
                                                        <input type="text" name="ecternalImg" />
                                                    </div>
                                                </li>
                                                <li class="add_ecternalImg">
                                                    <a href="javascript:void(0);">添加</a>
                                                </li>
                                            </ul>
                                        </td>          
                                    </tr>          
                                    <tr>            
                                        <td>
                                            <div class="dialog_button"> 
                                                <input type="hidden" name="selectImg">           
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
