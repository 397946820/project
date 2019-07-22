<@FTL.admin id="SiteList" title="站点列表" add_script_files=['admin/site/site.js']>
<div data-options="region:'center',border:false">
    <table id="siteDatagrid" class="easyui-datagrid"
           data-options="
           url:'${FTL.X.global_domain}/site/list',
           fitColumns:true,
           columns: [
            [
                {field: 'id', title: 'id',hidden:true, width: 100},
                {field: 'name', title: '站点名称'},
                {field: 'ico', title: '站点图标',formatter:getImage},
                {field: 'ebaySiteRelation', title: 'ebay站点关联key'},
                {field: 'creationDate', title: '创建时间',formatter:getTime},
                {field: 'lastUpdationDate', title: '最后更新时间',formatter:getTime}
            ]
        ],
        idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#siteToolbar',
        onRowContextMenu:siteCotextMenu">
    </table>
</div>
<div id="siteCotextMenu" class="easyui-menu" style="width:120px;">
    <div id="editeSiteContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removeSiteContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="siteToolbar">
    <a id="siteAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">添加</a>
    <a id="siteEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
    <a id="siteRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
</div>
<div id="siteDialog" class="easyui-dialog" title="添加站点" style="width:500px;height:300px;padding:10px;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:siteFormSave
    }]">
    <form id="siteForm" action="${FTL.X.global_domain}/site/save" method="post">
        <input type="hidden" name="id"/>
        <table>
            <tbody>
            <tr>
                <td>站点名称：</td>
                <td><input type="text" name="name" class="easyui-validate easyui-textbox" data-options="required:true" style="width: 250px"/></td>
            </tr>
            <tr>
                <td style="padding-top:10px;">站点图标：</td>
                <td style="padding-top:10px;"><input type="text" name="ico" class="easyui-validate easyui-textbox" style="width: 250px"/></td>
            </tr>
            <tr>
                <td style="padding-top:10px;">ebay站点关联key：</td>
                <td style="padding-top:10px;">
                    <input type="text" class="easyui-validate easyui-textbox" name="ebaySiteRelation"
                           data-options="required:true" style="width: 250px"/>
                </td>
            </tr>
        </table>
    </form>
</div>

</@FTL.admin>