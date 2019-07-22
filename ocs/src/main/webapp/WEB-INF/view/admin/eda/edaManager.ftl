<@FTL.admin id="edaManager" title="eda发货管理"
add_script_files=['admin/eda/edaManager.js']>
<div class="easyui-panel" id="edaManagerSearchParam-panel" >
		<form id="edaManagerSearchParam">
		<table style="float:left; min-width:1000px;padding:10px;">
			
			<tr style="min-width:1000px;">
				<td><label style="">EDA平台发货单ID:</label></td>
				<td><input type="text" name="edaPlatformOrderId"  class="easyui-textbox" />
				</td>
				<td><label style="">销售订单ID:</label></td>
				<td><input type="text" name="orderId"  class="easyui-textbox" />
				</td>
				<td><label style="">EDA订单创建时间:</label></td>
				<td ><input type="text" name="timeStart"  class="easyui-datebox" style="width:150px;" />
					&nbsp;~&nbsp;
					<input type="text" name="timeEnd" style="float:right;width:150px;" class="easyui-datebox" />
				</td>
				<td>
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="edaManagerReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="edaManagerSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		<br clear="both"/>
		</form>
		<div>
	     	<a id ="addEDAShipOrder" href="javascript:void(0);"  class="easyui-linkbutton" iconCls = "icon-add" plain="true">创建发货单</a>
	     	<a id ="modifyAddressBtn" href="javascript:void(0);"  class="easyui-linkbutton" iconCls = "icon-edit" plain="true">修改发货地址</a>
	     	
	     	<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="noShipOrderOutByEast" plain="true">美东仓未发货导出</a>
	     	<a  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-download" id="timeExport" plain="true">按照条件全部导出</a>
		</div>
	</div>
<table id="edaManagerList"  style="width:100%;height:100%" class="easyui-datagrid">        
</table>

<div id="edaShippingAddressModifyWin" class="easyui-dialog" style="width:500px" title="编辑"
            closed="true" buttons="#dlg-buttons" data-options="modal:true">
        <form id="addressModifyfm" method="post" novalidate style="margin:0;padding:20px 50px">
			<div>
				<table cellpadding="5">
		    		<tr>
		    			<td>收件人:</td>
		    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>地址一:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressLine1" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>地址二:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressLine2" data-options="" style="width:250px;"></input></td>
		    		</tr>
		    
		    		<tr>
		    			<td>城市:</td>
		    			<td><input class="easyui-textbox" type="text" name="city" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>省:</td>
		    			<td><input class="easyui-textbox" type="text" name="provState" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>国家:</td>
		    			<td><input class="easyui-textbox" type="text" name="country" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		
		    		<tr>
		    			<td>邮编:</td>
		    			<td><input class="easyui-textbox" type="text" name="postalCode" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>电话:</td>
		    			<td><input class="easyui-textbox" type="text" name="phone" data-options="" style="width:250px;"></input></td>
		    		</tr>
		    		
	    		</table>
			</div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="edaManager.modifyAddress()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#edaShippingAddressModifyWin').dialog('close')" style="width:90px">关闭</a>
    </div>
    
    <div id="addEdaOrderWin" class="easyui-dialog" style="width:800px" title="添加发货单"
            closed="true" buttons="#addEdaOrder-buttons" data-options="modal:true">
         <div>
         	<div class="easyui-panel" id="orderSkuList"  style="height:40px" >
         		<div style="margin-top: 5px;margin-left:200px;">
         		<form id="orderSearchFrm">
         		<span>
         			订单号:&nbsp;&nbsp;<input class="easyui-textbox" type="text" name="orderId" data-options="" style="width:250px;"></input>
         		</span>
         		<span><a  href="javascript:void(0);" id="orderSearch" class="easyui-linkbutton" iconCls="icon-search" style="padding-left:15px;">搜索</a></span>
         		</form>
         		</div>
         	</div>
         	 <table id="skuListGrid"  class="easyui-datagrid"></table> 
         	 
         </div>
        <form id="addEdaOrderfm" method="post" novalidate style="margin:0;padding:20px 50px">
			<div>
				<table cellpadding="5">
		    		<tr>
		    			<td>收件人:</td>
		    			<td><input class="easyui-textbox" type="text" name="name" data-options="required:true" style="width:250px;"></input></td>

		    			<td>电话:</td>
		    			<td><input class="easyui-textbox" type="text" name="phone" data-options="" style="width:250px;"></input></td>
		    		</tr>
	
		    		<tr>
		    			<td>地址一:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressLine1" data-options="required:true" style="width:250px;"></input></td>
		    			<td>地址二:</td>
		    			<td><input class="easyui-textbox" type="text" name="addressLine2" data-options="" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>城市:</td>
		    			<td><input class="easyui-textbox" type="text" name="city" data-options="required:true" style="width:250px;"></input></td>
		    			<td>省:</td>
		    			<td><input class="easyui-textbox" type="text" name="provState" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
		    		<tr>
		    			<td>国家:</td>
		    			<td><input class="easyui-textbox" type="text" name="country" data-options="required:true" style="width:250px;"></input></td>
		    			<td>邮编:</td>
		    			<td><input class="easyui-textbox" type="text" name="postalCode" data-options="required:true" style="width:250px;"></input></td>
		    		</tr>
	    		</table>
			</div>
        </form>
    </div>
    <div id="addEdaOrder-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="edaManager.addEdaOrderOk()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addEdaOrderWin').dialog('close')" style="width:90px">关闭</a>
    </div>
</@FTL.admin>
