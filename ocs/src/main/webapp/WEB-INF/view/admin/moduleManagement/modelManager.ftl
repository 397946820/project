<@FTL.admin id="modelManager" title="物品推广" add_script_files=['admin/seller/modelManager.js']>

	<table id="modelManagerList"  class="easyui-datagrid" style="width:100%;height:100%" > </table>
	
	<div id="modelManagerAddDialog" class="easyui-dialog" style="width:500px" title="编辑"
            closed="true" buttons="#dlg-buttons" data-options="modal:true">
        <form id="modelManagerAddfm" method="post" novalidate style="margin:0;padding:20px 50px">
				<div>
				<table cellpadding="5">
		    		<tr>
		    			<td>名称:</td>
		    			<td><input class="easyui-textbox" type="text" id="m_name" name="m_name" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>站点:</td>
		    			<td>
		    			<input type="text"  class="easyui-combobox" id="m_siteId" name="m_siteId"  style="width:250px;"  data-options="required:true,valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>ebay账户:</td>
		    			<td>
		    			<input type="text" style="width:250px;" class="easyui-combobox" id="m_account" name="m_account" data-options="required:true,valueField:'value',textField:'displayName',url:'/ocs/publication/getAccountList'"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>顶部推广:</td>
		    			<td>
		    			<input type="text"  class="easyui-combobox" name="m_topPromotionType" id="m_topPromotionType"   style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getPromoteList/0'"/>
						</td>
		    		</tr>
		    		<tr>
		    			<td>底部推广:</td>
		    			<td>
		    			<input type="text"  class="easyui-combobox" name="m_footerPromotionType" id="m_footerPromotionType"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getPromoteList/1'"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>卖家描述:</td>
		    			<td>
		    			<input type="text"  class="easyui-combobox" name="m_sellerDescriptionTemp" id="m_sellerDescriptionTemp"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getSellerDescription/0'"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>付款:</td>
		    			<td>
		    				<input type="text"  class="easyui-combobox" name="m_payment" id="m_payment"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/0/0'"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>买家要求:</td>
		    			<td>
		    				<input type="text"  class="easyui-combobox" name="m_buyRequire" id="m_buyRequire"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/0/0'"/>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>退货政策:</td>
		    			<td>
		    				<input type="text"  class="easyui-combobox" name= "m_returnPolicy" id="m_returnPolicy"  style="width:250px;" data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/0/0'" />
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>物品所在地:</td>
		    			<td>
		    				<input type="text"  class="easyui-combobox" name= "m_itemPlace" id="m_itemPlace"  style="width:250px;" data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/0/0'" />
		    			</td>
		    		</tr>
		    		<tr>
		    			<td>运输选项:</td>
		    			<td>
		    				<input type="text"  class="easyui-combobox" name= "m_tran" id="m_tran"  style="width:250px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getModelTempData/0/0'"/>
		    			</td>
		    		</tr>
		    		
	    	</table>
				</div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="modelManager.saveModel()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#modelManagerAddDialog').dialog('close')" style="width:90px">关闭</a>
    </div>
</@FTL.admin>