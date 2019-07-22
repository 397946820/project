<#import "moudle/baseInfo.ftl" as BASEINFO />
<@FTL.admin id="publicationList" title="范本信息" add_script_files=['admin/publication/moudle/baseInfo.js','admin/customerCenter/metadata.js','admin/publication/publication.js','admin/ocs/main.js','admin/index.js','admin/ocs/mainDate.js','admin/customerCenter/datagrid-cellediting.js']>
<div data-options="region:'center',border:false">
    <table id="publicationDatagrid" 
           data-options="
           url:'${FTL.X.global_domain}/publication/list',
           fitColumns:true,
           columns: [
            [
            	{field: 'variations', title: 'variations',hidden:true},
                {field: 'id', title: 'id',hidden:true},
                {field: 'name', title: 'name',hidden:true},
                {field: 'sku', title: 'sku',hidden:true},
                {field: 'correlation_id', title: 'correlation_id',hidden:true},
                {field:'check', checkbox:true},
                {field: 'reserverPrice', title: 'reserverPrice',hidden:true},
                {field:'buyoutPrice', title: 'buyoutPrice',hidden:true},
                {field:'productSubtitle',title: 'productSubtitle',hidden:true},
                {field: 'productTitle', title: 'mainTitle',hidden:true},
                {field: 'ebayProductURL', title: 'ebayProductURL',hidden:true},
                {field: 'ebayImages',width:'5%', title: '图片',formatter:getEbayImage},
                {field: 'nameOrSku',width:'16%', title: '名称/SKU',formatter:getNameOrSku},
                {field: 'itemId',width:'16%', title: '刊登标题/物品号',formatter:getTitleAndItemNum},
                {field: 'siteId',width:'5%', sortable:true,align : 'center',title: '站点',formatter:getSiteImage},
                {field: 'publicationType',width:'5%',align : 'center', sortable:true,title: '刊登类型',formatter:getTypeImage},
                {field: 'ebayAccount',width:'10%', sortable:true,title: 'eBay 账户'},
                {field: 'price',width:'3%', sortable:true,title: '价格'},
                {field: 'productCount',sortable:true,width:'5%', sortable:true,title: '刊登数量'},
                {field: 'publicationDays',width:'5%',sortable:true,title:'刊登天数'},
                {field: 'productFirstCategoryId',width:'5%',sortable:true, title: 'eBay 分类'},
                 {field: 'pre_count',width:'5%',sortable:true, title: '预刊登' ,formatter:getPreCount},
                 {field: 'correlationCount',width:'5%',sortable:true, title: '在线' ,formatter:getCorrelation},
                 {field: 'c_date',width:'10%',sortable:true, title: '创建日期' }
            ]
        ],
        nowrap:false,
        singleSelect: false,
        rownumbers: true,
        pagination: true,
        pageSize: 20,
        onDblClickRow : function(index,row){
         
   			 window.open(GLOBAL.domain+'/publication/toEdit?id='+row.id+'&conditions=main','_blank');
	    },
        queryParams:{
        	param:{
        		templateName:'',
        		sku :'',
        		itemId:'',
        		productTitle:'',
        		publicationType :'',
        		pubStatus:'',
        		siteId : '',
        		ebayAccount :'',
        		publicationDays:'',
        		labelId:''
        	}
        },
        border:false,
        fit:true,
        toolbar:'#publicationToolbar'">
    </table>
</div>
<!--  {field: 'pushStatus',width:'5%', title: '发布状态',formatter:getPushStatus}, -->
<div id="publicationToolbar">
	<div>
    <a id="publicationAddLinkbutton" href="${FTL.X.global_domain}/publication/toAdd" target="_blank" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">添加</a>
    <a id="publicationEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
     <a id="publicationCopyLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-copy"
       plain="true">复制</a>
    <a id="publicationRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
    <a id="publicationSaveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-Release"  
    plain="true">发布</a>
