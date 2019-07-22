
//提示框
function xbsAlert(tip,rollBack){
    setTimeout(function(){
        var box =new BlackBox();
        box.alert(tip,rollBack,{
            title: '温馨提示',
            value: '确定'
        })
    },350);
}
/**
 * yea or no提示框
 * @param tip
 * @param fun 传回参数 yes则为true,no则为false
 *
 * 例子:
 * xbandConfirm("确定要提交么?",function(data){
 *  alert(data);
 * })
 */
function xbsConfirm(tip,rollBack){
    var result = false;
    var box  =new BlackBox();
    box.confirm(tip,rollBack, {
        title: '温馨提示',
        value: '确定'
    })
}