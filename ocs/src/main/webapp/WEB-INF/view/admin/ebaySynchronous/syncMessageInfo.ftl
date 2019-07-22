<@FTL.admin id="syncMessageInfo" title="销售数据同步" add_script_files=['admin/synchronou/syncMessageInfo.js']>
	<div class="easyui-panel" id="yncMessageInfoBar">
		<form id="messageListSearchParam" style="background:#fff;padding:10px;border-bottom:1px solid #95B8E7;">
			<table style="float:left; min-width:710px;">
				<tr style="min-width:1000px;">
					<td><label style="">发送人:</label></td>
					<td>
						<select class="easyui-combobox" name="sender" value="" style="width:173px;" >
							<option ></option>
							<option value="eBay">eBay</option>
							<option value="非eBay">非eBay</option>
						</select>
					</td>
					<td><label style="">eBay账号:</label></td>
					<td>
						<input type="text"  id="ebayAccount" name="account"   style="width:173px;"  />
					</td>
					<!-- <td><label style="">读状态:</label></td>
					<td>
						<select class="easyui-combobox" name="read" value="" style="width:173px;" >
							<option ></option>
							<option value="1">已读</option>
							<option value="0">未读</option>
						</select>
					</td>
					<td><label style="">回复状态:</label></td>
					<td>
						<select class="easyui-combobox" name="replied" value="" style="width:173px;" >
							<option ></option>
							<option value="1">已回复</option>
							<option value="0">未回复</option>
						</select>
					</td> -->
					<td><label style="">消息类型:</label></td>
					<td>
						<select class="easyui-combobox" name="messageType" value="" style="width:173px;" >
							<option ></option>
							<option value="ContacteBayMemberViaAnonymousEmail">ContacteBayMemberViaAnonymousEmail</option>
							<option value="AskSellerQuestion">AskSellerQuestion</option>
							<option value="ResponseToASQQuestion">ResponseToASQQuestion</option>
							<option value="ContactEbayMember">ContactEbayMember</option>
							<option value="ContactTransactionPartner">ContactTransactionPartner</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>时间类型：</td>
					<td>
						<select class="easyui-combobox" name="dateType" value="" style="width:173px;" >
							<option ></option>
							<option value="receive_date">接收时间</option>
							<option value="expiration_date">失效时间</option>
						</select>
					</td>
					<td>时间段：</td>
					<td colspan="3">
					   <input type="text" id="startDate" name="startDate" class="easyui-datetimebox" style="width: 173px;"  data-options="validType:'checkDate'" /> &nbsp至&nbsp 
				       <input type="text" id="endDate" name="endDate" class="easyui-datetimebox" style="width: 173px;"  data-options="validType:'checkDate'" />
					</td>
					<td>
						<div style="clear:both;text-align: right;width:150px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="messageListReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="messageListSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
						</div>
					</td>
				</tr>
			</table>
			<br clear="all"/>
		</form>
		<div>
			<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-update" id="syncMessageInfoSync"
      		 plain="true">同步</a>
      		 <a id="readLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-readed"
	     	  plain="true">标记已读</a>
	       	<a id="unReadEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-read"
	     	  plain="true">标记未读</a>
		</div>
	</div>
		<table id="syncMessageInfoTable" style="width:100%;height:100%;">
		</table>
	<div id="ebayMessageView" class="easyui-dialog" title="ebay消息查看" style="width:800px;height:450px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	    <div style="width:100%;height:100%" id="ebayMessageViewDiv"></div>
	</div>
	<div id="ebayMessageRemark" class="easyui-dialog" title="备注" style="width:500px;height:300px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#text-button'" >
	    <div style="width:97%;margin:10px auto;" >
	    	<input class="easyui-textbox" style="width:100%;height:200px" data-options="multiline:true" id="messageRemark" />
	    </div>
	    <div id="text-button" style="width: 100%;text-align:right;">
	    	<a  href="javascript:void(0);" id="messageRemarkOk" class="easyui-linkbutton c8" iconCls="icon-ok"  >确定</a>
			<a  href="javascript:void(0);" id="messageRemarkCancel" class="easyui-linkbutton" iconCls="icon-clear" >关闭</a>
		</div>
	</div>
</@FTL.admin>