<a id="publicationCheckCostLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-check"  
    plain="true">检查费用</a>
    <a id="publicationTimingLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-time"  
    plain="true">定时刊登</a>
    <a href="javascript:void(0)" class="easyui-menubutton" style="width:70px;height:30px;" data-options="size:'large',iconAlign:'top',plain:false,menu:'#totalOption'">选项</a>
    <a id="publicationTimingPlanLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-time"  
    plain="true">定时刊登计划</a>
     <!--<a id="aLotModify" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">批量更新</a>-->
    <a id="aLotModifyNew" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"  plain="true">批量更新</a>
   	<a id="correlationLinkbuttons" href="javascript:void(0);" onclick="publicationMain.correlationLine()" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">关联</a>
  
    </div>
     <hr>
    <div style="height:95px;">
		<form id="publicationListCondition">
			<table style="float:left; min-width:1000px;">
				<tr style="min-width:1000px;">
					<td><label style="">名称:</label></td>
					<td>
						<input type="text" name="templateName" style="float:right" class="easyui-textbox" />
					</td>
					<td><label style="">SKU:</label></td>
					<td>
						
						<input type="text" name="sku" class="easyui-textbox" />
					</td>
					<td><label style="">物品号:</label></td>
					<td>
						
						<input type="text" name="itemId" class="easyui-textbox" />
					</td>
					<td><label style="">刊登天数:</label>
					 	<select class="easyui-combobox" name="publicationDays" style="width:173px;">
							<option ></option>
							<option value="Days_1">Days_1</option>
							<option value="Days_3">Days_3</option>
							<option value="Days_5">Days_5</option>
							<option value="Days_7">Days_7</option>
							<option value="Days_30">Days_30</option>
							<option value="GTC">GTC</option>
						</select>
					</td>
					
				</tr>
				<tr style="min-width:1000px;">
					<td><label style="">标题:</label></td>
						<td>
							<input type="text" name="productTitle" class="easyui-textbox" />
						</td>
					<td><label style="">刊登类型:</label></td>
					<td>
						<select class="easyui-combobox" name="publicationType" style="width:173px;">
							<option ></option>
							<option value="'Chinese'">拍卖</option>
							<option value="'FixedPriceItem'">固价</option>
							<option value="'FixedPriceItem1'">多属性</option>
							<option value="'FixedPriceItem','FixedPriceItem1'">固价+多属性</option>
						</select>
					</td>
					<td><label style="">eBay 账户:</label></td>
					<td>
						<input type="text"  id="ebayAccount" name="ebayAccount"   style="width:173px;"  />
					 </td>
					 <td>
						标签:<input class="easyui-textbox"  type="text"  id="labelState" name="labelId"   style="width:173px;"  />
					 </td>
					 <td >
						<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="pubListReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="pubListSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
						</div>
					</td>
					
				</tr>
				<tr style="min-width:1000px;">
				<td><label style="">站点:</label></td>
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
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="siteId" id="100" value="100" style="display: none;"/>
							<label for="100"><div class="icon3 country_size num_100"></div><span>(20)</span></label>
						</li>
						<li style="list-style: none;padding:5px;border:1px solid #95b8e7;border-left:none;float: left;border-left:none;">
							<input type="radio" name="siteId" id="15" value="15" style="display: none;"/>
							<label for="15"><div class="icon3 country_size num_15"></div><span>(20)</span></label>
						</li>
					</ul>
				</td>
				</tr>
			</table>
		</form>
		
	</div>
</div>
<div id="totalOption" style="display:none">
        <div onclick="fastEditor()">快速编辑</div>
        <div onclick="attribute.InitEdit()">编辑物品属性  </div>
        <div onclick="publicationMain.cancelTimingPlan()">取消定时计划</div>
</div>
<div id="fastEditorDialog" class="easyui-dialog" closed="true" data-options="iconCls:'icon-save',closable:true,resizable:true,modal:true,
    collapsible:true,maximizable:true" style="height:70%;width:900px;" buttons="#fastEditor-buttons">
	<table id="fastEditorDatagrid" class="easyui-datagrid" data-options="onClickRow: onClickRow" style="height:100%;width:100%"> </table>
</div>
<div id="labelEditorDialog" class="easyui-dialog" closed="true" data-options="iconCls:'icon-save',closable:true,resizable:true,modal:true,
    collapsible:true,maximizable:true" style="height:70%;width:900px;" >
   <table id="lebelEditorTable" style="width:100%;height:90%" >
   </table>
