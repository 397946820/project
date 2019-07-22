<@FTL.admin id="AreaList" title="地区列表">
<div data-options="region:'center',border:false">
    <table id="areaDatagrid" class="easyui-treegrid"
           data-options="url:'${FTL.X.global_domain}/area/list',
           toolbar:'#areaToolbar',
           idField:'id',
           treeField:'text',
           animate: true,
           fitColumns:true,
           singleSelect:true,
           rownumbers:true,
           pagination:true,
           pageSize:50,
           border:false,
           fit:true,
           rowStyler:onRowStyler,
           onContextMenu:onRowContextMenu">
        <thead>
        <tr>
            <th data-options="field:'id'" hidden="true">id</th>
            <th data-options="field:'_parentId'" hidden="true">parentId</th>
            <th data-options="field:'text'">名称</th>
            <th data-options="field:'pinyin'">拼音</th>
            <th data-options="field:'code'">编号</th>
            <th data-options="field:'link'">外部地址</th>
            <th data-options="field:'logo'">标志</th>
            <th data-options="field:'keywords'">关键字</th>
            <th data-options="field:'description'">描述</th>
            <th data-options="field:'priority'">排序</th>
            <th data-options="field:'status'">状态</th>
        </tr>
        </thead>
    </table>
</div>
<div id="cotextMenu" class="easyui-menu" style="width:120px;">
    <div id="addContextMenu" data-options="iconCls:'icon-add'">新增</div>
    <div id="editeContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removeContextMenu"  data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="areaToolbar">
    <a id="areaAddLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">添加</a>
    <a id="areaEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
    <a id="areaRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>
</div>
<div id="areaDialog" class="easyui-dialog" title="地区管理" style="width:600px;height:400px;padding:10px;"
     data-options="modal:true,
     closed:true,
     toolbar: [{
        text:'保存',
        iconCls:'icon-save',
        handler:areaFormSave
    }]">
    <form id="areaForm" action="${FTL.X.global_domain}/area/form" method="post">
        <table>
            <tbody>
            <tr>
                <td>名称：</td>
                <td>
                    <input type="hidden" name="id"/>
                    <input type="hidden" name="nodeId" id="nodeId"/>
                    <input type="text" id="title" class="easyui-validatebox" name="title"
                           data-options="required:true" style="width:500px;"/>
                </td>
            </tr>

            <tr>
                <td>拼音：</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="pinyin"
                           data-options="required:true" style="width:500px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    编号：
                </td>
                <td>
                    <input type="text" class="easyui-validatebox" name="code"
                           data-options="required:true" style="width:500px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    关键字：
                </td>
                <td>
                    <input type="text" class="easyui-validatebox" name="keywords"
                           data-options="required:false" style="width:500px;"/>
                </td>
            </tr>
            <tr>
                <td>
                    描述：
                </td>
                <td>
                    <textarea name="description" rows="3"
                              style="width:500px;"></textarea>
                </td>
            </tr>
            <tr>
                <td>
                    外部链接：
                </td>
                <td>
                    <input type="text" class="easyui-validatebox" name="link" style="width:500px;"/>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>填写此项将直接访问本地址，以http://开头</td>
            </tr>
        </table>
    </form>
</div>
</@FTL.admin>