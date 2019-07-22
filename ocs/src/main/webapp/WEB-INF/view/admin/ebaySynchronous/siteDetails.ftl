<@FTL.admin id="site" title="站点" add_script_files=['admin/synchronou/siteDetails.js']>
	<div id="td">
	<a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="synchronouSite()" style="width:90px;margin: 10px 20px;">同步站点</a>
	</div>
	
	<table id="siteDg" title="Site" class="easyui-datagrid" style="width:100%;" 
            url='${FTL.X.global_domain }/SiteDetails/findSiteDetailsList'
            toolbar="#td" pagination="true" fit="true" 
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="site" width="50">Site</th>
                <th field="site_id" width="50">SiteId</th>
                <th field="detail_version" width="50">DetailVersion</th>
                <th field="update_time" width="50">UpdateTime</th>
                <th field="currency" width="50">Currency</th>
                <th field="url" width="50">Url</th>
                <th field="abbreviation" width="50">Abbreviation</th>
            </tr>
        </thead>
    </table>

</@FTL.admin>