</div>
<div id="editorAttributDialog" class="easyui-dialog" closed="true" data-options="iconCls:'icon-save',closable:true,resizable:true,modal:true,
    collapsible:true,maximizable:true" style="height:70%;width:1000px;" >
   <table id="editorAttributsTable" style="width:100%;height:90%" >
   </table>
</div>
<div id="fastEditor-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="batchUpdates()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#fastEditorDialog').dialog('close')" style="width:90px">关闭</a>
</div>
<div id="copyItemDialog" class="easyui-dialog" style="width:600px" closed="true" closed="true" buttons="#copyItem-buttons">
	  <form id="copyItemForm" method="post" novalidate style="margin:0;padding:20px 50px"> 
			<div style="margin-bottom:10px">
				<input style="display:none" id="copyItemId" type="easyui-textbox" name="id">            	
            </div>
			<td>
				名称:<input type="text" class="easyui-textbox" style="width: 90%;" name="name"  id='copyNameId' />
			</td>
			<!-- <td><input type="checkbox" id="correlationId" value="1">关联</td> -->
       </form>
 </div>
  <div id="copyItem-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="copySubmit()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#copyItemDialog').dialog('close')" style="width:90px">取消</a>
    </div>
<div id="timingDialog" class="easyui-dialog" style="width:600px"
            closed="true" buttons="#dlg-buttons">
        <form id="timingForm" method="post" novalidate style="margin:0;padding:20px 50px">
			<div style="margin-bottom:10px">
				<input style="display:none" id="templateId" type="easyui-textbox" name="id">
