<@FTL.admin id="recycleBestOffers" title="议价回收站" add_script_files=['admin/ebaySales/recycleBestOffers.js']>
<table id="recycleBestOffersTable" 
            url="${FTL.X.global_domain }/BestOffers/selectBestOfferItems" 
            style="height:100%"
            singleSelect="true" 
            fitColumns="true"
            idField="id"
            pagination="true">
        <thead>
            <tr>
                <th field="productTitle"  width="200" align="center">物品标题</th>
                <th field="itemId" width="80" align="center">物品号</th>
                <th field="sku"  width="80" align="center">SKU</th>
                <th field="siteName"  width="80" align="center">站点</th>
                <th field="inland" width="80" align="center">国内运输费用/加收费用</th>
                <th field="internation" width="80" align="center">国际运输费用/加收费用</th>
                <th field="endDate" width="80" align="center">刊登结束时间</th>
                <th field="status" width="80" align="center">未处理数量</th>
            </tr>
        </thead>
 </table>
 
</@FTL.admin>