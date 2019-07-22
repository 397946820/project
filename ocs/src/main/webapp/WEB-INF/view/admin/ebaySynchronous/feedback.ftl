<@FTL.admin id="Feedback" title="评价管理" add_script_files=['admin/synchronou/feedback.js']>
<div data-options="region:'center',border:false">
    <table id="feedbackDatagrid" class="easyui-datagrid" style="height:100%;"
           data-options="
           url:'${FTL.X.global_domain}/Feedback/list',
           fitColumns:true,
           nowrap:false,
           columns: [
            [
            	{field: 'id', title: 'id',hidden:true},
            	{field: 'product_url', title: 'product_url',hidden:true},
            	{field: 'sku',title: 'sku',hidden:true},
            	{field: 'left_feedback_id', title: 'left_feedback_id',hidden:true},
            	{field: 'order_line_item_id', title: 'order_line_item_id',hidden:true},
            	{field: 'received_feedback_id', title: 'received_feedback_id',hidden:true},
            	{field: 'left_comment_time', title: 'left_comment_time',hidden:true},
            	{field: 'received_comment_time', title: 'received_comment_time',hidden:true},
            	{field: 'commenting_user_score', title: 'commenting_user_score',hidden:true},
            	{field: 'buyer_id', title: 'buyer_id',hidden:true},
                {field: 'item_id', title: 'item_id',hidden:true},
                {field: 'transaction_id', title: 'transaction_id',hidden:true},
                {field: 'imagesUrl',width:'10%', title: '产品图片',formatter:getImagesUrl},
              	<!--{field: 'seller_id',width:'15%', title: '账号'},-->
              	{field: 'seller_id',width:'10%', title: '物品号/订单号',formatter:getIdTitle},
              	{field:'receivedt_comment_type',width:'2%',title:'评价类型',formatter:getCommentType},
                {field: 'item_title',width:'20%', title: '标题',formatter:getItemTitle},
                {field: 'received_comment_text',width:'20%', title: '买家评价',formatter:getBuyer},
                {field: 'left_comment_text',width:'20%', title: '卖家评价',formatter:getSeller},
                {field: 'operation',width:'20%', title: '操作',formatter:getOperation}
                 
            ]
        ],
        idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 20,
        queryParams:{
        	param:{
        		itemTitle:'',
        		buyer_id :'',
        		item_id:'',
        		seller_id:'',
        		receivedt_comment_type :'',
        		left_feedback_id:'',
                received_feedback_id : ''
        	}
        },
        border:false,
        fit:true,
        fitColumns : true,
        nowrap:false,
        toolbar:'#feedbackToolbar'">
    </table>
</div>

<div id="feedbackToolbar">
	
    <div "easyui-panel" style="padding:10px;background:#fff;border-top:2px solid #95b8e7;border-bottom:2px solid #95b8e7;">
		<form id="feedbackToolbarListCondition">
			<table style="float:left;">
				<tr>
					<td  style="width:60px" align="right"><label style="">产品标题:</label></td>
					<td style="width:170px" align="left">
						<input type="text" name="itemTitle" style="float:right;width:170px;" class="easyui-textbox" />
					</td>
					<td  style= "width: 60px; text-align:right;"><label style="">买家账号:</label></td>
					<td style="width:170px" align="left">
						<input type="text" name="buyer_id" class="easyui-textbox" style="width:170px"  />
					</td>
					<td style="width: 60px;text-align:right;"><label style="">物品编号:</label></td>
					<td style="width:170px" align="left">
						<input type="text" name="itemId" class="easyui-textbox" style="width:170px" />
					</td>
					<td style="width: 60px;text-align:right"><label style="">卖家评价ID:</label></td>
					<td>
						<input type="text" name="received_feedback_id" class="easyui-textbox" style="width:170px" />
					</td>
				</tr>
				<tr style="min-width:1000px;">
					
					<td style="width:60px;text-align:right;"><label style="">账号:</label></td>
					<td style="width:170px;" align="left">
						<select id="sellerId" class="easyui-combobox" name="seller_id" style="width:170px;">
							<option ></option>
							<option value="testuser_yangguanbao">testuser_yangguanbao</option>
							<option value="uk.le">uk.le</option>
							<option value="le.deutschland">le.deutschland</option>
							<option value="nm.deutschland">nm.deutschland</option>
							<option value="uk.nm">uk.nm</option>
							<option value="lightingever01">lightingever01</option>
						</select>
					</td>
					<td style="width:60px;text-align:right;"><label>买家评价ID:</label></td>
					<td>
						<input type="text" name="received_feedback_id" class="easyui-textbox" style="width:170px" />
					</td>
				
				<td style="width: 60px;text-align:right;"><label style="">评价类型:</label></td>
					<td>
						<select class="easyui-combobox"  name="receivedt_comment_type" value="" style="width:173px;" >
							<option ></option>
							<option value="Positive">好评</option>
							<option value="Neutral">中评</option>
							<option value="Negative">差评</option>
						</select>
					 </td>
				 <td></td>	 
				 <td center="right">
					<div style="clear:both;text-align: right;width:200px;;line-height: 25px;">
				    	<a  href="javascript:void(0);" id="feedbackReset" onclick="feedback.reset()" class="easyui-linkbutton" iconCls="icon-clear" >重置</a>
						<a  href="javascript:void(0);" id="feedbackSearch" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
						
					</div>
				</td>
			 </tr>
			</table>
			<br  clear="all"/>
		</form>
		
	</div>
	<div>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-update"  plain="true" onclick="feedback.feedbackSynchronous()">同步ebay</a>
	</div>
