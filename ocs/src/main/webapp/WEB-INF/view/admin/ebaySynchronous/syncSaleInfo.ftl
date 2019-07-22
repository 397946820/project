<@FTL.admin id="syncSaleInfo" title="销售数据同步" 
add_script_files=['admin/synchronou/syncSaleInfo.js','admin/salesStatistics/saleOrderRefund.js']>
	<div class="easyui-panel" id="syncSaleInfoBar" style="background:#fff;padding-top:10px;">
		<form id="saleListSearchParam">
		<table style="float:left; min-width:700px;display:none;margin-left: 10px;" id="moreSeachTa">
			<tr>
				<td align="right"><label style="">OCS平台id:</label></td>
				<td colspan="5"><input type="text" name="ocsId" style="width:350px;" class="easyui-textbox" />&nbsp;&nbsp;(多个使用常用特殊符号隔开)</td>
			</tr>
			<tr>
				<td align="right"><label style="">SKU:</label></td>
				<td colspan="5"><input type="text" name="sku" style="width:350px;" class="easyui-textbox" />&nbsp;&nbsp;(多个sku使用英文空格隔开)</td>
			</tr>
			<tr>
				<td align="right"><label style="">物品号:</label></td>
				<td colspan="5"><input type="text" name="itemId" style="width:350px;" class="easyui-textbox" />&nbsp;&nbsp;(多个物品号使用英文空格隔开)</td>
			</tr>
			<tr >
				<td align="right"><label style="">物品标题:</label></td>
				<td><input type="text" name="title"  class="easyui-textbox" style="width:150px;" />
				</td>
				<td align="right"><label style="">Paypal交易号:</label></td>
				<td><input type="text" name="paypalCode" style="float:right;width:150px;" class="easyui-textbox" /></td>
				<td  align="right"><label style="">跟踪号:</label></td>
				<td><input type="text" name="shipNumber" style="float:right;width:150px;" class="easyui-textbox" /></td>
			</tr>
			<tr style="min-width:1000px;">
				<td align="right"><label style="">eBay交易号:</label></td>
				<td><input type="text" name="transactionId"  class="easyui-textbox"  style="width:150px;"/>
				<td align="right"><label style="">备注:</label></td>
				<td><input type="text" name="remark" style="float:right;width:150px;" class="easyui-textbox" /></td>
				<td align="right"><label style="">买家电邮:</label></td>
				<td>
					<input type="text" name="email"  class="easyui-textbox" style="width:150px;" />
				</td>
			</tr>
			<tr>
				<td align="right"><label style="">联系人:</label></td>
				<td><input type="text" name="buyerName"  class="easyui-textbox" style="width:150px;" />
				</td>
				<td align="right"><label style="">邮编:</label></td>
				<td>
					<input type="text" style="width:150px;" class="easyui-textbox"  name="postCode" />
				</td>
				<td align="right"><label style="">街道:</label></td>
				<td>
					<input type="text" style="width:150px;" class="easyui-textbox"  name="street" />
				</td>
			</tr>

		</table>
		<table style="float:left; min-width:700px;">
			
			<tr >
				<td align="right"><label style="">付款时间段:</label></td>
				<td><input type="text" name="payTimeStart"  class="easyui-datetimebox" style="width:150px;" />
				&nbsp;~&nbsp;
				<input type="text" name="payTimeEnd" style="float:right;width:150px;" class="easyui-datetimebox" />
				</td>
				<td align="right"><label style="">销售订单编号:</label></td>
				<td><input type="text" name="saleRecordNumber" style="float:right;width:150px;" class="easyui-textbox" /></td>
				<td align="right"><label style="">买家ID:</label></td>
				<td><input type="text" name="buyerId" style="float:right;width:150px;" class="easyui-textbox" /></td>
				
			</tr>
			<tr style="min-width:1000px;">
				<td align="right"><label style="">发货时间段:</label></td>
				<td><input type="text" name="shipTimeStart"  class="easyui-datetimebox"  style="width:150px;"/>
				&nbsp;~&nbsp;
				<input type="text" name="shipTimeEnd" style="float:right;width:150px;" class="easyui-datetimebox"  /></td>
				
				<td align="right"><label style="">订单号:</label></td>
				<td><input type="text" name="orderId" style="float:right;width:150px;" class="easyui-textbox" /></td>
				<td align="right"><label style="">订单状态:</label></td>
				<td>
					<select class="easyui-combobox" name="orderStatus" value="" style="width:150px;" >
						<option ></option>
						<option value="Active">Active</option>
						<option value="Inactive">Inactive</option>
						<option value="Cancelled">Cancelled</option>
						<option value="Completed">Completed</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right"><label style="">订单发起时间段:</label></td>
				<td><input type="text" name="orderTimeStart"  class="easyui-datetimebox" style="width:150px;" />
				&nbsp;~&nbsp;
				<input type="text" name="orderTimeEnd" style="float:right;width:150px;" class="easyui-datetimebox"/></td>
				<td align="right"><label style="">eBay账号:</label></td>
				<td>
					<input type="text" style="width:150px;" class="easyui-combobox"  name="account" data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getAccountList'"/>
				</td>
				<td align="right"><label style="">站点:</label></td>
				<td>
					<select class="easyui-combobox" name="site" value="" style="width:150px;" >
						<option ></option>
						<option value="US">US</option>
						<option value="UK">UK</option>
						<option value="Germany">Germany</option>
						<option value="France">France</option>
						<option value="Italy">Italy</option>
						<option value="eBayMotors">eBayMotors</option>
						<option value="Australia">Australia</option>
					</select>
				</td>
				<td>
					<div style="clear:both;text-align: left;width:250px;line-height: 25px;">
							<a  href="javascript:void(0);" id="viewMoreSearchButton" style="float:right;padding-left: 10px;">高级搜索</a>
					    	<a  href="javascript:void(0);" id="saleListReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="saleListSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
							
							
					</div>
				</td>
			
			</tr>
			<tr style="min-width:1000px;">
				<td colspan="9">
					<ul class="con-button" style="text-align: center;float: left;margin:0;padding:0; cursor: pointer;" id="conButtonList">
						<li style="background:#ccc; list-style: none;border:1px solid #95b8e7;float: left;height: 26px;line-height: 26px;padding: 0 5px;">
							<input type="radio" name="orderAllStatus" id="total" value="0" style="display: none;" checked="checked"/>
							<label for="total">所有</span></label>
							
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="orderAllStatus" id="paid" value="1" style="display: none;"/>
							<label for="paid">未付款<span></span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="orderAllStatus" id="noPaid" value="2" style="display: none;" />
							<label for="noPaid">已付款<span></span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="orderAllStatus" id="noShipped" value="3" style="display: none;"/>
							<label for="noShipped">未发货<span></span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="orderAllStatus" id="shipped" value="4" style="display: none;"/>
							<label for="shipped">已发货<span></span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="orderAllStatus" id="noItem" value="5" style="display: none;"/>
							<label for="noItem">缺货<span></span></label>
						</li>
					</ul>
				</td>
				</tr>
		</table>
		<br clear="all"/>
		</form>
		<div>
		<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-update" id="syncOrderList" plain="true">同步订单</a>
		<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="addUserCaseMessage" plain="true">发送消息</a>
		<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-import" id="addMore" plain="true">批量上传跟踪号</a>
		<!-- <a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="addMoreTempDownload" plain="true">上传跟踪号模板下载</a> -->
		<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="noShipOrderOutPutDE" plain="true">未发货订单导出(DE)</a>
		<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="noShipOrderOutPutUK" plain="true">未发货订单导出(UK)</a>
		<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="osNoShipOrderOutPutUS" plain="true">未发货OS订单导出(US)</a>
		<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="smallPKNoShipOrderOutput" plain="true">未发货小包订单导出</a>
		<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" id="setBill" plain="true">发送账单</a>
		<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" id="shippingMark" plain="true">标记发货</a>
		<a href="javascript:void(0)" class="easyui-menubutton" menu="#mm1" iconCls="icon-edit">移动</a>
		<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="shippingUploadRecord" plain="true">上传结果</a>
		</div>
	</div>
	<table id="syncSaleInfoTable" style="width:100%;height:90%" >
	</table>
	<div id="fileUpload" class="easyui-dialog" title="文件上传" style="width:400px;height:215px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	    <form id="ImportForm" enctype="multipart/form-data" method="POST">
		    <div style="margin-bottom:20px;width:80%;padding-left: 35px;margin-top: 40px;">
				<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">站点:</div>
				<select class="easyui-combobox" id="importSite" panelHeight="auto" style="width: 120px;" editable="false">
					<option value="UK">UK</option>
					<option value="DE">DE</option>
					<option value="OS">OS</option>
					<option value="smallPackage">小包</option>
				</select>
				<br clear="all"/>
				<br/>
				<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">File:</div>
				<input id="fileUploadInput" name="file" data-options="prompt:'Choose a file...'" style="width:80%">
			</div>
			<div style="width:80%;padding-left: 35px;">
				<a href="javascript:void(0);"  id="uploadSubmit" class="easyui-linkbutton" style="width:100%">Upload</a>
			</div>
		</form>
	</div>
	<div id="saleTranImportMessageWin" class="easyui-dialog" title="导入结果" style="width:400px;height:200px;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	   <div>
	   		<ul id="saleTranImportErrorList"></ul>
	   </div>
	</div>
	<div id="saleTranNumberUpload" class="easyui-dialog" title="上传运输号" style="width:800px;height:200px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	    <table id="saleTranNumberUploadTable" class="easyui-datagrid" style="width:100%;height:100%"></table>
	</div>
	<div id="saleCancelOrderWin" class="easyui-dialog" title="取消订单" style="width:400px;height:150px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	    <div style="padding-top: 15px;padding-left: 5px;">
	    <label>取消原因：</label>
	    <select class="easyui-combobox" id="orderCancelCause" style="width:300px;">
			<option value="ADDRESS_ISSUE">Something was wrong with the buyer's shipping address.(买家提供的地址错误)</option>
			<option value="BUYER_ASKED_CANCEL">Buyer asked to cancel the order.(买家要求取消订单)</option>
			<option value="OUT_OF_STOCK_OR_CANNOT_FULFILL">I'm out of stock or the item is damaged.(缺货或产品有缺陷)</option>
		</select>
	    </div>
	    
	</div>
	<div id="saleDetial" class="easyui-dialog" title="订单详情" closed="true" style="width:1000px;height:auto;padding-bottom:50px;" 
		data-options="modal:true,buttons: [{
					text:'关闭',
					handler:function(){
						$('#saleDetial').window('close');
					}
				}]">
		<div class="transadress" style="width:500px; height:200px;border-right:1px solid #ccc; float:left;clear:both;">
			<div style="padding:0 30px;float:left;"><p style="font-weight:300;">运输地址</p></div>
			<div style="padding:10px;float:left;line-height:20px;">
				<span id="t_Name"></span><br/>
				<span id="t_Street1"></span>&nbsp;
				<span id="t_Street2"></span><br/>
				<span id="t_CityName"></span><br/>
				<span id="t_StateOrProvnce"></span><br/>
				<span id="t_posttalCode"></span><br/>
				<span id="t_Country"></span><br/>
				<span id="t_Phone"></span><br/>
				<span id="t_email"></span>
				
			</div>
		</div>
		<div class="baseInfomation" style="display:inline-block; width:400px;height:200px; padding:0px 30px 30px 30px;float:left;">
			<table style="padding:10px 30px 30px 30px;">
					<tr>
						<td height="20" width="50">订单号</td>
						<td id="d_OrderID">OrderID</td>
					</tr>
					<tr>
						<td height="20"  width="50">销售号</td>
						<td id="d_SalesRecoreNumber">SalesRecoreNumber</td>
					</tr>
					<tr>
						<td height="20"  width="50">买家ID</td>
						<td id="d_BuyerUserID">BuyerUserID</td>
					</tr>
					<tr>
						<td height="20"  width="50">卖家ID</td>
						<td id="d_SellerUserID">SellerUserID</td>
					</tr>
					<tr>
						<td height="20"  width="50">Paypal交易号</td>
						<td id="d_paypalTranNum"></td>
					</tr>
			</table>
		</div>
		 <table id="saleDetailTable" class="easyui-datagrid" style="width:100%;height:auto;">
			
		 </table>
		 <div class="total"  style="padding:10px;padding-right:40px;float:right;font-weight:300;">合计:&nbsp<span id="t_total" style="">0</span></div>
		
	</div>
	
	<div id="addUserMessageWin" class="easyui-dialog" title="发送消息" style="width:500px;height:480px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#addUserMessageButton'">
	    <div  class="easyui-layout" data-options="fit:true">
		    <div data-options="region:'north',split:false" style="height:65%;">
		    	<div style="width:97%;margin:10px auto;" >
			    	<form id="addUserMessageFrm">
			    		<div style="width:100%;margin-top:10px;" >
			    			<div style="padding-bottom:10px;">
				    		<label style="padding-right: 13px;">&nbsp;&nbsp;&nbsp;物品ID：</label>
				    		<input id="itemChoose"  style="width:70%;" name="itemId" />
				    		</div>
				    		<div style="padding-bottom:10px;">
				    		<label style="padding-right: 13px;">消息分类：</label>
				    		<select class="easyui-combobox" name="questionType" style="width:70%;">
								<option value="General">一般正常</option>
								<option value="None">无</option>
								<option value="CustomizedSubject">自定义</option>
								<option value="MultipleItemShipping">多个包裹</option>
								<option value="Payment">付款</option>
								<option value="Shipping">运送</option>
							</select>
				    		</div>
				    		<div style="padding-bottom:10px;">
				    			<label style="padding-right: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;主题：</label>
				    			<input class="easyui-textbox"  name="subject" data-options="" style="width:70%;"  />
				    		</div>
				    		<div>
				    			<label style="padding-right: 13px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;内容：</label>
				    			<input class="easyui-textbox"  name="bodyContent" data-options="multiline:true" style="width:70%;height:128px"  />
				    		</div>
				    	</div>
			    	</form>
			    </div>
		    </div>
		    <div data-options="region:'south',title:'图片',split:false,collapsible:false" style="height:35%;">
		    		<ul style="position: absolute;margin:0;top: -1px;left: 34px;padding-left: 0px;" id="userCaseMessageImgUl">
		    			<li id="addImgButton" style="float:left;list-style:none;margin-top:1px;cursor:pointer;margin-left: 3px;">
		    				<div id='div1' style="position:relative;padding: 0 10px;border: 1px solid #b7c6df;padding-top: 1px;">
                                <img style="width: 22px;" src='/ocs/assets/app/images/synchronou/add-image-icon.png' alt="添加图片" title="添加图片">
                    		</div>
		    			</li>
		    			
		    		</ul>
		    </div>
    	</div>
	    
	    <div style="width: 100%;text-align:right;" id="addUserMessageButton">
	    	<a  href="javascript:void(0);" id="addUserMessageOk" class="easyui-linkbutton" iconCls="icon-ok"  >发送</a>
			<a  href="javascript:void(0);" id="addUserMessageCancel" class="easyui-linkbutton" iconCls="icon-clear" >取消</a>
		</div>
	</div>
	<div id="addUserMessageAddImgWin" class="easyui-dialog" title="添加图片" style="width:400px;height:280px;display:none;"data-options="iconCls:'icon-add',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#addUserMessageAddImgButton'">
	    <div  class="easyui-layout" data-options="fit:true">
		    <div style="width:97%;margin:10px 12px;" >
			    	<div style="padding-bottom:10px;">
		    		<label style="padding-right: 13px;">图片名称：</label>
		    		<input  class="easyui-textbox" style="width:70%;" id="addMessageimageName" data-options=""/>
		    		</div>
		    		<div style="padding-bottom:10px;">
		    		<label style="padding-right:16px;">图片URL：</label>
		    		<input  class="easyui-textbox" style="width:70%;" id="addMessageimageUrl" data-options=""/>
		    		</div>
			</div>
    	</div>
	    <div style="width: 100%;text-align:right;" id="addUserMessageAddImgButton">
	    	<a  href="javascript:void(0);" id="addUserMessageAddImgOk" class="easyui-linkbutton" iconCls="icon-ok"  >确定</a>
			<a  href="javascript:void(0);" id="addUserMessageAddImgCancel" class="easyui-linkbutton" iconCls="icon-clear" >取消</a>
		</div>
	</div>
	
