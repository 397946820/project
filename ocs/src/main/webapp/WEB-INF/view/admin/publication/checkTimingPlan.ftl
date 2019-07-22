<@FTL.admin id="checkTimingPlan" title="刊登监察" add_script_files=['admin/publication/checkTimingPlan.js','admin/ocs/main.js']>
<div data-options="region:'center',border:false">
	<table id="checkTimingPlanTable" class="easyui-datagrid" 
		data-options="
	    url:'${FTL.X.global_domain}/TimingPlan/checkList',
	    fitColumns:true,
	    idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 20,
        fit:true,
        nowrap:false,
        toolbar:'#toolSearch',
        queryParams:{
        	param:{
        		day:'',
                paramName:'',
        		value:''
        	}
        },
        onDblClickRow : doDblClickRow,
        
	    columns:[[
                	{field: 'id', title: 'id',hidden:true},
                	{field: 'publication_info', title: 'publication_info',hidden:true},
                	{field: 'productTitle', title: 'productTitle',hidden:true},
                	{field: 'new_template_id', title: 'new_template_id',hidden:true},
                	{field: 'template_name', title: 'template_name',hidden:true},
                	{field: 'endDate', title: 'endDate',hidden:true},
                	{field: 'ebayProductURL', title: '物品连接',hidden:true},
                	{field: 'itemId', title: '物品Id',formatter:getItemId},
	                {field: 'ebayImages',width:'80px', title: '图片' ,formatter:getEbayImage},
	                {field: 'sku',width:'15%', title: '范本/SKU/标题',formatter:getNameSku},
	                {field: 'startDate',width:'12%', title: '预刊登时间（本地/站点）',formatter:getLocalSiteDate},
	                {field: 'site_id',width:'5%',align:'center', title: '站点',formatter:getSiteImage},
	                {field: 'publicationType',align:'center',width:'5%', sortable:true,title: '刊登类型',formatter:getTypeImage},
	                {field: 'ebayAccount',align:'center',width:'10%', title: 'eBay账户'},
	                {field: 'createDate',align:'center',width:'12%', title: '创建日期'},
	                {field: 'is_success',align:'center',width:'4%', title: '刊登结果',formatter:getIsSuccess},
	                
		]]">
	</table>

</div>
 
<div id="toolSearch" >
<div style="padding:10px;">
	<form id="checkTimingPlanCondition">
			<table style="float:left; min-width:1000px;">
				
				<tr style="min-width:1000px;">
					<td>
						<select class="easyui-combobox" name="paramName" style="width:60px;">
							<option></option>
							<option value="first_site_date">站点</option>
							<option value="first_date">本地</option>
						</select>
						时间：<input type="text" id="startDate" name="startDate" class="easyui-datetimebox" style="width: 160px;"  data-options="validType:'checkDate'" /> &nbsp~&nbsp 
							<input type="text" id="endDate" name="endDate" class="easyui-datetimebox" style="width: 160px;"  data-options="validType:'checkDate'" />
					</td>
					<td >
						<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="checkListReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="cheListSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
						</div>
					</td>
				</tr>
				<tr style="min-width:1000px;">
				<td colspan="9">
					<ul class="con-button" style="text-align: center;float: left;margin:0;padding:0; cursor: pointer;" >
						<li style="background:#ccc; list-style: none;border:1px solid #95b8e7;float: left;height: 26px;line-height: 26px;padding: 0 5px;">
							<input type="radio" name="siteId" id="total" value="" style="display: none;"/>
							<label for="total">全部<span>（20）</span></label>
							
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="siteId" id="0" value="0" style="display: none;"/>
							<label for="0"><div class="icon3 country_size num_0"></div><span>(20)</span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="siteId" id="3" value="3" style="display: none;" />
							<label for="3"><div class="icon3 country_size num_3"></div><span>(20)</span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="siteId" id="77" value="77" style="display: none;"/>
							<label for="77"><div class="icon3 country_size num_77"></div><span>(20)</span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="siteId" id="71" value="71" style="display: none;"/>
							<label for="71"><div class="icon3 country_size num_71"></div><span>(20)</span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="siteId" id="101" value="101" style="display: none;"/>
							<label for="101"><div class="icon3 country_size num_101"></div><span>(20)</span></label>
						</li>
					</ul>
				</td>
				</tr>
			</table>
			<br clear="all"/>
		</form>
	</div>
</div>
</div>
</@FTL.admin>