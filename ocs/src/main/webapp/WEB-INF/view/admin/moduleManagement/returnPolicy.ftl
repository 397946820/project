<@FTL.admin id="returnPolicyList1" title="付款方式" add_script_files=['admin/seller/returnPolicy.js']>
	<table id="returnPoliydg"  class="easyui-datagrid" style="width:100%;height:100%"></table>
    <div id="returnPoliydlg" class="easyui-dialog" title="添加" style="width:700px;height:550px;" closed="true" data-options="iconCls:'icon-save',resizable:true,modal:true,buttons:'#dlg-buttons'" >
        <div>
        	<form id="returnPoliyfm" method="post"  style="margin:0;padding:10px 50px">
           <table>
				<tbody>
				<tr>
					<td>名称：</td>
					<td>
					<input type="hidden" name="payid" id="saveAsId">
					<input type="text" id="title" name="title" class="easyui-textbox" data-options="required:true" style="width: 50%" />
					</td>
				</tr>
				<tr>
					<td>站点：</td>
					<td>
					<input type="text"  class="easyui-combobox" id="siteId" name="siteId"  style="width: 50%"/>
					</td>
				</tr>
				<tr>
					<td>退货政策：</td>
					<td>
					<input type="text"  class="easyui-combobox" id="policyType" name="policyType" data-options="valueField:'returnsAcceptedOption',textField:'description',url:''"  style="width: 50%"/>
					
					</td>
				</tr>
				<tr class="hideClass">
					<td>退货天数：</td>
					<td>
					<input type="text"  class="easyui-combobox" id="returnDays" name="returnDays" data-options="valueField:'returnsWithinOption',textField:'description',url:''"  style="width: 50%"/>
					</td>
				</tr>
				<tr class="hideClass">
					<td></td>
					<td>
						<input id="isAllowDelay" type="checkbox" name="allowDelay" /> 
                        <label for="isAllowDelay"> 提供节假日延期退货至12月31日。</label>
					</td>
				</tr>
				<tr class="hideClass usClass">
					<td>退款方式:</td>
					<td>
					<input type="text"  class="easyui-combobox" id="returnType" name="returnType" data-options="valueField:'refundOption',textField:'description',url:''"  style="width: 50%"/>
						
					</td>
				</tr>
				<tr class="hideClass">
					<td>退货运费由谁负担:</td>
					<td>
					<input type="text"  class="easyui-combobox" id="fareTakeInHander" name="faretakeinhander" data-options="valueField:'shippingCostPaidByOption',textField:'description',url:''"  style="width: 50%"/>
						
					</td>
				</tr>
				<tr class="hideClass usClass">
					<td>折旧费:</td>
					<td>
						<input type="text"  class="easyui-combobox" id="depreciationRate" name="depreciationRate" data-options="valueField:'restockingFeeValueOption',textField:'description',url:''"  style="width: 50%"/>
						
					</td>
				</tr>
				<tr class="hideClass">
					<td>退货政策详情:</td>
					<td>
						 <input id="returnPolicyDescription" name="returnDescription" class="easyui-textbox" data-options="multiline:true"  style="width:70%;height:100px"/>
                        <span class="help-block help_block_bottom"> <b class="numoutput returnsPolicyNum" id="returnPolicyDescriptionLength">0</b> / ( 最多 4000 字符. 不支持 HTML )</span>
					</td>
				</tr>
				</tbody>
		   </table>
    </form>
        </div>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="returnPoliy.saveReturnPoliy()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#returnPoliydlg').dialog('close')" style="width:90px">关闭</a>
    </div>
</@FTL.admin>