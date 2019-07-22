var baseDate=[];
(function(baseDate,$){
	baseDate.getSiteCurrencyById= function(siteId){
		var currency = '';
		if(siteId==0){
			currency='USD';
		}else if(siteId==3){
			currency = 'GBP';
		}else if(siteId==77){
			currency='EUR'
		}
		return currency;
	}
	baseDate.getSiteNameById = function(siteId){
		var siteName = "";
		if(siteId==0){
			siteName="美国";
		}else if(siteId==3){
			siteName="英国";
		}else if(siteId==77){
			siteName="德国";
		}
		return siteName;
	}
})(baseDate,jQuery);