<!--             	<input  name="productName" id="productNameId" class="easyui-textbox" label="范本名称:" style="width:400px">
 -->            </div>
            <div style="margin-bottom:10px">
            <input class="easyui-datetimebox" id="timingDate" name="timing_publication_date" label="定时发布时间:"
              data-options="required:true,showSeconds:true" style="width:400px">
            </div>
            <div>
				<div style="margin-bottom:10px">
					<!-- <input   class="easyui-combobox" id="siteId" name="siteId" label="定时发布站点:"  style="width:400px;"  data-options="disabled:'true',value:'0',valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/> -->
				定时发布站点:<span id="siteName" style="color:green;"></span>
				</div>
			</div>
            <div style="margin-bottom:10px">
                <!-- <input id="site_date" class="easyui-datetimebox" name="site_date" label="当前站点时间:"
              data-options="disabled:'true',showSeconds:true" style="width:400px"> -->
                             当前站点时间:<span id="siteDate" style="color:green;"></span>
            </div>
            <div style="margin-bottom:10px">
            	<div class="datagrid-toolbar"></div>
            </div>
            <div style="margin-bottom:10px;color:green">
                <p style="font-size:14px"><b>设置方式：</b></p>
				<ul>
					<li>设定的时间为站点所在地时间，例如美国站点，设定的是美国时间，无需转换；</li>
					<li>定时时间至少比站点当前时间晚15分钟</li>
					<li>定时周期不能超过15天</li>
				</ul>
            </div>
            
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="timingPublication()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#timingDialog').dialog('close')" style="width:90px">关闭</a>
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
    <div id="aLotModifyCondition" class="easyui-dialog" title="条件选择" style="width:930px"  data-options="iconCls:'icon-save',resizable:true,closed:true,modal:true,
    buttons:[{
				text:'下一步',
				handler:function(){
					aLotModify.conditionNext();
				}
			},
			{
				text:'关闭',
				handler:function(){
					$('#aLotModifyCondition').dialog('close');
					aLotModify.clear();
				}
			}]">
      <div>
      <!--
      <div style="float:left;width:35%">
      
      <fieldset style="border-width: 1px; height:100%;border-radius: 12px; -webkit-border-radius: 12px;-moz-border-radius: 12px;">
		    <legend style="font-weight:800;">过滤条件</legend>
      <div style="height:480px;overflow-y:auto">
      <table id="aLotModifyAttrTable">
      	<tr>
      	<td>范本名称:</td><td><input type="text"  id="id_TEMPLATE_NAME" name="TEMPLATE_NAME"  class="easyui-textbox" /> </td>
      	</tr>
      	<tr>
      	<td>物品标题:</td><td><input type="text" id="id_PRODUCT_TITLE" name="PRODUCT_TITLE" class="easyui-textbox" /> </td>
      	</tr>
      	<tr>
      	<td>物品SKU:</td><td><input type="text" id="id_SKU" name="SKU" class="easyui-textbox" /> </td>
      	</tr>
      	<tr>
      	<td>刊登类型:</td>
      	<td>
      		<select class="easyui-combobox" id="id_PUBLICATION_TYPE" name="PUBLICATION_TYPE" style="width:173px;">
				<option ></option>
				<option value="Chinese">拍卖</option>
				<option value="FixedPriceItem">固价</option>
				<option value="FixedPriceItem1">多属性</option>
			</select> 
		</td>
      	</tr>
      	<tr>
      	<td>eBay账户:</td>
      	<td><input class="esayui-combobox" id="aLotEbayAccount" name="EBAY_ACCOUNT" style="width:173px;" /> </td>
      	</tr>
      	<tr>
      	<td>站点:</td>
      	<td><input class="esayui-combobox" id="aLotEbaySite" name="SITE_ID" style="width:173px;" /> </td>
      	</tr>
      </table>
      </div>
       </fieldset>
      </div>
      -->
      <div id="aLotModifyColumnSet" >
      	<fieldset style="border-width: 1px; border-radius: 12px; -webkit-border-radius: 12px;-moz-border-radius: 12px;">
		    <legend style="font-weight:800;">更改属性</legend>
		    <div style="padding-left: 0px;">【一般信息】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_FIRST_CATEGORY_ID" class="is_text_box" /> 第一分类
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_SECOND_CATEGORY_ID" class="is_text_box" /> 第二分类
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="STORE_FIRST_CATEGORY_ID" class="is_text_box"/> 商店第一分类
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="STORE_SECOND_CATEGORY_ID" class="is_text_box"/> 商店第二分类
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_UPC" class="is_text_box"/> UPC
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_EAN" class="is_text_box"/> EAN
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_ISBN" class="is_text_box" /> ISBN
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_MPN" class="is_text_box" /> MPN
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_BRAND" class="is_text_box"/> Brand
		   		</li>
		   	</ul>
		   	</div>
		   	<div style="padding-left: 0px;padding-top: 50px;">【物品描述】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="TOP_PROMOTION_TYPE"  class="is_combo_box" valueField='value' textField='displayName' url="/ocs/publication/getPromoteList/0"/> 顶部推广
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="FOOTER_PROMOTION_TYPE" class="is_combo_box" valueField='value' textField='displayName' url="/ocs/publication/getPromoteList/1"/> 底部推广
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="SELLER_DESCRIPTION" class="is_combo_box" valueField='value' textField='displayName' url="/ocs/publication/getSellerDescription/0"/> 卖家描述
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="ADVERT_ID" class="is_combo_box" valueField='value' textField='displayName' dataKey='templateBanner'/> 模板广告
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="EBAY_IMAGES" class="is_text_box" /> ebay图片
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="TEMPLATE_IMAGES" class="is_text_box" /> 模板图片
		   		</li>
		   		
		   	</ul>
		   	</div>
		   		<div style="padding-left: 0px;padding-top: 30px;">【拍卖】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="PUBLICATION_DAYS" class="is_combo_box" valueField='value' textField='displayName' dataKey='publicationDays' /> 刊登天数
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRICE" class="is_text_box" /> 价格
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_COUNT" class="is_text_box" /> 数量
		   		</li>
		   	</ul>
		   	</div>
		   		<div style="padding-left: 0px;padding-top: 30px;">【付款】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="PAYPAI_ACCOUNT" class="is_combo_box" valueField='value' textField='displayName' dataKey='payPalAccount'/> PayPal账号
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PAY_DESCRIPTION" class="is_text_box1" /> 付款说明
		   		</li>
		   	</ul>
		   	</div>
		   		<div style="padding-left: 0px;padding-top: 30px;">【物品所在地】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_ADDRESS" class="is_text_box" /> 物品所在地
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="REGION" class="is_combo_box" valueField='country' textField='description' url="/ocs/publication/getRegionList"/> 国家或地区
		   		</li>
		   	</ul>
		   	</div>
		   	<div style="padding-left: 0px;padding-top: 30px;">【国内运输】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="0_TRAN_TYPE_NAME" class="is_combo_box" valueField='value' textField='displayName' dataKey='0_trans'/> 运输方式(国内)
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="0_TRAN_COST" class="is_text_box"/> 运费(国内)
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="0_TRAN_EX_COST" class="is_text_box" /> 额外每件加收(国内)
		   		</li>
		   	</ul>
		   	</div>
		   		<div style="padding-left: 0px;padding-top: 30px;">【国外运输】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="1_TRAN_TYPE_NAME"  class="is_combo_box" valueField='value' textField='displayName' dataKey='1_trans'/> 运输方式(国外)
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="1_TRAN_COST" class="is_text_box"/> 运费(国外)
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="1_TRAN_EX_COST" class="is_text_box"/> 额外每件加收(国外)
		   		</li>
		   	</ul>
		   	</div>
		   	<div style="padding-left: 0px;padding-top: 30px;">【退货政策】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="POLICY_TYPE"  class="is_combo_box" valueField='value' textField='displayName' dataKey='returnPolicy'/> 退货政策
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="RETURN_DAYS" class="is_combo_box" valueField='value' textField='displayName' dataKey='returnPolicyDay'/> 退货天数
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="FARETAKEINHANDER" class="is_combo_box" valueField='value' textField='displayName' dataKey='returnPolicyHander'/> 退货运费负担
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="RETURN_DESCRIPTION" class="is_text_box1" /> 退货政策详情
		   		</li>
		   	</ul>
		   	</div>
		   		<div style="padding-left: 0px;padding-top: 30px;">【其他】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="_buyer_require" class="is_html" /> 买家要求
		   		</li>
		   		
		   	</ul>
		   	</div>
		   		
		  </fieldset>
      </div>
      </div>
    </div>
   <div id="aLotModifyList" class="easyui-dialog" title="编辑" style="width:1500px;height:700px;"  data-options="iconCls:'icon-save',resizable:true,closed:true,modal:true,
   	buttons:[{
				text:'确定',
				handler:function(){
					aLotModify.modifyOk();
				}
		    },{
				text:'关闭',
				handler:function(){
					$('#aLotModifyList').dialog('close');
					$('#aLotModifyCondition').dialog('close');
					aLotModify.clear();
				}
		    }]">
		    <div  class="easyui-layout" style="width:100%;height:100%;" fit="true">

			    <div data-options="region:'east',split:true" style="width:70%;">
			    	<div style="height:100%;">
				    	 <table id="aLotModifyListGrid"  class="easyui-datagrid"></table>  
				    </div>
			    </div>
			    <div data-options="region:'west',split:true" style="width:30%;">
			    	
			    	<div style="height:620px;overflow-y:auto">
				      <table id="aLotModifyAttrSetTable">
				      </table>
				     </div>
			    </div>
			</div>
		    
	</div>
    
    <div id="aLotModifyBuyerRequire" style="display:none;">
    	<div class="col-md-4 form_inline">
		<input type="radio" name="rbtnBuyerRequirementSpecified" id="rbtnBuyerRequirementSpecified1" value="0" checked="checked">
		<label for="rbtnBuyerRequirementSpecified1"> 允许所有买家购买我的物品</label> <br>
		<input type="radio" name="rbtnBuyerRequirementSpecified" id="rbtnBuyerRequirementSpecified2" value="1" >
		<label for="rbtnBuyerRequirementSpecified2">不允许以下买家购买我的物品</label>
		<ul id="ulBuyerRequirementDetail" class="buyerrequirement" style="padding-left:5px;">
		<li id="liLinkedPayPalAccount" style="list-style:none;">
		<input id="isHavePaypalAccount" type="checkbox" name="isHavePaypalAccount" >
		<label for="isHavePaypalAccount">没有 PayPal 账户</label></li>
		<li style="list-style:none;">
		<input id="isOffscale" type="checkbox" name="isOffscale" >
		<label for="isOffscale">主要运送地址在我的运送范围之外</label>
		</li>
		<li style="list-style:none;">
		<input id="chkMaxUnpaidItemStrikesInfoSpecified" type="checkbox" name="chkMaxUnpaidItemStrikesInfoSpecified" > <label for="chkMaxUnpaidItemStrikesInfoSpecified">曾收到</label>
		<select name="ddlMaxUnpaidItemStrikesInfoCount" id="ddlMaxUnpaidItemStrikesInfoCount" class="form-control input_xsmall">
		<option value="2" selected="selected">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
		</select>个弃标个案，在过去
		<select name="ddlMaxUnpaidItemStrikesInfoPeriod" id="ddlMaxUnpaidItemStrikesInfoPeriod" class="form-control input_xsmall">
		<option value="Days_30">30</option>
		<option value="Days_180" selected="selected">180</option>
		<option value="Days_360">360</option>
		</select>天
		</li>
		<li style="list-style:none;">
		<input id="chkMaxBuyerPolicyViolationsSpecified" type="checkbox" name="chkMaxBuyerPolicyViolationsSpecified">
		<label for="chkMaxBuyerPolicyViolationsSpecified">曾收到</label>
		<select name="ddlMaxBuyerPolicyViolationsCount"  id="ddlMaxBuyerPolicyViolationsCount" class="form-control input_xsmall">
		<option value="4">4</option>
		<option value="5">5</option>
		<option value="6">6</option>
		<option value="7">7</option>
		</select>个违反政策检举，在过去
		<select name="ddlMaxBuyerPolicyViolationsPeriod"  id="ddlMaxBuyerPolicyViolationsPeriod" class="form-control input_xsmall">
		<option value="Days_30">30</option>
		<option value="Days_180">180</option>
		</select>天
		</li>
		<li style="list-style:none;">
		<input id="chkMinFeedbackScoreSpecified" type="checkbox" name="chkMinFeedbackScoreSpecified">
		<label for="chkMinFeedbackScoreSpecified">信用指标等于或低于：</label>
		<select name="ddlMinFeedbackScore"  id="ddlMinFeedbackScore" class="form-control input_xsmall">
		<option value="-1">-1</option>
		<option value="-2">-2</option>
		<option value="-3">-3</option>
		</select>
		</li>
		<li style="list-style:none;">
		<input id="chkMaxItemRequirementsMaxItemCountSpecified" type="checkbox" name="chkMaxItemRequirementsMaxItemCountSpecified">
		<label for="chkMaxItemRequirementsMaxItemCountSpecified">在过去10天内曾出价或购买我的物品，已达到我所设定的限制</label>
		<select name="ddlMaxItemRequirementsMaxItemCount"  id="ddlMaxItemRequirementsMaxItemCount" class="form-control input_xsmall">
		<option value="1">1</option>
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
		</li>
		<li style="list-style:none;">
		<ul style="padding-left:5px;">
		<li style="list-style:none;">
		<input id="chkMaxItemRequirementsMinFeedbackScoreSpecified" type="checkbox" name="chkMaxItemRequirementsMinFeedbackScoreSpecified">
		<label for="chkMaxItemRequirementsMinFeedbackScoreSpecified">这项限制只适用于买家信用指数等于或低于</label>
		<select name="ddlMaxItemRequirementsMinFeedbackScore"  id="ddlMaxItemRequirementsMinFeedbackScore" class="form-control input_xsmall">
		<option value="5">5</option>
		<option value="4">4</option>
		<option value="3">3</option>
		<option value="2">2</option>
		<option value="1">1</option>
		<option selected="selected" value="0">0</option>
		</select>
		</li>
		</ul>
		</li>
		</ul>
		</div>
    </div>

