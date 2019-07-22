<@FTL.admin id="itemPromote" title="物品推广" add_script_files=['admin/seller/itemPromote.js']>

	<table id="itemPromoteList"  class="easyui-datagrid" style="width:100%;height:100%" > </table>

    <div id="sellerDialog" class="easyui-dialog" style="width:800px;height:650px;" closed="true" buttons="#dlg-buttons" data-options="modal:true">
       
	<div>
        <form id="sellerForm"  style="width:100%;">
        	<table style="background:#fff;padding:10px;" id="sellerFormrtoolbar">
        		<tr style="height: 32px;">
        			<td >名称:</td>
        			<td >
        				<input style="display:none" type="easyui-textbox" name="id">
                		<input id="templateName" name="templateName" class="easyui-textbox" required="true" style="width:220px;">
                	</td>
        			<td  >推广类型:</td>
        			<td >
        				<select class="easyui-combobox" name="promoteType" id="promoteType" required="true" style="width:173px;width:220px;" >
							<option value="0">顶部推广</option>
							<option value="1">底部推广</option>
							<option value="2">内部推广</option>
						</select>
        			</td>
        		</tr>
        		<tr style="height: 32px;">
        			<td >推广方式:</td>
        			<td colspan="3">

						<input type="radio" value="0" name="dataType" id="dataForChoose" checked="checked"/><label for="dataForChoose">指定推广</label>
						<input type="radio" value="1" name="dataType" id="dataForRule"/><label for="dataForRule">规则推广</label>
						
        			</td>
        		</tr>
        		<tr id="dataForSelected" style="height: 32px;">
        			<td>账号:</td>
        			<td>
        				<input type="text"  class="easyui-combobox" id="templateAccount" name="account"  style="width:173px;width:220px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getAccountList'"/>
        			</td>
        			<td >站点:</td>
        			<td>
        				<input type="text"  class="easyui-combobox" style="width:220px;"  id="templateSite"  name="siteId"   data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/>
        			</td>
        		</tr>
        		
        		<tr id="dataForRuleTr" style="height:300px;">
        			<td colspan="6" style="padding-left: 0;margin-left: 0;">
        				<table style="width: 100%;">
        					<tr style="height: 32px;">
			        			<td>账号:</td>
			        			<td>
									<input type="radio" value="0" name="account_check" id="account_check0" checked="checked"/><label for="account_check0">刊登账号</label>
									<input type="radio" value="1" name="account_check" id="account_check1"/><label for="account_check1">指定方式</label>
			        			</td>
			        			<td style="display:none;">
			        				<input type="text"  class="easyui-combobox" id="templateAccount1" name="account1" style="width:173px;width:220px;"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getAccountList'"/>
			        			</td>
			        		</tr>
			        		<tr style="height: 32px;">
			        			<td >站点:</td>
			        			<td>
									<input type="radio" value="0" name="site_check" id="site_check0" checked="checked"/><label for="site_check0">刊登站点</label>
									<input type="radio" value="1" name="site_check" id="site_check1"/><label for="site_check1">指定方式</label>
			        			</td>
			        			<td style="display:none;">
			        				<input type="text"  class="easyui-combobox" style="width:220px;"  id="templateSite1" name="siteId1"   data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/>
			        			</td>
			        		</tr>
			        		<tr style="height: 32px;">
			        			<td>刊登类型:</td>
			        			<td>
			        				<input type="radio" value="0" name="publictionType_check" id="publictionType_check0" checked="checked"/><label for="publictionType_check0">刊登类型</label>
									<input type="radio" value="1" name="publictionType_check" id="publictionType_check1"/><label for="publictionType_check1">指定方式</label>
			       				</td>
			        			<td style="display:none;">
			        				<input type="checkbox" value="Chinese" name="publictionType" id="Chinese"/><label for="Chinese">拍卖</label>
									<input type="checkbox" value="FixedPriceItem" name="publictionType" id="FixedPriceItem"/><label for="FixedPriceItem">固价</label>
									<input type="checkbox" value="FixedPriceItem1" name="publictionType" id="FixedPriceItem1"/><label for="FixedPriceItem1">多属性</label>
			        			</td>
			        		</tr>
			        		<tr style="height: 32px;">
			        			<td >物品分类:</td>
			        			<td>
									<input type="radio" value="0" name="itemType_check" id="itemType_check0" checked="checked"/><label for="itemType_check0">刊登分类</label>
									<input type="radio" value="1" name="itemType_check" id="itemType_check1"/><label for="itemType_check1">指定方式</label>
			        			</td>
			        			<td style="display:none;">
			        				<input name="itemType" id="itemTypeSelect" class="easyui-combotree" style="width:235px;"  />
			        			</td>
			        		</tr>
	        				<tr style="height: 32px;">
			        			<td>商店分类:</td>
			        			<td>
			        				<input type="radio" value="0" name="itemStore_check" id="itemStore_check0" checked="checked"/><label for="itemStore_check0">刊登分类</label>
									<input type="radio" value="1" name="itemStore_check" id="itemStore_check1"/><label for="itemStore_check1">指定方式</label>
			       				</td>
			        			<td style="display:none;">
			        				<input name="itemStore" id="itemStoreSelect" class="easyui-combotree"  style="width:235px;"/>
			        			</td>
			        		</tr>
			        		<tr style="height: 32px;">
			        			<td>关键字:</td>
			        			<td>
			        				<input name="keyword"  class="easyui-textbox" style="width:235px;" />
			        			</td>
			        			<td>
			        				<span style="color:red;">多个关键字请用英文的逗号（,）隔开</span>
			        			</td>
			        		</tr>
			        		<tr style="height: 32px;">
			        			<td>价格:</td>
			        			<td>
			                		<input name="priceStart" class="easyui-textbox" style="width:20%"/> &nbsp;——&nbsp;
			                		<input name="priceEnd" class="easyui-textbox"  style="width:20%">
			                	</td>
			                	<td>
			                	<span style="color:red;">不填代表不启用，只填写左边代表大于此数，只填写右边代表小于此数</span>
			                	</td>
			        		</tr>
			        		<tr style="height: 32px;">
			        			<td>排序:</td>
			        			<td>
			        				<select class="easyui-combobox" name="orderKey"  style="width:235px;" >
										<option value="0">最热卖</option>
										<option value="1">最不好卖</option>
										<option value="2">最新刊登</option>
										<option value="3">即将结束</option>
									</select>
			        			</td>
			        		</tr>
        				</table>
        			</td>
        		</tr>
        		<tr id="dataForRuleButtonTr">
        			<td colspan="6">
        				<a href="javascript:void(0);" id="dataForRuleLoad" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">加载</a>
        			</td>
        		</tr>
        		<tr id="dataForChooseTr">
        			<td colspan="6">
        				<a href="javascript:void(0);" id="itemDataChoose" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
        			</td>
        		</tr>
        	</table>
           <table id="itemPromoteChooseResult"  class="easyui-datagrid" style="width:100%;height:645px;overflow:auto;"  toolbar="#sellerFormrtoolbar"> </table>
        </form>
        </div>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="itemPromote.saveItemPromote();" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#sellerDialog').dialog('close')" style="width:90px">关闭</a>
    </div>
    
    
    <div id="productSelectLink" title="产品选择" class="easyui-dialog" style="width:460px;height:455px;" data-options="modal:true" closed="true" buttons="#productSelectLink-buttons">
    	<div style="padding-top: 5px;">
		   <span>SKU：</span><span><input type="text" id="searchTemplateSKU" style="border: 1px solid #95b8e7;border-radius: 4px;padding:2px 5px;width: 120px;" name="templateSKU"/></span>
		   <span style="padding-left:10px;">物品号：</span><span><input type="text" id="searchTemplateItemId" name="templateItemId" style="border: 1px solid #95b8e7;border-radius: 4px;padding:2px 5px;width: 120px;"/></span>
	  	</div>
	  	<div style="padding-top:5px;">
	  	 <span>标题：</span><span><input  type="text" id="searchTemplateName" name="templateName"  style="border: 1px solid #95b8e7;border-radius: 4px;padding:2px 5px;width: 120px;"/></span>
	     <span style="padding-left:10px;">分类：</span><span style="margin-left: 11px;"><input type="text" id="searchTemplateField" name="templateField"  style="border: 1px solid #95b8e7;border-radius: 4px;padding:2px 5px;width: 120px;"/></span>
	  	<div class="easyui-linkbutton c8" onclick="itemPromote.searchProduct();" style="margin-left: 374px;margin-top:-46px;">搜索</div>
	  	</div>

		<table id="selectProList"  class="easyui-datagrid" style="height:310px;"></table>
    </div>
    <div id="productSelectLink-buttons">
    	<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-ok" onclick="itemPromote.selectItemOK();" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#productSelectLink').dialog('close')" style="width:90px">关闭</a>
    </div>

</@FTL.admin>