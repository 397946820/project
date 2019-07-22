<@FTL.admin id="ReturnPolicyList" title="退货政策" add_script_files=['admin/returnpolicy/returnpolicy.js']>
<div data-options="region:'center',border:false">
    <table id="policyDatagrid" class="easyui-datagrid"
           data-options="
           url:'/returnpolicy/list',
           fitColumns:true,
           columns: [
            [
                {field: 'returnPolicyId', title: 'id',hidden:true},
                {field: 'daysAllowed', title: 'id',hidden:true},
                {field: 'allowExtension', title: 'id',hidden:true},
                {field: 'policyType', title: 'id',hidden:true},
                {field: 'refundMethod', title: 'id',hidden:true},
                {field: 'freightCarrier', title: 'id',hidden:true},
                {field: 'depreciationCost', title: 'id',hidden:true},
                {field: 'policyDetail', title: 'id',hidden:true},
                
                {field: 'returnPolicyName', title: '名称' ,width: 70},
                {field: 'ico', title: '站点',formatter:getImage,width: 50},
                {field: 'tuihuo', title: '退货', width: 100,
                	formatter:function(value,row,index){
                		var resoult ='';
                		if(row.policyType=='ReturnsAccepted'){
                			resoult='接受退货';
                			if(row.daysAllowed!=null){
                				resoult+='<br/>退货天数：'+row.daysAllowed+'天';
                			}
                		
                			if(row.freightCarrier!=null){
                				resoult+='<br/>退货运费由谁承担：'+row.freightCarrier;
                			}
                			if(row.refundMethod!=null){
                				resoult+='<br/>退货方式：'+row.refundMethod
                			}
                		}else{
                			resoult='不接受退货';
                		}
                  		return resoult;
                 }
                }
            ]
        ],
        idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 10,
        border:false,
        fit:true,
        toolbar:'#policyToolbar',
        onRowContextMenu:policyCotextMenu">
    </table>
</div>
<div id="policyCotextMenu" class="easyui-menu" style="width:120px;">
    <div id="editePolicyContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removePolicyContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="policyToolbar">
    <a id="policyAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">添加</a>
    <a id="policyEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
    <a id="policyRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
</div>
<div id="policyDialog" class="easyui-dialog" title="退货政策" style="width:800px;height:500px;padding:10px;"
     data-options="modal:true,
     closed:true,
     toolbar: [{
        text:'保存',
        iconCls:'icon-save',
        handler:policyFormSave
    }]">
    <form id="policyForm" action="${FTL.X.global_domain}/returnpolicy/save" method="post">
        <input type="hidden" name="returnPolicyId"/>
        <table>
            <tbody>
            <tr>
                <td>名称：</td>
                <td><input type="text" name="returnPolicyName" id="returnPolicyName" class="easyui-validate"  style="width: 250px"/></td>
            </tr>
            <tr>
				<td>站点：</td>
				<td>
					<select   name="siteId" id="siteSelectes"  style="width:100%">
					</select>
				</td>
				</tr>
            <tr>
            <tr>
				<td>退货政策：</td>
				<td>
					<select   name="policyType" id="policyType" style="width:100%">
						<option value="ReturnsAccepted">Returns Accepted</option>
						<option value="ReturnsNotAccepted">Returns Not Accepted</option>
					</select>
				</td>
			</tr>
            </tbody>
        </table>
        <table id="policyEditer">
        <tbody >
        	<tr>
				<td>退货天数：</td>
				<td>
					<select   name="daysAllowed" style="width:50%">
						<option value="14">14 days</option>
						<option value="30">30 days</option>
						<option value="60">60 days</option>
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="checkbox" value="1" name="allowExtension"><span>提供节假日延期退货至12月31日。</span>
				</td>
			</tr>
			<tr>
				<td>退款方式：</td>
				<td>
					<select   name="refundMethod" style="width:100%">
						<option value="MoneyBack">Money Back</option>
						<option value="Moneybackorreplacement">Money back or replacement (buyer's choice)</option>
						<option value="MoneyBackOrExchange">Money back or exchange (buyer's choice)</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>退货运费由谁负担：</td>
				<td>
					<select   name="freightCarrier" style="width:50%">
						<option value="Buyer">Buyer</option>
						<option value="Seller">Seller</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>折旧费：</td>
				<td>
					<select   name="depreciationCost" style="width:50%">
						<option value="NoRestockingFee">No</option>
						<option value="Percent_10">10%</option>
						<option value="Percent_15">15%</option>
						<option value="Percent_20">20%</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>退货政策详情：</td>
				<td>
					<textarea rows="3" cols="50" name="policyDetail"></textarea>
				</td>
			</tr>
        </tbody>
        </table>
    </form>
</div>

</@FTL.admin>