<div id="correlationDialog" class="easyui-dialog" closed="true" data-options="iconCls:'icon-save',closable:true,resizable:true,modal:true,
    collapsible:true,maximizable:true" style="height:540px;width:850px;" >
	
	<table id="searchCorrelationDatagrid" class="easyui-datagrid" data-options="toolbar:'#searchCorrelationToolbar'" style="height:220px;width:100%;overflow:auto;"> </table>
    
    <h2 style="margin-left: 14px;">已关联</h2>
    <table id="correlationDatagrid" class="easyui-datagrid"  style="height:120;width:100%;overflow:auto;"> </table>

</div>
<div id="searchCorrelationToolbar">
	<form id="searchCorreCondition">
			<table style="float:left;padding:10px;">
				<tr style="">
					<td width="80"><label style="">eBay 账户:</label></td>
					<td>
						<input class="easyui-textbox"  type="text"  id="coEbayAccount" name="ebayAccount"   style="width:173px;"  />
					 </td>
					 <td><label style="">站点:</label></td>
					<td>
						<input  id="coSiteId" name="siteId" style="float:right" class="easyui-combobox" data-options="valueField:'value',textField:'displayName',url:'${FTL.X.global_domain}/publication/getSiteList'" />
					</td>
					<td width="80"><label style="">刊登类型:</label></td>
					<td>
						<select class="easyui-combobox" id="coPublicationType" name="publicationType" style="width:173px;">
							<option ></option>
							<option value="'Chinese'">拍卖</option>
							<option value="'FixedPriceItem'">固价</option>
							<option value="'FixedPriceItem1'">多属性</option>
						</select>
					</td>
					<td width="50"></td>
				</tr>
				<tr>
					</tr>
					<tr>
					<td><label style="">范本名称:</label></td>
					<td>
						
						<input type="text" name="id" id="coId" hidden="true"/>
						<input type="text" id="coName" name="templateName" class="easyui-textbox" />
					</td>
					<td><label style="">物品号:</label></td>
					<td>
						
						<input type="text" name="itemId" class="easyui-textbox" />
					</td>
					<td><label style="">SKU:</label></td>
					<td>
						
						<input type="text" name="sku" class="easyui-textbox" />
					</td>
					
					<td>
						<div style="clear:both;text-align: left;width:60px;line-height: 25px;">
							<a  href="javascript:void(0);" id="correSearch" onclick="publicationMain.searchLineCorre()"  class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
						</div>
					</td>
				</tr>
				
			</table>
		</form>
