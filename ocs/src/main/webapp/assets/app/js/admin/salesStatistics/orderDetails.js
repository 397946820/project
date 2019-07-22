//双击效果
function doDblClickRow(rowIndex, rowData){
	$("#orderId_").html("<h3>Order Id:#"+ rowData.orderId + "</h3><p>your Selller Order ID:#"+ rowData.orderId +"</p>");
	$("#details").empty();
	$("#prices").empty();
	$("#details_").empty();
	$("#details").append("<tr><td class='solid-top'>Shipping service</td><td  class='solid-top bold' style='color:#e47911;width:50%;'>Second Day</td></tr>" +
			"<tr><td>Ship by</td><td><span class='helf'></span></td></tr>" +
			"<tr><td>Ship to</td><td><span class='helf'>" + rowData.name + "<br/>" + rowData.street + "<br/>"+ rowData.city + ",&nbsp;" +
			rowData.stateOrRegion + "&nbsp;" + rowData.postalCode +  "</span></td></tr>" +
			"<tr><td>Pruchase date</td><td><span class='helf'>" + gettime(rowData.purchaseat) + "</span></td></tr>" +
			"<tr><td>Fulfillment</td><td><span class='helf'>Amazon</span></td></tr>" +
			"<tr><td>Billing Country</td><td>" + rowData.platform + "<span class='helf'><img src=''/></span></td></tr>" +
			"<tr><td>Sales Channel</td><td>" + rowData.station + "<span class='helf'><img src=''/></span></td></tr>" +
			"<tr><td>Contact Buyer</td><td>" + rowData.customerName + "&nbsp;<span class='helf section-left'>" +
			"<img src='" + GLOBAL.domain + "/assets/app/images/mes.jpg' /></span></td></tr>");	
	
	$("#prices").append("<tr><td class='solid-top'>Items Total</td><td class='solid-top text-right'>" + rowData.currencySymbol + rowData.amount + "</td></tr>" +
			"<tr><td class='bold'>Grand total</td>" +
			"<td  class='text-right' style='color:#0066C3'>" + rowData.currencySymbol + rowData.amount + "</td></tr>");
	
	var unitprice = 0;
	if(rowData.count >  0) {
		unitprice = (rowData.price / rowData.count).toFixed(2);
	}
	var string = '';
	if(rowData.url == null) {
		string += "<td class='bold big' style='color:#4f9900;vertical-align:top;'></td>" +
				"<td class='text-right bold' style='vertical-align:top;'><span>" + rowData.count + "</span></td>" +
				"<td class='text-right bold' style='vertical-align:top;'><span></span></td>" + 
				"<td><p class='bold' style='color:#0066c0;'>" + rowData.title + "</p>";
	} else {
		string += "<td class='bold big' style='color:#4f9900;vertical-align:top;'></td>" +
				"<td class='text-right bold' style='vertical-align:top;'><span>" + rowData.count + "</span></td>" +
				"<td class='text-right bold' style='vertical-align:top;'><span></span></td>" + 
				"<td><p class='bold' style='color:#0066c0;'><a href = '" + rowData.url +"'  target='_blank'>" + rowData.title + "</a></p>";
	}
	
	   string += 	"<img src='" + rowData.productImg + "' class='left pro-img'/><div class='left'>" +
					"<p><span class='det-name'>SKU: </span><span class='det-txt'>" + rowData.sku + "</span></p>" +
					"<p><span class='det-name'>ASIN: </span><span class='det-txt'>" + rowData.asin + "</span></p>" +
					"<p><span class='det-name'>Listing ID: </span><span class='det-txt'></span></p>" +
					"<p><span class='det-name'>Order Item ID: </span><span class='det-txt'>" + rowData.itemId + "</span></p>" +
					"<p class='show-change'><img class='animat-img' src='" + GLOBAL.domain + "/assets/app/images/toup.jpg'/>" +
					"<span class='show-less' style='color:#0066c0;'>show less</span><div class='more-text'></div></p></div></td>" +
					"<td class='text-right' style='vertical-align:top;'>" + rowData.currencySymbol + unitprice + "</td>" +
					"<td style='vertical-align:top;'><table class='section-right2' border='0' cellspacing='0' cellpadding='10'>" +
					"<tr><td class='solid-top'>Items subtotal</td><td class='solid-top text-right'>" + rowData.currencySymbol + rowData.price + "</td></tr>" +
					"<tr><td class='bold'>Item total</td>" +
					"<td class='bold text-right'>" + rowData.currencySymbol + rowData.amount + "</td></tr></table></td>";
	$("#details_").append(string);
	$('#orderDetailsDialog').window('open');
}

$(function() {
	
	// 遮罩
	$('#orderDetailsDialog').dialog({
		modal:true
	});
	
	//开始时间不能选择未来时间
	$("#starttime").datebox({  
	    onSelect:function(date){  
	        var nowDate = new Date();  
	        var endtime = $('#endtime').datebox('getValue');
        	if(date>nowDate){  
        		$("#starttime").datebox("setValue","");
        		$.messager.alert("提示信息", "不能选择未来时间");
        		return;
        	}  
	    }  
	});
	
	//结束时间不能选择未来时间
	$("#endtime").datebox({  
	    onSelect:function(date){ 
	        var nowDate = new Date();  
	        var starttime = $('#starttime').datebox('getValue');
        	if(date>nowDate){  
        		$("#endtime").datebox("setValue","");  
        		$.messager.alert("提示信息", "不能选择未来时间");
 				return;
        	}  
	    }  
	});
	
	$("#orderDetailsDatagrid").datagrid({
		//取消单行选中效果
		onClickRow: function (rowIndex, rowData) {
            $(this).datagrid('unselectRow', rowIndex);
		}
	});
	
	//搜索
	$("#query").click(function(){
		if(!$("#searchFrom").form('validate')) {
			return;
		}
		var starttime = $('#starttime').datebox('getValue');
		var endtime =  $('#endtime').datebox('getValue');
		if(starttime == '' && endtime != '') {
			$.messager.alert("提示信息", "请选择开始时间");
			return;
		}
		if(starttime != '' && endtime == '') {
			$.messager.alert("提示信息", "请选择结束时间");
			return;
		}
		if(endtime != '' && starttime != '') {
			if(endtime < starttime) {
				$.messager.alert("提示信息", "结束时间应大于开始时间");
				return;
			}
		}
		
		var param = {
			sku : $('#sku').textbox('getValue'),
			asin : $('#asin').textbox('getValue'),
			orderId : $('#orderId').textbox('getValue'),
			station : $('#station').combobox('getValue'),
			starttime : $('#starttime').datebox('getValue'),
			endtime : $('#endtime').datebox('getValue')
		}
		
		var options = $('#orderDetailsDatagrid').datagrid('options');
		//url 定义
		options.url = GLOBAL.domain + '/salesStatistics/findOrderDetails';
		$('#orderDetailsDatagrid').datagrid('load',{
			param : param
		});
	});
	
	//重置
	$("#reset").click(function(){
		$('#sku').textbox('setValue','');
		$('#asin').textbox('setValue','');
		$('#orderId').textbox('setValue','');
		$('#station').combobox('setValue','');
		$('#starttime').datebox('setValue','');
		$('#endtime').datebox('setValue','');
	});
})

function gettime(value) {
	if(value != '' && value != null) {
		var time = new Date(value);
		time = time.format("yyyy-MM-dd hh:mm:ss");
		return time;
	}
	return '';
}