

function getStringByDateformat(date, format) {   
    if (!date) return;   
    if (!format) format = "yyyy-MM-dd";   
    switch(typeof date) {   
        case "string":   
            date = new Date(date.replace(/-/, "/"));   
            break;   
        case "number":   
            date = new Date(date);   
            break;   
    }    
    if (!date instanceof Date) return;   
    var dict = {   
        "yyyy": date.getFullYear(),   
        "M": date.getMonth() + 1,   
        "d": date.getDate(),   
        "H": date.getHours(),   
        "m": date.getMinutes(),   
        "s": date.getSeconds(),   
        "MM": ("" + (date.getMonth() + 101)).substr(1),   
        "dd": ("" + (date.getDate() + 100)).substr(1),   
        "HH": ("" + (date.getHours() + 100)).substr(1),   
        "mm": ("" + (date.getMinutes() + 100)).substr(1),   
        "ss": ("" + (date.getSeconds() + 100)).substr(1)   
    };       
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
        return dict[arguments[0]];   
    });                   
}   
/*
 * yyyy-MM-dd hh:mm:ss
 */
function convertDateFromString(dateString) { 
	if (dateString) { 
	var arr1 = dateString.split(" "); 
	var sdate = arr1[0].split('-'); 
	var date = new Date(sdate[0], sdate[1]-1, sdate[2]); 
	return date;
	} 
	}
function formatDateTime(date) {  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? ('0' + m) : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    var h = date.getHours();  
    h=h < 10 ? ('0' + h) : h;  
    var minute = date.getMinutes();  
    minute = minute < 10 ? ('0' + minute) : minute;  
    var second=date.getSeconds();  
    second=second < 10 ? ('0' + second) : second;  
    return y + '-' + m + '-' + d;  
}; 
function addDate(date, days) {
       /* if (days == undefined || days == '') {
            days = 1;
        }*/
        var date = new Date(date);
        date.setDate(date.getDate() + days);
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return date;
    }