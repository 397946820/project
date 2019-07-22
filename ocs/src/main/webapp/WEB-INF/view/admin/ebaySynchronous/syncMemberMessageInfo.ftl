<@FTL.admin id="syncMemberMessageInfo" title="消息管理" add_script_files=['admin/ocs/main.js','admin/ocs/baseDate.js','admin/synchronou/syncMemberMessageInfo.js']>
	<div class="easyui-panel" id="syncMemberMessageInfoBar">
		<form id="memberMessageListSearchParam" style="background:#fff;padding:10px;border-bottom:1px solid #95B8E7;">
			<table style="float:left; min-width:710px;">
				<tr style="min-width:1000px;">
					<td><label style="">物品号:</label></td>
					<td><input type="text" name="itemId" style="float:right" class="easyui-textbox" /></td>
					<td><label style="">来自(发送人):</label></td>
					<td><input type="text" name="senderID" style="float:right" class="easyui-textbox" /></td>
					<td><label style="">物品标题:</label></td>
					<td><input type="text" name="title" style="float:right" class="easyui-textbox" /></td>
				</tr>
				<tr>
				<td><label style="">主题:</label></td>
					<td><input type="text" name="subject" style="float:right" class="easyui-textbox" /></td>
					
				<td><label style="">eBay账号:</label></td>
				<td>
					<input type="text"  id="ebayAccount" name="recipientID"   style="width:173px;"  />
				</td>
				<td><label style="">SKU:</label></td>
				<td>
					<input type="text" name="sku" style="float:right" class="easyui-textbox" />
				</td>
				</tr>
				<tr>
					<td><label style="">消息内容:</label></td>
					<td>
						<input type="text" name="body" style="float:right" class="easyui-textbox" />
					</td>
					
					<td><label style="">创建时间</label></td>
					<td colspan="3">
					   <input type="text" id="startDate" name="startDate" class="easyui-datetimebox" style="width: 175px;"  data-options="validType:'checkDate'" /> <span style="padding:0 20px">至</span> 
				       <input type="text" id="endDate" name="endDate" class="easyui-datetimebox" style="width: 175px;"  data-options="validType:'checkDate'" />
					</td>
					<td></td>
					<td>
						<div style="clear:both;text-align: left;width:175px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="memberMessageListReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="memberMessageListSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
						</div>
					</td>
				</tr>
				<tr style="min-width:1000px;">
				   <td><label style="">状态 :</label></td>
				   <td colspan="9">
					<ul class="con-button" style="text-align: center;float: left;margin:0;padding:0; cursor: pointer;" >
						<li style="background:#ccc; list-style: none;border:1px solid #95b8e7;float: left;height: 30px;line-height: 28px;padding: 0 5px;">
							<input type="radio" checked="checked" name="messageStatus" id="total" value="" style="display: none;"/>
							<label for="total" >全部<span></span></label>
						</li>
						<li style="list-style: none;padding:7px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="messageStatus" value="1" style="display: none;"/>
							<label for="0"><div class="icon3 unreply"></div>售前未读<span></span></label>
						</li>
						<li style="list-style: none;padding:7px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="messageStatus" value="2" style="display: none;"/>
							<label for="0"><div class="icon3 replied"></div>售前已回复<span></span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="messageStatus" value="3" style="display: none;"/>
							<label for="0"><div class="icon3 reply"></div>售前已读<span></span></label>
						</li>
						<li style="list-style: none;padding:7px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="messageStatus" value="4" style="display: none;"/>
							<label for="0"><div class="icon3 unreply"></div>售后未读<span></span></label>
						</li>
						<li style="list-style: none;padding:7px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="messageStatus" value="5" style="display: none;"/>
							<label for="0"><div class="icon3 replied"></div>售后已回复<span></span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="messageStatus" value="6" style="display: none;"/>
							<label for="0"><div class="icon3 reply"></div>售后已读<span></span></label>
						</li>
					</ul>
				</tr>
			</table>
			<br clear="both"/>
		</form>
		<div>
			<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-update" id="syncMemberMessageInfosync"
      		 plain="true">同步</a>
  		 	<a id="readLinkbutton" href="javascript:void(0);" onclick="memberMessageManager.updateSelectToRead()" class="easyui-linkbutton" iconCls="icon-readed"
	     	  plain="true">标记已读</a>
	       	<a id="unReadEditLinkbutton" href="javascript:void(0);" onclick="memberMessageManager.updateSelectToUnRead()" class="easyui-linkbutton" iconCls="icon-read"
	     	  plain="true">标记未读</a>
