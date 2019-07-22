<@FTL.admin id="orderAndOrderItem" title="MySql=>Orable数据同步" add_script_files=['admin/task/orderAndItem.js']>
	<div id="lightOrderOrItem" class="easyui-panel" title="light订单数据同步"   style="width:100%;height:150px;padding:15px;background:#fafafa;"
    data-options="iconCls:'icon-save',closable:true,
    collapsible:true,maximizable:true">
		<form id="lightOrder" method="post" novalidate style="margin:0;padding:20px 50px">
			<div style="padding:5px 0;">
				时间参数：<select class="easyui-combobox" id="dateType" name="dateType"   style="width:120px;">
                	 	<option value="updated_at">updated_at</option>
                	 	<option value="light_created_at">light_created_at</option>
                	 	<option value="light_updated_at">light_updated_at</option>
                	 	<option value="ship_at">ship_at</option>
                	 	<option value="created_at">created_at</option>
                	  </select>
				开始时间: <select class="easyui-combobox" id="startDateOperator" name="startOperator"   style="width:90px;">
                	 	<option value=">">大于</option>
                	 	<option value=">=">大于等于</option>
                	  </select><input class="easyui-datetimebox" name="startDate" data-options="required:true,showSeconds:true"  style="width:150px">
		                 结束时间: <select class="easyui-combobox" id="endDateOperator" name="endOperator"   style="width:90px;">
                	 	<option value="<">小于</option>
                	 	<option value="<=">小于等于</option>
                	  </select><input  class="easyui-datetimebox" name="endDate" data-options="required:true,showSeconds:true"  style="width:150px">
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-shapes',size:'large',iconAlign:'top'" onclick="lightSynchronou()">同步</a>
			</div>
		</form>
	</div>
	<div id="amazonOrderOrItem" class="easyui-panel" title="amazon订单数据同步"   style="width:100%;height:150px;padding:15px;background:#fafafa;"
    data-options="iconCls:'icon-save',closable:true,
    collapsible:true,maximizable:true">
		<form id="amazonOrder" method="post" novalidate style="margin:0;padding:20px 50px">
			<div style="padding:5px 0;">
				时间参数：<select class="easyui-combobox" id="dateType" name="dateType"   style="width:120px;">
                	 	<option value="purchase_at">purchase_at</option>
                	 	<option value="created_at">created_at</option>
                	 	<option value="updated_at">updated_at</option>
                	 	<option value="amazon_updated_at">amazon_updated_at</option>
                	 	<option value="lastest_ship_date">lastest_ship_date</option>
                	 	<option value="lastest_delivery_date">lastest_delivery_date</option>
                	 	<option value="lastest_ship_date">lastest_ship_date</option>
                	  </select>
				开始时间: <select class="easyui-combobox" id="startDateOperator" name="startOperator"   style="width:90px;">
                	 	<option value=">">大于</option>
                	 	<option value=">=">大于等于</option>
                	  </select><input class="easyui-datetimebox" name="startDate" data-options="required:true,showSeconds:true"  style="width:150px">
		                 结束时间: <select class="easyui-combobox" id="endDateOperator" name="endOperator"   style="width:90px;">
                	 	<option value="<">小于</option>
                	 	<option value="<=">小于等于</option>
                	  </select><input  class="easyui-datetimebox" name="endDate" data-options="required:true,showSeconds:true"  style="width:150px">
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-shapes',size:'large',iconAlign:'top'" onclick="amazonSynchronou()">同步</a>
			</div>
		</form>
	</div>
	<div id="ebayOrderOrItem" class="easyui-panel" title="ebay订单数据同步"   style="width:100%;height:150px;padding:15px;background:#fafafa;"
    data-options="iconCls:'icon-save',closable:true,
    collapsible:true,maximizable:true">
		<form id="ebayOrder" method="post" novalidate style="margin:0;padding:20px 50px">
			<div style="padding:5px 0;">
				时间参数：<select class="easyui-combobox" id="dateType" name="dateType"   style="width:120px;">
                	 	
                	 	<option value="last_modified_time">last_modified_time</option>
                	 	<option value="created_time">created_time</option>
                	 	<option value="paid_time">paid_time</option>
                	 	<option value="shipped_time">shipped_time</option>
                	 	<option value="last_fetch_time">last_fetch_time</option>
                	  </select>
				开始时间: <select class="easyui-combobox" id="startDateOperator" name="startOperator"   style="width:90px;">
                	 	<option value=">">大于</option>
                	 	<option value=">=">大于等于</option>
                	  </select><input class="easyui-datetimebox" name="startDate" data-options="required:true,showSeconds:true"  style="width:150px">
		                 结束时间: <select class="easyui-combobox" id="endDateOperator" name="endOperator"   style="width:90px;">
                	 	<option value="<">小于</option>
                	 	<option value="<=">小于等于</option>
                	  </select><input  class="easyui-datetimebox" name="endDate" data-options="required:true,showSeconds:true"  style="width:150px">
		        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-shapes',size:'large',iconAlign:'top'" onclick="ebaySynchronou()">同步</a>
			</div>
		</form>
	</div>
</@FTL.admin>