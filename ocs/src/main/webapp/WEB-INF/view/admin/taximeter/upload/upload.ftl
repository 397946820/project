<#macro upload>
	<div id="uploadDialog" class="easyui-dialog" title="文件导入" closed="true" style="width: 450px; height: 200px; padding: 10px;" modal="true">
		<form id="uploadForm" method="post" enctype="multipart/form-data">
			<input class="easyui-filebox" name="file" id="file" data-options="prompt:'请选择文件...'" buttonText="选择文件"
				style="height: 30px; width: 350px;"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" style="height: 30px" id="upload">导入</a>
		</form>
		<hr>
		<div id="messages" class="easyui-panel" style="width:410px;height:100px;color: red;"  closed="true">
			<ul id="showMessages_">
			</ul>
		</div>
	</div>
</#macro>