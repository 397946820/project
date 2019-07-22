<@FTL.admin id="picCategorys" title="图片类目" add_style_files=['pic/ssi-uploader.min.css','pic/index.css'] add_script_files=['admin/pic/ssi-uploader.js','admin/pic/category.js','admin/pic/pic.js','admin/ocs/main.js','image_upload.js']>

<div class="easyui-layout" fit="true">
<div id="picCategoryDialog" class="easyui-dialog" title="添加类目" style="width:500px;height:200px;padding:10px;"
     data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:picCategory.picFormSave1
    },{
    	text:'退出',
        iconCls:'icon-no',
        handler:function(){
        	$('#picCategoryDialog').dialog('close');
        }
    }]">
    
    <form id="picForm" >
        <table>
            <tbody>
            <tr style="">
                <td>类目：</td>
                <td>
                    <input type="text" class="easyui-textbox" id="fieldName" name="name" style="width: 250px;border:1px solid #95b8e7;border-radius:5px;padding-top:10px;"/>
                </td>
            </tr>
        </table>
    </form>
</div>

		<div data-options="region:'west',split:true" title="类目" style="width: 260px; height: 500px;">
			<div class="easyui-panel" style="padding:5px;" style="width:260px;">
			<div id="picCategoryToolbar">
			    <a id="picCategoryAddLinkbutton"   href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
			       plain="true">添加</a>
			    <a id="picCategoryEditLinkbutton"  href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
			       plain="true">编辑</a>
			    <a id="picCategoryRemoveLinkbutton"  href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
			       plain="true">删除</a>
			</div>
			</div>
			<ul id="picCategoryTreegrid" class="easyui-tree" ></ul>
			
		</div>
		<div data-options="region:'center',title:'图片'" style="width:100%;min-width:900px">
		
		<div class="easyui-panel" style="padding-bottom:30px;" fit="true">
				<div id="ebayImgListTb">
					<span><label style="">名称:</label></span>
					<span style="height:23px">
						<input type="text" name="name" id="imagesListSearchName" style="float:right" class="easyui-textbox" />
					</span>
					<span>
						<input type="radio" name="categoryIdsType" value="1"/> 全局
					</span>
					<span>
						<input type="radio" checked="checked" name="categoryIdsType" value="0"/>当前分类以及子分类
					</span>
					<span>
						<a  href="javascript:void(0);" id="imagListSearch" class="easyui-linkbutton" iconCls="icon-search" style="margin:0 5px;">搜索</a>
						<a  href="javascript:void(0);" id="imagListDelete" class="easyui-linkbutton" iconCls="icon-remove" style="margin:0 5px;">删除</a>
						<a href="javascript:void(0);"  id="imageUploadSubmit" class="easyui-linkbutton" iconCls="icon-add" style="margin:0 5px;">Upload</a>
					</span>
					<span>
					<input id="allChoose" name="AllChoose" type="checkbox">全选
					</span
				</div>
				
				<div id="ebayImgListGrid" class="easyui-panel"  fit="true"  >
					<div class="img-box" ></div>
				</div>
			
		</div>
		<div id="ebayImgListPage" class="easyui-pagination" data-options="pageList:[50],total:0,pageSize:1" style="position:absolute;bottom:0;left:0;background:white;"></div>
	</div>
	<div id="imgUploadDialog" class="easyui-dialog" title="图片上传" style="width:800px;height:400px;padding:5px;"data-options="resizable:true,modal:true, closed:true">
		<table id="imgUploadList"></table>
		<div id="imgUploadListTb">
			<a href="javascript:void(0);" class="easyui-linkbutton" id="fileChoose" data-options="iconCls:'icon-add'">选择</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" style="float: right;padding-right: 5px;"id="fileUploadRun" data-options="iconCls:'icon-ok'">开始上传</a>
			<input type="file" id="file" name="myfile"  style="display:none" accept=".jpg,.png" multiple/>
		</div>
	</div>

</@FTL.admin>