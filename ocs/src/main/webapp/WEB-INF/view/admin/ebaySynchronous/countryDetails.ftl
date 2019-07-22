<@FTL.admin id="country" title="国家或地区" add_script_files=['admin/synchronou/countryDetails.js']>
	<div id="countryBar">
	<a href="javascript:void(0)" class="easyui-linkbutton c8" iconCls="icon-ok" onclick="synchronouCountry()" style="width:130px;margin: 10px 20px;">同步国家或地区</a>
	</div>
	<table id="countryDg" title="country" class="easyui-datagrid" style="width:100%;"
            url='${FTL.X.global_domain }/CountryDetails/findCountryDetailsList'
            toolbar="#countryBar" pagination="true" idField="itemid" fit="true"
            iconCls="icon-save" rownumbers="true" fitColumns="true" >
        <thead>
            <tr>
                <th field="country" width="50">Country</th>
                <th field="description" width="50">Description</th>
                <th field="detail_version" width="50">DetailVersion</th>
                <th field="update_time" width="50">UpdateTime</th>
            </tr>
        </thead>
    </table>
</@FTL.admin>