</div>
	</div>
	<table id="syncMemberMessageInfoTable" style="width:100%;height:100%">
	</table>
	<div id="ebayMemberMessageRemark" class="easyui-dialog" title="备注" style="width:500px;height:300px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#ebayMemberMessageRemarkButton'">
	    <div style="width:97%;margin:10px auto;" >
	    	<input class="easyui-textbox" style="width:100%;height:200px" data-options="multiline:true" id="memberMessageRemark" />
	    </div>
	    <div style="width: 100%;text-align:right;" id="ebayMemberMessageRemarkButton">
	    	<a  href="javascript:void(0);" id="memberMessageRemarkOk" class="easyui-linkbutton c8" iconCls="icon-ok"  >确定</a>
			<a  href="javascript:void(0);" id="memberMessageRemarkCancel" class="easyui-linkbutton" iconCls="icon-clear" >关闭</a>
		</div>
	</div>
	<div id="ebayMemberMessageAnswer" class="easyui-dialog" title="回复" style="width:1000px;height:600px;display:none;"data-options="iconCls:'icon-edit',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">

		    <div>
		    	<div style="float:left; width:45%; height:280px;overflow:auto;margin:15px; border:1px solid #95b8e7;border-radius:5px;" >
		    		<ul id="oldMessage" style="padding-left:20px;">
		    		</ul>
		    	</div>
		    	<div style="float:right; width:45%; height:280px;overflow:auto;margin:15px; border:1px solid #95b8e7;border-radius:5px;" title="">
		    		<div style=" float: left;margin-right: 50px;margin-left: 5px;margin-top: 5px;">
			    	<img style="border:1px solid #95b8e7;width:100px;height:150px;"  src="" id="imageValue" name="imageValue">
			     	</div>
			     	<div >
				     	<div    style="margin-top: 15px;padding-left: 50px;" >
				     		<a target="_blank" id="itemUrl"></a>
				     	</div>
				     	<div  style="margin-top: 15px;padding-left: 50px;" >
				     		SKU: <span id="itemSKU"></span>
				     	</div>
				     	<div  style="margin-top: 15px;padding-left: 50px;" >
				     		<span id="itemIdSpan"></span>
				     	</div>
				     	<div  style="margin-top: 15px;padding-left: 50px;" >
				     		<span id="itemMInfo"></span>
				     	</div>
			     	</div>
			    </div>
		    	<div style="width:45%;margin:15px ;float:left" >
		    		<input class="easyui-textbox" style="width:100%;height:75px" data-options="multiline:true" id="memberMessageAnswer" />
		    	</div>
			    <table id="syncSaleInfoTable" class="easyui-datagrid" style="width:45%;height: 150px;margin-left: 50px;">
				</table>
		    	<div style="width:45%;margin-top:-8px;margin-left:4px;" >
		    		<input type="checkbox" id="isView"/> 是否显示在物品详情页
		    	</div>
		    </div>
		   
	    <div style="background:#f4f4f4;border-top:1px solid #dddddd;margin-top:24px;width:100%;height:39px;">
		    <div style="position:absolute;width: 34%;right:-15px;bottom:15px;">
		    	<a  href="javascript:void(0);" id="memberMessageAnswerOk" class="easyui-linkbutton c8"  iconCls="icon-ok"  >回复</a>
				<a  href="javascript:void(0);" id="memberMessageAnswerCancel" class="easyui-linkbutton"  iconCls="icon-no" >关闭</a>
			</div>
		</div>
	</div>
	
	
</@FTL.admin>