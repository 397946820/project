<@FTL.admin id="Main" title="工作台" add_script_files=['admin/index.js'] add_style_files=['index_header.css']>
<div data-options="region:'north'" style="height:62px;">
<div class="box">
			<img class="header_img" src="assets/app/images/header.png" />
			<ul class="system">
				<#if projects??>
					<#list projects as projectInfo>
						<#if project?? && project.name==projectInfo.name>
							<li class="active">
						<#else>
							<li>
						</#if>
							<input type="hidden" id="project_${(projectInfo.name)!''}" value="${(projectInfo.id)!'0'}">
							${(projectInfo.name)!''}
						</li>
					</#list>
				</#if>
			</ul>
			<ul class="info">
				<li>欢迎您，${(user.nick)!''}</li>
				<li id="changePwd">修改密码</li>
				<li id="logout">退出</li>
			</ul>
		</div>
</div>

<div data-options="region:'east',split:true,collapsed:true" title="信息提醒" style="width:180px"></div>
<div data-options="region:'west',split:true" title="我的工作台" style="width:180px">
    <div class="easyui-tabs" data-options="fit:true,border:false">
        <div title="功能菜单">
            <ul id="mainTree">
                
            </ul>
        </div>
    </div>
     <div id="menuEvent" class="easyui-menu" style="width:120px;">
        <div onclick="extensionClickHandler()" data-options="iconCls:'icon-add'">打开新菜单</div>
    </div>
</div>

<div data-options="region:'center'">
    <div id="mainTabs" class="easyui-tabs" data-options="fit:true,border:false"></div>
</div>
<div id="pwdChangeDialog" class="easyui-dialog" title="修改密码" style="width:500px;height:165px;padding:10px;"
     data-options="modal:true,
     closed:true,
     toolbar: [{
        text:'保存',
        iconCls:'icon-save',
        handler:pwdChangeSave
    }]">
    <form id="pwdChangeForm" action="${FTL.X.global_domain}/user/changePassword" method="post">
        <table>
            <tbody>
	            <tr>
	                <td>旧密码：</td>
	                <td><input type="password" id="oldPwd" name="departmentId" class="easyui-validate" data-options="required:true,validType:'pwdIsTrue'" style="width: 250px"/></td>
	            </tr>
	            <tr>
	                <td>新密码：</td>
	                <td><input type="password" id="newPwd" name="userName" class="easyui-validate" data-options="required:true" style="width: 250px"/></td>
	            </tr>
	            <tr>
	                <td>确认密码：</td>
	                <td><input type="password" id="confirmPwd" name="userCode" class="easyui-validate" data-options="required:true,validType:'confirmpassword'" style="width: 250px"/></td>
	            </tr>
            </tbody>
        </table>
    </form>
</div>
</@FTL.admin>