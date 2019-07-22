<#macro imgSelect>
	<div class="easyui-dialog" id="selectImgModel" style="height:600px;width:950px;" data-options="resizable:true,closed:true,modal:true">
		<div class="easyui-layout" fit="true">
			<div data-options="region:'west',split:true"  style="width: 200px; height: 500px;">
				<div>
					<a href="javascript:void(0);"  id="imageUploadSubmit" class="easyui-linkbutton" style="width:100%">Images Upload</a>
				</div>
				<ul id="picCategoryTreegrid" class="easyui-tree" ></ul>
			</div>
			<div data-options="region:'center',iconCls:'icon-ok'" style="">
				
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
						<a  href="javascript:void(0);" id="imagListDelete" class="easyui-linkbutton" iconCls="icon-delete" style="margin:0 5px;">删除</a>
					</span>
				</div>
				
				<div id="ebayImgListGrid" class="easyui-panel"  fit="true"  >
					<div class="img-box" style="margin-left:30px;"></div>
				</div>
				<div id="ebayImgListPage" class="easyui-pagination" data-options="pageList: [50],total: 0,pageSize: 1"></div>
			</div>
			<div data-options="region:'south',border:false" style="text-align:right; background: #eee;height: 50px;padding-top:10px;padding-right:10px;">
				<a class="easyui-linkbutton c8" data-options="iconCls:'icon-ok'" href="javascript:void(0)" id="imagesChooseOk" style="width:80px">确定</a>
				<a class="easyui-linkbutton  c8" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#selectImgModel').dialog('close')" style="width:80px">取消</a>
			</div>
		</div>
		
	</div>
     
	<div id="imgUploadDialog" class="easyui-dialog" title="图片上传" style="width:800px;height:400px;padding:5px;"data-options="resizable:true,modal:true, closed:true">
		<table id="imgUploadList"></table>
		<div id="imgUploadListTb">
			<a href="javascript:void(0);" class="easyui-linkbutton" id="fileChoose" data-options="iconCls:'icon-add'">选择</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" style="float: right;padding-right: 5px;"id="fileUploadRun" data-options="iconCls:'icon-ok'">开始上传</a>
			<input type="file" id="file" name="myfile"  style="display:none" accept=".jpg,.png" multiple/>
		</div>
	</div>
<!--<div id="picCategoryDialog" class="easyui-dialog" title="添加类目" style="width:500px;height:300px;padding:10px;"
     data-options="modal:true,
     closed:true,
     toolbar: [{
        text:'保存',
        iconCls:'icon-save',
        handler:picCategoryFormSave
    }]">
    <form id="picCategoryForm" action="${FTL.X.global_domain}/picCategory/save" method="post">
        <input type="hidden" name="id"/>
        <table style="margin-left:30px;">
            <tbody>
            <tr>
                <td height="40">父类目：</td>
                <td>
                	<input type="text" id="pid" hidden="true" name="pid" class="easyui-validate" style="width: 250px" readonly="true" />
                	<input type="text" id="parentName" name="parentName" class="easyui-validate" style="width: 250px" readonly="true" />
                </td>
            </tr>
            <tr>
                <td height="40">类目名称：</td>
                <td><input type="text" name="name" class="easyui-validate" data-options="required:true" style="width: 250px"/></td>
            </tr>
            <tr>
                <td height="40">PA图片类目关联信息：</td>
                <td>
                    <input type="text" class="easyui-validate" name="ebayRelationInfo"
                           data-options="required:true" style="width: 250px"/>
                </td>
            </tr>
        </table>
    </form>
</div>-->
<div id="batchImageDialog" class="easyui-dialog" title="批量添加图片" style="width:850px;height:600px;padding:10px;overflow:auto;"
 	data-options="modal:true,
     closed:true">
    <!--<div class="addButton ssi-button success">添加更多图片</div>-->
	<div class="addImgBox" >
		<input type="file" multiple name="text-container" id="ssi-upload" />
	</div>
</div>
</#macro>
