<#macro AdminJavaScript release=true kindeditor=false xiuxiueditor=false>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/easyui/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/app/js/main.js"></script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/app/js/admin/public.js"></script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/app/js/admin/outOfBounds.js"></script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/app/js/ocs.js"></script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" src="${FTL.X.global_domain}/assets/kindeditor/lang/zh_CN.js"></script>
</#macro>
<#macro adminCSS release=true kindeditor=false>
<link href="${FTL.X.global_domain}/assets/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css"/>
<link href="${FTL.X.global_domain}/assets/easyui/themes/color.css" rel="stylesheet" type="text/css"/>
<link href="${FTL.X.global_domain}/assets/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
<link href="${FTL.X.global_domain}/assets/app/css/publication/sprite/public-icons.css" rel="stylesheet" type="text/css"/>
    <#if kindeditor>
    <link href="${FTL.X.global_domain}/assets/kindeditor/themes/default/default.css" rel="stylesheet"
          type="text/css">
    </#if>
</#macro>

<#macro addScripts files=[] release=false>
    <#list files as file>
    <script type="text/javascript"
            src="${FTL.X.global_domain}/assets/app/js/${file}?rand=${.now}"></script>
    </#list>
</#macro>

<#macro addStyles files=[] release=false>
    <#list files as file>
    <link href="${FTL.X.global_domain}/assets/app/css/${file}?rand=${.now}" rel="stylesheet">
    </#list>
</#macro>