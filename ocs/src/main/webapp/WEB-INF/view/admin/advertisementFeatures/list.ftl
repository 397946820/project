<@FTL.admin id="AdvertisementFeaturesList" title="广告特色列表"
add_script_files=['admin/advertisementFeatures/advertisementFeatures.js']>
<div data-options="region:'center',border:false">
	<table id="advertisementFeaturesDataGrid" class="easyui-datagrid"
		data-options="
           url:'${FTL.X.global_domain}/advertisementFeatures/findAll',
           fitColumns:true,
           columns: [
            [
                {field : 'id',checkbox : true},
                {field : 'siteId',checkbox : true,hidden:true},
                {field: 'name', title: '名称', width: 160},
                {field: 'ico', title: '站点', width: 200,formatter:getImage},
                {field: 'featureProperty', title: '广告特色', width: 240,
                	formatter:function(value,row,index){ 
                		return '<br/>' + value.replace(/,/g,'<br/>');
                	}
                }
            ]
        ],
        idField: 'id',
        singleSelect: false,
        rownumbers: true,
        pagination: true,
        pageSize: 50,
        border:false,
        fit:true,
        toolbar:'#advertisementFeaturesToolbar',
        onRowContextMenu:advertisementFeaturesCotextMenu">
	</table>
</div>
<div id="advertisementFeaturesCotextMenu" class="easyui-menu" style="width: 120px;">
	<div id="advertisementFeaturesEditContextMenu" data-options="iconCls:'icon-edit'">编辑</div>
	<div id="advertisementFeaturesRemoveContextMenu" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="advertisementFeaturesToolbar">
	<a id="advertisementFeaturesAddLinkbutton" href="javascript:void(0)"
		class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a> <a
		id="advertisementFeaturesEditLinkbutton" href="javascript:void(0);"
		class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑</a> <a
		id="advertisementFeaturesRemoveLinkbutton" href="javascript:void(0);"
		class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
</div>
<div id="advertisementFeaturesDialog" class="easyui-dialog" title="添加/编辑广告特色"
	style="width: 800px; height: 400px; padding: 10px;"
	data-options="modal:true,
     closed:true,
     toolbar: [{
        text:'保存',
        iconCls:'icon-save',
        handler:advertisementFeaturesFormSave
    },{
        text:'关闭',
        iconCls:'icon-no',
        handler:advertisementFeaturesFormNo
    }]">
	<form id="advertisementFeaturesForm"
		action="${FTL.X.global_domain}/advertisementFeatures/saveEdit"
		method="post">
		<input type="hidden" name="id" />
		<table align="center">
			<tbody>
				<tr>
					<td>名称：</td>
					<td><input type="text" name="name" class="easyui-validate"
						data-options="required:true" style="width: 250px" /></td>
				</tr>
				<tr>
					<td>站点：</td>
					<td><select name="siteId" id="siteId" style="width: 250px">
							<option value="18">美国</option>
							<option value="19">加拿大(英语)</option>
							<option value="20">加拿大(法语)</option>
							<option value="21">英国</option>
							<option value="22">澳大利亚</option>
							<option value="23">奥地利</option>
							<option value="24">比利时(法语)</option>
							<option value="25">比利时(荷兰语)</option>
							<option value="26">法国</option>
							<option value="27">德国</option>
							<option value="28">意大利</option>
							<option value="29">荷兰</option>
							<option value="30">西班牙</option>
							<option value="31">瑞士</option>
							<option value="32">eBay 汽车</option>
							<option value="33">香港</option>
							<option value="34">新加坡</option>
							<option value="35">印度</option>
							<option value="36">爱尔兰</option>
							<option value="37">马来西亚</option>
							<option value="38">菲律宾</option>
							<option value="39">波兰</option>
							<option value="40">俄罗斯</option>
					</select></td>
				</tr>
				<tr>
					<td valign="top">广告特色：</td>
					<td>
						<input type="checkbox" name="featureProperty" value="橱窗展示">橱窗展示 <font color="green"> $</font><br /> 
						<input type="checkbox" name="featureProperty" value="国际站点曝光" /> 国际站点曝光 <font color="green"> $</font><br /> 
						<input type="checkbox" name="featureProperty" value="粗体" /> 粗体 <font color="green"> $</font>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>

</@FTL.admin>