<div id="saleOrderRefundToolbar" style="height: 60px">
	<div>
		<form id="saleOrderRefundBeforeForm">
			<table style="float:left; min-width:800px;">
				<tr style="min-width: 800px;">
					<td><label>平台:</label></td>
					<td>
						<input type="text" name="platform" id="platform_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>账号:</label></td>
					<td>
						<input type="text" name="account" id="account_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;"/>
					</td>
					<td id="Site1"><label>站点:</label></td>
					<td id="Site2">
						<input type="text" name="site" id="site_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;"/>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>订单号:</label></td>
					<td>
						<input type="text" name="orderId" id="orderId_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>货币单位:</label></td>
					<td>
						<input type="text" name="currencyCode" id="currencyCode_" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div id="saleOrderRefund" class="easyui-dialog" title="退款退货单" closed="true" style="width:900px;height:400px;" 
		data-options="modal:true">
		<table id="saleOrderRefundDatagrid" class="easyui-datagrid" style="width:100%;height:230px">
			
		</table>
	<div style="height: 90px">
		<form id="saleOrderRefundBackForm">
			<input type="text" hidden="true" class="easyui-textbox" name="id" id="id_">
			<table style="float:left; min-width:800px;">
				<tr style="min-width: 800px;">
					<td><label>退款原因:</label></td>
					<td>
						<select name="cause" id="cause_" class="easyui-combobox" required="true" style="width: 150px;" panelMaxHeight="100" panelHeight="auto">
							<option value="退货退款">退货退款</option>
							<option value="不退货退款">不退货退款</option>
							<option value="未发货退款">未发货退款</option>
						</select>
					</td>
					<td><label>是否退还运费:</label></td>
					<td>
						<select name="isConsumerPaid" id="isConsumerPaid_" required="true" class="easyui-combobox" style="width: 150px;" panelHeight="auto" editable="false">
							<option value="0" >否</option>
							<option value="1" >是</option>
						</select>
						<span style="color:red;padding-left: 10px;" id="shipCost">0</span>
					</td>
					<td id="trackingNum1"><label>退款跟踪号:</label></td>
					<td id="trackingNum2">
						<input type="text" name="trackingNum" id="trackingNum_" class="easyui-textbox" style="width: 150px;" />
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>RMA单号:</label></td>
					<td>
						<input type="text" name="edaOrderNum" id="edaOrderNum_" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td id="adjustmentPositive1"><label>调解费用:</label></td>
					<td id="adjustmentPositive2">
						<input type="text" name="adjustmentPositive" id="adjustmentPositive_" required="true" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td id="approveDescription1"><label>审核未通过理由:</label></td>
					<td id="approveDescription2">
						<input type="text" name="approveDescription" id="approveDescription_" required="true" class="easyui-textbox" style="width: 150px;" />
					</td>
					<td id="receiptNo1"><label>入库单号:</label></td>
					<td id="receiptNo2">
						<input type="text" name="receiptNo" id="receiptNo_" class="easyui-textbox" style="width: 150px;" />
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td id="receivingTime1"><label>收货日期:</label></td>
					<td id="receivingTime2">
						<input type="text" name="receivingTime" id="receivingTime_" class="easyui-datetimebox" style="width: 150px;" editable="false" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div id="saleOrderReissueToolbar" style="height: 60px">
	<div>
		<form id="saleOrderReissueBeforeForm">
			<table style="float:left; min-width:800px;">
				<tr style="min-width: 800px;">
					<td><label>平台:</label></td>
					<td>
						<input type="text" name="platform" id="platform__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>账号:</label></td>
					<td>
						<input type="text" name="account" id="account__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;"/>
					</td>
					<td id="Site3"><label>站点:</label></td>
					<td id="Site4">
						<input type="text" name="site" id="site__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;"/>
					</td>
				</tr>
				<tr style="min-width: 800px;">
					<td><label>订单号:</label></td>
					<td>
						<input type="text" name="orderId" id="orderId__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
					<td><label>货币单位:</label></td>
					<td>
						<input type="text" name="currencyCode" id="currencyCode__" class="easyui-textbox" data-options="disabled:'true'" style="width: 150px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div id="saleOrderReissue" class="easyui-dialog" title="补发单" closed="true" style="width:900px;height:400px;" 
		data-options="modal:true">
		<table id="saleOrderReissueDatagrid" class="easyui-datagrid" style="width:100%;height:180px;overflow:auto;margin-left: 20px;">
			
		</table>
	<div style="height: 120px">
		<form id="saleOrderReissueBackForm">
			<input type="text" hidden="true" class="easyui-textbox"  name="id" id="id__">
			<table style="float:left; min-width:560px;padding-left: 35px;padding: 10px;">
				<tr>
					<td><label>补发原因:</label></td>
					<td>
						<input type="text" name="cause" required="true" id="cause__" class="easyui-textbox" style="width: 130px;"/>
					</td>
					<td><label>发货仓库:</label></td>
					<td>
						<select name="deliveryWarehouse" id="deliveryWarehouse__" required="true" class="easyui-combobox" style="width: 130px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="US03">美东仓</option>
							<option value="US02">美西仓</option>
							<option value="DE02">DE02</option>
							<option value="UK02">UK02</option>
							<option value="FBA">FBA</option>
						</select>
					</td>
					<td id="approveDescription3"><label>审核未通过理由:</label></td>
					<td id="approveDescription4">
						<input type="text" name="approveDescription" id="approveDescription__" required="true" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td id="trackingNum3"><label>补发跟踪号:</label></td>
					<td id="trackingNum4">
						<input type="text" name="trackingNum" id="trackingNum__" class="easyui-textbox" style="width: 130px;" />
					</td>
				</tr>
				<tr>
					<td><label>是否补发到新地址:</label></td>
					<td>
						<select id="newAddress" required="true" class="easyui-combobox" style="width: 130px;" panelHeight="auto" editable="false">
							<option value="0" >否</option>
							<option value="1" >是</option>
						</select>
					</td>
				</tr>
				<tr id="newAddress1">
					<td><label>收货人:</label></td>
					<td>
						<input type="text" id="name_" required="true" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>邮编:</label></td>
					<td>
						<input type="text" id="postalCode_" required="true" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>电话:</label></td>
					<td>
						<input type="text" id="phone_" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>国家:</label></td>
					<td>
						<!--<input type="text" id="country_" class="easyui-textbox" style="width: 130px;" />-->
						<select  id="country_" required="true" class="easyui-combobox" style="width: 130px;" panelMaxHeight="100" panelHeight="auto" editable="false">
							<option value=""></option>
							<option value="US">美国</option>
							<option value="DE">德国</option>
							<option value="UK">英国</option>
							<option value="FR">法国</option>
							<option value="IT">意大利</option>
							<option value="ES">西班牙</option>
							<option value="CA">加拿大</option>
							<option value="JP">日本</option>
							<option value="AT">奥地利</option>
							<option value="IE">爱尔兰</option>
							<option value="BE">比利时</option>
							<option value="EE">爱沙尼亚</option>
							<option value="BG">保加利亚</option>
							<option value="PL">波兰</option>
							<option value="DK">丹麦</option>
							<option value="FI">芬兰</option>
							<option value="NL">荷兰</option>
							<option value="CZ">捷克</option>
							<option value="HR">克罗地亚</option>
							<option value="LV">拉脱维亚</option>
							<option value="LT">立陶宛</option>
							<option value="LU">卢森堡</option>
							<option value="RO">罗马尼亚</option>
							<option value="MT">马耳他</option>
							<option value="PT">葡萄牙</option>
							<option value="SE">瑞典</option>
							<option value="CY">塞浦路斯</option>
							<option value="SK">斯洛伐克</option>
							<option value="SI">斯洛文尼亚</option>
							<option value="GR">希腊</option>
							<option value="HU">匈牙利</option>
							<option value="IS">冰岛</option>
							<option value="MC">摩纳哥</option>
							<option value="AD">安道尔</option>
							<option value="NO">挪威</option>
							<option value="CH">瑞士</option>
							<option value="LI">列支敦士登</option>
							<option value="AU">澳大利亚</option>
							<option value="IN">印度</option>
							<option value="SA">沙特阿拉伯</option>
							<option value="SG">新加坡</option>
							<option value="ZA">南非</option>
							<option value="KP">韩国</option>
							<option value="TW">中国台湾</option>
							<option value="TR">土耳其</option>
							<option value="IL">以色列</option>
							<option value="NZ">新西兰</option>
						</select>
					</td>
				</tr>
				<tr  id="newAddress2">
					<td><label>城市:</label></td>
					<td>
						<input type="text" id="city_" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>州:</label></td>
					<td>
						<input type="text" id="provState_" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>街道1:</label></td>
					<td>
						<input type="text" id="addressLine1_" class="easyui-textbox" style="width: 130px;" />
					</td>
					<td><label>街道2:</label></td>
					<td>
						<input type="text" id="addressLine2_" class="easyui-textbox" style="width: 130px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>

