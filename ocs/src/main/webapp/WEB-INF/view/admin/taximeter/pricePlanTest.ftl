<#import "pricePlanCommon.ftl" as pricePlan />
<@FTL.admin id="pricePlanList" title="价格计划管理测试版"
add_script_files=['admin/taximeter/pricePlanTest.js','admin/taximeter/common.js','admin/taximeter/datagrid-cellediting.js','admin/taximeter/pricePlanCommon.js']>

<@pricePlan.pricePlan></@>

</@FTL.admin>
