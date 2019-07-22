<@FTL.admin id="buyerRequire" title="买家要求" add_script_files=['admin/seller/buyerRequire.js']>
<table id="buyerTable"  class="easyui-datagrid" style="width:100%;height:100%">
</table>
    <div id="buyerDialog" class="easyui-dialog" style="width:500px"
            closed="true" buttons="#dlg-buttons">
        <form id="buyerfm" method="post" novalidate style="margin:0;padding:20px 50px">
				<div>
					<div style="margin-bottom:10px">
						<input style="display:none" type="easyui-textbox" name="id" id="buyerId">
                		<input class="easyui-textbox" id="title" name="name" style="width:90%" label="名称：">
            		</div>
					<div>
						<div style="margin-bottom:10px">
							<input type="text"  class="easyui-combobox" id="siteId" name="ddleBaySiteID" label="站点："   style="width:90%"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/>
						</div>
					</div>
					<div>
						<div width="30%">
							<label>买家要求：</label>
						</div>
						<div style="padding:10px">
            				
            				<input type="radio" name="allowAllBuyer" value="true" checked id="rbtnBuyerRequirementSpecified1" onclick="javascript:$('#panel').panel('close')">
            				<span>允许所有买家购买我的物品</span>
            				
            				<br/>
            				
            				<input type="radio" onclick="javascript:$('#panel').panel('open')" value="false" name="allowAllBuyer" id="rbtnBuyerRequirementSpecified2">
            				<span>不允许以下买家购买我的物品</span>
            				<br/>
            			</div>
							<div id="panel" class="easyui-panel" style="display: none;">
								<div name="payPalbox">
									<input id="isHavePaypalAccount" type="checkbox" name="payPal"> 没有 PayPal 账户
								</div>
								<br>
								<div name="transportDiv">
									<input id="isOffscale" type="checkbox" name="transportScope"> 主要运送地址在我的运送范围之外
								</div>
								<br>
								<div name="caseDiv">
								    <input id="chkMaxUnpaidItemStrikesInfoSpecified" type="checkbox" name="case1" >曾收到
								    <select  id="ddlMaxUnpaidItemStrikesInfoCount"  style="width:8%" disabled name="case11">
									    <option value="2" selected>2</option>
									    <option value="3">3</option>
									    <option value="4">4</option>
									    <option value="5">5</option>
								    </select>个弃标个案，在过去
								    <select  id="ddlMaxUnpaidItemStrikesInfoPeriod" style="width:12%" disabled name="case12">
									    <option value="Days_30" selected>30</option>
									    <option value="Days_180">180</option>
									    <option value="Days_360">360</option>
								    </select>天
								</div>
								<br>
								<div name="noCaseDiv">
									<input id="chkMaxBuyerPolicyViolationsSpecified" type="checkbox" name="case2" >曾收到
									<select  id="ddlMaxBuyerPolicyViolationsCount"  style="width:8%" disabled name="case21">
										<option value="4" checked="checked">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
									</select>个违反政策检举，在过去
									<select  id="ddlMaxBuyerPolicyViolationsPeriod" style="width:12%" disabled name="case22">
										<option value="Days_30" checked="checked">30</option>
										<option value="Days_180">180</option>
									</select>天
								</div>
								<br>
								<div name="cDiv">
									<input id="chkMinFeedbackScoreSpecified" type="checkbox" name="case3" >信用指标等于或低于
									<select  id="ddlMinFeedbackScore" style="width:8%" disabled name="case31" >
										<option value="-1" selected>-1</option>
										<option value="-2">-2</option>
										<option value="-3">-3</option>
									</select>
								</div>
								<br>
								<div name="cOndiv">
									<input id="chkMaxItemRequirementsMaxItemCountSpecified" type="checkbox" name="case4" >在过去10天内曾出价或购买我的物品，已达到我所设定的限制
									<select  id="ddlMaxItemRequirementsMaxItemCount" labelPosition="top" style="width:12%" disabled name="case41">
										<option value="1" selected>1</option>
										<option value="2">2</option>
										<option value="3">3</option>
										<option value="4">4</option>
										<option value="5">5</option>
										<option value="6">6</option>
										<option value="7">7</option>
										<option value="8">8</option>
										<option value="9">9</option>
										<option value="10">10</option>
										<option value="25">25</option>
										<option value="50">50</option>
										<option value="75">75</option>
										<option value="100">100</option>
									</select>
								</div>
								<br>
								<div name="caseD">
									<input  id="chkMaxItemRequirementsMinFeedbackScoreSpecified"  type="checkbox" name="case5" > 这项限制只适用于买家信用指数等于或低于
									<select  id="ddlMaxItemRequirementsMinFeedbackScore" style="width:8%" disabled  name="case51">
										<option value="5" selected>5</option>
										<option value="4">4</option>
										<option value="3">3</option>
										<option value="2">2</option>
										<option value="1">1</option>
										<option value="0">0</option>
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="saveBuyerRequire()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#buyerDialog').dialog('close')" style="width:90px">关闭</a>
    </div>

</@FTL.admin>