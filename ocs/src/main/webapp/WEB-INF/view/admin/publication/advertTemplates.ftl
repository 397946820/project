<@FTL.admin id="advertTemplate" title="广告模板" add_script_files=['admin/publication/advertTemplates.js','image_upload.js']>

<table id="advertTemplateDatagrid"  class="easyui-datagrid" style="width:100%;height:100%"
            url="${FTL.X.global_domain }/AdvertTemplates/list"
            toolbar="#advertToolbar" pagination="true" idField="id"
            rownumbers="true" fitColumns="true" data-options="queryParams:{param:{ebay_account:'',site_id:''}}" >
        <thead>
            <tr>
            	<th field="id" width="10" hidden="true" ></th>
            	<th field="img_name" width="10" hidden="true" ></th>
            	<th field="pid_id" width="10" hidden="true" ></th>
                <th field="url" width="50" data-options="formatter:getPicServlet">广告图片</th>
                <th field="name" width="50" >广告名称</th>
                <th field="product_url" width="10" data-options="formatter:getTitleAndItemNum">关联产品</th>
                <th field="ebay_account" width="50">Ebay账号</th>
                <th field="site_id" width="50" data-options="formatter:getSiteImage">站点</th>
            </tr>
        </thead>
    </table>
    <div id="advertToolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newAdvert()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editAdvert()">编辑</a>
       <a id="removeLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
    </div>
    <div id="externalDialog" class="easyui-dialog" style="width:600px;height:150px"
            closed="true" buttons="#external-buttons">
            <div></div>
            <div><input id="externavTextId" name="product_url" class="easyui-textbox"  label="外部链接:" style="width:450px">
            </div>
    </div>
    <div id="external-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="getProductUrlById()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#externalDialog').dialog('close')" style="width:90px">取消</a>
    </div>
    <div id="advertDialog" class="easyui-dialog" style="width:580px"
            closed="true" buttons="#dlg-buttons">
        <form id="advertForm"  style="margin:0;padding:20px 50px" action="${FTL.X.global_domain}/AdvertTemplates/save" enctype="multipart/form-data" method="post">
            <div style="margin-bottom:10px">
            	<input style="display:none" type="easyui-textbox" name="id">
            	<input style="display:none" type="easyui-textbox" name="pid_id">
                <input name="name" class="easyui-textbox" required="true"  label="名称:" style="width:100%">
            </div>
             <div style="margin-bottom:10px">
                <input type="text"  id="ebayAccount" name="ebay_account" label="账号:"  required="true" style="width:100%;"  />
            </div>
            <div style="margin-bottom:10px">
                <select id="siteId" label="站点:" name="site_id" class="easyui-combobox"  required="true" data-options="url:'${FTL.X.global_domain }/publication/getSiteList',valueField:'value', textField:'displayName'"  style="width:100%;"></select>
            </div>
            <div style="margin-bottom:10px">
	            <select label="在线刊登:" id='productUrlId' name="product_url" class="easyui-combogrid" style="width:100%" data-options="
				panelWidth: 600,
				idField: 'ebayProductURL',
				textField: 'productTitle',
				url: '${FTL.X.global_domain}/publication/lineList',
				method: 'post',
				columns: [[
					{field:'ebayProductURL',title:'推广 链接',width:200,hidden:true},
					{field:'ebayImages',title:'图片',width:80,formatter:getEbayImage},
					{field:'sku',title:'SKU',width:120},
					{field:'itemId',title:'物品号',width:120,align:'right'},
					{field:'productTitle',title:'标题',width:200,align:'right'},
					{field:'productFirstCategoryId',title:'分类',width:120}
				]],
				fitColumns: true,
				pagination: true,
        		pageSize: 20,
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
	        		conditions:'line'
	        	}
	        }
				">							
				</select><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addExternalUrl()">外部链接</a>
            </div>
            <div style="margin-bottom:10px">
            	<input class="easyui-filebox" style="width:100%" data-options='onChange:change_photo,prompt:"选择上传图片"' id="file_upload" name="file" label="广告图片"/>
           		<div id="Imgdiv">
				        <img id="Img" width="100%" height="200px"/>
				</div>
				<input type="text" name="url" hidden="true" />
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="saveAdvertForm()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#advertDialog').dialog('close')" style="width:90px">关闭</a>
    </div>
	
</@FTL.admin>