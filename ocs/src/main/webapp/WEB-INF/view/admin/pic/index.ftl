<#macro categoryInfo>

<div class="easyui-layout" fit="true">
<div id="picDialog" class="easyui-dialog" title="添加图片" style="width:500px;height:500px;padding:10px;"
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
                    <input id="categoryId" class="easyui-combobox" name="categoryId" data-options="" style="width: 250px">
                </td>
            </tr>
            <tr>
                <td>图片名称：</td>
                <td>
                     <input type="text" class="easyui-validate" name="name" style="width: 250px"/>
                </td>
            </tr>
            <tr>
                <td>ebay图片关联信息：</td>
                <td>
                    <input type="text" class="easyui-validate" name="ebayPicRelation" style="width: 250px"/>
                </td>
            </tr>
            <tr>
                <td>图片：</td>
                <td>
                	<input class="easyui-filebox" style="width:300px" data-options='onChange:change_photo,prompt:"选择上传图片"' id="file_upload" name="file"/>

                	<div id="Imgdiv">
				        <img id="Img" width="200px" height="200px"/>
				    </div>
				    <input type="text" name="url" hidden="true"/>
                </td>
            </tr>
            
        </table>
    </form>
</div>
<div id="picCategoryDialog" class="easyui-dialog" title="添加类目" style="width:500px;height:300px;padding:10px;"
     data-options="modal:true,
     closed:true,
     toolbar: [{
        text:'保存',
        iconCls:'icon-save',
        handler:picCategoryFormSave
    }]">
    <form id="picCategoryForm" action="${FTL.X.global_domain}/picCategory/save" method="post">
        <input type="hidden" name="id"/>
        <table>
            <tbody>
            <tr>
                <td>父类目：</td>
                <td>
                	<input type="text" id="pid" hidden="true" name="pid" class="easyui-validate" style="width: 250px" readonly="true" />
                	<input type="text" id="parentName" name="parentName" class="easyui-validate" style="width: 250px" readonly="true" />
                </td>
            </tr>
            <tr>
                <td>类目名称：</td>
                <td><input type="text" name="name" class="easyui-validate" data-options="required:true" style="width: 250px"/></td>
            </tr>
            <tr>
                <td>PA图片类目关联信息：</td>
                <td>
                    <input type="text" class="easyui-validate" name="ebayRelationInfo"
                           data-options="required:true" style="width: 250px"/>
                </td>
            </tr>
        </table>
    </form>
</div>
		<div data-options="region:'west',split:true" title="类目" style="width: 260px; height: 500px;">
			<div class="easyui-panel" style="padding:5px;" style="width: 260px;">
			<div id="picCategoryToolbar">
			    <a id="picCategoryAddLinkbutton" onclick="addPicCategory()"  href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
			       plain="true">添加</a>
			    <a id="picCategoryEditLinkbutton" onclick="editPicCategory()" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
			       plain="true">编辑</a>
			    <a id="picCategoryRemoveLinkbutton" onclick="deletePicCategory()" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove"
			       plain="true">删除</a>
			</div>
			</div>
			<ul id="picCategoryTreegrid" class="easyui-tree" data-options="url:'${FTL.X.global_domain}/picCategory/list',method:'post',loadFilter:myLoadFilter"></ul>
			
		</div>
		<div data-options="region:'center',title:'图片',iconCls:'icon-ok'" style="900px">
		
			<div class="easyui-panel" style="padding:5px;">
				<form id="picCondition">
			<table style="float:left">
				<tr >
					<td><label style="">名称:</label></td>
					<td style="height:23px">
						<input type="text" name="name" style="float:right" class="easyui-textbox" />
					</td>
					<td>
						<input type="radio" name="categoryIds" value="1"/> 全局
					</td>
					<td>
						<input type="radio" name="categoryIds" value="0"/>当前分类以及子分类
					</td>
					<td>
						<a  href="javascript:void(0);" id="picSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;height:23px">搜索</a>
					</td>
					<td>
						<a id="picAddLinkbutton" onclick="addPicSun()" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"
       					plain="true">添加</a>
					    <a id="picEditLinkbutton" onclick="editPicSun()" href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit"
					       plain="true">编辑</a>
					</td>
			    </tr>
			</table>
	  </form>
			</div>
			<div id="content" class="easyui-panel"  data-options="fit：true"></div>
			<div id="pp" class="easyui-pagination" data-options="pageList: [100],total: 0,pageSize: 1"></div>
		</div>
	</div>
	
</#macro>