<div id="sendMessageResult" class="easyui-dialog" title="发送结果" style="width:800px;height:600px;"  data-options="iconCls:'icon-save',resizable:true,closed:true,modal:true,
    buttons:[{
				text:'关闭',
				handler:function(){
					$('#addUserMessageWin').dialog('close');
					$('#addUserMessageFrm').form('clear');
					$('#userCaseMessageImgUl li').each(function(){
			    		if($(this).attr('id') != 'addImgButton'){
			    			$(this).remove();
			    		}
			    	});
			    	$('#sendMessageResult').dialog('close');
				}
			}]">
	 <table id="sendMessageResultListGrid"  class="easyui-datagrid" ></table>  
</div>
<div id="mm1" style="width:150px;">
	<div iconCls="icon-redo"  id="remarkNoItem">标记缺货</div>
	<div iconCls="icon-undo" id="unRemarkNoItem">取消标记缺货</div>
	<div class="menu-sep"></div>
	<div iconCls="icon-edit" id="editLabel">编辑标签</div>
</div>

<div id="orderLabelOptMenu" class="easyui-menu" style="width:120px;">
	<div iconCls="icon-undo" labelValue="0" >取消标签</div>
	<div class="menu-sep"></div>
</div>

<div id="ebayOrderRemark" class="easyui-dialog" title="备注" style="width:500px;height:300px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#text-button'" >
	<div style="width:97%;margin:10px auto;" >
		<input class="easyui-textbox" style="width:100%;height:200px" data-options="multiline:true" id="orderRemark" />
	</div>
	<div id="text-button" style="width: 100%;text-align:right;">
		<a  href="javascript:void(0);" id="ebayOrderRemarkOK" class="easyui-linkbutton c8" iconCls="icon-ok"  >确定</a>
		<a  href="javascript:void(0);" id="ebayOrderRemarkCancel" class="easyui-linkbutton" iconCls="icon-clear" >关闭</a>
	</div>
