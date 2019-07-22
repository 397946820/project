<@FTL.admin id="itemDataLink" title="刊登数据结构映射" add_script_files=['admin/sys/itemDataLink.js','ocs.js']>
<div data-options="region:'west'" style="width:50%">
	<ul id="itemDataTree"></ul>
</div>
<div data-options="region:'east',border:false" style="width:50%">
<div class="easyui-panel" title="" style="width:400px">
		<div style="padding:10px 60px 20px 60px">
	    <form id="itemDataTreeNodeForm" method="post">
	    	<input type="hidden" name="parentId"/>
	    	<table cellpadding="5">
	    		<tr id="itemDataDescId">
	    			<td>id:</td>
	    			<td><input class="easyui-textbox" type="text" name="id" editable="false" data-options=""></input></td>
	    		</tr>
	    		<tr>
	    			<td>Name:</td>
	    			<td><input class="easyui-textbox" type="text" id="itemDataDescName" name="name"  editable="false" data-options=""></input></td>
	    		</tr>
	    		<tr>
	    			<td>显示名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="displayName" data-options=""></input></td>
	    		</tr>
	    		<tr>
	    			<td>映射数据:</td>
	    			<td><input type="hidden" name="link" id="link"><input class="easyui-textbox" id="linkView" type="text" name="linkView" data-options=""></input><a href="javascript:void(0)" id="checkLinkOpt">选择</a></td>
	    		</tr>
	    		
	    	</table>
	    </form>
	    <div style="text-align:center;padding:5px">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="itemDataadd()">新增</a>
	    	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">保存</a>
	    </div>
	    </div>
	</div>
</div>
<div id="selectitemDataWin" class="easyui-dialog" title="My Dialog" style="width:300px;height:500px;"
    data-options="resizable:true,modal:true,closed:true">
   <ul id="selectitemDataTree"></ul>
</div>
</@FTL.admin>