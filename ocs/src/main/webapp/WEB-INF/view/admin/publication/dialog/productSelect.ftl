<#macro productSelect>
           <div class="selectCategoryModel" style="display: none;">
                <div class=" dialogbox">
                    <table class="dialog" cellspacing="0" cellpadding="0"> 
                       <tbody>
                            <tr valign="top"> 
                                <td align="center">
                                    <table width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff">
                                        <tbody>
                                            <tr>
                                                <td>
                                                    <div class="dialoghead" style="cursor:move">
                                                        <div class="dialog_title">物品分类</div> 
                                                        <div class="dialog_close">X</div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td valign="top" class="dialogmsghead">
                                                    <div style="position: relative; width: 860px; height: 580px;top:0px; left:0px; background:#fff;">               
                                                        <form>
                                                            <div class="diogcnt" id="divBody">
                                                                <div>
                                                                    <div class="selectCategory_header">
                                                                        <span class="SuccessMsg" style="display: none;">
                                                                            <div class="Success" id="msgBoxright">
                                                                                <span class="close">x</span>
                                                                                <ul>
                                                                                    <li>您已经选择了一个物品分类，请点击【确定】继续！</li>
                                                                                </ul>
                                                                            </div>
                                                                        </span>
                                                                        <span class="ErrorMsg" style="display: none;">
                                                                            <div class="Success" >
                                                                                
                                                                                <ul>
                                                                                    <li>您所选择的分类还有子分类可选，请继续选择分类！</li>
                                                                                </ul>
                                                                            </div>
                                                                        </span>
                                                                        <p>
                                                                            <b>分类号</b>
                                                                            <input name="txteBayCatID" type="text" readonly="readonly" class="txteBayCatID" style="border-width:0px;">
                                                                        </p>
                                                                    </div>
                                                                    <div class="storeCategory_header" style="display: none;">
                                                                        <p>
                                                                            <b>分类号</b>
                                                                            <input name="txteBayCatID" type="text" readonly="readonly" class="txteBayCatID" style="border-width:0px;">
                                                                        </p>
                                                                        <p class="synceBay" style="display:none;">
                                                                            <input type="submit" name="synceBay" value="同步eBay" class="btn btn-sm blue">
                                                                        </p>
                                                                        <span class="SuccessMsg" style="display: none;">
                                                                            <div class="Success" id="msgBoxright2">
                                                                                <span class="close">x</span>
                                                                                <ul>
                                                                                    <li>您已经选择了一个物品分类，请点击【确定】继续！</li>
                                                                                </ul>
                                                                            </div>
                                                                        </span>
                                                                    </div>
                                                                    <div class="selectcategory" id="select_category"> 
                                                                        <ul>
                                                                        </ul>                                 
                                                                    </div> 
                                                                    <p>
                                                                    	<span id="lbleBayCatText"></span>
                                                                    	<span id="lbleBayCatId" style="display:none;"></span>
                                                                    </p> 
                                                                </div>      
                                                            </div>  
                                                        </form>              
                                                    </div>
                                                </td>          
                                            </tr>          
                                            <tr>            
                                                <td>
                                                    <div  class="dialog_button">
                                                        <input type="hidden" name="category">               
                                                        <input type="button" value="确定" class="dialogbutton" >
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