</div>
<div id="ebayShipUploadRecord" class="easyui-dialog" title="上传记录" style="width:830px;height:500px;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#ebayShipUpload-button'" >

		<table id="ebayShipUploadRecordDatagrid" class="easyui-datagrid" style="width:100%;height:90%">
		</table>


</div>
<div id="ebayShipUpload-button" style="width: 100%;text-align:right;">
	<a  href="javascript:void(0);" id="ebayShipUploadRecordCancel" class="easyui-linkbutton" iconCls="icon-clear" >关闭</a>
</div>

<div id="ebayLabelListWin" class="easyui-dialog" title="标签" style="width:530px;height:400px;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#ebayLabelList-button'" >
		<table id="ebayLabelListDatagrid" class="easyui-datagrid" style="width:100%;height:100%">
		</table>
</div>
<div id="ebayLabelList-button" style="width: 100%;text-align:right;">
	<a  href="javascript:void(0);" id="ebayLabelListClose" class="easyui-linkbutton" iconCls="icon-clear" >关闭</a>
</div>

<div id="ebayLabelAddWin" class="easyui-dialog" title="标签" style="width:230px;height:200px;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true,buttons:'#ebayLabelAdd-button'" >
		<div style="padding-top:30px;padding-left:13px">
			<input type="text" id="labelName" class="easyui-textbox" style="width: 200px;" />
		</div>
</div>
<div id="ebayLabelAdd-button" style="width: 100%;text-align:right;">
	<a  href="javascript:void(0);" id="ebayLabelAddOK" class="easyui-linkbutton" iconCls="icon-ok" >确定</a>
	<a  href="javascript:void(0);" id="ebayLabelAddClose" class="easyui-linkbutton" iconCls="icon-clear" >关闭</a>
</div>
</@FTL.admin>