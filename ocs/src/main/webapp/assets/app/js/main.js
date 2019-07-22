

//public.js
$.extend($.fn.validatebox.defaults.rules, {
    //验证汉字
    zhCN: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '只能输入汉字'
    },
    //移动手机号码验证 value值为文本框中的值
    mobile: {
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: '输入手机号码格式不准确'
    },
    //国内邮编验证
    zipcode: {
        validator: function (value) {
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字'
    },
    //用户账号验证(只能包括 _ 数字 字母) param的值为[]中值 account[8,20]
    account: {
        validator: function (value, param) {
            if (value.length < param[0] || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';
                return false;
            } else {
                if (!/^[\w]+$/.test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成';
                    return false;
                } else {
                    return true;
                }
            }
        }, message: ''
    }
});
var onChange = function (event) {
    var $row = undefined;
    if('datagrid' == event.data.type) $row = $(event.data.grid).datagrid('getSelected');
    if('treegrid' == event.data.type) $row = $(event.data.grid).treegrid('getSelected');
    if (!$row) {
        $.messager.alert('消息提示', '请先选择记录', 'error');
    } else {
        $.messager.confirm('请先确认', '确定要删除记录吗？', function (r) {
            if (r) {
                $.ajax({
                    url: GLOBAL.domain + event.data.url,
                    type: 'post',
                    dataType: 'json',
                    data: 'id=' + $row.id,
                    beforeSend: function () {
                        $.messager.progress({
                            title: '请稍后',
                            msg: '正在删除记录...'
                        });
                    },
                    complete: function () {
                        $.messager.progress('close');
                    },
                    success: function (data) {
                        if (data) {
                            $.messager.alert('消息提示', data.description, (data.errorCode == 0) ? 'info' : 'error');
                            if('datagrid' == event.data.type){
                                $(event.data.grid).datagrid('clearSelections');
                                $(event.data.grid).datagrid('reload');
                            }

                            if('treegrid' == event.data.type){
                                $(event.data.grid).treegrid('clearSelections');
                                $(event.data.grid).treegrid('reload');
                            }
                        }
                    },
                    error: function () {
                        $.messager.alert('消息提示', '系统开小差，不给力', 'error');
                    }
                });
            }
        });
    }
    event.preventDefault();
};

var errorTip = function (data) {
    var $error = '<ol style="color:red;padding-left:60px;">', $tips = [];
    $.each(data.errors, function (k, v) {
        var $el = $('[name="' + k + '"]');
        $error += '<li>' + v + '</li>';
        $el.tooltip({
            content: '<span style="color:red;">' + v + '</span>',
            position: 'bottom',
            hideEvent: 'click'
        });
        $tips.push($el);
    });
    $error += '</ol>';
    $.messager.alert('消息提示', $error, 'error', function () {
        $.each($tips, function () {
            $(this).tooltip('show');
        });
    });
};

var rowStyler = function (row) {
    if (row.status == 'DISABLED') {
        return 'background-color:#CCCCCC;color:#FFFFFF;';
    }
    if (row.status == 'LOCKED') {
        return 'background-color:#FFFF00;color:#FF0000;';
    }
    if (row.status == 'EXPIRED') {
        return 'background-color:#FF0000;color:#FFFFFF;';
    }
};


$(function () {
    $('body').css('visibility', 'visible');
    $('input').attr('autocomplete', 'off').validatebox({tipPosition: 'bottom'});
    $('div.easyui-dialog').dialog({
        onClose: function () {
            $('.tooltip').remove();
        }
    });
});