</div>
<div id="menu" class="easyui-menu" style="width: 50px; display: none;">
 	 <div><a href="javascript:void(0)" onclick="feedback.commentsLink()" class="easyui-linkbutton" data-options="menu:'#mm1',iconCls:'icon-success',plain:true">评价</a></div>
	 <div><a href="javascript:void(0)" onclick="feedback.replyLink()" class="easyui-linkbutton" data-options="menu:'#mm2',iconCls:'icon-success',plain:true">回复</a></div>
	 <div><a href="javascript:void(0)" onclick="feedback.followUpLink()" class="easyui-linkbutton" data-options="menu:'#mm3',iconCls:'icon-success',plain:true">追加</a></div>
	 <div><a href="javascript:void(0)" onclick="note()" class="easyui-linkbutton" data-options="menu:'#mm4',iconCls:'icon-success',plain:true">发送消息</a></div>
	 <div><a href="javascript:void(0)" onclick="feedback.noteLink()" class="easyui-linkbutton" data-options="menu:'#mm5',iconCls:'icon-success',plain:true">备注</a></div>
	 
 </div>
<div id="replyAdditionalDialog" class="easyui-dialog" style="width:600px"
            closed="true" buttons="#feedback-buttons">
        <form id="replyAdditionalForm" method="post" novalidate style="margin:0;padding:10px 40px">
            
            
			<div style="margin-bottom:10px">
			<input style="display:none" id="targetSellerId" type="easyui-textbox" name="seller_id">
				<input style="display:none" id="formId" type="easyui-textbox" name="id">
				<input style="display:none" id="targetUserID" type="easyui-textbox" name="buyer_id">
        		<input style="display:none" id="feedbackID" type="easyui-textbox" name="received_feedback_id">
        		<input style="display:none" id="orderLineItemID" type="easyui-textbox" name="order_line_item_id">
        	</div>
            <div>
				<div style="margin-bottom:10px">
					<!-- <input   class="easyui-combobox" id="siteId" name="siteId" label="定时发布站点:"  style="width:400px;"  data-options="disabled:'true',value:'0',valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/> -->
				类型:<span id="responseType" name="response_type" style="color:green;padding-left:20px;"></span>
				</div>
			</div>
            <div style="margin-top:10px;">
            	<label style="padding-right:8px;">内容 :</label>
             	<input class="easyui-textbox" name="responseText" data-options="multiline:true"  style="width:87%;height:100px">
            </div>
            
        </form>
    </div>
    <div id="feedback-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="feedback.replyFollowUp()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#replyAdditionalDialog').dialog('close')" style="width:90px">关闭</a>
    </div>
    <div id="commentsDialog" class="easyui-dialog" style="width:600px"
            closed="true" buttons="#feedbackComments-buttons">
        <form id="commentsForm" method="post" novalidate style="margin:0;padding:10px 40px">
            
            
			<div style="margin-bottom:10px">
				<input style="display:none" id="commentsItemId" type="easyui-textbox" name="item_id">
				<input style="display:none" id="commentsSellerId" type="easyui-textbox" name="seller_id">
				<input style="display:none" id="commentsFormId" type="easyui-textbox" name="id">
				<input style="display:none" id="commentsTargetUserID" type="easyui-textbox" name="buyer_id">
        		<input style="display:none" id="commentsOrderLineItemID" type="easyui-textbox" name="order_line_item_id">
        	</div>
            <div>
				<div style="margin-bottom:10px">
					<!-- <input   class="easyui-combobox" id="siteId" name="siteId" label="定时发布站点:"  style="width:400px;"  data-options="disabled:'true',value:'0',valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/> -->
				类型  :<span id="commentsResponseType" name="response_type" style="color:green;padding-left:20px;"></span>
				</div>
			</div>
            <div style="margin-top:10px;">
            	<label style="padding-right:8px;">内容 ：</label>
             	<input class="easyui-textbox" name="responseText" data-options="multiline:true"  style="width:87%;height:100px">
            </div>
            
        </form>
    </div>
    <div id="feedbackComments-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="feedback.comments()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#commentsDialog').dialog('close')" style="width:90px">关闭</a>
    </div>
    <div id="noteDialog" class="easyui-dialog" style="width:600px"
            closed="true" buttons="#feedbackNote-buttons">
        <form id="noteForm" method="post" novalidate style="margin:0;padding:10px 40px">
            
            
			<div style="margin-bottom:10px">
				<input style="display:none" id="remarkId" type="easyui-textbox" name="id">
        	</div>
            <div style="margin-top:10px;">
            	<label style="padding-right:20px;"> 备注  : </label>
             	<input class="easyui-textbox" name="remark" data-options="multiline:true"  style="width:87%;height:100px">
            </div>
            
        </form>
    </div>
    <div id="feedbackNote-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="feedback.note()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#noteDialog').dialog('close')" style="width:90px">关闭</a>
    </div>
</@FTL.admin>