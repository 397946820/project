<#import "module.ftl" as M />
<#import "config.ftl" as X />
<#import "dom.ftl" as D />
<#macro admin
id=""
title=""
description=""
keywords=""
add_style_files=[]
add_script_files=[]
script_release=false
css_release=false
easyui_layout=true
kindeditor=false
xiuxiueditor=false>
    <@compress single_line=false>
    <!DOCTYPE html>
    <html id="${id}" lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta name="description" content="${description}">
        <meta name="keywords" content="${keywords}">
        <meta name="author" content="xbltd.com">
        <link rel="shortcut icon" href="${FTL.X.global_domain}/favicon.ico" type="image/x-icon"/>
        <title>LE后台管理系统</title>
        <@FTL.M.adminCSS release=css_release!false kindeditor=kindeditor!false/>
        <@FTL.M.addStyles files=add_style_files release=false/>
        <script type="text/javascript">
            var GLOBAL = {
                domain: '${FTL.X.global_domain}'
                <#--portal: '${FTL.X.global_portal}',-->
                <#--upload: '${FTL.X.global_upload}',-->
                <#--cdn: '${FTL.X.global_cdn}'-->
            };
        </script>
    </head>
    <body <#if easyui_layout>class="easyui-layout"</#if> style="visibility:visible;">
        <#nested>
        <@FTL.M.AdminJavaScript release=script_release!false kindeditor=kindeditor!false xiuxiueditor=!false/>
        <@FTL.M.addScripts files=add_script_files release=false/>
    </body>
    </html>
    </@compress>
</#macro>
<#macro savePublication
id=""
title=""
description=""
keywords=""
add_style_files=[]
add_script_files=[]>
    <@compress single_line=false>
    <!DOCTYPE html>
    <html id="${id}" lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta name="description" content="${description}">
        <meta name="keywords" content="${keywords}">
        <meta name="author" content="xbltd.com">
        <link rel="shortcut icon" href="${FTL.X.global_domain}/favicon.ico" type="image/x-icon"/>
        <title>LE后台管理系统</title>
        <@FTL.M.addStyles files=add_style_files release=false/>
        <script type="text/javascript">
            var GLOBAL = {
                domain: '${FTL.X.global_domain}'
                <#--portal: '${FTL.X.global_portal}',-->
                <#--upload: '${FTL.X.global_upload}',-->
                <#--cdn: '${FTL.X.global_cdn}'-->
            };
        </script>
    </head>
    <body style="visibility:visible;">
        <#nested>
        <@FTL.M.addScripts files=add_script_files release=false/>
    </body>
    </html>
    </@compress>
</#macro>