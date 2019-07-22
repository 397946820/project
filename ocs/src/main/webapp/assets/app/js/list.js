$(function(){

    TOP.api('rest', 'get',{
        method:'taobao.taobaoke.widget.items.convert',
        track_iids:'19237740785_track_11116',
        fields:'num_iid,click_url,commission_rate'
    },function(resp){
        if(resp.error_response){
            return false;
        }
        var respItem=resp.taobaoke_items.taobaoke_item;
        for(var i=0;i<respItem.length;i++){
            alert(respItem[i].num_iid);
            //$("#r"+respItem[i].num_iid).html("佣金："+Number(respItem[i].commission_rate)/100+"%");
            //$("#a"+respItem[i].num_iid).attr("href",respItem[i].click_url);
        }})
})