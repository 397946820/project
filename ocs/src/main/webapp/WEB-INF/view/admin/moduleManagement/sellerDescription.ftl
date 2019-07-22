<@FTL.admin id="sellerDescription" title="卖家描述" add_script_files=['admin/seller/sellerDescription.js']>

<table id="sellerDescriptionTable"  class="easyui-datagrid" style="width:100%;height:100%"
            url="${FTL.X.global_domain }/SellerDescription/findSellerDescriptionList"
            toolbar="#sellerToolbar" pagination="true" idField="id"
            iconCls="icon-save" rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
            	<th field="description1" width="10" hidden="true" ></th>
            	<th field="description2" width="10" hidden="true" ></th>
            	<th field="description3" width="10" hidden="true" ></th>
            	<th field="description4" width="10" hidden="true" ></th>
            	<th field="description5" width="10" hidden="true" ></th>
                <th field="ck" width="10" checkbox="true"></th>
                <th field="name" width="50">名称</th>
                <th field="siteId" width="50" data-options="field:'siteId',formatter:getSiteImage">站点</th>
                <th field="id" width="30" align="center" data-options="field:'_operate',formatter:formatOper">动作</th>
            </tr>
        </thead>
    </table>
    <div id="sellerToolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newseller()">添加</a>
        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editBuyerRequire()">编辑</a> -->
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyseller()">删除</a>
    </div>
    
    <div id="sellerDialog" class="easyui-dialog" style="width:820px"
            closed="true" buttons="#dlg-buttons">
        <form id="sellerForm"  novalidate style="margin:0;padding:20px 50px">
            <div style="margin-bottom:10px">
            	<input style="display:none" type="text" id="sellerTemplateId" name="id">
                <input name="name"id="sellerTemplateName" class="easyui-textbox" required="true"  label="名称:" style="width:50%">
            </div>
             <div style="margin-bottom:10px">

                <input type="text"  class="easyui-combobox" id="sellerTemplateSiteid"  label="站点:"  style="width:50%"  data-options="required:true,editable:false,valueField:'value',textField:'displayName',url:'/ocs/publication/getSiteList' "/>
            </div>
            <div style="margin-bottom:10px">
                <div id="sellerDescTabs" class="easyui-tabs" style="width:700px;height:300px">
					<div title="Payment">
						<textarea id="editor_0" name="content" style="width:695px;height:250px;">  
						    这里输入内容...  
						</textarea> 
					</div>
					<div title="Shipment">
						<textarea id="editor_1" name="content" style="width:695px;height:250px;">  
						    这里输入内容...  
						</textarea> 						
					</div>
					<div title="Return" data-options="animate:true">
						<textarea id="editor_2" name="content" style="width:695px;height:250px;">  
						    这里输入内容...  
						</textarea> 						
					</div>
					<div title="About us">
							<textarea id="editor_3" name="content" style="width:695px;height:250px;">  
						    这里输入内容...  
						</textarea> 					
					</div>
					<div title="FAQ">
						<textarea id="editor_4" name="content" style="width:695px;height:250px;">  
						    这里输入内容...  
						</textarea> 						
					</div>
				</div>
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="saveseller()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#sellerDialog').dialog('close')" style="width:90px">关闭</a>
    </div>

</@FTL.admin>