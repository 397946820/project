<@FTL.admin id="skuLinkManager" title="sku映射关系管理"
add_script_files=['admin/eda/skuLinkManager.js']>
<div class="easyui-panel">
		<form id="skuLinkManagerSearchParam">
		<table style="float:left; min-width:1000px;">
			
			<tr style="min-width:1000px;">
				<td><label style="">平台SKU:</label></td>
				<td><input type="text" name="pSku"  class="easyui-textbox" />
				</td>
				<td><label style="">原始SKU:</label></td>
				<td><input type="text" name="sku"  class="easyui-textbox" />
				</td>
				<td>
					<div style="clear:both;text-align: left;width:200px;line-height: 25px;">
					    	<a  href="javascript:void(0);" id="skuLinkManagerReset" class="easyui-linkbutton" iconCls="icon-clear" style="float:right;margin-left:10px;" >重置</a>
							<a  href="javascript:void(0);" id="skuLinkManagerSearch" class="easyui-linkbutton" iconCls="icon-search" style="float:right;">搜索</a>
					</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
<table id="skuLinkManagerList"  style="width:100%;height:95%" class="easyui-datagrid">        
</table>
<div id="skuLinkManagerDia" class="easyui-dialog" title="编辑" iconCls="icon-edit" style="width:500px;height:400px;"  data-options="iconCls:'icon-save',resizable:true,closed:true,modal:true,
   	buttons:[{
				text:'确定',
				handler:function(){
					skuLinkManager.pSkuSave();
				}
		    },{
				text:'取消',
				handler:function(){
					$('#pSkuLinkListGrid').datagrid('reload');
					$('#pSkuInput').textbox('setValue','');
					$('#skuLinkManagerDia').dialog('close');
				}
		    }]">
		    <div  class="easyui-layout" style="width:100%;height:100%;" fit="true">

					<div style="height: 10%;padding-top: 10px;padding-left: 10px;">
						<input class="easyui-textbox" type="text" label="平台SKU" id="pSkuInput" name="pSku" style="width:225px;" data-options="required:true" />
					</div>
			    	<div style="height:80%;">
				    	 <table id="pSkuLinkListGrid"  class="easyui-datagrid"></table>  
				    </div>
			   
			</div>
		    
	</div>
	
	<div id="skuListDia"  style="display:none;">
		    <div  class="easyui-layout" style="width:100%;height:100%;" fit="true">

					<div style="height: 10%;padding-top: 10px;padding-left: 65px;">
						<input class="easyui-textbox" type="text" id="skuChooseSku" name="sku" style="width:225px;"  />
						<a  href="javascript:void(0);" id="skuChooseSearch" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
					</div>
			    	<div style="height:80%;">
				    	 <table id="skuListGrid"  class="easyui-datagrid"></table>  
				    </div>
			   
			</div>
		    
	</div>
	<div id="fileUpload" class="easyui-dialog" title="文件上传" style="width:400px;height:200px;display:none;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	    <form id="ImportForm" enctype="multipart/form-data" action="/ocs/excel/import/pSkuLinkImport" method="POST">
		    <div style="margin-bottom:20px;width:80%;padding-left: 35px;margin-top: 40px;">
				<div style="float: left;height: 24px;line-height: 24px;padding-right: 10px;">File:</div>
				<input id="fileUploadInput" name="file" data-options="prompt:'Choose a file...'" style="width:80%">
			</div>
			<div style="width:80%;padding-left: 35px;">
				<a href="javascript:void(0);"  id="uploadSubmit" class="easyui-linkbutton" style="width:100%">Upload</a>
			</div>
		</form>
	</div>
	<div id="saleTranImportMessageWin" class="easyui-dialog" title="导入结果" style="width:400px;height:200px;"data-options="iconCls:'icon-save',
		resizable:false,modal:true,collapsible:false,minimizable:false,maximizable:false,closed:true">
	   <div>
	   		<ul id="saleTranImportErrorList"></ul>
	   </div>
	</div>
</@FTL.admin>
