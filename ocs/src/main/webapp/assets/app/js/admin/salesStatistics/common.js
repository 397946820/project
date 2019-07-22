var isBoolean = false;
$(function() {

	if(source == '' && permission == '') {
		$.ajax({
			url : GLOBAL.domain + "/role/queryByUserId",
			type : 'POST',
			success : function(data) {
				if(data != '') {
					var rows = JSON.parse(data);
					for (var i = 0; i < rows.length; i++) {
						if(rows[i].code == 'ADMINISTRATOR' || rows[i].code == 'CEO') {
							isBoolean = true;
							break;
						}
					}
					
				}
			}
		})
		$.ajax({
			url : GLOBAL.domain + "/salesStatistics/getPermission",
			type : 'POST',
			success : function(data) {
				permission = data;
				source = getSource(permission);
				 if(source == 'ebay') {
					 $('#detailsExport').show();
				 } else {
					 $('#detailsExport').hide();
				 }
				init();
				setPlatform(permission,'');
			}
		})
	}
	
	// 不能选择未来时间
	$.extend($.fn.datebox.defaults.rules, {
		checkDate : {
			validator : function(value, param) {
				var arr1 = value.split("-");
				var date = new Date(arr1[0], parseInt(arr1[1]) - 1, arr1[2]);
				var nowDate = new Date();
				return nowDate >= date;
			},
			message : "不能选择未来时间"
		}
	});
	
})

function getSource(data) {
	if(data != '') {
		var rows = JSON.parse(data);
		var children = rows.children;
		if(children != null) {
			if(children.length > 1) {
				$("#source_").show();
				var s = "";
				for (var i = 0; i < children.length; i++) {
					var sub = children[i];
					s += getName(sub.name);
				}
				if(s.indexOf('amazon') != -1) {
					return 'amazon';
				} else if(s.indexOf('ebay') != -1){
					return 'ebay';
				} else if(s.indexOf('light') != -1){
					return 'light';
				} else if(s.indexOf('walmart') != -1){
					return 'walmart';
				}
			} else {
				$("#source_").hide();
				var row = children[0];
				return getName(row.name);
			}
		}
	}
	return '';
}

function showOrHide(value,arr,flag) {
	if(flag == 'show') {
		for (var i = 0; i < arr.length; i++) {
			$(value).datagrid('showColumn', arr[i]);
		}
	} else if(flag == 'hide'){
		for (var i = 0; i < arr.length; i++) {
			$(value).datagrid('hideColumn', arr[i]);
		}
	}
}