</div>

<div id="aLotModifyConditionNew" class="easyui-dialog" title="条件选择" style="width:930px"  data-options="iconCls:'icon-save',resizable:true,closed:true,modal:true,
    buttons:[{
				text:'下一步',
				handler:function(){
					aLotModify.conditionNextNew();
				}
			},
			{
				text:'关闭',
				handler:function(){
					$('#aLotModifyConditionNew').dialog('close');
					aLotModify.clear();
				}
			}]">
      <div>
      <div id="aLotModifyColumnSetNew" >
      	<fieldset style="border-width: 1px; border-radius: 12px; -webkit-border-radius: 12px;-moz-border-radius: 12px;">
		    <legend style="font-weight:800;">更改属性</legend>
		    <div style="padding-left: 0px;">【一般信息】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="EBAY_ACCOUNT"  group="tabbox1" /> ebay账户
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PUBLICATION_TYPE" group="tabbox1"   /> 刊登类型
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="SITE_ID"  group="tabbox1"  /> 站点
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="SKU" group="tabbox1"  /> SKU
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_FIRST_CATEGORY_ID" group="tabbox1"  /> 第一分类
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_SECOND_CATEGORY_ID"  group="tabbox1"  /> 第二分类
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="STORE_FIRST_CATEGORY_ID" group="tabbox1"  /> 商店第一分类
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="STORE_SECOND_CATEGORY_ID" group="tabbox1"  /> 商店第二分类
		   		</li>
		   	</ul>
		   	</div>
		   	<div style="padding-left: 0px;padding-top: 50px;">【eBay物品描述】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="TOP_PROMOTION_TYPE" group="tabbox5" /> 顶部推广
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="FOOTER_PROMOTION_TYPE" group="tabbox5" /> 底部推广
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="SELLER_DESCRIPTION"  group="tabbox5"/> 卖家描述
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="ADVERT_ID"  group="tabbox5"/> 模板广告
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="EBAY_IMAGES"  group="tabbox5"/> ebay图片
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="TEMPLATE_IMAGES" group="tabbox5" /> 模板图片
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="COMMENTS"  group="tabbox5"/> 描述
		   		</li>
		   	</ul>
		   	</div>
		   		<div style="padding-left: 0px;padding-top: 30px;">【拍卖】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="PUBLICATION_DAYS"  group="tabbox9" /> 刊登天数
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRICE"  group="tabbox9" /> 价格
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_COUNT" group="tabbox9" /> 数量
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="ACCEPT_BUYER_COUNTER" link="ACCEPT_BUYER_COUNTER,ACCEPT_BUYER_COUNTER_MIN,ACCEPT_BUYER_COUNTER_MAX" group="tabbox9" /> 接受买家还价
		   		</li>
		   	</ul>
		   	</div>
		   	<div style="padding-left: 0px;padding-top: 30px;">【付款】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="PAYPAI_ACCOUNT"  group="tabbox10"/> PayPal账号
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="AUTO_PAY" group="tabbox10"/> 是否自动付款
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="SUPPORT_PAYPAI_INFO" group="tabbox10" /> 其他付款方式
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="PAY_DESCRIPTION" class="is_text_box1" group="tabbox10" /> 付款说明
		   		</li>
		   	</ul>
		   	</div>
		   	<div style="padding-left: 0px;padding-top: 30px;">【买家要求】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="BUYER_REQ" group="tabbox11" link="ALLOW_ALLBUYER,NO_PAYPAL,OUT_SHIP_COUNTRY,BUYER_REQ_1,BUYER_REQ_2,BUYER_REQ_3,BUYER_REQ_4,BUYER_REQ_4_1"/> 买家要求
		   		</li>
		   	</ul>
		   	</div>
		   	<div style="padding-left: 0px;padding-top: 30px;">【退货政策】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   	    <li style="float:left;">
		   			<input type="checkbox" name="RETUEN_POLOCY" group="tabbox12" link="POLICY_TYPE,RETURN_DAYS,ALLOW_DELAY,RETURN_TYPE,FARETAKEINHANDER,DEPRECIATION_RATE,RETURN_DESCRIPTION" /> 退货政策
		   		</li>
		   	</ul>
		   	</div>
		   	<div style="padding-left: 0px;padding-top: 30px;">【物品所在地】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<input type="checkbox" name="PRODUCT_ADDRESS" group="tabbox13" /> 物品所在地
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="REGION" group="tabbox13" /> 国家或地区
		   		</li>
		   		<li style="float:left;">
		   			<input type="checkbox" name="POST_CODE" group="tabbox13" /> 邮编
		   		</li>
		   	</ul>
		   	</div>
		   	<div style="padding-left: 0px;padding-top: 30px;">【运输】</div>
		    <div style="border-top:1px solid #ccc;">
		   	<ul style="list-style-type:none;padding-left: 0px;">
		   		<li style="float:left;">
		   			<li style="float:left;">
		   			<input type="checkbox" name="TRANKING_INFO" group="tabbox14" link="domesticTrans,DOMESTIC_OPT_DAY"/> 国内运输
		   			<input type="checkbox" name="TRANKING_INFO" group="tabbox14" link="calCulateTrans"/> 国际运输
		   			<input type="checkbox" name="TRANKING_INFO" group="tabbox14" link="SHIP_LOCATION_OVER"/> 不运送区域
		   		</li>
		   		</li>
		   	</ul>
		   	</div>
		  </fieldset>
      </div>
      </div>
    </div>
    
<div id="aLotModifyWindow" class="easyui-dialog" title="批量修改" style="width:1200px;height:750px;"  data-options="iconCls:'icon-save',resizable:true,closed:true,modal:true,
    buttons:[{
				text:'保存',
				handler:function(){
					aLotModify.saveUpdateData();
				}
			},
			{
				text:'关闭',
				handler:function(){
					$('#aLotModifyWindow').dialog('close');
					aLotModify.clearNew();
				}
			}]">
	<div id="aLotModifypage" style="height:98%">
	
	</div>
	
</div>
</@FTL.admin>