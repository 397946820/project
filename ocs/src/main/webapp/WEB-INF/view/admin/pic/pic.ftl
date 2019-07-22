<@FTL.admin id="PicList" title="图片列表" add_script_files=['admin/pic/pic.js','image_upload.js']>
<div data-options="region:'center',border:false">
    <table id="picDatagrid" class="easyui-datagrid"
           data-options="
           url:'${FTL.X.global_domain}/pic/list',
           fitColumns:true,
           columns: [
            [
                {field: 'id', title: 'id',hidden:true, width: 100},
                {field: 'imgType', title: 'imgType',hidden:true, width: 100},
                {field: 'categoryId', title: 'categoryId',hidden:true, width: 100},
                {field: 'name', title: '图片名称'},
                {field: 'categoryName', title: '类目名称'},
                {field: 'url', title: 'image',formatter:getPicServlet},
                {field: 'ebayPicRelation', title: 'ebay图片关联信息'},
                {field: 'creationDate', title: '创建时间',formatter:getTime},
                {field: 'lastUpdationDate', title: '最后更新时间',formatter:getTime}
            ]
        ],
        queryParams:{
        	param:{
        		name:'',
        		categoryIds:'',
        		
        	}
        },
        idField: 'id',
        singleSelect: true,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#picToolbar',
        onRowContextMenu:picCotextMenu">
    </table>
</div>
<div id="picCotextMenu" class="easyui-menu" style="width:120px;">
    <div id="editePicContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
    <div id="removePicContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="picToolbar" style="padding:10px;">
   <table>
       <tr>
	       <td>
      		   <input id="paramCategory" class="easyui-combobox" style="width: 150px"/>
	       </td>
       </tr>
   </table>
   <a id="picAddLinkbutton" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
       plain="true">添加</a>
    <a id="picEditLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
       plain="true">编辑</a>
    <a id="picRemoveLinkbutton" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
       plain="true">删除</a>

 </div>
<div id="picDialog" class="easyui-dialog" title="添加图片" style="width:500px;height:450px;padding:10px;"
     data-options="modal:true,
     closed:true,
     toolbar: [{
        text:'保存',
        iconCls:'icon-save',
        handler:picFormSave
    }]">
    <form id="picForm" action="${FTL.X.global_domain}/pic/save" enctype="multipart/form-data" method="post">
        <input type="hidden" name="id"/>
        <table>
            <tbody>
            <tr>
                <td>类目：</td>
                <td>
                    <input id="categoryId" class="easyui-combobox" name="categoryId" data-options="required:true" style="width: 250px"/>
                </td>
            </tr>
            <tr>
                <td>图片名称：</td>
                <td>
                     <input type="text" class="easyui-validate easyui-textbox" name="name" style="width: 250px"/>
                </td>
            </tr>
            <tr>
                <td>ebay图片关联信息：</td>
                <td height="20">
                    <input type="text" class="easyui-validate easyui-textbox" name="ebayPicRelation" style="width: 250px"/>
                </td>
            </tr>
            <tr>
                <td>图片：</td>
                <td>
                	<input class="easyui-filebox" style="width:250px" data-options='onChange:change_photo,prompt:"选择上传图片"' id="file_upload" name="file"/>
                	<div id="Imgdiv" style="padding-top:10px;margin-left:25px;">
				        <img id="Img" width="200px" height="200px"/>
				    </div>
				    <input type="text" name="url" hidden="true"/>
                </td>
            </tr>
            
        </table>
    </form>
</div>
</@FTL.admin>