function onload(value,flag) {
	if(isBoolean && flag == '' && source != 'allSource') {
		showOrHide(value,new Array('sku','asin','platform','station','currencycode','deduction','taxrate','price','priceTax','priceExcludingTax','status','af','sf','co','afRate','sfRate','coRate'),'show');
		showOrHide(value,new Array('terrace','priceUsd','priceRmb','totalAf','totalSf','totalCo','totalAfRate','totalSfRate','totalCoRate'),'hide');
	}
	if(source == 'amazon') {
		if(flag == 'flag') {
			$(value).datagrid('showColumn', 'purchaseat');
			$(value).datagrid('showColumn', 'lastestshipdate');
			$(value).datagrid('showColumn', 'updatedat');
			$(value).datagrid('hideColumn', 'lastfetchtime');
			$(value).datagrid('hideColumn', 'paidtime');
			$(value).datagrid('hideColumn', 'createdat');
		} else {
			$(value).datagrid('hideColumn', 'priceTax');
			$(value).datagrid('hideColumn', 'priceExcludingTax');
		}
		$(value).datagrid('showColumn', 'asin');
		$(value).datagrid('showColumn', 'station');
		$(value).datagrid('showColumn', 'price');
	} else if(source == 'ebay') {
		if(flag == 'flag') {
			$(value).datagrid('hideColumn', 'purchaseat');
			$(value).datagrid('hideColumn', 'updatedat');
			$(value).datagrid('showColumn', 'lastfetchtime');
			$(value).datagrid('showColumn', 'paidtime');
			$(value).datagrid('showColumn', 'lastestshipdate');
			$(value).datagrid('hideColumn', 'createdat');
		} else {
			$(value).datagrid('showColumn', 'priceTax');
			$(value).datagrid('showColumn', 'priceExcludingTax');
		}
		$(value).datagrid('hideColumn', 'asin');
		$(value).datagrid('hideColumn', 'price');
		$(value).datagrid('showColumn', 'station');
	} else if (source == 'light' || source == 'walmart') {
		if(flag == 'flag') {
			$(value).datagrid('hideColumn', 'purchaseat');
			$(value).datagrid('hideColumn', 'lastfetchtime');
			$(value).datagrid('hideColumn', 'paidtime');
			$(value).datagrid('hideColumn', 'lastestshipdate');
			$(value).datagrid('showColumn', 'createdat');
			if(source == 'light'){
				$(value).datagrid('showColumn', 'updatedat');
			} else {
				$(value).datagrid('hideColumn', 'updatedat');
			}
		} else {
			$(value).datagrid('hideColumn', 'priceTax');
			$(value).datagrid('hideColumn', 'priceExcludingTax');
		}
		$(value).datagrid('hideColumn', 'asin');
		if(source != 'light') {
			//$(value).datagrid('hideColumn', 'station');	
		}
		$(value).datagrid('showColumn', 'price');
	} else if (source == 'allSource') {
		if(flag == '' && isBoolean) {
			var boolean = false;
			var rows = $('#salesStatisticsDatagrid').datagrid('options').queryParams.param;
			if(rows != null) {
				var sku = rows.sku;
				if(sku != null && sku != '') {
					boolean = true;
				}
			}
			showOrHide(value,new Array('asin','platform','station','currencycode','deduction','taxrate','price','priceTax','priceExcludingTax','status','af','sf','co','afRate','sfRate','coRate'),'hide');
			showOrHide(value,new Array('terrace','priceUsd','priceRmb','totalAf','totalSf','totalCo','totalAfRate','totalSfRate','totalCoRate'),'show');
			if(boolean) {
				$(value).datagrid('showColumn', 'sku');
			} else {
				$(value).datagrid('hideColumn', 'sku');
			}
		}
	}
}

function getName(name) {
	if(name == '亚马逊') {
		return 'amazon';
	} else if(name == 'Ebay') {
		return 'ebay';
	} else if(name == '官网') {
		return 'light';
	} else if(name == '沃尔玛') {
		return 'walmart';
	}
	return '';
}

function setPlatform(data,value) {
	if(data != '') {
		var rows = JSON.parse(data);
		var children = rows.children;
		if(children != null) {
			for (var i = 0; i < children.length; i++) {
				var row = children[i];
				var source_ = getName(row.name);
				if(source_ == source) {
					var children1 = row.children;
					if(children1 != null) {
						var platform_ = [{text:'-- 请选择 --', value:''}];
						if(value == '') {
							for (var j = 0; j < children1.length; j++) {
								var element1 = children1[j];
								var platform = {
										text : element1.name, 
										value: element1.name
								};
								platform_.push(platform);
							}
							$('#platform').combobox('loadData',platform_);
						} else {
							for (var j = 0; j < children1.length; j++) {
								if(children1[j].name == value) {
									var children2 = children1[j].children;
									if(children2 != null) {
										for (var z = 0; z < children2.length; z++) {
											var element2 = children2[z];
											var platform = {
													text : element2.name, 
													value: element2.name
											};
											platform_.push(platform);
										}
										$('#station').combobox('loadData',platform_);
									}
								}
							}
						}
					}
				}
			}
		}
	}
}

function echo(newValue) {
	$('#station_').hide();
	
	reset_(newValue);
}

