<@FTL.admin id="preTimingPlan" title="预刊登队列" add_script_files=['admin/publication/preTimingPlan.js','admin/ocs/mainDate.js','admin/ocs/main.js']>
<div data-options="region:'center',border:false">
	<table id="preTimingPlanTable" class="easyui-datagrid" 
		data-options="
	    url:'${FTL.X.global_domain}/TimingPlan/list',
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
        		value:'',
        		pre_id:'',
        		startDate:'',
        		endDate:'',
        		siteId:''
        		
        	}
        },
	    columns:[[
                	{field: 'id', title: 'id',hidden:true},
                	{field: 'template_id', title: 'template_id',hidden:true},
                	{field: 'productTitle', title: 'productTitle',hidden:true},
                	{field: 'template_name', title: 'template_name',hidden:true},
                	{field: 'endDate', title: 'endDate',hidden:true},
	                {field: 'ebayImages',width:'10%', title: '图片' ,formatter:getEbayImage},
	                {field: 'sku',width:'15%', title: '范本/SKU/标题',formatter:getNameSku},
	                {field: 'startDate',width:'15%', title: '预刊登时间（本地/站点）',formatter:getLocalSiteDate},
	                {field: 'site_id',width:'5%', title: '站点',formatter:getSiteImage},
	                {field: 'publicationType',width:'5%', sortable:true,title: '刊登类型',formatter:getTypeImage},
	                {field: 'ebayAccount',width:'10%', title: 'eBay账户'},
	                {field: 'createDate',width:'12%', title: '创建日期'}
		]]">
	</table>

</div>
 
<div id="toolSearch" >
<div style="padding:10px;">
	<form id="preTimingPlanCondition">
			<table style="float:left;min-width:1000px;">
				
				<tr style="min-width:1000px;">
					<td>
						<select class="easyui-combobox" name="paramName" style="width:60px;">
							<option></option>
							<option value="first_site_date">站点</option>
							<option value="first_date">本地</option>
						</select>
						时间：<input type="text" id="startDate" name="startDate" class="easyui-datetimebox" style="width: 160px;"  data-options="validType:'checkDate'" /> &nbsp~&nbsp 
							<input type="text" id="endDate" name="endDate" class="easyui-datetimebox" style="width: 160px;"  data-options="validType:'checkDate'" />
						<a id="timingPlanLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-time"   plain="true">添加定时计划</a>
						<a id="singleTimingPlan" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-time"   plain="true">单个定时计划</a>
					</td>
					
					<td >
						<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="preListReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="preListSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
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
 <div id="timingPlanDialog" class="easyui-dialog" style="width:700px"
            closed="true" buttons="#timingPlan-buttons">
    <form id="timingPlanForm" method="post" novalidate style="margin:0;padding:20px 50px">

			<div style="margin-bottom:10px">
				<input style="display:none" id="timingTemplateId" type="easyui-textbox" name="id">
            	<input style="display:none" id="timingSiteId" type="easyui-textbox" name="timingSiteId">
            	<input style="display:none" id="templateNameId" type="easyui-textbox" name="timingTemplateName">

            </div>
            <div style="margin-bottom:10px">
            <input class="easyui-datetimebox" id="timingPlanDate" name="timingPlanPublicationDate" label="首次刊登时间:"
              data-options="required:true,showSeconds:true" style="width:250px">
            </div>
            <div>
				<div style="margin-bottom:10px">
				间     隔     天    数 : <input type="text" class="easyui-numberbox"  name="mumberOfDays" id="mumberOfDayId" data-options="min:0,precision:0,required:true">
				</div>
			</div>
            <div style="margin-bottom:10px">
					<!-- <input   class="easyui-combobox" id="siteId" name="siteId" label="定时发布站点:"  style="width:400px;"  data-options="disabled:'true',value:'0',valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/> -->
				刊     登       次     数 : <input type="text" class="easyui-numberbox"  name="numberOf" id="mumberOfId" data-options="min:0,precision:0,required:true">
			 </div>
			<div style="margin-bottom:10px">
					<!-- <input   class="easyui-combobox" id="siteId" name="siteId" label="定时发布站点:"  style="width:400px;"  data-options="disabled:'true',value:'0',valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/> -->
				延     时（分钟）: <input type="text" class="easyui-numberbox"  name="delayMinutes" id="delayMinutesId" data-options="min:0,precision:0,required:true">
			</div>
             <div style="margin-bottom:10px">
	             <div style="float: left;">
	                             站 点 时 间:<span id="timingSiteDate" style="color:green;"></span>
	             </div>
	             <div style="float: right;">
	              <a href="javascript:void(0)"   onclick="sequencePublication()" style="width:90px">点击生成序列</a>
	            </div>
            </div>
       
            <div class="datagrid-toolbar" style="margin-top: 40px;"></div>
            <div style="margin-top: 20px;">
               <table id="sequenceDatagrid"  class="easyui-datagrid" 
               data-options="
                data:null,
                 columns:[[
					{field:'templateId',title:'id',width:100,hidden:true}, 
					{field:'name',title:'范本名称',width:'40%'}, 
					{field:'siteId',title:'站点',width:'10%',formatter:getSiteImage},
					{field:'siteDate',title:'预刊登时间（站点）',width:'25%'},
					{field:'localDate',title:'预刊登时间（本地）',width:'25%'}
				]]"></table>  
            </div>
            
        </form>
    </div>
    <div id="timingPlan-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="savePlanPublication()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#timingPlanDialog').dialog('close')" style="width:90px">关闭</a>
    </div>
</@FTL.admin>