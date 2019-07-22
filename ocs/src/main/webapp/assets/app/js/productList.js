/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-16
 * Time: 上午10:05
 * To change this template use File | Settings | File Templates.
 */
$(function(){
    var speed = 1000;
    $("#pincontainer").masonry({
        singleMode: true,
        //columnWidth: 237,
        itemSelector: '.pin',
        animate: true,
        animationOptions: {
            duration: speed,
            queue: false
        }

    });
});