function reset_(newValue) {
	
	if(newValue != 'allSource') {
		setPlatform(permission,'');
		$("#platform_").show();
		$("#asin_").show();
		$("#status_").show();
		$("#whichTime_").show();
	}
	if(newValue == 'amazon') {
		$('#whichTime').combobox('loadData',[{text:'购买时间', value:'purchaseat'},{text:'发货时间', value:'lastestshipdate'},
											 {text:'订单更新时间', value:'updatedat'}]);
		$('#status').combobox('loadData', [{text:'-- 请选择 --', value:''},{text:'Shipped', value:'Shipped'},{text:'Pending', value:'Pending'},
			 							   {text:'Unshipped', value:'Unshipped'},{text:'Canceled', value:'Canceled'}]);
		$('#whichTime').combobox('setValue','purchaseat');
		$('#asin_').show();
	} else if (newValue == 'ebay') {
		$('#whichTime').combobox('loadData',[{text:'支付时间', value:'paidtime'},{text:'发货时间', value:'lastestshipdate'},
			 								 {text:'最新拉取数据时间', value:'lastfetchtime'}]);
		$('#status').combobox('loadData', [{text:'-- 请选择 --', value:''},{text:'Completed', value:'Completed'},{text:'Cancelled', value:'Cancelled'},
										   {text:'Inactive', value:'Inactive'},{text:'Active', value:'Active'}]);
		$('#whichTime').combobox('setValue','paidtime');
		$('#asin_').hide();
		$('#station_').show();
	} else if (newValue == 'light') {
		$('#whichTime').combobox('loadData',[{text:'订单发货时间', value:'lastestshipdate'},{text:'订单创建时间', value:'createdat'},{text:'订单更新时间', value:'updatedat'}]);
		//$('#whichTime').combobox('setValue','lastestshipdate');
		$('#whichTime').combobox('setValue','createdat');
		$('#asin_').hide();
		$('#station_').hide();
		$('#status').combobox('loadData', [{text:'-- 请选择 --', value:''},{text:'complete', value:'complete'},{text:'closed', value:'closed'}]);
	} else if (newValue == 'walmart') {
		$('#whichTime').combobox('loadData',[{text:'订单创建时间', value:'createdat'},{text:'订单发货时间', value:'lastestshipdate'}]);
		$('#whichTime').combobox('setValue','createdat');
		$('#asin_').hide();
		$('#station_').hide();
		$('#status').combobox('loadData', [{text:'-- 请选择 --', value:''},{text:'Shipped', value:'Shipped'},{text:'Acknowledged', value:'Acknowledged'}
											,{text:'Cancelled', value:'Cancelled'},{text:'Created', value:'Created'}]);
	} else if(newValue == 'allSource') {
		$('#whichTime').combobox('loadData',[{text:'', value:''}]);
		$('#status').combobox('loadData', [{text:'', value:''}]);
		$('#platform').combobox('loadData', [{text:'', value:''}]);
		$('#station').combobox('loadData', [{text:'', value:''}]);
	}
}

function getParam(value) {
	
	var result = '';
	
	var param =  {
		source : source,
		sku : $('#sku').textbox('getValue'),
		platform : $('#platform').combobox('getValue'),
		status : $('#status').combobox('getValue'),
		whichTime : $('#whichTime').combobox('getValue'),
		starttime : $('#starttime').datebox('getValue'),
		endtime : $('#endtime').datebox('getValue')
	}
	if(value == "") {
		var timeQuantum = {
				timeQuantum : $('#timeQuantum').combobox('getValue')
		}
		result = Object.assign(param,timeQuantum);
	} else {
		result = param;
	}
	
	var param_ = {};
	if(source == 'amazon') {
		param_ = {
			asin : $('#asin').textbox('getValue'),	
			station : $('#station').combobox('getValue')
		} 
	} else if(source == 'ebay') {
		param_ =  {
			station : $('#station').combobox('getValue')
		}
	}
	
	return Object.assign(result,param_);
}

function reset(newValue,value) {
	$('#sku').textbox('setValue','');
	$('#asin').textbox('setValue','');
	$('#platform').combobox('setValue','');
	$('#station').combobox('setValue','');
	$('#status').combobox('setValue','');
	$('#starttime').datebox('setValue','');
	$('#endtime').datebox('setValue','');
	if(value == '') {
		$('#timeQuantum').combobox('setValue','-30');
		$("#time_").hide();
	}
}
