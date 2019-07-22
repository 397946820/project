<@FTL.admin id="NoArriveRegionList" title="不运送地区"
add_script_files=['admin/noArriveRegion/noArriveRegion.js']
add_style_files=['noShip.css']>
<div data-options="region:'center',border:false">
	<table id="noArriveRegionDataGrid" class="easyui-datagrid"
		data-options="
           url:'${FTL.X.global_domain}/noArriveRegion/findAll',
           fitColumns:true,
           columns: [
            [
                {field : 'id',checkbox : true},
                {field : 'siteId',hidden : true},
                {field: 'name', title: '名称', width: 160},
                {field: 'ico', title: '站点', width: 200,formatter:getImage},
                {field: 'noArriveRegions', title: '不运送地区', width:300}
                
                
            ]
        ],
        idField: 'id',
        singleSelect: false,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#noArriveRegionToolbar',
        onRowContextMenu:noArriveRegionCotextMenu">
	</table>
</div>
<div id="noArriveRegionCotextMenu" class="easyui-menu"
	style="width: 120px;">
	<div id="noArriveRegionEditContextMenu"
		data-options="iconCls:'icon-edit'">编辑</div>
	<div id="noArriveRegionRemoveContextMenu"
		data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="noArriveRegionToolbar">
	<a id="noArriveRegionAddLinkbutton" href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> <a
		id="noArriveRegionEditLinkbutton" href="javascript:void(0);"
		class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a> <a
		id="noArriveRegionRemoveLinkbutton" href="javascript:void(0);"
		class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
</div>
<div id="noArriveRegionDialog" class="easyui-dialog" title="添加/编辑不运送地区"
	style="width: 500px; height: 350px; padding: 10px;"
	data-options="modal:true,
     closed:true,
     buttons: [{
        text:'保存',
        iconCls:'icon-save',
        handler:noArriveRegionFormSave
    },{
        text:'关闭',
        iconCls:'icon-no',
        handler:noArriveRegionFormNo
    }]">
	<form id="noArriveRegionForm">
		<input type="hidden" name="id" id="hid"/>
		<table align="center" cellspacing="20">
			<tbody>
				<tr>
					<td style="min-width:40px;"><label>名称：</label></td>
					<td><input type="text" name="name" id="name" class="easyui-validate easyui-textbox"
						data-options="required:true" style="width: 70%" /></td>
				</tr>
				<tr >
					<td>站点：</td>
					<td><input name="siteId" id="siteId" style="width: 70%" class="easyui-combobox" style="width:200px;"
					 data-options="
						 url: '/ocs/publication/getSiteList',
						 valueField :'value',
						 textField :'displayName'
					 ">
					</input></td>
				</tr>
				<tr>
					<td></td>
					<td>
						<div>
							<div style="display: none" id="noArriveRegion"></div>
							<div>
								<a href="javascript:void(0);"> <span id="create">
										创建不运送地区列表</span> <span id="edit" style="display: none;">
										编辑不运送地区列表</span>
								</a>
								<div>
									<font color="green"> 在您创建了不运送地区列表之后，系统会自动开启【买家条件】功能。
										在不运送列表国家或地区注册的用户，将被自动屏蔽掉。不过，您可以随时做更改。 </font>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

<div id="noShipDialog" class="easyui-dialog" title="不运送地区"
	style="width: 900px; height: 450px; padding: 10px;position: relative;"
	data-options="modal:true,
     closed:true,
     buttons: [{
        text:'确认',
        iconCls:'icon-save',
        handler:noShipSave
    },{
        text:'关闭',
        iconCls:'icon-no',
        handler:noShipNo
    }]">
	<div class="domestic" style="margin-left: 36px;" >
		<div class="domesticChoose">
			<div style=" margin=10px 0;font-weight:300;">Domestic</div>
			<div  class="subRegion">
				
			</div>
		</div>
		<div class="domesticChoose">
			<div style=" margin=10px 0;font-weight:300;">Domestic</div>
			<div  class="subRegion">
			</div>
		</div>
		<div class="domesticChoose">
			<div style=" margin=10px 0;font-weight:300;">Domestic</div>
			<div  class="subRegion">
			</div>
		</div>
	</div>
	<div class="international" style="max-height: 230px;overflow: auto;">
	<div style=" margin=10px 0;font-weight:300;">International</div>
		<div class="left">
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Africa" id="Africa"> <label for="Africa">非洲</label> <span>[<a
						class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Europe" id="Europe"> <label for="Europe">欧洲</label> <span>[<a
						class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Oceania" id="Oceania"> <label for="Oceania">大洋洲</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
		</div>
		<div class="center">
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" id="Asia" value="Asia"> <label for="Asia">亚洲</label> <span>[<a
						class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" id="Middle East" value="Middle East"> <label for="Middle East">中东</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Southeast Asia" id="Southeast Asia"> <label for="Southeast Asia">东南亚</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
		</div>
		<div class="right">
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="Central America and Caribbean" id="Central America and Caribbean">
					<label for="Central America and Caribbean">中美洲和加勒比海</label> <span>[<a class="showAll"
						href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="North America" id="North America"> <label for="North America">北美洲</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
			<div class="region">
				<div class="subRegion">
					<input type="checkbox" value="South America" id="South America"> <label for="South America">南美洲</label>
					<span>[<a class="showAll" href="javascript: void(0);">显示所有国家</a>]
					</span>
				</div>
				<div class="content">
					<ul>
						
					</ul>
				</div>
			</div>
		</div>
		
		
	</div>
	<div id="divAdditionalLocations" style="margin-left: 34px;">
		<p>Additional Locations</p>
		<div class="subRegion">
				<input type="checkbox" id="POBox" value="PO Box"> <label for="POBox">PO Box</label>
				<div id="Packstationen2" style="display:none;display: inline-block;margin-left: 40px;"><input type="checkbox" id="Packstationen" value="Packstation"> <label for="Packstationen">Packstationen</label></div>
		</div>
	</div>
	<div class="showSelected" style="position: absolute;bottom: 0px;">
		<span class="noSelected">您尚未选择国家或地区</span> <span class="selected"
			style="display: none;">您已经选择的国家或地区:&nbsp;&nbsp;&nbsp; <span id="noShip" style="font-weight:bold;"></span>
			[<a class="cancelSelected" href="javascript:void(0);">取消已选</a>]
		</span>
	</div>
</div>

</@FTL.admin>
