<@FTL.admin id="payMentList" title="付款方式" add_script_files=['admin/ebayPayment/payment.js']>
<table id="payMentdg"  class="easyui-datagrid" style="width:100%;height:100%"></table>

    
    <div id="paymentdlg" class="easyui-dialog" style="width:600px;height:450px;"
            closed="true" buttons="#dlg-buttons">
        <form id="paymentfm" method="post" novalidate style="margin:0;padding:10px 50px">
            <div style="margin-bottom:20px;font-size:14px;border-bottom:1px solid #ccc"><B>付款方式</B></div>
				<div>
				<div>	
				<table>
				<tbody>
				<tr>
					<td>名称：</td>
					<td>
					<input type="hidden" name="payid" id="saveAsId">
					<input type="text" id="title" name="payName" class="easyui-textbox" data-options="required:true" style="width: 50%" /></td>
				</tr>
				<tr>
					<td>站点：</td>
					<td>
					<input type="text"  class="easyui-combobox" id="siteId" name="ddleBaySiteID"  style="width: 50%"/>
					</td>
				</tr>
				<tr>
				<td>PayPal：</td>
				<td>
					<select class="easyui-combobox" data-options="required:true" id="paypaiAccount" name="payAccount"  style="width:50%">
							<option value="lightingorient@gmail.com">lightingorient@gmail.com</option>
							<option value="payment@lightingever.com">payment@lightingever.com</option>
							<option value="payment@lightingever.co.uk">payment@lightingever.co.uk</option>
							<option value="payment@lightingever.de">payment@lightingever.de</option>
							<option value="lightingallright@gmail.com">lightingallright@gmail.com</option>
					</select>
				</td>
				</tr>
				<tr>
				<td></td>
				<td>
					<input type="checkbox" name="autoPay" id="autoPay" /><label for="autoPay">Require immediate payment when buyer uses Buy It Now</label>
				</td>
				</tr>
				<tr>
					<td valign="top">PayPal：</td>
					<td id="paymentTypeList">
						
					</td>
				</tr>
				<tr>
					<td>付款说明:</td>
					<td>
						<textarea rows="5" cols="60" id="payMentDescription" name="description"></textarea>
					</td>
				</tr>
		</table>
            			</div>
							
						</div>
					
        
    </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="savePayment()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#paymentdlg').dialog('close')" style="width:90px">关闭</a>
    </div>
</@FTL.admin>