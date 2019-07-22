<@FTL.admin id="goodPlace" title="物品所在地" add_script_files=['admin/seller/goodPlace.js']>
<table id="goodTable"  class="easyui-datagrid" style="width:100%;height:100%">
</table>

    <div id="goodDialog" class="easyui-dialog" style="width:500px"
            closed="true" buttons="#dlg-buttons">
        <form id="goodForm" method="post" novalidate style="margin:0;padding:20px 50px">
            <div style="margin-bottom:10px">
            	<input style="display:none" id="saveAsId" type="easyui-textbox" name="id">
                <input name="name" class="easyui-textbox" id="title" required="true"  label="名称:" style="width:90%;">
            </div>
            <div>
				<div style="margin-bottom:10px">
					<input type="text"  class="easyui-combobox" id="siteId" name="ddleBaySiteID" label="站点："   style="width:90%"  data-options="valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList'"/>
				</div>
			</div>
            <div style="margin-bottom:10px">
                 <input type="text"  class="easyui-combobox" id="region" name="ddleBaySiteID" label="国家或地区:"  style="width:90%"  data-options="valueField:'country',textField:'description',url:'/ocs/publication/getRegionList'"/>
            </div>
            <div style="margin-bottom:10px">
                <input name="address" id="productAddress" class="easyui-textbox" label="物品所在地:" style="width:90%">
            </div>
            <div style="margin-bottom:10px;" >
                <input id="postCode" name="post_code" class="easyui-textbox"  label="邮编:" style="width:90%">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="saveGood()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#goodDialog').dialog('close')" style="width:90px">关闭</a>
    </div>
</@FTL.admin>